package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.SystemEconomy;
import nl.jixxed.eliteodysseymaterials.enums.SystemGovernment;
import nl.jixxed.eliteodysseymaterials.enums.SystemSecurity;
import nl.jixxed.eliteodysseymaterials.schemas.journal.FSDJump.FSDJump;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.FSDJumpJournalEvent;

public class FSDJumpMessageProcessor implements MessageProcessor<FSDJump> {
    @Override
    public void process(final FSDJump event) {
        final String body = event.getBody();
        final String starSystem = event.getStarSystem();
        final String economy = event.getSystemEconomy();
        final String secondEconomy = event.getSystemSecondEconomy();
        final String government = event.getSystemGovernment();
        final String security = event.getSystemSecurity();
        final String factionState = event.getSystemFaction().map(systemFaction -> systemFaction.getFactionState().orElse("")).orElse("");

        if (!starSystem.isBlank()) {
            final double x = event.getStarPos().get(0);
            final double y = event.getStarPos().get(1);
            final double z = event.getStarPos().get(2);
            EventService.publish(new FSDJumpJournalEvent(event, new StarSystem(starSystem, SystemEconomy.forKey(economy), SystemEconomy.forKey(secondEconomy), SystemGovernment.forKey(government), SystemSecurity.forKey(security), factionState, x, y, z), body));
        }
        EDDNService.fsdjump(event);
    }
    @Override
    public Class<FSDJump> getMessageClass() {
        return FSDJump.class;
    }

}
