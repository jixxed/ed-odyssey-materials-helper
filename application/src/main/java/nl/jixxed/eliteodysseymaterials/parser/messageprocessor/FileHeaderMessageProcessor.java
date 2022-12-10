package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.GameVersion;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Fileheader.Fileheader;

public class FileHeaderMessageProcessor implements MessageProcessor<Fileheader>{
    @Override
    public void process(final Fileheader fileheader) {
        ApplicationState.getInstance().setFileheader(fileheader);
        final String gameversion = fileheader.getGameversion();
        if (gameversion.startsWith("3")) {
            ApplicationState.getInstance().setGameVersion(GameVersion.LEGACY);
        } else if (gameversion.startsWith("4")) {
            ApplicationState.getInstance().setGameVersion(GameVersion.LIVE);
        }
    }

    @Override
    public Class<Fileheader> getMessageClass() {
        return Fileheader.class;
    }
}
