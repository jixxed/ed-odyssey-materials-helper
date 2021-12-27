package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuitModification implements Modification {
    ADDED_MELEE_DAMAGE(RecipeName.ADDED_MELEE_DAMAGE),
    COMBAT_MOVEMENT_SPEED(RecipeName.COMBAT_MOVEMENT_SPEED),
    DAMAGE_RESISTANCE(RecipeName.DAMAGE_RESISTANCE),
    ENHANCED_TRACKING(RecipeName.ENHANCED_TRACKING),
    EXTRA_AMMO_CAPACITY(RecipeName.EXTRA_AMMO_CAPACITY),
    EXTRA_BACKPACK_CAPACITY(RecipeName.EXTRA_BACKPACK_CAPACITY),
    FASTER_SHIELD_REGEN(RecipeName.FASTER_SHIELD_REGEN),
    IMPROVED_BATTERY_CAPACITY(RecipeName.IMPROVED_BATTERY_CAPACITY),
    IMPROVED_JUMP_ASSIST(RecipeName.IMPROVED_JUMP_ASSIST),
    INCREASED_AIR_RESERVES(RecipeName.INCREASED_AIR_RESERVES),
    INCREASED_SPRINT_DURATION(RecipeName.INCREASED_SPRINT_DURATION),
    NIGHT_VISION(RecipeName.NIGHT_VISION),
    QUIETER_FOOTSTEPS(RecipeName.QUIETER_FOOTSTEPS),
    REDUCED_TOOL_BATTERY_CONSUMPTION(RecipeName.REDUCED_TOOL_BATTERY_CONSUMPTION);
    private final RecipeName recipe;

    @Override
    public String getLocalizationKey() {
        return "loadout.modification.name." + this.name().toLowerCase();
    }

    @Override
    public String getImage() {

        return "/images/modification/" + name().toLowerCase() + ".png";
    }
}
