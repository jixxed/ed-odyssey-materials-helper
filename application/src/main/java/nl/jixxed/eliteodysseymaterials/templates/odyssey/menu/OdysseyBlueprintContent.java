package nl.jixxed.eliteodysseymaterials.templates.odyssey.menu;

import javafx.collections.ListChangeListener;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.EngineerBlueprintLabel;
import nl.jixxed.eliteodysseymaterials.templates.generic.Ingredient;
import nl.jixxed.eliteodysseymaterials.templates.generic.MissionIngredient;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyMaterialIngredient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class OdysseyBlueprintContent extends DestroyableVBox implements DestroyableEventTemplate {
    private static final String RECIPE_TITLE_LABEL_STYLE_CLASS = "recipe-title-label";
    private final List<Ingredient> ingredients = new ArrayList<>();
    private final OdysseyBlueprint blueprint;
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private DestroyableLabel countLabel;
    private DestroyableHBox recipeHeader;
    private DestroyableMenuButton addToWishlist;


    OdysseyBlueprintContent(final OdysseyBlueprint blueprint) {
        this.blueprint = blueprint;
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().add("recipe-content");
        loadIngredients();
        initDescription();

        if (!(this.blueprint instanceof EngineerBlueprint) || this.ingredients.stream().noneMatch(ingredient -> OdysseyStorageType.OTHER.equals(ingredient.getType()))) {//material based recipes
            initAsRecipe();
        } else {//mission based recipes
            initAsEngineerMission();
        }
        initIngredients();
        if (this.blueprint instanceof EngineerBlueprint engineerBlueprint) {

            initTips(engineerBlueprint);
            if (engineerBlueprint.getBlueprintName().equals(OdysseyBlueprintName.ENGINEER_D3)) {
                initSteps();
            }
        }
        if (this.blueprint instanceof ModuleBlueprint) {
            initEngineers();
        }
        initModifiers();
    }

    private void initSteps() {
        final DestroyableLabel referralLabelHeader = LabelBuilder.builder()
                .withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS)
                .withText("blueprint.label.completed.referrals")
                .build();
        this.getNodes().add(referralLabelHeader);
        final DestroyableHBox[] engineerLabels = Stream.of(Engineer.BALTANOS, Engineer.ROSA_DAYETTE, Engineer.ELEANOR_BRESA)
                .map(engineer -> new EngineerBlueprintLabel(engineer, true, 0))
                .sorted(Comparator.comparing(EngineerBlueprintLabel::getEngineerName))
                .toArray(DestroyableHBox[]::new);
        final DestroyableFlowPane flowPane = FlowPaneBuilder.builder()
                .withStyleClass("recipe-engineer-flow")
                .withNodes(engineerLabels)
                .build();
        this.getNodes().add(flowPane);
    }

    private void loadIngredients() {
        this.ingredients.addAll(getRecipeIngredients(Good.class, OdysseyStorageType.GOOD));
        this.ingredients.addAll(getRecipeIngredients(Asset.class, OdysseyStorageType.ASSET));
        this.ingredients.addAll(getRecipeIngredients(Data.class, OdysseyStorageType.DATA));
        if (this.blueprint instanceof EngineerBlueprint engineerRecipe) {
            this.ingredients.addAll(engineerRecipe.getOther().stream()
                    .map(text -> new MissionIngredient(text, OdysseyStorageType.OTHER))
                    .sorted(Comparator.comparing(MissionIngredient::getName))
                    .collect(Collectors.toCollection(ArrayList::new)));
        }
    }

    private void initIngredients() {
        final DestroyableFlowPane ingredientFlow = FlowPaneBuilder.builder()
                .withStyleClass("recipe-ingredient-flow")
                .withNodes(this.ingredients)
                .build();
        this.getNodes().add(ingredientFlow);
    }

    private void initDescription() {
        final DestroyableLabel descriptionTitle = LabelBuilder.builder()
                .withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS)
                .withText("blueprint.label.description")
                .build();

        final DestroyableRegion descriptionRegion = new DestroyableRegion();
        HBox.setHgrow(descriptionRegion, Priority.ALWAYS);

        this.recipeHeader = BoxBuilder.builder()
                .withNodes(descriptionTitle, descriptionRegion).buildHBox();
        final DestroyableLabel description = LabelBuilder.builder()
                .withStyleClass("blueprint-description")
                .withText(this.blueprint.getBlueprintName().getDescriptionLocalizationKey())
                .build();
        this.getNodes().addAll(this.recipeHeader, description);
    }

    private void initTips(final EngineerBlueprint engineerBlueprint) {
        final DestroyableLabel tipsTitle = LabelBuilder.builder()
                .withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS)
                .withText("blueprint.label.tips")
                .build();

        final DestroyableLabel description = LabelBuilder.builder()
                .withStyleClass("blueprint-description")
                .withText(engineerBlueprint.getTipsLocalizationKey())
                .build();
        this.getNodes().addAll(tipsTitle, description);
    }

    @SuppressWarnings("java:S1192")
    private void initAsRecipe() {
        this.addToWishlist = MenuButtonBuilder.builder().build();
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
        this.addToWishlist.addEventBinding(this.addToWishlist.onMouseClickedProperty(), event -> {
            if (this.addToWishlist.getItems().size() == 1) {
                this.addToWishlist.getItems().getFirst().fire();
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
            final Wishlists wishlists = loadCommanderWishlists(commander);
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

    private void loadInitialCount(final Wishlists wishlists) {
        final long count = wishlists.getSelectedWishlist().getItems().stream().filter(wishlistRecipe -> wishlistRecipe.getRecipeName().equals(this.blueprint.getBlueprintName())).count();
        if (count > 0L) {
            this.countLabel.addBinding(this.countLabel.textProperty(), LocaleService.getStringBinding("blueprint.on.wishlist", count));
        } else {
            this.countLabel.addBinding(this.countLabel.textProperty(), LocaleService.getStringBinding(() -> ""));
        }
    }

    private Wishlists loadCommanderWishlists(final Commander commander) {
        final Wishlists wishlists = WishlistService.getWishlists(commander);
        this.addToWishlist.deregisterAll((List<DestroyableMenuItem>) (List<?>) this.addToWishlist.getItems());
        this.addToWishlist.getItems().forEach(item -> ((DestroyableComponent) item).destroy());
        this.addToWishlist.getItems().clear();
        final List<DestroyableMenuItem> menuItems = wishlists.getAllWishlists().stream()
                .filter(wishlist -> wishlist != Wishlist.ALL)
                .sorted(Comparator.comparing(Wishlist::getName))
                .map(wishlist -> MenuItemBuilder.builder()
                        .withNonLocalizedText(wishlist.getName())
                        .withOnAction(event -> EventService.publish(new WishlistBlueprintEvent(commander, wishlist.getUuid(), List.of(new OdysseyWishlistBlueprint((OdysseyBlueprintName) this.blueprint.getBlueprintName(), true)), Action.ADDED)))
                        .build())
                .toList();
        this.addToWishlist.getItems().addAll(menuItems);
        this.addToWishlist.registerAll(menuItems);
        return wishlists;
    }

    private void initAsEngineerMission() {
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
        final DestroyableHBox[] engineerLabels = ((ModuleBlueprint) this.blueprint).getEngineers().stream()
                .map(EngineerBlueprintLabel::new)
                .sorted(Comparator.comparing(EngineerBlueprintLabel::getEngineerName))
                .toArray(DestroyableHBox[]::new);
        final DestroyableFlowPane flowPane = FlowPaneBuilder.builder()
                .withStyleClass("recipe-engineer-flow")
                .withNodes(engineerLabels)
                .build();
        this.getNodes().add(flowPane);

    }

    private void initModifiers() {
        final Map<OdysseyModifier, String> modifierMap = this.blueprint.getModifiers();
        if (!modifierMap.isEmpty()) {
            final DestroyableLabel modifierTitle = LabelBuilder.builder()
                    .withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS)
                    .withText("blueprint.label.modifiers")
                    .build();
            this.getNodes().add(modifierTitle);

            final List<DestroyableHBox> modifiers = modifierMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(modifierStringEntry -> {
                        final DestroyableLabel modifier = LabelBuilder.builder()
                                .withStyleClass("recipe-modifier-name")
                                .withText(modifierStringEntry.getKey().getLocalizationKey())
                                .build();
                        final DestroyableLabel value = LabelBuilder.builder()
                                .withStyleClass("recipe-modifier-value")
                                .withNonLocalizedText(modifierStringEntry.getValue())
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

    public void initEventHandling() {
        register(EventService.addListener(true, this, WishlistSelectedEvent.class, wishlistSelectedEvent -> {
            if (!(this.blueprint instanceof EngineerBlueprint) || this.ingredients.stream().noneMatch(ingredient -> OdysseyStorageType.OTHER.equals(ingredient.getType()))) {//material based recipes
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final Wishlists wishlists = loadCommanderWishlists(commander);
                    loadInitialCount(wishlists);
                });
            }
        }));
        register(EventService.addListener(true, this, CommanderSelectedEvent.class, commanderSelectedEvent -> {
            if (!(this.blueprint instanceof EngineerBlueprint) || this.ingredients.stream().noneMatch(ingredient -> OdysseyStorageType.OTHER.equals(ingredient.getType()))) {//material based recipes
                final Wishlists wishlists = loadCommanderWishlists(commanderSelectedEvent.getCommander());
                loadInitialCount(wishlists);
            }
        }));
        register(EventService.addListener(true, this, CommanderAllListedEvent.class, commanderAllListedEvent -> {
            if (!(this.blueprint instanceof EngineerBlueprint) || this.ingredients.stream().noneMatch(ingredient -> OdysseyStorageType.OTHER.equals(ingredient.getType()))) {//material based recipes
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final Wishlists wishlists = loadCommanderWishlists(commander);
                    loadInitialCount(wishlists);
                });
            }
        }));
        register(EventService.addListener(true, this, WishlistChangedEvent.class, wishlistEvent -> {
            if (this.countLabel != null) {
                final long count = APPLICATION_STATE.getPreferredCommander().map(commander -> WishlistService.getWishlists(commander).getSelectedWishlist().getItems().stream().filter(wishlistRecipe -> wishlistRecipe.getRecipeName().equals(this.blueprint.getBlueprintName())).count()).orElse(0L);
                if (count > 0L) {
                    this.countLabel.addBinding(this.countLabel.textProperty(), LocaleService.getStringBinding("blueprint.on.wishlist", count));
                } else {
                    this.countLabel.addBinding(this.countLabel.textProperty(), LocaleService.getStringBinding(() -> ""));
                }
            }
        }));
    }

    private List<OdysseyMaterialIngredient> getRecipeIngredients(final Class<? extends OdysseyMaterial> materialClass, final OdysseyStorageType storageType) {
        return this.blueprint.getMaterialCollection(materialClass).entrySet().stream()
                .map(material -> new OdysseyMaterialIngredient(storageType, material.getKey(), material.getValue(), StorageService.getMaterialCount(material.getKey(), AmountType.TOTAL)))
                .sorted(Comparator.comparing(OdysseyMaterialIngredient::getName))
                .collect(Collectors.toCollection(ArrayList::new));
    }


}
