package nl.jixxed.eliteodysseymaterials.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LabelBuilder extends AbstractLabeledBuilder<LabelBuilder> {

    public static LabelBuilder builder() {
        return new LabelBuilder();
    }

    public DestroyableLabel build() {
        final DestroyableLabel label = new DestroyableLabel();
        super.build(label);
        return label;
    }
}
