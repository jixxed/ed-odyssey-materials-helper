package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PowerStats extends Stats implements Template {
    private DestroyableLabel retractedPowerLabel;
    private DestroyableLabel deployedPowerLabel;
    private PowerBar retractedPowerBar;
    private PowerBar deployedPowerBar;

    public PowerStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.retractedPowerLabel = createValueLabel("ship.stats.thermalpower.retractedPower.value", Formatters.NUMBER_FORMAT_2.format(0D), Formatters.NUMBER_FORMAT_2.format(0D));
        this.deployedPowerLabel = createValueLabel("ship.stats.thermalpower.deployedPower.value", Formatters.NUMBER_FORMAT_2.format(0D), Formatters.NUMBER_FORMAT_2.format(0D));
        this.retractedPowerBar = new PowerBar();
        this.deployedPowerBar = new PowerBar();

        this.getChildren().add(BoxBuilder.builder().withNodes(new GrowingRegion(), createTitle("ship.stats.power"), new GrowingRegion()).buildHBox());
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.thermalpower.retractedPower"), new GrowingRegion(), this.retractedPowerLabel).buildHBox());
        this.getChildren().add(retractedPowerBar);
        this.getChildren().add(deployedPowerBar);
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.thermalpower.deployedPower"), new GrowingRegion(), this.deployedPowerLabel).buildHBox());
        this.getChildren().add(new GrowingRegion());

    }

    @Override
    public void initEventHandling() {
        eventListeners.add(EventService.addListener(this, ShipConfigEvent.class, event -> update()));
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


    @Override
    protected void update() {
        final Map<Integer, Double> retractedPower = calculateRetractedPower();
        final Double retractedUsage = retractedPower.values().stream().skip(1).reduce(0D, Double::sum);
        final Double powerBudget = retractedPower.get(0);
        final Double deployedUsage = calculateDeployedPower().values().stream().skip(1).reduce(0D, Double::sum);
        this.retractedPowerLabel.textProperty().bind(LocaleService.getStringBinding("ship.stats.thermalpower.retractedPower.value", Formatters.NUMBER_FORMAT_2.format(retractedUsage), Formatters.NUMBER_FORMAT_2.format(powerBudget)));
        this.deployedPowerLabel.textProperty().bind(LocaleService.getStringBinding("ship.stats.thermalpower.deployedPower.value", Formatters.NUMBER_FORMAT_2.format(deployedUsage), Formatters.NUMBER_FORMAT_2.format(powerBudget)));
        if(retractedUsage > powerBudget){
            if(!this.retractedPowerLabel.getStyleClass().contains("power-stats-overpower"))
                this.retractedPowerLabel.getStyleClass().add("power-stats-overpower");
        }else{
            this.retractedPowerLabel.getStyleClass().removeAll("power-stats-overpower");
        }
        if(deployedUsage > powerBudget){
            if(!this.deployedPowerLabel.getStyleClass().contains("power-stats-overpower"))
                this.deployedPowerLabel.getStyleClass().add("power-stats-overpower");
        }else{
            this.deployedPowerLabel.getStyleClass().removeAll("power-stats-overpower");
        }
        this.retractedPowerBar.update(true);
        this.deployedPowerBar.update(false);
    }
}
