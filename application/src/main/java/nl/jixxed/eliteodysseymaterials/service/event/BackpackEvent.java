package nl.jixxed.eliteodysseymaterials.service.event;

public class BackpackEvent extends Event {
    String timestamp;

    public BackpackEvent(final String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return this.timestamp;
    }
}
