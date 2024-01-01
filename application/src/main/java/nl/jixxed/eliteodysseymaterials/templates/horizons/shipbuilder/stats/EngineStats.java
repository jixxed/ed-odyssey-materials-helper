package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

@Slf4j
public class EngineStats extends Stats implements Template {
    private DestroyableLabel currentSpeed;
    private DestroyableLabel currentBoost;
    private DestroyableLabel currentRecharge;
    private DestroyableLabel minSpeed;
    private DestroyableLabel minBoost;
    private DestroyableLabel minRecharge;
    private DestroyableLabel maxSpeed;
    private DestroyableLabel maxBoost;
    private DestroyableLabel maxRecharge;
    public EngineStats() {
        super();
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getChildren().add(BoxBuilder.builder().withNodes(new GrowingRegion(), createTitle("ship.stats.engine"), new GrowingRegion()).buildHBox());
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));

        this.minSpeed = createValueLabel(String.format("%.2f", calculateMinSpeed()));
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.engine.minspeed"), new GrowingRegion(), this.minSpeed).buildHBox());
        this.minBoost = createValueLabel(String.format("%.2f", calculateMinBoost()));
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.engine.minboost"), new GrowingRegion(), this.minBoost).buildHBox());
        this.minRecharge = createValueLabel(String.format("%.2f", calculateMinRecharge()));
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.engine.minrecharge"), new GrowingRegion(), this.minRecharge).buildHBox());
        this.currentSpeed = createValueLabel(String.format("%.2f", calculateCurrentSpeed()));
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.engine.currentspeed"), new GrowingRegion(), this.currentSpeed).buildHBox());
        this.currentBoost = createValueLabel(String.format("%.2f", calculateCurrentBoost()));
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.engine.currentboost"), new GrowingRegion(), this.currentBoost).buildHBox());
        this.currentRecharge = createValueLabel(String.format("%.2f", calculateCurrentRecharge()));
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.engine.currentrecharge"), new GrowingRegion(), this.currentRecharge).buildHBox());
        this.maxSpeed = createValueLabel(String.format("%.2f", calculateMaxSpeed()));
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.engine.maxspeed"), new GrowingRegion(), this.maxSpeed).buildHBox());
        this.maxBoost = createValueLabel(String.format("%.2f", calculateMaxBoost()));
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.engine.maxboost"), new GrowingRegion(), this.maxBoost).buildHBox());
        this.maxRecharge = createValueLabel(String.format("%.2f", calculateMaxRecharge()));
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.engine.maxrecharge"), new GrowingRegion(), this.maxRecharge).buildHBox());
    }

    private double calculateMaxRecharge() {
        return 0d;
    }

    private double calculateMaxBoost() {
        return 0d;
    }

    private double calculateMaxSpeed() {
        return 0d;
    }

    private double calculateCurrentRecharge() {
        return 0d;
    }

    private double calculateCurrentBoost() {
        return 0d;
    }

    private double calculateCurrentSpeed() {
        return 0d;
    }

    private double calculateMinRecharge() {
        return 0d;
    }

    private double calculateMinBoost() {
        return 0d;
    }

    private double calculateMinSpeed() {
        return 0d;
    }

    @Override
    protected void update() {
        log.debug("update engine: " + this.getShip().isPresent());
        this.getShip().ifPresent(ship1 ->  log.debug("type: " + ship1.getShipType()));
        this.currentSpeed.setText(String.format("%.2f", calculateCurrentSpeed()));
        this.currentBoost.setText(String.format("%.2f", calculateCurrentBoost()));
        this.currentRecharge.setText(String.format("%.2f", calculateCurrentRecharge()));
        this.minSpeed.setText(String.format("%.2f", calculateMinSpeed()));
        this.minBoost.setText(String.format("%.2f", calculateMinBoost()));
        this.minRecharge.setText(String.format("%.2f", calculateMinRecharge()));
        this.maxSpeed.setText(String.format("%.2f", calculateMaxSpeed()));
        this.maxBoost.setText(String.format("%.2f", calculateMaxBoost()));
        this.maxRecharge.setText(String.format("%.2f", calculateMaxRecharge()));
    }
}
