package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;

@AllArgsConstructor
@Getter
public class StorageEvent implements Event {
    private final StoragePool storagePool;
}
