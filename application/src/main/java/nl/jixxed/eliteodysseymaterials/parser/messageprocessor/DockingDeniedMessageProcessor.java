package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.DockingDenied.DockingDenied;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;

public class DockingDeniedMessageProcessor implements MessageProcessor<DockingDenied> {


    @Override
    public void process(final DockingDenied event) {
        EDDNService.dockingDenied(event);
    }

    @Override
    public Class<DockingDenied> getMessageClass() {
        return DockingDenied.class;
    }
}
