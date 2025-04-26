package nl.jixxed.eliteodysseymaterials.helper;

import javafx.scene.Node;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VisibilityHelper {
    public static void hide(Node node) {
        setVisible(node, false);
    }

    public static void show(Node node) {
        setVisible(node, true);
    }

    public static void setVisible(Node node, boolean visible) {
        node.setVisible(visible);
        node.setManaged(visible);
    }

}
