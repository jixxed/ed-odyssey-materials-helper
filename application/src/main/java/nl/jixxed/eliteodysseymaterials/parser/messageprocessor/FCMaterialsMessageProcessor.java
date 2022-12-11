package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.FCMaterials.FCMaterials;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.FCMaterialsEvent;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class FCMaterialsMessageProcessor implements MessageProcessor<FCMaterials> {
    @Override
    public void process(final FCMaterials event) {
        if(event.getItems().map(List::isEmpty).orElse(true)) {
            EventService.publish(new FCMaterialsEvent(event.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))));
        }else {
            EDDNService.fcmaterialsjournal(event);
        }
    }

    @Override
    public Class<FCMaterials> getMessageClass() {
        return FCMaterials.class;
    }
}
