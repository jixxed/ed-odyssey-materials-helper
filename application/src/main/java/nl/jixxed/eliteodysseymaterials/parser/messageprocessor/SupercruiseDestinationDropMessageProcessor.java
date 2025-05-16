package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.SupercruiseDestinationDrop.SupercruiseDestinationDrop;
import nl.jixxed.eliteodysseymaterials.service.MiningService;

public class SupercruiseDestinationDropMessageProcessor implements MessageProcessor<SupercruiseDestinationDrop> {
    @Override
    public void process(final SupercruiseDestinationDrop event) {
        MiningService.setSupercruiseDestinationDrop(event);
    }

    @Override
    public Class<SupercruiseDestinationDrop> getMessageClass() {
        return SupercruiseDestinationDrop.class;
    }
}