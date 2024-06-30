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
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.HighGradeEmissionService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.hge.ExpiringMessage;
import nl.jixxed.eliteodysseymaterials.service.hge.Message;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsTab;

import java.util.*;
import java.util.stream.Stream;

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
            this.hgeCards = FlowPaneBuilder.builder().withStyleClass("hge-card-flow").build();
            HighGradeEmissionService.getMessages().forEach(this::createCard);
            final DestroyableLabel instructions = LabelBuilder.builder().withStyleClass("").withText(LocaleService.getStringBinding("hge.explain")).build();
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

    private void createCard(ExpiringMessage expiringMessage) {
        final Message message = expiringMessage.getMessage();
        final SystemAllegiance allegiance = SystemAllegiance.forKey(!message.getSystemAllegiance().isEmpty() ? message.getSystemAllegiance() : message.getAllegiance());
        final List<HorizonsMaterial> materials = getMaterials(message.getEvent(), message.getState(), allegiance, message.getMaterialsFound());
        final StarSystem starSystem = new StarSystem(message.getSystem(), message.getStarPos()[0], message.getStarPos()[1], message.getStarPos()[2]);
        log.debug(expiringMessage.getExpiration().toString());
        if (message.getEvent().equalsIgnoreCase("USSDrop")) {
            hgeCards.getChildren().removeIf(node -> Objects.equals(((HgeCard) node).getSystemName(), starSystem.getName())
                    && Objects.equals(((HgeCard) node).getFaction(), message.getFaction())
                    && ((materials.isEmpty() && ((HgeCard) node).getMaterials().isEmpty()) ||
                    !materials.isEmpty() && !((HgeCard) node).getMaterials().isEmpty() && materials.containsAll(((HgeCard) node).getMaterials()))
            );
        }
        if (hgeCards.getChildren().size() >= 100) {
            return;
        }
        hgeCards.getChildren().add(new HgeCard(starSystem,
                message.getFaction(),
                message.getInfluence(),
                materials,
                message.getState(),
                allegiance,
                expiringMessage.getExpiration(),
                message.getEvent().equalsIgnoreCase("FSSSignalDiscovered"),
                expiringMessage.isOwned()));
    }

    private static List<HorizonsMaterial> getMaterials(String event, String state, SystemAllegiance allegiance, Set<String> materialsFound) {
        final List<HorizonsMaterial> materials = new ArrayList<>();

        if ("FSSSignalDiscovered".equalsIgnoreCase(event)) {
            if (state.equalsIgnoreCase("Outbreak")) {
                //  Pharmaceutical Isolators
                materials.add(Manufactured.PHARMACEUTICALISOLATORS);
            } else if (Stream.of("CivilWar", "War").anyMatch(state::equalsIgnoreCase)) {
                // Military Grade Alloys
                // Military Supercapacitors
                materials.add(Manufactured.MILITARYGRADEALLOYS);
                materials.add(Manufactured.MILITARYSUPERCAPACITORS);
            } else if (Stream.of("Boom", "Expansion").anyMatch(state::equalsIgnoreCase)) {
                // Proto Heat Radiators
                // Proto Radiolic Alloys / Proto Light Alloys
                materials.add(Manufactured.PROTOHEATRADIATORS);
                materials.add(Manufactured.PROTORADIOLICALLOYS);
                materials.add(Manufactured.PROTOLIGHTALLOYS);
            } else if (Stream.of("CivilUnrest").anyMatch(state::equalsIgnoreCase)) {
                //Improvised Components
                materials.add(Manufactured.IMPROVISEDCOMPONENTS);
            }
            if (SystemAllegiance.EMPIRE.equals(allegiance)) {
                //Imperial Shielding
                materials.add(Manufactured.IMPERIALSHIELDING);
            } else if (SystemAllegiance.FEDERATION.equals(allegiance)) {
                //Core Dynamic Composites / Proprietary Composites
                materials.add(Manufactured.FEDCORECOMPOSITES);
                materials.add(Manufactured.FEDPROPRIETARYCOMPOSITES);
            }
        } else {
            if (materialsFound != null) {
                materialsFound.stream().map(HorizonsMaterial::subtypeForName).forEach(materials::add);
            }
            final List<HorizonsMaterial> composites = List.of(Manufactured.FEDCORECOMPOSITES, Manufactured.FEDPROPRIETARYCOMPOSITES);
            if (materials.stream().anyMatch(composites::contains)) {
                if (!materials.contains(Manufactured.FEDCORECOMPOSITES)) {
                    materials.add(Manufactured.FEDCORECOMPOSITES);
                }
                if (!materials.contains(Manufactured.FEDPROPRIETARYCOMPOSITES)) {
                    materials.add(Manufactured.FEDPROPRIETARYCOMPOSITES);
                }
            }
            final List<HorizonsMaterial> alloys = List.of(Manufactured.PROTORADIOLICALLOYS, Manufactured.PROTOLIGHTALLOYS);
            if (materials.stream().anyMatch(alloys::contains)) {
                if (!materials.contains(Manufactured.PROTORADIOLICALLOYS)) {
                    materials.add(Manufactured.PROTORADIOLICALLOYS);
                }
                if (!materials.contains(Manufactured.PROTOLIGHTALLOYS)) {
                    materials.add(Manufactured.PROTOLIGHTALLOYS);
                }
            }
        }
        return materials;
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
