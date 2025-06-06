package nl.jixxed.eliteodysseymaterials.templates.settings.sections;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.service.CAPIService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.RegistryService;
import nl.jixxed.eliteodysseymaterials.service.event.ApplicationRegisteredEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import static nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab.*;

public class FrontierAPI extends DestroyableVBox implements DestroyableEventTemplate {

    private DestroyableLabel capiStatusLabel;
    private DestroyableButton capiConnectButton;
    private DestroyableButton capiDisconnectButton;

    public final BooleanProperty registered = new SimpleBooleanProperty(RegistryService.isRegistered());

    public FrontierAPI() {
        this.initComponents();
        this.initEventHandling();
    }

    @Override
    public void initComponents() {
        final DestroyableLabel capiLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText("tab.settings.title.capi")
                .build();
        final DestroyableLabel capiExplainLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.capi.explain")
                .build();
        final DestroyableHBox capiConnectSetting = createCapiConnectSetting();

        this.getStyleClass().addAll("settingsblock", SETTINGS_SPACING_10_CLASS);
        this.getNodes().addAll(capiLabel, capiExplainLabel, capiConnectSetting);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, ApplicationRegisteredEvent.class, event -> {
            this.registered.set(event.isRegistered());
        }));
    }

    private DestroyableHBox createCapiConnectSetting() {
        DestroyableLabel capiConnectLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.capi.link.account")
                .build();
        this.capiConnectButton = ButtonBuilder.builder()
                .withText("tab.settings.capi.connect", ApplicationState.getInstance().getPreferredCommander().map(Commander::getName).orElse(""))
                .withOnAction(_ -> CAPIService.getInstance().authenticate())
                .build();
        this.capiDisconnectButton = ButtonBuilder.builder()
                .withText("tab.settings.capi.disconnect", ApplicationState.getInstance().getPreferredCommander().map(Commander::getName).orElse(""))
                .withOnAction(_ -> CAPIService.getInstance().deauthenticate())
                .build();
        this.capiConnectButton.addBinding(this.capiConnectButton.disableProperty(), CAPIService.getInstance().getActive().or(registered.not()));
        this.capiDisconnectButton.addBinding(this.capiDisconnectButton.disableProperty(), CAPIService.getInstance().getActive().not());
        if (registered.get()) {
            this.capiStatusLabel = LabelBuilder.builder()
                    .withStyleClass(SETTINGS_LABEL_CLASS)
                    .withVisibilityProperty(CAPIService.getInstance().getActive())
                    .withText("tab.settings.capi.connected")
                    .build();
        } else {
            this.capiStatusLabel = LabelBuilder.builder()
                    .withStyleClass(SETTINGS_LABEL_CLASS)
                    .withVisibilityProperty(registered.not().and(CAPIService.getInstance().getActive().not()))
                    .withText("tab.settings.capi.needs.registration")
                    .build();
        }
        registered.addListener((_, _, newValue) -> {
            if (Boolean.TRUE.equals(newValue)) {
                this.capiStatusLabel.addBinding(this.capiStatusLabel.textProperty(), LocaleService.getStringBinding("tab.settings.capi.connected"));
                this.capiStatusLabel.addBinding(this.capiStatusLabel.visibleProperty(), CAPIService.getInstance().getActive());
            } else {
                this.capiStatusLabel.addBinding(this.capiStatusLabel.textProperty(), LocaleService.getStringBinding("tab.settings.capi.needs.registration"));
                this.capiStatusLabel.addBinding(this.capiStatusLabel.visibleProperty(), registered.not());
            }
        });
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(capiConnectLabel, this.capiConnectButton, this.capiDisconnectButton, this.capiStatusLabel)
                .buildHBox();
    }

    @Override
    public void destroyInternal() {
        super.destroyInternal();
        registered.unbind();
    }
}
