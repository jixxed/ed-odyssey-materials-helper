package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.military.GuardianHullReinforcementPackage;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.military.HullReinforcementPackage;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.military.MetaAlloyHullReinforcementPackage;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class ArmourStats extends Stats implements Template {
    private DestroyableLabel resistanceRaw;
    private DestroyableLabel resistanceKinetic;
    private DestroyableLabel resistanceThermal;
    private DestroyableLabel resistanceExplosive;
    private DestroyableLabel resistanceCaustic;
    private DestroyableLabel integrityRaw;
    private DestroyableLabel integrityKinetic;
    private DestroyableLabel integrityThermal;
    private DestroyableLabel integrityExplosive;
    private DestroyableLabel integrityCaustic;

    public ArmourStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getChildren().add(BoxBuilder.builder().withNodes(new GrowingRegion(), createTitle("ship.stats.armour"), new GrowingRegion()).buildHBox());
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));

        this.resistanceRaw = createValueLabel(String.format("%.2f", calculateResistanceRaw()));
        this.resistanceKinetic = createValueLabel(String.format("%.2f", calculateResistanceKinetic()));
        this.resistanceThermal = createValueLabel(String.format("%.2f", calculateResistanceThermal()));
        this.resistanceExplosive = createValueLabel(String.format("%.2f", calculateResistanceExplosive()));
        this.resistanceCaustic = createValueLabel(String.format("%.2f", calculateResistanceCaustic()));
        this.integrityRaw = createValueLabel(String.format("%.2f", calculateIntegrityRaw()));
        this.integrityKinetic = createValueLabel(String.format("%.2f", calculateIntegrityKinetic()));
        this.integrityThermal = createValueLabel(String.format("%.2f", calculateIntegrityThermal()));
        this.integrityExplosive = createValueLabel(String.format("%.2f", calculateIntegrityExplosive()));
        this.integrityCaustic = createValueLabel(String.format("%.2f", calculateIntegrityCaustic()));
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.armour.resistanceraw"), new GrowingRegion(), this.resistanceRaw).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.armour.resistancekinetic"), new GrowingRegion(), this.resistanceKinetic).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.armour.resistancethermal"), new GrowingRegion(), this.resistanceThermal).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.armour.resistanceexplosive"), new GrowingRegion(), this.resistanceExplosive).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.armour.resistancecaustic"), new GrowingRegion(), this.resistanceCaustic).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.armour.integrityraw"), new GrowingRegion(), this.integrityRaw).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.armour.integritykinetic"), new GrowingRegion(), this.integrityKinetic).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.armour.integritythermal"), new GrowingRegion(), this.integrityThermal).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.armour.integrityexplosive"), new GrowingRegion(), this.integrityExplosive).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.armour.integritycaustic"), new GrowingRegion(), this.integrityCaustic).buildHBox());
    }

    @Override//todo if required?
    public void initEventHandling() {
        eventListeners.add(EventService.addListener(this, ShipConfigEvent.class, event -> update()));
    }

    // get primary stats
//    var integ_imrp = current.fit.getStat('integ_imrp');
//    var dmgprot = current.fit.getStat('dmgprot');
//    var lmprepcap_max = current.fit.getStat('lmprepcap_max');
//    var cargocap = current.fit.getStat('cargocap');
//
//    var slot = current.fit.getSlot('ship', 'hull');
//    var hardness = slot.getEffectiveAttrValue('hardness');
//
//    // get or compute derived stats
//    var mrpArmRes = (1 - dmgprot) * 100;
//    var kinArmRes = current.fit.getStat('_akinres')
//    var thmArmRes = current.fit.getStat('_athmres');
//    var expArmRes = current.fit.getStat('_aexpres')
//    var cauArmRes = current.fit.getStat('_acaures')
//    var rawArmInt = current.fit.getStat('_armour')
//    var kinArmInt = (rawArmInt / (1 - kinArmRes / 100));
//    var thmArmInt = (rawArmInt / (1 - thmArmRes / 100));
//    var expArmInt = (rawArmInt / (1 - expArmRes / 100));
//    var cauArmInt = (rawArmInt / (1 - cauArmRes / 100));
//    var rawArmRep = lmprepcap_max * cargocap;
//    var kinArmRep = (rawArmRep / (1 - kinArmRes / 100));
//    var thmArmRep = (rawArmRep / (1 - thmArmRes / 100));
//    var expArmRep = (rawArmRep / (1 - expArmRes / 100));
//    var cauArmRep = (rawArmRep / (1 - cauArmRes / 100));
//
//    // update displays
//    var htmlNA = '<small class="semantic" edsy-text="n-a">N/A</small>';
//		document.getElementById('outfitting_stats_raw_armour_hardness').innerHTML = formatNumHTML(hardness, 0);
//		document.getElementById('outfitting_stats_mod_armour_protect').innerHTML = (integ_imrp ? formatAttrHTML('dmgprot', mrpArmRes) : htmlNA);
//		document.getElementById('outfitting_stats_kin_armour_resist').innerHTML = formatAttrHTML('kinres', kinArmRes);
//		document.getElementById('outfitting_stats_thm_armour_resist').innerHTML = formatAttrHTML('thmres', thmArmRes);
//		document.getElementById('outfitting_stats_exp_armour_resist').innerHTML = formatAttrHTML('expres', expArmRes);
//		document.getElementById('outfitting_stats_cau_armour_resist').innerHTML = formatAttrHTML('caures', cauArmRes);
//		document.getElementById('outfitting_stats_raw_armour_integ').innerHTML = formatNumHTML(rawArmInt, 1);
//		document.getElementById('outfitting_stats_mod_armour_integ').innerHTML = (integ_imrp ? formatNumHTML(integ_imrp, 1) : htmlNA);
//		document.getElementById('outfitting_stats_kin_armour_integ').innerHTML = formatNumHTML(kinArmInt, 1);
//		document.getElementById('outfitting_stats_thm_armour_integ').innerHTML = formatNumHTML(thmArmInt, 1);
//		document.getElementById('outfitting_stats_exp_armour_integ').innerHTML = formatNumHTML(expArmInt, 1);
//		document.getElementById('outfitting_stats_cau_armour_integ').innerHTML = formatNumHTML(cauArmInt, 1);
//		document.getElementById('outfitting_stats_raw_armour_repair').innerHTML = (rawArmRep ? formatNumHTML(rawArmRep, 1) : htmlNA);
//		document.getElementById('outfitting_stats_kin_armour_repair').innerHTML = (rawArmRep ? formatNumHTML(kinArmRep, 1) : htmlNA);
//		document.getElementById('outfitting_stats_thm_armour_repair').innerHTML = (rawArmRep ? formatNumHTML(thmArmRep, 1) : htmlNA);
//		document.getElementById('outfitting_stats_exp_armour_repair').innerHTML = (rawArmRep ? formatNumHTML(expArmRep, 1) : htmlNA);
//		document.getElementById('outfitting_stats_cau_armour_repair').innerHTML = (rawArmRep ? formatNumHTML(cauArmRep, 1) : htmlNA);
//}; // updateUIStatsArm()
    public double getEffectiveDamageResistance(double baseResistance, double extraResistance, double bestResistance) {
        // https://forums.frontier.co.uk/threads/kinetic-resistance-calculation.266235/post-4230114
        // https://forums.frontier.co.uk/threads/shield-booster-mod-calculator.286097/post-4998592
		/* old
		var threshold = 30;
		var rawres = 1 - ((1 - baseres / 100) * (1 - extrares / 100));
		var maxres = 1 - ((1 - baseres / 100) * (1 - threshold / 100));
		return 100 * (rawres - max(0, pow((rawres - maxres) / 2, curve || 1)));
		*/
        double low = Math.max(Math.max(0.30D, baseResistance), bestResistance);
        double high = 0.65D;
        double expected = 1D - ((1D - baseResistance) * (1D - extraResistance));
        double penalized = low + (expected - low) / (1D - low) * (high - low);
        return (penalized >= 0.30D) ? penalized : expected;
    }

    ; // getEffectiveDamageResistance()

    public double calculateResistanceRaw() {
        return (double) getShip().map(ship -> ship.getAttributes().getOrDefault(HorizonsModifier.ARMOUR_HARDNESS, 0D)).orElse(0D);
    }

    public double calculateResistanceKinetic() {
        return calculateResistance(HorizonsModifier.KINETIC_RESISTANCE);

    }

    private double calculateResistance(HorizonsModifier horizonsModifier) {
        return getShip().map(ship -> {
            final Optional<Slot> armourSlot = ship.getCoreSlots().stream().filter(slot -> SlotType.CORE_ARMOUR.equals(slot.getSlotType())).findFirst();
            AtomicReference<Double> totalModuleMultiplier = new AtomicReference<>(1D);
            AtomicReference<Double> minimumMultiplier = new AtomicReference<>(1D);
            ship.getOptionalSlots().stream()
                    .filter(slot -> slot.getShipModule() instanceof HullReinforcementPackage
                            || slot.getShipModule() instanceof GuardianHullReinforcementPackage
                            || slot.getShipModule() instanceof MetaAlloyHullReinforcementPackage)
                    .forEach(slot -> {
                                double moduleResistance = (double) slot.getShipModule().getAttributeValue(horizonsModifier);
                                double multiplier = 1D - moduleResistance;
                                totalModuleMultiplier.updateAndGet(v -> v * multiplier);
                                minimumMultiplier.updateAndGet(v -> Math.min(v, multiplier));
                            }
                    );

            double armourResistance = (double) armourSlot.map(slot -> slot.getShipModule().getAttributeValue(horizonsModifier)).orElse(0D);
            return getEffectiveDamageResistance(armourResistance, (1 - totalModuleMultiplier.get()), (1 - minimumMultiplier.get()));
        }).orElse(0D) * 100D;
    }

    public double calculateResistanceThermal() {
        return calculateResistance(HorizonsModifier.THERMAL_RESISTANCE);
    }

    public double calculateResistanceExplosive() {
        return calculateResistance(HorizonsModifier.EXPLOSIVE_RESISTANCE);
    }

    public double calculateResistanceCaustic() {
        return calculateResistance(HorizonsModifier.CAUSTIC_RESISTANCE);
    }

    //    var armour = slot_hull.getEffectiveAttrValue('armour');
//    stats.hullbst += slot.getEffectiveAttrValue('hullbst');
//    stats.hullrnf += slot.getEffectiveAttrValue('hullrnf');
//    stats._armour = ((armour * (1 + stats.hullbst / 100)) + stats.hullrnf);
//    var rawArmInt = current.fit.getStat('_armour')
//    var kinArmInt = (rawArmInt / (1 - kinArmRes / 100));
//    var thmArmInt = (rawArmInt / (1 - thmArmRes / 100));
//    var expArmInt = (rawArmInt / (1 - expArmRes / 100));
//    var cauArmInt = (rawArmInt / (1 - cauArmRes / 100));
    public double calculateIntegrityRaw() {
        return getShip().map(ship -> {
            final double shipArmour = (double) ship.getAttributes().getOrDefault(HorizonsModifier.ARMOUR, 0D);
            final Optional<ShipModule> armour = ship.getCoreSlots().stream().filter(slot -> SlotType.CORE_ARMOUR.equals(slot.getSlotType())).findFirst().map(Slot::getShipModule);
            double hullBoost = (double) armour.map(shipModule -> shipModule.getAttributeValue(HorizonsModifier.HULL_BOOST)).orElse(0D);
            hullBoost += ship.getOptionalSlots().stream()
                    .filter(slot -> slot.getShipModule() instanceof HullReinforcementPackage)
                    .mapToDouble(slot -> (double) slot.getShipModule().getAttributeValue(HorizonsModifier.HULL_BOOST))
                    .sum();
            double hullReinforcement = ship.getOptionalSlots().stream()
                    .filter(slot -> slot.getShipModule() instanceof HullReinforcementPackage
                            || slot.getShipModule() instanceof GuardianHullReinforcementPackage
                            || slot.getShipModule() instanceof MetaAlloyHullReinforcementPackage)
                    .mapToDouble(slot -> (double) slot.getShipModule().getAttributeValue(HorizonsModifier.HULL_REINFORCEMENT))
                    .sum();
            return (shipArmour * (1D + hullBoost)) + hullReinforcement;
        }).orElse(0D);

    }

    public double calculateIntegrityKinetic() {
        return (calculateIntegrityRaw() / (1 - calculateResistanceKinetic() / 100));
    }

    public double calculateIntegrityThermal() {
        return (calculateIntegrityRaw() / (1 - calculateResistanceThermal() / 100));
    }

    public double calculateIntegrityExplosive() {
        return (calculateIntegrityRaw() / (1 - calculateResistanceExplosive() / 100));
    }

    public double calculateIntegrityCaustic() {
        return (calculateIntegrityRaw() / (1 - calculateResistanceCaustic() / 100));
    }

    @Override
    protected void update() {
        log.debug("update armour: " + this.getShip().isPresent());
        this.getShip().ifPresent(ship1 -> log.debug("type: " + ship1.getShipType()));
        this.resistanceRaw.setText(String.format("%.2f", calculateResistanceRaw()));
        this.resistanceKinetic.setText(String.format("%.2f", calculateResistanceKinetic()));
        this.resistanceThermal.setText(String.format("%.2f", calculateResistanceThermal()));
        this.resistanceExplosive.setText(String.format("%.2f", calculateResistanceExplosive()));
        this.resistanceCaustic.setText(String.format("%.2f", calculateResistanceCaustic()));
        this.integrityRaw.setText(String.format("%.2f", calculateIntegrityRaw()));
        this.integrityKinetic.setText(String.format("%.2f", calculateIntegrityKinetic()));
        this.integrityThermal.setText(String.format("%.2f", calculateIntegrityThermal()));
        this.integrityExplosive.setText(String.format("%.2f", calculateIntegrityExplosive()));
        this.integrityCaustic.setText(String.format("%.2f", calculateIntegrityCaustic()));
    }
}
