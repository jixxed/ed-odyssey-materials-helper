package nl.jixxed.eliteodysseymaterials.parser.messageprocessor.capi;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.SquadronPerk;
import nl.jixxed.eliteodysseymaterials.schemas.capi.squadron.CapiSquadron;

public class CapiSquadronMessageProcessor implements CapiMessageProcessor<CapiSquadron>{
    @Override
    public void process(CapiSquadron data) {
        SquadronPerk primaryPerk = SquadronPerk.forId(data.getPerks().getPrimary());
        SquadronPerk factionPerk = SquadronPerk.forId(data.getPerks().getSecondary());
        ApplicationState.getInstance().setPrimaryPerk(primaryPerk);
        ApplicationState.getInstance().setFactionPerk(factionPerk);
    }

    @Override
    public Class<CapiSquadron> getMessageClass() {
        return CapiSquadron.class;
    }

    @Override
    public void clear() {
        ApplicationState.getInstance().setPrimaryPerk(SquadronPerk.UNKNOWN);
        ApplicationState.getInstance().setFactionPerk(SquadronPerk.UNKNOWN);
    }
}
