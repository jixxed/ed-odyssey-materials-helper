package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Power;
import nl.jixxed.eliteodysseymaterials.schemas.journal.PowerplayMerits.PowerplayMerits;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.PowerplayEvent;

public class PowerplayMeritsMessageProcessor implements MessageProcessor<PowerplayMerits> {

    @Override
    public void process(final PowerplayMerits event) {
        Power power = Power.forName(event.getPower());
        ApplicationState.getInstance().setPower(power);
        ApplicationState.getInstance().setPowerMerits(event.getTotalMerits().longValue());
        EventService.publish(new PowerplayEvent(event.getTotalMerits().longValue(), power));
    }

    @Override
    public Class<PowerplayMerits> getMessageClass() {
        return PowerplayMerits.class;
    }
}
