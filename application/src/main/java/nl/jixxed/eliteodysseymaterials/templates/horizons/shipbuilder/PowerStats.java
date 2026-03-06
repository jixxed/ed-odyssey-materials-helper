package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Duration;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.StackPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.PowerProfile;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsShipSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.ShipBuilderEvent;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats.PowerBar;

import java.util.Arrays;
import java.util.Optional;

public class PowerStats extends DestroyableVBox implements DestroyableEventTemplate {

    private DestroyableLabel retractedPowerLabel;
    private DestroyableLabel deployedPowerLabel;
    private PowerBar retractedPowerBar;
    private PowerBar deployedPowerBar;
    private final DoubleProperty guardianBoost = new SimpleDoubleProperty(0.0);


    public PowerStats() {
        super();
        initComponents();
        initEventHandling();
    }

    protected static DestroyableLabel createLabel(final String localeKey, final String... values) {
        return LabelBuilder.builder()
                .withStyleClass("key")
                .withText(localeKey, Arrays.stream(values).toArray())
                .build();
    }

    protected DestroyableLabel createValueLabel(final String key, final String... values) {
        return LabelBuilder.builder()
                .withStyleClass("value")
                .withText(key, Arrays.stream(values).toArray())
                .build();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("power-stats");
//        this.getNodes().add(new GrowingRegion());
        this.retractedPowerLabel = createValueLabel("ship.stats.power.retracted.power.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D), Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D));
        this.deployedPowerLabel = createValueLabel("ship.stats.power.deployed.power.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D), Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D));
        this.retractedPowerBar = new PowerBar(true);
        this.deployedPowerBar = new PowerBar(false);
//        HBox.setHgrow(this.retractedPowerBar, Priority.ALWAYS);
//        HBox.setHgrow(this.deployedPowerBar, Priority.ALWAYS);
        DestroyableLabel retractedKey = createLabel("ship.stats.power.retracted.power");
        DestroyableLabel deployedKey = createLabel("ship.stats.power.deployed.power");
        var percentLabels = StackPaneBuilder.builder()
                .withNodes(
                        createPercentageLabel(0.2, "power.level.reason.destroyed.and.malfunctioning"),
                        createPercentageLabel(0.4, "power.level.reason.malfunctioning"),
                        createPercentageLabel(0.5, "power.level.reason.destroyed"),
                        createPercentageLabel(1.0, "power.level.reason.normal")
                )
                .build();
        percentLabels.setAlignment(Pos.BASELINE_LEFT);
        DestroyableVBox labels = BoxBuilder.builder()
                .withStyleClass("power-stats-labels")
                .withNodes(new GrowingRegion(), retractedKey,deployedKey)
                .buildVBox();
        DestroyableVBox bars = BoxBuilder.builder()
                .withStyleClass("power-stats-powerbars")
                .withNodes(percentLabels, retractedPowerBar, deployedPowerBar)
                .buildVBox();
        DestroyableVBox values = BoxBuilder.builder()
                .withStyleClass("power-stats-values")
                .withNodes(new GrowingRegion(), this.retractedPowerLabel, this.deployedPowerLabel)
                .buildVBox();
//        DestroyableHBox l1 = BoxBuilder.builder().withStyleClass("power-stats-line").withNodes(retractedKey, retractedPowerBar, this.retractedPowerLabel).buildHBox();
//        DestroyableHBox l2 = BoxBuilder.builder().withStyleClass("power-stats-line").withNodes(deployedKey, deployedPowerBar, this.deployedPowerLabel).buildHBox();
        this.retractedPowerLabel.setMinWidth(Label.USE_PREF_SIZE);
        this.deployedPowerLabel.setMinWidth(Label.USE_PREF_SIZE);
        retractedKey.setMinWidth(Label.USE_PREF_SIZE);
        deployedKey.setMinWidth(Label.USE_PREF_SIZE);
//        this.getNodes().addAll(l1,l2);
        HBox.setHgrow(bars, Priority.ALWAYS);
//        HBox.setHgrow(retractedPowerBar, Priority.ALWAYS);
//        HBox.setHgrow(deployedPowerBar, Priority.ALWAYS);
//        HBox.setHgrow(bars, Priority.ALWAYS);
//        HBox.setHgrow(values, Priority.ALWAYS);
//        bars.addBinding(bars.prefWidthProperty(), this.widthProperty().subtract(labels.widthProperty()).subtract(values.widthProperty()).subtract(ScalingHelper.getPixelDoubleBindingFromEm(1D)));
        this.getNodes().add(BoxBuilder.builder()
                .withStyleClass("power-stats-line")
                .withNodes(
                        labels,
                        bars,
                        values
                )
                .buildHBox());

        this.getNodes().addAll(BoxBuilder.builder()
                .withStyleClass("power-legends")
                .withNodes(new GrowingRegion(),
                        createLegend("P"),
//                        new GrowingRegion(),
                        createLegend("1"),
//                        new GrowingRegion(),
                        createLegend("2"),
//                        new GrowingRegion(),
                        createLegend("3"),
//                        new GrowingRegion(),
                        createLegend("4"),
//                        new GrowingRegion(),
                        createLegend("5")
                ).buildHBox());

    }

    private DestroyableLabel createPercentageLabel(Double percentage, String tooltipLocalizationKey) {

        DestroyableLabel label = LabelBuilder.builder().withNonLocalizedText(Formatters.NUMBER_FORMAT_1.format((percentage + (guardianBoost.getValue() * percentage)) * 100D) + "%").build();
        label.addBinding(label.translateXProperty(), Bindings.createDoubleBinding(() -> (guardianBoost.getValue() + 1D) * percentage / 1.1 * this.retractedPowerBar.widthProperty().get() - label.getWidth() / 2D, guardianBoost, this.retractedPowerBar.widthProperty(), label.widthProperty()));
        label.addBinding(label.textProperty(), Bindings.createStringBinding(() -> Formatters.NUMBER_FORMAT_1.format((percentage + (guardianBoost.getValue() * percentage)) * 100D) + "%", guardianBoost, this.retractedPowerBar.widthProperty()));
        final DestroyableTooltip percentageLabelTooltip = TooltipBuilder.builder()
                .withText(tooltipLocalizationKey)
                .withShowDelay(Duration.millis(100))
                .build();
        percentageLabelTooltip.install(label);
        return label;
    }

    private DestroyableHBox createLegend(String number) {
        final DestroyableCircle circle = new DestroyableCircle();
        circle.addBinding(circle.radiusProperty(), ScalingHelper.getPixelDoubleBindingFromEm(0.5D));
        circle.getStyleClass().add("power-group-" + number.toLowerCase());
        return BoxBuilder.builder()
                .withStyleClass("power-legend")
                .withNodes(
                        circle,
                        LabelBuilder.builder()
                                .withStyleClass("legend-text")
                                .withNonLocalizedText(number)
                                .build())
                .buildHBox();
    }


    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, ShipBuilderEvent.class, _ -> update()));
        register(EventService.addListener(true, this, HorizonsShipSelectedEvent.class, _ ->
                update()));
        register(EventService.addListener(true, this, ShipConfigEvent.class, event -> update()));
    }


    protected void update() {
        final PowerProfile retractedPower = calculateRetractedPower();
        final Double retractedUsage = retractedPower.usedPower();
        final Double powerBudget = retractedPower.getPowerCapacity();
        final Double deployedUsage = calculateDeployedPower().usedPower();
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

        this.guardianBoost.set(getPowerBoost());
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

    private PowerProfile calculateRetractedPower() {
        return getShip().map(Ship::getRetractedPower).orElseGet(PowerProfile::new);
    }

    private PowerProfile calculateDeployedPower() {
        return getShip().map(Ship::getDeployedPower).orElseGet(PowerProfile::new);
    }

    public Optional<Ship> getShip() {
        return Optional.ofNullable(ApplicationState.getInstance().getShip());
    }
}
