/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
