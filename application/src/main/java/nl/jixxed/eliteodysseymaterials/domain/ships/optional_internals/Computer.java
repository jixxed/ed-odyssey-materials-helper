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
//    		  3150 : { mtype:'ifa', cost: 4500, name:'Standard Docking Computer', class:1, rating:'E', integ:10, pwrdraw:0.39, boottime:3, limit:'ifa_dc', fdid:128049549, fdname:'Int_DockingComputer_Standard', eddbid:890 },
//            3151 : { mtype:'ifa', cost:13510, name:'Advanced Docking Computer', class:1, rating:'E', integ:10, pwrdraw:0.45, boottime:3, limit:'ifa_dc', fdid:128935155, fdname:'Int_DockingComputer_Advanced', eddbid:1810 },
//            3152 : { mtype:'ifa', cost: 9120, name:'Supercruise Assist',        class:1, rating:'E', integ:10, pwrdraw:0.30, boottime:3, limit:'ifa_sc', fdid:128932273, fdname:'Int_SuperCruiseAssist', eddbid:1809 },
    public static final Computer STANDARD_DOCKING_COMPUTER = new Computer(HorizonsBlueprintName.STANDARD_DOCKING_COMPUTER, ModuleSize.SIZE_1, ModuleClass.E,  4500, "Int_DockingComputer_Standard", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  10.00), Map.entry(HorizonsModifier.POWER_DRAW,0.39), Map.entry(HorizonsModifier.BOOT_TIME, 3.0)));
    public static final Computer ADVANCED_DOCKING_COMPUTER = new Computer(HorizonsBlueprintName.ADVANCED_DOCKING_COMPUTER, ModuleSize.SIZE_1, ModuleClass.E, 13510, "Int_DockingComputer_Advanced", Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  10.00), Map.entry(HorizonsModifier.POWER_DRAW,0.45), Map.entry(HorizonsModifier.BOOT_TIME, 3.0)));
    public static final Computer SUPERCRUISE_ASSIST        = new Computer(HorizonsBlueprintName.SUPERCRUISE_ASSIST       , ModuleSize.SIZE_1, ModuleClass.E,  9120, "Int_SuperCruiseAssist",        Map.ofEntries(Map.entry(HorizonsModifier.INTEGRITY,  10.00), Map.entry(HorizonsModifier.POWER_DRAW,0.30), Map.entry(HorizonsModifier.BOOT_TIME, 3.0)));
    public static final List<Computer> COMPUTERS = List.of(
            STANDARD_DOCKING_COMPUTER,
            ADVANCED_DOCKING_COMPUTER,
            SUPERCRUISE_ASSIST
    );

    public Computer(HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(name, moduleSize, moduleClass, false, basePrice, internalName, attributes);
    }

    public Computer(HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(name, moduleSize, moduleClass, origin, multiCrew, basePrice, internalName, attributes);
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
