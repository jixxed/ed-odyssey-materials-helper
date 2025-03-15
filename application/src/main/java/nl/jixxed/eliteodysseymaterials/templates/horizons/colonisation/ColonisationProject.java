package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItem;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItems;
import nl.jixxed.eliteodysseymaterials.service.ColonisationService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.Template;

import java.util.Optional;
import java.util.function.Consumer;

public class ColonisationProject extends HBox implements Template {

    private ColonisationItem colonisationItem;
    private Consumer<ColonisationItem> callbackSelect;
    private Runnable callbackDelete;

    public ColonisationProject(ColonisationItem colonisationItem, Consumer<ColonisationItem> callbackSelect, Runnable callbackDelete) {
        super();
        this.colonisationItem = colonisationItem;
        this.callbackSelect = callbackSelect;
        this.callbackDelete = callbackDelete;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        if (ColonisationItem.ALL == colonisationItem) {
            Button project = ButtonBuilder.builder()
                    .withText(LocaleService.getStringBinding("colonisation.all.projects"))
                    .withOnAction(event -> {
                        callbackSelect.accept(colonisationItem);
                    }).build();
            this.getChildren().add(project);
        } else {
            Button project = ButtonBuilder.builder()
                    .withNonLocalizedText(colonisationItem.getName())
                    .withOnAction(event -> {
                        callbackSelect.accept(colonisationItem);
                    }).build();
            Button delete = ButtonBuilder.builder()
                    .withNonLocalizedText("X")
                    .withOnAction(event -> {
                        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle(LocaleService.getLocalizedStringForCurrentLocale("tab.colonisation.delete.confirm.title"));
                        alert.setHeaderText(LocaleService.getLocalizedStringForCurrentLocale("tab.colonisation.delete.confirm.header"));
                        alert.setContentText(LocaleService.getLocalizedStringForCurrentLocale("tab.colonisation.delete.confirm.content"));

                        final Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
                                final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);
                                colonisationItems.delete(colonisationItem.getUuid());
                                ColonisationService.saveColonisationItems(commander, colonisationItems);
                                callbackDelete.run();
                            });
                        }

                    }).build();
            this.getChildren().addAll(project, delete);
        }
    }

    @Override
    public void initEventHandling() {
        //NOOP
    }
}
