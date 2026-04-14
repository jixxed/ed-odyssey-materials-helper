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

import nl.jixxed.eliteodysseymaterials.domain.HorizonsBiFunction;

public class ModifierFunctionHelper {
    /**
     * Calculation based on a range. A percentage will be provided that determines the position between start and end.
     * The calculation will lower the result with the provided percentage(position).
     *
     * @param start start of the range
     * @param end   end of the range
     * @return BiFunction
     */
    public static HorizonsBiFunction<Double> percentageNegative(final Double start, final Double end) {
        return new HorizonsBiFunction<>(start,end, HorizonsBiFunction.CalculationType.PERCENTAGE_NEGATIVE);
    }

    /**
     * Calculation based on a range. A percentage will be provided that determines the position between start and end.
     * The calculation will raise the result with the provided percentage(position).
     *
     * @param start start of the range
     * @param end   end of the range
     * @return BiFunction
     */
    public static HorizonsBiFunction<Double> percentagePositive(final Double start, final Double end) {
        return new HorizonsBiFunction<>(start, end, HorizonsBiFunction.CalculationType.PERCENTAGE_POSITIVE);
    }
    public static HorizonsBiFunction<Double> hullBoostPositive(final Double start, final Double end) {
        return new HorizonsBiFunction<>(start, end, HorizonsBiFunction.CalculationType.HULL_BOOST_POSITIVE);
    }
    public static HorizonsBiFunction<Double> hullBoostNegative(final Double start, final Double end) {
        return new HorizonsBiFunction<>(start, end, HorizonsBiFunction.CalculationType.HULL_BOOST_NEGATIVE);
    }
    public static HorizonsBiFunction<Double> shieldBoostPositive(final Double start, final Double end) {
        return new HorizonsBiFunction<>(start, end, HorizonsBiFunction.CalculationType.SHIELD_BOOST_POSITIVE);
    }
    public static HorizonsBiFunction<Double> shieldBoostNegative(final Double start, final Double end) {
        return new HorizonsBiFunction<>(start, end, HorizonsBiFunction.CalculationType.SHIELD_BOOST_NEGATIVE);
    }
    /**
     * Calculation based on a range. A percentage will be provided that determines the position between start and end.
     * The calculation will lower the result with the provided percentage(position).
     *
     * @param start start of the range
     * @param end   end of the range
     * @return BiFunction
     */
    public static HorizonsBiFunction<Double> resistancePositive(final Double start, final Double end) {
        return new HorizonsBiFunction<>(start,end, HorizonsBiFunction.CalculationType.RESISTANCE_POSITIVE);
    }

    /**
     * Calculation based on a range. A percentage will be provided that determines the position between start and end.
     * The calculation will raise the result with the provided percentage(position).
     *
     * @param start start of the range
     * @param end   end of the range
     * @return BiFunction
     */
    public static HorizonsBiFunction<Double> resistanceNegative(final Double start, final Double end) {
        return new HorizonsBiFunction<>(start, end, HorizonsBiFunction.CalculationType.RESISTANCE_NEGATIVE);
    }


//    /**
//     * Calculation based on a fixed value. The calculation will lower the result with the provided percentage.
//     *
//     * @param value percentage to lower the result with
//     * @return BiFunction
//     */
//    public static BiFunction<Double, Double, Double> percentageNegative(final Double value) {
//        return (base, percent) -> base * (1 - value);
//    }
//
//    /**
//     * Calculation based on a fixed value. The calculation will raise the result with the provided percentage.
//     *
//     * @param value percentage to raise the result with
//     * @return BiFunction
//     */
//    public static BiFunction<Double, Double, Double> percentagePositive(final Double value) {
//        return (base, percent) -> base * (1 + value);
//    }

    /**
     * Calculation that adds a fixed amount.
     *
     * @param value value to increase with
     * @return BiFunction
     */
    public static HorizonsBiFunction<Double> plus(final Double value) {
//        return (base, percent) -> base + value;
        return new HorizonsBiFunction<>(value, HorizonsBiFunction.CalculationType.PLUS);
    }

    /**
     * Calculation that subtracts a fixed amount.
     *
     * @param value value to subtract with
     * @return BiFunction
     */
    public static HorizonsBiFunction<Double> minus(final Double value) {
//        return (base, percent) -> base - value;
        return new HorizonsBiFunction<>(value, HorizonsBiFunction.CalculationType.MINUS);
    }

    /**
     * Returns a boolean value as provided.
     *
     * @param value the boolean to return
     * @return BiFunction
     */
    public static HorizonsBiFunction<Boolean> bool(final boolean value) {
//        return (base, percent) -> value;
        return new HorizonsBiFunction<>(value, HorizonsBiFunction.CalculationType.BOOL);
    }
}
