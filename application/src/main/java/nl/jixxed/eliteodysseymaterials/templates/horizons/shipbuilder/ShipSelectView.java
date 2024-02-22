package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfigurations;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.FrameShiftDriveBooster;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsShipSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipMapper;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

import java.util.List;
import java.util.stream.Collectors;

public class ShipSelectView extends VBox implements Template {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private HorizonsShipBuilderTab tab;


    public ShipSelectView(HorizonsShipBuilderTab tab) {
        this.tab = tab;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        final List<HBox> shipRows = Ship.ALL.stream().map(ship1 -> {
            final DestroyableLabel shipName = LabelBuilder.builder().withStyleClass("shipbuilder-ship-col-name").withText(LocaleService.getStringBinding(ship1.getShipType().getLocalizationKey())).build();
            final DestroyableLabel price = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-price").withNonLocalizedText(Formatters.NUMBER_FORMAT_0.format(ship1.getRetailPrice())).build();
            final DestroyableLabel size = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-size").withText(LocaleService.getStringBinding(ship1.getShipType().getShipSize().getLocalizationKey())).build();
            final DestroyableLabel crew = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-crew").withNonLocalizedText(Formatters.NUMBER_FORMAT_0.format(ship1.getAttributes().get(HorizonsModifier.CREW))).build();
            final DestroyableLabel masslock = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-masslock").withNonLocalizedText(Formatters.NUMBER_FORMAT_0.format(ship1.getAttributes().get(HorizonsModifier.MASS_LOCK))).build();
            final DestroyableLabel mass = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-mass").withNonLocalizedText(Formatters.NUMBER_FORMAT_0.format(ship1.getAttributes().get(HorizonsModifier.MASS))).build();
            final DestroyableLabel jumprange = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-jumprange").withText(LocaleService.getStringBinding("ship.stats.jumprange.value", Formatters.NUMBER_FORMAT_2.format(calculateJumpRangeMax(ship1)))).build();
            final DestroyableLabel speed = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-speed").withNonLocalizedText(Formatters.NUMBER_FORMAT_0.format(ship1.getAttributes().get(HorizonsModifier.TOP_SPEED))).build();
            final DestroyableLabel boost = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-boost").withNonLocalizedText(Formatters.NUMBER_FORMAT_0.format(ship1.getAttributes().get(HorizonsModifier.BOOST_SPEED))).build();
            final DestroyableLabel shield = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-shield").withNonLocalizedText(Formatters.NUMBER_FORMAT_0.format(ship1.getAttributes().get(HorizonsModifier.SHIELDS))).build();
            final DestroyableLabel armor = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-armor").withNonLocalizedText(Formatters.NUMBER_FORMAT_0.format(ship1.getAttributes().get(HorizonsModifier.ARMOUR))).build();
            final DestroyableLabel hardness = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-hardness").withNonLocalizedText(Formatters.NUMBER_FORMAT_0.format(ship1.getAttributes().get(HorizonsModifier.ARMOUR_HARDNESS))).build();
            final DestroyableLabel fuel = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-fuel").withNonLocalizedText(Formatters.NUMBER_FORMAT_0.format(ship1.getMaxFuel())).build();
            final DestroyableLabel cargo = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-cargo").withNonLocalizedText(Formatters.NUMBER_FORMAT_0.format(ship1.getMaxCargo())).build();
            final DestroyableLabel passenger = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-passenger").withNonLocalizedText(Formatters.NUMBER_FORMAT_0.format(ship1.getMaxPassenger())).build();
            final DestroyableLabel slotHardpoints = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-slothardpoints").withNonLocalizedText(ship1.getHardpointSlots().stream().map(Slot::getSlotSizeName).collect(Collectors.joining(" "))).build();
            final DestroyableLabel slotUltility = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-slotultility").withNonLocalizedText(Formatters.NUMBER_FORMAT_0.format(ship1.getUtilitySlots().size())).build();
            final DestroyableLabel slotPowerplant = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-slotpowerplant").withNonLocalizedText(Formatters.NUMBER_FORMAT_0.format(ship1.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_POWER_PLANT)).findFirst().map(Slot::getSlotSize).orElse(0))).build();
            final DestroyableLabel slotThrusters = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-slotthrusters").withNonLocalizedText(Formatters.NUMBER_FORMAT_0.format(ship1.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_THRUSTERS)).findFirst().map(Slot::getSlotSize).orElse(0))).build();
            final DestroyableLabel slotFrameshiftdrive = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-slotframeshiftdrive").withNonLocalizedText(Formatters.NUMBER_FORMAT_0.format(ship1.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_FRAME_SHIFT_DRIVE)).findFirst().map(Slot::getSlotSize).orElse(0))).build();
            final DestroyableLabel slotLifesupport = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-slotlifesupport").withNonLocalizedText(Formatters.NUMBER_FORMAT_0.format(ship1.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_LIFE_SUPPORT)).findFirst().map(Slot::getSlotSize).orElse(0))).build();
            final DestroyableLabel slotPowerdistribution = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-slotpowerdistribution").withNonLocalizedText(Formatters.NUMBER_FORMAT_0.format(ship1.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_POWER_DISTRIBUTION)).findFirst().map(Slot::getSlotSize).orElse(0))).build();
            final DestroyableLabel slotSensors = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-slotsensors").withNonLocalizedText(Formatters.NUMBER_FORMAT_0.format(ship1.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_SENSORS)).findFirst().map(Slot::getSlotSize).orElse(0))).build();
            final DestroyableLabel slotFueltank = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-slotfueltank").withNonLocalizedText(Formatters.NUMBER_FORMAT_0.format(ship1.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_FUEL_TANK)).findFirst().map(Slot::getSlotSize).orElse(0))).build();
            final DestroyableLabel slotOptional = LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-slotoptional").withNonLocalizedText(ship1.getOptionalSlots().stream().map(slot-> slot.getSlotType().equals(SlotType.MILITARY) ? "M"+slot.getSlotSizeName() :slot.getSlotSizeName() ).collect(Collectors.joining(" "))).build();
            return BoxBuilder.builder().withStyleClass("shipbuilder-ship-row").withNodes(shipName, price, size, crew, masslock, mass, jumprange, speed, boost, shield, armor, hardness, fuel, cargo, passenger, slotHardpoints, slotUltility, slotPowerplant, slotThrusters, slotFrameshiftdrive, slotLifesupport, slotPowerdistribution, slotSensors, slotFueltank, slotOptional
            ).withOnMouseClicked(event -> {
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                            final ShipConfigurations shipConfigurations = ShipService.getShipConfigurations(commander);
                            shipConfigurations.getSelectedShipConfiguration().ifPresent(configuration -> {
                                APPLICATION_STATE.setShip(new Ship(ship1));
                                ShipMapper.toShipConfiguration(APPLICATION_STATE.getShip(), configuration, tab.getShipSelect().getSelectionModel().getSelectedItem().getName());
                                ShipService.saveShipConfigurations(commander, shipConfigurations);
                                EventService.publish(new HorizonsShipSelectedEvent(configuration.getUuid()));
                            });
                        }
                );
            }).buildHBox();
        }).toList();
        HBox header = BoxBuilder.builder().withStyleClass("shipbuilder-ship-header").withNodes(
                LabelBuilder.builder().withStyleClass("shipbuilder-ship-col-name").withText(LocaleService.getStringBinding("ship.view.header.ship.name")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-price").withText(LocaleService.getStringBinding("ship.view.header.price")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-size").withText(LocaleService.getStringBinding("ship.view.header.size")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-crew").withText(LocaleService.getStringBinding("ship.view.header.crew")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-masslock").withText(LocaleService.getStringBinding("ship.view.header.masslock")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-mass").withText(LocaleService.getStringBinding("ship.view.header.mass")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-jumprange").withText(LocaleService.getStringBinding("ship.view.header.jumprange")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-speed").withText(LocaleService.getStringBinding("ship.view.header.speed")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-boost").withText(LocaleService.getStringBinding("ship.view.header.boost")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-shield").withText(LocaleService.getStringBinding("ship.view.header.shield")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-armor").withText(LocaleService.getStringBinding("ship.view.header.armor")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-hardness").withText(LocaleService.getStringBinding("ship.view.header.hardness")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-fuel").withText(LocaleService.getStringBinding("ship.view.header.fuel")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-cargo").withText(LocaleService.getStringBinding("ship.view.header.cargo")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-passenger").withText(LocaleService.getStringBinding("ship.view.header.passenger")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-slothardpoints").withText(LocaleService.getStringBinding("ship.view.header.slot.hardpoints")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-slotultility").withText(LocaleService.getStringBinding("ship.view.header.slot.utility")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-slotpowerplant").withText(LocaleService.getStringBinding("ship.view.header.slot.powerplant")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-slotthrusters").withText(LocaleService.getStringBinding("ship.view.header.slot.thrusters")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-slotframeshiftdrive").withText(LocaleService.getStringBinding("ship.view.header.slot.frameshiftdrive")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-slotlifesupport").withText(LocaleService.getStringBinding("ship.view.header.slot.lifesupport")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-slotpowerdistribution").withText(LocaleService.getStringBinding("ship.view.header.slot.powerdistribution")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-slotsensors").withText(LocaleService.getStringBinding("ship.view.header.slot.sensors")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-slotfueltank").withText(LocaleService.getStringBinding("ship.view.header.slot.fueltank")).build(),
                LabelBuilder.builder().withStyleClasses("shipbuilder-ship-col", "shipbuilder-ship-col-slotoptional").withText(LocaleService.getStringBinding("ship.view.header.slot.optional")).build()
        ).buildHBox();
        this.getChildren().addAll(shipRows.toArray(HBox[]::new));
        this.getChildren().add(0, header);
    }

    @Override
    public void initEventHandling() {

    }

    public double calculateJumpRangeMax(Ship ship) {
            final double fuel = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D)).orElse(1D);
            return calculateJumpRange(ship, ship.getEmptyMass() + fuel, fuel);
    }

    public double calculateJumpRange(Ship ship, final double mass, final double fuel) {
            final double maxFuelPerJump = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D)).orElse(1D);
            final double optimisedMass = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FSD_OPTIMISED_MASS, 1D)).orElse(1D);
            final double fuelMultiplier = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FUEL_MULTIPLIER, 1D)).orElse(1D);
            final double fuelPower = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FUEL_POWER, 1D)).orElse(1D);
            final double jumpRangeIncrease = ship.getOptionalSlots().stream().filter(slot -> slot.getShipModule() instanceof FrameShiftDriveBooster).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.JUMP_RANGE_INCREASE, 1D)).orElse(0D);
            return calculateJumpDistance(mass, Math.min(fuel, maxFuelPerJump), optimisedMass, fuelMultiplier, fuelPower, jumpRangeIncrease);
    }

    private double calculateJumpDistance(final double mass, final double fuel, final double optimisedMass, final double fuelMultiplier, final double fuelPower, final double jumpRangeIncrease) {
        return Math.pow(fuel / fuelMultiplier, 1 / fuelPower) * optimisedMass / mass + jumpRangeIncrease;
    }
}
