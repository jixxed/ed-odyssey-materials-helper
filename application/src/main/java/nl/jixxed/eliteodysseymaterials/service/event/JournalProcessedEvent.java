package nl.jixxed.eliteodysseymaterials.service.event;

import nl.jixxed.eliteodysseymaterials.enums.JournalEventType;

import java.io.File;

public class JournalProcessedEvent extends Event {
    private final String timestamp;
    private final JournalEventType journalEventType;
    private final File file;

    public JournalProcessedEvent(final String timestamp, final JournalEventType journalEventType, final File file) {
        this.timestamp = timestamp;
        this.journalEventType = journalEventType;
        this.file = file;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public JournalEventType getJournalEventType() {
        return this.journalEventType;
    }

    public File getFile() {
        return this.file;
    }
}
