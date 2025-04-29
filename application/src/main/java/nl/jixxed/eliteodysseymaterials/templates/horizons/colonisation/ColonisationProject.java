package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItem;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItems;
import nl.jixxed.eliteodysseymaterials.service.ColonisationService;
import nl.jixxed.eliteodysseymaterials.service.event.ColonisationRefreshEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableButton;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

public class ColonisationProject extends DestroyableHBox implements DestroyableTemplate {

    private final ColonisationItem colonisationItem;

    public ColonisationProject(ColonisationItem colonisationItem) {
        super();
        this.colonisationItem = colonisationItem;
        initComponents();
    }

    @Override
    public void initComponents() {
        if (ColonisationItem.ALL == colonisationItem) {
            DestroyableButton project = ButtonBuilder.builder()
                    .withText("colonisation.all.projects")
                    .withOnAction(_ -> {
                        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
                            final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);
                            colonisationItems.setSelectedColonisationItemUUID(colonisationItem.getUuid());
                            ColonisationService.saveColonisationItems(commander, colonisationItems);
                            EventService.publish(new ColonisationRefreshEvent());
                        });
                    })
                    .build();
            this.getNodes().add(project);
        } else {
            DestroyableButton project = ButtonBuilder.builder()
                    .withNonLocalizedText(colonisationItem.getName())
                    .withOnAction(_ -> {
                        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
                            final ColonisationItems colonisationItems = ColonisationService.getColonisationItems(commander);
                            colonisationItems.setSelectedColonisationItemUUID(colonisationItem.getUuid());
                            ColonisationService.saveColonisationItems(commander, colonisationItems);
                            EventService.publish(new ColonisationRefreshEvent());
                        });
                    })
                    .build();

            this.getNodes().addAll(project);
        }
    }
}
