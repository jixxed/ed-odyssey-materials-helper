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

import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleClass;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.domain.ships.OptionalModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Origin;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Computer extends OptionalModule {
    public static final Computer STANDARD_DOCKING_COMPUTER = new Computer("STANDARD_DOCKING_COMPUTER", HorizonsBlueprintName.STANDARD_DOCKING_COMPUTER, ModuleSize.SIZE_1, ModuleClass.E, 4500, "Int_DockingComputer_Standard", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY, 10.00), Map.entry(HorizonsModifier.POWER_DRAW, 0.39), Map.entry(HorizonsModifier.BOOT_TIME, 3.0)));
    public static final Computer ADVANCED_DOCKING_COMPUTER = new Computer("ADVANCED_DOCKING_COMPUTER", HorizonsBlueprintName.ADVANCED_DOCKING_COMPUTER, ModuleSize.SIZE_1, ModuleClass.E, 13510, "Int_DockingComputer_Advanced", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY, 10.00), Map.entry(HorizonsModifier.POWER_DRAW, 0.45), Map.entry(HorizonsModifier.BOOT_TIME, 3.0)));
    public static final Computer SUPERCRUISE_ASSIST = new Computer("SUPERCRUISE_ASSIST", HorizonsBlueprintName.SUPERCRUISE_ASSIST, ModuleSize.SIZE_1, ModuleClass.E, 9120, "Int_SuperCruiseAssist", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY, 10.00), Map.entry(HorizonsModifier.POWER_DRAW, 0.30), Map.entry(HorizonsModifier.BOOT_TIME, 3.0)));
    public static final List<Computer> COMPUTERS = List.of(
            STANDARD_DOCKING_COMPUTER,
            ADVANCED_DOCKING_COMPUTER,
            SUPERCRUISE_ASSIST
    );

    public Computer(final String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, false, basePrice, internalName, attributes);
    }

    public Computer(final String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, basePrice, internalName, attributes);
    }

    public Computer(OptionalModule optionalModule) {
        super(optionalModule);
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
    public Computer Clone() {
        return new Computer(this);
    }

    @Override
    public String getClarifier() {
        return " " + LocaleService.getLocalizedStringForCurrentLocale(this.getName().getLocalizationKey());
    }

    @Override
    public int getGrouping() {
        return switch (getName()) {
            case STANDARD_DOCKING_COMPUTER -> 1;
            case ADVANCED_DOCKING_COMPUTER -> 2;
            case SUPERCRUISE_ASSIST -> 3;
            default -> 0;
        };
    }

    @Override
    public int getModuleLimit() {
        return 1;
    }
}
