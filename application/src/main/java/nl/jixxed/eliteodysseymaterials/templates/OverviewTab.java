package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.ScrollPane;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public class OverviewTab extends EDOTab {

    private static final double SCROLL_SPEED = 0.005;
    private ScrollPane scrollPane;
    private MaterialOverview materialOverview;

    OverviewTab() {
        initComponents();
    }

    private void initComponents() {
        this.textProperty().bind(LocaleService.getStringBinding("tabs.overview"));
        this.scrollPane = ScrollPaneBuilder.builder().build();
        this.materialOverview = new MaterialOverview(this.scrollPane);
        this.scrollPane.setContent(this.materialOverview);
        this.setContent(this.scrollPane);
        this.scrollPane.getContent().setOnScroll(scrollEvent -> {
            final double deltaY = scrollEvent.getDeltaY() * SCROLL_SPEED;
            this.scrollPane.setVvalue(this.scrollPane.getVvalue() - deltaY);
        });
    }


    @Override
    public OdysseyTabs getTabType() {
        return OdysseyTabs.OVERVIEW;
    }
}
