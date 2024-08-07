package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfig;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.IntField;
import nl.jixxed.eliteodysseymaterials.templates.components.PipSelect;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

import java.util.Optional;

public class Config extends Stats implements Template {

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private PipSelect systemPipSelect;
    private PipSelect enginePipSelect;
    private PipSelect weaponPipSelect;
    private IntField cargo;
    private IntField fuel;
    private Slider fuelreserve;
    private CheckBox live;
    private DestroyableResizableImageView power;
    Button powerButton;
    Button powerUp;
    Button powerDown;
    private HBox powerBox;
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
        this.getChildren().add(BoxBuilder.builder().withNodes(new GrowingRegion(), createTitle("ship.stats.config"), new GrowingRegion()).buildHBox());
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        systemPipSelect = new PipSelect(8);
        enginePipSelect = new PipSelect(8);
        weaponPipSelect = new PipSelect(8);
        Double maxFuelReserve = this.getShip().map(Ship::getMaxFuelReserve).orElse(0D);
        int maxFuel = this.getShip().map(Ship::getMaxFuel).orElse(0D).intValue();
        int maxCargo = this.getShip().map(Ship::getMaxCargo).orElse(0D).intValue();
        live = new CheckBox();
        live.setSelected(ApplicationState.getInstance().isLiveStats());
        live.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    ApplicationState.getInstance().setLiveStats(Boolean.TRUE.equals(newValue));
                    EventService.publish(new ShipConfigEvent(ShipConfigEvent.Type.LIVE));
                });

        fuel = new IntField(0, maxFuel, getShip().map(Ship::getCurrentFuel).orElse(0D).intValue());
        fuelreserve = new Slider(0, maxFuelReserve, maxFuelReserve);
        fuelreserve.getStyleClass().add("config-fuelreserve");
        cargo = new IntField(0, maxCargo, getShip().map(Ship::getCurrentCargo).orElse(0D).intValue());
        int maxPassenger = this.getShip().map(Ship::getMaxPassenger).orElse(0D).intValue();

        this.live.setDisable(!isCurrentShip());
        fuel.getStyleClass().add("config-intfield");
        cargo.getStyleClass().add("config-intfield");
        powerBox = BoxBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-power-config").buildHBox();
        powerBox();
        fuelLabel = createLabel("ship.stats.config.fuel", String.valueOf(maxFuel));
        cargoLabel = createLabel("ship.stats.config.cargo", String.valueOf(maxCargo), String.valueOf(maxPassenger));
        fuelreserve.disableProperty().bind(live.selectedProperty());
        fuel.disableProperty().bind(live.selectedProperty());
        cargo.disableProperty().bind(live.selectedProperty());
        systemPipSelect.disableProperty().bind(live.selectedProperty());
        enginePipSelect.disableProperty().bind(live.selectedProperty());
        weaponPipSelect.disableProperty().bind(live.selectedProperty());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.config.live"), new GrowingRegion(), live).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.config.fuelreserve"), new GrowingRegion(), fuelreserve).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(fuelLabel, new GrowingRegion(), fuel).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(cargoLabel, new GrowingRegion(), cargo).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.config.system"), new GrowingRegion(), systemPipSelect).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.config.engine"), new GrowingRegion(), enginePipSelect).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.config.weapon"), new GrowingRegion(), weaponPipSelect).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.config.cargo_hatch"), new GrowingRegion(), powerBox).buildHBox());
        systemPipSelect.valueProperty().addListener((observable, oldValue, newValue) -> {
            ApplicationState.getInstance().setSystemPips(newValue.intValue());
            EventService.publish(new ShipConfigEvent(ShipConfigEvent.Type.PIPS));
        });
        enginePipSelect.valueProperty().addListener((observable, oldValue, newValue) -> {
            ApplicationState.getInstance().setEnginePips(newValue.intValue());
            EventService.publish(new ShipConfigEvent(ShipConfigEvent.Type.PIPS));
        });
        weaponPipSelect.valueProperty().addListener((observable, oldValue, newValue) -> {
            ApplicationState.getInstance().setWeaponPips(newValue.intValue());
            EventService.publish(new ShipConfigEvent(ShipConfigEvent.Type.PIPS));
        });
        fuel.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.getShip().ifPresent(ship -> ship.setCurrentFuel(newValue.doubleValue()));
            EventService.publish(new ShipConfigEvent(ShipConfigEvent.Type.WEIGHT));
        });
        cargo.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.getShip().ifPresent(ship -> ship.setCurrentCargo(newValue.doubleValue()));
            EventService.publish(new ShipConfigEvent(ShipConfigEvent.Type.WEIGHT));
        });
        fuelreserve.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.getShip().ifPresent(ship -> ship.setCurrentFuelReserve(newValue.doubleValue()));
            EventService.publish(new ShipConfigEvent(ShipConfigEvent.Type.WEIGHT));
        });
        this.live.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (Boolean.TRUE.equals(newValue)) {
                final Optional<ShipConfig> shipConfigOptional = ApplicationState.getInstance().getShipConfig();
                shipConfigOptional.ifPresent(shipConfig -> {
                    fuel.setValue(shipConfig.getFuelCapacity().intValue());
                    fuelreserve.setValue(shipConfig.getFuelReserve().doubleValue());
                    cargo.setValue(shipConfig.getCargoCapacity().intValue());
                    systemPipSelect.setValue(shipConfig.getSystemPips());
                    enginePipSelect.setValue(shipConfig.getEnginePips());
                    weaponPipSelect.setValue(shipConfig.getWeaponPips());
                });
            }
        });
    }

    private void powerBox() {
        this.power = createIcon("shipbuilder-slots-slotbox-button-icon", "/images/ships/icons/powered1.png", 1);
        this.powerButton = ButtonBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-button").withOnAction(event -> {
            this.getShip().ifPresent(ship -> {
                ship.getCargoHatch().getShipModule().togglePower();
                notifyChanged();
                updatePower(ship.getCargoHatch().getShipModule());
            });
        }).withGraphic(this.power).build();
        initPowerBox();
        this.getShip().ifPresent(ship -> {
            updatePower(ship.getCargoHatch().getShipModule());
        });
    }

    private void initPowerBox() {
        this.powerBox.getChildren().clear();
        if (!isCurrentShip()) {
            powerUp = ButtonBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-button").withOnAction(event -> {
                this.getShip().ifPresent(ship -> {
                    ship.getCargoHatch().getShipModule().decreasePowerGroup();
                    notifyChanged();
                    updatePower(ship.getCargoHatch().getShipModule());
                });
            }).withGraphic(createIcon("shipbuilder-slots-slotbox-button-icon", "/images/ships/icons/arrow_left.png")).build();
            powerDown = ButtonBuilder.builder().withStyleClass("shipbuilder-slots-slotbox-button").withOnAction(event -> {
                this.getShip().ifPresent(ship -> {
                    ship.getCargoHatch().getShipModule().increasePowerGroup();
                    notifyChanged();
                    updatePower(ship.getCargoHatch().getShipModule());
                });
            }).withGraphic(createIcon("shipbuilder-slots-slotbox-button-icon", "/images/ships/icons/arrow_right.png")).build();
            this.powerUp.setFocusTraversable(false);
            this.powerDown.setFocusTraversable(false);
            this.powerBox.getChildren().addAll(powerUp, powerButton, powerDown);
        } else {
            final GrowingRegion growingRegion = new GrowingRegion();
            this.powerBox.getChildren().addAll(growingRegion, this.power, new GrowingRegion());
        }
    }

    private void updatePower(ShipModule shipModule) {
        if (shipModule != null) {
            this.power = createIcon("shipbuilder-slots-slotbox-button-icon", "/images/ships/icons/" + (shipModule.isPowered() ? "powered" : "unpowered") + shipModule.getPowerGroup() + ".png", shipModule.getPowerGroup());
            this.powerButton.setGraphic(this.power);
            final boolean hasPowerToggle = shipModule.hasPowerToggle();
            this.power.setVisible(hasPowerToggle);
            if (!isCurrentShip()) {
                final int powerGroup = shipModule.getPowerGroup();
                this.powerUp.setVisible(hasPowerToggle && powerGroup > 1);
                this.powerDown.setVisible(hasPowerToggle && powerGroup < 5);
                this.powerButton.setVisible(hasPowerToggle);
            } else {
                this.powerBox.getChildren().clear();
                this.powerBox.getChildren().addAll(new GrowingRegion(), this.power, new GrowingRegion());
            }
        }
    }

    private static DestroyableResizableImageView createIcon(String styleClass, String imageResource, Integer powerGroup) {
        final DestroyableResizableImageView icon = createIcon(styleClass, imageResource);
        Tooltip.install(icon, TooltipBuilder.builder().withShowDelay(Duration.seconds(0.1)).withText(LocaleService.getStringBinding("ship.stats.config.power.group", powerGroup)).build());
        return icon;
    }

    private static DestroyableResizableImageView createIcon(String styleClass, String imageResource) {
        return ResizableImageViewBuilder.builder().withStyleClass(styleClass).withImage(imageResource).build();
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
        eventListeners.add(EventService.addListener(this, HorizonsShipSelectedEvent.class, event -> {
            fuelreserve.setMax(getShip().map(Ship::getMaxFuelReserve).orElse(0.0D));
            Double maxFuelReserve = this.getShip().map(Ship::getMaxFuelReserve).orElse(0D);
            int maxFuel = this.getShip().map(Ship::getMaxFuel).orElse(0D).intValue();
            int maxCargo = this.getShip().map(Ship::getMaxCargo).orElse(0D).intValue();
            int maxPassenger = this.getShip().map(Ship::getMaxPassenger).orElse(0D).intValue();
            fuel.setMaxValue(maxFuel);
            fuelreserve.setMax(maxFuelReserve);
            cargo.setMaxValue(maxCargo + maxPassenger);
            fuel.setValue(getShip().map(Ship::getCurrentFuel).orElse(0D).intValue());
            fuelreserve.setValue(getShip().map(Ship::getCurrentFuelReserve).orElse(0D));
            cargo.setValue(getShip().map(Ship::getCurrentCargo).orElse(0D).intValue());
            initPowerBox();
            this.getShip().ifPresent(ship -> {
                updatePower(ship.getCargoHatch().getShipModule());
            });
            this.live.setSelected(this.live.isSelected() && isCurrentShip());
            this.live.setDisable(!isCurrentShip());
        }));
        eventListeners.add(EventService.addListener(this, StatusEvent.class, event -> {
            if(ApplicationState.getInstance().isLiveStats()) {
                fuel.setValue(event.getFuelCapacity().intValue());
                fuelreserve.setValue(event.getFuelReserve().doubleValue());
                cargo.setValue(event.getCargoCapacity().intValue());
                systemPipSelect.setValue(event.getSystemPips());
                enginePipSelect.setValue(event.getEnginePips());
                weaponPipSelect.setValue(event.getWeaponPips());
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
            fuelLabel.textProperty().bind(LocaleService.getStringBinding("ship.stats.config.fuel", maxFuel));
        }
        if (cargo.getMaxValue() != maxCargo + maxPassenger) {
            cargo.setMaxValue(maxCargo + maxPassenger);
            cargoLabel.textProperty().bind(LocaleService.getStringBinding("ship.stats.config.cargo", maxCargo, maxPassenger));
        }
    }
}
