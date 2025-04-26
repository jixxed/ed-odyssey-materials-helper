package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Power;
import nl.jixxed.eliteodysseymaterials.schemas.journal.PowerplayRank.PowerplayRank;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.PowerplayEvent;

public class PowerplayRankMessageProcessor implements MessageProcessor<PowerplayRank> {

    @Override
    public void process(final PowerplayRank event) {
        final String powerValue = event.getPower();
        if (!powerValue.isBlank()) {
            Power power = Power.forName(powerValue);
            ApplicationState.getInstance().setPower(power);
        }
        ApplicationState.getInstance().setPowerRank(event.getRank().longValue());
        EventService.publish(new PowerplayEvent(ApplicationState.getInstance().getPower(), event.getRank().longValue()));
    }

    @Override
    public Class<PowerplayRank> getMessageClass() {
        return PowerplayRank.class;
    }
}
