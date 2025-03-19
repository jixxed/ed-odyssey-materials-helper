package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Power;
import nl.jixxed.eliteodysseymaterials.schemas.journal.PowerplayRank.PowerplayRank;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.PowerplayEvent;

public class PowerplayRankMessageProcessor implements MessageProcessor<PowerplayRank> {

    @Override
    public void process(final PowerplayRank event) {
        Power power = Power.forName(event.getPower());
        ApplicationState.getInstance().setPower(power);
        ApplicationState.getInstance().setPowerRank(event.getRank().longValue());
        EventService.publish(new PowerplayEvent(power, event.getRank().longValue()));
    }

    @Override
    public Class<PowerplayRank> getMessageClass() {
        return PowerplayRank.class;
    }
}
