package nl.jixxed.eliteodysseymaterials.helper;

import io.reactivex.rxjava3.functions.BiFunction;

public class ModifierFunctionHelper {
    /**
     * Calculation based on a range. A percentage will be provided that determines the position between start and end.
     * The calculation will lower the result with the provided percentage(position).
     *
     * @param start start of the range
     * @param end   end of the range
     * @return BiFunction
     */
    public static BiFunction<Double, Double, Double> percentageNegative(final Double start, final Double end) {
        return (base, percent) -> base * ((1 - start) - (Math.abs(end - start) * percent));
    }

    /**
     * Calculation based on a range. A percentage will be provided that determines the position between start and end.
     * The calculation will raise the result with the provided percentage(position).
     *
     * @param start start of the range
     * @param end   end of the range
     * @return BiFunction
     */
    public static BiFunction<Double, Double, Double> percentagePositive(final Double start, final Double end) {
        return (base, percent) -> base * ((1 + start) + (Math.abs(end - start) * percent));
    }

    /**
     * Calculation based on a fixed value. The calculation will lower the result with the provided percentage.
     *
     * @param value percentage to lower the result with
     * @return BiFunction
     */
    public static BiFunction<Double, Double, Double> percentageNegative(final Double value) {
        return (base, percent) -> base * (1 - value);
    }

    /**
     * Calculation based on a fixed value. The calculation will raise the result with the provided percentage.
     *
     * @param value percentage to raise the result with
     * @return BiFunction
     */
    public static BiFunction<Double, Double, Double> percentagePositive(final Double value) {
        return (base, percent) -> base * (1 + value);
    }

    /**
     * Calculation that adds a fixed amount.
     *
     * @param value value to increase with
     * @return BiFunction
     */
    public static BiFunction<Double, Double, Double> plus(final Double value) {
        return (base, percent) -> base + value;
    }

    /**
     * Calculation that subtracts a fixed amount.
     *
     * @param value value to subtract with
     * @return BiFunction
     */
    public static BiFunction<Double, Double, Double> minus(final Double value) {
        return (base, percent) -> base - value;
    }

    /**
     * Returns a boolean value as provided.
     *
     * @param value the boolean to return
     * @return BiFunction
     */
    public static BiFunction<Boolean, Double, Boolean> bool(final boolean value) {
        return (base, percent) -> value;
    }
}
