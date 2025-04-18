package nl.jixxed.eliteodysseymaterials.templates.dialog;

import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.FXApplication;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.HyperlinkBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

@Slf4j
public class VersionDialog extends DestroyableVBox implements DestroyableTemplate {
    private final Stage stage;
    private DestroyableHyperlink link;

    public VersionDialog(final Stage stage) {
        super();
        this.stage = stage;

        this.initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("version-dialog");
        this.link = HyperlinkBuilder.builder()
                .withStyleClass("version-dialog-download-link")
                .withText(LocaleService.getStringBinding("version.dialog.download"))
                .withOnAction(_ -> {
                    FXApplication.getInstance().getHostServices().showDocument("https://github.com/jixxed/ed-odyssey-materials-helper/releases/latest");
                    System.exit(0);
                })
                .build();
        //labels
        final DestroyableLabel explain = LabelBuilder.builder()
                .withStyleClass("version-dialog-text")
                .withText(LocaleService.getStringBinding("version.dialog.text"))
                .build();
        final DestroyableLabel explain2 = LabelBuilder.builder()
                .withStyleClass("version-dialog-text")
                .withText(LocaleService.getStringBinding("version.dialog.text2"))
                .build();

        //buttons
        final DestroyableHBox buttons = BoxBuilder.builder()
                .withStyleClass("version-dialog-buttons")
                .withNodes(new GrowingRegion(), okButton())
                .buildHBox();
        this.getNodes().addAll(explain, explain2, this.link, new GrowingRegion(), buttons);
    }

    private DestroyableButton okButton() {
        return ButtonBuilder.builder()
                .withText("version.dialog.ok")
                .withOnAction(event -> {
                    this.stage.close();
                })
                .build();
    }
}
