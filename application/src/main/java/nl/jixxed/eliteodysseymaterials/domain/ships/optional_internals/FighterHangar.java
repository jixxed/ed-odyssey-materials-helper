package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals;

import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleClass;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.domain.ships.OptionalModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Origin;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FighterHangar extends OptionalModule {
    public static final FighterHangar FIGHTER_HANGAR_5_D = new FighterHangar("FIGHTER_HANGAR_5_D", HorizonsBlueprintName.FIGHTER_HANGAR, ModuleSize.SIZE_5, ModuleClass.D, false, 575660, "Int_FighterBay_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  20.0), Map.entry(HorizonsModifier.INTEGRITY,  60.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.25), Map.entry(HorizonsModifier.BOOT_TIME,  5.0), Map.entry(HorizonsModifier.VEHICLE_SLOTS,  1.0), Map.entry(HorizonsModifier.VEHICLE_COUNT,  6.0), Map.entry(HorizonsModifier.AMMO_COST,  1030.0)));
    public static final FighterHangar FIGHTER_HANGAR_6_D = new FighterHangar("FIGHTER_HANGAR_6_D", HorizonsBlueprintName.FIGHTER_HANGAR, ModuleSize.SIZE_6, ModuleClass.D, false, 1869350, "Int_FighterBay_Size6_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  40.0), Map.entry(HorizonsModifier.INTEGRITY,  80.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.35), Map.entry(HorizonsModifier.BOOT_TIME,  5.0), Map.entry(HorizonsModifier.VEHICLE_SLOTS,  2.0), Map.entry(HorizonsModifier.VEHICLE_COUNT,  8.0), Map.entry(HorizonsModifier.AMMO_COST,  1030.0)));
    public static final FighterHangar FIGHTER_HANGAR_7_D = new FighterHangar("FIGHTER_HANGAR_7_D", HorizonsBlueprintName.FIGHTER_HANGAR, ModuleSize.SIZE_7, ModuleClass.D, false, 2369330, "Int_FighterBay_Size7_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  60.0), Map.entry(HorizonsModifier.INTEGRITY,  120.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.35), Map.entry(HorizonsModifier.BOOT_TIME,  5.0), Map.entry(HorizonsModifier.VEHICLE_SLOTS,  2.0), Map.entry(HorizonsModifier.VEHICLE_COUNT,  15.0), Map.entry(HorizonsModifier.AMMO_COST,  1030.0)));
    public static final List<FighterHangar> FIGHTER_HANGARS = List.of(
            FIGHTER_HANGAR_5_D,
            FIGHTER_HANGAR_6_D,
            FIGHTER_HANGAR_7_D
    );
    public FighterHangar(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
    }

    public FighterHangar(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, basePrice, internalName, attributes);
    }

    public FighterHangar(FighterHangar fighterHangar) {
        super(fighterHangar);
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
    public FighterHangar Clone() {
        return new FighterHangar(this);
    }
    @Override
    public int getGrouping() {
        return 1;
    }
}
