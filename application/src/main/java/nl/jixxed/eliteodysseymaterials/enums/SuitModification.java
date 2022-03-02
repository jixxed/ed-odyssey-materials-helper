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
}
