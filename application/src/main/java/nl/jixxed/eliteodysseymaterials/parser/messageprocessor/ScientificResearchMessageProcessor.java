package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.parser.EncodedScientificResearchParser;
import nl.jixxed.eliteodysseymaterials.parser.ManufacturedScientificResearchParser;
import nl.jixxed.eliteodysseymaterials.parser.RawScientificResearchParser;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ScientificResearch.ScientificResearch;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

public class ScientificResearchMessageProcessor implements MessageProcessor<ScientificResearch> {
    private static final RawScientificResearchParser RAW_PARSER = new RawScientificResearchParser();
    private static final EncodedScientificResearchParser ENCODED_PARSER = new EncodedScientificResearchParser();
    private static final ManufacturedScientificResearchParser MANUFACTURED_PARSER = new ManufacturedScientificResearchParser();

    @Override
    public void process(final ScientificResearch event) {
        final String category = event.getCategory();
        switch (category.toLowerCase()) {
            case "raw" ->
                    RAW_PARSER.parse(event);
            case "encoded" ->
                    ENCODED_PARSER.parse(event);
            case "manufactured" ->
                    MANUFACTURED_PARSER.parse(event);
        }
        EventService.publish(new StorageEvent(StoragePool.SHIP));
    }

    @Override
    public Class<ScientificResearch> getMessageClass() {
        return ScientificResearch.class;
    }
}