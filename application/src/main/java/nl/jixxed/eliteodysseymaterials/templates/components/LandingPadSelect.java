/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.components;

import javafx.css.PseudoClass;
import javafx.util.Duration;
import nl.jixxed.eliteodysseymaterials.builder.EdAwesomeIconViewPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.service.UserPreferencesService;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTooltip;

public class LandingPadSelect extends DestroyableHBox implements DestroyableTemplate {

    private DestroyableLabel medium;
    private DestroyableLabel auto;
    private DestroyableLabel large;

    public LandingPadSelect() {
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("landing-pad-select");
        var iconPane = EdAwesomeIconViewPaneBuilder.builder()
                .withStyleClass("landing-pad-icon")
                .withIcons(EdAwesomeIcon.SHIPS_LANDING_PAD_1, EdAwesomeIcon.SHIPS_LANDING_PAD_2)
                .build();
        medium = LabelBuilder.builder()
                .withStyleClass("landing-pad-option")
                .withText("landing.pad.medium")
                .build();
        auto = LabelBuilder.builder()
                .withStyleClass("landing-pad-option")
                .withText("landing.pad.auto")
                .build();
        large = LabelBuilder.builder()
                .withStyleClass("landing-pad-option")
                .withText("landing.pad.large")
                .build();
        this.addEventBinding(this.onMouseClickedProperty(), _ -> this.selectPad());
        this.getNodes().addAll(iconPane, medium, auto, large);
        updateStyle();
        DestroyableTooltip tooltip = TooltipBuilder.builder()
                .withText("landing.pad.select")
                .withShowDelay(Duration.ZERO)
                .withCustomX(() -> this.localToScreen(this.getBoundsInLocal()).getMaxX())
                .withCustomY(() -> this.localToScreen(this.getBoundsInLocal()).getMinY())
                .build();
        tooltip.install(this);

    }

    private void selectPad() {
        String prefPad = UserPreferencesService.getPreference(PreferenceConstants.PREFERRED_LANDING_PAD, "auto");
        if( "medium".equals(prefPad)){
            UserPreferencesService.setPreference(PreferenceConstants.PREFERRED_LANDING_PAD, "auto");
        }else if( "auto".equals(prefPad)){
            UserPreferencesService.setPreference(PreferenceConstants.PREFERRED_LANDING_PAD, "large");
        }else if( "large".equals(prefPad)){
            UserPreferencesService.setPreference(PreferenceConstants.PREFERRED_LANDING_PAD, "medium");
        }
        updateStyle();
    }

    private void updateStyle() {
        String prefPad = UserPreferencesService.getPreference(PreferenceConstants.PREFERRED_LANDING_PAD, "auto");
        medium.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), "medium".equals(prefPad));
        auto.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), "auto".equals(prefPad));
        large.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), "large".equals(prefPad));
    }
}
