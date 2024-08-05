package nl.jixxed.eliteodysseymaterials.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("java:S1192")
public abstract class OdysseyBlueprintConstants {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final Map<OdysseyBlueprintName, UpgradeBlueprint> SUIT_UPGRADES = new EnumMap<>(OdysseyBlueprintName.class);
    private static final Map<OdysseyBlueprintName, UpgradeBlueprint> WEAPON_UPGRADES = new EnumMap<>(OdysseyBlueprintName.class);
    private static final Map<OdysseyBlueprintName, ModuleBlueprint> SUIT_MODULE_BLUEPRINTS = new EnumMap<>(OdysseyBlueprintName.class);
    private static final Map<OdysseyBlueprintName, ModuleBlueprint> WEAPON_MODULE_BLUEPRINTS = new EnumMap<>(OdysseyBlueprintName.class);
    private static final Map<OdysseyBlueprintName, EngineerBlueprint> ENGINEER_UNLOCK_REQUIREMENTS = new EnumMap<>(OdysseyBlueprintName.class);
    public static final Map<BlueprintCategory, Map<OdysseyBlueprintName, ? extends OdysseyBlueprint>> RECIPES = Map.of(
            BlueprintCategory.SUIT_GRADES, SUIT_UPGRADES,
            BlueprintCategory.WEAPON_GRADES, WEAPON_UPGRADES,
            BlueprintCategory.SUIT_MODULES, SUIT_MODULE_BLUEPRINTS,
            BlueprintCategory.WEAPON_MODULES, WEAPON_MODULE_BLUEPRINTS,
            BlueprintCategory.ENGINEER_UNLOCKS, ENGINEER_UNLOCK_REQUIREMENTS
    );

    public static OdysseyBlueprint getRecipe(final BlueprintName<OdysseyBlueprintName> name) {
        OdysseyBlueprint blueprint = WEAPON_MODULE_BLUEPRINTS.get(name);
        if (blueprint != null) {
            return blueprint;
        }
        blueprint = SUIT_MODULE_BLUEPRINTS.get(name);
        if (blueprint != null) {
            return blueprint;
        }
        blueprint = SUIT_UPGRADES.get(name);
        if (blueprint != null) {
            return blueprint;
        }
        blueprint = WEAPON_UPGRADES.get(name);
        if (blueprint != null) {
            return blueprint;
        }
        return ENGINEER_UNLOCK_REQUIREMENTS.get(name);
    }

    public static BlueprintCategory getRecipeCategory(final BlueprintName<OdysseyBlueprintName> blueprintName) {
        return RECIPES.entrySet().stream()
                .filter(recipeCategoryMapEntry -> recipeCategoryMapEntry.getValue().containsKey(blueprintName))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public static Map<OdysseyBlueprintName, Integer> findRecipesContaining(final OdysseyMaterial odysseyMaterial) {
        final Map<OdysseyBlueprintName, Integer> newMap = new EnumMap<>(OdysseyBlueprintName.class);
        RECIPES.values()
                .forEach(recipes -> recipes.entrySet().stream()
                        .filter(stringIngredientsEntry -> stringIngredientsEntry.getValue().getMaterialCollection(odysseyMaterial.getClass()).containsKey(odysseyMaterial))
                        .forEach(entry -> newMap.put(entry.getKey(), entry.getValue().getMaterialCollection(odysseyMaterial.getClass()).get(odysseyMaterial))));
        return newMap;
    }
    public static Craftability getCraftability(final OdysseyBlueprintName odysseyBlueprintName) {
        final OdysseyBlueprint blueprint = OdysseyBlueprintConstants.getRecipe(odysseyBlueprintName);
        if (blueprint instanceof EngineerBlueprint engineerBlueprint) {
            return engineerBlueprint.getCraftability();
        } else {
            final AtomicBoolean hasGoods = new AtomicBoolean(true);
            final AtomicBoolean hasData = new AtomicBoolean(true);
            final AtomicBoolean hasAssets = new AtomicBoolean(true);
            blueprint.getMaterialCollection(Good.class).forEach((material, amountRequired) -> hasGoods.set(hasGoods.get() && (StorageService.getMaterialStorage(material).getTotalValue() - amountRequired) >= 0));
            blueprint.getMaterialCollection(Data.class).forEach((material, amountRequired) -> hasData.set(hasData.get() && (StorageService.getMaterialStorage(material).getTotalValue() - amountRequired) >= 0));
            blueprint.getMaterialCollection(Asset.class).forEach((material, amountRequired) -> hasAssets.set(hasAssets.get() && (StorageService.getMaterialStorage(material).getTotalValue() - amountRequired) >= 0));
            if (!hasGoods.get() || !hasData.get()) {
                return Craftability.NOT_CRAFTABLE;
            } else if (hasGoods.get() && hasData.get() && !hasAssets.get()) {
                return Craftability.CRAFTABLE_WITH_TRADE;
            } else {
                return Craftability.CRAFTABLE;
            }
        }
    }

    private static boolean isBlueprintIngredient(final OdysseyMaterial odysseyMaterial) {
        return SUIT_UPGRADES.values().stream().anyMatch(recipe -> recipe.getMaterialCollection(odysseyMaterial.getClass()).containsKey(odysseyMaterial)) ||
                WEAPON_UPGRADES.values().stream().anyMatch(recipe -> recipe.getMaterialCollection(odysseyMaterial.getClass()).containsKey(odysseyMaterial)) ||
                SUIT_MODULE_BLUEPRINTS.values().stream().anyMatch(recipe -> recipe.getMaterialCollection(odysseyMaterial.getClass()).containsKey(odysseyMaterial)) ||
                WEAPON_MODULE_BLUEPRINTS.values().stream().anyMatch(recipe -> recipe.getMaterialCollection(odysseyMaterial.getClass()).containsKey(odysseyMaterial));
    }

    public static boolean isBlueprintIngredientWithOverride(final OdysseyMaterial odysseyMaterial) {
        return isBlueprintIngredient(odysseyMaterial) || isRelevantOverride(odysseyMaterial);
    }

    public static boolean isNotRelevantAndNotEngineeringIngredient(final OdysseyMaterial odysseyMaterial) {
        return !isBlueprintIngredientWithOverride(odysseyMaterial) && !isEngineeringIngredient(odysseyMaterial);
    }

    public static boolean isNotRelevantWithOverrideAndNotRequiredEngineeringIngredient(final OdysseyMaterial odysseyMaterial) {
        return !isBlueprintIngredientWithOverride(odysseyMaterial) && !isEngineeringIngredientAndNotCompleted(odysseyMaterial);
    }

    public static boolean isNotRelevantAndNotRequiredEngineeringIngredient(final OdysseyMaterial odysseyMaterial) {
        return !isBlueprintIngredient(odysseyMaterial) && !isEngineeringIngredientAndNotCompleted(odysseyMaterial);
    }

    public static boolean isEngineeringOnlyIngredient(final OdysseyMaterial odysseyMaterial) {
        return isEngineeringIngredient(odysseyMaterial) && !isBlueprintIngredientWithOverride(odysseyMaterial);
    }

    public static boolean isEngineeringOrBlueprintIngredient(final OdysseyMaterial odysseyMaterial) {
        return isEngineeringIngredient(odysseyMaterial) || isBlueprintIngredient(odysseyMaterial);
    }

    public static boolean isEngineeringOrBlueprintIngredientWithOverride(final OdysseyMaterial odysseyMaterial) {
        final boolean isEngineerUnlockMaterial = (APPLICATION_STATE.getSoloMode()) ? OdysseyBlueprintConstants.isEngineeringIngredientAndNotCompleted(odysseyMaterial) : OdysseyBlueprintConstants.isEngineeringIngredient(odysseyMaterial);
        return isEngineerUnlockMaterial || isBlueprintIngredientWithOverride(odysseyMaterial);
    }

    public static boolean isEngineeringIngredient(final OdysseyMaterial odysseyMaterial) {
        return !odysseyMaterial.isUnknown() && ENGINEER_UNLOCK_REQUIREMENTS.values().stream().anyMatch(recipe -> recipe.getMaterialCollection(odysseyMaterial.getClass()).containsKey(odysseyMaterial));
    }

    public static boolean isEngineeringIngredientAndNotCompleted(final OdysseyMaterial odysseyMaterial) {
        return ENGINEER_UNLOCK_REQUIREMENTS.values().stream().filter(engineerRecipe -> !engineerRecipe.isCompleted()).anyMatch(recipe -> recipe.getMaterialCollection(odysseyMaterial.getClass()).containsKey(odysseyMaterial));
    }

    private static boolean isRelevantOverride(final OdysseyMaterial odysseyMaterial) {
        final String irrelevantValues = PreferencesService.getPreference(PreferenceConstants.IRRELEVANT_OVERRIDE, "");
        return Arrays.stream(irrelevantValues.split(",")).filter(string -> !string.isEmpty()).map(OdysseyMaterial::subtypeForName).anyMatch(mat -> mat.equals(odysseyMaterial));
    }

    public static Map<OdysseyBlueprintName, ModuleBlueprint> getSuitModuleBlueprints() {
        return SUIT_MODULE_BLUEPRINTS;
    }

    public static Map<OdysseyBlueprintName, ModuleBlueprint> getWeaponModuleBlueprints() {
        return WEAPON_MODULE_BLUEPRINTS;
    }

    static {
        ENGINEER_UNLOCK_REQUIREMENTS.put(OdysseyBlueprintName.ENGINEER_A1, new EngineerBlueprint(
                OdysseyBlueprintName.ENGINEER_A1,
                List.of("ingredient.a1.fly"),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.DOMINO_GREEN)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(OdysseyBlueprintName.ENGINEER_A2, new EngineerBlueprint(
                OdysseyBlueprintName.ENGINEER_A2,
                Map.of(
                        Good.PUSH, 5
                ),
                () -> APPLICATION_STATE.isEngineerKnown(Engineer.KIT_FOWLER)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(OdysseyBlueprintName.ENGINEER_A3, new EngineerBlueprint(
                OdysseyBlueprintName.ENGINEER_A3,
                Map.of(
                        Data.OPINIONPOLLS, 5
                ),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.KIT_FOWLER)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(OdysseyBlueprintName.ENGINEER_A4, new EngineerBlueprint(
                OdysseyBlueprintName.ENGINEER_A4,
                Map.of(
                        Good.SURVEILLANCEEQUIPMENT, 5
                ),
                () -> APPLICATION_STATE.isEngineerKnown(Engineer.YARDEN_BOND)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(OdysseyBlueprintName.ENGINEER_A5, new EngineerBlueprint(
                OdysseyBlueprintName.ENGINEER_A5,
                Map.of(
                        Data.SMEARCAMPAIGNPLANS, 5
                ),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.YARDEN_BOND)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(OdysseyBlueprintName.ENGINEER_B1, new EngineerBlueprint(
                OdysseyBlueprintName.ENGINEER_B1,
                List.of("ingredient.b1.conflict"),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.HERO_FERRARI)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(OdysseyBlueprintName.ENGINEER_B2, new EngineerBlueprint(
                OdysseyBlueprintName.ENGINEER_B2,
                Map.of(
                        Data.SETTLEMENTDEFENCEPLANS, 5
                ),
                () -> APPLICATION_STATE.isEngineerKnown(Engineer.WELLINGTON_BECK)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(OdysseyBlueprintName.ENGINEER_B3, new TotalMaterialEngineerBlueprint(
                OdysseyBlueprintName.ENGINEER_B3,
                Map.of(
                        Data.CLASSICENTERTAINMENT, 15,
                        Data.MULTIMEDIAENTERTAINMENT, 15,
                        Data.CATMEDIA, 15
                ),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.WELLINGTON_BECK),
                15
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(OdysseyBlueprintName.ENGINEER_B4, new EngineerBlueprint(
                OdysseyBlueprintName.ENGINEER_B4,
                Map.of(
                        Good.INSIGHTENTERTAINMENTSUITE, 5
                ),
                () -> APPLICATION_STATE.isEngineerKnown(Engineer.UMA_LASZLO)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(OdysseyBlueprintName.ENGINEER_B5, new EngineerBlueprint(
                OdysseyBlueprintName.ENGINEER_B5,
                List.of("ingredient.b5.sirius"),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.UMA_LASZLO)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(OdysseyBlueprintName.ENGINEER_C1, new EngineerBlueprint(
                OdysseyBlueprintName.ENGINEER_C1,
                List.of("ingredient.c1.restore"),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.JUDE_NAVARRO)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(OdysseyBlueprintName.ENGINEER_C2, new EngineerBlueprint(
                OdysseyBlueprintName.ENGINEER_C2,
                Map.of(
                        Good.GENETICREPAIRMEDS, 5
                ),
                () -> APPLICATION_STATE.isEngineerKnown(Engineer.TERRA_VELASQUEZ)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(OdysseyBlueprintName.ENGINEER_C3, new EngineerBlueprint(
                OdysseyBlueprintName.ENGINEER_C3,
                List.of("ingredient.c3.covert"),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.TERRA_VELASQUEZ)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(OdysseyBlueprintName.ENGINEER_C4, new EngineerBlueprint(
                OdysseyBlueprintName.ENGINEER_C4,
                Map.of(
                        Data.FINANCIALPROJECTIONS, 15
                ),
                () -> APPLICATION_STATE.isEngineerKnown(Engineer.ODEN_GEIGER)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(OdysseyBlueprintName.ENGINEER_C5, new TotalMaterialEngineerBlueprint(
                OdysseyBlueprintName.ENGINEER_C5,
                Map.of(
                        Good.GENETICSAMPLE, 20,
                        Data.EMPLOYEEGENETICDATA, 20,
                        Data.GENETICRESEARCH, 20
                ),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.ODEN_GEIGER),
                20
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(OdysseyBlueprintName.ENGINEER_D1_1, new EngineerBlueprint(
                OdysseyBlueprintName.ENGINEER_D1_1,
                List.of("ingredient.d1.1.colonia.rep"),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.BALTANOS)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(OdysseyBlueprintName.ENGINEER_D1_2, new TotalMaterialEngineerBlueprint(
                OdysseyBlueprintName.ENGINEER_D1_2,
                Map.of(
                        Data.CULINARYRECIPES, 10,
                        Data.COCKTAILRECIPES, 10
                ),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.ROSA_DAYETTE),
                10
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(OdysseyBlueprintName.ENGINEER_D1_3, new EngineerBlueprint(
                OdysseyBlueprintName.ENGINEER_D1_3,
                List.of("ingredient.d1.3.colonia.travel"),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.ELEANOR_BRESA)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(OdysseyBlueprintName.ENGINEER_D2, new OneOfMaterialEngineerBlueprint(
                OdysseyBlueprintName.ENGINEER_D2,
                Map.of(
                        Data.FACTIONASSOCIATES, 10,
                        Data.MANUFACTURINGINSTRUCTIONS, 10,
                        Data.DIGITALDESIGNS, 10
                ),
                () -> APPLICATION_STATE.isEngineerKnown(Engineer.YI_SHEN)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put(OdysseyBlueprintName.ENGINEER_D3, new EngineerBlueprint(
                OdysseyBlueprintName.ENGINEER_D3,
                Map.of(
                        Data.FACTIONASSOCIATES, 10,
                        Data.MANUFACTURINGINSTRUCTIONS, 10,
                        Data.DIGITALDESIGNS, 10
                ),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.YI_SHEN)
        ));
        SUIT_UPGRADES.put(OdysseyBlueprintName.MAVERICK_SUIT_GRADE_1_2, new UpgradeBlueprint(
                OdysseyBlueprintName.MAVERICK_SUIT_GRADE_1_2,
                Map.of(
                        Good.SUITSCHEMATIC, 1,
                        Good.HEALTHMONITOR, 1,
                        Data.MANUFACTURINGINSTRUCTIONS, 1,
                        Asset.CARBONFIBREPLATING, 2,
                        Asset.GRAPHENE, 2
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "1",
                        OdysseyModifier.SHIELD_REGENERATION_RATE, "1.21",
                        OdysseyModifier.SHIELD_HEALTH, "16.5",
                        OdysseyModifier.KINETIC_DAMAGE_RESISTANCE, "-34%",
                        OdysseyModifier.PLASMA_DAMAGE_RESISTANCE, "7%",
                        OdysseyModifier.THERMAL_DAMAGE_RESISTANCE, "58%",
                        OdysseyModifier.EXPLOSIVE_DAMAGE_RESISTANCE, "16%"//OK
                )
        ));
        SUIT_UPGRADES.put(OdysseyBlueprintName.MAVERICK_SUIT_GRADE_2_3, new UpgradeBlueprint(
                OdysseyBlueprintName.MAVERICK_SUIT_GRADE_2_3,
                Map.of(
                        Good.SUITSCHEMATIC, 2,
                        Good.HEALTHMONITOR, 2,
                        Data.MANUFACTURINGINSTRUCTIONS, 2,
                        Asset.CARBONFIBREPLATING, 5,
                        Asset.GRAPHENE,  5
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "2",
                        OdysseyModifier.SHIELD_REGENERATION_RATE, "1.49",
                        OdysseyModifier.SHIELD_HEALTH, "20.3",
                        OdysseyModifier.KINETIC_DAMAGE_RESISTANCE, "-13%",
                        OdysseyModifier.PLASMA_DAMAGE_RESISTANCE, "22%",
                        OdysseyModifier.THERMAL_DAMAGE_RESISTANCE, "64%",
                        OdysseyModifier.EXPLOSIVE_DAMAGE_RESISTANCE, "29%"//OK
                )
        ));
        SUIT_UPGRADES.put(OdysseyBlueprintName.MAVERICK_SUIT_GRADE_3_4, new UpgradeBlueprint(
                OdysseyBlueprintName.MAVERICK_SUIT_GRADE_3_4,
                Map.of(
                        Good.SUITSCHEMATIC, 4,
                        Good.HEALTHMONITOR, 4,
                        Data.MANUFACTURINGINSTRUCTIONS, 4,
                        Asset.CARBONFIBREPLATING, 9,
                        Asset.GRAPHENE, 9
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "3",
                        OdysseyModifier.SHIELD_REGENERATION_RATE, "1.82",
                        OdysseyModifier.SHIELD_HEALTH, "24.9",
                        OdysseyModifier.KINETIC_DAMAGE_RESISTANCE, "6%",
                        OdysseyModifier.PLASMA_DAMAGE_RESISTANCE, "35%",
                        OdysseyModifier.THERMAL_DAMAGE_RESISTANCE, "70%",
                        OdysseyModifier.EXPLOSIVE_DAMAGE_RESISTANCE, "41%"
                )
        ));
        SUIT_UPGRADES.put(OdysseyBlueprintName.MAVERICK_SUIT_GRADE_4_5, new UpgradeBlueprint(
                OdysseyBlueprintName.MAVERICK_SUIT_GRADE_4_5,
                Map.of(
                        Good.SUITSCHEMATIC, 5,
                        Good.HEALTHMONITOR, 5,
                        Data.MANUFACTURINGINSTRUCTIONS, 5,
                        Asset.CARBONFIBREPLATING, 12,
                        Asset.GRAPHENE, 12
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "4",
                        OdysseyModifier.SHIELD_REGENERATION_RATE, "2.23",
                        OdysseyModifier.SHIELD_HEALTH, "30.4",
                        OdysseyModifier.KINETIC_DAMAGE_RESISTANCE, "19%",
                        OdysseyModifier.PLASMA_DAMAGE_RESISTANCE, "45%",
                        OdysseyModifier.THERMAL_DAMAGE_RESISTANCE, "75%",
                        OdysseyModifier.EXPLOSIVE_DAMAGE_RESISTANCE, "50%"//OK
                )
        ));
        SUIT_UPGRADES.put(OdysseyBlueprintName.DOMINATOR_SUIT_GRADE_1_2, new UpgradeBlueprint(
                OdysseyBlueprintName.DOMINATOR_SUIT_GRADE_1_2,
                Map.of(
                        Good.SUITSCHEMATIC, 1,
                        Good.HEALTHMONITOR, 1,
                        Data.MANUFACTURINGINSTRUCTIONS, 1,
                        Asset.TITANIUMPLATING, 2,
                        Asset.GRAPHENE, 2
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "1",
                        OdysseyModifier.SHIELD_REGENERATION_RATE, "1.34",
                        OdysseyModifier.SHIELD_HEALTH, "18.3",
                        OdysseyModifier.KINETIC_DAMAGE_RESISTANCE, "-26%",
                        OdysseyModifier.PLASMA_DAMAGE_RESISTANCE, "16%",
                        OdysseyModifier.THERMAL_DAMAGE_RESISTANCE, "66%",
                        OdysseyModifier.EXPLOSIVE_DAMAGE_RESISTANCE, "16%"//OK
                )
        ));
        SUIT_UPGRADES.put(OdysseyBlueprintName.DOMINATOR_SUIT_GRADE_2_3, new UpgradeBlueprint(
                OdysseyBlueprintName.DOMINATOR_SUIT_GRADE_2_3,
                Map.of(
                        Good.SUITSCHEMATIC, 2,
                        Good.HEALTHMONITOR, 2,
                        Data.MANUFACTURINGINSTRUCTIONS, 2,
                        Asset.TITANIUMPLATING, 5,
                        Asset.GRAPHENE, 5
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "2",
                        OdysseyModifier.SHIELD_REGENERATION_RATE, "1.65",
                        OdysseyModifier.SHIELD_HEALTH, "22.5",
                        OdysseyModifier.KINETIC_DAMAGE_RESISTANCE, "-7%",
                        OdysseyModifier.PLASMA_DAMAGE_RESISTANCE, "29%",
                        OdysseyModifier.THERMAL_DAMAGE_RESISTANCE, "72%",
                        OdysseyModifier.EXPLOSIVE_DAMAGE_RESISTANCE, "29%"//OK
                )
        ));
        SUIT_UPGRADES.put(OdysseyBlueprintName.DOMINATOR_SUIT_GRADE_3_4, new UpgradeBlueprint(
                OdysseyBlueprintName.DOMINATOR_SUIT_GRADE_3_4,
                Map.of(
                        Good.SUITSCHEMATIC, 4,
                        Good.HEALTHMONITOR, 4,
                        Data.MANUFACTURINGINSTRUCTIONS, 4,
                        Asset.TITANIUMPLATING, 9,
                        Asset.GRAPHENE, 9
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "3",
                        OdysseyModifier.SHIELD_REGENERATION_RATE, "2.02",
                        OdysseyModifier.SHIELD_HEALTH, "27.6",
                        OdysseyModifier.KINETIC_DAMAGE_RESISTANCE, "19%",
                        OdysseyModifier.PLASMA_DAMAGE_RESISTANCE, "46%",
                        OdysseyModifier.THERMAL_DAMAGE_RESISTANCE, "78%",
                        OdysseyModifier.EXPLOSIVE_DAMAGE_RESISTANCE, "46%"
                )
        ));
        SUIT_UPGRADES.put(OdysseyBlueprintName.DOMINATOR_SUIT_GRADE_4_5, new UpgradeBlueprint(
                OdysseyBlueprintName.DOMINATOR_SUIT_GRADE_4_5,
                Map.of(
                        Good.SUITSCHEMATIC, 5,
                        Good.HEALTHMONITOR, 5,
                        Data.MANUFACTURINGINSTRUCTIONS, 5,
                        Asset.TITANIUMPLATING, 12,
                        Asset.GRAPHENE, 12
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "4",
                        OdysseyModifier.SHIELD_REGENERATION_RATE, "2.48",
                        OdysseyModifier.SHIELD_HEALTH, "33.8",
                        OdysseyModifier.KINETIC_DAMAGE_RESISTANCE, "25%",
                        OdysseyModifier.PLASMA_DAMAGE_RESISTANCE, "50%",
                        OdysseyModifier.THERMAL_DAMAGE_RESISTANCE, "80%",
                        OdysseyModifier.EXPLOSIVE_DAMAGE_RESISTANCE, "50%"//OK
                )
        ));
        SUIT_UPGRADES.put(OdysseyBlueprintName.ARTEMIS_SUIT_GRADE_1_2, new UpgradeBlueprint(
                OdysseyBlueprintName.ARTEMIS_SUIT_GRADE_1_2,
                Map.of(
                        Good.SUITSCHEMATIC, 1,
                        Good.HEALTHMONITOR, 1,
                        Data.MANUFACTURINGINSTRUCTIONS, 1,
                        Asset.AEROGEL, 2,
                        Asset.GRAPHENE, 2
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "1",
                        OdysseyModifier.SHIELD_REGENERATION_RATE, "1.07",
                        OdysseyModifier.SHIELD_HEALTH, "14.7",
                        OdysseyModifier.KINETIC_DAMAGE_RESISTANCE, "-42%",
                        OdysseyModifier.PLASMA_DAMAGE_RESISTANCE, "0%",
                        OdysseyModifier.THERMAL_DAMAGE_RESISTANCE, "50%",
                        OdysseyModifier.EXPLOSIVE_DAMAGE_RESISTANCE, "16%"//OK
                )
        ));
        SUIT_UPGRADES.put(OdysseyBlueprintName.ARTEMIS_SUIT_GRADE_2_3, new UpgradeBlueprint(
                OdysseyBlueprintName.ARTEMIS_SUIT_GRADE_2_3,
                Map.of(
                        Good.SUITSCHEMATIC, 2,
                        Good.HEALTHMONITOR, 2,
                        Data.MANUFACTURINGINSTRUCTIONS, 2,
                        Asset.AEROGEL, 5,
                        Asset.GRAPHENE, 5
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "2",
                        OdysseyModifier.SHIELD_REGENERATION_RATE, "1.32",
                        OdysseyModifier.SHIELD_HEALTH, "18.0",
                        OdysseyModifier.KINETIC_DAMAGE_RESISTANCE, "-21%",
                        OdysseyModifier.PLASMA_DAMAGE_RESISTANCE, "14%",
                        OdysseyModifier.THERMAL_DAMAGE_RESISTANCE, "57%",
                        OdysseyModifier.EXPLOSIVE_DAMAGE_RESISTANCE, "29%"//OK
                )
        ));
        SUIT_UPGRADES.put(OdysseyBlueprintName.ARTEMIS_SUIT_GRADE_3_4, new UpgradeBlueprint(
                OdysseyBlueprintName.ARTEMIS_SUIT_GRADE_3_4,
                Map.of(
                        Good.SUITSCHEMATIC, 4,
                        Good.HEALTHMONITOR, 4,
                        Data.MANUFACTURINGINSTRUCTIONS, 4,
                        Asset.AEROGEL, 9,
                        Asset.GRAPHENE, 9
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "3",
                        OdysseyModifier.SHIELD_REGENERATION_RATE, "1.62",
                        OdysseyModifier.SHIELD_HEALTH, "22.1",
                        OdysseyModifier.KINETIC_DAMAGE_RESISTANCE, "0%",
                        OdysseyModifier.PLASMA_DAMAGE_RESISTANCE, "29%",
                        OdysseyModifier.THERMAL_DAMAGE_RESISTANCE, "65%",
                        OdysseyModifier.EXPLOSIVE_DAMAGE_RESISTANCE, "41%"
                )
        ));
        SUIT_UPGRADES.put(OdysseyBlueprintName.ARTEMIS_SUIT_GRADE_4_5, new UpgradeBlueprint(
                OdysseyBlueprintName.ARTEMIS_SUIT_GRADE_4_5,
                Map.of(
                        Good.SUITSCHEMATIC, 5,
                        Good.HEALTHMONITOR, 5,
                        Data.MANUFACTURINGINSTRUCTIONS, 5,
                        Asset.AEROGEL, 12,
                        Asset.GRAPHENE, 12
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "4",
                        OdysseyModifier.SHIELD_REGENERATION_RATE, "1.98",
                        OdysseyModifier.SHIELD_HEALTH, "27.0",
                        OdysseyModifier.KINETIC_DAMAGE_RESISTANCE, "14%",
                        OdysseyModifier.PLASMA_DAMAGE_RESISTANCE, "39%",
                        OdysseyModifier.THERMAL_DAMAGE_RESISTANCE, "70%",
                        OdysseyModifier.EXPLOSIVE_DAMAGE_RESISTANCE, "50%"//OK
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.ADDED_MELEE_DAMAGE, new ModuleBlueprint(
                OdysseyBlueprintName.ADDED_MELEE_DAMAGE,
                Map.of(
                        Data.COMBATTRAININGMATERIAL, 10,
                        Data.COMBATANTPERFORMANCE, 10,
                        Asset.EPINEPHRINE, 10,
                        Asset.MICROTHRUSTERS, 15
                ), List.of(Engineer.JUDE_NAVARRO, Engineer.KIT_FOWLER, Engineer.ELEANOR_BRESA),
                Map.of(
                        OdysseyModifier.WEAPON_MELEE_DAMAGE_MULTIPLIER, "+150%",
                        OdysseyModifier.FISTS_MELEE_DAMAGE_MULTIPLIER, "+150%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.COMBAT_MOVEMENT_SPEED, new ModuleBlueprint(
                OdysseyBlueprintName.COMBAT_MOVEMENT_SPEED,
                Map.of(
                        Data.EVACUATIONPROTOCOLS, 10,
                        Data.GENETICRESEARCH, 5,
                        Asset.EPINEPHRINE, 10,
                        Asset.PHNEUTRALISER, 15
                ), List.of(Engineer.TERRA_VELASQUEZ, Engineer.YARDEN_BOND, Engineer.BALTANOS),
                Map.of(
                        OdysseyModifier.MOVEMENT_SPEED_PENALTY, "-100%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.DAMAGE_RESISTANCE, new ModuleBlueprint(
                OdysseyBlueprintName.DAMAGE_RESISTANCE,
                Map.of(
                        Data.WEAPONINVENTORY, 10,
                        Data.BALLISTICSDATA, 10,
                        Asset.TITANIUMPLATING, 5,
                        Asset.EPOXYADHESIVE, 15,
                        Asset.CARBONFIBREPLATING, 5
                ), List.of(Engineer.JUDE_NAVARRO, Engineer.UMA_LASZLO, Engineer.ELEANOR_BRESA),
                Map.of(
                        OdysseyModifier.EXPLOSIVE_DAMAGE_REDUCTION, "+10%",
                        OdysseyModifier.PLASMA_DAMAGE_REDUCTION, "+10%",
                        OdysseyModifier.KINETIC_DAMAGE_REDUCTION, "+10%",
                        OdysseyModifier.THERMAL_DAMAGE_REDUCTION, "+10%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.ENHANCED_TRACKING, new ModuleBlueprint(
                OdysseyBlueprintName.ENHANCED_TRACKING,
                Map.of(
                        Data.TOPOGRAPHICALSURVEYS, 10,
                        Data.STELLARACTIVITYLOGS, 10,
                        Data.SPECTRALANALYSISDATA, 10,
                        Asset.TRANSMITTER, 5,
                        Asset.CIRCUITBOARD, 5
                ), List.of(Engineer.DOMINO_GREEN, Engineer.ODEN_GEIGER, Engineer.ROSA_DAYETTE),
                Map.of(
                        OdysseyModifier.LOS_ANALYSIS_RANGE, "+100%",
                        OdysseyModifier.LOS_ANALYSIS_TIME, "-100%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.EXTRA_AMMO_CAPACITY, new ModuleBlueprint(
                OdysseyBlueprintName.EXTRA_AMMO_CAPACITY,
                Map.of(
                        Data.RECYCLINGLOGS, 15,
                        Data.WEAPONTESTDATA, 10,
                        Data.PRODUCTIONREPORTS, 10,
                        Asset.WEAPONCOMPONENT, 5
                ), List.of(Engineer.JUDE_NAVARRO, Engineer.KIT_FOWLER, Engineer.ELEANOR_BRESA),
                Map.of(
                        OdysseyModifier.AMMO_CAPACITY_MULTIPLIER, "+50%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.EXTRA_BACKPACK_CAPACITY, new ModuleBlueprint(
                OdysseyBlueprintName.EXTRA_BACKPACK_CAPACITY,
                Map.of(
                        Data.WEAPONINVENTORY, 10,
                        Data.CHEMICALINVENTORY, 10,
                        Data.DIGITALDESIGNS, 10,
                        Asset.EPOXYADHESIVE, 10,
                        Asset.MEMORYCHIP, 5
                ), List.of(Engineer.DOMINO_GREEN, Engineer.WELLINGTON_BECK, Engineer.ROSA_DAYETTE),
                Map.of(
                        OdysseyModifier.BACKPACK_COMPONENT_CAPACITY, "+100%",
                        OdysseyModifier.BACKPACK_ITEM_CAPACITY, "+100%",
                        OdysseyModifier.BACKPACK_DATA_CAPACITY, "+100%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.FASTER_SHIELD_REGEN, new ModuleBlueprint(
                OdysseyBlueprintName.FASTER_SHIELD_REGEN,
                Map.of(
                        Data.REACTOROUTPUTREVIEW, 10,
                        Asset.IONBATTERY, 5,
                        Asset.MICROTRANSFORMER, 15,
                        Asset.ELECTRICALWIRING, 15
                ), List.of(Engineer.KIT_FOWLER, Engineer.UMA_LASZLO, Engineer.ELEANOR_BRESA),
                Map.of(
                        OdysseyModifier.SHIELD_REGENERATION_RATE, "+25%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.IMPROVED_BATTERY_CAPACITY, new ModuleBlueprint(
                OdysseyBlueprintName.IMPROVED_BATTERY_CAPACITY,
                Map.of(
                        Data.REACTOROUTPUTREVIEW, 10,
                        Data.MAINTENANCELOGS, 15,
                        Asset.IONBATTERY, 5,
                        Asset.MICROSUPERCAPACITOR, 10,
                        Asset.ELECTRICALWIRING, 10
                ), List.of(Engineer.ODEN_GEIGER, Engineer.WELLINGTON_BECK, Engineer.ROSA_DAYETTE),
                Map.of(
                        OdysseyModifier.BATTERY_ENERGY_CAPACITY, "+50%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.IMPROVED_JUMP_ASSIST, new ModuleBlueprint(
                OdysseyBlueprintName.IMPROVED_JUMP_ASSIST,
                Map.of(
                        Good.GMEDS, 10,
                        Data.TOPOGRAPHICALSURVEYS, 10,
                        Asset.MICROTHRUSTERS, 5,
                        Asset.MOTOR, 10
                ), List.of(Engineer.HERO_FERRARI, Engineer.YARDEN_BOND, Engineer.BALTANOS),
                Map.of(
                        OdysseyModifier.JUMP_ASSIST_BOOST_DURATION, "+33%",
                        OdysseyModifier.JUMP_ASSIST_BOOST_RECHARGE_DURATION, "-33%",
                        OdysseyModifier.JUMP_ASSIST_BATTERY_CONSUMPTION, "-50%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.INCREASED_AIR_RESERVES, new ModuleBlueprint(
                OdysseyBlueprintName.INCREASED_AIR_RESERVES,
                Map.of(
                        Data.PHARMACEUTICALPATENTS, 5,
                        Data.AIRQUALITYREPORTS, 15,
                        Asset.OXYGENICBACTERIA, 10,
                        Asset.PHNEUTRALISER, 15
                ), List.of(Engineer.HERO_FERRARI, Engineer.TERRA_VELASQUEZ, Engineer.BALTANOS),
                Map.of(
                        OdysseyModifier.AIR_CAPACITY, "+400%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.INCREASED_SPRINT_DURATION, new ModuleBlueprint(
                OdysseyBlueprintName.INCREASED_SPRINT_DURATION,
                Map.of(
                        Data.TROOPDEPLOYMENTRECORDS, 5,
                        Data.GENESEQUENCINGDATA, 5,
                        Data.MEDICALTRIALRECORDS, 5,
                        Asset.OXYGENICBACTERIA, 10,
                        Asset.CHEMICALCATALYST, 15
                ), List.of(Engineer.HERO_FERRARI, Engineer.TERRA_VELASQUEZ, Engineer.BALTANOS),
                Map.of(
                        OdysseyModifier.SPRINT_DURATION, "+100%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.NIGHT_VISION, new ModuleBlueprint(
                OdysseyBlueprintName.NIGHT_VISION,
                Map.of(
                        Good.SURVEILLANCEEQUIPMENT, 10,
                        Data.SURVEILLEANCELOGS, 5,
                        Data.NOCDATA, 5,
                        Data.RADIOACTIVITYDATA, 5,
                        Asset.CIRCUITSWITCH, 10
                ), List.of(Engineer.ODEN_GEIGER, Engineer.YI_SHEN),
                Collections.emptyMap()
        ));
        SUIT_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.QUIETER_FOOTSTEPS, new ModuleBlueprint(
                OdysseyBlueprintName.QUIETER_FOOTSTEPS,
                Map.of(
                        Data.SETTLEMENTASSAULTPLANS, 5,
                        Data.TACTICALPLANS, 10,
                        Data.PATROLROUTES, 10,
                        Asset.MICROHYDRAULICS, 5,
                        Asset.VISCOELASTICPOLYMER, 15
                ), List.of(Engineer.YARDEN_BOND, Engineer.YI_SHEN),
                Map.of(
                        OdysseyModifier.FOOTSTEP_AUDIBLE_RANGE_MULTIPLIER, "-50%"
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.REDUCED_TOOL_BATTERY_CONSUMPTION, new ModuleBlueprint(
                OdysseyBlueprintName.REDUCED_TOOL_BATTERY_CONSUMPTION,
                Map.of(
                        Data.REACTOROUTPUTREVIEW, 10,
                        Asset.ELECTRICALWIRING, 15,
                        Asset.ELECTRICALFUSE, 5,
                        Asset.MICROTRANSFORMER, 10
                ), List.of(Engineer.DOMINO_GREEN, Engineer.WELLINGTON_BECK, Engineer.ROSA_DAYETTE),
                Map.of(
                        OdysseyModifier.TOOL_ENERGY_DRAIN_MULTIPLIER, "-50%"
                )
        ));


        WEAPON_UPGRADES.put(OdysseyBlueprintName.KARMA_1_2, new UpgradeBlueprint(
                OdysseyBlueprintName.KARMA_1_2,
                Map.of(
                        Good.WEAPONSCHEMATIC, 1,
                        Good.COMPRESSIONLIQUEFIEDGAS, 1,
                        Data.MANUFACTURINGINSTRUCTIONS, 1,
                        Asset.WEAPONCOMPONENT, 2,
                        Asset.TUNGSTENCARBIDE, 2
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "1"
                )
        ));

        WEAPON_UPGRADES.put(OdysseyBlueprintName.KARMA_2_3, new UpgradeBlueprint(
                OdysseyBlueprintName.KARMA_2_3,
                Map.of(
                        Good.WEAPONSCHEMATIC, 2,
                        Good.COMPRESSIONLIQUEFIEDGAS, 2,
                        Data.MANUFACTURINGINSTRUCTIONS, 2,
                        Asset.WEAPONCOMPONENT, 5,
                        Asset.TUNGSTENCARBIDE, 5
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "2"
                )
        ));
        WEAPON_UPGRADES.put(OdysseyBlueprintName.KARMA_3_4, new UpgradeBlueprint(
                OdysseyBlueprintName.KARMA_3_4,
                Map.of(
                        Good.WEAPONSCHEMATIC, 4,
                        Good.COMPRESSIONLIQUEFIEDGAS, 4,
                        Data.MANUFACTURINGINSTRUCTIONS, 4,
                        Asset.WEAPONCOMPONENT, 9,
                        Asset.TUNGSTENCARBIDE, 9
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "3"
                )
        ));
        WEAPON_UPGRADES.put(OdysseyBlueprintName.KARMA_4_5, new UpgradeBlueprint(
                OdysseyBlueprintName.KARMA_4_5,
                Map.of(
                        Good.WEAPONSCHEMATIC, 5,
                        Good.COMPRESSIONLIQUEFIEDGAS, 5,
                        Data.MANUFACTURINGINSTRUCTIONS, 5,
                        Asset.WEAPONCOMPONENT, 12,
                        Asset.TUNGSTENCARBIDE, 12
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "4"
                )
        ));

        WEAPON_UPGRADES.put(OdysseyBlueprintName.TK_1_2, new UpgradeBlueprint(
                OdysseyBlueprintName.TK_1_2,
                Map.of(
                        Good.WEAPONSCHEMATIC, 1,
                        Good.IONISEDGAS, 1,
                        Data.MANUFACTURINGINSTRUCTIONS, 1,
                        Asset.MICROELECTRODE, 2,
                        Asset.OPTICALFIBRE, 2
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "1"
                )
        ));

        WEAPON_UPGRADES.put(OdysseyBlueprintName.TK_2_3, new UpgradeBlueprint(
                OdysseyBlueprintName.TK_2_3,
                Map.of(
                        Good.WEAPONSCHEMATIC, 2,
                        Good.IONISEDGAS, 2,
                        Data.MANUFACTURINGINSTRUCTIONS, 2,
                        Asset.MICROELECTRODE, 5,
                        Asset.OPTICALFIBRE, 5
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "2"
                )
        ));
        WEAPON_UPGRADES.put(OdysseyBlueprintName.TK_3_4, new UpgradeBlueprint(
                OdysseyBlueprintName.TK_3_4,
                Map.of(
                        Good.WEAPONSCHEMATIC, 4,
                        Good.IONISEDGAS, 4,
                        Data.MANUFACTURINGINSTRUCTIONS, 4,
                        Asset.MICROELECTRODE, 9,
                        Asset.OPTICALFIBRE, 9
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "3"
                )
        ));
        WEAPON_UPGRADES.put(OdysseyBlueprintName.TK_4_5, new UpgradeBlueprint(
                OdysseyBlueprintName.TK_4_5,
                Map.of(
                        Good.WEAPONSCHEMATIC, 5,
                        Good.IONISEDGAS, 5,
                        Data.MANUFACTURINGINSTRUCTIONS, 5,
                        Asset.MICROELECTRODE, 12,
                        Asset.OPTICALFIBRE, 12
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "4"
                )
        ));

        WEAPON_UPGRADES.put(OdysseyBlueprintName.MANTICORE_1_2, new UpgradeBlueprint(
                OdysseyBlueprintName.MANTICORE_1_2,
                Map.of(
                        Good.WEAPONSCHEMATIC, 1,
                        Good.IONISEDGAS, 1,
                        Data.MANUFACTURINGINSTRUCTIONS, 1,
                        Asset.MICROELECTRODE, 2,
                        Asset.CHEMICALSUPERBASE, 2
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "1"
                )
        ));

        WEAPON_UPGRADES.put(OdysseyBlueprintName.MANTICORE_2_3, new UpgradeBlueprint(
                OdysseyBlueprintName.MANTICORE_2_3,
                Map.of(
                        Good.WEAPONSCHEMATIC, 2,
                        Good.IONISEDGAS, 2,
                        Data.MANUFACTURINGINSTRUCTIONS, 2,
                        Asset.MICROELECTRODE, 5,
                        Asset.CHEMICALSUPERBASE, 5
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "2"
                )
        ));
        WEAPON_UPGRADES.put(OdysseyBlueprintName.MANTICORE_3_4, new UpgradeBlueprint(
                OdysseyBlueprintName.MANTICORE_3_4,
                Map.of(
                        Good.WEAPONSCHEMATIC, 4,
                        Good.IONISEDGAS, 4,
                        Data.MANUFACTURINGINSTRUCTIONS, 4,
                        Asset.MICROELECTRODE, 9,
                        Asset.CHEMICALSUPERBASE, 9
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "3"
                )
        ));
        WEAPON_UPGRADES.put(OdysseyBlueprintName.MANTICORE_4_5, new UpgradeBlueprint(
                OdysseyBlueprintName.MANTICORE_4_5,
                Map.of(
                        Good.WEAPONSCHEMATIC, 5,
                        Good.IONISEDGAS, 5,
                        Data.MANUFACTURINGINSTRUCTIONS, 5,
                        Asset.MICROELECTRODE, 12,
                        Asset.CHEMICALSUPERBASE, 12
                ),
                Map.of(
                        OdysseyModifier.ENGINEER_MODIFICATION_SLOTS, "4"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.AUDIO_MASKING, new ModuleBlueprint(
                OdysseyBlueprintName.AUDIO_MASKING,
                Map.of(
                        Data.AUDIOLOGS, 5,
                        Data.PATROLROUTES, 10,
                        Asset.SCRAMBLER, 10,
                        Asset.TRANSMITTER, 15,
                        Asset.CIRCUITBOARD, 5
                ), List.of(Engineer.YARDEN_BOND, Engineer.YI_SHEN)
        ));
        WEAPON_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.FASTER_HANDLING, new ModuleBlueprint(
                OdysseyBlueprintName.FASTER_HANDLING,
                Map.of(
                        Data.OPERATIONALMANUAL, 10,
                        Data.COMBATANTPERFORMANCE, 10,
                        Data.COMBATTRAININGMATERIAL, 10,
                        Asset.VISCOELASTICPOLYMER, 5
                ), List.of(Engineer.HERO_FERRARI, Engineer.YARDEN_BOND, Engineer.BALTANOS),
                Map.of(
                        OdysseyModifier.HANDLING_SPEED, "+30-50%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.GREATER_RANGE_KINETIC, new ModuleBlueprint(
                OdysseyBlueprintName.GREATER_RANGE_KINETIC,
                Map.of(
                        Data.BALLISTICSDATA, 10,
                        Data.TOPOGRAPHICALSURVEYS, 10,
                        Asset.METALCOIL, 10,
                        Asset.RDX, 10,
                        Asset.WEAPONCOMPONENT, 5

                ), List.of(Engineer.DOMINO_GREEN, Engineer.WELLINGTON_BECK, Engineer.ROSA_DAYETTE),
                Map.of(
                        OdysseyModifier.WEAPON_EFFECTIVE_RANGE, "+50%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.GREATER_RANGE_LASER, new ModuleBlueprint(
                OdysseyBlueprintName.GREATER_RANGE_LASER,
                Map.of(
                        Data.STELLARACTIVITYLOGS, 10,
                        Data.RISKASSESSMENTS, 15,
                        Asset.OPTICALLENS, 5,
                        Asset.MICROTRANSFORMER, 15,
                        Asset.CIRCUITBOARD, 5
                ), List.of(Engineer.DOMINO_GREEN, Engineer.WELLINGTON_BECK, Engineer.ROSA_DAYETTE),
                Map.of(
                        OdysseyModifier.WEAPON_EFFECTIVE_RANGE, "+50%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.GREATER_RANGE_PLASMA, new ModuleBlueprint(
                OdysseyBlueprintName.GREATER_RANGE_PLASMA,
                Map.of(
                        Data.CHEMICALFORMULAE, 10,
                        Data.MINERALSURVEY, 15,
                        Asset.ELECTROMAGNET, 10,
                        Asset.MOTOR, 5,
                        Asset.ELECTRICALFUSE, 5

                ), List.of(Engineer.DOMINO_GREEN, Engineer.WELLINGTON_BECK, Engineer.ROSA_DAYETTE),
                Map.of(
                        OdysseyModifier.WEAPON_EFFECTIVE_RANGE, "+50%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.HEADSHOT_DAMAGE_KINETIC, new ModuleBlueprint(
                OdysseyBlueprintName.HEADSHOT_DAMAGE_KINETIC,
                Map.of(
                        Data.WEAPONTESTDATA, 10,
                        Data.MEDICALRECORDS, 5,
                        Asset.CHEMICALCATALYST, 10,
                        Asset.RDX, 15,
                        Asset.WEAPONCOMPONENT, 5
                ), List.of(Engineer.UMA_LASZLO, Engineer.YI_SHEN),
                Map.of(
                        OdysseyModifier.HEADSHOT_DAMAGE_MULTIPLIER, "+50%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.HEADSHOT_DAMAGE_LASER, new ModuleBlueprint(
                OdysseyBlueprintName.HEADSHOT_DAMAGE_LASER,
                Map.of(
                        Data.SPECTRALANALYSISDATA, 10,
                        Data.BIOMETRICDATA, 5,
                        Asset.IONBATTERY, 10,
                        Asset.OPTICALLENS, 5,
                        Asset.SCRAMBLER, 10
                ), List.of(Engineer.UMA_LASZLO, Engineer.YI_SHEN),
                Map.of(
                        OdysseyModifier.HEADSHOT_DAMAGE_MULTIPLIER, "+50%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.HEADSHOT_DAMAGE_PLASMA, new ModuleBlueprint(
                OdysseyBlueprintName.HEADSHOT_DAMAGE_PLASMA,
                Map.of(
                        Data.CHEMICALEXPERIMENTDATA, 10,
                        Data.BLOODTESTRESULTS, 5,
                        Asset.IONBATTERY, 10,
                        Asset.ELECTROMAGNET, 10,
                        Asset.MICROSUPERCAPACITOR, 15
                ), List.of(Engineer.UMA_LASZLO, Engineer.YI_SHEN),
                Map.of(
                        OdysseyModifier.HEADSHOT_DAMAGE_MULTIPLIER, "+50%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.HIGHER_ACCURACY_KINETIC, new ModuleBlueprint(
                OdysseyBlueprintName.HIGHER_ACCURACY_KINETIC,
                Map.of(
                        Data.EXTRACTIONYIELDDATA, 10,
                        Data.BIOMETRICDATA, 5,
                        Data.COMBATANTPERFORMANCE, 10,
                        Asset.RDX, 10,
                        Asset.VISCOELASTICPOLYMER, 10
                ), List.of(Engineer.YARDEN_BOND, Engineer.TERRA_VELASQUEZ, Engineer.BALTANOS),
                Map.of(
                        OdysseyModifier.HIP_FIRE_ACCURACY, "40-43%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.HIGHER_ACCURACY_LASER, new ModuleBlueprint(
                OdysseyBlueprintName.HIGHER_ACCURACY_LASER,
                Map.of(
                        Data.RADIOACTIVITYDATA, 5,
                        Data.COMBATANTPERFORMANCE, 10,
                        Asset.OPTICALLENS, 5,
                        Asset.ELECTRICALWIRING, 15,
                        Asset.METALCOIL, 10
                ), List.of(Engineer.YARDEN_BOND, Engineer.TERRA_VELASQUEZ, Engineer.BALTANOS),
                Map.of(
                        OdysseyModifier.HIP_FIRE_ACCURACY, "40-43%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.HIGHER_ACCURACY_PLASMA, new ModuleBlueprint(
                OdysseyBlueprintName.HIGHER_ACCURACY_PLASMA,
                Map.of(
                        Data.CHEMICALPATENTS, 5,
                        Data.COMBATANTPERFORMANCE, 10,
                        Asset.CHEMICALCATALYST, 10,
                        Asset.ELECTROMAGNET, 10,
                        Asset.METALCOIL, 10
                ), List.of(Engineer.YARDEN_BOND, Engineer.TERRA_VELASQUEZ, Engineer.BALTANOS),
                Map.of(
                        OdysseyModifier.HIP_FIRE_ACCURACY, "40-43%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.MAGAZINE_SIZE, new ModuleBlueprint(
                OdysseyBlueprintName.MAGAZINE_SIZE,
                Map.of(
                        Data.WEAPONTESTDATA, 10,
                        Data.SECURITYEXPENSES, 5,
                        Asset.WEAPONCOMPONENT, 5,
                        Asset.TUNGSTENCARBIDE, 5,
                        Asset.METALCOIL, 10

                ), List.of(Engineer.JUDE_NAVARRO, Engineer.KIT_FOWLER, Engineer.ELEANOR_BRESA),
                Map.of(
                        OdysseyModifier.MAGAZINE_SIZE, "+50%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.NOISE_SUPPRESSOR, new ModuleBlueprint(
                OdysseyBlueprintName.NOISE_SUPPRESSOR,
                Map.of(
                        Data.ATMOSPHERICDATA, 10,
                        Data.MININGANALYTICS, 10,
                        Asset.VISCOELASTICPOLYMER, 15,
                        Asset.WEAPONCOMPONENT, 5
                ), List.of(Engineer.HERO_FERRARI, Engineer.TERRA_VELASQUEZ, Engineer.BALTANOS)
        ));
        WEAPON_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.RELOAD_SPEED, new ModuleBlueprint(
                OdysseyBlueprintName.RELOAD_SPEED,
                Map.of(
                        Data.OPERATIONALMANUAL, 10,
                        Data.PRODUCTIONREPORTS, 10,
                        Data.COMBATTRAININGMATERIAL, 10,
                        Asset.MICROHYDRAULICS, 10,
                        Asset.ELECTROMAGNET, 10
                ), List.of(Engineer.JUDE_NAVARRO, Engineer.UMA_LASZLO, Engineer.ELEANOR_BRESA),
                Map.of(
                        OdysseyModifier.RELOAD_SPEED, "+25%",
                        OdysseyModifier.RELOAD_SPEED_APHELION, "+20%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.SCOPE, new ModuleBlueprint(
                OdysseyBlueprintName.SCOPE,
                Map.of(
                        Data.SPECTRALANALYSISDATA, 10,
                        Data.BIOMETRICDATA, 5,
                        Asset.OPTICALLENS, 10,
                        Asset.OPTICALFIBRE, 5
                ), List.of(Engineer.ODEN_GEIGER, Engineer.WELLINGTON_BECK, Engineer.ROSA_DAYETTE)
        ));
        WEAPON_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.STABILITY, new ModuleBlueprint(
                OdysseyBlueprintName.STABILITY,
                Map.of(
                        Data.MININGANALYTICS, 10,
                        Data.RISKASSESSMENTS, 15,
                        Asset.VISCOELASTICPOLYMER, 10,
                        Asset.MICROHYDRAULICS, 10
                ), List.of(Engineer.DOMINO_GREEN, Engineer.ODEN_GEIGER, Engineer.ROSA_DAYETTE),
                Map.of(
                        OdysseyModifier.INSTABILITY, "-50%"
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put(OdysseyBlueprintName.STOWED_RELOADING, new ModuleBlueprint(
                OdysseyBlueprintName.STOWED_RELOADING,
                Map.of(
                        Data.DIGITALDESIGNS, 10,
                        Data.OPERATIONALMANUAL, 10,
                        Data.PRODUCTIONSCHEDULE, 10,
                        Asset.CIRCUITBOARD, 5,
                        Asset.ENCRYPTEDMEMORYCHIP, 15
                ), List.of(Engineer.KIT_FOWLER, Engineer.UMA_LASZLO, Engineer.ELEANOR_BRESA)
        ));
    }

}