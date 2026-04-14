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
import nl.jixxed.eliteodysseymaterials.schemas.eddn.saasignalsfound.Genuse;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.saasignalsfound.Message;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.saasignalsfound.Signal;
import nl.jixxed.eliteodysseymaterials.schemas.journal.SAASignalsFound.SAASignalsFound;
import nl.jixxed.eliteodysseymaterials.service.LocationService;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;

public class EDDNSAASignalsFoundMapper extends EDDNMapper {
    @SuppressWarnings("unchecked")
    public static Message mapToEDDN(final SAASignalsFound saaSignalsFound, final Expansion expansion) {
        return new Message.MessageBuilder()
                .withTimestamp(saaSignalsFound.getTimestamp())
                .withEvent(saaSignalsFound.getEvent())
                .withStarPos(Optional.ofNullable(LocationService.getCurrentStarPos(saaSignalsFound.getSystemAddress()))
                        .map(coordinates -> coordinates.stream()
                                .map(BigDecimal::new)
                                .collect(Collectors.toList()))
                        .orElse(null))
                .withStarSystem(LocationService.getCurrentStarSystemName(saaSignalsFound.getSystemAddress()))
                .withSystemAddress(saaSignalsFound.getSystemAddress())
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withBodyID(saaSignalsFound.getBodyID())
                .withBodyName(saaSignalsFound.getBodyName())
                .withGenuses(mapToOptionalEmptyIfEmptyList(saaSignalsFound.getGenuses())
                        .map(genuses -> genuses.stream()
                                .map(genuse -> new Genuse.GenuseBuilder()
                                        .withGenus(genuse.getGenus())
                                        .build())
                                .toList())
                        .orElse(null))
                .withSignals(saaSignalsFound.getSignals().stream()
                        .map(signal -> new Signal.SignalBuilder()
                                .withType(signal.getType())
                                .withCount(signal.getCount())
                                .build())
                        .toList())
                .build();
    }
}
