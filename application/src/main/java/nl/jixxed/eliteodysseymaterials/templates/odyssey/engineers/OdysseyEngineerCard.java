package nl.jixxed.eliteodysseymaterials.templates.odyssey.engineers;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EngineerEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import nl.jixxed.eliteodysseymaterials.templates.generic.EngineerCard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class OdysseyEngineerCard extends EngineerCard {

    private Label suitModulesTitle;
    private List<HBox> suitBlueprintLabels;
    private Label weaponModulesTitle;
    private List<HBox> weaponBlueprintLabels;
    private HBox specialisation;

    private final List<EventListener<?>> eventListeners = new ArrayList<>();
    OdysseyEngineerCard(final Engineer engineer) {
        super(engineer);
        if (APPLICATION_STATE.isEngineerUnlocked(engineer)) {
            this.image.setImage(ImageService.getImage("/images/engineer/" + engineer.name().toLowerCase() + ".jpg"));
        } else {
            this.image.setImage(ImageService.getImage("/images/engineer/locked.png"));
        }
        initComponents();
        initEventHandling(engineer);
    }

    @SuppressWarnings("java:S2177")
    private void initComponents() {

        this.suitModulesTitle = getSuitModulesTitle();
        this.suitBlueprintLabels = getSuitBlueprints();
        this.weaponModulesTitle = getWeaponModulesTitle();
        this.weaponBlueprintLabels = getWeaponBlueprints();
        this.specialisation = getEngineerSpecialisation();
        this.getChildren().addAll(this.image, this.name, this.specialisation, this.location, new Separator(Orientation.HORIZONTAL), this.suitModulesTitle);
        this.getChildren().addAll(this.suitBlueprintLabels);
        this.getChildren().addAll(new Separator(Orientation.HORIZONTAL), this.weaponModulesTitle);
        this.getChildren().addAll(this.weaponBlueprintLabels);
        this.getChildren().addAll(this.unlockSeparator, this.unlockRequirementsTitle);
        this.getChildren().addAll(this.unlockRequirementsLabels);
        this.getStyleClass().add("engineer-card");
    }

    @SuppressWarnings("java:S2177")
    private void initEventHandling(final Engineer engineer) {
        this.eventListeners.add(EventService.addListener(true, this, EngineerEvent.class, engineerEvent -> {
            this.getChildren().removeAll(this.unlockSeparator, this.unlockRequirementsTitle);
            this.getChildren().removeAll(this.unlockRequirementsLabels);
            if (APPLICATION_STATE.isEngineerUnlocked(engineer)) {
                this.image.setImage(ImageService.getImage("/images/engineer/" + engineer.name().toLowerCase() + ".jpg"));
            } else {
                this.image.setImage(ImageService.getImage("/images/engineer/locked.png"));
                this.unlockRequirementsLabels = getUnlockRequirements();
                this.getChildren().addAll(this.unlockSeparator, this.unlockRequirementsTitle);
                this.getChildren().addAll(this.unlockRequirementsLabels);
            }
        }));
    }

    private HBox getEngineerSpecialisation() {
        LabelBuilder engineerSpecialisationLabelBuilder = LabelBuilder.builder().withText(LocaleService.getStringBinding(this.engineer.getSpecialisation().getLocalizationKey()));

        engineerSpecialisationLabelBuilder = switch (this.engineer.getSpecialisation()) {
            case FORCE -> engineerSpecialisationLabelBuilder.withStyleClass("specialisation-force");
            case DYNAMIC -> engineerSpecialisationLabelBuilder.withStyleClass("specialisation-dynamic");
            case STRATEGIC -> engineerSpecialisationLabelBuilder.withStyleClass("specialisation-strategic");
            case UNKNOWN -> engineerSpecialisationLabelBuilder.withStyleClass("specialisation-unknown");
        };

        final Label engineerSpecialisation = engineerSpecialisationLabelBuilder.build();

        final DestroyableResizableImageView specialisationIcon = ResizableImageViewBuilder.builder()
                .withStyleClass("specialisation-image")
                .withImage("/images/engineer/specialisation/" + this.engineer.getSpecialisation().name().toLowerCase() + ".png")
                .build();

        return BoxBuilder.builder()
                .withStyleClass("specialisation-line")
                .withNodes(new StackPane(specialisationIcon), engineerSpecialisation)
                .buildHBox();
    }

    private List<HBox> getWeaponBlueprints() {
        return OdysseyBlueprintConstants.getWeaponModuleBlueprints().entrySet().stream()
                .filter(recipeNameModuleRecipeEntry -> recipeNameModuleRecipeEntry.getValue().getEngineers().contains(this.engineer))
                .map(Map.Entry::getKey)
                .sorted(Comparator.comparing(OdysseyBlueprintName::name))
                .map(RECIPE_TO_ENGINEER_BLUEPRINT_LABEL)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private List<HBox> getSuitBlueprints() {
        return OdysseyBlueprintConstants.getSuitModuleBlueprints().entrySet().stream()
                .filter(recipeNameModuleRecipeEntry -> recipeNameModuleRecipeEntry.getValue().getEngineers().contains(this.engineer))
                .map(Map.Entry::getKey)
                .sorted(Comparator.comparing(OdysseyBlueprintName::name))
                .map(RECIPE_TO_ENGINEER_BLUEPRINT_LABEL)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private Label getWeaponModulesTitle() {
        return LabelBuilder.builder()
                .withStyleClass(ENGINEER_CATEGORY_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.engineer.weapon.modules"))
                .build();
    }

    private Label getSuitModulesTitle() {
        return LabelBuilder.builder()
                .withStyleClass(ENGINEER_CATEGORY_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.engineer.suit.modules"))
                .build();
    }
}
