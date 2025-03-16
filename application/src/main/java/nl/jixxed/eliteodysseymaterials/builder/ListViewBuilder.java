package nl.jixxed.eliteodysseymaterials.builder;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableListView;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ListViewBuilder<T> extends AbstractControlBuilder<ListViewBuilder<T>> {
    private final Class<T> clazz;
    private Callback<ListView<T>, ListCell<T>> cellFactory;
    private ObservableList<T> items;

    public static <T> ListViewBuilder<T> builder(final Class<T> clazz) {
        return new ListViewBuilder<>(clazz);
    }

    public ListViewBuilder<T> withCellFactory(Callback<ListView<T>, ListCell<T>> cellFactory) {
        this.cellFactory = cellFactory;
        return this;
    }

    public ListViewBuilder<T> withItems(ObservableList<T> items) {
        this.items = items;
        return this;
    }

    public DestroyableListView<T> build() {
        final DestroyableListView<T> listView = new DestroyableListView<>();
        super.build(listView);

        if (this.cellFactory != null) {
            listView.setCellFactory(this.cellFactory);
        }

        if (this.items != null) {
            listView.setItems(this.items);
        }

        return listView;
    }
}
