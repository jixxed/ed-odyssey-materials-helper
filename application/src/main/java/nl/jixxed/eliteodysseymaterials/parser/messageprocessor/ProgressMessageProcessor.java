package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.CommanderRank;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Progress.Progress;

public class ProgressMessageProcessor implements MessageProcessor<Progress> {
    @Override
    public void process(Progress progress) {
        ApplicationState.getInstance().setCommanderRankProgress(CommanderRank.FEDERATION, progress.getFederation().intValue());
        ApplicationState.getInstance().setCommanderRankProgress(CommanderRank.EMPIRE, progress.getEmpire().intValue());
    }

    @Override
    public Class<Progress> getMessageClass() {
        return Progress.class;
    }
}
