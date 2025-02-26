package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfigurations;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;
import nl.jixxed.eliteodysseymaterials.enums.ImportResult;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipMapper;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsTab;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent.Type.NONE;

public class HorizonsShipBuilderTab extends HorizonsTab {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String SHIP_CONTENT_STYLE_CLASS = "ships-content";
    private final List<EventListener<?>> eventListeners = new ArrayList<>();
    private Label noShip;
    private VBox content;
    private VBox contentChild;
    private VBox shipView;
    @Getter
    private ControlsLayer controlsLayer;
    @Getter
    private ShipSelectionLayer shipSelectionLayer;
    private NoShipLayer noShipLayer;
    private ShipLayer shipLayer;
//    private Region filler;
//    private VBox right;


    @Override
    public HorizonsTabs getTabType() {
        return HorizonsTabs.SHIPBUILDER;
    }

    public HorizonsShipBuilderTab() {
        initComponents();
        initEventHandling();

    }

    private void initComponents() {
        this.textProperty().bind(LocaleService.getStringBinding("tabs.shipeditor"));


        APPLICATION_STATE.getPreferredCommander()
                .flatMap(commander -> ShipService.getShipConfigurations(commander).getSelectedShipConfiguration())
                .ifPresent(configuration -> APPLICATION_STATE.setShip(ShipMapper.toShip(configuration)));
        shipLayer = new ShipLayer();
        controlsLayer = new ControlsLayer();
        shipSelectionLayer = new ShipSelectionLayer(this);
        noShipLayer = new NoShipLayer();
        controlsLayer.setVisible(false);
        shipSelectionLayer.setVisible(false);
        noShipLayer.setVisible(false);
        controlsLayer.setPickOnBounds(false);
        final StackPane stackPane = new StackPane(noShipLayer, shipSelectionLayer,  shipLayer);
        stackPane.getStyleClass().add(SHIP_CONTENT_STYLE_CLASS);
        content = BoxBuilder.builder().withNodes(controlsLayer, stackPane).buildVBox();
        this.setContent(content);
        refreshContent();
        EventService.publish(new ShipConfigEvent(NONE));
////        initShipSelectView();
//        initShipLayout();
//        this.noShip = LabelBuilder.builder().withNonLocalizedText("").build();
//        this.contentChild = BoxBuilder.builder().withStyleClass(SHIP_CONTENT_STYLE_CLASS).withNodes(this.shipSelect.getSelectionModel().getSelectedItem() == null || (this.shipSelect.getSelectionModel().getSelectedItem().getShipType() == null && this.shipSelect.getSelectionModel().getSelectedItem() != ShipConfiguration.CURRENT) ? this.shipSelectView : this.shipView).buildVBox();
//        this.content = BoxBuilder.builder().withStyleClass(SHIP_CONTENT_STYLE_CLASS).withNodes(hBoxShips, this.shipSelect.getItems().isEmpty() || this.shipSelect.getSelectionModel().getSelectedItem() == ShipConfiguration.CURRENT ? this.noShip : this.contentChild).buildVBox();
////        this.scrollPane = ScrollPaneBuilder.builder()
////                .withContent(this.content)
////                .build();
//
//        final HBox layout = BoxBuilder.builder().withNodes(this.content, new GrowingRegion(), this.right).buildHBox();

    }


    private void refreshContent() {
        APPLICATION_STATE.getPreferredCommander().ifPresentOrElse(commander -> {
            final Optional<ShipConfiguration> shipConfiguration = ShipService.getShipConfigurations(commander).getSelectedShipConfiguration();
            shipConfiguration.ifPresentOrElse(configuration -> {
                if (configuration.getShipType() != null) {
                    showShip();
                    shipLayer.initShipSlots();
                } else if (configuration == ShipConfiguration.CURRENT) {
                    showNoShip();
                } else {
                    showShipSelect();
                }
            }, this::showNoShip);
        }, this::showNoShip);
    }

    private void showNoShip() {
        controlsLayer.setVisible(true);
        shipLayer.setVisible(false);
        shipSelectionLayer.setVisible(false);
        noShipLayer.setVisible(true);
    }

    private void showShip() {
        controlsLayer.setVisible(true);
        shipLayer.setVisible(true);
        shipSelectionLayer.setVisible(false);
        noShipLayer.setVisible(false);

    }

    private void showShipSelect() {
        controlsLayer.setVisible(true);
        shipLayer.setVisible(false);
        shipSelectionLayer.setVisible(true);
        noShipLayer.setVisible(false);

    }

    private void initEventHandling() {
        this.eventListeners.add(EventService.addListener(true, this, 0, HorizonsShipSelectedEvent.class, horizonsShipSelectedEvent -> {
            APPLICATION_STATE.getPreferredCommander()
                    .flatMap(commander -> ShipService.getShipConfigurations(commander).getSelectedShipConfiguration())
                    .ifPresent(configuration -> APPLICATION_STATE.setShip(ShipMapper.toShip(configuration)));
            refreshContent();
        }));

        this.eventListeners.add(EventService.addListener(true, this, CommanderSelectedEvent.class, _ -> refreshContent()));
        this.eventListeners.add(EventService.addListener(true, this, CommanderAllListedEvent.class, _ -> refreshContent()));
        this.eventListeners.add(EventService.addListener(true, this, ImportResultEvent.class, importResultEvent -> {
            if (importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_HORIZONS_SHIP) || importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_SLEF)) {
                refreshContent();
            }
        }));

        this.eventListeners.add(EventService.addListener(true, this, ShipBuilderEvent.class, _ ->
        {
            if (APPLICATION_STATE.getShip() != null) {
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final ShipConfigurations shipConfigurations = ShipService.getShipConfigurations(commander);
                    final Optional<ShipConfiguration> selectedShipConfiguration = shipConfigurations.getSelectedShipConfiguration();
                    selectedShipConfiguration.ifPresent(shipConfiguration -> {
                        ShipMapper.toShipConfiguration(APPLICATION_STATE.getShip(), shipConfiguration, shipConfiguration.getName());
                        ShipService.saveShipConfigurations(commander, shipConfigurations);
                    });
                });
            }
        }));

        this.eventListeners.add(EventService.addListener(true, this, 9, ShipLoadoutEvent.class, event -> {

            if (this.controlsLayer.getShipSelect().getSelectionModel().getSelectedItem().equals(ShipConfiguration.CURRENT)) {
                refreshContent();
            }
        }));

    }


}
