package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.Shutdown.Shutdown;
import nl.jixxed.eliteodysseymaterials.service.MiningService;

public class ShutdownMessageProcessor implements MessageProcessor<Shutdown> {
    @Override
    public void process(final Shutdown event) {
//        HighGradeEmissionService.submitUss(event.getTimestamp());
        MiningService.sendMiningEventAndReset();
    }

    @Override
    public Class<Shutdown> getMessageClass() {
        return Shutdown.class;
    }
}
