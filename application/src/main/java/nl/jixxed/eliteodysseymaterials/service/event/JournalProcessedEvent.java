package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.JournalEventType;

import java.io.File;

@AllArgsConstructor
@Getter
public class JournalProcessedEvent extends Event {
    private final String timestamp;
    private final JournalEventType journalEventType;
    private final File file;
}
