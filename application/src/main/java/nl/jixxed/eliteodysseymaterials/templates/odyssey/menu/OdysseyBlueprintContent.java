package nl.jixxed.eliteodysseymaterials.templates.odyssey.menu;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyWishlistChangedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyWishlistSelectedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.EngineerBlueprintLabel;
import nl.jixxed.eliteodysseymaterials.templates.generic.Ingredient;
import nl.jixxed.eliteodysseymaterials.templates.generic.MissionIngredient;
import nl.jixxed.eliteodysseymaterials.templates.generic.menu.Modifier;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyMaterialIngredient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class OdysseyBlueprintContent extends DestroyableVBox implements DestroyableEventTemplate {
    private static final String TITLE_STYLE_CLASS = "title";
    public static final String SPACING = "spacing";
    private final List<Ingredient> ingredients = new ArrayList<>();
    private final OdysseyBlueprint blueprint;
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private DestroyableLabel countLabel;
    private DestroyableHBox recipeHeader;
    private AddToWishlistMenuButton addToWishlist;


    OdysseyBlueprintContent(final OdysseyBlueprint blueprint) {
        this.blueprint = blueprint;
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().add("blueprint-content");
        loadIngredients();
        initHeader();
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
                initReferrals();
            }
        }
        if (this.blueprint instanceof ModuleBlueprint) {
            initEngineers();
        }
        initModifiers();
    }

    private void initHeader() {
        final DestroyableRegion headerRegion = new DestroyableRegion();
        HBox.setHgrow(headerRegion, Priority.ALWAYS);
        this.recipeHeader = BoxBuilder.builder()
                .withNodes(headerRegion).buildHBox();
        this.getNodes().addAll(this.recipeHeader);
    }

    private void initDescription() {
        final DestroyableLabel descriptionTitle = LabelBuilder.builder()
                .withStyleClass(TITLE_STYLE_CLASS)
                .withText("blueprint.label.description")
                .build();

        final DestroyableLabel description = LabelBuilder.builder()
                .withStyleClass("description")
                .withText(this.blueprint.getBlueprintName().getDescriptionLocalizationKey())
                .build();

        this.getNodes().addAll(descriptionTitle, description);
    }


    private void initReferrals() {
        final DestroyableLabel referralLabelHeader = LabelBuilder.builder()
                .withStyleClass(TITLE_STYLE_CLASS)
                .withText("blueprint.label.completed.referrals")
                .build();
        final DestroyableHBox[] engineerLabels = Stream.of(Engineer.BALTANOS, Engineer.ROSA_DAYETTE, Engineer.ELEANOR_BRESA)
                .map(engineer -> new EngineerBlueprintLabel(engineer, true, 0))
                .sorted(Comparator.comparing(EngineerBlueprintLabel::getEngineerName))
                .toArray(DestroyableHBox[]::new);
        final DestroyableFlowPane flowPane = FlowPaneBuilder.builder()
                .withStyleClass("engineer-flow")
                .withNodes(engineerLabels)
                .build();
        this.getNodes().addAll(referralLabelHeader, flowPane);
    }

    private void loadIngredients() {
        this.ingredients.addAll(getRecipeIngredients(Good.class, OdysseyStorageType.GOOD));
        this.ingredients.addAll(getRecipeIngredients(Asset.class, OdysseyStorageType.ASSET));
        this.ingredients.addAll(getRecipeIngredients(Data.class, OdysseyStorageType.DATA));
        if (this.blueprint instanceof EngineerBlueprint engineerRecipe) {
            this.ingredients.addAll(engineerRecipe.getOther().stream()
                    .sorted(Comparator.comparing(LocaleService::getLocalizedStringForCurrentLocale))
                    .map(text -> new MissionIngredient(text, OdysseyStorageType.OTHER))
                    .collect(Collectors.toCollection(ArrayList::new)));
        }
    }

    private void initIngredients() {
        final DestroyableFlowPane ingredientFlow = FlowPaneBuilder.builder()
                .withStyleClass("ingredient-flow")
                .withNodes(this.ingredients)
                .build();
        this.getNodes().add(ingredientFlow);
    }

    private void initTips(final EngineerBlueprint engineerBlueprint) {
        final DestroyableLabel tipsTitle = LabelBuilder.builder()
                .withStyleClasses(TITLE_STYLE_CLASS, SPACING)
                .withText("blueprint.label.tips")
                .build();

        final DestroyableLabel description = LabelBuilder.builder()
                .withStyleClass("description")
                .withText(engineerBlueprint.getTipsLocalizationKey())
                .build();
        this.getNodes().addAll(tipsTitle, description);
    }

    @SuppressWarnings("java:S1192")
    private void initAsRecipe() {
        this.addToWishlist = new AddToWishlistMenuButton();
        this.countLabel = LabelBuilder.builder()
                .withStyleClass("wishlist-count")
                .build();
        this.countLabel.addBinding(this.countLabel.textProperty(), LocaleService.getStringBinding("blueprint.on.wishlist", 0));
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            updateWishlistsAndCount(commander);
        });
        final DestroyableHBox box = BoxBuilder.builder()
                .withStyleClass("wishlist-count-box")
                .withNodes(this.countLabel, this.addToWishlist)
                .buildHBox();
        HBox.setHgrow(this.addToWishlist, Priority.ALWAYS);
        this.recipeHeader.getNodes().add(box);
        final DestroyableLabel materialsTitle = LabelBuilder.builder()
                .withStyleClasses(TITLE_STYLE_CLASS, SPACING)
                .withText("blueprint.header.material")
                .build();
        this.getNodes().add(materialsTitle);
    }

    private void loadCommanderWishlists(final Commander commander) {
        this.addToWishlist.loadCommanderWishlists(commander, (OdysseyBlueprintName) this.blueprint.getBlueprintName());
    }

    private void initAsEngineerMission() {
        final DestroyableLabel objectivesTitle = LabelBuilder.builder()
                .withStyleClasses(TITLE_STYLE_CLASS, SPACING)
                .withText("blueprint.header.objective")
                .build();
        this.getNodes().add(objectivesTitle);
    }

    private void initEngineers() {
        final DestroyableLabel engineersTitle = LabelBuilder.builder()
                .withStyleClasses(TITLE_STYLE_CLASS, SPACING)
                .withText("blueprint.label.engineers")
                .build();
        this.getNodes().add(engineersTitle);
        final DestroyableHBox[] engineerLabels = ((ModuleBlueprint) this.blueprint).getEngineers().stream()
                .map(EngineerBlueprintLabel::new)
                .sorted(Comparator.comparing(EngineerBlueprintLabel::getEngineerName))
                .toArray(DestroyableHBox[]::new);
        final DestroyableFlowPane flowPane = FlowPaneBuilder.builder()
                .withStyleClass("engineer-flow")
                .withNodes(engineerLabels)
                .build();
        this.getNodes().add(flowPane);

    }

    private void initModifiers() {
        final Map<OdysseyModifier, String> modifierMap = this.blueprint.getModifiers();
        if (!modifierMap.isEmpty()) {
            final DestroyableLabel modifierTitle = LabelBuilder.builder()
                    .withStyleClasses(TITLE_STYLE_CLASS, SPACING)
                    .withText("blueprint.label.modifiers")
                    .build();
            this.getNodes().add(modifierTitle);

            final List<DestroyableHBox> modifiers = modifierMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(entry ->
                            new Modifier(entry.getKey().getLocalizationKey(), entry.getValue(), true)
                    )
                    .collect(Collectors.toCollection(ArrayList::new));

            final DestroyableFlowPane modifiersFlowPane = FlowPaneBuilder.builder()
                    .withStyleClass("modifier-flow")
                    .withNodes(modifiers)
                    .build();
            this.getNodes().addAll(modifiersFlowPane);
        }
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, OdysseyWishlistSelectedEvent.class, wishlistSelectedEvent -> {
            if (!(this.blueprint instanceof EngineerBlueprint) || this.ingredients.stream().noneMatch(ingredient -> OdysseyStorageType.OTHER.equals(ingredient.getType()))) {//material based recipes
                APPLICATION_STATE.getPreferredCommander().ifPresent(this::updateWishlistsAndCount);
            }
        }));
        register(EventService.addListener(true, this, OdysseyWishlistChangedEvent.class, wishlistEvent -> {
            if (this.countLabel != null) {
                final long count = APPLICATION_STATE.getPreferredCommander()
                        .map(commander -> WishlistService.getOdysseyWishlists(commander).getSelectedWishlist().getItems().stream()
                                .filter(wishlistRecipe -> wishlistRecipe.getRecipeName().equals(this.blueprint.getBlueprintName()))
                                .count())
                        .orElse(0L);
                updateCount(count);
            }
        }));
    }

    private void updateWishlistsAndCount(Commander commander) {
        loadCommanderWishlists(commander);
        updateCount(getBlueprintCountForCurrentWishtlist(commander, (OdysseyBlueprintName) this.blueprint.getBlueprintName()));
    }

    public long getBlueprintCountForCurrentWishtlist(final Commander commander, OdysseyBlueprintName odysseyBlueprintName) {
        return WishlistService.getOdysseyWishlists(commander).getSelectedWishlist().getItems().stream().filter(wishlistRecipe -> wishlistRecipe.getRecipeName().equals(odysseyBlueprintName)).count();
    }

    private void updateCount(final long count) {
        if (count > 0L) {
            this.countLabel.addBinding(this.countLabel.textProperty(), LocaleService.getStringBinding("blueprint.on.wishlist", count));
        } else {
            this.countLabel.addBinding(this.countLabel.textProperty(), LocaleService.getStringBinding(() -> ""));
        }
    }

    private List<OdysseyMaterialIngredient> getRecipeIngredients(final Class<? extends OdysseyMaterial> materialClass, final OdysseyStorageType storageType) {
        return this.blueprint.getMaterialCollection(materialClass).entrySet().stream()
                .sorted(Comparator.comparing(entry -> LocaleService.getLocalizedStringForCurrentLocale(entry.getKey().getLocalizationKey())))
                .map(material -> new OdysseyMaterialIngredient(storageType, material.getKey(), material.getValue(), StorageService.getMaterialCount(material.getKey(), AmountType.TOTAL)))
                .collect(Collectors.toCollection(ArrayList::new));
    }


}
