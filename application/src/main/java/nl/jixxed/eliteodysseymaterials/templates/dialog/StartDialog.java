package nl.jixxed.eliteodysseymaterials.templates.dialog;

import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TextAreaBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.VersionService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class StartDialog extends DestroyableVBox implements DestroyableTemplate {
    public static final String POLICY_LEVEL_REQUIRED = "v3";
    public static final String EULA_LEVEL_REQUIRED = "v1";

    private final Stage stage;

    public StartDialog(final Stage stage) {
        super();
        this.stage = stage;
        this.initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("start-dialog");
        //what new
        final DestroyableLabel whatsNewTitle = LabelBuilder.builder()
                .withStyleClass("title")
                .withText("dialog.start.whatsnew", PreferencesService.getPreference(PreferenceConstants.WHATS_NEW_VERSION, "3.0.0"))
                .build();
        final DestroyableTextArea whatsNewContent = getWhatsNewFromFile("/text/whatsnew.txt");
        //policy
        final DestroyableLabel policyTitle = LabelBuilder.builder()
                .withStyleClass("title")
                .withText("dialog.start.privacypolicy")
                .build();
        final DestroyableTextArea policyContent = getTextFromFile("/text/privacy.txt");
        //policy
        final DestroyableLabel eulaTitle = LabelBuilder.builder()
                .withStyleClass("title")
                .withText("dialog.start.eula")
                .build();
        final DestroyableTextArea eulaContent = getTextFromFile("/text/eula.txt");
        //buttons
        final DestroyableHBox buttonsBox = BoxBuilder.builder()
                .withStyleClass("buttons")
                .withNodes(createButtons())
                .buildHBox();
        this.getNodes().addAll(whatsNewTitle, whatsNewContent, policyTitle, policyContent, eulaTitle, eulaContent, buttonsBox);
        if(!VersionService.isDev()){
            PreferencesService.setPreference(PreferenceConstants.WHATS_NEW_VERSION, VersionService.getBuildVersion());
        }
    }

    @SuppressWarnings("unchecked")
    private <E extends Node & Destroyable> List<E> createButtons() {
        final boolean policyAccepted = PreferencesService.getPreference(PreferenceConstants.POLICY_ACCEPT_VERSION, "").equals(POLICY_LEVEL_REQUIRED);
        final boolean eulaAccepted = PreferencesService.getPreference(PreferenceConstants.EULA_ACCEPT_VERSION, "").equals(EULA_LEVEL_REQUIRED);
        return (List<E>) ((policyAccepted && eulaAccepted) ? List.of(new GrowingRegion(), continueButton()) : List.of(new GrowingRegion(), acceptButton(), closeButton()));
    }

    private DestroyableTextArea getTextFromFile(String name) {
        String text;
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResource(name)).openStream(), StandardCharsets.UTF_8))) {
            text = bufferedReader.lines().collect(Collectors.joining("\n"));
        } catch (final Exception e) {
            log.error("failed to load {}", name, e);
            text = "";
        }
        final DestroyableTextArea whatsNewContent = TextAreaBuilder.builder()
                .withStyleClass("text-content")
                .withNonLocalizedText(text)
                .withFocusTraversable(false)
                .withEditable(false)
                .build();
        VBox.setVgrow(whatsNewContent, Priority.ALWAYS);
        return whatsNewContent;
    }

    private DestroyableTextArea getWhatsNewFromFile(String name) {
        String whatsnew;
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResource(name)).openStream(), StandardCharsets.UTF_8))) {
            whatsnew = bufferedReader.lines().collect(Collectors.joining("\n"));
            whatsnew = ChangelogParser.getWhatsNew(whatsnew, PreferencesService.getPreference(PreferenceConstants.WHATS_NEW_VERSION, "3.0.0"));
        } catch (final Exception e) {
            log.error("failed to load {}", name, e);
            whatsnew = "";
        }
        final DestroyableTextArea whatsNewContent = TextAreaBuilder.builder()
                .withStyleClass("text-content")
                .withNonLocalizedText(whatsnew)
                .withFocusTraversable(false)
                .withEditable(false)
                .build();
        VBox.setVgrow(whatsNewContent, Priority.ALWAYS);
        return whatsNewContent;
    }

    private DestroyableButton continueButton() {
        return ButtonBuilder.builder()
                .withText("dialog.start.continue")
                .withOnAction(_ -> this.stage.close())
                .build();
    }

    private DestroyableButton closeButton() {
        return ButtonBuilder.builder()
                .withText("dialog.start.close")
                .withOnAction(_ -> this.stage.close())
                .build();
    }

    private DestroyableButton acceptButton() {
        return ButtonBuilder.builder()
                .withText("dialog.start.accept")
                .withOnAction(event -> {
                    PreferencesService.setPreference(PreferenceConstants.POLICY_ACCEPT_VERSION, POLICY_LEVEL_REQUIRED);
                    PreferencesService.setPreference(PreferenceConstants.EULA_ACCEPT_VERSION, EULA_LEVEL_REQUIRED);
                    this.stage.close();
                })
                .build();
    }
}
