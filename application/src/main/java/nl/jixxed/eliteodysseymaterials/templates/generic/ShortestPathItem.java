package nl.jixxed.eliteodysseymaterials.templates.generic;

import javafx.beans.binding.StringBinding;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.ClipboardHelper;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Slf4j
class ShortestPathItem<T extends BlueprintName<T>> extends DestroyableVBox implements DestroyableTemplate {
    private static final String SHORTEST_PATH_ITEM_LABEL_STYLE_CLASS = "shortest-path-item-label";
    private final PathItem<T> pathItem;
    private final int index;
    private final Expansion expansion;

    ShortestPathItem(final PathItem<T> pathItem, final int index, final Expansion expansion) {
        this.pathItem = pathItem;
        this.index = index;
        this.expansion = expansion;
        initComponents();
    }

    @Override
    public void initComponents() {
        DestroyableLabel distanceLabel = LabelBuilder.builder()
                .withStyleClass(SHORTEST_PATH_ITEM_LABEL_STYLE_CLASS)
                .withText("tab.wishlist.travel.path.column.distance")
                .build();
        DestroyableLabel blueprintsLabel = LabelBuilder.builder()
                .withStyleClass(SHORTEST_PATH_ITEM_LABEL_STYLE_CLASS)
                .withText("tab.wishlist.travel.path.column.blueprints")
                .build();
        DestroyableLabel distance = LabelBuilder.builder()
                .withStyleClass("shortest-path-item-distance")
                .withText(((this.index > 1) ? "tab.wishlist.distance.plus" : "tab.wishlist.distance"), Formatters.NUMBER_FORMAT_2.format(this.pathItem.getDistance()))
                .build();
        DestroyableLabel engineer = LabelBuilder.builder()
                .withStyleClass("shortest-path-item-label-big")
                .withText(this.pathItem.getEngineer().getLocalizationKey())
                .build();
        List<DestroyableLabel> blueprints = new ArrayList<>(this.pathItem.getRecipes().entrySet().stream()
//                .map(bp -> (bp instanceof HorizonsBlueprint horizonsBlueprint)
//                        ? HorizonsBlueprintConstants.getRecipe(horizonsBlueprint.getBlueprintName(), horizonsBlueprint.getHorizonsBlueprintType(), HorizonsBlueprintGrade.GRADE_1)
//                        : bp).distinct())
//                .collect(Collectors.groupingBy(
//                        recipe -> recipe,
//                        Collectors.summingInt(value -> 1))
//                ).entrySet().stream()
                .sorted(Comparator.comparing((Map.Entry o) -> o.getKey() instanceof HorizonsExperimentalEffectBlueprint)
                        .thenComparing((Map.Entry o) -> LocaleService.getLocalizedStringForCurrentLocale(((Blueprint) o.getKey()).getBlueprintName().getLocalizationKey())))
                .map(entry ->
                        {
                            final StringBinding stringBinding;
                            if (Expansion.HORIZONS.equals(this.expansion)) {
                                final String localeKey = ((HorizonsBlueprintConstants.getRecipe(((HorizonsBlueprint) entry.getKey()).getBlueprintName(), ((HorizonsBlueprint) entry.getKey()).getHorizonsBlueprintType(), ((HorizonsBlueprint) entry.getKey()).getHorizonsBlueprintGrade())) instanceof HorizonsExperimentalEffectBlueprint)
                                        ? "tab.wishlist.travel.path.column.blueprints.blueprint.horizons.experimental"
                                        : "tab.wishlist.travel.path.column.blueprints.blueprint.horizons";
                                stringBinding = LocaleService.getStringBinding(localeKey,
                                        LocaleService.LocalizationKey.of(entry.getKey().getBlueprintName().getLocalizationKey()),
                                        LocaleService.LocalizationKey.of(getBlueprintType((EngineeringBlueprint<T>) entry.getKey()).getLocalizationKey()),
                                        entry.getValue());
                            } else {
                                stringBinding = LocaleService.getStringBinding("tab.wishlist.travel.path.column.blueprints.blueprint.odyssey", LocaleService.LocalizationKey.of(entry.getKey().getBlueprintName().getLocalizationKey()), entry.getValue());
                            }
                            return LabelBuilder.builder()
                                    .withStyleClasses("shortest-path-item-label-value", "shortest-path-item-label-blueprint")
                                    .withOnMouseClicked(event -> EventService.publish((Expansion.HORIZONS.equals(this.expansion)) ? new HorizonsBlueprintClickEvent(entry.getKey()) : new BlueprintClickEvent(entry.getKey().getBlueprintName())))
                                    .withText(stringBinding
                                    )
                                    .build();
                        }
                ).toList());

        this.getStyleClass().add("shortest-path-item");
        if (!Engineer.UNKNOWN.equals(this.pathItem.getEngineer())) {
            this.getNodes().addAll(BoxBuilder.builder()
                    .withNodes(engineer, new GrowingRegion(), LabelBuilder.builder()
                            .withStyleClass("shortest-path-item-label-big")
                            .withNonLocalizedText("  " + this.index)
                            .build()).buildHBox());

            if (!Engineer.REMOTE_WORKSHOP.equals(this.pathItem.getEngineer())) {
                addLocation(this.pathItem.getEngineer());
                this.getNodes().addAll(BoxBuilder.builder()
                        .withNodes(distanceLabel, distance).buildHBox());
                this.getNodes().addAll(new DestroyableLabel());
            }
        } else {
            this.getNodes().addAll(engineer);
        }
        this.getNodes().addAll(blueprintsLabel);
        this.getNodes().addAll(blueprints);
        this.getNodes().addAll(new GrowingRegion("-spacer"));
//        final String wishlistUUID = this.pathItem.getBlueprints().stream().findFirst().map(WishlistBlueprintTemplate::getWishlistUUID).orElse("");
        if (!ApplicationState.getInstance().getPreferredCommander()
                .map(commander -> WishlistService.getHorizonsWishlists(commander).getSelectedWishlistUUID().equals(Wishlist.ALL.getUuid()))
                .orElse(false)) {
            DestroyableButton removeButton = ButtonBuilder.builder()
                    .withText(LocaleService.getStringBinding("tab.wishlist.travel.path.column.actions.remove"))
                    .withOnAction(_ -> EventService.publish((Expansion.HORIZONS.equals(this.expansion)) ? new HorizonsRemoveWishlistShortestPathItemEvent((PathItem<HorizonsBlueprintName>) this.pathItem) : new RemoveWishlistShortestPathItemEvent((PathItem<OdysseyBlueprintName>) this.pathItem)))
                    .build();
            DestroyableButton hideButton = ButtonBuilder.builder()
                    .withText(LocaleService.getStringBinding("tab.wishlist.travel.path.column.actions.hide"))
                    .withOnAction(_ -> EventService.publish((Expansion.HORIZONS.equals(this.expansion)) ? new HorizonsHideWishlistShortestPathItemEvent((PathItem<HorizonsBlueprintName>) this.pathItem) : new HideWishlistShortestPathItemEvent((PathItem<OdysseyBlueprintName>) this.pathItem)))
                    .build();
            this.getNodes().addAll(BoxBuilder.builder()
                    .withStyleClass("shortest-path-item-button")
                    .withNodes(hideButton, new GrowingRegion(), removeButton).buildHBox());
        }
    }

    private HorizonsBlueprintType getBlueprintType(final EngineeringBlueprint<T> blueprint) {
        if (blueprint instanceof HorizonsEngineeringBlueprint horizonsModuleWishlistBlueprint) {
            return horizonsModuleWishlistBlueprint.getHorizonsBlueprintType();
        }
        return null;
    }

    private static final String STYLECLASS_MATERIAL_TOOLTIP_LOCATION_LINE = "material-tooltip-location-line";

    private void addLocation(final Engineer engineer) {
        final DestroyableLabel label = LabelBuilder.builder()
                .withStyleClass("shortest-path-item-label-value")
                .withNonLocalizedText(engineer.getSettlement().getSettlementName() + " | " + engineer.getStarSystem().getName() + " ")
                .build();
        final DestroyableResizableImageView copyIcon = ResizableImageViewBuilder.builder()
                .withStyleClass("material-tooltip-copy-icon")
                .withImage("/images/other/copy.png")
                .build();
        final DestroyableStackPane copyIconStackPane = StackPaneBuilder.builder()
                .withNodes(copyIcon)
                .build();
        final DestroyableHBox locationLine = BoxBuilder.builder()
                .withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_LOCATION_LINE)
                .withNodes(label, copyIconStackPane)
                .withOnMouseClicked(_ -> {
                    copyLocationToClipboard(engineer.getStarSystem().getName());
                    NotificationService.showInformation(NotificationType.COPY, LocaleService.LocaleString.of("notification.clipboard.title"), LocaleService.LocaleString.of("notification.clipboard.system.copied.text"));
                })
                .buildHBox();
        this.getNodes().add(locationLine);
    }

    private static void copyLocationToClipboard(final String text) {
        ClipboardHelper.copyToClipboard(text);
    }
}
