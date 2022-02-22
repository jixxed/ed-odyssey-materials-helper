package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuitModification implements Modification {
    ADDED_MELEE_DAMAGE(BlueprintName.ADDED_MELEE_DAMAGE),
    COMBAT_MOVEMENT_SPEED(BlueprintName.COMBAT_MOVEMENT_SPEED),
    DAMAGE_RESISTANCE(BlueprintName.DAMAGE_RESISTANCE),
    ENHANCED_TRACKING(BlueprintName.ENHANCED_TRACKING),
    EXTRA_AMMO_CAPACITY(BlueprintName.EXTRA_AMMO_CAPACITY),
    EXTRA_BACKPACK_CAPACITY(BlueprintName.EXTRA_BACKPACK_CAPACITY),
    FASTER_SHIELD_REGEN(BlueprintName.FASTER_SHIELD_REGEN),
    IMPROVED_BATTERY_CAPACITY(BlueprintName.IMPROVED_BATTERY_CAPACITY),
    IMPROVED_JUMP_ASSIST(BlueprintName.IMPROVED_JUMP_ASSIST),
    INCREASED_AIR_RESERVES(BlueprintName.INCREASED_AIR_RESERVES),
    INCREASED_SPRINT_DURATION(BlueprintName.INCREASED_SPRINT_DURATION),
    NIGHT_VISION(BlueprintName.NIGHT_VISION),
    QUIETER_FOOTSTEPS(BlueprintName.QUIETER_FOOTSTEPS),
    REDUCED_TOOL_BATTERY_CONSUMPTION(BlueprintName.REDUCED_TOOL_BATTERY_CONSUMPTION);
    private final BlueprintName recipe;

    @Override
    public String getLocalizationKey() {
        return "loadout.modification.name." + this.name().toLowerCase();
    }

    @Override
    public String getImage() {

        return "/images/modification/" + name().toLowerCase() + ".png";
    }
}
