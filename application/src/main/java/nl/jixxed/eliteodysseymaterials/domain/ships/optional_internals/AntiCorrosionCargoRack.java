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

public class AntiCorrosionCargoRack extends OptionalModule {
    public static final AntiCorrosionCargoRack ANTI_CORROSION_CARGO_RACK_1_E = new AntiCorrosionCargoRack("ANTI_CORROSION_CARGO_RACK_1_E", HorizonsBlueprintName.ANTI_CORROSION_CARGO_RACK, ModuleSize.SIZE_1, ModuleClass.E, false, 6250, "Int_CorrosionProofCargoRack_Size1_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY, 1.0)));
    public static final AntiCorrosionCargoRack ANTI_CORROSION_CARGO_RACK_1_F = new AntiCorrosionCargoRack("ANTI_CORROSION_CARGO_RACK_1_F", HorizonsBlueprintName.ANTI_CORROSION_CARGO_RACK, ModuleSize.SIZE_1, ModuleClass.F, Origin.TECHBROKER, false, 12560, "Int_CorrosionProofCargoRack_Size1_Class2", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY, 2.0)));
    // unreleased
    //    public static final AntiCorrosionCargoRack ANTI_CORROSION_CARGO_RACK_2_E = new AntiCorrosionCargoRack("ANTI_CORROSION_CARGO_RACK_2_E", HorizonsBlueprintName.ANTI_CORROSION_CARGO_RACK, ModuleSize.SIZE_2, ModuleClass.E, false, 0, "Int_CorrosionProofCargoRack_Size2_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY,  4.0)));
    public static final AntiCorrosionCargoRack ANTI_CORROSION_CARGO_RACK_4_E = new AntiCorrosionCargoRack("ANTI_CORROSION_CARGO_RACK_4_E", HorizonsBlueprintName.ANTI_CORROSION_CARGO_RACK, ModuleSize.SIZE_4, ModuleClass.E, Origin.TECHBROKER, false, 94330, "Int_CorrosionProofCargoRack_Size4_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY, 16.0)));
    //CG
    public static final AntiCorrosionCargoRack ANTI_CORROSION_CARGO_RACK_5_E = new AntiCorrosionCargoRack("ANTI_CORROSION_CARGO_RACK_5_E", HorizonsBlueprintName.ANTI_CORROSION_CARGO_RACK, ModuleSize.SIZE_5, ModuleClass.E, false, 0, "Int_CorrosionProofCargoRack_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY, 32.0)));
    public static final AntiCorrosionCargoRack ANTI_CORROSION_CARGO_RACK_6_E = new AntiCorrosionCargoRack("ANTI_CORROSION_CARGO_RACK_6_E", HorizonsBlueprintName.ANTI_CORROSION_CARGO_RACK, ModuleSize.SIZE_6, ModuleClass.E, false, 0, "Int_CorrosionProofCargoRack_Size6_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY, 64.0)));

    public static final List<AntiCorrosionCargoRack> ANTI_CORROSION_CARGO_RACKS = List.of(
            ANTI_CORROSION_CARGO_RACK_1_E,
            ANTI_CORROSION_CARGO_RACK_1_F,
//            ANTI_CORROSION_CARGO_RACK_2_E,
            ANTI_CORROSION_CARGO_RACK_4_E,
            ANTI_CORROSION_CARGO_RACK_5_E,
            ANTI_CORROSION_CARGO_RACK_6_E
    );

    public AntiCorrosionCargoRack(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
    }

    public AntiCorrosionCargoRack(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, basePrice, internalName, attributes);
    }

    public AntiCorrosionCargoRack(AntiCorrosionCargoRack antiCorrosionCargoRack) {
        super(antiCorrosionCargoRack);
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
    public AntiCorrosionCargoRack Clone() {
        return new AntiCorrosionCargoRack(this);
    }

    @Override
    public boolean isCGExclusive() {
        return ANTI_CORROSION_CARGO_RACK_5_E.equals(this) || ANTI_CORROSION_CARGO_RACK_6_E.equals(this);
    }

    @Override
    public boolean isHiddenStat(HorizonsModifier modifier) {
        if(HorizonsModifier.POWER_DRAW.equals(modifier)){
            return true;
        }
        return super.isHiddenStat(modifier);
    }
}

