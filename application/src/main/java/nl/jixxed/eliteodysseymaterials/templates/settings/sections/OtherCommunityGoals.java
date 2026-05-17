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
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.templates.components.ButtonIntField;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import static nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab.*;

public class OtherCommunityGoals extends DestroyableVBox implements DestroyableTemplate {

    public OtherCommunityGoals() {
        this.initComponents();
    }

    @Override
    public void initComponents() {
        final DestroyableLabel overviewOtherLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText("tab.settings.title.other.communitygoals")
                .build();

        final DestroyableHBox quantityMultiplierSetting = createQuantityMultiplierSetting();
        this.getStyleClass().addAll("settingsblock", SETTINGS_SPACING_10_CLASS);
        this.getNodes().addAll(overviewOtherLabel, quantityMultiplierSetting);

    }

    private DestroyableHBox createQuantityMultiplierSetting() {
        var fcEnabledLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.communitygoals.quantity.multiplier")
                .build();

        final ButtonIntField rangeField = new ButtonIntField(0, 999, PreferencesService.getPreference(PreferenceConstants.OTHER_COMMUNITY_GOAL_CARGO_MULTIPLIER, 2));
        rangeField.addHandlerOnValidChange(range -> PreferencesService.setPreference(PreferenceConstants.OTHER_COMMUNITY_GOAL_CARGO_MULTIPLIER, range));

        rangeField.getStyleClass().add("comunity-goal-range-setting");

        DestroyableLabel explainLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.communitygoals.quantity.multiplier.explain")
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(fcEnabledLabel, rangeField, explainLabel)
                .buildHBox();
    }
}
