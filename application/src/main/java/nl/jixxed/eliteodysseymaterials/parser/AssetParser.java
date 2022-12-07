package nl.jixxed.eliteodysseymaterials.parser;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.parser.mapping.MaterialMapping;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;

import java.util.List;
import java.util.Map;

@Slf4j
public class AssetParser implements Parser<MaterialMapping> {
    @Override
    public void parse(final List<MaterialMapping> components, final StoragePool storagePool, final Map<? extends OdysseyMaterial, Storage> knownMap) {
        components.forEach(component ->
        {
            final String name = component.getName();
            final Asset asset = Asset.forName(name);
            final int amount = component.getCount().intValue();
            if (asset.isUnknown()) {
                log.warn("Unknown Asset detected: " + component);
                NotificationService.showWarning(NotificationType.ERROR, "Unknown Material Detected", name + "\nPlease report!");
            } else {
                final Storage storage = knownMap.get(asset);
                //stack values as items occur multiple times in the json
                storage.setValue(storage.getValue(storagePool) + amount, storagePool);
            }
        });
    }
}
