package nl.jixxed.eliteodysseymaterials.service.event;

public class ShipLockerEvent extends Event {
    String timestamp;

    public ShipLockerEvent(final String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return this.timestamp;
    }
}
