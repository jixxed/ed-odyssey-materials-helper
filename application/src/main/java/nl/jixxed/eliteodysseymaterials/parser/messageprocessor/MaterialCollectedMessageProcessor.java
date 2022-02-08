package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.parser.EncodedParser;
import nl.jixxed.eliteodysseymaterials.parser.ManufacturedParser;
import nl.jixxed.eliteodysseymaterials.parser.RawParser;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

import java.util.Map;

public class MaterialCollectedMessageProcessor implements MessageProcessor {
    private static final RawParser RAW_PARSER = new RawParser();
    private static final EncodedParser ENCODED_PARSER = new EncodedParser();
    private static final ManufacturedParser MANUFACTURED_PARSER = new ManufacturedParser();

    @Override
    public void process(final JsonNode journalMessage) {
        final String category = journalMessage.get("Category").asText();
        switch (category) {
            case "Raw" -> RAW_PARSER.parse(journalMessage, (Map<HorizonsMaterial, Integer>) (Map<?, Integer>) StorageService.getRaw());
            case "Encoded" -> ENCODED_PARSER.parse(journalMessage, (Map<HorizonsMaterial, Integer>) (Map<?, Integer>) StorageService.getEncoded());
            case "Manufactured" -> MANUFACTURED_PARSER.parse(journalMessage, (Map<HorizonsMaterial, Integer>) (Map<?, Integer>) StorageService.getManufactured());
        }
        EventService.publish(new StorageEvent(StoragePool.SHIP));
    }
}
//{
//        "timestamp": "2022-02-07T12:09:22Z",
//        "event": "MaterialCollected",
//        "Category": "Manufactured",
//        "Name": "guardian_sentinel_wreckagecomponents",
//        "Name_Localised": "Guardian Wreckage Components",
//        "Count": 3
//        }