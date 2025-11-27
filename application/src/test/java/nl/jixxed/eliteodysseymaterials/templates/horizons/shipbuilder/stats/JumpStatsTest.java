package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import de.saxsys.mvvmfx.testingutils.JfxToolkitExtension;
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

@ExtendWith(MockitoExtension.class)
@ExtendWith(JfxToolkitExtension.class)
class JumpStatsTest {

    @Test
    void calculateJumpRangeMin() {
        ScalingHelper.init();
        ApplicationState.getInstance().setShip(Ship.ADDER);
        LocaleService.setCurrentLocale(Locale.ENGLISH);
        final JumpStats jumpStats = new JumpStats();
        Assertions.assertEquals(8.67, Precision.round(jumpStats.calculateJumpRangeMin(), 2));
    }

    @Test
    void calculateJumpRangeCurrent() {
        ScalingHelper.init();
        ApplicationState.getInstance().setShip(Ship.ADDER);
        LocaleService.setCurrentLocale(Locale.ENGLISH);
        final JumpStats jumpStats = new JumpStats();
        Assertions.assertEquals(9.31, Precision.round(jumpStats.calculateJumpRangeCurrent(), 2));
    }

    @Test
    void calculateJumpRangeMaxFueled() {
        ScalingHelper.init();
        ApplicationState.getInstance().setShip(Ship.ADDER);
        LocaleService.setCurrentLocale(Locale.ENGLISH);
        final JumpStats jumpStats = new JumpStats();
        Assertions.assertEquals(9.36, Precision.round(jumpStats.calculateJumpRangeMaxFueled(), 2));
    }

    @Test
    void calculateJumpRangeMax() {
        ScalingHelper.init();
        ApplicationState.getInstance().setShip(Ship.ADDER);
        LocaleService.setCurrentLocale(Locale.ENGLISH);
        final JumpStats jumpStats = new JumpStats();
        Assertions.assertEquals(10.28, Precision.round(jumpStats.calculateJumpRangeMax(), 2));
    }
}