package nl.jixxed.eliteodysseymaterials.templates.horizons.commodities;

import javafx.scene.control.ScrollPane;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsTab;

public class HorizonsCommoditiesOverviewTab extends HorizonsTab implements DestroyableTemplate {

    public HorizonsCommoditiesOverviewTab() {
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("commodities-tab");
        HorizonsCommoditiesOverview commoditiesOverview = new HorizonsCommoditiesOverview();
        this.addBinding(this.textProperty(), LocaleService.getStringBinding("tabs.horizons.commodities"));
        this.setClosable(false);
        ScrollPane scrollPane = register(ScrollPaneBuilder.builder()
                .withStyleClass("commodities-tab-content")
                .withContent(commoditiesOverview)
                .build());
        this.setContent(scrollPane);

    }

    @Override
    public HorizonsTabType getTabType() {
        return HorizonsTabType.COMMODITIES;
    }
}
