package nl.jixxed.eliteodysseymaterials.service.eddn;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.approachsettlement.Message;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ApproachSettlement.ApproachSettlement;
import nl.jixxed.eliteodysseymaterials.service.LocationService;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EDDNApproachSettlementMapper extends EDDNMapper {

    public static Message mapToEDDN(final ApproachSettlement approachSettlement,final Expansion expansion) {
        return new Message.MessageBuilder()
                .withTimestamp(approachSettlement.getTimestamp())
                .withEvent(approachSettlement.getEvent())
                .withBodyID(approachSettlement.getBodyID())
                .withBodyName(approachSettlement.getBodyName())
                .withStarPos(LocationService.getCurrentStarPos(approachSettlement.getSystemAddress()))
                .withStarSystem(LocationService.getCurrentStarSystemName(approachSettlement.getSystemAddress()))
                .withSystemAddress(approachSettlement.getSystemAddress())
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withLatitude(approachSettlement.getLatitude().orElse(null))
                .withLongitude(approachSettlement.getLongitude().orElse(null))
                .withMarketID(approachSettlement.getMarketID().orElse(null))
                .withName(approachSettlement.getName())
                .build();
    }

}
