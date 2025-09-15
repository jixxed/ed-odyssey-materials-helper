package nl.jixxed.eliteodysseymaterials.templates.settings.sections;

import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ToggleSwitchBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.UserPreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.CollectorModeEvent;
import nl.jixxed.eliteodysseymaterials.service.event.ColonisationStockStateEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import static nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab.*;
import static nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab.SETTINGS_SPACING_10_CLASS;

public class HorizonsColonisation extends DestroyableVBox implements DestroyableTemplate {

    public HorizonsColonisation() {
        this.initComponents();
    }

    @Override
    public void initComponents() {
        final DestroyableLabel overviewHorizonsLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText("tab.settings.title.horizons.colonisation")
                .build();

        final DestroyableHBox fcEnabled = createEnableFCSetting();
        final DestroyableHBox scEnabled = createEnableSCSetting();
        this.getStyleClass().addAll("settingsblock", SETTINGS_SPACING_10_CLASS);
        this.getNodes().addAll(overviewHorizonsLabel, fcEnabled, scEnabled);

    }

    private DestroyableHBox createEnableFCSetting() {
        var fcEnabledLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.colonisation.fc.enabled")
                .build();
        var fcEnabled = ToggleSwitchBuilder.builder()
                .withSelectedChangeListener((_, _, newValue) -> {
                    UserPreferencesService.setPreference(PreferenceConstants.COLONISATION_ENABLE_FC, Boolean.TRUE.equals(newValue));
                    EventService.publish(new ColonisationStockStateEvent());
                })
                .withSelected(UserPreferencesService.getPreference(PreferenceConstants.COLONISATION_ENABLE_FC, true))
                .build();

        DestroyableLabel fcEnabledLabelExplainLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.colonisation.fc.explain")
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(fcEnabledLabel, fcEnabled, fcEnabledLabelExplainLabel)
                .buildHBox();
    }

    private DestroyableHBox createEnableSCSetting() {
        var scEnabledLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.colonisation.sc.enabled")
                .build();
        var scEnabled = ToggleSwitchBuilder.builder()
                .withSelectedChangeListener((_, _, newValue) -> {
                    UserPreferencesService.setPreference(PreferenceConstants.COLONISATION_ENABLE_SC, Boolean.TRUE.equals(newValue));
                    EventService.publish(new ColonisationStockStateEvent());
                })
                .withSelected(UserPreferencesService.getPreference(PreferenceConstants.COLONISATION_ENABLE_SC, true))
                .build();

        DestroyableLabel scEnabledLabelExplainLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.colonisation.sc.explain")
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(scEnabledLabel, scEnabled, scEnabledLabelExplainLabel)
                .buildHBox();
    }
}
