package nl.jixxed.eliteodysseymaterials;

import nl.jixxed.eliteodysseymaterials.enums.Component;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.Goods;

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

    public static String findRecipesContaining(Goods goods) {
        final List<String> recipesList = RECIPES.values().stream()
                .map(recipes -> recipes.entrySet().stream()
                        .filter(stringIngredientsEntry -> stringIngredientsEntry.getValue().getGoods().containsKey(goods))
                        .map(entry -> entry.getKey() + " (" + entry.getValue().getGoods().get(goods).toString() + ")")
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return String.join("\n", recipesList);
    }

    public static String findRecipesContaining(Component component) {
        final List<String> recipesList = RECIPES.values().stream()
                .map(recipes -> recipes.entrySet().stream()
                        .filter(stringIngredientsEntry -> stringIngredientsEntry.getValue().getComponents().containsKey(component))
                        .map(entry -> entry.getKey() + " (" + entry.getValue().getComponents().get(component).toString() + ")")
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return String.join("\n", recipesList);
    }

    public static String findRecipesContaining(Data data) {
        final List<String> recipesList = RECIPES.values().stream()
                .map(recipes -> recipes.entrySet().stream()
                        .filter(stringIngredientsEntry -> stringIngredientsEntry.getValue().getData().containsKey(data))
                        .map(entry -> entry.getKey() + " (" + entry.getValue().getData().get(data).toString() + ")")
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return String.join("\n", recipesList);
    }

    public static boolean isRecipeIngredient(Goods goods) {
        return ENGINEER_UNLOCK_REQUIREMENTS.values().stream().anyMatch(recipe -> recipe.getGoods().containsKey(goods)) ||
                SUIT_UPGRADES.values().stream().anyMatch(recipe -> recipe.getGoods().containsKey(goods)) ||
                WEAPON_UPGRADES.values().stream().anyMatch(recipe -> recipe.getGoods().containsKey(goods)) ||
                SUIT_MODULE_BLUEPRINTS.values().stream().anyMatch(recipe -> recipe.getGoods().containsKey(goods)) ||
                WEAPON_MODULE_BLUEPRINTS.values().stream().anyMatch(recipe -> recipe.getGoods().containsKey(goods));
    }

    public static boolean isEngineeringOnlyIngredient(Goods goods, boolean onlyLockedEngineers) {
        return ENGINEER_UNLOCK_REQUIREMENTS.values().stream().filter(recipe -> !onlyLockedEngineers || !recipe.isCompleted()).anyMatch(recipe -> recipe.getGoods().containsKey(goods)) && !(
                SUIT_UPGRADES.values().stream().anyMatch(recipe -> recipe.getGoods().containsKey(goods)) ||
                        WEAPON_UPGRADES.values().stream().anyMatch(recipe -> recipe.getGoods().containsKey(goods)) ||
                        SUIT_MODULE_BLUEPRINTS.values().stream().anyMatch(recipe -> recipe.getGoods().containsKey(goods)) ||
                        WEAPON_MODULE_BLUEPRINTS.values().stream().anyMatch(recipe -> recipe.getGoods().containsKey(goods)));
    }

    public static boolean isRecipeIngredient(Component component) {
        return ENGINEER_UNLOCK_REQUIREMENTS.values().stream().anyMatch(recipe -> recipe.getComponents().containsKey(component)) ||
                SUIT_UPGRADES.values().stream().anyMatch(recipe -> recipe.getComponents().containsKey(component)) ||
                WEAPON_UPGRADES.values().stream().anyMatch(recipe -> recipe.getComponents().containsKey(component)) ||
                SUIT_MODULE_BLUEPRINTS.values().stream().anyMatch(recipe -> recipe.getComponents().containsKey(component)) ||
                WEAPON_MODULE_BLUEPRINTS.values().stream().anyMatch(recipe -> recipe.getComponents().containsKey(component));
    }

    public static boolean isEngineeringOnlyIngredient(Component component, boolean onlyLockedEngineers) {
        return ENGINEER_UNLOCK_REQUIREMENTS.values().stream().filter(recipe -> !onlyLockedEngineers || !recipe.isCompleted()).anyMatch(recipe -> recipe.getComponents().containsKey(component)) && !(
                SUIT_UPGRADES.values().stream().anyMatch(recipe -> recipe.getComponents().containsKey(component)) ||
                        WEAPON_UPGRADES.values().stream().anyMatch(recipe -> recipe.getComponents().containsKey(component)) ||
                        SUIT_MODULE_BLUEPRINTS.values().stream().anyMatch(recipe -> recipe.getComponents().containsKey(component)) ||
                        WEAPON_MODULE_BLUEPRINTS.values().stream().anyMatch(recipe -> recipe.getComponents().containsKey(component)));
    }

    public static boolean isRecipeIngredient(Data data) {
        return ENGINEER_UNLOCK_REQUIREMENTS.values().stream().anyMatch(recipe -> recipe.getData().containsKey(data)) ||
                SUIT_UPGRADES.values().stream().anyMatch(recipe -> recipe.getData().containsKey(data)) ||
                WEAPON_UPGRADES.values().stream().anyMatch(recipe -> recipe.getData().containsKey(data)) ||
                SUIT_MODULE_BLUEPRINTS.values().stream().anyMatch(recipe -> recipe.getData().containsKey(data)) ||
                WEAPON_MODULE_BLUEPRINTS.values().stream().anyMatch(recipe -> recipe.getData().containsKey(data));
    }

    public static boolean isEngineeringOnlyIngredient(Data data, boolean onlyLockedEngineers) {
        return ENGINEER_UNLOCK_REQUIREMENTS.values().stream().filter(recipe -> !onlyLockedEngineers || !recipe.isCompleted()).anyMatch(recipe -> recipe.getData().containsKey(data)) && !(
                SUIT_UPGRADES.values().stream().anyMatch(recipe -> recipe.getData().containsKey(data)) ||
                        WEAPON_UPGRADES.values().stream().anyMatch(recipe -> recipe.getData().containsKey(data)) ||
                        SUIT_MODULE_BLUEPRINTS.values().stream().anyMatch(recipe -> recipe.getData().containsKey(data)) ||
                        WEAPON_MODULE_BLUEPRINTS.values().stream().anyMatch(recipe -> recipe.getData().containsKey(data)));
    }

    static {
        ENGINEER_UNLOCK_REQUIREMENTS.put("A1. Domino Green", new EngineerRecipe(
                Map.of(
                ),
                Map.of(
                ),
                Map.of(
                ),
                List.of("Fly 100 Ly in shuttles"),
                () -> Settings.isEngineerUnlocked(Engineer.DOMINO_GREEN)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("A2. Domino Green -> Kit Fowler", new EngineerRecipe(
                Map.of(
                        Goods.PUSH, 5
                ),
                Map.of(
                ),
                Map.of(
                ),
                List.of(
                ),
                () -> Settings.isEngineerKnown(Engineer.KIT_FOWLER)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("A3. Kit Fowler", new EngineerRecipe(
                Map.of(
                ),
                Map.of(
                        Data.OPINIONPOLLS, 40
                ),
                Map.of(
                ),
                List.of(
                ),
                () -> Settings.isEngineerUnlocked(Engineer.KIT_FOWLER)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("A4. Kit Fowler -> Yarden Bond", new EngineerRecipe(
                Map.of(
                        Goods.SURVEILLANCEEQUIPMENT, 5
                ),
                Map.of(
                ),
                Map.of(
                ),
                List.of(
                ),
                () -> Settings.isEngineerKnown(Engineer.YARDEN_BOND)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("A5. Yarden Bond (Sell 8 of below)", new EngineerRecipe(
                Map.of(
                ),
                Map.of(
                        Data.SMEARCAMPAIGNPLANS, 8
                ),
                Map.of(
                ),
                List.of(
                ),
                () -> Settings.isEngineerUnlocked(Engineer.YARDEN_BOND)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("B1. Hero Ferrari", new EngineerRecipe(
                Map.of(
                ),
                Map.of(
                ),
                Map.of(
                ),
                List.of("10 Surface conflict zones"),
                () -> Settings.isEngineerUnlocked(Engineer.HERO_FERRARI)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("B2. Hero Ferrari -> Wellington Beck", new EngineerRecipe(
                Map.of(
                ),
                Map.of(
                        Data.SETTLEMENTDEFENCEPLANS, 15
                ),
                Map.of(
                ),
                List.of(
                ),
                () -> Settings.isEngineerKnown(Engineer.WELLINGTON_BECK)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("B3. Wellington Beck (Sell 25 of one below)", new EngineerRecipe(
                Map.of(
                ),
                Map.of(
                        Data.CLASSICENTERTAINMENT, 25,
                        Data.MULTIMEDIAENTERTAINMENT, 25,
                        Data.CATMEDIA, 25
                ),
                Map.of(
                ),
                List.of(
                ),
                () -> Settings.isEngineerUnlocked(Engineer.WELLINGTON_BECK)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("B4. Wellington Beck -> Uma Laszlo", new EngineerRecipe(
                Map.of(
                        Goods.INSIGHTENTERTAINMENTSUITE, 5
                ),
                Map.of(
                ),
                Map.of(
                ),
                List.of(
                ),
                () -> Settings.isEngineerKnown(Engineer.UMA_LASZLO)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("B5. Uma Laszlo", new EngineerRecipe(
                Map.of(
                ),
                Map.of(
                ),
                Map.of(
                ),
                List.of("Unfriendly reputation or lower with Sirius Corporation", "No outstanding donation requests"),
                () -> Settings.isEngineerUnlocked(Engineer.UMA_LASZLO)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("C1. Jude Navarro", new EngineerRecipe(
                Map.of(
                ),
                Map.of(
                ),
                Map.of(
                ),
                List.of("10 Restore or Reactivation Missions"),
                () -> Settings.isEngineerUnlocked(Engineer.JUDE_NAVARRO)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("C2. Jude Navarro -> Terra Velasquez", new EngineerRecipe(
                Map.of(
                        Goods.GENETICREPAIRMEDS, 5
                ),
                Map.of(
                ),
                Map.of(
                ),
                List.of(
                ),
                () -> Settings.isEngineerKnown(Engineer.TERRA_VELASQUEZ)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("C3. Terra Velasquez", new EngineerRecipe(
                Map.of(
                ),
                Map.of(
                ),
                Map.of(
                ),
                List.of("6 Covert Theft Missions", "6 Covert Heist Missions"),
                () -> Settings.isEngineerUnlocked(Engineer.TERRA_VELASQUEZ)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("C4. Terra Velasquez -> Oden Geiger", new EngineerRecipe(
                Map.of(
                ),
                Map.of(
                        Data.FINANCIALPROJECTIONS, 15
                ),
                Map.of(
                ),
                List.of(
                ),
                () -> Settings.isEngineerKnown(Engineer.ODEN_GEIGER)
        ));
        ENGINEER_UNLOCK_REQUIREMENTS.put("C5. Oden Geiger (Sell 20 of one below)", new EngineerRecipe(
                Map.of(
                        Goods.BIOLOGICALSAMPLE, 20
                ),
                Map.of(
                        Data.EMPLOYEEGENETICDATA, 20,
                        Data.GENETICRESEARCH, 20
                ),
                Map.of(

                ),
                List.of(
                ),
                () -> Settings.isEngineerUnlocked(Engineer.ODEN_GEIGER)
        ));
        SUIT_UPGRADES.put("Maverick Suit Grade 1>2", new Recipe(
                Map.of(
                        Goods.SUITSCHEMATIC, 1,
                        Goods.HEALTHMONITOR, 1,
                        Goods.LARGECAPACITYPOWERREGULATOR, 1
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 1
                ),
                Map.of(
                        Component.CARBONFIBREPLATING, 5,
                        Component.GRAPHENE, 5
                )
        ));
        SUIT_UPGRADES.put("Maverick Suit Grade 2>3", new Recipe(
                Map.of(
                        Goods.SUITSCHEMATIC, 5,
                        Goods.HEALTHMONITOR, 5,
                        Goods.LARGECAPACITYPOWERREGULATOR, 5
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 5
                ),
                Map.of(
                        Component.CARBONFIBREPLATING, 15,
                        Component.GRAPHENE, 15
                )
        ));
        SUIT_UPGRADES.put("Maverick Suit Grade 3>4", new Recipe(
                Map.of(
                        Goods.SUITSCHEMATIC, 10,
                        Goods.HEALTHMONITOR, 10,
                        Goods.LARGECAPACITYPOWERREGULATOR, 10
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 10
                ),
                Map.of(
                        Component.CARBONFIBREPLATING, 25,
                        Component.GRAPHENE, 25
                )
        ));
        SUIT_UPGRADES.put("Maverick Suit Grade 4>5", new Recipe(
                Map.of(
                        Goods.SUITSCHEMATIC, 15,
                        Goods.HEALTHMONITOR, 15,
                        Goods.LARGECAPACITYPOWERREGULATOR, 15
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 15
                ),
                Map.of(
                        Component.CARBONFIBREPLATING, 35,
                        Component.GRAPHENE, 35
                )
        ));
        SUIT_UPGRADES.put("Dominator Suit Grade 1>2", new Recipe(
                Map.of(
                        Goods.SUITSCHEMATIC, 1,
                        Goods.HEALTHMONITOR, 1,
                        Goods.LARGECAPACITYPOWERREGULATOR, 1
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 1
                ),
                Map.of(
                        Component.TITANIUMPLATING, 5,
                        Component.GRAPHENE, 5
                )
        ));
        SUIT_UPGRADES.put("Dominator Suit Grade 2>3", new Recipe(
                Map.of(
                        Goods.SUITSCHEMATIC, 5,
                        Goods.HEALTHMONITOR, 5,
                        Goods.LARGECAPACITYPOWERREGULATOR, 5
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 5
                ),
                Map.of(
                        Component.TITANIUMPLATING, 15,
                        Component.GRAPHENE, 15
                )
        ));
        SUIT_UPGRADES.put("Dominator Suit Grade 3>4", new Recipe(
                Map.of(
                        Goods.SUITSCHEMATIC, 10,
                        Goods.HEALTHMONITOR, 10,
                        Goods.LARGECAPACITYPOWERREGULATOR, 10
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 10
                ),
                Map.of(
                        Component.TITANIUMPLATING, 25,
                        Component.GRAPHENE, 25
                )
        ));
        SUIT_UPGRADES.put("Dominator Suit Grade 4>5", new Recipe(
                Map.of(
                        Goods.SUITSCHEMATIC, 15,
                        Goods.HEALTHMONITOR, 15,
                        Goods.LARGECAPACITYPOWERREGULATOR, 15
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 15
                ),
                Map.of(
                        Component.TITANIUMPLATING, 35,
                        Component.GRAPHENE, 35
                )
        ));
        SUIT_UPGRADES.put("Artemis Suit Grade 1>2", new Recipe(
                Map.of(
                        Goods.SUITSCHEMATIC, 1,
                        Goods.HEALTHMONITOR, 1,
                        Goods.LARGECAPACITYPOWERREGULATOR, 1
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 1
                ),
                Map.of(
                        Component.AEROGEL, 5,
                        Component.GRAPHENE, 5
                )
        ));
        SUIT_UPGRADES.put("Artemis Suit Grade 2>3", new Recipe(
                Map.of(
                        Goods.SUITSCHEMATIC, 5,
                        Goods.HEALTHMONITOR, 5,
                        Goods.LARGECAPACITYPOWERREGULATOR, 5
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 5
                ),
                Map.of(
                        Component.AEROGEL, 15,
                        Component.GRAPHENE, 15
                )
        ));
        SUIT_UPGRADES.put("Artemis Suit Grade 3>4", new Recipe(
                Map.of(
                        Goods.SUITSCHEMATIC, 10,
                        Goods.HEALTHMONITOR, 10,
                        Goods.LARGECAPACITYPOWERREGULATOR, 10
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 10
                ),
                Map.of(
                        Component.AEROGEL, 25,
                        Component.GRAPHENE, 25
                )
        ));
        SUIT_UPGRADES.put("Artemis Suit Grade 4>5", new Recipe(
                Map.of(
                        Goods.SUITSCHEMATIC, 15,
                        Goods.HEALTHMONITOR, 15,
                        Goods.LARGECAPACITYPOWERREGULATOR, 15
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 15
                ),
                Map.of(
                        Component.AEROGEL, 35,
                        Component.GRAPHENE, 35
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put("Suit Added Melee Damage", new Recipe(
                Map.of(),
                Map.of(
                        Data.COMBATTRAININGMATERIAL, 10,
                        Data.COMBATANTPERFORMANCE, 10
                ),
                Map.of(
                        Component.EPINEPHRINE, 10,
                        Component.MICROTHRUSTERS, 15
                )
        ));
//        10x Evacuation Protocols
//        5x Genetic Research
//        10x Epinephrine
//        15x PH Neutraliser
        SUIT_MODULE_BLUEPRINTS.put("Suit Combat Movement Speed", new Recipe(
                Map.of(
                ),
                Map.of(
                        Data.EVACUATIONPROTOCOLS, 10,
                        Data.GENETICRESEARCH, 5
                ),
                Map.of(
                        Component.EPINEPHRINE, 10,
                        Component.PHNEUTRALISER, 15
                )
        ));
        SUIT_MODULE_BLUEPRINTS.put("Suit Damage Resistance", new Recipe(
                Map.of(
                ),
                Map.of(
                        Data.WEAPONINVENTORY, 10,
                        Data.BALLISTICSDATA, 10
                ),
                Map.of(
                        Component.TITANIUMPLATING, 5,
                        Component.EPOXYADHESIVE, 15,
                        Component.CARBONFIBREPLATING, 5
                )
        ));
//        5x Transmitter
//        5x Circuit Board
//        10x Topographical Surveys
//        10x Stellar Activity Logs
//        10x Spectral Analysis Data
        SUIT_MODULE_BLUEPRINTS.put("Suit Enhanced Tracking", new Recipe(
                Map.of(
                ),
                Map.of(
                        Data.TOPOGRAPHICALSURVEYS, 10,
                        Data.STELLARACTIVITYLOGS, 10,
                        Data.SPECTRALANALYSISDATA, 10
                ),
                Map.of(
                        Component.TRANSMITTER, 5,
                        Component.CIRCUITBOARD, 5
                )
        ));
//        5x Weapon Component
//        15x Recycling Logs
//        10x Weapon Test Data
//        10x Production Reports
        SUIT_MODULE_BLUEPRINTS.put("Suit Extra Ammo Capacity", new Recipe(
                Map.of(
                ),
                Map.of(
                        Data.RECYCLINGLOGS, 15,
                        Data.WEAPONTESTDATA, 10,
                        Data.PRODUCTIONREPORTS, 10
                ),
                Map.of(
                        Component.WEAPONCOMPONENT, 5
                )
        ));

//        10x Epoxy Adhesive
//        5x Memory Chip
//        10x Weapon Inventory
//        10x Chemical Inventory
//        10x Digital Designs
        SUIT_MODULE_BLUEPRINTS.put("Suit Extra Backpack Capacity", new Recipe(
                Map.of(
                ),
                Map.of(
                        Data.WEAPONINVENTORY, 10,
                        Data.CHEMICALINVENTORY, 10,
                        Data.DIGITALDESIGNS, 10
                ),
                Map.of(
                        Component.EPOXYADHESIVE, 10,
                        Component.MEMORYCHIP, 5
                )
        ));
//        10x Reactor Output Review
//        5x Ion Battery
//        15x Micro Transformer
//        15x Electrical Wiring
        SUIT_MODULE_BLUEPRINTS.put("Suit Faster Shield Regen", new Recipe(
                Map.of(
                ),
                Map.of(
                        Data.REACTOROUTPUTREVIEW, 10
                ),
                Map.of(
                        Component.IONBATTERY, 5,
                        Component.MICROTRANSFORMER, 15,
                        Component.ELECTRICALWIRING, 15
                )
        ));
//        10x Reactor Output Review
//        15x Maintenance Logs
//        5x Ion Battery
//        10x Micro Supercapacitor
//        10x Electrical Wiring
        SUIT_MODULE_BLUEPRINTS.put("Suit Improved Battery Capacity", new Recipe(
                Map.of(
                ),
                Map.of(
                        Data.REACTOROUTPUTREVIEW, 10,
                        Data.MAINTENANCELOGS, 15
                ),
                Map.of(
                        Component.IONBATTERY, 5,
                        Component.MICROSUPERCAPACITOR, 10,
                        Component.ELECTRICALWIRING, 10
                )
        ));
//        10x G-Meds
//        5x Micro Thrusters
//        10x Motor
//        10x Topographical Surveys
        SUIT_MODULE_BLUEPRINTS.put("Suit Improved Jump Assist", new Recipe(
                Map.of(
                        Goods.GMEDS, 10
                ),
                Map.of(
                        Data.TOPOGRAPHICALSURVEYS, 10
                ),
                Map.of(
                        Component.MICROTHRUSTERS, 5,
                        Component.MOTOR, 10
                )
        ));
//        10x Oxygenic Bacteria
//        15x PH Neutraliser
//        5x Pharmaceutical Patents
//        15x Air Quality Reports
        SUIT_MODULE_BLUEPRINTS.put("Suit Increased Air Reserves", new Recipe(
                Map.of(
                ),
                Map.of(
                        Data.PHARMACEUTICALPATENTS, 5,
                        Data.AIRQUALITYREPORTS, 15
                ),
                Map.of(
                        Component.OXYGENICBACTERIA, 10,
                        Component.PHNEUTRALISER, 15
                )
        ));
//        10x Oxygenic Bacteria
//        15x Chemical Catalyst
//        5x Troop Deployment Records
//        5x Gene Sequencing Data
//        5x Clinical Trial Records
        SUIT_MODULE_BLUEPRINTS.put("Suit Increased Sprint Duration", new Recipe(
                Map.of(
                ),
                Map.of(
                        Data.TROOPDEPLOYMENTRECORDS, 5,
                        Data.GENESEQUENCINGDATA, 5,
                        Data.MEDICALTRIALRECORDS, 5
                ),
                Map.of(
                        Component.OXYGENICBACTERIA, 10,
                        Component.CHEMICALCATALYST, 15
                )
        ));
//        10x Surveillance Equipment
//        5x Surveillance Logs
//        5x Radioactivity Data
//        5x NOC Data
//        10x Circuit Switch
        SUIT_MODULE_BLUEPRINTS.put("Suit Night Vision", new Recipe(
                Map.of(
                        Goods.SURVEILLANCEEQUIPMENT, 10
                ),
                Map.of(
                        Data.SURVEILLEANCELOGS, 5,
                        Data.NOCDATA, 5,
                        Data.RADIOACTIVITYDATA, 5
                ),
                Map.of(
                        Component.CIRCUITSWITCH, 10
                )
        ));
//        5x Settlement Assault Plans
//        10x Tactical Plans
//        10x Patrol Routes
//        5x Micro Hydraulics
//        15x Viscoelastic Polymer
        SUIT_MODULE_BLUEPRINTS.put("Suit Quieter Footsteps", new Recipe(
                Map.of(
                ),
                Map.of(
                        Data.SETTLEMENTASSAULTPLANS, 5,
                        Data.TACTICALPLANS, 10,
                        Data.PATROLROUTES, 10
                ),
                Map.of(
                        Component.MICROHYDRAULICS, 5,
                        Component.VISCOELASTICPOLYMER, 15
                )
        ));
//        5x Electrical Fuse
//        10x Micro Transformer
//        15x Electrical Wiring
//        10x Reactor Output Review
        SUIT_MODULE_BLUEPRINTS.put("Suit Night Vision", new Recipe(
                Map.of(
                ),
                Map.of(
                        Data.REACTOROUTPUTREVIEW, 10
                ),
                Map.of(
                        Component.ELECTRICALWIRING, 15,
                        Component.ELECTRICALFUSE, 5,
                        Component.MICROTRANSFORMER, 10
                )
        ));


        WEAPON_UPGRADES.put("Karma (AR-50, C-44, L-6, P-15) 1>2", new Recipe(
                Map.of(
                        Goods.WEAPONSCHEMATIC, 1,
                        Goods.COMPRESSIONLIQUEFIEDGAS, 1
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 1
                ),
                Map.of(
                        Component.WEAPONCOMPONENT, 5,
                        Component.TUNGSTENCARBIDE, 5
                )
        ));

        WEAPON_UPGRADES.put("Karma (AR-50, C-44, L-6, P-15) 2>3", new Recipe(
                Map.of(
                        Goods.WEAPONSCHEMATIC, 5,
                        Goods.COMPRESSIONLIQUEFIEDGAS, 5
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 5
                ),
                Map.of(
                        Component.WEAPONCOMPONENT, 15,
                        Component.TUNGSTENCARBIDE, 15
                )
        ));
        WEAPON_UPGRADES.put("Karma (AR-50, C-44, L-6, P-15) 3>4", new Recipe(
                Map.of(
                        Goods.WEAPONSCHEMATIC, 10,
                        Goods.COMPRESSIONLIQUEFIEDGAS, 10
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 10
                ),
                Map.of(
                        Component.WEAPONCOMPONENT, 25,
                        Component.TUNGSTENCARBIDE, 25
                )
        ));
        WEAPON_UPGRADES.put("Karma (AR-50, C-44, L-6, P-15) 4>5", new Recipe(
                Map.of(
                        Goods.WEAPONSCHEMATIC, 15,
                        Goods.COMPRESSIONLIQUEFIEDGAS, 15
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 15
                ),
                Map.of(
                        Component.WEAPONCOMPONENT, 35,
                        Component.TUNGSTENCARBIDE, 35
                )
        ));

        WEAPON_UPGRADES.put("TK (Aphelion, Eclipse, Zenith) 1>2", new Recipe(
                Map.of(
                        Goods.WEAPONSCHEMATIC, 1,
                        Goods.IONISEDGAS, 1
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 1
                ),
                Map.of(
                        Component.MICROELECTRODE, 5,
                        Component.OPTICALFIBRE, 5
                )
        ));

        WEAPON_UPGRADES.put("TK (Aphelion, Eclipse, Zenith) 2>3", new Recipe(
                Map.of(
                        Goods.WEAPONSCHEMATIC, 5,
                        Goods.IONISEDGAS, 5
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 5
                ),
                Map.of(
                        Component.MICROELECTRODE, 15,
                        Component.OPTICALFIBRE, 15
                )
        ));
        WEAPON_UPGRADES.put("TK (Aphelion, Eclipse, Zenith) 3>4", new Recipe(
                Map.of(
                        Goods.WEAPONSCHEMATIC, 10,
                        Goods.IONISEDGAS, 10
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 10
                ),
                Map.of(
                        Component.MICROELECTRODE, 25,
                        Component.OPTICALFIBRE, 25
                )
        ));
        WEAPON_UPGRADES.put("TK (Aphelion, Eclipse, Zenith) 4>5", new Recipe(
                Map.of(
                        Goods.WEAPONSCHEMATIC, 15,
                        Goods.IONISEDGAS, 15
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 15
                ),
                Map.of(
                        Component.MICROELECTRODE, 35,
                        Component.OPTICALFIBRE, 35
                )
        ));

        WEAPON_UPGRADES.put("Manticore (Executioner, Intimidator, Oppressor, Tormentor) 1>2", new Recipe(
                Map.of(
                        Goods.WEAPONSCHEMATIC, 1,
                        Goods.IONISEDGAS, 1
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 1
                ),
                Map.of(
                        Component.MICROELECTRODE, 5,
                        Component.CHEMICALSUPERBASE, 5
                )
        ));

        WEAPON_UPGRADES.put("Manticore (Executioner, Intimidator, Oppressor, Tormentor) 2>3", new Recipe(
                Map.of(
                        Goods.WEAPONSCHEMATIC, 5,
                        Goods.IONISEDGAS, 5
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 5
                ),
                Map.of(
                        Component.MICROELECTRODE, 15,
                        Component.CHEMICALSUPERBASE, 15
                )
        ));
        WEAPON_UPGRADES.put("Manticore (Executioner, Intimidator, Oppressor, Tormentor) 3>4", new Recipe(
                Map.of(
                        Goods.WEAPONSCHEMATIC, 10,
                        Goods.IONISEDGAS, 10
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 10
                ),
                Map.of(
                        Component.MICROELECTRODE, 25,
                        Component.CHEMICALSUPERBASE, 25
                )
        ));
        WEAPON_UPGRADES.put("Manticore (Executioner, Intimidator, Oppressor, Tormentor) 4>5", new Recipe(
                Map.of(
                        Goods.WEAPONSCHEMATIC, 15,
                        Goods.IONISEDGAS, 15
                ),
                Map.of(
                        Data.MANUFACTURINGINSTRUCTIONS, 15
                ),
                Map.of(
                        Component.MICROELECTRODE, 35,
                        Component.CHEMICALSUPERBASE, 35
                )
        ));
//        5x Audio Logs
//        10x Patrol Routes
//        10x Scrambler
//        15x Transmitter
//        5x Circuit Board
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Audio Masking", new Recipe(
                Map.of(
                ),
                Map.of(
                        Data.AUDIOLOGS, 5,
                        Data.PATROLROUTES, 10
                ),
                Map.of(
                        Component.SCRAMBLER, 10,
                        Component.TRANSMITTER, 15,
                        Component.CIRCUITBOARD, 5
                )
        ));
//        5x Viscoelastic Polymer
//        10x Operational Manual
//        10x Combatant Performance
//        10x Combat Training Material
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Faster Handling", new Recipe(
                Map.of(
                ),
                Map.of(
                        Data.OPERATIONALMANUAL, 10,
                        Data.COMBATANTPERFORMANCE, 10,
                        Data.COMBATTRAININGMATERIAL, 10
                ),
                Map.of(
                        Component.VISCOELASTICPOLYMER, 5
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
                ),
                Map.of(
                        Data.BALLISTICSDATA, 10,
                        Data.TOPOGRAPHICALSURVEYS, 10
                ),
                Map.of(
                        Component.METALCOIL, 10,
                        Component.RDX, 10,
                        Component.WEAPONCOMPONENT, 5

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
                        Goods.IONISEDGAS, 1
                ),
                Map.of(
                        Data.STELLARACTIVITYLOGS, 10,
                        Data.RISKASSESSMENTS, 15
                ),
                Map.of(
                        Component.OPTICALLENS, 5,
                        Component.IONBATTERY, 20,
                        Component.MICROTRANSFORMER, 15,
                        Component.CIRCUITBOARD, 5
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
                        Goods.IONISEDGAS, 1
                ),
                Map.of(
                        Data.CHEMICALFORMULAE, 10,
                        Data.MINERALSURVEY, 15
                ),
                Map.of(
                        Component.METALCOIL, 10,
                        Component.ELECTROMAGNET, 10,
                        Component.MOTOR, 5,
                        Component.ELECTRICALFUSE, 5

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
                ),
                Map.of(
                        Data.WEAPONTESTDATA, 10,
                        Data.MEDICALRECORDS, 5
                ),
                Map.of(
                        Component.CHEMICALCATALYST, 10,
                        Component.RDX, 15,
                        Component.WEAPONCOMPONENT, 5
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
                ),
                Map.of(
                        Data.SPECTRALANALYSISDATA, 10,
                        Data.BIOMETRICDATA, 5
                ),
                Map.of(
                        Component.IONBATTERY, 10,
                        Component.OPTICALLENS, 5,
                        Component.SCRAMBLER, 10
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
                ),
                Map.of(
                        Data.CHEMICALEXPERIMENTDATA, 10,
                        Data.BLOODTESTRESULTS, 5
                ),
                Map.of(
                        Component.IONBATTERY, 10,
                        Component.ELECTROMAGNET, 10,
                        Component.MICROSUPERCAPACITOR, 15
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
                ),
                Map.of(
                        Data.EXTRACTIONYIELDDATA, 10,
                        Data.BIOMETRICDATA, 5,
                        Data.COMBATANTPERFORMANCE, 10
                ),
                Map.of(
                        Component.VISCOELASTICPOLYMER, 10,
                        Component.RDX, 10
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
                ),
                Map.of(
                        Data.RADIOACTIVITYDATA, 5,
                        Data.COMBATANTPERFORMANCE, 10
                ),
                Map.of(
                        Component.OPTICALLENS, 5,
                        Component.ELECTRICALWIRING, 15,
                        Component.METALCOIL, 10
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
                ),
                Map.of(
                        Data.CHEMICALPATENTS, 5,
                        Data.COMBATANTPERFORMANCE, 10
                ),
                Map.of(
                        Component.CHEMICALCATALYST, 10,
                        Component.ELECTROMAGNET, 10,
                        Component.METALCOIL, 10
                )
        ));
//        5x Weapon Component
//        5x Tungsten Carbide
//        10x Metal Coil
//        10x Weapon Test Data
//        5x Security Expenses
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Magazine Size", new Recipe(
                Map.of(
                ),
                Map.of(
                        Data.WEAPONTESTDATA, 10,
                        Data.SECURITYEXPENSES, 5
                ),
                Map.of(
                        Component.WEAPONCOMPONENT, 5,
                        Component.TUNGSTENCARBIDE, 5,
                        Component.METALCOIL, 10

                )
        ));
//        15x Viscoelastic Polymer
//        5x Weapon Component
//        10x Atmospheric Data
//        10x Mining Analytics
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Noise Suppressor", new Recipe(
                Map.of(
                ),
                Map.of(
                        Data.ATMOSPHERICDATA, 10,
                        Data.MININGANALYTICS, 10
                ),
                Map.of(
                        Component.VISCOELASTICPOLYMER, 15,
                        Component.WEAPONCOMPONENT, 5
                )
        ));
//        10x Micro Hydraulics
//        10x Electromagnet
//        10x Operational Manual
//        10x Production Reports
//        10x Combat Training Material
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Reload Speed", new Recipe(
                Map.of(
                ),
                Map.of(
                        Data.OPERATIONALMANUAL, 10,
                        Data.PRODUCTIONREPORTS, 10,
                        Data.COMBATTRAININGMATERIAL, 10
                ),
                Map.of(
                        Component.MICROHYDRAULICS, 10,
                        Component.ELECTROMAGNET, 10
                )
        ));
//        10x Spectral Analysis Data
//        5x Biometric Data
//        10x Optical Lens
//        5x Optical Fibre
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Scope", new Recipe(
                Map.of(
                ),
                Map.of(
                        Data.SPECTRALANALYSISDATA, 10,
                        Data.BIOMETRICDATA, 5

                ),
                Map.of(
                        Component.OPTICALLENS, 10,
                        Component.OPTICALFIBRE, 5
                )
        ));
//        10x Viscoelastic Polymer
//        10x Micro Hydraulics
//        10x Mining Analytics
//        15x Risk Assessments
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Stability", new Recipe(
                Map.of(
                ),
                Map.of(
                        Data.MINERALANALYTICS, 10,
                        Data.RISKASSESSMENTS, 15
                ),
                Map.of(
                        Component.VISCOELASTICPOLYMER, 10,
                        Component.MICROHYDRAULICS, 10
                )
        ));
//        10x Digital Designs
//        10x Operational Manual
//        10x Production Schedule
//        5x Circuit Board
//        15x Encrypted Memory Chip
        WEAPON_MODULE_BLUEPRINTS.put("Weapon Stowed Reloading", new Recipe(
                Map.of(
                ),
                Map.of(
                        Data.DIGITALDESIGNS, 10,
                        Data.OPERATIONALMANUAL, 10,
                        Data.PRODUCTIONSCHEDULE, 10
                ),
                Map.of(
                        Component.CIRCUITBOARD, 5,
                        Component.ENCRYPTEDMEMORYCHIP, 15
                )
        ));
    }

}