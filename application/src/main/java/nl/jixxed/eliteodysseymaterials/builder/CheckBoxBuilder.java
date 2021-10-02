package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.CheckBox;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckBoxBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private StringBinding stringBinding;
    private ChangeListener<Boolean> changeListener;
    private String nonLocalizedText;
    private boolean visibility = true;
    private boolean enabled = false;

    public static CheckBoxBuilder builder() {
        return new CheckBoxBuilder();
    }

    public CheckBoxBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public CheckBoxBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public CheckBoxBuilder withText(final StringBinding stringBinding) {
        this.stringBinding = stringBinding;
        return this;
    }

    public CheckBoxBuilder withVisibility(final boolean visibility) {
        this.visibility = visibility;
        return this;
    }

    public CheckBoxBuilder withNonLocalizedText(final String nonLocalizedText) {
        this.nonLocalizedText = nonLocalizedText;
        return this;
    }

    public CheckBoxBuilder withValue(final boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public CheckBoxBuilder withChangeListener(final ChangeListener<Boolean> changeListener) {
        this.changeListener = changeListener;
        return this;
    }

    public CheckBox build() {
        final CheckBox checkBox = new CheckBox();
        checkBox.getStyleClass().addAll(this.styleClasses);
        if (this.stringBinding != null) {
            checkBox.textProperty().bind(this.stringBinding);
        } else if (this.nonLocalizedText != null) {
            checkBox.setText(this.nonLocalizedText);
        }
        if (this.changeListener != null) {
            checkBox.selectedProperty().addListener(this.changeListener);
        }
        checkBox.setVisible(this.visibility);
        checkBox.setSelected(this.enabled);
        return checkBox;
    }

}
