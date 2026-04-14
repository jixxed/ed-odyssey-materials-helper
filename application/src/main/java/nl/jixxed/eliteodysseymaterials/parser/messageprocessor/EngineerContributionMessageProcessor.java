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

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.schemas.journal.EngineerContribution.EngineerContribution;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

@Slf4j
public class EngineerContributionMessageProcessor implements MessageProcessor<EngineerContribution> {

    @Override
    public void process(final EngineerContribution event) {
        event.getMaterial().ifPresent(material -> {
            try {
                final HorizonsMaterial horizonsMaterial = HorizonsMaterial.subtypeForName(material);
                if (!horizonsMaterial.isUnknown()) {
                    if (horizonsMaterial instanceof Commodity commodity) {
                        StorageService.removeCommodity(commodity, StoragePool.SHIP, event.getQuantity().intValue());
                    } else {
                        StorageService.removeMaterial(horizonsMaterial, event.getQuantity().intValue());
                    }
                }
                EventService.publish(new StorageEvent(StoragePool.SHIP));
            } catch (final IllegalArgumentException e) {
                log.error(e.getMessage());
                //NotificationService.showWarning(NotificationType.ERROR, "Unknown Material Detected", material + "\nPlease report!");
            }
        });
    }

    @Override
    public Class<EngineerContribution> getMessageClass() {
        return EngineerContribution.class;
    }
}