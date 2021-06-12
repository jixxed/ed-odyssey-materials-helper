package nl.jixxed.eliteodysseymaterials;

import nl.jixxed.eliteodysseymaterials.domain.EngineerRecipe;
import nl.jixxed.eliteodysseymaterials.domain.Recipe;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.templates.Settings;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class RecipeConstants {
    static final Map<String, Recipe> SUIT_UPGRADES = new HashMap<>();
    static final Map<String, Recipe> WEAPON_UPGRADES = new HashMap<>();
    static final Map<String, Recipe> SUIT_MODULE_BLUEPRINTS = new HashMap<>();
    static final Map<String, Recipe> WEAPON_MODULE_BLUEPRINTS = new HashMap<>();
    static final Map<String, EngineerRecipe> ENGINEER_UNLOCK_REQUIREMENTS = new HashMap<>();
    static final Map<String, Map<String, ? extends Recipe>> RECIPES = Map.of(
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
        return String.join("\n", recipesList);
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

    static {
        ENGINEER_UNLOCK_REQUIREMENTS.put("A1. Domino Green", new EngineerRecipe(
                List.of("Fly 100 Ly in shuttles"),
                () -> Settings.isEngineerUnlocked(Engineer.DOMINO_GREEN)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("A2. Domino Green -> Kit Fowler", new EngineerRecipe(
                Map.of(
                        Good.PUSH, 5
                ),
                () -> Settings.isEngineerKnown(Engineer.KIT_FOWLER)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("A3. Kit Fowler", new EngineerRecipe(
                Map.of(
                        Data.OPINIONPOLLS, 40
                ),
                () -> Settings.isEngineerUnlocked(Engineer.KIT_FOWLER)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("A4. Kit Fowler -> Yarden Bond", new EngineerRecipe(
                Map.of(
                        Good.SURVEILLANCEEQUIPMENT, 5
                ),
                () -> Settings.isEngineerKnown(Engineer.YARDEN_BOND)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("A5. Yarden Bond", new EngineerRecipe(
                Map.of(
                        Data.SMEARCAMPAIGNPLANS, 8
                ),
                () -> Settings.isEngineerUnlocked(Engineer.YARDEN_BOND)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("B1. Hero Ferrari", new EngineerRecipe(
                List.of("10 Surface conflict zones"),
                () -> Settings.isEngineerUnlocked(Engineer.HERO_FERRARI)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("B2. Hero Ferrari -> Wellington Beck", new EngineerRecipe(
                Map.of(
                        Data.SETTLEMENTDEFENCEPLANS, 15
                ),
                () -> Settings.isEngineerKnown(Engineer.WELLINGTON_BECK)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("B3. Wellington Beck (Sell 25 of one below)", new EngineerRecipe(
                Map.of(
                        Data.CLASSICENTERTAINMENT, 25,
                        Data.MULTIMEDIAENTERTAINMENT, 25,
                        Data.CATMEDIA, 25
                ),
                () -> Settings.isEngineerUnlocked(Engineer.WELLINGTON_BECK)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("B4. Wellington Beck -> Uma Laszlo", new EngineerRecipe(
                Map.of(
                        Good.INSIGHTENTERTAINMENTSUITE, 5
                ),
                () -> Settings.isEngineerKnown(Engineer.UMA_LASZLO)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("B5. Uma Laszlo", new EngineerRecipe(
                List.of("Unfriendly reputation or lower with Sirius Corporation"),
                () -> Settings.isEngineerUnlocked(Engineer.UMA_LASZLO)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("C1. Jude Navarro", new EngineerRecipe(
                List.of("10 Restore or Reactivation Missions"),
                () -> Settings.isEngineerUnlocked(Engineer.JUDE_NAVARRO)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("C2. Jude Navarro -> Terra Velasquez", new EngineerRecipe(
                Map.of(
                        Good.GENETICREPAIRMEDS, 5
                ),
                () -> Settings.isEngineerKnown(Engineer.TERRA_VELASQUEZ)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("C3. Terra Velasquez (Complete one of below)", new EngineerRecipe(
                List.of("6 Covert Theft Missions", "6 Covert Heist Missions"),
                () -> Settings.isEngineerUnlocked(Engineer.TERRA_VELASQUEZ)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("C4. Terra Velasquez -> Oden Geiger", new EngineerRecipe(
                Map.of(
                        Data.FINANCIALPROJECTIONS, 15
                ),
                () -> Settings.isEngineerKnown(Engineer.ODEN_GEIGER)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("C5. Oden Geiger (Sell 20 of one below)", new EngineerRecipe(
                Map.of(
                        Good.GENETICSAMPLE, 20,
                        Data.EMPLOYEEGENETICDATA, 20,
                        Data.GENETICRESEARCH, 20
                ),
                () -> Settings.isEngineerUnlocked(Engineer.ODEN_GEIGER)
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
        SUIT_MODULE_BLUEPRINTS.put("Suit Added Melee Damage", new Recipe(
                Map.of(
                        Data.COMBATTRAININGMATERIAL, 10,
                        Data.COMBATANTPERFORMANCE, 10,
                        Asset.EPINEPHRINE, 10,
                        Asset.MICROTHRUSTERS, 15
                )
        ));
//        10x Evacuation Protocols
//        5x Genetic Research
//        10x Epinephrine
//        15x PH Neutraliser
        SUIT_MODULE_BLUEPRINTS.put("Suit Combat Movement Speed", new Recipe(
                Map.of(
                        Data.EVACUATIONPROTOCOLS, 10,
                        Data.GENETICRESEARCH, 5,
                        Asset.EPINEPHRINE, 10,
                        Asset.PHNEUTRALISER, 15
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put("Suit Damage Resistance", new Recipe(
                Map.of(
                        Data.WEAPONINVENTORY, 10,
                        Data.BALLISTICSDATA, 10,
                        Asset.TITANIUMPLATING, 5,
                        Asset.EPOXYADHESIVE, 15,
                        Asset.CARBONFIBREPLATING, 5
                )
        ));
//        5x Transmitter
//        5x Circuit Board
//        10x Topographical Surveys
//        10x Stellar Activity Logs
//        10x Spectral Analysis Data
        SUIT_MODULE_BLUEPRINTS.put("Suit Enhanced Tracking", new Recipe(
                Map.of(
                        Data.TOPOGRAPHICALSURVEYS, 10,
                        Data.STELLARACTIVITYLOGS, 10,
                        Data.SPECTRALANALYSISDATA, 10,
                        Asset.TRANSMITTER, 5,
                        Asset.CIRCUITBOARD, 5
                )
        ));
//        5x Weapon Component
//        15x Recycling Logs
//        10x Weapon Test Data
//        10x Production Reports
        SUIT_MODULE_BLUEPRINTS.put("Suit Extra Ammo Capacity", new Recipe(
                Map.of(
                        Data.RECYCLINGLOGS, 15,
                        Data.WEAPONTESTDATA, 10,
                        Data.PRODUCTIONREPORTS, 10,
                        Asset.WEAPONCOMPONENT, 5
                )
        ));

//        10x Epoxy Adhesive
//        5x Memory Chip
//        10x Weapon Inventory
//        10x Chemical Inventory
//        10x Digital Designs
        SUIT_MODULE_BLUEPRINTS.put("Suit Extra Backpack Capacity", new Recipe(
                Map.of(
                        Data.WEAPONINVENTORY, 10,
                        Data.CHEMICALINVENTORY, 10,
                        Data.DIGITALDESIGNS, 10,
                        Asset.EPOXYADHESIVE, 10,
                        Asset.MEMORYCHIP, 5
                )
        ));
//        10x Reactor Output Review
//        5x Ion Battery
//        15x Micro Transformer
//        15x Electrical Wiring
        SUIT_MODULE_BLUEPRINTS.put("Suit Faster Shield Regen", new Recipe(
                Map.of(
                        Data.REACTOROUTPUTREVIEW, 10,
                        Asset.IONBATTERY, 5,
                        Asset.MICROTRANSFORMER, 15,
                        Asset.ELECTRICALWIRING, 15
                )
        ));
//        10x Reactor Output Review
//        15x Maintenance Logs
//        5x Ion Battery
//        10x Micro Supercapacitor
//        10x Electrical Wiring
        SUIT_MODULE_BLUEPRINTS.put("Suit Improved Battery Capacity", new Recipe(
                Map.of(
                        Data.REACTOROUTPUTREVIEW, 10,
                        Data.MAINTENANCELOGS, 15,
                        Asset.IONBATTERY, 5,
                        Asset.MICROSUPERCAPACITOR, 10,
                        Asset.ELECTRICALWIRING, 10
                )
        ));
//        10x G-Meds
//        5x Micro Thrusters
//        10x Motor
//        10x Topographical Surveys
        SUIT_MODULE_BLUEPRINTS.put("Suit Improved Jump Assist", new Recipe(
                Map.of(
                        Good.GMEDS, 10,
                        Data.TOPOGRAPHICALSURVEYS, 10,
                        Asset.MICROTHRUSTERS, 5,
                        Asset.MOTOR, 10
                )
        ));
//        10x Oxygenic Bacteria
//        15x PH Neutraliser
//        5x Pharmaceutical Patents
//        15x Air Quality Reports
        SUIT_MODULE_BLUEPRINTS.put("Suit Increased Air Reserves", new Recipe(
                Map.of(
                        Data.PHARMACEUTICALPATENTS, 5,
                        Data.AIRQUALITYREPORTS, 15,
                        Asset.OXYGENICBACTERIA, 10,
                        Asset.PHNEUTRALISER, 15
                )
        ));
//        10x Oxygenic Bacteria
//        15x Chemical Catalyst
//        5x Troop Deployment Records
//        5x Gene Sequencing Data
//        5x Clinical Trial Records
        SUIT_MODULE_BLUEPRINTS.put("Suit Increased Sprint Duration", new Recipe(
                Map.of(
                        Data.TROOPDEPLOYMENTRECORDS, 5,
                        Data.GENESEQUENCINGDATA, 5,
                        Data.MEDICALTRIALRECORDS, 5,
                        Asset.OXYGENICBACTERIA, 10,
                        Asset.CHEMICALCATALYST, 15
                )
        ));
//        10x Surveillance Equipment
//        5x Surveillance Logs
//        5x Radioactivity Data
//        5x NOC Data
//        10x Circuit Switch
        SUIT_MODULE_BLUEPRINTS.put("Suit Night Vision", new Recipe(
                Map.of(
                        Good.SURVEILLANCEEQUIPMENT, 10,
                        Data.SURVEILLEANCELOGS, 5,
                        Data.NOCDATA, 5,
                        Data.RADIOACTIVITYDATA, 5,
                        Asset.CIRCUITSWITCH, 10
                )
        ));
//        5x Settlement Assault Plans
//        10x Tactical Plans
//        10x Patrol Routes
//        5x Micro Hydraulics
//        15x Viscoelastic Polymer
        SUIT_MODULE_BLUEPRINTS.put("Suit Quieter Footsteps", new Recipe(
                Map.of(
                        Data.SETTLEMENTASSAULTPLANS, 5,
                        Data.TACTICALPLANS, 10,
                        Data.PATROLROUTES, 10,
                        Asset.MICROHYDRAULICS, 5,
                        Asset.VISCOELASTICPOLYMER, 15
                )
        ));
//        5x Electrical Fuse
//        10x Micro Transformer
//        15x Electrical Wiring
//        10x Reactor Output Review
        SUIT_MODULE_BLUEPRINTS.put("Suit Reduced Tool Battery Consumption", new Recipe(
                Map.of(
                        Data.REACTOROUTPUTREVIEW, 10,
                        Asset.ELECTRICALWIRING, 15,
                        Asset.ELECTRICALFUSE, 5,
                        Asset.MICROTRANSFORMER, 10
                )
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
//        5x Audio Logs
//        10x Patrol Routes
//        10x Scrambler
//        15x Transmitter
//        5x Circuit Board
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Audio Masking", new Recipe(
                Map.of(
                        Data.AUDIOLOGS, 5,
                        Data.PATROLROUTES, 10,
                        Asset.SCRAMBLER, 10,
                        Asset.TRANSMITTER, 15,
                        Asset.CIRCUITBOARD, 5
                )
        ));
//        5x Viscoelastic Polymer
//        10x Operational Manual
//        10x Combatant Performance
//        10x Combat Training Material
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Faster Handling", new Recipe(
                Map.of(
                        Data.OPERATIONALMANUAL, 10,
                        Data.COMBATANTPERFORMANCE, 10,
                        Data.COMBATTRAININGMATERIAL, 10,
                        Asset.VISCOELASTICPOLYMER, 5
                )
        ));
//        Kinetic
//        10x Metal Coil
//        10x RDX
//        5x Weapon Component
//        10x Ballistics Data
//        10x Topographical Surveys
//        500 CR
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Greater Range (Kinetic)", new Recipe(
                Map.of(
                        Data.BALLISTICSDATA, 10,
                        Data.TOPOGRAPHICALSURVEYS, 10,
                        Asset.METALCOIL, 10,
                        Asset.RDX, 10,
                        Asset.WEAPONCOMPONENT, 5

                )
        ));
//                Laser
//        5x Optical Lens
//        20x Ion Battery
//        1x Ionised Gas
//        15x Micro Transformer
//        5x Circuit Board
//        10x Stellar Activity Logs
//        15x Risk Assessments
//        500 CR
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Greater Range (Laser)", new Recipe(
                Map.of(
                        Good.IONISEDGAS, 1,
                        Data.STELLARACTIVITYLOGS, 10,
                        Data.RISKASSESSMENTS, 15,
                        Asset.OPTICALLENS, 5,
                        Asset.IONBATTERY, 20,
                        Asset.MICROTRANSFORMER, 15,
                        Asset.CIRCUITBOARD, 5
                )
        ));
//                Plasma
//        10x Metal Coil
//        10x Electromagnet
//        1x Ionised Gas
//        5x Motor
//        5x Electrical Fuse
//        10x Chemical Formulae
//        15x Mineral Survey
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Greater Range (Plasma)", new Recipe(
                Map.of(
                        Good.IONISEDGAS, 1,
                        Data.CHEMICALFORMULAE, 10,
                        Data.MINERALSURVEY, 15,
                        Asset.METALCOIL, 10,
                        Asset.ELECTROMAGNET, 10,
                        Asset.MOTOR, 5,
                        Asset.ELECTRICALFUSE, 5

                )
        ));
//        Kinetic Weapons
//        10x Weapon Test Data
//        5x Medical Records
//        10x Chemical Catalyst
//        15x RDX
//        5x Weapon Component
//        500,000 CR
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Headshot Damage (Kinetic)", new Recipe(
                Map.of(
                        Data.WEAPONTESTDATA, 10,
                        Data.MEDICALRECORDS, 5,
                        Asset.CHEMICALCATALYST, 10,
                        Asset.RDX, 15,
                        Asset.WEAPONCOMPONENT, 5
                )
        ));
//        Thermal Weapons
//        10x Spectral Analysis Data
//        5x Biometric Data
//        10x Ion Battery
//        5x Optical Lens
//        10x Scrambler
//        750,000 CR
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Headshot Damage (Laser)", new Recipe(
                Map.of(
                        Data.SPECTRALANALYSISDATA, 10,
                        Data.BIOMETRICDATA, 5,
                        Asset.IONBATTERY, 10,
                        Asset.OPTICALLENS, 5,
                        Asset.SCRAMBLER, 10
                )
        ));
//        Plasma Weapons
//        10x Chemical Experiment Data
//        5x Blood Test Results
//        10x Ion Battery
//        10x Electromagnet
//        15x Micro Supercapacitor
//        500,000 CR
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Headshot Damage (Plasma)", new Recipe(
                Map.of(
                        Data.CHEMICALEXPERIMENTDATA, 10,
                        Data.BLOODTESTRESULTS, 5,
                        Asset.IONBATTERY, 10,
                        Asset.ELECTROMAGNET, 10,
                        Asset.MICROSUPERCAPACITOR, 15
                )
        ));
//        Kinetic Weapons
//        10x Extraction Yield Data
//        5x Biometric Data
//        10x Combatant Performance
//        10x Viscoelastic Polymer
//        10x RDX
//        500,000 CR
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Higher Accuracy (Kinetic)", new Recipe(
                Map.of(
                        Data.EXTRACTIONYIELDDATA, 10,
                        Data.BIOMETRICDATA, 5,
                        Data.COMBATANTPERFORMANCE, 10,
                        Asset.VISCOELASTICPOLYMER, 10,
                        Asset.RDX, 10
                )
        ));
//        Thermal Weapons
//        5x Radioactivity Data
//        10x Combatant Performance
//        10x Metal Coil
//        5x Optical Lens
//        15x Electrical Wiring
//        500,000 CR
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Higher Accuracy (Laser)", new Recipe(
                Map.of(
                        Data.RADIOACTIVITYDATA, 5,
                        Data.COMBATANTPERFORMANCE, 10,
                        Asset.OPTICALLENS, 5,
                        Asset.ELECTRICALWIRING, 15,
                        Asset.METALCOIL, 10
                )
        ));
//        Plasma Weapons
//        5x Chemical Patents
//        10x Combatant Performance
//        10x Chemical Catalyst
//        10x Electromagnet
//        10x Metal Coil
//        500,000 CR
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Higher Accuracy (Plasma)", new Recipe(
                Map.of(
                        Data.CHEMICALPATENTS, 5,
                        Data.COMBATANTPERFORMANCE, 10,
                        Asset.CHEMICALCATALYST, 10,
                        Asset.ELECTROMAGNET, 10,
                        Asset.METALCOIL, 10
                )
        ));
//        5x Weapon Component
//        5x Tungsten Carbide
//        10x Metal Coil
//        10x Weapon Test Data
//        5x Security Expenses
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Magazine Size", new Recipe(
                Map.of(
                        Data.WEAPONTESTDATA, 10,
                        Data.SECURITYEXPENSES, 5,
                        Asset.WEAPONCOMPONENT, 5,
                        Asset.TUNGSTENCARBIDE, 5,
                        Asset.METALCOIL, 10

                )
        ));
//        15x Viscoelastic Polymer
//        5x Weapon Component
//        10x Atmospheric Data
//        10x Mining Analytics
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Noise Suppressor", new Recipe(
                Map.of(
                        Data.ATMOSPHERICDATA, 10,
                        Data.MININGANALYTICS, 10,
                        Asset.VISCOELASTICPOLYMER, 15,
                        Asset.WEAPONCOMPONENT, 5
                )
        ));
//        10x Micro Hydraulics
//        10x Electromagnet
//        10x Operational Manual
//        10x Production Reports
//        10x Combat Training Material
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Reload Speed", new Recipe(
                Map.of(
                        Data.OPERATIONALMANUAL, 10,
                        Data.PRODUCTIONREPORTS, 10,
                        Data.COMBATTRAININGMATERIAL, 10,
                        Asset.MICROHYDRAULICS, 10,
                        Asset.ELECTROMAGNET, 10
                )
        ));
//        10x Spectral Analysis Data
//        5x Biometric Data
//        10x Optical Lens
//        5x Optical Fibre
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Scope", new Recipe(
                Map.of(
                        Data.SPECTRALANALYSISDATA, 10,
                        Data.BIOMETRICDATA, 5,
                        Asset.OPTICALLENS, 10,
                        Asset.OPTICALFIBRE, 5
                )
        ));
//        10x Viscoelastic Polymer
//        10x Micro Hydraulics
//        10x Mining Analytics
//        15x Risk Assessments
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Stability", new Recipe(
                Map.of(
                        Data.MININGANALYTICS, 10,
                        Data.RISKASSESSMENTS, 15,
                        Asset.VISCOELASTICPOLYMER, 10,
                        Asset.MICROHYDRAULICS, 10
                )
        ));
//        10x Digital Designs
//        10x Operational Manual
//        10x Production Schedule
//        5x Circuit Board
//        15x Encrypted Memory Chip
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Stowed Reloading", new Recipe(
                Map.of(
                        Data.DIGITALDESIGNS, 10,
                        Data.OPERATIONALMANUAL, 10,
                        Data.PRODUCTIONSCHEDULE, 10,
                        Asset.CIRCUITBOARD, 5,
                        Asset.ENCRYPTEDMEMORYCHIP, 15
                )
        ));
    }

}