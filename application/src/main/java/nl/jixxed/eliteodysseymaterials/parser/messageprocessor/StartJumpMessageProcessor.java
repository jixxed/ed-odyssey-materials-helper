package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.StartJump.StartJump;
import nl.jixxed.eliteodysseymaterials.service.HighGradeEmissionService;

public class StartJumpMessageProcessor implements MessageProcessor<StartJump> {
    @Override
    public void process(final StartJump event) {
        HighGradeEmissionService.submitUss(event.getTimestamp());
    }
    @Override
    public Class<StartJump> getMessageClass() {
        return StartJump.class;
    }
}
