package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalInitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties("version")
public class LoadoutSet {
    @EqualsAndHashCode.Include
    private String uuid = UUID.randomUUID().toString();
    private String name;
    private List<Loadout> loadouts = new ArrayList<>();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    @JsonIgnore
    public static final LoadoutSet CURRENT = new LoadoutSet("0", "Current Loadout (read only)", new ArrayList<>());

    static {
        EVENT_LISTENERS.add(EventService.addStaticListener(JournalInitEvent.class, journalInitEvent -> {
            if (!journalInitEvent.isInitialised()) {
                CURRENT.loadouts.clear();
            }
        }));
    }

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

    public LoadoutSet cloneLoadoutSet() {
        final LoadoutSet newLoadoutSet = new LoadoutSet();
        String newName = this.name;
        if (this == CURRENT) {
            newName = newName.replace(" (read only)", "");
        }
        newLoadoutSet.setName(newName + " (cloned)");
        newLoadoutSet.getLoadouts().addAll(this.loadouts.stream().map(Loadout::cloneLoadout).toList());
        return newLoadoutSet;
    }
}

