package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.Tabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Arrays;

public class EngineersTab extends EDOTab {

    private ScrollPane scrollPane;
    private FlowPane flowPane;

    EngineersTab() {
        initComponents();
    }

    private void initComponents() {
        this.textProperty().bind(LocaleService.getStringBinding("tabs.engineers"));
        this.scrollPane = new ScrollPane();
        setupScrollPane(this.scrollPane);
        final EngineerCard[] engineerCards = Arrays.stream(Engineer.values())
                .filter(engineer -> !Engineer.UNKNOWN.equals(engineer))
                .map(EngineerCard::new)
                .toArray(EngineerCard[]::new);
        this.flowPane = FlowPaneBuilder.builder()
                .withStyleClass("engineer-grid")
                .withNodes(engineerCards)
                .build();
        this.scrollPane.setContent(this.flowPane);
        this.setContent(this.scrollPane);
    }

    @Override
    public Tabs getTabType() {
        return Tabs.ENGINEERS;
    }
}
