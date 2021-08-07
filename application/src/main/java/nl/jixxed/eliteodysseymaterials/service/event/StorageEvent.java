package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;

/**
 * Event indicating a mutation has been made to the storage. the storagePool indicates the type of storage that was changed.
 */
@AllArgsConstructor
@Getter
public class StorageEvent implements Event {
    private final StoragePool storagePool;
}
