package nl.jixxed.eliteodysseymaterials.domain;

import java.util.Objects;

public class HorizonsModifierValue<T> {
    private final String modification;
    private final boolean isPositive;

    public HorizonsModifierValue(final String modification, final boolean isPositive) {
        this.modification = modification;
        this.isPositive = isPositive;
    }

    public String modification() {
        return this.modification;
    }

    public boolean isPositive() {
        return this.isPositive;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        final var that = (HorizonsModifierValue) obj;
        return Objects.equals(this.modification, that.modification) &&
                this.isPositive == that.isPositive;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.modification, this.isPositive);
    }

    @Override
    public String toString() {
        return "HorizonsModifierValue[" +
                "modification=" + this.modification + ", " +
                "isPositive=" + this.isPositive + ']';
    }

    //    public abstract  T getModifiedValue(final T baseValue, final Double percentComplete);
    public Object getModifiedValue(final T baseValue, final Double percentComplete) {
        return baseValue;
    }
}
