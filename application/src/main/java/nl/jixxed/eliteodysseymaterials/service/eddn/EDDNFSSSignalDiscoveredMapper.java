package nl.jixxed.eliteodysseymaterials.service.eddn;

import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.fsssignaldiscovered.Message;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.fsssignaldiscovered.Signal;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Event;
import nl.jixxed.eliteodysseymaterials.schemas.journal.FSSSignalDiscovered.FSSSignalDiscovered;
import nl.jixxed.eliteodysseymaterials.service.LocationService;

import java.util.List;

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
                .withStarPos(LocationService.getCurrentStarPos())
                .withStarSystem(LocationService.getCurrentStarSystemName())
                .withSystemAddress(LocationService.getCurrentSystemAddress())
                .withSignals(signals)
                .build();
    }
}
