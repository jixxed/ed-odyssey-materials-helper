package nl.jixxed.eliteodysseymaterials.templates.settings.sections;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.CheckBoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ToggleSwitchBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableToggleSwitch;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import static nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab.*;

public class Tracking extends DestroyableVBox implements DestroyableTemplate {
    private Label trackingOptOutLabel;
    private Label trackingOptOutExplainLabel;
    private CheckBox trackingOptOutCheckBox;
    private DestroyableLabel eddnLabel;
    private DestroyableLabel eddnExplainLabel;
    private DestroyableToggleSwitch eddnButton;

    public Tracking() {
        this.initComponents();
        this.initEventHandling();
    }
    @Override
    public void initComponents() {
        final Label trackingLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tab.settings.title.tracking"))
                .build();
        final HBox eddnSetting = createEDDNSetting();
        final HBox trackingOptOutSetting = createTrackingOptOutSetting();
        this.getStyleClass().addAll("settingsblock", SETTINGS_SPACING_10_CLASS);
        this.getChildren().addAll(trackingLabel, trackingOptOutSetting, eddnSetting);

    }

    @Override
    public void initEventHandling() {

    }
    private HBox createEDDNSetting() {
        this.eddnLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.eddn.toggle")).build();
        this.eddnExplainLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.eddn.explain")).build();
        this.eddnButton = ToggleSwitchBuilder.builder()
                .withSelectedChangeListener((observable, oldValue, newValue) -> {
                    PreferencesService.setPreference(PreferenceConstants.EDDN_ENABLED, Boolean.TRUE.equals(newValue));
                })
                .withSelected(PreferencesService.getPreference(PreferenceConstants.EDDN_ENABLED, Boolean.FALSE))
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.eddnLabel, this.eddnButton, this.eddnExplainLabel)
                .buildHBox();
    }


    private HBox createTrackingOptOutSetting() {
        this.trackingOptOutLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.tracking.opt.out")).build();
        this.trackingOptOutExplainLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.tracking.opt.out.explain")).build();
        this.trackingOptOutCheckBox = CheckBoxBuilder.builder()
                .withSelected(PreferencesService.getPreference(PreferenceConstants.TRACKING_OPT_OUT, Boolean.FALSE))
                .withSelectedProperty((observable, oldValue, newValue) ->
                        PreferencesService.setPreference(PreferenceConstants.TRACKING_OPT_OUT, newValue)
                )
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.trackingOptOutLabel, this.trackingOptOutCheckBox, this.trackingOptOutExplainLabel)
                .buildHBox();
    }

}
