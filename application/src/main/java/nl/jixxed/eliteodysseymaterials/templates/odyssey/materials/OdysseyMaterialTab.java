/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.odyssey.materials;

import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableScrollPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyTab;

public class OdysseyMaterialTab extends OdysseyTab implements DestroyableTemplate {

    private static final double SCROLL_SPEED = 0.01;
    private DestroyableScrollPane scrollPane;
    private OdysseyMaterialOverview materialOverview;

    public OdysseyMaterialTab() {
        initComponents();
    }

    public void initComponents() {
        this.getStyleClass().add("materials-tab");
        this.addBinding(this.textProperty(), LocaleService.getStringBinding("tabs.odyssey.materials"));
        this.materialOverview = new OdysseyMaterialOverview();
        this.scrollPane = register(ScrollPaneBuilder.builder()
                .withStyleClass("materials-tab-content")
                .withContent(this.materialOverview)
                .build());

        this.scrollPane.addChangeListener(this.scrollPane.widthProperty(), this.materialOverview.getResizeListener());
        this.setContent(this.scrollPane);
        this.scrollPane.getContent().setOnScroll(scrollEvent -> {
            final double speed = this.scrollPane.getHeight() / this.scrollPane.getContent().getLayoutBounds().getHeight();
            final double deltaY = scrollEvent.getDeltaY() * speed * SCROLL_SPEED;
            this.scrollPane.setVvalue(this.scrollPane.getVvalue() - deltaY);
        });
        scrollPane.setFitToWidth(true);
    }


    @Override
    public OdysseyTabType getTabType() {
        return OdysseyTabType.OVERVIEW;
    }
}
