package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.military.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTooltip;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class ArmourStats extends Stats implements DestroyableTemplate {
    private Shield armourResistance;
    private Shield armourIntegrity;
    private DestroyableLabel mrpIntegrity;
    private DestroyableLabel mrpProtection;

    public ArmourStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("armour-stats");
        addTitle("ship.stats.armour");
        armourResistance = new Shield("%", "blue", "armour");
        armourIntegrity = new Shield("\u2795", "red", "armour");

        final DestroyableVBox shields = BoxBuilder.builder()
                .withNodes(armourResistance, armourIntegrity)
                .buildVBox();
        mrpIntegrity = LabelBuilder.builder()
                .withStyleClass("armour-value")
                .withText("ship.stats.armour.mrp.integrity.unit", 0)
                .build();
        mrpProtection = LabelBuilder.builder()
                .withStyleClass("armour-value")
                .withText("ship.stats.armour.mrp.protection.unit", 0)
                .build();
        final DestroyableLabel mrpLabel = LabelBuilder.builder()
                .withText("ship.stats.armour.mrp")
                .build();
        final DestroyableTooltip tooltip = TooltipBuilder.builder()
                .withShowDelay(Duration.ZERO)
                .withText("ship.stats.armour.mrp")
                .build();
        tooltip.install(mrpLabel);
        var integrityLine = BoxBuilder.builder()
                .withNodes(
                        LabelBuilder.builder()
                                .withText("ship.stats.armour.mrp.integrity")
                                .build(),
                        new GrowingRegion(),
                        mrpIntegrity)
                .buildHBox();
        var protectionLine = BoxBuilder.builder()
                .withNodes(
                        LabelBuilder.builder()
                                .withText("ship.stats.armour.mrp.protection")
                                .build(),
                        new GrowingRegion(),
                        mrpProtection)
                .buildHBox();
        this.getNodes().addAll(shields, new GrowingRegion(), mrpLabel, integrityLine, protectionLine);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, ShipConfigEvent.class, _ -> update()));
    }

    public double getEffectiveDamageResistance(double baseResistance, double extraResistance, double bestResistance) {
        // https://forums.frontier.co.uk/threads/kinetic-resistance-calculation.266235/post-4230114
        // https://forums.frontier.co.uk/threads/shield-booster-mod-calculator.286097/post-4998592

        double low = Math.max(Math.max(0.30D, baseResistance), bestResistance);
        double high = 0.65D;
        double expected = 1D - ((1D - baseResistance) * (1D - extraResistance));
        double penalized = low + (expected - low) / (1D - low) * (high - low);
        return (penalized >= 0.30D) ? penalized : expected;
    }

    public double calculateResistanceRaw() {
        return (double) getShip().map(ship -> ship.getAttributes().getOrDefault(HorizonsModifier.ARMOUR_HARDNESS, 0D)).orElse(0D);
    }

    public double calculateResistanceKinetic() {
        return calculateResistance(HorizonsModifier.KINETIC_RESISTANCE);

    }

    private double calculateResistance(HorizonsModifier horizonsModifier) {
        return getShip().map(ship -> {
            final Optional<Slot> armourSlot = ship.getCoreSlots().stream().filter(slot -> SlotType.CORE_ARMOUR.equals(slot.getSlotType())).findFirst().filter(Slot::isOccupied);
            AtomicReference<Double> totalModuleMultiplier = new AtomicReference<>(1D);
            AtomicReference<Double> minimumMultiplier = new AtomicReference<>(1D);
            ship.getOptionalSlots().stream()
                    .filter(slot -> slot.getShipModule() instanceof HullReinforcementPackage
                            || slot.getShipModule() instanceof GuardianHullReinforcementPackage
                            || slot.getShipModule() instanceof MetaAlloyHullReinforcementPackage)
                    .forEach(slot -> {
                                double moduleResistance = (double) slot.getShipModule().getAttributeValue(horizonsModifier, true);
                                double multiplier = 1D - moduleResistance;
                                totalModuleMultiplier.updateAndGet(v -> v * multiplier);
                                minimumMultiplier.updateAndGet(v -> Math.min(v, multiplier));
                            }
                    );

            double armourResistance = (double) armourSlot.map(slot -> slot.getShipModule()).map(sm -> sm.getAttributeValue(horizonsModifier, true)).orElse(0D);
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

    public double calculateIntegrityRaw() {
        return getShip().map(ship -> {
            final double shipArmour = (double) ship.getAttributes().getOrDefault(HorizonsModifier.ARMOUR, 0D);
            final Optional<ShipModule> armour = ship.getCoreSlots().stream().filter(slot -> SlotType.CORE_ARMOUR.equals(slot.getSlotType())).findFirst().map(Slot::getShipModule);
            double hullBoost = (double) armour.map(shipModule -> shipModule.getAttributeValue(HorizonsModifier.HULL_BOOST, true)).orElse(0D);
            hullBoost += ship.getOptionalSlots().stream()
                    .filter(slot -> slot.getShipModule() instanceof HullReinforcementPackage)
                    .mapToDouble(slot -> (double) slot.getShipModule().getAttributeValue(HorizonsModifier.HULL_BOOST, true))
                    .sum();
            double hullReinforcement = ship.getOptionalSlots().stream()
                    .filter(slot -> slot.getShipModule() instanceof HullReinforcementPackage
                            || slot.getShipModule() instanceof GuardianHullReinforcementPackage
                            || slot.getShipModule() instanceof MetaAlloyHullReinforcementPackage)
                    .mapToDouble(slot -> (double) slot.getShipModule().getAttributeValue(HorizonsModifier.HULL_REINFORCEMENT, true))
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

    private double calculateMRPProtection() {
        double protection = getShip().map(ship ->
                ship.getOptionalSlots().stream()
                        .filter(slot -> slot.getShipModule() instanceof ModuleReinforcementPackage || slot.getShipModule() instanceof GuardianModuleReinforcementPackage)
                        .map(slot -> (double) slot.getShipModule().getAttributeValue(HorizonsModifier.MODULE_DEFENCE_ABSORPTION, true))
                        .reduce(1D, (a, b) -> a *= 1 - b)
        ).orElse(1D);
        return (1 - protection) * 100;
    }

    private double calculateMRPIntegrity() {
        return getShip().map(ship ->
                ship.getOptionalSlots().stream()
                        .filter(slot -> slot.getShipModule() instanceof ModuleReinforcementPackage || slot.getShipModule() instanceof GuardianModuleReinforcementPackage)
                        .map(slot -> (double) slot.getShipModule().getAttributeValue(HorizonsModifier.INTEGRITY, true))
                        .reduce(0D, (a, b) -> a += b)
        ).orElse(0D);
    }

    @Override
    protected void update() {
        armourResistance.updateValues(calculateResistanceRaw(), calculateResistanceKinetic(), calculateResistanceThermal(), calculateResistanceCaustic(), calculateResistanceExplosive());
        armourIntegrity.updateValues(calculateIntegrityRaw(), calculateIntegrityKinetic(), calculateIntegrityThermal(), calculateIntegrityCaustic(), calculateIntegrityExplosive());
        mrpIntegrity.addBinding(mrpIntegrity.textProperty(), LocaleService.getStringBinding("ship.stats.armour.mrp.integrity.unit", Formatters.NUMBER_FORMAT_0.format(calculateMRPIntegrity())));
        mrpProtection.addBinding(mrpProtection.textProperty(), LocaleService.getStringBinding("ship.stats.armour.mrp.protection.unit", Formatters.NUMBER_FORMAT_0.format(calculateMRPProtection())));
    }

}
