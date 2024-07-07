package nl.jixxed.eliteodysseymaterials.domain;

import nl.jixxed.eliteodysseymaterials.service.hge.FactionV2;

import java.util.Objects;
import java.util.Set;

public record HgeStarSystem(StarSystem starSystem, Set<FactionV2> faction) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HgeStarSystem that = (HgeStarSystem) o;
        return Objects.equals(starSystem.getName(), that.starSystem.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(starSystem.getName());
    }
}
