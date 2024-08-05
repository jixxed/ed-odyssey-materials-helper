package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.Music.Music;

public class MusicMessageProcessor implements MessageProcessor<Music> {
    @Override
    public void process(final Music event) {
        if(event.getMusicTrack().equals("MainMenu")) {
//            HighGradeEmissionService.submitUss(event.getTimestamp());
        }
    }
    @Override
    public Class<Music> getMessageClass() {
        return Music.class;
    }
}
