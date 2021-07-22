package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public class OverviewTab extends Tab {

    public OverviewTab() {
        super();
        this.textProperty().bind(LocaleService.getStringBinding("tabs.overview"));

        final ScrollPane scrollPane = new ScrollPane();
        final MaterialOverview materialOverview = new MaterialOverview(scrollPane);
        materialOverview.getStyleClass().add("card-grid");
        materialOverview.setSpacing(10);
        scrollPane.pannableProperty().set(true);
        scrollPane.setContent(materialOverview);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.setContent(scrollPane);

    }

}
