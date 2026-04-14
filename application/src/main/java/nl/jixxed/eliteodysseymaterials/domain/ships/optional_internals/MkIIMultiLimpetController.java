/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals;

import nl.jixxed.eliteodysseymaterials.constants.horizons.SynthesisBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsSynthesisBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.ships.LimpetOptionalModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleClass;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MkIIMultiLimpetController extends LimpetOptionalModule {
    public static final MkIIMultiLimpetController MK_II_MINING_MULTI_LIMPET_CONTROLLER_5_A = new MkIIMultiLimpetController("MK_II_MINING_MULTI_LIMPET_CONTROLLER_5_A", HorizonsBlueprintName.MK_II_MINING_MULTI_LIMPET_CONTROLLER, ModuleSize.SIZE_5, ModuleClass.A, true, 2332801, "Int_MultiDroneControl_MiningV2_Size5_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 64.0), Map.entry(HorizonsModifier.INTEGRITY, 105.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.4), Map.entry(HorizonsModifier.BOOT_TIME, 6.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS, 14.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE, 9100.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED, 200.0), Map.entry(HorizonsModifier.DRONE_MULTI_TARGET_SPEED, 176.0)));

    public static final List<MkIIMultiLimpetController> MK_II_MULTI_LIMPET_CONTROLLERS = List.of(
            MK_II_MINING_MULTI_LIMPET_CONTROLLER_5_A
    );

    public MkIIMultiLimpetController(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
    }

    public MkIIMultiLimpetController(MkIIMultiLimpetController multiLimpetController) {
        super(multiLimpetController);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return Collections.emptyList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public MkIIMultiLimpetController Clone() {
        return new MkIIMultiLimpetController(this);
    }

    @Override
    public int getModuleLimit() {
        return 1;
    }

    @Override
    public Collection<HorizonsSynthesisBlueprint> synthesisBlueprints() {
        return SynthesisBlueprints.LIMPETS.values();
    }
}
