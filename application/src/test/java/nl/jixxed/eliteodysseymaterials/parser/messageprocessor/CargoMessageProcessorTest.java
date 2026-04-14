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