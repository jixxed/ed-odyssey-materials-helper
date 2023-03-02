package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsTab;

public class HorizonsShipBuilderTab extends HorizonsTab {
@Getter
    private final Ship ship = new Ship(Ship.ADDER);
    private ScrollPane scrollPane;
    private VBox hardpointsVbox;
    private VBox coreVbox;
    private VBox optionalVBox;
    private VBox utilityVbox;
    private HBox items;
    private StackPane stackPane;
    private Circle circleStart;
    private Circle circleEnd;
    private Line line;
    private Line line2;
    private Group overlayLine;
    private Pane pane;

    @Override
    public HorizonsTabs getTabType() {
        return HorizonsTabs.SHIPBUILDER;
    }

    public HorizonsShipBuilderTab() {
        initComponents();
        initEventHandling();

    }

    private void initComponents() {
        this.textProperty().bind(LocaleService.getStringBinding("tabs.ships"));
        this.hardpointsVbox = BoxBuilder.builder().withStyleClass("shipbuilder-slots-vbox").withNodes(this.ship.getHardpointSlots().stream().map(slot -> new SlotBox(this, slot)).toArray(SlotBox[]::new)).buildVBox();
        this.coreVbox = BoxBuilder.builder().withStyleClass("shipbuilder-slots-vbox").withNodes(this.ship.getCoreSlots().stream().map(slot -> new SlotBox(this, slot)).toArray(SlotBox[]::new)).buildVBox();
        this.optionalVBox = BoxBuilder.builder().withStyleClass("shipbuilder-slots-vbox").withNodes(this.ship.getOptionalSlots().stream().map(slot -> new SlotBox(this, slot)).toArray(SlotBox[]::new)).buildVBox();
        this.utilityVbox = BoxBuilder.builder().withStyleClass("shipbuilder-slots-vbox").withNodes(this.ship.getUtilitySlots().stream().map(slot -> new SlotBox(this, slot)).toArray(SlotBox[]::new)).buildVBox();

        this.items = BoxBuilder.builder().withNodes(this.hardpointsVbox, this.coreVbox, this.optionalVBox, this.utilityVbox).withStyleClass("shipbuilder-slots-hbox").buildHBox();
        final DestroyableResizableImageView image = ResizableImageViewBuilder.builder().withStyleClass("shipbuilder-ship-image").withImage("/images/ships/ship/grid.png").build();
        final DestroyableResizableImageView image2 = ResizableImageViewBuilder.builder().withStyleClass("shipbuilder-ship-image").withImage("/images/ships/ship/concept.png").build();
        this.stackPane = new StackPane(image,image2);
        this.stackPane.getStyleClass().add("shipbuilder-stackpane");
        final VBox content = BoxBuilder.builder().withNode(this.items).buildVBox();
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(content)
                .build();
        this.setContent(this.scrollPane);

        //Drawing a Circle
        this.circleStart = new Circle();
        this.circleEnd = new Circle();
        this.line = new Line();
        this.line2 = new Line();
        this.pane = new Pane(this.circleStart, this.line, this.circleEnd, this.line2);
        this.pane.getStyleClass().add("shipbuilder-overlay");
        this.overlayLine = new Group(this.pane);
//        this.overlayLine.getStyleClass().add("shipbuilder-overlay");
        this.stackPane.getChildren().add(this.overlayLine);
        StackPane.setAlignment(this.overlayLine, Pos.TOP_LEFT);
        StackPane.setAlignment(image, Pos.TOP_LEFT);
    }

    private void initEventHandling() {

    }

    void toggleHardpointImage(final boolean imageVisible) {
        if (imageVisible) {
            if (this.items.getChildren().contains(this.coreVbox)) {
                this.items.getChildren().removeAll(this.coreVbox, this.optionalVBox, this.utilityVbox);
                this.items.getChildren().add(this.stackPane);
            }
        } else {
            if (this.items.getChildren().contains(this.stackPane)) {
                this.items.getChildren().remove(this.stackPane);
                this.items.getChildren().addAll(this.coreVbox, this.optionalVBox, this.utilityVbox);
            }
        }
    }

    void toggleUtilityImage(final boolean imageVisible) {
        if (imageVisible) {
            if (this.items.getChildren().contains(this.coreVbox)) {
                this.items.getChildren().removeAll(this.coreVbox, this.optionalVBox, this.hardpointsVbox);
                this.items.getChildren().add(0, this.stackPane);
            }
        } else {
            if (this.items.getChildren().contains(this.stackPane)) {
                this.items.getChildren().remove(this.stackPane);
                this.items.getChildren().add(0, this.hardpointsVbox);
                this.items.getChildren().add(1, this.coreVbox);
                this.items.getChildren().add(2, this.optionalVBox);
            }
        }
    }

    void drawHardpointLine(final int index) {
        final double slotHeight = 5D;
        //Setting the properties of the circle
        this.circleStart.setCenterX(0);
        this.circleStart.setCenterY(ScalingHelper.getPixelDoubleFromEm(slotHeight/2) + ScalingHelper.getPixelDoubleFromEm(slotHeight) * index);
        this.circleStart.setRadius(ScalingHelper.getPixelDoubleFromEm(0.2D));
        this.circleStart.setStroke(Color.valueOf("#89D07F"));
        this.circleStart.setFill(Color.valueOf("#89D07F"));

        this.line.setStartX(this.circleStart.getCenterX());
        this.line.setStartY(this.circleStart.getCenterY());
        this.line.setEndX(this.circleStart.getCenterX() + ScalingHelper.getPixelDoubleFromEm(2D));
        this.line.setEndY(this.circleStart.getCenterY());
        this.line.setStrokeWidth(ScalingHelper.getPixelDoubleFromEm(0.1D));
        this.line.setStroke(Color.valueOf("#89D07F"));

        this.circleEnd.setCenterX(ScalingHelper.getPixelDoubleFromEm(600D/14D));
        this.circleEnd.setCenterY(ScalingHelper.getPixelDoubleFromEm(200D/14D));
        this.circleEnd.setRadius(ScalingHelper.getPixelDoubleFromEm(0.4D));
        this.circleEnd.setStroke(Color.valueOf("#89D07F"));
        this.circleEnd.setFill(Color.valueOf("#89D07F"));

        this.line2.setStartX(this.line.getEndX() + ScalingHelper.getPixelDoubleFromEm(0.1D));
        this.line2.setStartY(this.line.getEndY());
        this.line2.setEndX(this.circleEnd.getCenterX());
        this.line2.setEndY(this.circleEnd.getCenterY());
        this.line2.setStrokeWidth(ScalingHelper.getPixelDoubleFromEm(0.1D));
        this.line2.setStroke(Color.valueOf("#89D07F"));


        //Creating a Group object
    }

    void drawUtilityLine(final int index) {
        final double slotHeight = 5D;
        final double imageWidth = 81D;
        //Setting the properties of the circle
        this.circleStart.setCenterX(ScalingHelper.getPixelDoubleFromEm(imageWidth) - ScalingHelper.getPixelDoubleFromEm(0.2D) * 1.5);
        this.circleStart.setCenterY(ScalingHelper.getPixelDoubleFromEm(slotHeight/2) + ScalingHelper.getPixelDoubleFromEm(slotHeight) * index);
        this.circleStart.setRadius(ScalingHelper.getPixelDoubleFromEm(0.2D));
        this.circleStart.setStroke(Color.valueOf("#89D07F"));
        this.circleStart.setFill(Color.valueOf("#89D07F"));

        this.line.setStartX(this.circleStart.getCenterX());
        this.line.setStartY(this.circleStart.getCenterY());
        this.line.setEndX(this.circleStart.getCenterX() - ScalingHelper.getPixelDoubleFromEm(2D));
        this.line.setEndY(this.circleStart.getCenterY());
        this.line.setStrokeWidth(ScalingHelper.getPixelDoubleFromEm(0.1D));
        this.line.setStroke(Color.valueOf("#89D07F"));

        this.circleEnd.setCenterX(ScalingHelper.getPixelDoubleFromEm(600D/14D));
        this.circleEnd.setCenterY(ScalingHelper.getPixelDoubleFromEm(200D/14D));
        this.circleEnd.setRadius(ScalingHelper.getPixelDoubleFromEm(0.4D));
        this.circleEnd.setStroke(Color.valueOf("#89D07F"));
        this.circleEnd.setFill(Color.valueOf("#89D07F"));

        this.line2.setStartX(this.line.getEndX() - ScalingHelper.getPixelDoubleFromEm(0.1D));
        this.line2.setStartY(this.line.getEndY());
        this.line2.setEndX(this.circleEnd.getCenterX());
        this.line2.setEndY(this.circleEnd.getCenterY());
        this.line2.setStrokeWidth(ScalingHelper.getPixelDoubleFromEm(0.1D));
        this.line2.setStroke(Color.valueOf("#89D07F"));


        //Creating a Group object
    }
}
