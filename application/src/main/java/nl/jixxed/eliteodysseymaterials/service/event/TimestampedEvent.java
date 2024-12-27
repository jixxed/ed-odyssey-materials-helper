package nl.jixxed.eliteodysseymaterials.service.event;

public interface TimestampedEvent extends Event {
    String getTimestamp();
}
