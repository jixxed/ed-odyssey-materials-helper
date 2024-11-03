package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Power;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Powerplay.Powerplay;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.PowerplayEvent;

public class PowerplayMessageProcessor implements MessageProcessor<Powerplay> {

    @Override
    public void process(final Powerplay event) {
        Power power = Power.forName(event.getPower());
        ApplicationState.getInstance().setPower(power);
        ApplicationState.getInstance().setPowerMerits(event.getMerits().longValue());
        ApplicationState.getInstance().setPowerRank(event.getRank().longValue());
        EventService.publish(new PowerplayEvent(power, event.getMerits().longValue(), event.getRank().longValue()));
    }

    @Override
    public Class<Powerplay> getMessageClass() {
        return Powerplay.class;
    }
}
