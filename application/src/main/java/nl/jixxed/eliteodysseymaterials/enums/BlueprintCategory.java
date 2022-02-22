package nl.jixxed.eliteodysseymaterials.enums;

public enum BlueprintCategory {

    ENGINEER_UNLOCKS, WEAPON_MODULES, WEAPON_GRADES, SUIT_MODULES, SUIT_GRADES,//odyssey
    HARDPOINT, UTILITY_MOUNT, CORE_INTERNAL, OPTIONAL_INTERNAL;

    public String getLocalizationKey() {
        return "blueprint.category.name." + this.name().toLowerCase();
    }
}
