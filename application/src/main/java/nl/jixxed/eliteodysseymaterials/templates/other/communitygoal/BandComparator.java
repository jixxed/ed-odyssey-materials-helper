/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.other.communitygoal;

import nl.jixxed.eliteodysseymaterials.service.cg.ReportModels;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BandComparator implements Comparator<ReportModels.BandMax> {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");

    @Override
    public int compare(ReportModels.BandMax a, ReportModels.BandMax b) {
        boolean aIsTopX = a.band().startsWith("top");
        boolean bIsTopX = b.band().startsWith("top");

        // topX bands come first
        if (aIsTopX && !bIsTopX) return -1;
        if (!aIsTopX && bIsTopX) return 1;

        // If both are topX, sort numerically: top1, top5, top10, etc.
        if (aIsTopX && bIsTopX) {
            int aNum = extractNumber(a.band());
            int bNum = extractNumber(b.band());
            return Integer.compare(aNum, bNum);
        }

        // For percentage bands, sort from lowest to highest
        int aPercent = Integer.parseInt(a.band().replace("%", ""));
        int bPercent = Integer.parseInt(b.band().replace("%", ""));

        return Integer.compare(aPercent, bPercent);
    }

    private int extractNumber(String text) {
        Matcher matcher = NUMBER_PATTERN.matcher(text);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        return 0;
    }
}