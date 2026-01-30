package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.FXApplication;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfigurations;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabType;
import nl.jixxed.eliteodysseymaterials.enums.ImportResult;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipMapper;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsTab;

import java.util.Optional;

import static nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent.Type.NONE;

@Slf4j
public class HorizonsShipBuilderTab extends HorizonsTab implements DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    private ControlsSection controlsSection;
    private StatsSection statsSection;
    private ModulesSection modulesSection;
    private ShipSelectView shipSelectView;
    private ModuleDetailsPopover detailsPopOver;
    private ShipViewPopover shipViewPopOver;

    @Override
    public HorizonsTabType getTabType() {
        return HorizonsTabType.SHIPBUILDER;
    }

    public HorizonsShipBuilderTab() {
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().add("shipbuilder-tab");
        this.addBinding(this.textProperty(), LocaleService.getStringBinding("tabs.shipeditor"));


        APPLICATION_STATE.getPreferredCommander()
                .flatMap(commander -> ShipService.getShipConfigurations(commander).getSelectedShipConfiguration())
                .ifPresent(configuration -> APPLICATION_STATE.setShip(ShipMapper.toShip(configuration)));
        controlsSection = new ControlsSection();
        statsSection = new StatsSection();
        modulesSection = new ModulesSection();


        detailsPopOver = new ModuleDetailsPopover();
        shipViewPopOver = new ShipViewPopover();
        FXApplication.getInstance().getPrimaryStage().focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                System.out.println("App lost focus");
                detailsPopOver.hide(Duration.ZERO);
                shipViewPopOver.hide(Duration.ZERO);
            }
        });
        this.register(detailsPopOver);
        this.register(shipViewPopOver);
        shipSelectView = new ShipSelectView();
        statsSection.setVisible(false);
        modulesSection.setVisible(false);
        shipSelectView.setVisible(false);
        DestroyableVBox content = BoxBuilder.builder()
                .withStyleClass("contents")
                .withNodes(
                        controlsSection,
                        statsSection,
                        modulesSection,
                        shipSelectView)
                .buildVBox();
        this.setClosable(false);
        ScrollPane scrollPane = register(ScrollPaneBuilder.builder()
                .withStyleClass("shipbuilder-tab-content")
                .withContent(content)
                .build());
        this.setContent(scrollPane);
        refreshContent();
        EventService.publish(new ShipConfigEvent(NONE));
    }

    private void refreshContent() {
        APPLICATION_STATE.getPreferredCommander().ifPresentOrElse(commander -> {
            final Optional<ShipConfiguration> shipConfiguration = ShipService.getShipConfigurations(commander).getSelectedShipConfiguration();
            shipConfiguration.ifPresentOrElse(configuration -> {
                if (configuration.getShipType() != null) {
                    showShip();
                    modulesSection.initShipSlots();
                } else if (configuration == ShipConfiguration.CURRENT) {
                    showNoShip();
                } else {
                    showShipSelect();
                }
            }, this::showNoShip);
        }, this::showNoShip);
    }

    private void showNoShip() {
        setVisible(statsSection, false);
        setVisible(modulesSection, false);
        setVisible(shipSelectView, false);
    }

    private void showShip() {
        setVisible(statsSection, true);
        setVisible(modulesSection, true);
        setVisible(shipSelectView, false);

    }

    private void showShipSelect() {
        setVisible(statsSection, false);
        setVisible(modulesSection, false);
        setVisible(shipSelectView, true);

    }

    private void setVisible(Node node, boolean visible) {
        node.setVisible(visible);
        node.setManaged(visible);
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, 0, HorizonsShipSelectedEvent.class, horizonsShipSelectedEvent -> {
            APPLICATION_STATE.getPreferredCommander()
                    .flatMap(commander -> ShipService.getShipConfigurations(commander).getSelectedShipConfiguration())
                    .ifPresent(configuration -> APPLICATION_STATE.setShip(ShipMapper.toShip(configuration)));
            refreshContent();
        }));

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
    }

    @Override
    public void destroyInternal() {
        super.destroyInternal();
        this.modulesSection = null;
    }
}
