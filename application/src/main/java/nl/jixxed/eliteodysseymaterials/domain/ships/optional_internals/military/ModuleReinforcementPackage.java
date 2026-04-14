/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.military;

import nl.jixxed.eliteodysseymaterials.domain.ships.MilitaryOptionalModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleClass;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.domain.ships.Origin;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ModuleReinforcementPackage extends MilitaryOptionalModule {
    public static final ModuleReinforcementPackage MODULE_REINFORCEMENT_PACKAGE_1_E = new ModuleReinforcementPackage("MODULE_REINFORCEMENT_PACKAGE_1_E", HorizonsBlueprintName.MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_1, ModuleClass.E, 5000, "Int_ModuleReinforcement_Size1_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.0), Map.entry(HorizonsModifier.INTEGRITY, 77.0), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION, 0.30)));
    public static final ModuleReinforcementPackage MODULE_REINFORCEMENT_PACKAGE_1_D = new ModuleReinforcementPackage("MODULE_REINFORCEMENT_PACKAGE_1_D", HorizonsBlueprintName.MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_1, ModuleClass.D, 15000, "Int_ModuleReinforcement_Size1_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.0), Map.entry(HorizonsModifier.INTEGRITY, 70.0), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION, 0.60)));
    public static final ModuleReinforcementPackage MODULE_REINFORCEMENT_PACKAGE_2_E = new ModuleReinforcementPackage("MODULE_REINFORCEMENT_PACKAGE_2_E", HorizonsBlueprintName.MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_2, ModuleClass.E, 12000, "Int_ModuleReinforcement_Size2_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.0), Map.entry(HorizonsModifier.INTEGRITY, 115.0), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION, 0.30)));
    public static final ModuleReinforcementPackage MODULE_REINFORCEMENT_PACKAGE_2_D = new ModuleReinforcementPackage("MODULE_REINFORCEMENT_PACKAGE_2_D", HorizonsBlueprintName.MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_2, ModuleClass.D, 36000, "Int_ModuleReinforcement_Size2_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.0), Map.entry(HorizonsModifier.INTEGRITY, 105.0), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION, 0.60)));
    public static final ModuleReinforcementPackage MODULE_REINFORCEMENT_PACKAGE_3_E = new ModuleReinforcementPackage("MODULE_REINFORCEMENT_PACKAGE_3_E", HorizonsBlueprintName.MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_3, ModuleClass.E, 28000, "Int_ModuleReinforcement_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.0), Map.entry(HorizonsModifier.INTEGRITY, 170.0), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION, 0.30)));
    public static final ModuleReinforcementPackage MODULE_REINFORCEMENT_PACKAGE_3_D = new ModuleReinforcementPackage("MODULE_REINFORCEMENT_PACKAGE_3_D", HorizonsBlueprintName.MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_3, ModuleClass.D, 84000, "Int_ModuleReinforcement_Size3_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.0), Map.entry(HorizonsModifier.INTEGRITY, 155.0), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION, 0.60)));
    public static final ModuleReinforcementPackage MODULE_REINFORCEMENT_PACKAGE_4_E = new ModuleReinforcementPackage("MODULE_REINFORCEMENT_PACKAGE_4_E", HorizonsBlueprintName.MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_4, ModuleClass.E, 65000, "Int_ModuleReinforcement_Size4_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 16.0), Map.entry(HorizonsModifier.INTEGRITY, 260.0), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION, 0.30)));
    public static final ModuleReinforcementPackage MODULE_REINFORCEMENT_PACKAGE_4_D = new ModuleReinforcementPackage("MODULE_REINFORCEMENT_PACKAGE_4_D", HorizonsBlueprintName.MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_4, ModuleClass.D, 195000, "Int_ModuleReinforcement_Size4_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.0), Map.entry(HorizonsModifier.INTEGRITY, 235.0), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION, 0.60)));
    public static final ModuleReinforcementPackage MODULE_REINFORCEMENT_PACKAGE_5_E = new ModuleReinforcementPackage("MODULE_REINFORCEMENT_PACKAGE_5_E", HorizonsBlueprintName.MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_5, ModuleClass.E, 150000, "Int_ModuleReinforcement_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 32.0), Map.entry(HorizonsModifier.INTEGRITY, 385.0), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION, 0.30)));
    public static final ModuleReinforcementPackage MODULE_REINFORCEMENT_PACKAGE_5_D = new ModuleReinforcementPackage("MODULE_REINFORCEMENT_PACKAGE_5_D", HorizonsBlueprintName.MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_5, ModuleClass.D, 450000, "Int_ModuleReinforcement_Size5_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 16.0), Map.entry(HorizonsModifier.INTEGRITY, 350.0), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION, 0.60)));

    public static final List<ModuleReinforcementPackage> MODULE_REINFORCEMENT_PACKAGES = List.of(
            MODULE_REINFORCEMENT_PACKAGE_1_E,
            MODULE_REINFORCEMENT_PACKAGE_1_D,
            MODULE_REINFORCEMENT_PACKAGE_2_E,
            MODULE_REINFORCEMENT_PACKAGE_2_D,
            MODULE_REINFORCEMENT_PACKAGE_3_E,
            MODULE_REINFORCEMENT_PACKAGE_3_D,
            MODULE_REINFORCEMENT_PACKAGE_4_E,
            MODULE_REINFORCEMENT_PACKAGE_4_D,
            MODULE_REINFORCEMENT_PACKAGE_5_E,
            MODULE_REINFORCEMENT_PACKAGE_5_D
    );

    public ModuleReinforcementPackage(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, basePrice, internalName, attributes);
    }

    public ModuleReinforcementPackage(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, basePrice, internalName, attributes);
    }

    public ModuleReinforcementPackage(ModuleReinforcementPackage moduleReinforcementPackage) {
        super(moduleReinforcementPackage);
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
    public ModuleReinforcementPackage Clone() {
        return new ModuleReinforcementPackage(this);
    }
}
