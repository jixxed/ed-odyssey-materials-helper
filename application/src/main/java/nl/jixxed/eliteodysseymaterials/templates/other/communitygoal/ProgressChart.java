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

import io.fair_acc.chartfx.axes.AxisLabelOverlapPolicy;
import io.fair_acc.chartfx.axes.spi.DefaultNumericAxis;
import io.fair_acc.chartfx.plugins.XValueIndicator;
import io.fair_acc.chartfx.renderer.spi.BasicDataSetRenderer;
import io.fair_acc.chartfx.ui.HiddenSidesPane;
import io.fair_acc.dataset.events.ChartBits;
import io.fair_acc.dataset.spi.DoubleDataSet;
import javafx.util.StringConverter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.cg.ReportModels;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableChart;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Slf4j
public class ProgressChart extends DestroyableHBox implements DestroyableTemplate {

    DoubleDataSet totalProgress = new DoubleDataSet("Total progress");
    DoubleDataSet commanders = new DoubleDataSet("Commanders");
    DoubleDataSet hourlyContribution = new DoubleDataSet("Hourly contribution");
    private DestroyableChart chart;
    private TimeNumericAxis xAxis;

    public ProgressChart() {
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("community-goal-progress-chart");
        // prepare axis
        xAxis = new TimeNumericAxis("time", "iso");
        xAxis.setOverlapPolicy(AxisLabelOverlapPolicy.SKIP_ALT);
        xAxis.setAutoRangeRounding(false);
        xAxis.setTimeAxis(true);
        xAxis.getStyleClass().add("time-axis");
        xAxis.nameProperty().bind(LocaleService.getStringBinding("community.goal.progress.chart.xaxis"));
        xAxis.setTickLabelFormatter(new StringConverter<>() {
            @Override
            public String toString(Number object) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLL d");
                LocalDateTime instant = LocalDateTime.ofInstant(Instant.ofEpochMilli(object.longValue()), ZoneId.systemDefault());
                return formatter.format(instant);
            }

            @Override
            public Number fromString(String string) {
                return Instant.parse(string).toEpochMilli();
            }
        });
        final DefaultNumericAxis yAxisTotalProgress = new DefaultNumericAxis("progress", "points");
        final DefaultNumericAxis yAxisCommanders = new DefaultNumericAxis("commanders", "points");
        final DefaultNumericAxis yAxisHourlyContribution = new DefaultNumericAxis("delta", "points");
        yAxisTotalProgress.getStyleClass().add("progress-axis");
        yAxisCommanders.getStyleClass().add("commanders-axis");
        yAxisHourlyContribution.getStyleClass().add("delta-axis");
        yAxisTotalProgress.nameProperty().bind(LocaleService.getStringBinding("community.goal.progress.chart.progress"));
        yAxisCommanders.nameProperty().bind(LocaleService.getStringBinding("community.goal.progress.chart.commanders"));
        yAxisHourlyContribution.nameProperty().bind(LocaleService.getStringBinding("community.goal.progress.chart.delta"));
        totalProgress.setName(LocaleService.getLocalizedStringForCurrentLocale("community.goal.progress.chart.progress"));
        commanders.setName(LocaleService.getLocalizedStringForCurrentLocale("community.goal.progress.chart.commanders"));
        hourlyContribution.setName(LocaleService.getLocalizedStringForCurrentLocale("community.goal.progress.chart.delta"));
        chart = new DestroyableChart(xAxis, yAxisTotalProgress, yAxisCommanders, yAxisHourlyContribution);
        // Add renderers to chart

        BasicDataSetRenderer totalProgressRenderer = new BasicDataSetRenderer(totalProgress);
        totalProgressRenderer.getAxes().setAll(xAxis, yAxisTotalProgress);

        BasicDataSetRenderer commandersRenderer = new BasicDataSetRenderer(commanders);
        commandersRenderer.getAxes().setAll(xAxis, yAxisCommanders);

        BasicDataSetRenderer hourlyContributionRenderer = new BasicDataSetRenderer(hourlyContribution);
        hourlyContributionRenderer.getAxes().setAll(xAxis, yAxisHourlyContribution);

        chart.getRenderers().addAll(totalProgressRenderer, commandersRenderer, hourlyContributionRenderer);

        //prevent empty menu popout
        final HiddenSidesPane hiddenSidePane = chart.getPlotArea();
        hiddenSidePane.setTriggerDistance(0);

        chart.getPlugins().add(new XIndicatorLine(chart, xAxis, 0));
        chart.getPlugins().add(new LineTooltip(chart));

        this.getNodes().add(chart);
        chart.setVisible(false);
        chart.setManaged(false);
    }

    public void update(ReportModels.CommunityGoalReport report) {

        chart.getPlugins().removeIf(plugin -> plugin instanceof XValueIndicator);
        for (var unlock : report.tierUnlocks()) {
            TierIndicator tierLine = new TierIndicator(xAxis, unlock.tier(), unlock.earliestOccurrence().toEpochMilli());
            chart.getPlugins().addFirst(tierLine);
        }
        totalProgress.clearData();
        commanders.clearData();
        hourlyContribution.clearData();
        report.hourlyData().forEach(data -> {

            var x = data.hourUtc().toEpochMilli();
            totalProgress.add(x, data.currentTotal());
            commanders.add(x, data.numContributors());
            hourlyContribution.add(x, data.delta());
        });
        chart.fireInvalidated(() -> Arrays.stream(ChartBits.values()).mapToInt(ChartBits::getAsInt).reduce(0, (a, b) -> a | b));

        boolean emptyDataset = !report.hourlyData().isEmpty() && report.hourlyData().size() != 1;
        chart.setVisible(emptyDataset);
        chart.setManaged(emptyDataset);

    }
}
