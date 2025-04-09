package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItem;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItems;
import nl.jixxed.eliteodysseymaterials.service.ColonisationService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableButton;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

import java.util.Optional;
import java.util.function.Consumer;

public class ColonisationProject extends DestroyableHBox implements DestroyableTemplate {

    private final ColonisationItem colonisationItem;
    private final Consumer<ColonisationItem> callbackSelect;
    private final Runnable callbackDelete;

    public ColonisationProject(ColonisationItem colonisationItem, Consumer<ColonisationItem> callbackSelect, Runnable callbackDelete) {
        super();
        this.colonisationItem = colonisationItem;
        this.callbackSelect = callbackSelect;
        this.callbackDelete = callbackDelete;
        initComponents();
    }

    @Override
    public void initComponents() {
        if (ColonisationItem.ALL == colonisationItem) {
            DestroyableButton project = ButtonBuilder.builder()
                    .withText("colonisation.all.projects")
                    .withOnAction(_ -> callbackSelect.accept(colonisationItem))
                    .build();
            this.getNodes().add(project);
        } else {
            DestroyableButton project = ButtonBuilder.builder()
                    .withNonLocalizedText(colonisationItem.getName())
                    .withOnAction(_ -> callbackSelect.accept(colonisationItem))
                    .build();
            DestroyableButton delete = ButtonBuilder.builder()
                    .withNonLocalizedText("X")
                    .withOnAction(_ -> {
                        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle(LocaleService.getLocalizedStringForCurrentLocale("tab.colonisation.delete.confirm.title"));
                        alert.setHeaderText(LocaleService.getLocalizedStringForCurrentLocale("tab.colonisation.delete.confirm.header"));
                        alert.setContentText(LocaleService.getLocalizedStringForCurrentLocale("tab.colonisation.delete.confirm.content"));

                        final Optional<ButtonType> result = alert.showAndWait();
                        if (result.map(type -> type == ButtonType.OK).orElse(false)) {
                            ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
                                final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);
                                colonisationItems.delete(colonisationItem.getUuid());
                                ColonisationService.saveColonisationItems(commander, colonisationItems);
                                callbackDelete.run();
                            });
                        }

                    })
                    .build();
            this.getNodes().addAll(project, delete);
        }
    }
}
