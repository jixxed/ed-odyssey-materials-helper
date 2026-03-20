package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.CommanderRank;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Rank.Rank;

public class RankMessageProcessor implements MessageProcessor<Rank> {
    @Override
    public void process(Rank rank) {
        ApplicationState.getInstance().setCommanderRank(CommanderRank.FEDERATION, rank.getFederation().intValue());
        ApplicationState.getInstance().setCommanderRank(CommanderRank.EMPIRE, rank.getEmpire().intValue());
    }

    @Override
    public Class<Rank> getMessageClass() {
        return Rank.class;
    }
}
