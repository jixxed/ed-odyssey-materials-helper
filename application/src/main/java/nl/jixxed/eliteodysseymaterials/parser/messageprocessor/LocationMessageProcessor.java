package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Location.Location;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalInitEvent;
import nl.jixxed.eliteodysseymaterials.service.event.LocationJournalEvent;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class LocationMessageProcessor implements MessageProcessor<Location> {
    private Boolean isFirstLocationEventInJournal = Boolean.TRUE;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    public LocationMessageProcessor() {
        eventListeners.add(EventService.addListener(this, JournalInitEvent.class, journalInitEvent -> {
            if (!journalInitEvent.isInitialised()) {
                this.isFirstLocationEventInJournal = Boolean.FALSE;
            }
        }));
    }
    @Override
    public void process(final Location event) {
        final String station = event.getStationName_Localised().orElseGet(()-> event.getStationName().orElse(""));
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
            EventService.publish(new LocationJournalEvent(event, new StarSystem(starSystem, economy, secondEconomy, government, security, allegiance, event.getPopulation(), factionState, x, y, z), body, station, this.isFirstLocationEventInJournal));
            this.isFirstLocationEventInJournal = Boolean.FALSE;
        }
        EDDNService.location(event);
    }

    @Override
    public Class<Location> getMessageClass() {
        return Location.class;
    }
}
