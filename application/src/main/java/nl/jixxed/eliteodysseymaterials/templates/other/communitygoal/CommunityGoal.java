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

import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Orientation;
import javafx.scene.layout.Region;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Goal;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.persistence.commander.model.CommunityGoalModel;
import nl.jixxed.eliteodysseymaterials.persistence.commander.model.query.QCommunityGoalModel;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.cg.CommunityGoalsService;
import nl.jixxed.eliteodysseymaterials.service.cg.ReportModels;
import nl.jixxed.eliteodysseymaterials.service.event.CommunityGoalEvent;
import nl.jixxed.eliteodysseymaterials.service.event.CommunityGoalReportEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.CopyableLocation;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CommunityGoal extends DestroyableVBox implements DestroyableEventTemplate {

    private final Goal goal;
    private final int index;
    private ProgressChart progressChart;
    private BandChart bandChart;
    private RewardsTable rewardsTable;
    private DestroyableLabel currentTier;
    private DestroyableLabel maxTier;
    private DestroyableLabel activityType;
    private DestroyableLabel expires;
    private DestroyableLabel expiresTimer;
    private DestroyableHBox location;
    private DestroyableLabel goalText;
    private DestroyableLabel title;
    private ProgressStats progressStats;
    private CommodityList commodityList;
    private DestroyableLabel expiresTitle;
    private DestroyableLabel currentBand;
    private DestroyableLabel contribution;
    private BandPredictionTable bandPredictionTable;

    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    private static String lookupLocalized(Map<String, Object> localized, Locale locale, String key, String fallback) {
        if (localized == null) return fallback;
        Object localeMap = localized.get(locale.toLanguageTag());
        if (localeMap instanceof Map<?, ?> map) {
            Object value = map.get(key);
            return value != null ? value.toString() : fallback;
        }
        return fallback;
    }

    public CommunityGoal(Goal goal, int index) {
        this.goal = goal;
        this.index = index;
        initComponents();
        initEventHandling();
        refreshContent();

        scheduledExecutor.scheduleWithFixedDelay(this::smallUpdate, 1, 1, TimeUnit.MINUTES);
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
                                        LabelBuilder.builder().withStyleClass("tier-label").withText("community.goal.information.tierreached").build()
                                )
                                .buildVBox(),
                        BoxBuilder.builder()
                                .withStyleClass("max-tier")
                                .withNodes(
                                        maxTier,
                                        LabelBuilder.builder().withStyleClass("tier-label").withText("community.goal.information.maxtier").build()
                                )
                                .buildVBox()
                )
                .buildHBox();
        activityType = LabelBuilder.builder().withStyleClass("value").build();
        contribution = LabelBuilder.builder().withStyleClass("value").build();
        currentBand = LabelBuilder.builder().withStyleClass("value").build();
        expires = LabelBuilder.builder().withStyleClass("value").build();
        expiresTimer = LabelBuilder.builder().withStyleClass("value").build();
        location = BoxBuilder.builder().withStyleClass("system").buildHBox();
        expiresTitle = LabelBuilder.builder().withStyleClass("name").withText("community.goal.information.expires").build();

        DestroyableVBox cgData = BoxBuilder.builder()
                .withStyleClass("cg-data")
                .withNodes(
                        BoxBuilder.builder().withStyleClass("cg-data-entry").withNodes(
                                LabelBuilder.builder().withStyleClass("name").withText("community.goal.information.location").build(),
                                location
                        ).buildVBox(),
                        BoxBuilder.builder().withStyleClass("cg-data-entry").withNodes(
                                expiresTitle,
                                expires,
                                expiresTimer
                        ).buildVBox(),
                        BoxBuilder.builder().withStyleClass("cg-data-entry").withNodes(
                                LabelBuilder.builder().withStyleClass("name").withText("community.goal.information.activitytype").build(),
                                activityType
                        ).buildVBox(),
                        BoxBuilder.builder().withStyleClass("cg-data-entry").withNodes(
                                LabelBuilder.builder().withStyleClass("name").withText("community.goal.information.contribution").build(),
                                contribution
                        ).buildVBox(),
                        BoxBuilder.builder().withStyleClass("cg-data-entry").withNodes(
                                LabelBuilder.builder().withStyleClass("name").withText("community.goal.information.currentband").build(),
                                currentBand
                        ).buildVBox()
                )
                .buildVBox();
        goalText = LabelBuilder.builder().withStyleClass("cg-text").build();
        DestroyableVBox destroyableVBox = BoxBuilder.builder().withNode(goalText).buildVBox();
        goalText.setMinHeight(Region.USE_PREF_SIZE);
        DestroyableHBox info = BoxBuilder.builder()
                .withStyleClass("information")
                .withNodes(cgData, destroyableVBox)
                .buildHBox();
        commodityList = new CommodityList();
        progressChart = new ProgressChart();
        bandChart = new BandChart();
        bandPredictionTable = new BandPredictionTable();
        rewardsTable = new RewardsTable();
        progressStats = new ProgressStats();
        DestroyableSeparator destroyableSeparator = new DestroyableSeparator(Orientation.HORIZONTAL);
        destroyableSeparator.getStyleClass().add("splitter");
        this.getNodes().addAll(header, destroyableSeparator, info, commodityList, progressStats, progressChart, bandChart, bandPredictionTable, rewardsTable);
        this.setManaged(false);
        this.setVisible(false);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, CommunityGoalEvent.class, _ -> refreshContent()));
        register(EventService.addListener(true, this, CommunityGoalReportEvent.class, event -> {
            if (event.getReport() != null && event.getReport().goals().stream().anyMatch(g -> goal.getId().equals((int) g.cgid()))) {
                refreshContent();
            }
        }));

    }

    private void refreshContent() {
        CommunityGoalsService.getReport(goal.getId())
                .flatMap(report -> report.goals().stream()
                        .filter(g -> goal.getId().equals((int) g.cgid()))
                        .findFirst())
                .ifPresent(goalReport -> {
                    commodityList.update(goalReport);
                    progressChart.update(goalReport);
                    bandChart.update(goalReport);
                    rewardsTable.update(goalReport);
                    progressStats.update(goalReport);
                    currentTier.setText(Optional.ofNullable(goalReport.currentAchievedTier())
                            .map(Object::toString).orElse(""));
                    maxTier.setText(Optional.ofNullable(goalReport.currentTopTier())
                            .map(Object::toString).orElse(""));
                    Map<String, Object> localized = (Map<String, Object>) goalReport.metadata().get("localized");
                    String fallbackActivityType = Optional.ofNullable(goalReport.metadata().get("activityType"))
                            .map(Object::toString).orElse("");
                    activityType.addBinding(activityType.textProperty(),
                        LocaleService.getStringBinding(locale -> lookupLocalized(localized, locale, "activityType", fallbackActivityType)));

                    ZonedDateTime localExpiry = toLocalExpiry(
                            Optional.ofNullable(goalReport.metadata().get("expiry"))
                                    .map(Object::toString).orElse(""));
                    String formatKey = localExpiry.getYear() == ZonedDateTime.now().getYear() ? "community.goal.date.format.currentyear" : "community.goal.date.format.previousyear";
                    StringBinding formattedDate = LocaleService.getStringBinding(locale -> {
                        String pattern = LocaleService.getLocalizedStringForLocale(locale, formatKey);
                        return DateTimeFormatter.ofPattern(pattern).withLocale(locale).format(localExpiry);
                    });
                    expires.addBinding(expires.textProperty(), formattedDate);
                    if (localExpiry.isAfter(ZonedDateTime.now())) {
                        expiresTimer.addBinding(expiresTimer.textProperty(), LocaleService.getStringBinding(() -> Formatters.timeUntil(localExpiry)));
                        expiresTitle.addBinding(expiresTitle.textProperty(), LocaleService.getStringBinding("community.goal.information.expires"));
                    } else {
                        expiresTitle.addBinding(expiresTitle.textProperty(), LocaleService.getStringBinding("community.goal.information.expired"));
                        expiresTimer.setVisible(false);
                        expiresTimer.setManaged(false);
                    }

                    StarSystem starSystem = LocationService.getStarSystem(
                            Optional.ofNullable(goalReport.metadata().get("starsystem_name"))
                                    .map(Object::toString).orElse(""));
                    location.getNodes().clear();
                    location.setVisible(false);
                    location.setManaged(false);
                    if (starSystem != null) {
                        location.getNodes().add(new CopyableLocation(starSystem,
                                    Optional.ofNullable(goalReport.metadata().get("market_name"))
                                            .map(Object::toString).orElse("")));
                        location.setVisible(true);
                        location.setManaged(true);
                    }
                    String fallbackBulletin = Optional.ofNullable(goalReport.metadata().get("bulletin"))
                            .map(Object::toString).orElse("");
                    goalText.addBinding(goalText.textProperty(),
                        LocaleService.getStringBinding(locale -> lookupLocalized(localized, locale, "bulletin", fallbackBulletin)));
                    String fallbackTitle = Optional.ofNullable(goalReport.metadata().get("title"))
                            .map(Object::toString).orElse("");
                    title.addBinding(title.textProperty(),
                        LocaleService.getStringBinding(locale -> lookupLocalized(localized, locale, "title", fallbackTitle)));
                    ApplicationState.getInstance().getPreferredCommander().ifPresent(_ -> {//database must be initialized with a commander
                        Optional<CommunityGoalModel> communityGoalModel = new QCommunityGoalModel()
                                .cgid.eq((int) goalReport.cgid())
                                .orderBy()
                                .timestamp.desc()
                                .setMaxRows(1)
                                .findOneOrEmpty();
                        Long contributionValue = communityGoalModel
                                .map(CommunityGoalModel::getPlayerContribution)
                                .map(BigInteger::longValue)
                                .orElse(-1L);
                        Long currentBandValue = communityGoalModel
                                .map(CommunityGoalModel::getPlayerPercentileBand)
                                .map(BigInteger::longValue)
                                .orElse(-1L);
                        if (currentBandValue < 0L) {//no journal events, not signed up
                            currentBand.addBinding(currentBand.textProperty(), LocaleService.getStringBinding("community.goal.information.currentband.none"));
                        } else if (goalReport.hourlyData().isEmpty()) {//no data, set to highest from journal
                            String band = currentBandValue.toString();
                            String label = (band.contains("top")) ? "community.goal.reward.table.top" : "community.goal.reward.table.percent";
                            StringBinding title = LocaleService.getStringBinding(label, band.replace("top", ""));
                            currentBand.addBinding(currentBand.textProperty(), title);
                        } else {
                            Optional<ReportModels.BandMax> lowestMax = goalReport.hourlyData().getLast().bandMax().stream()
                                    .filter(bandMax -> bandMax.max() >= contributionValue)
                                    .max(new BandComparator());
                            String band = lowestMax.map(ReportModels.BandMax::band).orElse("top10");
                            String label = (band.contains("top")) ? "community.goal.reward.table.top" : "community.goal.reward.table.percent";
                            StringBinding title = LocaleService.getStringBinding(label, band.replace("top", "").replace("%", ""));
                            currentBand.addBinding(currentBand.textProperty(), title);
                        }

 if (contributionValue < 0L) {//no journal events, not signed up
                            contribution.setText("-");
                        } else {//no data, set to highest from journal
                            contribution.setText(Formatters.NUMBER_FORMAT_0.format(contributionValue));
                        }
                        bandPredictionTable.update(goalReport, contributionValue);
                    });
                    this.setManaged(true);
                    this.setVisible(true);
                });
    }

    private ZonedDateTime toLocalExpiry(String expiryUtc) {
        return LocalDateTime.parse(expiryUtc, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .atZone(ZoneOffset.UTC)
                .withZoneSameInstant(ZoneId.systemDefault());
    }

    private void smallUpdate() {
        Platform.runLater(() -> {
            CommunityGoalsService.getReport(goal.getId())
                    .flatMap(report -> report.goals().stream()
                            .filter(g -> goal.getId().equals((int) g.cgid()))
                            .findFirst())
                    .ifPresent(goalReport -> {
                        String expiryStr = Optional.ofNullable(goalReport.metadata().get("expiry"))
                                .map(Object::toString).orElse(null);
                        if (expiryStr != null) {
                            ZonedDateTime localExpiry = toLocalExpiry(expiryStr);
                            if (localExpiry.isAfter(ZonedDateTime.now())) {
                            expiresTimer.addBinding(expiresTimer.textProperty(), LocaleService.getStringBinding(() -> Formatters.timeUntil(localExpiry)));
                            expiresTitle.addBinding(expiresTitle.textProperty(), LocaleService.getStringBinding("community.goal.information.expires"));
                        } else {
                            expiresTitle.addBinding(expiresTitle.textProperty(), LocaleService.getStringBinding("community.goal.information.expired"));
                            expiresTimer.setVisible(false);
                            expiresTimer.setManaged(false);
                        }
                        }
                        bandChart.update(goalReport);
                    });
        });
    }

    @Override
    public void destroyTemplate() {
        scheduledExecutor.shutdownNow();
        super.destroy();
    }
}
