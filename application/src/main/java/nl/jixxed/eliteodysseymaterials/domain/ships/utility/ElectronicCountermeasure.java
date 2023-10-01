package nl.jixxed.eliteodysseymaterials.domain.ships.utility;

import nl.jixxed.eliteodysseymaterials.constants.horizons.utilitymounts.ElectronicCounterMeasureBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ElectronicCountermeasure extends UtilityModule {
//    51060 : { mtype:'uec',  cost:  12500, name:'Electronic Countermeasure', class:0, rating:'F', mass:1.30, integ:20, pwrdraw:0.20, passive:1, boottime:0, ecmrng:3000, ecmdur:3, ecmpwr:4.00, ecmheat:4.0, ecmcool:10, fdid:128049516, fdname:'Hpt_ElectronicCountermeasure_Tiny', eddbid:885 },
    public static final ElectronicCountermeasure ELECTRONIC_COUNTERMEASURE_0_F = new ElectronicCountermeasure("ELECTRONIC_COUNTERMEASURE_0_F", HorizonsBlueprintName.ELECTRONIC_COUNTERMEASURE, ModuleSize.SIZE_0, ModuleClass.F, false, Mounting.NA, 12500, "Hpt_ElectronicCountermeasure_Tiny", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  20.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.ECM_RANGE,  3000.0), Map.entry(HorizonsModifier.ECM_TIME_TO_CHARGE,  3.0), Map.entry(HorizonsModifier.ECM_ACTIVE_POWER_CONSUMPTION,  4.0), Map.entry(HorizonsModifier.ECM_HEAT,  4.0), Map.entry(HorizonsModifier.ECM_COOLDOWN,  10.0)));

    public static final List<ElectronicCountermeasure> ELECTRONIC_COUNTERMEASURES = List.of(
            ELECTRONIC_COUNTERMEASURE_0_F
    );
    public ElectronicCountermeasure(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
    super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
}

    public ElectronicCountermeasure(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public ElectronicCountermeasure(ElectronicCountermeasure electronicCountermeasure) {
        super(electronicCountermeasure);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return ElectronicCounterMeasureBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public ElectronicCountermeasure Clone() {
        return new ElectronicCountermeasure(this);
    }
}
