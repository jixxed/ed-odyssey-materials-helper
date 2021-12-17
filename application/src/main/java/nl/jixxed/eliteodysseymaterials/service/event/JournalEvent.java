package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class JournalEvent implements Event {
    private final String timeStamp;

    public String getTimeStamp() {
        return this.timeStamp;
    }
}
