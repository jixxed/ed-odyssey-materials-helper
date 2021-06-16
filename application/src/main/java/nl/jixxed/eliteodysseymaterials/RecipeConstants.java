package nl.jixxed.eliteodysseymaterials;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.EngineerRecipe;
import nl.jixxed.eliteodysseymaterials.domain.ModuleRecipe;
import nl.jixxed.eliteodysseymaterials.domain.Recipe;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class RecipeConstants {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    public static final Map<String, Recipe> SUIT_UPGRADES = new HashMap<>();
    public static final Map<String, Recipe> WEAPON_UPGRADES = new HashMap<>();
    public static final Map<String, ModuleRecipe> SUIT_MODULE_BLUEPRINTS = new HashMap<>();
    public static final Map<String, ModuleRecipe> WEAPON_MODULE_BLUEPRINTS = new HashMap<>();
    public static final Map<String, EngineerRecipe> ENGINEER_UNLOCK_REQUIREMENTS = new HashMap<>();
    public static final Map<String, Map<String, ? extends Recipe>> RECIPES = Map.of(
            "Suit Grades", SUIT_UPGRADES,
            "Weapon Grades", WEAPON_UPGRADES,
            "Suit Modules", SUIT_MODULE_BLUEPRINTS,
            "Weapon Modules", WEAPON_MODULE_BLUEPRINTS,
            "Engineer Unlocks", ENGINEER_UNLOCK_REQUIREMENTS
    );

    public static String findRecipesContaining(final Material material) {
        final List<String> recipesList = RECIPES.values().stream()
                .map(recipes -> recipes.entrySet().stream()
                        .filter(stringIngredientsEntry -> stringIngredientsEntry.getValue().getMaterialCollection(material.getClass()).containsKey(material))
                        .map(entry -> entry.getKey() + " (" + entry.getValue().getMaterialCollection(material.getClass()).get(material).toString() + ")")
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return recipesList.stream().sorted().collect(Collectors.joining("\n"));
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

    public static boolean isEngineeringIngredientAndCompleted(final Material material) {
        return ENGINEER_UNLOCK_REQUIREMENTS.values().stream().filter(EngineerRecipe::isCompleted).anyMatch(recipe -> recipe.getMaterialCollection(material.getClass()).containsKey(material));
    }

    public static boolean isEngineeringIngredientAndNotCompleted(final Material material) {
        return ENGINEER_UNLOCK_REQUIREMENTS.values().stream().filter(engineerRecipe -> !engineerRecipe.isCompleted()).anyMatch(recipe -> recipe.getMaterialCollection(material.getClass()).containsKey(material));
    }

    static {
        ENGINEER_UNLOCK_REQUIREMENTS.put("A1. Domino Green", new EngineerRecipe(
                List.of("Fly 100 Ly in shuttles"),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.DOMINO_GREEN)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("A2. Domino Green -> Kit Fowler", new EngineerRecipe(
                Map.of(
                        Good.PUSH, 5
                ),
                () -> APPLICATION_STATE.isEngineerKnown(Engineer.KIT_FOWLER)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("A3. Kit Fowler", new EngineerRecipe(
                Map.of(
                        Data.OPINIONPOLLS, 40
                ),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.KIT_FOWLER)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("A4. Kit Fowler -> Yarden Bond", new EngineerRecipe(
                Map.of(
                        Good.SURVEILLANCEEQUIPMENT, 5
                ),
                () -> APPLICATION_STATE.isEngineerKnown(Engineer.YARDEN_BOND)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("A5. Yarden Bond", new EngineerRecipe(
                Map.of(
                        Data.SMEARCAMPAIGNPLANS, 8
                ),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.YARDEN_BOND)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("B1. Hero Ferrari", new EngineerRecipe(
                List.of("10 Surface conflict zones"),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.HERO_FERRARI)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("B2. Hero Ferrari -> Wellington Beck", new EngineerRecipe(
                Map.of(
                        Data.SETTLEMENTDEFENCEPLANS, 15
                ),
                () -> APPLICATION_STATE.isEngineerKnown(Engineer.WELLINGTON_BECK)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("B3. Wellington Beck (Sell 25 in total, any combination)", new EngineerRecipe(
                Map.of(
                        Data.CLASSICENTERTAINMENT, 25,
                        Data.MULTIMEDIAENTERTAINMENT, 25,
                        Data.CATMEDIA, 25
                ),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.WELLINGTON_BECK)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("B4. Wellington Beck -> Uma Laszlo", new EngineerRecipe(
                Map.of(
                        Good.INSIGHTENTERTAINMENTSUITE, 5
                ),
                () -> APPLICATION_STATE.isEngineerKnown(Engineer.UMA_LASZLO)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("B5. Uma Laszlo", new EngineerRecipe(
                List.of("Unfriendly reputation or lower with Sirius Corporation"),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.UMA_LASZLO)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("C1. Jude Navarro", new EngineerRecipe(
                List.of("10 Restore or Reactivation Missions"),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.JUDE_NAVARRO)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("C2. Jude Navarro -> Terra Velasquez", new EngineerRecipe(
                Map.of(
                        Good.GENETICREPAIRMEDS, 5
                ),
                () -> APPLICATION_STATE.isEngineerKnown(Engineer.TERRA_VELASQUEZ)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("C3. Terra Velasquez (Complete all of below)", new EngineerRecipe(
                List.of("6 Covert Theft Missions", "6 Covert Heist Missions"),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.TERRA_VELASQUEZ)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("C4. Terra Velasquez -> Oden Geiger", new EngineerRecipe(
                Map.of(
                        Data.FINANCIALPROJECTIONS, 15
                ),
                () -> APPLICATION_STATE.isEngineerKnown(Engineer.ODEN_GEIGER)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("C5. Oden Geiger (Sell 20 in total, any combination)", new EngineerRecipe(
                Map.of(
                        Good.GENETICSAMPLE, 20,
                        Data.EMPLOYEEGENETICDATA, 20,
                        Data.GENETICRESEARCH, 20
                ),
                () -> APPLICATION_STATE.isEngineerUnlocked(Engineer.ODEN_GEIGER)
        ));
        SUIT_UPGRADES.put("Maverick Suit Grade 1>2", new Recipe(
                Map.of(
                        Good.SUITSCHEMATIC, 1,
                        Good.HEALTHMONITOR, 1,
                        Good.LARGECAPACITYPOWERREGULATOR, 1,
                        Data.MANUFACTURINGINSTRUCTIONS, 1,
                        Asset.CARBONFIBREPLATING, 5,
                        Asset.GRAPHENE, 5
                )
        ));
        SUIT_UPGRADES.put("Maverick Suit Grade 2>3", new Recipe(
                Map.of(
                        Good.SUITSCHEMATIC, 5,
                        Good.HEALTHMONITOR, 5,
                        Good.LARGECAPACITYPOWERREGULATOR, 5,
                        Data.MANUFACTURINGINSTRUCTIONS, 5,
                        Asset.CARBONFIBREPLATING, 15,
                        Asset.GRAPHENE, 15
                )
        ));
        SUIT_UPGRADES.put("Maverick Suit Grade 3>4", new Recipe(
                Map.of(
                        Good.SUITSCHEMATIC, 10,
                        Good.HEALTHMONITOR, 10,
                        Good.LARGECAPACITYPOWERREGULATOR, 10,
                        Data.MANUFACTURINGINSTRUCTIONS, 10,
                        Asset.CARBONFIBREPLATING, 25,
                        Asset.GRAPHENE, 25
                )
        ));
        SUIT_UPGRADES.put("Maverick Suit Grade 4>5", new Recipe(
                Map.of(
                        Good.SUITSCHEMATIC, 15,
                        Good.HEALTHMONITOR, 15,
                        Good.LARGECAPACITYPOWERREGULATOR, 15,
                        Data.MANUFACTURINGINSTRUCTIONS, 15,
                        Asset.CARBONFIBREPLATING, 35,
                        Asset.GRAPHENE, 35
                )
        ));
        SUIT_UPGRADES.put("Dominator Suit Grade 1>2", new Recipe(
                Map.of(
                        Good.SUITSCHEMATIC, 1,
                        Good.HEALTHMONITOR, 1,
                        Good.LARGECAPACITYPOWERREGULATOR, 1,
                        Data.MANUFACTURINGINSTRUCTIONS, 1,
                        Asset.TITANIUMPLATING, 5,
                        Asset.GRAPHENE, 5
                )
        ));
        SUIT_UPGRADES.put("Dominator Suit Grade 2>3", new Recipe(
                Map.of(
                        Good.SUITSCHEMATIC, 5,
                        Good.HEALTHMONITOR, 5,
                        Good.LARGECAPACITYPOWERREGULATOR, 5,
                        Data.MANUFACTURINGINSTRUCTIONS, 5,
                        Asset.TITANIUMPLATING, 15,
                        Asset.GRAPHENE, 15
                )
        ));
        SUIT_UPGRADES.put("Dominator Suit Grade 3>4", new Recipe(
                Map.of(
                        Good.SUITSCHEMATIC, 10,
                        Good.HEALTHMONITOR, 10,
                        Good.LARGECAPACITYPOWERREGULATOR, 10,
                        Data.MANUFACTURINGINSTRUCTIONS, 10,
                        Asset.TITANIUMPLATING, 25,
                        Asset.GRAPHENE, 25
                )
        ));
        SUIT_UPGRADES.put("Dominator Suit Grade 4>5", new Recipe(
                Map.of(
                        Good.SUITSCHEMATIC, 15,
                        Good.HEALTHMONITOR, 15,
                        Good.LARGECAPACITYPOWERREGULATOR, 15,
                        Data.MANUFACTURINGINSTRUCTIONS, 15,
                        Asset.TITANIUMPLATING, 35,
                        Asset.GRAPHENE, 35
                )
        ));
        SUIT_UPGRADES.put("Artemis Suit Grade 1>2", new Recipe(
                Map.of(
                        Good.SUITSCHEMATIC, 1,
                        Good.HEALTHMONITOR, 1,
                        Good.LARGECAPACITYPOWERREGULATOR, 1,
                        Data.MANUFACTURINGINSTRUCTIONS, 1,
                        Asset.AEROGEL, 5,
                        Asset.GRAPHENE, 5
                )
        ));
        SUIT_UPGRADES.put("Artemis Suit Grade 2>3", new Recipe(
                Map.of(
                        Good.SUITSCHEMATIC, 5,
                        Good.HEALTHMONITOR, 5,
                        Good.LARGECAPACITYPOWERREGULATOR, 5,
                        Data.MANUFACTURINGINSTRUCTIONS, 5,
                        Asset.AEROGEL, 15,
                        Asset.GRAPHENE, 15
                )
        ));
        SUIT_UPGRADES.put("Artemis Suit Grade 3>4", new Recipe(
                Map.of(
                        Good.SUITSCHEMATIC, 10,
                        Good.HEALTHMONITOR, 10,
                        Good.LARGECAPACITYPOWERREGULATOR, 10,
                        Data.MANUFACTURINGINSTRUCTIONS, 10,
                        Asset.AEROGEL, 25,
                        Asset.GRAPHENE, 25
                )
        ));
        SUIT_UPGRADES.put("Artemis Suit Grade 4>5", new Recipe(
                Map.of(
                        Good.SUITSCHEMATIC, 15,
                        Good.HEALTHMONITOR, 15,
                        Good.LARGECAPACITYPOWERREGULATOR, 15,
                        Data.MANUFACTURINGINSTRUCTIONS, 15,
                        Asset.AEROGEL, 35,
                        Asset.GRAPHENE, 35
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put("Added Melee Damage", new ModuleRecipe(
                Map.of(
                        Data.COMBATTRAININGMATERIAL, 10,
                        Data.COMBATANTPERFORMANCE, 10,
                        Asset.EPINEPHRINE, 10,
                        Asset.MICROTHRUSTERS, 15
                ), List.of(Engineer.JUDE_NAVARRO, Engineer.KIT_FOWLER)
        ));
        SUIT_MODULE_BLUEPRINTS.put("Combat Movement Speed", new ModuleRecipe(
                Map.of(
                        Data.EVACUATIONPROTOCOLS, 10,
                        Data.GENETICRESEARCH, 5,
                        Asset.EPINEPHRINE, 10,
                        Asset.PHNEUTRALISER, 15
                ), List.of(Engineer.TERRA_VELASQUEZ)
        ));
        SUIT_MODULE_BLUEPRINTS.put("Damage Resistance", new ModuleRecipe(
                Map.of(
                        Data.WEAPONINVENTORY, 10,
                        Data.BALLISTICSDATA, 10,
                        Asset.TITANIUMPLATING, 5,
                        Asset.EPOXYADHESIVE, 15,
                        Asset.CARBONFIBREPLATING, 5
                ), List.of(Engineer.JUDE_NAVARRO, Engineer.UMA_LASZLO)
        ));
        SUIT_MODULE_BLUEPRINTS.put("Enhanced Tracking", new ModuleRecipe(
                Map.of(
                        Data.TOPOGRAPHICALSURVEYS, 10,
                        Data.STELLARACTIVITYLOGS, 10,
                        Data.SPECTRALANALYSISDATA, 10,
                        Asset.TRANSMITTER, 5,
                        Asset.CIRCUITBOARD, 5
                ), List.of(Engineer.DOMINO_GREEN, Engineer.ODEN_GEIGER)
        ));
        SUIT_MODULE_BLUEPRINTS.put("Extra Ammo Capacity", new ModuleRecipe(
                Map.of(
                        Data.RECYCLINGLOGS, 15,
                        Data.WEAPONTESTDATA, 10,
                        Data.PRODUCTIONREPORTS, 10,
                        Asset.WEAPONCOMPONENT, 5
                ), List.of(Engineer.JUDE_NAVARRO, Engineer.KIT_FOWLER)
        ));
        SUIT_MODULE_BLUEPRINTS.put("Extra Backpack Capacity", new ModuleRecipe(
                Map.of(
                        Data.WEAPONINVENTORY, 10,
                        Data.CHEMICALINVENTORY, 10,
                        Data.DIGITALDESIGNS, 10,
                        Asset.EPOXYADHESIVE, 10,
                        Asset.MEMORYCHIP, 5
                ), List.of(Engineer.DOMINO_GREEN, Engineer.WELLINGTON_BECK)
        ));
        SUIT_MODULE_BLUEPRINTS.put("Faster Shield Regen", new ModuleRecipe(
                Map.of(
                        Data.REACTOROUTPUTREVIEW, 10,
                        Asset.IONBATTERY, 5,
                        Asset.MICROTRANSFORMER, 15,
                        Asset.ELECTRICALWIRING, 15
                ), List.of(Engineer.KIT_FOWLER, Engineer.UMA_LASZLO)
        ));
        SUIT_MODULE_BLUEPRINTS.put("Improved Battery Capacity", new ModuleRecipe(
                Map.of(
                        Data.REACTOROUTPUTREVIEW, 10,
                        Data.MAINTENANCELOGS, 15,
                        Asset.IONBATTERY, 5,
                        Asset.MICROSUPERCAPACITOR, 10,
                        Asset.ELECTRICALWIRING, 10
                ), List.of(Engineer.ODEN_GEIGER, Engineer.WELLINGTON_BECK)
        ));
        SUIT_MODULE_BLUEPRINTS.put("Improved Jump Assist", new ModuleRecipe(
                Map.of(
                        Good.GMEDS, 10,
                        Data.TOPOGRAPHICALSURVEYS, 10,
                        Asset.MICROTHRUSTERS, 5,
                        Asset.MOTOR, 10
                ), List.of(Engineer.HERO_FERRARI)
        ));
        SUIT_MODULE_BLUEPRINTS.put("Increased Air Reserves", new ModuleRecipe(
                Map.of(
                        Data.PHARMACEUTICALPATENTS, 5,
                        Data.AIRQUALITYREPORTS, 15,
                        Asset.OXYGENICBACTERIA, 10,
                        Asset.PHNEUTRALISER, 15
                ), List.of(Engineer.HERO_FERRARI, Engineer.TERRA_VELASQUEZ)
        ));
        SUIT_MODULE_BLUEPRINTS.put("Increased Sprint Duration", new ModuleRecipe(
                Map.of(
                        Data.TROOPDEPLOYMENTRECORDS, 5,
                        Data.GENESEQUENCINGDATA, 5,
                        Data.MEDICALTRIALRECORDS, 5,
                        Asset.OXYGENICBACTERIA, 10,
                        Asset.CHEMICALCATALYST, 15
                ), List.of(Engineer.HERO_FERRARI, Engineer.TERRA_VELASQUEZ)
        ));
        SUIT_MODULE_BLUEPRINTS.put("Night Vision", new ModuleRecipe(
                Map.of(
                        Good.SURVEILLANCEEQUIPMENT, 10,
                        Data.SURVEILLEANCELOGS, 5,
                        Data.NOCDATA, 5,
                        Data.RADIOACTIVITYDATA, 5,
                        Asset.CIRCUITSWITCH, 10
                ), List.of(Engineer.ODEN_GEIGER)
        ));
        SUIT_MODULE_BLUEPRINTS.put("Quieter Footsteps", new ModuleRecipe(
                Map.of(
                        Data.SETTLEMENTASSAULTPLANS, 5,
                        Data.TACTICALPLANS, 10,
                        Data.PATROLROUTES, 10,
                        Asset.MICROHYDRAULICS, 5,
                        Asset.VISCOELASTICPOLYMER, 15
                ), List.of(Engineer.YARDEN_BOND)
        ));
        SUIT_MODULE_BLUEPRINTS.put("Reduced Tool Battery Consumption", new ModuleRecipe(
                Map.of(
                        Data.REACTOROUTPUTREVIEW, 10,
                        Asset.ELECTRICALWIRING, 15,
                        Asset.ELECTRICALFUSE, 5,
                        Asset.MICROTRANSFORMER, 10
                ), List.of(Engineer.DOMINO_GREEN, Engineer.WELLINGTON_BECK)
        ));


        WEAPON_UPGRADES.put("Karma (AR-50, C-44, L-6, P-15) 1>2", new Recipe(
                Map.of(
                        Good.WEAPONSCHEMATIC, 1,
                        Good.COMPRESSIONLIQUEFIEDGAS, 1,
                        Data.MANUFACTURINGINSTRUCTIONS, 1,
                        Asset.WEAPONCOMPONENT, 5,
                        Asset.TUNGSTENCARBIDE, 5
                )
        ));

        WEAPON_UPGRADES.put("Karma (AR-50, C-44, L-6, P-15) 2>3", new Recipe(
                Map.of(
                        Good.WEAPONSCHEMATIC, 5,
                        Good.COMPRESSIONLIQUEFIEDGAS, 5,
                        Data.MANUFACTURINGINSTRUCTIONS, 5,
                        Asset.WEAPONCOMPONENT, 15,
                        Asset.TUNGSTENCARBIDE, 15
                )
        ));
        WEAPON_UPGRADES.put("Karma (AR-50, C-44, L-6, P-15) 3>4", new Recipe(
                Map.of(
                        Good.WEAPONSCHEMATIC, 10,
                        Good.COMPRESSIONLIQUEFIEDGAS, 10,
                        Data.MANUFACTURINGINSTRUCTIONS, 10,
                        Asset.WEAPONCOMPONENT, 25,
                        Asset.TUNGSTENCARBIDE, 25
                )
        ));
        WEAPON_UPGRADES.put("Karma (AR-50, C-44, L-6, P-15) 4>5", new Recipe(
                Map.of(
                        Good.WEAPONSCHEMATIC, 15,
                        Good.COMPRESSIONLIQUEFIEDGAS, 15,
                        Data.MANUFACTURINGINSTRUCTIONS, 15,
                        Asset.WEAPONCOMPONENT, 35,
                        Asset.TUNGSTENCARBIDE, 35
                )
        ));

        WEAPON_UPGRADES.put("TK (Aphelion, Eclipse, Zenith) 1>2", new Recipe(
                Map.of(
                        Good.WEAPONSCHEMATIC, 1,
                        Good.IONISEDGAS, 1,
                        Data.MANUFACTURINGINSTRUCTIONS, 1,
                        Asset.MICROELECTRODE, 5,
                        Asset.OPTICALFIBRE, 5
                )
        ));

        WEAPON_UPGRADES.put("TK (Aphelion, Eclipse, Zenith) 2>3", new Recipe(
                Map.of(
                        Good.WEAPONSCHEMATIC, 5,
                        Good.IONISEDGAS, 5,
                        Data.MANUFACTURINGINSTRUCTIONS, 5,
                        Asset.MICROELECTRODE, 15,
                        Asset.OPTICALFIBRE, 15
                )
        ));
        WEAPON_UPGRADES.put("TK (Aphelion, Eclipse, Zenith) 3>4", new Recipe(
                Map.of(
                        Good.WEAPONSCHEMATIC, 10,
                        Good.IONISEDGAS, 10,
                        Data.MANUFACTURINGINSTRUCTIONS, 10,
                        Asset.MICROELECTRODE, 25,
                        Asset.OPTICALFIBRE, 25
                )
        ));
        WEAPON_UPGRADES.put("TK (Aphelion, Eclipse, Zenith) 4>5", new Recipe(
                Map.of(
                        Good.WEAPONSCHEMATIC, 15,
                        Good.IONISEDGAS, 15,
                        Data.MANUFACTURINGINSTRUCTIONS, 15,
                        Asset.MICROELECTRODE, 35,
                        Asset.OPTICALFIBRE, 35
                )
        ));

        WEAPON_UPGRADES.put("Manticore (Executioner, Intimidator, Oppressor, Tormentor) 1>2", new Recipe(
                Map.of(
                        Good.WEAPONSCHEMATIC, 1,
                        Good.IONISEDGAS, 1,
                        Data.MANUFACTURINGINSTRUCTIONS, 1,
                        Asset.MICROELECTRODE, 5,
                        Asset.CHEMICALSUPERBASE, 5
                )
        ));

        WEAPON_UPGRADES.put("Manticore (Executioner, Intimidator, Oppressor, Tormentor) 2>3", new Recipe(
                Map.of(
                        Good.WEAPONSCHEMATIC, 5,
                        Good.IONISEDGAS, 5,
                        Data.MANUFACTURINGINSTRUCTIONS, 5,
                        Asset.MICROELECTRODE, 15,
                        Asset.CHEMICALSUPERBASE, 15
                )
        ));
        WEAPON_UPGRADES.put("Manticore (Executioner, Intimidator, Oppressor, Tormentor) 3>4", new Recipe(
                Map.of(
                        Good.WEAPONSCHEMATIC, 10,
                        Good.IONISEDGAS, 10,
                        Data.MANUFACTURINGINSTRUCTIONS, 10,
                        Asset.MICROELECTRODE, 25,
                        Asset.CHEMICALSUPERBASE, 25
                )
        ));
        WEAPON_UPGRADES.put("Manticore (Executioner, Intimidator, Oppressor, Tormentor) 4>5", new Recipe(
                Map.of(
                        Good.WEAPONSCHEMATIC, 15,
                        Good.IONISEDGAS, 15,
                        Data.MANUFACTURINGINSTRUCTIONS, 15,
                        Asset.MICROELECTRODE, 35,
                        Asset.CHEMICALSUPERBASE, 35
                )
        ));
        WEAPON_MODULE_BLUEPRINTS.put("Audio Masking", new ModuleRecipe(
                Map.of(
                        Data.AUDIOLOGS, 5,
                        Data.PATROLROUTES, 10,
                        Asset.SCRAMBLER, 10,
                        Asset.TRANSMITTER, 15,
                        Asset.CIRCUITBOARD, 5
                ), List.of(Engineer.YARDEN_BOND)
        ));
        WEAPON_MODULE_BLUEPRINTS.put("Faster Handling", new ModuleRecipe(
                Map.of(
                        Data.OPERATIONALMANUAL, 10,
                        Data.COMBATANTPERFORMANCE, 10,
                        Data.COMBATTRAININGMATERIAL, 10,
                        Asset.VISCOELASTICPOLYMER, 5
                ), List.of(Engineer.HERO_FERRARI)
        ));
        WEAPON_MODULE_BLUEPRINTS.put("Greater Range (Kinetic)", new ModuleRecipe(
                Map.of(
                        Data.BALLISTICSDATA, 10,
                        Data.TOPOGRAPHICALSURVEYS, 10,
                        Asset.METALCOIL, 10,
                        Asset.RDX, 10,
                        Asset.WEAPONCOMPONENT, 5

                ), List.of(Engineer.DOMINO_GREEN, Engineer.WELLINGTON_BECK)
        ));
        WEAPON_MODULE_BLUEPRINTS.put("Greater Range (Laser)", new ModuleRecipe(
                Map.of(
                        Good.IONISEDGAS, 1,
                        Data.STELLARACTIVITYLOGS, 10,
                        Data.RISKASSESSMENTS, 15,
                        Asset.OPTICALLENS, 5,
                        Asset.IONBATTERY, 20,
                        Asset.MICROTRANSFORMER, 15,
                        Asset.CIRCUITBOARD, 5
                ), List.of(Engineer.DOMINO_GREEN, Engineer.WELLINGTON_BECK)
        ));
        WEAPON_MODULE_BLUEPRINTS.put("Greater Range (Plasma)", new ModuleRecipe(
                Map.of(
                        Good.IONISEDGAS, 1,
                        Data.CHEMICALFORMULAE, 10,
                        Data.MINERALSURVEY, 15,
                        Asset.METALCOIL, 10,
                        Asset.ELECTROMAGNET, 10,
                        Asset.MOTOR, 5,
                        Asset.ELECTRICALFUSE, 5

                ), List.of(Engineer.DOMINO_GREEN, Engineer.WELLINGTON_BECK)
        ));
        WEAPON_MODULE_BLUEPRINTS.put("Headshot Damage (Kinetic)", new ModuleRecipe(
                Map.of(
                        Data.WEAPONTESTDATA, 10,
                        Data.MEDICALRECORDS, 5,
                        Asset.CHEMICALCATALYST, 10,
                        Asset.RDX, 15,
                        Asset.WEAPONCOMPONENT, 5
                ), List.of(Engineer.UMA_LASZLO)
        ));
        WEAPON_MODULE_BLUEPRINTS.put("Headshot Damage (Laser)", new ModuleRecipe(
                Map.of(
                        Data.SPECTRALANALYSISDATA, 10,
                        Data.BIOMETRICDATA, 5,
                        Asset.IONBATTERY, 10,
                        Asset.OPTICALLENS, 5,
                        Asset.SCRAMBLER, 10
                ), List.of(Engineer.UMA_LASZLO)
        ));
        WEAPON_MODULE_BLUEPRINTS.put("Headshot Damage (Plasma)", new ModuleRecipe(
                Map.of(
                        Data.CHEMICALEXPERIMENTDATA, 10,
                        Data.BLOODTESTRESULTS, 5,
                        Asset.IONBATTERY, 10,
                        Asset.ELECTROMAGNET, 10,
                        Asset.MICROSUPERCAPACITOR, 15
                ), List.of(Engineer.UMA_LASZLO)
        ));
        WEAPON_MODULE_BLUEPRINTS.put("Higher Accuracy (Kinetic)", new ModuleRecipe(
                Map.of(
                        Data.EXTRACTIONYIELDDATA, 10,
                        Data.BIOMETRICDATA, 5,
                        Data.COMBATANTPERFORMANCE, 10,
                        Asset.VISCOELASTICPOLYMER, 10,
                        Asset.RDX, 10
                ), List.of(Engineer.YARDEN_BOND, Engineer.TERRA_VELASQUEZ)
        ));
        WEAPON_MODULE_BLUEPRINTS.put("Higher Accuracy (Laser)", new ModuleRecipe(
                Map.of(
                        Data.RADIOACTIVITYDATA, 5,
                        Data.COMBATANTPERFORMANCE, 10,
                        Asset.OPTICALLENS, 5,
                        Asset.ELECTRICALWIRING, 15,
                        Asset.METALCOIL, 10
                ), List.of(Engineer.YARDEN_BOND, Engineer.TERRA_VELASQUEZ)
        ));
        WEAPON_MODULE_BLUEPRINTS.put("Higher Accuracy (Plasma)", new ModuleRecipe(
                Map.of(
                        Data.CHEMICALPATENTS, 5,
                        Data.COMBATANTPERFORMANCE, 10,
                        Asset.CHEMICALCATALYST, 10,
                        Asset.ELECTROMAGNET, 10,
                        Asset.METALCOIL, 10
                ), List.of(Engineer.YARDEN_BOND, Engineer.TERRA_VELASQUEZ)
        ));
//        WEAPON_MODULE_BLUEPRINTS.put("Improved Hip Fire Accuracy", new ModuleRecipe(
//                Map.of(
//                        Asset.OPTICALLENS, 5,
//                        Asset.AEROGEL, 20,
//                        Asset.OPTICALFIBRE, 25,
//                        Asset.METALCOIL, 10,
//                        Asset.ELECTRICALWIRING, 15,
//                        Data.RADIOACTIVITYDATA, 5
//                ), List.of(Engineer.TERRA_VELASQUEZ)
//        ));
        WEAPON_MODULE_BLUEPRINTS.put("Magazine Size", new ModuleRecipe(
                Map.of(
                        Data.WEAPONTESTDATA, 10,
                        Data.SECURITYEXPENSES, 5,
                        Asset.WEAPONCOMPONENT, 5,
                        Asset.TUNGSTENCARBIDE, 5,
                        Asset.METALCOIL, 10

                ), List.of(Engineer.JUDE_NAVARRO, Engineer.KIT_FOWLER)
        ));
        WEAPON_MODULE_BLUEPRINTS.put("Noise Suppressor", new ModuleRecipe(
                Map.of(
                        Data.ATMOSPHERICDATA, 10,
                        Data.MININGANALYTICS, 10,
                        Asset.VISCOELASTICPOLYMER, 15,
                        Asset.WEAPONCOMPONENT, 5
                ), List.of(Engineer.HERO_FERRARI, Engineer.TERRA_VELASQUEZ)
        ));
        WEAPON_MODULE_BLUEPRINTS.put("Reload Speed", new ModuleRecipe(
                Map.of(
                        Data.OPERATIONALMANUAL, 10,
                        Data.PRODUCTIONREPORTS, 10,
                        Data.COMBATTRAININGMATERIAL, 10,
                        Asset.MICROHYDRAULICS, 10,
                        Asset.ELECTROMAGNET, 10
                ), List.of(Engineer.JUDE_NAVARRO, Engineer.UMA_LASZLO)
        ));
        WEAPON_MODULE_BLUEPRINTS.put("Scope", new ModuleRecipe(
                Map.of(
                        Data.SPECTRALANALYSISDATA, 10,
                        Data.BIOMETRICDATA, 5,
                        Asset.OPTICALLENS, 10,
                        Asset.OPTICALFIBRE, 5
                ), List.of(Engineer.ODEN_GEIGER, Engineer.WELLINGTON_BECK)
        ));
        WEAPON_MODULE_BLUEPRINTS.put("Stability", new ModuleRecipe(
                Map.of(
                        Data.MININGANALYTICS, 10,
                        Data.RISKASSESSMENTS, 15,
                        Asset.VISCOELASTICPOLYMER, 10,
                        Asset.MICROHYDRAULICS, 10
                ), List.of(Engineer.DOMINO_GREEN, Engineer.ODEN_GEIGER)
        ));
        WEAPON_MODULE_BLUEPRINTS.put("Stowed Reloading", new ModuleRecipe(
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