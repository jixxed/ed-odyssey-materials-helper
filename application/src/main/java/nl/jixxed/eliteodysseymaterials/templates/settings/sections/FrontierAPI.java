package nl.jixxed.eliteodysseymaterials.templates.settings.sections;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.service.CAPIService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.CommanderSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab;

import static nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab.*;

public class FrontierAPI extends DestroyableVBox implements DestroyableTemplate {

    private DestroyableLabel capiStatusLabel;
    private Button capiConnectButton;
    private Button capiDisconnectButton;
    public FrontierAPI() {
        this.initComponents();
        this.initEventHandling();
    }
    @Override
    public void initComponents() {
        final Label capiLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tab.settings.title.capi"))
                .build();
        final Label capiExplainLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.capi.explain"))
                .build();
        final HBox capiConnectSetting = createCapiConnectSetting();

        this.getStyleClass().addAll("settingsblock", SETTINGS_SPACING_10_CLASS);
        this.getChildren().addAll(capiLabel, capiExplainLabel, capiConnectSetting);
    }

    @Override
    public void initEventHandling() {

        register(EventService.addStaticListener(true, CommanderSelectedEvent.class, event ->{
            this.capiConnectButton.textProperty().bind(LocaleService.getStringBinding(() -> LocaleService.getLocalizedStringForCurrentLocale("tab.settings.capi.connect", ApplicationState.getInstance().getPreferredCommander().map(Commander::getName).orElse(""))));
            this.capiDisconnectButton.textProperty().bind(LocaleService.getStringBinding(() -> LocaleService.getLocalizedStringForCurrentLocale("tab.settings.capi.disconnect", ApplicationState.getInstance().getPreferredCommander().map(Commander::getName).orElse(""))));
        }));
    }
    private HBox createCapiConnectSetting() {
        DestroyableLabel capiConnectLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.capi.link.account")).build();
        this.capiConnectButton = ButtonBuilder.builder()
                .withText(LocaleService.getStringBinding(() -> LocaleService.getLocalizedStringForCurrentLocale("tab.settings.capi.connect", ApplicationState.getInstance().getPreferredCommander().map(Commander::getName).orElse(""))))
                .withOnAction(event -> CAPIService.getInstance().authenticate())
                .build();
        this.capiDisconnectButton = ButtonBuilder.builder()
                .withText(LocaleService.getStringBinding(() -> LocaleService.getLocalizedStringForCurrentLocale("tab.settings.capi.disconnect", ApplicationState.getInstance().getPreferredCommander().map(Commander::getName).orElse(""))))
//                .withText(LocaleService.getStringBinding("tab.settings.capi.disconnect", ApplicationState.getInstance().getPreferredCommander().map(Commander::getName).orElse("")))
                .withOnAction(event -> CAPIService.getInstance().deauthenticate())
                .build();
        this.capiConnectButton.disableProperty().bind(CAPIService.getInstance().getActive().or(SettingsTab.REGISTERED.not()));
        this.capiDisconnectButton.disableProperty().bind(CAPIService.getInstance().getActive().not());
        if (SettingsTab.REGISTERED.get()) {
            this.capiStatusLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withVisibilityProperty(CAPIService.getInstance().getActive()).withText(LocaleService.getStringBinding("tab.settings.capi.connected")).build();
        } else {
            this.capiStatusLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withVisibilityProperty(SettingsTab.REGISTERED.not().and(CAPIService.getInstance().getActive().not())).withText(LocaleService.getStringBinding("tab.settings.capi.needs.registration")).build();
        }
        SettingsTab.REGISTERED.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.capiStatusLabel.textProperty().bind(LocaleService.getStringBinding("tab.settings.capi.connected"));
                this.capiStatusLabel.visibleProperty().bind(CAPIService.getInstance().getActive());
            } else {
                this.capiStatusLabel.textProperty().bind(LocaleService.getStringBinding("tab.settings.capi.needs.registration"));
                this.capiStatusLabel.visibleProperty().bind(SettingsTab.REGISTERED.not());
            }
        });
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(capiConnectLabel, this.capiConnectButton, this.capiDisconnectButton, this.capiStatusLabel)
                .buildHBox();
    }
}
