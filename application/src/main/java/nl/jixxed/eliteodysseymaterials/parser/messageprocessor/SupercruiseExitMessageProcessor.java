package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.SupercruiseExit.SupercruiseExit;
import nl.jixxed.eliteodysseymaterials.service.MiningService;

public class SupercruiseExitMessageProcessor implements MessageProcessor<SupercruiseExit> {
    @Override
    public void process(final SupercruiseExit event) {
        MiningService.setSupercruiseExit(event);
    }

    @Override
    public Class<SupercruiseExit> getMessageClass() {
        return SupercruiseExit.class;
    }
}