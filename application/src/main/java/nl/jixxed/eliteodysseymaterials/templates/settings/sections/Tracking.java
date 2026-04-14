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
import nl.jixxed.eliteodysseymaterials.builder.ToggleSwitchBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import static nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab.*;

public class Tracking extends DestroyableVBox implements DestroyableTemplate {

    public Tracking() {
        this.initComponents();
    }

    @Override
    public void initComponents() {
        final DestroyableLabel trackingLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText("tab.settings.title.tracking")
                .build();
        final DestroyableHBox eddnSetting = createEDDNSetting();
        final DestroyableHBox trackingOptOutSetting = createTrackingOptOutSetting();
        this.getStyleClass().addAll("settingsblock", SETTINGS_SPACING_10_CLASS);
        this.getNodes().addAll(trackingLabel, trackingOptOutSetting, eddnSetting);
    }

    private DestroyableHBox createEDDNSetting() {
        DestroyableLabel eddnLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.eddn.toggle")
                .build();
        DestroyableLabel eddnExplainLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.eddn.explain")
                .build();
        DestroyableToggleSwitch eddnButton = ToggleSwitchBuilder.builder()
                .withSelectedChangeListener((_, _, newValue) -> PreferencesService.setPreference(PreferenceConstants.EDDN_ENABLED, Boolean.TRUE.equals(newValue)))
                .withSelected(PreferencesService.getPreference(PreferenceConstants.EDDN_ENABLED, Boolean.FALSE))
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(eddnLabel, eddnButton, eddnExplainLabel)
                .buildHBox();
    }


    private DestroyableHBox createTrackingOptOutSetting() {
        DestroyableLabel trackingOptOutLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.tracking.opt.out")
                .build();
        DestroyableLabel trackingOptOutExplainLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.tracking.opt.out.explain")
                .build();
        DestroyableCheckBox trackingOptOutCheckBox = CheckBoxBuilder.builder()
                .withSelected(PreferencesService.getPreference(PreferenceConstants.TRACKING_OPT_OUT, Boolean.FALSE))
                .withSelectedProperty((_, _, newValue) ->
                        PreferencesService.setPreference(PreferenceConstants.TRACKING_OPT_OUT, newValue)
                )
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(trackingOptOutLabel, trackingOptOutCheckBox, trackingOptOutExplainLabel)
                .buildHBox();
    }

}
