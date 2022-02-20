package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Data
public class LoadoutSetList {
    @SuppressWarnings("java:S1700")
    private Set<LoadoutSet> loadoutSets = new HashSet<>();
    private String selectedLoadoutSetUUID;

    @JsonIgnore
    public Set<LoadoutSet> getAllLoadoutSets() {
        return Collections.unmodifiableSet(this.loadoutSets);
    }

    @JsonIgnore
    public LoadoutSet getSelectedLoadoutSet() {
        if (this.selectedLoadoutSetUUID == null || this.selectedLoadoutSetUUID.isEmpty()) {
            return selectFirstloadoutSet();
        } else {
            return this.loadoutSets.stream()
                    .filter(loadoutSet -> loadoutSet.getUuid().equals(this.selectedLoadoutSetUUID))
                    .findFirst()
                    .orElseGet(this::selectFirstloadoutSet);
        }
    }

    @JsonIgnore
    private LoadoutSet selectFirstloadoutSet() {
        final LoadoutSet loadoutSet = this.loadoutSets.stream().findFirst().orElse(createInitialLoadoutSet());
        this.selectedLoadoutSetUUID = loadoutSet.getUuid();
        return loadoutSet;
    }

    @JsonIgnore
    private LoadoutSet createInitialLoadoutSet() {
        final LoadoutSet loadoutSet = new LoadoutSet();
        loadoutSet.setName("Default Loadout");
        this.loadoutSets.add(loadoutSet);
        return loadoutSet;
    }

    @JsonIgnore
    private LoadoutSet getLoadoutSet(final String uuid) {
        return getAllLoadoutSets().stream().filter(loadoutSet -> loadoutSet.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    @JsonIgnore
    public void addLoadoutSet(final LoadoutSet loadoutSetToAdd) {
        //reset UUID if already exists
        while (this.loadoutSets.stream().anyMatch(loadoutSet -> loadoutSet.getUuid().equals(loadoutSetToAdd.getUuid()))) {
            loadoutSetToAdd.setUuid(UUID.randomUUID().toString());
        }
        this.loadoutSets.add(loadoutSetToAdd);
    }

    @JsonIgnore
    void updateLoadoutSet(final LoadoutSet loadoutSetToAdd) {
        this.loadoutSets.removeIf(loadoutSet -> loadoutSet.getUuid().equals(loadoutSetToAdd.getUuid()));
        this.loadoutSets.add(loadoutSetToAdd);
    }

    @JsonIgnore
    public void delete(final String loadoutSetUUID) {
        this.loadoutSets.removeIf(loadoutSet -> loadoutSet.getUuid().equals(loadoutSetUUID));
        if (this.loadoutSets.isEmpty()) {
            createInitialLoadoutSet();
        }
    }

    @JsonIgnore
    public void renameLoadoutSet(final String uuid, final String name) {
        if (name != null && !name.isEmpty()) {
            final LoadoutSet loadoutSet = getLoadoutSet(uuid);
            if (loadoutSet != null) {
                loadoutSet.setName((name.length() > 50) ? name.substring(0, 50) : name);
            }
        }
    }

    @JsonIgnore
    public LoadoutSet createLoadoutSet(final String name) {
        final LoadoutSet loadoutSet = new LoadoutSet();
        if (name != null && !name.isEmpty()) {
            loadoutSet.setName(name);
        } else {
            loadoutSet.setName("Default Loadout");
        }
        this.addLoadoutSet(loadoutSet);
        this.selectedLoadoutSetUUID = loadoutSet.getUuid();
        return loadoutSet;
    }
}
