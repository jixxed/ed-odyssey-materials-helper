package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BlueprintCategory {

    ENGINEER_UNLOCKS(1), WEAPON_MODULES(5), WEAPON_GRADES(4), SUIT_MODULES(3), SUIT_GRADES(2),//odyssey
    HARDPOINT(2), UTILITY_MOUNT(3), CORE_INTERNAL(4), OPTIONAL_INTERNAL(5), EXPERIMENTAL_EFFECTS(6), SYNTHESIS(7), TECHBROKER(8);//horizons
    private final int order;

    public String getLocalizationKey() {
        return "blueprint.category.name." + this.name().toLowerCase();
    }
}
