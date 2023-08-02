package nl.jixxed.eliteodysseymaterials.domain.ships;

import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint.PulseLaser;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.CargoRack;
import nl.jixxed.eliteodysseymaterials.domain.ships.special.FuelTank;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ship {
    public static final Ship ADDER = new Ship(
            ShipType.ADDER,
            Map.ofEntries(
//                    Map.entry(HorizonsModifier.TOPSPD,220),
//                    Map.entry(HorizonsModifier.BSTSPD,320),
//                    Map.entry(HorizonsModifier.MNV,4),
//                    Map.entry(HorizonsModifier.SHIELDS,60),
//                    Map.entry(HorizonsModifier.ARMOUR,90),
//                    Map.entry(HorizonsModifier.MASS,35),
//                    Map.entry(HorizonsModifier.FWDACC,39.41),
//                    Map.entry(HorizonsModifier.REVACC,27.73),
//                    Map.entry(HorizonsModifier.LATACC,27.86),
//                    Map.entry(HorizonsModifier.MINTHRUST,45.454),
//                    Map.entry(HorizonsModifier.BOOSTCOST,8),
//                    Map.entry(HorizonsModifier.PITCH,38),
//                    Map.entry(HorizonsModifier.YAW,14),
//                    Map.entry(HorizonsModifier.ROLL,100),
//                    Map.entry(HorizonsModifier.PITCHACC,200),
//                    Map.entry(HorizonsModifier.YAWACC,100),
//                    Map.entry(HorizonsModifier.ROLLACC,220),
//                    Map.entry(HorizonsModifier.MINPITCH,30),
//                    Map.entry(HorizonsModifier.HEATCAP,170),
//                    Map.entry(HorizonsModifier.HEATDISMIN,1.45),
//                    Map.entry(HorizonsModifier.HEATDISMAX,22.60),
//                    Map.entry(HorizonsModifier.FUELCOST,50),
//                    Map.entry(HorizonsModifier.FUELRESERVE,0.36),
//                    Map.entry(HorizonsModifier.HARDNESS,35),
//                    Map.entry(HorizonsModifier.MASSLOCK,7),
//                    Map.entry(HorizonsModifier.CREW,2)
            ),
            List.of(
                    Slot.builder().slotType(SlotType.HARDPOINT).index(0).slotSize(2).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(1).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build(),
                    Slot.builder().slotType(SlotType.HARDPOINT).index(2).slotSize(1).shipModule(PulseLaser.PULSE_LASER_1_F_F).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.UTILITY).index(0).slotSize(0).build(),
                    Slot.builder().slotType(SlotType.UTILITY).index(1).slotSize(0).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(1).shipModule(Armour.ADDER_ARMOUR_GRADE_1).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(3).shipModule(PowerPlant.POWER_PLANT_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(3).shipModule(Thrusters.THRUSTERS_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(3).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(1).shipModule(LifeSupport.LIFE_SUPPORT_1_E).build(),
                    Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(2).shipModule(PowerDistributor.POWER_DISTRIBUTOR_2_E).build(),
                    Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(3).shipModule(Sensors.SENSORS_3_E).build(),
                    Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(3).shipModule(FuelTank.FUEL_TANK_3_C).build()
            ),
            List.of(
                    Slot.builder().slotType(SlotType.OPTIONAL).index(0).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(1).slotSize(3).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(2).slotSize(2).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(3).slotSize(2).shipModule(CargoRack.CARGO_RACK_2_E).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(4).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(5).slotSize(1).build(),
                    Slot.builder().slotType(SlotType.OPTIONAL).index(6).slotSize(1).build()
            ));
    @Getter
    private final ShipType shipType;
    @Getter
    private final List<Slot> hardpointSlots;
    @Getter
    private final List<Slot> utilitySlots;
    @Getter
    private final List<Slot> coreSlots;
    @Getter
    private final List<Slot> optionalSlots;

    @Getter
    private final Map<HorizonsModifier, Object> attributes = new HashMap<>();
    @Getter
    @Setter
    private double currentFuel;
    @Getter
    @Setter
    private double currentCargo = 0D;

    private Ship(final ShipType shipType, final Map<HorizonsModifier, Object> attributes, final List<Slot> hardpointSlots, final List<Slot> utilitySlots, final List<Slot> coreSlots, final List<Slot> optionalSlots) {
        this.shipType = shipType;
        this.attributes.putAll(attributes);
        this.hardpointSlots = new ArrayList<>(hardpointSlots);
        this.utilitySlots = new ArrayList<>(utilitySlots);
        this.coreSlots = new ArrayList<>(coreSlots);
        this.optionalSlots = new ArrayList<>(optionalSlots);

        this.currentFuel = this.getCoreSlots().stream()
                .filter(slot -> slot.getSlotType() == SlotType.CORE_FUEL_TANK)
                .findFirst().map(slot -> (double) slot.getShipModule().getAttributes().get(HorizonsModifier.FUEL_CAPACITY))
                .orElse(0D);
    }

    public Ship(final Ship ship) {
        this.shipType = ship.shipType;
        this.hardpointSlots = new ArrayList<>(ship.hardpointSlots.stream().map(Slot::new).toList());
        this.utilitySlots = new ArrayList<>(ship.utilitySlots.stream().map(Slot::new).toList());
        this.coreSlots = new ArrayList<>(ship.coreSlots.stream().map(Slot::new).toList());
        this.optionalSlots = new ArrayList<>(ship.optionalSlots.stream().map(Slot::new).toList());
    }

    public double getMaxFuel() {
        final Double coreFuel = this.getCoreSlots().stream()
                .filter(slot -> slot.getSlotType() == SlotType.CORE_FUEL_TANK)
                .findFirst().map(slot -> (double) slot.getShipModule().getAttributes().get(HorizonsModifier.FUEL_CAPACITY))
                .orElse(0D);
        final Double optionalFuel = this.getOptionalSlots().stream()
                .filter(slot -> slot.getShipModule() instanceof FuelTank)
                .map(slot -> (double) slot.getShipModule().getAttributes().get(HorizonsModifier.FUEL_CAPACITY))
                .mapToDouble(Double::doubleValue)
                .sum();
        return coreFuel + optionalFuel;
    }
    public double getMaxCargo() {
        return this.getOptionalSlots().stream()
                .filter(slot -> slot.getShipModule() instanceof CargoRack)
                .map(slot -> (double) slot.getShipModule().getAttributes().get(HorizonsModifier.CARGO_CAPACITY))
                .mapToDouble(Double::doubleValue)
                .sum();
    }
    public double getMass() {
        return this.getOptionalSlots().stream()
                .filter(slot -> slot.getShipModule() instanceof CargoRack)
                .map(slot -> (double) slot.getShipModule().getAttributes().get(HorizonsModifier.CARGO_CAPACITY))
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
