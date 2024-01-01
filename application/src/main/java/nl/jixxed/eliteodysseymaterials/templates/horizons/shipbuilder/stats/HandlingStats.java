package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
@Slf4j
public class HandlingStats extends Stats implements Template {
    private final DestroyableLabel currentPitch;
    private final DestroyableLabel currentRoll;
    private final DestroyableLabel currentYaw;
    private final DestroyableLabel minimumPitch;
    private final DestroyableLabel minimumRoll;
    private final DestroyableLabel minimumYaw;
    private final DestroyableLabel maximumPitch;
    private final DestroyableLabel maximumRoll;
    private final DestroyableLabel maximumYaw;

    public HandlingStats() {
        super();
        initComponents();
        initEventHandling();
        this.currentPitch = createValueLabel(String.format("%.2f", calculatePitchCurrent()));
        this.currentRoll = createValueLabel(String.format("%.2f", calculateRollCurrent()));
        this.currentYaw = createValueLabel(String.format("%.2f", calculateYawCurrent()));
        this.minimumPitch = createValueLabel(String.format("%.2f", calculatePitchMinimum()));
        this.minimumRoll = createValueLabel(String.format("%.2f", calculateRollMinimum()));
        this.minimumYaw = createValueLabel(String.format("%.2f", calculateYawMinimum()));
        this.maximumPitch = createValueLabel(String.format("%.2f", calculatePitchMaximum()));
        this.maximumRoll = createValueLabel(String.format("%.2f", calculateRollMaximum()));
        this.maximumYaw = createValueLabel(String.format("%.2f", calculateYawMaximum()));
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.handling.currentpitch"), new GrowingRegion(), this.currentPitch).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.handling.currentroll"), new GrowingRegion(), this.currentRoll).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.handling.currentyaw"), new GrowingRegion(), this.currentYaw).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.handling.minimumpitch"), new GrowingRegion(), this.minimumPitch).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.handling.minimumroll"), new GrowingRegion(), this.minimumRoll).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.handling.minimumyaw"), new GrowingRegion(), this.minimumYaw).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.handling.maximumpitch"), new GrowingRegion(), this.maximumPitch).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.handling.maximumroll"), new GrowingRegion(), this.maximumRoll).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.handling.maximumyaw"), new GrowingRegion(), this.maximumYaw).buildHBox());
    }
    private double calculatePitchCurrent(){
        return 0d;
    }
    private double calculateRollCurrent(){
        return 0d;
    }
    private double calculateYawCurrent(){
        return 0d;
    }
    private double calculatePitchMinimum(){
        return 0d;
    }
    private double calculateRollMinimum(){
        return 0d;
    }
    private double calculateYawMinimum(){
        return 0d;
    }
    private double calculatePitchMaximum(){
        return 0d;
    }
    private double calculateRollMaximum(){
        return 0d;
    }
    private double calculateYawMaximum(){
        return 0d;
    }

    @Override
    public void initComponents() {
        this.getChildren().add(BoxBuilder.builder().withNodes(new GrowingRegion(), createTitle("ship.stats.handling"), new GrowingRegion()).buildHBox());
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));

    }

    @Override
    public void initEventHandling() {

    }

    @Override
    protected void update() {
        log.debug("update handling: " + this.getShip().isPresent());
        this.getShip().ifPresent(ship1 ->  log.debug("type: " + ship1.getShipType()));
        this.currentPitch.setText(String.format("%.2f", calculatePitchCurrent()));
        this.currentRoll.setText(String.format("%.2f", calculateRollCurrent()));
        this.currentYaw.setText(String.format("%.2f", calculateYawCurrent()));
        this.minimumPitch.setText(String.format("%.2f", calculatePitchMinimum()));
        this.minimumRoll.setText(String.format("%.2f", calculateRollMinimum()));
        this.minimumYaw.setText(String.format("%.2f", calculateYawMinimum()));
        this.maximumPitch.setText(String.format("%.2f", calculatePitchMaximum()));
        this.maximumRoll.setText(String.format("%.2f", calculateRollMaximum()));
        this.maximumYaw.setText(String.format("%.2f", calculateYawMaximum()));
    }
}
