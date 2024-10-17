package nl.jixxed.eliteodysseymaterials.service.eddn;

import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.shipyard.Message;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Shipyard.Shipyard;

import java.util.Objects;
import java.util.stream.Collectors;

public class EDDNShipyardMapper extends EDDNMapper {
    public static Message mapToEDDN(final Shipyard shipyard, final Expansion expansion) {
        return new Message.MessageBuilder()
                .withTimestamp(shipyard.getTimestamp())
                .withAllowCobraMkIV(shipyard.getAllowCobraMkIV().orElse(null))
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withStationName(shipyard.getStationName())
                .withSystemName(shipyard.getStarSystem())
                .withMarketId(shipyard.getMarketID())
                .withShips(mapToOptionalEmptyIfEmptyList(shipyard.getPriceList())
                        .map(prices -> prices.stream()
                                .map(price -> nullIfBlank(price.getShipType()))
                                .filter(Objects::nonNull)
                                .collect(Collectors.toSet()))
                        .orElse(null))
                .build();
    }
}
