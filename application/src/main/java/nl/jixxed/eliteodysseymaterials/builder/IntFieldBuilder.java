package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.StringBinding;
import javafx.beans.value.ObservableValue;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.components.IntField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IntFieldBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private StringBinding promptTextBinding;
    private Boolean isTraversable;
    private Integer minValue;
    private Integer maxValue;
    private Integer initialValue;
    private ObservableValue<Boolean> visibilityObservable;

    public static IntFieldBuilder builder() {
        return new IntFieldBuilder();
    }

    public IntFieldBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public IntFieldBuilder withPromptTextProperty(final StringBinding promptTextBinding) {
        this.promptTextBinding = promptTextBinding;
        return this;
    }

    public IntFieldBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
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

    public IntFieldBuilder withFocusTraversable(final boolean isTraversable) {
        this.isTraversable = isTraversable;
        return this;
    }

    public IntFieldBuilder withVisibilityProperty(final ObservableValue<Boolean> observable) {
        this.visibilityObservable = observable;
        return this;
    }

    public IntField build() {
        final IntField intField = new IntField(this.minValue, this.maxValue, this.initialValue);
        intField.getStyleClass().addAll(this.styleClasses);
        if (this.promptTextBinding != null) {
            intField.promptTextProperty().bind(this.promptTextBinding);
        }
        if (this.isTraversable != null) {
            intField.setFocusTraversable(this.isTraversable);
        }
        if (this.visibilityObservable != null) {
            intField.visibleProperty().bind(this.visibilityObservable);
        }
        return intField;
    }

}
