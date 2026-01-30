package nl.jixxed.eliteodysseymaterials.templates.generic;

import javafx.beans.binding.StringBinding;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.EdAwesomeIconViewPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.EdAwesomeIconViewPane;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.*;

@Slf4j
public class ShortestPathItem<T extends BlueprintName<T>> extends DestroyableVBox implements DestroyableTemplate {
    @Getter
    private final PathItem<T> pathItem;
    private final int index;
    private final Expansion expansion;

    public ShortestPathItem(final PathItem<T> pathItem, final int index, final Expansion expansion) {
        this.pathItem = pathItem;
        this.index = index;
        this.expansion = expansion;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("shortest-path-item");
        DestroyableLabel blueprintsLabel = LabelBuilder.builder()
                .withStyleClass("blueprints-title")
                .withText("tab.wishlist.travel.path.column.blueprints")
                .build();
        DestroyableLabel engineer = LabelBuilder.builder()
                .withStyleClass("engineer")
                .withText(this.pathItem.getEngineer().getLocalizationKey())
                .build();
        final Optional<HorizonsWishlist> horizonsWishlist = ApplicationState.getInstance().getPreferredCommander()
                .map(commander -> WishlistService.getHorizonsWishlists(commander).getSelectedWishlist());
        final Optional<Wishlist> odysseyWishlist = ApplicationState.getInstance().getPreferredCommander()
                .map(commander -> WishlistService.getOdysseyWishlists(commander).getSelectedWishlist());
        List<DestroyableHBox> blueprints = new ArrayList<>(this.pathItem.getRecipes().entrySet().stream()
                .sorted(Comparator.comparing((Map.Entry o) -> o.getKey() instanceof HorizonsExperimentalEffectBlueprint)
                        .thenComparing((Map.Entry o) -> LocaleService.getLocalizedStringForCurrentLocale(((Blueprint) o.getKey()).getBlueprintName().getLocalizationKey())))
                .map(entry ->
                        {
                            final StringBinding stringBinding;
                            final Blueprint<T> blueprint = entry.getKey();
                            final Integer amount = (Expansion.HORIZONS.equals(this.expansion))
                                    ? horizonsWishlist.map(wl -> calculateAmount(wl, blueprint)).orElse(0)
                                    : odysseyWishlist.map(wl -> calculateAmount(wl, blueprint)).orElse(0);

                            //.orElse(Collections.emptyList());
//                            final Integer amount = entry.getValue();
                            if (Expansion.HORIZONS.equals(this.expansion)) {
                                if((HorizonsBlueprintConstants.getRecipe(((HorizonsBlueprint) blueprint).getBlueprintName(), ((HorizonsBlueprint) blueprint).getHorizonsBlueprintType(), ((HorizonsBlueprint) blueprint).getHorizonsBlueprintGrade())) instanceof HorizonsExperimentalEffectBlueprint){
                                    final String localeKey = "tab.wishlist.travel.path.column.blueprints.blueprint.horizons.experimental";
                                    stringBinding = LocaleService.getStringBinding(localeKey,
                                            LocaleService.LocalizationKey.of(blueprint.getBlueprintName().getLocalizationKey()),
                                            LocaleService.LocalizationKey.of(getBlueprintType((EngineeringBlueprint<T>) blueprint).getLocalizationKey()),
                                            amount);
                                }else{
                                    final String localeKey = "tab.wishlist.travel.path.column.blueprints.blueprint.horizons";
                                    stringBinding = LocaleService.getStringBinding(localeKey,
                                            LocaleService.LocalizationKey.of(blueprint.getBlueprintName().getLocalizationKey()),
                                            LocaleService.LocalizationKey.of(getBlueprintType((EngineeringBlueprint<T>) blueprint).getLocalizationKey()),
                                            amount,
                                            ((HorizonsBlueprint) blueprint).getHorizonsBlueprintGrade().getGrade());
                                }
                            } else {
                                stringBinding = LocaleService.getStringBinding("tab.wishlist.travel.path.column.blueprints.blueprint.odyssey", LocaleService.LocalizationKey.of(blueprint.getBlueprintName().getLocalizationKey()), amount);
                            }
                            EdAwesomeIconViewPane icon = EdAwesomeIconViewPaneBuilder.builder()
                                    .withStyleClass("type-icon")
                                    .withIcons(blueprint instanceof HorizonsExperimentalEffectBlueprint ? EdAwesomeIcon.SHIPS_EXPERIMENTAL_EFFECT : EdAwesomeIcon.SHIPS_ENGINEER)
                                    .build();
                            return BoxBuilder.builder()
                                    .withStyleClass("blueprints-line")
                                    .withNodes(icon,
                                            LabelBuilder.builder()
                                                    .withStyleClasses("blueprint")
                                                    .withText(stringBinding)
                                                    .build())
                                    .withOnMouseClicked(_ -> EventService.publish((Expansion.HORIZONS.equals(this.expansion)) ? new HorizonsBlueprintClickEvent(blueprint) : new BlueprintClickEvent(blueprint.getBlueprintName())))
                                    .buildHBox();
                        }
                ).toList());

        if (!Engineer.UNKNOWN.equals(this.pathItem.getEngineer())) {
            final DestroyableLabel indexLabel = LabelBuilder.builder()
                    .withStyleClass("index")
                    .withNonLocalizedText(String.valueOf(this.index))
                    .build();
            final DestroyableHBox titleBox = BoxBuilder.builder()
                    .withStyleClass("title-line")
                    .withNodes(engineer, new GrowingRegion(), indexLabel)
                    .buildHBox();
            this.getNodes().addAll(titleBox);

            if (!Engineer.REMOTE_WORKSHOP.equals(this.pathItem.getEngineer())) {
                final CopyableLocation copyableLocation = new CopyableLocation(this.pathItem.getEngineer().getStarSystem(), this.pathItem.getEngineer().getSettlement().getSettlementName());
                HBox.setHgrow(copyableLocation, Priority.NEVER);
                VBox.setVgrow(copyableLocation, Priority.NEVER);
                DestroyableLabel distanceLabel = LabelBuilder.builder()
                        .withStyleClass("distance-title")
                        .withText("tab.wishlist.travel.path.column.distance")
                        .build();
                DestroyableLabel distance = LabelBuilder.builder()
                        .withStyleClass("distance")
                        .withText(((this.index > 1) ? "tab.wishlist.distance.plus" : "tab.wishlist.distance"), Formatters.NUMBER_FORMAT_2.format(this.pathItem.getDistance()))
                        .build();
                final DestroyableHBox distanceBox = BoxBuilder.builder()
                        .withStyleClass("distance-line")
                        .withNodes(distanceLabel, distance)
                        .buildHBox();
                this.getNodes().addAll(copyableLocation, distanceBox);
            }
        } else {
            this.getNodes().add(engineer);
        }
        this.getNodes().add(blueprintsLabel);
        this.getNodes().addAll(blueprints);
        this.getNodes().add(new GrowingRegion());
        if (Expansion.HORIZONS.equals(this.expansion)) {
            addButtonsHorizons();
        } else {
            addButtonsOdyssey();
        }
    }

    private static <T extends BlueprintName<T>> Integer calculateAmount(HorizonsWishlist horizonsWishlist, Blueprint<T> blueprint) {
        return horizonsWishlist.getItems().stream()
                .filter(bp -> bp.getRecipeName().equals(blueprint.getBlueprintName())
                        && bp.getBlueprint() != null // can be null if all grades are 0%
                        && ((sameType((HorizonsBlueprint) blueprint, bp) || sameEffect((HorizonsBlueprint) blueprint, bp)) && sameMaxGrade((HorizonsBlueprint) blueprint, bp))
                )
                .mapToInt(WishlistBlueprint::getQuantity)
                .sum();
    }

    private static <T extends BlueprintName<T>> boolean sameMaxGrade(HorizonsBlueprint blueprint, WishlistBlueprint<HorizonsBlueprintName> bp) {
        return bp instanceof HorizonsModuleWishlistBlueprint hmwb && hmwb.getMaxSelectedGrade().equals(blueprint.getHorizonsBlueprintGrade());
    }

    private static <T extends BlueprintName<T>> boolean sameEffect(HorizonsBlueprint blueprint, WishlistBlueprint<HorizonsBlueprintName> bp) {
        return bp instanceof HorizonsModuleWishlistBlueprint hmwb && hmwb.getExperimentalEffect() != null && hmwb.getExperimentalEffect().equals(blueprint.getHorizonsBlueprintType());
    }

    private static <T extends BlueprintName<T>> boolean sameType(HorizonsBlueprint blueprint, WishlistBlueprint<HorizonsBlueprintName> bp) {
        return ((HorizonsBlueprint) bp.getBlueprint()).getHorizonsBlueprintType().equals(blueprint.getHorizonsBlueprintType());
    }

    private static <T extends BlueprintName<T>> Integer calculateAmount(Wishlist odysseyWishlist, Blueprint<T> blueprint) {
        return odysseyWishlist.getItems().stream()
                .filter(bp -> bp.getRecipeName().equals(blueprint.getBlueprintName()))
                .mapToInt(WishlistBlueprint::getQuantity)
                .sum();
    }

    private void addButtonsHorizons() {
        if (!ApplicationState.getInstance().getPreferredCommander()
                .map(commander -> HorizonsWishlist.ALL.getUuid().equals(WishlistService.getHorizonsWishlists(commander).getSelectedWishlist().getUuid()))
                .orElse(false)) {
            DestroyableButton removeButton = ButtonBuilder.builder()
                    .withText(LocaleService.getStringBinding("tab.wishlist.travel.path.column.actions.remove"))
                    .withOnAction(_ -> {
                        removeBlueprints();
                        EventService.publish(new HorizonsRemoveWishlistShortestPathItemEvent((PathItem<HorizonsBlueprintName>) this.pathItem));
                    })
                    .build();
            DestroyableButton hideButton = ButtonBuilder.builder()
                    .withText(LocaleService.getStringBinding("tab.wishlist.travel.path.column.actions.hide"))
                    .withOnAction(_ -> {
                        hideBlueprints();
                        EventService.publish(new HorizonsHideWishlistShortestPathItemEvent((PathItem<HorizonsBlueprintName>) this.pathItem));
                    })
                    .build();
            this.getNodes().addAll(BoxBuilder.builder()
                    .withStyleClass("buttons")
                    .withNodes(hideButton, new GrowingRegion(), removeButton).buildHBox());
        }
    }

    private void addButtonsOdyssey() {
        if (!ApplicationState.getInstance().getPreferredCommander()
                .map(commander -> Wishlist.ALL.getUuid().equals(WishlistService.getOdysseyWishlists(commander).getSelectedWishlist().getUuid()))
                .orElse(false)) {
            DestroyableButton removeButton = ButtonBuilder.builder()
                    .withText(LocaleService.getStringBinding("tab.wishlist.travel.path.column.actions.remove"))
                    .withOnAction(_ -> {
                        removeBlueprints();
                        EventService.publish(new RemoveWishlistShortestPathItemEvent((PathItem<OdysseyBlueprintName>) this.pathItem));
                    })
                    .build();
            DestroyableButton hideButton = ButtonBuilder.builder()
                    .withText(LocaleService.getStringBinding("tab.wishlist.travel.path.column.actions.hide"))
                    .withOnAction(_ -> {
                        hideBlueprints();
                        EventService.publish(new HideWishlistShortestPathItemEvent((PathItem<OdysseyBlueprintName>) this.pathItem));
                    })
                    .build();
            this.getNodes().addAll(BoxBuilder.builder()
                    .withStyleClass("buttons")
                    .withNodes(hideButton, new GrowingRegion(), removeButton).buildHBox());
        }
    }

    private void hideBlueprints() {
        if (Expansion.HORIZONS.equals(this.expansion)) {
            ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
                final HorizonsWishlists horizonsWishlists = WishlistService.getHorizonsWishlists(commander);
                final HorizonsWishlist selectedWishlist = horizonsWishlists.getSelectedWishlist();
                selectedWishlist.getItems().stream().filter(bp -> this.pathItem.getRecipes().containsKey(bp.getBlueprint())).forEach(bp -> {
                    bp.setVisible(false);
                });
                WishlistService.saveHorizonsWishlists(commander, horizonsWishlists);
            });
        } else {
            ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
                final Wishlists odysseyWishlists = WishlistService.getOdysseyWishlists(commander);
                final Wishlist selectedWishlist = odysseyWishlists.getSelectedWishlist();
                selectedWishlist.getItems().stream().filter(bp -> this.pathItem.getRecipes().containsKey(bp.getBlueprint())).forEach(bp -> {
                    bp.setVisible(false);
                });
                WishlistService.saveOdysseyWishlists(commander, odysseyWishlists);
            });
        }
    }

    private void removeBlueprints() {
        if (Expansion.HORIZONS.equals(this.expansion)) {
            ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
                final HorizonsWishlists horizonsWishlists = WishlistService.getHorizonsWishlists(commander);
                final HorizonsWishlist selectedWishlist = horizonsWishlists.getSelectedWishlist();
                if (this.pathItem.getEngineer() == Engineer.REMOTE_WORKSHOP) {
                    selectedWishlist.getItems().stream()
                            .filter(bp -> !((HorizonsWishlistBlueprint) bp).getUuid().equals("-1"))
                            .filter(bp -> this.pathItem.getRecipes().containsKey(bp.getBlueprint()))
                            .filter(bp -> bp instanceof HorizonsModuleWishlistBlueprint)
                            .map(HorizonsModuleWishlistBlueprint.class::cast)
                            .filter(bp -> bp.getExperimentalEffect() != null)
                            .forEach(bp -> splitBlueprint(commander, selectedWishlist, bp));
                }
                selectedWishlist.getItems().removeIf(bp -> !((HorizonsWishlistBlueprint) bp).getUuid().equals("-1") && this.pathItem.getRecipes().containsKey(bp.getBlueprint()));
                WishlistService.saveHorizonsWishlists(commander, horizonsWishlists);
            });
        } else {
            ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
                final Wishlists odysseyWishlists = WishlistService.getOdysseyWishlists(commander);
                final Wishlist selectedWishlist = odysseyWishlists.getSelectedWishlist();
                selectedWishlist.getItems().removeIf(bp -> this.pathItem.getRecipes().containsKey(bp.getBlueprint()));
                WishlistService.saveOdysseyWishlists(commander, odysseyWishlists);
            });
        }
    }

    private void splitBlueprint(Commander commander, HorizonsWishlist wishlist, HorizonsModuleWishlistBlueprint blueprint) {
        var bp = new HorizonsExperimentalWishlistBlueprint(blueprint.getExperimentalEffect());
        bp.setRecipeName((blueprint.getRecipeName()));
        bp.setQuantity(blueprint.getQuantity());
        EventService.publish(new HorizonsWishlistBlueprintEvent(commander, wishlist.getUuid(), List.of(bp), Action.ADDED));
    }

    private HorizonsBlueprintType getBlueprintType(final EngineeringBlueprint<T> blueprint) {
        if (blueprint instanceof HorizonsEngineeringBlueprint horizonsModuleWishlistBlueprint) {
            return horizonsModuleWishlistBlueprint.getHorizonsBlueprintType();
        }
        return null;
    }
}
