package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TextBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class HorizonsBlueprintContent extends VBox {
    private static final String RECIPE_TITLE_LABEL_STYLE_CLASS = "recipe-title-label";
    private final List<Ingredient> ingredients = new ArrayList<>();
    private final HorizonsBlueprint recipe;
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private Label countLabel;
    private HBox recipeHeader;
    private MenuButton addToWishlist;

    HorizonsBlueprintContent(final HorizonsBlueprint recipe) {
        this.recipe = recipe;
        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        this.getStyleClass().add("recipe-content");
        loadIngredients();
        initDescription();
        initRecipe();
        initIngredients();
        initEngineers();

        initModifiers();
    }


    private void loadIngredients() {
        this.ingredients.addAll(getRecipeIngredients(this.recipe, Raw.class, HorizonsStorageType.RAW, StorageService.getRaw()));
        this.ingredients.addAll(getRecipeIngredients(this.recipe, Encoded.class, HorizonsStorageType.ENCODED, StorageService.getEncoded()));
        this.ingredients.addAll(getRecipeIngredients(this.recipe, Manufactured.class, HorizonsStorageType.MANUFACTURED, StorageService.getManufactured()));
    }

    private void initIngredients() {
        final FlowPane ingredientFlow = FlowPaneBuilder.builder().withStyleClass("recipe-ingredient-flow").withNodes(this.ingredients).build();
        this.getChildren().add(ingredientFlow);
    }

    private void initDescription() {
        final Label descriptionTitle = LabelBuilder.builder()
                .withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("blueprint.label.description"))
                .build();

        final Region descriptionRegion = new Region();
        HBox.setHgrow(descriptionRegion, Priority.ALWAYS);

        this.recipeHeader = BoxBuilder.builder().withNodes(descriptionTitle, descriptionRegion).buildHBox();
        final Text description = TextBuilder.builder()
                .withStyleClass("recipe-description")
                .withWrappingWidth(465D)
                .withText(LocaleService.getStringBinding(this.recipe.getHorizonsBlueprintObjectName().getDescriptionLocalizationKey()))
                .build();

        this.getChildren().addAll(this.recipeHeader, description);
    }

    @SuppressWarnings("java:S1192")
    private void initRecipe() {
        this.addToWishlist = new MenuButton();
        this.addToWishlist.getStyleClass().add("recipe-wishlist-button");
        this.addToWishlist.textProperty().bind(LocaleService.getStringBinding("blueprint.add.to.wishlist"));
        this.addToWishlist.getItems().addAll(new ArrayList<>());
        this.countLabel = LabelBuilder.builder().withStyleClass("recipe-wishlist-count").build();
        this.countLabel.textProperty().bind(LocaleService.getStringBinding("blueprint.on.wishlist", 0));
//        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
//            final Wishlists wishlists = loadCommanderWishlists(commander);
//            loadInitialCount(wishlists);
//        });
        final HBox box = BoxBuilder.builder()
                .withStyleClass("recipe-wishlist-count-box")
                .withNodes(this.countLabel, this.addToWishlist)
                .buildHBox();
        HBox.setHgrow(this.addToWishlist, Priority.ALWAYS);
//        this.recipeHeader.getChildren().add(box);
        final Label materialHeader = LabelBuilder.builder()
                .withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("blueprint.header.material"))
                .build();
        this.getChildren().add(materialHeader);
    }

//    private void loadInitialCount(final Wishlists wishlists) {
//        final long count = wishlists.getSelectedWishlist().getItems().stream().filter(wishlistRecipe -> wishlistRecipe.getRecipeName().equals(this.recipe.getHorizonsRecipeObjectName())).count();
//        if (count > 0L) {
//            this.countLabel.textProperty().bind(LocaleService.getStringBinding("blueprint.on.wishlist", count));
//        } else {
//            this.countLabel.textProperty().bind(LocaleService.getStringBinding(() -> ""));
//        }
//    }
//
//    private Wishlists loadCommanderWishlists(final Commander commander) {
//        final Wishlists wishlists = APPLICATION_STATE.getWishlists(commander.getFid());
//        this.addToWishlist.getItems().clear();
//        final List<MenuItem> menuItems = wishlists.getAllWishlists().stream().sorted(Comparator.comparing(Wishlist::getName)).map(wishlist -> {
//            final MenuItem menuItem = new MenuItem();
//            menuItem.setOnAction(event -> EventService.publish(new WishlistRecipeEvent(commander.getFid(), wishlist.getUuid(), List.of(new WishlistRecipe(this.recipe.getHorizonsRecipeObjectName(), true)), Action.ADDED)));
//            menuItem.setText(wishlist.getName());
//            return menuItem;
//        }).toList();
//        this.addToWishlist.getItems().addAll(menuItems);
//        return wishlists;
//    }

//    private void initAsEngineerMission() {
//        final Label materialHeader = LabelBuilder.builder()
//                .withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS)
//                .withText(LocaleService.getStringBinding("blueprint.header.objective"))
//                .build();
//        this.getChildren().add(materialHeader);
//    }

    private void initEngineers() {
        final Label engineerLabelHeader = LabelBuilder.builder()
                .withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("blueprint.label.engineers"))
                .build();
        this.getChildren().add(engineerLabelHeader);
        final Label[] engineerLabels = ((HorizonsBlueprint) this.recipe).getEngineers().stream()
                .map(EngineerModuleLabel::new)
                .sorted(Comparator.comparing(EngineerModuleLabel::getText))
                .toArray(Label[]::new);
        final FlowPane flowPane = FlowPaneBuilder.builder().withStyleClass("recipe-engineer-flow").withNodes(engineerLabels).build();
        this.getChildren().add(flowPane);

    }

    private void initModifiers() {
        final Map<HorizonsModifier, HorizonsModifierValue> modifierMap = this.recipe.getModifiers();
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

    private void initEventHandling() {
//        EventService.addListener(this, WishlistSelectedEvent.class, wishlistSelectedEvent -> {
//            if (!(this.recipe instanceof EngineerRecipe) || this.ingredients.stream().noneMatch(ingredient -> OdysseyStorageType.OTHER.equals(ingredient.getType()))) {//material based recipes
//                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
//                    final Wishlists wishlists = loadCommanderWishlists(commander);
//                    loadInitialCount(wishlists);
//                });
//            }
//        });
//        EventService.addListener(this, CommanderSelectedEvent.class, commanderSelectedEvent -> {
//            if (!(this.recipe instanceof EngineerRecipe) || this.ingredients.stream().noneMatch(ingredient -> OdysseyStorageType.OTHER.equals(ingredient.getType()))) {//material based recipes
//                final Wishlists wishlists = loadCommanderWishlists(commanderSelectedEvent.getCommander());
//                loadInitialCount(wishlists);
//            }
//        });
//        EventService.addListener(this, CommanderAllListedEvent.class, commanderAllListedEvent -> {
//            if (!(this.recipe instanceof EngineerRecipe) || this.ingredients.stream().noneMatch(ingredient -> OdysseyStorageType.OTHER.equals(ingredient.getType()))) {//material based recipes
//                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
//                    final Wishlists wishlists = loadCommanderWishlists(commander);
//                    loadInitialCount(wishlists);
//                });
//            }
//        });
//        EventService.addListener(this, WishlistChangedEvent.class, wishlistEvent -> {
//            if (this.countLabel != null) {
//                final long count = APPLICATION_STATE.getPreferredCommander().map(commander -> APPLICATION_STATE.getWishlists(commander.getFid()).getSelectedWishlist().getItems().stream().filter(wishlistRecipe -> wishlistRecipe.getRecipeName().equals(this.recipe.getRecipeName())).count()).orElse(0L);
//                if (count > 0L) {
//                    this.countLabel.textProperty().bind(LocaleService.getStringBinding("blueprint.on.wishlist", count));
//                } else {
//                    this.countLabel.textProperty().bind(LocaleService.getStringBinding(() -> ""));
//                }
//            }
//        });
    }

    private List<HorizonsMaterialIngredient> getRecipeIngredients(final HorizonsBlueprint recipe, final Class<? extends HorizonsMaterial> materialClass, final HorizonsStorageType storageType, final Map<? extends HorizonsMaterial, Integer> materialMap) {
        return recipe.getMaterialCollection(materialClass).entrySet().stream()
                .map(material -> new HorizonsMaterialIngredient(storageType, material.getKey(), material.getValue(), materialMap.get(material.getKey())))
                .sorted(Comparator.comparing(HorizonsMaterialIngredient::getName))
                .collect(Collectors.toCollection(ArrayList::new));
    }


}
