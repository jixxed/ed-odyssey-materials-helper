package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ModificationChange {
    private final SelectedModification oldModification;
    private final SelectedModification newModification;
}
