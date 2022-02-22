package nl.jixxed.eliteodysseymaterials.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.horizons.core_internals.*;
import nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints.*;
import nl.jixxed.eliteodysseymaterials.constants.horizons.optional_internals.*;
import nl.jixxed.eliteodysseymaterials.constants.horizons.utilitymounts.*;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintCategory;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintModificationType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintObjectName;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("java:S1192")
public abstract class HorizonsBlueprintConstants {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final Map<HorizonsBlueprintObjectName, Map<HorizonsBlueprintModificationType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>> HARDPOINT_BLUEPRINTS = new EnumMap<>(HorizonsBlueprintObjectName.class);
    private static final Map<HorizonsBlueprintObjectName, Map<HorizonsBlueprintModificationType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>> UTILITY_MOUNT_BLUEPRINTS = new EnumMap<>(HorizonsBlueprintObjectName.class);
    private static final Map<HorizonsBlueprintObjectName, Map<HorizonsBlueprintModificationType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>> CORE_INTERNAL_BLUEPRINTS = new EnumMap<>(HorizonsBlueprintObjectName.class);
    private static final Map<HorizonsBlueprintObjectName, Map<HorizonsBlueprintModificationType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>> OPTIONAL_INTERNAL_BLUEPRINTS = new EnumMap<>(HorizonsBlueprintObjectName.class);
    public static final Map<BlueprintCategory, Map<HorizonsBlueprintObjectName, Map<HorizonsBlueprintModificationType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>>> RECIPES =
            Map.of(

                    BlueprintCategory.HARDPOINT, HARDPOINT_BLUEPRINTS,
                    BlueprintCategory.UTILITY_MOUNT, UTILITY_MOUNT_BLUEPRINTS,
                    BlueprintCategory.CORE_INTERNAL, CORE_INTERNAL_BLUEPRINTS,
                    BlueprintCategory.OPTIONAL_INTERNAL, OPTIONAL_INTERNAL_BLUEPRINTS
            );

    public static HorizonsBlueprint getRecipe(final HorizonsBlueprintObjectName name, final HorizonsBlueprintModificationType horizonsBlueprintModificationType, final HorizonsBlueprintGrade horizonsBlueprintGrade) {
        HorizonsBlueprint recipe = HARDPOINT_BLUEPRINTS.getOrDefault(name, Collections.emptyMap()).getOrDefault(horizonsBlueprintModificationType, Collections.emptyMap()).get(horizonsBlueprintGrade);
        if (recipe != null) {
            return recipe;
        }
        recipe = UTILITY_MOUNT_BLUEPRINTS.getOrDefault(name, Collections.emptyMap()).getOrDefault(horizonsBlueprintModificationType, Collections.emptyMap()).get(horizonsBlueprintGrade);
        if (recipe != null) {
            return recipe;
        }
        recipe = CORE_INTERNAL_BLUEPRINTS.getOrDefault(name, Collections.emptyMap()).getOrDefault(horizonsBlueprintModificationType, Collections.emptyMap()).get(horizonsBlueprintGrade);
        if (recipe != null) {
            return recipe;
        }
        recipe = OPTIONAL_INTERNAL_BLUEPRINTS.getOrDefault(name, Collections.emptyMap()).getOrDefault(horizonsBlueprintModificationType, Collections.emptyMap()).get(horizonsBlueprintGrade);
        return recipe;
//        return ENGINEER_UNLOCK_REQUIREMENTS.get(name);
//TODO
    }

    public static Set<HorizonsBlueprintModificationType> getRecipeModificationTypes(final HorizonsBlueprintObjectName name) {
        Map<HorizonsBlueprintModificationType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> recipeModificationTypes = HARDPOINT_BLUEPRINTS.getOrDefault(name, Collections.emptyMap());
        if (!recipeModificationTypes.isEmpty()) {
            return recipeModificationTypes.keySet();
        }
        recipeModificationTypes = UTILITY_MOUNT_BLUEPRINTS.getOrDefault(name, Collections.emptyMap());
        if (!recipeModificationTypes.isEmpty()) {
            return recipeModificationTypes.keySet();
        }
        recipeModificationTypes = CORE_INTERNAL_BLUEPRINTS.getOrDefault(name, Collections.emptyMap());
        if (!recipeModificationTypes.isEmpty()) {
            return recipeModificationTypes.keySet();
        }
        recipeModificationTypes = OPTIONAL_INTERNAL_BLUEPRINTS.getOrDefault(name, Collections.emptyMap());
        if (!recipeModificationTypes.isEmpty()) {
            return recipeModificationTypes.keySet();
        }
        return Collections.emptySet();
    }

    public static BlueprintCategory getRecipeCategory(final HorizonsBlueprintObjectName recipeName) {
        return RECIPES.entrySet().stream()
                .filter(recipeCategoryMapEntry -> recipeCategoryMapEntry.getValue().containsKey(recipeName))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }

//    public static Map<HorizonsRecipeObjectName, Integer> findRecipesContaining(final HorizonsMaterial material) {
//        final Map<HorizonsRecipeObjectName, Integer> newMap = new EnumMap<>(HorizonsRecipeObjectName.class);
//        RECIPES.values()
//                .forEach(recipes -> recipes.entrySet().stream()
//                        .filter(stringIngredientsEntry -> stringIngredientsEntry.getValue().getMaterialCollection(material.getClass()).containsKey(material))
//                        .forEach(entry -> newMap.put(entry.getKey(), entry.getValue().getMaterialCollection(material.getClass()).get(material))));
//        return newMap;
//    }

//    private static boolean isBlueprintIngredient(final HorizonsMaterial material) {
//        return HARDPOINT_BLUEPRINTS.values().stream().anyMatch(recipe -> recipe.getMaterialCollection(material.getClass()).containsKey(material)) ||
//                UTILITY_MOUNT_BLUEPRINTS.values().stream().anyMatch(recipe -> recipe.getMaterialCollection(material.getClass()).containsKey(material)) ||
//                CORE_INTERNAL_BLUEPRINTS.values().stream().anyMatch(recipe -> recipe.getMaterialCollection(material.getClass()).containsKey(material)) ||
//                OPTIONAL_INTERNAL_BLUEPRINTS.values().stream().anyMatch(recipe -> recipe.getMaterialCollection(material.getClass()).containsKey(material));
//    }
//
//    private static boolean isBlueprintIngredientWithOverride(final HorizonsMaterial material) {
//        return isBlueprintIngredient(material) || isRelevantOverride(material);
//    }
//
//    public static boolean isNotRelevantAndNotEngineeringIngredient(final HorizonsMaterial material) {
//        return !isBlueprintIngredientWithOverride(material) && !isEngineeringIngredient(material);
//    }
//
//    public static boolean isNotRelevantWithOverrideAndNotRequiredEngineeringIngredient(final HorizonsMaterial material) {
//        return !isBlueprintIngredientWithOverride(material) && !isEngineeringIngredientAndNotCompleted(material);
//    }
//
//    public static boolean isNotRelevantAndNotRequiredEngineeringIngredient(final HorizonsMaterial material) {
//        return !isBlueprintIngredient(material) && !isEngineeringIngredientAndNotCompleted(material);
//    }
//
//    public static boolean isEngineeringOnlyIngredient(final HorizonsMaterial material) {
//        return isEngineeringIngredient(material) && !isBlueprintIngredientWithOverride(material);
//    }
//
//    public static boolean isEngineeringOrBlueprintIngredient(final HorizonsMaterial material) {
//        return isEngineeringIngredient(material) || isBlueprintIngredient(material);
//    }
//
//    public static boolean isEngineeringOrBlueprintIngredientWithOverride(final HorizonsMaterial material) {
//        return isEngineeringIngredient(material) || isBlueprintIngredientWithOverride(material);
//    }
//
//    public static boolean isEngineeringIngredient(final HorizonsMaterial material) {
//        return !material.isUnknown() && ENGINEER_UNLOCK_REQUIREMENTS.values().stream().anyMatch(recipe -> recipe.getMaterialCollection(material.getClass()).containsKey(material));
//    }
//
//    private static boolean isEngineeringIngredientAndNotCompleted(final HorizonsMaterial material) {
//        return ENGINEER_UNLOCK_REQUIREMENTS.values().stream().filter(engineerRecipe -> !engineerRecipe.isCompleted()).anyMatch(recipe -> recipe.getMaterialCollection(material.getClass()).containsKey(material));
//    }
//
//    private static boolean isRelevantOverride(final HorizonsMaterial material) {
//        final String irrelevantValues = PreferencesService.getPreference(PreferenceConstants.IRRELEVANT_OVERRIDE, "");
//        return Arrays.stream(irrelevantValues.split(",")).filter(string -> !string.isEmpty()).map(Material::subtypeForName).anyMatch(mat -> mat.equals(material));
//    }


    static {
        CORE_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintObjectName.ARMOUR, ArmourBlueprints.BLUEPRINTS);
        CORE_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintObjectName.FRAME_SHIFT_DRIVE, FSDBlueprints.BLUEPRINTS);
        CORE_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintObjectName.LIFE_SUPPORT, LifeSupportBlueprints.BLUEPRINTS);
        CORE_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, PowerDistributorBlueprints.BLUEPRINTS);
        CORE_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintObjectName.POWER_PLANT, PowerPlantBlueprints.BLUEPRINTS);
        CORE_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintObjectName.SENSORS, SensorBlueprints.BLUEPRINTS);
        CORE_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintObjectName.THRUSTERS, ThrusterBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintObjectName.BEAM_LASER, BeamLaserBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintObjectName.BURST_LASER, BurstLaserBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintObjectName.CANNON, CannonBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintObjectName.FRAGMENT_CANNON, FragmentCannonBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintObjectName.MINE_LAUNCHER, MineLauncherBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintObjectName.MISSILE_RACK, MissileRackBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintObjectName.MULTI_CANNON, MultiCannonBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintObjectName.PLASMA_ACCELERATOR, PlasmaAcceleratorBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintObjectName.PULSE_LASER, PulseLaserBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintObjectName.RAIL_GUN, RailGunBlueprints.BLUEPRINTS);
        HARDPOINT_BLUEPRINTS.put(HorizonsBlueprintObjectName.TORPEDO_PYLON, TorpedoPylonBlueprints.BLUEPRINTS);
        OPTIONAL_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintObjectName.AUTO_FIELD_MAINTENANCE_UNIT, AutoFieldMaintenanceUnitBlueprints.BLUEPRINTS);
        OPTIONAL_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintObjectName.COLLECTOR_LIMPET_CONTROLLER, CollectorLimpetControllerBlueprints.BLUEPRINTS);
        OPTIONAL_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintObjectName.FRAME_SHIFT_DRIVE_INTERDICTOR, FSDInterdictorBlueprints.BLUEPRINTS);
        OPTIONAL_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintObjectName.FUEL_SCOOP, FuelScoopBlueprints.BLUEPRINTS);
        OPTIONAL_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintObjectName.FUEL_TRANSFER_LIMPET_CONTROLLER, FuelTransferLimpetControllerBlueprints.BLUEPRINTS);
        OPTIONAL_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintObjectName.HATCH_BREAKER_LIMPET_CONTROLLER, HatchBreakerLimpetControllerBlueprints.BLUEPRINTS);
        OPTIONAL_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintObjectName.HULL_REINFORCEMENT_PACKAGE, HullReinforcementBlueprints.BLUEPRINTS);
        OPTIONAL_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintObjectName.PROSPECTOR_LIMPET_CONTROLLER, ProspectorLimpetControllerBlueprints.BLUEPRINTS);
        OPTIONAL_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintObjectName.REFINERY, RefineryBlueprints.BLUEPRINTS);
        OPTIONAL_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintObjectName.SHIELD_CELL_BANK, ShieldCellBankBlueprints.BLUEPRINTS);
        OPTIONAL_INTERNAL_BLUEPRINTS.put(HorizonsBlueprintObjectName.SHIELD_GENERATOR, ShieldGeneratorBlueprints.BLUEPRINTS);
        UTILITY_MOUNT_BLUEPRINTS.put(HorizonsBlueprintObjectName.CHAFF_LAUNCHER, ChaffLauncherBlueprints.BLUEPRINTS);
        UTILITY_MOUNT_BLUEPRINTS.put(HorizonsBlueprintObjectName.ELECTRONIC_COUNTERMEASURE, ElectronicCounterMeasureBlueprints.BLUEPRINTS);
        UTILITY_MOUNT_BLUEPRINTS.put(HorizonsBlueprintObjectName.FRAME_SHIFT_WAKE_SCANNER, FrameShiftWakeScannerBlueprints.BLUEPRINTS);
        UTILITY_MOUNT_BLUEPRINTS.put(HorizonsBlueprintObjectName.HEAT_SINK_LAUNCHER, HeatSinkLauncherBlueprints.BLUEPRINTS);
        UTILITY_MOUNT_BLUEPRINTS.put(HorizonsBlueprintObjectName.KILL_WARRANT_SCANNER, KillWarrantScannerBlueprints.BLUEPRINTS);
        UTILITY_MOUNT_BLUEPRINTS.put(HorizonsBlueprintObjectName.MANIFEST_SCANNER, ManifestScannerBlueprints.BLUEPRINTS);
        UTILITY_MOUNT_BLUEPRINTS.put(HorizonsBlueprintObjectName.POINT_DEFENCE, PointDefenceBlueprints.BLUEPRINTS);
        UTILITY_MOUNT_BLUEPRINTS.put(HorizonsBlueprintObjectName.SHIELD_BOOSTER, ShieldBoosterBlueprints.BLUEPRINTS);


    }
}