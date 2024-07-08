package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.service.hge.ExpiringMessage;

import java.util.Set;

@RequiredArgsConstructor
@Getter
public class HighGradeEmissionEvent implements Event {
    private final ExpiringMessage expiringMessage;
    private final Set<HorizonsMaterial> collectedOrPotentialMaterials;
}
