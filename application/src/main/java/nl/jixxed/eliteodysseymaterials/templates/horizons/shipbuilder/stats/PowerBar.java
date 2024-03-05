package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.SegmentType;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegment;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegmentView;
import org.controlsfx.control.SegmentedBar;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PowerBar extends VBox {
    private static final NumberFormat NUMBER_FORMAT_2 = NumberFormat.getNumberInstance();

    static {
        NUMBER_FORMAT_2.setMaximumFractionDigits(2);
    }

    private SegmentedBar<TypeSegment> segmentedBar;
    private TypeSegment group1;
    private TypeSegment group2;
    private TypeSegment group3;
    private TypeSegment group4;
    private TypeSegment group5;
    private TypeSegment groupAvailable;
    private TypeSegment groupOverPower;
    private double overPower = 0D;


    public PowerBar() {

        this.segmentedBar = new SegmentedBar<>();
        this.segmentedBar.getStyleClass().add("power-progressbar");
        this.segmentedBar.setOrientation(Orientation.HORIZONTAL);
        this.segmentedBar.setInfoNodeFactory(segment -> LabelBuilder.builder()
                .withStyleClass("power-progressbar-label").withNonLocalizedText(segment.getText() + ": " + NUMBER_FORMAT_2.format(segment.getSegmentType() == SegmentType.POWER_OVERPOWER ? overPower : segment.getValue()) + "MW").build());
        this.segmentedBar.setSegmentViewFactory(segment -> new TypeSegmentView(segment, Map.of(
                SegmentType.POWER_GROUP_1, Color.web("#2e92df"),
                SegmentType.POWER_GROUP_2, Color.web("#89D07F"),
                SegmentType.POWER_GROUP_3, Color.web("#CE6C1E"),
                SegmentType.POWER_GROUP_4, Color.web("#6D3DA8"),
                SegmentType.POWER_GROUP_5, Color.web("#F8FF2E"),
                SegmentType.POWER_GROUP_NONE, Color.rgb(128, 128, 128),
                SegmentType.POWER_OVERPOWER, Color.rgb(240, 20, 20)
        ), false));
        final Map<Integer, Double> power = calculateRetractedPower();
        this.group1 = new TypeSegment(power.get(1), SegmentType.POWER_GROUP_1);
        this.group2 = new TypeSegment(power.get(2), SegmentType.POWER_GROUP_2);
        this.group3 = new TypeSegment(power.get(3), SegmentType.POWER_GROUP_3);
        this.group4 = new TypeSegment(power.get(4), SegmentType.POWER_GROUP_4);
        this.group5 = new TypeSegment(power.get(5), SegmentType.POWER_GROUP_5);
        this.groupOverPower = new TypeSegment(0D, SegmentType.POWER_OVERPOWER);
        this.groupAvailable = new TypeSegment(power.get(0) - power.get(1) - power.get(2) - power.get(3) - power.get(4) - power.get(5), SegmentType.POWER_GROUP_NONE);
        this.segmentedBar.getSegments().addAll(group1, group2, group3, group4, group5, groupAvailable, groupOverPower);
        this.getChildren().add(this.segmentedBar);
    }

    public void update(boolean retracted) {
        final Map<Integer, Double> power = retracted ? calculateRetractedPower() : calculateDeployedPower();

        group1.setValue(power.get(1));
        group2.setValue(power.get(2));
        group3.setValue(power.get(3));
        group4.setValue(power.get(4));
        group5.setValue(power.get(5));
        double usedPower = power.get(1) + power.get(2) + power.get(3) + power.get(4) + power.get(5);
        final double available = power.get(0) - usedPower;
        groupAvailable.setValue(Math.max(available, 0D));
//        groupOverPower.setValue(Math.abs(Math.min(available, 0D)));
        overPower = Math.abs(Math.min(available, 0D));
        groupOverPower.setValue(overPower > 0D ? usedPower / 10D : 0D);

    }

    private Map<Integer, Double> calculateRetractedPower() {
        return getShip().map(Ship::getRetractedPower).orElseGet(() -> new HashMap<>(Map.of(
                0, 0D,
                1, 0D,
                2, 0D,
                3, 0D,
                4, 0D,
                5, 0D
        )));
    }

    private Map<Integer, Double> calculateDeployedPower() {
        return getShip().map(Ship::getDeployedPower).orElseGet(() -> new HashMap<>(Map.of(
                0, 0D,
                1, 0D,
                2, 0D,
                3, 0D,
                4, 0D,
                5, 0D
        )));
    }

    public Optional<Ship> getShip() {
        return Optional.ofNullable(ApplicationState.getInstance().getShip());
    }

}
