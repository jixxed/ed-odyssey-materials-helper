package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.GameVersion;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Commander.Commander;

public class CommanderMessageProcessor implements MessageProcessor<Commander> {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    @Override
    public void process(final Commander commander) {
        if (!APPLICATION_STATE.getGameVersion().equals(GameVersion.UNKNOWN)) {
            APPLICATION_STATE.addCommander(commander.getName(), commander.getFid(), APPLICATION_STATE.getGameVersion(), commander.getTimestamp());
        }
    }

    @Override
    public Class<Commander> getMessageClass() {
        return Commander.class;
    }
}
