package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.ScrollPane;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public class HorizonsMaterialOverviewTab extends EDHTab implements Template {
    private ScrollPane scrollPane;
    private HorizonsMaterialOverview materialOverview;

    HorizonsMaterialOverviewTab() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.materialOverview = new HorizonsMaterialOverview();
        this.textProperty().bind(LocaleService.getStringBinding("tabs.horizons.materials"));
        this.setClosable(false);
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(this.materialOverview)
                .build();
        this.setContent(this.scrollPane);

    }

    @Override
    public void initEventHandling() {
        //NOOP
    }

    @Override
    public HorizonsTabs getTabType() {
        return HorizonsTabs.MATERIALS;
    }
}
