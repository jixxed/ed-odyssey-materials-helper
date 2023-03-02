package nl.jixxed.eliteodysseymaterials.domain;

import io.reactivex.rxjava3.functions.BiFunction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class HorizonsBooleanModifierValue extends HorizonsModifierValue<Boolean> {
    private final BiFunction<Boolean, Double, Boolean> modifier;

    public HorizonsBooleanModifierValue(final String modification, final boolean isPositive) {
        super(modification, isPositive);
        this.modifier = (a, b) -> a;
    }

    public HorizonsBooleanModifierValue(final String modification, final boolean isPositive, final BiFunction<Boolean, Double, Boolean> modifier) {
        super(modification, isPositive);
        this.modifier = modifier;
    }

    @Override
    public Boolean getModifiedValue(final Boolean baseValue, final Double percentComplete) {
        try {
            return this.modifier.apply( baseValue, percentComplete);
        } catch (final Throwable t) {
            log.error("Error modifying value", t);
        }
        return  baseValue;
    }

}
