package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BlueprintCategory {

    ENGINEER_UNLOCKS(1), WEAPON_MODULES(5), WEAPON_GRADES(4), SUIT_MODULES(3), SUIT_GRADES(2),//odyssey
    HARDPOINT(2), UTILITY_MOUNT(3), CORE_INTERNAL(4), OPTIONAL_INTERNAL(5), OPTIONAL_MILITARY(6), EXPERIMENTAL_EFFECTS(7), SYNTHESIS(8), TECHBROKER(9);//horizons
    private final int order;

    public String getLocalizationKey() {
        return "blueprint.category.name." + this.name().toLowerCase();
    }
}
