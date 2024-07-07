package nl.jixxed.eliteodysseymaterials.templates.horizons.hgefinder;

import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.Allegiance;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.State;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.hge.FactionV2;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class HgeCard extends VBox implements Template {
    private final boolean highGrade;
    private final boolean owned;
    //            ----------------------------------------------------------
    //            | System    Timbalderis (C) 10.65Ly            (T) 1:59  |
    //            | Faction   Hippies(53.4%)                               |
    //            | Materials                   State         Allegiance   |
    //            | Imperial Shielding          Outbreak      Empire       |
    //            | Pharmaceutical Isolators                               |
    //            |                                                        |
    //            ----------------------------------------------------------

    private HBox systemTimerBox;
    private HBox factionBox;
    private HBox materialsStateAllegianceBox;
    private VBox materialsBox;
    private VBox stateBox;
    private VBox allegianceBox;

    private CopyableSystemLabel system;
    @Getter
    private String faction;
    @Getter
    private String systemName;
    private Double influence;
    @Getter
    private Set<HorizonsMaterial> materials;
    private Set<State> states;
    private Allegiance allegiance;
    private LocalDateTime expiration;
    private DestroyableLabel timer;

    public HgeCard(StarSystem starSystem, FactionV2 faction, Set<HorizonsMaterial> materials, Allegiance allegiance, LocalDateTime expiration, boolean highGrade) {
        this.system = new CopyableSystemLabel();
        this.system.setStarSystem(starSystem);
        this.systemName = starSystem.getName();
        this.faction = faction != null ? faction.getName() : "";
        this.influence = faction != null ?faction.getInfluence() : 0D;
        this.materials = materials;
        this.states = faction != null ? faction.getActiveStates() : Set.of();
        this.allegiance = allegiance;
        this.expiration = expiration;
        this.highGrade = highGrade;
        this.owned = false;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("hge-card");
        if(owned) {
            this.getStyleClass().add("hge-card-highlight");
        }
        final DestroyableLabel factionLabel = this.faction != null
                ? LabelBuilder.builder().withStyleClass("hge-card-faction-label").withNonLocalizedText(this.faction + " ("+ Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(influence * 100) + "%)").build()
                : LabelBuilder.builder().withStyleClass("hge-card-faction-label").withText(LocaleService.getStringBinding("hge.unknown")).build();
        Tooltip.install(factionLabel, TooltipBuilder.builder()
                .withShowDelay(javafx.util.Duration.millis(100))
                .withShowDuration(javafx.util.Duration.seconds(30))
                .withNonLocalizedText(factionLabel.getText()).build());
        final Duration duration = Duration.between(LocalDateTime.now(), expiration);
        timer = LabelBuilder.builder().withStyleClass("hge-card-timer").withNonLocalizedText(duration.toMinutesPart() + ":" + String.format("%02d", duration.toSecondsPart())).build();
        final DestroyableResizableImageView imageView = ResizableImageViewBuilder.builder().withStyleClass("hge-card-timer-image").withImage(ImageService.getImage("/images/other/timer.png")).build();
        FlowPane timerPane = FlowPaneBuilder.builder().withStyleClass("hge-card-timer-flow").withNodes(imageView, timer).build();
        final DestroyableLabel signalType = this.highGrade
                ? LabelBuilder.builder().withStyleClasses("hge-card-source", "hge-card-source-fss").withText(LocaleService.getStringBinding("hge.fss")).build()
                : LabelBuilder.builder().withStyleClasses("hge-card-source", "hge-card-source-uss").withText(LocaleService.getStringBinding("hge.uss")).build();
        this.systemTimerBox = BoxBuilder.builder().withStyleClass("hge-card-top").withNodes(
                LabelBuilder.builder().withStyleClass("hge-card-system-faction-label").withText(LocaleService.getStringBinding("hge.card.system")).build(),
                this.system,
                new GrowingRegion(),
                timerPane
        ).buildHBox();
        this.factionBox = BoxBuilder.builder().withStyleClass("hge-card-top").withNodes(
                LabelBuilder.builder().withStyleClass("hge-card-system-faction-label").withText(LocaleService.getStringBinding("hge.card.faction")).build(),
                factionLabel,
                new GrowingRegion(),
                signalType
        ).buildHBox();

        this.materialsBox = BoxBuilder.builder().withStyleClass("hge-card-materials-vbox").withNodes(LabelBuilder.builder().withStyleClass("hge-card-materials-label").withText(LocaleService.getStringBinding("hge.card.materials")).build()).buildVBox();
        this.materials.forEach(material -> materialsBox.getChildren().add(LabelBuilder.builder().withText(LocaleService.getStringBinding(material.getLocalizationKey())).build()));
        final List<DestroyableLabel> stateLabels = this.faction != null
                ? states.stream().map(state1-> LabelBuilder.builder().withText(LocaleService.getStringBinding(state1.getLocalizationKey())).build()).toList()
                : List.of(LabelBuilder.builder().withText(LocaleService.getStringBinding("hge.unknown")).build());
        this.stateBox = BoxBuilder.builder().withStyleClass("hge-card-state-vbox").withNodes(LabelBuilder.builder().withStyleClass("hge-card-state-label").withText(LocaleService.getStringBinding("hge.card.state")).build()).buildVBox();
        this.stateBox.getChildren().addAll(stateLabels);
        final DestroyableResizableImageView allegianceImage = ResizableImageViewBuilder.builder().withStyleClass("hge-card-allegiance-image").withImage(ImageService.getImage("/images/allegiance/" + allegiance.name().toLowerCase() + ".png")).build();
        this.allegianceBox = BoxBuilder.builder().withStyleClass("hge-card-allegiance-vbox").withNodes(
                LabelBuilder.builder().withStyleClass("hge-card-allegiance-label").withText(LocaleService.getStringBinding("hge.card.allegiance")).build(),
                FlowPaneBuilder.builder().withStyleClass("hge-card-allegiance-flow").withNodes(allegianceImage, LabelBuilder.builder().withStyleClass("hge-card-allegiance-value").withText(LocaleService.getStringBinding(allegiance.getLocalizationKey())).build()).build()
        ).buildVBox();
        this.materialsStateAllegianceBox = BoxBuilder.builder().withStyleClass("hge-card-bottom").withNodes(materialsBox, new GrowingRegion(), stateBox, allegianceBox).buildHBox();

        Tooltip.install(timerPane, TooltipBuilder.builder()
                .withShowDelay(javafx.util.Duration.millis(100))
                .withShowDuration(javafx.util.Duration.seconds(30))
                .withText(LocaleService.getStringBinding(highGrade ? "hge.card.fss.timer" : "hge.card.uss.timer")).build());

        this.getChildren().addAll(this.systemTimerBox, this.factionBox, materialsStateAllegianceBox);
    }

    @Override
    public void initEventHandling() {

    }

    public void update() {
        final Duration duration = Duration.between(LocalDateTime.now(), expiration);
        timer.setText(duration.toMinutesPart() + ":" + String.format("%02d", duration.toSecondsPart()));
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiration);
    }
}
