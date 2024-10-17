package nl.jixxed.eliteodysseymaterials.service.eddn;

import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.fcmaterialsjournal.Item;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.fcmaterialsjournal.Message;
import nl.jixxed.eliteodysseymaterials.schemas.journal.FCMaterials.FCMaterials;

public class EDDNFCMaterialsJournalMapper extends EDDNMapper {
    public static Message mapToEDDN(final FCMaterials fcMaterials, final Expansion expansion) {
        return new Message.MessageBuilder()
                .withTimestamp(fcMaterials.getTimestamp())
                .withEvent(fcMaterials.getEvent())
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withCarrierID(fcMaterials.getCarrierID())
                .withMarketID(fcMaterials.getMarketID())
                .withCarrierName(fcMaterials.getCarrierName())
                .withItems(mapToOptionalEmptyIfEmptyList(fcMaterials.getItems())
                        .map(items -> items.stream()
                                .map(item -> new Item.ItemBuilder()
                                        .withName(item.getName())
                                        .withId(item.getId())
                                        .withPrice(item.getPrice())
                                        .withStock(item.getStock())
                                        .withDemand(item.getDemand())
                                        .build())
                                .toList())
                        .orElse(null))
                .build();
    }
}
