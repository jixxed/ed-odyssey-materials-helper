package nl.jixxed.eliteodysseymaterials.parser;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.List;

@Slf4j
public class CarrierAssetParser {
    public void parse(List<nl.jixxed.eliteodysseymaterials.schemas.capi.fleetcarrier.Asset> assets, StoragePool storagePool) {
        if(assets == null) {
            return;
        }
        assets.forEach(capiAsset ->
        {
            final String name = capiAsset.getName();
            final Asset asset = Asset.forName(name);
            final int amount = capiAsset.getQuantity().intValue();
            if (asset.isUnknown()) {
                log.warn("Unknown Asset detected: " + capiAsset);
            } else {
                StorageService.addMaterial(asset, storagePool, amount);
            }
        });
    }
}
