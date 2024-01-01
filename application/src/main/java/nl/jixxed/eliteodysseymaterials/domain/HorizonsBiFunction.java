package nl.jixxed.eliteodysseymaterials.domain;

import io.reactivex.rxjava3.functions.BiFunction;

public class HorizonsBiFunction<T> {
    private boolean bool;
    private double value;
    private double start;
    private double end;
    CalculationType calculationType;

    public HorizonsBiFunction(Double value, Double start, Double end, CalculationType calculationType) {
        this.value = value;
        this.start = start;
        this.end = end;
        this.calculationType = calculationType;
    }

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
            case FALLOFF_PERCENTAGE_POSITIVE ->
                    (BiFunction<T, Double, T>) (BiFunction<Double, Double, Double>) (base, percent) -> (base + value) * ((1D + start) + (Math.abs(end - start) * percent));
            case RESISTANCE_NEGATIVE ->
                    (BiFunction<T, Double, T>) (BiFunction<Double, Double, Double>) (base, percent) -> 1 - ((1 - base) * ((1D + start) + (Math.abs(end - start) * percent)));
            case RESISTANCE_POSITIVE ->
                    (BiFunction<T, Double, T>) (BiFunction<Double, Double, Double>) (base, percent) -> 1 - ((1 - base) * ((1D - start) - (Math.abs(end - start) * percent)));
            case PERCENTAGE_POSITIVE ->
                    (BiFunction<T, Double, T>) (BiFunction<Double, Double, Double>) (base, percent) -> base * ((1D + start) + (Math.abs(end - start) * percent));
            case PERCENTAGE_NEGATIVE ->
                    (BiFunction<T, Double, T>) (BiFunction<Double, Double, Double>) (base, percent) -> base * ((1D - start) - (Math.abs(end - start) * percent));
            case PLUS ->
                    (BiFunction<T, Double, T>) (BiFunction<Double, Double, Double>) (base, percent) -> base + value;
            case MINUS ->
                    (BiFunction<T, Double, T>) (BiFunction<Double, Double, Double>) (base, percent) -> base - value;
            case BOOL -> (BiFunction<T, Double, T>) (BiFunction<Boolean, Double, Boolean>) (base, percent) -> bool;
        };
    }

    public HorizonsBiFunction stack(HorizonsBiFunction other) {
        HorizonsBiFunction toReturn = new HorizonsBiFunction(this.start, this.end, this.calculationType);
        toReturn.value = this.value;
        toReturn.bool = this.bool;
//        if (this.calculationType != other.calculationType) {
//            //todo not sure if this stacking is OK
////            throw new IllegalArgumentException("calculation types do not match! " + calculationType +"/" +other.calculationType);
//            toReturn.start -= other.start;
//            toReturn.end -= other.end;
//            toReturn.value -= other.value;
//            toReturn.bool = toReturn.bool || other.bool;
//        }
        toReturn.start += other.start;
        toReturn.end += other.end;
        toReturn.value += other.value;
        toReturn.bool = toReturn.bool || other.bool;
        return toReturn;
    }

    public enum CalculationType {
        PERCENTAGE_POSITIVE, PERCENTAGE_NEGATIVE, PLUS, MINUS, BOOL, FALLOFF_PERCENTAGE_POSITIVE, RESISTANCE_NEGATIVE, RESISTANCE_POSITIVE
    }
}
