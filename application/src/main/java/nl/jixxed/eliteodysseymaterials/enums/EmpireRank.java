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

import java.util.Arrays;

@RequiredArgsConstructor
public enum EmpireRank {
    NONE(0),
    OUTSIDER(1),
    SERF(2),
    MASTER(3),
    SQUIRE(4),
    KNIGHT(5),
    LORD(6),
    BARON(7),
    VISCOUNT(8),
    COUNT(9),
    EARL(10),
    MARQUIS(11),
    DUKE(12),
    PRINCE(13),
    KING(14);

    @Getter
    private final Integer rank;

    public static EmpireRank forRank(final Integer rank) {
        return Arrays.stream(EmpireRank.values())
                .filter(empireRank -> empireRank.rank.equals(rank))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown Empire Rank: " + rank));
    }

    public String getLocalizationKey() {
        return "empire.rank." + this.name().toLowerCase();
    }
}
