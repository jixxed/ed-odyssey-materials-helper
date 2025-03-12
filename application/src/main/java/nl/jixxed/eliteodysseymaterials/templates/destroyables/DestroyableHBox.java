package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import lombok.Getter;
@Getter
public class DestroyableHBox extends HBox implements DestroyableComponent {
    @Override
    public ObservableList<Node> getChildren() {
        return super.getChildren();
    }
}
