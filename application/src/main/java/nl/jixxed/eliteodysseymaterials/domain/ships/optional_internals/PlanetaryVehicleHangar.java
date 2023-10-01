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

public class PlanetaryVehicleHangar extends OptionalModule {

//		 5280 : { mtype:'ipvh', cost: 18000, name:'Planetary Vehicle Hangar', class:2, rating:'H', mass:12.00, integ:30, pwrdraw:0.25, boottime:5, vslots:1, vcount:1, ammocost: 1030, fdid:128672288, fdname:'Int_BuggyBay_Size2_Class1', eddbid:1528 },
//            5270 : { mtype:'ipvh', cost: 21600, name:'Planetary Vehicle Hangar', class:2, rating:'G', mass: 6.00, integ:30, pwrdraw:0.75, boottime:5, vslots:1, vcount:1, ammocost: 1030, fdid:128672289, fdname:'Int_BuggyBay_Size2_Class2', eddbid:1529 },
//            5480 : { mtype:'ipvh', cost: 72000, name:'Planetary Vehicle Hangar', class:4, rating:'H', mass:20.00, integ:30, pwrdraw:0.40, boottime:5, vslots:2, vcount:1, ammocost: 1030, fdid:128672290, fdname:'Int_BuggyBay_Size4_Class1', eddbid:1526 },
//            5470 : { mtype:'ipvh', cost: 86400, name:'Planetary Vehicle Hangar', class:4, rating:'G', mass:10.00, integ:30, pwrdraw:1.20, boottime:5, vslots:2, vcount:1, ammocost: 1030, fdid:128672291, fdname:'Int_BuggyBay_Size4_Class2', eddbid:1527 },
//            5680 : { mtype:'ipvh', cost:576000, name:'Planetary Vehicle Hangar', class:6, rating:'H', mass:34.00, integ:30, pwrdraw:0.60, boottime:5, vslots:4, vcount:1, ammocost: 1030, fdid:128672292, fdname:'Int_BuggyBay_Size6_Class1', eddbid:1524 },
//            5670 : { mtype:'ipvh', cost:691200, name:'Planetary Vehicle Hangar', class:6, rating:'G', mass:17.00, integ:30, pwrdraw:1.80, boottime:5, vslots:4, vcount:1, ammocost: 1030, fdid:128672293, fdname:'Int_BuggyBay_Size6_Class2', eddbid:1525 },
    public static final PlanetaryVehicleHangar PLANETARY_VEHICLE_HANGAR_2_H = new PlanetaryVehicleHangar("PLANETARY_VEHICLE_HANGAR_2_H", HorizonsBlueprintName.PLANETARY_VEHICLE_HANGAR, ModuleSize.SIZE_2, ModuleClass.H, false, 18000, "Int_BuggyBay_Size2_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  12.0), Map.entry(HorizonsModifier.INTEGRITY,  30.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.25), Map.entry(HorizonsModifier.BOOT_TIME,  5.0), Map.entry(HorizonsModifier.VEHICLE_SLOTS,  1.0), Map.entry(HorizonsModifier.VEHICLE_COUNT,  1.0)));
    public static final PlanetaryVehicleHangar PLANETARY_VEHICLE_HANGAR_2_G = new PlanetaryVehicleHangar("PLANETARY_VEHICLE_HANGAR_2_G", HorizonsBlueprintName.PLANETARY_VEHICLE_HANGAR, ModuleSize.SIZE_2, ModuleClass.G, false, 21600, "Int_BuggyBay_Size2_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  6.0), Map.entry(HorizonsModifier.INTEGRITY,  30.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.75), Map.entry(HorizonsModifier.BOOT_TIME,  5.0), Map.entry(HorizonsModifier.VEHICLE_SLOTS,  1.0), Map.entry(HorizonsModifier.VEHICLE_COUNT,  1.0)));
    public static final PlanetaryVehicleHangar PLANETARY_VEHICLE_HANGAR_4_H = new PlanetaryVehicleHangar("PLANETARY_VEHICLE_HANGAR_4_H", HorizonsBlueprintName.PLANETARY_VEHICLE_HANGAR, ModuleSize.SIZE_4, ModuleClass.H, false, 72000, "Int_BuggyBay_Size4_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  20.0), Map.entry(HorizonsModifier.INTEGRITY,  30.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.4), Map.entry(HorizonsModifier.BOOT_TIME,  5.0), Map.entry(HorizonsModifier.VEHICLE_SLOTS,  2.0), Map.entry(HorizonsModifier.VEHICLE_COUNT,  1.0)));
    public static final PlanetaryVehicleHangar PLANETARY_VEHICLE_HANGAR_4_G = new PlanetaryVehicleHangar("PLANETARY_VEHICLE_HANGAR_4_G", HorizonsBlueprintName.PLANETARY_VEHICLE_HANGAR, ModuleSize.SIZE_4, ModuleClass.G, false, 86400, "Int_BuggyBay_Size4_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  10.0), Map.entry(HorizonsModifier.INTEGRITY,  30.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.2), Map.entry(HorizonsModifier.BOOT_TIME,  5.0), Map.entry(HorizonsModifier.VEHICLE_SLOTS,  2.0), Map.entry(HorizonsModifier.VEHICLE_COUNT,  1.0)));
    public static final PlanetaryVehicleHangar PLANETARY_VEHICLE_HANGAR_6_H = new PlanetaryVehicleHangar("PLANETARY_VEHICLE_HANGAR_6_H", HorizonsBlueprintName.PLANETARY_VEHICLE_HANGAR, ModuleSize.SIZE_6, ModuleClass.H, false, 576000, "Int_BuggyBay_Size6_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  34.0), Map.entry(HorizonsModifier.INTEGRITY,  30.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.6), Map.entry(HorizonsModifier.BOOT_TIME,  5.0), Map.entry(HorizonsModifier.VEHICLE_SLOTS,  4.0), Map.entry(HorizonsModifier.VEHICLE_COUNT,  1.0)));
    public static final PlanetaryVehicleHangar PLANETARY_VEHICLE_HANGAR_6_G = new PlanetaryVehicleHangar("PLANETARY_VEHICLE_HANGAR_6_G", HorizonsBlueprintName.PLANETARY_VEHICLE_HANGAR, ModuleSize.SIZE_6, ModuleClass.G, false, 691200, "Int_BuggyBay_Size6_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  17.0), Map.entry(HorizonsModifier.INTEGRITY,  30.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.8), Map.entry(HorizonsModifier.BOOT_TIME,  5.0), Map.entry(HorizonsModifier.VEHICLE_SLOTS,  4.0), Map.entry(HorizonsModifier.VEHICLE_COUNT,  1.0)));

    public static final List<PlanetaryVehicleHangar> PLANETARY_VEHICLE_HANGARS = List.of(
            PLANETARY_VEHICLE_HANGAR_2_H,
            PLANETARY_VEHICLE_HANGAR_2_G,
            PLANETARY_VEHICLE_HANGAR_4_H,
            PLANETARY_VEHICLE_HANGAR_4_G,
            PLANETARY_VEHICLE_HANGAR_6_H,
            PLANETARY_VEHICLE_HANGAR_6_G
    );
    public PlanetaryVehicleHangar(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
    }

    public PlanetaryVehicleHangar(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, basePrice, internalName, attributes);
    }

    public PlanetaryVehicleHangar(PlanetaryVehicleHangar planetaryVehicleHangar) {
        super(planetaryVehicleHangar);
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
    public PlanetaryVehicleHangar Clone() {
        return new PlanetaryVehicleHangar(this);
    }
}
