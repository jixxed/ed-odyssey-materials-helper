/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.beans.binding.StringBinding;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.NumberAxisBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.HoverableLineChart;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTooltip;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static nl.jixxed.eliteodysseymaterials.helper.ShipMathHelper.*;

@Slf4j
public class JumpStats extends Stats implements DestroyableTemplate {
    private RangeIndicator jumpRangeIndicator;
    private RangeIndicator totalJumpRangeIndicator;
    private RangeIndicator totalJumpRangeIndicator2;
    private Map<Integer, XYChart.Data<Number, Number>> superCruisePitchMap;
    private Map<Integer, XYChart.Data<Number, Number>> superCruiseRollMap;
    private Map<Integer, XYChart.Data<Number, Number>> superCruiseYawMap;
    private HoverableLineChart chart;
    private DestroyableLabel supercruiseLabel;

    public JumpStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("jump-stats");
        addTitle("ship.stats.jump");
        jumpRangeIndicator = new RangeIndicator(
                calculateJumpRangeMinMaintenance(getShip().orElse(null)),
                calculateJumpRangeCurrentMaintenance(getShip().orElse(null)),
                calculateJumpRangeMaxMaintenance(getShip().orElse(null)),
                "ship.stats.jumprange", "ship.stats.jumprange.value");
        DestroyableTooltip tooltip = TooltipBuilder.builder()
                .withText("ship.stats.jumprange.explain")
                .withShowDelay(Duration.ZERO)
                .build();
        tooltip.install(jumpRangeIndicator);
        totalJumpRangeIndicator = new RangeIndicator(
                calculateTotalJumpRangeMin(),
                calculateTotalJumpRangeCurrent(getShip().orElse(null)),
                calculateTotalJumpRangeMax(getShip().orElse(null)),
                "ship.stats.totaljumprange", "ship.stats.totaljumprange.value");
        totalJumpRangeIndicator2 = new RangeIndicator(
                calculateTotalJumpRangeMin(),
                calculateTotalJumpRangeCurrent(getShip().orElse(null)),
                calculateTotalJumpRangeMax(getShip().orElse(null)),
                "ship.stats.totaljumprange", "ship.stats.totaljumprange.value");
        DestroyableHBox box = BoxBuilder.builder()
                .withStyleClass("ranges")
                .withNodes(jumpRangeIndicator, totalJumpRangeIndicator)
                .buildHBox();
        this.getNodes().add(box);
        this.getNodes().add(totalJumpRangeIndicator2);
        NumberAxis xAxis = NumberAxisBuilder.builder()
                .withLabel("ship.stats.jump.chart.xaxis")
                .withLowerBound(0D)
                .withUpperBound(100D)
                .withTickUnit(25D)
                .withAutoRanging(false)
                .build();
        NumberAxis yAxis = NumberAxisBuilder.builder()
                .withLabel("ship.stats.jump.chart.yaxis")
                .build();

        XYChart.Series<Number, Number> superCruisePitchSeries = new XYChart.Series<>();
        superCruisePitchSeries.nameProperty().bind(LocaleService.getStringBinding("ship.stats.jump.chart.pitch"));
        XYChart.Series<Number, Number> superCruiseRollSeries = new XYChart.Series<>();
        superCruiseRollSeries.nameProperty().bind(LocaleService.getStringBinding("ship.stats.jump.chart.roll"));
        XYChart.Series<Number, Number> superCruiseYawSeries = new XYChart.Series<>();
        superCruiseYawSeries.nameProperty().bind(LocaleService.getStringBinding("ship.stats.jump.chart.yaw"));

        Function<Map<Object, Number>, StringBinding> tooltipFunction = data -> LocaleService.getStringBinding("ship.stats.jump.chart.tooltip",
                LocaleService.LocalizationKey.of("ship.stats.jump.chart.throttle"), Formatters.NUMBER_FORMAT_0.format(data.get("x")),
                LocaleService.LocalizationKey.of("ship.stats.jump.chart.roll"), Formatters.NUMBER_FORMAT_1.format(data.get(superCruiseRollSeries)),
                LocaleService.LocalizationKey.of("ship.stats.jump.chart.pitch"), Formatters.NUMBER_FORMAT_1.format(data.get(superCruisePitchSeries)),
                LocaleService.LocalizationKey.of("ship.stats.jump.chart.yaw"), Formatters.NUMBER_FORMAT_1.format(data.get(superCruiseYawSeries))
        );

        chart = new HoverableLineChart(xAxis, yAxis, tooltipFunction);
        chart.setLegendVisible(false);
        chart.setCreateSymbols(false);
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
        superCruisePitchMap = new HashMap<>();
        superCruiseRollMap = new HashMap<>();
        superCruiseYawMap = new HashMap<>();
        for (int index = 0; index <= 100; index++) {
            superCruisePitchMap.put(index, new XYChart.Data<>(index, 0));
            superCruiseRollMap.put(index, new XYChart.Data<>(index, 0));
            superCruiseYawMap.put(index, new XYChart.Data<>(index, 0));
        }
        superCruisePitchSeries.getData().addAll(superCruisePitchMap.values());
        superCruiseRollSeries.getData().addAll(superCruiseRollMap.values());
        superCruiseYawSeries.getData().addAll(superCruiseYawMap.values());
        chart.getData().add(superCruisePitchSeries);
        chart.getData().add(superCruiseRollSeries);
        chart.getData().add(superCruiseYawSeries);

        this.getNodes().add(new GrowingRegion());
        supercruiseLabel = LabelBuilder.builder().withText("ship.stats.jumprange.supercruise.profile").build();
        this.getNodes().add(supercruiseLabel);
        this.getNodes().add(chart);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, ShipConfigEvent.class, event -> update()));
    }

  @Override
    protected void update() {
        getShip().ifPresent(ship -> {
            this.jumpRangeIndicator.updateValues(
                    calculateJumpRangeMinMaintenance(ship),
                    calculateJumpRangeCurrentMaintenance(ship),
                    calculateJumpRangeMaxMaintenance(ship));
            this.totalJumpRangeIndicator.updateValues(
                    calculateTotalJumpRangeMin(),
                    calculateTotalJumpRangeCurrent(ship),
                    calculateTotalJumpRangeMax(ship));
            this.totalJumpRangeIndicator2.updateValues(
                    calculateTotalJumpRangeMin(),
                    calculateTotalJumpRangeCurrent(ship),
                    calculateTotalJumpRangeMax(ship));

            if (ship.supportsGraphs()) {
                chart.setVisible(true);
                chart.setManaged(true);
                supercruiseLabel.setVisible(true);
                supercruiseLabel.setManaged(true);
                totalJumpRangeIndicator.setVisible(true);
                totalJumpRangeIndicator.setManaged(true);
                totalJumpRangeIndicator2.setVisible(false);
                totalJumpRangeIndicator2.setManaged(false);
                superCruisePitchMap.forEach((key, value) -> {
                    Double[][] profile = ship.getShipSpecs().getSupercruiseProfile();
                    Double pivot = profile[0][1];
                    Double min = profile[0][0];
                    Double max = profile[0][2];
                    mapProfile(key, value, pivot, min, max);
                });
                superCruiseRollMap.forEach((key, value) -> {
                    Double[][] profile = ship.getShipSpecs().getSupercruiseProfile();
                    Double pivot = profile[1][1];
                    Double min = profile[1][0];
                    Double max = profile[1][2];
                    mapProfile(key, value, pivot, min, max);
                });
                superCruiseYawMap.forEach((key, value) -> {
                    Double[][] profile = ship.getShipSpecs().getSupercruiseProfile();
                    Double pivot = profile[2][1];
                    Double min = profile[2][0];
                    Double max = profile[2][2];
                    mapProfile(key, value, pivot, min, max);
                });
            } else {
                chart.setVisible(false);
                chart.setManaged(false);
                supercruiseLabel.setVisible(false);
                supercruiseLabel.setManaged(false);
                totalJumpRangeIndicator.setVisible(false);
                totalJumpRangeIndicator.setManaged(false);
                totalJumpRangeIndicator2.setVisible(true);
                totalJumpRangeIndicator2.setManaged(true);
            }
        });
    }

    private static void mapProfile(Integer key, XYChart.Data<Number, Number> value, Double pivot, Double min, Double max) {
        int distance = Math.abs(key - 50) * 2;
        if (key <= 50) {
            double pivotMax = pivot;
            double pivotMin = pivotMax * (min / 100);
            double y = pivotMax - (pivotMax - pivotMin) * (distance / 100.0);
            value.setYValue(y);
        } else {
            double pivotMin = pivot;
            double pivotMax = pivotMin * (max / 100);
            double y = pivotMin + (pivotMax - pivotMin) * (distance / 100.0);
            value.setYValue(y);
        }
    }
}
