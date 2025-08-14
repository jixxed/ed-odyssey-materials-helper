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
import nl.jixxed.eliteodysseymaterials.domain.HorizonsTechBrokerBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.military.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.special.CargoHatch;
import nl.jixxed.eliteodysseymaterials.domain.ships.special.FuelTank;
import nl.jixxed.eliteodysseymaterials.domain.ships.utility.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.ShipModuleService;
import nl.jixxed.eliteodysseymaterials.service.ships.PriceService;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;

@Slf4j
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class ShipModule implements Serializable {

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
        ShipModuleService.register(this);
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
        return ShipModuleService.getModules(slotType);
    }

    public static List<ShipModule> getBasicModules() {
        return ShipModuleService.getBasicModules();
    }

    public static ShipModule getModule(String id) {
        return ShipModuleService.getModule(id);
    }

    public void applyModification(final HorizonsBlueprintType modification, final HorizonsBlueprintGrade grade, final BigDecimal modificationCompleteness) {
        if (validModification(modification, false)) {
            final Modification mod = new Modification(modification, modificationCompleteness, grade);
            if (!this.modifications.contains(mod)) {
                this.modifications.clear();
                this.modifications.add(mod);
                this.modifiers.clear();
            }else{
                this.modifications.stream().filter(existingMod -> Objects.equals(existingMod, mod))
                        .findFirst()
                        .ifPresent(existingModification -> {
                            existingModification.setGrade(grade);
                            existingModification.setModificationCompleteness(modificationCompleteness);
                        });
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
        if (HorizonsModifier.DAMAGE_PER_SECOND.equals(moduleAttribute) && this.attributes.containsKey(HorizonsModifier.DAMAGE)) {
            if((double)this.attributes.get(HorizonsModifier.BURST_INTERVAL) == 0D){
                return this.attributes.get(HorizonsModifier.DAMAGE);
            }
            if(this.attributes.containsKey(HorizonsModifier.DAMAGE_MULTIPLIER)){
                return (double) this.attributes.get(HorizonsModifier.DAMAGE) * (double) this.attributes.getOrDefault(HorizonsModifier.DAMAGE_MULTIPLIER, 1.0) / (double) this.attributes.getOrDefault(HorizonsModifier.CHARGE_TIME, 1.0);
            }
            return (double) this.attributes.get(HorizonsModifier.DAMAGE) * (double) getOriginalAttributeValue(HorizonsModifier.RATE_OF_FIRE) * (double) this.attributes.getOrDefault(HorizonsModifier.ROUNDS_PER_SHOT, 1.0);
        }
        if (HorizonsModifier.BREACH_DAMAGE.equals(moduleAttribute)) {
            return (double) getOriginalAttributeValue(HorizonsModifier.DAMAGE) * (double) this.attributes.get(HorizonsModifier.BREACH_DAMAGE);
        }
        if (HorizonsModifier.RATE_OF_FIRE.equals(moduleAttribute) && this.attributes.containsKey(HorizonsModifier.BURST_INTERVAL)) {
            // Rate of Fire = Ammo Clip / (((Burst Size - 1) / Burst Rate of Fire + Burst Interval) * ceil(Ammo Clip / Burst Size))
            if((double)this.attributes.get(HorizonsModifier.BURST_INTERVAL) == 0D){
                return Double.POSITIVE_INFINITY;
            }
            double burstSize = (double) this.attributes.getOrDefault(HorizonsModifier.BURST_SIZE, 1D);
            double ammoClipSize = (double) this.attributes.getOrDefault(HorizonsModifier.AMMO_CLIP_SIZE, burstSize);// default to burstSize if there is no ammo clip
            double burstRateOfFire = (double) this.attributes.getOrDefault(HorizonsModifier.BURST_RATE_OF_FIRE, -1D);
            double burstInterval = (double) this.attributes.getOrDefault(HorizonsModifier.BURST_INTERVAL, 1D);
            return ammoClipSize / (((burstSize - 1) / burstRateOfFire + burstInterval) * Math.ceil(ammoClipSize / burstSize));
        }
        if (!this.attributes.containsKey(moduleAttribute)) {
            throw new IllegalArgumentException("Unknown Module Attribute: " + moduleAttribute + " for module: " + this.name);
        }
        return this.attributes.get(moduleAttribute);
    }

    public Object getAttributeValue(final HorizonsModifier moduleAttribute) {
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
                    BigDecimal completeness = modifications.getFirst().getModificationCompleteness().orElse(BigDecimal.ZERO);
                    Object attributeValue = getAttributeValue(moduleAttribute, completeness.doubleValue());
                    if(attributeValue instanceof Double) {
                        double currentValue1 = (double) attributeValue;
                        double maxValue = (double) getAttributeValue(moduleAttribute, 1.0);
                        return maxValue == currentValue1 ? BigDecimal.ONE : completeness;
                    }else{
                        return modifications.getFirst().getModificationCompleteness().orElse(BigDecimal.ZERO);
                    }
                } else {
                    return BigDecimal.ONE;
                }
            }).orElse(BigDecimal.ZERO);
        }
    }

    public Object getAttributeValue(final HorizonsModifier moduleAttribute, Double completeness) {
        if (HorizonsModifier.DAMAGE_PER_SECOND.equals(moduleAttribute) && this.attributes.containsKey(HorizonsModifier.DAMAGE)) {
            if((double)this.attributes.get(HorizonsModifier.BURST_INTERVAL) == 0D){
                return getAttributeValue(HorizonsModifier.DAMAGE, completeness);
            }

            if(this.attributes.containsKey(HorizonsModifier.DAMAGE_MULTIPLIER)){
                return (double) getAttributeValue(HorizonsModifier.DAMAGE, completeness) * getAttributeValueOrDefault(HorizonsModifier.DAMAGE_MULTIPLIER, completeness, 1.0) / getAttributeValueOrDefault(HorizonsModifier.CHARGE_TIME, completeness, 1.0);
            }
            return (double) getAttributeValue(HorizonsModifier.DAMAGE, completeness) * getAttributeValueOrDefault(HorizonsModifier.RATE_OF_FIRE, completeness, 1.0) * getAttributeValueOrDefault(HorizonsModifier.ROUNDS_PER_SHOT, completeness, 1.0);
        }
        if (HorizonsModifier.BREACH_DAMAGE.equals(moduleAttribute)) {
            return (double) getAttributeValue(HorizonsModifier.DAMAGE, completeness) * (double) this.attributes.get(HorizonsModifier.BREACH_DAMAGE);//todo move BREACH_DAMAGE to dedicated attribute
        }
        if (HorizonsModifier.RATE_OF_FIRE.equals(moduleAttribute) && this.attributes.containsKey(HorizonsModifier.BURST_INTERVAL)) {
            // Rate of Fire = Ammo Clip / (((Burst Size - 1) / Burst Rate of Fire + Burst Interval) * ceil(Ammo Clip / Burst Size))
            if((double)this.attributes.get(HorizonsModifier.BURST_INTERVAL) == 0D){
                return Double.POSITIVE_INFINITY;
            }
            double burstSize = getAttributeValueOrDefault(HorizonsModifier.BURST_SIZE, completeness, 1.0);
            double ammoClipSize = getAttributeValueOrDefault(HorizonsModifier.AMMO_CLIP_SIZE, completeness, burstSize);// default to burstSize if there is no ammo clip
            double burstRateOfFire = getAttributeValueOrDefault(HorizonsModifier.BURST_RATE_OF_FIRE, completeness, -1.0);
            double burstInterval = getAttributeValueOrDefault(HorizonsModifier.BURST_INTERVAL, completeness, 1.0);
            return ammoClipSize / (((burstSize - 1) / burstRateOfFire + burstInterval) * Math.ceil(ammoClipSize / burstSize));
        }
        if (!this.attributes.containsKey(moduleAttribute)) {
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
            if (HorizonsModifier.AMMO_CLIP_SIZE.equals(moduleAttribute)){
                double burstSize = getAttributeValueOrDefault(HorizonsModifier.BURST_SIZE, completeness, 1.0);
                toReturn = Math.ceil(toReturn / burstSize) * burstSize;
            }
            if (HorizonsModifier.AMMO_MAXIMUM.equals(moduleAttribute)){
                toReturn = Math.floor(toReturn);
            }
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
            final HorizonsBlueprint moduleBlueprint = isLegacy()
                    ? (HorizonsBlueprint) HorizonsBlueprintConstants.getLegacyRecipe(this.name.getPrimary(), modification.getModification(), modification.getGrade())
                    : (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(this.name.getPrimary(), modification.getModification(), modification.getGrade());

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
        return ShipModuleService.findHigherSize(this);
    }

    public Optional<ShipModule> findHighestSize(int maxSize) {
        return ShipModuleService.findHighestSize(this, maxSize);
    }

    public Optional<ShipModule> findLowerSize() {
        return ShipModuleService.findLowerSize(this);
    }

    public Optional<ShipModule> findLowerSize(int maxSize) {
        return ShipModuleService.findLowerSize(this, maxSize);
    }

    public Optional<ShipModule> findHigherClass() {
        return ShipModuleService.findHigherClass(this);
    }

    public Optional<ShipModule> findLowerClass() {
        return ShipModuleService.findLowerClass(this);
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
                && new HashSet<>(this.getModifications()).containsAll(other.getModifications())
                && this.getModifications().stream()
                .allMatch(mod -> other.getModifications().stream()
                        .filter(modOther -> modOther.equals(mod))
                        .allMatch(modOther -> modOther.getModificationCompleteness().equals(mod.getModificationCompleteness())
                                && modOther.getGrade().equals(mod.getGrade())));
    }

    public boolean isSameExperimentalEffects(ShipModule other) {
        return other != null && this.experimentalEffects.size() == other.getExperimentalEffects().size()
                && new HashSet<>(this.getExperimentalEffects()).containsAll(other.getExperimentalEffects());
    }

    public HorizonsTechBrokerBlueprint techBrokerBlueprint() {
        return null;
    }

    public boolean hasTechBrokerBlueprint() {
        return this.techBrokerBlueprint() != null;
    }
}
