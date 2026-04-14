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

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.codexentry.Message;
import nl.jixxed.eliteodysseymaterials.schemas.journal.CodexEntry.CodexEntry;
import nl.jixxed.eliteodysseymaterials.service.LocationService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EDDNCodexEntryMapper extends EDDNMapper {

    @SuppressWarnings("unchecked")
    public static Message mapToEDDN(final CodexEntry codexEntry, final Expansion expansion) {
        final String bodyName = LocationService.getStatusBodyName().orElse(null);
        final nl.jixxed.eliteodysseymaterials.domain.Location currentLocation = LocationService.getCurrentLocation();
        final Long bodyID = LocationService.getStatusBodyName().map(statusBodyName -> (statusBodyName.equals(currentLocation.getBody())) ? currentLocation.getBodyID() : null).orElse(null);
        return new nl.jixxed.eliteodysseymaterials.schemas.eddn.codexentry.Message.MessageBuilder()
                .withTimestamp(codexEntry.getTimestamp())
                .withEvent(codexEntry.getEvent())
                .withEntryID(codexEntry.getEntryID())
                .withSystem(codexEntry.getSystem())
                .withSystemAddress(codexEntry.getSystemAddress())
                .withStarPos(Optional.ofNullable(LocationService.getCurrentStarPos(codexEntry.getSystemAddress()))
                        .map(coordinates -> coordinates.stream()
                                .map(BigDecimal::new)
                                .collect(Collectors.toList()))
                        .orElse(null))
                .withBodyID(codexEntry.getBodyID().orElseGet(() -> bodyID != null ? BigInteger.valueOf(bodyID) : null))
                .withBodyName(bodyName)
                .withCategory(codexEntry.getCategory())
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withLatitude(codexEntry.getLatitude().orElse(null))
                .withLongitude(codexEntry.getLongitude().orElse(null))
                .withName(codexEntry.getName())
                .withNearestDestination(codexEntry.getNearestDestination().orElse(null))
                .withRegion(codexEntry.getRegion())
                .withSubCategory(codexEntry.getSubCategory())
                .withTraits(mapToOptionalEmptyIfEmptyList(codexEntry.getTraits()).map(traits -> traits.stream().map(EDDNMapper::nullIfBlank).filter(Objects::nonNull).toList()).orElse(null))
                .withVoucherAmount(codexEntry.getVoucherAmount().orElse(null))
                .build();
    }
}
