package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.Blueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsExperimentalEffectBlueprint;

@RequiredArgsConstructor
@Getter
public class HorizonsBlueprintClickEvent implements Event {
    private final Blueprint blueprint;
    private final boolean experimental;

    public HorizonsBlueprintClickEvent(final Blueprint blueprint) {
        this.blueprint = blueprint;
        this.experimental = blueprint instanceof HorizonsExperimentalEffectBlueprint;
    }
}
