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

import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatapointComparator implements Comparator<LineTooltip.DataPoint> {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");
    private final String topText;
    private final String percentText;

    public DatapointComparator() {
        topText = LocaleService.getLocalizedStringForCurrentLocale("community.goal.band.chart.top", "");
        percentText = LocaleService.getLocalizedStringForCurrentLocale("community.goal.band.chart.percent", "");
    }

    @Override
    public int compare(LineTooltip.DataPoint a, LineTooltip.DataPoint b) {
        boolean aIsTopX = a.datasetName.startsWith(topText);
        boolean bIsTopX = b.datasetName.startsWith(topText);
        boolean aIsPercentage = a.datasetName.endsWith(percentText);
        boolean bIsPercentage = b.datasetName.endsWith(percentText);
        // topX bands come first
        if (aIsTopX && !bIsTopX) return -1;
        if (!aIsTopX && bIsTopX) return 1;

        // If both are topX, sort numerically: top1, top5, top10, etc.
        if (aIsTopX && bIsTopX) {
            int aNum = extractNumber(a.datasetName);
            int bNum = extractNumber(b.datasetName);
            return Integer.compare(aNum, bNum);
        }
        if (aIsPercentage && !bIsPercentage) return -1;
        if (!aIsPercentage && bIsPercentage) return 1;

        if(aIsPercentage && bIsPercentage) {
            // For percentage bands, sort from lowest to highest
            int aPercent = extractNumber(a.datasetName);
            int bPercent = extractNumber(b.datasetName);

            return Integer.compare(aPercent, bPercent);
        }
        return 1;//natural order
    }

    private int extractNumber(String text) {
        Matcher matcher = NUMBER_PATTERN.matcher(text);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        return 0;
    }
}