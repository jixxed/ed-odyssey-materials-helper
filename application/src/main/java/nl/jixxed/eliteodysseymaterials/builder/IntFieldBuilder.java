package nl.jixxed.eliteodysseymaterials.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.components.IntField;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IntFieldBuilder extends AbstractTextInputControlBuilder<IntFieldBuilder> {
    private Integer minValue;
    private Integer maxValue;
    private Integer initialValue;

    public static IntFieldBuilder builder() {
        return new IntFieldBuilder();
    }

    public IntFieldBuilder withMinValue(final Integer minValue) {
        this.minValue = minValue;
        return this;
    }

    public IntFieldBuilder withMaxValue(final Integer maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    public IntFieldBuilder withInitialValue(final Integer initialValue) {
        this.initialValue = initialValue;
        return this;
    }

    @SuppressWarnings("unchecked")
    public IntField build() {
        final IntField intField = new IntField(this.minValue, this.maxValue, this.initialValue);
        super.build(intField);
        return intField;
    }

}
