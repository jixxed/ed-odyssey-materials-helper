package nl.jixxed.eliteodysseymaterials.enums;

public enum SuitModification implements Modification {
    ADDED_MELEE_DAMAGE,
    COMBAT_MOVEMENT_SPEED,
    DAMAGE_RESISTANCE,
    ENHANCED_TRACKING,
    EXTRA_AMMO_CAPACITY,
    EXTRA_BACKPACK_CAPACITY,
    FASTER_SHIELD_REGEN,
    IMPROVED_BATTERY_CAPACITY,
    IMPROVED_JUMP_ASSIST,
    INCREASED_AIR_RESERVES,
    INCREASED_SPRINT_DURATION,
    NIGHT_VISION,
    QUIETER_FOOTSTEPS,
    REDUCED_TOOL_BATTERY_CONSUMPTION;

    public String getLocalizationKey() {
        return "loadout.modification.name." + this.name().toLowerCase();
    }

    public String getImage() {

        return "/images/modification/" + name().toLowerCase() + ".png";
    }
}
