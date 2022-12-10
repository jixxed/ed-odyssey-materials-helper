package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.FSSBodySignals.FSSBodySignals;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;

public class FSSBodySignalsMessageProcessor implements MessageProcessor<FSSBodySignals> {
    @Override
    public void process(final FSSBodySignals event) {
        EDDNService.fssbodysignals(event);
    }

    @Override
    public Class<FSSBodySignals> getMessageClass() {
        return FSSBodySignals.class;
    }
}
