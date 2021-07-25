package nl.jixxed.eliteodysseymaterials.templates;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.Tabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EngineersTab extends EDOTab {

    public EngineersTab() {
        super();
        this.textProperty().bind(LocaleService.getStringBinding("tabs.engineers"));

        final ScrollPane scrollPane = new ScrollPane();
        final FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(5));
        flowPane.getStyleClass().add("engineer-grid");
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        final List<EngineerCard> engineerCards = Arrays.stream(Engineer.values())
                .filter(engineer -> !Engineer.UNKNOWN.equals(engineer))
                .map(EngineerCard::new)
                .collect(Collectors.toList());
        flowPane.getChildren().addAll(engineerCards);
        scrollPane.pannableProperty().set(true);
        scrollPane.setContent(flowPane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.setContent(scrollPane);

    }

    @Override
    public Tabs getTabType() {
        return Tabs.ENGINEERS;
    }
}
