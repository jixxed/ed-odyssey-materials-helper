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
}
