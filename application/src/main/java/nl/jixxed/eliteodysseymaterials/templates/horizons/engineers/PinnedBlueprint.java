package nl.jixxed.eliteodysseymaterials.templates.horizons.engineers;

import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.service.PinnedBlueprintService;
import nl.jixxed.eliteodysseymaterials.service.event.EngineerPinEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalInitEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;

public class PinnedBlueprint extends Blueprint implements DestroyableEventTemplate {

    private final Engineer engineer;

    public PinnedBlueprint(HorizonsBlueprint blueprint, Integer maxGrade, boolean withType, Engineer engineer) {
        super(blueprint, maxGrade, withType);
        this.engineer = engineer;
        initEventHandling();
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, 9, EngineerPinEvent.class, engineerPinEvent -> {
            if (this.engineer.equals(engineerPinEvent.getEngineer())) {
                if (engineerPinEvent.isPinned()) {
                    final int grade = getMaxGrade(engineerPinEvent.getHorizonsBlueprint());
                    update(engineerPinEvent.getHorizonsBlueprint(), grade, true);
                } else {
                    update(null, 0, true);
                }
            }
        }));

        register(EventService.addListener(true, this, JournalInitEvent.class, event -> {
            if (event.isInitialised()) {
                final HorizonsBlueprint pinnedBlueprint = PinnedBlueprintService.getPinnedBlueprint(engineer);
                if (pinnedBlueprint != null) {
                    final int grade = getMaxGrade(pinnedBlueprint);
                    update(pinnedBlueprint, grade, true);
                } else {
                    update(null, 0, true);
                }
            }
        }));
    }

    private int getMaxGrade(HorizonsBlueprint pinnedBlueprint) {
        return HorizonsBlueprintGrade.forDigit(Math.min(HorizonsBlueprintConstants.getEngineerMaxGrade(pinnedBlueprint, engineer), ApplicationState.getInstance().getEngineerRank(engineer))).getGrade();
    }
}
