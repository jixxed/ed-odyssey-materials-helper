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

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuitModification implements Modification {
    ADDED_MELEE_DAMAGE(OdysseyBlueprintName.ADDED_MELEE_DAMAGE),
    COMBAT_MOVEMENT_SPEED(OdysseyBlueprintName.COMBAT_MOVEMENT_SPEED),
    DAMAGE_RESISTANCE(OdysseyBlueprintName.DAMAGE_RESISTANCE),
    ENHANCED_TRACKING(OdysseyBlueprintName.ENHANCED_TRACKING),
    EXTRA_AMMO_CAPACITY(OdysseyBlueprintName.EXTRA_AMMO_CAPACITY),
    EXTRA_BACKPACK_CAPACITY(OdysseyBlueprintName.EXTRA_BACKPACK_CAPACITY),
    FASTER_SHIELD_REGEN(OdysseyBlueprintName.FASTER_SHIELD_REGEN),
    IMPROVED_BATTERY_CAPACITY(OdysseyBlueprintName.IMPROVED_BATTERY_CAPACITY),
    IMPROVED_JUMP_ASSIST(OdysseyBlueprintName.IMPROVED_JUMP_ASSIST),
    INCREASED_AIR_RESERVES(OdysseyBlueprintName.INCREASED_AIR_RESERVES),
    INCREASED_SPRINT_DURATION(OdysseyBlueprintName.INCREASED_SPRINT_DURATION),
    NIGHT_VISION(OdysseyBlueprintName.NIGHT_VISION),
    QUIETER_FOOTSTEPS(OdysseyBlueprintName.QUIETER_FOOTSTEPS),
    REDUCED_TOOL_BATTERY_CONSUMPTION(OdysseyBlueprintName.REDUCED_TOOL_BATTERY_CONSUMPTION);
    private final OdysseyBlueprintName recipe;

    @Override
    public String getLocalizationKey() {
        return "loadout.modification.name." + this.name().toLowerCase();
    }

    @Override
    public String getImage(final boolean present) {

        return "/images/modification/" + name().toLowerCase() + (present ? "_active" : "") + ".png";
    }

    public static SuitModification forFdevName(final String name) {
        return switch (name) {
            case "suit_increasedmeleedamage" -> ADDED_MELEE_DAMAGE;
            case "suit_adsmovementspeed" -> COMBAT_MOVEMENT_SPEED;
            case "suit_improvedarmourrating" -> DAMAGE_RESISTANCE;
            case "suit_improvedradar" -> ENHANCED_TRACKING;
            case "suit_increasedammoreserves" -> EXTRA_AMMO_CAPACITY;
            case "suit_backpackcapacity" -> EXTRA_BACKPACK_CAPACITY;
            case "suit_increasedshieldregen" -> FASTER_SHIELD_REGEN;
            case "suit_increasedbatterycapacity" -> IMPROVED_BATTERY_CAPACITY;
            case "suit_improvedjumpassist" -> IMPROVED_JUMP_ASSIST;
            case "suit_increasedo2capacity" -> INCREASED_AIR_RESERVES;
            case "suit_increasedsprintduration" -> INCREASED_SPRINT_DURATION;
            case "suit_nightvision" -> NIGHT_VISION;
            case "suit_quieterfootsteps" -> QUIETER_FOOTSTEPS;
            case "suit_reducedtoolbatteryconsumption" -> REDUCED_TOOL_BATTERY_CONSUMPTION;
            default -> throw new IllegalArgumentException("Unknown SuitModification: " + name);
        };
    }
}
