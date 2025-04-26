package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.StackPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.ShieldGenerator;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.military.GuardianShieldReinforcementPackage;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.military.ShieldCellBank;
import nl.jixxed.eliteodysseymaterials.domain.ships.utility.ShieldBooster;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier.*;

@Slf4j
public class ShieldStats extends Stats implements DestroyableEventTemplate {
    private Shield shieldResistance;
    private Shield shieldIntegrity;
    private DestroyableLabel regenTime;
    private DestroyableLabel rebuildTime;
    private DestroyableLabel scbRestoration;
    private DestroyableLabel scbRestorationLabel;

    public ShieldStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(new GrowingRegion(), createTitle("ship.stats.shield"), new GrowingRegion())
                .buildHBox());
        this.getNodes().add(new DestroyableSeparator(Orientation.HORIZONTAL));
        shieldResistance = new Shield("RES", "%", "blue");
        shieldIntegrity = new Shield("STR", "\u2795", "red");
        final DestroyableVBox shields = BoxBuilder.builder()
                .withNodes(shieldResistance, shieldIntegrity).buildVBox();
        regenTime = LabelBuilder.builder()
                .withStyleClass("ship-stats-shield-label")
                .withText("ship.stats.shield.build.unit", 0)
                .build();
        rebuildTime = LabelBuilder.builder()
                .withStyleClass("ship-stats-shield-label")
                .withText("ship.stats.shield.build.unit", 0)
                .build();
        final DestroyableLabel regen = LabelBuilder.builder()
                .withText("ship.stats.shield.regen")
                .build();
        final DestroyableLabel rebuild = LabelBuilder.builder()
                .withText("ship.stats.shield.rebuild")
                .build();
        final DestroyableTooltip regenTooltip = TooltipBuilder.builder()
                .withShowDelay(Duration.ZERO)
                .withText("ship.stats.shield.regen.tooltip")
                .build();
        regenTooltip.install(regen);
        final DestroyableTooltip rebuildTooltip = TooltipBuilder.builder()
                .withShowDelay(Duration.ZERO)
                .withText("ship.stats.shield.rebuild.tooltip")
                .build();
        rebuildTooltip.install(rebuild);
        scbRestoration = LabelBuilder.builder()
                .withStyleClass("ship-stats-shield-label")
                .withText("ship.stats.armour.scb.restoration.unit", 0, 0)
                .withVisibility(false)
                .build();
        scbRestorationLabel = LabelBuilder.builder()
                .withText("ship.stats.armour.scb.restoration")
                .withVisibility(false)
                .build();

        final DestroyableVBox rebuildBox = BoxBuilder.builder()
                .withNodes(new GrowingRegion(),
                        BoxBuilder.builder()
                                .withNodes(new GrowingRegion(), scbRestoration)
                                .buildHBox(),
                        BoxBuilder.builder()
                                .withNodes(new GrowingRegion(), scbRestorationLabel)
                                .buildHBox(),
                        BoxBuilder.builder()
                                .withNodes(regenTime, new GrowingRegion(), rebuildTime)
                                .buildHBox(),
                        BoxBuilder.builder()
                                .withNodes(regen, new GrowingRegion(), rebuild)
                                .buildHBox()
                ).buildVBox();
        final DestroyableStackPane stackPane = StackPaneBuilder.builder().withNodes(shields, rebuildBox).build();
        stackPane.getStyleClass().add("shield-stats-stackpane");
        this.getNodes().addAll(stackPane);

    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, ShipConfigEvent.class, event -> update()));
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
        return switch (horizonsModifier) {
            case KINETIC_RESISTANCE ->
                    rawShieldStrength() / (1 - absoluteShieldResistance) / (1 - calculateResistanceKinetic() / 100);
            case THERMAL_RESISTANCE ->
                    rawShieldStrength() / (1 - absoluteShieldResistance) / (1 - calculateResistanceThermal() / 100);
            case EXPLOSIVE_RESISTANCE ->
                    rawShieldStrength() / (1 - absoluteShieldResistance) / (1 - calculateResistanceExplosive() / 100);
            case CAUSTIC_RESISTANCE ->
                    rawShieldStrength() / (1 - absoluteShieldResistance) / (1 - calculateResistanceCaustic() / 100);
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
                            double shieldReinforcement = ship.getOptionalSlots().stream()
                                    .filter(slot -> slot.getShipModule() instanceof GuardianShieldReinforcementPackage)
                                    .mapToDouble(slot -> (Double) slot.getShipModule().getAttributeValue(SHIELD_REINFORCEMENT))
                                    .sum();
                            double totalShieldBoost = ship.getUtilitySlots().stream()
                                    .filter(slot -> slot.getShipModule() instanceof ShieldBooster)
                                    .mapToDouble(slot -> (Double) slot.getShipModule().getAttributeValue(SHIELD_BOOST))
                                    .sum();
                            return shieldReinforcement + (shields
                                    * getEffectiveShieldBoostMultiplier(totalShieldBoost)
                                    * getMassCurveMultiplier((double) ship.getAttributes().getOrDefault(MASS, 0D), new ModuleProfile(minimumMass, optimalMass, maximumMass, minimumStrength, optimalStrength, maximumStrength)));
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
        shieldResistance.updateValues(0D, calculateResistanceKinetic(), calculateResistanceThermal(), calculateResistanceCaustic(), calculateResistanceExplosive());
        shieldIntegrity.updateValues(calculateStrengthRaw(), calculateStrengthKinetic(), calculateStrengthThermal(), calculateStrengthCaustic(), calculateStrengthExplosive());
        regenTime.addBinding(regenTime.textProperty(), LocaleService.getStringBinding("ship.stats.shield.build.unit", Formatters.NUMBER_FORMAT_0.format(calculateRegenTime())));
        rebuildTime.addBinding(rebuildTime.textProperty(), LocaleService.getStringBinding("ship.stats.shield.build.unit", Formatters.NUMBER_FORMAT_0.format(calculateRebuildTime())));
        final Scb scb = calculateSCB();
        if (scb.amount > 0D) {
            scbRestoration.setVisible(true);
            scbRestorationLabel.setVisible(true);
            scbRestoration.addBinding(scbRestoration.textProperty(), LocaleService.getStringBinding("ship.stats.armour.scb.restoration.unit", Formatters.NUMBER_FORMAT_1.format(scb.charges), Formatters.NUMBER_FORMAT_1.format(scb.amount)));
        } else {
            scbRestoration.setVisible(false);
            scbRestorationLabel.setVisible(false);
        }
    }

    private Double calculateRegenTime() {
        return getShip().map(ship -> ship.getOptionalSlots().stream()
                .filter(optSlot -> optSlot.getShipModule() instanceof ShieldGenerator)
                .findFirst()
                .map(sgSlot -> {
                    final Optional<Slot> powerDistributor = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_POWER_DISTRIBUTION)).findFirst().filter(Slot::isOccupied);
                    final double systemRecharge = (double) powerDistributor.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(SYSTEMS_RECHARGE)).orElse(0D);
                    final double multiplier = Math.pow(ApplicationState.getInstance().getSystemPips() / 8.0, 1.1);
                    final Double regenRate = (Double) sgSlot.getShipModule().getAttributeValue(REGEN_RATE);
                    final Double energyPerRegen = (Double) sgSlot.getShipModule().getAttributeValue(ENERGY_PER_REGEN);
                    final double rawShieldStrength = rawShieldStrength();
                    final Double fastTime = 0D;
                    final Double slowTime = (rawShieldStrength / 2) / Math.min(regenRate, (systemRecharge * multiplier) / energyPerRegen);
                    return fastTime + slowTime;
                }).orElse(0D)).orElse(0D);
    }

    private Double calculateRebuildTime() {
        return getShip().map(ship -> ship.getOptionalSlots().stream()
                .filter(optSlot -> optSlot.getShipModule() instanceof ShieldGenerator)
                .findFirst()
                .map(sgSlot -> {
                    final Optional<Slot> powerDistributor = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_POWER_DISTRIBUTION)).findFirst().filter(Slot::isOccupied);
                    final double systemCapacity = (double) powerDistributor.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(SYSTEMS_CAPACITY)).orElse(0D);
                    final double systemRecharge = (double) powerDistributor.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(SYSTEMS_RECHARGE)).orElse(0D);
                    final double multiplier = Math.pow(ApplicationState.getInstance().getSystemPips() / 8.0, 1.1);
                    final Double brokenRegenRate = (Double) sgSlot.getShipModule().getAttributeValue(BROKEN_REGEN_RATE);
                    final Double energyPerRegen = (Double) sgSlot.getShipModule().getAttributeValue(ENERGY_PER_REGEN);
                    final double rawShieldStrength = rawShieldStrength();
                    final Double fastTime = Math.min((rawShieldStrength / 2) / brokenRegenRate, (systemCapacity / Math.max(0, brokenRegenRate * energyPerRegen - systemRecharge * multiplier)));
                    final Double slowTime = ((rawShieldStrength / 2) - brokenRegenRate * fastTime) / Math.min(brokenRegenRate, (systemRecharge * multiplier) / energyPerRegen);
                    return 16D + fastTime + slowTime;
                }).orElse(0D)).orElse(0D);
    }

    private Scb calculateSCB() {
        return getShip().map(ship -> {
                    double charges = ship.getOptionalSlots().stream()
                            .filter(slot -> slot.getShipModule() instanceof ShieldCellBank)
                            .map(slot -> (double) slot.getShipModule().getAttributeValue(HorizonsModifier.AMMO_CLIP_SIZE) + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.AMMO_MAXIMUM))
                            .mapToDouble(Double::doubleValue)
                            .average()
                            .orElse(0D);
                    double amount = ship.getOptionalSlots().stream()
                            .filter(slot -> slot.getShipModule() instanceof ShieldCellBank)
                            .map(slot -> (double) slot.getShipModule().getAttributeValue(HorizonsModifier.SHIELDBANK_DURATION) * (double) slot.getShipModule().getAttributeValue(HorizonsModifier.SHIELDBANK_REINFORCEMENT))
                            .mapToDouble(Double::doubleValue)
                            .sum();
                    return new Scb(charges, amount / rawShieldStrength() * 100D);
                }
        ).orElse(new Scb(0D, 0D));
    }

    record Scb(double charges, double amount) {
    }

}
