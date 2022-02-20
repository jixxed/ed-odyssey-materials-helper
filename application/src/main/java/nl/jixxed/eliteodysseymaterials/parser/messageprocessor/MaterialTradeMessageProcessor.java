package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.parser.EncodedTradeParser;
import nl.jixxed.eliteodysseymaterials.parser.ManufacturedTradeParser;
import nl.jixxed.eliteodysseymaterials.parser.RawTradeParser;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

import java.util.Map;

public class MaterialTradeMessageProcessor implements MessageProcessor {
    private static final RawTradeParser RAW_TRADE_PARSER = new RawTradeParser();
    private static final EncodedTradeParser ENCODED_TRADE_PARSER = new EncodedTradeParser();
    private static final ManufacturedTradeParser MANUFACTURED_TRADE_PARSER = new ManufacturedTradeParser();

    @Override
    public void process(final JsonNode journalMessage) {
        final String category = journalMessage.get("TraderType").asText();
        switch (category) {
            case "raw" -> RAW_TRADE_PARSER.parse(journalMessage, (Map<HorizonsMaterial, Integer>) (Map<?, Integer>) StorageService.getRaw());
            case "encoded" -> ENCODED_TRADE_PARSER.parse(journalMessage, (Map<HorizonsMaterial, Integer>) (Map<?, Integer>) StorageService.getEncoded());
            case "manufactured" -> MANUFACTURED_TRADE_PARSER.parse(journalMessage, (Map<HorizonsMaterial, Integer>) (Map<?, Integer>) StorageService.getManufactured());
        }
        EventService.publish(new StorageEvent(StoragePool.SHIP));
    }
}