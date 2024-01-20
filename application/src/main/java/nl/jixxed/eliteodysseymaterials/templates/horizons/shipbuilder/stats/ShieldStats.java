package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.ShieldGenerator;
import nl.jixxed.eliteodysseymaterials.domain.ships.utility.ShieldBooster;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

import java.util.concurrent.atomic.AtomicReference;

import static nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier.*;

@Slf4j
public class ShieldStats extends Stats implements Template {
    private DestroyableLabel resistanceKinetic;
    private DestroyableLabel resistanceThermal;
    private DestroyableLabel resistanceExplosive;
    private DestroyableLabel resistanceCaustic;
    private DestroyableLabel strengthRaw;
    private DestroyableLabel strengthKinetic;
    private DestroyableLabel strengthThermal;
    private DestroyableLabel strengthExplosive;
    private DestroyableLabel strengthCaustic;

    public ShieldStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getChildren().add(BoxBuilder.builder().withNodes(new GrowingRegion(), createTitle("ship.stats.shield"), new GrowingRegion()).buildHBox());
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));

        this.resistanceKinetic = createValueLabel(String.format("%.2f", calculateResistanceKinetic()));
        this.resistanceThermal = createValueLabel(String.format("%.2f", calculateResistanceThermal()));
        this.resistanceExplosive = createValueLabel(String.format("%.2f", calculateResistanceExplosive()));
        this.resistanceCaustic = createValueLabel(String.format("%.2f", calculateResistanceCaustic()));
        this.strengthRaw = createValueLabel(String.format("%.2f", calculateStrengthRaw()));
        this.strengthKinetic = createValueLabel(String.format("%.2f", calculateStrengthKinetic()));
        this.strengthThermal = createValueLabel(String.format("%.2f", calculateStrengthThermal()));
        this.strengthExplosive = createValueLabel(String.format("%.2f", calculateStrengthExplosive()));
        this.strengthCaustic = createValueLabel(String.format("%.2f", calculateStrengthCaustic()));
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.shield.resistancekinetic"), new GrowingRegion(), this.resistanceKinetic).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.shield.resistancethermal"), new GrowingRegion(), this.resistanceThermal).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.shield.resistanceexplosive"), new GrowingRegion(), this.resistanceExplosive).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.shield.resistancecaustic"), new GrowingRegion(), this.resistanceCaustic).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.shield.strengthraw"), new GrowingRegion(), this.strengthRaw).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.shield.strengthkinetic"), new GrowingRegion(), this.strengthKinetic).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.shield.strengththermal"), new GrowingRegion(), this.strengthThermal).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.shield.strengthexplosive"), new GrowingRegion(), this.strengthExplosive).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.shield.strengthcaustic"), new GrowingRegion(), this.strengthCaustic).buildHBox());

    }

    @Override
    public void initEventHandling() {
        eventListeners.add(EventService.addListener(this, ShipConfigEvent.class, event -> update()));
    }

    public double calculateResistanceKinetic() {
        return calculateResistance(KINETIC_RESISTANCE);
    }

    public double calculateResistanceThermal() {
        return calculateResistance(THERMAL_RESISTANCE);
    }

    public double calculateResistanceExplosive() {
        return calculateResistance(EXPLOSIVE_RESISTANCE);
    }

    public double calculateResistanceCaustic() {
        return calculateResistance(CAUSTIC_RESISTANCE);
    }

    public double calculateStrengthRaw() {
        return rawShieldStrength();
    }

    public double calculateStrengthKinetic() {
        return calculateCurrentStrength(KINETIC_RESISTANCE);
    }

    public double calculateStrengthThermal() {
        return calculateCurrentStrength(THERMAL_RESISTANCE);
    }

    public double calculateStrengthExplosive() {
        return calculateCurrentStrength(EXPLOSIVE_RESISTANCE);
    }

    public double calculateStrengthCaustic() {
        return calculateCurrentStrength(CAUSTIC_RESISTANCE);
    }

    private double calculateCurrentStrength(HorizonsModifier horizonsModifier) {
        final double multiplier = ApplicationState.getInstance().getSystemPips() / 8.0;
        double absoluteShieldResistance = 0.60 * Math.pow(multiplier, 0.85);
        return switch (horizonsModifier){
            case KINETIC_RESISTANCE -> rawShieldStrength() / (1 - absoluteShieldResistance) / (1 - calculateResistanceKinetic() / 100);
            case THERMAL_RESISTANCE -> rawShieldStrength() / (1 - absoluteShieldResistance) / (1 - calculateResistanceThermal() / 100);
            case EXPLOSIVE_RESISTANCE -> rawShieldStrength() / (1 - absoluteShieldResistance) / (1 - calculateResistanceExplosive() / 100);
            case CAUSTIC_RESISTANCE -> rawShieldStrength() / (1 - absoluteShieldResistance) / (1 - calculateResistanceCaustic() / 100);
            default -> rawShieldStrength() / (1 - absoluteShieldResistance);
        };
    }

    private double getEffectiveShieldBoostMultiplier(Double shieldbst) {
        return 1 + shieldbst;
    }

    private double rawShieldStrength() {
        return getShip()
                .map(ship -> ship.getOptionalSlots().stream()
                        .filter(slot -> slot.getShipModule() instanceof ShieldGenerator)
                        .findFirst()
                        .map(shieldGeneratorSlot -> {
                            ShipModule module = shieldGeneratorSlot.getShipModule();
                            Double minimumMass = (Double) module.getAttributeValue(SHIELDGEN_MINIMUM_MASS);
                            Double optimalMass = (Double) module.getAttributeValue(SHIELDGEN_OPTIMAL_MASS);
                            Double maximumMass = (Double) module.getAttributeValue(SHIELDGEN_MAXIMUM_MASS);
                            Double minimumStrength = (Double) module.getAttributeValue(SHIELDGEN_MINIMUM_STRENGTH);
                            Double optimalStrength = (Double) module.getAttributeValue(SHIELDGEN_OPTIMAL_STRENGTH);
                            Double maximumStrength = (Double) module.getAttributeValue(SHIELDGEN_MAXIMUM_STRENGTH);
                            double shields = (double) ship.getAttributes().getOrDefault(SHIELDS, 0D);
                            double totalShieldBoost = ship.getUtilitySlots().stream()
                                    .filter(slot -> slot.getShipModule() instanceof ShieldBooster)
                                    .mapToDouble(slot -> (Double) slot.getShipModule().getAttributeValue(SHIELD_BOOST))
                                    .sum();
                            return shields
                                    * getEffectiveShieldBoostMultiplier(totalShieldBoost)
                                    * getMassCurveMultiplier((double)ship.getAttributes().getOrDefault(MASS,0D), new ModuleProfile(minimumMass, optimalMass, maximumMass, minimumStrength, optimalStrength, maximumStrength));
                        }).orElse(0D))
                .orElse(0D);
    }

    private double calculateResistance(HorizonsModifier horizonsModifier) {
        return getShip().map(ship -> ship.getOptionalSlots().stream()
                        .filter(slot -> slot.getShipModule() instanceof ShieldGenerator)
                        .findFirst()
                        .map(shieldGeneratorSlot -> {
                            AtomicReference<Double> totalModuleMultiplier = new AtomicReference<>(1D);
                            ship.getUtilitySlots().stream()
                                    .filter(slot ->
                                            slot.getShipModule() instanceof ShieldBooster
                                    )
                                    .forEach(slot -> {
                                                double moduleResistance = (double) slot.getShipModule().getAttributeValue(horizonsModifier);
                                                double multiplier = 1D - moduleResistance;
                                                totalModuleMultiplier.updateAndGet(v -> v * multiplier);
                                            }
                                    );
                            double shieldResistance = (double) shieldGeneratorSlot.getShipModule().getAttributeValue(horizonsModifier);
                            return getEffectiveDamageResistance((1 - totalModuleMultiplier.get()), shieldResistance);
                        })
                        .orElse(0D))
                .orElse(0D) * 100D;
    }

    public double getEffectiveDamageResistance(double extraResistance, double exemptResistance) {
        double low = 0.30D;
        double high = 0.65D;
        double expected = 1D - (1D - extraResistance);
        double penalized = low + (expected - low) / (1D - low) * (high - low);
        var actual = (penalized >= 0.30) ? penalized : expected;
        return 1 - (1 - exemptResistance) * (1 - actual);
    }

    @Override
    protected void update() {
        log.debug("update armour: " + this.getShip().isPresent());
        this.getShip().ifPresent(ship1 -> log.debug("type: " + ship1.getShipType()));
        this.resistanceKinetic.setText(String.format("%.2f", calculateResistanceKinetic()));
        this.resistanceThermal.setText(String.format("%.2f", calculateResistanceThermal()));
        this.resistanceExplosive.setText(String.format("%.2f", calculateResistanceExplosive()));
        this.resistanceCaustic.setText(String.format("%.2f", calculateResistanceCaustic()));
        this.strengthRaw.setText(String.format("%.2f", calculateStrengthRaw()));
        this.strengthKinetic.setText(String.format("%.2f", calculateStrengthKinetic()));
        this.strengthThermal.setText(String.format("%.2f", calculateStrengthThermal()));
        this.strengthExplosive.setText(String.format("%.2f", calculateStrengthExplosive()));
        this.strengthCaustic.setText(String.format("%.2f", calculateStrengthCaustic()));
    }
}
