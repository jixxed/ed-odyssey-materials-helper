package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.value.ChangeListener;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableToggleSwitch;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ToggleSwitchBuilder extends AbstractLabeledBuilder<ToggleSwitchBuilder> {

    private boolean selected = false;
    private ChangeListener<Boolean> listener;

    public static ToggleSwitchBuilder builder() {
        return new ToggleSwitchBuilder();
    }


    public ToggleSwitchBuilder withSelected(final boolean selected) {
        this.selected = selected;
        return this;
    }

    public ToggleSwitchBuilder withSelectedChangeListener(final ChangeListener<Boolean> listener) {
        this.listener = listener;
        return this;
    }

    public DestroyableToggleSwitch build() {
        final DestroyableToggleSwitch toggleSwitch = new DestroyableToggleSwitch();
        super.build(toggleSwitch);

        toggleSwitch.setSelected(this.selected);

        if (this.listener != null) {
            toggleSwitch.addChangeListener(toggleSwitch.selectedProperty(), this.listener);
        }
        return toggleSwitch;
    }
}
