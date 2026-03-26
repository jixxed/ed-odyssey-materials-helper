package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.EjectCargo.EjectCargo;

import java.time.LocalDateTime;

public class EjectCargoMessageProcessor implements MessageProcessor<EjectCargo> {

    public static LocalDateTime lastEjectCargo = null;
    @Override
    public void process(final EjectCargo event) {
        lastEjectCargo = event.getTimestamp();
    }

    @Override
    public Class<EjectCargo> getMessageClass() {
        return EjectCargo.class;
    }
}
