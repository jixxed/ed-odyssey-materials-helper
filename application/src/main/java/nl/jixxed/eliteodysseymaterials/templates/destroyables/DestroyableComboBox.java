package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;

import java.util.HashMap;
import java.util.List;

@SuppressWarnings("java:S3740")
public class DestroyableComboBox<T> extends ComboBox<T> implements DestroyableComponent {
    private final HashMap<ObservableValue, List<ChangeListener>> listenersMap = new HashMap<>();

    @Override
    public HashMap<ObservableValue, List<ChangeListener>> getListenersMap() {
        return this.listenersMap;
    }

    @Override
    public void destroyInternal() {
        this.promptTextProperty().unbind();
        this.itemsProperty().unbind();
    }
}
