package nl.jixxed.eliteodysseymaterials.helper;

import io.reactivex.rxjava3.functions.BiFunction;

public class ModifierFunctionHelper {
    public static BiFunction<Double,Double,Double> percentageNegative(final Double start, final Double end){
        return (base, percent)-> base * ((1 - start) - (Math.abs(end - start) * percent));
    }
//    base, percent)-> base * (1 - (0.1 * percent))
    public static BiFunction<Double,Double,Double> percentagePositive(final Double start, final Double end){
        return (base, percent)-> base * ((1 + start) + (Math.abs(end - start) * percent));
    }
}
