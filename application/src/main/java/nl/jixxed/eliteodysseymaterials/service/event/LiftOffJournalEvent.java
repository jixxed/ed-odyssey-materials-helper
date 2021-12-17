package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;

@Getter
public class LiftOffJournalEvent extends JournalEvent {
    private final String starSystem;
    private final String body;
    private final String nearestDestination;
    private final Boolean playerControlled;//false if ship dismissed when player is in SRV, true if player is taking off

    public LiftOffJournalEvent(final String timeStamp, final String starSystem, final String body, final String nearestDestination, final Boolean playerControlled) {
        super(timeStamp);
        this.starSystem = starSystem;
        this.body = body;
        this.nearestDestination = nearestDestination;
        this.playerControlled = playerControlled;
    }
}
