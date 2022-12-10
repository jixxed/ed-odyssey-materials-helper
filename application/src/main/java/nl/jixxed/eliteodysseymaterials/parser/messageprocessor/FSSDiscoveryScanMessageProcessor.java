package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.FSSDiscoveryScan.FSSDiscoveryScan;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;

public class FSSDiscoveryScanMessageProcessor implements MessageProcessor<FSSDiscoveryScan> {
    @Override
    public void process(final FSSDiscoveryScan event) {
        EDDNService.fssdiscoveryscan(event);
    }

    @Override
    public Class<FSSDiscoveryScan> getMessageClass() {
        return FSSDiscoveryScan.class;
    }
}
