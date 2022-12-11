package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.Shipyard.Shipyard;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipyardEvent;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ShipyardMessageProcessor implements MessageProcessor<Shipyard> {
    @Override
    public void process(final Shipyard event) {
        if(event.getPriceList().map(List::isEmpty).orElse(true)) {
            EventService.publish(new ShipyardEvent(event.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))));
        }else {
            EDDNService.shipyard(event);
        }
    }

    @Override
    public Class<Shipyard> getMessageClass() {
        return Shipyard.class;
    }
}
