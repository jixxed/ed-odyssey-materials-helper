package nl.jixxed.eliteodysseymaterials.templates.settings.sections;

import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.templates.components.ButtonIntField;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import static nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab.*;

public class HorizonsMaterials extends DestroyableVBox implements DestroyableTemplate {

    public HorizonsMaterials() {
        this.initComponents();
    }

    @Override
    public void initComponents() {
        final DestroyableLabel overviewHorizonsLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText("tab.settings.title.horizons.materials")
                .build();

        final DestroyableHBox maxRangeTrader = createMaterialTraderMaxRangeSetting();
        final DestroyableHBox maxRangeBroker = createTechnologyBrokerMaxRangeSetting();
        this.getStyleClass().addAll("settingsblock", SETTINGS_SPACING_10_CLASS);
        this.getNodes().addAll(overviewHorizonsLabel, maxRangeTrader, maxRangeBroker);

    }

    private DestroyableHBox createTechnologyBrokerMaxRangeSetting() {
        final DestroyableLabel maxRangeLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.broker.max.range"))
                .build();
        final ButtonIntField rangeField = new ButtonIntField(0, 9999999, PreferencesService.getPreference(PreferenceConstants.HORIZONS_TECHNOLOGY_BROKER_MAX_RANGE, 5000));
        rangeField.addHandlerOnValidChange(range -> PreferencesService.setPreference(PreferenceConstants.HORIZONS_TECHNOLOGY_BROKER_MAX_RANGE, range));

        rangeField.getStyleClass().add("material-trader-range-setting");
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(maxRangeLabel, rangeField)
                .buildHBox();
    }

    private DestroyableHBox createMaterialTraderMaxRangeSetting() {
        final DestroyableLabel maxRangeLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.materials.max.range"))
                .build();
        final ButtonIntField rangeField = new ButtonIntField(0, 9999999, PreferencesService.getPreference(PreferenceConstants.HORIZONS_MATERIAL_TRADER_MAX_RANGE, 5000));
        rangeField.addHandlerOnValidChange(range -> PreferencesService.setPreference(PreferenceConstants.HORIZONS_MATERIAL_TRADER_MAX_RANGE, range));

        rangeField.getStyleClass().add("material-trader-range-setting");
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(maxRangeLabel, rangeField)
                .buildHBox();
    }

}
