package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.FrameShiftDriveBooster;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableSeparator;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

@Slf4j
public class JumpStats extends Stats implements DestroyableTemplate {
    private RangeIndicator jumpRangeIndicator;
    private RangeIndicator totalJumpRangeIndicator;

    public JumpStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(new GrowingRegion(), createTitle("ship.stats.jumprange"), new GrowingRegion())
                .buildHBox());
        this.getNodes().add(new DestroyableSeparator(Orientation.HORIZONTAL));
        jumpRangeIndicator = new RangeIndicator(calculateJumpRangeMin(), calculateJumpRangeCurrent(), calculateJumpRangeMax(), "ship.stats.jumprange", "ship.stats.jumprange.value");
        totalJumpRangeIndicator = new RangeIndicator(calculateTotalJumpRangeMin(), calculateTotalJumpRangeCurrent(), calculateTotalJumpRangeMax(), "ship.stats.totaljumprange", "ship.stats.totaljumprange.value");
        this.getNodes().addAll(jumpRangeIndicator, totalJumpRangeIndicator);

    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, ShipConfigEvent.class, event -> update()));
    }


    public double calculateJumpRangeMin() {
        //added ship.getMaxFuelReserve() compared to ingame value
        return getShip().map(ship -> calculateJumpRange(ship.getEmptyMass() + ship.getMaxFuelReserve() + ship.getMaxCargo() + ship.getMaxPassenger() + ship.getMaxFuel(), ship.getMaxFuel())).orElse(0.0D);
    }

    public double calculateJumpRangeCurrent() {
        return getShip().map(ship -> calculateJumpRange(ship.getEmptyMass() + ship.getCurrentCargo() + ship.getCurrentFuel() + ship.getCurrentFuelReserve(), ship.getCurrentFuel())).orElse(0.0D);
    }

    public double calculateJumpRangeMax() {
        return getShip().map(ship -> {
            final double fuel = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D)).orElse(1D);
            return calculateJumpRange(ship.getEmptyMass() + fuel, fuel);
        }).orElse(0.0D);
    }

    public double calculateJumpRangeMaxFueled() {
        return getShip().map(ship -> calculateJumpRange(ship.getEmptyMass() + ship.getMaxFuel(), ship.getMaxFuel())).orElse(0.0D);
    }

    //    var getJumpDistance = function(mass, fuel, fsdOpt, fsdMul, fsdExp, jmpBst) {
//        // https://forums.frontier.co.uk/threads/mass-effect-on-hyperspace-range.32734/#post-643461
//        return pow(fuel / fsdMul, 1 / fsdExp) * fsdOpt / mass + jmpBst;
//    }; // getJumpDistance()
//
//
//    var getJumpRange = function(fuelcap, mass, fuel, fsdOpt, fsdMul, fsdExp, jmpBst) {
//        var range = 0;
//        while (fuelcap > fuel) {
//            range += getJumpDistance(mass, fuel, fsdOpt, fsdMul, fsdExp, jmpBst);
//            fuelcap -= fuel;
//            mass -= fuel;
//        }
//        return range + getJumpDistance(mass, fuelcap, fsdOpt, fsdMul, fsdExp, jmpBst);
//    }; // getJumpRange()
    public double calculateTotalJumpRangeMin() {
        //added ship.getMaxFuelReserve() compared to ingame value
        return 0d;
        //return getShip().map(ship -> calculateJumpRange(ship.getEmptyMass() + ship.getMaxFuelReserve() + ship.getMaxCargo()+ ship.getMaxPassenger() + ship.getMaxFuel(), ship.getMaxFuel())).orElse(0.0D);
    }

    public double calculateTotalJumpRangeCurrent() {
        return getShip().map(ship -> {
            var range = 0d;
            final double fuelCap = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D)).orElse(1D);
            double fuel = ship.getCurrentFuel();
            while (fuel > fuelCap) {
                range += calculateJumpRange(ship.getEmptyMass() + ship.getCurrentCargo() + fuel + ship.getCurrentFuelReserve(), fuel);
                fuel -= fuelCap;
            }
            return range + calculateJumpRange(ship.getEmptyMass() + ship.getCurrentCargo() + fuel + ship.getCurrentFuelReserve(), fuel);
        }).orElse(0.0D);

        //return getShip().map(ship -> calculateJumpRange(ship.getEmptyMass() + ship.getCurrentCargo() + ship.getCurrentFuel() + ship.getCurrentFuelReserve(), ship.getCurrentFuel())).orElse(0.0D);
    }

    public double calculateTotalJumpRangeMax() {
        return getShip().map(ship -> {
            var range = 0d;
            final double fuelCap = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D)).orElse(1D);
            double fuel = ship.getMaxFuel();
            while (fuel > fuelCap) {
                range += calculateJumpRange(ship.getEmptyMass() + fuel, fuel);
                fuel -= fuelCap;
            }
            return range + calculateJumpRange(ship.getEmptyMass() + fuel, fuel);
        }).orElse(0.0D);
    }

    public double calculateJumpRange(final double mass, final double fuel) {
        return getShip().map(ship -> {
            final double maxFuelPerJump = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D)).orElse(1D);
            final double optimisedMass = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FSD_OPTIMISED_MASS, 1D)).orElse(1D);
            final double fuelMultiplier = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FUEL_MULTIPLIER, 1D)).orElse(1D);
            final double fuelPower = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FUEL_POWER, 1D)).orElse(1D);
            final double jumpRangeIncrease = ship.getOptionalSlots().stream().filter(slot -> slot.getShipModule() instanceof FrameShiftDriveBooster).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.JUMP_RANGE_INCREASE, 1D)).orElse(0D);
            return calculateJumpDistance(mass, Math.min(fuel, maxFuelPerJump), optimisedMass, fuelMultiplier, fuelPower, jumpRangeIncrease);
        }).orElse(0.0D);
    }

    private double calculateJumpDistance(final double mass, final double fuel, final double optimisedMass, final double fuelMultiplier, final double fuelPower, final double jumpRangeIncrease) {
        if (fuel <= 0D) {
            return 0D;
        }
        return Math.pow(fuel / fuelMultiplier, 1 / fuelPower) * optimisedMass / mass + jumpRangeIncrease;
    }


    @Override
    protected void update() {
        this.jumpRangeIndicator.updateValues(calculateJumpRangeMin(), calculateJumpRangeCurrent(), calculateJumpRangeMax());
        this.totalJumpRangeIndicator.updateValues(calculateTotalJumpRangeMin(), calculateTotalJumpRangeCurrent(), calculateTotalJumpRangeMax());


    }
}
