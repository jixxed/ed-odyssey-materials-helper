/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.horizons.engineers;

import nl.edomh.ui.shared.builder.FlowPaneBuilder;
import nl.edomh.ui.shared.builder.ScrollPaneBuilder;
import nl.edomh.core.enums.Engineer;
import nl.edomh.core.service.LocaleService;
import nl.edomh.ui.shared.enums.HorizonsTabType;
import nl.edomh.ui.shared.templates.destroyables.DestroyableFlowPane;
import nl.edomh.ui.shared.templates.destroyables.DestroyableScrollPane;
import nl.edomh.ui.shared.templates.destroyables.DestroyableTemplate;
import nl.edomh.ui.shared.templates.generic.HorizonsTab;

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
