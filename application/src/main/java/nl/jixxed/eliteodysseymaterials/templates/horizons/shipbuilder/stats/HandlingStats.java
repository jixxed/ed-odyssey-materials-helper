package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import eu.hansolo.fx.charts.*;
import eu.hansolo.fx.charts.data.XYChartItem;
import eu.hansolo.fx.charts.series.XYSeriesBuilder;
import javafx.geometry.Orientation;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLineChart;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableSeparator;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

import java.util.Optional;

@Slf4j
public class HandlingStats extends Stats implements DestroyableTemplate {
    private RangeIndicator pitchIndicator;
    private RangeIndicator rollIndicator;
    private RangeIndicator yawIndicator;

    public HandlingStats() {
        super();
        initComponents();
        initEventHandling();

        pitchIndicator = new RangeIndicator(0D, 0D, 0D, "ship.stats.handling.pitch", "ship.stats.handling.pitch.value");
//        this.getNodes().add(pitchIndicator);
        rollIndicator = new RangeIndicator(0D, 0D, 0D, "ship.stats.handling.roll", "ship.stats.handling.roll.value");
//        this.getNodes().add(rollIndicator);
        yawIndicator = new RangeIndicator(0D, 0D, 0D, "ship.stats.handling.yaw", "ship.stats.handling.yaw.value");
//        this.getNodes().add(yawIndicator);
//        final NumberAxis xAxis = new NumberAxis();
//        final NumberAxis yAxis = new NumberAxis();
//        xAxis.setLabel("Supercruise speed");
//        DestroyableLineChart<XYChartItem> pitchChart = new DestroyableLineChart<>();
        XYChartItem p0 = new XYChartItem(0, 45, "0%", "0% Speed");
        XYChartItem p1 = new XYChartItem(10, 50, "10%", "10% Speed");
        XYChartItem p2 = new XYChartItem(20, 80, "20%", "20% Speed");
        XYChartItem p3 = new XYChartItem(30, 90, "30%", "30% Speed");
        XYChartItem p4 = new XYChartItem(40, 100, "40%", "40% Speed");
        XYChartItem p5 = new XYChartItem(50, 90, "50%", "50% Speed");
        XYChartItem p6 = new XYChartItem(60, 80, "60%", "60% Speed");
        XYChartItem p7 = new XYChartItem(70, 70, "70%", "70% Speed");
        XYChartItem p8 = new XYChartItem(80, 60, "80%", "80% Speed");
        XYChartItem p9 = new XYChartItem(90, 50, "90%", "90% Speed");
        XYChartItem p10 = new XYChartItem(100, 40, "100%", "100% Speed");
        var series = XYSeriesBuilder.create()
                .items(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10)
                .chartType(ChartType.SMOOTH_LINE)
                .fill(Color.web("#00AEF520"))
                .stroke(Color.web("#00AEF5"))
                .symbolFill(Color.web("#00AEF5"))
                .symbolStroke(Color.web("#293C47"))
                .symbolSize(10)
                .strokeWidth(3)
                .symbolsVisible(true)
                .build();
        var xAxisBottom = AxisBuilder.create(Orientation.HORIZONTAL, Position.BOTTOM)
                .type(AxisType.TEXT)
                .prefHeight(40d)
                .categories("0%",
                        "10%",
                        "20%",
                        "30%",
                        "40%",
                        "50%",
                        "60%",
                        "70%",
                        "80%",
                        "90%",
                        "100%")
                .minValue(0)
                .maxValue(100)
                .autoScale(true)
                .axisColor(Color.web("#85949B"))
                .tickLabelColor(Color.web("#85949B"))
                .tickMarkColor(Color.web("#85949B"))
                //.tickMarksVisible(false)
                .build();
        AnchorPane.setBottomAnchor(xAxisBottom, 0d);
        AnchorPane.setLeftAnchor(xAxisBottom, 40d);
        AnchorPane.setRightAnchor(xAxisBottom, 40d);

        var yAxisLeft = AxisBuilder.create(Orientation.VERTICAL, Position.LEFT)
                .type(AxisType.LINEAR)
                .prefWidth(40d)
                .minValue(0)
                .maxValue(110)
                .autoScale(true)
                .axisColor(Color.web("#85949B"))
                .tickLabelColor(Color.web("#85949B"))
                .tickMarkColor(Color.web("#85949B"))
//                //.tickMarksVisible(false)
//                // test the new numberFormatter as well
//                .numberFormatter(new StringConverter<Number>() {
//                    private final DecimalFormat df = new DecimalFormat("0 deg/s");
//
//                    @Override
//                    public String toString(Number object) {
//                        if (object == null) {
//                            return "";
//                        }
//                        return df.format(object);
//                    }
//
//                    @Override
//                    public Number fromString(String string) {
//                        try {
//                            if (string == null) {
//                                return null;
//                            }
//                            string = string.trim();
//                            if (string.length() < 1) {
//                                return null;
//                            }
//                            return df.parse(string).doubleValue();
//                        } catch (ParseException ex) {
//                            throw new RuntimeException(ex);
//                        }
//                    }
//                })
                .build();
        AnchorPane.setTopAnchor(yAxisLeft, 0d);
        AnchorPane.setBottomAnchor(yAxisLeft, 40d);
        AnchorPane.setLeftAnchor(yAxisLeft, 0d);

        var grid = GridBuilder.create(xAxisBottom, yAxisLeft)
                .gridLinePaint(Color.web("#384C57"))
                .minorHGridLinesVisible(false)
                .mediumHGridLinesVisible(false)
                .minorVGridLinesVisible(false)
                .mediumVGridLinesVisible(false)
                .gridLineDashes(4, 4)
                .build();

        XYPane lineChartPane = new XYPane(series);

        DestroyableLineChart<XYChartItem> lineChart = new DestroyableLineChart<>(lineChartPane, grid, yAxisLeft, xAxisBottom);

//        pitchChart.getData().add(series);
        this.getNodes().add(BoxBuilder.builder().withNodes(BoxBuilder.builder().withNodes(pitchIndicator, rollIndicator, yawIndicator).buildVBox(), lineChart).buildHBox());

    }

    private double calculatePitchCurrent(Ship ship, double pitchSpeed, ModuleProfile moduleProfile) {
        return pitchSpeed * getMassCurveMultiplier(ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo() + ship.getCurrentFuelReserve(), moduleProfile) / 100;
    }

    private double calculateRollCurrent(Ship ship, double rollSpeed, ModuleProfile moduleProfile) {
        return rollSpeed * getMassCurveMultiplier(ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo() + ship.getCurrentFuelReserve(), moduleProfile) / 100;
    }

    private double calculateYawCurrent(Ship ship, double yawSpeed, ModuleProfile moduleProfile) {
        return yawSpeed * getMassCurveMultiplier(ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo() + ship.getCurrentFuelReserve(), moduleProfile) / 100;
    }

    private double calculatePitchMinimum(Ship ship, double pitchSpeed, ModuleProfile moduleProfile) {
        return pitchSpeed * getMassCurveMultiplier(ship.getEmptyMass() + ship.getMaxFuel() + ship.getMaxCargo() + ship.getMaxFuelReserve(), moduleProfile) / 100;
    }

    private double calculateRollMinimum(Ship ship, double rollSpeed, ModuleProfile moduleProfile) {
        return rollSpeed * getMassCurveMultiplier(ship.getEmptyMass() + ship.getMaxFuel() + ship.getMaxCargo() + ship.getMaxFuelReserve(), moduleProfile) / 100;
    }

    private double calculateYawMinimum(Ship ship, double yawSpeed, ModuleProfile moduleProfile) {
        return yawSpeed * getMassCurveMultiplier(ship.getEmptyMass() + ship.getMaxFuel() + ship.getMaxCargo() + ship.getMaxFuelReserve(), moduleProfile) / 100;
    }

    private double calculatePitchMaximum(Ship ship, double pitchSpeed, ModuleProfile moduleProfile) {
        return pitchSpeed * getMassCurveMultiplier(ship.getEmptyMass(), moduleProfile) / 100;
    }

    private double calculateRollMaximum(Ship ship, double rollSpeed, ModuleProfile moduleProfile) {
        return rollSpeed * getMassCurveMultiplier(ship.getEmptyMass(), moduleProfile) / 100;
    }

    private double calculateYawMaximum(Ship ship, double yawSpeed, ModuleProfile moduleProfile) {
        return yawSpeed * getMassCurveMultiplier(ship.getEmptyMass(), moduleProfile) / 100;
    }

    @Override
    public void initComponents() {
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(new GrowingRegion(), createTitle("ship.stats.handling"), new GrowingRegion())
                .buildHBox());
        this.getNodes().add(new DestroyableSeparator(Orientation.HORIZONTAL));

    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, ShipConfigEvent.class, event -> update()));
    }

    private static Double getMaximumMultiplier(Optional<Slot> thrusters) {
        return thrusters.map(Slot::getShipModule)
                .flatMap(shipModule -> { //enhanced thrusters
                    if (shipModule.getAttibutes().contains(HorizonsModifier.MAXIMUM_MULTIPLIER_ROTATION)) {
                        final Double rotation = (Double) shipModule.getAttributeValue(HorizonsModifier.MAXIMUM_MULTIPLIER_ROTATION);
                        return Optional.of(rotation);
                    }
                    return Optional.empty();
                })
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.MAXIMUM_MULIPLIER)).orElse(0D));
    }

    private static Double getOptimalMultiplier(Optional<Slot> thrusters) {
        return thrusters.map(Slot::getShipModule)
                .flatMap(shipModule -> { //enhanced thrusters
                    if (shipModule.getAttibutes().contains(HorizonsModifier.OPTIMAL_MULTIPLIER_ROTATION)) {
                        final Double rotation = (Double) shipModule.getAttributeValue(HorizonsModifier.OPTIMAL_MULTIPLIER_ROTATION);
                        return Optional.of(rotation);
                    }
                    return Optional.empty();
                })
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.OPTIMAL_MULTIPLIER)).orElse(0D));
    }

    private static Double getMinimumMultiplier(Optional<Slot> thrusters) {
        return thrusters.map(Slot::getShipModule)
                .flatMap(shipModule -> { //enhanced thrusters
                    if (shipModule.getAttibutes().contains(HorizonsModifier.MINIMUM_MULTIPLIER_ROTATION)) {
                        final Double rotation = (Double) shipModule.getAttributeValue(HorizonsModifier.MINIMUM_MULTIPLIER_ROTATION);
                        return Optional.of(rotation);
                    }
                    return Optional.empty();
                })
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.MINIMUM_MULTIPLIER)).orElse(0D));
    }

    @Override
    protected void update() {
        getShip().ifPresent(ship -> {
            final Optional<Slot> thrusters = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_THRUSTERS)).findFirst().filter(Slot::isOccupied);
            final Double minimumMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.ENGINE_MINIMUM_MASS)).orElse(0D);
            final Double optimalMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.ENGINE_OPTIMAL_MASS)).orElse(0D);
            final Double maximumMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.MAXIMUM_MASS)).orElse(0D);
            final Double minimumMultiplier = getMinimumMultiplier(thrusters);
            final Double optimalMultiplier = getOptimalMultiplier(thrusters);
            final Double maximumMultiplier = getMaximumMultiplier(thrusters);
            final Double pitchSpeed = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.MAX_PITCH_SPEED, 0.0D);
            final Double yawSpeed = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.MAX_YAW_SPEED, 0.0D);
            final Double rollSpeed = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.MAX_ROLL_SPEED, 0.0D);
            final Double minPitchSpeed = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.MIN_PITCH_SPEED, 0.0D);
            final Double minYawSpeed = yawSpeed;//reported to be unaffected by pips
            final Double minRollSpeed = rollSpeed;//reported to be unaffected by pips
            final double multiplier = ApplicationState.getInstance().getEnginePips() / 8.0;
            final ModuleProfile moduleProfile = new ModuleProfile(minimumMass, optimalMass, maximumMass, minimumMultiplier, optimalMultiplier, maximumMultiplier);
            var currentPitch = calculatePitchCurrent(ship, (pitchSpeed * multiplier + minPitchSpeed * (1 - multiplier)), moduleProfile);
            var currentRoll = calculateRollCurrent(ship, (rollSpeed * multiplier + minRollSpeed * (1 - multiplier)), moduleProfile);
            var currentYaw = calculateYawCurrent(ship, (yawSpeed * multiplier + minYawSpeed * (1 - multiplier)), moduleProfile);
            var minimumPitch = calculatePitchMinimum(ship, (minPitchSpeed), moduleProfile);
            var minimumRoll = calculateRollMinimum(ship, minRollSpeed, moduleProfile);
            var minimumYaw = calculateYawMinimum(ship, minYawSpeed, moduleProfile);
            var maximumPitch = calculatePitchMaximum(ship, pitchSpeed, moduleProfile);
            var maximumRoll = calculateRollMaximum(ship, rollSpeed, moduleProfile);
            var maximumYaw = calculateYawMaximum(ship, yawSpeed, moduleProfile);


            this.pitchIndicator.updateValues(minimumPitch, currentPitch, maximumPitch);
            this.rollIndicator.updateValues(minimumRoll, currentRoll, maximumRoll);
            this.yawIndicator.updateValues(minimumYaw, currentYaw, maximumYaw);

        });
    }

}
