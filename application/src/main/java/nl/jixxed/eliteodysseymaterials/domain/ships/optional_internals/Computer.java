package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals;

import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Computer extends OptionalModule {
    public static final Computer STANDARD_DOCKING_COMPUTER = new Computer("STANDARD_DOCKING_COMPUTER", HorizonsBlueprintName.STANDARD_DOCKING_COMPUTER, ModuleSize.SIZE_1, ModuleClass.E,  4500, "Int_DockingComputer_Standard", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  10.00), Map.entry(HorizonsModifier.POWER_DRAW,0.39), Map.entry(HorizonsModifier.BOOT_TIME, 3.0)));
    public static final Computer ADVANCED_DOCKING_COMPUTER = new Computer("ADVANCED_DOCKING_COMPUTER", HorizonsBlueprintName.ADVANCED_DOCKING_COMPUTER, ModuleSize.SIZE_1, ModuleClass.E, 13510, "Int_DockingComputer_Advanced", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  10.00), Map.entry(HorizonsModifier.POWER_DRAW,0.45), Map.entry(HorizonsModifier.BOOT_TIME, 3.0)));
    public static final Computer SUPERCRUISE_ASSIST        = new Computer("SUPERCRUISE_ASSIST", HorizonsBlueprintName.SUPERCRUISE_ASSIST       , ModuleSize.SIZE_1, ModuleClass.E,  9120, "Int_SuperCruiseAssist",        Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  10.00), Map.entry(HorizonsModifier.POWER_DRAW,0.30), Map.entry(HorizonsModifier.BOOT_TIME, 3.0)));
    public static final List<Computer> COMPUTERS = List.of(
            STANDARD_DOCKING_COMPUTER,
            ADVANCED_DOCKING_COMPUTER,
            SUPERCRUISE_ASSIST
    );

    public Computer(final String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, false, basePrice, internalName, attributes);
    }

    public Computer(final String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
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
    public ShipModule Clone() {
        return new Computer(this);
    }

    @Override
    public String getClarifier() {
        return " " + LocaleService.getLocalizedStringForCurrentLocale(this.getName().getLocalizationKey());
    }
}
