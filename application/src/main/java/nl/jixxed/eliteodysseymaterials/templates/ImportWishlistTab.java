package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.enums.ImportResult;
import nl.jixxed.eliteodysseymaterials.enums.Tabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ImportResultEvent;
import nl.jixxed.eliteodysseymaterials.service.event.ImportWishlistEvent;

import java.util.Base64;

@Slf4j
public class ImportWishlistTab extends EDOTab {

    private ScrollPane scrollPane;
    private TextArea textArea;
    private Button button;
    private Label importLabel;
    private Label errorLabel;
    private Label note;

    ImportWishlistTab() {
        initComponents();
        initEventHandling();
    }

    private void initEventHandling() {
        EventService.addListener(ImportResultEvent.class, importResultEvent -> {
            if (ImportResult.ERROR.equals(importResultEvent.getResult())) {
                this.errorLabel.setVisible(true);
            }
            if (ImportResult.SUCCESS.equals(importResultEvent.getResult())) {
                this.textArea.setText("");
            }
        });
    }

    private void initComponents() {
        this.importLabel = LabelBuilder.builder()
                .withStyleClass("import-header")
                .withText(LocaleService.getStringBinding("tab.import.header"))
                .build();
        this.errorLabel = LabelBuilder.builder()
                .withStyleClass("import-error")
                .withText(LocaleService.getStringBinding("tab.import.status.invalid"))
                .withVisibility(false)
                .build();
        this.note = LabelBuilder.builder()
                .withNonLocalizedText("This feature is partially implemented. Imported wishlists are view-only, not persisted and lost after restart.")
                .build();
        this.setText("+");
        this.scrollPane = new ScrollPane();
        setupScrollPane(this.scrollPane);
        this.textArea = new TextArea();
        this.textArea.getStyleClass().add("import-input");
        this.textArea.setWrapText(true);
        this.button = ButtonBuilder.builder().withStyleClass("import-button").withText(LocaleService.getStringBinding("tab.import.button.import")).withOnAction(event -> {

            this.errorLabel.setVisible(false);
            try {
                if (this.textArea.getText().isEmpty()) {
                    throw new IllegalArgumentException("import string is empty");
                }
                final String decoded = new String(Base64.getDecoder().decode(this.textArea.getText().trim()));
                EventService.publish(new ImportWishlistEvent(decoded));

            } catch (final IllegalArgumentException ex) {
                log.error("failed to import wishlist", ex);
                EventService.publish(new ImportResultEvent(ImportResult.ERROR));
            }
        }).build();
        final HBox importButtonAndLabel = BoxBuilder.builder().withNodes(this.button, this.errorLabel).buildHBox();
        final VBox content = BoxBuilder.builder().withNodes(this.importLabel, this.note, this.textArea, importButtonAndLabel).withStyleClass("import-content").buildVBox();
        this.scrollPane.setContent(content);
        this.setContent(this.scrollPane);
    }

    @Override
    public Tabs getTabType() {
        return Tabs.ENGINEERS;
    }
}
