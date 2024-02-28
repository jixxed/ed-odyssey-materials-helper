package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.DockingGranted.DockingGranted;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;

public class DockingGrantedMessageProcessor implements MessageProcessor<DockingGranted> {


    @Override
    public void process(final DockingGranted event) {
        EDDNService.dockingGranted(event);
    }

    @Override
    public Class<DockingGranted> getMessageClass() {
        return DockingGranted.class;
    }
}
