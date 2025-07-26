package nl.jixxed.eliteodysseymaterials.domain.ships;

import com.google.common.util.concurrent.AtomicDouble;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBiFunction;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.military.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.special.CargoHatch;
import nl.jixxed.eliteodysseymaterials.domain.ships.special.FuelTank;
import nl.jixxed.eliteodysseymaterials.domain.ships.utility.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.ships.PriceService;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.function.Predicate.not;

@Slf4j
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class ShipModule implements Serializable {

    private static final List<ShipModule> SHIP_MODULES = new ArrayList<>();

    public static final List<List<? extends ShipModule>> ALL_MODULES = List.of(
            //hardpoint
            AbrasionBlaster.ABRASION_BLASTERS,
            AXMissileRack.AX_MISSILE_RACKS,
            AXMultiCannon.AX_MULTI_CANNONS,
            BeamLaser.BEAM_LASERS,
            BurstLaser.BURST_LASERS,
            Cannon.CANNONS,
            EnzymeMissileRack.ENZYME_MISSILE_RACKS,
            FragmentCannon.FRAGMENT_CANNONS,
            GuardianGaussCannon.GUARDIAN_GAUSS_CANNONS,
            GuardianNaniteTorpedoPylon.GUARDIAN_NANITE_TORPEDO_PYLONS,
            GuardianPlasmaCharger.GUARDIAN_PLASMA_CHARGERS,
            GuardianShardCannon.GUARDIAN_SHARD_CANNONS,
            MineLauncher.MINE_LAUNCHERS,
            MiningLaser.MINING_LASERS,
            MissileRack.MISSILE_RACKS,
            MultiCannon.MULTI_CANNONS,
            PlasmaAccelerator.PLASMA_ACCELERATORS,
            PulseLaser.PULSE_LASERS,
            RailGun.RAIL_GUNS,
            RemoteReleaseFlakLauncher.REMOTE_RELEASE_FLAK_LAUNCHERS,
            RemoteReleaseFlechetteLauncher.REMOTE_RELEASE_FLECHETTE_LAUNCHERS,
            SeismicChargeLauncher.SEISMIC_CHARGE_LAUNCHERS,
            ShockCannon.SHOCK_CANNONS,
            SubSurfaceDisplacementMissile.SUB_SURFACE_DISPLACEMENT_MISSILES,
            TorpedoPylon.TORPEDO_PYLONS,
            //core
            Armour.ARMOURS,
            FrameShiftDrive.FRAME_SHIFT_DRIVES,
            LifeSupport.LIFE_SUPPORTS,
            PowerDistributor.POWER_DISTRIBUTORS,
            PowerPlant.POWER_PLANTS,
            Sensors.SENSORS,
            Thrusters.THRUSTERS,
            FuelTank.FUEL_TANKS,
            CargoHatch.CARGO_HATCHES,
            //military
            GuardianHullReinforcementPackage.GUARDIAN_HULL_REINFORCEMENT_PACKAGES,
            GuardianModuleReinforcementPackage.GUARDIAN_MODULE_REINFORCEMENT_PACKAGES,
            GuardianShieldReinforcementPackage.GUARDIAN_SHIELD_REINFORCEMENT_PACKAGES,
            HullReinforcementPackage.HULL_REINFORCEMENT_PACKAGES,
            ModuleReinforcementPackage.MODULE_REINFORCEMENT_PACKAGES,
            ShieldCellBank.SHIELD_CELL_BANKS,
            //optional
            AntiCorrosionCargoRack.ANTI_CORROSION_CARGO_RACKS,
            AutoFieldMaintenanceUnit.AUTO_FIELD_MAINTENANCE_UNITS,
            CargoRack.CARGO_RACKS,
            LargeCargoRack.LARGE_CARGO_RACKS,
            CollectorLimpetController.COLLECTOR_LIMPET_CONTROLLERS,
            Computer.COMPUTERS,
            DecontaminationLimpetController.DECONTAMINATION_LIMPET_CONTROLLERS,
            DetailedSurfaceScanner.DETAILED_SURFACE_SCANNERS,
            ExperimentalWeaponStabiliser.EXPERIMENTAL_WEAPON_STABILISERS,
            FighterHangar.FIGHTER_HANGARS,
            FrameShiftDriveBooster.FRAME_SHIFT_DRIVE_BOOSTERS,
            FrameShiftDriveInterdictor.FRAME_SHIFT_DRIVE_INTERDICTORS,
            FuelScoop.FUEL_SCOOPS,
            FuelTransferLimpetController.FUEL_TRANSFER_LIMPET_CONTROLLERS,
            HatchBreakerLimpetController.HATCH_BREAKER_LIMPET_CONTROLLERS,
            MetaAlloyHullReinforcementPackage.META_ALLOY_HULL_REINFORCEMENT_PACKAGES,
            MultiLimpetController.MULTI_LIMPET_CONTROLLERS,
            PassengerCabin.PASSENGER_CABINS,
            PlanetaryVehicleHangar.PLANETARY_VEHICLE_HANGARS,
            ProspectorLimpetController.PROSPECTOR_LIMPET_CONTROLLERS,
            ReconLimpetController.RECON_LIMPET_CONTROLLERS,
            Refinery.REFINERIES,
            RepairLimpetController.REPAIR_LIMPET_CONTROLLERS,
            ResearchLimpetController.RESEARCH_LIMPET_CONTROLLERS,
            ShieldGenerator.SHIELD_GENERATORS,
            DiscoveryScanner.DISCOVERY_SCANNERS,
            //utility
            ChaffLauncher.CHAFF_LAUNCHERS,
            ElectronicCountermeasure.ELECTRONIC_COUNTERMEASURES,
            FrameShiftWakeScanner.FRAME_SHIFT_WAKE_SCANNERS,
            KillWarrantScanner.KILL_WARRANT_SCANNERS,
            ManifestScanner.MANIFEST_SCANNERS,
            PointDefence.POINT_DEFENCES,
            PulseWaveAnalyser.PULSE_WAVE_ANALYSERS,
            ShieldBooster.SHIELD_BOOSTERS,
            SinkLauncher.SINK_LAUNCHERS,
            Xeno.XENOS

    );
    @Getter
    @EqualsAndHashCode.Include
    private final String id;
    @Getter
    @EqualsAndHashCode.Include
    private final HorizonsBlueprintName name;
    private final Map<HorizonsModifier, Object> attributes = new HashMap<>();
    @Getter
    private final Map<HorizonsModifier, Object> modifiers = new HashMap<>();
    @Getter
    @EqualsAndHashCode.Include
    private final ModuleClass moduleClass;
    @Getter
    @EqualsAndHashCode.Include
    private final ModuleSize moduleSize;
    @Getter
    private final long basePrice;
    @Getter
    private final Origin origin;
    @Getter
    private final boolean multiCrew;
    @Getter
    private boolean legacy;
    @Getter
    private final String internalName;
    @Getter
    private final List<Modification> modifications = new ArrayList<>();
    @Getter
    private final List<HorizonsBlueprintType> experimentalEffects = new ArrayList<>();
    @Getter
    @Setter
    private Long buyPrice;
    @Getter
    private boolean powered = true;
    @Getter
    @Setter
    private String customName = "";

    private boolean powerToggle = true;
    @Setter
    private int powerGroup = 3;

    public int getPowerGroup() {
        return (!hasPowerToggle() && isPassivePower()) ? -1 : powerGroup;
    }

    public ShipModule(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final long basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        this(id, name, moduleSize, moduleClass, Origin.HUMAN, false, basePrice, internalName, attributes);
    }

    ShipModule(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final Origin origin, final long basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        this(id, name, moduleSize, moduleClass, origin, false, basePrice, internalName, attributes);
    }

    ShipModule(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final boolean multiCrew, final long basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        this(id, name, moduleSize, moduleClass, Origin.HUMAN, multiCrew, basePrice, internalName, attributes);
    }

    ShipModule(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final Origin origin, final boolean multiCrew, final long basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        this.id = id;
        this.name = name;
        this.moduleSize = moduleSize;
        this.moduleClass = moduleClass;
        this.origin = origin;
        this.multiCrew = multiCrew;
        this.basePrice = PriceService.getModulePriceOrDefault(internalName, basePrice);
        this.internalName = internalName;
        this.attributes.putAll(attributes);
        this.attributes.computeIfAbsent(HorizonsModifier.POWER_DRAW, modifier -> {
            this.powerToggle = false;
            return 0.0;
        });
        SHIP_MODULES.add(this);
    }

    public ShipModule(final ShipModule shipModule) {
        this.id = shipModule.id;
        this.name = shipModule.name;
        this.moduleSize = shipModule.moduleSize;
        this.moduleClass = shipModule.moduleClass;
        this.origin = shipModule.origin;
        this.multiCrew = shipModule.multiCrew;
        this.basePrice = PriceService.getModulePriceOrDefault(shipModule.internalName, shipModule.basePrice);
        this.internalName = shipModule.internalName;
        this.modifications.addAll(shipModule.modifications.stream().map(modification -> new Modification(modification.getModification(), modification.getModificationCompleteness().orElse(null), modification.getGrade())).toList());
        this.experimentalEffects.addAll(shipModule.experimentalEffects);
        this.attributes.putAll(shipModule.attributes);
        this.modifiers.putAll(shipModule.modifiers);
        this.legacy = shipModule.legacy;
        this.powerGroup = shipModule.powerGroup;
        this.powered = shipModule.powered;
        this.powerToggle = shipModule.powerToggle;
        this.buyPrice = shipModule.buyPrice;
    }

    public static List<ShipModule> getModules(final SlotType slotType) {
        return SHIP_MODULES.stream().filter(module -> {
            final Class<? extends ShipModule> aClass = module.getClass();
            return slotType.getModuleClasses().stream().anyMatch(moduleClass -> moduleClass.isAssignableFrom(aClass));
        }).toList();
    }

    public static List<ShipModule> getBasicModules() {
        return SHIP_MODULES.stream().filter(not(ShipModule::isPreEngineered).and(not(CargoHatch.class::isInstance))).toList();
    }

    public static ShipModule getModule(String id) {
        return SHIP_MODULES.stream().filter(module -> module.getId().equals(id)).findFirst().orElseThrow(IllegalArgumentException::new);
    }

    public void applyModification(final HorizonsBlueprintType modification, final HorizonsBlueprintGrade grade, final BigDecimal modificationCompleteness) {
        if (validModification(modification, false)) {
            final Modification mod = new Modification(modification, modificationCompleteness, grade);
            if (!this.modifications.contains(mod)) {
                this.modifications.clear();
                this.modifications.add(mod);
                this.modifiers.clear();
            }
        }
    }

    public void applyExperimentalEffect(final HorizonsBlueprintType experimentalEffect) {
        if (validModification(experimentalEffect, true) && !this.experimentalEffects.contains(experimentalEffect)) {
            this.experimentalEffects.clear();
            this.experimentalEffects.add(experimentalEffect);
            this.modifiers.clear();
        }
    }

    public void removeModification(final HorizonsBlueprintType modification) {
        this.modifications.removeIf(modification1 -> Objects.equals(modification1.getModification(), modification));
    }

    public void removeExperimentalEffect(final HorizonsBlueprintType experimentalEffect) {
        this.experimentalEffects.removeIf(experimentalEffect1 -> Objects.equals(experimentalEffect1, experimentalEffect));
    }

    private boolean validModification(final HorizonsBlueprintType modification, final boolean experimental) {
        if (modification == null) {
            return false;
        }
        if (experimental) {
            return getAllowedExperimentalEffects().contains(modification);
        }
        return getAllowedBlueprints().contains(modification);
    }

    public <T> T getAttributeValueOrDefault(final HorizonsModifier moduleAttribute, final T defaultValue) {
        try {
            return (T) getAttributeValue(moduleAttribute);
        } catch (final IllegalArgumentException e) {
            return defaultValue;
        }
    }

    public <T> T getAttributeValueOrDefault(final HorizonsModifier moduleAttribute, Double completeness, final T defaultValue) {
        try {
            return (T) getAttributeValue(moduleAttribute, completeness);
        } catch (final IllegalArgumentException e) {
            return defaultValue;
        }
    }

    public Object getOriginalAttributeValue(final HorizonsModifier moduleAttribute) {
        if (!this.attributes.containsKey(moduleAttribute)) {
            if (HorizonsModifier.DAMAGE_PER_SECOND.equals(moduleAttribute) && this.attributes.containsKey(HorizonsModifier.DAMAGE)) {
                return (double) this.attributes.get(HorizonsModifier.DAMAGE) * (double) this.attributes.getOrDefault(HorizonsModifier.RATE_OF_FIRE, 1.0) * (double) this.attributes.getOrDefault(HorizonsModifier.ROUNDS_PER_SHOT, 1.0);
            }
            throw new IllegalArgumentException("Unknown Module Attribute: " + moduleAttribute + " for module: " + this.name);
        }
        return this.attributes.get(moduleAttribute);
    }

    public Object getAttributeValue(final HorizonsModifier moduleAttribute) {
//        if (HorizonsModifier.DAMAGE_PER_SECOND.equals(moduleAttribute) && this.attributes.containsKey(HorizonsModifier.DAMAGE) && !this.attributes.containsKey(HorizonsModifier.DAMAGE_PER_SECOND)) {
//            return (double) getAttributeValue(HorizonsModifier.DAMAGE, null) * getAttributeValueOrDefault(HorizonsModifier.RATE_OF_FIRE, 1.0) * getAttributeValueOrDefault(HorizonsModifier.ROUNDS_PER_SHOT, 1.0);
//        }
        return getAttributeValue(moduleAttribute, null);
    }

    public BigDecimal getAttributeCompleteness(final HorizonsModifier moduleAttribute) {
        Object currentValue = this.modifiers.get(moduleAttribute);
        if (currentValue != null) {
            double minValue = (double) getAttributeValue(moduleAttribute, 0.0);
            double maxValue = (double) getAttributeValue(moduleAttribute, 1.0);
            if (maxValue == minValue) {
                return BigDecimal.ONE;
            }
            return BigDecimal.valueOf((Double.parseDouble(this.modifiers.get(moduleAttribute).toString()) - minValue) / (maxValue - minValue));
        } else {
            return modifications.stream().findFirst().map(modification -> {
                final HorizonsBlueprint moduleBlueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(this.name.getPrimary(), modifications.getFirst().getModification(), modifications.getFirst().getGrade());
                final var horizonsModifierValue = moduleBlueprint.getModifiers().get(moduleAttribute);
                if (horizonsModifierValue != null && horizonsModifierValue.isPositive()) {
                    return modifications.getFirst().getModificationCompleteness().orElse(BigDecimal.ZERO);
                } else {
                    return BigDecimal.ONE;
                }
            }).orElse(BigDecimal.ZERO);
        }
    }

    public Object getAttributeValue(final HorizonsModifier moduleAttribute, Double completeness) {
        if (!this.attributes.containsKey(moduleAttribute)) {
            if (HorizonsModifier.DAMAGE_PER_SECOND.equals(moduleAttribute) && this.attributes.containsKey(HorizonsModifier.DAMAGE)) {
                return (double) getAttributeValue(HorizonsModifier.DAMAGE, completeness) * getAttributeValueOrDefault(HorizonsModifier.RATE_OF_FIRE, completeness, 1.0) * getAttributeValueOrDefault(HorizonsModifier.ROUNDS_PER_SHOT, completeness, 1.0);
            }
            throw new IllegalArgumentException("Unknown Module Attribute: " + moduleAttribute + " for module: " + this.name);
        }
        if (modifiers.containsKey(moduleAttribute) && (completeness == null || isLegacy())) {
            if (modifiers.get(moduleAttribute) instanceof Boolean) {
                return modifiers.get(moduleAttribute);
            }
            return Double.parseDouble(modifiers.get(moduleAttribute).toString());
        }
        final Object baseAttributeValue = this.attributes.get(moduleAttribute);
        if (baseAttributeValue instanceof Double) {
            Double toReturn = Double.valueOf((double) baseAttributeValue);
            toReturn = (Double) applyModsToAttributeValue(moduleAttribute, toReturn, completeness);
            return toReturn;
        } else if (baseAttributeValue instanceof Boolean) {
            Boolean toReturn = Boolean.valueOf((boolean) baseAttributeValue);
            return applyModsToAttributeValue(moduleAttribute, toReturn, completeness);
        }
        return baseAttributeValue;
    }

    private Object applyModsToAttributeValue(HorizonsModifier moduleAttribute, Object attributeValue, Double completeness) {
        final AtomicReference<Object> value = new AtomicReference<>(attributeValue);
        final AtomicReference<HorizonsBiFunction> moduleModifier = new AtomicReference<>();
        final AtomicDouble modificationCompleteness = new AtomicDouble();
        final AtomicBoolean positive = new AtomicBoolean();
        this.modifications.forEach(modification -> {
            final HorizonsBlueprint moduleBlueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(this.name.getPrimary(), modification.getModification(), modification.getGrade());
            final HorizonsModifierValue horizonsModifierValue = moduleBlueprint.getModifiers().get(moduleAttribute);
            if (horizonsModifierValue != null) {
                final HorizonsBiFunction current = moduleModifier.get();
                if (current == null) {
                    positive.set(horizonsModifierValue.isPositive());
                    modificationCompleteness.set(completeness != null ? completeness : modification.getModificationCompleteness().orElse(BigDecimal.ZERO).doubleValue());
                    moduleModifier.set(horizonsModifierValue.getModifier());
                } else {
                    moduleModifier.set(current.stack(horizonsModifierValue.getModifier()));
                }
            }
        });
        if (moduleModifier.get() != null) {
            //if negative effect, apply fully
            try {
                value.set(moduleModifier.get().getFunction().apply(value.get(), positive.get() ? modificationCompleteness.get() : 1D));
            } catch (final Throwable t) {
                log.error("Error modifying value", t);
            }
        }
        if (!this.modifications.isEmpty()) {
            this.experimentalEffects.forEach(modification -> {
                final HorizonsBlueprint experimentalEffectBlueprint = HorizonsBlueprintConstants.getExperimentalEffects().get(this.name.getPrimary()).get(modification);
                final HorizonsModifierValue experimentalEffectModifier = experimentalEffectBlueprint.getModifiers().get(moduleAttribute);
                if (experimentalEffectModifier != null) {
//                value.set(experimentalEffectModifier.getModifiedValue(value.get(), 1D));
                    try {
                        value.set(experimentalEffectModifier.getModifier().getFunction().apply(value.get(), 1D));
                    } catch (final Throwable t) {
                        log.error("Error modifying value", t);
                    }
                }
            });
        }
        return value.get();
    }

    public abstract List<HorizonsBlueprintType> getAllowedBlueprints();

    public abstract List<HorizonsBlueprintType> getAllowedExperimentalEffects();

    public abstract ShipModule Clone();

    public String getLocalizationKey() {
        return "ships.module.name." + this.internalName.toLowerCase();
    }

    public String getClarifier() {
        return "";//isPreEngineered() ? " " + LocaleService.getLocalizedStringForCurrentLocale(getName().getLocalizationKey()) : "";
    }

    /**
     * returns the button row for the module. 0 means no grouping.
     * if 1 module of a type has a grouping, all modules of that type should have a grouping.
     *
     * @return number of the group
     */
    public int getGrouping() {
        return 0;
    }

    public boolean hasGrouping() {
        return getGrouping() > 0;
    }

    public String getNonSortingClarifier() {
        return "";
    }

    public boolean isAllowed(ShipType shipType) {
        return true;
    }

    public boolean isPreEngineered() {
        return false;
    }

    public boolean isStoreExclusive() {
        return false;
    }

    public boolean isAdvanced() {
        return false;
    }

    public boolean isEnhanced() {
        return false;
    }

    public boolean isDumbfire() {
        return false;
    }

    public boolean isSeeker() {
        return false;
    }

    public boolean isCGExclusive() {
        return false;
    }

    public int getModuleLimit() {
        return 999;
    }

    public void setLegacy(boolean legacy) {
        this.legacy = legacy;
    }

    public Set<HorizonsModifier> getAttibutes() {
        return attributes.keySet();
    }

    public void addModifier(HorizonsModifier horizonsModifier, Double value) {
        modifiers.put(horizonsModifier, value);
    }

    public Optional<ShipModule> findHigherSize() {
        return SHIP_MODULES.stream()
                .filter(shipModule -> shipModule.getName().equals(this.getName()) &&
                        (shipModule instanceof HardpointModule || shipModule.getModuleClass().equals(this.getModuleClass())) &&
                        shipModule.getModuleSize().isHigher(this.getModuleSize())
                )
                .sorted(Comparator.comparing(ShipModule::getModuleSize))
                .findFirst();
    }

    public Optional<ShipModule> findHighestSize(int maxSize) {
        return SHIP_MODULES.stream()
                .filter(shipModule -> shipModule.getName().equals(this.getName()) &&
                        (shipModule instanceof HardpointModule || shipModule.getModuleClass().equals(this.getModuleClass())) &&
                        shipModule.getModuleSize().isLowerOrEqual(maxSize)
                )
                .sorted(Comparator.comparing(ShipModule::getModuleSize).reversed())
                .findFirst();
    }

    public Optional<ShipModule> findLowerSize() {
        return SHIP_MODULES.stream()
                .filter(shipModule -> shipModule.getName().equals(this.getName()) &&
                        (shipModule instanceof HardpointModule || shipModule.getModuleClass().equals(this.getModuleClass())) &&
                        shipModule.getModuleSize().isLower(this.getModuleSize())
                )
                .sorted(Comparator.comparing(ShipModule::getModuleSize).reversed())
                .findFirst();
    }

    public Optional<ShipModule> findLowerSize(int maxSize) {
        return SHIP_MODULES.stream()
                .filter(shipModule -> shipModule.getName().equals(this.getName()) &&
                        (shipModule instanceof HardpointModule || shipModule.getModuleClass().equals(this.getModuleClass())) &&
                        shipModule.getModuleSize().isLowerOrEqual(maxSize)
                )
                .sorted(Comparator.comparing(ShipModule::getModuleSize).reversed())
                .findFirst();
    }

    public Optional<ShipModule> findHigherClass() {
        return SHIP_MODULES.stream()
                .filter(shipModule -> shipModule.getName().equals(this.getName()) &&
                        shipModule.getModuleSize().equals(this.getModuleSize()) &&
                        shipModule.getModuleClass().isHigher(this.getModuleClass())
                )
                .sorted(Comparator.comparing(ShipModule::getModuleClass))
                .findFirst();
    }

    public Optional<ShipModule> findLowerClass() {
        return SHIP_MODULES.stream()
                .filter(shipModule -> shipModule.getName().equals(this.getName()) &&
                        shipModule.getModuleSize().equals(this.getModuleSize()) &&
                        shipModule.getModuleClass().isLower(this.getModuleClass())
                )
                .sorted(Comparator.comparing(ShipModule::getModuleClass).reversed())
                .findFirst();
    }

    public void togglePower() {
        powered = !powered;
    }

    public void increasePowerGroup() {
        if (powerGroup < 5) {
            powerGroup++;
        }
    }

    public void decreasePowerGroup() {
        if (powerGroup > 1) {
            powerGroup--;
        }
    }

    // whether the module has a power toggle
    public boolean hasPowerToggle() {
        return powerToggle;
    }

    // whether the module consumes power when not deployed
    public boolean isPassivePower() {
        return false;
    }

    // whether the module consumes power when not deployed
    public boolean isPassivePowerWithoutToggle() {
        return isPassivePower() && !hasPowerToggle();
    }

    public boolean isHiddenStat(HorizonsModifier modifier) {
        if (HorizonsModifier.POWER_DRAW.equals(modifier) && (this.getAttributeValue(modifier).equals(0D) && this.getOriginalAttributeValue(modifier).equals(0D))) {
            return true;
        }
        return false;
    }

    public MatchType getPreEngineeredMatchType() {
        return MatchType.STATS;
    }

    public boolean isSelectable() {
        return true;
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey()) + " " + getModuleSize().intValue() + getModuleClass().name();
    }

    public boolean isSame(ShipModule other) {
        if (other == null) {
            return false;
        }
        //compare this module name, size, class, modifications, experimental effects
        return this.getName().equals(other.getName())
                && this.getModuleSize().equals(other.getModuleSize())
                && this.getModuleClass().equals(other.getModuleClass())
                && isSameModifications(other)
                && isSameExperimentalEffects(other);
    }

    public boolean isSameSize(ShipModule other) {
        return other != null && this.getModuleSize() == other.getModuleSize();
    }

    public boolean isSameClass(ShipModule other) {
        return other != null && this.getModuleClass() == other.getModuleClass();
    }

    public boolean isSameModifications(ShipModule other) {
        return other != null && this.modifications.size() == other.getModifications().size()
                && new HashSet<>(this.getModifications()).containsAll(other.getModifications());
    }

    public boolean isSameExperimentalEffects(ShipModule other) {
        return other != null && this.experimentalEffects.size() == other.getExperimentalEffects().size()
                && new HashSet<>(this.getExperimentalEffects()).containsAll(other.getExperimentalEffects());
    }
}
