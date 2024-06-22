package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.USSDrop.USSDrop;
import nl.jixxed.eliteodysseymaterials.service.HighGradeEmissionService;

public class USSDropMessageProcessor implements MessageProcessor<USSDrop> {
    @Override
    public void process(final USSDrop event) {
        HighGradeEmissionService.ussDrop(event);
    }

    @Override
    public Class<USSDrop> getMessageClass() {
        return USSDrop.class;
    }
}
