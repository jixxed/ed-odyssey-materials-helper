package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EnzymeMissileRack extends HardpointModule {
    public static final EnzymeMissileRack ENZYME_MISSILE_RACK_2_B_F = new EnzymeMissileRack("ENZYME_MISSILE_RACK_2_B_F", HorizonsBlueprintName.ENZYME_MISSILE_RACK, ModuleSize.SIZE_2, ModuleClass.B, Origin.TECHBROKER,false, Mounting.FIXED    ,   480500, "Hpt_CausticMissile_Fixed_Medium"  ,Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.2), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 2.5), Map.entry(HorizonsModifier.DAMAGE, 5.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.08), Map.entry(HorizonsModifier.THERMAL_LOAD, 1.5), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 750.0  ), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.500), Map.entry(HorizonsModifier.BURST_INTERVAL, 2.000), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  8.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  64.0), Map.entry(HorizonsModifier.RELOAD_TIME, 5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.80), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 0.8), Map.entry(HorizonsModifier.CAUSTIC_DAMAGE_RATIO, 0.2), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, 3000.0), Map.entry(HorizonsModifier.AMMO_COST, 235.0)));
    public static final EnzymeMissileRack ENZYME_MISSILE_RACK_2_B_F_PRE = new EnzymeMissileRack("ENZYME_MISSILE_RACK_2_B_F_PRE", HorizonsBlueprintName.ENZYME_MISSILE_RACK_PRE, ModuleSize.SIZE_2, ModuleClass.B, Origin.TECHBROKER,false, Mounting.FIXED    ,   0, "Hpt_CausticMissile_Fixed_Medium"  ,Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.00), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.2), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 2.5), Map.entry(HorizonsModifier.DAMAGE, 5.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.08), Map.entry(HorizonsModifier.THERMAL_LOAD, 1.5), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 750.0  ), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.500), Map.entry(HorizonsModifier.BURST_INTERVAL, 2.000), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  8.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  64.0), Map.entry(HorizonsModifier.RELOAD_TIME, 5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.80), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 0.8), Map.entry(HorizonsModifier.CAUSTIC_DAMAGE_RATIO, 0.2), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, 3000.0), Map.entry(HorizonsModifier.AMMO_COST, 235.0)));

    static {
        ENZYME_MISSILE_RACK_2_B_F_PRE.getModifications().add(
                new Modification(HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE_INCREASED_DAMAGE, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
    }
    public static final List<EnzymeMissileRack> ENZYME_MISSILE_RACKS = List.of(
            ENZYME_MISSILE_RACK_2_B_F,
            ENZYME_MISSILE_RACK_2_B_F_PRE
    );
    public EnzymeMissileRack(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public EnzymeMissileRack(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public EnzymeMissileRack(EnzymeMissileRack enzymeMissileRack) {
        super(enzymeMissileRack);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return Collections.emptyList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public EnzymeMissileRack Clone() {
        return new EnzymeMissileRack(this);
    }

    @Override
    public boolean isPreEngineered() {
        return ENZYME_MISSILE_RACK_2_B_F_PRE.equals(this);
    }

    @Override
    public boolean isCGExclusive() {
        return isPreEngineered();
    }
}
