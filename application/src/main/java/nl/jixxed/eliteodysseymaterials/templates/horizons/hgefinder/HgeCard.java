package nl.jixxed.eliteodysseymaterials.templates.horizons.hgefinder;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.SystemAllegiance;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class HgeCard extends VBox implements Template {
    //            ----------------------------------------------------------
    //            | System    Timbalderis (C) 10.65Ly            (T) 1:59  |
    //            | Faction   Hippies(53.4%)                               |
    //            | Materials                   State         Allegiance   |
    //            | Imperial Shielding          Outbreak      Empire       |
    //            | Pharmaceutical Isolators                               |
    //            |                                                        |
    //            ----------------------------------------------------------
    private HBox systemFactionBox;
    private VBox systemFactionLabelsBox;
    private VBox systemFactionValuesBox;

    private HBox materialsStateAllegianceBox;
    private VBox materialsBox;
    private VBox stateBox;
    private VBox allegianceBox;

    private CopyableSystemLabel system;
    private String faction;
    private Double influence;
    private List<HorizonsMaterial> materials;
    private String state;
    private SystemAllegiance allegiance;
    private LocalDateTime expiration;
    private DestroyableLabel timer;

    public HgeCard(StarSystem starSystem, String faction, Double influence, List<HorizonsMaterial> materials, String state, SystemAllegiance allegiance, LocalDateTime expiration) {
        this.system = new CopyableSystemLabel();
        this.system.setStarSystem(starSystem);
        this.faction = faction;
        this.influence = influence;
        this.materials = materials;
        this.state = state;
        this.allegiance = allegiance;
        this.expiration = expiration;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.systemFactionLabelsBox = BoxBuilder.builder().withNodes(LabelBuilder.builder().withNonLocalizedText("SYSTEM").build(), LabelBuilder.builder().withNonLocalizedText("FACTION").build()).buildVBox();
        this.systemFactionValuesBox = BoxBuilder.builder().withNodes(this.system, LabelBuilder.builder().withNonLocalizedText(this.faction).build()).buildVBox();
        final Duration duration = Duration.between(LocalDateTime.now(), expiration);
        timer = LabelBuilder.builder().withNonLocalizedText(duration.toMinutesPart() + ":" + duration.toSecondsPart()).build();
        this.systemFactionBox = BoxBuilder.builder().withNodes(systemFactionLabelsBox, systemFactionValuesBox, new GrowingRegion(), timer).buildHBox();

        this.materialsBox = BoxBuilder.builder().withNodes(LabelBuilder.builder().withNonLocalizedText("MATERIALS").build()).buildVBox();
        this.materials.forEach(material -> materialsBox.getChildren().add(LabelBuilder.builder().withText(LocaleService.getStringBinding(material.getLocalizationKey())).build()));
        this.stateBox = BoxBuilder.builder().withNodes(LabelBuilder.builder().withNonLocalizedText("STATE").build(), LabelBuilder.builder().withNonLocalizedText(state).build()).buildVBox();
        this.allegianceBox = BoxBuilder.builder().withNodes(LabelBuilder.builder().withNonLocalizedText("ALLEGIANCE").build(), LabelBuilder.builder().withText(LocaleService.getStringBinding(allegiance.getLocalizationKey())).build()).buildVBox();
        this.materialsStateAllegianceBox = BoxBuilder.builder().withNodes(materialsBox, stateBox, allegianceBox).buildHBox();

        this.getChildren().addAll(systemFactionBox, materialsStateAllegianceBox);
    }

    @Override
    public void initEventHandling() {

    }

    public void update(){
        final Duration duration = Duration.between(LocalDateTime.now(), expiration);
        timer.setText(duration.toMinutesPart() + ":" + duration.toSecondsPart());
    }

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(expiration);
    }
}
