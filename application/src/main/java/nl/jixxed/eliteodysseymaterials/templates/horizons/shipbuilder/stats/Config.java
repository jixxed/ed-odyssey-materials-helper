package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfig;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.IntField;
import nl.jixxed.eliteodysseymaterials.templates.components.PipSelect;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.math.BigDecimal;
import java.util.Optional;

public class Config extends Stats implements DestroyableEventTemplate {

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private PipSelect systemPipSelect;
    private PipSelect enginePipSelect;
    private PipSelect weaponPipSelect;
    private IntField cargo;
    private IntField fuel;
    private DestroyableSlider fuelreserve;
    private DestroyableCheckBox live;
    private DestroyableResizableImageView power;
    private DestroyableButton powerButton;
    private DestroyableButton powerUp;
    private DestroyableButton powerDown;
    private DestroyableHBox powerBox;
    private DestroyableLabel cargoLabel;
    private DestroyableLabel fuelLabel;

    public Config() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("shipbuilder-stats-configuration");
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(new GrowingRegion(), createTitle("ship.stats.config"), new GrowingRegion()).buildHBox());
        this.getNodes().add(new DestroyableSeparator(Orientation.HORIZONTAL));
        systemPipSelect = new PipSelect(8);
        enginePipSelect = new PipSelect(8);
        weaponPipSelect = new PipSelect(8);
        Double maxFuelReserve = this.getShip().map(Ship::getMaxFuelReserve).orElse(0D);
        int maxFuel = this.getShip().map(Ship::getMaxFuel).orElse(0D).intValue();
        int maxCargo = this.getShip().map(Ship::getMaxCargo).orElse(0D).intValue();
        int maxPassenger = this.getShip().map(Ship::getMaxPassenger).orElse(0D).intValue();
        live = CheckBoxBuilder.builder()
                .withSelected(ApplicationState.getInstance().isLiveStats())
                .withSelectedProperty((_, _, newValue) -> {
                    ApplicationState.getInstance().setLiveStats(Boolean.TRUE.equals(newValue));
                    EventService.publish(new ShipConfigEvent(ShipConfigEvent.Type.LIVE));
                })
                .withDisable(!isCurrentShip())
                .build();


        fuel = new IntField(0, maxFuel, this.getShip().map(Ship::getCurrentFuel).orElse(0D).intValue());
        fuel.getStyleClass().add("config-intfield");

        fuelreserve = SliderBuilder.builder()
                .withStyleClass("config-fuelreserve")
                .withMin(0)
                .withMax(maxFuelReserve)
                .withValue(this.getShip().map(Ship::getCurrentFuelReserve).orElse(0D).intValue())
                .withDisableProperty(live.selectedProperty())
                .build();

        cargo = new IntField(0, maxCargo + maxPassenger, this.getShip().map(Ship::getCurrentCargo).orElse(0D).intValue());
        cargo.getStyleClass().add("config-intfield");

        powerBox = BoxBuilder.builder()
                .withStyleClass("shipbuilder-slots-slotbox-power-config")
                .buildHBox();
        powerBox();
        fuelLabel = createLabel("ship.stats.config.fuel", String.valueOf(maxFuel));
        cargoLabel = createLabel("ship.stats.config.cargo", String.valueOf(maxCargo), String.valueOf(maxPassenger));

        fuel.addBinding(fuel.disableProperty(), live.selectedProperty());
        cargo.addBinding(cargo.disableProperty(), live.selectedProperty());
        systemPipSelect.addBinding(systemPipSelect.disableProperty(), live.selectedProperty());
        enginePipSelect.addBinding(enginePipSelect.disableProperty(), live.selectedProperty());
        weaponPipSelect.addBinding(weaponPipSelect.disableProperty(), live.selectedProperty());

        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.config.live"), new GrowingRegion(), live)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.config.fuelreserve"), new GrowingRegion(), fuelreserve)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(fuelLabel, new GrowingRegion(), fuel)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(cargoLabel, new GrowingRegion(), cargo)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.config.system"), new GrowingRegion(), systemPipSelect)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.config.engine"), new GrowingRegion(), enginePipSelect)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.config.weapon"), new GrowingRegion(), weaponPipSelect)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.config.cargo_hatch"), new GrowingRegion(), powerBox)
                .buildHBox());
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
        fuelreserve.addChangeListener(fuelreserve.valueProperty(), (_, _, newValue) -> {
            this.getShip().ifPresent(ship -> ship.setCurrentFuelReserve(newValue.doubleValue()));
            EventService.publish(new ShipConfigEvent(ShipConfigEvent.Type.WEIGHT));
            if (Boolean.FALSE.equals(isCurrentShip())) {
                EventService.publish(new ShipBuilderEvent());
            }
        });
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

    private void powerBox() {
        this.power = createIcon("shipbuilder-slots-slotbox-button-icon", "/images/ships/icons/powered1.png", 1);
        this.powerButton = ButtonBuilder.builder()
                .withStyleClass("shipbuilder-slots-slotbox-button")
                .withOnAction(event -> {
                    this.getShip().ifPresent(ship -> {
                        ship.getCargoHatch().getShipModule().togglePower();
                        notifyChanged();
                        updatePower(ship.getCargoHatch().getShipModule());
                    });
                })
                .withGraphic(this.power)
                .build();
        initPowerBox();
        this.getShip().ifPresent(ship -> {
            updatePower(ship.getCargoHatch().getShipModule());
        });
    }

    private void initPowerBox() {
        this.powerBox.getNodes().clear();
        if (!isCurrentShip()) {
            powerUp = ButtonBuilder.builder()
                    .withStyleClass("shipbuilder-slots-slotbox-button")
                    .withOnAction(event -> {
                        this.getShip().ifPresent(ship -> {
                            ship.getCargoHatch().getShipModule().decreasePowerGroup();
                            notifyChanged();
                            updatePower(ship.getCargoHatch().getShipModule());
                        });
                    })
                    .withGraphic(createIcon("shipbuilder-slots-slotbox-button-icon", "/images/ships/icons/arrow_left.png"))
                    .build();
            powerDown = ButtonBuilder.builder()
                    .withStyleClass("shipbuilder-slots-slotbox-button")
                    .withOnAction(event -> {
                        this.getShip().ifPresent(ship -> {
                            ship.getCargoHatch().getShipModule().increasePowerGroup();
                            notifyChanged();
                            updatePower(ship.getCargoHatch().getShipModule());
                        });
                    })
                    .withGraphic(createIcon("shipbuilder-slots-slotbox-button-icon", "/images/ships/icons/arrow_right.png"))
                    .build();
            this.powerUp.setFocusTraversable(false);
            this.powerDown.setFocusTraversable(false);
            this.powerBox.getNodes().addAll(powerUp, powerButton, powerDown);
        } else {
            final GrowingRegion growingRegion = new GrowingRegion();
            this.powerBox.getNodes().addAll(growingRegion, this.power, new GrowingRegion());
        }
    }

    private void updatePower(ShipModule shipModule) {
        if (shipModule != null) {
            //todo memory leak? destroy old graphic if exists
            this.power = createIcon("shipbuilder-slots-slotbox-button-icon", "/images/ships/icons/" + (shipModule.isPowered() ? "powered" : "unpowered") + shipModule.getPowerGroup() + ".png", shipModule.getPowerGroup());
            this.powerButton.setGraphic(this.power);
            this.powerButton.register(this.power);
            final boolean hasPowerToggle = shipModule.hasPowerToggle();
            this.power.setVisible(hasPowerToggle);
            if (!isCurrentShip()) {
                final int powerGroup = shipModule.getPowerGroup();
                this.powerUp.setVisible(hasPowerToggle && powerGroup > 1);
                this.powerDown.setVisible(hasPowerToggle && powerGroup < 5);
                this.powerButton.setVisible(hasPowerToggle);
            } else {
                this.powerBox.getNodes().clear();
                this.powerBox.getNodes().addAll(new GrowingRegion(), this.power, new GrowingRegion());
            }
        }
    }

    private static DestroyableResizableImageView createIcon(String styleClass, String imageResource, Integer powerGroup) {
        final DestroyableResizableImageView icon = createIcon(styleClass, imageResource);
        Tooltip.install(icon, TooltipBuilder.builder()
                .withShowDelay(Duration.seconds(0.1))
                .withText("ship.stats.config.power.group", powerGroup)
                .build());
        return icon;
    }

    private static DestroyableResizableImageView createIcon(String styleClass, String imageResource) {
        return ResizableImageViewBuilder.builder()
                .withStyleClass(styleClass)
                .withImage(imageResource)
                .build();
    }

    private void notifyChanged() {
        EventService.publish(new ShipBuilderEvent());
        update();
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
            int maxPassenger = this.getShip().map(Ship::getMaxPassenger).orElse(0D).intValue();
            fuel.setMaxValue(maxFuel);
            fuel.setValue(getShip().map(Ship::getCurrentFuel).orElse(0D).intValue());
            fuelreserve.setMax(maxFuelReserve);
            fuelreserve.setValue(getShip().map(Ship::getCurrentFuelReserve).orElse(0D));
            cargo.setMaxValue(maxCargo + maxPassenger);
            cargo.setValue(getShip().map(Ship::getCurrentCargo).orElse(0D).intValue());
            initPowerBox();
            this.getShip().ifPresent(ship -> {
                updatePower(ship.getCargoHatch().getShipModule());
            });
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
    }

    @Override
    protected void update() {
        int maxFuel = this.getShip().map(Ship::getMaxFuel).orElse(0D).intValue();
        int maxCargo = this.getShip().map(Ship::getMaxCargo).orElse(0D).intValue();
        int maxPassenger = this.getShip().map(Ship::getMaxPassenger).orElse(0D).intValue();

        if (fuel.getMaxValue() != maxFuel) {
            fuel.setMaxValue(maxFuel);
            fuelLabel.addBinding(fuelLabel.textProperty(), LocaleService.getStringBinding("ship.stats.config.fuel", maxFuel));
        }

        cargo.setMaxValue(maxCargo + maxPassenger);
        cargoLabel.addBinding(cargoLabel.textProperty(), LocaleService.getStringBinding("ship.stats.config.cargo", maxCargo, maxPassenger));

    }
}
