package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.SystemAllegiance;
import nl.jixxed.eliteodysseymaterials.enums.SystemEconomy;
import nl.jixxed.eliteodysseymaterials.enums.SystemGovernment;
import nl.jixxed.eliteodysseymaterials.enums.SystemSecurity;
import nl.jixxed.eliteodysseymaterials.schemas.journal.CarrierJump.CarrierJump;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;
import nl.jixxed.eliteodysseymaterials.service.event.CarrierJumpJournalEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

public class CarrierJumpMessageProcessor implements MessageProcessor<CarrierJump> {
    @Override
    public void process(final CarrierJump event) {
        final String body = event.getBody();
        final String starSystem = event.getStarSystem();
        final String economy = event.getSystemEconomy();
        final String secondEconomy = event.getSystemSecondEconomy();
        final String government = event.getSystemGovernment();
        final String security = event.getSystemSecurity();
        final String allegiance = event.getSystemAllegiance();
        final String factionState = event.getSystemFaction().map(systemFaction -> systemFaction.getFactionState().orElse("")).orElse("");

        if (!starSystem.isBlank()) {
            final double x = event.getStarPos().get(0).doubleValue();
            final double y = event.getStarPos().get(1).doubleValue();
            final double z = event.getStarPos().get(2).doubleValue();
            EventService.publish(new CarrierJumpJournalEvent(event, new StarSystem(starSystem, SystemEconomy.forKey(economy), SystemEconomy.forKey(secondEconomy), SystemGovernment.forKey(government), SystemSecurity.forKey(security), SystemAllegiance.forKey(allegiance), factionState, x, y, z), body));
        }
        EDDNService.carrierjump(event);
    }
    @Override
    public Class<CarrierJump> getMessageClass() {
        return CarrierJump.class;
    }

}
