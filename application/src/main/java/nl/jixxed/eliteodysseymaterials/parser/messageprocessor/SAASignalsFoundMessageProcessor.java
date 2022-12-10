package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.SAASignalsFound.SAASignalsFound;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;

public class SAASignalsFoundMessageProcessor implements MessageProcessor<SAASignalsFound> {
    @Override
    public void process(final SAASignalsFound event) {
        EDDNService.saasignalsfound(event);
    }

    @Override
    public Class<SAASignalsFound> getMessageClass() {
        return SAASignalsFound.class;
    }
}
