package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.FrameShiftDriveBooster;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.Template;

public class JumpStats extends Stats implements Template {
    private final Ship ship;

    public JumpStats(final Ship ship) {
        super();
        this.ship = ship;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getChildren().add(LabelBuilder.builder().withText(LocaleService.getStringBinding("ship.stats.jumprange")).build());
        this.getChildren().add(LabelBuilder.builder().withNonLocalizedText(String.format("##.##", calculateJumpRange())).build());
    }
//    { attr:'jumpbst',    fdattr:'FSDJumpRangeBoost',      abbr:'JBst'

//    { attr:'fsdoptmass', fdattr:'FSDOptimalMass',         abbr:'OMas', name:'Optimised Mass'

    // derived FSD stats
//    var slot = this.getSlot('component', CORE_ABBR_SLOT.FD);
//    var optmass = slot.getEffectiveAttrValue('fsdoptmass');
//    var maxfuel = slot.getEffectiveAttrValue('maxfuel');
//    var fuelmul = slot.getEffectiveAttrValue('fuelmul');
//    var fuelpower = slot.getEffectiveAttrValue('fuelpower');
//    stats._jump_laden    = getJumpDistance(            stats.mass + stats.fuelcap + stats.cargocap, min(stats.fuelcap, maxfuel), optmass, fuelmul, fuelpower, stats.jumpbst);
//    stats._jump_unladen  = getJumpDistance(            stats.mass + stats.fuelcap                 , min(stats.fuelcap, maxfuel), optmass, fuelmul, fuelpower, stats.jumpbst);
//    stats._jump_max      = getJumpDistance(            stats.mass + min(stats.fuelcap, maxfuel)   , min(stats.fuelcap, maxfuel), optmass, fuelmul, fuelpower, stats.jumpbst);
//    stats._range_laden   = getJumpRange(stats.fuelcap, stats.mass + stats.fuelcap + stats.cargocap, min(stats.fuelcap, maxfuel), optmass, fuelmul, fuelpower, stats.jumpbst);
//    stats._range_unladen = getJumpRange(stats.fuelcap, stats.mass + stats.fuelcap                 , min(stats.fuelcap, maxfuel), optmass, fuelmul, fuelpower, stats.jumpbst);
    private double calculateJumpRange() {
        // https://forums.frontier.co.uk/threads/mass-effect-on-hyperspace-range.32734/#post-643461
        double fuelcap = this.ship.getMaxFuel();
        double mass = this.ship.getCurrentCargo() + this.ship.getCurrentFuel();
        final double fuel = this.ship.getCurrentFuel();
        final double fsdOpt = this.ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double)slot.getShipModule().getAttributes().get(HorizonsModifier.OPTIMISED_MASS)).orElse(0D);
        final double fsdMul = this.ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double)slot.getShipModule().getAttributes().get(HorizonsModifier.FUEL_MULTIPLIER)).orElse(0D);
        final double fsdExp =  this.ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double)slot.getShipModule().getAttributes().get(HorizonsModifier.FUEL_POWER)).orElse(0D);
        final double jmpBst = this.ship.getOptionalSlots().stream().filter(slot -> slot.getShipModule() instanceof FrameShiftDriveBooster).findFirst().map(slot -> (double)slot.getShipModule().getAttributes().get(HorizonsModifier.JUMP_RANGE_INCREASE)).orElse(0D);
        var range = 0;
        while (fuelcap > fuel) {
            range += calculateJumpDistance(mass, fuel, fsdOpt, fsdMul, fsdExp, jmpBst);
            fuelcap -= fuel;
            mass -= fuel;
        }
        return range + calculateJumpDistance(mass, fuelcap, fsdOpt, fsdMul, fsdExp, jmpBst);
    }

    private double calculateJumpDistance(final double mass, final double fuel, final double fsdOpt, final double fsdMul, final double fsdExp, final double jmpBst) {
        return Math.pow(fuel / fsdMul, 1 / fsdExp) * fsdOpt / mass + jmpBst;
    }

    @Override
    public void initEventHandling() {

    }
}
