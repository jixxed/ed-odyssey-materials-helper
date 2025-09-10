package nl.jixxed.eliteodysseymaterials.templates.horizons.engineers;

import javafx.beans.binding.StringBinding;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PinnedBlueprintService;
import nl.jixxed.eliteodysseymaterials.service.event.EngineerPinEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalInitEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;

import java.util.List;

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
        return HorizonsBlueprintGrade.forDigit(Math.max(Math.min(HorizonsBlueprintConstants.getEngineerMaxGrade(pinnedBlueprint, engineer), ApplicationState.getInstance().getEngineerRank(engineer)), 1)).getGrade();
    }

    @Override
    StringBinding getBlueprintStringBinding(boolean withType, HorizonsBlueprint blueprint) {
        if (blueprint != null && (HorizonsBlueprintName.SEEKER_MISSILE_RACK.equals(blueprint.getBlueprintName()) || HorizonsBlueprintName.DUMBFIRE_MISSILE_RACK.equals(blueprint.getBlueprintName()))) {
            return LocaleService.getStringBinding(() -> LocaleService.getLocalizedStringForCurrentLocale(HorizonsBlueprintName.DUMBFIRE_MISSILE_RACK.getLocalizationKey()) + " & " +
                    LocaleService.getLocalizedStringForCurrentLocale(HorizonsBlueprintName.SEEKER_MISSILE_RACK.getLocalizationKey()) + " - " +
                    LocaleService.getLocalizedStringForCurrentLocale(blueprint.getHorizonsBlueprintType().getLocalizationKey()));
        }
        return super.getBlueprintStringBinding(withType, blueprint);
    }
}
