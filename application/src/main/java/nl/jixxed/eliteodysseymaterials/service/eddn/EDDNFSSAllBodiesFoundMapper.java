package nl.jixxed.eliteodysseymaterials.service.eddn;

import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.fssallbodiesfound.Message;
import nl.jixxed.eliteodysseymaterials.schemas.journal.FSSAllBodiesFound.FSSAllBodiesFound;
import nl.jixxed.eliteodysseymaterials.service.LocationService;

public class EDDNFSSAllBodiesFoundMapper extends EDDNMapper {
    public static Message mapToEDDN(final FSSAllBodiesFound fssAllBodiesFound, final Expansion expansion) {
        return new Message.MessageBuilder()
                .withTimestamp(fssAllBodiesFound.getTimestamp())
                .withEvent(fssAllBodiesFound.getEvent())
                .withStarPos(LocationService.getCurrentStarPos(fssAllBodiesFound.getSystemAddress()))
                .withSystemName(fssAllBodiesFound.getSystemName())
                .withSystemAddress(fssAllBodiesFound.getSystemAddress())
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withCount(fssAllBodiesFound.getCount())
                .build();
    }
}
