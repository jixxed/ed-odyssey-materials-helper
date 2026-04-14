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
public enum FederationRank {
    NONE(0),
    RECRUIT(1),
    CADET(2),
    MIDSHIPMAN(3),
    PETTY_OFFICER(4),
    CHIEF_PETTY_OFFICER(5),
    WARRANT_OFFICER(6),
    ENSIGN(7),
    LIEUTENANT(8),
    LIEUTENANT_COMMANDER(9),
    POST_COMMANDER(10),
    POST_CAPTAIN(11),
    REAR_ADMIRAL(12),
    VICE_ADMIRAL(13),
    ADMIRAL(14);

    @Getter
    private final Integer rank;

    public static FederationRank forRank(final Integer rank) {
        return Arrays.stream(FederationRank.values())
                .filter(federationRank -> federationRank.rank.equals(rank))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown Federation Rank: " + rank));
    }

    public String getLocalizationKey() {
        return "federation.rank." + this.name().toLowerCase();
    }
}
