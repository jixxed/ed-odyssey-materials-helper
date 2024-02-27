package nl.jixxed.eliteodysseymaterials.service.eddn;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.dockingGranted.Message;
import nl.jixxed.eliteodysseymaterials.schemas.journal.DockingGranted.DockingGranted;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EDDNDockingGrantedMapper extends EDDNMapper {

    public static Message mapToEDDN(final DockingGranted dockingGranted, final Expansion expansion) {
        return new Message.MessageBuilder()
                .withTimestamp(dockingGranted.getTimestamp())
                .withEvent(dockingGranted.getEvent())
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withMarketID(dockingGranted.getMarketID())
                .withStationName(dockingGranted.getStationName())
                .withStationType(dockingGranted.getStationType())
                .withLandingPad(dockingGranted.getLandingPad())
                .build();
    }
}
