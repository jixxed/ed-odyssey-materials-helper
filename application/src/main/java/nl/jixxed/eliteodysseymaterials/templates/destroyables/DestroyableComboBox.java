package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import lombok.Getter;

import java.util.List;

@Getter
public class DestroyableComboBox<T> extends ComboBox<T> implements DestroyableComponent {

    public void addAll(List<T> items) {
        items.stream()
                .filter(Destroyable.class::isInstance)
                .map(Destroyable.class::cast)
                .forEach(this::register);
        this.getItems().addAll(items);
        Platform.runLater(() -> {
            if (this.getSkin() instanceof ComboBoxListViewSkin<?> skin) {
                ((ListView) skin.getPopupContent()).requestLayout();
            }
        });
    }

    public void add(T menuItem) {
        addAll(List.of(menuItem));
    }

    public void clear() {
        getItems().stream().filter(Destroyable.class::isInstance).map(Destroyable.class::cast).forEach(Destroyable::destroy);
        getItems().clear();
    }

    @Override
    public void destroyInternal() {
        DestroyableComponent.super.destroyInternal();
        this.setCellFactory(null);
//        this.getItems().clear();
    }
}
