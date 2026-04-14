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
import nl.jixxed.eliteodysseymaterials.schemas.eddn.fsssignaldiscovered.Message;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.fsssignaldiscovered.Signal;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Event;
import nl.jixxed.eliteodysseymaterials.schemas.journal.FSSSignalDiscovered.FSSSignalDiscovered;
import nl.jixxed.eliteodysseymaterials.service.LocationService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EDDNFSSSignalDiscoveredMapper extends EDDNMapper {
    @SuppressWarnings("unchecked")
    public static Message mapToEDDN(final List<FSSSignalDiscovered> fssSignalDiscoveredList, final Expansion expansion) {
        final List<Signal> signals = fssSignalDiscoveredList.stream()
                .filter(fssSignalDiscovered -> fssSignalDiscovered.getSystemAddress().equals(LocationService.getCurrentSystemAddress()))
                .map(fssSignalDiscovered -> new Signal.SignalBuilder()
                        .withTimestamp(fssSignalDiscovered.getTimestamp())
                        .withIsStation(fssSignalDiscovered.getIsStation().orElse(null))
                        .withSignalName(fssSignalDiscovered.getSignalName())
                        .withSignalType(fssSignalDiscovered.getSignalType().orElse(null))
                        .withSpawningFaction(fssSignalDiscovered.getSpawningFaction().orElse(null))
                        .withSpawningPower(fssSignalDiscovered.getSpawningPower().orElse(null))
                        .withOpposingPower(fssSignalDiscovered.getOpposingPower().orElse(null))
                        .withSpawningState(fssSignalDiscovered.getSpawningState().orElse(null))
                        .withThreatLevel(fssSignalDiscovered.getThreatLevel().orElse(null))
                        .withUSSType(fssSignalDiscovered.getUSSType().orElse(null))
                        .build())
                .toList();
        return new Message.MessageBuilder()
                .withTimestamp(signals.stream()
                        .findFirst()
                        .map(Signal::getTimestamp)
                        .orElse(null))
                .withEvent(fssSignalDiscoveredList.stream()
                        .findFirst()
                        .map(Event::getEvent)
                        .orElse(null))
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withStarPos(Optional.of(LocationService.getCurrentStarPos())
                        .map(coordinates -> coordinates.stream()
                                .map(BigDecimal::new)
                                .collect(Collectors.toList()))
                        .orElse(null))
                .withStarSystem(LocationService.getCurrentStarSystemName())
                .withSystemAddress(LocationService.getCurrentSystemAddress())
                .withSignals(signals)
                .build();
    }
}
