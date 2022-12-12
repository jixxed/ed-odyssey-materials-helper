package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.ModuleInfo.ModuleInfo;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ModuleInfoEvent;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ModuleInfoMessageProcessor implements MessageProcessor<ModuleInfo> {
    @Override
    public void process(final ModuleInfo event) {
        if(event.getModules().map(List::isEmpty).orElse(true)) {
            EventService.publish(new ModuleInfoEvent(event.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))));
        }
    }

    @Override
    public Class<ModuleInfo> getMessageClass() {
        return ModuleInfo.class;
    }
}
