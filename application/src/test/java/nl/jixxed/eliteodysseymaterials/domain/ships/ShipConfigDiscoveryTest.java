package nl.jixxed.eliteodysseymaterials.domain.ships;


import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.FrameShiftDrive;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.FrameShiftDriveBooster;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.ShieldGenerator;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.military.GuardianShieldReinforcementPackage;
import nl.jixxed.eliteodysseymaterials.domain.ships.utility.ShieldBooster;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats.ModuleProfile;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier.*;

@Slf4j
public class ShipConfigDiscoveryTest {
//    @Disabled
    @Test
    void findModuleStats() {

        ShipModule.getBasicModules();
        final Ship ship = Ship.EXPLORER_NX;
        ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().ifPresent(slot ->
                slot.setShipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_OVERCHARGE_8_A_MK_II));
//        for (double optimisedMass = 2600.0; optimisedMass < 3200.0; optimisedMass+=10.0){
        double optimisedMass = 4670.0;
//        double fuelMultiplier = 0.013;
        double maxFuelPerJump = 6.8;
        double fuelPower = 2.9;
//        for (double fuelPower = 6.0; fuelPower >= 1.0; fuelPower-=0.01){
            for (double fuelMultiplier = 0.004; fuelMultiplier >= 0.00395; fuelMultiplier-=0.000001){

                double rangeCurrent = calculateJumpRangeCurrent(ship, optimisedMass, fuelPower, fuelMultiplier, maxFuelPerJump);
//                if(rangeCurrent > 34.1 && rangeCurrent < 34.2)
                log.info("Current jump range: {}, optimisedMass: {}, fuelMultiplier: {}", rangeCurrent, optimisedMass,fuelMultiplier);
            }
//        }

//        }
    }

    public double calculateJumpRangeCurrent(final Ship ship, double optimisedMass, double fuelPower, double fuelMultiplier, double maxFuelPerJump) {
        return calculateJumpRange(ship, ship.getEmptyMass() + ship.getCurrentCargo() + ship.getCurrentFuel() + ship.getCurrentFuelReserve(), ship.getCurrentFuel(), optimisedMass, fuelPower, fuelMultiplier,maxFuelPerJump);
    }

    public double calculateJumpRange(final Ship ship, final double mass, final double fuel, double optimisedMass, double fuelPower, double fuelMultiplier, double maxFuelPerJump) {

//            final double maxFuelPerJump = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D, true)).orElse(1D);
//        final double fuelMultiplier = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FUEL_MULTIPLIER, 1D, true)).orElse(1D);
        final double jumpRangeIncrease = ship.getOptionalSlots().stream().filter(slot -> slot.getShipModule() instanceof FrameShiftDriveBooster).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.JUMP_RANGE_INCREASE, 1D, true)).orElse(0D);
            return calculateJumpDistance(mass, Math.min(fuel, maxFuelPerJump), optimisedMass, fuelMultiplier, fuelPower, jumpRangeIncrease);
    }

    private double calculateJumpDistance(final double mass, final double fuel, final double optimisedMass, final double fuelMultiplier, final double fuelPower, final double jumpRangeIncrease) {
        if (fuel <= 0D) {
            return 0D;
        }
        return Math.pow(fuel / fuelMultiplier, 1 / fuelPower) * optimisedMass / mass + jumpRangeIncrease;
    }
    @Test
    void findShipStats() {

        ShipModule.getBasicModules();
        final Ship ship = Ship.EXPLORER_NX;
        //Fill in MASS and FUEL_RESERVE on the ship
//        Ship.ALL.stream().forEach(sheep -> log.debug(sheep.getShipType() + " " + (sheep.getEmptyMass() + sheep.getCurrentFuel() + sheep.getCurrentFuelReserve())));


//        System.out.println(OperatingSystem.getOperatingSystem().getDetailedString());
//        System.out.println(OperatingSystem.getOperatingSystem().getArch());
//        System.out.println(OperatingSystem.getOperatingSystem().getDesktopEnvironment());
//        System.out.println(OperatingSystem.getOperatingSystem().getDisplayString());
//        System.out.println(OperatingSystem.getOperatingSystem().getType());
//        Ship.ALL.stream().sorted(Comparator.comparing(sheep -> (Double)sheep.getAttributes().get(MANOEUVRABILITY))).forEach(sheep ->  {
//            Double pitch = (Double) sheep.getAttributes().get(MAX_PITCH_SPEED);
//            Double roll = (Double) sheep.getAttributes().get(MAX_ROLL_SPEED);
//            Double yaw = (Double) sheep.getAttributes().get(MAX_YAW_SPEED);
//            Double manoeuver = (Double)sheep.getAttributes().get(MANOEUVRABILITY);
//            log.debug(sheep.getShipType() + ": " + manoeuver + " sum: " + (pitch*roll*yaw) + " Pitch: " + pitch + " Roll: " + roll + " Yaw: " + yaw);
//        });
//
//        Ship.ALL.stream().sorted(Comparator.comparing(sheep -> (Double)sheep.getAttributes().get(MANOEUVRABILITY))).forEach(sheep ->  {
//            Double pitch = (Double) sheep.getAttributes().get(FORWARD_ACCELERATION);
//            Double roll = (Double) sheep.getAttributes().get(REVERSE_ACCELERATION);
//            Double yaw = (Double) sheep.getAttributes().get(LATERAL_ACCELERATION);
//            Double manoeuver = (Double)sheep.getAttributes().get(MANOEUVRABILITY);
//            log.debug(sheep.getShipType() + ": " + manoeuver + " sum: " + (pitch*roll*yaw) + " fwd: " + pitch + " rev: " + roll + " lat: " + yaw);
//        });
        findTopSpeed(ship, 205.0, 0.5);
        findBoostSpeed(ship, 283.0, 0.5);
        findPitch(ship, 29.28, 0.5);
        findRoll(ship, 73.21, 0.5);
        findYaw(ship, 13.67, 0.5);
        findShieldStrength(ship, 195.7, 0.5);
        findArmourStrength(ship, 621.0, 0.5);
// Requires TOP_SPEED to be set
        findMinThrust(ship, 190.0, 0.5);
//        findMinPitch(ship, 14.12, 0.5D);
//findprice(ship);

    }

    private void findprice(Ship ship) {
//        2025-09-19 10:14:31 INFO  n.j.e.d.s.ShipConfigDiscoveryTest - Base price of PANTHER_CLIPPER_MK_II is 286906177
//        286906165,
//                301348585,
        long value = ship.getRetailPrice() - ship.getModulesValue();
        log.info("Base price of {} is {}", ship.getShipType(), value);
    }

    private double calculatePitchMinimum(Ship ship, double pitchSpeed, ModuleProfile moduleProfile) {
        return pitchSpeed * moduleProfile.getMassCurveMultiplier(ship.getEmptyMass() + ship.getMaxFuel() + ship.getMaxCargo() + ship.getMaxFuelReserve()) / 100;
    }

    protected void findMinPitch(Ship ship, Double expected, Double delta) {
        final Optional<Slot> thrusters = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_THRUSTERS)).findFirst().filter(Slot::isOccupied);
        final Double minimumMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.ENGINE_MINIMUM_MASS, false)).orElse(0D);
        final Double optimalMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.ENGINE_OPTIMAL_MASS, false)).orElse(0D);
        final Double maximumMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.MAXIMUM_MASS, false)).orElse(0D);
        final Double minimumMultiplier = getMinimumMultiplier(thrusters);
        final Double optimalMultiplier = getOptimalMultiplier(thrusters);
        final Double maximumMultiplier = getMaximumMultiplier(thrusters);
        final ModuleProfile moduleProfile = new ModuleProfile(minimumMass, optimalMass, maximumMass, minimumMultiplier, optimalMultiplier, maximumMultiplier);

        Double value = 0D;
        Double minimumPitch = 0D;
        while (!within(minimumPitch, expected, delta / 10D) && value < 1000D) {
            final Double pitchSpeed = value = value + delta;

            minimumPitch = calculatePitchMinimum(ship, pitchSpeed, moduleProfile);

        }
        log.info("Value of {} results in max pitch of {} which is {}within range", value, minimumPitch, (!within(minimumPitch, expected, delta / 10D)) ? "not " : "");
        var minimumPitch2 = calculatePitchMinimum(ship, value + delta, moduleProfile);
        var inRange = within(minimumPitch2, expected, delta / 10D);
        log.info("Value of {} results in max pitch of {} which is {}within range", value + delta, minimumPitch2, (!inRange) ? "not " : "");

    }


    private double calculateMaxSpeed(final Ship ship, final double speed, final ModuleProfile moduleProfile) {
        return speed * moduleProfile.getMassCurveMultiplier(ship.getEmptyMass()) / 100D;
    }

    private static Double getMaximumMultiplier(Optional<Slot> thrusters) {
        return thrusters
                .map(Slot::getShipModule)
                .flatMap(shipModule -> Optional.ofNullable((Double) shipModule.getAttributeValueOrDefault(HorizonsModifier.MAXIMUM_MULTIPLIER_SPEED, null, false)))
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.MAXIMUM_MULIPLIER, false)).orElse(0D));
    }

    private static Double getOptimalMultiplier(Optional<Slot> thrusters) {
        return thrusters
                .map(Slot::getShipModule)
                .flatMap(shipModule -> Optional.ofNullable((Double) shipModule.getAttributeValueOrDefault(HorizonsModifier.OPTIMAL_MULTIPLIER_SPEED, null, false)))
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.OPTIMAL_MULTIPLIER, false)).orElse(0D));
    }

    private static Double getMinimumMultiplier(Optional<Slot> thrusters) {
        return thrusters
                .map(Slot::getShipModule)
                .flatMap(shipModule -> Optional.ofNullable((Double) shipModule.getAttributeValueOrDefault(HorizonsModifier.MINIMUM_MULTIPLIER_SPEED, null, false)))
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.MINIMUM_MULTIPLIER, false)).orElse(0D));
    }

    private double calculateMinSpeed(Ship ship, Double speed, Double minimumThrust, ModuleProfile moduleProfile) {
        return speed * (moduleProfile.getMassCurveMultiplier(ship.getEmptyMass() + ship.getMaxFuel() + ship.getMaxCargo() + ship.getMaxFuelReserve()) / 100D) * (minimumThrust / 100D);
    }

    protected void findMinThrust(Ship ship, Double expected, Double delta) {

        final Optional<Slot> thrusters = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_THRUSTERS)).findFirst().filter(Slot::isOccupied);
        final Double minimumMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.ENGINE_MINIMUM_MASS, false)).orElse(0D);
        final Double optimalMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.ENGINE_OPTIMAL_MASS, false)).orElse(0D);
        final Double maximumMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.MAXIMUM_MASS, false)).orElse(0D);
        final Double minimumMultiplier = getMinimumMultiplier(thrusters);
        final Double optimalMultiplier = getOptimalMultiplier(thrusters);
        final Double maximumMultiplier = getMaximumMultiplier(thrusters);
        final ModuleProfile moduleProfile = new ModuleProfile(minimumMass, optimalMass, maximumMass, minimumMultiplier, optimalMultiplier, maximumMultiplier);

        final Double topSpeed = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.TOP_SPEED, 0.0D);
        Double value = 0D;
        Double thrust = 0D;
        while (!within(thrust, expected, delta) && value < 1000D) {
            final Double minThrust = value = value + delta;

            thrust = calculateMinSpeed(ship, topSpeed, minThrust, moduleProfile);

        }
        log.info("Value of {} results in min thrust of {} which is within range", value, thrust);
        var thrust2 = calculateMinSpeed(ship, topSpeed, value + delta, moduleProfile);
        var inRange = within(thrust2, expected, delta);
        log.info("Value of {} results in min thrust of {} which is {}within range", value + delta, thrust2, (!inRange) ? "not " : "");

    }

    protected void findTopSpeed(Ship ship, Double expected, Double delta) {

        final Optional<Slot> thrusters = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_THRUSTERS)).findFirst().filter(Slot::isOccupied);
        final Double minimumMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.ENGINE_MINIMUM_MASS, false)).orElse(0D);
        final Double optimalMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.ENGINE_OPTIMAL_MASS, false)).orElse(0D);
        final Double maximumMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.MAXIMUM_MASS, false)).orElse(0D);
        final Double minimumMultiplier = getMinimumMultiplier(thrusters);
        final Double optimalMultiplier = getOptimalMultiplier(thrusters);
        final Double maximumMultiplier = getMaximumMultiplier(thrusters);
        final ModuleProfile moduleProfile = new ModuleProfile(minimumMass, optimalMass, maximumMass, minimumMultiplier, optimalMultiplier, maximumMultiplier);

        Double value = 0D;
        Double maxSpeed = 0D;
        while (!within(maxSpeed, expected, delta) && value < 1000D) {
            final Double topSpeed = value = value + delta;

            maxSpeed = calculateMaxSpeed(ship, topSpeed, moduleProfile);

        }
        log.info("Value of {} results in max speed of {} which is within range", value, maxSpeed);

        while (within(maxSpeed, expected, delta) && value < 1000D) {
            final Double topSpeed = value = value + delta;

            maxSpeed = calculateMaxSpeed(ship, topSpeed, moduleProfile);
            log.info("Value of {} results in max speed of {} which is within range", value, maxSpeed);

        }
        var maxSpeed2 = calculateMaxSpeed(ship, value + delta, moduleProfile);
        var inRange = within(maxSpeed2, expected, delta);
        log.info("Value of {} results in max speed of {} which is {}within range", value + delta, maxSpeed2, (!inRange) ? "not " : "");

    }


    protected void findBoostSpeed(Ship ship, Double expected, Double delta) {

        final Optional<Slot> thrusters = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_THRUSTERS)).findFirst().filter(Slot::isOccupied);
        final Double minimumMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.ENGINE_MINIMUM_MASS, false)).orElse(0D);
        final Double optimalMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.ENGINE_OPTIMAL_MASS, false)).orElse(0D);
        final Double maximumMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.MAXIMUM_MASS, false)).orElse(0D);
        final Double minimumMultiplier = getMinimumMultiplier(thrusters);
        final Double optimalMultiplier = getOptimalMultiplier(thrusters);
        final Double maximumMultiplier = getMaximumMultiplier(thrusters);

        final ModuleProfile moduleProfile = new ModuleProfile(minimumMass, optimalMass, maximumMass, minimumMultiplier, optimalMultiplier, maximumMultiplier);

        Double value = 0D;
        Double maxBoost = 0D;
        while (!within(maxBoost, expected, delta) && value < 1000D) {
            final Double boostSpeed = value = value + delta;

            maxBoost = calculateMaxSpeed(ship, boostSpeed, moduleProfile);

        }
        log.info("Value of {} results in max boost of {} which is within range", value, maxBoost);
        while (within(maxBoost, expected, delta) && value < 1000D) {
            final Double boostSpeed = value = value + delta;

            maxBoost = calculateMaxSpeed(ship, boostSpeed, moduleProfile);
            log.info("Value of {} results in max boost of {} which is within range", value, maxBoost);

        }
        var maxBoost2 = calculateMaxSpeed(ship, value + delta, moduleProfile);
        var inRange = within(maxBoost2, expected, delta);
        log.info("Value of {} results in max boost of {} which is {}within range", value + delta, maxBoost2, (!inRange) ? "not " : "");
    }

    private boolean within(Double value, Double expected, double v) {
        return value >= expected - v && value <= expected + v;
    }



    private double calculatePitchCurrent(Ship ship, double pitchSpeed, ModuleProfile moduleProfile) {
        return pitchSpeed * moduleProfile.getMassCurveMultiplier(ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo() + ship.getCurrentFuelReserve()) / 100;
    }

    private double calculateRollCurrent(Ship ship, double rollSpeed, ModuleProfile moduleProfile) {
        return rollSpeed * moduleProfile.getMassCurveMultiplier(ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo() + ship.getCurrentFuelReserve()) / 100;
    }

    private double calculateYawCurrent(Ship ship, double yawSpeed, ModuleProfile moduleProfile) {
        return yawSpeed * moduleProfile.getMassCurveMultiplier(ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo() + ship.getCurrentFuelReserve()) / 100;
    }

    protected void findPitch(Ship ship, Double expected, Double delta) {
        final Optional<Slot> thrusters = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_THRUSTERS)).findFirst().filter(Slot::isOccupied);
        final Double minimumMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.ENGINE_MINIMUM_MASS, false)).orElse(0D);
        final Double optimalMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.ENGINE_OPTIMAL_MASS, false)).orElse(0D);
        final Double maximumMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.MAXIMUM_MASS, false)).orElse(0D);
        final Double minimumMultiplier = getMinimumMultiplier(thrusters);
        final Double optimalMultiplier = getOptimalMultiplier(thrusters);
        final Double maximumMultiplier = getMaximumMultiplier(thrusters);
        final ModuleProfile moduleProfile = new ModuleProfile(minimumMass, optimalMass, maximumMass, minimumMultiplier, optimalMultiplier, maximumMultiplier);

        Double value = 0D;
        Double maximumPitch = 0D;
        while (!within(maximumPitch, expected, delta / 10D) && value < 1000D) {
            final Double pitchSpeed = value = value + delta;

            maximumPitch = calculatePitchCurrent(ship, pitchSpeed, moduleProfile);

        }
        log.info("Value of {} results in max pitch of {} which is {}within range", value, maximumPitch, (!within(maximumPitch, expected, delta / 10D)) ? "not " : "");
        var maximumPitch2 = calculatePitchCurrent(ship, value + delta, moduleProfile);
        var inRange = within(maximumPitch2, expected, delta / 10D);
        log.info("Value of {} results in max pitch of {} which is {}within range", value + delta, maximumPitch2, (!inRange) ? "not " : "");

    }

    protected void findRoll(Ship ship, Double expected, Double delta) {
        final Optional<Slot> thrusters = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_THRUSTERS)).findFirst().filter(Slot::isOccupied);
        final Double minimumMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.ENGINE_MINIMUM_MASS, false)).orElse(0D);
        final Double optimalMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.ENGINE_OPTIMAL_MASS, false)).orElse(0D);
        final Double maximumMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.MAXIMUM_MASS, false)).orElse(0D);
        final Double minimumMultiplier = getMinimumMultiplier(thrusters);
        final Double optimalMultiplier = getOptimalMultiplier(thrusters);
        final Double maximumMultiplier = getMaximumMultiplier(thrusters);
        final ModuleProfile moduleProfile = new ModuleProfile(minimumMass, optimalMass, maximumMass, minimumMultiplier, optimalMultiplier, maximumMultiplier);


        Double value = 0D;
        Double maximumRoll = 0D;
        while (!within(maximumRoll, expected, delta / 10D) && value < 1000D) {
            final Double rollSpeed = value = value + delta;

            maximumRoll = calculateRollCurrent(ship, rollSpeed, moduleProfile);

        }
        log.info("Value of {} results in max roll of {} which is {}within range", value, maximumRoll, (!within(maximumRoll, expected, delta / 10D)) ? "not " : "");
        var maximumRoll2 = calculateRollCurrent(ship, value + delta, moduleProfile);
        var inRange = within(maximumRoll2, expected, delta / 10D);
        log.info("Value of {} results in max roll of {} which is {}within range", value + delta, maximumRoll2, (!inRange) ? "not " : "");

    }

    protected void findYaw(Ship ship, Double expected, Double delta) {
        final Optional<Slot> thrusters = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_THRUSTERS)).findFirst().filter(Slot::isOccupied);
        final Double minimumMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.ENGINE_MINIMUM_MASS, false)).orElse(0D);
        final Double optimalMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.ENGINE_OPTIMAL_MASS, false)).orElse(0D);
        final Double maximumMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.MAXIMUM_MASS, false)).orElse(0D);
        final Double minimumMultiplier = getMinimumMultiplier(thrusters);
        final Double optimalMultiplier = getOptimalMultiplier(thrusters);
        final Double maximumMultiplier = getMaximumMultiplier(thrusters);
        final ModuleProfile moduleProfile = new ModuleProfile(minimumMass, optimalMass, maximumMass, minimumMultiplier, optimalMultiplier, maximumMultiplier);


        Double value = 0D;
        Double maximumYaw = 0D;
        while (!within(maximumYaw, expected, delta / 10D) && value < 1000D) {
            final Double yawSpeed = value = value + delta;

            maximumYaw = calculateYawCurrent(ship, yawSpeed, moduleProfile);

        }
        log.info("Value of {} results in max yaw of {} which is {}within range", value, maximumYaw, (!within(maximumYaw, expected, delta / 10D)) ? "not " : "");
        var maximumYaw2 = calculateYawCurrent(ship, value + delta, moduleProfile);
        var inRange = within(maximumYaw2, expected, delta / 10D);
        log.info("Value of {} results in max yaw of {} which is {}within range", value + delta, maximumYaw2, (!inRange) ? "not " : "");

    }

    private void findShieldStrength(Ship ship, Double expected, Double delta) {
        ship.getOptionalSlots().stream()
                .filter(slot -> slot.getShipModule() instanceof ShieldGenerator)
                .findFirst()
                .ifPresent(shieldGeneratorSlot -> {
                    ShipModule module = shieldGeneratorSlot.getShipModule();
                    Double minimumMass = (Double) module.getAttributeValue(SHIELDGEN_MINIMUM_MASS, false);
                    Double optimalMass = (Double) module.getAttributeValue(SHIELDGEN_OPTIMAL_MASS, false);
                    Double maximumMass = (Double) module.getAttributeValue(SHIELDGEN_MAXIMUM_MASS, false);
                    Double minimumStrength = (Double) module.getAttributeValue(SHIELDGEN_MINIMUM_STRENGTH, false);
                    Double optimalStrength = (Double) module.getAttributeValue(SHIELDGEN_OPTIMAL_STRENGTH, false);
                    Double maximumStrength = (Double) module.getAttributeValue(SHIELDGEN_MAXIMUM_STRENGTH, false);
                    double shieldReinforcement = ship.getOptionalSlots().stream()
                            .filter(slot -> slot.getShipModule() instanceof GuardianShieldReinforcementPackage)
                            .mapToDouble(slot -> (Double) slot.getShipModule().getAttributeValue(SHIELD_REINFORCEMENT, false))
                            .sum();
                    double totalShieldBoost = ship.getUtilitySlots().stream()
                            .filter(slot -> slot.getShipModule() instanceof ShieldBooster)
                            .mapToDouble(slot -> (Double) slot.getShipModule().getAttributeValue(SHIELD_BOOST, false))
                            .sum();

                    Double value = 0D;
                    Double shields = 0D;
                    ModuleProfile moduleProfile = new ModuleProfile(minimumMass, optimalMass, maximumMass, minimumStrength, optimalStrength, maximumStrength);
                    while (!within(shields, expected, delta) && value < 1000D) {

                        final Double shieldValue = value = value + delta;
                        shields = shieldReinforcement + (shieldValue
                                * getEffectiveShieldBoostMultiplier(totalShieldBoost)
                                * moduleProfile.getMassCurveMultiplier((double) ship.getAttributes().getOrDefault(MASS, 0D)));

                    }
                    log.info("Value of {} results in shields of {} which is within range", value, shields);
                    var shields2 = shields;
                    while (within(shields2, expected, delta) && value < 1000D) {
                        final Double shieldValue = value = value + delta;
                         shields2 = shieldReinforcement + (shieldValue
                                * getEffectiveShieldBoostMultiplier(totalShieldBoost)
                                * moduleProfile.getMassCurveMultiplier((double) ship.getAttributes().getOrDefault(MASS, 0D)));
                        var inRange = within(shields2, expected, delta);
                        log.info("Value of {} results in shields of {} which is {}within range", value, shields2, (!inRange) ? "not " : "");
                    }
                });

    }

    private double getEffectiveShieldBoostMultiplier(Double shieldbst) {
        return 1 + shieldbst;
    }


    public void findArmourStrength(Ship ship, Double expected, Double delta) {

        final Optional<ShipModule> armourModule = ship.getCoreSlots().stream().filter(slot -> SlotType.CORE_ARMOUR.equals(slot.getSlotType())).findFirst().map(Slot::getShipModule);
        double hullBoost = (double) armourModule.map(shipModule -> shipModule.getAttributeValue(HorizonsModifier.HULL_BOOST, false)).orElse(0D);

        Double value = 0D;
        Double armour = 0D;
        while (!within(armour, expected, delta) && value < 1000D) {

            final Double armourValue = value = value + delta;
            armour = (armourValue * (1D + hullBoost));
        }
        log.info("Value of {} results in armour of {} which is within range", value, armour);
        var armour2 = ((value + delta) * (1D + hullBoost));
        var inRange = within(armour2, expected, delta);
        log.info("Value of {} results in armour of {} which is {}within range", value + delta, armour2, (!inRange) ? "not " : "");

    }
}
