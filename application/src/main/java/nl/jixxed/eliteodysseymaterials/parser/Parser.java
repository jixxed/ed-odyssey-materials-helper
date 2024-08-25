package nl.jixxed.eliteodysseymaterials.parser;

import nl.jixxed.eliteodysseymaterials.enums.StoragePool;

import java.util.List;

public interface Parser<E> {
    void parse(final List<E> items, final StoragePool storagePool);

}
