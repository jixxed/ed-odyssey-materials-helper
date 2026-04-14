/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.domain.ships.utility;

import nl.jixxed.eliteodysseymaterials.constants.horizons.utilitymounts.PointDefenceBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PointDefence extends UtilityModule {
    public static final PointDefence POINT_DEFENCE_0_I = new PointDefence("POINT_DEFENCE_0_I", HorizonsBlueprintName.POINT_DEFENCE, ModuleSize.SIZE_0, ModuleClass.I, true, Mounting.TURRETED, 18550, "Hpt_PlasmaPointDefence_Turret_Tiny", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  0.5), Map.entry(HorizonsModifier.INTEGRITY,  30.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  2.0), Map.entry(HorizonsModifier.DAMAGE,  0.2), Map.entry(HorizonsModifier.THERMAL_LOAD,  0.07), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  2500.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1000.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  10.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.2), Map.entry(HorizonsModifier.BURST_RATE_OF_FIRE,  15.0), Map.entry(HorizonsModifier.BURST_SIZE,  4.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  12.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  10000.0), Map.entry(HorizonsModifier.RELOAD_TIME,  0.4), Map.entry(HorizonsModifier.JITTER,  0.75), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO,  1.0), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));
    public static final PointDefence POINT_DEFENCE_0_I_PRE = new PointDefence("POINT_DEFENCE_0_I_PRE", HorizonsBlueprintName.POINT_DEFENCE_PRE, ModuleSize.SIZE_0, ModuleClass.I, true, Mounting.TURRETED, 0, "Hpt_PlasmaPointDefence_Turret_Tiny", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  0.5), Map.entry(HorizonsModifier.INTEGRITY,  30.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  2.0), Map.entry(HorizonsModifier.DAMAGE,  0.2), Map.entry(HorizonsModifier.THERMAL_LOAD,  0.07), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  2500.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1000.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  10.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.2), Map.entry(HorizonsModifier.BURST_RATE_OF_FIRE,  15.0), Map.entry(HorizonsModifier.BURST_SIZE,  4.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  12.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  10000.0), Map.entry(HorizonsModifier.RELOAD_TIME,  0.4), Map.entry(HorizonsModifier.JITTER,  0.75), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO,  1.0), Map.entry(HorizonsModifier.AMMO_COST,  1.0)));

    static {
        POINT_DEFENCE_0_I_PRE.getModifications().add(
                new Modification(HorizonsBlueprintType.LIGHTWEIGHT_FOCUSED, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
    }
    public static final List<PointDefence> POINT_DEFENCES = List.of(
            POINT_DEFENCE_0_I
    );

    public PointDefence(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public PointDefence(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public PointDefence(PointDefence pointDefence) {
        super(pointDefence);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        if(isPreEngineered()){
            return Collections.emptyList();
        }
        return PointDefenceBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public PointDefence Clone() {
        return new PointDefence(this);
    }
    @Override
    public boolean isPreEngineered() {
        return POINT_DEFENCE_0_I_PRE.equals(this);
    }

    @Override
    public boolean isCGExclusive() {
        return isPreEngineered();
    }
    @Override
    public boolean isPassivePower(){
        return true;
    }
}
