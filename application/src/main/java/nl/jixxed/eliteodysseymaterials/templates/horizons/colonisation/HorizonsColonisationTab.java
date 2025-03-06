package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import javafx.scene.control.ScrollPane;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsTab;

public class HorizonsColonisationTab extends HorizonsTab implements Template {
    private ScrollPane scrollPane;
    private ColonisationView colonisationView;

    public HorizonsColonisationTab() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.colonisationView = new ColonisationView();
        this.textProperty().bind(LocaleService.getStringBinding("tabs.horizons.colonisation"));
        this.setClosable(false);
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(this.colonisationView)
                .build();
        this.setContent(this.scrollPane);

    }

    @Override
    public void initEventHandling() {
        //NOOP
    }

    @Override
    public HorizonsTabs getTabType() {
        return HorizonsTabs.COLONISATION;
    }
}
