package nl.jixxed.eliteodysseymaterials.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableToggleButton;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ToggleButtonBuilder extends AbstractButtonBaseBuilder<ToggleButtonBuilder> {

    public static ToggleButtonBuilder builder() {
        return new ToggleButtonBuilder();
    }

    @SuppressWarnings("unchecked")
    public DestroyableToggleButton build() {
        final DestroyableToggleButton button = new DestroyableToggleButton();
        super.build(button);
        return button;
    }

}
