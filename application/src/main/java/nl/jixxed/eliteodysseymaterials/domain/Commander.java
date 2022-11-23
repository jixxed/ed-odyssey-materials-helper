package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.GameVersion;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Commander {
    private final String name;
    private final String fid;
    private final GameVersion gameVersion;

    @Override
    public String toString() {
        return this.name + "(" + this.gameVersion.name() + ")";
    }
}
