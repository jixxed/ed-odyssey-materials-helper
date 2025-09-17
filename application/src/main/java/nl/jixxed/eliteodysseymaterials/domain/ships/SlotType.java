package nl.jixxed.eliteodysseymaterials.domain.ships;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint.MiningLaser;
import nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint.MiningVollyRepeater;
import nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint.SeismicChargeLauncher;
import nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint.SubSurfaceDisplacementMissile;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.special.CargoHatch;
import nl.jixxed.eliteodysseymaterials.domain.ships.special.FuelTank;

import java.util.List;

@RequiredArgsConstructor
public enum SlotType {
    CARGO_HATCH(List.of(CargoHatch.class), false),
    HARDPOINT(List.of(HardpointModule.class), false),
    MINING_HARDPOINT(List.of(MiningLaser.class, SeismicChargeLauncher.class, SubSurfaceDisplacementMissile.class, MiningVollyRepeater.class), false),
    UTILITY(List.of(UtilityModule.class), false),
    CORE_ARMOUR(List.of(Armour.class), true),
    CORE_POWER_PLANT(List.of(PowerPlant.class), true),
    CORE_THRUSTERS(List.of(Thrusters.class), true),
    CORE_FRAME_SHIFT_DRIVE(List.of(FrameShiftDrive.class), true),
    CORE_LIFE_SUPPORT(List.of(LifeSupport.class), true),
    CORE_POWER_DISTRIBUTION(List.of(PowerDistributor.class), true),
    CORE_SENSORS(List.of(Sensors.class), true),
    CORE_FUEL_TANK(List.of(FuelTank.class), true),
    OPTIONAL(List.of(OptionalModule.class), false),
    MILITARY(List.of(MilitaryOptionalModule.class), false),
    SLF(List.of(FighterHangar.class), false),
    LIMPET(List.of(
            ProspectorLimpetController.class,
            CollectorLimpetController.class,
            ReconLimpetController.class,
            RepairLimpetController.class,
            DecontaminationLimpetController.class,
            HatchBreakerLimpetController.class,
            FuelTransferLimpetController.class,
            ResearchLimpetController.class,
            MultiLimpetController.class,
            LimpetOptionalModule.class), false),
    CARGO(List.of(CargoOptionalModule.class, CargoRack.class, AntiCorrosionCargoRack.class, FuelTank.class), false);
    @Getter
    private final List<Class<? extends ShipModule>> moduleClasses;
    @Getter
    private final boolean core;
}
