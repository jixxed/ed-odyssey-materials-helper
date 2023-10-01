package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class HorizonsBooleanModifierValue extends HorizonsModifierValue<Boolean> {
    @Getter
    private final HorizonsBiFunction modifier;

//    public HorizonsBooleanModifierValue(final String modification, final boolean isPositive) {
//        super(modification, isPositive);
//        this.modifier = (a, b) -> a;
//    }

    public HorizonsBooleanModifierValue(final String modification, final boolean isPositive, final HorizonsBiFunction modifier) {
        super(modification, isPositive);
        this.modifier = modifier;
    }

}
