package nl.jixxed.eliteodysseymaterials.service.eddn;

import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.saasignalsfound.Genuse;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.saasignalsfound.Message;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.saasignalsfound.Signal;
import nl.jixxed.eliteodysseymaterials.schemas.journal.SAASignalsFound.SAASignalsFound;
import nl.jixxed.eliteodysseymaterials.service.LocationService;

public class EDDNSAASignalsFoundMapper extends EDDNMapper {
    public static Message mapToEDDN(final SAASignalsFound saaSignalsFound, final Expansion expansion) {
        return new Message.MessageBuilder()
                .withTimestamp(saaSignalsFound.getTimestamp())
                .withEvent(saaSignalsFound.getEvent())
                .withStarPos(LocationService.getCurrentStarPos(saaSignalsFound.getSystemAddress()))
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
