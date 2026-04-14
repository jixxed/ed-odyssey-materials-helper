/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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

        final Set<LoadoutSet> loadoutSets1 = new HashSet<>(this.loadoutSets);
        loadoutSets1.add(LoadoutSet.CURRENT);
        return Collections.unmodifiableSet(loadoutSets1);
    }

    @JsonIgnore
    public LoadoutSet getSelectedLoadoutSet() {
        if (this.selectedLoadoutSetUUID == null || this.selectedLoadoutSetUUID.isEmpty()) {
            return selectFirstloadoutSet();
        } else {
            return this.getAllLoadoutSets().stream()
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
    public void updateLoadoutSet(final LoadoutSet loadoutSetToAdd) {
        if (!loadoutSetToAdd.equals(LoadoutSet.CURRENT)) {
            this.loadoutSets.removeIf(loadoutSet -> loadoutSet.getUuid().equals(loadoutSetToAdd.getUuid()));
            this.loadoutSets.add(loadoutSetToAdd);
        }
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
