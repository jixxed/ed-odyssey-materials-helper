package nl.jixxed.eliteodysseymaterials.helper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CarrierJumpCostHelper {

    public static final double CARRIER_BASE_FUEL_PER_JUMP = 5D;
    public static final Integer FUEL_RESERVOIR_CAPACITY = 1000;
    public static final Double MAX_JUMP_DISTANCE = 500D;

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

    /**
     * Calculates the jump range of a carrier based on the fuel in reservoir, additional fuel, carrier mass and current capacity used.
     * @param fuelInReservoir in tons, the amount of fuel currently in the reservoir of the carrier
     * @param additionalFuel in tons, the amount of additional fuel that can be added to the reservoir of the carrier
     * @param carrierMass in tons, 25k for a fleet carrier, 15k for a squadron carrier
     * @param currentCapacityUsed in tons, the currently used capacity of the carrier
     * @return the total jump range in light years
     */
    public static Double calculateJumpRange(Integer fuelInReservoir, Integer additionalFuel, Integer carrierMass, Integer currentCapacityUsed) {
        double totalJumpRange = 0.0;
        int currentFuelInReservoir = fuelInReservoir;
        int currentAdditionalFuel = additionalFuel;
        int currentCapacity = currentCapacityUsed;

        while (true) {
            // Transfer fuel from cargo to reservoir if needed
            if (currentFuelInReservoir < FUEL_RESERVOIR_CAPACITY && currentAdditionalFuel > 0) {
                int fuelToTransfer = Math.min(
                        FUEL_RESERVOIR_CAPACITY - currentFuelInReservoir,
                        currentAdditionalFuel
                );
                currentFuelInReservoir += fuelToTransfer;
                currentAdditionalFuel -= fuelToTransfer;
                currentCapacity -= fuelToTransfer;
//                log.debug("Transferred {}T fuel from cargo to reservoir. Current fuel in reservoir: {}T, additional fuel left: {}T, current capacity used: {}T", fuelToTransfer, currentFuelInReservoir, currentAdditionalFuel, currentCapacity);
            }

            // Try maximum jump first
            int fuelForMaxJump = requiredFuelForJump(
                    MAX_JUMP_DISTANCE,
                    carrierMass,
                    currentCapacity,
                    currentFuelInReservoir
            );

            if (currentFuelInReservoir >= fuelForMaxJump) {
                // Can make full 500Ly jump
                totalJumpRange += MAX_JUMP_DISTANCE;
                currentFuelInReservoir -= fuelForMaxJump;
//                log.debug("Made full {}Ly jump consuming {}T fuel. Total jump range: {}Ly, fuel left in reservoir: {}T", MAX_JUMP_DISTANCE, fuelForMaxJump, totalJumpRange, currentFuelInReservoir);
                continue; // Continue to next jump
            }

            // Calculate maximum distance for partial jump
            // The rounding threshold is when: 5 + D * mass / 200000 = currentFuelInReservoir + 0.5
            // Because Math.round() rounds to nearest integer (round half up)
            double currentMass = currentCapacity + currentFuelInReservoir + carrierMass;

            // Maximum distance before fuel requirement rounds up to next integer
            // Solve: 5 + D * mass / 200000 = currentFuelInReservoir + 0.5
            // D = (currentFuelInReservoir - 4.5) * 200000 / mass
            double maxDistance = (currentFuelInReservoir - 4.5) * 200_000D / currentMass;

            // Ensure non-negative
            if (maxDistance <= 0) {
                break;
            }

            totalJumpRange += maxDistance;
//            log.debug("Made partial {}Ly jump. Total jump range: {}Ly", maxDistance, totalJumpRange);
            break; // After partial jump, we're done
        }

        return totalJumpRange;
    }
}