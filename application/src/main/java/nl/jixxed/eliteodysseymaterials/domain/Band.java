/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents a rewards band (e.g., top1, 50%, 75%).
 * Bands are sorted topX first (numerically), then percentage bands (ascending).
 */
@Getter
@EqualsAndHashCode
public class Band implements Comparable<Band> {
    private final String name;

    public Band(final String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Band b) {
        Band a = this;
        var aIsTopX = a.name.startsWith("top");
        var bIsTopX = b.name.startsWith("top");

        // If both are topX, sort alphabetically/numerically
        if (aIsTopX && bIsTopX) {
            // Extract numbers and sort: top1, top5, top10, etc.
            var aNum = Integer.parseInt(a.name.replace("top",""));
            var bNum = Integer.parseInt(b.name.replace("top",""));
            return Integer.compare(aNum, bNum);
        }

        // topX bands come first
        if (aIsTopX) return -1;
        if (bIsTopX) return 1;

        // For percentage bands, sort from lowest to highest
        var aPercent = Integer.parseInt(a.name.replace("%", ""));
        var bPercent = Integer.parseInt(b.name.replace("%", ""));
        return Integer.compare(aPercent, bPercent);
    }
}
