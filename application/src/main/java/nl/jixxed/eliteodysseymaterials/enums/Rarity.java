package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;

@RequiredArgsConstructor
@Getter
public enum Rarity {
    VERY_COMMON(300, 1, EdAwesomeIcon.MATERIALS_GRADE_1),
    COMMON(250, 2, EdAwesomeIcon.MATERIALS_GRADE_2),
    STANDARD(200, 3, EdAwesomeIcon.MATERIALS_GRADE_3),
    RARE(150, 4, EdAwesomeIcon.MATERIALS_GRADE_4),
    VERY_RARE(100, 5, EdAwesomeIcon.MATERIALS_GRADE_5),
    UNKNOWN(0, 0, EdAwesomeIcon.MATERIALS_GRADE_1);

    private final Integer maxAmount;
    private final Integer level;
    private final EdAwesomeIcon icon;
}
