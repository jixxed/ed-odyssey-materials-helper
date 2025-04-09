package nl.jixxed.eliteodysseymaterials.builder;

import javafx.scene.control.ToggleGroup;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableToggleButton;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ToggleButtonBuilder extends AbstractButtonBaseBuilder<ToggleButtonBuilder> {

    private Boolean isSelected;
    private ToggleGroup toggleGroup;

    public static ToggleButtonBuilder builder() {
        return new ToggleButtonBuilder();
    }

    public ToggleButtonBuilder withSelected(boolean selected) {
        this.isSelected = selected;
        return this;
    }

    public ToggleButtonBuilder withToggleGroup(ToggleGroup toggleGroup) {
        this.toggleGroup = toggleGroup;
        return this;
    }

    @SuppressWarnings("unchecked")
    public DestroyableToggleButton build() {
        final DestroyableToggleButton button = new DestroyableToggleButton();
        super.build(button);
        if (this.isSelected != null) {
            button.setSelected(this.isSelected);
        }
        if (this.toggleGroup != null) {
            button.setToggleGroup(this.toggleGroup);
        }
        return button;
    }
}
