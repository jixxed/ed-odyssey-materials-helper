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
import nl.jixxed.eliteodysseymaterials.domain.PathItem;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.event.BlueprintClickEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HideWishlistShortestPathItemEvent;
import nl.jixxed.eliteodysseymaterials.service.event.RemoveWishlistShortestPathItemEvent;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

class ShortestPathItem extends VBox implements Template {
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();
    private static final String SHORTEST_PATH_ITEM_LABEL_STYLE_CLASS = "shortest-path-item-label";
    private final PathItem pathItem;
    private final int index;
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

    ShortestPathItem(final PathItem pathItem, final int index) {
        this.pathItem = pathItem;
        this.index = index;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.distanceLabel = LabelBuilder.builder().withStyleClass(SHORTEST_PATH_ITEM_LABEL_STYLE_CLASS).withText(LocaleService.getStringBinding("tab.wishlist.travel.path.column.distance")).build();
        this.blueprintsLabel = LabelBuilder.builder().withStyleClass(SHORTEST_PATH_ITEM_LABEL_STYLE_CLASS).withText(LocaleService.getStringBinding("tab.wishlist.travel.path.column.blueprints")).build();
        this.distance = LabelBuilder.builder().withNonLocalizedText(" " + ((this.index > 1) ? "+" : "") + NUMBER_FORMAT.format(this.pathItem.getDistance()) + "Ly").build();
        this.engineer = LabelBuilder.builder().withStyleClass("shortest-path-item-label-big").withText(LocaleService.getStringBinding(() -> " " + LocaleService.getLocalizedStringForCurrentLocale(this.pathItem.getEngineer().getLocalizationKey()))).build();
        this.blueprints.addAll(this.pathItem.getRecipes().entrySet().stream().map(entry ->
                LabelBuilder.builder().withStyleClasses("shortest-path-item-label-value", "shortest-path-item-label-blueprint").withOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(entry.getKey().getBlueprintName()))).withText(LocaleService.getStringBinding(() -> "\u2022 " + LocaleService.getLocalizedStringForCurrentLocale(entry.getKey().getBlueprintName().getLocalizationKey()) + ((entry.getValue() > 1) ? " (" + entry.getValue() + ")" : ""))).build()
        ).toList());

        this.removeButton = ButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.wishlist.travel.path.column.actions.remove")).build();
        this.hideButton = ButtonBuilder.builder().withText(LocaleService.getStringBinding("tab.wishlist.travel.path.column.actions.hide")).build();

        this.removeButton.setOnAction((ActionEvent event) -> EventService.publish(new RemoveWishlistShortestPathItemEvent(this.pathItem)));
        this.hideButton.setOnAction((ActionEvent event) -> EventService.publish(new HideWishlistShortestPathItemEvent(this.pathItem)));

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
        this.getChildren().addAll(BoxBuilder.builder().withStyleClass("shortest-path-item-button").withNodes(new GrowingRegion(), this.hideButton, this.removeButton).buildHBox());
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
