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

