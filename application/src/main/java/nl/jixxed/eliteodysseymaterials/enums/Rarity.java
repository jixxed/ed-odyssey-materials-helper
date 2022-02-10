package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Rarity {
    VERY_COMMON(300), COMMON(250), STANDARD(200), RARE(150), VERY_RARE(100), UNKNOWN(0);

    private final Integer maxAmount;

    public String getImagePath() {
        return "/images/ships/materials/grades/" + name().toLowerCase() + ".png";
    }
}
