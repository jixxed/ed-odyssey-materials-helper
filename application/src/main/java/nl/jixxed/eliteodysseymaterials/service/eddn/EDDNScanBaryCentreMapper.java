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
import nl.jixxed.eliteodysseymaterials.schemas.eddn.scanbarycentre.Message;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ScanBaryCentre.ScanBaryCentre;
import nl.jixxed.eliteodysseymaterials.service.LocationService;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;

public class EDDNScanBaryCentreMapper extends EDDNMapper {
    @SuppressWarnings("unchecked")
    public static Message mapToEDDN(final ScanBaryCentre scanBaryCentre, final Expansion expansion) {
        return new Message.MessageBuilder()
                .withTimestamp(scanBaryCentre.getTimestamp())
                .withEvent(scanBaryCentre.getEvent())
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withStarSystem(scanBaryCentre.getStarSystem())
                .withStarPos(Optional.ofNullable(LocationService.getCurrentStarPos(scanBaryCentre.getSystemAddress()))
                        .map(coordinates -> coordinates.stream()
                                .map(BigDecimal::new)
                                .collect(Collectors.toList()))
                        .orElse(null))
                .withSystemAddress(scanBaryCentre.getSystemAddress())
                .withBodyID(scanBaryCentre.getBodyID())
                .withSemiMajorAxis(scanBaryCentre.getSemiMajorAxis())
                .withEccentricity(scanBaryCentre.getEccentricity())
                .withOrbitalInclination(scanBaryCentre.getOrbitalInclination())
                .withPeriapsis(scanBaryCentre.getPeriapsis())
                .withOrbitalPeriod(scanBaryCentre.getOrbitalPeriod())
                .withAscendingNode(scanBaryCentre.getAscendingNode())
                .withMeanAnomaly(scanBaryCentre.getMeanAnomaly())
                .build();
    }
}
