package nl.jixxed.eliteodysseymaterials.templates.horizons.menu;

import javafx.collections.ListChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.Destroyable;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.generic.EngineerBlueprintLabel;
import nl.jixxed.eliteodysseymaterials.templates.generic.Ingredient;
import nl.jixxed.eliteodysseymaterials.templates.generic.MissionIngredient;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsMaterialIngredient;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class HorizonsBlueprintContent extends VBox implements DestroyableTemplate {
    private static final String RECIPE_TITLE_LABEL_STYLE_CLASS = "recipe-title-label";
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private Label countLabel;
    private final List<Ingredient> ingredients = new ArrayList<>();
    private final HorizonsBlueprint blueprint;
    private HBox recipeHeader;
    private MenuButton addToWishlist;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

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
            final Button experimentalEffects = ButtonBuilder.builder().withStyleClass("recipe-wishlist-quickswitch").withText(LocaleService.getStringBinding("blueprint.category.name.experimental_effects")).withOnAction(event ->
                    EventService.publish(new HorizonsBlueprintClickEvent(this.blueprint, true))
            ).build();
            final HBox box = BoxBuilder.builder()
                    .withStyleClass("recipe-wishlist-count-box")
                    .withNodes(experimentalEffects)
                    .buildHBox();
            HBox.setHgrow(experimentalEffects, Priority.ALWAYS);
            final Region region = new Region();
            region.getStyleClass().add("recipe-wishlist-quickswitch-spacer");
            this.getChildren().addAll(box, region);
        }
        if (this.blueprint instanceof HorizonsExperimentalEffectBlueprint) {
            final Button blueprints = ButtonBuilder.builder().withStyleClass("recipe-wishlist-quickswitch").withText(LocaleService.getStringBinding("blueprint.category.name.blueprints")).withOnAction(event ->
            {
                EventService.publish(new HorizonsBlueprintClickEvent(this.blueprint, false));
            }).build();

            final HBox box = BoxBuilder.builder()
                    .withStyleClass("recipe-wishlist-count-box")
                    .withNodes(blueprints)
                    .buildHBox();
            HBox.setHgrow(blueprints, Priority.ALWAYS);
            final Region region = new Region();
            region.getStyleClass().add("recipe-wishlist-quickswitch-spacer");
            this.getChildren().addAll(box, region);
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
        if(this.blueprint instanceof HorizonsTechBrokerBlueprint blueprint){
            initClosestTrader(blueprint.getHorizonsBrokerTypes());
        }

    }

    private void initClosestTrader(final List<HorizonsBrokerType> horizonsBrokerTypes) {

        final Label title = LabelBuilder.builder()
                .withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("blueprint.label.nearest"))
                .build();
        this.getChildren().addAll(title, new HorizonsNearestBroker(horizonsBrokerTypes));
    }

    private void loadIngredients() {
        this.ingredients.addAll(getRecipeIngredients(this.blueprint, Raw.class, HorizonsStorageType.RAW, StorageService.getRaw()));
        this.ingredients.addAll(getRecipeIngredients(this.blueprint, Encoded.class, HorizonsStorageType.ENCODED, StorageService.getEncoded()));
        this.ingredients.addAll(getRecipeIngredients(this.blueprint, Manufactured.class, HorizonsStorageType.MANUFACTURED, StorageService.getManufactured()));
        this.ingredients.addAll(getRecipeIngredients(this.blueprint, Commodity.class, HorizonsStorageType.COMMODITY, StorageService.getCommoditiesShip()));
        if (this.blueprint instanceof HorizonsEngineerBlueprint horizonsEngineerBlueprint) {
            this.ingredients.addAll(horizonsEngineerBlueprint.getOther().stream()
                    .map(text -> new MissionIngredient(text, HorizonsStorageType.OTHER))
                    .collect(Collectors.toCollection(ArrayList::new)));
        }
    }

    private void initIngredients() {
        final FlowPane ingredientFlow = FlowPaneBuilder.builder().withStyleClass("recipe-ingredient-flow").withNodes(this.ingredients.stream().filter(ingredient -> !ingredient.getType().equals(HorizonsStorageType.OTHER)).toList()).build();
        this.getChildren().add(ingredientFlow);
    }

    private void initObjectives() {
        final FlowPane ingredientFlow = FlowPaneBuilder.builder().withStyleClass("recipe-ingredient-flow").withNodes(this.ingredients.stream().filter(ingredient -> ingredient.getType().equals(HorizonsStorageType.OTHER)).toList()).build();
        this.getChildren().add(ingredientFlow);
    }

    private void initTips(final HorizonsEngineerBlueprint engineerBlueprint) {

        final Label levelingHeader = LabelBuilder.builder()
                .withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("blueprint.label.leveling"))
                .build();
        this.getChildren().add(levelingHeader);
        final FlowPane levelingFlow = FlowPaneBuilder.builder().withStyleClass("recipe-ingredient-flow").withNodes(engineerBlueprint.getLeveling().stream().map(levelingTip -> BoxBuilder.builder().withStyleClass("leveling").withNode(LabelBuilder.builder().withStyleClass("leveling-name").withText(LocaleService.getStringBinding(levelingTip)).build()).buildVBox()).toList()).build();
        this.getChildren().add(levelingFlow);
    }


    private void initDescription() {
        final Label descriptionTitle = LabelBuilder.builder()
                .withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("blueprint.label.description"))
                .build();

        final Region descriptionRegion = new Region();
        HBox.setHgrow(descriptionRegion, Priority.ALWAYS);

        this.recipeHeader = BoxBuilder.builder().withNodes(descriptionTitle, descriptionRegion).buildHBox();

        this.getChildren().add(this.recipeHeader);
        if(GameVersion.LIVE.equals(this.blueprint.getGameVersion())) {
            final DestroyableLabel liveOnly = LabelBuilder.builder()
                    .withStyleClass("recipe-live").withText(LocaleService.getStringBinding("blueprint.is.live")).build();
            this.getChildren().add(liveOnly);
        }
        final Text description = TextBuilder.builder()
                .withStyleClass("blueprint-description-text")
                .withWrappingWidth(465D)
                .withText(LocaleService.getStringBinding(HorizonsBlueprintType.SYNTHESIS.equals(this.blueprint.getHorizonsBlueprintType()) || HorizonsBlueprintType.ENGINEER.equals(this.blueprint.getHorizonsBlueprintType()) ? this.blueprint.getHorizonsBlueprintName().getDescriptionLocalizationKey() : this.blueprint.getHorizonsBlueprintType().getDescriptionLocalizationKey(this.blueprint.getHorizonsBlueprintName().lcName())))
                .build();
        final TextFlow textFlow = new TextFlow(description);
        textFlow.getStyleClass().add("blueprint-description");
        this.getChildren().add( textFlow);
    }

    @SuppressWarnings("java:S1192")
    private void initRecipe() {
        this.addToWishlist = new MenuButton();
        this.addToWishlist.getStyleClass().add("recipe-wishlist-button");
        this.addToWishlist.textProperty().bind(LocaleService.getStringBinding("blueprint.add.to.wishlist"));
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
        this.addToWishlist.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (this.addToWishlist.getItems().size() == 1) {
                event.consume();
            }
        });
        this.addToWishlist.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            if (this.addToWishlist.getItems().size() == 1) {
                event.consume();
            }
        });
        this.countLabel = LabelBuilder.builder().withStyleClass("recipe-wishlist-count").build();
        this.countLabel.textProperty().bind(LocaleService.getStringBinding("blueprint.on.wishlist", 0));
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final HorizonsWishlists wishlists = loadCommanderWishlists(commander);
            loadInitialCount(wishlists);
        });
        final HBox box = BoxBuilder.builder()
                .withStyleClass("recipe-wishlist-count-box")
                .withNodes(this.countLabel, this.addToWishlist)
                .buildHBox();
        HBox.setHgrow(this.addToWishlist, Priority.ALWAYS);
        this.recipeHeader.getChildren().add(box);
        final Label materialHeader = LabelBuilder.builder()
                .withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("blueprint.header.material"))
                .build();
        this.getChildren().add(materialHeader);
    }

    private void loadInitialCount(final HorizonsWishlists wishlists) {
        final long count = wishlists.getSelectedWishlist().getItems().stream().filter(wishlistRecipe -> wishlistRecipe.getRecipeName().equals(this.blueprint.getBlueprintName())).count();
        if (count > 0L) {
            this.countLabel.textProperty().bind(LocaleService.getStringBinding("blueprint.on.wishlist", count));
        } else {
            this.countLabel.textProperty().bind(LocaleService.getStringBinding(() -> ""));
        }
    }

    private HorizonsWishlists loadCommanderWishlists(final Commander commander) {
        final HorizonsWishlists wishlists = WishlistService.getHorizonsWishlists(commander);
        this.addToWishlist.getItems().clear();
        final List<MenuItem> menuItems = wishlists.getAllWishlists().stream().filter(horizonsWishlist -> !horizonsWishlist.equals(HorizonsWishlist.ALL)).sorted(Comparator.comparing(HorizonsWishlist::getName)).flatMap(wishlist -> {
            final List<MenuItem> items = new ArrayList<>();
            if (this.blueprint instanceof HorizonsModuleBlueprint) {
                final MenuItem menuItemSingle = createMenuItem(commander, wishlist);
                menuItemSingle.textProperty().bind(LocaleService.getStringBinding("blueprint.add.to.wishlist.single.grade", wishlist.getName()));
                items.add(menuItemSingle);
                final MenuItem menuItemAll = createAllGradeMenuItem(commander, wishlist);
                menuItemAll.textProperty().bind(LocaleService.getStringBinding("blueprint.add.to.wishlist.all.grade", wishlist.getName()));
                items.add(menuItemAll);
            } else {
                final MenuItem menuItemSingle = createMenuItem(commander, wishlist);
                menuItemSingle.setText(wishlist.getName());
                items.add(menuItemSingle);
            }
            return items.stream();
        }).toList();
        this.addToWishlist.getItems().addAll(menuItems);
        return wishlists;
    }

    private MenuItem createMenuItem(final Commander commander, final HorizonsWishlist wishlist) {
        final MenuItem menuItem = new MenuItem();
        menuItem.setOnAction(event -> {
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
        });
        return menuItem;
    }

    private MenuItem createAllGradeMenuItem(final Commander commander, final HorizonsWishlist wishlist) {
        final MenuItem menuItem = new MenuItem();
        menuItem.setOnAction(event -> {
            final Set<HorizonsBlueprintGrade> blueprintGrades = HorizonsBlueprintConstants.getBlueprintGrades((this.blueprint.getBlueprintName()), this.blueprint.getHorizonsBlueprintType());

            final Map<HorizonsBlueprintGrade, Double> gradePercentages = new EnumMap<>(HorizonsBlueprintGrade.class);
            blueprintGrades.forEach(horizonsBlueprintGrade -> gradePercentages.put(horizonsBlueprintGrade, 1D));
            final HorizonsModuleWishlistBlueprint bp = new HorizonsModuleWishlistBlueprint(this.blueprint.getHorizonsBlueprintType(), gradePercentages);
            bp.setRecipeName((this.blueprint.getBlueprintName()));
            bp.setVisible(true);
            EventService.publish(new HorizonsWishlistBlueprintEvent(commander, wishlist.getUuid(), List.of(bp), Action.ADDED));
        });
        return menuItem;
    }


    private void initObjectivesLabel() {
        final Label materialHeader = LabelBuilder.builder()
                .withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("blueprint.header.objective"))
                .build();
        this.getChildren().add(materialHeader);
    }


    private void initEngineers() {
        final Label engineerLabelHeader = LabelBuilder.builder()
                .withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("blueprint.label.engineers"))
                .build();
        this.getChildren().add(engineerLabelHeader);
        final HBox[] engineerLabels = this.blueprint.getEngineers().stream()
                .filter(Predicate.not(Engineer.REMOTE_WORKSHOP::equals))
                .map(engineer -> {
                    final EngineerBlueprintLabel blueprintLabel = new EngineerBlueprintLabel(engineer, this.blueprint, true, this.blueprint.getHorizonsBlueprintGrade().getGrade());
                    this.destroyables.add(blueprintLabel);
                    return blueprintLabel;
                })
                .sorted(Comparator.comparing(engineerBlueprintLabel -> engineerBlueprintLabel.getLabel().getText()))
                .toArray(HBox[]::new);
        final FlowPane flowPane = FlowPaneBuilder.builder().withStyleClass("recipe-engineer-flow").withNodes(engineerLabels).build();
        this.getChildren().add(flowPane);

    }

    private void initModifiers() {
        final Map<HorizonsModifier, HorizonsModifierValue> modifierMap = this.blueprint.getModifiers();
        if (!modifierMap.isEmpty()) {
            final Label modifierTitle = LabelBuilder.builder().withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS).withText(LocaleService.getStringBinding("blueprint.label.modifiers")).build();
            this.getChildren().add(modifierTitle);

            final List<HBox> modifiers = modifierMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(entry -> {
                        final Label modifier = LabelBuilder.builder().withStyleClasses("recipe-modifier-name", entry.getValue().isPositive() ? "recipe-modifier-positive" : "recipe-modifier-negative").withText(LocaleService.getStringBinding(entry.getKey().getLocalizationKey())).build();

                        final Label value = LabelBuilder.builder().withStyleClasses("recipe-modifier-value", entry.getValue().isPositive() ? "recipe-modifier-positive" : "recipe-modifier-negative").withNonLocalizedText(entry.getValue().modification()).build();
                        final HBox modifierBox = BoxBuilder.builder().withStyleClass("recipe-modifier").withNodes(modifier, value).buildHBox();
                        HBox.setHgrow(value, Priority.ALWAYS);
                        return modifierBox;
                    })
                    .collect(Collectors.toCollection(ArrayList::new));

            final FlowPane modifiersFlowPane = FlowPaneBuilder.builder().withStyleClass("recipe-modifier-flow").withNodes(modifiers).build();
            this.getChildren().addAll(modifiersFlowPane);
        }
    }

    @Override
    public void initEventHandling() {
        this.eventListeners.add(EventService.addListener(this, HorizonsWishlistSelectedEvent.class, wishlistSelectedEvent -> {
            if (!(this.blueprint instanceof HorizonsEngineerBlueprint) || !this.ingredients.stream().allMatch(ingredient -> HorizonsStorageType.OTHER.equals(ingredient.getType()))) {//material based recipes
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final HorizonsWishlists wishlists = loadCommanderWishlists(commander);
                    loadInitialCount(wishlists);
                });
            }
        }));
        this.eventListeners.add(EventService.addListener(this, CommanderSelectedEvent.class, commanderSelectedEvent -> {
            if (!(this.blueprint instanceof HorizonsEngineerBlueprint) || !this.ingredients.stream().allMatch(ingredient -> HorizonsStorageType.OTHER.equals(ingredient.getType()))) {//material based recipes
                final HorizonsWishlists wishlists = loadCommanderWishlists(commanderSelectedEvent.getCommander());
                loadInitialCount(wishlists);
            }
        }));
        this.eventListeners.add(EventService.addListener(this, CommanderAllListedEvent.class, commanderAllListedEvent -> {
            if (!(this.blueprint instanceof HorizonsEngineerBlueprint) || !this.ingredients.stream().allMatch(ingredient -> HorizonsStorageType.OTHER.equals(ingredient.getType()))) {//material based recipes
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final HorizonsWishlists wishlists = loadCommanderWishlists(commander);
                    loadInitialCount(wishlists);
                });
            }
        }));
        this.eventListeners.add(EventService.addListener(this, HorizonsWishlistChangedEvent.class, wishlistEvent -> {
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
                    this.countLabel.textProperty().bind(LocaleService.getStringBinding("blueprint.on.wishlist", count));
                } else {
                    this.countLabel.textProperty().bind(LocaleService.getStringBinding(() -> ""));
                }
            }
        }));
    }

    private List<HorizonsMaterialIngredient> getRecipeIngredients(final HorizonsBlueprint recipe, final Class<? extends HorizonsMaterial> materialClass, final HorizonsStorageType storageType, final Map<? extends HorizonsMaterial, Integer> materialMap) {
        return recipe.getMaterialCollection(materialClass).entrySet().stream()
                .map(material -> {
                    final HorizonsMaterialIngredient horizonsMaterialIngredient = new HorizonsMaterialIngredient(storageType, material.getKey(), material.getValue(), materialMap.get(material.getKey()));
                    this.destroyables.add(horizonsMaterialIngredient);
                    return horizonsMaterialIngredient;
                })
                .sorted(Comparator.comparing(HorizonsMaterialIngredient::getName))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private final List<Destroyable> destroyables = new ArrayList<>();

    @Override
    public List<Destroyable> getDestroyablesList() {
        return this.destroyables;
    }

    @Override
    public void destroyInternal() {
        this.eventListeners.forEach(EventService::removeListener);
        this.ingredients.clear();
    }
}
