package nl.jixxed.eliteodysseymaterials.service.eddn;

import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.outfitting.Message;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Outfitting.Item;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Outfitting.Outfitting;

import java.util.stream.Collectors;

public class EDDNOutfittingMapper extends EDDNMapper {
    public static Message mapToEDDN(final Outfitting outfitting, final Expansion expansion) {
        return new Message.MessageBuilder()
                .withTimestamp(outfitting.getTimestamp())
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withMarketId(outfitting.getMarketID())
                .withModules(outfitting.getItems()
                        .map(items -> items.stream()
                                .map(Item::getName)
                                .filter(name -> name.startsWith("hpt_") || name.startsWith("int_") || name.contains("_armour_"))
                                .filter(name -> !name.equalsIgnoreCase("int_planetapproachsuite"))
                                .collect(Collectors.toSet()))
                        .orElse(null))
                .withStationName(outfitting.getStationName())
                .withSystemName(outfitting.getStarSystem())
                .build();
    }
}
