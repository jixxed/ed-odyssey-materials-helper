package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals;

import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PassengerCabin extends OptionalModule {
//    		 6250 : { mtype:'ipc', cost:   4320, name:'Economy Class Passenger Cabin',  class:2, rating:'E', mass: 2.50, cabincap: 2, cabincls:'E', fdid:128734690, fdname:'Int_PassengerCabin_Size2_Class1', eddbid:1563 },
//            //	 6251 : { mtype:'ipc', cost:    NaN, name:'Prisoner Cells',                 class:2, rating:'E', mass: 2.50, cabincap: 2, cabincls:'P', fdid:null,      fdname:'Int_PassengerCabin_Size2_Class0', eddbid:null },
//            6350 : { mtype:'ipc', cost:   8670, name:'Economy Class Passenger Cabin',  class:3, rating:'E', mass: 5.00, cabincap: 4, cabincls:'E', fdid:128734691, fdname:'Int_PassengerCabin_Size3_Class1', eddbid:1564 },
//            //	 6351 : { mtype:'ipc', cost:    NaN, name:'Prisoner Cells',                 class:3, rating:'E', mass: 5.00, cabincap: 4, cabincls:'P', fdid:null,      fdname:'Int_PassengerCabin_Size3_Class0', eddbid:null },
//            6340 : { mtype:'ipc', cost:  26720, name:'Business Class Passenger Cabin', class:3, rating:'D', mass: 5.00, cabincap: 3, cabincls:'B', fdid:128734692, fdname:'Int_PassengerCabin_Size3_Class2', eddbid:1568 },
//            6450 : { mtype:'ipc', cost:  18960, name:'Economy Class Passenger Cabin',  class:4, rating:'E', mass:10.00, cabincap: 8, cabincls:'E', fdid:128727922, fdname:'Int_PassengerCabin_Size4_Class1', eddbid:1565 },
//            //	 6451 : { mtype:'ipc', cost:    NaN, name:'Prisoner Cells',                 class:4, rating:'E', mass:10.00, cabincap: 8, cabincls:'P', fdid:null,      fdname:'Int_PassengerCabin_Size4_Class0', eddbid:null },
//            6440 : { mtype:'ipc', cost:  56870, name:'Business Class Passenger Cabin', class:4, rating:'D', mass:10.00, cabincap: 6, cabincls:'B', fdid:128727923, fdname:'Int_PassengerCabin_Size4_Class2', eddbid:1569 },
//            6430 : { mtype:'ipc', cost: 170600, name:'First Class Passenger Cabin',    class:4, rating:'C', mass:10.00, cabincap: 3, cabincls:'F', fdid:128727924, fdname:'Int_PassengerCabin_Size4_Class3', eddbid:1572 },
//            6550 : { mtype:'ipc', cost:  34960, name:'Economy Class Passenger Cabin',  class:5, rating:'E', mass:20.00, cabincap:16, cabincls:'E', fdid:128734693, fdname:'Int_PassengerCabin_Size5_Class1', eddbid:1566 },
//            //	 6551 : { mtype:'ipc', cost:    NaN, name:'Prisoner Cells',                 class:5, rating:'E', mass:20.00, cabincap:16, cabincls:'P', fdid:null,      fdname:'Int_PassengerCabin_Size5_Class0', eddbid:null },
//            6540 : { mtype:'ipc', cost:  92370, name:'Business Class Passenger Cabin', class:5, rating:'D', mass:20.00, cabincap:10, cabincls:'B', fdid:128734694, fdname:'Int_PassengerCabin_Size5_Class2', eddbid:1570 },
//            6530 : { mtype:'ipc', cost: 340540, name:'First Class Passenger Cabin',    class:5, rating:'C', mass:20.00, cabincap: 6, cabincls:'F', fdid:128734695, fdname:'Int_PassengerCabin_Size5_Class3', eddbid:1573 },
//            6520 : { mtype:'ipc', cost:1658100, name:'Luxury Class Passenger Cabin',   class:5, rating:'B', mass:20.00, cabincap: 4, cabincls:'L', reserved:{51:1,52:1,53:1}, fdid:128727925, fdname:'Int_PassengerCabin_Size5_Class4', eddbid:1575 },
//            6650 : { mtype:'ipc', cost:  61420, name:'Economy Class Passenger Cabin',  class:6, rating:'E', mass:40.00, cabincap:32, cabincls:'E', fdid:128727926, fdname:'Int_PassengerCabin_Size6_Class1', eddbid:1567 },
//            //	 6651 : { mtype:'ipc', cost:    NaN, name:'Prisoner Cells',                 class:6, rating:'E', mass:40.00, cabincap:32, cabincls:'P', fdid:null,      fdname:'Int_PassengerCabin_Size6_Class0', eddbid:null },
//            6640 : { mtype:'ipc', cost: 184240, name:'Business Class Passenger Cabin', class:6, rating:'D', mass:40.00, cabincap:16, cabincls:'B', fdid:128727927, fdname:'Int_PassengerCabin_Size6_Class2', eddbid:1571 },
//            6630 : { mtype:'ipc', cost: 552700, name:'First Class Passenger Cabin',    class:6, rating:'C', mass:40.00, cabincap:12, cabincls:'F', fdid:128727928, fdname:'Int_PassengerCabin_Size6_Class3', eddbid:1574 },
//            6620 : { mtype:'ipc', cost:4974300, name:'Luxury Class Passenger Cabin',   class:6, rating:'B', mass:40.00, cabincap: 8, cabincls:'L', reserved:{51:1,52:1,53:1}, fdid:128727929, fdname:'Int_PassengerCabin_Size6_Class4', eddbid:1576 },
//        public static final PassengerCabin ECONOMY_CLASS_PASSENGER_CABIN_4_E = new PassengerCabin();
//        public static final PassengerCabin ECONOMY_CLASS_PASSENGER_CABIN_5_E = new PassengerCabin();
//        public static final PassengerCabin ECONOMY_CLASS_PASSENGER_CABIN_6_E = new PassengerCabin();
//        public static final PassengerCabin BUSINESS_CLASS_PASSENGER_CABIN_4_D = new PassengerCabin();
//        public static final PassengerCabin BUSINESS_CLASS_PASSENGER_CABIN_6_D = new PassengerCabin();
//        public static final PulseLaser PULSE_LASER_4_A_G = new PulseLaser(HorizonsBlueprintName.PULSE_LASER, ModuleSize.SIZE_4, ModuleClass.A, false, Mounting.GIMBALLED, 877600, "Hpt_PulseLaser_Gimbal_Huge", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 16.00)));

    public static final PassengerCabin ECONOMY_CLASS_PASSENGER_CABIN_2_E = new PassengerCabin(HorizonsBlueprintName.ECONOMY_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_2, ModuleClass.E, false, 4320, "Int_PassengerCabin_Size2_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.50), Map.entry(HorizonsModifier.CABIN_CAPACITY, 2), Map.entry(HorizonsModifier.CABIN_CLASS, 'E')));
    public static final PassengerCabin ECONOMY_CLASS_PASSENGER_CABIN_3_E = new PassengerCabin(HorizonsBlueprintName.ECONOMY_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_3, ModuleClass.E, false, 8670, "Int_PassengerCabin_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 4), Map.entry(HorizonsModifier.CABIN_CLASS, 'E')));
    public static final PassengerCabin BUSINESS_CLASS_PASSENGER_CABIN_3_D = new PassengerCabin(HorizonsBlueprintName.BUSINESS_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_3, ModuleClass.D, false, 26720, "Int_PassengerCabin_Size3_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 3), Map.entry(HorizonsModifier.CABIN_CLASS, 'B')));
    public static final PassengerCabin ECONOMY_CLASS_PASSENGER_CABIN_4_E = new PassengerCabin(HorizonsBlueprintName.ECONOMY_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_4, ModuleClass.E, false, 18960, "Int_PassengerCabin_Size4_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 8), Map.entry(HorizonsModifier.CABIN_CLASS, 'E')));
    public static final PassengerCabin BUSINESS_CLASS_PASSENGER_CABIN_4_D = new PassengerCabin(HorizonsBlueprintName.BUSINESS_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_4, ModuleClass.D, false, 56870, "Int_PassengerCabin_Size4_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 6), Map.entry(HorizonsModifier.CABIN_CLASS, 'B')));
    public static final PassengerCabin FIRST_CLASS_PASSENGER_CABIN_4_C = new PassengerCabin(HorizonsBlueprintName.FIRST_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_4, ModuleClass.C, false, 170600, "Int_PassengerCabin_Size4_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 3), Map.entry(HorizonsModifier.CABIN_CLASS, 'F')));
    public static final PassengerCabin ECONOMY_CLASS_PASSENGER_CABIN_5_E = new PassengerCabin(HorizonsBlueprintName.ECONOMY_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_5, ModuleClass.E, false, 34960, "Int_PassengerCabin_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 16), Map.entry(HorizonsModifier.CABIN_CLASS, 'E')));
    public static final PassengerCabin BUSINESS_CLASS_PASSENGER_CABIN_5_D = new PassengerCabin(HorizonsBlueprintName.BUSINESS_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_5, ModuleClass.D, false, 92370, "Int_PassengerCabin_Size5_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 10), Map.entry(HorizonsModifier.CABIN_CLASS, 'B')));
    public static final PassengerCabin FIRST_CLASS_PASSENGER_CABIN_5_C = new PassengerCabin(HorizonsBlueprintName.FIRST_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_5, ModuleClass.C, false, 340540, "Int_PassengerCabin_Size5_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 6), Map.entry(HorizonsModifier.CABIN_CLASS, 'F')));
    public static final PassengerCabin LUXURY_CLASS_PASSENGER_CABIN_5_B = new PassengerCabin(HorizonsBlueprintName.LUXURY_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_5, ModuleClass.B, false, 1658100, "Int_PassengerCabin_Size5_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 4), Map.entry(HorizonsModifier.CABIN_CLASS, 'L')));
    public static final PassengerCabin ECONOMY_CLASS_PASSENGER_CABIN_6_E = new PassengerCabin(HorizonsBlueprintName.ECONOMY_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_6, ModuleClass.E, false, 61420, "Int_PassengerCabin_Size6_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 40.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 32), Map.entry(HorizonsModifier.CABIN_CLASS, 'E')));
    public static final PassengerCabin BUSINESS_CLASS_PASSENGER_CABIN_6_D = new PassengerCabin(HorizonsBlueprintName.BUSINESS_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_6, ModuleClass.D, false, 184240, "Int_PassengerCabin_Size6_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 40.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 16), Map.entry(HorizonsModifier.CABIN_CLASS, 'B')));
    public static final PassengerCabin FIRST_CLASS_PASSENGER_CABIN_6_C = new PassengerCabin(HorizonsBlueprintName.FIRST_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_6, ModuleClass.C, false, 552700, "Int_PassengerCabin_Size6_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 40.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 12), Map.entry(HorizonsModifier.CABIN_CLASS, 'F')));
    public static final PassengerCabin LUXURY_CLASS_PASSENGER_CABIN_6_B = new PassengerCabin(HorizonsBlueprintName.LUXURY_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_6, ModuleClass.B, false, 4974300, "Int_PassengerCabin_Size6_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 40.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 8), Map.entry(HorizonsModifier.CABIN_CLASS, 'L')));
    public static final List<PassengerCabin> PASSENGER_CABINS = List.of(
            ECONOMY_CLASS_PASSENGER_CABIN_2_E,
            ECONOMY_CLASS_PASSENGER_CABIN_3_E,
            BUSINESS_CLASS_PASSENGER_CABIN_3_D,
            ECONOMY_CLASS_PASSENGER_CABIN_4_E,
            BUSINESS_CLASS_PASSENGER_CABIN_4_D,
            FIRST_CLASS_PASSENGER_CABIN_4_C,
            ECONOMY_CLASS_PASSENGER_CABIN_5_E,
            BUSINESS_CLASS_PASSENGER_CABIN_5_D,
            FIRST_CLASS_PASSENGER_CABIN_5_C,
            LUXURY_CLASS_PASSENGER_CABIN_5_B,
            ECONOMY_CLASS_PASSENGER_CABIN_6_E,
            BUSINESS_CLASS_PASSENGER_CABIN_6_D,
            FIRST_CLASS_PASSENGER_CABIN_6_C,
            LUXURY_CLASS_PASSENGER_CABIN_6_B
    );

    public PassengerCabin(HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
    }

    public PassengerCabin(HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(name, moduleSize, moduleClass, origin, multiCrew, basePrice, internalName, attributes);
    }

    public PassengerCabin(OptionalModule optionalModule) {
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
        return new PassengerCabin(this);
    }

    @Override
    public String getNonSortingClarifier() {
        return " (" + LocaleService.getLocalizedStringForCurrentLocale(getName().getLocalizationKey()).toUpperCase().substring(0, 3) + ")";
    }
    @Override
    public boolean isAllowed(ShipType shipType) {
        if(this.getName().equals(HorizonsBlueprintName.LUXURY_CLASS_PASSENGER_CABIN)){
            return (shipType.equals(ShipType.DOLPHIN) || shipType.equals(ShipType.BELUGA_LINER) || shipType.equals(ShipType.ORCA));
        }
        return true;
    }
}
