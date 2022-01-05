package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Location {
    private final StarSystem starSystem;
    private final String body;
    private final String station;
    private final Double latitude;
    private final Double longitude;

}
