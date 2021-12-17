package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class StarSystem {
    private final String name;
    private final double x;
    private final double y;
    private final double z;
}
