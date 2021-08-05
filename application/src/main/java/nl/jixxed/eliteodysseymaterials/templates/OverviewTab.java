package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.ScrollPane;
import nl.jixxed.eliteodysseymaterials.enums.Tabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public class OverviewTab extends EDOTab {

    private ScrollPane scrollPane;
    private MaterialOverview materialOverview;

    OverviewTab() {
        initComponents();
    }

    private void initComponents() {
        this.textProperty().bind(LocaleService.getStringBinding("tabs.overview"));
        this.scrollPane = new ScrollPane();
        setupScrollPane(this.scrollPane);
        this.materialOverview = new MaterialOverview(this.scrollPane);
        this.scrollPane.setContent(this.materialOverview);
        this.setContent(this.scrollPane);
    }


    @Override
    public Tabs getTabType() {
        return Tabs.OVERVIEW;
    }
}
