package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CargoMessageProcessorTest {

    @Test
    void isBankTransfer() {
        LocalDateTime now = LocalDateTime.now();

        CargoTransferMessageProcessor.lastCargoTransfer = now.minusSeconds(3);
        EjectCargoMessageProcessor.lastEjectCargo = now.minusSeconds(10);
        assertFalse(CargoMessageProcessor.isBankTransfer(now));

        CargoTransferMessageProcessor.lastCargoTransfer = now.minusSeconds(10);
        EjectCargoMessageProcessor.lastEjectCargo = now.minusSeconds(3);
        assertFalse(CargoMessageProcessor.isBankTransfer(now));

        CargoTransferMessageProcessor.lastCargoTransfer = now.minusSeconds(10);
        EjectCargoMessageProcessor.lastEjectCargo = now.minusSeconds(10);
        assertTrue(CargoMessageProcessor.isBankTransfer(now));

        CargoTransferMessageProcessor.lastCargoTransfer = null;
        EjectCargoMessageProcessor.lastEjectCargo = now.minusSeconds(10);
        assertTrue(CargoMessageProcessor.isBankTransfer(now));

        CargoTransferMessageProcessor.lastCargoTransfer = now.minusSeconds(10);
        EjectCargoMessageProcessor.lastEjectCargo = null;
        assertTrue(CargoMessageProcessor.isBankTransfer(now));

        CargoTransferMessageProcessor.lastCargoTransfer = null;
        EjectCargoMessageProcessor.lastEjectCargo = null;
        assertTrue(CargoMessageProcessor.isBankTransfer(now));

        CargoTransferMessageProcessor.lastCargoTransfer = null;
        EjectCargoMessageProcessor.lastEjectCargo = now.minusSeconds(2);
        assertFalse(CargoMessageProcessor.isBankTransfer(now));

        CargoTransferMessageProcessor.lastCargoTransfer = now.minusSeconds(2);
        EjectCargoMessageProcessor.lastEjectCargo = null;
        assertFalse(CargoMessageProcessor.isBankTransfer(now));
    }
}