package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.FrameShiftDriveBooster;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

@Slf4j
public class JumpStats extends Stats implements Template {
    private DestroyableLabel minJumpRange;
    private DestroyableLabel maxJumpRange;
    private DestroyableLabel maxFueledJumpRange;
    private DestroyableLabel currentJumpRange;

    public JumpStats() {
        super();
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getChildren().add(BoxBuilder.builder().withNodes(new GrowingRegion(), createTitle("ship.stats.jumprange"), new GrowingRegion()).buildHBox());
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        this.minJumpRange = createValueLabel(String.format("%.2f", calculateJumpRangeMin()));
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.jumprange.min"), new GrowingRegion(),
                this.minJumpRange).buildHBox());
        this.currentJumpRange = createValueLabel(String.format("%.2f", calculateJumpRangeCurrent()));
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.jumprange.current"), new GrowingRegion(), this.currentJumpRange).buildHBox());

        this.maxFueledJumpRange = createValueLabel(String.format("%.2f", calculateJumpRangeMaxFueled()));
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.jumprange.maxfueled"), new GrowingRegion(), this.maxFueledJumpRange).buildHBox());

        this.maxJumpRange = createValueLabel(String.format("%.2f", calculateJumpRangeMax()));
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.jumprange.max"), new GrowingRegion(), this.maxJumpRange).buildHBox());
    }


    public double calculateJumpRangeMin() {
        return getShip().map(ship -> calculateJumpRange(ship.getEmptyMass() + ship.getMaxCargo() + ship.getMaxFuel(), ship.getMaxFuel())).orElse(0.0D);
    }

    public double calculateJumpRangeCurrent() {
        return getShip().map(ship -> calculateJumpRange(ship.getEmptyMass() + ship.getCurrentCargo() + ship.getCurrentFuel() + ship.getFuelReserve(), ship.getCurrentFuel())).orElse(0.0D);
    }

    public double calculateJumpRangeMax() {
        return getShip().map(ship -> {
            final double fuel = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D)).orElse(1D);
            return calculateJumpRange(ship.getEmptyMass() + fuel, fuel);
        }).orElse(0.0D);
    }

    public double calculateJumpRangeMaxFueled() {
        return getShip().map(ship -> calculateJumpRange(ship.getEmptyMass() + ship.getMaxFuel(), ship.getMaxFuel())).orElse(0.0D);
    }

    public double calculateJumpRange(final double mass, final double fuel) {
        return getShip().map(ship -> {
            final double maxFuelPerJump = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D)).orElse(1D);
            final double optimisedMass = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FSD_OPTIMISED_MASS, 1D)).orElse(1D);
            final double fuelMultiplier = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FUEL_MULTIPLIER, 1D)).orElse(1D);
            final double fuelPower = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FUEL_POWER, 1D)).orElse(1D);
            final double jumpRangeIncrease = ship.getOptionalSlots().stream().filter(slot -> slot.getShipModule() instanceof FrameShiftDriveBooster).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.JUMP_RANGE_INCREASE, 1D)).orElse(0D);
            return calculateJumpDistance(mass, Math.min(fuel, maxFuelPerJump), optimisedMass, fuelMultiplier, fuelPower, jumpRangeIncrease);
        }).orElse(0.0D);
    }

    private double calculateJumpDistance(final double mass, final double fuel, final double optimisedMass, final double fuelMultiplier, final double fuelPower, final double jumpRangeIncrease) {
        return Math.pow(fuel / fuelMultiplier, 1 / fuelPower) * optimisedMass / mass + jumpRangeIncrease;
    }

    @Override
    protected void update() {
        log.debug("update ranges: " + this.getShip().isPresent());
        this.getShip().ifPresent(ship1 ->  log.debug("type: " + ship1.getShipType()));
        this.minJumpRange.setText(String.format("%.2f", calculateJumpRangeMin()));
        this.currentJumpRange.setText(String.format("%.2f", calculateJumpRangeCurrent()));
        this.maxFueledJumpRange.setText(String.format("%.2f", calculateJumpRangeMaxFueled()));
        this.maxJumpRange.setText(String.format("%.2f", calculateJumpRangeMax()));
    }
}
