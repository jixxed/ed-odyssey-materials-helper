package nl.jixxed.eliteodysseymaterials.templates.dialog;

import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.RegistryService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

@Slf4j
public class URLSchemeDialog extends DestroyableVBox implements DestroyableTemplate {
    private final Stage stage;

    public URLSchemeDialog(final Stage stage) {
        super();
        this.stage = stage;
        this.initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("urlscheme-dialog");
        PreferencesService.setPreference(PreferenceConstants.URL_SCHEME, true);

        //labels
        final DestroyableLabel explain = LabelBuilder.builder()
                .withStyleClass("explain-text")
                .withText(LocaleService.getStringBinding("url.scheme.registration.text"))
                .build();
        final DestroyableLabel explain2 = LabelBuilder.builder()
                .withStyleClass("explain-text")
                .withText(LocaleService.getStringBinding("url.scheme.registration.text2"))
                .build();

        //buttons
        final DestroyableHBox buttons = BoxBuilder.builder()
                .withStyleClass("buttons")
                .withNodes(new GrowingRegion(), registerButton(), noThanksButton())
                .buildHBox();

        this.getNodes().addAll(explain, explain2, new GrowingRegion(), buttons);
    }

    private DestroyableButton noThanksButton() {
        return ButtonBuilder.builder()
                .withText("dialog.urlscheme.nothanks")
                .withOnAction(event -> this.stage.close())
                .build();
    }

    private DestroyableButton registerButton() {
        return ButtonBuilder.builder()
                .withText("dialog.urlscheme.register")
                .withOnAction(event -> {
                    RegistryService.registerApplication();
                    this.stage.close();
                })
                .build();
    }

}
