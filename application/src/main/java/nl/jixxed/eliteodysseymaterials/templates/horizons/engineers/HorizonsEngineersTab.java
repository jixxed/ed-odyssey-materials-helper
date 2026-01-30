package nl.jixxed.eliteodysseymaterials.templates.horizons.engineers;

import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableScrollPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsTab;

import java.util.Arrays;

public class HorizonsEngineersTab extends HorizonsTab implements DestroyableTemplate {

    private DestroyableFlowPane flowPane;
    private HorizonsEngineerCard[] horizonsEngineerCards;


    public HorizonsEngineersTab() {
        initComponents();
    }

    public void initComponents() {
        this.getStyleClass().add("engineers-tab");
        this.addBinding(this.textProperty(), LocaleService.getStringBinding("tabs.engineers"));
        this.horizonsEngineerCards = Arrays.stream(Engineer.values())
                .filter(Engineer::isHorizons)
                .filter(engineer -> !Engineer.UNKNOWN.equals(engineer))
                .map(HorizonsEngineerCard::new)
                .toArray(HorizonsEngineerCard[]::new);
        this.flowPane = FlowPaneBuilder.builder()
                .withStyleClass("engineer-grid")
                .withNodes(this.horizonsEngineerCards)
                .build();
        DestroyableScrollPane scrollPane = register(ScrollPaneBuilder.builder()
                .withStyleClass("engineers-tab-content")
                .withContent(this.flowPane)
                .build());
        this.setContent(scrollPane);
    }

    @Override
    public HorizonsTabType getTabType() {
        return HorizonsTabType.ENGINEERS;
    }
}
