package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItem;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItems;
import nl.jixxed.eliteodysseymaterials.service.ColonisationService;
import nl.jixxed.eliteodysseymaterials.service.event.ColonisationConstructionDepotEvent;
import nl.jixxed.eliteodysseymaterials.service.event.ColonisationRefreshEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalInitEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import java.util.Comparator;

public class ColonisationView extends DestroyableVBox implements DestroyableEventTemplate {
    private DestroyableFlowPane projects;
    private DestroyableVBox project;
    private BillOfMaterials billOfMaterials;
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private ColonisationProjectView colonisationProjectView;

    public ColonisationView() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("colonisation-view");
//        final DestroyableMenuButton[] list = Arrays.stream(ColonisationCategory.values())
//                .map(category ->
//                        MenuButtonBuilder.builder()
//                                .withText(category.getLocalizationKey())
//                                .withMenuItems(Arrays.stream(ColonisationBuildable.values())
//                                        .filter(buildable -> buildable.getColonisationCategory().equals(category))
//                                        .collect(Collectors.toMap(ColonisationBuildable::getLocalizationKey, c -> _ -> addColonisationItem(c))))
//                                .build()).toArray(DestroyableMenuButton[]::new);
//        DestroyableFlowPane buildables = FlowPaneBuilder.builder()
//                .withStyleClass("buttons")
//                .withNodes(list)
//                .build();
        projects = FlowPaneBuilder.builder()
                .withStyleClass("projects")
                .build();
        colonisationProjectView = new ColonisationProjectView();
        refreshProjects();
        billOfMaterials = new BillOfMaterials();
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);
            billOfMaterials.setBuildable(colonisationItems.getSelectedColonisationItem());
        });
        this.getNodes().addAll(/*buildables, */projects, colonisationProjectView, billOfMaterials);
    }

//    private void addColonisationItem(ColonisationBuildable colonisationBuildable) {
//        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
//            final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);
//            final ColonisationItem colonisationItem = colonisationItems.createColonisationItem(colonisationBuildable);
//            ColonisationService.saveColonisationItems(commander, colonisationItems);
//            refreshProjects();
//            billOfMaterials.setBuildable(colonisationItem);
//        });
//    }

    private void refreshProjects() {
        projects.getNodes().clear();
        ColonisationProject[] projectList = APPLICATION_STATE.getPreferredCommander().map(commander -> {
            final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);
            return colonisationItems.getAllColonisationItems().stream()
                    .sorted(Comparator.comparing(ColonisationItem::getUuid))
                    .map(colonisationItem ->
                            new ColonisationProject(colonisationItem))
                    .toArray(ColonisationProject[]::new);
        }).orElseGet(() -> new ColonisationProject[]{});
        projects.getNodes().addAll(projectList);
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
    }

    private void update() {
        refreshProjects();
        APPLICATION_STATE.getPreferredCommander().ifPresentOrElse(commander -> {
                    final ColonisationItem selectedColonisationItem = ColonisationService.getColonisationItems(commander).getSelectedColonisationItem();
                    billOfMaterials.setBuildable(selectedColonisationItem);
                    colonisationProjectView.setBuildable(selectedColonisationItem);
                },
                () -> {
                    billOfMaterials.clear();
                    colonisationProjectView.clear();
                });
    }
}
