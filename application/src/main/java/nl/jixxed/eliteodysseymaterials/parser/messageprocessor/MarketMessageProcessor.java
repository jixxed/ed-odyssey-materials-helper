package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.Market.Market;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.MarketEvent;

import java.time.format.DateTimeFormatter;

public class MarketMessageProcessor implements MessageProcessor<Market> {
    @Override
    public void process(final Market event) {
        if(event.getItems().isEmpty()) {
            EventService.publish(new MarketEvent(event.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))));
        }else {
            EDDNService.commodity(event);
        }
    }

    @Override
    public Class<Market> getMessageClass() {
        return Market.class;
    }
}
