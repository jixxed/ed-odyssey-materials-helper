package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import java.util.HashMap;
import java.util.Map;

public class Table<R, C, V> {

    Map<R, Map<C, V>> backingMap;

    public Table() {
        backingMap = new HashMap<>();
    }

    public void add(R row, Map<C, V> values) {
        backingMap.put(row, values);
    }

    public Map<R, Map<C, V>> getValues() {
        return backingMap;
    }

}
