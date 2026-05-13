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

import javafx.geometry.Orientation;
import javafx.scene.layout.Region;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.Goal;
import nl.jixxed.eliteodysseymaterials.service.cg.CommunityGoalsService;
import nl.jixxed.eliteodysseymaterials.service.cg.ReportModels;
import nl.jixxed.eliteodysseymaterials.service.event.CommunityGoalEvent;
import nl.jixxed.eliteodysseymaterials.service.event.CommunityGoalReportEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

public class CommunityGoal extends DestroyableVBox implements DestroyableEventTemplate {

    private final Goal goal;
    private ReportModels.ReportResponse report;
    private ProgressChart progressChart;
    private BandChart bandChart;
    private RewardsTable rewardsTable;
    private DestroyableLabel currentTier;
    private DestroyableLabel maxTier;
    private DestroyableLabel activityType;
    private DestroyableLabel expires;
    private DestroyableLabel commodityList;
    private DestroyableLabel station;
    private DestroyableLabel system;
    private DestroyableLabel goalText;
    private DestroyableLabel title;

    public CommunityGoal(Goal goal) {
        this.goal = goal;
        initComponents();
        initEventHandling();
        CommunityGoalsService.getReport(goal.getId()).ifPresent(report -> {
            this.report = report;
            refreshContent();
        });

    }

    @Override
    public void initComponents() {
        getStyleClass().add("community-goal");
        currentTier = LabelBuilder.builder().withStyleClass("tier-value").build();
        maxTier = LabelBuilder.builder().withStyleClass("tier-value").build();
        title = LabelBuilder.builder().withStyleClass("cg-title").build();
        DestroyableHBox header = BoxBuilder.builder()
                .withStyleClass("header")
                .withNodes(
                        title,
                        new GrowingRegion(),
                        BoxBuilder.builder()
                                .withStyleClass("current-tier")
                                .withNodes(
                                        currentTier,
                                        LabelBuilder.builder().withStyleClass("tier-label").withNonLocalizedText("Tier reached").build()
                                )
                                .buildVBox(),
                        BoxBuilder.builder()
                                .withStyleClass("max-tier")
                                .withNodes(
                                        maxTier,
                                        LabelBuilder.builder().withStyleClass("tier-label").withNonLocalizedText("Max tier").build()
                                )
                                .buildVBox()
                )
                .buildHBox();
        activityType = LabelBuilder.builder().withStyleClass("value").build();
        expires = LabelBuilder.builder().withStyleClass("value").build();
        commodityList = LabelBuilder.builder().withStyleClass("value").build();
        station = LabelBuilder.builder().withStyleClass("value").build();
        system = LabelBuilder.builder().withStyleClass("value").build();
        DestroyableVBox cgData = BoxBuilder.builder()
                .withStyleClass("cg-data")
                .withNodes(
                        BoxBuilder.builder().withStyleClass("cg-data-entry").withNodes(
                                LabelBuilder.builder().withStyleClass("name").withNonLocalizedText("System").build(),
                                system
                        ).buildVBox(),
                        BoxBuilder.builder().withStyleClass("cg-data-entry").withNodes(
                                LabelBuilder.builder().withStyleClass("name").withNonLocalizedText("Station").build(),
                                station
                        ).buildVBox(),
                        BoxBuilder.builder().withStyleClass("cg-data-entry").withNodes(
                                LabelBuilder.builder().withStyleClass("name").withNonLocalizedText("Commodity list").build(),
                                commodityList
                        ).buildVBox(),
                        BoxBuilder.builder().withStyleClass("cg-data-entry").withNodes(
                                LabelBuilder.builder().withStyleClass("name").withNonLocalizedText("Expires").build(),
                                expires
                        ).buildVBox(),
                        BoxBuilder.builder().withStyleClass("cg-data-entry").withNodes(
                                LabelBuilder.builder().withStyleClass("name").withNonLocalizedText("Activity type").build(),
                                activityType
                        ).buildVBox()
                )
                .buildVBox();
        goalText = LabelBuilder.builder().withStyleClass("cg-text").build();
//        VBox.setVgrow(goalText, javafx.scene.layout.Priority.ALWAYS);
        DestroyableVBox destroyableVBox = BoxBuilder.builder().withNode(goalText).buildVBox();
//        destroyableVBox.minHeightProperty().bind(goalText.prefHeightProperty());
        goalText.setMinHeight(Region.USE_PREF_SIZE);
        DestroyableHBox info = BoxBuilder.builder()
                .withStyleClass("information")
                .withNodes(cgData, destroyableVBox)
                .buildHBox();
        progressChart = new ProgressChart();
        bandChart = new BandChart();
        rewardsTable = new RewardsTable();
        DestroyableSeparator destroyableSeparator = new DestroyableSeparator(Orientation.HORIZONTAL);
        destroyableSeparator.getStyleClass().add("splitter");
        this.getNodes().addAll(header, destroyableSeparator, info, progressChart, bandChart, rewardsTable);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, CommunityGoalEvent.class, _ -> refreshContent()));
        register(EventService.addListener(true, this, CommunityGoalReportEvent.class, event -> {
            if (event.getReport() != null && event.getReport().goals().stream().anyMatch(g -> goal.getId().equals((int) g.cgid()))) {
                report = event.getReport();
                refreshContent();
            }
        }));

    }

    private void refreshContent() {
        if (report != null) {
            report.goals().stream()
                    .filter(g -> goal.getId().equals((int) g.cgid()))
                    .findFirst()
                    .ifPresent(goalReport -> {
                        progressChart.update(goalReport);
                        bandChart.update(goalReport);
                        rewardsTable.update(goalReport);
                        currentTier.setText(goalReport.currentAchievedTier().toString());
                        maxTier.setText(goalReport.currentTopTier().toString());
                        activityType.setText(goalReport.metadata().get("activityType").toString());
                        expires.setText(goalReport.metadata().get("expiry").toString());
                        commodityList.setText(goalReport.metadata().get("target_commodity_list").toString());
                        system.setText(goalReport.metadata().get("starsystem_name").toString());
                        station.setText(goalReport.metadata().get("market_name").toString());
                        goalText.setText(goalReport.metadata().get("bulletin").toString());
                        title.setText(goalReport.metadata().get("title").toString());
                    });
        }
    }
}
