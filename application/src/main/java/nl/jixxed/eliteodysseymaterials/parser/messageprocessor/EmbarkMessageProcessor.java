package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.journalevents.Embark.Embark;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

public class EmbarkMessageProcessor implements MessageProcessor<Embark> {
    @Override
    public void process(final Embark event) {
        StorageService.resetBackPackCounts();
    }

    @Override
    public Class<Embark> getMessageClass() {
        return Embark.class;
    }
}
