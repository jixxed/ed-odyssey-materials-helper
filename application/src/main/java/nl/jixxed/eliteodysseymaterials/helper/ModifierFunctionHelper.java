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

    /**
     * Calculation based on a range. A percentage will be provided that determines the position between start and end.
     * The calculation will raise the result with the provided percentage(position).
     *
     * @param start start of the range
     * @param end   end of the range
     * @return BiFunction
     */
    public static HorizonsBiFunction<Double> falloffPercentagePositive(final Double value, final Double start, final Double end) {
        return new HorizonsBiFunction<>(value, start, end, HorizonsBiFunction.CalculationType.FALLOFF_PERCENTAGE_POSITIVE);
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
