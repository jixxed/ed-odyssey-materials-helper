package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WeaponModification implements Modification {
    AUDIO_MASKING(BlueprintName.AUDIO_MASKING),
    FASTER_HANDLING(BlueprintName.FASTER_HANDLING),
    GREATER_RANGE_KINETIC(BlueprintName.GREATER_RANGE_KINETIC),
    GREATER_RANGE_LASER(BlueprintName.GREATER_RANGE_LASER),
    GREATER_RANGE_PLASMA(BlueprintName.GREATER_RANGE_PLASMA),
    HEADSHOT_DAMAGE_KINETIC(BlueprintName.HEADSHOT_DAMAGE_KINETIC),
    HEADSHOT_DAMAGE_LASER(BlueprintName.HEADSHOT_DAMAGE_LASER),
    HEADSHOT_DAMAGE_PLASMA(BlueprintName.HEADSHOT_DAMAGE_PLASMA),
    HIGHER_ACCURACY_KINETIC(BlueprintName.HIGHER_ACCURACY_KINETIC),
    HIGHER_ACCURACY_LASER(BlueprintName.HIGHER_ACCURACY_LASER),
    HIGHER_ACCURACY_PLASMA(BlueprintName.HIGHER_ACCURACY_PLASMA),
    MAGAZINE_SIZE(BlueprintName.MAGAZINE_SIZE),
    NOISE_SUPPRESSOR(BlueprintName.NOISE_SUPPRESSOR),
    RELOAD_SPEED(BlueprintName.RELOAD_SPEED),
    SCOPE(BlueprintName.SCOPE),
    STABILITY(BlueprintName.STABILITY),
    STOWED_RELOADING(BlueprintName.STOWED_RELOADING),
    NONE(BlueprintName.NONE);

    private final BlueprintName recipe;

    @Override
    public String getLocalizationKey() {
        return "loadout.modification.name." + this.name().toLowerCase();
    }

    @Override
    public String getImage() {
        String name = name();
        name = name.endsWith("_KINETIC") ? name.substring(0, name.indexOf("_KINETIC")) : name;
        name = name.endsWith("_LASER") ? name.substring(0, name.indexOf("_LASER")) : name;
        name = name.endsWith("_PLASMA") ? name.substring(0, name.indexOf("_PLASMA")) : name;
        return "/images/modification/" + name.toLowerCase() + ".png";
    }
}
