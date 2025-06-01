package nl.jixxed.eliteodysseymaterials.templates.horizons.menu;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsBlueprintClickEvent;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsWishlistChangedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsWishlistSelectedEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.EngineerBlueprintLabel;
import nl.jixxed.eliteodysseymaterials.templates.generic.Ingredient;
import nl.jixxed.eliteodysseymaterials.templates.generic.MissionIngredient;
import nl.jixxed.eliteodysseymaterials.templates.generic.menu.Modifier;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsMaterialIngredient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class HorizonsBlueprintContent extends DestroyableVBox implements DestroyableEventTemplate {
    private static final String TITLE_STYLE_CLASS = "title";
    private static final String SPACING = "spacing";
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private DestroyableLabel countLabel;
    private List<Ingredient> ingredients = new ArrayList<>();
    private final HorizonsBlueprint blueprint;
    private DestroyableHBox recipeHeader;
    private AddToWishlistMenuButton addToWishlist;


    HorizonsBlueprintContent(final HorizonsBlueprint blueprint) {
        this.blueprint = blueprint;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("blueprint-content");
        loadIngredients();

        initSwitchButton();
        initHeader();


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

    private void initHeader() {
        final DestroyableRegion headerRegion = new DestroyableRegion();
        HBox.setHgrow(headerRegion, Priority.ALWAYS);
        this.recipeHeader = BoxBuilder.builder()
                .withNodes(headerRegion).buildHBox();
        this.getNodes().addAll(this.recipeHeader);

    }

    private void initSwitchButton() {
        if (this.blueprint instanceof HorizonsModuleBlueprint && HorizonsBlueprintConstants.getExperimentalEffects().containsKey(this.blueprint.getHorizonsBlueprintName())) {
            final DestroyableButton experimentalEffects = ButtonBuilder.builder()
                    .withStyleClass("switch")
                    .withText("blueprint.category.name.experimental_effects")
                    .withOnAction(_ ->
                            EventService.publish(new HorizonsBlueprintClickEvent(this.blueprint, true))
                    )
                    .build();
            this.getNodes().addAll(experimentalEffects, new GrowingRegion("switch-spacer"));
        }
        if (this.blueprint instanceof HorizonsExperimentalEffectBlueprint) {
            final DestroyableButton blueprints = ButtonBuilder.builder()
                    .withStyleClass("switch")
                    .withText("blueprint.category.name.blueprints")
                    .withOnAction(_ ->
                            EventService.publish(new HorizonsBlueprintClickEvent(this.blueprint, false)))
                    .build();

            this.getNodes().addAll(blueprints, new GrowingRegion("switch-spacer"));
        }
    }

    private void initClosestTrader(final List<HorizonsBrokerType> horizonsBrokerTypes) {

        final DestroyableLabel title = LabelBuilder.builder()
                .withStyleClasses(TITLE_STYLE_CLASS, SPACING)
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
                .withStyleClass("ingredient-flow")
                .withNodes(this.ingredients.stream().filter(ingredient -> !ingredient.getType().equals(HorizonsStorageType.OTHER)).toList())
                .build();
        this.getNodes().add(ingredientFlow);
    }

    private void initObjectives() {
        final DestroyableFlowPane ingredientFlow = FlowPaneBuilder.builder()
                .withStyleClass("ingredient-flow")
                .withNodes(this.ingredients.stream().filter(ingredient -> ingredient.getType().equals(HorizonsStorageType.OTHER)).toList())
                .build();
        this.getNodes().add(ingredientFlow);
    }

    private void initTips(final HorizonsEngineerBlueprint engineerBlueprint) {

        final DestroyableLabel levelingHeader = LabelBuilder.builder()
                .withStyleClasses(TITLE_STYLE_CLASS, SPACING)
                .withText("blueprint.label.leveling")
                .build();
        this.getNodes().add(levelingHeader);
        final DestroyableFlowPane levelingFlow = FlowPaneBuilder.builder()
                .withStyleClass("ingredient-flow")
                .withNodes(engineerBlueprint.getLeveling().stream()
                        .map(levelingTip -> BoxBuilder.builder()
                                .withStyleClass("leveling")
                                .withNode(LabelBuilder.builder()
                                        .withStyleClass("name")
                                        .withText(levelingTip)
                                        .build())
                                .buildVBox())
                        .toList())
                .build();
        this.getNodes().add(levelingFlow);
    }


    private void initDescription() {
        final DestroyableLabel descriptionTitle = LabelBuilder.builder()
                .withStyleClass(TITLE_STYLE_CLASS)
                .withText("blueprint.label.description")
                .build();
        this.getNodes().add(descriptionTitle);
        if (GameVersion.LIVE.equals(this.blueprint.getGameVersion())) {
            final DestroyableLabel liveOnly = LabelBuilder.builder()
                    .withStyleClass("live")
                    .withText("blueprint.is.live")
                    .build();
            this.getNodes().add(liveOnly);
            VBox.setVgrow(liveOnly, Priority.ALWAYS);
        }
        final DestroyableLabel description = LabelBuilder.builder()
                .withStyleClass("description")
                .withText(HorizonsBlueprintType.SYNTHESIS.equals(this.blueprint.getHorizonsBlueprintType()) || HorizonsBlueprintType.ENGINEER.equals(this.blueprint.getHorizonsBlueprintType()) ? this.blueprint.getHorizonsBlueprintName().getDescriptionLocalizationKey() : this.blueprint.getHorizonsBlueprintType().getDescriptionLocalizationKey(this.blueprint.getHorizonsBlueprintName().lcName()))
                .build();
        this.getNodes().addAll(description);
        HBox.setHgrow(description, Priority.ALWAYS);
        VBox.setVgrow(description, Priority.ALWAYS);
    }

    @SuppressWarnings("java:S1192")
    private void initRecipe() {
        this.addToWishlist = new AddToWishlistMenuButton();
        this.countLabel = LabelBuilder.builder()
                .withStyleClass("wishlist-count")
                .build();
        this.countLabel.addBinding(this.countLabel.textProperty(), LocaleService.getStringBinding("blueprint.on.wishlist", 0));
        APPLICATION_STATE.getPreferredCommander().ifPresent(this::updateWishlistsAndCount);
        final DestroyableHBox box = BoxBuilder.builder()
                .withStyleClass("wishlist-count-box")
                .withNodes(this.countLabel, this.addToWishlist)
                .buildHBox();
        HBox.setHgrow(this.addToWishlist, Priority.ALWAYS);
        this.recipeHeader.getNodes().add(box);
        final DestroyableLabel materialHeader = LabelBuilder.builder()
                .withStyleClasses(TITLE_STYLE_CLASS, SPACING)
                .withText("blueprint.header.material")
                .build();
        this.getNodes().add(materialHeader);
    }

    private void updateWishlistsAndCount(Commander commander) {
        loadCommanderWishlists(commander);
        updateCount(getBlueprintCountForCurrentWishtlist(commander, this.blueprint.getBlueprintName()));
    }

    private long getBlueprintCountForCurrentWishtlist(Commander commander, HorizonsBlueprintName blueprintName) {
        return WishlistService.getHorizonsWishlists(commander).getSelectedWishlist().getItems().stream()
                .filter(wishlistRecipe -> wishlistRecipe.getRecipeName().equals(blueprintName))
                .count();
    }

    private void loadCommanderWishlists(final Commander commander) {
        this.addToWishlist.loadCommanderWishlists(commander, this.blueprint);
    }


    private void updateCount(final long count) {
        if (count > 0L) {
            this.countLabel.addBinding(this.countLabel.textProperty(), LocaleService.getStringBinding("blueprint.on.wishlist", count));
        } else {
            this.countLabel.addBinding(this.countLabel.textProperty(), LocaleService.getStringBinding(() -> ""));
        }
    }


    private void initObjectivesLabel() {
        final DestroyableLabel materialHeader = LabelBuilder.builder()
                .withStyleClasses(TITLE_STYLE_CLASS, SPACING)
                .withText("blueprint.header.objective")
                .build();
        this.getNodes().add(materialHeader);
    }


    private void initEngineers() {
        final DestroyableLabel engineerLabelHeader = LabelBuilder.builder()
                .withStyleClasses(TITLE_STYLE_CLASS, SPACING)
                .withText("blueprint.label.engineers")
                .build();
        this.getNodes().add(engineerLabelHeader);
        final EngineerBlueprintLabel[] engineerLabels = this.blueprint.getEngineers().stream()
                .filter(Predicate.not(Engineer.REMOTE_WORKSHOP::equals))
                .map(engineer -> new EngineerBlueprintLabel(engineer, this.blueprint, true, this.blueprint.getHorizonsBlueprintGrade().getGrade()))
                .sorted(Comparator.comparing(EngineerBlueprintLabel::getEngineerName))
                .toArray(EngineerBlueprintLabel[]::new);
        final DestroyableFlowPane flowPane = FlowPaneBuilder.builder()
                .withStyleClass("engineer-flow")
                .withNodes(engineerLabels)
                .build();
        this.getNodes().add(flowPane);

    }

    private void initModifiers() {
        final Map<HorizonsModifier, HorizonsModifierValue> modifierMap = this.blueprint.getModifiers();
        if (!modifierMap.isEmpty()) {
            final DestroyableLabel modifierTitle = LabelBuilder.builder()
                    .withStyleClasses(TITLE_STYLE_CLASS, SPACING)
                    .withText("blueprint.label.modifiers")
                    .build();
            this.getNodes().add(modifierTitle);

            final List<DestroyableHBox> modifiers = modifierMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(entry -> new Modifier(entry.getKey().getLocalizationKey(), entry.getValue().modification(), entry.getValue().isPositive()))
                    .collect(Collectors.toCollection(ArrayList::new));

            final DestroyableFlowPane modifiersFlowPane = FlowPaneBuilder.builder()
                    .withStyleClass("modifier-flow")
                    .withNodes(modifiers)
                    .build();
            this.getNodes().addAll(modifiersFlowPane);
        }
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, HorizonsWishlistSelectedEvent.class, wishlistSelectedEvent -> {
            if (!(this.blueprint instanceof HorizonsEngineerBlueprint) || !this.ingredients.stream().allMatch(ingredient -> HorizonsStorageType.OTHER.equals(ingredient.getType()))) {//material based recipes
                APPLICATION_STATE.getPreferredCommander().ifPresent(this::updateWishlistsAndCount);
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

    private List<HorizonsMaterialIngredient> getRecipeIngredients(final HorizonsBlueprint blueprint, final Class<? extends HorizonsMaterial> materialClass, final HorizonsStorageType storageType) {
        return blueprint.getMaterialCollection(materialClass).entrySet().stream()
                .sorted(Comparator.comparing(entry -> LocaleService.getLocalizedStringForCurrentLocale(entry.getKey().getLocalizationKey())))
                .map(material ->
                        new HorizonsMaterialIngredient(blueprint, storageType, material.getKey(), material.getValue(),
                                storageType == HorizonsStorageType.COMMODITY
                                        ? StorageService.getCommodityCount((Commodity) material.getKey(), StoragePool.SHIP)
                                        : StorageService.getMaterialCount(material.getKey())))
                .toList();
    }

}
