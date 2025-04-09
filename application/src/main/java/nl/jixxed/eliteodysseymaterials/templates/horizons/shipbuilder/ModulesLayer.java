package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.ImageSlot;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.event.AfterFontSizeSetEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsShipSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipMapper;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

public class ModulesLayer extends DestroyableAnchorPane implements DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    private DestroyableScrollPane scrollPane;
    private DestroyableVBox hardpointsVbox;
    private DestroyableVBox coreVbox;
    private DestroyableVBox optionalVBox;
    private DestroyableVBox utilityVbox;
    private DestroyableHBox slotColumns;
    private DestroyableCircle circleStart;
    private DestroyableCircle circleEnd;
    private DestroyableLine line;
    private DestroyableLine line2;
    private DestroyableGroup overlayLine;
    private DestroyablePane pane;
    private DestroyableResizableImageView shipImage;
    private DestroyableStackPane imageLayer;
    private HorizonsShipBuilderTab tab;
    private DestroyableResizableImageView gridImage;
    private int lastIndex;

    public ModulesLayer(HorizonsShipBuilderTab tab) {
        this.tab = tab;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("shipbuilder-modules-layer");
        initShipLayout();

    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, 0, HorizonsShipSelectedEvent.class, horizonsShipSelectedEvent -> {
            APPLICATION_STATE.getPreferredCommander()
                    .flatMap(commander -> ShipService.getShipConfigurations(commander).getSelectedShipConfiguration())
                    .ifPresent(configuration -> APPLICATION_STATE.setShip(ShipMapper.toShip(configuration)));
            initShipSlots();
        }));

        register(EventService.addListener(true, this, 9, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            if (this.imageLayer.isVisible()) {
                if (this.hardpointsVbox.isVisible()) {
                    drawHardpointLine(lastIndex);
                } else {
                    drawUtilityLine(lastIndex);
                }
            }
        }));
    }

    public void initShipLayout() {
        this.hardpointsVbox = BoxBuilder.builder()
                .withStyleClass("shipbuilder-slots-vbox")
                .buildVBox();
        this.coreVbox = BoxBuilder.builder()
                .withStyleClass("shipbuilder-slots-vbox")
                .buildVBox();
        this.optionalVBox = BoxBuilder.builder()
                .withStyleClass("shipbuilder-slots-vbox")
                .buildVBox();
        this.utilityVbox = BoxBuilder.builder()
                .withStyleClass("shipbuilder-slots-vbox")
                .buildVBox();
//        this.hardpointsVbox = BoxBuilder.builder().withStyleClass("shipbuilder-slots-vbox").withNodes(this.ship.getHardpointSlots().stream().map(slot -> new SlotBox(this, slot)).toArray(SlotBox[]::new)).buildVBox();
//        this.coreVbox = BoxBuilder.builder().withStyleClass("shipbuilder-slots-vbox").withNodes(this.ship.getCoreSlots().stream().map(slot -> new SlotBox(this, slot)).toArray(SlotBox[]::new)).buildVBox();
//        this.optionalVBox = BoxBuilder.builder().withStyleClass("shipbuilder-slots-vbox").withNodes(this.ship.getOptionalSlots().stream().map(slot -> new SlotBox(this, slot)).toArray(SlotBox[]::new)).buildVBox();
//        this.utilityVbox = BoxBuilder.builder().withStyleClass("shipbuilder-slots-vbox").withNodes(this.ship.getUtilitySlots().stream().map(slot -> new SlotBox(this, slot)).toArray(SlotBox[]::new)).buildVBox();
        initShipSlots();
        final DestroyableRegion regionRight = new DestroyableRegion();
        regionRight.addBinding(regionRight.minWidthProperty(), this.tab.getDetailsLayer().getModuleDetails().widthProperty());
        regionRight.addBinding(regionRight.maxWidthProperty(), this.tab.getDetailsLayer().getModuleDetails().widthProperty());
        this.slotColumns = BoxBuilder.builder()
                .withNodes(this.hardpointsVbox, this.coreVbox, this.optionalVBox, this.utilityVbox, regionRight)
                .buildHBox();
        gridImage = ResizableImageViewBuilder.builder()
                .withStyleClass("shipbuilder-ship-image")
                .withImage("/images/ships/ship/grid.png")
                .build();
        shipImage = ResizableImageViewBuilder.builder()
                .withStyleClass("shipbuilder-ship-image")
                .withImage("/images/ships/ship/grid.png")
                .build();
        this.imageLayer = StackPaneBuilder.builder()
                .withNodes(gridImage, shipImage)
                .build();
        this.imageLayer.getStyleClass().add("shipbuilder-stackpane");
        this.imageLayer.setVisible(false);
        this.imageLayer.setPickOnBounds(false);
        final DestroyableRegion region = new DestroyableRegion();
        region.addBinding(region.minHeightProperty(), this.tab.getStatsLayer().getStats().heightProperty());
        region.addBinding(region.maxHeightProperty(), this.tab.getStatsLayer().getStats().heightProperty());
        final DestroyableVBox modulesLayer = BoxBuilder.builder()
                .withNodes(this.slotColumns, region)
                .withStyleClass("shipbuilder-slots-hbox").buildVBox();
        final DestroyableAnchorPane anchorPane = AnchorPaneBuilder.builder()
                .withNodes(this.imageLayer)
                .build();
        anchorPane.setPickOnBounds(false);
        StackPane.setAlignment(anchorPane, Pos.TOP_LEFT);
//        anchorPane.addBinding(//        anchorPane.minWidthProperty(),this.slotColumns.widthProperty());
//        anchorPane.addBinding(//        anchorPane.maxWidthProperty(),this.slotColumns.widthProperty());
        anchorPane.getStyleClass().add("shipbuilder-stackpane-anchor");
        final DestroyableStackPane content = StackPaneBuilder.builder()
                .withNodes(modulesLayer, anchorPane)
                .build();
        content.getStyleClass().add("shipbuilder-slots-content");
        AnchorPane.setTopAnchor(this.imageLayer, 0D);
        AnchorPane.setRightAnchor(this.imageLayer, 0D);
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(content)
                .withStyleClass("shipbuilder-modules-scrollpane")
                .build();
        //Drawing a Circle
        this.circleStart = new DestroyableCircle();
        this.circleEnd = new DestroyableCircle();
        this.line = new DestroyableLine();
        this.line2 = new DestroyableLine();
        this.pane = PaneBuilder.builder()
                .withNodes(this.circleStart, this.line, this.circleEnd, this.line2)
                .build();
        this.pane.getStyleClass().add("shipbuilder-overlay");
        this.overlayLine = new DestroyableGroup(this.pane);
//        this.overlayLine.getStyleClass().add("shipbuilder-overlay");
        this.imageLayer.getNodes().add(this.overlayLine);
//        StackPane.setAlignment(this.overlayLine, Pos.TOP_LEFT);
//        StackPane.setAlignment(gridImage, Pos.TOP_LEFT);
//        StackPane.setAlignment(shipImage, Pos.TOP_LEFT);

        this.getNodes().add(this.scrollPane);
        AnchorPane.setTopAnchor(this.scrollPane, -1D);
        AnchorPane.setRightAnchor(this.scrollPane, 0D);
        AnchorPane.setBottomAnchor(this.scrollPane, -1D);
        AnchorPane.setLeftAnchor(this.scrollPane, 0D);
    }

    public void initShipSlots() {
        this.hardpointsVbox.getNodes().clear();
        this.coreVbox.getNodes().clear();
        this.optionalVBox.getNodes().clear();
        this.utilityVbox.getNodes().clear();
        if (APPLICATION_STATE.getShip() != null) {
            this.hardpointsVbox.getNodes().addAll(APPLICATION_STATE.getShip().getHardpointSlots().stream().map(slot -> new SlotBox(this, slot)).toArray(SlotBox[]::new));
            this.coreVbox.getNodes().addAll(APPLICATION_STATE.getShip().getCoreSlots().stream().map(slot -> new SlotBox(this, slot)).toArray(SlotBox[]::new));
            this.optionalVBox.getNodes().addAll(APPLICATION_STATE.getShip().getOptionalSlots().stream().map(slot -> new SlotBox(this, slot)).toArray(SlotBox[]::new));
            this.utilityVbox.getNodes().addAll(APPLICATION_STATE.getShip().getUtilitySlots().stream().map(slot -> new SlotBox(this, slot)).toArray(SlotBox[]::new));
        }
    }

    void toggleHardpointImage(final boolean imageVisible) {
//        this.imageLayer.setStyle("-fx-padding: 3.25em 0em 0em 27em");
        AnchorPane.clearConstraints(this.imageLayer);
        AnchorPane.setTopAnchor(this.imageLayer, 0D);
        AnchorPane.setRightAnchor(this.imageLayer, 0D);
        StackPane.setAlignment(this.overlayLine, Pos.TOP_RIGHT);
        StackPane.setAlignment(gridImage, Pos.TOP_RIGHT);
        StackPane.setAlignment(shipImage, Pos.TOP_RIGHT);
        this.imageLayer.setVisible(imageVisible);
        this.hardpointsVbox.setVisible(true);
        this.coreVbox.setVisible(!imageVisible);
        this.optionalVBox.setVisible(!imageVisible);
        this.utilityVbox.setVisible(!imageVisible);
//        if (imageVisible) {
//            if (this.slotColumns.getNodes().contains(this.coreVbox)) {
//                this.slotColumns.getNodes().removeAll(this.coreVbox, this.optionalVBox, this.utilityVbox);
//                this.slotColumns.getNodes().add(this.imageLayer);
//            }
//        } else {
//            if (this.slotColumns.getNodes().contains(this.imageLayer)) {
//                this.slotColumns.getNodes().remove(this.imageLayer);
//                this.slotColumns.getNodes().addAll(this.coreVbox, this.optionalVBox, this.utilityVbox);
//            }
//        }
    }

    void toggleUtilityImage(final boolean imageVisible) {
//        this.imageLayer.setStyle("-fx-padding: 3.25em 0em 0em 0em");
        AnchorPane.clearConstraints(this.imageLayer);
        AnchorPane.setTopAnchor(this.imageLayer, 0D);
        AnchorPane.setLeftAnchor(this.imageLayer, 0D);
        StackPane.setAlignment(this.overlayLine, Pos.TOP_LEFT);
        StackPane.setAlignment(gridImage, Pos.TOP_LEFT);
        StackPane.setAlignment(shipImage, Pos.TOP_LEFT);
        this.imageLayer.setVisible(imageVisible);
        this.hardpointsVbox.setVisible(!imageVisible);
        this.coreVbox.setVisible(!imageVisible);
        this.optionalVBox.setVisible(!imageVisible);
        this.utilityVbox.setVisible(true);
//        if (imageVisible) {
//            if (this.slotColumns.getNodes().contains(this.coreVbox)) {
//                this.slotColumns.getNodes().removeAll(this.coreVbox, this.optionalVBox, this.hardpointsVbox);
//                this.slotColumns.getNodes().add(0, this.imageLayer);
//            }
//        } else {
//            if (this.slotColumns.getNodes().contains(this.imageLayer)) {
//                this.slotColumns.getNodes().remove(this.imageLayer);
//                this.slotColumns.getNodes().add(0, this.hardpointsVbox);
//                this.slotColumns.getNodes().add(1, this.coreVbox);
//                this.slotColumns.getNodes().add(2, this.optionalVBox);
//            }
//        }
    }

    void drawHardpointLine(final int index) {
        lastIndex = index;
        final ImageSlot imageSlot = APPLICATION_STATE.getShip().getHardpointSlots().get(index);
        shipImage.setImage(ImageService.getImage("/images/ships/ship/" + APPLICATION_STATE.getShip().getShipType().name().toLowerCase() + "." + imageSlot.getImageIndex() + ".png"));
        final double x = this.shipImage.getWidth() / 1920D * imageSlot.getX();
        final double y = this.shipImage.getHeight() / 1090D * imageSlot.getY();
        Platform.runLater(() -> {
            final double slotHeight = 5.0D;
            final double spacing = 0.0D;
            //Setting the properties of the circle
            this.circleStart.setCenterX(0 + ScalingHelper.getPixelDoubleFromEm(0.2D) * 1.5);
            this.circleStart.setCenterY(ScalingHelper.getPixelDoubleFromEm(slotHeight / 2) + ScalingHelper.getPixelDoubleFromEm(slotHeight + spacing) * index);
            this.circleStart.setRadius(ScalingHelper.getPixelDoubleFromEm(0.2D));
            this.circleStart.setStroke(Color.valueOf("#89D07F"));
            this.circleStart.setFill(Color.valueOf("#89D07F"));

            this.line.setStartX(this.circleStart.getCenterX());
            this.line.setStartY(this.circleStart.getCenterY());
            this.line.setEndX(this.circleStart.getCenterX() + ScalingHelper.getPixelDoubleFromEm(2D));
            this.line.setEndY(this.circleStart.getCenterY());
            this.line.setStrokeWidth(ScalingHelper.getPixelDoubleFromEm(0.1D));
            this.line.setStroke(Color.valueOf("#89D07F"));

            //        this.circleEnd.setCenterX(ScalingHelper.getPixelDoubleFromEm(600D/14D));
            //        this.circleEnd.setCenterY(ScalingHelper.getPixelDoubleFromEm(200D/14D));
            this.circleEnd.setCenterX(x);
            this.circleEnd.setCenterY(y);
            this.circleEnd.setRadius(ScalingHelper.getPixelDoubleFromEm(0.4D));
            this.circleEnd.setStroke(Color.valueOf("#89D07F"));
            this.circleEnd.setFill(Color.valueOf("#89D07F"));

            this.line2.setStartX(this.line.getEndX() + ScalingHelper.getPixelDoubleFromEm(0.1D));
            this.line2.setStartY(this.line.getEndY());
            this.line2.setEndX(this.circleEnd.getCenterX());
            this.line2.setEndY(this.circleEnd.getCenterY());
            this.line2.setStrokeWidth(ScalingHelper.getPixelDoubleFromEm(0.1D));
            this.line2.setStroke(Color.valueOf("#89D07F"));
        });
    }

    void drawUtilityLine(final int index) {
        lastIndex = index;
        final ImageSlot imageSlot = APPLICATION_STATE.getShip().getUtilitySlots().get(index);
        shipImage.setImage(ImageService.getImage("/images/ships/ship/" + APPLICATION_STATE.getShip().getShipType().name().toLowerCase() + "." + imageSlot.getImageIndex() + ".png"));
        final double x = this.shipImage.getWidth() / 1920D * (double) imageSlot.getX();
        final double y = this.shipImage.getHeight() / 1090D * (double) imageSlot.getY();
        Platform.runLater(() -> {
            final double slotHeight = 5.0D;
            final double spacing = 0.0D;
            final double imageWidth = 81D;
            //Setting the properties of the circle
            this.circleStart.setCenterX(this.shipImage.getWidth() - ScalingHelper.getPixelDoubleFromEm(0.2D) * 1.5);
            this.circleStart.setCenterY(ScalingHelper.getPixelDoubleFromEm(slotHeight / 2) + ScalingHelper.getPixelDoubleFromEm(slotHeight + spacing) * index);
            this.circleStart.setRadius(ScalingHelper.getPixelDoubleFromEm(0.2D));
            this.circleStart.setStroke(Color.valueOf("#89D07F"));
            this.circleStart.setFill(Color.valueOf("#89D07F"));

            drawLine(this.line,
                    this.circleStart.getCenterX(),
                    this.circleStart.getCenterY(),
                    this.circleStart.getCenterX() - ScalingHelper.getPixelDoubleFromEm(2D),
                    this.circleStart.getCenterY());

            this.circleEnd.setCenterX(x);
            this.circleEnd.setCenterY(y);
            this.circleEnd.setRadius(ScalingHelper.getPixelDoubleFromEm(0.4D));
            this.circleEnd.setStroke(Color.valueOf("#89D07F"));
            this.circleEnd.setFill(Color.valueOf("#89D07F"));

            drawLine(this.line2,
                    this.line.getEndX() - ScalingHelper.getPixelDoubleFromEm(0.1D),
                    this.line.getEndY(),
                    this.circleEnd.getCenterX(),
                    this.circleEnd.getCenterY());
        });
    }

    private void drawLine(Line line, double startX, double startY, double endX, double endY) {
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);
        line.setStrokeWidth(ScalingHelper.getPixelDoubleFromEm(0.1D));
        line.setStroke(Color.valueOf("#89D07F"));
    }
}
