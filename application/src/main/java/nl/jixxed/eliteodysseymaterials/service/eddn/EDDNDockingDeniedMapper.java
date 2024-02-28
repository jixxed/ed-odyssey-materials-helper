package nl.jixxed.eliteodysseymaterials.service.eddn;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.dockingDenied.Message;
import nl.jixxed.eliteodysseymaterials.schemas.journal.DockingDenied.DockingDenied;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EDDNDockingDeniedMapper extends EDDNMapper {

    public static Message mapToEDDN(final DockingDenied dockingDenied, final Expansion expansion) {
        return new Message.MessageBuilder()
                .withTimestamp(dockingDenied.getTimestamp())
                .withEvent(dockingDenied.getEvent())
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withMarketID(dockingDenied.getMarketID())
                .withStationName(dockingDenied.getStationName())
                .withStationType(dockingDenied.getStationType())
                .withReason(dockingDenied.getReason())
                .build();
    }
}
