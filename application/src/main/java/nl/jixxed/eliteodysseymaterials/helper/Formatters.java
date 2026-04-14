/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.helper;

import java.math.RoundingMode;
import java.text.NumberFormat;

public class Formatters {


    public static final NumberFormat NUMBER_FORMAT_3 = NumberFormat.getNumberInstance();
    public static final NumberFormat NUMBER_FORMAT_2 = NumberFormat.getNumberInstance();
    public static final NumberFormat NUMBER_FORMAT_2_NO_GROUP = NumberFormat.getNumberInstance();
    public static final NumberFormat NUMBER_FORMAT_2_DUAL_DECIMAL = NumberFormat.getNumberInstance();
    public static final NumberFormat NUMBER_FORMAT_1 = NumberFormat.getNumberInstance();
    public static final NumberFormat NUMBER_FORMAT_1_CEIL = NumberFormat.getNumberInstance();
    public static final NumberFormat NUMBER_FORMAT_0 = NumberFormat.getNumberInstance();
    static {
        NUMBER_FORMAT_0.setMaximumFractionDigits(0);
        NUMBER_FORMAT_1_CEIL.setMaximumFractionDigits(1);
        NUMBER_FORMAT_1.setMaximumFractionDigits(1);
        NUMBER_FORMAT_2.setMaximumFractionDigits(2);
        NUMBER_FORMAT_1.setRoundingMode(RoundingMode.HALF_UP);
        NUMBER_FORMAT_2.setRoundingMode(RoundingMode.HALF_UP);
        NUMBER_FORMAT_2_DUAL_DECIMAL.setMaximumFractionDigits(2);
        NUMBER_FORMAT_2_DUAL_DECIMAL.setMinimumFractionDigits(2);
        NUMBER_FORMAT_2_DUAL_DECIMAL.setGroupingUsed(false);
        NUMBER_FORMAT_1_CEIL.setRoundingMode(RoundingMode.UP);
        NUMBER_FORMAT_3.setMaximumFractionDigits(3);
        NUMBER_FORMAT_3.setMinimumFractionDigits(3);
        NUMBER_FORMAT_3.setGroupingUsed(false);

        NUMBER_FORMAT_2_NO_GROUP.setMaximumFractionDigits(2);
        NUMBER_FORMAT_2_NO_GROUP.setMinimumFractionDigits(2);
        NUMBER_FORMAT_2_NO_GROUP.setGroupingUsed(false);
    }
}
