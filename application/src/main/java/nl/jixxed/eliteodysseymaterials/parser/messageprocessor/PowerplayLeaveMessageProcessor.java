package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Power;
import nl.jixxed.eliteodysseymaterials.schemas.journal.PowerplayLeave.PowerplayLeave;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.PowerplayLeaveEvent;

public class PowerplayLeaveMessageProcessor implements MessageProcessor<PowerplayLeave> {

    @Override
    public void process(final PowerplayLeave event) {
        Power power = ApplicationState.getInstance().getPower();
        ApplicationState.getInstance().resetPowerplay();
        EventService.publish(new PowerplayLeaveEvent(power));
    }

    @Override
    public Class<PowerplayLeave> getMessageClass() {
        return PowerplayLeave.class;
    }
}
