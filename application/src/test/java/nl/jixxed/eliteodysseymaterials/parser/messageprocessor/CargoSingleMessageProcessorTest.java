/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.parser.messageprocessor.journal.CargoSingleMessageProcessor;
import nl.jixxed.eliteodysseymaterials.parser.messageprocessor.journal.CargoTransferSingleMessageProcessor;
import nl.jixxed.eliteodysseymaterials.parser.messageprocessor.journal.EjectCargoSingleMessageProcessor;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CargoSingleMessageProcessorTest {

    @Test
    void isBankTransfer() {
        LocalDateTime now = LocalDateTime.now();

        CargoTransferSingleMessageProcessor.lastCargoTransfer = now.minusSeconds(3);
        EjectCargoSingleMessageProcessor.lastEjectCargo = now.minusSeconds(10);
        assertFalse(CargoSingleMessageProcessor.isBankTransfer(now));

        CargoTransferSingleMessageProcessor.lastCargoTransfer = now.minusSeconds(10);
        EjectCargoSingleMessageProcessor.lastEjectCargo = now.minusSeconds(3);
        assertFalse(CargoSingleMessageProcessor.isBankTransfer(now));

        CargoTransferSingleMessageProcessor.lastCargoTransfer = now.minusSeconds(10);
        EjectCargoSingleMessageProcessor.lastEjectCargo = now.minusSeconds(10);
        assertTrue(CargoSingleMessageProcessor.isBankTransfer(now));

        CargoTransferSingleMessageProcessor.lastCargoTransfer = null;
        EjectCargoSingleMessageProcessor.lastEjectCargo = now.minusSeconds(10);
        assertTrue(CargoSingleMessageProcessor.isBankTransfer(now));

        CargoTransferSingleMessageProcessor.lastCargoTransfer = now.minusSeconds(10);
        EjectCargoSingleMessageProcessor.lastEjectCargo = null;
        assertTrue(CargoSingleMessageProcessor.isBankTransfer(now));

        CargoTransferSingleMessageProcessor.lastCargoTransfer = null;
        EjectCargoSingleMessageProcessor.lastEjectCargo = null;
        assertTrue(CargoSingleMessageProcessor.isBankTransfer(now));

        CargoTransferSingleMessageProcessor.lastCargoTransfer = null;
        EjectCargoSingleMessageProcessor.lastEjectCargo = now.minusSeconds(2);
        assertFalse(CargoSingleMessageProcessor.isBankTransfer(now));

        CargoTransferSingleMessageProcessor.lastCargoTransfer = now.minusSeconds(2);
        EjectCargoSingleMessageProcessor.lastEjectCargo = null;
        assertFalse(CargoSingleMessageProcessor.isBankTransfer(now));
    }
}