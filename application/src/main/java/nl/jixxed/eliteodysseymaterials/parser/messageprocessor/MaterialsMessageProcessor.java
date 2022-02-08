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

public class MaterialsMessageProcessor implements MessageProcessor {
    private static final RawParser RAW_PARSER = new RawParser();
    private static final EncodedParser ENCODED_PARSER = new EncodedParser();
    private static final ManufacturedParser MANUFACTURED_PARSER = new ManufacturedParser();

    @Override
    public void process(final JsonNode journalMessage) {
        StorageService.resetHorizonsMaterialCounts();

        RAW_PARSER.parse(journalMessage.get("Raw").elements(), (Map<HorizonsMaterial, Integer>) (Map<?, Integer>) StorageService.getRaw());
        MANUFACTURED_PARSER.parse(journalMessage.get("Manufactured").elements(), (Map<HorizonsMaterial, Integer>) (Map<?, Integer>) StorageService.getManufactured());
        ENCODED_PARSER.parse(journalMessage.get("Encoded").elements(), (Map<HorizonsMaterial, Integer>) (Map<?, Integer>) StorageService.getEncoded());
        EventService.publish(new StorageEvent(StoragePool.SHIP));

    }
}
//{
//        "timestamp": "2022-02-07T10:11:02Z",
//        "event": "Materials",
//        "Raw": [
//        {
//        "Name": "chromium",
//        "Count": 108
//        },
//        {
//        "Name": "carbon",
//        "Count": 300
//        }
//        ],
//        "Manufactured": [
//        {
//        "Name": "hybridcapacitors",
//        "Name_Localised": "Hybrid Capacitors",
//        "Count": 67
//        },
//        {
//        "Name": "protolightalloys",
//        "Name_Localised": "Proto Light Alloys",
//        "Count": 89
//        }
//        ],
//        "Encoded": [
//        {
//        "Name": "shieldsoakanalysis",
//        "Name_Localised": "Inconsistent Shield Soak Analysis",
//        "Count": 86
//        },
//        {
//        "Name": "shieldpatternanalysis",
//        "Name_Localised": "Aberrant Shield Pattern Analysis",
//        "Count": 105
//        }
//        ]
//        }