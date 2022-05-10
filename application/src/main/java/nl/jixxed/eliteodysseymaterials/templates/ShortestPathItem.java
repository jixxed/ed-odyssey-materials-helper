package nl.jixxed.eliteodysseymaterials.templates;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class ShortestPathItem<T extends BlueprintName<T>> extends VBox implements Template {
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
        this.blueprints.addAll(this.pathItem.getBlueprints().stream().flatMap(temp -> temp.getRecipe().stream().map(bp -> (bp instanceof HorizonsBlueprint horizonsBlueprint)
                        ? HorizonsBlueprintConstants.getRecipe(horizonsBlueprint.getBlueprintName(), horizonsBlueprint.getHorizonsBlueprintType(), HorizonsBlueprintGrade.GRADE_1)
                        : bp).distinct())
                .collect(Collectors.groupingBy(
                        recipe -> recipe,
                        Collectors.summingInt(value -> 1))
                ).entrySet().stream()
                .map(entry ->
                        LabelBuilder.builder()
                                .withStyleClasses("shortest-path-item-label-value", "shortest-path-item-label-blueprint")
                                .withOnMouseClicked(event -> EventService.publish((Expansion.HORIZONS.equals(this.expansion)) ? new HorizonsBlueprintClickEvent(entry.getKey()) : new BlueprintClickEvent(entry.getKey().getBlueprintName())))
                                .withText((Expansion.HORIZONS.equals(this.expansion)) ?
                                        LocaleService.getStringBinding("tab.wishlist.travel.path.column.blueprints.blueprint.horizons", LocaleService.LocalizationKey.of(entry.getKey().getBlueprintName().getLocalizationKey()), LocaleService.LocalizationKey.of(getBlueprintType((EngineeringBlueprint<T>) entry.getKey()).getLocalizationKey()), entry.getValue()) :
                                        LocaleService.getStringBinding("tab.wishlist.travel.path.column.blueprints.blueprint.odyssey", LocaleService.LocalizationKey.of(entry.getKey().getBlueprintName().getLocalizationKey()), entry.getValue()))
                                .build()
                ).toList()
        );

        this.removeButton = ButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.wishlist.travel.path.column.actions.remove")).build();
        this.hideButton = ButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.wishlist.travel.path.column.actions.hide")).build();

        this.removeButton.setOnAction((ActionEvent event) -> EventService.publish((Expansion.HORIZONS.equals(this.expansion)) ? new HorizonsRemoveWishlistShortestPathItemEvent((PathItem<HorizonsBlueprintName>) this.pathItem) : new RemoveWishlistShortestPathItemEvent((PathItem<OdysseyBlueprintName>) this.pathItem)));
        this.hideButton.setOnAction((ActionEvent event) -> EventService.publish((Expansion.HORIZONS.equals(this.expansion)) ? new HorizonsHideWishlistShortestPathItemEvent((PathItem<HorizonsBlueprintName>) this.pathItem) : new HideWishlistShortestPathItemEvent((PathItem<OdysseyBlueprintName>) this.pathItem)));

        this.getStyleClass().add("shortest-path-item");
        if (!Engineer.UNKNOWN.equals(this.pathItem.getEngineer())) {
            this.getChildren().addAll(BoxBuilder.builder().withNodes(this.engineer, new GrowingRegion(), LabelBuilder.builder().withStyleClass("shortest-path-item-label-big").withNonLocalizedText(String.valueOf(this.index)).build()).buildHBox());
            addLocation(this.pathItem.getEngineer());
            this.getChildren().addAll(BoxBuilder.builder().withNodes(this.distanceLabel, this.distance).buildHBox());
            this.getChildren().addAll(new Label());
        } else {
            this.getChildren().addAll(this.engineer);
        }
        this.getChildren().addAll(this.blueprintsLabel);
        this.getChildren().addAll(this.blueprints);
        this.getChildren().addAll(new GrowingRegion("shortest-path-item-spacer"));
        this.getChildren().addAll(BoxBuilder.builder().withStyleClass("shortest-path-item-button").withNodes(this.hideButton, new GrowingRegion(), this.removeButton).buildHBox());
    }

    private <T extends BlueprintName<T>> Comparator<? super Map.Entry<Blueprint<T>, Integer>> getGradeSorter() {

        return Comparator.comparing((Map.Entry<Blueprint<T>, Integer> blueprintIntegerEntry) -> {
            if (blueprintIntegerEntry.getKey() instanceof HorizonsModuleBlueprint moduleBlueprint) {
                return moduleBlueprint.getHorizonsBlueprintGrade().getGrade();
            }
            return 0;
        }).reversed();
    }

//    private static <T extends BlueprintName<T>> Predicate<Map.Entry<Blueprint<T>, Integer>> distinctByKey(final Function<Map.Entry<Blueprint<T>, Integer>, Object> keyExtractor) {
//        final Set<Object> seen = ConcurrentHashMap.newKeySet();
//        return t -> seen.add(keyExtractor.apply(t));
//    }
//
//    private static <T extends BlueprintName<T>> Function<Map.Entry<Blueprint<T>, Integer>, Object> getKey(final Blueprint<T> blueprint) {
//        return (bp) -> {
//            if (bp.getKey() instanceof HorizonsExperimentalEffectBlueprint experimentalEffectBlueprint) {
//                return experimentalEffectBlueprint.getBlueprintName().name() + experimentalEffectBlueprint.getHorizonsBlueprintType().name();
//            } else if (bp.getKey() instanceof HorizonsModuleBlueprint moduleBlueprint) {
//                return moduleBlueprint.getBlueprintName().name() + moduleBlueprint.getHorizonsBlueprintType().name();
//            }
//            return bp.getKey().getBlueprintName();
//        };
//    }

    private HorizonsBlueprintType getBlueprintType(final EngineeringBlueprint<T> blueprint) {
        if (blueprint instanceof HorizonsEngineeringBlueprint horizonsModuleWishlistBlueprint) {
            return horizonsModuleWishlistBlueprint.getHorizonsBlueprintType();
        }
        return null;
    }


    private static final String STYLECLASS_MATERIAL_TOOLTIP_LOCATION_LINE = "material-tooltip-location-line";

    private void addLocation(final Engineer engineer) {
        if (!Engineer.REMOTE_WORKSHOP.equals(engineer)) {
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
