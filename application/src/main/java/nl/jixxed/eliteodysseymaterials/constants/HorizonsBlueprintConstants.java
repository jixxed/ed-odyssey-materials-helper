package nl.jixxed.eliteodysseymaterials.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.horizons.EngineerBlueprints;
import nl.jixxed.eliteodysseymaterials.constants.horizons.ExperimentalEffectBlueprints;
import nl.jixxed.eliteodysseymaterials.constants.horizons.SynthesisBlueprints;
import nl.jixxed.eliteodysseymaterials.constants.horizons.TechbrokerBlueprints;
import nl.jixxed.eliteodysseymaterials.constants.horizons.core_internals.*;
import nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints.*;
import nl.jixxed.eliteodysseymaterials.constants.horizons.optional_internals.*;
import nl.jixxed.eliteodysseymaterials.constants.horizons.utilitymounts.*;
import nl.jixxed.eliteodysseymaterials.domain.Blueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsEngineerBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("java:S1192")
public abstract class HorizonsBlueprintConstants {

    private static final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>> HARDPOINT_BLUEPRINTS = new EnumMap<>(HorizonsBlueprintName.class);
    private static final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>> UTILITY_MOUNT_BLUEPRINTS = new EnumMap<>(HorizonsBlueprintName.class);
    private static final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>> CORE_INTERNAL_BLUEPRINTS = new EnumMap<>(HorizonsBlueprintName.class);
    private static final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>> OPTIONAL_INTERNAL_BLUEPRINTS = new EnumMap<>(HorizonsBlueprintName.class);
    private static final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, HorizonsBlueprint>> EXPERIMENTAL_EFFECTS = new EnumMap<>(HorizonsBlueprintName.class);
    private static final Map<HorizonsBlueprintName, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> SYNTHESIS = new EnumMap<>(HorizonsBlueprintName.class);
    private static final Map<HorizonsBlueprintName, HorizonsEngineerBlueprint> ENGINEER_UNLOCK_REQUIREMENTS = new EnumMap<>(HorizonsBlueprintName.class);
    private static final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, HorizonsBlueprint>> TECHBROKER_UNLOCKS = new EnumMap<>(HorizonsBlueprintName.class);

    public static Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>> getHardpointBlueprints() {
        return HARDPOINT_BLUEPRINTS;
    }

    public static Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>> getUtilityMountBlueprints() {
        return UTILITY_MOUNT_BLUEPRINTS;
    }

    public static Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>> getCoreInternalBlueprints() {
        return CORE_INTERNAL_BLUEPRINTS;
    }

    public static Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>> getOptionalInternalBlueprints() {
        return OPTIONAL_INTERNAL_BLUEPRINTS;
    }

    public static Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, HorizonsBlueprint>> getExperimentalEffects() {
        return EXPERIMENTAL_EFFECTS;
    }

    public static Map<HorizonsBlueprintName, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> getSynthesis() {
        return SYNTHESIS;
    }

    public static Map<HorizonsBlueprintName, HorizonsEngineerBlueprint> getEngineerUnlockRequirements() {
        return ENGINEER_UNLOCK_REQUIREMENTS;
    }

    public static Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, HorizonsBlueprint>> getTechbrokerUnlocks() {
        return TECHBROKER_UNLOCKS;
    }

    public static final Map<BlueprintCategory, Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>>> RECIPES =
            Map.of(

                    BlueprintCategory.HARDPOINT, HARDPOINT_BLUEPRINTS,
                    BlueprintCategory.UTILITY_MOUNT, UTILITY_MOUNT_BLUEPRINTS,
                    BlueprintCategory.CORE_INTERNAL, CORE_INTERNAL_BLUEPRINTS,
                    BlueprintCategory.OPTIONAL_INTERNAL, OPTIONAL_INTERNAL_BLUEPRINTS
            );

    public static Blueprint<HorizonsBlueprintName> getRecipeByInternalName(final String internalModuleName, final String internalBlueprintName, final HorizonsBlueprintGrade horizonsBlueprintGrade) {

        final HorizonsBlueprintName horizonsBlueprintName = HorizonsBlueprintName.forInternalName(internalModuleName);
        final HorizonsBlueprintType horizonsBlueprintType = HorizonsBlueprintType.forInternalName(internalBlueprintName);
        return getRecipe(horizonsBlueprintName, horizonsBlueprintType, horizonsBlueprintGrade);

    }

    public static Blueprint<HorizonsBlueprintName> getRecipe(final HorizonsBlueprintName name, final HorizonsBlueprintType horizonsBlueprintType, final HorizonsBlueprintGrade horizonsBlueprintGrade) {

        HorizonsBlueprint recipe = ENGINEER_UNLOCK_REQUIREMENTS.get(name);
        if (recipe != null) {
            return recipe;
        }
        if (horizonsBlueprintType != null) {
            recipe = EXPERIMENTAL_EFFECTS.getOrDefault(name, Collections.emptyMap()).get(horizonsBlueprintType);
            if (recipe != null) {
                return recipe;
            }
            recipe = TECHBROKER_UNLOCKS.getOrDefault(name, Collections.emptyMap()).get(horizonsBlueprintType);
            if (recipe != null) {
                return recipe;
            }
        }
        if (horizonsBlueprintGrade != null) {
            recipe = SYNTHESIS.getOrDefault(name, Collections.emptyMap()).get(horizonsBlueprintGrade);
            if (recipe != null) {
                return recipe;
            }
        }
        if (horizonsBlueprintType != null && horizonsBlueprintGrade != null) {
            recipe = HARDPOINT_BLUEPRINTS.getOrDefault(name, Collections.emptyMap()).getOrDefault(horizonsBlueprintType, Collections.emptyMap()).get(horizonsBlueprintGrade);
            if (recipe != null) {
                return recipe;
            }
            recipe = UTILITY_MOUNT_BLUEPRINTS.getOrDefault(name, Collections.emptyMap()).getOrDefault(horizonsBlueprintType, Collections.emptyMap()).get(horizonsBlueprintGrade);
            if (recipe != null) {
                return recipe;
            }
            recipe = CORE_INTERNAL_BLUEPRINTS.getOrDefault(name, Collections.emptyMap()).getOrDefault(horizonsBlueprintType, Collections.emptyMap()).get(horizonsBlueprintGrade);
            if (recipe != null) {
                return recipe;
            }
            recipe = OPTIONAL_INTERNAL_BLUEPRINTS.getOrDefault(name, Collections.emptyMap()).getOrDefault(horizonsBlueprintType, Collections.emptyMap()).get(horizonsBlueprintGrade);
        }
        if (recipe != null) {
            return recipe;
        }
        final String error = "could not find blueprint for name/type/grade: " + name + "/" + (horizonsBlueprintType != null ? horizonsBlueprintType.name() : "NULL") + "/" + (horizonsBlueprintGrade != null ? horizonsBlueprintGrade.name() : "NULL");
        throw new IllegalArgumentException(error);
    }

    public static HorizonsEngineerBlueprint getEngineerRecipe(final HorizonsBlueprintName name) {
        return ENGINEER_UNLOCK_REQUIREMENTS.get(name);

    }

    private static Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> getBlueprintTypesMap(final HorizonsBlueprintName name) {
        Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> blueprintModificationTypes = HARDPOINT_BLUEPRINTS.getOrDefault(name, Collections.emptyMap());
        if (!blueprintModificationTypes.isEmpty()) {
            return blueprintModificationTypes;
        }
        blueprintModificationTypes = UTILITY_MOUNT_BLUEPRINTS.getOrDefault(name, Collections.emptyMap());
        if (!blueprintModificationTypes.isEmpty()) {
            return blueprintModificationTypes;
        }
        blueprintModificationTypes = CORE_INTERNAL_BLUEPRINTS.getOrDefault(name, Collections.emptyMap());
        if (!blueprintModificationTypes.isEmpty()) {
            return blueprintModificationTypes;
        }
        blueprintModificationTypes = OPTIONAL_INTERNAL_BLUEPRINTS.getOrDefault(name, Collections.emptyMap());
        if (!blueprintModificationTypes.isEmpty()) {
            return blueprintModificationTypes;
        }
        return Collections.emptyMap();
    }

    public static Craftability getCraftability(final HorizonsBlueprintName horizonsBlueprintName, final HorizonsBlueprintType horizonsBlueprintType, final HorizonsBlueprintGrade horizonsBlueprintGrade) {
        return getCraftability(horizonsBlueprintName, horizonsBlueprintType, horizonsBlueprintGrade == null ? Collections.emptyMap() : Map.of(horizonsBlueprintGrade, 1));
    }

    public static Craftability getCraftability(final HorizonsBlueprintName horizonsBlueprintName, final HorizonsBlueprintType horizonsBlueprintType, final Map<HorizonsBlueprintGrade, Integer> horizonsBlueprintGrades) {
        final List<Craftability> craftabilities = horizonsBlueprintGrades.entrySet().stream().map(horizonsBlueprintGradeRolls -> {
            final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(horizonsBlueprintName, horizonsBlueprintType, horizonsBlueprintGradeRolls.getKey());
            final AtomicBoolean hasRaw = new AtomicBoolean(true);
            final AtomicBoolean hasEncoded = new AtomicBoolean(true);
            final AtomicBoolean hasManufactured = new AtomicBoolean(true);
            final AtomicBoolean hasCommodity = new AtomicBoolean(true);
            final Integer numberOfRolls = horizonsBlueprintGradeRolls.getValue();
            blueprint.getMaterialCollection(Raw.class).forEach((material, amountRequired) -> hasRaw.set(hasRaw.get() && (StorageService.getMaterialCount(material) - (amountRequired * numberOfRolls)) >= 0));
            blueprint.getMaterialCollection(Encoded.class).forEach((material, amountRequired) -> hasEncoded.set(hasEncoded.get() && (StorageService.getMaterialCount(material) - (amountRequired * numberOfRolls)) >= 0));
            blueprint.getMaterialCollection(Manufactured.class).forEach((material, amountRequired) -> hasManufactured.set(hasManufactured.get() && (StorageService.getMaterialCount(material) - (amountRequired * numberOfRolls)) >= 0));
            blueprint.getMaterialCollection(Commodity.class).forEach((material, amountRequired) -> hasCommodity.set(hasCommodity.get() && (StorageService.getCommodityCount((Commodity) material, StoragePool.SHIP) - (amountRequired * numberOfRolls)) >= 0));
            if (!hasRaw.get() || !hasEncoded.get() || !hasManufactured.get()) {
                return Craftability.NOT_CRAFTABLE;
            } else if (hasRaw.get() && hasEncoded.get() && hasManufactured.get() && !hasCommodity.get()) {
                return Craftability.CRAFTABLE_WITH_TRADE;
            } else {
                return Craftability.CRAFTABLE;
            }
        }).toList();
        if (craftabilities.stream().allMatch(Craftability.CRAFTABLE::equals)) {
            return Craftability.CRAFTABLE;
        } else if (craftabilities.stream().allMatch(craftability -> Craftability.CRAFTABLE.equals(craftability) || Craftability.CRAFTABLE_WITH_TRADE.equals(craftability))) {
            return Craftability.CRAFTABLE_WITH_TRADE;
        }
        return Craftability.NOT_CRAFTABLE;
    }
    public static int getEngineerMaxGrade(final HorizonsBlueprint horizonsBlueprint1, final Engineer engineer) {
        return RECIPES.values().stream()
                .flatMap(horizonsBlueprintTypeMapMap -> horizonsBlueprintTypeMapMap.values().stream())
                .flatMap(horizonsBlueprintGradeHorizonsBlueprintMap -> horizonsBlueprintGradeHorizonsBlueprintMap.values().stream())
                .flatMap(horizonsBlueprintGradeHorizonsBlueprintMap -> horizonsBlueprintGradeHorizonsBlueprintMap.values().stream())
                .filter(horizonsBlueprint -> horizonsBlueprint.getHorizonsBlueprintName().equals(horizonsBlueprint1.getHorizonsBlueprintName()) && horizonsBlueprint.getHorizonsBlueprintType().equals(horizonsBlueprint1.getHorizonsBlueprintType()) && horizonsBlueprint.getEngineers().contains(engineer))
                .max(Comparator.comparing((HorizonsBlueprint horizonsBlueprint) -> horizonsBlueprint.getHorizonsBlueprintGrade().getGrade()))
                .map(horizonsBlueprint -> horizonsBlueprint.getHorizonsBlueprintGrade().getGrade()).orElse(0);
    }

    public static Set<HorizonsBlueprintType> getBlueprintTypes(final HorizonsBlueprintName name) {
        return getBlueprintTypesMap(name).keySet();
    }


    public static Set<HorizonsBlueprintGrade> getBlueprintGrades(final HorizonsBlueprintName name, final HorizonsBlueprintType horizonsBlueprintType) {
        final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> blueprintModificationTypes = getBlueprintTypesMap(name);
        final Map<HorizonsBlueprintGrade, HorizonsBlueprint> gradeMap = blueprintModificationTypes.getOrDefault(horizonsBlueprintType, Collections.emptyMap());
        return gradeMap.keySet();
    }

    public static Set<HorizonsBlueprintGrade> getSynthesisBlueprintGrades(final HorizonsBlueprintName name) {
        return SYNTHESIS.get(name).keySet();
    }

    public static BlueprintCategory getRecipeCategory(final BlueprintName<HorizonsBlueprintName> recipeName, final boolean isExperimental) {
        BlueprintCategory blueprintCategory = null;
        if (isExperimental) {
            blueprintCategory = BlueprintCategory.EXPERIMENTAL_EFFECTS;
        } else if (ENGINEER_UNLOCK_REQUIREMENTS.entrySet().stream().anyMatch(recipeCategoryMapEntry -> recipeCategoryMapEntry.getKey().equals(recipeName))) {
            blueprintCategory = BlueprintCategory.ENGINEER_UNLOCKS;
        } else if (SYNTHESIS.entrySet().stream().anyMatch(recipeCategoryMapEntry -> recipeCategoryMapEntry.getKey().equals(recipeName))) {
            blueprintCategory = BlueprintCategory.SYNTHESIS;
        } else if (TECHBROKER_UNLOCKS.entrySet().stream().anyMatch(recipeCategoryMapEntry -> recipeCategoryMapEntry.getKey().equals(recipeName))) {
            blueprintCategory = BlueprintCategory.TECHBROKER;
        } else {
            blueprintCategory = RECIPES.entrySet().stream()
                    .filter(recipeCategoryMapEntry -> recipeCategoryMapEntry.getValue().containsKey(recipeName))
                    .findFirst()
                    .map(Map.Entry::getKey)
                    .orElse(null);
        }
        return blueprintCategory;
    }

    static {
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_A, EngineerBlueprints.ENGINEER_A);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_A1, EngineerBlueprints.ENGINEER_A1);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_A2, EngineerBlueprints.ENGINEER_A2);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_A3, EngineerBlueprints.ENGINEER_A3);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_A3A, EngineerBlueprints.ENGINEER_A3A);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_A3B, EngineerBlueprints.ENGINEER_A3B);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_A3C, EngineerBlueprints.ENGINEER_A3C);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_B, EngineerBlueprints.ENGINEER_B);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_B1, EngineerBlueprints.ENGINEER_B1);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_B2, EngineerBlueprints.ENGINEER_B2);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_B2A, EngineerBlueprints.ENGINEER_B2A);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_C, EngineerBlueprints.ENGINEER_C);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_C1, EngineerBlueprints.ENGINEER_C1);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_C1A, EngineerBlueprints.ENGINEER_C1A);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_C1B, EngineerBlueprints.ENGINEER_C1B);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_D, EngineerBlueprints.ENGINEER_D);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_D1, EngineerBlueprints.ENGINEER_D1);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_D2, EngineerBlueprints.ENGINEER_D2);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_D2A, EngineerBlueprints.ENGINEER_D2A);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_D2B, EngineerBlueprints.ENGINEER_D2B);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_E, EngineerBlueprints.ENGINEER_E);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_E1, EngineerBlueprints.ENGINEER_E1);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_E2, EngineerBlueprints.ENGINEER_E2);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_E2A, EngineerBlueprints.ENGINEER_E2A);
        ENGINEER_UNLOCK_REQUIREMENTS.put(HorizonsBlueprintName.ENGINEER_E2B, EngineerBlueprints.ENGINEER_E2B);
        CORE_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintName.ARMOUR, ArmourBlueprints.BLUEPRINTS);
        CORE_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintName.FRAME_SHIFT_DRIVE, FSDBlueprints.BLUEPRINTS);
        CORE_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintName.LIFE_SUPPORT, LifeSupportBlueprints.BLUEPRINTS);
        CORE_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintName.POWER_DISTRIBUTOR, PowerDistributorBlueprints.BLUEPRINTS);
        CORE_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintName.POWER_PLANT, PowerPlantBlueprints.BLUEPRINTS);
        CORE_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintName.SENSORS, SensorBlueprints.BLUEPRINTS);
        CORE_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintName.THRUSTERS, ThrusterBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintName.AX_MISSILE_RACK, MiningLaserBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintName.BEAM_LASER, BeamLaserBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintName.BURST_LASER, BurstLaserBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintName.CANNON, CannonBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintName.FRAGMENT_CANNON, FragmentCannonBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintName.GUARDIAN_GAUSS_CANNON, MiningLaserBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintName.GUARDIAN_PLASMA_CHARGER, MiningLaserBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintName.GUARDIAN_SHARD_CANNON, MiningLaserBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintName.MINE_LAUNCHER, MineLauncherBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintName.MINING_LASER, MiningLaserBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintName.MISSILE_RACK, MissileRackBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintName.MULTI_CANNON, MultiCannonBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintName.PLASMA_ACCELERATOR, PlasmaAcceleratorBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintName.PULSE_LASER, PulseLaserBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintName.RAIL_GUN, RailGunBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintName.TORPEDO_PYLON, TorpedoPylonBlueprints.BLUEPRINTS);
        OPTIONAL_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, AutoFieldMaintenanceUnitBlueprints.BLUEPRINTS);
        OPTIONAL_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintName.COLLECTOR_LIMPET_CONTROLLER, CollectorLimpetControllerBlueprints.BLUEPRINTS);
        OPTIONAL_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, FrameShiftDriveInterdictorBlueprints.BLUEPRINTS);
        OPTIONAL_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintName.FUEL_SCOOP, FuelScoopBlueprints.BLUEPRINTS);
        OPTIONAL_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintName.FUEL_TRANSFER_LIMPET_CONTROLLER, FuelTransferLimpetControllerBlueprints.BLUEPRINTS);
        OPTIONAL_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintName.HATCH_BREAKER_LIMPET_CONTROLLER, HatchBreakerLimpetControllerBlueprints.BLUEPRINTS);
        OPTIONAL_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HullReinforcementBlueprints.BLUEPRINTS);
        OPTIONAL_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, ProspectorLimpetControllerBlueprints.BLUEPRINTS);
        OPTIONAL_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintName.REFINERY, RefineryBlueprints.BLUEPRINTS);
        OPTIONAL_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintName.SHIELD_CELL_BANK, ShieldCellBankBlueprints.BLUEPRINTS);
        OPTIONAL_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintName.SHIELD_GENERATOR, ShieldGeneratorBlueprints.BLUEPRINTS);
        OPTIONAL_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintName.DETAILED_SURFACE_SCANNER, DetailedSurfaceScannerBlueprints.BLUEPRINTS);
        UTILITY_MOUNT_BLUEPRINTS.put(HorizonsBlueprintName.CAUSTIC_SINK_LAUNCHER, CausticSinkLauncherBlueprints.BLUEPRINTS);
        UTILITY_MOUNT_BLUEPRINTS.put(HorizonsBlueprintName.CHAFF_LAUNCHER, ChaffLauncherBlueprints.BLUEPRINTS);
        UTILITY_MOUNT_BLUEPRINTS.put(HorizonsBlueprintName.ELECTRONIC_COUNTERMEASURE, ElectronicCounterMeasureBlueprints.BLUEPRINTS);
        UTILITY_MOUNT_BLUEPRINTS.put(HorizonsBlueprintName.FRAME_SHIFT_WAKE_SCANNER, FrameShiftWakeScannerBlueprints.BLUEPRINTS);
        UTILITY_MOUNT_BLUEPRINTS.put(HorizonsBlueprintName.HEAT_SINK_LAUNCHER, HeatSinkLauncherBlueprints.BLUEPRINTS);
        UTILITY_MOUNT_BLUEPRINTS.put(HorizonsBlueprintName.KILL_WARRANT_SCANNER, KillWarrantScannerBlueprints.BLUEPRINTS);
        UTILITY_MOUNT_BLUEPRINTS.put(HorizonsBlueprintName.MANIFEST_SCANNER, ManifestScannerBlueprints.BLUEPRINTS);
        UTILITY_MOUNT_BLUEPRINTS.put(HorizonsBlueprintName.POINT_DEFENCE, PointDefenceBlueprints.BLUEPRINTS);
        UTILITY_MOUNT_BLUEPRINTS.put(HorizonsBlueprintName.SHIELD_BOOSTER, ShieldBoosterBlueprints.BLUEPRINTS);
        EXPERIMENTAL_EFFECTS.put(HorizonsBlueprintName.BEAM_LASER, ExperimentalEffectBlueprints.BEAM_LASER);
        EXPERIMENTAL_EFFECTS.put(HorizonsBlueprintName.BURST_LASER, ExperimentalEffectBlueprints.BURST_LASER);
        EXPERIMENTAL_EFFECTS.put(HorizonsBlueprintName.PULSE_LASER, ExperimentalEffectBlueprints.PULSE_LASER);
        EXPERIMENTAL_EFFECTS.put(HorizonsBlueprintName.MULTI_CANNON, ExperimentalEffectBlueprints.MULTI_CANNON);
        EXPERIMENTAL_EFFECTS.put(HorizonsBlueprintName.CANNON, ExperimentalEffectBlueprints.CANNON);
        EXPERIMENTAL_EFFECTS.put(HorizonsBlueprintName.FRAGMENT_CANNON, ExperimentalEffectBlueprints.FRAGMENT_CANNON);
        EXPERIMENTAL_EFFECTS.put(HorizonsBlueprintName.GUARDIAN_SHARD_CANNON, ExperimentalEffectBlueprints.GUARDIAN_SHARD_CANNON);
        EXPERIMENTAL_EFFECTS.put(HorizonsBlueprintName.MISSILE_RACK, ExperimentalEffectBlueprints.MISSILE_RACK);
        EXPERIMENTAL_EFFECTS.put(HorizonsBlueprintName.TORPEDO_PYLON, ExperimentalEffectBlueprints.TORPEDO_PYLON);
        EXPERIMENTAL_EFFECTS.put(HorizonsBlueprintName.MINING_LASER, ExperimentalEffectBlueprints.MINING_LASER);
        EXPERIMENTAL_EFFECTS.put(HorizonsBlueprintName.MINE_LAUNCHER, ExperimentalEffectBlueprints.MINE_LAUNCHER);
        EXPERIMENTAL_EFFECTS.put(HorizonsBlueprintName.PLASMA_ACCELERATOR, ExperimentalEffectBlueprints.PLASMA_ACCELERATOR);
        EXPERIMENTAL_EFFECTS.put(HorizonsBlueprintName.RAIL_GUN, ExperimentalEffectBlueprints.RAIL_GUN);
        EXPERIMENTAL_EFFECTS.put(HorizonsBlueprintName.POWER_PLANT, ExperimentalEffectBlueprints.POWER_PLANT);
        EXPERIMENTAL_EFFECTS.put(HorizonsBlueprintName.ARMOUR, ExperimentalEffectBlueprints.ARMOUR);
        EXPERIMENTAL_EFFECTS.put(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, ExperimentalEffectBlueprints.HULL_REINFORCEMENT_PACKAGE);
        EXPERIMENTAL_EFFECTS.put(HorizonsBlueprintName.SHIELD_CELL_BANK, ExperimentalEffectBlueprints.SHIELD_CELL_BANK);
        EXPERIMENTAL_EFFECTS.put(HorizonsBlueprintName.SHIELD_BOOSTER, ExperimentalEffectBlueprints.SHIELD_BOOSTER);
        EXPERIMENTAL_EFFECTS.put(HorizonsBlueprintName.SHIELD_GENERATOR, ExperimentalEffectBlueprints.SHIELD_GENERATOR);
        EXPERIMENTAL_EFFECTS.put(HorizonsBlueprintName.FRAME_SHIFT_DRIVE, ExperimentalEffectBlueprints.FRAME_SHIFT_DRIVE);
        EXPERIMENTAL_EFFECTS.put(HorizonsBlueprintName.THRUSTERS, ExperimentalEffectBlueprints.THRUSTERS);
        EXPERIMENTAL_EFFECTS.put(HorizonsBlueprintName.POWER_DISTRIBUTOR, ExperimentalEffectBlueprints.POWER_DISTRIBUTOR);
        SYNTHESIS.put(HorizonsBlueprintName.AFM_REFILL, SynthesisBlueprints.AFM_REFILL);
        SYNTHESIS.put(HorizonsBlueprintName.AX_EXPLOSIVE_MUNITIONS, SynthesisBlueprints.AX_EXPLOSIVE_MUNITIONS);
        SYNTHESIS.put(HorizonsBlueprintName.AX_REMOTE_FLAK_MUNITIONS, SynthesisBlueprints.AX_REMOTE_FLAK_MUNITIONS);
        SYNTHESIS.put(HorizonsBlueprintName.AX_SMALL_CALIBRE_MUNITIONS, SynthesisBlueprints.AX_SMALL_CALIBRE_MUNITIONS);
        SYNTHESIS.put(HorizonsBlueprintName.CHAFF, SynthesisBlueprints.CHAFF);
        SYNTHESIS.put(HorizonsBlueprintName.CONFIGURABLE_EXPLOSIVE_MUNITIONS, SynthesisBlueprints.CONFIGURABLE_EXPLOSIVE_MUNITIONS);
        SYNTHESIS.put(HorizonsBlueprintName.CONFIGURABLE_SMALL_CALIBRE_MUNITIONS, SynthesisBlueprints.CONFIGURABLE_SMALL_CALIBRE_MUNITIONS);
        SYNTHESIS.put(HorizonsBlueprintName.ENZYME_MISSILE_LAUNCHER_MUNITIONS, SynthesisBlueprints.ENZYME_MISSILE_LAUNCHER_MUNITIONS);
        SYNTHESIS.put(HorizonsBlueprintName.EXPLOSIVE_MUNITIONS, SynthesisBlueprints.EXPLOSIVE_MUNITIONS);
        SYNTHESIS.put(HorizonsBlueprintName.FLECHETTE_LAUNCHER_MUNITIONS, SynthesisBlueprints.FLECHETTE_LAUNCHER_MUNITIONS);
        SYNTHESIS.put(HorizonsBlueprintName.FSD_INJECTION, SynthesisBlueprints.FSD_INJECTION);
        SYNTHESIS.put(HorizonsBlueprintName.GUARDIAN_GAUSS_CANNON_MUNITIONS, SynthesisBlueprints.GUARDIAN_GAUSS_CANNON_MUNITIONS);
        SYNTHESIS.put(HorizonsBlueprintName.GUARDIAN_PLASMA_CHARGER_MUNITIONS, SynthesisBlueprints.GUARDIAN_PLASMA_CHARGER_MUNITIONS);
        SYNTHESIS.put(HorizonsBlueprintName.GUARDIAN_SHARD_CANNON_MUNITIONS, SynthesisBlueprints.GUARDIAN_SHARD_CANNON_MUNITIONS);
        SYNTHESIS.put(HorizonsBlueprintName.HEATSINKS, SynthesisBlueprints.HEATSINKS);
        SYNTHESIS.put(HorizonsBlueprintName.HIGH_VELOCITY_MUNITIONS, SynthesisBlueprints.HIGH_VELOCITY_MUNITIONS);
        SYNTHESIS.put(HorizonsBlueprintName.LARGE_CALIBRE_MUNITIONS, SynthesisBlueprints.LARGE_CALIBRE_MUNITIONS);
        SYNTHESIS.put(HorizonsBlueprintName.LIFE_SUPPORT_SYNTHESIS, SynthesisBlueprints.LIFE_SUPPORT_SYNTHESIS);
        SYNTHESIS.put(HorizonsBlueprintName.LIMPETS, SynthesisBlueprints.LIMPETS);
        SYNTHESIS.put(HorizonsBlueprintName.PLASMA_MUNITIONS, SynthesisBlueprints.PLASMA_MUNITIONS);
        SYNTHESIS.put(HorizonsBlueprintName.SEISMIC_CHARGE_MUNITIONS, SynthesisBlueprints.SEISMIC_CHARGE_MUNITIONS);
        SYNTHESIS.put(HorizonsBlueprintName.SHOCK_CANNON_MUNITIONS, SynthesisBlueprints.SHOCK_CANNON_MUNITIONS);
        SYNTHESIS.put(HorizonsBlueprintName.SMALL_CALIBRE_MUNITIONS, SynthesisBlueprints.SMALL_CALIBRE_MUNITIONS);
        SYNTHESIS.put(HorizonsBlueprintName.CAUSTIC_SINKS, SynthesisBlueprints.CAUSTIC_SINKS);
        SYNTHESIS.put(HorizonsBlueprintName.SRV_AMMO_RESTOCK, SynthesisBlueprints.SRV_AMMO_RESTOCK);
        SYNTHESIS.put(HorizonsBlueprintName.SRV_REFUEL, SynthesisBlueprints.SRV_REFUEL);
        SYNTHESIS.put(HorizonsBlueprintName.SRV_REPAIR, SynthesisBlueprints.SRV_REPAIR);
        SYNTHESIS.put(HorizonsBlueprintName.SUB_SURFACE_DISPLACEMENT_MUNITIONS, SynthesisBlueprints.SUB_SURFACE_DISPLACEMENT_MUNITIONS);
        TECHBROKER_UNLOCKS.put(HorizonsBlueprintName.HUMAN_MODULES, TechbrokerBlueprints.HUMAN_MODULES);
        TECHBROKER_UNLOCKS.put(HorizonsBlueprintName.HUMAN_WEAPONS, TechbrokerBlueprints.HUMAN_WEAPONS);
        TECHBROKER_UNLOCKS.put(HorizonsBlueprintName.HUMAN_LIVERY, TechbrokerBlueprints.HUMAN_LIVERY);
        TECHBROKER_UNLOCKS.put(HorizonsBlueprintName.GUARDIAN_MODULES, TechbrokerBlueprints.GUARDIAN_MODULES);
        TECHBROKER_UNLOCKS.put(HorizonsBlueprintName.GUARDIAN_WEAPONS, TechbrokerBlueprints.GUARDIAN_WEAPONS);
        TECHBROKER_UNLOCKS.put(HorizonsBlueprintName.GUARDIAN_FIGHTERS, TechbrokerBlueprints.GUARDIAN_FIGHTERS);
    }

    public static Map<HorizonsBlueprint, Integer> findRecipesContaining(final HorizonsMaterial horizonsMaterial) {
        final Map<HorizonsBlueprint, Integer> newMap = new HashMap<>();
        ENGINEER_UNLOCK_REQUIREMENTS.values()
                .stream()
                .filter(horizonsBlueprint -> horizonsBlueprint.getMaterialCollection(horizonsMaterial.getClass()).containsKey(horizonsMaterial))
                .forEach(horizonsBlueprint -> newMap.put(horizonsBlueprint, horizonsBlueprint.getMaterialCollection(horizonsMaterial.getClass()).get(horizonsMaterial)));
        RECIPES.values()
                .stream()
                .flatMap(horizonsBlueprintNameMap -> horizonsBlueprintNameMap.values().stream())
                .flatMap(horizonsBlueprintTypesMap -> horizonsBlueprintTypesMap.values().stream())
                .flatMap(horizonsBlueprintGradesMap -> horizonsBlueprintGradesMap.values().stream())
                .filter(horizonsBlueprint -> horizonsBlueprint.getMaterialCollection(horizonsMaterial.getClass()).containsKey(horizonsMaterial))
                .forEach(horizonsBlueprint -> newMap.put(horizonsBlueprint, horizonsBlueprint.getMaterialCollection(horizonsMaterial.getClass()).get(horizonsMaterial)));
        EXPERIMENTAL_EFFECTS.values()
                .stream()
                .flatMap(horizonsBlueprintNameMap -> horizonsBlueprintNameMap.values().stream())
                .filter(horizonsBlueprint -> horizonsBlueprint.getMaterialCollection(horizonsMaterial.getClass()).containsKey(horizonsMaterial))
                .forEach(horizonsBlueprint -> newMap.put(horizonsBlueprint, horizonsBlueprint.getMaterialCollection(horizonsMaterial.getClass()).get(horizonsMaterial)));
        SYNTHESIS.values()
                .stream()
                .flatMap(horizonsBlueprintNameMap -> horizonsBlueprintNameMap.values().stream())
                .filter(horizonsBlueprint -> horizonsBlueprint.getMaterialCollection(horizonsMaterial.getClass()).containsKey(horizonsMaterial))
                .forEach(horizonsBlueprint -> newMap.put(horizonsBlueprint, horizonsBlueprint.getMaterialCollection(horizonsMaterial.getClass()).get(horizonsMaterial)));
        TECHBROKER_UNLOCKS.values()
                .stream()
                .flatMap(horizonsBlueprintNameMap -> horizonsBlueprintNameMap.values().stream())
                .filter(horizonsBlueprint -> horizonsBlueprint.getMaterialCollection(horizonsMaterial.getClass()).containsKey(horizonsMaterial))
                .forEach(horizonsBlueprint -> newMap.put(horizonsBlueprint, horizonsBlueprint.getMaterialCollection(horizonsMaterial.getClass()).get(horizonsMaterial)));
        return newMap;
    }
}