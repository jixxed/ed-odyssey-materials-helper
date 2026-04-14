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

import nl.jixxed.eliteodysseymaterials.domain.ships.CargoOptionalModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleClass;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class LargeCargoRack extends CargoOptionalModule {
    public static final LargeCargoRack LARGE_CARGO_RACK_7_D = new LargeCargoRack("LARGE_CARGO_RACK_7_D", HorizonsBlueprintName.LARGE_CARGO_RACK, ModuleSize.SIZE_7, ModuleClass.D, 1958680, "Int_LargeCargoRack_Size7_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY, 192.0)));
    public static final LargeCargoRack LARGE_CARGO_RACK_8_D = new LargeCargoRack("LARGE_CARGO_RACK_8_D", HorizonsBlueprintName.LARGE_CARGO_RACK, ModuleSize.SIZE_8, ModuleClass.D, 4929317, "Int_LargeCargoRack_Size8_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY, 384.0)));

    public static final List<LargeCargoRack> LARGE_CARGO_RACKS = List.of(
            LARGE_CARGO_RACK_7_D,
            LARGE_CARGO_RACK_8_D
    );

    public LargeCargoRack(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, basePrice, internalName, attributes);
    }

    private LargeCargoRack(final LargeCargoRack cargoRack) {
        super(cargoRack);
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
    public LargeCargoRack Clone() {
        return new LargeCargoRack(this);
    }

    @Override
    public int getGrouping() {
        return 1;
    }
}
