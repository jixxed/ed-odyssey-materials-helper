package nl.jixxed.eliteodysseymaterials.enums;

public enum RecipeName {

    ENGINEER_A1("A1. Domino Green"),
    ENGINEER_A2("A2. Domino Green -> Kit Fowler"),
    ENGINEER_A3("A3. Kit Fowler"),
    ENGINEER_A4("A4. Kit Fowler -> Yarden Bond"),
    ENGINEER_A5("A5. Yarden Bond"),
    ENGINEER_B1("B1. Hero Ferrari"),
    ENGINEER_B2("B2. Hero Ferrari -> Wellington Beck"),
    ENGINEER_B3("B3. Wellington Beck (Sell 25 in total, any combination)"),
    ENGINEER_B4("B4. Wellington Beck -> Uma Laszlo"),
    ENGINEER_B5("B5. Uma Laszlo"),
    ENGINEER_C1("C1. Jude Navarro"),
    ENGINEER_C2("C2. Jude Navarro -> Terra Velasquez"),
    ENGINEER_C3("C3. Terra Velasquez (Complete all of below)"),
    ENGINEER_C4("C4. Terra Velasquez -> Oden Geiger"),
    ENGINEER_C5("C5. Oden Geiger (Sell 20 in total, any combination)"),
    MAVERICK_SUIT_GRADE_1_2("Maverick Suit Grade 1>2"),
    MAVERICK_SUIT_GRADE_2_3("Maverick Suit Grade 2>3"),
    MAVERICK_SUIT_GRADE_3_4("Maverick Suit Grade 3>4"),
    MAVERICK_SUIT_GRADE_4_5("Maverick Suit Grade 4>5"),
    DOMINATOR_SUIT_GRADE_1_2("Dominator Suit Grade 1>2"),
    DOMINATOR_SUIT_GRADE_2_3("Dominator Suit Grade 2>3"),
    DOMINATOR_SUIT_GRADE_3_4("Dominator Suit Grade 3>4"),
    DOMINATOR_SUIT_GRADE_4_5("Dominator Suit Grade 4>5"),
    ARTEMIS_SUIT_GRADE_1_2("Artemis Suit Grade 1>2"),
    ARTEMIS_SUIT_GRADE_2_3("Artemis Suit Grade 2>3"),
    ARTEMIS_SUIT_GRADE_3_4("Artemis Suit Grade 3>4"),
    ARTEMIS_SUIT_GRADE_4_5("Artemis Suit Grade 4>5"),
    ADDED_MELEE_DAMAGE("Added Melee Damage"),
    COMBAT_MOVEMENT_SPEED("Combat Movement Speed"),
    DAMAGE_RESISTANCE("Damage Resistance"),
    ENHANCED_TRACKING("Enhanced Tracking"),
    EXTRA_AMMO_CAPACITY("Extra Ammo Capacity"),
    EXTRA_BACKPACK_CAPACITY("Extra Backpack Capacity"),
    FASTER_SHIELD_REGEN("Faster Shield Regen"),
    IMPROVED_BATTERY_CAPACITY("Improved Battery Capacity"),
    IMPROVED_JUMP_ASSIST("Improved Jump Assist"),
    INCREASED_AIR_RESERVES("Increased Air Reserves"),
    INCREASED_SPRINT_DURATION("Increased Sprint Duration"),
    NIGHT_VISION("Night Vision"),
    QUIETER_FOOTSTEPS("Quieter Footsteps"),
    REDUCED_TOOL_BATTERY_CONSUMPTION("Reduced Tool Battery Consumption"),
    KARMA_1_2("Karma (AR-50, C-44, L-6, P-15) 1>2"),
    KARMA_2_3("Karma (AR-50, C-44, L-6, P-15) 2>3"),
    KARMA_3_4("Karma (AR-50, C-44, L-6, P-15) 3>4"),
    KARMA_4_5("Karma (AR-50, C-44, L-6, P-15) 4>5"),
    TK_1_2("TK (Aphelion, Eclipse, Zenith) 1>2"),
    TK_2_3("TK (Aphelion, Eclipse, Zenith) 2>3"),
    TK_3_4("TK (Aphelion, Eclipse, Zenith) 3>4"),
    TK_4_5("TK (Aphelion, Eclipse, Zenith) 4>5"),
    MANTICORE_1_2("Manticore (Executioner, Intimidator, Oppressor, Tormentor) 1>2"),
    MANTICORE_2_3("Manticore (Executioner, Intimidator, Oppressor, Tormentor) 2>3"),
    MANTICORE_3_4("Manticore (Executioner, Intimidator, Oppressor, Tormentor) 3>4"),
    MANTICORE_4_5("Manticore (Executioner, Intimidator, Oppressor, Tormentor) 4>5"),
    AUDIO_MASKING("Audio Masking"),
    FASTER_HANDLING("Faster Handling"),
    GREATER_RANGE_KINETIC("Greater Range (Kinetic)"),
    GREATER_RANGE_LASER("Greater Range (Laser)"),
    GREATER_RANGE_PLASMA("Greater Range (Plasma)"),
    HEADSHOT_DAMAGE_KINETIC("Headshot Damage (Kinetic)"),
    HEADSHOT_DAMAGE_LASER("Headshot Damage (Laser)"),
    HEADSHOT_DAMAGE_PLASMA("Headshot Damage (Plasma)"),
    HIGHER_ACCURACY_KINETIC("Higher Accuracy (Kinetic)"),
    HIGHER_ACCURACY_LASER("Higher Accuracy (Laser)"),
    HIGHER_ACCURACY_PLASMA("Higher Accuracy (Plasma)"),
    IMPROVED_HIP_FIRE_ACCURACY("Improved Hip Fire Accuracy"),
    MAGAZINE_SIZE("Magazine Size"),
    NOISE_SUPPRESSOR("Noise Suppressor"),
    RELOAD_SPEED("Reload Speed"),
    SCOPE("Scope"),
    STABILITY("Stability"),
    STOWED_RELOADING("Stowed Reloading");


    String name;

    RecipeName(final String name) {
        this.name = name;
    }

    public static RecipeName forName(final String name) {
        try {
            return RecipeName.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return null;
        }
    }

    public String friendlyName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
