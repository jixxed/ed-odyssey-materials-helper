package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.scene.layout.StackPane;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.StackPaneBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfigurations;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;
import nl.jixxed.eliteodysseymaterials.enums.ImportResult;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipMapper;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsTab;

import java.util.Optional;

import static nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent.Type.NONE;

public class HorizonsShipBuilderTab extends HorizonsTab implements DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String SHIP_CONTENT_STYLE_CLASS = "ships-content";

    private DestroyableLabel noShip;
    private DestroyableVBox content;
    private DestroyableVBox contentChild;
    private DestroyableVBox shipView;
    @Getter
    private ControlsLayer controlsLayer;
    @Getter
    private StatsLayer statsLayer;
    @Getter
    private StatsBGLayer statsBGLayer;
    @Getter
    private ModulesLayer modulesLayer;
    @Getter
    private DetailsLayer detailsLayer;
    @Getter
    private ShipSelectionLayer shipSelectionLayer;
    private NoShipLayer noShipLayer;
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

    public void initComponents() {
        this.addBinding(this.textProperty(), LocaleService.getStringBinding("tabs.shipeditor"));


        APPLICATION_STATE.getPreferredCommander()
                .flatMap(commander -> ShipService.getShipConfigurations(commander).getSelectedShipConfiguration())
                .ifPresent(configuration -> APPLICATION_STATE.setShip(ShipMapper.toShip(configuration)));
        controlsLayer = new ControlsLayer();
        statsLayer = new StatsLayer();
        statsBGLayer = new StatsBGLayer(statsLayer);
        detailsLayer = new DetailsLayer();
        modulesLayer = new ModulesLayer(this);
        shipSelectionLayer = new ShipSelectionLayer(this);
        noShipLayer = new NoShipLayer();
        controlsLayer.setVisible(false);
        statsLayer.setVisible(false);
        statsBGLayer.setVisible(false);
        modulesLayer.setVisible(false);
        detailsLayer.setVisible(false);
        shipSelectionLayer.setVisible(false);
        noShipLayer.setVisible(false);
        controlsLayer.setPickOnBounds(false);
        statsLayer.setPickOnBounds(false);
        statsBGLayer.setPickOnBounds(false);
        detailsLayer.setPickOnBounds(false);
        final StackPane stackPane = register(StackPaneBuilder.builder()
                .withStyleClass(SHIP_CONTENT_STYLE_CLASS)
                .withNodes(noShipLayer, shipSelectionLayer, modulesLayer, statsBGLayer, controlsLayer, detailsLayer, statsLayer)
                .build());
        this.setContent(stackPane);
        refreshContent();
        EventService.publish(new ShipConfigEvent(NONE));
    }


    private void refreshContent() {
        APPLICATION_STATE.getPreferredCommander().ifPresentOrElse(commander -> {
            final Optional<ShipConfiguration> shipConfiguration = ShipService.getShipConfigurations(commander).getSelectedShipConfiguration();
            shipConfiguration.ifPresentOrElse(configuration -> {
                if (configuration.getShipType() != null) {
                    showShip();
                    modulesLayer.initShipSlots();
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
        statsLayer.setVisible(false);
        statsBGLayer.setVisible(false);
        modulesLayer.setVisible(false);
        detailsLayer.setVisible(false);
        shipSelectionLayer.setVisible(false);
        noShipLayer.setVisible(true);
    }

    private void showShip() {
        controlsLayer.setVisible(true);
        statsLayer.setVisible(true);
        statsBGLayer.setVisible(true);
        modulesLayer.setVisible(true);
        detailsLayer.setVisible(true);
        shipSelectionLayer.setVisible(false);
        noShipLayer.setVisible(false);

    }

    private void showShipSelect() {
        controlsLayer.setVisible(true);
        statsLayer.setVisible(false);
        statsBGLayer.setVisible(false);
        modulesLayer.setVisible(false);
        detailsLayer.setVisible(false);
        shipSelectionLayer.setVisible(true);
        noShipLayer.setVisible(false);

    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, 0, HorizonsShipSelectedEvent.class, horizonsShipSelectedEvent -> {
            APPLICATION_STATE.getPreferredCommander()
                    .flatMap(commander -> ShipService.getShipConfigurations(commander).getSelectedShipConfiguration())
                    .ifPresent(configuration -> APPLICATION_STATE.setShip(ShipMapper.toShip(configuration)));
            refreshContent();
        }));

        register(EventService.addListener(true, this, CommanderSelectedEvent.class, _ -> refreshContent()));
        register(EventService.addListener(true, this, CommanderAllListedEvent.class, _ -> refreshContent()));
        register(EventService.addListener(true, this, ImportResultEvent.class, importResultEvent -> {
            if (importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_HORIZONS_SHIP) || importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_SLEF)) {
                refreshContent();
            }
        }));

        register(EventService.addListener(true, this, ShipBuilderEvent.class, _ ->
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

        register(EventService.addListener(true, this, 9, ShipLoadoutEvent.class, event -> {

            if (this.controlsLayer.getShipSelect().getSelectionModel().getSelectedItem().equals(ShipConfiguration.CURRENT)) {
                refreshContent();
            }
        }));

    }


}
