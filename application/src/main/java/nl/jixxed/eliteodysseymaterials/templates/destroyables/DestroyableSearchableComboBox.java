package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.controlsfx.control.SearchableComboBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("java:S3740")
public class DestroyableSearchableComboBox<T> extends SearchableComboBox<T> implements DestroyableComponent {
    private final HashMap<ObservableValue, List<ChangeListener>> listenersMap = new HashMap<>();

    @Override
    public Map<ObservableValue, List<ChangeListener>> getListenersMap() {
        return this.listenersMap;
    }

    @Override
    public void destroyInternal() {
        this.promptTextProperty().unbind();
        this.itemsProperty().unbind();
    }
}
