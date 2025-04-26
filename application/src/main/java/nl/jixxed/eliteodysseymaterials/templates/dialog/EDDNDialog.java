package nl.jixxed.eliteodysseymaterials.templates.dialog;

import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

@Slf4j
public class EDDNDialog extends DestroyableVBox implements DestroyableTemplate {
    private final Stage stage;

    public EDDNDialog(final Stage stage) {
        super();
        this.stage = stage;
        this.initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("eddn-dialog");
        PreferencesService.setPreference(PreferenceConstants.EDDN_ASKED, true);

        //labels
        final DestroyableLabel explain = LabelBuilder.builder()
                .withStyleClass("explain-text")
                .withText(LocaleService.getStringBinding("eddn.registration.text"))
                .build();
        final DestroyableLabel explain2 = LabelBuilder.builder()
                .withStyleClass("explain-text")
                .withText(LocaleService.getStringBinding("eddn.registration.text2"))
                .build();

        //buttons
        final DestroyableHBox buttons = BoxBuilder.builder()
                .withStyleClass("buttons")
                .withNodes(new GrowingRegion(),
                        ButtonBuilder.builder()
                                .withText("eddn.registration.yes")
                                .withOnAction(event -> {
                                    PreferencesService.setPreference(PreferenceConstants.EDDN_ENABLED, true);
                                    this.stage.close();
                                })
                                .build(),
                        ButtonBuilder.builder()
                                .withText("eddn.registration.no")
                                .withOnAction(event -> this.stage.close())
                                .build()
                ).buildHBox();
        this.getNodes().addAll(explain, explain2, new GrowingRegion(), buttons);
    }
}
