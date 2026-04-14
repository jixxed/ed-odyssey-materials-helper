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

import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.fssbodysignals.Message;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.fssbodysignals.Signal;
import nl.jixxed.eliteodysseymaterials.schemas.journal.FSSBodySignals.FSSBodySignals;
import nl.jixxed.eliteodysseymaterials.service.LocationService;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;

public class EDDNFSSBodySignalsMapper extends EDDNMapper {
    @SuppressWarnings("unchecked")
    public static Message mapToEDDN(final FSSBodySignals fssBodySignals, final Expansion expansion) {
        return new Message.MessageBuilder()
                .withTimestamp(fssBodySignals.getTimestamp())
                .withEvent(fssBodySignals.getEvent())
                .withStarPos(Optional.ofNullable(LocationService.getCurrentStarPos(fssBodySignals.getSystemAddress()))
                        .map(coordinates -> coordinates.stream()
                                .map(BigDecimal::new)
                                .collect(Collectors.toList()))
                        .orElse(null))
                .withStarSystem(LocationService.getCurrentStarSystemName(fssBodySignals.getSystemAddress()))
                .withSystemAddress(fssBodySignals.getSystemAddress())
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withBodyID(fssBodySignals.getBodyID())
                .withBodyName(fssBodySignals.getBodyName())
                .withSignals(fssBodySignals.getSignals().stream()
                        .map(signal -> new Signal.SignalBuilder()
                                .withType(signal.getType())
                                .withCount(signal.getCount())
                                .build())
                        .toList())
                .build();
    }
}
