package nl.jixxed.eliteodysseymaterials.service.eddn;

import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.fssdiscoveryscan.Message;
import nl.jixxed.eliteodysseymaterials.schemas.journal.FSSDiscoveryScan.FSSDiscoveryScan;
import nl.jixxed.eliteodysseymaterials.service.LocationService;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;

public class EDDNFSSDiscoveryScanMapper extends EDDNMapper {
    @SuppressWarnings("unchecked")
    public static Message mapToEDDN(final FSSDiscoveryScan fssDiscoveryScan, final Expansion expansion) {
        return new Message.MessageBuilder()
                .withTimestamp(fssDiscoveryScan.getTimestamp())
                .withEvent(fssDiscoveryScan.getEvent())
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withStarPos(Optional.ofNullable(LocationService.getCurrentStarPos(fssDiscoveryScan.getSystemAddress()))
                        .map(coordinates -> coordinates.stream()
                                .map(BigDecimal::new)
                                .collect(Collectors.toList()))
                        .orElse(null))
                .withSystemAddress(fssDiscoveryScan.getSystemAddress())
                .withSystemName(fssDiscoveryScan.getSystemName())
                .withBodyCount(fssDiscoveryScan.getBodyCount())
                .withNonBodyCount(fssDiscoveryScan.getNonBodyCount())
                .build();
    }
}
