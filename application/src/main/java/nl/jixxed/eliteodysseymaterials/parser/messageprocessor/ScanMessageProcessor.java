package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.Scan.Scan;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;

public class ScanMessageProcessor implements MessageProcessor<Scan> {
    @Override
    public void process(final Scan event) {

        EDDNService.scan(event);
    }

    @Override
    public Class<Scan> getMessageClass() {
        return Scan.class;
    }
}
