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
import io.fair_acc.chartfx.renderer.spi.AbstractRendererXY;
import io.fair_acc.chartfx.renderer.spi.BasicDataSetRenderer;
import io.fair_acc.chartfx.ui.HiddenSidesPane;
import io.fair_acc.dataset.DataSet;
import io.fair_acc.dataset.events.ChartBits;
import javafx.util.StringConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.persistence.commander.model.CommunityGoalModel;
import nl.jixxed.eliteodysseymaterials.persistence.commander.model.query.QCommunityGoalModel;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.cg.ReportModels;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableChart;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class BandChart extends DestroyableHBox implements DestroyableTemplate {

    Map<String, Dataset> datasets = new HashMap<>();
    private DestroyableChart chart;
    private TimeNumericAxis xAxis;
    private DefaultNumericAxis yAxis;

    public BandChart() {
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("community-goal-band-chart");
        // prepare axis
        xAxis = new TimeNumericAxis("time", "iso");
        xAxis.setOverlapPolicy(AxisLabelOverlapPolicy.SKIP_ALT);
        xAxis.setAutoRangeRounding(false);
        xAxis.setTimeAxis(true);
        xAxis.getStyleClass().add("time-axis");
        xAxis.nameProperty().bind(LocaleService.getStringBinding("community.goal.band.chart.xaxis"));
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
        yAxis = new DefaultNumericAxis("value", "points");
        yAxis.getStyleClass().add("y-axis");

        chart = new DestroyableChart(xAxis, yAxis);
        // Add renderers to chart

        //prevent empty menu popout
        final HiddenSidesPane hiddenSidePane = chart.getPlotArea();
        hiddenSidePane.setTriggerDistance(0);

        XIndicatorLine e = new XIndicatorLine(chart, xAxis, 0);
        chart.getPlugins().add(e);
        LineTooltip e1 = new LineTooltip(chart);
        chart.getPlugins().add(e1);
        e.chartProperty().set(chart);
        e1.chartProperty().set(chart);

        this.getNodes().add(chart);
        this.setVisible(false);
        this.setManaged(false);
    }

    public void update(ReportModels.CommunityGoalReport report) {
        datasets.values().forEach(Dataset::clearData);


        report.hourlyData().forEach(data -> {
            data.bandMax().forEach(bandMax -> {
                var x = data.hourUtc().toEpochMilli();
                Dataset dataset = datasets.computeIfAbsent(bandMax.band(), (band) -> this.create(band, BasicDataSetRenderer::new));
                dataset.dataSet.add(x, bandMax.max());
            });
        });
        Dataset myProgress = datasets.computeIfAbsent("My progress", (band) -> this.create(band, ProgressDataSetRenderer::new));
        AtomicLong progress = new AtomicLong(-1);
        AtomicLong lastTimestampEntry = new AtomicLong(-1);
        AtomicReference<CommunityGoalModel> lastModel = new AtomicReference<>();

        LocalDateTime expiry = LocalDateTime.parse(report.metadata().get("expiry").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        new QCommunityGoalModel()
                .cgid.eq((int) report.cgid())
                .timestamp.le(expiry)
                .orderBy("timestamp")
                .findStream()
                .forEach(model -> {
                    lastModel.set(model);

                    if (model.getPlayerContribution().longValue() > progress.get()) { // remove duplicates
                        progress.set(model.getPlayerContribution().longValue());
                        long timestampMilli = model.getTimestamp().toInstant(ZoneOffset.UTC).toEpochMilli();
                        lastTimestampEntry.set(timestampMilli);
                        myProgress.dataSet.add(
                                timestampMilli,
                                model.getPlayerContribution().longValue()
                        );
                    }
                });
        CommunityGoalModel last = lastModel.get();
        if (last != null) {
            long lastTimestamp = last.getTimestamp().toInstant(ZoneOffset.UTC).toEpochMilli();
            if (lastTimestampEntry.get() < lastTimestamp) {

                long timestampMilli = Math.min(Instant.now().toEpochMilli(), expiry.toInstant(ZoneOffset.UTC).plus(2, ChronoUnit.HOURS).toEpochMilli());
                long lastContribution = last.getPlayerContribution().longValue();
                myProgress.dataSet.add(timestampMilli, lastContribution);
            }
        }

        chart.getPlugins().removeIf(plugin -> plugin instanceof XValueIndicator);
//        chart.getPlugins().clear();

        for (var unlock : report.tierUnlocks()) {
            TierIndicator tierLine = new TierIndicator(xAxis, unlock.tier(), unlock.earliestOccurrence().toEpochMilli());
            chart.getPlugins().addFirst(tierLine);
        }
//        XIndicatorLine e = new XIndicatorLine(chart, xAxis, 0);
//        chart.getPlugins().add(e);
//        LineTooltip e1 = new LineTooltip(chart);
//        chart.getPlugins().add(e1);
//        e.chartProperty().set(chart);
//        e1.chartProperty().set(chart);
        chart.fireInvalidated(() -> Arrays.stream(ChartBits.values()).mapToInt(ChartBits::getAsInt).reduce(0, (a, b) -> a | b));

        boolean emptyDataset = !report.hourlyData().isEmpty() && report.hourlyData().size() != 1;
        this.setVisible(emptyDataset);
        this.setManaged(emptyDataset);

    }

    private Dataset create(String band, Function<DataSet, AbstractRendererXY<?>> rendererFunction) {
        BandDataSet dataSet = new BandDataSet(band);
        if (band.contains("top")) {
            dataSet.nameProperty().bind(LocaleService.getStringBinding("community.goal.band.chart.top", band.replace("top", "")));
        } else if (band.contains("%")) {
            dataSet.nameProperty().bind(LocaleService.getStringBinding("community.goal.band.chart.percent", band.replace("%", "")));
        } else {
            dataSet.nameProperty().bind(LocaleService.getStringBinding("community.goal.band.chart.myprogress"));
        }
        AbstractRendererXY<?> renderer =rendererFunction.apply(dataSet);// new BasicDataSetRenderer(dataSet);
//        renderer.getDatasetNodes().forEach(dataSetNode -> dataSetNode.getStyleClass().add(band.toLowerCase().replace(" ", "-").replace("%", "percent")));
//        renderer.getStyleClass().add(band.toLowerCase().replace(" ", "-").replace("%", "percent"));
//        dataSet.getStyleClasses().add(band.toLowerCase().replace(" ", "-").replace("%", "percent"));
        if (band.startsWith("top") || band.equals("25%") || band.equals("10%")) {
            renderer.getDatasetNodes().forEach(dataSetNode -> dataSetNode.setVisible(false));
        }
        renderer.getAxes().setAll(xAxis, yAxis);
        chart.getRenderers().add(renderer);
//        chart.getAxes().add(yAxis);
        return new Dataset(dataSet, renderer, yAxis);
    }


    @Getter
    @AllArgsConstructor
    private class Dataset {
        private final BandDataSet dataSet;
        private final AbstractRendererXY<?> renderer;
        private final DefaultNumericAxis yAxis;

        public void clearData() {
            dataSet.clearData();
        }
    }
}
