/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import static nl.jixxed.eliteodysseymaterials.helper.ShipMathHelper.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;

import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

@Slf4j
public class EngineStats extends Stats implements DestroyableTemplate {
    private RangeIndicator speedIndicator;
    private RangeIndicator boostIndicator;
    private RechargeRangeIndicator rechargeIndicator;
    private DestroyableLabel forwardAcceleration;
    private DestroyableLabel reverseAcceleration;
    private DestroyableLabel lateralAcceleration;

    public EngineStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("engine-stats");
        addTitle("ship.stats.engine");
        speedIndicator = new RangeIndicator(0D, 0D, 0D, "ship.stats.engine.speed", "ship.stats.engine.speed.value");
        boostIndicator = new RangeIndicator(0D, 0D, 0D, "ship.stats.engine.boost", "ship.stats.engine.boost.value");
        rechargeIndicator = new RechargeRangeIndicator(0D, 0D, 0D, 0D, "ship.stats.engine.recharge", "ship.stats.engine.recharge.value");

        this.getNodes().add(speedIndicator);
        this.getNodes().add(boostIndicator);
        this.getNodes().add(rechargeIndicator);
        this.getNodes().add(new GrowingRegion());
        this.forwardAcceleration = createValueLabel("ship.stats.engine.acceleration.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D));
        this.reverseAcceleration = createValueLabel("ship.stats.engine.acceleration.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D));
        this.lateralAcceleration = createValueLabel("ship.stats.engine.acceleration.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D));
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.engine.acceleration"))
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.engine.acceleration.forward"), new GrowingRegion(), this.forwardAcceleration)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.engine.acceleration.reverse"), new GrowingRegion(), this.reverseAcceleration)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.engine.acceleration.lateral"), new GrowingRegion(), this.lateralAcceleration)
                .buildHBox());

    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, ShipConfigEvent.class, event -> update()));
    }

    @Override
    protected void update() {

        getShip().ifPresent(ship -> {
            final double boostInterval = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.BOOST_INTERVAL, 0.0D);

            var minSpeed = calculateMinSpeed(ship);
            var currentSpeed = calculateCurrentSpeed(ship);
            var maxSpeed = calculateMaxSpeed(ship);
            var minBoost = calculateMinBoostSpeed(ship);
            var currentBoost = calculateCurrentBoostSpeed(ship);
            var maxBoost = calculateMaxBoostSpeed(ship);
            var minRecharge = calculateMinRechargeTime(ship);
            var currentRecharge = calculateCurrentRechargeTime(ship);
            var maxRecharge = calculateMaxRechargeTime(ship);

            this.speedIndicator.updateValues(minSpeed, currentSpeed, maxSpeed);
            this.boostIndicator.updateValues(minBoost, currentBoost, maxBoost);
            this.rechargeIndicator.updateValues(minRecharge, currentRecharge, maxRecharge, boostInterval);
            this.forwardAcceleration.addBinding(this.forwardAcceleration.textProperty(), LocaleService.getStringBinding("ship.stats.engine.acceleration.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(calculateForwardAcceleration(ship))));
            this.reverseAcceleration.addBinding(this.reverseAcceleration.textProperty(), LocaleService.getStringBinding("ship.stats.engine.acceleration.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(calculateReverseAcceleration(ship))));
            this.lateralAcceleration.addBinding(this.lateralAcceleration.textProperty(), LocaleService.getStringBinding("ship.stats.engine.acceleration.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(calculateLateralAcceleration(ship))));
        });
    }


}
