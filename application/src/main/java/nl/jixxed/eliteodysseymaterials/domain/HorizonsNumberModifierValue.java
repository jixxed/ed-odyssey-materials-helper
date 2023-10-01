package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class HorizonsNumberModifierValue extends HorizonsModifierValue<Double> {
    @Getter
    private final HorizonsBiFunction modifier;

//    public HorizonsNumberModifierValue(final String modification, final boolean isPositive) {
//        super(modification, isPositive);
//        this.modifier = (a,b) -> a;
//    }
    public HorizonsNumberModifierValue(final String modification, final boolean isPositive, final HorizonsBiFunction modifier) {
        super(modification, isPositive);
        this.modifier = modifier;
    }

}
