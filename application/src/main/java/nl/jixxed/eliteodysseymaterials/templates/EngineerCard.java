package nl.jixxed.eliteodysseymaterials.templates;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.constants.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.RecipeName;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.BlueprintClickEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EngineerEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LocationEvent;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class EngineerCard extends VBox {
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final Function<RecipeName, HBox> RECIPE_TO_ENGINEER_BLUEPRINT_LABEL = recipeName -> BoxBuilder.builder()
            .withNodes(LabelBuilder.builder()
                            .withStyleClass("engineer-bullet")
                            .withNonLocalizedText("\u2022")
                            .withOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(recipeName)))
                            .build(),
                    LabelBuilder.builder()
                            .withStyleClass("engineer-blueprint")
                            .withText(LocaleService.getStringBinding(recipeName.getLocalizationKey()))
                            .withOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(recipeName)))
                            .build()).buildHBox();

    static {
        NUMBER_FORMAT.setMaximumFractionDigits(2);
    }

    private final Engineer engineer;

    private ResizableImageView image;
    private Label name;
    private HBox specialisation;
    private Label engineerLocation;
    private Label engineerDistance;
    private ResizableImageView copyIcon;
    private FlowPane location;
    private Label suitModulesTitle;
    private List<HBox> suitBlueprintLabels;
    private Label weaponModulesTitle;
    private List<HBox> weaponBlueprintLabels;
    private Label unlockRequirementsTitle;
    private List<HBox> unlockRequirementsLabels;
    private Separator unlockSeparator;

    EngineerCard(final Engineer engineer) {
        this.engineer = engineer;
        initComponents();
        initEventHandling(engineer);
    }

    private void initEventHandling(final Engineer engineer) {
        EventService.addListener(LocationEvent.class, locationEvent -> this.engineerDistance.setText("(" + NUMBER_FORMAT.format(engineer.getDistance(locationEvent.getLocation().getX(), locationEvent.getLocation().getY(), locationEvent.getLocation().getZ())) + "Ly)"));
        EventService.addListener(EngineerEvent.class, engineerEvent -> {
            this.getChildren().removeAll(this.unlockSeparator, this.unlockRequirementsTitle);
            this.getChildren().removeAll(this.unlockRequirementsLabels);
            if (APPLICATION_STATE.isEngineerUnlocked(engineer)) {
                this.image.setImage(new Image(getClass().getResourceAsStream("/images/engineer/" + engineer.name().toLowerCase() + ".jpg")));
            } else {
                this.image.setImage(new Image(getClass().getResourceAsStream("/images/engineer/locked.png")));
                this.unlockRequirementsLabels = getUnlockRequirements();
                this.getChildren().addAll(this.unlockSeparator, this.unlockRequirementsTitle);
                this.getChildren().addAll(this.unlockRequirementsLabels);
            }
        });
    }

    private void initComponents() {
        this.image = getEngineerImageView();
        this.name = getEngineerName();
        this.specialisation = getEngineerSpecialisation();
        this.location = getEngineerLocation();
        this.suitModulesTitle = getSuitModulesTitle();
        this.suitBlueprintLabels = getSuitBlueprints();
        this.weaponModulesTitle = getWeaponModulesTitle();
        this.weaponBlueprintLabels = getWeaponBlueprints();
        this.unlockRequirementsTitle = getUnlockRequirementsTitle();
        this.unlockRequirementsLabels = getUnlockRequirements();
        this.unlockSeparator = new Separator(Orientation.HORIZONTAL);
        this.getChildren().addAll(this.image, this.name, this.specialisation, this.location, new Separator(Orientation.HORIZONTAL), this.suitModulesTitle);
        this.getChildren().addAll(this.suitBlueprintLabels);
        this.getChildren().addAll(new Separator(Orientation.HORIZONTAL), this.weaponModulesTitle);
        this.getChildren().addAll(this.weaponBlueprintLabels);
        this.getChildren().addAll(this.unlockSeparator, this.unlockRequirementsTitle);
        this.getChildren().addAll(this.unlockRequirementsLabels);
        this.getStyleClass().add("engineer-card");
    }

    private List<HBox> getUnlockRequirements() {
        return this.engineer.getPrerequisites().stream()
                .map(prerequisite -> BoxBuilder.builder()
                        .withNodes(LabelBuilder.builder()
                                        .withStyleClass("engineer-bullet")
                                        .withNonLocalizedText((prerequisite.isCompleted()) ? "\u2714" : "\u2022")
                                        .withOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(prerequisite.getRecipeName())))
                                        .build(),
                                LabelBuilder.builder()
                                        .withStyleClass("engineer-prerequisite")
                                        .withText(LocaleService.getStringBinding(prerequisite.getLocalisationKey()))
                                        .withOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(prerequisite.getRecipeName())))
                                        .build()).buildHBox())
                .collect(Collectors.toList());
    }

    private Label getUnlockRequirementsTitle() {
        return LabelBuilder.builder()
                .withStyleClass("engineer-category")
                .withText(LocaleService.getStringBinding("tab.engineer.unlock.prerequisites"))
                .build();
    }

    private List<HBox> getWeaponBlueprints() {
        return RecipeConstants.getWeaponModuleBlueprints().entrySet().stream()
                .filter(recipeNameModuleRecipeEntry -> recipeNameModuleRecipeEntry.getValue().getEngineers().contains(this.engineer))
                .map(Map.Entry::getKey)
                .sorted(Comparator.comparing(RecipeName::name))
                .map(RECIPE_TO_ENGINEER_BLUEPRINT_LABEL)
                .collect(Collectors.toList());
    }

    private List<HBox> getSuitBlueprints() {
        return RecipeConstants.getSuitModuleBlueprints().entrySet().stream()
                .filter(recipeNameModuleRecipeEntry -> recipeNameModuleRecipeEntry.getValue().getEngineers().contains(this.engineer))
                .map(Map.Entry::getKey)
                .sorted(Comparator.comparing(RecipeName::name))
                .map(RECIPE_TO_ENGINEER_BLUEPRINT_LABEL)
                .collect(Collectors.toList());
    }

    private Label getWeaponModulesTitle() {
        return LabelBuilder.builder()
                .withStyleClass("engineer-category")
                .withText(LocaleService.getStringBinding("tab.engineer.weapon.modules"))
                .build();
    }

    private Label getSuitModulesTitle() {
        return LabelBuilder.builder()
                .withStyleClass("engineer-category")
                .withText(LocaleService.getStringBinding("tab.engineer.suit.modules"))
                .build();
    }

    private FlowPane getEngineerLocation() {
        this.engineerLocation = LabelBuilder.builder()
                .withStyleClass("engineer-location")
                .withNonLocalizedText(this.engineer.getSettlement().getSettlementName() + " | " + this.engineer.getLocation().getStarSystem())
                .build();

        this.engineerDistance = LabelBuilder.builder()
                .withStyleClass("engineer-distance")
                .withNonLocalizedText("(0Ly)")
                .build();

        this.copyIcon = ResizableImageViewBuilder.builder()
                .withStyleClass("engineer-copy-icon")
                .withImage("/images/other/copy.png")
                .build();

        return FlowPaneBuilder.builder().withStyleClass("engineer-location-line")
                .withOnMouseClicked(event -> copyLocationToClipboard())
                .withNodes(this.engineerLocation, new StackPane(this.copyIcon), this.engineerDistance)
                .build();

    }

    private void copyLocationToClipboard() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(this.engineer.getLocation().getStarSystem());
        clipboard.setContent(content);
    }

    private HBox getEngineerSpecialisation() {
        LabelBuilder engineerSpecialisationLabelBuilder = LabelBuilder.builder().withText(LocaleService.getStringBinding(this.engineer.getSpecialisation().getLocalizationKey()));

        engineerSpecialisationLabelBuilder = switch (this.engineer.getSpecialisation()) {
            case FORCE -> engineerSpecialisationLabelBuilder.withStyleClass("specialisation-force");
            case DYNAMIC -> engineerSpecialisationLabelBuilder.withStyleClass("specialisation-dynamic");
            case STRATEGIC -> engineerSpecialisationLabelBuilder.withStyleClass("specialisation-strategic");
            case UNKNOWN -> engineerSpecialisationLabelBuilder;
        };

        final Label engineerSpecialisation = engineerSpecialisationLabelBuilder.build();

        final ResizableImageView specialisationIcon = ResizableImageViewBuilder.builder()
                .withStyleClass("specialisation-image")
                .withImage("/images/engineer/specialisation/" + this.engineer.getSpecialisation().name().toLowerCase() + ".png")
                .build();

        return BoxBuilder.builder()
                .withStyleClass("specialisation-line")
                .withNodes(new StackPane(specialisationIcon), engineerSpecialisation)
                .buildHBox();
    }

    private Label getEngineerName() {
        return LabelBuilder.builder()
                .withStyleClass("engineer-name")
                .withText(LocaleService.getStringBinding(this.engineer.getLocalizationKey()))
                .withOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(RecipeName.forEngineer(this.engineer))))
                .build();
    }

    private ResizableImageView getEngineerImageView() {
        return ResizableImageViewBuilder.builder()
                .withStyleClass("engineer-image")
                .withPreserveRatio(true)
                .withImage(new Image(getClass().getResourceAsStream("/images/engineer/locked.png")))
                .build();

    }
}
