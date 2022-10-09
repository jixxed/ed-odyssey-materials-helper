package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Rarity {
    VERY_COMMON(300, 1), COMMON(250, 2), STANDARD(200, 3), RARE(150, 4), VERY_RARE(100, 5), UNKNOWN(0, 0);

    private final Integer maxAmount;
    private final Integer level;

    public String getImagePath() {
        return "/images/ships/materials/grades/" + name().toLowerCase() + ".png";
    }
}
