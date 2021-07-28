package nl.jixxed.eliteodysseymaterials.enums;

public enum RecipeCategory {

    ENGINEER_UNLOCKS, WEAPON_MODULES, WEAPON_GRADES, SUIT_MODULES, SUIT_GRADES;

    public String getLocalizationKey() {
        return "recipe.category.name." + this.name().toLowerCase();
    }
}
