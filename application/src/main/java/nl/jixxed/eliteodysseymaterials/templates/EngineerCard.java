package nl.jixxed.eliteodysseymaterials.templates;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.*;
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
    private static final Function<RecipeName, Label> RECIPE_TO_ENGINEER_BLUEPRINT_LABEL = recipeName -> LabelBuilder.builder()
            .withStyleClass("engineer-blueprint")
            .withText(LocaleService.getStringBinding(recipeName.getLocalizationKey()))
            .withOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(recipeName)))
            .build();

    static {
        NUMBER_FORMAT.setMaximumFractionDigits(2);
    }

    private final Engineer engineer;

    private ImageView image;
    private Label name;
    private HBox specialisation;
    private Label engineerLocation;
    private Label engineerDistance;
    private ResizableImageView copyIcon;
    private FlowPane location;
    private Label suitModulesTitle;
    private List<Label> suitBlueprintLabels;
    private Label weaponModulesTitle;
    private List<Label> weaponBlueprintLabels;

    EngineerCard(final Engineer engineer) {
        this.engineer = engineer;
        initComponents();
        initEventHandling(engineer);
    }

    private void initEventHandling(final Engineer engineer) {
        EventService.addListener(LocationEvent.class, locationEvent -> this.engineerDistance.setText("(" + NUMBER_FORMAT.format(engineer.getDistance(locationEvent.getX(), locationEvent.getY(), locationEvent.getZ())) + "Ly)"));
        EventService.addListener(EngineerEvent.class, engineerEvent -> {
            if (APPLICATION_STATE.isEngineerUnlocked(engineer)) {
                this.image.setImage(new Image(getClass().getResourceAsStream("/images/engineer/" + engineer.name().toLowerCase() + ".jpg")));
            } else {
                this.image.setImage(new Image(getClass().getResourceAsStream("/images/engineer/locked.png")));
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
        this.getChildren().addAll(this.image, this.name, this.specialisation, this.location, new Separator(Orientation.HORIZONTAL), this.suitModulesTitle);
        this.getChildren().addAll(this.suitBlueprintLabels);
        this.getChildren().addAll(new Separator(Orientation.HORIZONTAL), this.weaponModulesTitle);
        this.getChildren().addAll(this.weaponBlueprintLabels);
        this.getStyleClass().add("engineer-card");
    }

    private List<Label> getWeaponBlueprints() {
        return RecipeConstants.getWeaponModuleBlueprints().entrySet().stream()
                .filter(recipeNameModuleRecipeEntry -> recipeNameModuleRecipeEntry.getValue().getEngineers().contains(this.engineer))
                .map(Map.Entry::getKey)
                .sorted(Comparator.comparing(RecipeName::name))
                .map(RECIPE_TO_ENGINEER_BLUEPRINT_LABEL)
                .collect(Collectors.toList());
    }

    private List<Label> getSuitBlueprints() {
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
                .withNonLocalizedText(this.engineer.getSettlement() + " | " + this.engineer.getSystem())
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
        content.putString(this.engineer.getSystem());
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

    private ImageView getEngineerImageView() {
        return ImageViewBuilder.builder()
                .withStyleClass("engineer-image")
                .withImage(new Image(getClass().getResourceAsStream("/images/engineer/locked.png")))
                .build();

    }
}
