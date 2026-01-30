package nl.jixxed.eliteodysseymaterials.templates.dialog.importdialog;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.css.PseudoClass;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FontAwesomeIconViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprintJson;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.service.PinnedBlueprintService;
import nl.jixxed.eliteodysseymaterials.templates.components.FontAwesomeIconViewPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import java.util.Comparator;
import java.util.Objects;

public class PinEntry extends DestroyableVBox implements DestroyableTemplate {

    private final Engineer engineer;
    private final HorizonsBlueprintJson blueprintJson;

    public PinEntry(final Engineer engineer, final HorizonsBlueprintJson blueprintJson) {
        this.engineer = engineer;
        this.blueprintJson = blueprintJson;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("pin-item");
        // --------------------------------------
        // | Engineer name (colored if changed) |
        // | BP current -> BP new               |
        // --------------------------------------
        HorizonsBlueprint currentPinnedBlueprint = PinnedBlueprintService.getPinnedBlueprint(engineer);
        HorizonsBlueprint newPinnedBlueprint = getNewPinnedBlueprint();
        DestroyableLabel engineerName = LabelBuilder.builder().withStyleClass("engineer").withText(engineer.getLocalizationKey()).build();
        DestroyableLabel currentPinnedBlueprintName = LabelBuilder.builder().withStyleClass("current-name").withText(currentPinnedBlueprint != null ? currentPinnedBlueprint.getBlueprintName().getLocalizationKey() : "blueprint.name.none").build();
        DestroyableLabel currentPinnedBlueprintType = LabelBuilder.builder().withStyleClass("current-type").withText(currentPinnedBlueprint != null ? currentPinnedBlueprint.getHorizonsBlueprintType().getLocalizationKey() : "blank").build();
        DestroyableLabel newPinnedBlueprintName = LabelBuilder.builder().withStyleClass("new-name").withText(newPinnedBlueprint != null ? newPinnedBlueprint.getBlueprintName().getLocalizationKey() : "blueprint.name.none").build();
        DestroyableLabel newPinnedBlueprintType = LabelBuilder.builder().withStyleClass("new-type").withText(newPinnedBlueprint != null ? newPinnedBlueprint.getHorizonsBlueprintType().getLocalizationKey() : "blank").build();
        newPinnedBlueprintName.pseudoClassStateChanged(PseudoClass.getPseudoClass("changed"), !Objects.equals(currentPinnedBlueprintName.getText(), newPinnedBlueprintName.getText()));
        newPinnedBlueprintType.pseudoClassStateChanged(PseudoClass.getPseudoClass("changed"), !Objects.equals(currentPinnedBlueprintType.getText(), newPinnedBlueprintType.getText()));
        var arrow = new FontAwesomeIconViewPane(FontAwesomeIconViewBuilder.builder()
                        .withStyleClasses("arrow")
                        .withIcon(FontAwesomeIcon.CHEVRON_RIGHT)
                        .build());
        DestroyableHBox change = BoxBuilder.builder().withStyleClass("change").withNodes(BoxBuilder.builder().withNodes(currentPinnedBlueprintName, currentPinnedBlueprintType).buildVBox(), arrow, BoxBuilder.builder().withNodes(newPinnedBlueprintName, newPinnedBlueprintType).buildVBox()).buildHBox();
        this.getNodes().addAll(engineerName, change);
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("changed"), !Objects.equals(currentPinnedBlueprint, newPinnedBlueprint));
    }

    private HorizonsBlueprint getNewPinnedBlueprint() {
        if(blueprintJson != null) {
            final Integer engineerRank = ApplicationState.getInstance().getEngineerRank(engineer);
            final HorizonsBlueprintName name = HorizonsBlueprintName.forName(blueprintJson.getName().equals("MISSILE_RACK") ? "DUMBFIRE_MISSILE_RACK" : blueprintJson.getName());
            final HorizonsBlueprintType horizonsBlueprintType = HorizonsBlueprintType.forName(blueprintJson.getType());
            final int maxBlueprintGrade = HorizonsBlueprintConstants.getBlueprintGrades(name, horizonsBlueprintType).stream().max(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).orElse(HorizonsBlueprintGrade.GRADE_1).getGrade();
            return  (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(
                    name,
                    horizonsBlueprintType,
                    HorizonsBlueprintGrade.forDigit(Math.max(Math.min(engineerRank, maxBlueprintGrade), 1))
            );
        }
        return null;
    }
}