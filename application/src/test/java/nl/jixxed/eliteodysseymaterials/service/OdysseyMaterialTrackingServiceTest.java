package nl.jixxed.eliteodysseymaterials.service;

import de.saxsys.mvvmfx.testingutils.JfxToolkitExtension;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.enums.GameMode;
import nl.jixxed.eliteodysseymaterials.enums.Operation;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ShipLocker.ShipLocker;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ExtendWith(JfxToolkitExtension.class)
class OdysseyMaterialTrackingServiceTest {
    @BeforeAll
    static void beforeAll() {
        final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
        EventService.publish(new LoadGameEvent(GameMode.SOLO, Expansion.ODYSSEY));
    }

    @BeforeEach
    void setUp() {
        MaterialTrackingService.initialize();
        EventService.publish(new JournalInitEvent(true));
    }

    @AfterEach
    void tearDown() {
//        MaterialTrackingService.close();//sends data!
    }

    @Test
    void testClear() {

        final BackpackChangeEvent event53 = BackpackChangeEvent.builder()
                .odysseyMaterial(Data.ESPIONAGEMATERIAL)
                .amount(5)
                .operation(Operation.ADDED)
                .timestamp("2021-12-15T19:04:53Z")
                .commander("COMMANDER")
                .system("SYSTEM")
                .body("BODY")
                .settlement("SETTLEMENT")
                .x(0.0)
                .y(0.0)
                .z(0.0)
                .build();
        final BackpackChangeEvent event54 = BackpackChangeEvent.builder()
                .odysseyMaterial(Data.COMBATTRAININGMATERIAL)
                .amount(5)
                .operation(Operation.ADDED)
                .timestamp("2021-12-15T19:04:54Z")
                .commander("COMMANDER")
                .system("SYSTEM")
                .body("BODY")
                .settlement("SETTLEMENT")
                .x(0.0)
                .y(0.0)
                .z(0.0)
                .build();
        final BackpackChangeEvent event55 = BackpackChangeEvent.builder()
                .odysseyMaterial(Data.EMPLOYEEEXPENSES)
                .amount(5)
                .operation(Operation.ADDED)
                .timestamp("2021-12-15T19:04:55Z")
                .commander("COMMANDER")
                .system("SYSTEM")
                .body("BODY")
                .settlement("SETTLEMENT")
                .x(0.0)
                .y(0.0)
                .z(0.0)
                .build();
        final BackpackChangeEvent event56 = BackpackChangeEvent.builder()
                .odysseyMaterial(Data.CAMPAIGNPLANS)
                .amount(5)
                .operation(Operation.ADDED)
                .timestamp("2021-12-15T19:04:56Z")
                .commander("COMMANDER")
                .system("SYSTEM")
                .body("BODY")
                .settlement("SETTLEMENT")
                .x(0.0)
                .y(0.0)
                .z(0.0)
                .build();
        final BackpackChangeEvent event57 = BackpackChangeEvent.builder()
                .odysseyMaterial(Data.CATMEDIA)
                .amount(5)
                .operation(Operation.ADDED)
                .timestamp("2021-12-15T19:04:57Z")
                .commander("COMMANDER")
                .system("SYSTEM")
                .body("BODY")
                .settlement("SETTLEMENT")
                .x(0.0)
                .y(0.0)
                .z(0.0)
                .build();
        final BackpackChangeEvent event58 = BackpackChangeEvent.builder()
                .odysseyMaterial(Data.BLACKLISTDATA)
                .amount(5)
                .operation(Operation.ADDED)
                .timestamp("2021-12-15T19:04:58Z")
                .commander("COMMANDER")
                .system("SYSTEM")
                .body("BODY")
                .settlement("SETTLEMENT")
                .x(0.0)
                .y(0.0)
                .z(0.0)
                .build();
        final BackpackChangeEvent event59 = BackpackChangeEvent.builder()
                .odysseyMaterial(Data.AIRQUALITYREPORTS)
                .amount(5)
                .operation(Operation.ADDED)
                .timestamp("2021-12-15T19:04:59Z")
                .commander("COMMANDER")
                .system("SYSTEM")
                .body("BODY")
                .settlement("SETTLEMENT")
                .x(0.0)
                .y(0.0)
                .z(0.0)
                .build();


        EventService.publish(event53);
        EventService.publish(event54);
        EventService.publish(event55);
        EventService.publish(event56);
        EventService.publish(event57);
        EventService.publish(event58);
        EventService.publish(event59);
        assertThat(MaterialTrackingService.BACKPACK_CHANGE_EVENTS).containsExactlyInAnyOrder(event53, event54, event55, event56, event57, event58, event59);
        EventService.publish(new ShipLockerEvent((ShipLocker) (new ShipLocker.ShipLockerBuilder()).withTimestamp(LocalDateTime.of(2021,12,15,19,4,56)).build()));// should remove -2s - +2s -> 54-58
        assertThat(MaterialTrackingService.BACKPACK_CHANGE_EVENTS).containsExactlyInAnyOrder(event53, event59);
    }
}