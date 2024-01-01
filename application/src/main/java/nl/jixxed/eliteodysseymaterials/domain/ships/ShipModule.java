package nl.jixxed.eliteodysseymaterials.domain.ships;

import com.google.common.util.concurrent.AtomicDouble;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBiFunction;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class ShipModule implements Serializable {
    private static final List<ShipModule> SHIP_MODULES = new ArrayList<>();
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
    private final int basePrice;
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

    ShipModule(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        this(id, name, moduleSize, moduleClass, Origin.HUMAN, false, basePrice, internalName, attributes);
    }

    ShipModule(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final Origin origin, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        this(id, name, moduleSize, moduleClass, origin, false, basePrice, internalName, attributes);
    }

    ShipModule(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final boolean multiCrew, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        this(id, name, moduleSize, moduleClass, Origin.HUMAN, multiCrew, basePrice, internalName, attributes);
    }

    ShipModule(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final Origin origin, final boolean multiCrew, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        this.id = id;
        this.name = name;
        this.moduleSize = moduleSize;
        this.moduleClass = moduleClass;
        this.origin = origin;
        this.multiCrew = multiCrew;
        this.basePrice = basePrice;
        this.internalName = internalName;
        this.attributes.putAll(attributes);
        if (!this.attributes.containsKey(HorizonsModifier.POWER_DRAW)) {
            this.attributes.put(HorizonsModifier.POWER_DRAW, 0.0);
        }
        SHIP_MODULES.add(this);
    }

    ShipModule(final ShipModule shipModule) {
        this.id = shipModule.id;
        this.name = shipModule.name;
        this.moduleSize = shipModule.moduleSize;
        this.moduleClass = shipModule.moduleClass;
        this.origin = shipModule.origin;
        this.multiCrew = shipModule.multiCrew;
        this.basePrice = shipModule.basePrice;
        this.internalName = shipModule.internalName;
        this.modifications.addAll(shipModule.modifications.stream().map(modification -> new Modification(modification.getModification(), modification.getModificationCompleteness(), modification.getGrade())).toList());
        this.experimentalEffects.addAll(shipModule.experimentalEffects);
        this.attributes.putAll(shipModule.attributes);
    }

    public static List<ShipModule> getModules(final SlotType slotType) {
        return SHIP_MODULES.stream().filter(module -> {
            final Class<? extends ShipModule> aClass = module.getClass();
            final Class<? extends ShipModule> moduleClass = slotType.getModuleClass();
            return moduleClass.isAssignableFrom(aClass);
        }).toList();
    }

    public void applyModification(final HorizonsBlueprintType modification, final HorizonsBlueprintGrade grade, final Double modificationCompleteness) {
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

    public Object getAttributeValueOrDefault(final HorizonsModifier moduleAttribute, final Object defaultValue) {
        try {
            return getAttributeValue(moduleAttribute);
        } catch (final IllegalArgumentException e) {
            return defaultValue;
        }
    }

    public Object getOriginalAttributeValue(final HorizonsModifier moduleAttribute) {
        if (!this.attributes.containsKey(moduleAttribute)) {
            throw new IllegalArgumentException("Unknown Module Attribute: " + moduleAttribute + " for module: " + this.name);
        }
        return this.attributes.get(moduleAttribute);
    }

    public Object getAttributeValue(final HorizonsModifier moduleAttribute) {
        return getAttributeValue(moduleAttribute, null);
    }

    public double getAttributeCompleteness(final HorizonsModifier moduleAttribute) {
        Double currentValue = (Double) this.modifiers.get(moduleAttribute);
        if (currentValue != null) {
            double minValue = (double) getAttributeValue(moduleAttribute, 0.0);
            double maxValue = (double) getAttributeValue(moduleAttribute, 1.0);
            if (maxValue == minValue) {
                return 1D;
            }
            return (currentValue - minValue) / (maxValue - minValue);
        } else {
           return modifications.stream().findFirst().map(modification -> {
                final HorizonsBlueprint moduleBlueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(this.name.getPrimary(), modifications.getFirst().getModification(), modifications.getFirst().getGrade());
                final var horizonsModifierValue = moduleBlueprint.getModifiers().get(moduleAttribute);
                if (horizonsModifierValue != null && horizonsModifierValue.isPositive()) {
                    return modifications.getFirst().getModificationCompleteness();
                } else {
                    return 1D;
                }
            }).orElse(0D);
        }
    }

    public Object getAttributeValue(final HorizonsModifier moduleAttribute, Double completeness) {
        if (!this.attributes.containsKey(moduleAttribute)) {
            throw new IllegalArgumentException("Unknown Module Attribute: " + moduleAttribute + " for module: " + this.name);
        }
        if (isLegacy()) {
            return modifiers.containsKey(moduleAttribute) ? modifiers.get(moduleAttribute) : attributes.get(moduleAttribute);
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
                    modificationCompleteness.set(completeness != null ? completeness : modification.getModificationCompleteness());
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
        return value.get();
    }

    public abstract List<HorizonsBlueprintType> getAllowedBlueprints();

    public abstract List<HorizonsBlueprintType> getAllowedExperimentalEffects();

    public abstract ShipModule Clone();

    public String getLocalizationKey() {
        return "ships.module.name." + this.internalName.toLowerCase();
    }

    public String getClarifier() {
        return isPreEngineered() ? " " + LocaleService.getLocalizedStringForCurrentLocale(getName().getLocalizationKey()) : "";
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

    public boolean isCGExclusive() {
        return false;
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

    public boolean groupOnName() {
        return false;
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

    public Optional<ShipModule> findLowerSize() {
        return SHIP_MODULES.stream()
                .filter(shipModule -> shipModule.getName().equals(this.getName()) &&
                        (shipModule instanceof HardpointModule || shipModule.getModuleClass().equals(this.getModuleClass())) &&
                        shipModule.getModuleSize().isLower(this.getModuleSize())
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

}
