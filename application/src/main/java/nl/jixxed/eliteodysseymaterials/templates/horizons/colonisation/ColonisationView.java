package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.MenuButtonBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItem;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItems;
import nl.jixxed.eliteodysseymaterials.enums.ColonisationBuildable;
import nl.jixxed.eliteodysseymaterials.enums.ColonisationCategory;
import nl.jixxed.eliteodysseymaterials.service.ColonisationService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalInitEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableMenuButton;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ColonisationView extends DestroyableVBox implements DestroyableEventTemplate {
    private DestroyableFlowPane projects;
    private BillOfMaterials billOfMaterials;
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public ColonisationView() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("colonisation-view");
        final DestroyableMenuButton[] list = Arrays.stream(ColonisationCategory.values())
                .map(category ->
                        MenuButtonBuilder.builder()
                                .withText(category.getLocalizationKey())
                                .withMenuItems(Arrays.stream(ColonisationBuildable.values())
                                        .filter(buildable -> buildable.getColonisationCategory().equals(category))
                                        .collect(Collectors.toMap(ColonisationBuildable::getLocalizationKey, c -> _ -> addColonisationItem(c))))
                                .build()).toArray(DestroyableMenuButton[]::new);
        DestroyableFlowPane buildables = FlowPaneBuilder.builder()
                .withStyleClass("buttons")
                .withNodes(list)
                .build();
        projects = FlowPaneBuilder.builder()
                .withStyleClass("projects")
                .build();
        refreshProjects();
        billOfMaterials = new BillOfMaterials();
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);
            billOfMaterials.setBuildable(colonisationItems.getSelectedColonisationItem());
        });
        this.getNodes().addAll(buildables, projects, billOfMaterials);
    }

    private void addColonisationItem(ColonisationBuildable colonisationBuildable) {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);
            final ColonisationItem colonisationItem = colonisationItems.createColonisationItem(colonisationBuildable);
            ColonisationService.saveColonisationItems(commander, colonisationItems);
            refreshProjects();
            billOfMaterials.setBuildable(colonisationItem);
        });
    }

    private void refreshProjects() {
        projects.getNodes().clear();
        ColonisationProject[] projectList = APPLICATION_STATE.getPreferredCommander().map(commander -> {
            final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);
            return colonisationItems.getAllColonisationItems().stream()
                    .sorted(Comparator.comparing(ColonisationItem::getUuid))
                    .map(colonisationItem -> new ColonisationProject(colonisationItem,
                            item -> billOfMaterials.setBuildable(item),
                            () -> {
                                refreshProjects();
                                billOfMaterials.setBuildable(ColonisationItem.ALL);
                            }))
                    .toArray(ColonisationProject[]::new);
        }).orElseGet(() -> new ColonisationProject[]{});
        projects.getNodes().addAll(projectList);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(this, JournalInitEvent.class, journalInitEvent -> {
            if (journalInitEvent.isInitialised()) refreshProjects();

            APPLICATION_STATE.getPreferredCommander().ifPresentOrElse(commander ->
                            billOfMaterials.setBuildable(ColonisationService.getColonisationItems(commander).getSelectedColonisationItem()),
                    () -> billOfMaterials.clear());
        }));
    }
}
