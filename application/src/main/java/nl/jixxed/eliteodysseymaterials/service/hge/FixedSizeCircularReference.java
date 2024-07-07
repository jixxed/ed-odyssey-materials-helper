package nl.jixxed.eliteodysseymaterials.service.hge;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FixedSizeCircularReference<T> {
    List<T> entries;
    int size;

    public FixedSizeCircularReference(int size) {
        this.entries = Collections.synchronizedList(new LinkedList<>());
        this.size = size;
    }

    public synchronized void add(T entry) {
        entries.remove(entry);
        entries.add(entry);
        if (entries.size() > size) {
            entries.removeFirst();
        }
    }

    public synchronized List<T> asList() {
        return entries;
    }
}
