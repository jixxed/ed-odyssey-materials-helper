package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
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
    private DestroyableLabel retractedPower;
    private DestroyableLabel deployedPower;
    private PowerBar retractedPowerBar;
    private PowerBar deployedPowerBar;

    public PowerStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.retractedPower = createValueLabel("ship.stats.thermalpower.retractedPower.value", Formatters.NUMBER_FORMAT_2.format(0D), Formatters.NUMBER_FORMAT_2.format(0D));
        this.deployedPower = createValueLabel("ship.stats.thermalpower.deployedPower.value", Formatters.NUMBER_FORMAT_2.format(0D), Formatters.NUMBER_FORMAT_2.format(0D));
        this.retractedPowerBar = new PowerBar();
        this.deployedPowerBar = new PowerBar();

        this.getChildren().add(BoxBuilder.builder().withNodes(new GrowingRegion(), createTitle("ship.stats.power"), new GrowingRegion()).buildHBox());
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.thermalpower.retractedPower"), new GrowingRegion(), this.retractedPower).buildHBox());
        this.getChildren().add(retractedPowerBar);
        this.getChildren().add(deployedPowerBar);
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.thermalpower.deployedPower"), new GrowingRegion(), this.deployedPower).buildHBox());
        this.getChildren().add(new GrowingRegion());

    }

    @Override
    public void initEventHandling() {
        eventListeners.add(EventService.addListener(this, ShipConfigEvent.class, event -> update()));
    }

    private Map<Integer, Double> calculateRetractedPower() {
        Map<Integer, Double> powerValues = new HashMap<>(Map.of(
                0, 0D,
                1, 0D,
                2, 0D,
                3, 0D,
                4, 0D,
                5, 0D
        ));

        getShip().ifPresent(ship -> {
            powerValues.put(0, (Double) ship.getCoreSlots().stream().filter(slot -> SlotType.CORE_POWER_PLANT.equals(slot.getSlotType())).findFirst().map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_CAPACITY)).orElse(0.0D));
            if(ship.getCargoHatch().isOccupied() && ship.getCargoHatch().getShipModule().isPowered()){
                powerValues.compute(ship.getCargoHatch().getShipModule().getPowerGroup(), (key, value) -> value + (double) ship.getCargoHatch().getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW));
            }
            ship.getUtilitySlots().stream()
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPassivePower())
                    .filter(slot -> slot.getShipModule().isPowered())
                    .forEach(slot -> powerValues.compute(slot.getShipModule().getPowerGroup(), (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW)));
            ship.getOptionalSlots().stream()
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .forEach(slot -> powerValues.compute(slot.getShipModule().getPowerGroup(), (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW)));
            ship.getCoreSlots().stream()
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .forEach(slot -> powerValues.compute(slot.getShipModule().getPowerGroup(), (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW)));
        });
        return powerValues;
    }

    private Map<Integer, Double> calculateDeployedPower() {
        Map<Integer, Double> powerValues = calculateRetractedPower();
        getShip().ifPresent(ship -> {
            ship.getHardpointSlots().stream()
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .forEach(slot -> powerValues.compute(
                            slot.getShipModule().getPowerGroup(),
                            (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW)
                    ));
            ship.getUtilitySlots().stream()
                    .filter(Slot::isOccupied)
                    .filter(slot -> !slot.getShipModule().isPassivePower())
                    .forEach(slot -> powerValues.compute(
                            slot.getShipModule().getPowerGroup(),
                            (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW)
                    ));
        });

        return powerValues;
    }


    @Override
    protected void update() {
        final Double retractedUsage = calculateRetractedPower().values().stream().skip(1).reduce(0D, Double::sum);
        final Double powerBudget = calculateRetractedPower().get(0);
        final Double deployedUsage = calculateDeployedPower().values().stream().skip(1).reduce(0D, Double::sum);
        this.retractedPower.textProperty().bind(LocaleService.getStringBinding("ship.stats.thermalpower.retractedPower.value", Formatters.NUMBER_FORMAT_2.format(retractedUsage), Formatters.NUMBER_FORMAT_2.format(powerBudget)));
        this.deployedPower.textProperty().bind(LocaleService.getStringBinding("ship.stats.thermalpower.deployedPower.value", Formatters.NUMBER_FORMAT_2.format(deployedUsage), Formatters.NUMBER_FORMAT_2.format(powerBudget)));
        if(retractedUsage > powerBudget){
            if(!this.retractedPower.getStyleClass().contains("power-stats-overpower"))
                this.retractedPower.getStyleClass().add("power-stats-overpower");
        }else{
            this.retractedPower.getStyleClass().removeAll("power-stats-overpower");
        }
        if(deployedUsage > powerBudget){
            if(!this.deployedPower.getStyleClass().contains("power-stats-overpower"))
                this.deployedPower.getStyleClass().add("power-stats-overpower");
        }else{
            this.deployedPower.getStyleClass().removeAll("power-stats-overpower");
        }
        this.retractedPowerBar.update(true);
        this.deployedPowerBar.update(false);
    }
}
