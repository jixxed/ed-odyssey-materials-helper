/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.parser;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Raw;
import nl.jixxed.eliteodysseymaterials.schemas.journal.MaterialTrade.MaterialTrade;
import nl.jixxed.eliteodysseymaterials.schemas.journal.MaterialTrade.Paid;
import nl.jixxed.eliteodysseymaterials.schemas.journal.MaterialTrade.Received;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

@Slf4j
public class RawTradeParser implements HorizonsParser<MaterialTrade> {

    @Override
    public void parse(final MaterialTrade event) {
        final Paid paid = event.getPaid();
        final String paidName = paid.getMaterial();
        final int paidAmount = paid.getQuantity().intValue();
        final Received received = event.getReceived();
        final String receivedName = received.getMaterial();
        final int receivedAmount = received.getQuantity().intValue();
        final Raw paidMaterial = Raw.forName(paidName);
        if (Raw.UNKNOWN.equals(paidMaterial)) {
            log.warn("Unknown Paid Raw data detected: " + event);
        } else {
            StorageService.removeMaterial(paidMaterial, paidAmount);
        }
        final Raw receivedMaterial = Raw.forName(receivedName);
        if (Raw.UNKNOWN.equals(receivedMaterial)) {
            log.warn("Unknown Received Raw data detected: " + event);
        } else {
            StorageService.addMaterial(receivedMaterial, receivedAmount);
        }
    }
}
