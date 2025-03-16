package nl.jixxed.eliteodysseymaterials.templates.horizons.menu;

import javafx.collections.ListChangeListener;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.EngineerBlueprintLabel;
import nl.jixxed.eliteodysseymaterials.templates.generic.Ingredient;
import nl.jixxed.eliteodysseymaterials.templates.generic.MissionIngredient;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsMaterialIngredient;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class HorizonsBlueprintContent extends DestroyableVBox implements DestroyableEventTemplate {
    private static final String RECIPE_TITLE_LABEL_STYLE_CLASS = "recipe-title-label";
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private DestroyableLabel countLabel;
    private final List<Ingredient> ingredients = new ArrayList<>();
    private final HorizonsBlueprint blueprint;
    private DestroyableHBox recipeHeader;
    private DestroyableMenuButton addToWishlist;


    HorizonsBlueprintContent(final HorizonsBlueprint blueprint) {
        this.blueprint = blueprint;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("recipe-content");
        loadIngredients();

        if (this.blueprint instanceof HorizonsModuleBlueprint && HorizonsBlueprintConstants.getExperimentalEffects().containsKey(this.blueprint.getHorizonsBlueprintName())) {
            final DestroyableButton experimentalEffects = ButtonBuilder.builder()
                    .withStyleClass("recipe-wishlist-quickswitch")
                    .withText("blueprint.category.name.experimental_effects")
                    .withOnAction(event ->
                            EventService.publish(new HorizonsBlueprintClickEvent(this.blueprint, true))
                    )
                    .build();
            final DestroyableHBox box = BoxBuilder.builder()
                    .withStyleClass("recipe-wishlist-count-box")
                    .withNodes(experimentalEffects)
                    .buildHBox();
            HBox.setHgrow(experimentalEffects, Priority.ALWAYS);
            final DestroyableRegion region = new DestroyableRegion();
            region.getStyleClass().add("recipe-wishlist-quickswitch-spacer");
            this.getNodes().addAll(box, region);
        }
        if (this.blueprint instanceof HorizonsExperimentalEffectBlueprint) {
            final DestroyableButton blueprints = ButtonBuilder.builder()
                    .withStyleClass("recipe-wishlist-quickswitch")
                    .withText("blueprint.category.name.blueprints")
                    .withOnAction(event ->
                    {
                        EventService.publish(new HorizonsBlueprintClickEvent(this.blueprint, false));
                    })
                    .build();

            final DestroyableHBox box = BoxBuilder.builder()
                    .withStyleClass("recipe-wishlist-count-box")
                    .withNodes(blueprints)
                    .buildHBox();
            HBox.setHgrow(blueprints, Priority.ALWAYS);
            final DestroyableRegion region = new DestroyableRegion();
            region.getStyleClass().add("recipe-wishlist-quickswitch-spacer");
            this.getNodes().addAll(box, region);
        }


        initDescription();
        if (this.blueprint instanceof HorizonsEngineerBlueprint) {
            initObjectivesLabel();
            initObjectives();
        }
        if (!this.ingredients.isEmpty() && !this.ingredients.stream().allMatch(ingredient -> ingredient.getType().equals(HorizonsStorageType.OTHER))) {
            initRecipe();
            initIngredients();
        }
        if (this.blueprint instanceof HorizonsEngineerBlueprint engineerBlueprint) {
            initTips(engineerBlueprint);
        }

        if (!(this.blueprint instanceof HorizonsEngineerBlueprint) && !this.blueprint.getHorizonsBlueprintType().equals(HorizonsBlueprintType.SYNTHESIS) && !HorizonsBlueprintConstants.getTechbrokerUnlocks().containsKey(this.blueprint.getHorizonsBlueprintName())) {
            initEngineers();
        }
        initModifiers();
        if (this.blueprint instanceof HorizonsTechBrokerBlueprint blueprint) {
            initClosestTrader(blueprint.getHorizonsBrokerTypes());
        }

    }

    private void initClosestTrader(final List<HorizonsBrokerType> horizonsBrokerTypes) {

        final DestroyableLabel title = LabelBuilder.builder()
                .withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS)
                .withText("blueprint.label.nearest")
                .build();
        this.getNodes().addAll(title, new HorizonsNearestBroker(horizonsBrokerTypes));
    }

    private void loadIngredients() {
        this.ingredients.addAll(getRecipeIngredients(this.blueprint, Raw.class, HorizonsStorageType.RAW));
        this.ingredients.addAll(getRecipeIngredients(this.blueprint, Encoded.class, HorizonsStorageType.ENCODED));
        this.ingredients.addAll(getRecipeIngredients(this.blueprint, Manufactured.class, HorizonsStorageType.MANUFACTURED));
        this.ingredients.addAll(getRecipeIngredients(this.blueprint, Commodity.class, HorizonsStorageType.COMMODITY));
        if (this.blueprint instanceof HorizonsEngineerBlueprint horizonsEngineerBlueprint) {
            this.ingredients.addAll(horizonsEngineerBlueprint.getOther().stream()
                    .map(text -> new MissionIngredient(text, HorizonsStorageType.OTHER))
                    .collect(Collectors.toCollection(ArrayList::new)));
        }
    }

    private void initIngredients() {
        final DestroyableFlowPane ingredientFlow = FlowPaneBuilder.builder()
                .withStyleClass("recipe-ingredient-flow")
                .withNodes(this.ingredients.stream().filter(ingredient -> !ingredient.getType().equals(HorizonsStorageType.OTHER)).toList())
                .build();
        this.getNodes().add(ingredientFlow);
    }

    private void initObjectives() {
        final DestroyableFlowPane ingredientFlow = FlowPaneBuilder.builder()
                .withStyleClass("recipe-ingredient-flow")
                .withNodes(this.ingredients.stream().filter(ingredient -> ingredient.getType().equals(HorizonsStorageType.OTHER)).toList())
                .build();
        this.getNodes().add(ingredientFlow);
    }

    private void initTips(final HorizonsEngineerBlueprint engineerBlueprint) {

        final DestroyableLabel levelingHeader = LabelBuilder.builder()
                .withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS)
                .withText("blueprint.label.leveling")
                .build();
        this.getNodes().add(levelingHeader);
        final DestroyableFlowPane levelingFlow = FlowPaneBuilder.builder()
                .withStyleClass("recipe-ingredient-flow")
                .withNodes(engineerBlueprint.getLeveling().stream()
                        .map(levelingTip -> BoxBuilder.builder()
                                .withStyleClass("leveling")
                                .withNode(LabelBuilder.builder()
                                        .withStyleClass("leveling-name")
                                        .withText(levelingTip)
                                        .build())
                                .buildVBox())
                        .toList())
                .build();
        this.getNodes().add(levelingFlow);
    }


    private void initDescription() {
        final DestroyableLabel descriptionTitle = LabelBuilder.builder()
                .withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS)
                .withText("blueprint.label.description")
                .build();


        this.recipeHeader = BoxBuilder.builder()
                .withNodes(descriptionTitle, new GrowingRegion()).buildHBox();

        this.getNodes().add(this.recipeHeader);
        if (GameVersion.LIVE.equals(this.blueprint.getGameVersion())) {
            final DestroyableLabel liveOnly = LabelBuilder.builder()
                    .withStyleClass("recipe-live")
                    .withText("blueprint.is.live")
                    .build();
            this.getNodes().add(liveOnly);
        }
        //TODO: could be a label?
        final DestroyableText description = TextBuilder.builder()
                .withStyleClass("blueprint-description-text")
                .withWrappingWidth(465D)
                .withText(HorizonsBlueprintType.SYNTHESIS.equals(this.blueprint.getHorizonsBlueprintType()) || HorizonsBlueprintType.ENGINEER.equals(this.blueprint.getHorizonsBlueprintType()) ? this.blueprint.getHorizonsBlueprintName().getDescriptionLocalizationKey() : this.blueprint.getHorizonsBlueprintType().getDescriptionLocalizationKey(this.blueprint.getHorizonsBlueprintName().lcName()))
                .build();
        final DestroyableTextFlow textFlow = TextFlowBuilder.builder()
                .withStyleClass("blueprint-description")
                .withTexts(description)
                .build();
        this.getNodes().add(textFlow);
    }

    @SuppressWarnings("java:S1192")
    private void initRecipe() {
        this.addToWishlist = MenuButtonBuilder.builder()
                .build();
        this.addToWishlist.getStyleClass().add("recipe-wishlist-button");
        this.addToWishlist.addBinding(this.addToWishlist.textProperty(), LocaleService.getStringBinding("blueprint.add.to.wishlist"));
        this.addToWishlist.getItems().addAll(new ArrayList<>());
        this.addToWishlist.getItems().addListener((ListChangeListener<MenuItem>) c -> {
            if (c.getList().size() <= 1) {
                if (!this.addToWishlist.getStyleClass().contains("hidden-menu-button")) {
                    this.addToWishlist.getStyleClass().add("hidden-menu-button");
                }
            } else {
                this.addToWishlist.getStyleClass().remove("hidden-menu-button");
            }
        });
        this.addToWishlist.setOnMouseClicked(event -> {
            if (this.addToWishlist.getItems().size() == 1) {
                this.addToWishlist.getItems().get(0).fire();
                event.consume();
            }
        });
        this.addToWishlist.registerEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (this.addToWishlist.getItems().size() == 1) {
                event.consume();
            }
        });
        this.addToWishlist.registerEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            if (this.addToWishlist.getItems().size() == 1) {
                event.consume();
            }
        });
        this.countLabel = LabelBuilder.builder()
                .withStyleClass("recipe-wishlist-count")
                .build();
        this.countLabel.addBinding(this.countLabel.textProperty(), LocaleService.getStringBinding("blueprint.on.wishlist", 0));
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final HorizonsWishlists wishlists = loadCommanderWishlists(commander);
            loadInitialCount(wishlists);
        });
        final DestroyableHBox box = BoxBuilder.builder()
                .withStyleClass("recipe-wishlist-count-box")
                .withNodes(this.countLabel, this.addToWishlist)
                .buildHBox();
        HBox.setHgrow(this.addToWishlist, Priority.ALWAYS);
        this.recipeHeader.getNodes().add(box);
        final DestroyableLabel materialHeader = LabelBuilder.builder()
                .withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS)
                .withText("blueprint.header.material")
                .build();
        this.getNodes().add(materialHeader);
    }

    private void loadInitialCount(final HorizonsWishlists wishlists) {
        final long count = wishlists.getSelectedWishlist().getItems().stream().filter(wishlistRecipe -> wishlistRecipe.getRecipeName().equals(this.blueprint.getBlueprintName())).count();
        if (count > 0L) {
            this.countLabel.addBinding(this.countLabel.textProperty(), LocaleService.getStringBinding("blueprint.on.wishlist", count));
        } else {
            this.countLabel.addBinding(this.countLabel.textProperty(), LocaleService.getStringBinding(() -> ""));
        }
    }

    private HorizonsWishlists loadCommanderWishlists(final Commander commander) {
        final HorizonsWishlists wishlists = WishlistService.getHorizonsWishlists(commander);
        this.addToWishlist.getItems().clear();
        final List<DestroyableMenuItem> menuItems = wishlists.getAllWishlists().stream().filter(horizonsWishlist -> !horizonsWishlist.equals(HorizonsWishlist.ALL)).sorted(Comparator.comparing(HorizonsWishlist::getName)).flatMap(wishlist -> {
            final List<DestroyableMenuItem> items = new ArrayList<>();
            if (this.blueprint instanceof HorizonsModuleBlueprint) {
                final DestroyableMenuItem menuItemSingle = createMenuItem(commander, wishlist);
                menuItemSingle.addBinding(menuItemSingle.textProperty(), LocaleService.getStringBinding("blueprint.add.to.wishlist.single.grade", wishlist.getName()));
                items.add(menuItemSingle);
                final DestroyableMenuItem menuItemAll = createAllGradeMenuItem(commander, wishlist);
                menuItemAll.addBinding(menuItemAll.textProperty(), LocaleService.getStringBinding("blueprint.add.to.wishlist.all.grade", wishlist.getName()));
                items.add(menuItemAll);
            } else {
                final DestroyableMenuItem menuItemSingle = createMenuItem(commander, wishlist);
                menuItemSingle.setText(wishlist.getName());
                items.add(menuItemSingle);
            }
            return items.stream();
        }).toList();
        this.addToWishlist.getItems().addAll(menuItems);
        return wishlists;
    }

    private DestroyableMenuItem createMenuItem(final Commander commander, final HorizonsWishlist wishlist) {
        return MenuItemBuilder.builder()
                .withOnAction(_ -> {
                    final HorizonsWishlistBlueprint bp;
                    if (this.blueprint instanceof HorizonsModuleBlueprint horizonsModuleBlueprint) {
                        bp = new HorizonsModuleWishlistBlueprint(horizonsModuleBlueprint.getHorizonsBlueprintType(), new EnumMap<>(Map.of(horizonsModuleBlueprint.getHorizonsBlueprintGrade(), 1D)));
                    } else if (this.blueprint instanceof HorizonsExperimentalEffectBlueprint horizonsExperimentalEffectBlueprint) {
                        bp = new HorizonsExperimentalWishlistBlueprint(horizonsExperimentalEffectBlueprint.getHorizonsBlueprintType());
                    } else if (BlueprintCategory.SYNTHESIS.equals((this.blueprint.getBlueprintName()).getBlueprintCategory())) {
                        bp = new HorizonsSynthesisWishlistBlueprint(this.blueprint.getHorizonsBlueprintGrade());
                    } else if (BlueprintCategory.TECHBROKER.equals((this.blueprint.getBlueprintName()).getBlueprintCategory())) {
                        bp = new HorizonsTechBrokerWishlistBlueprint(this.blueprint.getHorizonsBlueprintType());
                    } else {
                        bp = new HorizonsEngineerWishlistBlueprint((this.blueprint.getBlueprintName()), true);
                    }
                    bp.setRecipeName((this.blueprint.getBlueprintName()));
                    bp.setVisible(true);
                    EventService.publish(new HorizonsWishlistBlueprintEvent(commander, wishlist.getUuid(), List.of(bp), Action.ADDED));
                })
                .build();
    }

    private DestroyableMenuItem createAllGradeMenuItem(final Commander commander, final HorizonsWishlist wishlist) {
        return MenuItemBuilder.builder()
                .withOnAction(_ -> {
                    final Set<HorizonsBlueprintGrade> blueprintGrades = HorizonsBlueprintConstants.getBlueprintGrades((this.blueprint.getBlueprintName()), this.blueprint.getHorizonsBlueprintType());

                    final Map<HorizonsBlueprintGrade, Double> gradePercentages = new EnumMap<>(HorizonsBlueprintGrade.class);
                    blueprintGrades.forEach(horizonsBlueprintGrade -> gradePercentages.put(horizonsBlueprintGrade, 1D));
                    final HorizonsModuleWishlistBlueprint bp = new HorizonsModuleWishlistBlueprint(this.blueprint.getHorizonsBlueprintType(), gradePercentages);
                    bp.setRecipeName((this.blueprint.getBlueprintName()));
                    bp.setVisible(true);
                    EventService.publish(new HorizonsWishlistBlueprintEvent(commander, wishlist.getUuid(), List.of(bp), Action.ADDED));
                }).build();
    }


    private void initObjectivesLabel() {
        final DestroyableLabel materialHeader = LabelBuilder.builder()
                .withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS)
                .withText("blueprint.header.objective")
                .build();
        this.getNodes().add(materialHeader);
    }


    private void initEngineers() {
        final DestroyableLabel engineerLabelHeader = LabelBuilder.builder()
                .withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS)
                .withText("blueprint.label.engineers")
                .build();
        this.getNodes().add(engineerLabelHeader);
        final DestroyableHBox[] engineerLabels = this.blueprint.getEngineers().stream()
                .filter(Predicate.not(Engineer.REMOTE_WORKSHOP::equals))
                .map(engineer -> new EngineerBlueprintLabel(engineer, this.blueprint, true, this.blueprint.getHorizonsBlueprintGrade().getGrade()))
                .sorted(Comparator.comparing(EngineerBlueprintLabel::getEngineerName))
                .toArray(DestroyableHBox[]::new);
        final DestroyableFlowPane flowPane = FlowPaneBuilder.builder()
                .withStyleClass("recipe-engineer-flow")
                .withNodes(engineerLabels)
                .build();
        this.getNodes().add(flowPane);

    }

    private void initModifiers() {
        final Map<HorizonsModifier, HorizonsModifierValue> modifierMap = this.blueprint.getModifiers();
        if (!modifierMap.isEmpty()) {
            final DestroyableLabel modifierTitle = LabelBuilder.builder()
                    .withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS)
                    .withText("blueprint.label.modifiers")
                    .build();
            this.getNodes().add(modifierTitle);

            final List<DestroyableHBox> modifiers = modifierMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(entry -> {
                        final DestroyableLabel modifier = LabelBuilder.builder()
                                .withStyleClasses("recipe-modifier-name", entry.getValue().isPositive() ? "recipe-modifier-positive" : "recipe-modifier-negative")
                                .withText(entry.getKey().getLocalizationKey())
                                .build();

                        final DestroyableLabel value = LabelBuilder.builder()
                                .withStyleClasses("recipe-modifier-value", entry.getValue().isPositive() ? "recipe-modifier-positive" : "recipe-modifier-negative")
                                .withNonLocalizedText(entry.getValue().modification())
                                .build();
                        final DestroyableHBox modifierBox = BoxBuilder.builder()
                                .withStyleClass("recipe-modifier")
                                .withNodes(modifier, value).buildHBox();
                        HBox.setHgrow(value, Priority.ALWAYS);
                        return modifierBox;
                    })
                    .collect(Collectors.toCollection(ArrayList::new));

            final DestroyableFlowPane modifiersFlowPane = FlowPaneBuilder.builder()
                    .withStyleClass("recipe-modifier-flow")
                    .withNodes(modifiers)
                    .build();
            this.getNodes().addAll(modifiersFlowPane);
        }
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, HorizonsWishlistSelectedEvent.class, wishlistSelectedEvent -> {
            if (!(this.blueprint instanceof HorizonsEngineerBlueprint) || !this.ingredients.stream().allMatch(ingredient -> HorizonsStorageType.OTHER.equals(ingredient.getType()))) {//material based recipes
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final HorizonsWishlists wishlists = loadCommanderWishlists(commander);
                    loadInitialCount(wishlists);
                });
            }
        }));
        register(EventService.addListener(true, this, CommanderSelectedEvent.class, commanderSelectedEvent -> {
            if (!(this.blueprint instanceof HorizonsEngineerBlueprint) || !this.ingredients.stream().allMatch(ingredient -> HorizonsStorageType.OTHER.equals(ingredient.getType()))) {//material based recipes
                final HorizonsWishlists wishlists = loadCommanderWishlists(commanderSelectedEvent.getCommander());
                loadInitialCount(wishlists);
            }
        }));
        register(EventService.addListener(true, this, CommanderAllListedEvent.class, commanderAllListedEvent -> {
            if (!(this.blueprint instanceof HorizonsEngineerBlueprint) || !this.ingredients.stream().allMatch(ingredient -> HorizonsStorageType.OTHER.equals(ingredient.getType()))) {//material based recipes
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final HorizonsWishlists wishlists = loadCommanderWishlists(commander);
                    loadInitialCount(wishlists);
                });
            }
        }));
        register(EventService.addListener(true, this, HorizonsWishlistChangedEvent.class, wishlistEvent -> {
            if (this.countLabel != null) {
                final long count = APPLICATION_STATE.getPreferredCommander().map(commander -> WishlistService.getHorizonsWishlists(commander).getSelectedWishlist().getItems().stream().filter(wishlistRecipe -> {
                            if (wishlistRecipe instanceof HorizonsModuleWishlistBlueprint horizonsModuleWishlistBlueprint) {
                                return horizonsModuleWishlistBlueprint.getRecipeName().equals(this.blueprint.getBlueprintName()) && horizonsModuleWishlistBlueprint.getBlueprintType().equals(this.blueprint.getHorizonsBlueprintType());
                            } else if (wishlistRecipe instanceof HorizonsExperimentalWishlistBlueprint horizonsExperimentalWishlistBlueprint) {
                                return horizonsExperimentalWishlistBlueprint.getRecipeName().equals(this.blueprint.getBlueprintName()) && horizonsExperimentalWishlistBlueprint.getBlueprintType().equals(this.blueprint.getHorizonsBlueprintType());
                            }
                            return wishlistRecipe.getRecipeName().equals(this.blueprint.getBlueprintName());
                        }
                ).count()).orElse(0L);
                if (count > 0L) {
                    this.countLabel.addBinding(this.countLabel.textProperty(), LocaleService.getStringBinding("blueprint.on.wishlist", count));
                } else {
                    this.countLabel.addBinding(this.countLabel.textProperty(), LocaleService.getStringBinding(() -> ""));
                }
            }
        }));
    }

    private List<HorizonsMaterialIngredient> getRecipeIngredients(final HorizonsBlueprint recipe, final Class<? extends HorizonsMaterial> materialClass, final HorizonsStorageType storageType) {
        return recipe.getMaterialCollection(materialClass).entrySet().stream()
                .map(material ->
                        new HorizonsMaterialIngredient(storageType, material.getKey(), material.getValue(),
                                storageType == HorizonsStorageType.COMMODITY
                                        ? StorageService.getCommodityCount((Commodity) material.getKey(), StoragePool.SHIP)
                                        : StorageService.getMaterialCount(material.getKey())))
                .sorted(Comparator.comparing(HorizonsMaterialIngredient::getName))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
