package nl.jixxed.eliteodysseymaterials.templates.horizons.engineers;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsTab;

import java.util.Arrays;

public class HorizonsEngineersTab extends HorizonsTab {

    private ScrollPane scrollPane;
    private FlowPane flowPane;

    public HorizonsEngineersTab() {
        initComponents();
    }

    private void initComponents() {
        this.textProperty().bind(LocaleService.getStringBinding("tabs.engineers"));
        final HorizonsEngineerCard[] horizonsEngineerCards = Arrays.stream(Engineer.values())
                .filter(Engineer::isHorizons)
                .filter(engineer -> !Engineer.UNKNOWN.equals(engineer))
                .map(HorizonsEngineerCard::new)
                .toArray(HorizonsEngineerCard[]::new);
        this.flowPane = FlowPaneBuilder.builder()
                .withStyleClass("engineer-grid")
                .withNodes(horizonsEngineerCards)
                .build();
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(this.flowPane)
                .build();
        this.setContent(this.scrollPane);
    }

    @Override
    public HorizonsTabs getTabType() {
        return HorizonsTabs.ENGINEERS;
    }
}
