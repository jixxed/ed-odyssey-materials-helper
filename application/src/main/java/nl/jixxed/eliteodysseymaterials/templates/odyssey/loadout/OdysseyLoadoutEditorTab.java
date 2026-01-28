package nl.jixxed.eliteodysseymaterials.templates.odyssey.loadout;

import javafx.scene.control.ScrollPane;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.LoadoutSet;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabType;
import nl.jixxed.eliteodysseymaterials.service.LoadoutService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyTab;

public class OdysseyLoadoutEditorTab extends OdysseyTab implements DestroyableEventTemplate {


    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    private DestroyableFlowPane loadoutItemsFlow;


    public OdysseyLoadoutEditorTab() {
        initComponents();
        initEventHandling();
    }


    @Override
    public void initComponents() {
        this.getStyleClass().add("loadout-tab");
        this.addBinding(this.textProperty(), LocaleService.getStringBinding("tabs.loadout"));
        final OdysseyLoadoutEditorMenu menu = new OdysseyLoadoutEditorMenu();
        this.loadoutItemsFlow = FlowPaneBuilder.builder()
                .withStyleClass("loadouts")
                .build();
        final DestroyableVBox content = BoxBuilder.builder()
                .withStyleClass("contents")
                .withNodes(menu, this.loadoutItemsFlow).buildVBox();
        ScrollPane scrollPane = register(ScrollPaneBuilder.builder()
                .withStyleClass("loadout-tab-content")
                .withContent(content)
                .build());
        this.setContent(scrollPane);
        refreshContent();

    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, _ -> refreshContent()));
        register(EventService.addListener(true, this, LanguageChangedEvent.class, _ -> refreshContent()));
        register(EventService.addListener(true, this, LoadoutSetSelectedEvent.class, _ -> refreshContent()));
        register(EventService.addListener(true, this, LoadoutRemovedEvent.class, _ -> refreshContent()));
        register(EventService.addListener(true, this, LoadoutAddedEvent.class, _ -> refreshContent()));
        register(EventService.addListener(true, this, LoadoutMovedEvent.class, _ -> refreshContent()));
        register(EventService.addListener(true, this, 9, LoadoutEvent.class, event -> {
            if (LoadoutSet.CURRENT.equals(event.getLoadoutSet())) refreshContent();
        }));

    }

    private void refreshContent() {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final LoadoutSet selectedLoadoutSet = LoadoutService.getLoadoutSetList(commander).getSelectedLoadoutSet();
            this.loadoutItemsFlow.getNodes().clear();
            this.loadoutItemsFlow.getNodes().addAll(selectedLoadoutSet.getLoadouts().stream()
                    .map(loadout -> new OdysseyLoadoutItem(selectedLoadoutSet, loadout))
                    .toList());
        });
    }

    @Override
    public OdysseyTabType getTabType() {
        return OdysseyTabType.LOADOUT;
    }
}
