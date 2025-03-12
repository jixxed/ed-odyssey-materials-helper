package nl.jixxed.eliteodysseymaterials.builder;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComboBox;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ComboBoxBuilder<T> extends AbstractComboBoxBuilder<T, ComboBoxBuilder<T>> {
    private final Class<T> clazz;

    public static <T> ComboBoxBuilder<T> builder(final Class<T> clazz) {
        return new ComboBoxBuilder<>(clazz);
    }

    @SuppressWarnings("unchecked")
    public DestroyableComboBox<T> build() {
        final DestroyableComboBox<T> comboBox = new DestroyableComboBox<>();
        super.build(comboBox);
        return comboBox;
    }
}
