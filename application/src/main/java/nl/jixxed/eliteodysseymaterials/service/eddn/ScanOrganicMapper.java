/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service.eddn;

import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.scanorganic.Message;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ScanOrganic.ScanOrganic;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalInitEvent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ScanOrganicMapper extends EDDNMapper {

    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();
    private static boolean isInitialized = false;

    public static void init() {
        EVENT_LISTENERS.add(EventService.addStaticListener(JournalInitEvent.class, journalInitEvent -> {
            isInitialized = journalInitEvent.isInitialised();
        }));
    }

    public static Message mapToEDDN(final ScanOrganic scanOrganic, final Expansion expansion) {
        StarSystem currentStarSystem = LocationService.getCurrentStarSystem();
        Message.MessageBuilderBase<Message> messageBuilder = new Message.MessageBuilder()
                .withEvent(scanOrganic.getEvent())
                .withTimestamp(scanOrganic.getTimestamp())
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withStarSystem(currentStarSystem.getName())//enrichment
                .withStarPos(List.of(BigDecimal.valueOf(currentStarSystem.getX()), BigDecimal.valueOf(currentStarSystem.getY()), BigDecimal.valueOf(currentStarSystem.getZ())))//enrichment
                .withScanType(scanOrganic.getScanType())
                .withGenus(scanOrganic.getGenus())
                .withSpecies(scanOrganic.getSpecies())
                .withVariant(scanOrganic.getVariant().orElse(null))
                .withSystemAddress(scanOrganic.getSystemAddress())
                .withBodyID(scanOrganic.getBody());
        if (isInitialized) {
            messageBuilder = messageBuilder.withBodyName(LocationService.getStatusBodyName().orElse(null))//enrichment
                    .withLatitude(LocationService.getStatusLatitude())//enrichment
                    .withLongitude(LocationService.getStatusLongitude());//enrichment
        }
        return messageBuilder.build();
    }
}
