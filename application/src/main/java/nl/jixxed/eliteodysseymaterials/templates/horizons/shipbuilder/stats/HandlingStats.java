package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.beans.binding.StringBinding;
import javafx.css.PseudoClass;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.NumberAxisBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.HoverableLineChart;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableSeparator;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
public class HandlingStats extends Stats implements DestroyableTemplate {
    private RangeIndicator pitchIndicator;
    private RangeIndicator rollIndicator;
    private RangeIndicator yawIndicator;
    private Map<Integer, XYChart.Data<Number, Number>> minPitchMap;
    private Map<Integer, XYChart.Data<Number, Number>> maxPitchMap;
    private Map<Integer, XYChart.Data<Number, Number>> currentPitchMap;
    private HoverableLineChart pitchChart;
    private Map<Integer, XYChart.Data<Number, Number>> minRollMap;
    private Map<Integer, XYChart.Data<Number, Number>> maxRollMap;
    private Map<Integer, XYChart.Data<Number, Number>> currentRollMap;
    private HoverableLineChart rollChart;
    private Map<Integer, XYChart.Data<Number, Number>> minYawMap;
    private Map<Integer, XYChart.Data<Number, Number>> maxYawMap;
    private Map<Integer, XYChart.Data<Number, Number>> currentYawMap;
    private HoverableLineChart yawChart;
    private DestroyableHBox buttons;
    private DestroyableHBox maxValues;
    private Handling toShow = Handling.PITCH;
    private DestroyableLabel pitchButton;
    private DestroyableLabel rollButton;
    private DestroyableLabel yawButton;
    private DestroyableLabel pitchMaxValue;
    private DestroyableLabel rollMaxValue;
    private DestroyableLabel yawMaxValue;
    private DestroyableSeparator separator;

    public HandlingStats() {
        super();
        initComponents();
        initEventHandling();

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
        this.getStyleClass().add("handling-stats");
        addTitle("ship.stats.handling");
        pitchIndicator = new RangeIndicator(0D, 0D, 0D, "ship.stats.handling.pitch", "ship.stats.handling.pitch.value");
        this.getNodes().add(pitchIndicator);
        rollIndicator = new RangeIndicator(0D, 0D, 0D, "ship.stats.handling.roll", "ship.stats.handling.roll.value");
        this.getNodes().add(rollIndicator);
        yawIndicator = new RangeIndicator(0D, 0D, 0D, "ship.stats.handling.yaw", "ship.stats.handling.yaw.value");
        this.getNodes().add(yawIndicator);
        pitchButton = LabelBuilder.builder()
                .withStyleClass("handling-button")
                .withText("ship.stats.handling.pitch")
                .withHoverProperty(self -> (_, _, newValue) -> {
                    if (Boolean.TRUE.equals(newValue)) {
                        toShow = Handling.PITCH;
                        setVisibility(true, pitchChart);
                        setVisibility(false, rollChart, yawChart);
                        self.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
                        rollButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
                        yawButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
                    }
                })
                .build();
        pitchButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
        pitchButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("first"), true);
        rollButton = LabelBuilder.builder()
                .withStyleClass("handling-button")
                .withText("ship.stats.handling.roll")
                .withHoverProperty(self -> (_, _, newValue) -> {
                    if (Boolean.TRUE.equals(newValue)) {
                        toShow = Handling.ROLL;
                        setVisibility(true, rollChart);
                        setVisibility(false, pitchChart, yawChart);
                        self.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
                        pitchButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
                        yawButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
                    }
                })
                .build();
        yawButton = LabelBuilder.builder()
                .withStyleClass("handling-button")
                .withText("ship.stats.handling.yaw")
                .withHoverProperty(self -> (_, _, newValue) -> {
                    if (Boolean.TRUE.equals(newValue)) {
                        toShow = Handling.YAW;
                        setVisibility(true, yawChart);
                        setVisibility(false, rollChart, pitchChart);
                        self.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
                        pitchButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
                        rollButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
                    }
                })
                .build();
        buttons = BoxBuilder.builder()
                .withStyleClass("handling-buttons")
                .withNodes(pitchButton, rollButton, yawButton)
                .buildHBox();
        HBox.setHgrow(pitchButton, Priority.ALWAYS);
        HBox.setHgrow(rollButton, Priority.ALWAYS);
        HBox.setHgrow(yawButton, Priority.ALWAYS);
        this.getNodes().add(buttons);


        separator = new DestroyableSeparator(Orientation.HORIZONTAL);
        separator.getStyleClass().add("splitter");
        this.getNodes().add(separator);

        pitchMaxValue = LabelBuilder.builder()
                .withStyleClass("max-value")
                .withText("ship.stats.handling.pitch.value", 0)
                .build();
        rollMaxValue = LabelBuilder.builder()
                .withStyleClass("max-value")
                .withText("ship.stats.handling.roll.value", 0)
                .build();
        yawMaxValue = LabelBuilder.builder()
                .withStyleClass("max-value")
                .withText("ship.stats.handling.yaw.value", 0)
                .build();
        maxValues = BoxBuilder.builder()
                .withStyleClass("max-values")
                .withNodes(pitchMaxValue, rollMaxValue, yawMaxValue)
                .buildHBox();
        HBox.setHgrow(pitchMaxValue, Priority.ALWAYS);
        HBox.setHgrow(rollMaxValue, Priority.ALWAYS);
        HBox.setHgrow(yawMaxValue, Priority.ALWAYS);


        this.getNodes().add(maxValues);
        this.getNodes().add(new GrowingRegion());
        addPitchChart();
        addRollChart();
        addYawChart();
    }

    private void addPitchChart() {
        NumberAxis xAxis = NumberAxisBuilder.builder()
                .withLabel("ship.stats.handling.chart.xaxis")
                .withLowerBound(0D)
                .withUpperBound(100D)
                .withTickUnit(25D)
                .withAutoRanging(false)
                .build();
        NumberAxis yAxis = NumberAxisBuilder.builder()
                .withLabel("ship.stats.handling.chart.yaxis")
                .build();

        XYChart.Series<Number, Number> minPitchSeries = new XYChart.Series<>();
        minPitchSeries.nameProperty().bind(LocaleService.getStringBinding("ship.stats.handling.chart.min"));
        XYChart.Series<Number, Number> currentPitchSeries = new XYChart.Series<>();
        currentPitchSeries.nameProperty().bind(LocaleService.getStringBinding("ship.stats.handling.chart.current"));
        XYChart.Series<Number, Number> maxPitchSeries = new XYChart.Series<>();
        maxPitchSeries.nameProperty().bind(LocaleService.getStringBinding("ship.stats.handling.chart.max"));

        Function<Map<Object, Number>, StringBinding> tooltipFunction = data -> LocaleService.getStringBinding("ship.stats.handling.chart.tooltip",
                LocaleService.LocalizationKey.of("ship.stats.handling.chart.throttle"), Formatters.NUMBER_FORMAT_0.format(data.get("x")),
                LocaleService.LocalizationKey.of("ship.stats.handling.chart.min"), Formatters.NUMBER_FORMAT_2.format(data.get(minPitchSeries)),
                LocaleService.LocalizationKey.of("ship.stats.handling.chart.current"), Formatters.NUMBER_FORMAT_2.format(data.get(currentPitchSeries)),
                LocaleService.LocalizationKey.of("ship.stats.handling.chart.max"), Formatters.NUMBER_FORMAT_2.format(data.get(maxPitchSeries))
        );

        pitchChart = new HoverableLineChart(xAxis, yAxis, tooltipFunction);
        pitchChart.setLegendVisible(false);
        pitchChart.setCreateSymbols(false);
        pitchChart.setHorizontalGridLinesVisible(false);
        pitchChart.setVerticalGridLinesVisible(false);
        minPitchMap = new HashMap<>();
        maxPitchMap = new HashMap<>();
        currentPitchMap = new HashMap<>();
        for (int index = 0; index <= 100; index++) {
            minPitchMap.put(index, new XYChart.Data<>(index, 0));
            maxPitchMap.put(index, new XYChart.Data<>(index, 0));
            currentPitchMap.put(index, new XYChart.Data<>(index, 0));
        }
        minPitchSeries.getData().addAll(minPitchMap.values());
        maxPitchSeries.getData().addAll(maxPitchMap.values());
        currentPitchSeries.getData().addAll(currentPitchMap.values());
        pitchChart.getData().add(minPitchSeries);
        pitchChart.getData().add(maxPitchSeries);
        pitchChart.getData().add(currentPitchSeries);

        this.getNodes().add(pitchChart);
    }

    private void addRollChart() {
        NumberAxis xAxis = NumberAxisBuilder.builder()
                .withLabel("ship.stats.handling.chart.xaxis")
                .withLowerBound(0D)
                .withUpperBound(100D)
                .withTickUnit(25D)
                .withAutoRanging(false)
                .build();
        NumberAxis yAxis = NumberAxisBuilder.builder()
                .withLabel("ship.stats.handling.chart.yaxis")
                .build();

        XYChart.Series<Number, Number> minRollSeries = new XYChart.Series<>();
        minRollSeries.nameProperty().bind(LocaleService.getStringBinding("ship.stats.handling.chart.min"));
        XYChart.Series<Number, Number> currentRollSeries = new XYChart.Series<>();
        currentRollSeries.nameProperty().bind(LocaleService.getStringBinding("ship.stats.handling.chart.current"));
        XYChart.Series<Number, Number> maxRollSeries = new XYChart.Series<>();
        maxRollSeries.nameProperty().bind(LocaleService.getStringBinding("ship.stats.handling.chart.max"));

        Function<Map<Object, Number>, StringBinding> tooltipFunction = data -> LocaleService.getStringBinding("ship.stats.handling.chart.tooltip",
                LocaleService.LocalizationKey.of("ship.stats.handling.chart.throttle"), Formatters.NUMBER_FORMAT_0.format(data.get("x")),
                LocaleService.LocalizationKey.of("ship.stats.handling.chart.min"), Formatters.NUMBER_FORMAT_2.format(data.get(minRollSeries)),
                LocaleService.LocalizationKey.of("ship.stats.handling.chart.current"), Formatters.NUMBER_FORMAT_2.format(data.get(currentRollSeries)),
                LocaleService.LocalizationKey.of("ship.stats.handling.chart.max"), Formatters.NUMBER_FORMAT_2.format(data.get(maxRollSeries))
        );

        rollChart = new HoverableLineChart(xAxis, yAxis, tooltipFunction);
        rollChart.setLegendVisible(false);
        rollChart.setCreateSymbols(false);
        rollChart.setHorizontalGridLinesVisible(false);
        rollChart.setVerticalGridLinesVisible(false);
        minRollMap = new HashMap<>();
        maxRollMap = new HashMap<>();
        currentRollMap = new HashMap<>();
        for (int index = 0; index <= 100; index++) {
            minRollMap.put(index, new XYChart.Data<>(index, 0));
            maxRollMap.put(index, new XYChart.Data<>(index, 0));
            currentRollMap.put(index, new XYChart.Data<>(index, 0));
        }
        minRollSeries.getData().addAll(minRollMap.values());
        maxRollSeries.getData().addAll(maxRollMap.values());
        currentRollSeries.getData().addAll(currentRollMap.values());
        rollChart.getData().add(minRollSeries);
        rollChart.getData().add(maxRollSeries);
        rollChart.getData().add(currentRollSeries);

        this.getNodes().add(rollChart);
    }

    private void addYawChart() {
        NumberAxis xAxis = NumberAxisBuilder.builder()
                .withLabel("ship.stats.handling.chart.xaxis")
                .withLowerBound(0D)
                .withUpperBound(100D)
                .withTickUnit(25D)
                .withAutoRanging(false)
                .build();
        NumberAxis yAxis = NumberAxisBuilder.builder()
                .withLabel("ship.stats.handling.chart.yaxis")
                .build();

        XYChart.Series<Number, Number> minYawSeries = new XYChart.Series<>();
        minYawSeries.nameProperty().bind(LocaleService.getStringBinding("ship.stats.handling.chart.min"));
        XYChart.Series<Number, Number> currentYawSeries = new XYChart.Series<>();
        currentYawSeries.nameProperty().bind(LocaleService.getStringBinding("ship.stats.handling.chart.current"));
        XYChart.Series<Number, Number> maxYawSeries = new XYChart.Series<>();
        maxYawSeries.nameProperty().bind(LocaleService.getStringBinding("ship.stats.handling.chart.max"));

        Function<Map<Object, Number>, StringBinding> tooltipFunction = data -> LocaleService.getStringBinding("ship.stats.handling.chart.tooltip",
                LocaleService.LocalizationKey.of("ship.stats.handling.chart.throttle"), Formatters.NUMBER_FORMAT_0.format(data.get("x")),
                LocaleService.LocalizationKey.of("ship.stats.handling.chart.min"), Formatters.NUMBER_FORMAT_2.format(data.get(minYawSeries)),
                LocaleService.LocalizationKey.of("ship.stats.handling.chart.current"), Formatters.NUMBER_FORMAT_2.format(data.get(currentYawSeries)),
                LocaleService.LocalizationKey.of("ship.stats.handling.chart.max"), Formatters.NUMBER_FORMAT_2.format(data.get(maxYawSeries))
        );

        yawChart = new HoverableLineChart(xAxis, yAxis, tooltipFunction);
        yawChart.setLegendVisible(false);
        yawChart.setCreateSymbols(false);
        yawChart.setHorizontalGridLinesVisible(false);
        yawChart.setVerticalGridLinesVisible(false);
        minYawMap = new HashMap<>();
        maxYawMap = new HashMap<>();
        currentYawMap = new HashMap<>();
        for (int index = 0; index <= 100; index++) {
            minYawMap.put(index, new XYChart.Data<>(index, 0));
            maxYawMap.put(index, new XYChart.Data<>(index, 0));
            currentYawMap.put(index, new XYChart.Data<>(index, 0));
        }
        minYawSeries.getData().addAll(minYawMap.values());
        maxYawSeries.getData().addAll(maxYawMap.values());
        currentYawSeries.getData().addAll(currentYawMap.values());
        yawChart.getData().add(minYawSeries);
        yawChart.getData().add(maxYawSeries);
        yawChart.getData().add(currentYawSeries);

        this.getNodes().add(yawChart);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, ShipConfigEvent.class, event -> update()));
    }

    private static Double getMaximumMultiplier(Optional<Slot> thrusters) {
        return thrusters.map(Slot::getShipModule)
                .flatMap(shipModule -> { //enhanced thrusters
                    if (shipModule.getAttibutes().contains(HorizonsModifier.MAXIMUM_MULTIPLIER_ROTATION)) {
                        final Double rotation = (Double) shipModule.getAttributeValue(HorizonsModifier.MAXIMUM_MULTIPLIER_ROTATION, true);
                        return Optional.of(rotation);
                    }
                    return Optional.empty();
                })
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.MAXIMUM_MULIPLIER, true)).orElse(0D));
    }

    private static Double getOptimalMultiplier(Optional<Slot> thrusters) {
        return thrusters.map(Slot::getShipModule)
                .flatMap(shipModule -> { //enhanced thrusters
                    if (shipModule.getAttibutes().contains(HorizonsModifier.OPTIMAL_MULTIPLIER_ROTATION)) {
                        final Double rotation = (Double) shipModule.getAttributeValue(HorizonsModifier.OPTIMAL_MULTIPLIER_ROTATION, true);
                        return Optional.of(rotation);
                    }
                    return Optional.empty();
                })
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.OPTIMAL_MULTIPLIER, true)).orElse(0D));
    }

    private static Double getMinimumMultiplier(Optional<Slot> thrusters) {
        return thrusters.map(Slot::getShipModule)
                .flatMap(shipModule -> { //enhanced thrusters
                    if (shipModule.getAttibutes().contains(HorizonsModifier.MINIMUM_MULTIPLIER_ROTATION)) {
                        final Double rotation = (Double) shipModule.getAttributeValue(HorizonsModifier.MINIMUM_MULTIPLIER_ROTATION, true);
                        return Optional.of(rotation);
                    }
                    return Optional.empty();
                })
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.MINIMUM_MULTIPLIER, true)).orElse(0D));
    }

    @Override
    protected void update() {
        getShip().ifPresent(ship -> {
            final Optional<Slot> thrusters = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_THRUSTERS)).findFirst().filter(Slot::isOccupied);
            final Double minimumMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.ENGINE_MINIMUM_MASS, true)).orElse(0D);
            final Double optimalMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.ENGINE_OPTIMAL_MASS, true)).orElse(0D);
            final Double maximumMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.MAXIMUM_MASS, true)).orElse(0D);
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

            pitchMaxValue.textProperty().bind(LocaleService.getStringBinding("ship.stats.handling.pitch.value", Formatters.NUMBER_FORMAT_2.format(mapProfile(50, ApplicationState.getInstance().getEnginePips(), (Double[][]) ship.getAttributes().get(HorizonsModifier.CRUISE_PITCH_PROFILE), moduleProfile, ship))));
            rollMaxValue.textProperty().bind(LocaleService.getStringBinding("ship.stats.handling.roll.value", Formatters.NUMBER_FORMAT_2.format(mapProfile(50, ApplicationState.getInstance().getEnginePips(), (Double[][]) ship.getAttributes().get(HorizonsModifier.CRUISE_ROLL_PROFILE), moduleProfile, ship))));
            yawMaxValue.textProperty().bind(LocaleService.getStringBinding("ship.stats.handling.yaw.value", Formatters.NUMBER_FORMAT_2.format(mapProfile(50, ApplicationState.getInstance().getEnginePips(), (Double[][]) ship.getAttributes().get(HorizonsModifier.CRUISE_YAW_PROFILE), moduleProfile, ship))));

            if (ship.supportsGraphs()) {
                setVisibility(true, buttons, maxValues, separator);
                if (toShow == Handling.PITCH) {
                    setVisibility(true, pitchChart);
                    setVisibility(false, rollChart);
                    setVisibility(false, yawChart);
                } else if (toShow == Handling.ROLL) {
                    setVisibility(false, pitchChart);
                    setVisibility(true, rollChart);
                    setVisibility(false, yawChart);
                } else if (toShow == Handling.YAW) {
                    setVisibility(false, pitchChart);
                    setVisibility(false, rollChart);
                    setVisibility(true, yawChart);
                }
                setVisibility(false, pitchIndicator, rollIndicator, yawIndicator);
                minPitchMap.forEach((key, value) -> {
                    Double[][] profile = (Double[][]) ship.getAttributes().get(HorizonsModifier.CRUISE_PITCH_PROFILE);
                    value.setYValue(mapProfile(key, 0, profile, moduleProfile, ship));
                });
                maxPitchMap.forEach((key, value) -> {
                    Double[][] profile = (Double[][]) ship.getAttributes().get(HorizonsModifier.CRUISE_PITCH_PROFILE);
                    value.setYValue(mapProfile(key, 8, profile, moduleProfile, ship));
                });
                currentPitchMap.forEach((key, value) -> {
                    Double[][] profile = (Double[][]) ship.getAttributes().get(HorizonsModifier.CRUISE_PITCH_PROFILE);
                    value.setYValue(mapProfile(key, ApplicationState.getInstance().getEnginePips(), profile, moduleProfile, ship));
                });
                minRollMap.forEach((key, value) -> {
                    Double[][] profile = (Double[][]) ship.getAttributes().get(HorizonsModifier.CRUISE_ROLL_PROFILE);
                    value.setYValue(mapProfile(key, 0, profile, moduleProfile, ship));
                });
                maxRollMap.forEach((key, value) -> {
                    Double[][] profile = (Double[][]) ship.getAttributes().get(HorizonsModifier.CRUISE_ROLL_PROFILE);
                    value.setYValue(mapProfile(key, 8, profile, moduleProfile, ship));
                });
                currentRollMap.forEach((key, value) -> {
                    Double[][] profile = (Double[][]) ship.getAttributes().get(HorizonsModifier.CRUISE_ROLL_PROFILE);
                    value.setYValue(mapProfile(key, ApplicationState.getInstance().getEnginePips(), profile, moduleProfile, ship));
                });
                minYawMap.forEach((key, value) -> {
                    Double[][] profile = (Double[][]) ship.getAttributes().get(HorizonsModifier.CRUISE_YAW_PROFILE);
                    value.setYValue(mapProfile(key, 0, profile, moduleProfile, ship));
                });
                maxYawMap.forEach((key, value) -> {
                    Double[][] profile = (Double[][]) ship.getAttributes().get(HorizonsModifier.CRUISE_YAW_PROFILE);
                    value.setYValue(mapProfile(key, 8, profile, moduleProfile, ship));
                });
                currentYawMap.forEach((key, value) -> {
                    Double[][] profile = (Double[][]) ship.getAttributes().get(HorizonsModifier.CRUISE_YAW_PROFILE);
                    value.setYValue(mapProfile(key, ApplicationState.getInstance().getEnginePips(), profile, moduleProfile, ship));
                });
            } else {
                setVisibility(false, buttons, maxValues, pitchChart, rollChart, yawChart, separator);
                setVisibility(true, pitchIndicator, rollIndicator, yawIndicator);
            }
        });
    }

    //    private static void mapProfile(Integer key, XYChart.Data<Number, Number> value, Double pivot, Double min, Double max) {
    private double mapProfile(Integer key, int pips, Double[][] profile, ModuleProfile moduleProfile, Ship ship) {

//        Final	0	    8
//        0%	38,93	39,81
//        50%	27,52	35,68
//        100%	30,02	42,22
//        calculatePitchCurrent(ship, (pitchSpeed * multiplier + minPitchSpeed * (1 - multiplier)), moduleProfile);
        Double pivot = profile[1][0] + ((profile[1][1] - profile[1][0]) * (pips / 8D));
        Double min = profile[0][0] + ((profile[0][1] - profile[0][0]) * (pips / 8D));
        Double max = profile[2][0] + ((profile[2][1] - profile[2][0]) * (pips / 8D));
        int distance = Math.abs(key - 50) * 2;
        if (key <= 50) {
            double pivotMax = pivot;
            double pivotMin = pivotMax * (min / 100);
            double y = (pivotMax - (pivotMax - pivotMin) * (distance / 100.0)) * getMassCurveMultiplier(ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo() + ship.getCurrentFuelReserve(), moduleProfile) / 100D;
            return (Double.isNaN(y) || Double.isInfinite(y)) ? 0 : y;
        } else {
            double pivotMin = pivot;
            double pivotMax = pivotMin * (max / 100);
            double y = (pivotMin + (pivotMax - pivotMin) * (distance / 100.0)) * getMassCurveMultiplier(ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo() + ship.getCurrentFuelReserve(), moduleProfile) / 100D;
            return (Double.isNaN(y) || Double.isInfinite(y)) ? 0 : y;
        }
    }

    private void setVisibility(boolean visible, Node... nodes) {
        Arrays.stream(nodes).forEach(node -> {
            node.setVisible(visible);
            node.setManaged(visible);
        });
    }

    enum Handling {PITCH, ROLL, YAW}
}
