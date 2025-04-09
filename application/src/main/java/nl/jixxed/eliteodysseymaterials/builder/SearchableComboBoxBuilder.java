package nl.jixxed.eliteodysseymaterials.builder;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableSearchableComboBox;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchableComboBoxBuilder<T> extends AbstractComboBoxBuilder<T, SearchableComboBoxBuilder<T>>{
    private final Class<T> clazz;

    public static <T> SearchableComboBoxBuilder<T> builder(final Class<T> clazz) {
        return new SearchableComboBoxBuilder<>(clazz);
    }

    @SuppressWarnings("unchecked")
    public DestroyableSearchableComboBox<T> build() {
        final DestroyableSearchableComboBox<T> comboBox = new DestroyableSearchableComboBox<>();
        super.build(comboBox);
        return comboBox;
    }

}
