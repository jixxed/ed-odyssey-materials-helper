package nl.jixxed.eliteodysseymaterials.domain;

import io.reactivex.rxjava3.functions.BiFunction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class HorizonsNumberModifierValue extends HorizonsModifierValue<Double> {
    private final BiFunction<Double, Double, Double> modifier;

    public HorizonsNumberModifierValue(final String modification, final boolean isPositive) {
        super(modification, isPositive);
        this.modifier = (a,b) -> a;
    }
    public HorizonsNumberModifierValue(final String modification, final boolean isPositive, final BiFunction<Double, Double, Double> modifier) {
        super(modification, isPositive);
        this.modifier = modifier;
    }
    @Override
    public Double getModifiedValue(final Double baseValue, final Double percentComplete) {
        try {
            return this.modifier.apply(baseValue, percentComplete);
        } catch (final Throwable t) {
            log.error("Error modifying value", t);
        }
        return baseValue;
    }

}
