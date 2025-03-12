package nl.jixxed.eliteodysseymaterials.templates.horizons.commodities;

import javafx.scene.control.ScrollPane;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsTab;

public class HorizonsCommoditiesOverviewTab extends HorizonsTab implements DestroyableTemplate {
    private ScrollPane scrollPane;
    private HorizonsCommoditiesOverview commoditiesOverview;

    public HorizonsCommoditiesOverviewTab() {
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
