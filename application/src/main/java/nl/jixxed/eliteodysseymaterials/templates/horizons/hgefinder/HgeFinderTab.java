package nl.jixxed.eliteodysseymaterials.templates.horizons.hgefinder;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.Allegiance;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterialType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.hge.ExpiringMessage;
import nl.jixxed.eliteodysseymaterials.service.hge.FactionV2;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsTab;

import java.util.*;

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
        this.getStyleClass().add("hge-box");
        this.textProperty().bind(LocaleService.getStringBinding("tabs.hge"));

        if (Boolean.FALSE.equals(PreferencesService.getPreference(PreferenceConstants.TRACKING_OPT_OUT, false))) {
            this.categories = FlowPaneBuilder.builder().withStyleClass("hge-category-flow")
                    .withNodes(
                            new HighGradeEmissionCard(HorizonsMaterialType.CHEMICAL),
                            new HighGradeEmissionCard(HorizonsMaterialType.THERMIC),
                            new HighGradeEmissionCard(HorizonsMaterialType.HEAT),
                            new HighGradeEmissionCard(HorizonsMaterialType.MECHANICAL_COMPONENTS),
                            new HighGradeEmissionCard(HorizonsMaterialType.CAPACITORS),
                            new HighGradeEmissionCard(HorizonsMaterialType.SHIELDING),
                            new HighGradeEmissionCard(HorizonsMaterialType.COMPOSITE),
                            new HighGradeEmissionCard(HorizonsMaterialType.ALLOYS)
                    ).withOrientation(Orientation.HORIZONTAL)
                    .build();
            this.hgeCards = FlowPaneBuilder.builder().withStyleClass("hge-card-flow").build();
//            HighGradeEmissionService.getMessages().forEach(this::createCard);
            final DestroyableLabel instructions = LabelBuilder.builder().withStyleClass("hge-my-collected-title").withText(LocaleService.getStringBinding("hge.explain")).build();
            this.scrollPane = ScrollPaneBuilder.builder()
                    .withContent(BoxBuilder.builder().withStyleClass("hge-flows").withNodes(this.categories, instructions, this.hgeCards).buildVBox())
                    .build();
            this.setContent(this.scrollPane);
            this.task = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        hgeCards.getChildren().removeIf(node -> ((HgeCard) node).isExpired());
                        hgeCards.getChildren().forEach(node -> ((HgeCard) node).update());
                    });
                }
            };
            this.timer = new Timer("updateHge", true);
            this.timer.scheduleAtFixedRate(this.task, 0, 1000L);
            //TODO testing cards
//            hgeCards.getChildren().add(new HgeCard(Engineer.BILL_TURNER.getStarSystem(),
//                    "FSS Faction",
//                    0.55,
//                    getMaterials("FSSSignalDiscovered", "Boom", SystemAllegiance.FEDERATION, null),
//                    "Boom",
//                    SystemAllegiance.FEDERATION,
//                    LocalDateTime.now().plusHours(1), true, true));
//            hgeCards.getChildren().add(new HgeCard(Engineer.BILL_TURNER.getStarSystem(),
//                    null,
//                    null,
//                    getMaterials("FSSSignalDiscovered", "War", SystemAllegiance.EMPIRE, null),
//                    "War",
//                    SystemAllegiance.EMPIRE,
//                    LocalDateTime.now().plusHours(1), true, false));
//            hgeCards.getChildren().add(new HgeCard(Engineer.BILL_TURNER.getStarSystem(),
//                    null,
//                    null,
//                    getMaterials("FSSSignalDiscovered", "Outbreak", SystemAllegiance.INDEPENDENT, null),
//                    "Outbreak",
//                    SystemAllegiance.INDEPENDENT,
//                    LocalDateTime.now().plusHours(1), true, false));
//            hgeCards.getChildren().add(new HgeCard(Engineer.BILL_TURNER.getStarSystem(),
//                    null,
//                    null,
//                    getMaterials("USSDrop", "CivilUnrest", SystemAllegiance.INDEPENDENT, Set.of("improvisedcomponents")),
//                    "Civil Unrest",
//                    SystemAllegiance.INDEPENDENT,
//                    LocalDateTime.now().plusHours(1), false, false));
//            hgeCards.getChildren().add(new HgeCard(Engineer.BILL_TURNER.getStarSystem(),
//                    null,
//                    null,
//                    getMaterials("USSDrop", "War", SystemAllegiance.EMPIRE, Set.of("imperialshielding")),
//                    "War",
//                    SystemAllegiance.EMPIRE,
//                    LocalDateTime.now().plusHours(1), false, false));
//            hgeCards.getChildren().add(new HgeCard(Engineer.BILL_TURNER.getStarSystem(),
//                    null,
//                    null,
//                    getMaterials("USSDrop", "Outbreak", SystemAllegiance.INDEPENDENT, Set.of("pharmaceuticalisolators")),
//                    "Outbreak",
//                    SystemAllegiance.INDEPENDENT,
//                    LocalDateTime.now().plusHours(1), false, false));
//            hgeCards.getChildren().add(new HgeCard(Engineer.BILL_TURNER.getStarSystem(),
//                    null,
//                    null,
//                    getMaterials("USSDrop", "CivilUnrest", SystemAllegiance.INDEPENDENT, Set.of("improvisedcomponents")),
//                    "Civil Unrest",
//                    SystemAllegiance.INDEPENDENT,
//                    LocalDateTime.now().plusHours(1), false, false));
        } else {
            this.scrollPane = ScrollPaneBuilder.builder()
                    .withContent(LabelBuilder.builder().withStyleClass("hge-disabled").withText(LocaleService.getStringBinding("hge.tracking.disabled")).build())
                    .build();
            this.setContent(this.scrollPane);
        }
    }

    private void createCard(ExpiringMessage message, Set<HorizonsMaterial> collectedMaterials) {
        final List<HorizonsMaterialType> materialTypes = getMaterialTypes(message.getSystemAllegiance(), message.getFactions());
        final StarSystem starSystem = new StarSystem(message.getSystem(), message.getStarPos()[0], message.getStarPos()[1], message.getStarPos()[2]);
//        log.debug(expiringMessage.getExpiration().toString());
//        if (message.getEvent().equalsIgnoreCase("USSDrop")) {
//            hgeCards.getChildren().removeIf(node -> Objects.equals(((HgeCard) node).getSystemName(), starSystem.getName())
//                    && Objects.equals(((HgeCard) node).getFaction(), message.getFaction())
//                    && ((collectedMaterials.isEmpty() && ((HgeCard) node).getMaterials().isEmpty()) ||
//                    !collectedMaterials.isEmpty() && !((HgeCard) node).getMaterials().isEmpty() && collectedMaterials.containsAll(((HgeCard) node).getMaterials()))
//            );
//        }
        if (hgeCards.getChildren().size() >= 100) {
            return;
        }
        FactionV2 faction = message.getFactions().stream().filter(f -> f.getName().equalsIgnoreCase(message.getFaction())).findFirst().orElse(null);
        hgeCards.getChildren().add(new HgeCard(starSystem,
                faction,
                collectedMaterials,
                message.getSystemAllegiance(),
                message.getExpiration(),
                message.getEvent().equalsIgnoreCase("FSSSignalDiscovered")));
    }

    private static List<HorizonsMaterialType> getMaterialTypes( Allegiance allegiance, Set<FactionV2> factions) {
        final List<HorizonsMaterialType> materialTypes = new ArrayList<>();
        if (Allegiance.EMPIRE.equals(allegiance)) {
            //Imperial Shielding
            materialTypes.add(HorizonsMaterialType.SHIELDING);
        } else if (Allegiance.FEDERATION.equals(allegiance)) {
            //Core Dynamic Composites / Proprietary Composites
            materialTypes.add(HorizonsMaterialType.COMPOSITE);
        }
        factions.forEach(faction -> {
            faction.getActiveStates().forEach(state -> {
                List<HorizonsMaterialType> types = switch (state){
                    case OUTBREAK -> List.of(HorizonsMaterialType.CHEMICAL);
                    case WAR, CIVILWAR -> List.of(HorizonsMaterialType.THERMIC, HorizonsMaterialType.CAPACITORS);
                    case BOOM, EXPANSION -> List.of(HorizonsMaterialType.HEAT, HorizonsMaterialType.ALLOYS);
                    case CIVILUNREST -> List.of(HorizonsMaterialType.MECHANICAL_COMPONENTS);
                    default -> Collections.emptyList();
                };
                    materialTypes.addAll(types);
            });

        });
        return materialTypes;
    }

    private void initEventHandling() {
        this.eventListeners.add(EventService.addListener(this, HorizonsHgeSearchEvent.class, horizonsHgeSearchEvent -> {
            update(horizonsHgeSearchEvent.getSearch());
        }));

        this.eventListeners.add(EventService.addStaticListener(HighGradeEmissionEvent.class, event -> {
            Platform.runLater(() -> createCard(event.getExpiringMessage(), event.getCollectedMaterials()));
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
