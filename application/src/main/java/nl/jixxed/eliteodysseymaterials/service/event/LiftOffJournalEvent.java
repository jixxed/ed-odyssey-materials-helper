package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;

@Getter
public class LiftOffJournalEvent extends JournalEvent {
    private final String starSystem;
    private final String body;
    private final String nearestDestination;
    private final Boolean playerControlled;//false if ship dismissed when player is in SRV, true if player is taking off
    private final Boolean taxi;//false if ship dismissed when player is in SRV, true if player is taking off
    private final Double latitude;
    private final Double longitude;

    @SuppressWarnings("java:S107")
    public LiftOffJournalEvent(final String timeStamp, final String starSystem, final String body, final String nearestDestination, final Boolean playerControlled, final Boolean taxi, final Double latitude, final Double longitude) {
        super(timeStamp);
        this.starSystem = starSystem;
        this.body = body;
        this.nearestDestination = nearestDestination;
        this.playerControlled = playerControlled;
        this.taxi = taxi;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
