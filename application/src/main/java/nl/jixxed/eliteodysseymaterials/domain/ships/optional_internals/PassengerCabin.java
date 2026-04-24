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

import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PassengerCabin extends OptionalModule {
    public static final PassengerCabin ECONOMY_CLASS_PASSENGER_CABIN_2_E = new PassengerCabin("ECONOMY_CLASS_PASSENGER_CABIN_2_E", HorizonsBlueprintName.ECONOMY_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_2, ModuleClass.E, false, 4320, "Int_PassengerCabin_Size2_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.50), Map.entry(HorizonsModifier.CABIN_CAPACITY, 2.0), Map.entry(HorizonsModifier.CABIN_CLASS, "E")));
    public static final PassengerCabin ECONOMY_CLASS_PASSENGER_CABIN_3_E = new PassengerCabin("ECONOMY_CLASS_PASSENGER_CABIN_3_E", HorizonsBlueprintName.ECONOMY_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_3, ModuleClass.E, false, 8670, "Int_PassengerCabin_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 4.0), Map.entry(HorizonsModifier.CABIN_CLASS, "E")));
    public static final PassengerCabin BUSINESS_CLASS_PASSENGER_CABIN_3_D = new PassengerCabin("BUSINESS_CLASS_PASSENGER_CABIN_3_D", HorizonsBlueprintName.BUSINESS_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_3, ModuleClass.D, false, 26720, "Int_PassengerCabin_Size3_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 3.0), Map.entry(HorizonsModifier.CABIN_CLASS, "B")));
    public static final PassengerCabin ECONOMY_CLASS_PASSENGER_CABIN_4_E = new PassengerCabin("ECONOMY_CLASS_PASSENGER_CABIN_4_E", HorizonsBlueprintName.ECONOMY_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_4, ModuleClass.E, false, 18960, "Int_PassengerCabin_Size4_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 8.0), Map.entry(HorizonsModifier.CABIN_CLASS, "E")));
    public static final PassengerCabin BUSINESS_CLASS_PASSENGER_CABIN_4_D = new PassengerCabin("BUSINESS_CLASS_PASSENGER_CABIN_4_D", HorizonsBlueprintName.BUSINESS_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_4, ModuleClass.D, false, 56870, "Int_PassengerCabin_Size4_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 6.0), Map.entry(HorizonsModifier.CABIN_CLASS, "B")));
    public static final PassengerCabin FIRST_CLASS_PASSENGER_CABIN_4_C = new PassengerCabin("FIRST_CLASS_PASSENGER_CABIN_4_C", HorizonsBlueprintName.FIRST_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_4, ModuleClass.C, false, 170600, "Int_PassengerCabin_Size4_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 3.0), Map.entry(HorizonsModifier.CABIN_CLASS, "F")));
    public static final PassengerCabin ECONOMY_CLASS_PASSENGER_CABIN_5_E = new PassengerCabin("ECONOMY_CLASS_PASSENGER_CABIN_5_E", HorizonsBlueprintName.ECONOMY_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_5, ModuleClass.E, false, 34960, "Int_PassengerCabin_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 16.0), Map.entry(HorizonsModifier.CABIN_CLASS, "E")));
    public static final PassengerCabin BUSINESS_CLASS_PASSENGER_CABIN_5_D = new PassengerCabin("BUSINESS_CLASS_PASSENGER_CABIN_5_D", HorizonsBlueprintName.BUSINESS_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_5, ModuleClass.D, false, 92370, "Int_PassengerCabin_Size5_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 10.0), Map.entry(HorizonsModifier.CABIN_CLASS, "B")));
    public static final PassengerCabin FIRST_CLASS_PASSENGER_CABIN_5_C = new PassengerCabin("FIRST_CLASS_PASSENGER_CABIN_5_C", HorizonsBlueprintName.FIRST_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_5, ModuleClass.C, false, 340540, "Int_PassengerCabin_Size5_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 6.0), Map.entry(HorizonsModifier.CABIN_CLASS, "F")));
    public static final PassengerCabin LUXURY_CLASS_PASSENGER_CABIN_5_B = new PassengerCabin("LUXURY_CLASS_PASSENGER_CABIN_5_B", HorizonsBlueprintName.LUXURY_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_5, ModuleClass.B, false, 1658100, "Int_PassengerCabin_Size5_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 4.0), Map.entry(HorizonsModifier.CABIN_CLASS, "L")));
    public static final PassengerCabin ECONOMY_CLASS_PASSENGER_CABIN_6_E = new PassengerCabin("ECONOMY_CLASS_PASSENGER_CABIN_6_E", HorizonsBlueprintName.ECONOMY_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_6, ModuleClass.E, false, 61420, "Int_PassengerCabin_Size6_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 40.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 32.0), Map.entry(HorizonsModifier.CABIN_CLASS, "E")));
    public static final PassengerCabin BUSINESS_CLASS_PASSENGER_CABIN_6_D = new PassengerCabin("BUSINESS_CLASS_PASSENGER_CABIN_6_D", HorizonsBlueprintName.BUSINESS_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_6, ModuleClass.D, false, 184240, "Int_PassengerCabin_Size6_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 40.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 16.0), Map.entry(HorizonsModifier.CABIN_CLASS, "B")));
    public static final PassengerCabin FIRST_CLASS_PASSENGER_CABIN_6_C = new PassengerCabin("FIRST_CLASS_PASSENGER_CABIN_6_C", HorizonsBlueprintName.FIRST_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_6, ModuleClass.C, false, 552700, "Int_PassengerCabin_Size6_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 40.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 12.0), Map.entry(HorizonsModifier.CABIN_CLASS, "F")));
    public static final PassengerCabin LUXURY_CLASS_PASSENGER_CABIN_6_B = new PassengerCabin("LUXURY_CLASS_PASSENGER_CABIN_6_B", HorizonsBlueprintName.LUXURY_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_6, ModuleClass.B, false, 4974300, "Int_PassengerCabin_Size6_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 40.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 8.0), Map.entry(HorizonsModifier.CABIN_CLASS, "L")));

    public static final PassengerCabin MK_II_ECONOMY_CLASS_PASSENGER_CABIN_2_D = new PassengerCabin("MK_II_ECONOMY_CLASS_PASSENGER_CABIN_2_D", HorizonsBlueprintName.MK_II_ECONOMY_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_2, ModuleClass.D, false, 0, "Int_mkii_passengercabin_size2_class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.50), Map.entry(HorizonsModifier.CABIN_CAPACITY, 3.0), Map.entry(HorizonsModifier.CABIN_CLASS, "E")));
    public static final PassengerCabin MK_II_ECONOMY_CLASS_PASSENGER_CABIN_3_D = new PassengerCabin("MK_II_ECONOMY_CLASS_PASSENGER_CABIN_3_D", HorizonsBlueprintName.MK_II_ECONOMY_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_3, ModuleClass.D, false, 0, "Int_mkii_passengercabin_size3_class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 6.0), Map.entry(HorizonsModifier.CABIN_CLASS, "E")));
    public static final PassengerCabin MK_II_BUSINESS_CLASS_PASSENGER_CABIN_3_C = new PassengerCabin("MK_II_BUSINESS_CLASS_PASSENGER_CABIN_3_C", HorizonsBlueprintName.MK_II_BUSINESS_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_3, ModuleClass.C, false, 0, "Int_mkii_passengercabin_size3_class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 5.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 4.0), Map.entry(HorizonsModifier.CABIN_CLASS, "B")));
    public static final PassengerCabin MK_II_ECONOMY_CLASS_PASSENGER_CABIN_4_D = new PassengerCabin("MK_II_ECONOMY_CLASS_PASSENGER_CABIN_4_D", HorizonsBlueprintName.MK_II_ECONOMY_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_4, ModuleClass.D, false, 0, "Int_mkii_passengercabin_size4_class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 12.0), Map.entry(HorizonsModifier.CABIN_CLASS, "E")));
    public static final PassengerCabin MK_II_BUSINESS_CLASS_PASSENGER_CABIN_4_C = new PassengerCabin("MK_II_BUSINESS_CLASS_PASSENGER_CABIN_4_C", HorizonsBlueprintName.MK_II_BUSINESS_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_4, ModuleClass.C, false, 0, "Int_mkii_passengercabin_size4_class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 10.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 9.0), Map.entry(HorizonsModifier.CABIN_CLASS, "B")));
    public static final PassengerCabin MK_II_ECONOMY_CLASS_PASSENGER_CABIN_5_D = new PassengerCabin("MK_II_ECONOMY_CLASS_PASSENGER_CABIN_5_D", HorizonsBlueprintName.MK_II_ECONOMY_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_5, ModuleClass.D, false, 0, "Int_mkii_passengercabin_size5_class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 24.0), Map.entry(HorizonsModifier.CABIN_CLASS, "E")));
    public static final PassengerCabin MK_II_BUSINESS_CLASS_PASSENGER_CABIN_5_C = new PassengerCabin("MK_II_BUSINESS_CLASS_PASSENGER_CABIN_5_C", HorizonsBlueprintName.MK_II_BUSINESS_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_5, ModuleClass.C, false, 0, "Int_mkii_passengercabin_size5_class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 20.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 15.0), Map.entry(HorizonsModifier.CABIN_CLASS, "B")));
    public static final PassengerCabin MK_II_ECONOMY_CLASS_PASSENGER_CABIN_6_D = new PassengerCabin("MK_II_ECONOMY_CLASS_PASSENGER_CABIN_6_D", HorizonsBlueprintName.MK_II_ECONOMY_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_6, ModuleClass.D, false, 0, "Int_mkii_passengercabin_size6_class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 40.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 48.0), Map.entry(HorizonsModifier.CABIN_CLASS, "E")));
    public static final PassengerCabin MK_II_BUSINESS_CLASS_PASSENGER_CABIN_6_C = new PassengerCabin("MK_II_BUSINESS_CLASS_PASSENGER_CABIN_6_C", HorizonsBlueprintName.MK_II_BUSINESS_CLASS_PASSENGER_CABIN, ModuleSize.SIZE_6, ModuleClass.C, false, 0, "Int_mkii_passengercabin_size6_class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 40.00), Map.entry(HorizonsModifier.CABIN_CAPACITY, 24.0), Map.entry(HorizonsModifier.CABIN_CLASS, "B")));

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
            LUXURY_CLASS_PASSENGER_CABIN_6_B,
            MK_II_ECONOMY_CLASS_PASSENGER_CABIN_2_D,
            MK_II_ECONOMY_CLASS_PASSENGER_CABIN_3_D,
            MK_II_BUSINESS_CLASS_PASSENGER_CABIN_3_C,
            MK_II_ECONOMY_CLASS_PASSENGER_CABIN_4_D,
            MK_II_BUSINESS_CLASS_PASSENGER_CABIN_4_C,
            MK_II_ECONOMY_CLASS_PASSENGER_CABIN_5_D,
            MK_II_BUSINESS_CLASS_PASSENGER_CABIN_5_C,
            MK_II_ECONOMY_CLASS_PASSENGER_CABIN_6_D,
            MK_II_BUSINESS_CLASS_PASSENGER_CABIN_6_C
    );

    public PassengerCabin(final String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
    }

    public PassengerCabin(final String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, basePrice, internalName, attributes);
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
    public PassengerCabin Clone() {
        return new PassengerCabin(this);
    }

    @Override
    public String getNonSortingClarifier() {
        return " " + LocaleService.getLocalizedStringForCurrentLocale(getName().getLocalizationKey() + ".short");
    }

    @Override
    public int getGrouping() {
        int group = getModuleSize().intValue() * 10;
        if (getName().equals(HorizonsBlueprintName.LUXURY_CLASS_PASSENGER_CABIN)) {
            group++;
        }
        if (getId().startsWith("MK_II")) {
            group++;
        }
        return group;
    }

    @Override
    public boolean isAllowed(ShipType shipType) {
        if (LUXURY_CLASS_PASSENGER_CABIN_5_B.equals(this) || LUXURY_CLASS_PASSENGER_CABIN_6_B.equals(this)) {
            return (shipType.equals(ShipType.DOLPHIN) || shipType.equals(ShipType.BELUGA_LINER) || shipType.equals(ShipType.ORCA));
        }
        if (HorizonsBlueprintName.MK_II_ECONOMY_CLASS_PASSENGER_CABIN.equals(this.getName())
                || HorizonsBlueprintName.MK_II_BUSINESS_CLASS_PASSENGER_CABIN.equals(this.getName())) {
            return shipType.equals(ShipType.LYNX_HIGHLINER);
        }
        return true;
    }
}
