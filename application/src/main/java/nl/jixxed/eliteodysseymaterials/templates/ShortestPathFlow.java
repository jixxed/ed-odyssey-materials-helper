package nl.jixxed.eliteodysseymaterials.templates;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.FlowPane;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.PathItem;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;

import java.util.List;
import java.util.stream.IntStream;

public class ShortestPathFlow<T extends BlueprintName<T>> extends FlowPane implements Template {
    @Getter
    private final ObservableList<ShortestPathItem<T>> items = FXCollections.observableArrayList();
    private final Expansion expansion;

    ShortestPathFlow(final Expansion expansion) {
        this.expansion = expansion;
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

    public void setItems(final List<PathItem<T>> pathItems) {
        this.getChildren().clear();
        this.items.clear();
        this.items.addAll(IntStream.range(0, pathItems.size()).mapToObj(index -> new ShortestPathItem<>(pathItems.get(index), index + 1, this.expansion)).toList());
        this.getChildren().addAll(this.items);
    }
}
