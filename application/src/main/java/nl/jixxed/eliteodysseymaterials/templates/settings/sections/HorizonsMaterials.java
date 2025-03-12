package nl.jixxed.eliteodysseymaterials.templates.settings.sections;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.templates.components.ButtonIntField;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import static nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab.*;

public class HorizonsMaterials extends DestroyableVBox implements DestroyableTemplate {

    public HorizonsMaterials() {
        this.initComponents();
        this.initEventHandling();
    }
    @Override
    public void initComponents() {
        final Label overviewHorizonsLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tab.settings.title.horizons.materials"))
                .build();

        final HBox maxRangeTrader = createMaterialTraderMaxRangeSetting();
        final HBox maxRangeBroker = createTechnologyBrokerMaxRangeSetting();
        this.getStyleClass().addAll("settingsblock", SETTINGS_SPACING_10_CLASS);
        this.getChildren().addAll(overviewHorizonsLabel, maxRangeTrader, maxRangeBroker);

    }

    @Override
    public void initEventHandling() {

    }
    private HBox createTechnologyBrokerMaxRangeSetting() {
        final Label maxRangeLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.broker.max.range")).build();
        final ButtonIntField rangeField = new ButtonIntField(0, 9999999, PreferencesService.getPreference(PreferenceConstants.HORIZONS_TECHNOLOGY_BROKER_MAX_RANGE, 5000));
        rangeField.addHandlerOnValidChange(range -> PreferencesService.setPreference(PreferenceConstants.HORIZONS_TECHNOLOGY_BROKER_MAX_RANGE, range));

        rangeField.getStyleClass().add("material-trader-range-setting");
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(maxRangeLabel, rangeField)
                .buildHBox();
    }

    private HBox createMaterialTraderMaxRangeSetting() {
        final Label maxRangeLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.materials.max.range")).build();
        final ButtonIntField rangeField = new ButtonIntField(0, 9999999, PreferencesService.getPreference(PreferenceConstants.HORIZONS_MATERIAL_TRADER_MAX_RANGE, 5000));
        rangeField.addHandlerOnValidChange(range -> PreferencesService.setPreference(PreferenceConstants.HORIZONS_MATERIAL_TRADER_MAX_RANGE, range));

        rangeField.getStyleClass().add("material-trader-range-setting");
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(maxRangeLabel, rangeField)
                .buildHBox();
    }

}
