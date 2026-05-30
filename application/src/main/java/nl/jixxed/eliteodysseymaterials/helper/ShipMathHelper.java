/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.helper;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.FrameShiftDriveBooster;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats.ModuleProfile;

import java.util.Optional;

public class ShipMathHelper {

    public static double getSynthesisBoost(final Ship ship) {
        if (ship == null) return 1.0D;
        return ship.getCoreSlots().stream()
                .filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE)
                .findFirst()
                .filter(Slot::isOccupied)
                .map(slot -> slot.getShipModule().synthesisBlueprints().stream()
                        .filter(bp -> bp.getHorizonsBlueprintGrade().equals(slot.getShipModule().getSynthesisGrade()))
                        .findFirst()
                        .map(bp -> 1D + bp.getModifiers().get(HorizonsModifier.JUMP_RANGE).getModifier().getEnd())
                        .orElse(1D))
                .orElse(1D);
    }

    // ── Jump range ──────────────────────────────────────────────────────────────────

    public static double calculateJumpRangeMinOutfitting(final Ship ship) {
        if (ship == null) return 0.0D;
        final double maxFuelPerJump = ship.getCoreSlots().stream()
                .filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied)
                .map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D, true))
                .orElse(1D);
        return calculateJumpRange(ship, ship.getEmptyMass() + ship.getMaxFuel() + ship.getMaxCargo(), maxFuelPerJump) * getSynthesisBoost(ship);
    }

    public static double calculateJumpRangeCurrentOutfitting(final Ship ship) {
        if (ship == null) return 0.0D;
        final double maxFuelPerJump = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D, true)).orElse(1D);
        return calculateJumpRange(ship, ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo() + ship.getCurrentFuelReserve(), Math.min(ship.getCurrentFuel() + ship.getCurrentFuelReserve(), maxFuelPerJump)) * getSynthesisBoost(ship);
    }

    public static double calculateJumpRangeMaxOutfitting(final Ship ship) {
        if (ship == null) return 0.0D;
        final double maxFuelPerJump = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D, true)).orElse(1D);
        return calculateJumpRange(ship, ship.getEmptyMass() + maxFuelPerJump, maxFuelPerJump) * getSynthesisBoost(ship);
    }

    public static double calculateJumpRangeMinMaintenance(final Ship ship) {
        if (ship == null) return 0.0D;
        final double maxFuelPerJump = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D, true)).orElse(1D);
        return calculateJumpRange(ship, ship.getEmptyMass() + ship.getCurrentFuel() + ship.getMaxCargo(), Math.min(ship.getCurrentFuel(), maxFuelPerJump)) * getSynthesisBoost(ship);
    }

    public static double calculateJumpRangeCurrentMaintenance(final Ship ship) {
        if (ship == null) return 0.0D;
        final double maxFuelPerJump = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D, true)).orElse(1D);
        return calculateJumpRange(ship, ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo(), Math.min(ship.getCurrentFuel(), maxFuelPerJump)) * getSynthesisBoost(ship);
    }

    public static double calculateJumpRangeMaxMaintenance(final Ship ship) {
        if (ship == null) return 0.0D;
        final double maxFuelPerJump = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D, true)).orElse(1D);
        return calculateJumpRange(ship, ship.getEmptyMass() + ship.getCurrentFuel(), Math.min(ship.getCurrentFuel(), maxFuelPerJump)) * getSynthesisBoost(ship);
    }

    public static double calculateJumpRangeMaxFueled(final Ship ship) {
        if (ship == null) return 0.0D;
        return calculateJumpRange(ship, ship.getEmptyMass() + ship.getMaxFuel(), ship.getMaxFuel());
    }

    public static double calculateTotalJumpRangeMin() {
        return 0d;
    }

    public static double calculateTotalJumpRangeCurrent(final Ship ship) {
        if (ship == null) return 0d;
        var range = 0d;
        final double fuelCap = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE)
                .findFirst()
                .filter(Slot::isOccupied)
                .map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D, true))
                .orElse(1D);
        double fuel = ship.getCurrentFuel();
        while (fuel > fuelCap) {
            range += calculateJumpRange(ship, ship.getEmptyMass() + fuel + ship.getCurrentCargo(), Math.min(fuel, fuelCap)) * getSynthesisBoost(ship);
            fuel -= fuelCap;
        }
        return range + calculateJumpRange(ship, ship.getEmptyMass() + fuel + ship.getCurrentCargo(), Math.min(fuel, fuelCap)) * getSynthesisBoost(ship);
    }

    public static double calculateTotalJumpRangeMax(final Ship ship) {
        if (ship == null) return 0d;
        var range = 0d;
        final double fuelCap = ship.getCoreSlots().stream()
                .filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE)
                .findFirst()
                .filter(Slot::isOccupied)
                .map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D, true))
                .orElse(1D);
        double fuel = ship.getMaxFuel();
        while (fuel > fuelCap) {
            range += calculateJumpRange(ship, ship.getEmptyMass() + fuel, Math.min(fuel, fuelCap)) * getSynthesisBoost(ship);
            fuel -= fuelCap;
        }
        return (range + calculateJumpRange(ship, ship.getEmptyMass() + fuel, Math.min(fuel, fuelCap))) * getSynthesisBoost(ship);
    }

    public static double calculateJumpRange(final Ship ship, final double mass, final double fuel) {
        if (ship == null) return 0.0D;
        final double maxFuelPerJump = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D, true)).orElse(1D);
        final double optimisedMass = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FSD_OPTIMISED_MASS, 1D, true)).orElse(1D);
        final double fuelMultiplier = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FUEL_MULTIPLIER, 1D, true)).orElse(1D);
        final double fuelPower = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FUEL_POWER, 1D, true)).orElse(1D);
        final double jumpRangeIncrease = ship.getOptionalSlots().stream().filter(slot -> slot.getShipModule() instanceof FrameShiftDriveBooster).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.JUMP_RANGE_INCREASE, 1D, true)).orElse(0D);
        return calculateJumpDistance(mass, Math.min(fuel, maxFuelPerJump), optimisedMass, fuelMultiplier, fuelPower, jumpRangeIncrease);
    }

    public static double calculateJumpDistance(final double mass, final double fuel, final double optimisedMass, final double fuelMultiplier, final double fuelPower, final double jumpRangeIncrease) {
        if (fuel <= 0D) {
            return 0D;
        }
        return Math.pow(fuel / fuelMultiplier, 1 / fuelPower) * optimisedMass / mass + jumpRangeIncrease;
    }

    // ── Engine speed ────────────────────────────────────────────────────────────────

    /**
     * Convenience: min top speed (max fuel + cargo + fuel reserve mass)
     */
    public static double calculateMinSpeed(final Ship ship) {
        final Double topSpeed = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.TOP_SPEED, 0.0D);
        final double mass = ship.getEmptyMass() + ship.getMaxFuel() + ship.getMaxCargo() + ship.getMaxFuelReserve();
        return topSpeed * getEngineMassProfile(ship).getMassCurveMultiplier(mass) / 100D * ((Double) ship.getAttributes().getOrDefault(HorizonsModifier.MINIMUM_THRUST, 0.0D) / 100D);
    }

    /**
     * Convenience: current top speed with pip interpolation
     */
    public static double calculateCurrentSpeed(final Ship ship) {
        final Double topSpeed = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.TOP_SPEED, 0.0D);
        final double multiplier = ApplicationState.getInstance().getEnginePips() / 8.0;
        final Double minimumThrust = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.MINIMUM_THRUST, 0.0D);
        final double mass = ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo();
        return topSpeed * getEngineMassProfile(ship).getMassCurveMultiplier(mass) / 100D * (multiplier + (minimumThrust / 100D) * (1D - multiplier));
    }

    /**
     * Convenience: max top speed (empty mass)
     */
    public static double calculateMaxSpeed(final Ship ship) {
        final Double topSpeed = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.TOP_SPEED, 0.0D);
        return topSpeed * getEngineMassProfile(ship).getMassCurveMultiplier(ship.getEmptyMass()) / 100D;
    }

    /**
     * Convenience: min boost speed (max fuel + cargo + fuel reserve mass, checks capacity)
     */
    public static double calculateMinBoostSpeed(final Ship ship) {
        if (!hasBoostCapacity(ship)) return Double.NaN;
        final Double boostSpeed = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.BOOST_SPEED, 0.0D);
        final double mass = ship.getEmptyMass() + ship.getMaxFuel() + ship.getMaxCargo() + ship.getMaxFuelReserve();
        return boostSpeed * getEngineMassProfile(ship).getMassCurveMultiplier(mass) / 100D * ((Double) ship.getAttributes().getOrDefault(HorizonsModifier.MINIMUM_THRUST, 0.0D) / 100D);
    }

    /**
     * Convenience: current boost speed with pip interpolation (checks capacity)
     */
    public static double calculateCurrentBoostSpeed(final Ship ship) {
        if (!hasBoostCapacity(ship)) return Double.NaN;
        final Double boostSpeed = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.BOOST_SPEED, 0.0D);
        final double multiplier = ApplicationState.getInstance().getEnginePips() / 8.0;
        final Double minimumThrust = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.MINIMUM_THRUST, 0.0D);
        final double mass = ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo();
        return boostSpeed * getEngineMassProfile(ship).getMassCurveMultiplier(mass) / 100D * (multiplier + (minimumThrust / 100D) * (1D - multiplier));
    }

    /**
     * Convenience: max boost speed (empty mass, checks capacity)
     */
    public static double calculateMaxBoostSpeed(final Ship ship) {
        if (!hasBoostCapacity(ship)) return Double.NaN;
        final Double boostSpeed = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.BOOST_SPEED, 0.0D);
        return boostSpeed * getEngineMassProfile(ship).getMassCurveMultiplier(ship.getEmptyMass()) / 100D;
    }

    // ── Engine recharge ────────────────────────────────────────────────────────────

    /**
     * Convenience: min recharge time (no pips, checks capacity)
     */
    public static double calculateMinRechargeTime(final Ship ship) {
        if (!hasBoostCapacity(ship)) return Double.NaN;
        final double engineRecharge = (double) getPowerDistributor(ship)
                .map(s -> s.getShipModule().getAttributeValue(HorizonsModifier.ENGINES_RECHARGE, true))
                .orElse(0D);
        return (double) ship.getAttributes().getOrDefault(HorizonsModifier.BOOST_COST, 0D) / engineRecharge;
    }

    /**
     * Convenience: current recharge time with pip interpolation (checks capacity)
     */
    public static double calculateCurrentRechargeTime(final Ship ship) {
        if (!hasBoostCapacity(ship)) return Double.NaN;
        final double engineRecharge = (double) getPowerDistributor(ship)
                .map(s -> s.getShipModule().getAttributeValue(HorizonsModifier.ENGINES_RECHARGE, true))
                .orElse(0D);
        final double multiplier = ApplicationState.getInstance().getEnginePips() / 8.0;
        return (double) ship.getAttributes().getOrDefault(HorizonsModifier.BOOST_COST, 0D) / (engineRecharge * Math.pow(multiplier, 1.1));
    }

    /**
     * Convenience: max recharge time (checks capacity)
     */
    public static double calculateMaxRechargeTime(final Ship ship) {
        if (!hasBoostCapacity(ship)) return Double.NaN;
        final double engineRecharge = (double) getPowerDistributor(ship)
                .map(s -> s.getShipModule().getAttributeValue(HorizonsModifier.ENGINES_RECHARGE, true))
                .orElse(0D);
        final double multiplier = ApplicationState.getInstance().getEnginePips() / 8.0;
        return (double) ship.getAttributes().getOrDefault(HorizonsModifier.BOOST_COST, 0D) / (engineRecharge * Math.pow(multiplier > 0 ? 1D / 8D : 0D, 1.1));
    }

    // ── Engine acceleration ────────────────────────────────────────────────────────

    /**
     * Convenience: forward acceleration with current fuel/cargo/reserve mass
     */
    public static double calculateForwardAcceleration(final Ship ship) {
        return (Double) ship.getAttributes().get(HorizonsModifier.FORWARD_ACCELERATION) * getAccelerationProfile(ship).getMassCurveMultiplier(ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo() + ship.getCurrentFuelReserve()) / 100D;
    }

    /**
     * Convenience: reverse acceleration with current fuel/cargo/reserve mass
     */
    public static double calculateReverseAcceleration(final Ship ship) {
        return (Double) ship.getAttributes().get(HorizonsModifier.REVERSE_ACCELERATION) * getAccelerationProfile(ship).getMassCurveMultiplier(ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo() + ship.getCurrentFuelReserve()) / 100D;
    }

    /**
     * Convenience: lateral acceleration with current fuel/cargo/reserve mass
     */
    public static double calculateLateralAcceleration(final Ship ship) {
        return (Double) ship.getAttributes().get(HorizonsModifier.LATERAL_ACCELERATION) * getAccelerationProfile(ship).getMassCurveMultiplier(ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo() + ship.getCurrentFuelReserve()) / 100D;
    }

    // ── Handling ────────────────────────────────────────────────────────────────────

    /**
     * Convenience: min pitch with pip interpolation
     */
    public static double calculateMinPitch(final Ship ship) {
        return handleRotation(ship, HorizonsModifier.MAX_PITCH_SPEED, RotationMode.MIN, null);
    }

    /**
     * Convenience: current pitch with pip interpolation
     */
    public static double calculateCurrentPitch(final Ship ship) {
        return handleRotation(ship, HorizonsModifier.MAX_PITCH_SPEED, RotationMode.CURRENT, null);
    }

    /**
     * Convenience: max pitch (empty mass)
     */
    public static double calculateMaxPitch(final Ship ship) {
        return handleRotation(ship, HorizonsModifier.MAX_PITCH_SPEED, RotationMode.MAX, null);
    }

    /**
     * Convenience: current boosted pitch
     */
    public static double calculateCurrentBoostedPitch(final Ship ship) {
        return handleRotation(ship, HorizonsModifier.MAX_PITCH_SPEED, RotationMode.CURRENT, getBoostedProfile(ship));
    }

    /**
     * Convenience: min roll (roll unaffected by pips, same as max)
     */
    public static double calculateMinRoll(final Ship ship) {
        return handleRotation(ship, HorizonsModifier.MAX_ROLL_SPEED, RotationMode.MIN, null);
    }

    /**
     * Convenience: current roll with pip interpolation
     */
    public static double calculateCurrentRoll(final Ship ship) {
        return handleRotation(ship, HorizonsModifier.MAX_ROLL_SPEED, RotationMode.CURRENT, null);
    }

    /**
     * Convenience: max roll (empty mass)
     */
    public static double calculateMaxRoll(final Ship ship) {
        return handleRotation(ship, HorizonsModifier.MAX_ROLL_SPEED, RotationMode.MAX, null);
    }

    /**
     * Convenience: current boosted roll with pip interpolation
     */
    public static double calculateCurrentBoostedRoll(final Ship ship) {
        return handleRotation(ship, HorizonsModifier.MAX_ROLL_SPEED, RotationMode.CURRENT, getBoostedProfile(ship));
    }

    /**
     * Convenience: min yaw (yaw unaffected by pips, same as max)
     */
    public static double calculateMinYaw(final Ship ship) {
        return handleRotation(ship, HorizonsModifier.MAX_YAW_SPEED, RotationMode.MIN, null);
    }

    /**
     * Convenience: current yaw (yaw unaffected by pips)
     */
    public static double calculateCurrentYaw(final Ship ship) {
        return handleRotation(ship, HorizonsModifier.MAX_YAW_SPEED, RotationMode.CURRENT, null);
    }

    /**
     * Convenience: max yaw (empty mass)
     */
    public static double calculateMaxYaw(final Ship ship) {
        return handleRotation(ship, HorizonsModifier.MAX_YAW_SPEED, RotationMode.MAX, null);
    }

    /**
     * Convenience: current boosted yaw (yaw unaffected by pips)
     */
    public static double calculateCurrentBoostedYaw(final Ship ship) {
        return handleRotation(ship, HorizonsModifier.MAX_YAW_SPEED, RotationMode.CURRENT, getBoostedProfile(ship));
    }

    private enum RotationMode {MIN, CURRENT, MAX}

    private static double handleRotation(final Ship ship, final HorizonsModifier speedAttr, final RotationMode mode, final ModuleProfile boostProfile) {
        final double speed = resolveSpeed(ship, speedAttr, mode);
        final ModuleProfile profile = getRotationProfile(ship);
        final double mass = resolveMass(ship, mode);
        double result = speed * profile.getMassCurveMultiplier(mass) / 100;
        if (boostProfile != null) {
            result = result * boostProfile.getMassCurveMultiplier(mass) / 100;
        }
        return result;
    }

    private static double resolveSpeed(final Ship ship, final HorizonsModifier speedAttr, final RotationMode mode) {
        final double multiplier = ApplicationState.getInstance().getEnginePips() / 8.0;
        final Double maxSpeed = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.MAX_PITCH_SPEED, 0.0D);
        final Double minSpeed = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.MIN_PITCH_SPEED, 0.0D);
        return switch (mode) {
            case MAX -> (Double) ship.getAttributes().getOrDefault(speedAttr, 0.0D);
            case MIN -> {
                // Roll/Yaw MIN uses MAX speed (no pip interpolation), Pitch MIN uses pip interpolation between min and max
                final Double attrSpeed = (Double) ship.getAttributes().getOrDefault(speedAttr, 0.0D);
                if (speedAttr != HorizonsModifier.MAX_PITCH_SPEED) yield attrSpeed;
                yield maxSpeed * multiplier + minSpeed * (1 - multiplier);
            }
            case CURRENT -> {
                if (speedAttr == HorizonsModifier.MAX_ROLL_SPEED) {
                    yield (Double) ship.getAttributes().getOrDefault(speedAttr, 0.0D) * multiplier;
                }
                yield (Double) ship.getAttributes().getOrDefault(HorizonsModifier.MAX_PITCH_SPEED, 0.0D) * multiplier
                        + (Double) ship.getAttributes().getOrDefault(HorizonsModifier.MIN_PITCH_SPEED, 0.0D) * (1 - multiplier);
            }
        };
    }

    private static double resolveMass(final Ship ship, final RotationMode mode) {
        return switch (mode) {
            case MAX -> ship.getEmptyMass();
            case MIN -> ship.getEmptyMass() + ship.getMaxFuel() + ship.getMaxCargo();
            case CURRENT -> ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo();
        };
    }

    // ── ShipMapper convenience (max values only) ──────────────────────────────────

    /**
     * Convenience: max top speed (empty mass, no pip interpolation)
     */
    public static double calculateTopSpeedMax(final Ship ship) {
        return calculateMaxSpeed(ship);
    }

    /**
     * Convenience: max boost speed (empty mass, no pip interpolation, no capacity check)
     */
    public static double calculateBoostSpeedMax(final Ship ship) {
        final Double boostSpeed = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.BOOST_SPEED, 0.0D);
        return boostSpeed * getEngineMassProfile(ship).getMassCurveMultiplier(ship.getEmptyMass()) / 100D;
    }

    /**
     * Convenience: max pitch (empty mass)
     */
    public static double calculatePitchMax(final Ship ship) {
        return calculateMaxPitch(ship);
    }

    /**
     * Convenience: max roll (empty mass)
     */
    public static double calculateRollMax(final Ship ship) {
        return calculateMaxRoll(ship);
    }

    /**
     * Convenience: max yaw (empty mass)
     */
    public static double calculateYawMax(final Ship ship) {
        return calculateMaxYaw(ship);
    }

    // ── Helper methods ─────────────────────────────────────────────────────────────

    public static Optional<Slot> getThrusters(final Ship ship) {
        return ship.getCoreSlots().stream()
                .filter(slot -> slot.getSlotType().equals(SlotType.CORE_THRUSTERS))
                .findFirst()
                .filter(Slot::isOccupied);
    }

    public static Optional<Slot> getPowerDistributor(final Ship ship) {
        return ship.getCoreSlots().stream()
                .filter(slot -> slot.getSlotType().equals(SlotType.CORE_POWER_DISTRIBUTION))
                .findFirst()
                .filter(Slot::isOccupied);
    }

    /**
     * Check if power distributor provides enough capacity for boost
     */
    private static boolean hasBoostCapacity(final Ship ship) {
        final double boostCost = (double) ship.getAttributes().getOrDefault(HorizonsModifier.BOOST_COST, 0D);
        final double engineCapacity = (double) getPowerDistributor(ship)
                .map(s -> s.getShipModule().getAttributeValue(HorizonsModifier.ENGINES_CAPACITY, true))
                .orElse(0D);
        return engineCapacity > boostCost;
    }

    public static ModuleProfile getEngineMassProfile(final Ship ship) {
        final Optional<Slot> thrusters = getThrusters(ship);
        return getProfile(thrusters, getMinimumMultiplier(thrusters), getOptimalMultiplier(thrusters), getMaximumMultiplier(thrusters));
    }

    public static ModuleProfile getAccelerationProfile(final Ship ship) {
        final Optional<Slot> thrusters = getThrusters(ship);
        return getProfile(thrusters, getMinimumAccelerationMultiplier(thrusters), getOptimalAccelerationMultiplier(thrusters), getMaximumAccelerationMultiplier(thrusters));
    }

    /**
     * For HandlingStats chart rendering
     */
    public static ModuleProfile getRotationProfile(final Ship ship) {
        final Optional<Slot> thrusters = getThrusters(ship);
        return getProfile(thrusters, getMinimumMultiplierRotation(thrusters), getOptimalMultiplierRotation(thrusters), getMaximumMultiplierRotation(thrusters));
    }

    public static ModuleProfile getBoostedProfile(final Ship ship) {
        final Optional<Slot> thrusters = getThrusters(ship);
        return getProfile(thrusters, getMinimumBoostedMultiplier(thrusters), getOptimalBoostedMultiplier(thrusters), getMaximumBoostedMultiplier(thrusters));
    }

    private static ModuleProfile getProfile(final Optional<Slot> thrusters, final Double minMult, final Double optMult, final Double maxMult) {
        if (thrusters.isEmpty()) {
            return new ModuleProfile(0D, 0D, 0D, 0D, 0D, 0D);
        }
        final var mod = thrusters.get().getShipModule();
        final Double minimumMass = (Double) mod.getAttributeValue(HorizonsModifier.ENGINE_MINIMUM_MASS, true);
        final Double optimalMass = (Double) mod.getAttributeValue(HorizonsModifier.ENGINE_OPTIMAL_MASS, true);
        final Double maximumMass = (Double) mod.getAttributeValue(HorizonsModifier.MAXIMUM_MASS, true);
        return new ModuleProfile(minimumMass, optimalMass, maximumMass, minMult, optMult, maxMult);
    }

    // ── Internal multiplier retrieval ──────────────────────────────────────────────

    private static Double getMaximumMultiplier(Optional<Slot> thrusters) {
        return thrusters
                .map(Slot::getShipModule)
                .flatMap(shipModule -> Optional.ofNullable((Double) shipModule.getAttributeValueOrDefault(HorizonsModifier.MAXIMUM_MULTIPLIER_SPEED, null, true)))
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.MAXIMUM_MULIPLIER, true)).orElse(0D));
    }

    private static Double getOptimalMultiplier(Optional<Slot> thrusters) {
        return thrusters
                .map(Slot::getShipModule)
                .flatMap(shipModule -> Optional.ofNullable((Double) shipModule.getAttributeValueOrDefault(HorizonsModifier.OPTIMAL_MULTIPLIER_SPEED, null, true)))
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.OPTIMAL_MULTIPLIER, true)).orElse(0D));
    }

    private static Double getMinimumMultiplier(Optional<Slot> thrusters) {
        return thrusters
                .map(Slot::getShipModule)
                .flatMap(shipModule -> Optional.ofNullable((Double) shipModule.getAttributeValueOrDefault(HorizonsModifier.MINIMUM_MULTIPLIER_SPEED, null, true)))
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.MINIMUM_MULTIPLIER, true)).orElse(0D));
    }

    private static Double getMaximumAccelerationMultiplier(Optional<Slot> thrusters) {
        return thrusters
                .map(Slot::getShipModule)
                .flatMap(shipModule -> Optional.ofNullable((Double) shipModule.getAttributeValueOrDefault(HorizonsModifier.MAXIMUM_MULTIPLIER_ACCELERATION, null, true)))
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.MAXIMUM_MULIPLIER, true)).orElse(0D));
    }

    private static Double getOptimalAccelerationMultiplier(Optional<Slot> thrusters) {
        return thrusters
                .map(Slot::getShipModule)
                .flatMap(shipModule -> Optional.ofNullable((Double) shipModule.getAttributeValueOrDefault(HorizonsModifier.OPTIMAL_MULTIPLIER_ACCELERATION, null, true)))
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.OPTIMAL_MULTIPLIER, true)).orElse(0D));
    }

    private static Double getMinimumAccelerationMultiplier(Optional<Slot> thrusters) {
        return thrusters
                .map(Slot::getShipModule)
                .flatMap(shipModule -> Optional.ofNullable((Double) shipModule.getAttributeValueOrDefault(HorizonsModifier.MINIMUM_MULTIPLIER_ACCELERATION, null, true)))
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.MINIMUM_MULTIPLIER, true)).orElse(0D));
    }

    private static Double getMaximumMultiplierRotation(Optional<Slot> thrusters) {
        return thrusters.map(Slot::getShipModule)
                .flatMap(shipModule -> {
                    if (shipModule.getAttibutes().contains(HorizonsModifier.MAXIMUM_MULTIPLIER_ROTATION)) {
                        final Double rotation = (Double) shipModule.getAttributeValue(HorizonsModifier.MAXIMUM_MULTIPLIER_ROTATION, true);
                        return Optional.of(rotation);
                    }
                    return Optional.empty();
                })
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.MAXIMUM_MULIPLIER, true)).orElse(0D));
    }

    private static Double getOptimalMultiplierRotation(Optional<Slot> thrusters) {
        return thrusters.map(Slot::getShipModule)
                .flatMap(shipModule -> {
                    if (shipModule.getAttibutes().contains(HorizonsModifier.OPTIMAL_MULTIPLIER_ROTATION)) {
                        final Double rotation = (Double) shipModule.getAttributeValue(HorizonsModifier.OPTIMAL_MULTIPLIER_ROTATION, true);
                        return Optional.of(rotation);
                    }
                    return Optional.empty();
                })
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.OPTIMAL_MULTIPLIER, true)).orElse(0D));
    }

    private static Double getMinimumMultiplierRotation(Optional<Slot> thrusters) {
        return thrusters.map(Slot::getShipModule)
                .flatMap(shipModule -> {
                    if (shipModule.getAttibutes().contains(HorizonsModifier.MINIMUM_MULTIPLIER_ROTATION)) {
                        final Double rotation = (Double) shipModule.getAttributeValue(HorizonsModifier.MINIMUM_MULTIPLIER_ROTATION, true);
                        return Optional.of(rotation);
                    }
                    return Optional.empty();
                })
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.MINIMUM_MULTIPLIER, true)).orElse(0D));
    }

    private static Double getMaximumBoostedMultiplier(Optional<Slot> thrusters) {
        return thrusters.map(Slot::getShipModule)
                .map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.MAXIMUM_BOOSTED_MULTIPLIER, true))
                .orElse(0D);
    }

    private static Double getOptimalBoostedMultiplier(Optional<Slot> thrusters) {
        return thrusters.map(Slot::getShipModule)
                .map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.OPTIMAL_BOOSTED_MULTIPLIER, true))
                .orElse(0D);
    }

    private static Double getMinimumBoostedMultiplier(Optional<Slot> thrusters) {
        return thrusters.map(Slot::getShipModule)
                .map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.MINIMUM_BOOSTED_MULTIPLIER, true))
                .orElse(0D);
    }
}
