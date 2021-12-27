package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WeaponModification implements Modification {
    AUDIO_MASKING(RecipeName.AUDIO_MASKING),
    FASTER_HANDLING(RecipeName.FASTER_HANDLING),
    GREATER_RANGE_KINETIC(RecipeName.GREATER_RANGE_KINETIC),
    GREATER_RANGE_LASER(RecipeName.GREATER_RANGE_LASER),
    GREATER_RANGE_PLASMA(RecipeName.GREATER_RANGE_PLASMA),
    HEADSHOT_DAMAGE_KINETIC(RecipeName.HEADSHOT_DAMAGE_KINETIC),
    HEADSHOT_DAMAGE_LASER(RecipeName.HEADSHOT_DAMAGE_LASER),
    HEADSHOT_DAMAGE_PLASMA(RecipeName.HEADSHOT_DAMAGE_PLASMA),
    HIGHER_ACCURACY_KINETIC(RecipeName.HIGHER_ACCURACY_KINETIC),
    HIGHER_ACCURACY_LASER(RecipeName.HIGHER_ACCURACY_LASER),
    HIGHER_ACCURACY_PLASMA(RecipeName.HIGHER_ACCURACY_PLASMA),
    MAGAZINE_SIZE(RecipeName.MAGAZINE_SIZE),
    NOISE_SUPPRESSOR(RecipeName.NOISE_SUPPRESSOR),
    RELOAD_SPEED(RecipeName.RELOAD_SPEED),
    SCOPE(RecipeName.SCOPE),
    STABILITY(RecipeName.STABILITY),
    STOWED_RELOADING(RecipeName.STOWED_RELOADING),
    NONE(RecipeName.NONE);

    private final RecipeName recipe;

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
