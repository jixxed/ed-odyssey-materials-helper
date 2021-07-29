package nl.jixxed.eliteodysseymaterials;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.EngineerRecipe;
import nl.jixxed.eliteodysseymaterials.domain.ModuleRecipe;
import nl.jixxed.eliteodysseymaterials.domain.Recipe;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class RecipeConstants {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final Map<RecipeName, Recipe> SUIT_UPGRADES = new HashMap<>();
    private static final Map<RecipeName, Recipe> WEAPON_UPGRADES = new HashMap<>();
    private static final Map<RecipeName, ModuleRecipe> SUIT_MODULE_BLUEPRINTS = new HashMap<>();
    private static final Map<RecipeName, ModuleRecipe> WEAPON_MODULE_BLUEPRINTS = new HashMap<>();
    private static final Map<RecipeName, EngineerRecipe> ENGINEER_UNLOCK_REQUIREMENTS = new HashMap<>();
    public static final Map<RecipeCategory, Map<RecipeName, ? extends Recipe>> RECIPES = Map.of(
            RecipeCategory.SUIT_GRADES, SUIT_UPGRADES,
            RecipeCategory.WEAPON_GRADES, WEAPON_UPGRADES,
            RecipeCategory.SUIT_MODULES, SUIT_MODULE_BLUEPRINTS,
            RecipeCategory.WEAPON_MODULES, WEAPON_MODULE_BLUEPRINTS,
            RecipeCategory.ENGINEER_UNLOCKS, ENGINEER_UNLOCK_REQUIREMENTS
    );

    public static Recipe getRecipe(final RecipeName name) {
        Recipe recipe = WEAPON_MODULE_BLUEPRINTS.get(name);
        if (recipe != null) {
            return recipe;
        }
        recipe = SUIT_MODULE_BLUEPRINTS.get(name);
        if (recipe != null) {
            return recipe;
        }
        recipe = SUIT_UPGRADES.get(name);
        if (recipe != null) {
            return recipe;
        }
        recipe = WEAPON_UPGRADES.get(name);
        if (recipe != null) {
            return recipe;
        }
        return ENGINEER_UNLOCK_REQUIREMENTS.get(name);
    }

    public static RecipeCategory getRecipeCategory(final RecipeName recipeName) {
        return RECIPES.entrySet().stream()
                .filter(recipeCategoryMapEntry -> recipeCategoryMapEntry.getValue().containsKey(recipeName))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public static Map<RecipeName, Integer> findRecipesContaining(final Material material) {
        final Map<RecipeName, Integer> newMap = new HashMap<>();
        RECIPES.values()
                .forEach(recipes -> recipes.entrySet().stream()
                        .filter(stringIngredientsEntry -> stringIngredientsEntry.getValue().getMaterialCollection(material.getClass()).containsKey(material))
                        .forEach(entry -> newMap.put(entry.getKey(), entry.getValue().getMaterialCollection(material.getClass()).get(material))));
        return newMap;
    }

    public static boolean isBlueprintIngredient(final Material material) {
        return SUIT_UPGRADES.values().stream().anyMatch(recipe -> recipe.getMaterialCollection(material.getClass()).containsKey(material)) ||
                WEAPON_UPGRADES.values().stream().anyMatch(recipe -> recipe.getMaterialCollection(material.getClass()).containsKey(material)) ||
                SUIT_MODULE_BLUEPRINTS.values().stream().anyMatch(recipe -> recipe.getMaterialCollection(material.getClass()).containsKey(material)) ||
                WEAPON_MODULE_BLUEPRINTS.values().stream().anyMatch(recipe -> recipe.getMaterialCollection(material.getClass()).containsKey(material));
    }

    public static boolean isNotRelevantAndNotEngineeringIngredient(final Material material) {
        return !isBlueprintIngredient(material) && !isEngineeringIngredient(material);
    }

    public static boolean isNotRelevantAndCompletedEngineeringIngredient(final Material material) {
        return !isBlueprintIngredient(material) && isEngineeringIngredientAndCompleted(material);
    }

    public static boolean isEngineeringOnlyIngredient(final Material material) {
        return isEngineeringIngredient(material) && !isBlueprintIngredient(material);
    }

    public static boolean isEngineeringIngredient(final Material material) {
        return ENGINEER_UNLOCK_REQUIREMENTS.values().stream().anyMatch(recipe -> recipe.getMaterialCollection(material.getClass()).containsKey(material));
    }

    private static boolean isEngineeringIngredientAndCompleted(final Material material) {
        return ENGINEER_UNLOCK_REQUIREMENTS.values().stream().filter(EngineerRecipe::isCompleted).anyMatch(recipe -> recipe.getMaterialCollection(material.getClass()).containsKey(material));
    }

    public static boolean isEngineeringIngredientAndNotCompleted(final Material material) {
        return ENGINEER_UNLOCK_REQUIREMENTS.values().stream().filter(engineerRecipe -> !engineerRecipe.isCompleted()).anyMatch(recipe -> recipe.getMaterialCollection(material.getClass()).containsKey(material));
    }

    public static Map<RecipeName, ModuleRecipe> getSuitModuleBlueprints() {
        return SUIT_MODULE_BLUEPRINTS;
    }

    public static Map<RecipeName, ModuleRecipe> getWeaponModuleBlueprints() {
        return WEAPON_MODULE_BLUEPRINTS;
    }

    static {
        ENGINEER_UNLOCK_REQUIREMENTS.put(RecipeName.ENGINEER_A1, new EngineerRecipe(
                RecipeName.ENGINEER_A1,
                List.of("ingredient.a1.fly"),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.DOMINO_GREEN)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(RecipeName.ENGINEER_A2, new EngineerRecipe(
                RecipeName.ENGINEER_A2,
                Map.of(
                        Good.PUSH, 5
                ),
                () -> APPLICATION_STATE.isEngineerKnown(Engineer.KIT_FOWLER)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(RecipeName.ENGINEER_A3, new EngineerRecipe(
                RecipeName.ENGINEER_A3,
                Map.of(
                        Data.OPINIONPOLLS, 10
                ),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.KIT_FOWLER)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(RecipeName.ENGINEER_A4, new EngineerRecipe(
                RecipeName.ENGINEER_A4,
                Map.of(
                        Good.SURVEILLANCEEQUIPMENT, 5
                ),
                () -> APPLICATION_STATE.isEngineerKnown(Engineer.YARDEN_BOND)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(RecipeName.ENGINEER_A5, new EngineerRecipe(
                RecipeName.ENGINEER_A5,
                Map.of(
                        Data.SMEARCAMPAIGNPLANS, 8
                ),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.YARDEN_BOND)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(RecipeName.ENGINEER_B1, new EngineerRecipe(
                RecipeName.ENGINEER_B1,
                List.of("ingredient.b1.conflict"),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.HERO_FERRARI)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(RecipeName.ENGINEER_B2, new EngineerRecipe(
                RecipeName.ENGINEER_B2,
                Map.of(
                        Data.SETTLEMENTDEFENCEPLANS, 15
                ),
                () -> APPLICATION_STATE.isEngineerKnown(Engineer.WELLINGTON_BECK)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(RecipeName.ENGINEER_B3, new EngineerRecipe(
                RecipeName.ENGINEER_B3,
                Map.of(
                        Data.CLASSICENTERTAINMENT, 25,
                        Data.MULTIMEDIAENTERTAINMENT, 25,
                        Data.CATMEDIA, 25
                ),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.WELLINGTON_BECK)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(RecipeName.ENGINEER_B4, new EngineerRecipe(
                RecipeName.ENGINEER_B4,
                Map.of(
                        Good.INSIGHTENTERTAINMENTSUITE, 5
                ),
                () -> APPLICATION_STATE.isEngineerKnown(Engineer.UMA_LASZLO)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(RecipeName.ENGINEER_B5, new EngineerRecipe(
                RecipeName.ENGINEER_B5,
                List.of("ingredient.b5.sirius"),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.UMA_LASZLO)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(RecipeName.ENGINEER_C1, new EngineerRecipe(
                RecipeName.ENGINEER_C1,
                List.of("ingredient.c1.restore"),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.JUDE_NAVARRO)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(RecipeName.ENGINEER_C2, new EngineerRecipe(
                RecipeName.ENGINEER_C2,
                Map.of(
                        Good.GENETICREPAIRMEDS, 5
                ),
                () -> APPLICATION_STATE.isEngineerKnown(Engineer.TERRA_VELASQUEZ)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(RecipeName.ENGINEER_C3, new EngineerRecipe(
                RecipeName.ENGINEER_C3,
                List.of("ingredient.c3.theft", "ingredient.c3.heist"),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.TERRA_VELASQUEZ)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(RecipeName.ENGINEER_C4, new EngineerRecipe(
                RecipeName.ENGINEER_C4,
                Map.of(
                        Data.FINANCIALPROJECTIONS, 15
                ),
                () -> APPLICATION_STATE.isEngineerKnown(Engineer.ODEN_GEIGER)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(RecipeName.ENGINEER_C5, new EngineerRecipe(
                RecipeName.ENGINEER_C5,
                Map.of(
                        Good.GENETICSAMPLE, 20,
                        Data.EMPLOYEEGENETICDATA, 20,
                        Data.GENETICRESEARCH, 20
                ),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.ODEN_GEIGER)
        ));
        SUIT_UPGRADES.put(RecipeName.MAVERICK_SUIT_GRADE_1_2, new Recipe(
                RecipeName.MAVERICK_SUIT_GRADE_1_2,
                Map.of(
                        Good.SUITSCHEMATIC, 1,
                        Good.HEALTHMONITOR, 1,
                        Good.LARGECAPACITYPOWERREGULATOR, 1,
                        Data.MANUFACTURINGINSTRUCTIONS, 1,
                        Asset.CARBONFIBREPLATING, 5,
                        Asset.GRAPHENE, 5
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "1",
                        Modifier.SHIELD_REGENERATION_RATE, "1.21",
                        Modifier.SHIELD_HEALTH, "16.5",
                        Modifier.KINETIC_DAMAGE_RESISTANCE, "-34%",
                        Modifier.PLASMA_DAMAGE_RESISTANCE, "7%",
                        Modifier.THERMAL_DAMAGE_RESISTANCE, "58%",
                        Modifier.EXPLOSIVE_DAMAGE_RESISTANCE, "16%"//OK
                )
        ));
        SUIT_UPGRADES.put(RecipeName.MAVERICK_SUIT_GRADE_2_3, new Recipe(
                RecipeName.MAVERICK_SUIT_GRADE_2_3,
                Map.of(
                        Good.SUITSCHEMATIC, 5,
                        Good.HEALTHMONITOR, 5,
                        Good.LARGECAPACITYPOWERREGULATOR, 5,
                        Data.MANUFACTURINGINSTRUCTIONS, 5,
                        Asset.CARBONFIBREPLATING, 15,
                        Asset.GRAPHENE, 15
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "2",
                        Modifier.SHIELD_REGENERATION_RATE, "1.49",
                        Modifier.SHIELD_HEALTH, "20.3",
                        Modifier.KINETIC_DAMAGE_RESISTANCE, "-13%",
                        Modifier.PLASMA_DAMAGE_RESISTANCE, "22%",
                        Modifier.THERMAL_DAMAGE_RESISTANCE, "64%",
                        Modifier.EXPLOSIVE_DAMAGE_RESISTANCE, "29%"//OK
                )
        ));
        SUIT_UPGRADES.put(RecipeName.MAVERICK_SUIT_GRADE_3_4, new Recipe(
                RecipeName.MAVERICK_SUIT_GRADE_3_4,
                Map.of(
                        Good.SUITSCHEMATIC, 10,
                        Good.HEALTHMONITOR, 10,
                        Good.LARGECAPACITYPOWERREGULATOR, 10,
                        Data.MANUFACTURINGINSTRUCTIONS, 10,
                        Asset.CARBONFIBREPLATING, 25,
                        Asset.GRAPHENE, 25
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "3",
                        Modifier.SHIELD_REGENERATION_RATE, "1.82",
                        Modifier.SHIELD_HEALTH, "24.9",
                        Modifier.KINETIC_DAMAGE_RESISTANCE, "6%",
                        Modifier.PLASMA_DAMAGE_RESISTANCE, "35%",
                        Modifier.THERMAL_DAMAGE_RESISTANCE, "70%",
                        Modifier.EXPLOSIVE_DAMAGE_RESISTANCE, "41%"
                )
        ));
        SUIT_UPGRADES.put(RecipeName.MAVERICK_SUIT_GRADE_4_5, new Recipe(
                RecipeName.MAVERICK_SUIT_GRADE_4_5,
                Map.of(
                        Good.SUITSCHEMATIC, 15,
                        Good.HEALTHMONITOR, 15,
                        Good.LARGECAPACITYPOWERREGULATOR, 15,
                        Data.MANUFACTURINGINSTRUCTIONS, 15,
                        Asset.CARBONFIBREPLATING, 35,
                        Asset.GRAPHENE, 35
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "4",
                        Modifier.SHIELD_REGENERATION_RATE, "2.23",
                        Modifier.SHIELD_HEALTH, "30.4",
                        Modifier.KINETIC_DAMAGE_RESISTANCE, "19%",
                        Modifier.PLASMA_DAMAGE_RESISTANCE, "45%",
                        Modifier.THERMAL_DAMAGE_RESISTANCE, "75%",
                        Modifier.EXPLOSIVE_DAMAGE_RESISTANCE, "50%"//OK
                )
        ));
        SUIT_UPGRADES.put(RecipeName.DOMINATOR_SUIT_GRADE_1_2, new Recipe(
                RecipeName.DOMINATOR_SUIT_GRADE_1_2,
                Map.of(
                        Good.SUITSCHEMATIC, 1,
                        Good.HEALTHMONITOR, 1,
                        Good.LARGECAPACITYPOWERREGULATOR, 1,
                        Data.MANUFACTURINGINSTRUCTIONS, 1,
                        Asset.TITANIUMPLATING, 5,
                        Asset.GRAPHENE, 5
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "1",
                        Modifier.SHIELD_REGENERATION_RATE, "1.34",
                        Modifier.SHIELD_HEALTH, "18.3",
                        Modifier.KINETIC_DAMAGE_RESISTANCE, "-26%",
                        Modifier.PLASMA_DAMAGE_RESISTANCE, "16%",
                        Modifier.THERMAL_DAMAGE_RESISTANCE, "66%",
                        Modifier.EXPLOSIVE_DAMAGE_RESISTANCE, "16%"//OK
                )
        ));
        SUIT_UPGRADES.put(RecipeName.DOMINATOR_SUIT_GRADE_2_3, new Recipe(
                RecipeName.DOMINATOR_SUIT_GRADE_2_3,
                Map.of(
                        Good.SUITSCHEMATIC, 5,
                        Good.HEALTHMONITOR, 5,
                        Good.LARGECAPACITYPOWERREGULATOR, 5,
                        Data.MANUFACTURINGINSTRUCTIONS, 5,
                        Asset.TITANIUMPLATING, 15,
                        Asset.GRAPHENE, 15
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "2",
                        Modifier.SHIELD_REGENERATION_RATE, "1.65",
                        Modifier.SHIELD_HEALTH, "22.5",
                        Modifier.KINETIC_DAMAGE_RESISTANCE, "-7%",
                        Modifier.PLASMA_DAMAGE_RESISTANCE, "29%",
                        Modifier.THERMAL_DAMAGE_RESISTANCE, "72%",
                        Modifier.EXPLOSIVE_DAMAGE_RESISTANCE, "29%"//OK
                )
        ));
        SUIT_UPGRADES.put(RecipeName.DOMINATOR_SUIT_GRADE_3_4, new Recipe(
                RecipeName.DOMINATOR_SUIT_GRADE_3_4,
                Map.of(
                        Good.SUITSCHEMATIC, 10,
                        Good.HEALTHMONITOR, 10,
                        Good.LARGECAPACITYPOWERREGULATOR, 10,
                        Data.MANUFACTURINGINSTRUCTIONS, 10,
                        Asset.TITANIUMPLATING, 25,
                        Asset.GRAPHENE, 25
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "3",
                        Modifier.SHIELD_REGENERATION_RATE, "2.02",
                        Modifier.SHIELD_HEALTH, "27.6",
                        Modifier.KINETIC_DAMAGE_RESISTANCE, "19%",
                        Modifier.PLASMA_DAMAGE_RESISTANCE, "46%",
                        Modifier.THERMAL_DAMAGE_RESISTANCE, "78%",
                        Modifier.EXPLOSIVE_DAMAGE_RESISTANCE, "46%"
                )
        ));
        SUIT_UPGRADES.put(RecipeName.DOMINATOR_SUIT_GRADE_4_5, new Recipe(
                RecipeName.DOMINATOR_SUIT_GRADE_4_5,
                Map.of(
                        Good.SUITSCHEMATIC, 15,
                        Good.HEALTHMONITOR, 15,
                        Good.LARGECAPACITYPOWERREGULATOR, 15,
                        Data.MANUFACTURINGINSTRUCTIONS, 15,
                        Asset.TITANIUMPLATING, 35,
                        Asset.GRAPHENE, 35
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "4",
                        Modifier.SHIELD_REGENERATION_RATE, "2.48",
                        Modifier.SHIELD_HEALTH, "33.8",
                        Modifier.KINETIC_DAMAGE_RESISTANCE, "25%",
                        Modifier.PLASMA_DAMAGE_RESISTANCE, "50%",
                        Modifier.THERMAL_DAMAGE_RESISTANCE, "80%",
                        Modifier.EXPLOSIVE_DAMAGE_RESISTANCE, "50%"//OK
                )
        ));
        SUIT_UPGRADES.put(RecipeName.ARTEMIS_SUIT_GRADE_1_2, new Recipe(
                RecipeName.ARTEMIS_SUIT_GRADE_1_2,
                Map.of(
                        Good.SUITSCHEMATIC, 1,
                        Good.HEALTHMONITOR, 1,
                        Good.LARGECAPACITYPOWERREGULATOR, 1,
                        Data.MANUFACTURINGINSTRUCTIONS, 1,
                        Asset.AEROGEL, 5,
                        Asset.GRAPHENE, 5
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "1",
                        Modifier.SHIELD_REGENERATION_RATE, "1.07",
                        Modifier.SHIELD_HEALTH, "14.7",
                        Modifier.KINETIC_DAMAGE_RESISTANCE, "-42%",
                        Modifier.PLASMA_DAMAGE_RESISTANCE, "0%",
                        Modifier.THERMAL_DAMAGE_RESISTANCE, "50%",
                        Modifier.EXPLOSIVE_DAMAGE_RESISTANCE, "16%"//OK
                )
        ));
        SUIT_UPGRADES.put(RecipeName.ARTEMIS_SUIT_GRADE_2_3, new Recipe(
                RecipeName.ARTEMIS_SUIT_GRADE_2_3,
                Map.of(
                        Good.SUITSCHEMATIC, 5,
                        Good.HEALTHMONITOR, 5,
                        Good.LARGECAPACITYPOWERREGULATOR, 5,
                        Data.MANUFACTURINGINSTRUCTIONS, 5,
                        Asset.AEROGEL, 15,
                        Asset.GRAPHENE, 15
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "2",
                        Modifier.SHIELD_REGENERATION_RATE, "1.32",
                        Modifier.SHIELD_HEALTH, "18.0",
                        Modifier.KINETIC_DAMAGE_RESISTANCE, "-21%",
                        Modifier.PLASMA_DAMAGE_RESISTANCE, "14%",
                        Modifier.THERMAL_DAMAGE_RESISTANCE, "57%",
                        Modifier.EXPLOSIVE_DAMAGE_RESISTANCE, "29%"//OK
                )
        ));
        SUIT_UPGRADES.put(RecipeName.ARTEMIS_SUIT_GRADE_3_4, new Recipe(
                RecipeName.ARTEMIS_SUIT_GRADE_3_4,
                Map.of(
                        Good.SUITSCHEMATIC, 10,
                        Good.HEALTHMONITOR, 10,
                        Good.LARGECAPACITYPOWERREGULATOR, 10,
                        Data.MANUFACTURINGINSTRUCTIONS, 10,
                        Asset.AEROGEL, 25,
                        Asset.GRAPHENE, 25
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "3",
                        Modifier.SHIELD_REGENERATION_RATE, "1.62",
                        Modifier.SHIELD_HEALTH, "22.1",
                        Modifier.KINETIC_DAMAGE_RESISTANCE, "0%",
                        Modifier.PLASMA_DAMAGE_RESISTANCE, "29%",
                        Modifier.THERMAL_DAMAGE_RESISTANCE, "65%",
                        Modifier.EXPLOSIVE_DAMAGE_RESISTANCE, "41%"
                )
        ));
        SUIT_UPGRADES.put(RecipeName.ARTEMIS_SUIT_GRADE_4_5, new Recipe(
                RecipeName.ARTEMIS_SUIT_GRADE_4_5,
                Map.of(
                        Good.SUITSCHEMATIC, 15,
                        Good.HEALTHMONITOR, 15,
                        Good.LARGECAPACITYPOWERREGULATOR, 15,
                        Data.MANUFACTURINGINSTRUCTIONS, 15,
                        Asset.AEROGEL, 35,
                        Asset.GRAPHENE, 35
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "4",
                        Modifier.SHIELD_REGENERATION_RATE, "1.98",
                        Modifier.SHIELD_HEALTH, "27.0",
                        Modifier.KINETIC_DAMAGE_RESISTANCE, "14%",
                        Modifier.PLASMA_DAMAGE_RESISTANCE, "39%",
                        Modifier.THERMAL_DAMAGE_RESISTANCE, "70%",
                        Modifier.EXPLOSIVE_DAMAGE_RESISTANCE, "50%"//OK
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(RecipeName.ADDED_MELEE_DAMAGE, new ModuleRecipe(
                RecipeName.ADDED_MELEE_DAMAGE,
                Map.of(
                        Data.COMBATTRAININGMATERIAL, 10,
                        Data.COMBATANTPERFORMANCE, 10,
                        Asset.EPINEPHRINE, 10,
                        Asset.MICROTHRUSTERS, 15
                ), List.of(Engineer.JUDE_NAVARRO, Engineer.KIT_FOWLER),
                Map.of(
                        Modifier.WEAPON_MELEE_DAMAGE_MULTIPLIER, "+150%",
                        Modifier.FISTS_MELEE_DAMAGE_MULTIPLIER, "+150%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(RecipeName.COMBAT_MOVEMENT_SPEED, new ModuleRecipe(
                RecipeName.COMBAT_MOVEMENT_SPEED,
                Map.of(
                        Data.EVACUATIONPROTOCOLS, 10,
                        Data.GENETICRESEARCH, 5,
                        Asset.EPINEPHRINE, 10,
                        Asset.PHNEUTRALISER, 15
                ), List.of(Engineer.TERRA_VELASQUEZ, Engineer.YARDEN_BOND),
                Map.of(
                        Modifier.MOVEMENT_SPEED_PENALTY, "-100%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(RecipeName.DAMAGE_RESISTANCE, new ModuleRecipe(
                RecipeName.DAMAGE_RESISTANCE,
                Map.of(
                        Data.WEAPONINVENTORY, 10,
                        Data.BALLISTICSDATA, 10,
                        Asset.TITANIUMPLATING, 5,
                        Asset.EPOXYADHESIVE, 15,
                        Asset.CARBONFIBREPLATING, 5
                ), List.of(Engineer.JUDE_NAVARRO, Engineer.UMA_LASZLO),
                Map.of(
                        Modifier.EXPLOSIVE_DAMAGE_REDUCTION, "+10%",
                        Modifier.PLASMA_DAMAGE_REDUCTION, "+10%",
                        Modifier.KINETIC_DAMAGE_REDUCTION, "+10%",
                        Modifier.THERMAL_DAMAGE_REDUCTION, "+10%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(RecipeName.ENHANCED_TRACKING, new ModuleRecipe(
                RecipeName.ENHANCED_TRACKING,
                Map.of(
                        Data.TOPOGRAPHICALSURVEYS, 10,
                        Data.STELLARACTIVITYLOGS, 10,
                        Data.SPECTRALANALYSISDATA, 10,
                        Asset.TRANSMITTER, 5,
                        Asset.CIRCUITBOARD, 5
                ), List.of(Engineer.DOMINO_GREEN, Engineer.ODEN_GEIGER),
                Map.of(
                        Modifier.LOS_ANALYSIS_RANGE, "+100%",
                        Modifier.LOS_ANALYSIS_TIME, "-100%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(RecipeName.EXTRA_AMMO_CAPACITY, new ModuleRecipe(
                RecipeName.EXTRA_AMMO_CAPACITY,
                Map.of(
                        Data.RECYCLINGLOGS, 15,
                        Data.WEAPONTESTDATA, 10,
                        Data.PRODUCTIONREPORTS, 10,
                        Asset.WEAPONCOMPONENT, 5
                ), List.of(Engineer.JUDE_NAVARRO, Engineer.KIT_FOWLER),
                Map.of(
                        Modifier.AMMO_CAPACITY_MULTIPLIER, "+50%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(RecipeName.EXTRA_BACKPACK_CAPACITY, new ModuleRecipe(
                RecipeName.EXTRA_BACKPACK_CAPACITY,
                Map.of(
                        Data.WEAPONINVENTORY, 10,
                        Data.CHEMICALINVENTORY, 10,
                        Data.DIGITALDESIGNS, 10,
                        Asset.EPOXYADHESIVE, 10,
                        Asset.MEMORYCHIP, 5
                ), List.of(Engineer.DOMINO_GREEN, Engineer.WELLINGTON_BECK),
                Map.of(
                        Modifier.BACKPACK_COMPONENT_CAPACITY, "+100%",
                        Modifier.BACKPACK_ITEM_CAPACITY, "+100%",
                        Modifier.BACKPACK_DATA_CAPACITY, "+100%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(RecipeName.FASTER_SHIELD_REGEN, new ModuleRecipe(
                RecipeName.FASTER_SHIELD_REGEN,
                Map.of(
                        Data.REACTOROUTPUTREVIEW, 10,
                        Asset.IONBATTERY, 5,
                        Asset.MICROTRANSFORMER, 15,
                        Asset.ELECTRICALWIRING, 15
                ), List.of(Engineer.KIT_FOWLER, Engineer.UMA_LASZLO),
                Map.of(
                        Modifier.SHIELD_REGENERATION_RATE, "+25%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(RecipeName.IMPROVED_BATTERY_CAPACITY, new ModuleRecipe(
                RecipeName.IMPROVED_BATTERY_CAPACITY,
                Map.of(
                        Data.REACTOROUTPUTREVIEW, 10,
                        Data.MAINTENANCELOGS, 15,
                        Asset.IONBATTERY, 5,
                        Asset.MICROSUPERCAPACITOR, 10,
                        Asset.ELECTRICALWIRING, 10
                ), List.of(Engineer.ODEN_GEIGER, Engineer.WELLINGTON_BECK),
                Map.of(
                        Modifier.BATTERY_ENERGY_CAPACITY, "+50%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(RecipeName.IMPROVED_JUMP_ASSIST, new ModuleRecipe(
                RecipeName.IMPROVED_JUMP_ASSIST,
                Map.of(
                        Good.GMEDS, 10,
                        Data.TOPOGRAPHICALSURVEYS, 10,
                        Asset.MICROTHRUSTERS, 5,
                        Asset.MOTOR, 10
                ), List.of(Engineer.HERO_FERRARI, Engineer.YARDEN_BOND),
                Map.of(
                        Modifier.JUMP_ASSIST_BOOST_DRAIN, "-25%",
                        Modifier.JUMP_ASSIST_BOOST_RECHARGE_RATE, "+25%",
                        Modifier.BATTERY_CONSUMPTION, "-25%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(RecipeName.INCREASED_AIR_RESERVES, new ModuleRecipe(
                RecipeName.INCREASED_AIR_RESERVES,
                Map.of(
                        Data.PHARMACEUTICALPATENTS, 5,
                        Data.AIRQUALITYREPORTS, 15,
                        Asset.OXYGENICBACTERIA, 10,
                        Asset.PHNEUTRALISER, 15
                ), List.of(Engineer.HERO_FERRARI, Engineer.TERRA_VELASQUEZ),
                Map.of(
                        Modifier.AIR_CAPACITY, "+400%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(RecipeName.INCREASED_SPRINT_DURATION, new ModuleRecipe(
                RecipeName.INCREASED_SPRINT_DURATION,
                Map.of(
                        Data.TROOPDEPLOYMENTRECORDS, 5,
                        Data.GENESEQUENCINGDATA, 5,
                        Data.MEDICALTRIALRECORDS, 5,
                        Asset.OXYGENICBACTERIA, 10,
                        Asset.CHEMICALCATALYST, 15
                ), List.of(Engineer.HERO_FERRARI, Engineer.TERRA_VELASQUEZ),
                Map.of(
                        Modifier.SPRINT_DURATION, "+100%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(RecipeName.NIGHT_VISION, new ModuleRecipe(
                RecipeName.NIGHT_VISION,
                Map.of(
                        Good.SURVEILLANCEEQUIPMENT, 10,
                        Data.SURVEILLEANCELOGS, 5,
                        Data.NOCDATA, 5,
                        Data.RADIOACTIVITYDATA, 5,
                        Asset.CIRCUITSWITCH, 10
                ), List.of(Engineer.ODEN_GEIGER),
                Collections.emptyMap()
        ));
        SUIT_MODULE_BLUEPRINTS.put(RecipeName.QUIETER_FOOTSTEPS, new ModuleRecipe(
                RecipeName.QUIETER_FOOTSTEPS,
                Map.of(
                        Data.SETTLEMENTASSAULTPLANS, 5,
                        Data.TACTICALPLANS, 10,
                        Data.PATROLROUTES, 10,
                        Asset.MICROHYDRAULICS, 5,
                        Asset.VISCOELASTICPOLYMER, 15
                ), List.of(Engineer.YARDEN_BOND),
                Map.of(
                        Modifier.FOOTSTEP_AUDIBLE_RANGE_MULTIPLIER, "-50%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(RecipeName.REDUCED_TOOL_BATTERY_CONSUMPTION, new ModuleRecipe(
                RecipeName.REDUCED_TOOL_BATTERY_CONSUMPTION,
                Map.of(
                        Data.REACTOROUTPUTREVIEW, 10,
                        Asset.ELECTRICALWIRING, 15,
                        Asset.ELECTRICALFUSE, 5,
                        Asset.MICROTRANSFORMER, 10
                ), List.of(Engineer.DOMINO_GREEN, Engineer.WELLINGTON_BECK),
                Map.of(
                        Modifier.TOOL_ENERGY_DRAIN_MULTIPLIER, "-50%"
                )
        ));


        WEAPON_UPGRADES.put(RecipeName.KARMA_1_2, new Recipe(
                RecipeName.KARMA_1_2,
                Map.of(
                        Good.WEAPONSCHEMATIC, 1,
                        Good.COMPRESSIONLIQUEFIEDGAS, 1,
                        Data.MANUFACTURINGINSTRUCTIONS, 1,
                        Asset.WEAPONCOMPONENT, 5,
                        Asset.TUNGSTENCARBIDE, 5
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "1"
                )
        ));

        WEAPON_UPGRADES.put(RecipeName.KARMA_2_3, new Recipe(
                RecipeName.KARMA_2_3,
                Map.of(
                        Good.WEAPONSCHEMATIC, 5,
                        Good.COMPRESSIONLIQUEFIEDGAS, 5,
                        Data.MANUFACTURINGINSTRUCTIONS, 5,
                        Asset.WEAPONCOMPONENT, 15,
                        Asset.TUNGSTENCARBIDE, 15
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "2"
                )
        ));
        WEAPON_UPGRADES.put(RecipeName.KARMA_3_4, new Recipe(
                RecipeName.KARMA_3_4,
                Map.of(
                        Good.WEAPONSCHEMATIC, 10,
                        Good.COMPRESSIONLIQUEFIEDGAS, 10,
                        Data.MANUFACTURINGINSTRUCTIONS, 10,
                        Asset.WEAPONCOMPONENT, 25,
                        Asset.TUNGSTENCARBIDE, 25
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "3"
                )
        ));
        WEAPON_UPGRADES.put(RecipeName.KARMA_4_5, new Recipe(
                RecipeName.KARMA_4_5,
                Map.of(
                        Good.WEAPONSCHEMATIC, 15,
                        Good.COMPRESSIONLIQUEFIEDGAS, 15,
                        Data.MANUFACTURINGINSTRUCTIONS, 15,
                        Asset.WEAPONCOMPONENT, 35,
                        Asset.TUNGSTENCARBIDE, 35
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "4"
                )
        ));

        WEAPON_UPGRADES.put(RecipeName.TK_1_2, new Recipe(
                RecipeName.TK_1_2,
                Map.of(
                        Good.WEAPONSCHEMATIC, 1,
                        Good.IONISEDGAS, 1,
                        Data.MANUFACTURINGINSTRUCTIONS, 1,
                        Asset.MICROELECTRODE, 5,
                        Asset.OPTICALFIBRE, 5
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "1"
                )
        ));

        WEAPON_UPGRADES.put(RecipeName.TK_2_3, new Recipe(
                RecipeName.TK_2_3,
                Map.of(
                        Good.WEAPONSCHEMATIC, 5,
                        Good.IONISEDGAS, 5,
                        Data.MANUFACTURINGINSTRUCTIONS, 5,
                        Asset.MICROELECTRODE, 15,
                        Asset.OPTICALFIBRE, 15
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "2"
                )
        ));
        WEAPON_UPGRADES.put(RecipeName.TK_3_4, new Recipe(
                RecipeName.TK_3_4,
                Map.of(
                        Good.WEAPONSCHEMATIC, 10,
                        Good.IONISEDGAS, 10,
                        Data.MANUFACTURINGINSTRUCTIONS, 10,
                        Asset.MICROELECTRODE, 25,
                        Asset.OPTICALFIBRE, 25
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "3"
                )
        ));
        WEAPON_UPGRADES.put(RecipeName.TK_4_5, new Recipe(
                RecipeName.TK_4_5,
                Map.of(
                        Good.WEAPONSCHEMATIC, 15,
                        Good.IONISEDGAS, 15,
                        Data.MANUFACTURINGINSTRUCTIONS, 15,
                        Asset.MICROELECTRODE, 35,
                        Asset.OPTICALFIBRE, 35
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "4"
                )
        ));

        WEAPON_UPGRADES.put(RecipeName.MANTICORE_1_2, new Recipe(
                RecipeName.MANTICORE_1_2,
                Map.of(
                        Good.WEAPONSCHEMATIC, 1,
                        Good.IONISEDGAS, 1,
                        Data.MANUFACTURINGINSTRUCTIONS, 1,
                        Asset.MICROELECTRODE, 5,
                        Asset.CHEMICALSUPERBASE, 5
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "1"
                )
        ));

        WEAPON_UPGRADES.put(RecipeName.MANTICORE_2_3, new Recipe(
                RecipeName.MANTICORE_2_3,
                Map.of(
                        Good.WEAPONSCHEMATIC, 5,
                        Good.IONISEDGAS, 5,
                        Data.MANUFACTURINGINSTRUCTIONS, 5,
                        Asset.MICROELECTRODE, 15,
                        Asset.CHEMICALSUPERBASE, 15
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "2"
                )
        ));
        WEAPON_UPGRADES.put(RecipeName.MANTICORE_3_4, new Recipe(
                RecipeName.MANTICORE_3_4,
                Map.of(
                        Good.WEAPONSCHEMATIC, 10,
                        Good.IONISEDGAS, 10,
                        Data.MANUFACTURINGINSTRUCTIONS, 10,
                        Asset.MICROELECTRODE, 25,
                        Asset.CHEMICALSUPERBASE, 25
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "3"
                )
        ));
        WEAPON_UPGRADES.put(RecipeName.MANTICORE_4_5, new Recipe(
                RecipeName.MANTICORE_4_5,
                Map.of(
                        Good.WEAPONSCHEMATIC, 15,
                        Good.IONISEDGAS, 15,
                        Data.MANUFACTURINGINSTRUCTIONS, 15,
                        Asset.MICROELECTRODE, 35,
                        Asset.CHEMICALSUPERBASE, 35
                ),
                Map.of(
                        Modifier.ENGINEER_MODIFICATION_SLOTS, "4"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(RecipeName.AUDIO_MASKING, new ModuleRecipe(
                RecipeName.AUDIO_MASKING,
                Map.of(
                        Data.AUDIOLOGS, 5,
                        Data.PATROLROUTES, 10,
                        Asset.SCRAMBLER, 10,
                        Asset.TRANSMITTER, 15,
                        Asset.CIRCUITBOARD, 5
                ), List.of(Engineer.YARDEN_BOND)
        ));
        WEAPON_MODULE_BLUEPRINTS.put(RecipeName.FASTER_HANDLING, new ModuleRecipe(
                RecipeName.FASTER_HANDLING,
                Map.of(
                        Data.OPERATIONALMANUAL, 10,
                        Data.COMBATANTPERFORMANCE, 10,
                        Data.COMBATTRAININGMATERIAL, 10,
                        Asset.VISCOELASTICPOLYMER, 5
                ), List.of(Engineer.HERO_FERRARI, Engineer.YARDEN_BOND),
                Map.of(
                        Modifier.HANDLING_SPEED, "+10%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(RecipeName.GREATER_RANGE_KINETIC, new ModuleRecipe(
                RecipeName.GREATER_RANGE_KINETIC,
                Map.of(
                        Data.BALLISTICSDATA, 10,
                        Data.TOPOGRAPHICALSURVEYS, 10,
                        Asset.METALCOIL, 10,
                        Asset.RDX, 10,
                        Asset.WEAPONCOMPONENT, 5

                ), List.of(Engineer.DOMINO_GREEN, Engineer.WELLINGTON_BECK),
                Map.of(
                        Modifier.WEAPON_EFFECTIVE_RANGE, "+50%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(RecipeName.GREATER_RANGE_LASER, new ModuleRecipe(
                RecipeName.GREATER_RANGE_LASER,
                Map.of(
                        Data.STELLARACTIVITYLOGS, 10,
                        Data.RISKASSESSMENTS, 15,
                        Asset.OPTICALLENS, 5,
                        Asset.MICROTRANSFORMER, 15,
                        Asset.CIRCUITBOARD, 5
                ), List.of(Engineer.DOMINO_GREEN, Engineer.WELLINGTON_BECK),
                Map.of(
                        Modifier.WEAPON_EFFECTIVE_RANGE, "+50%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(RecipeName.GREATER_RANGE_PLASMA, new ModuleRecipe(
                RecipeName.GREATER_RANGE_PLASMA,
                Map.of(
                        Data.CHEMICALFORMULAE, 10,
                        Data.MINERALSURVEY, 15,
                        Asset.ELECTROMAGNET, 10,
                        Asset.MOTOR, 5,
                        Asset.ELECTRICALFUSE, 5

                ), List.of(Engineer.DOMINO_GREEN, Engineer.WELLINGTON_BECK),
                Map.of(
                        Modifier.WEAPON_EFFECTIVE_RANGE, "+50%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(RecipeName.HEADSHOT_DAMAGE_KINETIC, new ModuleRecipe(
                RecipeName.HEADSHOT_DAMAGE_KINETIC,
                Map.of(
                        Data.WEAPONTESTDATA, 10,
                        Data.MEDICALRECORDS, 5,
                        Asset.CHEMICALCATALYST, 10,
                        Asset.RDX, 15,
                        Asset.WEAPONCOMPONENT, 5
                ), List.of(Engineer.UMA_LASZLO),
                Map.of(
                        Modifier.HEADSHOT_DAMAGE_MULTIPLIER, "+50%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(RecipeName.HEADSHOT_DAMAGE_LASER, new ModuleRecipe(
                RecipeName.HEADSHOT_DAMAGE_LASER,
                Map.of(
                        Data.SPECTRALANALYSISDATA, 10,
                        Data.BIOMETRICDATA, 5,
                        Asset.IONBATTERY, 10,
                        Asset.OPTICALLENS, 5,
                        Asset.SCRAMBLER, 10
                ), List.of(Engineer.UMA_LASZLO),
                Map.of(
                        Modifier.HEADSHOT_DAMAGE_MULTIPLIER, "+50%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(RecipeName.HEADSHOT_DAMAGE_PLASMA, new ModuleRecipe(
                RecipeName.HEADSHOT_DAMAGE_PLASMA,
                Map.of(
                        Data.CHEMICALEXPERIMENTDATA, 10,
                        Data.BLOODTESTRESULTS, 5,
                        Asset.IONBATTERY, 10,
                        Asset.ELECTROMAGNET, 10,
                        Asset.MICROSUPERCAPACITOR, 15
                ), List.of(Engineer.UMA_LASZLO),
                Map.of(
                        Modifier.HEADSHOT_DAMAGE_MULTIPLIER, "+50%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(RecipeName.HIGHER_ACCURACY_KINETIC, new ModuleRecipe(
                RecipeName.HIGHER_ACCURACY_KINETIC,
                Map.of(
                        Data.EXTRACTIONYIELDDATA, 10,
                        Data.BIOMETRICDATA, 5,
                        Data.COMBATANTPERFORMANCE, 10,
                        Asset.RDX, 10,
                        Asset.VISCOELASTICPOLYMER, 10
                ), List.of(Engineer.YARDEN_BOND, Engineer.TERRA_VELASQUEZ),
                Map.of(
                        Modifier.HIP_FIRE_ACCURACY, "+10%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(RecipeName.HIGHER_ACCURACY_LASER, new ModuleRecipe(
                RecipeName.HIGHER_ACCURACY_LASER,
                Map.of(
                        Data.RADIOACTIVITYDATA, 5,
                        Data.COMBATANTPERFORMANCE, 10,
                        Asset.OPTICALLENS, 5,
                        Asset.ELECTRICALWIRING, 15,
                        Asset.METALCOIL, 10
                ), List.of(Engineer.YARDEN_BOND, Engineer.TERRA_VELASQUEZ),
                Map.of(
                        Modifier.HIP_FIRE_ACCURACY, "+15%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(RecipeName.HIGHER_ACCURACY_PLASMA, new ModuleRecipe(
                RecipeName.HIGHER_ACCURACY_PLASMA,
                Map.of(
                        Data.CHEMICALPATENTS, 5,
                        Data.COMBATANTPERFORMANCE, 10,
                        Asset.CHEMICALCATALYST, 10,
                        Asset.ELECTROMAGNET, 10,
                        Asset.METALCOIL, 10
                ), List.of(Engineer.YARDEN_BOND, Engineer.TERRA_VELASQUEZ),
                Map.of(
                        Modifier.HIP_FIRE_ACCURACY, "+30%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(RecipeName.MAGAZINE_SIZE, new ModuleRecipe(
                RecipeName.MAGAZINE_SIZE,
                Map.of(
                        Data.WEAPONTESTDATA, 10,
                        Data.SECURITYEXPENSES, 5,
                        Asset.WEAPONCOMPONENT, 5,
                        Asset.TUNGSTENCARBIDE, 5,
                        Asset.METALCOIL, 10

                ), List.of(Engineer.JUDE_NAVARRO, Engineer.KIT_FOWLER),
                Map.of(
                        Modifier.MAGAZINE_SIZE, "+50%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(RecipeName.NOISE_SUPPRESSOR, new ModuleRecipe(
                RecipeName.NOISE_SUPPRESSOR,
                Map.of(
                        Data.ATMOSPHERICDATA, 10,
                        Data.MININGANALYTICS, 10,
                        Asset.VISCOELASTICPOLYMER, 15,
                        Asset.WEAPONCOMPONENT, 5
                ), List.of(Engineer.HERO_FERRARI, Engineer.TERRA_VELASQUEZ)
        ));
        WEAPON_MODULE_BLUEPRINTS.put(RecipeName.RELOAD_SPEED, new ModuleRecipe(
                RecipeName.RELOAD_SPEED,
                Map.of(
                        Data.OPERATIONALMANUAL, 10,
                        Data.PRODUCTIONREPORTS, 10,
                        Data.COMBATTRAININGMATERIAL, 10,
                        Asset.MICROHYDRAULICS, 10,
                        Asset.ELECTROMAGNET, 10
                ), List.of(Engineer.JUDE_NAVARRO, Engineer.UMA_LASZLO),
                Map.of(
                        Modifier.RELOAD_SPEED, "+25%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(RecipeName.SCOPE, new ModuleRecipe(
                RecipeName.SCOPE,
                Map.of(
                        Data.SPECTRALANALYSISDATA, 10,
                        Data.BIOMETRICDATA, 5,
                        Asset.OPTICALLENS, 10,
                        Asset.OPTICALFIBRE, 5
                ), List.of(Engineer.ODEN_GEIGER, Engineer.WELLINGTON_BECK)
        ));
        WEAPON_MODULE_BLUEPRINTS.put(RecipeName.STABILITY, new ModuleRecipe(
                RecipeName.STABILITY,
                Map.of(
                        Data.MININGANALYTICS, 10,
                        Data.RISKASSESSMENTS, 15,
                        Asset.VISCOELASTICPOLYMER, 10,
                        Asset.MICROHYDRAULICS, 10
                ), List.of(Engineer.DOMINO_GREEN, Engineer.ODEN_GEIGER),
                Map.of(
                        Modifier.INSTABILITY, "-50%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(RecipeName.STOWED_RELOADING, new ModuleRecipe(
                RecipeName.STOWED_RELOADING,
                Map.of(
                        Data.DIGITALDESIGNS, 10,
                        Data.OPERATIONALMANUAL, 10,
                        Data.PRODUCTIONSCHEDULE, 10,
                        Asset.CIRCUITBOARD, 5,
                        Asset.ENCRYPTEDMEMORYCHIP, 15
                ), List.of(Engineer.KIT_FOWLER, Engineer.UMA_LASZLO)
        ));
    }

}