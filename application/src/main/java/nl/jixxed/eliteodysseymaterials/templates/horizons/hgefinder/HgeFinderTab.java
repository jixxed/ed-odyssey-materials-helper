package nl.jixxed.eliteodysseymaterials.templates.horizons.hgefinder;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterialType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;
import nl.jixxed.eliteodysseymaterials.enums.SystemAllegiance;
import nl.jixxed.eliteodysseymaterials.service.HighGradeEmissionService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.hge.ExpiringMessage;
import nl.jixxed.eliteodysseymaterials.service.hge.Message;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsTab;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

@Slf4j
public class HgeFinderTab extends HorizonsTab {
    private ScrollPane scrollPane;
    private FlowPane categories;
    private FlowPane hgeCards;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    private TimerTask task;
    private static Timer timer;
    @Override
    public HorizonsTabs getTabType() {
        return HorizonsTabs.HGEFINDER;
    }

    public HgeFinderTab() {
        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        this.textProperty().bind(LocaleService.getStringBinding("tabs.hge"));
        this.categories = FlowPaneBuilder.builder()
                .withNodes(
                        new HgeCategoryCard(HorizonsMaterialType.CHEMICAL),
                        new HgeCategoryCard(HorizonsMaterialType.THERMIC),
                        new HgeCategoryCard(HorizonsMaterialType.HEAT),
                        new HgeCategoryCard(HorizonsMaterialType.MECHANICAL_COMPONENTS),
                        new HgeCategoryCard(HorizonsMaterialType.CAPACITORS),
                        new HgeCategoryCard(HorizonsMaterialType.SHIELDING),
                        new HgeCategoryCard(HorizonsMaterialType.COMPOSITE),
                        new HgeCategoryCard(HorizonsMaterialType.ALLOYS)
                ).withOrientation(Orientation.HORIZONTAL)
                .build();
        this.hgeCards = FlowPaneBuilder.builder().build();
        HighGradeEmissionService.getMessages().forEach(this::createCard);
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(BoxBuilder.builder().withNodes(this.categories, this.hgeCards).buildVBox())
                .build();
        this.setContent(this.scrollPane);
        this.task = new TimerTask() {
            @Override
            public void run() {
                hgeCards.getChildren().removeIf(node -> ((HgeCard)node).isExpired());
            }
        };
        this.timer = new Timer("cleanup", true);
        this.timer.scheduleAtFixedRate(this.task, 0, 1000L);
    }

    private void createCard(ExpiringMessage expiringMessage) {
        final Message message = expiringMessage.getMessage();
        final List<HorizonsMaterial> materials = message.getMaterialsFound().stream().map(HorizonsMaterial::subtypeForName).collect(Collectors.toList());
        final StarSystem starSystem = new StarSystem(message.getSystem(), message.getStarPos()[0], message.getStarPos()[1], message.getStarPos()[2]);
        final SystemAllegiance allegiance = SystemAllegiance.forKey(message.getAllegiance());
        hgeCards.getChildren().add(new HgeCard(starSystem,
                message.getFaction(),
                message.getInfluence(),
                materials,
                message.getState(),
                allegiance,
                expiringMessage.getExpiration()));
    }

    private void initEventHandling() {
        this.eventListeners.add(EventService.addListener(this, HorizonsHgeSearchEvent.class, horizonsHgeSearchEvent -> {
            update(horizonsHgeSearchEvent.getSearch());
        }));

        this.eventListeners.add(EventService.addStaticListener(HighGradeEmissionEvent.class, event -> {
            Platform.runLater(() -> createCard(event.getExpiringMessage()));
        }));

        this.eventListeners.add(EventService.addStaticListener(TerminateApplicationEvent.class, event -> {
            if (timer != null) {
                timer.cancel();
                log.info("HGE shutdown finished.");
            }
        }));
    }

    private void update(final String search) {

    }
}
