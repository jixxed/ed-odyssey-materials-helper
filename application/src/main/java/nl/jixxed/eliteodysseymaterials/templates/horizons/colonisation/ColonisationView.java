package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import javafx.scene.control.MenuButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.MenuButtonBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItem;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItems;
import nl.jixxed.eliteodysseymaterials.enums.ColonisationBuildable;
import nl.jixxed.eliteodysseymaterials.enums.ColonisationCategory;
import nl.jixxed.eliteodysseymaterials.service.ColonisationService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalInitEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ColonisationView extends VBox implements DestroyableTemplate {
    private FlowPane buildables;
    private FlowPane projects;
    private BillOfMaterials billOfMaterials;
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    @Getter
    private final java.util.List<EventListener<?>> eventListeners = new ArrayList<>();

    public ColonisationView() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
//        final ColonisationButton[] list = Arrays.stream(ColonisationCategory.values())
//                .map(category -> new ColonisationButton(category, buildable -> billOfMaterials.setBuildable(buildable)))
//                .toArray(ColonisationButton[]::new);
        final MenuButton[] list = Arrays.stream(ColonisationCategory.values())
                .map(category ->
                        MenuButtonBuilder.builder()
                                .withText(LocaleService.getStringBinding(category.getLocalizationKey()))
                                .withMenuItems(Arrays.stream(ColonisationBuildable.values())
                                        .filter(buildable -> buildable.getColonisationCategory().equals(category))
                                        .collect(Collectors.toMap(ColonisationBuildable::getLocalizationKey, c -> event -> addColonisationItem(c))))
                                .build()).toArray(MenuButton[]::new);
        buildables = FlowPaneBuilder.builder().withStyleClass("colonisation-buttons").withNodes(list).build();
        projects = FlowPaneBuilder.builder().withStyleClass("colonisation-projects").build();
        refreshProjects();
        billOfMaterials = new BillOfMaterials();
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);
            billOfMaterials.setBuildable(colonisationItems.getSelectedColonisationItem());
        });
        this.getChildren().addAll(buildables, projects, billOfMaterials);
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

    private void refreshProjects(){
        projects.getChildren().clear();
        ColonisationProject[] projectList = APPLICATION_STATE.getPreferredCommander().map(commander -> {
            final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);
            return colonisationItems.getAllColonisationItems().stream()
                    .sorted(Comparator.comparing(ColonisationItem::getUuid))
                    .map(colonisationItem -> new ColonisationProject(colonisationItem, item -> billOfMaterials.setBuildable(item), () -> {
                        refreshProjects();
                        billOfMaterials.setBuildable(ColonisationItem.ALL);
                    }))
                    .toArray(ColonisationProject[]::new);
        }).orElseGet(() -> new ColonisationProject[]{});
        projects.getChildren().addAll(projectList);

    }

    @Override
    public void initEventHandling() {
        eventListeners.add(EventService.addListener(this, JournalInitEvent.class, journalInitEvent -> {
            if (journalInitEvent.isInitialised()) refreshProjects();

            APPLICATION_STATE.getPreferredCommander().ifPresentOrElse(commander -> {
                billOfMaterials.setBuildable(ColonisationService.getColonisationItems(commander).getSelectedColonisationItem());
            }, () -> billOfMaterials.clear());
        }));
    }
}
