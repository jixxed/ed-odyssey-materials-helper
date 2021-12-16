package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Getter;


@Getter
public class FullLocation extends Location {
    private final String body;
    private final String station;

    public FullLocation(final Location location, final String body, final String station) {
        super(location.getStarSystem(), location.getX(), location.getY(), location.getZ());
        this.body = body;
        this.station = station;
    }
}
