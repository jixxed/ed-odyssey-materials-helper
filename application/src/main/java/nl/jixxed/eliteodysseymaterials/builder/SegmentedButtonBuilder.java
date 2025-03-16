package nl.jixxed.eliteodysseymaterials.builder;

import javafx.scene.control.ToggleButton;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableSegmentedButton;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SegmentedButtonBuilder extends AbstractControlBuilder<SegmentedButtonBuilder> {
    List<ToggleButton> buttons = new ArrayList<>();

    public static SegmentedButtonBuilder builder() {
        return new SegmentedButtonBuilder();
    }

    public SegmentedButtonBuilder withButtons(DestroyableToggleButton... buttons) {
        this.buttons.addAll(Arrays.asList(buttons));
        return this;
    }

    public SegmentedButtonBuilder withButtons(List<DestroyableToggleButton> buttons) {
        this.buttons.addAll(buttons);
        return this;
    }

    @SuppressWarnings("unchecked")
    public DestroyableSegmentedButton build() {
        DestroyableSegmentedButton segmentedButton = new DestroyableSegmentedButton();
        super.build(segmentedButton);
        segmentedButton.getButtons().addAll(this.buttons);

        return segmentedButton;
    }
}
