package nl.jixxed.eliteodysseymaterials.parser;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.Good;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.parser.mapping.MaterialMapping;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;

import java.util.List;
import java.util.Map;

@Slf4j
public class GoodParser implements Parser<MaterialMapping> {
    @Override
    public void parse(final List<MaterialMapping> items, final StoragePool storagePool, final Map<? extends OdysseyMaterial, Storage> knownMap) {
        items.forEach(item ->
        {
            final String name = item.getName();
            final Good good = Good.forName(name);
            final int amount = item.getCount().intValue();
            if (good.isUnknown()) {
                log.warn("Unknown Good detected: " + item);
                NotificationService.showWarning(NotificationType.ERROR, "Unknown Material Detected", name + "\nPlease report!");
            } else {
                final Storage storage = knownMap.get(good);
                //stack values as items occur multiple times in the json
                storage.setValue(storage.getValue(storagePool) + amount, storagePool);
            }
        });
    }
}
