package nl.jixxed.eliteodysseymaterials.helper;

import javafx.scene.Scene;

public class DebugUtils {
    public static void printSceneGraph(Scene scene) {
        printNode(scene.getRoot(), 0);
    }

    private static void printNode(javafx.scene.Node node, int depth) {
        String indent = " ".repeat(depth * 2);
        System.out.println(indent + node.getClass().getSimpleName());
        if (node instanceof javafx.scene.Parent) {
            ((javafx.scene.Parent) node).getChildrenUnmodifiable().forEach(child -> printNode(child, depth + 1));
        }
    }
}