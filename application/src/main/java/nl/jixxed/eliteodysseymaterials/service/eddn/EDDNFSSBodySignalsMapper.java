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
