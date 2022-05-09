package nl.jixxed.eliteodysseymaterials.domain;

import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;

public interface Blueprint<E extends BlueprintName<E>> {
    BlueprintName<E> getBlueprintName();
}
