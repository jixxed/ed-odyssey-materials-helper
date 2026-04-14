/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.settings.sections;

import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.CheckBoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.FlipRemainingAvailableEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import static nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab.*;

public class OdysseyWishlist extends DestroyableVBox implements DestroyableTemplate {

    public OdysseyWishlist() {
        this.initComponents();
    }

    @Override
    public void initComponents() {
        final DestroyableLabel wishlistOdysseyLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText("tab.settings.title.wishlist.odyssey")
                .build();

        final DestroyableHBox odysseyRemainingAvailableSetting = createOdysseyRemainingAvailableSetting();

        this.getStyleClass().addAll("settingsblock", SETTINGS_SPACING_10_CLASS);
        this.getNodes().addAll(wishlistOdysseyLabel, odysseyRemainingAvailableSetting);
    }

    private DestroyableHBox createOdysseyRemainingAvailableSetting() {
        DestroyableLabel flipOdysseyRemainingAvailableLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.flip.remaining.available.odyssey"))
                .build();
        DestroyableLabel flipOdysseyRemainingAvailableExplainLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.flip.remaining.available.odyssey.explain"))
                .build();
        //available is default(false)
        DestroyableCheckBox flipOdysseyRemainingAvailableCheckBox = CheckBoxBuilder.builder()
                .withSelected(PreferencesService.getPreference(PreferenceConstants.FLIP_WISHLIST_REMAINING_AVAILABLE_ODYSSEY, Boolean.FALSE))//available is default(false)
                .withSelectedProperty((observable, oldValue, newValue) -> {
                    PreferencesService.setPreference(PreferenceConstants.FLIP_WISHLIST_REMAINING_AVAILABLE_ODYSSEY, newValue);
                    EventService.publish(new FlipRemainingAvailableEvent(Expansion.ODYSSEY, newValue));
                })
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(flipOdysseyRemainingAvailableLabel, flipOdysseyRemainingAvailableCheckBox, flipOdysseyRemainingAvailableExplainLabel)
                .buildHBox();
    }

}
