package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

@Slf4j
public class WeaponStats extends Stats implements Template {
    private DestroyableLabel rawDamage;
    private DestroyableLabel absolutePercentage;
    private DestroyableLabel kineticPercentage;
    private DestroyableLabel thermalPercentage;
    private DestroyableLabel explosivePercentage;
    private DestroyableLabel causticPercentage;
    private DestroyableLabel currentDuration;
    private DestroyableLabel maxDuration;
    private DestroyableLabel maxAmmoDuration;
    private DestroyableLabel currentSustainedDamagePercentage;
    private DestroyableLabel maxSustainedDamagePercentage;

    public WeaponStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getChildren().add(BoxBuilder.builder().withNodes(new GrowingRegion(), createTitle("ship.stats.weapon"), new GrowingRegion()).buildHBox());
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        this.rawDamage = createValueLabel(String.format("%.2f", calculateRawDamage()));
        this.absolutePercentage = createValueLabel(String.format("%.2f", calculateAbsolutePercentage()));
        this.kineticPercentage = createValueLabel(String.format("%.2f", calculateKineticPercentage()));
        this.thermalPercentage = createValueLabel(String.format("%.2f", calculateThermalPercentage()));
        this.explosivePercentage = createValueLabel(String.format("%.2f", calculateExplosivePercentage()));
        this.causticPercentage = createValueLabel(String.format("%.2f", calculateCausticPercentage()));
        this.currentDuration = createValueLabel(String.format("%.2f", calculateCurrentDuration()));
        this.maxDuration = createValueLabel(String.format("%.2f", calculateMaxDuration()));
        this.maxAmmoDuration = createValueLabel(String.format("%.2f", calculateMaxAmmoDuration()));
        this.currentSustainedDamagePercentage = createValueLabel(String.format("%.2f", calculateCurrentSustainedDamagePercentage()));
        this.maxSustainedDamagePercentage = createValueLabel(String.format("%.2f", calculateMaxSustainedDamagePercentage()));

        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.weapon.rawdamage"), new GrowingRegion(), this.rawDamage).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.weapon.absolutepercentage"), new GrowingRegion(), this.absolutePercentage).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.weapon.kineticpercentage"), new GrowingRegion(), this.kineticPercentage).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.weapon.thermalpercentage"), new GrowingRegion(), this.thermalPercentage).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.weapon.explosivepercentage"), new GrowingRegion(), this.explosivePercentage).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.weapon.causticpercentage"), new GrowingRegion(), this.causticPercentage).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.weapon.currentduration"), new GrowingRegion(), this.currentDuration).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.weapon.maxduration"), new GrowingRegion(), this.maxDuration).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.weapon.maxammoduration"), new GrowingRegion(), this.maxAmmoDuration).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.weapon.currentsustaineddamagepercentage"), new GrowingRegion(), this.currentSustainedDamagePercentage).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.weapon.maxsustaineddamagepercentage"), new GrowingRegion(), this.maxSustainedDamagePercentage).buildHBox());

    }
    @Override
    public void initEventHandling() {
        eventListeners.add(EventService.addListener(this, ShipConfigEvent.class, event -> update()));
    }

//    // derived Weapon stats
//    var slot = this.getSlot('component', CORE_ABBR_SLOT.PD);
//    var wepcap = slot.getEffectiveAttrValue('wepcap');
//    var wepchg = slot.getEffectiveAttrValue('wepchg');
//    var powerdist_wep = this.getEffectivePowerDist('wep');
//    var powerdistWepMul = pow(powerdist_wep / MAX_POWER_DIST, 1.1);
//			weapons.sort(sortObjKeyAsc);
//    var eps = 0;
//    var seps = 0;
//			for (var i = 0;  i < weapons.length;  i++) {
//        eps += weapons[i].eps;
//        seps += weapons[i].seps;
//    }
//    var eps_cur = eps;
//    var eps_max = eps;
//    stats.wepcap_burst_cur = (wepcap / max(0, eps_cur - wepchg * powerdistWepMul));
//    stats.wepcap_burst_max = (wepcap / max(0, eps_max - wepchg));
//			for (var i = 0;  i < weapons.length;  i++) {
//        if (stats.wepcap_burst_cur >= weapons[i].spc) {
//            eps_cur = eps_cur - weapons[i].eps + weapons[i].seps;
//            stats.wepcap_burst_cur = (wepcap / max(0, eps_cur - wepchg * powerdistWepMul));
//        }
//        if (stats.wepcap_burst_max >= weapons[i].spc) {
//            eps_max = eps_max - weapons[i].eps + weapons[i].seps;
//            stats.wepcap_burst_max = (wepcap / max(0, eps_max - wepchg));
//        }
//    }
//    stats.wepchg_sustain_cur = min(max(wepchg * powerdistWepMul / seps, 0), 1);
//    stats.wepchg_sustain_max = min(max(wepchg                   / seps, 0), 1);
    //---------------------------------------
//    var dps = current.fit.getStat('dps');
//    var dps_abs = current.fit.getStat('dps_abs');
//    var dps_thm = current.fit.getStat('dps_thm');
//    var dps_kin = current.fit.getStat('dps_kin');
//    var dps_exp = current.fit.getStat('dps_exp');
//    var dps_axe = current.fit.getStat('dps_axe');
//    var dps_cau = current.fit.getStat('dps_cau');
//    var dps_nodistdraw = current.fit.getStat('dps_nodistdraw');
//    var dps_distdraw = current.fit.getStat('dps_distdraw');
//    var ammotime_wepcap = current.fit.getStat('ammotime_wepcap');
//    var ammotime_nocap = current.fit.getStat('ammotime_nocap');
//    var wepcap_burst_cur = current.fit.getStat('wepcap_burst_cur');
//    var wepcap_burst_max = current.fit.getStat('wepcap_burst_max');
//    var wepchg_sustain_cur = current.fit.getStat('wepchg_sustain_cur');
//    var wepchg_sustain_max = current.fit.getStat('wepchg_sustain_max');
//
//    // compute derived stats
//    var curWpnSus = ((dps_nodistdraw + (dps_distdraw ? (dps_distdraw * wepchg_sustain_cur) : 0)) / dps);
//    var maxWpnSus = ((dps_nodistdraw + (dps_distdraw ? (dps_distdraw * wepchg_sustain_max) : 0)) / dps);
//    var ammWpnDur = min(ammotime_nocap, ((ammotime_wepcap <= wepcap_burst_max) ? ammotime_wepcap : (wepcap_burst_max + (ammotime_wepcap - wepcap_burst_max) / maxWpnSus)));
//
//    // update displays
//    var htmlNA = '<small class="semantic" edsy-text="n-a">N/A</small>';
//		document.getElementById('outfitting_stats_wpn_raw_burst').innerHTML = (dps ? formatAttrHTML('dps', dps, 1) : htmlNA);
//		document.getElementById('outfitting_stats_wpn_abs_burst').innerHTML = (dps ? formatPctHTML(dps_abs / dps, 0) : htmlNA);
//		document.getElementById('outfitting_stats_wpn_thm_burst').innerHTML = (dps ? formatPctHTML(dps_thm / dps, 0) : htmlNA);
//		document.getElementById('outfitting_stats_wpn_kin_burst').innerHTML = (dps ? formatPctHTML(dps_kin / dps, 0) : htmlNA);
//		document.getElementById('outfitting_stats_wpn_exp_burst').innerHTML = (dps ? formatPctHTML(dps_exp / dps, 0) : htmlNA);
//		document.getElementById('outfitting_stats_wpn_axe_burst').innerHTML = (dps ? formatPctHTML(dps_axe / dps, 0) : htmlNA);
//		document.getElementById('outfitting_stats_wpn_cur_dur').innerHTML = (dps ? formatTimeHTML(wepcap_burst_cur) : htmlNA);
//		document.getElementById('outfitting_stats_wpn_max_dur').innerHTML = (dps ? formatTimeHTML(wepcap_burst_max) : htmlNA);
//		document.getElementById('outfitting_stats_wpn_amm_dur').innerHTML = (dps ? formatTimeHTML(ammWpnDur) : htmlNA);
//		document.getElementById('outfitting_stats_wpn_cur_sus').innerHTML = (dps ? formatPctHTML(curWpnSus, 1) : htmlNA);
//		document.getElementById('outfitting_stats_wpn_max_sus').innerHTML = (dps ? formatPctHTML(maxWpnSus, 1) : htmlNA);
    private double calculateRawDamage() {
        return 0d;
    }

    private double calculateAbsolutePercentage() {
        return 0d;
    }

    private double calculateKineticPercentage() {
        return 0d;
    }

    private double calculateThermalPercentage() {
        return 0d;
    }

    private double calculateExplosivePercentage() {
        return 0d;
    }

    private double calculateCausticPercentage() {
        return 0d;
    }

    private double calculateCurrentDuration() {
        return 0d;
    }

    private double calculateMaxDuration() {
        return 0d;
    }

    private double calculateMaxAmmoDuration() {
        return 0d;
    }

    private double calculateCurrentSustainedDamagePercentage() {
        return 0d;
    }

    private double calculateMaxSustainedDamagePercentage() {
        return 0d;
    }

    @Override
    protected void update() {
        this.rawDamage.setText(String.format("%.2f", calculateRawDamage()));
        this.absolutePercentage.setText(String.format("%.2f", calculateAbsolutePercentage()));
        this.kineticPercentage.setText(String.format("%.2f", calculateKineticPercentage()));
        this.thermalPercentage.setText(String.format("%.2f", calculateThermalPercentage()));
        this.explosivePercentage.setText(String.format("%.2f", calculateExplosivePercentage()));
        this.causticPercentage.setText(String.format("%.2f", calculateCausticPercentage()));
        this.currentDuration.setText(String.format("%.2f", calculateCurrentDuration()));
        this.maxDuration.setText(String.format("%.2f", calculateMaxDuration()));
        this.maxAmmoDuration.setText(String.format("%.2f", calculateMaxAmmoDuration()));
        this.currentSustainedDamagePercentage.setText(String.format("%.2f", calculateCurrentSustainedDamagePercentage()));
        this.maxSustainedDamagePercentage.setText(String.format("%.2f", calculateMaxSustainedDamagePercentage()));
    }
}
