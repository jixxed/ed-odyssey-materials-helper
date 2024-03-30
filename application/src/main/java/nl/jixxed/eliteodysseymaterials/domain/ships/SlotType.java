package nl.jixxed.eliteodysseymaterials.domain.ships;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.special.CargoHatch;
import nl.jixxed.eliteodysseymaterials.domain.ships.special.FuelTank;

@RequiredArgsConstructor
public enum SlotType {
    CARGO_HATCH(CargoHatch.class, false),
    HARDPOINT(HardpointModule.class, false),
    UTILITY(UtilityModule.class, false),
    CORE_ARMOUR(Armour.class, true),
    CORE_POWER_PLANT(PowerPlant.class, true),
    CORE_THRUSTERS(Thrusters.class, true),
    CORE_FRAME_SHIFT_DRIVE(FrameShiftDrive.class, true),
    CORE_LIFE_SUPPORT(LifeSupport.class, true),
    CORE_POWER_DISTRIBUTION(PowerDistributor.class, true),
    CORE_SENSORS(Sensors.class, true),
    CORE_FUEL_TANK(FuelTank.class, true),
    OPTIONAL(OptionalModule.class, false),
    MILITARY(MilitaryOptionalModule.class, false);
    @Getter
    private final Class<? extends ShipModule> moduleClass;
    @Getter
    private final boolean core;
}
