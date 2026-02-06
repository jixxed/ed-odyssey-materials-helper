package nl.jixxed.eliteodysseymaterials.helper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CarrierJumpCostHelper {

    public static final double CARRIER_BASE_FUEL_PER_JUMP = 5D;

    /**
     * Calculates the required fuel for a carrier jump based on the distance, carrier mass, current capacity used and fuel in reservoir.
     * @param distance in light years
     * @param carrierMass in tons, 25k for a fleet carrier, 15k for a squadron carrier
     * @param currentCapacityUsed in tons, the currently used capacity of the carrier
     * @param fuelInReservoir in tons, the amount of fuel currently in the reservoir of the carrier
     * @return the required fuel for the jump in tons
     */
    public static Integer requiredFuelForJump(Double distance, Integer carrierMass, Integer currentCapacityUsed, Integer fuelInReservoir) {
        double mass = currentCapacityUsed + fuelInReservoir + carrierMass;
        return Math.toIntExact(Math.round(CARRIER_BASE_FUEL_PER_JUMP + distance * mass / 200_000D));
    }
}