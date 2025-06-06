package nl.jixxed.eliteodysseymaterials.templates.odyssey.engineers;

import javafx.css.PseudoClass;
import javafx.geometry.Orientation;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.event.BlueprintClickEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EngineerEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.EngineerCard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class OdysseyEngineerCard extends EngineerCard implements DestroyableEventTemplate {

    protected static final Function<OdysseyBlueprintName, DestroyableHBox> RECIPE_TO_ENGINEER_BLUEPRINT_LABEL = recipeName -> BoxBuilder.builder()
            .withNodes(LabelBuilder.builder()
                            .withStyleClass("bullet")
                            .withNonLocalizedText("\u2022")
                            .withOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(recipeName)))
                            .build(),
                    LabelBuilder.builder()
                            .withStyleClass("blueprint")
                            .withText(recipeName.getLocalizationKey())
                            .withOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(recipeName)))
                            .build())
            .buildHBox();

    private DestroyableLabel suitModulesTitle;
    private List<DestroyableHBox> suitBlueprintLabels;
    private DestroyableLabel weaponModulesTitle;
    private List<DestroyableHBox> weaponBlueprintLabels;
    private DestroyableHBox specialisation;


    OdysseyEngineerCard(final Engineer engineer) {
        super(engineer);
    }

    @SuppressWarnings("java:S2177")
    @Override
    public void initComponents() {
        super.initComponents();
        if (APPLICATION_STATE.isEngineerUnlocked(engineer)) {
            this.image.setImage(ImageService.getImage("/images/engineer/" + engineer.name().toLowerCase() + ".jpg"));
        } else {
            this.image.setImage(ImageService.getImage("/images/engineer/locked.png"));
        }
        this.suitModulesTitle = getSuitModulesTitle();
        this.suitBlueprintLabels = getSuitBlueprints();
        this.weaponModulesTitle = getWeaponModulesTitle();
        this.weaponBlueprintLabels = getWeaponBlueprints();
        this.specialisation = getEngineerSpecialisation();
        final DestroyableVBox text = BoxBuilder.builder()
                .withStyleClass("text-box")
                .withNodes(this.name,
                        this.specialisation,
                        this.location,
                        new DestroyableSeparator(Orientation.HORIZONTAL),
                        this.suitModulesTitle).buildVBox();
        text.getNodes().addAll(this.suitBlueprintLabels);
        text.getNodes().addAll(new DestroyableSeparator(Orientation.HORIZONTAL), this.weaponModulesTitle);
        text.getNodes().addAll(this.weaponBlueprintLabels);
        text.getNodes().add(this.unlockSection);
        this.getNodes().addAll(this.image, text);
    }

    @SuppressWarnings("java:S2177")
    @Override
    public void initEventHandling() {
        super.initEventHandling();
        register(EventService.addListener(true, this, EngineerEvent.class, _ -> {
//            this.getNodes().removeAll(this.unlockSeparator, this.unlockRequirementsTitle);
//            this.getNodes().removeAll(this.unlockRequirementsLabels);
            if (APPLICATION_STATE.isEngineerUnlocked(engineer)) {
                this.image.setImage(ImageService.getImage("/images/engineer/" + engineer.name().toLowerCase() + ".jpg"));
            } else {
                this.image.setImage(ImageService.getImage("/images/engineer/locked.png"));
//                this.unlockRequirementsLabels = getUnlockRequirements();
//                this.getNodes().addAll(this.unlockSeparator, this.unlockRequirementsTitle);
//                this.getNodes().addAll(this.unlockRequirementsLabels);
            }
        }));
    }

    private DestroyableHBox getEngineerSpecialisation() {
        final DestroyableLabel engineerSpecialisation = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(this.engineer.getSpecialisation().getLocalizationKey())
                .build();

        final DestroyableResizableImageView specialisationIcon = ResizableImageViewBuilder.builder()
                .withStyleClass("spec-image")
                .withImage("/images/engineer/specialisation/" + this.engineer.getSpecialisation().name().toLowerCase() + ".png")
                .build();

        final DestroyableHBox specialisationLine = BoxBuilder.builder()
                .withStyleClass("specialisation")
                .withNodes(specialisationIcon, engineerSpecialisation)
                .buildHBox();

        String specialisationType = switch (this.engineer.getSpecialisation()) {
            case FORCE -> "force";
            case DYNAMIC -> "dynamic";
            case STRATEGIC -> "strategic";
            case UNKNOWN -> "unknown";
        };
        specialisationLine.pseudoClassStateChanged(PseudoClass.getPseudoClass(specialisationType), true);
        return specialisationLine;
    }

    private List<DestroyableHBox> getWeaponBlueprints() {
        return OdysseyBlueprintConstants.getWeaponModuleBlueprints().entrySet().stream()
                .filter(recipeNameModuleRecipeEntry -> recipeNameModuleRecipeEntry.getValue().getEngineers().contains(this.engineer))
                .map(Map.Entry::getKey)
                .sorted(Comparator.comparing(OdysseyBlueprintName::name))
                .map(RECIPE_TO_ENGINEER_BLUEPRINT_LABEL)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private List<DestroyableHBox> getSuitBlueprints() {
        return OdysseyBlueprintConstants.getSuitModuleBlueprints().entrySet().stream()
                .filter(recipeNameModuleRecipeEntry -> recipeNameModuleRecipeEntry.getValue().getEngineers().contains(this.engineer))
                .map(Map.Entry::getKey)
                .sorted(Comparator.comparing(OdysseyBlueprintName::name))
                .map(RECIPE_TO_ENGINEER_BLUEPRINT_LABEL)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private DestroyableLabel getWeaponModulesTitle() {
        return LabelBuilder.builder()
                .withStyleClass(ENGINEER_CATEGORY_STYLE_CLASS)
                .withText("tab.engineer.weapon.modules")
                .build();
    }

    private DestroyableLabel getSuitModulesTitle() {
        return LabelBuilder.builder()
                .withStyleClass(ENGINEER_CATEGORY_STYLE_CLASS)
                .withText("tab.engineer.suit.modules")
                .build();
    }
}
