package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.Modification;

@RequiredArgsConstructor
@Getter
public class ModificationChange {
    private final Modification oldModification;
    private final Modification newModification;
}
