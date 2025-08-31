package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.constants.horizons.SynthesisBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsSynthesisBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GuardianNaniteTorpedoPylon extends HardpointModule {
    public static final GuardianNaniteTorpedoPylon GUARDIAN_NANITE_TORPEDO_PYLON_2_I_F = new GuardianNaniteTorpedoPylon("GUARDIAN_NANITE_TORPEDO_PYLON_2_I_F", HorizonsBlueprintName.GUARDIAN_NANITE_TORPEDO_PYLON, ModuleSize.SIZE_2, ModuleClass.I, Origin.GUARDIAN, true, Mounting.FIXED, 843170, "Hpt_ATVentDisruptorPylon_Fixed_Medium", Map.ofEntries( Map.entry(HorizonsModifier.MASS,  3.0), Map.entry(HorizonsModifier.INTEGRITY,  50.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.4), Map.entry(HorizonsModifier.BOOT_TIME,  0.0),  Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  0.0), Map.entry(HorizonsModifier.DAMAGE,  0.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.0), Map.entry(HorizonsModifier.THERMAL_LOAD,  35.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1000.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.5), Map.entry(HorizonsModifier.BURST_INTERVAL,  2.0),  Map.entry(HorizonsModifier.BURST_RATE_OF_FIRE,  -1.0), Map.entry(HorizonsModifier.BURST_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  64.0), Map.entry(HorizonsModifier.ROUNDS_PER_SHOT,  1.0), Map.entry(HorizonsModifier.RELOAD_TIME,  3.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.0), Map.entry(HorizonsModifier.JITTER,  0.0), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE,  true), Map.entry(HorizonsModifier.AMMO_COST,  15222.0)));
    public static final GuardianNaniteTorpedoPylon GUARDIAN_NANITE_TORPEDO_PYLON_3_I_F = new GuardianNaniteTorpedoPylon("GUARDIAN_NANITE_TORPEDO_PYLON_3_I_F", HorizonsBlueprintName.GUARDIAN_NANITE_TORPEDO_PYLON, ModuleSize.SIZE_3, ModuleClass.I, Origin.GUARDIAN, true, Mounting.FIXED, 1627420, "Hpt_ATVentDisruptorPylon_Fixed_Large", Map.ofEntries( Map.entry(HorizonsModifier.MASS,  5.0), Map.entry(HorizonsModifier.INTEGRITY,  80.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.7), Map.entry(HorizonsModifier.BOOT_TIME,  0.0),  Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  0.0), Map.entry(HorizonsModifier.DAMAGE,  0.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.0), Map.entry(HorizonsModifier.THERMAL_LOAD,  55.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1000.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.5), Map.entry(HorizonsModifier.BURST_INTERVAL,  2.0),  Map.entry(HorizonsModifier.BURST_RATE_OF_FIRE,  -1.0), Map.entry(HorizonsModifier.BURST_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  128.0), Map.entry(HorizonsModifier.ROUNDS_PER_SHOT,  1.0), Map.entry(HorizonsModifier.RELOAD_TIME,  3.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.0), Map.entry(HorizonsModifier.JITTER,  0.0), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE,  true), Map.entry(HorizonsModifier.AMMO_COST,  15222.0)));

    public static final List<GuardianNaniteTorpedoPylon> GUARDIAN_NANITE_TORPEDO_PYLONS = List.of(
            GUARDIAN_NANITE_TORPEDO_PYLON_2_I_F,
            GUARDIAN_NANITE_TORPEDO_PYLON_3_I_F
    );
    public GuardianNaniteTorpedoPylon(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public GuardianNaniteTorpedoPylon(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public GuardianNaniteTorpedoPylon(GuardianNaniteTorpedoPylon guardianNaniteTorpedoPylon) {
        super(guardianNaniteTorpedoPylon);
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
    public GuardianNaniteTorpedoPylon Clone() {
        return new GuardianNaniteTorpedoPylon(this);
    }

    @Override
    public int getGrouping() {
        return 1;
    }

    @Override
    public Collection<HorizonsSynthesisBlueprint> synthesisBlueprints() {
        return SynthesisBlueprints.GUARDIAN_NANITE_MUNITIONS.values();
    }

}
