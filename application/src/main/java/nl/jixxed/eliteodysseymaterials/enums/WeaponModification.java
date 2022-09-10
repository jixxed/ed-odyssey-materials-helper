package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WeaponModification implements Modification {
    AUDIO_MASKING(OdysseyBlueprintName.AUDIO_MASKING),
    FASTER_HANDLING(OdysseyBlueprintName.FASTER_HANDLING),
    GREATER_RANGE_KINETIC(OdysseyBlueprintName.GREATER_RANGE_KINETIC),
    GREATER_RANGE_LASER(OdysseyBlueprintName.GREATER_RANGE_LASER),
    GREATER_RANGE_PLASMA(OdysseyBlueprintName.GREATER_RANGE_PLASMA),
    HEADSHOT_DAMAGE_KINETIC(OdysseyBlueprintName.HEADSHOT_DAMAGE_KINETIC),
    HEADSHOT_DAMAGE_LASER(OdysseyBlueprintName.HEADSHOT_DAMAGE_LASER),
    HEADSHOT_DAMAGE_PLASMA(OdysseyBlueprintName.HEADSHOT_DAMAGE_PLASMA),
    HIGHER_ACCURACY_KINETIC(OdysseyBlueprintName.HIGHER_ACCURACY_KINETIC),
    HIGHER_ACCURACY_LASER(OdysseyBlueprintName.HIGHER_ACCURACY_LASER),
    HIGHER_ACCURACY_PLASMA(OdysseyBlueprintName.HIGHER_ACCURACY_PLASMA),
    MAGAZINE_SIZE(OdysseyBlueprintName.MAGAZINE_SIZE),
    NOISE_SUPPRESSOR(OdysseyBlueprintName.NOISE_SUPPRESSOR),
    RELOAD_SPEED(OdysseyBlueprintName.RELOAD_SPEED),
    SCOPE(OdysseyBlueprintName.SCOPE),
    STABILITY(OdysseyBlueprintName.STABILITY),
    STOWED_RELOADING(OdysseyBlueprintName.STOWED_RELOADING),
    NONE(OdysseyBlueprintName.NONE);

    private final OdysseyBlueprintName recipe;

    @Override
    public String getLocalizationKey() {
        return "loadout.modification.name." + this.name().toLowerCase();
    }

    @Override
    public String getImage(final boolean present) {
        String name = name();
        name = name.endsWith("_KINETIC") ? name.substring(0, name.indexOf("_KINETIC")) : name;
        name = name.endsWith("_LASER") ? name.substring(0, name.indexOf("_LASER")) : name;
        name = name.endsWith("_PLASMA") ? name.substring(0, name.indexOf("_PLASMA")) : name;
        return "/images/modification/" + name.toLowerCase() + (present ? "_active" : "") + ".png";
    }

    public static WeaponModification forFdevName(final String name, final Weapon weapon) {
        String toCheck = name;
        if (name.startsWith("weapon_range") || name.startsWith("weapon_headshotdamage") || name.startsWith("weapon_accuracy")) {
            toCheck += "_" + weaponType(weapon);
        }
        return switch (toCheck) {
            case "weapon_suppression_unpressurised" -> AUDIO_MASKING;
            case "weapon_handling" -> FASTER_HANDLING;
            case "weapon_range_kinetic" -> GREATER_RANGE_KINETIC;
            case "weapon_range_laser" -> GREATER_RANGE_LASER;
            case "weapon_range_plasma" -> GREATER_RANGE_PLASMA;
            case "weapon_headshotdamage_kinetic" -> HEADSHOT_DAMAGE_KINETIC;
            case "weapon_headshotdamage_laser" -> HEADSHOT_DAMAGE_LASER;
            case "weapon_headshotdamage_plasma" -> HEADSHOT_DAMAGE_PLASMA;
            case "weapon_accuracy_kinetic" -> HIGHER_ACCURACY_KINETIC;
            case "weapon_accuracy_laser" -> HIGHER_ACCURACY_LASER;
            case "weapon_accuracy_plasma" -> HIGHER_ACCURACY_PLASMA;
            case "weapon_clipsize" -> MAGAZINE_SIZE;
            case "weapon_suppression_pressurised" -> NOISE_SUPPRESSOR;
            case "weapon_reloadspeed" -> RELOAD_SPEED;
            case "weapon_scope" -> SCOPE;
            case "weapon_stability" -> STABILITY;
            case "weapon_backpackreloading" -> STOWED_RELOADING;
            default -> throw new IllegalArgumentException("Unknown WeaponModification: " + name);
        };
    }

    private static String weaponType(final Weapon weapon) {
        return switch (weapon) {
            case KINEMATIC_L6, KINEMATIC_AR50, KINEMATIC_C44, KINEMATIC_P15 -> "kinetic";
            case TAKADA_APHELION, TAKADA_ECLIPSE, TAKADA_ZENITH -> "laser";
            case MANTICORE_EXECUTIONER, MANTICORE_TORMENTOR, MANTICORE_INTIMIDATOR, MANTICORE_OPPRESSOR -> "plasma";
        };
    }
}
