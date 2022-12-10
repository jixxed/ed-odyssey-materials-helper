package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.FSSAllBodiesFound.FSSAllBodiesFound;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;

public class FSSAllBodiesFoundMessageProcessor implements MessageProcessor<FSSAllBodiesFound> {
    @Override
    public void process(final FSSAllBodiesFound event) {
        EDDNService.fssallbodiesfound(event);
    }

    @Override
    public Class<FSSAllBodiesFound> getMessageClass() {
        return FSSAllBodiesFound.class;
    }
}
