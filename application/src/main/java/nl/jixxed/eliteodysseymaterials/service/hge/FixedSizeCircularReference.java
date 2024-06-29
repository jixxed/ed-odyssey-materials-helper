package nl.jixxed.eliteodysseymaterials.service.hge;

import java.util.ArrayList;
import java.util.List;

public class FixedSizeCircularReference<T> {
    T[] entries;

    public FixedSizeCircularReference(int size) {
        this.entries = (T[]) new Object[size];
        this.size = size;
    }
    int cur = 0;
    int size;

    public synchronized void add(T entry) {
        entries[cur++] = entry;
        if (cur >= size) {
            cur = 0;
        }
    }

    public synchronized List<T> asList() {
        int c = cur;
        int s = size;
        T[] e =  entries;
        List<T> list = new ArrayList<>();
        int oldest = (c == s - 1) ? 0 : c;
        for (int i = 0; i < e.length; i++) {
            var entry = e[oldest + i < s ? oldest + i : oldest + i - s];
            if (entry != null) list.add(entry);
        }
        return list;
    }
}
