/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.settings;

import javafx.scene.control.ScrollPane;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.enums.MainTabType;
import nl.jixxed.eliteodysseymaterials.helper.OsCheck;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.generic.MainTab;
import nl.jixxed.eliteodysseymaterials.templates.settings.sections.*;

@Slf4j
public class SettingsTab extends MainTab implements DestroyableTemplate {
    public static final String SETTINGS_LABEL_CLASS = "settings-label";
    public static final String SETTINGS_LINK_CLASS = "settings-link";
    public static final String SETTINGS_DROPDOWN_CLASS = "settings-dropdown";
    public static final String SETTINGS_SPACING_10_CLASS = "settings-spacing-10";
    public static final String SETTINGS_JOURNAL_LINE_STYLE_CLASS = "settings-journal-line";
    public static final String SETTINGS_BUTTON_STYLE_CLASS = "settings-button";


    public SettingsTab() {
        initComponents();
    }

    public void initComponents() {
        this.getStyleClass().add("settings-tab");
        this.addBinding(this.textProperty(), LocaleService.getStringBinding("tabs.settings"));
//        final DestroyableLabel settingsLabel = LabelBuilder.builder()
//                .withStyleClass("settings-header")
//                .withText(LocaleService.getStringBinding("tabs.settings"))
//                .build();
        final DestroyableVBox settings = BoxBuilder.builder()
                .withStyleClasses("settings-vbox", SETTINGS_SPACING_10_CLASS)
                .withNodes(
                        new General(),
                        new OdysseyMaterials(),
                        new HorizonsMaterials(),
                        new OdysseyWishlist(),
                        new HorizonsWishlist(),
                        new HorizonsShips(),
                        new HorizonsColonisation(),
                        new Notifications(),
                        new FrontierAPI()
                )
                .buildVBox();
        //AR
        if (OsCheck.isWindows()) {
            settings.getNodes().add(new AugmentedReality());
        }
        //Tracking
        settings.getNodes().add(new Tracking());

        ScrollPane scrollPane = register(ScrollPaneBuilder.builder()
                .withStyleClass("settings-tab-content")
                .withContent(settings)
                .build());
        this.setContent(scrollPane);
    }


    @Override
    public MainTabType getTabType() {
        return MainTabType.SETTINGS;
    }

}
