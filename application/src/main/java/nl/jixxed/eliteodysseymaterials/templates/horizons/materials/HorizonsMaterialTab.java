package nl.jixxed.eliteodysseymaterials.templates.horizons.materials;

import javafx.scene.control.ScrollPane;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsTab;

public class HorizonsMaterialTab extends HorizonsTab implements DestroyableTemplate {

    public HorizonsMaterialTab() {
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("materials-tab");
        HorizonsMaterialOverview materialOverview = new HorizonsMaterialOverview();
        this.addBinding(this.textProperty(), LocaleService.getStringBinding("tabs.horizons.materials"));
        this.setClosable(false);
        ScrollPane scrollPane = register(ScrollPaneBuilder.builder()
                .withStyleClass("materials-tab-content")
                .withContent(materialOverview)
                .build());
        this.setContent(scrollPane);

    }

    @Override
    public HorizonsTabs getTabType() {
        return HorizonsTabs.MATERIALS;
    }
}
