package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import de.saxsys.mvvmfx.testingutils.JfxToolkitExtension;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import org.apache.commons.math3.util.Precision;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Locale;

@Slf4j
@ExtendWith(MockitoExtension.class)
@ExtendWith(JfxToolkitExtension.class)
class JumpStatsTest {

    @Test
    void calculateJumpRangeMinOutfitting() {
        ScalingHelper.init();
        ApplicationState.getInstance().setShip(Ship.ADDER);
        LocaleService.setCurrentLocale(Locale.ENGLISH);
        final JumpStats jumpStats = new JumpStats();
        Assertions.assertEquals(8.67, Precision.round(jumpStats.calculateJumpRangeMinOutfitting(), 2));
    }

    @Test
    void calculateJumpRangeCurrentOutfitting() {
        ScalingHelper.init();
        ApplicationState.getInstance().setShip(Ship.ADDER);
        LocaleService.setCurrentLocale(Locale.ENGLISH);
        final JumpStats jumpStats = new JumpStats();
        Assertions.assertEquals(9.31, Precision.round(jumpStats.calculateJumpRangeCurrentOutfitting(), 2));
    }

    @Test
    void calculateJumpRangeMaxOutfittingFueled() {
        ScalingHelper.init();
        ApplicationState.getInstance().setShip(Ship.ADDER);
        LocaleService.setCurrentLocale(Locale.ENGLISH);
        final JumpStats jumpStats = new JumpStats();
        Assertions.assertEquals(9.36, Precision.round(jumpStats.calculateJumpRangeMaxFueled(), 2));
    }

    @Test
    void calculateJumpRangeMaxOutfitting() {
        ScalingHelper.init();
        ApplicationState.getInstance().setShip(Ship.ADDER);
        LocaleService.setCurrentLocale(Locale.ENGLISH);
        final JumpStats jumpStats = new JumpStats();
        Assertions.assertEquals(10.28, Precision.round(jumpStats.calculateJumpRangeMaxOutfitting(), 2));
    }

    @Test
    void calculateJumpDistance() {
        ScalingHelper.init();
        ApplicationState.getInstance().setShip(Ship.ADDER);
        LocaleService.setCurrentLocale(Locale.ENGLISH);
//        calc();
//        I have this formula: y=Math.pow(fuel / fuelMultiplier, 1 / fuelPower) * optimisedMass / mass;
//
//        with the following values for fuel, optimisedMass and mass find fuelMultiplier and fuelPower
//
//        y, fuel, optimisedMass, mass
//        40.67, 2, 7238.5, 1704.44
//        49.47, 4, 7238.5, 1706.44
//        55.19, 6.8, 7238.5, 1710.44
        final JumpStats jumpStats = new JumpStats();
//        final double mass, final double fuel, final double optimisedMass,
        double fuelMultiplier = 0.0110;//69;//682;
        double fuelPower = 2.5025;
//        final double jumpRangeIncrease
        /*Assertions.assertAll(
                () -> Assertions.assertEquals(40.67, Precision.round(jumpStats.calculateJumpDistance(1704.44, 2.0, 7238.5, fuelMultiplier, fuelPower, 0), 2)),
                () -> Assertions.assertEquals(49.47, Precision.round(jumpStats.calculateJumpDistance(1706.44, 4.0, 7238.5, fuelMultiplier, fuelPower, 0), 2)),
                () -> Assertions.assertEquals(55.19, Precision.round(jumpStats.calculateJumpDistance(1710.44, 6.8, 7238.5, fuelMultiplier, fuelPower, 0), 2))
        );*/
        double mass = 1701.3;//ship.getEmptyMass() + ship.getCurrentCargo() + ship.getCurrentFuel() + ship.getCurrentFuelReserve()
        double fuelReserve = 1.14;
        double fuel = 8.0;
        double fsdMaxFuelUse = 6.8;
        double maxCargo = 96.0;
        double maxFuelCap = 8.0;
        double rangeMin = 0.0;
        double rangeCur = 0.0;
        double rangeMax = 0.0;
        double optimisedMass = 7238.5;
        log.info("Mk2");
        rangeMin = Precision.round(jumpStats.calculateJumpDistance(mass + maxFuelCap + maxCargo, fsdMaxFuelUse, optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeCur = Precision.round(jumpStats.calculateJumpDistance(mass + fuel + fuelReserve, Math.min(fuel + fuelReserve, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeMax = Precision.round(jumpStats.calculateJumpDistance(mass + fsdMaxFuelUse, fsdMaxFuelUse, optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        log.info("ranges = {}/{}/{}", rangeMin, rangeCur, rangeMax);
        fuel = 4.0;
        maxFuelCap = 4.0;
        rangeMin = Precision.round(jumpStats.calculateJumpDistance(mass + maxFuelCap + maxCargo, fsdMaxFuelUse, optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeCur = Precision.round(jumpStats.calculateJumpDistance(mass + fuel + fuelReserve, Math.min(fuel + fuelReserve, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeMax = Precision.round(jumpStats.calculateJumpDistance(mass + fsdMaxFuelUse, fsdMaxFuelUse, optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        log.info("ranges = {}/{}/{}", rangeMin, rangeCur, rangeMax);
        fuel = 2.0;
        maxFuelCap = 2.0;
        rangeMin = Precision.round(jumpStats.calculateJumpDistance(mass + maxFuelCap + maxCargo, fsdMaxFuelUse, optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeCur = Precision.round(jumpStats.calculateJumpDistance(mass + fuel + fuelReserve, Math.min(fuel + fuelReserve, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeMax = Precision.round(jumpStats.calculateJumpDistance(mass + fsdMaxFuelUse, fsdMaxFuelUse, optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        log.info("ranges = {}/{}/{}", rangeMin, rangeCur, rangeMax);
        //Mk2 8A FSD
        //outfitting / rh panel
        //3C 52.29/55.19/55.27
        //2C 52.41/49.47/55.27
        //1C 52.47/40.67/55.27

        //maintenance
        //3C 52.29/55.23/55.23
        //2C 42.39/44.78/44.78
        //1C 32.17/33.99/33.99

        double curCargo = maxCargo;

        fuel = 8.0;
        maxFuelCap = 8.0;
        rangeMin = Precision.round(jumpStats.calculateJumpDistance(mass + fuel + maxCargo, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeCur = Precision.round(jumpStats.calculateJumpDistance(mass + fuel + curCargo, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeMax = Precision.round(jumpStats.calculateJumpDistance(mass + fuel, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        log.info("ranges = {}/{}/{}", rangeMin, rangeCur, rangeMax);
        fuel = 4.0;
        maxFuelCap = 8.0;
        rangeMin = Precision.round(jumpStats.calculateJumpDistance(mass + fuel + maxCargo, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeCur = Precision.round(jumpStats.calculateJumpDistance(mass + fuel + curCargo, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeMax = Precision.round(jumpStats.calculateJumpDistance(mass + fuel, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        log.info("ranges = {}/{}/{}", rangeMin, rangeCur, rangeMax);
        fuel = 2.0;
        maxFuelCap = 8.0;
        rangeMin = Precision.round(jumpStats.calculateJumpDistance(mass + fuel + maxCargo, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeCur = Precision.round(jumpStats.calculateJumpDistance(mass + fuel + curCargo, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeMax = Precision.round(jumpStats.calculateJumpDistance(mass + fuel, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        log.info("ranges = {}/{}/{}", rangeMin, rangeCur, rangeMax);


//        Map.entry(HorizonsModifier.MASS, 160.0),
//        Map.entry(HorizonsModifier.FSD_OPTIMISED_MASS, 2800.0),
//        Map.entry(HorizonsModifier.MAX_FUEL_PER_JUMP, 13.6),
//        Map.entry(HorizonsModifier.FUEL_MULTIPLIER, 0.008),
//        Map.entry(HorizonsModifier.FUEL_POWER, 2.9),
        //8e
        // 208 -> 160
        // fsdMaxFuelUse 13.6
        //
        //Reg 8A FSD
        //min/max do not change with actual fuel level
        //outfitting / rh panel
        //1C 20.78/13.26/21.84
        //2C 20.76/15.69/21.84
        //3C 20.71/19.09/21.84
        //4C 20.62/21.79/21.84

//        "UnladenMass": 1701.300049,
//        "UnladenMass": 1653.300049,
//                "CargoCapacity": 96,
//                "MaxJumpRange": 21.837168,
//                "FuelCapacity": {
//            "Main": 16.0,
//                    "Reserve": 1.14
        //maintenance
        //1C 10.73/11.35/11.35
        //2C 13.61/14.40/14.40
        //3C 17.25/18.25/18.25
        //4C 20.62/21.81/21.81
//mass 1670.4 /2160
        fuelMultiplier = 0.008;
        fuelPower = 2.9;
        mass = 1653.300049;//ship.getEmptyMass() + ship.getCurrentCargo() + ship.getCurrentFuel() + ship.getCurrentFuelReserve()
        fuelReserve = 1.14;
        fuel = 16.0;
        maxFuelCap = 16.0;
        fsdMaxFuelUse = 13.6;
        maxCargo = 96.0;
        optimisedMass = 2800.0;
        //4C 20.62/21.79/21.84
        log.info("Mk1 outfitting");
        rangeMin = Precision.round(jumpStats.calculateJumpDistance(mass + maxFuelCap + maxCargo, fsdMaxFuelUse, optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeCur = Precision.round(jumpStats.calculateJumpDistance(mass + fuel + fuelReserve + curCargo, Math.min(fuel + fuelReserve, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeMax = Precision.round(jumpStats.calculateJumpDistance(mass + fsdMaxFuelUse, fsdMaxFuelUse, optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        log.info("ranges = {}/{}/{}", rangeMin, rangeCur, rangeMax);
        fuel = 8.0;
        maxFuelCap = 8.0;
        //3C 20.71/19.09/21.84
        rangeMin = Precision.round(jumpStats.calculateJumpDistance(mass + maxFuelCap + maxCargo, fsdMaxFuelUse, optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeCur = Precision.round(jumpStats.calculateJumpDistance(mass + fuel + fuelReserve + curCargo, Math.min(fuel + fuelReserve, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeMax = Precision.round(jumpStats.calculateJumpDistance(mass + fsdMaxFuelUse, fsdMaxFuelUse, optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        log.info("ranges = {}/{}/{}", rangeMin, rangeCur, rangeMax);
        fuel = 4.0;
        maxFuelCap = 4.0;
        //2C 20.76/15.69/21.84
        rangeMin = Precision.round(jumpStats.calculateJumpDistance(mass + maxFuelCap + maxCargo, fsdMaxFuelUse, optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeCur = Precision.round(jumpStats.calculateJumpDistance(mass + fuel + fuelReserve + curCargo, Math.min(fuel + fuelReserve, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeMax = Precision.round(jumpStats.calculateJumpDistance(mass + fsdMaxFuelUse, fsdMaxFuelUse, optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        log.info("ranges = {}/{}/{}", rangeMin, rangeCur, rangeMax);
        fuel = 2.0;
        maxFuelCap = 2.0;
        //1C 20.78/13.26/21.84
        rangeMin = Precision.round(jumpStats.calculateJumpDistance(mass + maxFuelCap + maxCargo, fsdMaxFuelUse, optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeCur = Precision.round(jumpStats.calculateJumpDistance(mass + fuel + fuelReserve + curCargo, Math.min(fuel + fuelReserve, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeMax = Precision.round(jumpStats.calculateJumpDistance(mass + fsdMaxFuelUse, fsdMaxFuelUse, optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        log.info("ranges = {}/{}/{}", rangeMin, rangeCur, rangeMax);



        //1C 10.73/11.35/11.35
        //2C 13.61/14.40/14.40
        //3C 17.25/18.25/18.25
        fuel = 16.0;
        maxFuelCap = 16.0;
        //4C 20.62/21.81/21.81
        log.info("Mk1 maintenacne");
        rangeMin = Precision.round(jumpStats.calculateJumpDistance(mass + fuel + maxCargo, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeCur = Precision.round(jumpStats.calculateJumpDistance(mass + fuel + curCargo, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeMax = Precision.round(jumpStats.calculateJumpDistance(mass + fuel, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        log.info("ranges = {}/{}/{}", rangeMin, rangeCur, rangeMax);
        fuel = 8.0;
        maxFuelCap = 8.0;
        //3C 17.25/18.25/18.25
        rangeMin = Precision.round(jumpStats.calculateJumpDistance(mass + fuel + maxCargo, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeCur = Precision.round(jumpStats.calculateJumpDistance(mass + fuel + curCargo, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeMax = Precision.round(jumpStats.calculateJumpDistance(mass + fuel, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        log.info("ranges = {}/{}/{}", rangeMin, rangeCur, rangeMax);
        fuel = 8.0;
        maxFuelCap = 16.0;
        //4C half fuel 17.25/18.25/18.25
        rangeMin = Precision.round(jumpStats.calculateJumpDistance(mass + fuel + maxCargo, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeCur = Precision.round(jumpStats.calculateJumpDistance(mass + fuel + curCargo, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeMax = Precision.round(jumpStats.calculateJumpDistance(mass + fuel, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        log.info("ranges = {}/{}/{}", rangeMin, rangeCur, rangeMax);
        fuel = 4.0;
        maxFuelCap = 4.0;
        //2C 13.61/14.40/14.40
        rangeMin = Precision.round(jumpStats.calculateJumpDistance(mass + fuel + maxCargo, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeCur = Precision.round(jumpStats.calculateJumpDistance(mass + fuel + curCargo, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeMax = Precision.round(jumpStats.calculateJumpDistance(mass + fuel, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        log.info("ranges = {}/{}/{}", rangeMin, rangeCur, rangeMax);
        fuel = 2.0;
        maxFuelCap = 2.0;
        //1C 10.73/11.35/11.35
        rangeMin = Precision.round(jumpStats.calculateJumpDistance(mass + fuel + maxCargo, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeCur = Precision.round(jumpStats.calculateJumpDistance(mass + fuel + curCargo, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        rangeMax = Precision.round(jumpStats.calculateJumpDistance(mass + fuel, Math.min(fuel, fsdMaxFuelUse), optimisedMass, fuelMultiplier, fuelPower, 0), 2);
        log.info("ranges = {}/{}/{}", rangeMin, rangeCur, rangeMax);

    }

}