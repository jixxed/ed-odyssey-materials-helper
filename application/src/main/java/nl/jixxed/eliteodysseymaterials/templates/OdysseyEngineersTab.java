package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Arrays;

public class OdysseyEngineersTab extends EDOTab {

    private ScrollPane scrollPane;
    private FlowPane flowPane;

    OdysseyEngineersTab() {
        initComponents();
    }

    private void initComponents() {
        this.textProperty().bind(LocaleService.getStringBinding("tabs.engineers"));
        final OdysseyEngineerCard[] odysseyEngineerCards = Arrays.stream(Engineer.values())
                .filter(Engineer::isOdyssey)
                .filter(engineer -> !Engineer.UNKNOWN.equals(engineer))
                .map(OdysseyEngineerCard::new)
                .toArray(OdysseyEngineerCard[]::new);
        this.flowPane = FlowPaneBuilder.builder()
                .withStyleClass("engineer-grid")
                .withNodes(odysseyEngineerCards)
                .build();
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(this.flowPane)
                .build();
        this.setContent(this.scrollPane);
    }

    @Override
    public OdysseyTabs getTabType() {
        return OdysseyTabs.ENGINEERS;
    }
}
