package nl.jixxed.eliteodysseymaterials.templates.dialog;

import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
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
        PreferencesService.setPreference(PreferenceConstants.EDDN_ASKED, true);

        //labels
        final DestroyableLabel explain = LabelBuilder.builder()
                .withStyleClass("eddn-dialog-text")
                .withText(LocaleService.getStringBinding("eddn.registration.text"))
                .build();
        final DestroyableLabel explain2 = LabelBuilder.builder()
                .withStyleClass("eddn-dialog-text")
                .withText(LocaleService.getStringBinding("eddn.registration.text2"))
                .build();

        //buttons
        final GrowingRegion region = new GrowingRegion();
        final GrowingRegion regionV = new GrowingRegion();
        final DestroyableHBox buttons = BoxBuilder.builder()
                .withNodes(region,
                        ButtonBuilder.builder()
                                .withText(LocaleService.getStringBinding("eddn.registration.yes"))
                                .withOnAction(event -> {
                                    PreferencesService.setPreference(PreferenceConstants.EDDN_ENABLED, true);
                                    this.stage.close();
                                })
                                .build(),
                        ButtonBuilder.builder()
                                .withText(LocaleService.getStringBinding("eddn.registration.no"))
                                .withOnAction(event -> this.stage.close())
                                .build()
                ).buildHBox();
        buttons.addBinding(buttons.spacingProperty(), ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        this.getStyleClass().add("urlscheme-dialog");
        this.getNodes().addAll(explain, explain2, regionV, buttons);
    }
}
