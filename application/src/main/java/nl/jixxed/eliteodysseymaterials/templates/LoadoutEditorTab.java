package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.enums.Tabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public class LoadoutEditorTab extends EDOTab implements Template {
    private ScrollPane scrollPane;

    @Override
    public Tabs getTabType() {
        return Tabs.LOADOUT;
    }

    @Override
    public void initEventHandling() {

    }

    @Override
    public void initComponents() {
        this.textProperty().bind(LocaleService.getStringBinding("tabs.loadout"));
        final FlowPane loadoutItemsFlow = FlowPaneBuilder.builder().withNodes(new LoadoutItem()).build();
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(loadoutItemsFlow)
                .build();
        this.setContent(this.scrollPane);
    }
}
