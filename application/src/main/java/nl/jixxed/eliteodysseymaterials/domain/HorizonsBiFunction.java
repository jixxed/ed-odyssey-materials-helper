package nl.jixxed.eliteodysseymaterials.domain;

import io.reactivex.rxjava3.functions.BiFunction;

public class HorizonsBiFunction<T> {
    private boolean bool;
    private double value;
    private double start;
    private double end;
    CalculationType calculationType;

    public HorizonsBiFunction(Double start, Double end, CalculationType calculationType) {
        this.start = start;
        this.end = end;
        this.calculationType = calculationType;
    }

    public HorizonsBiFunction(Double value, CalculationType calculationType) {
        this.value = value;
        this.calculationType = calculationType;
    }

    public HorizonsBiFunction(Boolean bool, CalculationType calculationType) {
        this.bool = bool;
        this.calculationType = calculationType;
    }

    public final BiFunction<T, Double, T> getFunction() {
        return switch (calculationType) {
            case PERCENTAGE_POSITIVE -> (BiFunction<T, Double, T>)(BiFunction<Double, Double, Double>) (base, percent) -> base * ((1D + start) + (Math.abs(end - start) * percent));
            case PERCENTAGE_NEGATIVE -> (BiFunction<T, Double, T>)(BiFunction<Double, Double, Double>)(base, percent) -> base * ((1D - start) - (Math.abs(end - start) * percent));
            case PLUS -> (BiFunction<T, Double, T>)(BiFunction<Double, Double, Double>) (base, percent) -> base + value;
            case MINUS -> (BiFunction<T, Double, T>)(BiFunction<Double, Double, Double>) (base, percent) -> base - value;
            case BOOL -> (BiFunction<T, Double, T>)(BiFunction<Boolean, Double, Boolean>) (base, percent) -> bool;
        };
    }

    public HorizonsBiFunction stack(HorizonsBiFunction other) {
        HorizonsBiFunction toReturn = new HorizonsBiFunction(this.start, this.end, this.calculationType);
        toReturn.value = this.value;
        toReturn.bool = this.bool;
        if (this.calculationType != other.calculationType) {
            throw new IllegalArgumentException("calculation types do not match!");
        }
        toReturn.start += other.start;
        toReturn.end += other.end;
        toReturn.value += other.value;
        toReturn.bool = toReturn.bool || other.bool;
        return toReturn;
    }

    //    public static BiFunction<Double, Double, Double> percentagePositive(final Double start, final Double end) {
//        return (base, percent) -> base * ((1 + start) + (Math.abs(end - start) * percent));
//    }
//    public static BiFunction<Double, Double, Double> minus(final Double value) {
//        return (base, percent) -> base - value;
//    }
//    public static BiFunction<Boolean, Double, Boolean> bool(final boolean value) {
//        return (base, percent) -> value;
//    }
    public enum CalculationType {
        PERCENTAGE_POSITIVE,PERCENTAGE_NEGATIVE, PLUS,  MINUS, BOOL
    }
}
