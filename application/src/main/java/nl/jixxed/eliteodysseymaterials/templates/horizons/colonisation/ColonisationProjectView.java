package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItem;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItems;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.ColonisationBuildable;
import nl.jixxed.eliteodysseymaterials.enums.ColonisationLayout;
import nl.jixxed.eliteodysseymaterials.service.ColonisationService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.event.ColonisationRefreshEvent;
import nl.jixxed.eliteodysseymaterials.service.event.ColonisationSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.CopyableLocation;

import java.util.*;
import java.util.function.Predicate;

@Slf4j
public class ColonisationProjectView extends DestroyableHBox implements DestroyableEventTemplate {


    private ColonisationItem colonisationItem;
    private DestroyableButton trackButton;
    private DestroyableButton delete;
    private DestroyableTextField nameTextField;
    private DestroyableLabel nameTitle;
    private DestroyableLabel systemTitle;
    private CopyableLocation location;
    private DestroyableLabel marketIDTitle;
    private DestroyableLabel marketID;
    private DestroyableComboBox<ColonisationBuildable> buildableSelect;
    private DestroyableComboBox<ColonisationLayout> layoutSelect;
    private DestroyableCheckBox hideFromAll;
    private DestroyableLabel typeTitle;
    private DestroyableLabel layoutTitle;
    private DestroyableButton save;

    public ColonisationProjectView() {
        initComponents();
        initEventHandling();
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
        marketID = LabelBuilder.builder()
                .withStyleClass("market-id")
                .withNonLocalizedText("0").build();
        nameTitle = LabelBuilder.builder()
                .withStyleClass("title")
                .withText("tab.colonisation.project.name")
                .build();
        systemTitle = LabelBuilder.builder()
                .withStyleClass("title")
                .withText("tab.colonisation.project.system")
                .build();
        typeTitle = LabelBuilder.builder()
                .withStyleClass("title")
                .withText("tab.colonisation.project.type")
                .build();
        layoutTitle = LabelBuilder.builder()
                .withStyleClass("title")
                .withText("tab.colonisation.project.layout")
                .build();
        hideFromAll = CheckBoxBuilder.builder()
                .withStyleClass("hide-from-all")
                .withText("tab.colonisation.project.hide.from.all")
                .build();
        nameTextField = TextFieldBuilder.builder()
                .withStyleClass("name-input")
                .withNonLocalizedText("nope")
                .build();

        save = ButtonBuilder.builder().withNonLocalizedText("Save")
                .withOnAction(_ -> {
                    ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
                        final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);
                        final ColonisationItem selectedColonisationItem = colonisationItems.getSelectedColonisationItem();
                        selectedColonisationItem.setName(nameTextField.getText());
                        selectedColonisationItem.setHideFromAll(hideFromAll.isSelected());
                        selectedColonisationItem.setColonisationBuildable(buildableSelect.getSelectionModel().getSelectedItem());
                        selectedColonisationItem.setColonisationLayout(layoutSelect.getSelectionModel().getSelectedItem());
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
                        newItem.setName(LocationService.getCurrentLocation().getStation());
                        colonisationItems.addColonisationItem(newItem);
                        colonisationItems.setSelectedColonisationItemUUID(newItem.getUuid());
                        ColonisationService.saveColonisationItems(commander, colonisationItems);
                        setBuildable(ColonisationService.getColonisationItems(commander).getSelectedColonisationItem());
                        EventService.publish(new ColonisationRefreshEvent());
                    });
                }).build();

        layoutSelect = ComboBoxBuilder.builder(ColonisationLayout.class)
                .withStyleClass("colonisation-select")
                .build();
        buildableSelect = ComboBoxBuilder.builder(ColonisationBuildable.class)
                .withStyleClass("colonisation-select")
                .withItemsProperty(FXCollections.observableArrayList(Arrays.stream(ColonisationBuildable.values()).filter(Predicate.not(ColonisationBuildable.UNKNOWN::equals)).toList()))
                .withValueChangeListener((_, _, newValue) -> {
                    final List<ColonisationLayout> colonisationLayouts = newValue == null ? Collections.emptyList() : newValue.getColonisationLayouts();
                    layoutSelect.setItems(FXCollections.observableArrayList(colonisationLayouts));
                })
                .build();
        location = new CopyableLocation(StarSystem.SOL);
        final DestroyableVBox content = BoxBuilder.builder()
                .withStyleClass("contents")
                .withNodes(nameTitle, nameTextField, typeTitle, buildableSelect, layoutTitle, layoutSelect, hideFromAll, systemTitle, location, marketIDTitle, marketID)
                .buildVBox();
        final DestroyableVBox buttons = BoxBuilder.builder()
                .withStyleClass("buttons-contents")
                .withNodes(trackButton, delete, new GrowingRegion(), save)
                .buildVBox();

        this.setVisible(false);
        this.setManaged(false);
        this.getNodes().addAll(content, new GrowingRegion(), trackButton, buttons);
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);
            setBuildable(colonisationItems.getSelectedColonisationItem());
        });
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, ColonisationSelectedEvent.class, event -> {
            setBuildable(event.getColonisationItem());
        }));
    }

    public void setBuildable(ColonisationItem colonisationItem) {
        if (this.colonisationItem == null || !this.colonisationItem.getUuid().equals(colonisationItem.getUuid())) {
            log.info("Setting colonisation item: {}", colonisationItem);
            this.colonisationItem = colonisationItem;
            update();
        }
    }

    private void update() {
        setVisibility(this, !colonisationItem.isAll());
        setVisibility(trackButton, colonisationItem.isCurrent());
        setVisibility(delete, !colonisationItem.isAll() && !colonisationItem.isCurrent());
        setVisibility(nameTitle, !colonisationItem.isAll() && !colonisationItem.isCurrent());
        setVisibility(nameTextField, !colonisationItem.isAll() && !colonisationItem.isCurrent());
        setVisibility(save, !colonisationItem.isAll() && !colonisationItem.isCurrent());
        setVisibility(marketIDTitle, !colonisationItem.isAll());
        setVisibility(marketID, !colonisationItem.isAll());
        setVisibility(systemTitle, !colonisationItem.isAll() && !Objects.equals(colonisationItem.getSystemName(), "Sol") && !Objects.equals(colonisationItem.getSystemName(), null));
        setVisibility(location, !colonisationItem.isAll() && !Objects.equals(colonisationItem.getSystemName(), "Sol") && !Objects.equals(colonisationItem.getSystemName(), null));
        setVisibility(buildableSelect, !colonisationItem.isAll() && !colonisationItem.isCurrent());
        setVisibility(layoutSelect, !colonisationItem.isAll() && !colonisationItem.isCurrent());

        setVisibility(typeTitle, !colonisationItem.isAll() && !colonisationItem.isCurrent());
        setVisibility(buildableSelect, !colonisationItem.isAll() && !colonisationItem.isCurrent());
        setVisibility(layoutTitle, !colonisationItem.isAll() && !colonisationItem.isCurrent());
        setVisibility(layoutSelect, !colonisationItem.isAll() && !colonisationItem.isCurrent());
        nameTextField.setText(!colonisationItem.isCurrent() ? colonisationItem.getName() : "");
        marketID.setText(colonisationItem.getMarketID());
        if (!Objects.equals(colonisationItem.getSystemName(), "Sol") && !Objects.equals(colonisationItem.getSystemName(), null)) {
            final String body = (colonisationItem.getSystemName().length() + 1 <= colonisationItem.getBodyName().length()) ? colonisationItem.getBodyName().substring(colonisationItem.getSystemName().length() + 1) : "";
            location.setLocation(new StarSystem(colonisationItem.getSystemName(), colonisationItem.getX(), colonisationItem.getY(), colonisationItem.getZ()), body);
        } else {
            location.setLocation(StarSystem.SOL);
        }
        hideFromAll.setSelected(colonisationItem.getHideFromAll());
        buildableSelect.getSelectionModel().select(ColonisationBuildable.UNKNOWN.equals(colonisationItem.getColonisationBuildable()) ? null : colonisationItem.getColonisationBuildable());
        layoutSelect.getSelectionModel().select(colonisationItem.getColonisationLayout());
    }

    private void setVisibility(Node node, boolean visible) {
        node.setVisible(visible);
        node.setManaged(visible);
    }

    public void clear() {
        setBuildable(ColonisationItem.ALL);
    }
}
