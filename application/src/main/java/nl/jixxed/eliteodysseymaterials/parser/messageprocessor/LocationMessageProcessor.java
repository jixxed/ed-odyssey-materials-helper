package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.SystemEconomy;
import nl.jixxed.eliteodysseymaterials.enums.SystemGovernment;
import nl.jixxed.eliteodysseymaterials.enums.SystemSecurity;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Location.Location;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalInitEvent;
import nl.jixxed.eliteodysseymaterials.service.event.LocationJournalEvent;

public class LocationMessageProcessor implements MessageProcessor<Location> {
    private Boolean isFirstLocationEventInJournal = Boolean.TRUE;

    public LocationMessageProcessor() {
        EventService.addListener(this, JournalInitEvent.class, journalInitEvent -> {
            if (!journalInitEvent.isInitialised()) {
                this.isFirstLocationEventInJournal = Boolean.FALSE;
            }
        });
    }
    @Override
    public void process(final Location event) {
        final String station = event.getStationName().orElse("");
        final String body = event.getBody();
        final String starSystem = event.getStarSystem();
        final String economy = event.getSystemEconomy();
        final String secondEconomy = event.getSystemSecondEconomy();
        final String government = event.getSystemGovernment();
        final String security = event.getSystemSecurity();
        final String factionState = event.getSystemFaction().map(systemFaction -> systemFaction.getFactionState().orElse("")).orElse("None");
        if (!starSystem.isBlank()) {
            final double x = event.getStarPos().get(0);
            final double y = event.getStarPos().get(1);
            final double z = event.getStarPos().get(2);
            EventService.publish(new LocationJournalEvent(event, new StarSystem(starSystem, SystemEconomy.forKey(economy), SystemEconomy.forKey(secondEconomy), SystemGovernment.forKey(government), SystemSecurity.forKey(security), factionState, x, y, z), body, station, this.isFirstLocationEventInJournal));
            this.isFirstLocationEventInJournal = Boolean.FALSE;
        }
        EDDNService.location(event);
    }

    @Override
    public Class<Location> getMessageClass() {
        return Location.class;
    }
}
