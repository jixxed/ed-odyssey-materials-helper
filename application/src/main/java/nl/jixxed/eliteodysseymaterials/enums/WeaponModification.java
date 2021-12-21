package nl.jixxed.eliteodysseymaterials.enums;

public enum WeaponModification implements Modification {
    AUDIO_MASKING,
    FASTER_HANDLING,
    GREATER_RANGE_KINETIC,
    GREATER_RANGE_LASER,
    GREATER_RANGE_PLASMA,
    HEADSHOT_DAMAGE_KINETIC,
    HEADSHOT_DAMAGE_LASER,
    HEADSHOT_DAMAGE_PLASMA,
    HIGHER_ACCURACY_KINETIC,
    HIGHER_ACCURACY_LASER,
    HIGHER_ACCURACY_PLASMA,
    MAGAZINE_SIZE,
    NOISE_SUPPRESSOR,
    RELOAD_SPEED,
    SCOPE,
    STABILITY,
    STOWED_RELOADING,
    NONE;

    public String getLocalizationKey() {
        return "loadout.modification.name." + this.name().toLowerCase();
    }

    public String getImage() {
        String name = name();
        name = name.endsWith("_KINETIC") ? name.substring(0, name.indexOf("_KINETIC")) : name;
        name = name.endsWith("_LASER") ? name.substring(0, name.indexOf("_LASER")) : name;
        name = name.endsWith("_PLASMA") ? name.substring(0, name.indexOf("_PLASMA")) : name;
        return "/images/modification/" + name.toLowerCase() + ".png";
    }
}
