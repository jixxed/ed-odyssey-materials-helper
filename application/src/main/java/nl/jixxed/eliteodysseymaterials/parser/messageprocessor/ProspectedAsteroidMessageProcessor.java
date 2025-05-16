package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.ProspectedAsteroid.ProspectedAsteroid;
import nl.jixxed.eliteodysseymaterials.service.MiningService;

public class ProspectedAsteroidMessageProcessor implements MessageProcessor<ProspectedAsteroid> {
    @Override
    public void process(final ProspectedAsteroid event) {
        MiningService.addProspectedAsteroid(event);
    }

    @Override
    public Class<ProspectedAsteroid> getMessageClass() {
        return ProspectedAsteroid.class;
    }
}