/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
    public static final ElectronicCountermeasure ELECTRONIC_COUNTERMEASURE_0_F = new ElectronicCountermeasure("ELECTRONIC_COUNTERMEASURE_0_F", HorizonsBlueprintName.ELECTRONIC_COUNTERMEASURE, ModuleSize.SIZE_0, ModuleClass.F, false, Mounting.NA, 12500, "Hpt_ElectronicCountermeasure_Tiny", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  20.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.ECM_RANGE,  3000.0), Map.entry(HorizonsModifier.ECM_TIME_TO_CHARGE,  3.0), Map.entry(HorizonsModifier.ECM_ACTIVE_POWER_CONSUMPTION,  4.0), Map.entry(HorizonsModifier.ECM_HEAT,  4.0), Map.entry(HorizonsModifier.ECM_COOLDOWN,  10.0)));

    public static final List<ElectronicCountermeasure> ELECTRONIC_COUNTERMEASURES = List.of(
            ELECTRONIC_COUNTERMEASURE_0_F
    );
    public ElectronicCountermeasure(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
    super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
}

    public ElectronicCountermeasure(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
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
    @Override
    public boolean isPassivePower(){
        return true;
    }
}
