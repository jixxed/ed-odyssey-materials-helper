/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.domain.ships;


import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.Thrusters;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.military.ShieldCellBank;
import nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats.ModuleProfile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier.*;

/**
 * This test was to create a graph for mass vs multiplier. the Mk2 Agile Trusters initially listed false stats, but these have later been corrected.
 * Leaving the test available in case something similar is needed in the future
 */
@Slf4j
public class AgileThrustersDiscoveryTest {
    //    test	            Additional modules              expected mass	speed	boost	pitch	roll	yaw
    //    asp X	            remove shield	                416.6	        290.0	    394.0	    44.08	116.0	11.60
    //    asp X	            remove shield + SCB 3A	        421.6	        290.0	    394.0	    44.06	115.94	11.59
    //    asp X	            remove shield + SCB 3A+3A	    426.6	        289.0	    393.0	    43.95	115.67	11.57
    //    asp X	            remove shield + SCB 3A+3A+3A	431.6	        288.0	    392.0	    43.85	115.40	11.54
    //    type-7	        SCB 5B 	                        498.5	        202.0	    336.0	    24.63	67.17	24.63
    //    type-7	        SCB 6B+6B	                    594.5	        194.0	    323.0	    23.69	64.6	23.69
    //    corsair	        SCB 6B+6B	                    699.4	        291.0	    369.0	    27.0	83.07	10.38
    //    corsair	        SCB 6B+6B+5B+5B+5B	            795.4	        283.0	    359.0	    26.28	80.85	10.11
    //    federal gunship	SCB 6B	                        904.8	        168.0	    276.0	    24.68	78.98	17.77
    //    anaconda		                                    1037.1	        174.0	    233.0	    24.26	58.16	9.69
    //    anaconda	        SCB 6B	                        1101.1	        174.0	    231.0	    24.11	57.86	9.64
    //    anaconda	        SCB 6B+6B+5B	                1197.1	        173.0	    231.0	    24.01	57.63	9.61
    //    anaconda	        SCB 6B+6B+5B+5B+5A+4A	        1261.1	        173.0	    230.0	    24.0	57.6	9.6
    private static Stream<Arguments> testcases() {
        return Stream.of(
               Arguments.of(0, 416.6, 290.0, 394.0, 44.08, 116.0, 11.60, Ship.ASP, Map.of(1, Optional.empty())),
               Arguments.of(1, 421.6, 290.0, 394.0, 44.06, 115.94, 11.59, Ship.ASP, Map.of(1, Optional.empty(), 0, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_3_A))),
               Arguments.of(2, 426.6, 289.0, 393.0, 43.95, 115.67, 11.57, Ship.ASP, Map.of(1, Optional.empty(), 0, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_3_A), 2, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_3_A))),
               Arguments.of(3, 431.6, 288.0, 392.0, 43.85, 115.40, 11.54, Ship.ASP, Map.of(1, Optional.empty(), 0, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_3_A), 2, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_3_A), 3, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_3_A))),
               Arguments.of(4, 498.5, 202.0, 336.0, 24.63, 67.17, 24.63, Ship.TYPE_7, Map.of(0, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_5_B))),
               Arguments.of(5, 594.5, 194.0, 323.0, 23.69, 64.6, 23.69, Ship.TYPE_7, Map.of(0, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_6_B), 1, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_6_B))),
               Arguments.of(6, 699.4, 291.0, 369.0, 27.0, 83.07, 10.38, Ship.CORSAIR, Map.of(0, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_6_B), 2, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_6_B))),
               Arguments.of(7, 795.4, 283.0, 359.0, 26.28, 80.85, 10.11, Ship.CORSAIR, Map.of(0, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_6_B), 2, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_6_B), 3, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_5_B), 4, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_5_B), 5, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_5_B))),
               Arguments.of(8, 904.8, 168.0, 276.0, 24.68, 78.98, 17.77, Ship.FEDERATION_GUNSHIP, Map.of(0, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_6_B))),
               Arguments.of(9, 1037.1, 174.0, 233.0, 24.23, 58.16, 9.69, Ship.ANACONDA, Map.of()),
               Arguments.of(10, 1101.1, 174.0, 231.0, 24.11, 57.86, 9.64, Ship.ANACONDA, Map.of(0, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_6_B))),
               Arguments.of(11, 1197.1, 173.0, 231.0, 24.01, 57.63, 9.61, Ship.ANACONDA, Map.of(0, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_6_B), 1, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_6_B), 3, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_5_B))),
               Arguments.of(12, 1261.1, 173.0, 230.0, 24.0, 57.6, 9.6, Ship.ANACONDA, Map.of(0, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_6_B), 1, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_6_B), 3, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_5_B), 4, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_5_B), 5, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_5_A), 6, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_4_A), 7, Optional.of(ShieldCellBank.SHIELD_CELL_BANK_1_B)))
        );
    }

    @ParameterizedTest
    @MethodSource("testcases")
    @Tag("manual")
    void findMultiplier(int test, double expectedMass, double speed, double boost, double pitch, double roll, double yaw, Ship ship, Map<Integer, Optional<ShipModule>> modules) {
        ShipModule.getBasicModules();
        ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_THRUSTERS).findFirst().ifPresent(slot ->
                slot.setShipModule(Thrusters.THRUSTERS_5_A_MK_II));

        modules.forEach((key, value) -> ship.getOptionalSlots().get(key).setShipModule(value.orElse(null)));

        Assertions.assertEquals(expectedMass, ship.getEmptyMass() + ship.getCurrentCargo() + ship.getCurrentFuel() + ship.getCurrentFuelReserve(), 0.05);

        for (double i = 96.0; i < 116.0; i = i + 0.01) {
            var testPitch = (double)ship.getAttributes().get(MAX_PITCH_SPEED) * (i / 100D);
            var testRoll = (double)ship.getAttributes().get(MAX_ROLL_SPEED) * (i / 100D);
            var testYaw = (double)ship.getAttributes().get(MAX_YAW_SPEED) * (i / 100D);
            if(isClose(testPitch, pitch) && isClose(testRoll, roll) && isClose(testYaw, yaw)){
                log.info("test {}: {} tests valid with mass {} and multiplier of {}", test, ship.getMaxFuelReserve(), expectedMass - ship.getCurrentFuelReserve(), i);
            }
        }
        ModuleProfile mp = new ModuleProfile(420D, 420D, 1260D, 96D, 100D, 116D);
        log.info("test {}: {} multiplier calculated", test,mp.getMassCurveMultiplier(expectedMass - ship.getCurrentFuelReserve()));

    }
    boolean isClose(double a, double b){
        return Math.abs(a - b) < 0.005;
    }

}
