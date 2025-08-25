package nl.jixxed.eliteodysseymaterials.parser.messageprocessor.capi;

import nl.jixxed.eliteodysseymaterials.schemas.capi.squadron.CapiSquadron;

public class CapiSquadronMessageProcessor implements CapiMessageProcessor<CapiSquadron>{
    @Override
    public void process(CapiSquadron data) {

    }

    @Override
    public Class<CapiSquadron> getMessageClass() {
        return CapiSquadron.class;
    }
}
