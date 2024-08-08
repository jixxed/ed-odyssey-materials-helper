package nl.jixxed.eliteodysseymaterials.templates.dialog;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.templates.Template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
public class StartDialog extends VBox implements Template {
    public static final String POLICY_LEVEL_REQUIRED = "v2";
    private final Stage stage;

    public StartDialog(final Stage stage) {
        super();
        this.stage = stage;
        this.initComponents();
        this.initEventHandling();
    }

    @Override
    public void initComponents() {
        final boolean policyAccepted = PreferencesService.getPreference(PreferenceConstants.POLICY_ACCEPT_VERSION, "").equals(POLICY_LEVEL_REQUIRED);
        PreferencesService.setPreference(PreferenceConstants.WHATS_NEW_VERSION, PreferencesService.getPreference(PreferenceConstants.APP_SETTINGS_VERSION, "0"));
        //what new
        final Label whatsNewTitle = LabelBuilder.builder().withStyleClass("start-dialog-title").withNonLocalizedText("What's new?").build();
        String whatsnew;
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResource("/text/whatsnew.txt").openStream(), StandardCharsets.UTF_8))) {
            whatsnew = bufferedReader.lines().collect(Collectors.joining("\n"));
        } catch (final IOException e) {
            log.error("failed to load what's new", e);
            whatsnew = "";
        }
        final TextArea whatsNewContent = new TextArea(whatsnew);
        whatsNewContent.setWrapText(true);
        whatsNewContent.setEditable(false);
        whatsNewContent.setFocusTraversable(false);
        VBox.setVgrow(whatsNewContent, Priority.ALWAYS);

        //policy
        final Label policyTitle = LabelBuilder.builder().withStyleClass("start-dialog-title").withNonLocalizedText("Privacy policy").build();
        String policy;
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResource("/text/privacy.txt").openStream(), StandardCharsets.UTF_8))) {
            policy = bufferedReader.lines().collect(Collectors.joining("\n"));
        } catch (final IOException e) {
            log.error("failed to load privacy policy", e);
            policy = "";
        }
        final TextArea policyContent = new TextArea(policy);
        policyContent.setWrapText(true);
        policyContent.setEditable(false);
        policyContent.setFocusTraversable(false);
        VBox.setVgrow(policyContent, Priority.ALWAYS);
        //buttons
        final Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        final HBox buttons = (!policyAccepted)
                ? BoxBuilder.builder().withNodes(region,
                ButtonBuilder.builder().withNonLocalizedText("Accept & continue").withOnAction(event -> {
                    PreferencesService.setPreference(PreferenceConstants.POLICY_ACCEPT_VERSION, POLICY_LEVEL_REQUIRED);
                    this.stage.close();
                }).build(),
                ButtonBuilder.builder().withNonLocalizedText("Close application").withOnAction(event -> this.stage.close()).build()
        ).buildHBox()
                : BoxBuilder.builder().withNodes(region,
                ButtonBuilder.builder().withNonLocalizedText("Continue").withOnAction(event -> this.stage.close()).build()
        ).buildHBox();
        buttons.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        this.getStyleClass().add("start-dialog");
        this.getChildren().addAll(whatsNewTitle, whatsNewContent, policyTitle, policyContent, buttons);
    }

    @Override
    public void initEventHandling() {
        //NOOP
    }
}
