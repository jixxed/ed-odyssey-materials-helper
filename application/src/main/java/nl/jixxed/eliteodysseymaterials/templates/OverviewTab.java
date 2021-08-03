package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.ScrollPane;
import nl.jixxed.eliteodysseymaterials.enums.Tabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public class OverviewTab extends EDOTab {

    OverviewTab() {
        super();
        this.textProperty().bind(LocaleService.getStringBinding("tabs.overview"));

        final ScrollPane scrollPane = new ScrollPane();
        final MaterialOverview materialOverview = new MaterialOverview(scrollPane);
        scrollPane.pannableProperty().set(true);
        scrollPane.setContent(materialOverview);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.setContent(scrollPane);

    }

    @Override
    public Tabs getTabType() {
        return Tabs.OVERVIEW;
    }
}
