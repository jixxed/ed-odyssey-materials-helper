package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LoadoutSet {
    @EqualsAndHashCode.Include
    private String uuid = UUID.randomUUID().toString();
    private String name;
    private List<Loadout> loadouts = new ArrayList<>();

    @Override
    public String toString() {
        return this.name;
    }

    @JsonIgnore
    public void addLoadout(final Loadout loadout) {
        this.loadouts.add(loadout);
    }

    @JsonIgnore
    public void removeLoadout(final Loadout loadout) {
        this.loadouts.remove(loadout);
    }

    @JsonIgnore
    public void moveDown(final Loadout loadout) {
        final int newPos = this.loadouts.indexOf(loadout) - 1;
        this.loadouts.remove(loadout);
        this.loadouts.add(newPos, loadout);
    }

    @JsonIgnore
    public void moveUp(final Loadout loadout) {
        final int newPos = this.loadouts.indexOf(loadout) + 1;
        this.loadouts.remove(loadout);
        this.loadouts.add(newPos, loadout);
    }
}

