package nl.jixxed.eliteodysseymaterials.domain;

import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;

public interface HorizonsEngineeringBlueprint extends EngineeringBlueprint<HorizonsBlueprintName> {

    HorizonsBlueprintType getHorizonsBlueprintType();

    HorizonsBlueprintGrade getHorizonsBlueprintGrade();

    boolean hasSingleEngineerPerRegion();
}
