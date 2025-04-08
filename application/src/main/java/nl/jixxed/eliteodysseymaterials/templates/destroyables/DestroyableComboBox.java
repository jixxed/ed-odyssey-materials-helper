package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.scene.control.ComboBox;
import lombok.Getter;

import java.util.List;

@Getter
public class DestroyableComboBox<T> extends ComboBox<T> implements DestroyableComponent {

    public void addAll(List<DestroyableMenuItem> menuItems) {
        registerAll(menuItems);
        this.getItems().addAll((List<T>) menuItems);
    }

    public void add(DestroyableMenuItem menuItem) {
        this.getItems().addAll((List<T>) register(menuItem));
    }

    public void clear() {
        getItems().stream().map(DestroyableMenuItem.class::cast).forEach(DestroyableMenuItem::destroy);
    }
}
