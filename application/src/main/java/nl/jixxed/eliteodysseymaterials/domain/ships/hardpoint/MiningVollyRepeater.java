package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MiningVollyRepeater extends MiningHardpointModule {
    public static final MiningVollyRepeater MINING_VOLLEY_REPEATER_3_C_F = new MiningVollyRepeater("MINING_VOLLEY_REPEATER_3_C_F", HorizonsBlueprintName.MINING_VOLLEY_REPEATER, ModuleSize.SIZE_3, ModuleClass.C, false, Mounting.FIXED, 149906, "Hpt_Miningtoolv2_Fixed_Large", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  61.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.5), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  6.0), Map.entry(HorizonsModifier.DAMAGE, 0.3), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.3), Map.entry(HorizonsModifier.THERMAL_LOAD,  0.4), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  18.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  500.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 20.0), Map.entry(HorizonsModifier.BURST_INTERVAL, 0.05), Map.entry(HorizonsModifier.BURST_SIZE,  1.0), Map.entry(HorizonsModifier.BURST_RATE_OF_FIRE,  -1.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.10), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.20), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO,  1.0), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  500.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  1.0), Map.entry(HorizonsModifier.JITTER,  2.19), Map.entry(HorizonsModifier.ROUNDS_PER_SHOT,  1.0), Map.entry(HorizonsModifier.RELOAD_TIME,  4.0)));

    public static final List<MiningVollyRepeater> MINING_VOLLEY_REPEATERS = List.of(
            MINING_VOLLEY_REPEATER_3_C_F
    );

    public MiningVollyRepeater(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public MiningVollyRepeater(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public MiningVollyRepeater(MiningVollyRepeater miningVollyRepeater) {
        super(miningVollyRepeater);
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
    public MiningVollyRepeater Clone() {
        return new MiningVollyRepeater(this);
    }

}
