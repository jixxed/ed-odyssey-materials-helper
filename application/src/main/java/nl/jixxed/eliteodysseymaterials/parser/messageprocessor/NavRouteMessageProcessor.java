package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.NavRoute.NavRoute;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.NavRouteEvent;

import java.time.format.DateTimeFormatter;

public class NavRouteMessageProcessor implements MessageProcessor<NavRoute> {
    @Override
    public void process(final NavRoute event) {
        if(event.getRoute().isEmpty()) {
            EventService.publish(new NavRouteEvent(event.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))));
        }else {
            EDDNService.navroute(event);
        }
    }

    @Override
    public Class<NavRoute> getMessageClass() {
        return NavRoute.class;
    }
}
