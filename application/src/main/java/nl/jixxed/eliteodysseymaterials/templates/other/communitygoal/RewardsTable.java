/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.other.communitygoal;

import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.Band;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.cg.ReportModels;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import java.util.*;

public class RewardsTable extends DestroyableVBox implements DestroyableTemplate {
    DestroyableHBox table;
    public RewardsTable() {
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("cg-rewards-table");
        table = BoxBuilder.builder().withStyleClass("reward-table").buildHBox();
        DestroyableLabel title = LabelBuilder.builder().withStyleClass("rewards-title").withNonLocalizedText("Tier rewards").build();
        this.getNodes().addAll(title, BoxBuilder.builder().withStyleClass("reward-table-box").withNode(table).buildVBox());
    }

    public void update(ReportModels.CommunityGoalReport report) {
        table.getNodes().clear();
//        report.tierRewards().stream().findFirst().ifPresent(tierReward -> {
//            final int tier = tierReward.tier();
//            List<ReportModels.BandReward> bandReward = tierReward.bands();
//            this.getNodes().add(BoxBuilder.builder().buildVBox());
//        });
        DestroyableVBox tierBox = BoxBuilder.builder().withStyleClass("reward-tiers").withNode(LabelBuilder.builder().withStyleClass("cg-reward-title").withNonLocalizedText("Tier").build()).buildVBox();//TODO localize
        Map<Band, DestroyableVBox> rewards = new HashMap<>();
        Set<Band> bands = new HashSet<>();
        report.tierRewards().stream().sorted(Comparator.comparingInt(ReportModels.TierReward::tier).reversed()).forEach(tierReward -> {
            final int tier = tierReward.tier();
            tierBox.getNodes().add(LabelBuilder.builder().withStyleClass("cg-reward-tier").withNonLocalizedText(String.valueOf(tier)).build());
            tierReward.bands().forEach(reward -> bands.add(new Band(reward.band())));
        });
        table.getNodes().add(tierBox);
        for (int i = tierBox.getNodes().size() - 1; i > 0; i--) {
            int finalI = i;
            bands.stream().sorted().forEach(band -> {
                Optional<ReportModels.BandReward> reward = report.tierRewards().stream().filter(tierReward -> tierReward.tier() == finalI)
                        .findFirst().flatMap(tierReward -> tierReward.bands().stream().filter(bandReward -> new Band(bandReward.band()).equals(band)).findFirst());
                rewards.computeIfAbsent(band, k -> BoxBuilder.builder().withStyleClass("reward-values").withNode(LabelBuilder.builder().withStyleClass("cg-reward-title").withNonLocalizedText(band.getName()).build()).buildVBox())
                        .getNodes()
                        .add(LabelBuilder.builder().withStyleClass("cg-reward-value")
                                .withNonLocalizedText(reward
                                        .map(ReportModels.BandReward::reward)
                                        .map(Formatters.NUMBER_FORMAT_0::format)
                                        .orElse("?"))
                                .build());
            });
        }
        rewards.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(Map.Entry::getValue).forEach(rewardsBox -> {
            table.getNodes().addAll(new GrowingRegion("cg-spacer"), rewardsBox);
        });
        table.getNodes().add(new GrowingRegion("cg-spacer"));
    }
}