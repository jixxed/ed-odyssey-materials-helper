package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Power;
import nl.jixxed.eliteodysseymaterials.schemas.journal.PowerplayMerits.PowerplayMerits;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.PowerplayEvent;

public class PowerplayMeritsMessageProcessor implements MessageProcessor<PowerplayMerits> {

    @Override
    public void process(final PowerplayMerits event) {
        final String powerValue = event.getPower();
        if (!powerValue.isBlank()) {
            Power power = Power.forName(powerValue);
            ApplicationState.getInstance().setPower(power);
        }
        ApplicationState.getInstance().setPowerMerits(event.getTotalMerits().longValue());
        EventService.publish(new PowerplayEvent(event.getTotalMerits().longValue(), ApplicationState.getInstance().getPower()));
    }

    @Override
    public Class<PowerplayMerits> getMessageClass() {
        return PowerplayMerits.class;
    }
}
