package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PowerStats extends Stats implements DestroyableEventTemplate {
    private DestroyableLabel retractedPowerLabel;
    private DestroyableLabel deployedPowerLabel;
    private PowerBar retractedPowerBar;
    private PowerBar deployedPowerBar;
    public static final Color POWER_GROUP_P_COLOR = Color.web("#32A4A8");
    public static final Color POWER_GROUP_1_COLOR = Color.web("#CE6C1E");
    public static final Color POWER_GROUP_2_COLOR = Color.web("#89D07F");
    public static final Color POWER_GROUP_3_COLOR = Color.web("#2E92DF");
    public static final Color POWER_GROUP_4_COLOR = Color.web("#6D3DA8");
    public static final Color POWER_GROUP_5_COLOR = Color.web("#F8FF2E");

    public PowerStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.retractedPowerLabel = createValueLabel("ship.stats.power.retracted.power.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D), Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D));
        this.deployedPowerLabel = createValueLabel("ship.stats.power.deployed.power.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D), Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D));
        this.retractedPowerBar = new PowerBar(true);
        this.deployedPowerBar = new PowerBar(false);

        this.getNodes().add(BoxBuilder.builder()
                .withNodes(new GrowingRegion(), createTitle("ship.stats.power"), new GrowingRegion())
                .buildHBox());
        this.getNodes().add(new DestroyableSeparator(Orientation.HORIZONTAL));
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.power.retracted.power"), new GrowingRegion(), this.retractedPowerLabel)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withStyleClass("power-stats-powerbars")
                .withNodes(retractedPowerBar, deployedPowerBar).buildVBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.power.deployed.power"), new GrowingRegion(), this.deployedPowerLabel)
                .buildHBox());
        this.getNodes().add(new GrowingRegion());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(
                        createLegend(POWER_GROUP_P_COLOR, "P"),
                        new GrowingRegion(),
                        createLegend(POWER_GROUP_1_COLOR, "1"),
                        new GrowingRegion(),
                        createLegend(POWER_GROUP_2_COLOR, "2"),
                        new GrowingRegion(),
                        createLegend(POWER_GROUP_3_COLOR, "3"),
                        new GrowingRegion(),
                        createLegend(POWER_GROUP_4_COLOR, "4"),
                        new GrowingRegion(),
                        createLegend(POWER_GROUP_5_COLOR, "5")
                ).buildHBox());

    }

    private DestroyableHBox createLegend(Color powerGroupColor, String number) {
        final DestroyableCircle circle = new DestroyableCircle();
        circle.addBinding(circle.radiusProperty(), ScalingHelper.getPixelDoubleBindingFromEm(0.5D));
        circle.setFill(powerGroupColor);
        return BoxBuilder.builder()
                .withStyleClass("power-stats-legend-label")
                .withNodes(
                        circle,
                        LabelBuilder.builder()
                                .withStyleClass("power-stats-legend-label-text")
                                .withNonLocalizedText(number)
                                .build())
                .buildHBox();
    }


    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, ShipConfigEvent.class, event -> update()));
    }

    private Map<Integer, Double> calculateRetractedPower() {
        return getShip().map(Ship::getRetractedPower).orElseGet(() -> new HashMap<>(Map.of(
                -1, 0D,
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
                -1, 0D,
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
        final Double retractedUsage = retractedPower.values().stream().skip(2).reduce(0D, Double::sum);
        final Double powerBudget = retractedPower.get(0);
        final Double deployedUsage = calculateDeployedPower().values().stream().skip(2).reduce(0D, Double::sum);
        this.retractedPowerLabel.addBinding(this.retractedPowerLabel.textProperty(), LocaleService.getStringBinding("ship.stats.power.retracted.power.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(retractedUsage), Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(powerBudget)));
        this.deployedPowerLabel.addBinding(this.deployedPowerLabel.textProperty(), LocaleService.getStringBinding("ship.stats.power.deployed.power.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(deployedUsage), Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(powerBudget)));
        if (retractedUsage > powerBudget) {
            if (!this.retractedPowerLabel.getStyleClass().contains("power-stats-overpower"))
                this.retractedPowerLabel.getStyleClass().add("power-stats-overpower");
        } else {
            this.retractedPowerLabel.getStyleClass().removeAll("power-stats-overpower");
        }
        if (deployedUsage > powerBudget) {
            if (!this.deployedPowerLabel.getStyleClass().contains("power-stats-overpower"))
                this.deployedPowerLabel.getStyleClass().add("power-stats-overpower");
        } else {
            this.deployedPowerLabel.getStyleClass().removeAll("power-stats-overpower");
        }
        this.retractedPowerBar.update();
        this.deployedPowerBar.update();
    }
}
