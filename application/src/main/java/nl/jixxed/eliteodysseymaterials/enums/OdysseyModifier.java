/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.enums;

public enum OdysseyModifier {
    AIR_CAPACITY,
    AMMO_CAPACITY_MULTIPLIER,
    BACKPACK_COMPONENT_CAPACITY,
    BACKPACK_DATA_CAPACITY,
    BACKPACK_ITEM_CAPACITY,
    BATTERY_ENERGY_CAPACITY,
    ENGINEER_MODIFICATION_SLOTS,
    EXPLOSIVE_DAMAGE_REDUCTION,
    EXPLOSIVE_DAMAGE_RESISTANCE,
    FISTS_MELEE_DAMAGE_MULTIPLIER,
    FOOTSTEP_AUDIBLE_RANGE_MULTIPLIER,
    HANDLING_SPEED,
    HIP_FIRE_ACCURACY,
    HEADSHOT_DAMAGE_MULTIPLIER,
    JUMP_ASSIST_BOOST_DURATION,
    JUMP_ASSIST_BOOST_RECHARGE_DURATION,
    JUMP_ASSIST_BATTERY_CONSUMPTION,
    KINETIC_DAMAGE_REDUCTION,
    KINETIC_DAMAGE_RESISTANCE,
    LOS_ANALYSIS_RANGE,
    LOS_ANALYSIS_TIME,
    MOVEMENT_SPEED_PENALTY,
    MAGAZINE_SIZE,
    PLASMA_DAMAGE_REDUCTION,
    PLASMA_DAMAGE_RESISTANCE,
    RELOAD_SPEED,
    RELOAD_SPEED_APHELION,
    SHIELD_HEALTH,
    SHIELD_REGENERATION_RATE,
    SPRINT_DURATION,
    INSTABILITY,
    THERMAL_DAMAGE_REDUCTION,
    THERMAL_DAMAGE_RESISTANCE,
    TOOL_ENERGY_DRAIN_MULTIPLIER,
    WEAPON_EFFECTIVE_RANGE,
    WEAPON_MELEE_DAMAGE_MULTIPLIER;

    public static OdysseyModifier forName(final String name) {
        try {
            return OdysseyModifier.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return null;
        }
    }

    public String getLocalizationKey() {
        return "modifier.name." + this.name().toLowerCase();
    }
}
