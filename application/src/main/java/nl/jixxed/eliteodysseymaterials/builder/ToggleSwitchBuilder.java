package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableToggleSwitch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ToggleSwitchBuilder {

    private StringBinding stringBinding;
    private boolean selected = false;
    private String nonLocalizedText;
    private final List<String> styleClasses = new ArrayList<>();
    private ChangeListener<Boolean> listener;

    public static ToggleSwitchBuilder builder() {
        return new ToggleSwitchBuilder();
    }

    public ToggleSwitchBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public ToggleSwitchBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public ToggleSwitchBuilder withText(final StringBinding stringBinding) {
        this.stringBinding = stringBinding;
        return this;
    }

    public ToggleSwitchBuilder withSelected(final boolean selected) {
        this.selected = selected;
        return this;
    }

    public ToggleSwitchBuilder withNonLocalizedText(final String nonLocalizedText) {
        this.nonLocalizedText = nonLocalizedText;
        return this;
    }

    public ToggleSwitchBuilder withSelectedChangeListener(final ChangeListener<Boolean> listener) {
        this.listener = listener;
        return this;
    }

    public DestroyableToggleSwitch build() {
        final DestroyableToggleSwitch toggleSwitch = new DestroyableToggleSwitch();
        toggleSwitch.getStyleClass().addAll(this.styleClasses);
        if (this.stringBinding != null) {
            toggleSwitch.textProperty().bind(this.stringBinding);
        } else if (this.nonLocalizedText != null) {
            toggleSwitch.setText(this.nonLocalizedText);
        }
        toggleSwitch.setSelected(this.selected);
        if (this.listener != null) {
            toggleSwitch.addChangeListener(toggleSwitch.selectedProperty(), this.listener);
        }
        return toggleSwitch;
    }
}
