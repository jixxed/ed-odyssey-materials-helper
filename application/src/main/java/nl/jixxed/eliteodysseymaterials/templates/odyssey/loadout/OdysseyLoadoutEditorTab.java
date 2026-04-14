/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
