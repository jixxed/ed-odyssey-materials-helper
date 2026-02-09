package nl.jixxed.eliteodysseymaterials.helper;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;
@Slf4j
public class CarrierJumpCostHelperTest {
    private static StarSystem HIP_36601 = new StarSystem("HIP 36601", 337.81250, 562.96875, -1457.84375);
    private static StarSystem OUTOTZ_LS_K = new StarSystem("Outotz LS-K d8-3", 526.31250, 238.46875, -1613.75000);
    private static StarSystem OUTOTZ_QB_A = new StarSystem("Outotz QB-A c28-2", 363.90625, 206.25, -1144.21875);
    private static StarSystem HIP_35438 = new StarSystem("HIP 35438", 234.21875, 186.15625, -661.9375);
    private static StarSystem COL_285_SECTOR = new StarSystem("Col 285 Sector MC-T c4-12", 125.31250, 121.34375, -179.21875);

    private static StarSystem HIP_20482 = new StarSystem("HIP 20482", -10.56250, -67.50000, -185.50000);
    private static StarSystem LFT_926 = new StarSystem("LFT 926 B 1", 51.03125, 17.68750, 30.21875);
    private static StarSystem MEENE = new StarSystem("Meene", 118.78125, -56.43750, -97.18750);

    private static StarSystem LHS_3388 = new StarSystem("LHS 3388", -72.9375, 18.59375, 92.9375);
    private static StarSystem WREDGUIA = new StarSystem("Wredguia LI-F b52-11", -390.46875, -14.93750, 62.59375);

    private static Integer FLEETCARRIER_MASS = 25000;
    private static Integer SQUADRONCARRIER_MASS = 15000;

    private static Integer FLEETCARRIER_CAPACITY = 25000;
    private static Integer SQUADRONCARRIER_CAPACITY = 60000;

    private static Stream<Arguments> testcases() {
        return Stream.of(
                Arguments.of(FLEETCARRIER_MASS, FLEETCARRIER_CAPACITY, HIP_36601, OUTOTZ_LS_K, 14310, 268, 190), // Distance 406.37329979842735
                Arguments.of(FLEETCARRIER_MASS, FLEETCARRIER_CAPACITY, OUTOTZ_LS_K, OUTOTZ_QB_A, 14310, 190, 96), // Distance 497.86889099158174
                Arguments.of(FLEETCARRIER_MASS, FLEETCARRIER_CAPACITY, OUTOTZ_QB_A, HIP_35438, 15214, 1000, 906), // Distance 499.81777734177786
                Arguments.of(FLEETCARRIER_MASS, FLEETCARRIER_CAPACITY, HIP_35438, COL_285_SECTOR, 15214, 906, 812), // Distance 499.0777725433933
                Arguments.of(FLEETCARRIER_MASS, FLEETCARRIER_CAPACITY, HIP_20482, LFT_926, 7988, 579, 523), //Distance 239.96932990879273
                Arguments.of(FLEETCARRIER_MASS, FLEETCARRIER_CAPACITY, LFT_926, MEENE, 7988, 523, 483), //Distance 162.2249384776043
                Arguments.of(SQUADRONCARRIER_MASS, SQUADRONCARRIER_CAPACITY, LHS_3388, WREDGUIA, 1, 879, 752),//Distance 320.7353778696505
                Arguments.of(SQUADRONCARRIER_MASS, SQUADRONCARRIER_CAPACITY, WREDGUIA, LHS_3388, 41719, 752, 692)//Distance 320.7353778696505
        );
    }

    @ParameterizedTest
    @MethodSource("testcases")
    public void testJumpRangeCalculations(Integer carrierMass, Integer carrierCapacity, StarSystem from, StarSystem to, Integer freeSpace, Integer fuelInReservoirBeforeJump, Integer fuelInReservoirAfterJump) {
        var distance = LocationService.calculateDistance(from, to);
        var fuelConsumed = CarrierJumpCostHelper.requiredFuelForJump(distance, carrierMass, carrierCapacity - freeSpace, fuelInReservoirBeforeJump);
        log.debug("Distance {}Ly with {} cargo consumes {}T Fuel", distance, carrierCapacity - freeSpace, fuelConsumed);
        Assertions.assertThat(fuelConsumed).isEqualTo(fuelInReservoirBeforeJump - fuelInReservoirAfterJump);
    }

    private static Stream<Arguments> totalJumpRanges() {
        return Stream.of(
                Arguments.of(SQUADRONCARRIER_MASS, 5, 0, 0, 6.66),
                Arguments.of(SQUADRONCARRIER_MASS, 48, 0, 0, 506.66),
                Arguments.of(SQUADRONCARRIER_MASS, 1000, 50, 0, 11972.07),
                Arguments.of(SQUADRONCARRIER_MASS, 1000, 60000, 0, 304312.75),
                Arguments.of(SQUADRONCARRIER_MASS, 1000,  0,60000, 2565.31),
                Arguments.of(FLEETCARRIER_MASS, 5, 0, 0, 4.00),
                Arguments.of(FLEETCARRIER_MASS, 48, 0, 0, 347.33),
                Arguments.of(FLEETCARRIER_MASS, 1000, 50, 0, 7591.94),
                Arguments.of(FLEETCARRIER_MASS, 1000, 25000, 0, 134687.79),
                Arguments.of(FLEETCARRIER_MASS, 1000, 0, 25000, 3793.54)
        );
    }

    @ParameterizedTest
    @MethodSource("totalJumpRanges")
    public void testCalculateJumpRange(Integer carrierMass, Integer fuelInReservoir, Integer additionalFuel, Integer additionalStorageCapacityUsed, Double expectedJumpRange) {
         var jumpRange = CarrierJumpCostHelper.calculateJumpRange(fuelInReservoir, additionalFuel, carrierMass, additionalStorageCapacityUsed + additionalFuel);
         log.debug("With {}T fuel in reservoir, {}T additional fuel, {}T used capacity and {}T mass, the jump range is {}Ly", fuelInReservoir, additionalFuel, additionalStorageCapacityUsed + additionalFuel, carrierMass, jumpRange);
         Assertions.assertThat(jumpRange).isEqualTo(expectedJumpRange, Offset.offset(0.005));
    }
}