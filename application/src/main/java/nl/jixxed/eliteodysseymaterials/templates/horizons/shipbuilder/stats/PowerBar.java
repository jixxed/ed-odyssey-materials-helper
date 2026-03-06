package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Orientation;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.PaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.SegmentedBarBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.PowerProfile;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.event.AfterFontSizeSetEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.SegmentType;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegment;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegmentView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.Optional;

public class PowerBar extends DestroyableHBox implements DestroyableEventTemplate {


    private DestroyableSegmentedBar<TypeSegment> segmentedBar;
    private TypeSegment groupP;
    private TypeSegment group1;
    private TypeSegment group2;
    private TypeSegment group3;
    private TypeSegment group4;
    private TypeSegment group5;
    private TypeSegment groupAvailable;
    private TypeSegment groupOverPower;
    private TypeSegment groupOverPowerPotential;
    private double overPower = 0D;
    private boolean retracted;
    private final DoubleProperty guardianBoost = new SimpleDoubleProperty(0.0);
//    private DoubleProperty overPowerFactor = new SimpleDoubleProperty(1.1);
//    private DoubleProperty availableFactor = new SimpleDoubleProperty(1D);

    public PowerBar(boolean retracted) {
        this.retracted = retracted;
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        final PowerProfile retractedPower = calculateRetractedPower();
        this.groupP = new TypeSegment(Math.max(0D, retractedPower.getPowerGroupPassive()), SegmentType.POWER_GROUP_P);
        this.group1 = new TypeSegment(Math.max(0D, retractedPower.getPowerGroup1()), SegmentType.POWER_GROUP_1);
        this.group2 = new TypeSegment(Math.max(0D, retractedPower.getPowerGroup2()), SegmentType.POWER_GROUP_2);
        this.group3 = new TypeSegment(Math.max(0D, retractedPower.getPowerGroup3()), SegmentType.POWER_GROUP_3);
        this.group4 = new TypeSegment(Math.max(0D, retractedPower.getPowerGroup4()), SegmentType.POWER_GROUP_4);
        this.group5 = new TypeSegment(Math.max(0D, retractedPower.getPowerGroup5()), SegmentType.POWER_GROUP_5);
        this.groupOverPower = new TypeSegment(0D, SegmentType.POWER_OVERPOWER);
        this.groupOverPowerPotential = new TypeSegment(0D, SegmentType.POWER_POTENTIAL_OVERPOWER);
        this.groupAvailable = new TypeSegment(Math.max(0D, retractedPower.getPowerCapacity() - retractedPower.getPowerGroupPassive() - retractedPower.getPowerGroup1() - retractedPower.getPowerGroup2() - retractedPower.getPowerGroup3() - retractedPower.getPowerGroup4() - retractedPower.getPowerGroup5()), SegmentType.POWER_GROUP_NONE);
        this.segmentedBar = SegmentedBarBuilder.builder(TypeSegment.class)
                .withStyleClass("power-progressbar")
                .withOrientation(Orientation.HORIZONTAL)
                .withInfoNodeFactory(segment -> {
                    if (segment.getSegmentType() == SegmentType.POWER_POTENTIAL_OVERPOWER) {
                        return null;
                    }


                    final PowerProfile powerProfile = retracted ? calculateRetractedPower() : calculateDeployedPower();
                    double powerCapacity = powerProfile.getPowerCapacity() / (1.0 + getPowerBoost());
                    Double temp = 0D;
                    Double percentage = switch (segment.getSegmentType()) {
                        case POWER_GROUP_5:
                            temp += powerProfile.getPowerGroup5();
                        case POWER_GROUP_4:
                            temp += powerProfile.getPowerGroup4();
                        case POWER_GROUP_3:
                            temp += powerProfile.getPowerGroup3();
                        case POWER_GROUP_2:
                            temp += powerProfile.getPowerGroup2();
                        case POWER_GROUP_1:
                            temp += powerProfile.getPowerGroup1();
                        case POWER_GROUP_P:
                            yield temp + powerProfile.getPowerGroupPassive();
                        case POWER_GROUP_NONE:
                            double usedPower1 = powerProfile.usedPower();
                            final double available1 = powerCapacity - usedPower1;
                            yield Math.max(available1, 0D);
                        case POWER_OVERPOWER:
                            double usedPower2 = powerProfile.usedPower();
                            final double available2 = powerCapacity - usedPower2;
                            overPower = Math.abs(Math.min(available2, 0D));
                            yield overPower;
                        default:
                            yield 0.0;
                    } / powerCapacity * 100;
                    String text = segment.getText() + ": "
                            + Formatters.NUMBER_FORMAT_2.format(segment.getSegmentType() == SegmentType.POWER_OVERPOWER ? overPower : segment.getValue())
                            + "MW (" + Formatters.NUMBER_FORMAT_1_CEIL.format(percentage) + "%)";
                    return LabelBuilder.builder()
                            .withStyleClass("tooltip-text")
                            .withNonLocalizedText(text)
                            .build();
                })
                .withSegmentViewFactory(segment -> new TypeSegmentView(segment, false))
                .withSegments(groupP, group1, group2, group3, group4, group5, groupAvailable, groupOverPower, groupOverPowerPotential)
                .build();

        final DestroyableLine lineDestroyedAndMalfunction = createLine(0.2);
        final DestroyableLine lineMalfunction = createLine(0.4);
        final DestroyableLine lineDestroyed = createLine(0.5);
        final DestroyableLine lineMax = createLine(1.0);
//        HBox.setHgrow(this.segmentedBar, Priority.ALWAYS);
//        this.segmentedBar.setMinWidth(100);
//        this.segmentedBar.setMaxWidth(9999);

        final DestroyablePane stackPane = PaneBuilder.builder()
                .withStyleClass("bar-container")
                .withNodes(this.segmentedBar, lineDestroyedAndMalfunction, lineMalfunction, lineDestroyed, lineMax)
                .build();
        this.segmentedBar.addBinding(this.segmentedBar.prefWidthProperty(), stackPane.widthProperty());
//        DestroyableAnchorPane build = AnchorPaneBuilder.builder().withStyleClass("anchorpane-container").withNodes(stackPane).build();
        HBox.setHgrow(stackPane, Priority.ALWAYS);
//        AnchorPane.setBottomAnchor(stackPane, 0.0);
//        AnchorPane.setLeftAnchor(stackPane, 0.0);
//        AnchorPane.setRightAnchor(stackPane, 0.0);
//        AnchorPane.setTopAnchor(stackPane, 0.0);
        this.getNodes().add(stackPane);
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            update();
        }));
    }


    private DestroyableLine createLine(double percentage) {
        DestroyableLine lineMalfunction = new DestroyableLine();
        lineMalfunction.getStyleClass().add("percentage-line");
//        lineMalfunction.setStartX(0D);
        lineMalfunction.addBinding(lineMalfunction.startXProperty(), Bindings.createDoubleBinding(() -> (guardianBoost.getValue() + 1D) * percentage / 1.1 * this.segmentedBar.widthProperty().get(), guardianBoost, this.segmentedBar.widthProperty()));
        lineMalfunction.addBinding(lineMalfunction.endXProperty(), Bindings.createDoubleBinding(() -> (guardianBoost.getValue() + 1D) * percentage / 1.1 * this.segmentedBar.widthProperty().get(), guardianBoost, this.segmentedBar.widthProperty()));
//        lineMalfunction.addBinding(lineMalfunction.startXProperty(), this.segmentedBar.widthProperty().multiply(percentage / 1.1));
//        lineMalfunction.addBinding(lineMalfunction.endXProperty(), this.segmentedBar.widthProperty().multiply(percentage / 1.1));
        lineMalfunction.addBinding(lineMalfunction.endYProperty(), this.segmentedBar.heightProperty());
        return lineMalfunction;
    }

    public void update() {
        final PowerProfile powerProfile = retracted ? calculateRetractedPower() : calculateDeployedPower();
        double originalPowerCapacity = powerProfile.getPowerCapacity() / (1.0 + getPowerBoost());
        double available = powerProfile.getPowerCapacity() - powerProfile.getPowerGroupPassive();
        groupP.setValue(available > 0 ? powerProfile.getPowerGroupPassive() : powerProfile.getPowerGroupPassive() - Math.abs(available));
        available = available - powerProfile.getPowerGroup1();
        group1.setValue(available > 0 ? powerProfile.getPowerGroup1() : Math.max(0D, powerProfile.getPowerGroup1() - Math.abs(available)));
        available = available - powerProfile.getPowerGroup2();
        group2.setValue(available > 0 ? powerProfile.getPowerGroup2() : Math.max(0D, powerProfile.getPowerGroup2() - Math.abs(available)));
        available = available - powerProfile.getPowerGroup3();
        group3.setValue(available > 0 ? powerProfile.getPowerGroup3() : Math.max(0D, powerProfile.getPowerGroup3() - Math.abs(available)));
        available = available - powerProfile.getPowerGroup4();
        group4.setValue(available > 0 ? powerProfile.getPowerGroup4() : Math.max(0D, powerProfile.getPowerGroup4() - Math.abs(available)));
        available = available - powerProfile.getPowerGroup5();
        group5.setValue(available > 0 ? powerProfile.getPowerGroup5() : Math.max(0D, powerProfile.getPowerGroup5() - Math.abs(available)));
//        double usedPower = powerProfile.usedPower();
//        final double available = powerProfile.getPowerCapacity() - usedPower;
        groupAvailable.setValue(Math.max(available, 0D));
//        groupOverPower.setValue(Math.abs(Math.min(available, 0D)));
        overPower = Math.abs(Math.min(available, 0D));
//        overPowerFactor.set(overPower > 0D ? 1.1 : 1D);
//        availableFactor.set(overPower > 0D ? (power.get(0) + overPower) / power.get(0) : 1D);
        double v = (getPowerBoost() > 0D) ? 0.06 : 0.1;
        groupOverPower.setValue(overPower > 0D ? originalPowerCapacity * v : 0D);
        groupOverPowerPotential.setValue(overPower <= 0D ? originalPowerCapacity * v : 0D);
        this.guardianBoost.set(getPowerBoost());
    }

    private PowerProfile calculateRetractedPower() {
        return getShip().map(Ship::getRetractedPower).orElseGet(PowerProfile::new);
    }

    private PowerProfile calculateDeployedPower() {
        return getShip().map(Ship::getDeployedPower).orElseGet(PowerProfile::new);
    }

    private double getPowerBoost() {
        return getShip()
                .map(ship -> ship.getCoreSlots().stream()
                        .filter(slot -> slot.getSlotType().equals(SlotType.CORE_POWER_DISTRIBUTION))
                        .findFirst()
                        .map(slot -> (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_BOOST, true))
                        .orElse(0.0))
                .orElse(0.0);
    }

    public Optional<Ship> getShip() {
        return Optional.ofNullable(ApplicationState.getInstance().getShip());
    }

}
