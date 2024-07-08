package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.schemas.journal.FSDJump.FSDJump;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.FSDJumpJournalEvent;
@Slf4j
public class FSDJumpMessageProcessor implements MessageProcessor<FSDJump> {
    @Override
    public void process(final FSDJump event) {
        final String body = event.getBody();
        final String starSystem = event.getStarSystem();
        final Economy economy = Economy.forKey(event.getSystemEconomy());
        final Economy secondEconomy = Economy.forKey(event.getSystemSecondEconomy());
        final Government government = Government.forId(event.getSystemGovernment());
        final Security security = Security.forId(event.getSystemSecurity());
        final Allegiance allegiance = Allegiance.forKey(event.getSystemAllegiance());
        final State factionState = event.getSystemFaction().map(systemFaction -> State.forName(systemFaction.getFactionState().orElse(""))).orElse(State.NONE);

        if (!starSystem.isBlank()) {
            final double x = event.getStarPos().get(0).doubleValue();
            final double y = event.getStarPos().get(1).doubleValue();
            final double z = event.getStarPos().get(2).doubleValue();
            EventService.publish(new FSDJumpJournalEvent(event, new StarSystem(starSystem, economy, secondEconomy, government, security, allegiance, event.getPopulation(), factionState, x, y, z), body));
        }
        EDDNService.fsdjump(event);
    }

    @Override
    public Class<FSDJump> getMessageClass() {
        return FSDJump.class;
    }

}
