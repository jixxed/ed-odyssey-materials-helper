package nl.jixxed.eliteodysseymaterials.templates.horizons.powerplay;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.HighlightTextFlowBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Power;
import nl.jixxed.eliteodysseymaterials.enums.PowerPerk;
import nl.jixxed.eliteodysseymaterials.enums.RankReward;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.PowerplayEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.SegmentType;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegment;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegmentView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import org.controlsfx.control.SegmentedBar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PowerplayPerk extends VBox implements Template {

    private final List<EventListener<?>> eventListeners = new ArrayList<>();
    public static final int MAX_RANK = 100;
    private SegmentedBar<TypeSegment> segmentedBar;
    private Power power;
    @Getter
    private PowerPerk perk;
    @Getter
    private List<RankReward> rewards;
    private DestroyableLabel currentProgress;

    public PowerplayPerk(Power power, PowerPerk perk, List<RankReward> rewards) {
        this.power = power;
        this.perk = perk;
        this.rewards = rewards;

        this.initComponents();
        this.initEventHandling();
    }

    @Override
    public void initComponents() {
        Label category = LabelBuilder.builder()
                .withStyleClass("power-category")
                .withText(LocaleService.getStringBinding(this.perk.getLocalizationTitleKey()))
                .build();
//        Label subtitle = LabelBuilder.builder()
//                .withStyleClass("power-category-explain")
//                .withText(LocaleService.getStringBinding(this.perk.getLocalizationKey(), this.rewards.getLast().reward(), this.rewards.stream().map(RankReward::rank).map(String::valueOf).collect(Collectors.joining(", "))))
//                .build();
        TextFlow subtitle = HighlightTextFlowBuilder.builder()
                .withStyleClass("power-category-explain")
                .withHighlightStyleClass("power-category-explain-highlight")
                .withText(this.perk.getLocalizationKey(), this.rewards.getLast().reward() + "%", this.rewards.stream().map(RankReward::rank).map(String::valueOf).collect(Collectors.joining(", ")))
                .build();
        final Integer reward = this.rewards.stream().filter(rankReward -> rankReward.rank() <= 55).max(Comparator.comparing(RankReward::rank)).map(RankReward::reward).orElse(0);
        currentProgress = LabelBuilder.builder()
                .withStyleClass("power-category-current")
                .withText(LocaleService.getStringBinding("tab.powerplay.current.reward", reward))
                .build();
        segmentedBar = createSegmentedBar();
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        this.getChildren().add(category);
        this.getChildren().add(subtitle);
        update();
    }

    @Override
    public void initEventHandling() {
        this.eventListeners.add(EventService.addListener(true, this, PowerplayEvent.class, powerplayEvent ->
                this.update()));
    }

    public void update(){
        this.getChildren().remove(segmentedBar);
        this.getChildren().remove(currentProgress);
        if((Power.ALL.equals(this.power) && !Power.NONE.equals(ApplicationState.getInstance().getPower())) || this.power.equals(ApplicationState.getInstance().getPower())){
            segmentedBar = createSegmentedBar();
            this.getChildren().add(segmentedBar);
            final Integer reward = this.rewards.stream().filter(rankReward -> rankReward.rank() <= ApplicationState.getInstance().getPowerRank()).max(Comparator.comparing(RankReward::rank)).map(RankReward::reward).orElse(0);
            currentProgress.textProperty().bind(LocaleService.getStringBinding("tab.powerplay.current.reward", reward));
            this.getChildren().add(currentProgress);
        }
    }

    private SegmentedBar<TypeSegment> createSegmentedBar() {
        SegmentedBar<TypeSegment> segmentedBar = new SegmentedBar<>();
        segmentedBar.getStyleClass().add("power-segmented-bar");
        segmentedBar.setOrientation(Orientation.HORIZONTAL);
        segmentedBar.setInfoNodeFactory(segment -> null);
        segmentedBar.setSegmentViewFactory(segment -> new TypeSegmentView(segment,
                Map.of(
                        SegmentType.RANK_COMPLETE, Color.rgb(222, 222, 222),
                        SegmentType.RANK_NOT_COMPLETE, Color.rgb(128, 128, 128),
                        SegmentType.RANK_NOT_DONE, Color.rgb(255, 255, 255),
                        SegmentType.RANK_DONE, Color.rgb(46, 146, 223)
                ), false));
        int unlockedRank = (int) Math.min(ApplicationState.getInstance().getPowerRank(), MAX_RANK);
        int currentRank = 0;
        for (RankReward rank : this.rewards) {
            if (rank.rank() <= unlockedRank) {
                //segment is complete
                segmentedBar.getSegments().add(new TypeSegment(rank.rank() - currentRank - 1, SegmentType.RANK_COMPLETE));
                segmentedBar.getSegments().add(new TypeSegment(1, SegmentType.RANK_DONE));
            } else if (currentRank > unlockedRank) {
                //segment is not fully complete
                segmentedBar.getSegments().add(new TypeSegment(rank.rank() - currentRank - 1, SegmentType.RANK_NOT_COMPLETE));
                segmentedBar.getSegments().add(new TypeSegment(1, SegmentType.RANK_NOT_DONE));
            } else {
                //segment is partially not complete
                segmentedBar.getSegments().add(new TypeSegment(unlockedRank - currentRank, SegmentType.RANK_COMPLETE));
                segmentedBar.getSegments().add(new TypeSegment(rank.rank() - unlockedRank - 1, SegmentType.RANK_NOT_COMPLETE));
                segmentedBar.getSegments().add(new TypeSegment(1, SegmentType.RANK_NOT_DONE));
            }
            currentRank = rank.rank();
        }
        if (currentRank < MAX_RANK) {
            if (unlockedRank - currentRank > 0) {
                //segment is partially not complete
                segmentedBar.getSegments().add(new TypeSegment(unlockedRank - currentRank, SegmentType.RANK_COMPLETE));
                segmentedBar.getSegments().add(new TypeSegment(MAX_RANK - unlockedRank, SegmentType.RANK_NOT_COMPLETE));
            } else {
                //segment is not fully complete
                segmentedBar.getSegments().add(new TypeSegment(MAX_RANK - currentRank, SegmentType.RANK_NOT_COMPLETE));
            }
        }
        return segmentedBar;
    }


}
