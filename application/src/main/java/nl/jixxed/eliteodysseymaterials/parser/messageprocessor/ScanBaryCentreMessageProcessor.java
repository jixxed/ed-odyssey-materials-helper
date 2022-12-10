package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.ScanBaryCentre.ScanBaryCentre;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;

public class ScanBaryCentreMessageProcessor implements MessageProcessor<ScanBaryCentre> {
    @Override
    public void process(final ScanBaryCentre event) {

        EDDNService.scanbarycentre(event);
    }

    @Override
    public Class<ScanBaryCentre> getMessageClass() {
        return ScanBaryCentre.class;
    }
}
