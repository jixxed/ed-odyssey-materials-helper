/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GuardianShardLauncher extends HardpointModule {
    public static final GuardianShardLauncher GUARDIAN_SHARD_LAUNCHER_1_H_F_FIGHTER = new GuardianShardLauncher("GUARDIAN_PLASMA_AUTO_CANNON_1_H_F_FIGHTER", HorizonsBlueprintName.GUARDIAN_SHARD_LAUNCHER, ModuleSize.SIZE_1, ModuleClass.H, Origin.GUARDIAN, false, Mounting.FIXED, 0, "hpt_guardianshard_fixed_gdn_fighter",            Map.ofEntries(Map.entry(HorizonsModifier.MASS, 0.25), Map.entry(HorizonsModifier.INTEGRITY, 30.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.0), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 73.1), Map.entry(HorizonsModifier.DAMAGE, 8.3), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 1.12), Map.entry(HorizonsModifier.THERMAL_LOAD, 1.5), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE, 2000.0), Map.entry(HorizonsModifier.SHOT_SPEED, 1333.33), Map.entry(HorizonsModifier.RATE_OF_FIRE, 2.9), Map.entry(HorizonsModifier.BURST_RATE_OF_FIRE, 12.0), Map.entry(HorizonsModifier.BURST_SIZE, 2.0), Map.entry(HorizonsModifier.BURST_INTERVAL, 0.2), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 10.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 180.0), Map.entry(HorizonsModifier.ROUNDS_PER_SHOT, 3.0), Map.entry(HorizonsModifier.RELOAD_TIME, 5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.3), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.40), Map.entry(HorizonsModifier.JITTER, 5.00), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, 1700.0), Map.entry(HorizonsModifier.AMMO_COST, 0.0), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE, false)));

    public static final List<GuardianShardLauncher> GUARDIAN_SHARD_LAUNCHERS = List.of(
            GUARDIAN_SHARD_LAUNCHER_1_H_F_FIGHTER
    );

    public GuardianShardLauncher(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public GuardianShardLauncher(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public GuardianShardLauncher(GuardianShardLauncher guardianPlasmaCharger) {
        super(guardianPlasmaCharger);
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
    public GuardianShardLauncher Clone() {
        return new GuardianShardLauncher(this);
    }
    public boolean isSelectable() {
        return false;
    }
}
