package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ComboBoxBuilder<T> {
    private final List<String> styleClasses = new ArrayList<>();
    private final Class<T> clazz;
    private ObservableList<T> items;
    private ChangeListener<T> listener;
    private StringBinding promptTextBinding;
    private Tooltip tooltip;
    private boolean localized = false;

    public static <T> ComboBoxBuilder<T> builder(final Class<T> clazz) {
        return new ComboBoxBuilder<>(clazz);
    }

    public ComboBoxBuilder<T> withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public ComboBoxBuilder<T> asLocalized() {
        this.localized = true;
        return this;
    }

    public ComboBoxBuilder<T> withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public ComboBoxBuilder<T> withItemsProperty(final ObservableList<T> items) {
        this.items = items;
        return this;
    }

    public ComboBoxBuilder<T> withPromptTextProperty(final StringBinding promptTextBinding) {
        this.promptTextBinding = promptTextBinding;
        return this;
    }

    public ComboBoxBuilder<T> withValueChangeListener(final ChangeListener<T> listener) {
        this.listener = listener;
        return this;
    }


    public ComboBox<T> build() {
        final ComboBox<T> comboBox = new ComboBox<>();
        comboBox.getStyleClass().addAll(this.styleClasses);
        if (this.items != null) {
            comboBox.itemsProperty().set(new SimpleListProperty<>(this.items));
        }
        if (this.listener != null) {
            comboBox.valueProperty().addListener(this.listener);
        }
        if (this.localized) {
            //this sets the old value back after a locale change, instead of blanking it
            comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null && oldValue != null) {
                    comboBox.selectionModelProperty().getValue().select(oldValue);
                }
            });
        }
        if (this.promptTextBinding != null) {
            comboBox.promptTextProperty().bind(this.promptTextBinding);
        }

        if (this.tooltip != null) {
            comboBox.setTooltip(this.tooltip);
        }
        return comboBox;
    }

    public ComboBoxBuilder<T> withToolTip(final Tooltip tooltip) {
        this.tooltip = tooltip;
        return this;
    }
}
