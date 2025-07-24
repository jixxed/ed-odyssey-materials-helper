package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.CheckBoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.SliderBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfig;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.MinMaxIntField;
import nl.jixxed.eliteodysseymaterials.templates.components.PipSelect;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Config extends Stats implements DestroyableEventTemplate {

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private PipSelect systemPipSelect;
    private PipSelect enginePipSelect;
    private PipSelect weaponPipSelect;
    private MinMaxIntField cargo;
    private MinMaxIntField fuel;
    private DestroyableSlider fuelreserve;
    private DestroyableCheckBox live;
    private ConfigPowerControl powerControl;
    private DestroyableLabel cargoLabel;
    private DestroyableLabel fuelLabel;
    private Disposable subscribe;

    public Config() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("config");
        addTitle("ship.stats.config");
        systemPipSelect = new PipSelect(8);
        enginePipSelect = new PipSelect(8);
        weaponPipSelect = new PipSelect(8);
        Double maxFuelReserve = this.getShip().map(Ship::getMaxFuelReserve).orElse(0D);
        int maxFuel = this.getShip().map(Ship::getMaxFuel).orElse(0D).intValue();
        int maxCargo = this.getShip().map(Ship::getMaxCargo).orElse(0D).intValue();
        live = CheckBoxBuilder.builder()
                .withSelected(ApplicationState.getInstance().isLiveStats())
                .withSelectedProperty((_, _, newValue) -> {
                    ApplicationState.getInstance().setLiveStats(Boolean.TRUE.equals(newValue));
                    EventService.publish(new ShipConfigEvent(ShipConfigEvent.Type.LIVE));
                })
                .withDisable(!isCurrentShip())
                .build();


        fuel = new MinMaxIntField(0, maxFuel, this.getShip().map(Ship::getCurrentFuel).orElse(0D).intValue());

        fuelreserve = SliderBuilder.builder()
                .withStyleClass("fuelreserve")
                .withMin(0)
                .withMax(maxFuelReserve)
                .withValue(this.getShip().map(Ship::getCurrentFuelReserve).orElse(0D))
                .withDisableProperty(live.selectedProperty())
                .build();

        cargo = new MinMaxIntField(0, maxCargo, this.getShip().map(Ship::getCurrentCargo).orElse(0D).intValue());
        powerControl = new ConfigPowerControl();

        fuelLabel = createLabel("ship.stats.config.fuel", String.valueOf(maxFuel));
        cargoLabel = createLabel("ship.stats.config.cargo", String.valueOf(maxCargo));
        fuel.addBinding(fuel.disableProperty(), live.selectedProperty());
        cargo.addBinding(cargo.disableProperty(), live.selectedProperty());
        systemPipSelect.addBinding(systemPipSelect.disableProperty(), live.selectedProperty());
        enginePipSelect.addBinding(enginePipSelect.disableProperty(), live.selectedProperty());
        weaponPipSelect.addBinding(weaponPipSelect.disableProperty(), live.selectedProperty());
        DestroyableVBox controls = BoxBuilder.builder()
                .withStyleClass("config-controls")
                .buildVBox();
        controls.getNodes().add(BoxBuilder.builder()
                .withStyleClass("control-line")
                .withNodes(createLabel("ship.stats.config.live"), new GrowingRegion(), live)
                .buildHBox());
        controls.getNodes().add(BoxBuilder.builder()
                .withStyleClass("control-line")
                .withNodes(createLabel("ship.stats.config.fuelreserve"), new GrowingRegion(), fuelreserve)
                .buildHBox());
        controls.getNodes().add(BoxBuilder.builder()
                .withStyleClass("control-line")
                .withNodes(fuelLabel, new GrowingRegion(), fuel)
                .buildHBox());
        controls.getNodes().add(BoxBuilder.builder()
                .withStyleClass("control-line")
                .withNodes(cargoLabel, new GrowingRegion(), cargo)
                .buildHBox());
        controls.getNodes().add(BoxBuilder.builder()
                .withStyleClass("control-line")
                .withNodes(createSmallLabel("ship.stats.config.system"), new GrowingRegion(), systemPipSelect)
                .buildHBox());
        controls.getNodes().add(BoxBuilder.builder()
                .withStyleClass("control-line")
                .withNodes(createSmallLabel("ship.stats.config.engine"), new GrowingRegion(), enginePipSelect)
                .buildHBox());
        controls.getNodes().add(BoxBuilder.builder()
                .withStyleClass("control-line")
                .withNodes(createSmallLabel("ship.stats.config.weapon"), new GrowingRegion(), weaponPipSelect)
                .buildHBox());
        controls.getNodes().add(BoxBuilder.builder()
                .withStyleClass("control-line")
                .withNodes(createLabel("ship.stats.config.cargo_hatch"), new GrowingRegion(), powerControl)
                .buildHBox());
        this.getNodes().add(controls);
        systemPipSelect.addChangeListener(systemPipSelect.valueProperty(), (_, _, newValue) -> {
            ApplicationState.getInstance().setSystemPips(newValue.intValue());
            EventService.publish(new ShipConfigEvent(ShipConfigEvent.Type.PIPS));
        });
        enginePipSelect.addChangeListener(enginePipSelect.valueProperty(), (_, _, newValue) -> {
            ApplicationState.getInstance().setEnginePips(newValue.intValue());
            EventService.publish(new ShipConfigEvent(ShipConfigEvent.Type.PIPS));
        });
        weaponPipSelect.addChangeListener(weaponPipSelect.valueProperty(), (_, _, newValue) -> {
            ApplicationState.getInstance().setWeaponPips(newValue.intValue());
            EventService.publish(new ShipConfigEvent(ShipConfigEvent.Type.PIPS));
        });
        fuel.addChangeListener(fuel.valueProperty(), (_, _, newValue) -> {
            this.getShip().ifPresent(ship -> ship.setCurrentFuel(newValue.doubleValue()));
            EventService.publish(new ShipConfigEvent(ShipConfigEvent.Type.WEIGHT));
            if (Boolean.FALSE.equals(isCurrentShip())) {
                EventService.publish(new ShipBuilderEvent());
            }
        });
        cargo.addChangeListener(cargo.valueProperty(), (_, _, newValue) -> {
            this.getShip().ifPresent(ship -> ship.setCurrentCargo(newValue.doubleValue()));
            EventService.publish(new ShipConfigEvent(ShipConfigEvent.Type.WEIGHT));
            if (Boolean.FALSE.equals(isCurrentShip())) {
                EventService.publish(new ShipBuilderEvent());
            }
        });
        subscribe = Observable
                .create((ObservableEmitter<Number> emitter) -> fuelreserve.addChangeListener(fuelreserve.valueProperty(), (_, _, newValue) -> emitter.onNext(newValue)))
                .debounce(100, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(newValue -> Platform.runLater(() -> {
                    this.getShip().ifPresent(ship -> ship.setCurrentFuelReserve(newValue.doubleValue()));
                    EventService.publish(new ShipConfigEvent(ShipConfigEvent.Type.WEIGHT));
                    if (Boolean.FALSE.equals(isCurrentShip())) {
                        EventService.publish(new ShipBuilderEvent());
                    }
                }));

        this.live.addChangeListener(live.selectedProperty(), (_, _, newValue) -> {
            if (Boolean.TRUE.equals(newValue)) {
                final Optional<ShipConfig> shipConfigOptional = ApplicationState.getInstance().getShipConfig();
                shipConfigOptional.ifPresent(shipConfig -> {
                    updateConfig(shipConfig.getFuelCapacity(), shipConfig.getFuelReserve(), shipConfig.getCargoCapacity(), shipConfig.getSystemPips(), shipConfig.getEnginePips(), shipConfig.getWeaponPips());
                });
            }
        });
    }

    private void updateConfig(BigDecimal fuelCapacity, BigDecimal fuelReserve, BigDecimal cargoCapacity, Integer systemPips, Integer enginePips, Integer weaponPips) {
        fuel.setValue(fuelCapacity.intValue());
        fuelreserve.setValue(fuelReserve.doubleValue());
        cargo.setValue(cargoCapacity.intValue());
        systemPipSelect.setValue(systemPips);
        enginePipSelect.setValue(enginePips);
        weaponPipSelect.setValue(weaponPips);
    }

    private static Boolean isCurrentShip() {
        return APPLICATION_STATE.getPreferredCommander()
                .flatMap(commander -> ShipService.getShipConfigurations(commander).getSelectedShipConfiguration())
                .map(shipConfiguration -> ShipConfiguration.CURRENT == shipConfiguration)
                .orElse(Boolean.FALSE);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, HorizonsShipSelectedEvent.class, event -> {
            double maxFuelReserve = this.getShip().map(Ship::getMaxFuelReserve).orElse(0D);
            int maxFuel = this.getShip().map(Ship::getMaxFuel).orElse(0D).intValue();
            int maxCargo = this.getShip().map(Ship::getMaxCargo).orElse(0D).intValue();
            fuel.setMaxValue(maxFuel);
            fuel.setValue(getShip().map(Ship::getCurrentFuel).orElse(0D).intValue());
            fuelreserve.setMax(maxFuelReserve);
            fuelreserve.setValue(getShip().map(Ship::getCurrentFuelReserve).orElse(0D));
            cargo.setMaxValue(maxCargo);
            cargo.setValue(getShip().map(Ship::getCurrentCargo).orElse(0D).intValue());
            this.getShip().ifPresent(ship -> {
                powerControl.updatePower(ship.getCargoHatch().getShipModule());
            });
            log.debug("Ship selected: {}, isCurrentShip: {}", event.getShipUUID(), isCurrentShip());
            this.live.setSelected(this.live.isSelected() && isCurrentShip());
            this.live.setDisable(!isCurrentShip());
        }));
        register(EventService.addListener(true, this, StatusEvent.class, event -> {
            if (ApplicationState.getInstance().isLiveStats()) {
                updateConfig(
                        event.getFuelCapacity(),
                        event.getFuelReserve(),
                        event.getCargoCapacity(),
                        event.getSystemPips(),
                        event.getEnginePips(),
                        event.getWeaponPips());
            }
        }));
        register(EventService.addListener(true, this, ShipBuilderEvent.class, event -> {
            update();
        }));
    }

    @Override
    protected void update() {
        int maxFuel = this.getShip().map(Ship::getMaxFuel).orElse(0D).intValue();
        int maxCargo = this.getShip().map(Ship::getMaxCargo).orElse(0D).intValue();

        if (fuel.getMaxValue() != maxFuel) {
            fuel.setMaxValue(maxFuel);
            fuelLabel.addBinding(fuelLabel.textProperty(), LocaleService.getStringBinding("ship.stats.config.fuel", maxFuel));
        }

        cargo.setMaxValue(maxCargo);
        cargoLabel.addBinding(cargoLabel.textProperty(), LocaleService.getStringBinding("ship.stats.config.cargo", maxCargo));

        this.getShip().ifPresent(ship -> {
            powerControl.updatePower(ship.getCargoHatch().getShipModule());
        });
    }


    @Override
    public void destroyInternal() {
        super.destroyInternal();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }
}
