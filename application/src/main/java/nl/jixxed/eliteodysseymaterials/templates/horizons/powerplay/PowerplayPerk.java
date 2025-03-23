package nl.jixxed.eliteodysseymaterials.templates.horizons.powerplay;

import javafx.beans.binding.StringBinding;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.HighlightTextFlowBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.SegmentedBarBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Power;
import nl.jixxed.eliteodysseymaterials.enums.PowerPerk;
import nl.jixxed.eliteodysseymaterials.enums.RankReward;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.PowerplayEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.SegmentType;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegment;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegmentView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PowerplayPerk extends DestroyableVBox implements DestroyableEventTemplate {


    public static final int MAX_RANK = 100;
    private DestroyableSegmentedBar<TypeSegment> segmentedBar;
    private Power power;
    @Getter
    private PowerPerk perk;
    @Getter
    private List<RankReward> rewards;
    private DestroyableLabel currentProgress;
    private static final Callback<TypeSegment, Node> segmentViewFactory = segment -> new TypeSegmentView(segment,
            Map.of(
                    SegmentType.RANK_COMPLETE, Color.rgb(222, 222, 222),
                    SegmentType.RANK_NOT_COMPLETE, Color.rgb(128, 128, 128),
                    SegmentType.RANK_NOT_DONE, Color.rgb(255, 255, 255),
                    SegmentType.RANK_DONE, Color.rgb(46, 146, 223)
            ), false);

    public PowerplayPerk(Power power, PowerPerk perk, List<RankReward> rewards) {
        this.power = power;
        this.perk = perk;
        this.rewards = rewards;

        this.initComponents();
        this.initEventHandling();
    }

    @Override
    public void initComponents() {
        DestroyableLabel category = LabelBuilder.builder()
                .withStyleClass("power-category")
                .withText(this.perk.getLocalizationTitleKey())
                .build();
        DestroyableHighlightTextFlow subtitle = HighlightTextFlowBuilder.builder()
                .withStyleClass("power-category-explain")
                .withHighlightStyleClass("power-category-explain-highlight")
                .withText(this.perk.getLocalizationKey(), this.rewards.getLast().reward() + "%", this.rewards.stream().map(RankReward::rank).map(String::valueOf).collect(Collectors.joining(", ")))
                .build();
        final Integer reward = this.rewards.stream().filter(rankReward -> rankReward.rank() <= 55).max(Comparator.comparing(RankReward::rank)).map(RankReward::reward).orElse(0);
        final StringBinding currentProgressBinding = (PowerPerk.RANK_DECAL.equals(this.perk))
                ? LocaleService.getStringBinding(getRankRewardStringBinding(reward))
                : LocaleService.getStringBinding("tab.powerplay.current.reward", reward);
        currentProgress = LabelBuilder.builder()
                .withStyleClass("power-category-current")
                .withText(currentProgressBinding)
                .build();
        segmentedBar = createSegmentedBar();
        this.getNodes().add(new DestroyableSeparator(Orientation.HORIZONTAL));
        this.getNodes().add(category);
        this.getNodes().add(subtitle);
        this.getNodes().add(segmentedBar);
        this.getNodes().add(currentProgress);
        update();
    }

    private static String getRankRewardStringBinding(Integer reward) {
        return (reward > 0) ? "tab.powerplay.current.reward.unlocked" : "tab.powerplay.current.reward.locked";
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, PowerplayEvent.class, powerplayEvent ->
                this.update()));
    }

    public void update() {
        this.getChildren().remove(segmentedBar);
        this.getChildren().remove(currentProgress);
        if ((Power.ALL.equals(this.power) && !Power.NONE.equals(ApplicationState.getInstance().getPower())) || this.power.equals(ApplicationState.getInstance().getPower())) {
            segmentedBar = createSegmentedBar();
            this.getChildren().add(segmentedBar);
            final Integer reward = this.rewards.stream().filter(rankReward -> rankReward.rank() <= ApplicationState.getInstance().getPowerRank()).max(Comparator.comparing(RankReward::rank)).map(RankReward::reward).orElse(0);
            final StringBinding currentProgressBinding = (PowerPerk.RANK_DECAL.equals(this.perk))
                    ? LocaleService.getStringBinding(getRankRewardStringBinding(reward))
                    : LocaleService.getStringBinding("tab.powerplay.current.reward", reward);
            currentProgress.addBinding(currentProgress.textProperty(), currentProgressBinding);
            this.getChildren().add(currentProgress);
        }
    }

    private DestroyableSegmentedBar<TypeSegment> createSegmentedBar() {
        segmentedBar = SegmentedBarBuilder.builder(TypeSegment.class)
                .withStyleClass("power-segmented-bar")
                .withOrientation(Orientation.HORIZONTAL)
                .withInfoNodeFactory(_ -> null)
                .withSegmentViewFactory(segmentViewFactory)
                .build();

        int unlockedRank = (int) Math.min(ApplicationState.getInstance().getPowerRank(), MAX_RANK);
        if (unlockedRank >= this.rewards.stream().max(Comparator.comparing(RankReward::rank)).map(RankReward::rank).orElse(0)) {
            unlockedRank = MAX_RANK;
        }
        int currentRank = 0;
        for (RankReward rank : this.rewards) {
            if (rank.rank() <= unlockedRank) {
                //segment is complete
                segmentedBar.getSegments().add(segmentedBar.register(new TypeSegment(rank.rank() - currentRank - 1, SegmentType.RANK_COMPLETE)));
                segmentedBar.getSegments().add(segmentedBar.register(new TypeSegment(1, SegmentType.RANK_DONE)));
            } else if (currentRank > unlockedRank) {
                //segment is not fully complete
                segmentedBar.getSegments().add(segmentedBar.register(new TypeSegment(rank.rank() - currentRank - 1, SegmentType.RANK_NOT_COMPLETE)));
                segmentedBar.getSegments().add(segmentedBar.register(new TypeSegment(1, SegmentType.RANK_NOT_DONE)));
            } else {
                //segment is partially not complete
                segmentedBar.getSegments().add(segmentedBar.register(new TypeSegment(unlockedRank - currentRank, SegmentType.RANK_COMPLETE)));
                segmentedBar.getSegments().add(segmentedBar.register(new TypeSegment(rank.rank() - unlockedRank - 1, SegmentType.RANK_NOT_COMPLETE)));
                segmentedBar.getSegments().add(segmentedBar.register(new TypeSegment(1, SegmentType.RANK_NOT_DONE)));
            }
            currentRank = rank.rank();
        }
        if (currentRank < MAX_RANK) {
            if (unlockedRank - currentRank > 0) {
                //segment is partially not complete
                segmentedBar.getSegments().add(segmentedBar.register(new TypeSegment(unlockedRank - currentRank, SegmentType.RANK_COMPLETE)));
                segmentedBar.getSegments().add(segmentedBar.register(new TypeSegment(MAX_RANK - unlockedRank, SegmentType.RANK_NOT_COMPLETE)));
            } else {
                //segment is not fully complete
                segmentedBar.getSegments().add(segmentedBar.register(new TypeSegment(MAX_RANK - currentRank, SegmentType.RANK_NOT_COMPLETE)));
            }
        }
        return segmentedBar;
    }


}
