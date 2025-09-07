package nl.jixxed.eliteodysseymaterials.builder;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.layout.Region;
import javafx.util.StringConverter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComboBox;
@Slf4j
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
        comboBox.addChangeListener(comboBox.itemsProperty(), (_,_,_) -> {
            comboBox.setVisibleRowCount(Math.min(comboBox.getItems().size(), 10));
            Platform.runLater(() -> {
                if (comboBox.getSkin() instanceof ComboBoxListViewSkin<?> skin) {
                    log.debug("Requesting layout for combobox {}", comboBox);
                    ((ListView)skin.getPopupContent()).requestLayout();
                    ((ListView)skin.getPopupContent()).autosize();
                }
            });
//            comboBox.show();
//            Platform.runLater(comboBox::hide);

        });
//        comboBox.onShownProperty()
        return comboBox;
    }
}
