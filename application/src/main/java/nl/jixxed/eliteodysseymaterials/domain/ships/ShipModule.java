package nl.jixxed.eliteodysseymaterials.domain.ships;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class ShipModule implements Serializable {
    private static final List<ShipModule> SHIP_MODULES = new ArrayList<>();
    @Getter
    @EqualsAndHashCode.Include
    private final HorizonsBlueprintName name;
    private final Map<HorizonsModifier, Object> attributes = new HashMap<>();
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
    private final String internalName;
    @Getter
    private final List<Modification> modifications = new ArrayList<>();
    @Getter
    private final List<HorizonsBlueprintType> experimentalEffects = new ArrayList<>();

    ShipModule(final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        this(name, moduleSize, moduleClass, Origin.HUMAN, false, basePrice, internalName, attributes);
    }

    ShipModule(final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final Origin origin, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        this(name, moduleSize, moduleClass, origin, false, basePrice, internalName, attributes);
    }

    ShipModule(final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final boolean multiCrew, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        this(name, moduleSize, moduleClass, Origin.HUMAN, multiCrew, basePrice, internalName, attributes);
    }

    ShipModule(final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final Origin origin, final boolean multiCrew, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        this.name = name;
        this.moduleSize = moduleSize;
        this.moduleClass = moduleClass;
        this.origin = origin;
        this.multiCrew = multiCrew;
        this.basePrice = basePrice;
        this.internalName = internalName;
        this.attributes.putAll(attributes);
        SHIP_MODULES.add(this);
    }

    ShipModule(final ShipModule shipModule) {
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
            if(!this.modifications.contains(mod)){
                this.modifications.clear();
                this.modifications.add(mod);
            }
        }
    }

    public void applyExperimentalEffect(final HorizonsBlueprintType experimentalEffect) {
        if (validModification(experimentalEffect, true) && !this.experimentalEffects.contains(experimentalEffect)) {
            this.experimentalEffects.clear();
            this.experimentalEffects.add(experimentalEffect);
        }
    }

    public void removeModification(final HorizonsBlueprintType modification) {
        this.modifications.removeIf(modification1 -> Objects.equals(modification1.getModification(), modification));
    }

    public void removeExperimentalEffect(final HorizonsBlueprintType experimentalEffect) {
        this.experimentalEffects.removeIf(experimentalEffect1 -> Objects.equals(experimentalEffect1, experimentalEffect));
    }
    private boolean validModification(final HorizonsBlueprintType modification, final boolean experimental) {
        if (experimental) {
            return getAllowedExperimentalEffects().contains(modification);
        }
        return getAllowedBlueprints().contains(modification);
    }

    public Object getAttributeValueOrDefault(final HorizonsModifier moduleAttribute, final Object defaultValue) {
       try{
           return getAttributeValue(moduleAttribute);
       }catch (final IllegalArgumentException e){
           return defaultValue;
       }
    }
    public Object getAttributeValue(final HorizonsModifier moduleAttribute) {
        if (!this.attributes.containsKey(moduleAttribute)) {
            throw new IllegalArgumentException("Unknown Module Attribute: " + moduleAttribute + " for module: " + this.name);
        }
        final Object baseAttributeValue = this.attributes.get(moduleAttribute);
        if(baseAttributeValue instanceof Double){
            Double toReturn = Double.valueOf((double)baseAttributeValue);
            toReturn = preAdaptAttributeValue(moduleAttribute, toReturn);
            toReturn = (Double)applyModsToAttributeValue(moduleAttribute, toReturn);
            toReturn = postAdaptAttributeValue(moduleAttribute, toReturn);
            return toReturn;
        }else if(baseAttributeValue instanceof Boolean){
            Boolean toReturn = Boolean.valueOf((boolean)baseAttributeValue);
            return applyModsToAttributeValue(moduleAttribute, toReturn);
        }
        return baseAttributeValue;
    }

    public Double preAdaptAttributeValue(final HorizonsModifier moduleAttribute, final Double toReturn) {
        return toReturn;
    }

    public Double postAdaptAttributeValue(final HorizonsModifier moduleAttribute, final Double toReturn) {
        return toReturn;
    }

    private Object applyModsToAttributeValue(HorizonsModifier moduleAttribute, Object attributeValue) {
        final AtomicReference<Object> value = new AtomicReference<>(attributeValue);
        this.modifications.forEach(modification -> {
            final HorizonsBlueprint moduleBlueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(this.name, modification.getModification(), modification.getGrade());
            final HorizonsModifierValue moduleModifier = moduleBlueprint.getModifiers().get(moduleAttribute);
            if(moduleModifier != null) {
                //if negative effect, apply fully
                value.set(moduleModifier.getModifiedValue(value.get(), moduleModifier.isPositive() ? modification.getModificationCompleteness() : 1D));
            }
        });
        this.experimentalEffects.forEach(modification -> {
            final HorizonsBlueprint experimentalEffectBlueprint = HorizonsBlueprintConstants.getExperimentalEffects().get(this.name).get(modification);
            final HorizonsModifierValue experimentalEffectModifier = experimentalEffectBlueprint.getModifiers().get(moduleAttribute);
            if(experimentalEffectModifier != null) {
                value.set(experimentalEffectModifier.getModifiedValue(value.get(), 1D));
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
        return "";
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
}
