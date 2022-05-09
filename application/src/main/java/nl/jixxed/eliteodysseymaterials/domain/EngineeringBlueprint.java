package nl.jixxed.eliteodysseymaterials.domain;

import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;

import java.util.List;

public interface EngineeringBlueprint<E extends BlueprintName<E>> extends Blueprint<E> {

    List<Engineer> getEngineers();

}
