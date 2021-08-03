package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.StringBinding;
import javafx.scene.control.Label;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LabelBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private StringBinding stringBinding;

    public static LabelBuilder builder() {
        return new LabelBuilder();
    }

    public LabelBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public LabelBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public LabelBuilder withText(final StringBinding stringBinding) {
        this.stringBinding = stringBinding;
        return this;
    }

    public Label build() {
        final Label label = new Label();
        label.getStyleClass().addAll(this.styleClasses);
        if (this.stringBinding != null) {
            label.textProperty().bind(this.stringBinding);
        }
        return label;
    }

}
