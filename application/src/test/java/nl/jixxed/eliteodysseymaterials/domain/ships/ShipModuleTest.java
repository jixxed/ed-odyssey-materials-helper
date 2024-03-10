package nl.jixxed.eliteodysseymaterials.domain.ships;

import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import org.apache.commons.math3.util.Precision;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipModuleTest {

    private static Stream<Arguments> testcases() {
        return Stream.of(
                Arguments.of("ASP_ARMOUR_GRADE_1", HorizonsModifier.HULL_BOOST, HorizonsBlueprintType.HEAVY_DUTY, null, 137.6/100D),
                Arguments.of("ASP_ARMOUR_GRADE_1", HorizonsModifier.HULL_BOOST, HorizonsBlueprintType.LIGHTWEIGHT, null, 71/100D),
                Arguments.of("ASP_ARMOUR_GRADE_1", HorizonsModifier.HULL_BOOST, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintType.ANGLED_PLATING, 130.47/100D),
                Arguments.of("ASP_ARMOUR_GRADE_1", HorizonsModifier.HULL_BOOST, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintType.ANGLED_PLATING, 65.87/100D),
                Arguments.of("ASP_ARMOUR_GRADE_1", HorizonsModifier.HULL_BOOST, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintType.DEEP_PLATING, 156.61/100D),
                Arguments.of("ASP_ARMOUR_GRADE_1", HorizonsModifier.HULL_BOOST, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintType.DEEP_PLATING, 84.68/100D),
                Arguments.of("ASP_ARMOUR_GRADE_1", HorizonsModifier.HULL_BOOST, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintType.LAYERED_PLATING, 130.47/100D),
                Arguments.of("ASP_ARMOUR_GRADE_1", HorizonsModifier.HULL_BOOST, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintType.LAYERED_PLATING, 65.87/100D),
                Arguments.of("ASP_ARMOUR_GRADE_1", HorizonsModifier.HULL_BOOST, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintType.REFLECTIVE_PLATING, 130.47/100D),
                Arguments.of("ASP_ARMOUR_GRADE_1", HorizonsModifier.HULL_BOOST, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintType.REFLECTIVE_PLATING, 65.87/100D),
                Arguments.of("SHIELD_BOOSTER_0_A", HorizonsModifier.SHIELD_BOOST, HorizonsBlueprintType.HEAVY_DUTY, null, 65.6/100D),
                Arguments.of("SHIELD_BOOSTER_0_A", HorizonsModifier.SHIELD_BOOST, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintType.BLAST_BLOCK, 63.94/100D),
                Arguments.of("SHIELD_BOOSTER_0_A", HorizonsModifier.SHIELD_BOOST, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintType.FORCE_BLOCK, 63.94/100D),
                Arguments.of("SHIELD_BOOSTER_0_A", HorizonsModifier.SHIELD_BOOST, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintType.SUPER_CAPACITOR, 73.88/100D),
                Arguments.of("SHIELD_BOOSTER_0_A", HorizonsModifier.SHIELD_BOOST, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintType.THERMO_BLOCK, 63.94/100D),
                Arguments.of("SHIELD_BOOSTER_0_A", HorizonsModifier.SHIELD_BOOST, HorizonsBlueprintType.BLAST_RESISTANT, null, 20.0/100D),
                Arguments.of("SHIELD_BOOSTER_0_A", HorizonsModifier.SHIELD_BOOST, HorizonsBlueprintType.BLAST_RESISTANT, HorizonsBlueprintType.BLAST_BLOCK, 18.8/100D),
                Arguments.of("SHIELD_BOOSTER_0_A", HorizonsModifier.SHIELD_BOOST, HorizonsBlueprintType.BLAST_RESISTANT, HorizonsBlueprintType.FORCE_BLOCK, 18.8/100D),
                Arguments.of("SHIELD_BOOSTER_0_A", HorizonsModifier.SHIELD_BOOST, HorizonsBlueprintType.BLAST_RESISTANT, HorizonsBlueprintType.SUPER_CAPACITOR, 26.0/100D),
                Arguments.of("SHIELD_BOOSTER_0_A", HorizonsModifier.SHIELD_BOOST, HorizonsBlueprintType.BLAST_RESISTANT, HorizonsBlueprintType.THERMO_BLOCK, 18.8/100D),
                Arguments.of("SHIELD_BOOSTER_0_A", HorizonsModifier.KINETIC_RESISTANCE, HorizonsBlueprintType.KINETIC_RESISTANT, null, 27/100D),
                Arguments.of("SHIELD_BOOSTER_0_A", HorizonsModifier.THERMAL_RESISTANCE, HorizonsBlueprintType.KINETIC_RESISTANT, null, -4/100D),
                Arguments.of("SHIELD_BOOSTER_0_A", HorizonsModifier.EXPLOSIVE_RESISTANCE, HorizonsBlueprintType.KINETIC_RESISTANT, null, -4/100D),
                Arguments.of("SHIELD_BOOSTER_0_A", HorizonsModifier.KINETIC_RESISTANCE, HorizonsBlueprintType.KINETIC_RESISTANT, HorizonsBlueprintType.SUPER_CAPACITOR, 25.54/100D),
                Arguments.of("SHIELD_BOOSTER_0_A", HorizonsModifier.THERMAL_RESISTANCE, HorizonsBlueprintType.KINETIC_RESISTANT, HorizonsBlueprintType.SUPER_CAPACITOR, -6.08/100D),
                Arguments.of("SHIELD_BOOSTER_0_A", HorizonsModifier.EXPLOSIVE_RESISTANCE, HorizonsBlueprintType.KINETIC_RESISTANT, HorizonsBlueprintType.SUPER_CAPACITOR, -6.08/100D),
                Arguments.of("SHIELD_GENERATOR_6_A", HorizonsModifier.KINETIC_RESISTANCE, HorizonsBlueprintType.REINFORCED_SHIELDS, null, 49.9/100D),
                Arguments.of("SHIELD_GENERATOR_6_A", HorizonsModifier.THERMAL_RESISTANCE, HorizonsBlueprintType.REINFORCED_SHIELDS, null, -0.2/100D),
                Arguments.of("SHIELD_GENERATOR_6_A", HorizonsModifier.EXPLOSIVE_RESISTANCE, HorizonsBlueprintType.REINFORCED_SHIELDS, null, 58.25/100D),
                Arguments.of("SHIELD_GENERATOR_6_A", HorizonsModifier.KINETIC_RESISTANCE, HorizonsBlueprintType.REINFORCED_SHIELDS, HorizonsBlueprintType.MULTI_WEAVE, 51.4/100D),
                Arguments.of("SHIELD_GENERATOR_6_A", HorizonsModifier.THERMAL_RESISTANCE, HorizonsBlueprintType.REINFORCED_SHIELDS, HorizonsBlueprintType.MULTI_WEAVE, 2.81/100D),
                Arguments.of("SHIELD_GENERATOR_6_A", HorizonsModifier.EXPLOSIVE_RESISTANCE, HorizonsBlueprintType.REINFORCED_SHIELDS, HorizonsBlueprintType.MULTI_WEAVE, 59.5/100D),
                Arguments.of("SHIELD_GENERATOR_6_A", HorizonsModifier.KINETIC_RESISTANCE, HorizonsBlueprintType.REINFORCED_SHIELDS, HorizonsBlueprintType.FAST_CHARGE, 49.15/100D),
                Arguments.of("SHIELD_GENERATOR_6_A", HorizonsModifier.THERMAL_RESISTANCE, HorizonsBlueprintType.REINFORCED_SHIELDS, HorizonsBlueprintType.FAST_CHARGE, -1.7/100D),
                Arguments.of("SHIELD_GENERATOR_6_A", HorizonsModifier.EXPLOSIVE_RESISTANCE, HorizonsBlueprintType.REINFORCED_SHIELDS, HorizonsBlueprintType.FAST_CHARGE, 57.62/100D),
                Arguments.of("HULL_REINFORCEMENT_PACKAGE_5_D", HorizonsModifier.HULL_BOOST, HorizonsBlueprintType.LIGHTWEIGHT_HULL_REINFORCEMENT, null, 24.0/100D),
                Arguments.of("HULL_REINFORCEMENT_PACKAGE_5_E", HorizonsModifier.KINETIC_RESISTANCE, HorizonsBlueprintType.LIGHTWEIGHT_HULL_REINFORCEMENT, HorizonsBlueprintType.DEEP_PLATING, 0.55/100D),
                Arguments.of("HULL_REINFORCEMENT_PACKAGE_5_E", HorizonsModifier.THERMAL_RESISTANCE, HorizonsBlueprintType.LIGHTWEIGHT_HULL_REINFORCEMENT, HorizonsBlueprintType.REFLECTIVE_PLATING, 4.45/100D),
                Arguments.of("HULL_REINFORCEMENT_PACKAGE_5_E", HorizonsModifier.HULL_REINFORCEMENT, HorizonsBlueprintType.KINETIC_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintType.REFLECTIVE_PLATING, 393.3),
                Arguments.of("HULL_REINFORCEMENT_PACKAGE_5_E", HorizonsModifier.KINETIC_RESISTANCE, HorizonsBlueprintType.KINETIC_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintType.REFLECTIVE_PLATING, 41.5/100D),
                Arguments.of("HULL_REINFORCEMENT_PACKAGE_5_E", HorizonsModifier.THERMAL_RESISTANCE, HorizonsBlueprintType.KINETIC_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintType.REFLECTIVE_PLATING, -5.11/100D),
                Arguments.of("HULL_REINFORCEMENT_PACKAGE_5_E", HorizonsModifier.EXPLOSIVE_RESISTANCE, HorizonsBlueprintType.KINETIC_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintType.REFLECTIVE_PLATING,-7.25/100D)

        );
    }

    @ParameterizedTest
    @MethodSource("testcases")
    void getAttributeValue( String moduleID, HorizonsModifier modifierToTest, HorizonsBlueprintType blueprint, HorizonsBlueprintType effect, double expectedValue) {

        ShipModule shipModule = ShipModule.getModule(moduleID).Clone();
        if (blueprint != null)
            shipModule.applyModification(blueprint, HorizonsBlueprintGrade.GRADE_5, BigDecimal.ONE);
        if (effect != null)
            shipModule.applyExperimentalEffect(effect);
        assertEquals(expectedValue, Precision.round((Double) shipModule.getAttributeValue(modifierToTest),4), 0.00001);
    }

    @Test
    void getBasicModules() {
        Assertions.assertAll(
                ShipModule.getBasicModules().stream().map(shipModule -> () -> Assertions.assertDoesNotThrow(() -> LocaleService.getLocalizedStringForCurrentLocale(shipModule.getLocalizationKey()))));
    }
}