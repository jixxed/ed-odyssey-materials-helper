package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsEngineerBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.BlueprintClickEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.Destroyable;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class HorizonsBlueprintContent extends VBox implements DestroyableTemplate {
    private static final String RECIPE_TITLE_LABEL_STYLE_CLASS = "recipe-title-label";
    private final List<Ingredient> ingredients = new ArrayList<>();
    private final HorizonsBlueprint blueprint;
    private HBox recipeHeader;

    HorizonsBlueprintContent(final HorizonsBlueprint blueprint) {
        this.blueprint = blueprint;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("recipe-content");
        loadIngredients();
        initDescription();
        if (this.blueprint instanceof HorizonsEngineerBlueprint) {
            initObjectivesLabel();
            initObjectives();
        }
        if (!this.ingredients.isEmpty() && !this.ingredients.stream().allMatch(ingredient -> ingredient.getType().equals(HorizonsStorageType.OTHER))) {
            initRecipe();
            initIngredients();
        }

        if (!(this.blueprint instanceof HorizonsEngineerBlueprint) && !this.blueprint.getHorizonsBlueprintType().equals(HorizonsBlueprintType.SYNTHESIS) && !HorizonsBlueprintConstants.getTechbrokerUnlocks().containsKey(this.blueprint.getHorizonsBlueprintName())) {
            initEngineers();
        }
        initModifiers();

        if (!HorizonsBlueprintGrade.NONE.equals(this.blueprint.getHorizonsBlueprintGrade()) && HorizonsBlueprintConstants.getExperimentalEffects().containsKey(this.blueprint.getHorizonsBlueprintName())) {
            final Button experimentalEffects = ButtonBuilder.builder().withText(LocaleService.getStringBinding("blueprint.category.name.experimental_effects")).withOnAction(event ->
                    EventService.publish(new BlueprintClickEvent(this.blueprint.getHorizonsBlueprintName(), true))
            ).build();
            final HBox box = BoxBuilder.builder()
                    .withStyleClass("recipe-wishlist-count-box")
                    .withNodes(experimentalEffects)
                    .buildHBox();
            HBox.setHgrow(experimentalEffects, Priority.ALWAYS);
            this.recipeHeader.getChildren().add(box);
        }
        if (HorizonsBlueprintGrade.NONE.equals(this.blueprint.getHorizonsBlueprintGrade()) && !(this.blueprint instanceof HorizonsEngineerBlueprint) && !HorizonsBlueprintConstants.getTechbrokerUnlocks().containsKey(this.blueprint.getHorizonsBlueprintName())) {
            final Button blueprints = ButtonBuilder.builder().withText(LocaleService.getStringBinding("blueprint.category.name.blueprints")).withOnAction(event ->
                    EventService.publish(new BlueprintClickEvent(this.blueprint.getHorizonsBlueprintName()))
            ).build();
            final HBox box = BoxBuilder.builder()
                    .withStyleClass("recipe-wishlist-count-box")
                    .withNodes(blueprints)
                    .buildHBox();
            HBox.setHgrow(blueprints, Priority.ALWAYS);
            this.recipeHeader.getChildren().add(box);
        }

    }


    private void loadIngredients() {
        this.ingredients.addAll(getRecipeIngredients(this.blueprint, Raw.class, HorizonsStorageType.RAW, StorageService.getRaw()));
        this.ingredients.addAll(getRecipeIngredients(this.blueprint, Encoded.class, HorizonsStorageType.ENCODED, StorageService.getEncoded()));
        this.ingredients.addAll(getRecipeIngredients(this.blueprint, Manufactured.class, HorizonsStorageType.MANUFACTURED, StorageService.getManufactured()));
        this.ingredients.addAll(getRecipeIngredients(this.blueprint, Commodity.class, HorizonsStorageType.COMMODITY, StorageService.getCommodities()));
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
                .withWrappingWidth(465D)
                .withText(LocaleService.getStringBinding(HorizonsBlueprintType.SYNTHESIS.equals(this.blueprint.getHorizonsBlueprintType()) || HorizonsBlueprintType.ENGINEER.equals(this.blueprint.getHorizonsBlueprintType()) ? this.blueprint.getHorizonsBlueprintName().getDescriptionLocalizationKey() : this.blueprint.getHorizonsBlueprintType().getDescriptionLocalizationKey()))
                .build();
        final TextFlow textFlow = new TextFlow(description);
        textFlow.getStyleClass().add("blueprint-description");
        this.getChildren().addAll(this.recipeHeader, textFlow);
    }

    @SuppressWarnings("java:S1192")
    private void initRecipe() {
        final Label materialHeader = LabelBuilder.builder()
                .withStyleClass(RECIPE_TITLE_LABEL_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("blueprint.header.material"))
                .build();
        this.getChildren().add(materialHeader);
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
        //NOOP
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
}
