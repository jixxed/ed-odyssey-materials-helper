package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
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
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsTab;

import java.util.Optional;

import static nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent.Type.NONE;

@Slf4j
public class HorizonsShipBuilderTab extends HorizonsTab implements DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String SHIP_CONTENT_STYLE_CLASS = "ships-content";

    private ControlsSection controlsSection;
    private StatsSection statsSection;
    private ModulesSection modulesSection;
    private ShipSelectView shipSelectView;
    //    private NoShipLayer noShipLayer;
    private ModuleDetailsPopover detailsPopOver;
    private ShipViewPopover shipViewPopOver;

    @Override
    public HorizonsTabs getTabType() {
        return HorizonsTabs.SHIPBUILDER;
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


        this.register(detailsPopOver);
        this.register(shipViewPopOver);
        shipSelectView = new ShipSelectView();
//        shipSelectionLayer = new ShipSelectionLayer();
//        noShipLayer = new NoShipLayer();
//        controlsLayer.setVisible(false);
        statsSection.setVisible(false);
        modulesSection.setVisible(false);
        shipSelectView.setVisible(false);
//        noShipLayer.setVisible(false);
//        controlsLayer.setPickOnBounds(false);
//        statsSection.setPickOnBounds(false);
        DestroyableVBox content = BoxBuilder.builder()
                .withStyleClass("contents")
                .withNodes(
                        controlsSection,
                        statsSection,
                        modulesSection,
//                        noShipLayer,
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
//                    statsSection.initStats();
                } else if (configuration == ShipConfiguration.CURRENT) {
                    showNoShip();
                } else {
                    showShipSelect();
                }
            }, this::showNoShip);
        }, this::showNoShip);
    }

    private void showNoShip() {
//        setVisible(controlsLayer, true);
        setVisible(statsSection, false);
        setVisible(modulesSection, false);
        setVisible(shipSelectView, false);
//        setVisible(noShipLayer, true);
    }

    private void showShip() {
//        setVisible(controlsLayer, true);
        setVisible(statsSection, true);
        setVisible(modulesSection, true);
        setVisible(shipSelectView, false);
//        setVisible(noShipLayer, false);

    }

    private void showShipSelect() {
//        setVisible(controlsLayer, true);
        setVisible(statsSection, false);
        setVisible(modulesSection, false);
        setVisible(shipSelectView, true);
//        setVisible(noShipLayer, false);

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

        register(EventService.addListener(true, this, 9, ShipLoadoutEvent.class, event -> {

//            if (this.controlsLayer.getShipSelect().getSelectionModel().getSelectedItem().equals(ShipConfiguration.CURRENT)) {
//                refreshContent();
//            }
        }));
//        register(EventService.addListener(true, this, /*9,*/ SlotboxHoverEvent.class, (event) -> {
//            if (activeSlotBox != null && activeSlotBox.isMenuOpen()) {
//                return;
//            }
//            activeSlotBox = event.getSlotBox();
////            if (event.isShow() && event.getShipModule() != null) {
////                if (!detailsPopOver.isShowing()) {
////                    var moduleDetails = new ModuleDetails(event.getShipModule());
////                    if (detailsPopOver.getContentNode() instanceof Destroyable destroyable) {
////                        destroyable.destroy();
////                    }
////                    detailsPopOver.setContentNode(detailsPopOver.register(moduleDetails));
////                    detailsPopOver.show(activeSlotBox);
////                }
////            } else if (detailsPopOver.isShowing() && !activeSlotBox.isMenuOpen()) {
////                detailsPopOver.hide(Duration.ZERO);
////            }
//
//            if (event.isHover() && (event.getSlotBox().getSlot().getSlotType() == SlotType.HARDPOINT || event.getSlotBox().getSlot().getSlotType() == SlotType.UTILITY)) {
//                if (!shipViewPopOver.isShowing()) {
//                    final Bounds boundsInLocal = event.getSlotBox().getBoundsInLocal();
//                    final Bounds bounds = event.getSlotBox().localToScreen(boundsInLocal);
//                    final int x = (int) bounds.getMinX();
//                    final int y = (int) (bounds.getMinY() - shipViewPopOver.getHeight());
//
//                    var shipView = new ShipView(activeSlotBox);
//                    if (shipViewPopOver.getContentNode() instanceof Destroyable destroyable) {
//                        destroyable.destroy();
//                    }
//                    shipViewPopOver.setContentNode(shipViewPopOver.register(shipView));
//                    shipViewPopOver.show(event.getSlotBox(), x, y, Duration.millis(1));
//                }
//            } else if (shipViewPopOver.isShowing() && !activeSlotBox.isMenuOpen()) {
//                shipViewPopOver.hide(Duration.ZERO);
//            }
//        }));
////        register(EventService.addListener(true, this, /*9,*/ ModuleSelectHoverEvent.class, (event) -> {
////            ShipModule shipModule = event.isShow() ? event.getShipModule() : activeSlotBox.getSlot().getShipModule();
////            var moduleDetails = new ModuleDetails(shipModule);
////            if (detailsPopOver.getContentNode() instanceof Destroyable destroyable) {
////                destroyable.destroy();
////            }
////            detailsPopOver.setContentNode(detailsPopOver.register(moduleDetails));
////            updateDetailsPopoverPosition();
////        }));
////        register(EventService.addListener(true, this, /*9,*/ ModuleSelectCloseEvent.class, (event) -> {
////            if (shipViewPopOver.isShowing()) {
////                shipViewPopOver.hide(Duration.ZERO);
////            }
////        }));

    }

    @Override
    public void destroyInternal() {
        super.destroyInternal();
        this.modulesSection = null;
    }
}
