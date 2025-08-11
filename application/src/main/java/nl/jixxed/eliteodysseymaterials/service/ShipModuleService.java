package nl.jixxed.eliteodysseymaterials.service;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ships.HardpointModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.military.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.special.CargoHatch;
import nl.jixxed.eliteodysseymaterials.domain.ships.special.FuelTank;
import nl.jixxed.eliteodysseymaterials.domain.ships.utility.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;

@Slf4j
public class ShipModuleService {

    private static final List<ShipModule> SHIP_MODULES = new ArrayList<>();

    public static void register(ShipModule module) {
        SHIP_MODULES.add(module);
    }

    static {
        //hardpoint
        var abrasionBlasters = AbrasionBlaster.ABRASION_BLASTERS;
        var aXMissileRacks = AXMissileRack.AX_MISSILE_RACKS;
        var aXMultiCannons = AXMultiCannon.AX_MULTI_CANNONS;
        var beamLasers = BeamLaser.BEAM_LASERS;
        var burstLasers = BurstLaser.BURST_LASERS;
        var cannons = Cannon.CANNONS;
        var enzymeMissileRacks = EnzymeMissileRack.ENZYME_MISSILE_RACKS;
        var fragmentCannons = FragmentCannon.FRAGMENT_CANNONS;
        var guardianGaussCannons = GuardianGaussCannon.GUARDIAN_GAUSS_CANNONS;
        var guardianNaniteTorpedoPylons = GuardianNaniteTorpedoPylon.GUARDIAN_NANITE_TORPEDO_PYLONS;
        var guardianPlasmaChargers = GuardianPlasmaCharger.GUARDIAN_PLASMA_CHARGERS;
        var guardianShardCannons = GuardianShardCannon.GUARDIAN_SHARD_CANNONS;
        var mineLaunchers = MineLauncher.MINE_LAUNCHERS;
        var miningLasers = MiningLaser.MINING_LASERS;
        var missileRacks = MissileRack.MISSILE_RACKS;
        var multiCannons = MultiCannon.MULTI_CANNONS;
        var plasmaAccelerators = PlasmaAccelerator.PLASMA_ACCELERATORS;
        var pulseLasers = PulseLaser.PULSE_LASERS;
        var railGuns = RailGun.RAIL_GUNS;
        var remoteReleaseFlakLaunchers = RemoteReleaseFlakLauncher.REMOTE_RELEASE_FLAK_LAUNCHERS;
        var remoteReleaseFlechetteLaunchers = RemoteReleaseFlechetteLauncher.REMOTE_RELEASE_FLECHETTE_LAUNCHERS;
        var seismicChargeLaunchers = SeismicChargeLauncher.SEISMIC_CHARGE_LAUNCHERS;
        var shockCannons = ShockCannon.SHOCK_CANNONS;
        var subSurfaceDisplacementMissiles = SubSurfaceDisplacementMissile.SUB_SURFACE_DISPLACEMENT_MISSILES;
        var torpedoPylons = TorpedoPylon.TORPEDO_PYLONS;
        //core
        var armours = Armour.ARMOURS;
        var frameShiftDrives = FrameShiftDrive.FRAME_SHIFT_DRIVES;
        var lifeSupports = LifeSupport.LIFE_SUPPORTS;
        var powerDistributors = PowerDistributor.POWER_DISTRIBUTORS;
        var powerPlants = PowerPlant.POWER_PLANTS;
        var sensorss = Sensors.SENSORS;
        var thrusterss = Thrusters.THRUSTERS;
        var fuelTanks = FuelTank.FUEL_TANKS;
        var cargoHatchs = CargoHatch.CARGO_HATCHES;
        //military
        var guardianHullReinforcementPackages = GuardianHullReinforcementPackage.GUARDIAN_HULL_REINFORCEMENT_PACKAGES;
        var guardianModuleReinforcementPackages = GuardianModuleReinforcementPackage.GUARDIAN_MODULE_REINFORCEMENT_PACKAGES;
        var guardianShieldReinforcementPackages = GuardianShieldReinforcementPackage.GUARDIAN_SHIELD_REINFORCEMENT_PACKAGES;
        var hullReinforcementPackages = HullReinforcementPackage.HULL_REINFORCEMENT_PACKAGES;
        var moduleReinforcementPackages = ModuleReinforcementPackage.MODULE_REINFORCEMENT_PACKAGES;
        var shieldCellBanks = ShieldCellBank.SHIELD_CELL_BANKS;
        //optional
        var antiCorrosionCargoRacks = AntiCorrosionCargoRack.ANTI_CORROSION_CARGO_RACKS;
        var autoFieldMaintenanceUnits = AutoFieldMaintenanceUnit.AUTO_FIELD_MAINTENANCE_UNITS;
        var cargoRacks = CargoRack.CARGO_RACKS;
        var largeCargoRacks = LargeCargoRack.LARGE_CARGO_RACKS;
        var collectorLimpetControllers = CollectorLimpetController.COLLECTOR_LIMPET_CONTROLLERS;
        var computers = Computer.COMPUTERS;
        var decontaminationLimpetControllers = DecontaminationLimpetController.DECONTAMINATION_LIMPET_CONTROLLERS;
        var detailedSurfaceScanners = DetailedSurfaceScanner.DETAILED_SURFACE_SCANNERS;
        var experimentalWeaponStabilisers = ExperimentalWeaponStabiliser.EXPERIMENTAL_WEAPON_STABILISERS;
        var fighterHangars = FighterHangar.FIGHTER_HANGARS;
        var frameShiftDriveBoosters = FrameShiftDriveBooster.FRAME_SHIFT_DRIVE_BOOSTERS;
        var frameShiftDriveInterdictors = FrameShiftDriveInterdictor.FRAME_SHIFT_DRIVE_INTERDICTORS;
        var fuelScoops = FuelScoop.FUEL_SCOOPS;
        var fuelTransferLimpetControllers = FuelTransferLimpetController.FUEL_TRANSFER_LIMPET_CONTROLLERS;
        var hatchBreakerLimpetControllers = HatchBreakerLimpetController.HATCH_BREAKER_LIMPET_CONTROLLERS;
        var metaAlloyHullReinforcementPackages = MetaAlloyHullReinforcementPackage.META_ALLOY_HULL_REINFORCEMENT_PACKAGES;
        var multiLimpetControllers = MultiLimpetController.MULTI_LIMPET_CONTROLLERS;
        var passengerCabins = PassengerCabin.PASSENGER_CABINS;
        var planetaryVehicleHangars = PlanetaryVehicleHangar.PLANETARY_VEHICLE_HANGARS;
        var prospectorLimpetControllers = ProspectorLimpetController.PROSPECTOR_LIMPET_CONTROLLERS;
        var reconLimpetControllers = ReconLimpetController.RECON_LIMPET_CONTROLLERS;
        var refinerys = Refinery.REFINERIES;
        var repairLimpetControllers = RepairLimpetController.REPAIR_LIMPET_CONTROLLERS;
        var researchLimpetControllers = ResearchLimpetController.RESEARCH_LIMPET_CONTROLLERS;
        var shieldGenerators = ShieldGenerator.SHIELD_GENERATORS;
        var discoveryScanners = DiscoveryScanner.DISCOVERY_SCANNERS;
        //utility
        var chaffLaunchers = ChaffLauncher.CHAFF_LAUNCHERS;
        var electronicCountermeasures = ElectronicCountermeasure.ELECTRONIC_COUNTERMEASURES;
        var frameShiftWakeScanners = FrameShiftWakeScanner.FRAME_SHIFT_WAKE_SCANNERS;
        var killWarrantScanners = KillWarrantScanner.KILL_WARRANT_SCANNERS;
        var manifestScanners = ManifestScanner.MANIFEST_SCANNERS;
        var pointDefences = PointDefence.POINT_DEFENCES;
        var pulseWaveAnalysers = PulseWaveAnalyser.PULSE_WAVE_ANALYSERS;
        var shieldBoosters = ShieldBooster.SHIELD_BOOSTERS;
        var sinkLaunchers = SinkLauncher.SINK_LAUNCHERS;
        var xenos = Xeno.XENOS;
    }


    public static List<ShipModule> getModules(final SlotType slotType) {
        return SHIP_MODULES.stream().filter(module -> {
            try {
                final Class<? extends ShipModule> aClass = module.getClass();
                // weird predicate double negate wrapping to try and fix:
                // IncompatibleClassChangeError
                // Class java.util.AbstractList$RandomAccessSpliterator does not implement the requested interface java.util.function.Predicate
                return slotType.getModuleClasses().stream().anyMatch(Predicate.not(moduleClass -> !moduleClass.isAssignableFrom(aClass)));
            } catch (Exception ex) {
                log.error("Error filtering modules for slot type: " + slotType, ex);
                return false;
            }
        }).toList();
    }

    public static List<ShipModule> getAllModules() {
        return SHIP_MODULES;
    }

    public static List<ShipModule> getBasicModules() {
        return SHIP_MODULES.stream().filter(not(ShipModule::isPreEngineered).and(not(CargoHatch.class::isInstance))).toList();
    }

    public static ShipModule getModule(String id) {
//        log.info(SHIP_MODULES.size() + " modules found while searching. " + id);
        return SHIP_MODULES.stream().filter(module -> module.getId().equals(id)).findFirst().orElseThrow(IllegalArgumentException::new);
    }

    public static Optional<ShipModule> findHigherSize(ShipModule module) {
        return SHIP_MODULES.stream()
                .filter(shipModule -> shipModule.getName().equals(module.getName()) &&
                        (shipModule instanceof HardpointModule || shipModule.getModuleClass().equals(module.getModuleClass())) &&
                        shipModule.getModuleSize().isHigher(module.getModuleSize())
                )
                .sorted(Comparator.comparing(ShipModule::getModuleSize))
                .findFirst();
    }

    public static Optional<ShipModule> findHighestSize(ShipModule module, int maxSize) {
        return SHIP_MODULES.stream()
                .filter(shipModule -> shipModule.getName().equals(module.getName()) &&
                        (shipModule instanceof HardpointModule || shipModule.getModuleClass().equals(module.getModuleClass())) &&
                        shipModule.getModuleSize().isLowerOrEqual(maxSize)
                )
                .sorted(Comparator.comparing(ShipModule::getModuleSize).reversed())
                .findFirst();
    }

    public static Optional<ShipModule> findLowerSize(ShipModule module) {
        return SHIP_MODULES.stream()
                .filter(shipModule -> shipModule.getName().equals(module.getName()) &&
                        (shipModule instanceof HardpointModule || shipModule.getModuleClass().equals(module.getModuleClass())) &&
                        shipModule.getModuleSize().isLower(module.getModuleSize())
                )
                .sorted(Comparator.comparing(ShipModule::getModuleSize).reversed())
                .findFirst();
    }

    public static Optional<ShipModule> findLowerSize(ShipModule module, int maxSize) {
        return SHIP_MODULES.stream()
                .filter(shipModule -> shipModule.getName().equals(module.getName()) &&
                        (shipModule instanceof HardpointModule || shipModule.getModuleClass().equals(module.getModuleClass())) &&
                        shipModule.getModuleSize().isLowerOrEqual(maxSize)
                )
                .sorted(Comparator.comparing(ShipModule::getModuleSize).reversed())
                .findFirst();
    }

    public static Optional<ShipModule> findHigherClass(ShipModule module) {
        return SHIP_MODULES.stream()
                .filter(shipModule -> shipModule.getName().equals(module.getName()) &&
                        shipModule.getModuleSize().equals(module.getModuleSize()) &&
                        shipModule.getModuleClass().isHigher(module.getModuleClass())
                )
                .sorted(Comparator.comparing(ShipModule::getModuleClass))
                .findFirst();
    }

    public static Optional<ShipModule> findLowerClass(ShipModule module) {
        return SHIP_MODULES.stream()
                .filter(shipModule -> shipModule.getName().equals(module.getName()) &&
                        shipModule.getModuleSize().equals(module.getModuleSize()) &&
                        shipModule.getModuleClass().isLower(module.getModuleClass())
                )
                .sorted(Comparator.comparing(ShipModule::getModuleClass).reversed())
                .findFirst();
    }

}
