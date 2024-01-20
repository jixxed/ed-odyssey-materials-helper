package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

@Slf4j
public class WeaponStats extends Stats implements Template {
    private DestroyableLabel rawDamage;
    private DestroyableLabel absolutePercentage;
    private DestroyableLabel kineticPercentage;
    private DestroyableLabel thermalPercentage;
    private DestroyableLabel explosivePercentage;
    private DestroyableLabel causticPercentage;
    private DestroyableLabel currentDuration;
    private DestroyableLabel maxDuration;
    private DestroyableLabel maxAmmoDuration;
    private DestroyableLabel currentSustainedDamagePercentage;
    private DestroyableLabel maxSustainedDamagePercentage;

    public WeaponStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getChildren().add(BoxBuilder.builder().withNodes(new GrowingRegion(), createTitle("ship.stats.weapon"), new GrowingRegion()).buildHBox());
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        this.rawDamage = createValueLabel(String.format("%.2f", calculateRawDamage()));
        this.absolutePercentage = createValueLabel(String.format("%.2f", calculateAbsolutePercentage()));
        this.kineticPercentage = createValueLabel(String.format("%.2f", calculateKineticPercentage()));
        this.thermalPercentage = createValueLabel(String.format("%.2f", calculateThermalPercentage()));
        this.explosivePercentage = createValueLabel(String.format("%.2f", calculateExplosivePercentage()));
        this.causticPercentage = createValueLabel(String.format("%.2f", calculateCausticPercentage()));
        this.currentDuration = createValueLabel(String.format("%.2f", calculateCurrentDuration()));
        this.maxDuration = createValueLabel(String.format("%.2f", calculateMaxDuration()));
        this.maxAmmoDuration = createValueLabel(String.format("%.2f", calculateMaxAmmoDuration()));
        this.currentSustainedDamagePercentage = createValueLabel(String.format("%.2f", calculateCurrentSustainedDamagePercentage()));
        this.maxSustainedDamagePercentage = createValueLabel(String.format("%.2f", calculateMaxSustainedDamagePercentage()));

        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.weapon.rawdamage"), new GrowingRegion(), this.rawDamage).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.weapon.absolutepercentage"), new GrowingRegion(), this.absolutePercentage).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.weapon.kineticpercentage"), new GrowingRegion(), this.kineticPercentage).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.weapon.thermalpercentage"), new GrowingRegion(), this.thermalPercentage).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.weapon.explosivepercentage"), new GrowingRegion(), this.explosivePercentage).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.weapon.causticpercentage"), new GrowingRegion(), this.causticPercentage).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.weapon.currentduration"), new GrowingRegion(), this.currentDuration).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.weapon.maxduration"), new GrowingRegion(), this.maxDuration).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.weapon.maxammoduration"), new GrowingRegion(), this.maxAmmoDuration).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.weapon.currentsustaineddamagepercentage"), new GrowingRegion(), this.currentSustainedDamagePercentage).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.weapon.maxsustaineddamagepercentage"), new GrowingRegion(), this.maxSustainedDamagePercentage).buildHBox());

    }
    @Override
    public void initEventHandling() {
        eventListeners.add(EventService.addListener(this, ShipConfigEvent.class, event -> update()));
    }

    private double calculateRawDamage() {
        return 0d;
    }

    private double calculateAbsolutePercentage() {
        return 0d;
    }

    private double calculateKineticPercentage() {
        return 0d;
    }

    private double calculateThermalPercentage() {
        return 0d;
    }

    private double calculateExplosivePercentage() {
        return 0d;
    }

    private double calculateCausticPercentage() {
        return 0d;
    }

    private double calculateCurrentDuration() {
        return 0d;
    }

    private double calculateMaxDuration() {
        return 0d;
    }

    private double calculateMaxAmmoDuration() {
        return 0d;
    }

    private double calculateCurrentSustainedDamagePercentage() {
        return 0d;
    }

    private double calculateMaxSustainedDamagePercentage() {
        return 0d;
    }

    @Override
    protected void update() {
        log.debug("update weapon: " + this.getShip().isPresent());
        this.getShip().ifPresent(ship1 -> log.debug("type: " + ship1.getShipType()));
        this.rawDamage.setText(String.format("%.2f", calculateRawDamage()));
        this.absolutePercentage.setText(String.format("%.2f", calculateAbsolutePercentage()));
        this.kineticPercentage.setText(String.format("%.2f", calculateKineticPercentage()));
        this.thermalPercentage.setText(String.format("%.2f", calculateThermalPercentage()));
        this.explosivePercentage.setText(String.format("%.2f", calculateExplosivePercentage()));
        this.causticPercentage.setText(String.format("%.2f", calculateCausticPercentage()));
        this.currentDuration.setText(String.format("%.2f", calculateCurrentDuration()));
        this.maxDuration.setText(String.format("%.2f", calculateMaxDuration()));
        this.maxAmmoDuration.setText(String.format("%.2f", calculateMaxAmmoDuration()));
        this.currentSustainedDamagePercentage.setText(String.format("%.2f", calculateCurrentSustainedDamagePercentage()));
        this.maxSustainedDamagePercentage.setText(String.format("%.2f", calculateMaxSustainedDamagePercentage()));
    }
}
