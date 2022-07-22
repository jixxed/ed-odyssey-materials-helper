package nl.jixxed.eliteodysseymaterials.templates.odyssey.menu;

import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TextBuilder;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
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

class OdysseyBlueprintContent extends VBox {
    private static final String RECIPE_TITLE_LABEL_STYLE_CLASS = "recipe-title-label";
    private final List<Ingredient> ingredients = new ArrayList<>();
    private final OdysseyBlueprint blueprint;
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private Label countLabel;
    private HBox recipeHeader;
    private MenuButton addToWishlist;

    OdysseyBlueprintContent(final OdysseyBlueprint blueprint) {
        this.blueprint = blueprint;
        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        this.getStyleClass().add("recipe-content");
        loadIngredients();
        initDescription();

        if (!(this.blueprint instanceof EngineerBlueprint) || this.ingredients.stream().noneMatch(ingredient -> OdysseyStorageType.OTHER.equals(ingredient.getType()))) {//material based recipes
            initAsRecipe();
        } else {//mission based recipes
            initAsEngineerMission();
        }
        initIngredients();
        if (this.blueprint instanceof EngineerBlueprint engineerRecipe && engineerRecipe.getBlueprintName().equals(OdysseyBlueprintName.ENGINEER_D3)) {
            initSteps();
        }
        if (this.blueprint instanceof ModuleBlueprint) {
            initEngineers();
        }
        initModifiers();
    }

    private void initSteps() {
        final Label referralLabelHeader = LabelBuilder.builder()
                .withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("blueprint.label.completed.referrals"))
                .build();
        this.getChildren().add(referralLabelHeader);
        final HBox[] engineerLabels = Stream.of(Engineer.BALTANOS, Engineer.ROSA_DAYETTE, Engineer.ELEANOR_BRESA)
                .map(engineer -> new EngineerBlueprintLabel(engineer, true, 0))
                .sorted(Comparator.comparing(engineerBlueprintLabel -> engineerBlueprintLabel.getLabel().getText()))
                .toArray(HBox[]::new);
        final FlowPane flowPane = FlowPaneBuilder.builder().withStyleClass("recipe-engineer-flow").withNodes(engineerLabels).build();
        this.getChildren().add(flowPane);
    }

    private void loadIngredients() {
        this.ingredients.addAll(getRecipeIngredients(Good.class, OdysseyStorageType.GOOD, StorageService.getGoods()));
        this.ingredients.addAll(getRecipeIngredients(Asset.class, OdysseyStorageType.ASSET, StorageService.getAssets()));
        this.ingredients.addAll(getRecipeIngredients(Data.class, OdysseyStorageType.DATA, StorageService.getData()));
        if (this.blueprint instanceof EngineerBlueprint engineerRecipe) {
            this.ingredients.addAll(engineerRecipe.getOther().stream()
                    .map(text -> new MissionIngredient(text, OdysseyStorageType.OTHER))
                    .sorted(Comparator.comparing(MissionIngredient::getName))
                    .collect(Collectors.toCollection(ArrayList::new)));
        }
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
                .withStyleClass("blueprint-description-text")
                .withText(LocaleService.getStringBinding(this.blueprint.getBlueprintName().getDescriptionLocalizationKey()))
                .build();
        description.wrappingWidthProperty().bind(this.widthProperty().subtract(35));
        final TextFlow textFlow = new TextFlow(description);
        textFlow.getStyleClass().add("blueprint-description");
        this.getChildren().addAll(this.recipeHeader, textFlow);
    }

    @SuppressWarnings("java:S1192")
    private void initAsRecipe() {
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
            final Wishlists wishlists = loadCommanderWishlists(commander);
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

    private void loadInitialCount(final Wishlists wishlists) {
        final long count = wishlists.getSelectedWishlist().getItems().stream().filter(wishlistRecipe -> wishlistRecipe.getRecipeName().equals(this.blueprint.getBlueprintName())).count();
        if (count > 0L) {
            this.countLabel.textProperty().bind(LocaleService.getStringBinding("blueprint.on.wishlist", count));
        } else {
            this.countLabel.textProperty().bind(LocaleService.getStringBinding(() -> ""));
        }
    }

    private Wishlists loadCommanderWishlists(final Commander commander) {
        final Wishlists wishlists = APPLICATION_STATE.getWishlists(commander.getFid());
        this.addToWishlist.getItems().clear();
        final List<MenuItem> menuItems = wishlists.getAllWishlists().stream().filter(wishlist -> wishlist != Wishlist.ALL).sorted(Comparator.comparing(Wishlist::getName)).map(wishlist -> {
            final MenuItem menuItem = new MenuItem();
            menuItem.setOnAction(event -> EventService.publish(new WishlistBlueprintEvent(commander.getFid(), wishlist.getUuid(), List.of(new OdysseyWishlistBlueprint((OdysseyBlueprintName) this.blueprint.getBlueprintName(), true)), Action.ADDED)));
            menuItem.setText(wishlist.getName());
            return menuItem;
        }).toList();
        this.addToWishlist.getItems().addAll(menuItems);
        return wishlists;
    }

    private void initAsEngineerMission() {
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
        final HBox[] engineerLabels = ((ModuleBlueprint) this.blueprint).getEngineers().stream()
                .map(EngineerBlueprintLabel::new)
                .sorted(Comparator.comparing(engineerBlueprintLabel -> engineerBlueprintLabel.getLabel().getText()))
                .toArray(HBox[]::new);
        final FlowPane flowPane = FlowPaneBuilder.builder().withStyleClass("recipe-engineer-flow").withNodes(engineerLabels).build();
        this.getChildren().add(flowPane);

    }

    private void initModifiers() {
        final Map<OdysseyModifier, String> modifierMap = this.blueprint.getModifiers();
        if (!modifierMap.isEmpty()) {
            final Label modifierTitle = LabelBuilder.builder().withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS).withText(LocaleService.getStringBinding("blueprint.label.modifiers")).build();
            this.getChildren().add(modifierTitle);

            final List<HBox> modifiers = modifierMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(modifierStringEntry -> {
                        final Label modifier = LabelBuilder.builder().withStyleClass("recipe-modifier-name").withText(LocaleService.getStringBinding(modifierStringEntry.getKey().getLocalizationKey())).build();
                        final Label value = LabelBuilder.builder().withStyleClass("recipe-modifier-value").withNonLocalizedText(modifierStringEntry.getValue()).build();
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
        EventService.addListener(this, WishlistSelectedEvent.class, wishlistSelectedEvent -> {
            if (!(this.blueprint instanceof EngineerBlueprint) || this.ingredients.stream().noneMatch(ingredient -> OdysseyStorageType.OTHER.equals(ingredient.getType()))) {//material based recipes
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final Wishlists wishlists = loadCommanderWishlists(commander);
                    loadInitialCount(wishlists);
                });
            }
        });
        EventService.addListener(this, CommanderSelectedEvent.class, commanderSelectedEvent -> {
            if (!(this.blueprint instanceof EngineerBlueprint) || this.ingredients.stream().noneMatch(ingredient -> OdysseyStorageType.OTHER.equals(ingredient.getType()))) {//material based recipes
                final Wishlists wishlists = loadCommanderWishlists(commanderSelectedEvent.getCommander());
                loadInitialCount(wishlists);
            }
        });
        EventService.addListener(this, CommanderAllListedEvent.class, commanderAllListedEvent -> {
            if (!(this.blueprint instanceof EngineerBlueprint) || this.ingredients.stream().noneMatch(ingredient -> OdysseyStorageType.OTHER.equals(ingredient.getType()))) {//material based recipes
                APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                    final Wishlists wishlists = loadCommanderWishlists(commander);
                    loadInitialCount(wishlists);
                });
            }
        });
        EventService.addListener(this, WishlistChangedEvent.class, wishlistEvent -> {
            if (this.countLabel != null) {
                final long count = APPLICATION_STATE.getPreferredCommander().map(commander -> APPLICATION_STATE.getWishlists(commander.getFid()).getSelectedWishlist().getItems().stream().filter(wishlistRecipe -> wishlistRecipe.getRecipeName().equals(this.blueprint.getBlueprintName())).count()).orElse(0L);
                if (count > 0L) {
                    this.countLabel.textProperty().bind(LocaleService.getStringBinding("blueprint.on.wishlist", count));
                } else {
                    this.countLabel.textProperty().bind(LocaleService.getStringBinding(() -> ""));
                }
            }
        });
    }

    private List<OdysseyMaterialIngredient> getRecipeIngredients(final Class<? extends OdysseyMaterial> materialClass, final OdysseyStorageType storageType, final Map<? extends OdysseyMaterial, Storage> materialMap) {
        return this.blueprint.getMaterialCollection(materialClass).entrySet().stream()
                .map(material -> new OdysseyMaterialIngredient(storageType, material.getKey(), material.getValue(), materialMap.get(material.getKey()).getTotalValue()))
                .sorted(Comparator.comparing(OdysseyMaterialIngredient::getName))
                .collect(Collectors.toCollection(ArrayList::new));
    }


}
