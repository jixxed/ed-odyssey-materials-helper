package nl.jixxed.eliteodysseymaterials.templates.generic;

import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
class ShortestPathItem<T extends BlueprintName<T>> extends VBox implements DestroyableTemplate {
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();
    private static final String SHORTEST_PATH_ITEM_LABEL_STYLE_CLASS = "shortest-path-item-label";
    private final PathItem<T> pathItem;
    private final int index;
    private final Expansion expansion;
    private Button removeButton;
    private Button hideButton;
    private Label engineer;
    private Label distanceLabel;
    private Label distance;
    private Label blueprintsLabel;
    private final List<Label> blueprints = new ArrayList<>();

    static {
        NUMBER_FORMAT.setMaximumFractionDigits(2);
    }

    ShortestPathItem(final PathItem<T> pathItem, final int index, final Expansion expansion) {
        this.pathItem = pathItem;
        this.index = index;
        this.expansion = expansion;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.distanceLabel = LabelBuilder.builder().withStyleClass(SHORTEST_PATH_ITEM_LABEL_STYLE_CLASS).withText(LocaleService.getStringBinding("tab.wishlist.travel.path.column.distance")).build();
        this.blueprintsLabel = LabelBuilder.builder().withStyleClass(SHORTEST_PATH_ITEM_LABEL_STYLE_CLASS).withText(LocaleService.getStringBinding("tab.wishlist.travel.path.column.blueprints")).build();
        this.distance = LabelBuilder.builder().withNonLocalizedText(" " + ((this.index > 1) ? "+" : "") + NUMBER_FORMAT.format(this.pathItem.getDistance()) + "Ly").build();
        this.engineer = LabelBuilder.builder().withStyleClass("shortest-path-item-label-big").withText(LocaleService.getStringBinding(() -> " " + LocaleService.getLocalizedStringForCurrentLocale(this.pathItem.getEngineer().getLocalizationKey()))).build();
        this.blueprints.addAll(this.pathItem.getBlueprints().stream().flatMap(temp -> temp.getRecipe().keySet().stream().map(bp -> (bp instanceof HorizonsBlueprint horizonsBlueprint)
                        ? HorizonsBlueprintConstants.getRecipe(horizonsBlueprint.getBlueprintName(), horizonsBlueprint.getHorizonsBlueprintType(), HorizonsBlueprintGrade.GRADE_1)
                        : bp).distinct())
                .collect(Collectors.groupingBy(
                        recipe -> recipe,
                        Collectors.summingInt(value -> 1))
                ).entrySet().stream()
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
                ).toList()
        );

        this.getStyleClass().add("shortest-path-item");
        if (!Engineer.UNKNOWN.equals(this.pathItem.getEngineer())) {
            this.getChildren().addAll(BoxBuilder.builder().withNodes(this.engineer, new GrowingRegion(), LabelBuilder.builder().withStyleClass("shortest-path-item-label-big").withNonLocalizedText("  " + this.index).build()).buildHBox());

            if (!Engineer.REMOTE_WORKSHOP.equals(this.pathItem.getEngineer())) {
                addLocation(this.pathItem.getEngineer());
                this.getChildren().addAll(BoxBuilder.builder().withNodes(this.distanceLabel, this.distance).buildHBox());
                this.getChildren().addAll(new Label());
            }
        } else {
            this.getChildren().addAll(this.engineer);
        }
        this.getChildren().addAll(this.blueprintsLabel);
        this.getChildren().addAll(this.blueprints);
        this.getChildren().addAll(new GrowingRegion("shortest-path-item-spacer"));
        final String wishlistUUID = this.pathItem.getBlueprints().stream().findFirst().map(WishlistBlueprintTemplate::getWishlistUUID).orElse("");
        if (!wishlistUUID.equals(Wishlist.ALL.getUuid())) {
            this.removeButton = ButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.wishlist.travel.path.column.actions.remove")).build();
            this.hideButton = ButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.wishlist.travel.path.column.actions.hide")).build();

            this.removeButton.setOnAction((ActionEvent event) -> EventService.publish((Expansion.HORIZONS.equals(this.expansion)) ? new HorizonsRemoveWishlistShortestPathItemEvent((PathItem<HorizonsBlueprintName>) this.pathItem) : new RemoveWishlistShortestPathItemEvent((PathItem<OdysseyBlueprintName>) this.pathItem)));
            this.hideButton.setOnAction((ActionEvent event) -> EventService.publish((Expansion.HORIZONS.equals(this.expansion)) ? new HorizonsHideWishlistShortestPathItemEvent((PathItem<HorizonsBlueprintName>) this.pathItem) : new HideWishlistShortestPathItemEvent((PathItem<OdysseyBlueprintName>) this.pathItem)));
            this.getChildren().addAll(BoxBuilder.builder().withStyleClass("shortest-path-item-button").withNodes(this.hideButton, new GrowingRegion(), this.removeButton).buildHBox());
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
        final Label label = LabelBuilder.builder().withStyleClass("shortest-path-item-label-value").withNonLocalizedText(engineer.getSettlement().getSettlementName() + " | " + engineer.getStarSystem().getName() + " ").build();
        this.getChildren().add(BoxBuilder.builder().withStyleClass(STYLECLASS_MATERIAL_TOOLTIP_LOCATION_LINE)
                .withOnMouseClicked(event -> {
                    copyLocationToClipboard(engineer.getStarSystem().getName());
                    NotificationService.showInformation(NotificationType.COPY, "Clipboard", "System name copied.");
                }).withNodes(label, new StackPane(ResizableImageViewBuilder.builder()
                        .withStyleClass("material-tooltip-copy-icon")
                        .withImage("/images/other/copy.png")
                        .build())).buildHBox());
    }

    private static void copyLocationToClipboard(final String text) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

    @Override
    public void initEventHandling() {
        //NOOP
    }
}
