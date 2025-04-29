package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TextFieldBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItem;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItems;
import nl.jixxed.eliteodysseymaterials.service.ColonisationService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.ColonisationRefreshEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.Optional;

public class ColonisationProjectView extends DestroyableHBox implements DestroyableTemplate {


    ColonisationItem colonisationItem;
    private DestroyableButton trackButton;
    private DestroyableButton delete;
    private DestroyableTextField nameTextField;
    private DestroyableLabel nameTitle;
    private DestroyableLabel marketIDTitle;
    private DestroyableLabel marketID;
    private DestroyableHBox nameInput;

    public ColonisationProjectView() {
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("project-view");
        delete = ButtonBuilder.builder()
                .withNonLocalizedText("Delete")
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
                            setBuildable(ColonisationService.getColonisationItems(commander).getSelectedColonisationItem());
                            EventService.publish(new ColonisationRefreshEvent());
                        });
                    }
                })
                .build();
        marketIDTitle = LabelBuilder.builder()
                .withStyleClass("title")
                .withText("tab.colonisation.project.market.id")
                .build();
        marketID = LabelBuilder.builder().withNonLocalizedText("0").build();
        nameTitle = LabelBuilder.builder()
                .withStyleClass("title")
                .withText("tab.colonisation.project.name")
                .build();
        nameTextField = TextFieldBuilder.builder().withNonLocalizedText("nope").build();
        final DestroyableButton saveName = ButtonBuilder.builder().withNonLocalizedText("Save")
                .withOnAction(_ -> {
                    ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
                        final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);
                        final ColonisationItem selectedColonisationItem = colonisationItems.getSelectedColonisationItem();
                        selectedColonisationItem.setName(nameTextField.getText());
                        ColonisationService.saveColonisationItems(commander, colonisationItems);
                        EventService.publish(new ColonisationRefreshEvent());
                    });
                }).build();

        trackButton = ButtonBuilder.builder().withNonLocalizedText("Track")
                .withOnAction(_ -> {
                    ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
                        final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);
                        final ColonisationItem selectedColonisationItem = colonisationItems.getSelectedColonisationItem();
                        ColonisationItem newItem = new ColonisationItem(selectedColonisationItem);
                        colonisationItems.addColonisationItem(newItem);
                        colonisationItems.setSelectedColonisationItemUUID(newItem.getUuid());
                        ColonisationService.saveColonisationItems(commander, colonisationItems);
                        setBuildable(ColonisationService.getColonisationItems(commander).getSelectedColonisationItem());
                        EventService.publish(new ColonisationRefreshEvent());
                    });
                }).build();

        nameInput = BoxBuilder.builder()
                .withNodes(nameTextField, saveName)
                .buildHBox();

        final DestroyableVBox content = BoxBuilder.builder()
                .withStyleClass("contents")
                .withNodes(nameTitle, nameInput, marketIDTitle, marketID)
                .buildVBox();

        this.setVisible(false);
        this.setManaged(false);
        this.getNodes().addAll(content, new GrowingRegion(), trackButton, delete);
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);
            setBuildable(colonisationItems.getSelectedColonisationItem());
        });
    }

    public void setBuildable(ColonisationItem colonisationItem) {
        this.colonisationItem = colonisationItem;
        update();
    }

    private void update() {
        setVisibility(this, !colonisationItem.isAll());
        setVisibility(trackButton, colonisationItem.isCurrent());
        setVisibility(delete, !colonisationItem.isAll() && !colonisationItem.isCurrent());
        setVisibility(nameTitle, !colonisationItem.isAll() && !colonisationItem.isCurrent());
        setVisibility(nameInput, !colonisationItem.isAll() && !colonisationItem.isCurrent());
        setVisibility(marketIDTitle, !colonisationItem.isAll());
        setVisibility(marketID, !colonisationItem.isAll());

        nameTextField.setText(!colonisationItem.isCurrent() ? colonisationItem.getName() : "");
        marketID.setText(colonisationItem.getMarketID());
    }

    private void setVisibility(Node node, boolean visible) {
        node.setVisible(visible);
        node.setManaged(visible);
    }

    public void clear() {
        setBuildable(ColonisationItem.ALL);
    }
}
