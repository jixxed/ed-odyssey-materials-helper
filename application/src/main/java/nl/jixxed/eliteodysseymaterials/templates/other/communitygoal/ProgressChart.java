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
import io.fair_acc.chartfx.renderer.spi.BasicDataSetRenderer;
import io.fair_acc.chartfx.ui.HiddenSidesPane;
import io.fair_acc.chartfx.ui.ToolBarFlowPane;
import io.fair_acc.dataset.events.ChartBits;
import io.fair_acc.dataset.spi.DoubleDataSet;
import javafx.scene.paint.Color;
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

//import javafx.scene.chart.CategoryAxis;
//import javafx.scene.chart.NumberAxis;
//import javafx.scene.chart.XYChart;

@Slf4j
public class ProgressChart extends DestroyableHBox implements DestroyableTemplate {

    DoubleDataSet commanders =
            new DoubleDataSet("Commanders");
    DoubleDataSet progress =
            new DoubleDataSet("Total progress");
    DoubleDataSet delta =
            new DoubleDataSet("Hourly contribution");
    private DestroyableChart chart;

    public ProgressChart() {
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("community-goal-progress-chart");
        // prepare axis
        final TimeNumericAxis xAxis1 = new TimeNumericAxis("time", "iso");
        xAxis1.setOverlapPolicy(AxisLabelOverlapPolicy.SKIP_ALT);
        xAxis1.setAutoRangeRounding(false);
        xAxis1.setTimeAxis(true);
        xAxis1.getStyleClass().add("time-axis");
        xAxis1.setTickLabelFormatter(new StringConverter<>() {
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
        xAxis1.nameProperty().bind(LocaleService.getStringBinding("community.goal.progress.chart.xaxis"));
        final DefaultNumericAxis yAxis1 = new DefaultNumericAxis("commanders", "points");
        final DefaultNumericAxis yAxis2 = new DefaultNumericAxis("progress", "points");
        final DefaultNumericAxis yAxis3 = new DefaultNumericAxis("delta", "points");
        yAxis1.getStyleClass().add("commanders-axis");
        yAxis2.getStyleClass().add("progress-axis");
        yAxis3.getStyleClass().add("delta-axis");
        yAxis1.nameProperty().bind(LocaleService.getStringBinding("community.goal.progress.chart.commanders"));
        yAxis2.nameProperty().bind(LocaleService.getStringBinding("community.goal.progress.chart.progress"));
        yAxis3.nameProperty().bind(LocaleService.getStringBinding("community.goal.progress.chart.delta"));
        chart = new DestroyableChart(xAxis1, yAxis1, yAxis2, yAxis3);
        // Add renderers to chart

        // -------------------------
        // Renderer for LEFT axis
        // -------------------------
        BasicDataSetRenderer leftRenderer = new BasicDataSetRenderer();
        leftRenderer.getAxes().setAll(xAxis1, yAxis1);

        // -------------------------
        // Renderer for RIGHT axis
        // -------------------------
        BasicDataSetRenderer rightRenderer = new BasicDataSetRenderer();
        rightRenderer.getAxes().setAll(xAxis1, yAxis2);
        // -------------------------
        // Renderer for RIGHT axis
        // -------------------------
        BasicDataSetRenderer rightRenderer2 = new BasicDataSetRenderer();
        rightRenderer2.getAxes().setAll(xAxis1, yAxis3);
        chart.getRenderers().add(leftRenderer);
        chart.getRenderers().add(rightRenderer);
        chart.getRenderers().add(rightRenderer2);
        ((ToolBarFlowPane) chart.getToolBar()).setToolBarDefaultColor(Color.color(0, 0, 0, 0));
        ((ToolBarFlowPane) chart.getToolBar()).setToolBarSelectedColor(Color.color(0, 0, 0, 0));
        leftRenderer.getDatasets().add(commanders);
        rightRenderer.getDatasets().add(progress);
        rightRenderer2.getDatasets().add(delta);
        commanders.getStyleClasses().add("ddataset");
        progress.getStyleClasses().add("ddataset");
        delta.getStyleClasses().add("ddataset");
        final HiddenSidesPane hiddenSidePane = chart.getPlotArea();
        hiddenSidePane.setTriggerDistance(0);
        XValueLine indicator = new XValueLine(chart, xAxis1, 0);
        chart.getPlugins().add(indicator);
        chart.getPlugins().add(new LineTooltip(chart));
        this.getNodes().add(chart);
        chart.setVisible(false);
        chart.setManaged(false);
    }

    public void update(ReportModels.CommunityGoalReport report) {
//        xAxis.setLowerBound(report.hourlyData().stream().mapToDouble(data -> data.hourUtc().toEpochMilli()).min().orElse(0L));
//        xAxis.setUpperBound(report.hourlyData().stream().mapToDouble(data -> data.hourUtc().toEpochMilli()).max().orElse(0L));
//        yAxis.setLowerBound(0);

        commanders.clearData();
        progress.clearData();
        delta.clearData();
        report.hourlyData().forEach(data -> {
//            LocalDateTime hourUtc =LocalDateTime.ofInstant( data.hourUtc(), ZoneOffset.UTC);
//            String x = "";
//            if (hourUtc.getHour() == 0) {
//                DateTimeFormatter format = DateTimeFormatter.ofPattern("LLL d");
//                x = format.format(hourUtc);
//            }
            var x = data.hourUtc().toEpochMilli();
            commanders.add(x, data.numContributors());
            progress.add(x, data.currentTotal());
            delta.add(x, data.delta());
        });
        chart.fireInvalidated(() -> Arrays.stream(ChartBits.values()).mapToInt(ChartBits::getAsInt).reduce(0, (a, b) -> a | b));
        chart.setVisible(true);
        chart.setManaged(true);
//        chart.getGridRenderer().setDrawOnTop(true);
//        chart.getGridRenderer().getHorizontalMajorGrid().setVisible(false);
//        Platform.runLater(() -> {
//            chart.getGridRenderer().getHorizontalMajorGrid().setVisible(true);
//        });
//
    }
}
