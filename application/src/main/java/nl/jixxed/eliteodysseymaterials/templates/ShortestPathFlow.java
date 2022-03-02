package nl.jixxed.eliteodysseymaterials.templates;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.FlowPane;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.PathItem;

import java.util.List;
import java.util.stream.IntStream;

public class ShortestPathFlow extends FlowPane implements Template {
    @Getter
    private final ObservableList<ShortestPathItem> items = FXCollections.observableArrayList();

    ShortestPathFlow() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("shortest-path-flow");
    }

    @Override
    public void initEventHandling() {
        //NOOP
    }

    public void setItems(final List<PathItem> pathItems) {
        this.getChildren().clear();
        this.items.clear();
        this.items.addAll(IntStream.range(0, pathItems.size()).mapToObj(index -> new ShortestPathItem(pathItems.get(index), index + 1)).toList());
        this.getChildren().addAll(this.items);
    }
}
