package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public class OverviewTab extends Tab {
    private final MaterialOverview materialOverview = new MaterialOverview();

    public OverviewTab() {
        super();
        this.textProperty().bind(LocaleService.getStringBinding("tabs.overview"));

        this.materialOverview.getStyleClass().add("card-grid");
        this.materialOverview.setSpacing(10);
        final ScrollPane scrollPane = new ScrollPane();
        scrollPane.pannableProperty().set(true);
        scrollPane.setContent(this.materialOverview);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.setContent(scrollPane);

    }

}
