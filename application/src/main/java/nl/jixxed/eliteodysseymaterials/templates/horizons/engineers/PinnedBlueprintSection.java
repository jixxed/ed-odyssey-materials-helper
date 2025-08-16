package nl.jixxed.eliteodysseymaterials.templates.horizons.engineers;

import javafx.geometry.Orientation;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.service.PinnedBlueprintService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableSeparator;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import java.util.List;

public class PinnedBlueprintSection extends DestroyableVBox implements DestroyableTemplate {

    private DestroyableSeparator pinnedBlueprintSeparator;
    private DestroyableLabel pinnedBlueprintTitle;
    private PinnedBlueprint pinnedBlueprint;
    private Engineer engineer;

    public PinnedBlueprintSection(Engineer engineer) {
        this.engineer = engineer;
        initComponents();
    }

    @Override
    public void initComponents() {

        this.pinnedBlueprintTitle = getPinnedBlueprintTitle();
        this.pinnedBlueprintSeparator = new DestroyableSeparator(Orientation.HORIZONTAL);
        this.pinnedBlueprint = getPinnedBlueprint();
        this.getNodes().addAll(this.pinnedBlueprintSeparator, this.pinnedBlueprintTitle, this.pinnedBlueprint);
        this.visibleProperty().bind(pinnedBlueprint.visibleProperty());
        this.managedProperty().bind(pinnedBlueprint.managedProperty());

    }

    private PinnedBlueprint getPinnedBlueprint() {
        HorizonsBlueprint blueprint = PinnedBlueprintService.getPinnedBlueprint(this.engineer);
        if (blueprint == null) {
            return new PinnedBlueprint(null, 0, true, engineer);
        }
        blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(blueprint.getBlueprintName(), blueprint.getHorizonsBlueprintType(), HorizonsBlueprintGrade.forDigit(HorizonsBlueprintConstants.getEngineerMaxGrade(blueprint, this.engineer)));
        final Integer engineerRank = ApplicationState.getInstance().getEngineerRank(engineer);
        return new PinnedBlueprint(blueprint, Math.min(engineerRank, HorizonsBlueprintConstants.getEngineerMaxGrade(blueprint, this.engineer)), true, this.engineer);
    }

    private DestroyableLabel getPinnedBlueprintTitle() {
        return LabelBuilder.builder()
                .withStyleClass("category")
                .withText("tab.engineer.pinned.blueprint")
                .build();
    }
}
