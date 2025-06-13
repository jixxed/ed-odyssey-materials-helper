package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItem;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItems;
import nl.jixxed.eliteodysseymaterials.service.ColonisationService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

public class ColonisationView extends DestroyableVBox implements DestroyableEventTemplate {
    private BillOfMaterials billOfMaterials;
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private ColonisationProjectView colonisationProjectView;
    private ColonisationProjectStatistics colonisationProjectStatistics;

    private DestroyableComboBox<ColonisationItem> colonisationSelect;
    private DestroyableFlowPane projectInfo;
    private DestroyableLabel hint;
    private Trailblazers trailblazers;

    public ColonisationView() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("colonisation-view");
        hint = LabelBuilder.builder()
                .withStyleClass("hint")
                .withText("tab.colonisation.hint")
                .build();
        final Optional<ColonisationItems> wishlists = APPLICATION_STATE.getPreferredCommander().map(ColonisationService::getColonisationItems);
        final Set<ColonisationItem> items = wishlists.map(ColonisationItems::getAllColonisationItems)
                .orElse(Collections.emptySet());
        this.colonisationSelect = ComboBoxBuilder.builder(ColonisationItem.class)
                .withStyleClass("colonisation-select")
                .withSelected(wishlists.map(ColonisationItems::getSelectedColonisationItem).orElse(null))
                .withItemsProperty(FXCollections.observableArrayList(items.stream().sorted(Comparator.comparing(ColonisationItem::isAll).thenComparing(ColonisationItem::isCurrent).reversed().thenComparing(ColonisationItem::getName)).toList()))
                .withValueChangeListener((_, _, newValue) -> {
                    if (newValue != null) {
                        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
                            final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);
                            colonisationItems.setSelectedColonisationItemUUID(newValue.getUuid());
                            ColonisationService.saveColonisationItems(commander, colonisationItems);
                            EventService.publish(new ColonisationSelectedEvent(newValue));
                        });
                    }
                })
                .build();

        DestroyableCheckBox pauseCapiCheckBox = CheckBoxBuilder.builder()
                .withStyleClass("colonisation-checkbox")
                .withText("tab.colonisation.pause.capi")
                .withSelected(PreferencesService.getPreference("colonisation.horizons.pause.capi", false))
                .withSelectedProperty((_, _, newValue) ->
                {
                    PreferencesService.setPreference("colonisation.horizons.pause.capi", newValue);
                })
                .build();
        colonisationProjectView = new ColonisationProjectView();
        colonisationProjectStatistics = new ColonisationProjectStatistics();
        trailblazers = new Trailblazers();
        final DestroyableVBox statsBlazers = BoxBuilder.builder()
                .withStyleClass("stats-blazers")
                .withNodes(colonisationProjectStatistics, trailblazers)
                .buildVBox();
        projectInfo = FlowPaneBuilder.builder()
                .withStyleClass("project-info")
                .withNodes(colonisationProjectView, statsBlazers)
                .build();
        refreshProjects();
        billOfMaterials = new BillOfMaterials();
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);

            if (colonisationItems.getAllColonisationItems().size() <= 1) {
                setVisibility(projectInfo, false);
                setVisibility(hint, true);
            } else {
                setVisibility(projectInfo, true);
                setVisibility(hint, false);
            }
            billOfMaterials.setBuildable(colonisationItems.getSelectedColonisationItem());
        });
        final DestroyableHBox menu = BoxBuilder.builder()
                .withStyleClass("colonisation-menu")
                .withNodes(colonisationSelect, pauseCapiCheckBox)
                .buildHBox();
        this.getNodes().addAll(menu, hint, projectInfo, billOfMaterials);

    }

    private void refreshProjects() {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);
            final Set<ColonisationItem> items = colonisationItems.getAllColonisationItems();
            this.colonisationSelect.getItems().clear();
            this.colonisationSelect.getItems().addAll(items.stream().sorted(Comparator.comparing(ColonisationItem::isAll).thenComparing(ColonisationItem::isCurrent).reversed().thenComparing(ColonisationItem::getName)).toList());
            this.colonisationSelect.getSelectionModel().select(colonisationItems.getSelectedColonisationItem());
        });
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, JournalInitEvent.class, journalInitEvent -> {
            if (journalInitEvent.isInitialised()) {
                update();
            }
        }));
        register(EventService.addListener(true, this, ColonisationConstructionDepotEvent.class, event -> {
            update();
        }));
        register(EventService.addListener(true, this, ColonisationRefreshEvent.class, event -> {
            update();
        }));
        register(EventService.addListener(true, this, LanguageChangedEvent.class, _ -> refreshProjects()));

    }

    private void update() {
        refreshProjects();
        APPLICATION_STATE.getPreferredCommander().ifPresentOrElse(commander -> {
                    final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);

                    if (colonisationItems.getAllColonisationItems().size() <= 1) {
                        setVisibility(projectInfo, false);
                        setVisibility(hint, true);
                    } else {
                        setVisibility(projectInfo, true);
                        setVisibility(hint, false);
                    }
                    final ColonisationItem selectedColonisationItem = colonisationItems.getSelectedColonisationItem();
                    billOfMaterials.setBuildable(selectedColonisationItem);
                    colonisationProjectView.setBuildable(selectedColonisationItem);
                },
                () -> {
                    billOfMaterials.clear();
                    colonisationProjectView.clear();
                });
    }

    private void setVisibility(Node node, boolean visible) {
        node.setVisible(visible);
        node.setManaged(visible);
    }
}
