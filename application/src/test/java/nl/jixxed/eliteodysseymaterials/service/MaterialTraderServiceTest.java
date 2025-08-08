package nl.jixxed.eliteodysseymaterials.service;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.EngineerState;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsStorageType;
import nl.jixxed.eliteodysseymaterials.enums.MaterialTrader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaterialTraderServiceTest {

    @Test
    void findClosest() {
        ShipModule.getBasicModules();
        ApplicationState.getInstance().setEngineerState(Engineer.ELEANOR_BRESA, EngineerState.KNOWN);
        MaterialTrader closest = MaterialTraderService.findClosest(Engineer.ELEANOR_BRESA.getStarSystem(), HorizonsStorageType.RAW);
        Assertions.assertThat(closest.getName()).isEqualTo("TolaGarf's Junkyard");
        ApplicationState.getInstance().setEngineerState(Engineer.ELEANOR_BRESA, EngineerState.INVITED);
        closest = MaterialTraderService.findClosest(Engineer.ELEANOR_BRESA.getStarSystem(), HorizonsStorageType.RAW);
        Assertions.assertThat(closest.getName()).isEqualTo("Bresa Modifications");
    }
}