package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import nl.jixxed.eliteodysseymaterials.enums.Tabs;

public abstract class EDOTab extends Tab {
    public abstract Tabs getTabType();


    void setupScrollPane(final ScrollPane scrollPane) {
        scrollPane.pannableProperty().set(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }
}
