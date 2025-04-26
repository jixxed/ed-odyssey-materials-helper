package nl.jixxed.eliteodysseymaterials.builder;

import javafx.util.StringConverter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComboBox;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ComboBoxBuilder<T> extends AbstractComboBoxBuilder<T, ComboBoxBuilder<T>> {
    private final Class<T> clazz;
    private StringConverter<T> stringConverter;

    public static <T> ComboBoxBuilder<T> builder(final Class<T> clazz) {
        return new ComboBoxBuilder<>(clazz);
    }

    public ComboBoxBuilder<T> withConverter(StringConverter<T> stringConverter) {

        this.stringConverter = stringConverter;
        return this;
    }

    @SuppressWarnings("unchecked")
    public DestroyableComboBox<T> build() {
        final DestroyableComboBox<T> comboBox = new DestroyableComboBox<>();
        super.build(comboBox);
        if(stringConverter != null) {
            comboBox.converterProperty().set(stringConverter);
        }
        return comboBox;
    }
}
