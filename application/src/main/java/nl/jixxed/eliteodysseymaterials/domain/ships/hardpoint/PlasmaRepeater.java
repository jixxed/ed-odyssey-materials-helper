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

public class PlasmaRepeater extends HardpointModule {
    public static final PlasmaRepeater PLASMA_REPEATER_1_E_F_INDIE_FIGHTER = new PlasmaRepeater("PLASMA_REPEATER_1_E_F_INDIE_FIGHTER", HorizonsBlueprintName.PLASMA_REPEATER, ModuleSize.SIZE_1, ModuleClass.E, false, Mounting.FIXED, 0, "hpt_plasmarepeater_fixed_indie_fighter",            Map.ofEntries(Map.entry(HorizonsModifier.MASS, 0.25), Map.entry(HorizonsModifier.INTEGRITY, 30.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.0), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 18.0), Map.entry(HorizonsModifier.DAMAGE, 1.8), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.1), Map.entry(HorizonsModifier.THERMAL_LOAD, 0.3), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 110.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE, 3000.0), Map.entry(HorizonsModifier.SHOT_SPEED, 1000.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 10.0), Map.entry(HorizonsModifier.BURST_RATE_OF_FIRE, -1.0), Map.entry(HorizonsModifier.BURST_SIZE, 1.0), Map.entry(HorizonsModifier.BURST_INTERVAL, 0.1), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 30.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 30.0), Map.entry(HorizonsModifier.ROUNDS_PER_SHOT, 1.0), Map.entry(HorizonsModifier.RELOAD_TIME, 1.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.6), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.6), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, 1500.0), Map.entry(HorizonsModifier.AMMO_COST, 0.0)));
    public static final PlasmaRepeater PLASMA_REPEATER_1_E_F_EMPIRE_FIGHTER = new PlasmaRepeater("PLASMA_REPEATER_1_E_F_EMPIRE_FIGHTER", HorizonsBlueprintName.PLASMA_REPEATER, ModuleSize.SIZE_1, ModuleClass.E, false, Mounting.FIXED, 0, "hpt_plasmarepeater_fixed_empire_fighter",            Map.ofEntries(Map.entry(HorizonsModifier.MASS, 0.25), Map.entry(HorizonsModifier.INTEGRITY, 30.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.0), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 18.0), Map.entry(HorizonsModifier.DAMAGE, 1.8), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.1), Map.entry(HorizonsModifier.THERMAL_LOAD, 0.3), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 110.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE, 3000.0), Map.entry(HorizonsModifier.SHOT_SPEED, 1000.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 10.0), Map.entry(HorizonsModifier.BURST_RATE_OF_FIRE, -1.0), Map.entry(HorizonsModifier.BURST_SIZE, 1.0), Map.entry(HorizonsModifier.BURST_INTERVAL, 0.1), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 30.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 30.0), Map.entry(HorizonsModifier.ROUNDS_PER_SHOT, 1.0), Map.entry(HorizonsModifier.RELOAD_TIME, 1.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.6), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.6), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, 1500.0), Map.entry(HorizonsModifier.AMMO_COST, 0.0)));
    public static final PlasmaRepeater PLASMA_REPEATER_1_E_F_FED_FIGHTER = new PlasmaRepeater("PLASMA_REPEATER_1_E_F_FED_FIGHTER", HorizonsBlueprintName.PLASMA_REPEATER, ModuleSize.SIZE_1, ModuleClass.E, false, Mounting.FIXED, 0, "hpt_plasmarepeater_fixed_fed_fighter",            Map.ofEntries(Map.entry(HorizonsModifier.MASS, 0.25), Map.entry(HorizonsModifier.INTEGRITY, 30.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.0), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 18.0), Map.entry(HorizonsModifier.DAMAGE, 1.8), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.1), Map.entry(HorizonsModifier.THERMAL_LOAD, 0.3), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 110.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE, 3000.0), Map.entry(HorizonsModifier.SHOT_SPEED, 1000.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 10.0), Map.entry(HorizonsModifier.BURST_RATE_OF_FIRE, -1.0), Map.entry(HorizonsModifier.BURST_SIZE, 1.0), Map.entry(HorizonsModifier.BURST_INTERVAL, 0.1), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 30.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 30.0), Map.entry(HorizonsModifier.ROUNDS_PER_SHOT, 1.0), Map.entry(HorizonsModifier.RELOAD_TIME, 1.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.4), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 0.6), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 0.6), Map.entry(HorizonsModifier.JITTER, 0.0), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, 1500.0), Map.entry(HorizonsModifier.AMMO_COST, 0.0)));

    public static final List<PlasmaRepeater> PLASMA_REPEATERS = List.of(
            PLASMA_REPEATER_1_E_F_INDIE_FIGHTER,
            PLASMA_REPEATER_1_E_F_EMPIRE_FIGHTER,
            PLASMA_REPEATER_1_E_F_FED_FIGHTER
    );

    public PlasmaRepeater(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public PlasmaRepeater(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public PlasmaRepeater(PlasmaRepeater guardianPlasmaCharger) {
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
    public PlasmaRepeater Clone() {
        return new PlasmaRepeater(this);
    }
    public boolean isSelectable() {
        return false;
    }
}
