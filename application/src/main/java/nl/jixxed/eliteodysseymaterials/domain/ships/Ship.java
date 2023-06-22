package nl.jixxed.eliteodysseymaterials.domain.ships;

import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint.PulseLaser;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.CargoRack;
import nl.jixxed.eliteodysseymaterials.domain.ships.special.FuelTank;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    public static final Ship ADDER = new Ship(
            ShipType.ADDER,
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
    @Setter
    private double currentFuel = this.getCoreSlots().stream()
            .filter(slot -> slot.getSlotType() == SlotType.CORE_FUEL_TANK)
            .findFirst().map(slot -> (double) slot.getShipModule().getAttributes().get(HorizonsModifier.FUEL_CAPACITY))
            .orElse(0D);
    @Getter
    @Setter
    private double currentCargo = 0D;

    private Ship(final ShipType shipType, final List<Slot> hardpointSlots, final List<Slot> utilitySlots, final List<Slot> coreSlots, final List<Slot> optionalSlots) {
        this.shipType = shipType;
        this.hardpointSlots = new ArrayList<>(hardpointSlots);
        this.utilitySlots = new ArrayList<>(utilitySlots);
        this.coreSlots = new ArrayList<>(coreSlots);
        this.optionalSlots = new ArrayList<>(optionalSlots);
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
