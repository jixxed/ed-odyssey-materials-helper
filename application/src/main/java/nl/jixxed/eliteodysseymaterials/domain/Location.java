package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Location {
    private final String starSystem;
    private final double x;
    private final double y;
    private final double z;
}
