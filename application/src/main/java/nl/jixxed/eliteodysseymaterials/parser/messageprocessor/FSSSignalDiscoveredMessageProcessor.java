package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.FSSSignalDiscovered.FSSSignalDiscovered;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;

public class FSSSignalDiscoveredMessageProcessor implements MessageProcessor<FSSSignalDiscovered> {
    @Override
    public void process(final FSSSignalDiscovered event) {
        EDDNService.fsssignaldiscovered(event);
    }

    @Override
    public Class<FSSSignalDiscovered> getMessageClass() {
        return FSSSignalDiscovered.class;
    }
}
