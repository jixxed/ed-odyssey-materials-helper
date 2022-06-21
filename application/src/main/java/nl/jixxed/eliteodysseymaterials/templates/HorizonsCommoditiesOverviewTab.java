package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.ScrollPane;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public class HorizonsCommoditiesOverviewTab extends EDHTab implements Template {
    private ScrollPane scrollPane;
    private HorizonsCommoditiesOverview commoditiesOverview;

    HorizonsCommoditiesOverviewTab() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.commoditiesOverview = new HorizonsCommoditiesOverview();
        this.textProperty().bind(LocaleService.getStringBinding("tabs.horizons.commodities"));
        this.setClosable(false);
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(this.commoditiesOverview)
                .build();
        this.setContent(this.scrollPane);

    }

    @Override
    public void initEventHandling() {
        //NOOP
    }

    @Override
    public HorizonsTabs getTabType() {
        return HorizonsTabs.COMMODITIES;
    }
}
