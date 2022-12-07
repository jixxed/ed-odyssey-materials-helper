package nl.jixxed.eliteodysseymaterials.parser;

import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;

import java.util.List;
import java.util.Map;

public interface Parser<E> {
    void parse(final List<E> items, final StoragePool storagePool, Map<? extends OdysseyMaterial, Storage> knownMap);

}
