package nl.jixxed.eliteodysseymaterials.service.eddn;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.codexentry.Message;
import nl.jixxed.eliteodysseymaterials.schemas.journal.CodexEntry.CodexEntry;
import nl.jixxed.eliteodysseymaterials.service.LocationService;

import java.math.BigInteger;
import java.util.Objects;

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
                .withStarPos(LocationService.getCurrentStarPos(codexEntry.getSystemAddress()))
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
