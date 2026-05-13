/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.other;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.OtherTabType;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.AfterFontSizeSetEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.OtherTabSelectedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.other.colonisation.ColonisationSearchBar;
import nl.jixxed.eliteodysseymaterials.templates.other.permits.PermitsSearchBar;
import nl.jixxed.eliteodysseymaterials.templates.other.powerplay.PowerplaySearchBar;

@Slf4j
class OtherSearchBar extends DestroyableHBox implements DestroyableEventTemplate {

    private static final String FX_FONT_SIZE_DPX = "-fx-font-size: %dpx";
    private PowerplaySearchBar powerplaySearchBar;
    private PermitsSearchBar permitsSearchBar;
    private ColonisationSearchBar colonisationSearchBar;

    OtherSearchBar() {
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().add("search");
        this.powerplaySearchBar = register(new PowerplaySearchBar());
        this.permitsSearchBar = register(new PermitsSearchBar());
        this.colonisationSearchBar = register(new ColonisationSearchBar());

        applyFontSizingHack();

        HBox.setHgrow(this.powerplaySearchBar, Priority.ALWAYS);
        HBox.setHgrow(this.permitsSearchBar, Priority.ALWAYS);
        HBox.setHgrow(this.colonisationSearchBar, Priority.ALWAYS);
        this.getNodes().addAll(this.powerplaySearchBar);
    }

    public void initEventHandling() {
        //hack for component resizing on other fontsizes
        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSizeEvent.getFontSize());
            this.styleProperty().set(fontStyle);
        }));
        register(EventService.addListener(true, this, OtherTabSelectedEvent.class, event -> switchTab(event.getSelectedTab())));
    }


    private void applyFontSizingHack() {
        //hack for component resizing on other fontsizes
        final Integer fontSize = FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize();
        final String fontStyle = String.format(FX_FONT_SIZE_DPX, fontSize);
        this.styleProperty().set(fontStyle);
    }

    private void switchTab(OtherTabType selectedTab) {
        this.getChildren().removeAll(this.permitsSearchBar, this.powerplaySearchBar, this.colonisationSearchBar);
        if (OtherTabType.POWERPLAY.equals(selectedTab)) {
            this.getChildren().add(this.powerplaySearchBar);
        } else if (OtherTabType.COLONISATION.equals(selectedTab)) {
            this.getChildren().add(this.colonisationSearchBar);
        } else if (OtherTabType.PERMIT.equals(selectedTab)) {
            this.getChildren().add(this.permitsSearchBar);
        } else {
            this.getChildren().add(this.powerplaySearchBar);
        }
    }
}
