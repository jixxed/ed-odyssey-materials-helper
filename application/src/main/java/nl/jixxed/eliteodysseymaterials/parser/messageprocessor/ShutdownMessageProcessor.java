package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.Shutdown.Shutdown;

public class ShutdownMessageProcessor implements MessageProcessor<Shutdown> {
    @Override
    public void process(final Shutdown event) {
//        HighGradeEmissionService.submitUss(event.getTimestamp());
    }
    @Override
    public Class<Shutdown> getMessageClass() {
        return Shutdown.class;
    }
}
