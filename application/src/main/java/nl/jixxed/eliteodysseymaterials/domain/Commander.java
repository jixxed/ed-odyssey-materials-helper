package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Commander {
    private final String name;
    private final String fid;

    @Override
    public String toString() {
        return this.name;
    }
}
