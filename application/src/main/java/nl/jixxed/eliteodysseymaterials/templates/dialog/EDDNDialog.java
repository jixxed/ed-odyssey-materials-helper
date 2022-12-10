package nl.jixxed.eliteodysseymaterials.templates.dialog;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.templates.Template;

@Slf4j
public class EDDNDialog extends VBox implements Template {
    private final Stage stage;

    public EDDNDialog(final Stage stage) {
        super();
        this.stage = stage;
        this.initComponents();
        this.initEventHandling();
    }

    @Override
    public void initComponents() {
        PreferencesService.setPreference(PreferenceConstants.EDDN_ASKED, true);

        //labels
        final Label explain = LabelBuilder.builder().withStyleClass("eddn-dialog-text").withText(LocaleService.getStringBinding("eddn.registration.text")).build();
        final Label explain2 = LabelBuilder.builder().withStyleClass("eddn-dialog-text").withText(LocaleService.getStringBinding("eddn.registration.text2")).build();

        //buttons
        final Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        final Region regionV = new Region();
        VBox.setVgrow(regionV, Priority.ALWAYS);
        final HBox buttons = BoxBuilder.builder().withNodes(region,
                ButtonBuilder.builder().withNonLocalizedText("Yes").withOnAction(event -> {
                    PreferencesService.setPreference(PreferenceConstants.EDDN_ENABLED, true);
                    this.stage.close();
                }).build(),
                ButtonBuilder.builder().withNonLocalizedText("No").withOnAction(event -> this.stage.close()).build()
        ).buildHBox();
        buttons.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        this.getStyleClass().add("urlscheme-dialog");
        this.getChildren().addAll(explain, explain2, regionV, buttons);
    }

    @Override
    public void initEventHandling() {
        //NOOP
    }
}
