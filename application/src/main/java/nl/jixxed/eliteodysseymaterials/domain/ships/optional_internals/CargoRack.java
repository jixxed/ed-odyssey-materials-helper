package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals;

import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleClass;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.domain.ships.OptionalModule;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CargoRack extends OptionalModule {
//    		  150 : { mtype:'icr', cost:   1000, name:'Cargo Rack (Cap: 2)',   class:1, rating:'E', cargocap:  2, fdid:128064338, fdname:'Int_CargoRack_Size1_Class1', eddbid:1191 },
//            250 : { mtype:'icr', cost:   3250, name:'Cargo Rack (Cap: 4)',   class:2, rating:'E', cargocap:  4, fdid:128064339, fdname:'Int_CargoRack_Size2_Class1', eddbid:1192 },
//            350 : { mtype:'icr', cost:  10560, name:'Cargo Rack (Cap: 8)',   class:3, rating:'E', cargocap:  8, fdid:128064340, fdname:'Int_CargoRack_Size3_Class1', eddbid:1193 },
//            450 : { mtype:'icr', cost:  34330, name:'Cargo Rack (Cap: 16)',  class:4, rating:'E', cargocap: 16, fdid:128064341, fdname:'Int_CargoRack_Size4_Class1', eddbid:1194 },
//            550 : { mtype:'icr', cost: 111570, name:'Cargo Rack (Cap: 32)',  class:5, rating:'E', cargocap: 32, fdid:128064342, fdname:'Int_CargoRack_Size5_Class1', eddbid:1195 },
//            650 : { mtype:'icr', cost: 362590, name:'Cargo Rack (Cap: 64)',  class:6, rating:'E', cargocap: 64, fdid:128064343, fdname:'Int_CargoRack_Size6_Class1', eddbid:1196 },
//            750 : { mtype:'icr', cost:1178420, name:'Cargo Rack (Cap: 128)', class:7, rating:'E', cargocap:128, fdid:128064344, fdname:'Int_CargoRack_Size7_Class1', eddbid:1197 },
//            850 : { mtype:'icr', cost:3829870, name:'Cargo Rack (Cap: 256)', class:8, rating:'E', cargocap:256, fdid:128064345, fdname:'Int_CargoRack_Size8_Class1', eddbid:1198 },

    public static final CargoRack CARGO_RACK_1_E = new CargoRack(HorizonsBlueprintName.CARGO_RACK, ModuleSize.SIZE_1, ModuleClass.E,    1000, "Int_CargoRack_Size1_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY,   2.00)));
    public static final CargoRack CARGO_RACK_2_E = new CargoRack(HorizonsBlueprintName.CARGO_RACK, ModuleSize.SIZE_2, ModuleClass.E,    3250, "Int_CargoRack_Size2_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY,   4.00)));
    public static final CargoRack CARGO_RACK_3_E = new CargoRack(HorizonsBlueprintName.CARGO_RACK, ModuleSize.SIZE_3, ModuleClass.E,   10560, "Int_CargoRack_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY,   8.00)));
    public static final CargoRack CARGO_RACK_4_E = new CargoRack(HorizonsBlueprintName.CARGO_RACK, ModuleSize.SIZE_4, ModuleClass.E,   34330, "Int_CargoRack_Size4_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY,  16.00)));
    public static final CargoRack CARGO_RACK_5_E = new CargoRack(HorizonsBlueprintName.CARGO_RACK, ModuleSize.SIZE_5, ModuleClass.E,  111570, "Int_CargoRack_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY,  32.00)));
    public static final CargoRack CARGO_RACK_6_E = new CargoRack(HorizonsBlueprintName.CARGO_RACK, ModuleSize.SIZE_6, ModuleClass.E,  362590, "Int_CargoRack_Size6_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY,  64.00)));
    public static final CargoRack CARGO_RACK_7_E = new CargoRack(HorizonsBlueprintName.CARGO_RACK, ModuleSize.SIZE_7, ModuleClass.E, 1178420, "Int_CargoRack_Size7_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY, 128.00)));
    public static final CargoRack CARGO_RACK_8_E = new CargoRack(HorizonsBlueprintName.CARGO_RACK, ModuleSize.SIZE_8, ModuleClass.E, 3829870, "Int_CargoRack_Size8_Class1", Map.ofEntries(Map.entry(HorizonsModifier.CARGO_CAPACITY, 256.00)));
    public static final List<CargoRack> CARGO_RACKS = List.of(
             CARGO_RACK_1_E,
             CARGO_RACK_2_E,
             CARGO_RACK_3_E,
             CARGO_RACK_4_E,
             CARGO_RACK_5_E,
             CARGO_RACK_6_E,
             CARGO_RACK_7_E,
             CARGO_RACK_8_E
    );
    private CargoRack(final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(name,moduleSize,moduleClass, false, basePrice, internalName, attributes);
    }
    private CargoRack(final CargoRack cargoRack) {
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
    public CargoRack Clone() {
        return new CargoRack(this);
    }
}
