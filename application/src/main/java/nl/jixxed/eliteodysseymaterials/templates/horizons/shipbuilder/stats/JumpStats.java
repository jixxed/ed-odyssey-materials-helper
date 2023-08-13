package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.FrameShiftDriveBooster;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipBuilderEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

@Slf4j
public class JumpStats extends Stats implements Template {
    private DestroyableLabel minJumpRange;
    private DestroyableLabel maxJumpRange;
    private DestroyableLabel maxFueledJumpRange;
    private DestroyableLabel currentJumpRange;

    public JumpStats(final Ship ship) {
        super(ship);
        initComponents();
        initEventHandling();
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
        return calculateJumpRange(this.ship.getEmptyMass() + this.ship.getMaxCargo() + this.ship.getMaxFuel(), this.ship.getMaxFuel());
    }

    public double calculateJumpRangeCurrent() {
        return calculateJumpRange(this.ship.getEmptyMass() + this.ship.getCurrentCargo() + this.ship.getCurrentFuel() + this.ship.getFuelReserve(), this.ship.getCurrentFuel());
    }

    public double calculateJumpRangeMax() {
        final double fuel = this.ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP,1D)).orElse(1D);
        return calculateJumpRange(this.ship.getEmptyMass() + fuel, fuel);
    }

    public double calculateJumpRangeMaxFueled() {
        return calculateJumpRange(this.ship.getEmptyMass() + this.ship.getMaxFuel(), this.ship.getMaxFuel());
    }

    public double calculateJumpRange(final double mass, final double fuel) {
        final double maxFuelPerJump = this.ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP,1D)).orElse(1D);
        final double optimisedMass = this.ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.OPTIMISED_MASS,1D)).orElse(1D);
        final double fuelMultiplier = this.ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FUEL_MULTIPLIER,1D)).orElse(1D);
        final double fuelPower = this.ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FUEL_POWER,1D)).orElse(1D);
        final double jumpRangeIncrease = this.ship.getOptionalSlots().stream().filter(slot -> slot.getShipModule() instanceof FrameShiftDriveBooster).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.JUMP_RANGE_INCREASE,1D)).orElse(0D);
        return calculateJumpDistance(mass, Math.min(fuel, maxFuelPerJump), optimisedMass, fuelMultiplier, fuelPower, jumpRangeIncrease);
    }

    private double calculateJumpDistance(final double mass, final double fuel, final double optimisedMass, final double fuelMultiplier, final double fuelPower, final double jumpRangeIncrease) {
        return Math.pow(fuel / fuelMultiplier, 1 / fuelPower) * optimisedMass / mass + jumpRangeIncrease;
    }

    @Override
    public void initEventHandling() {
        this.eventListeners.add(EventService.addListener(this, ShipBuilderEvent.class, event -> update()));
    }

    private void update() {
        log.debug("update ranges");
        this.minJumpRange.setText(String.format("%.2f", calculateJumpRangeMin()));
        this.currentJumpRange.setText(String.format("%.2f", calculateJumpRangeCurrent()));
        this.maxFueledJumpRange.setText(String.format("%.2f", calculateJumpRangeMaxFueled()));
        this.maxJumpRange.setText(String.format("%.2f", calculateJumpRangeMax()));
    }
}
