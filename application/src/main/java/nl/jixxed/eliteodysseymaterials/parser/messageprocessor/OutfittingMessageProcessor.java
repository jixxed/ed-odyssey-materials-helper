package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.Outfitting.Outfitting;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.OutfittingEvent;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class OutfittingMessageProcessor implements MessageProcessor<Outfitting> {
    @Override
    public void process(final Outfitting event) {
        if(event.getItems().map(List::isEmpty).orElse(true)) {
            EventService.publish(new OutfittingEvent(event.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))));
        }else {
            EDDNService.outfitting(event);
        }
    }

    @Override
    public Class<Outfitting> getMessageClass() {
        return Outfitting.class;
    }
}
