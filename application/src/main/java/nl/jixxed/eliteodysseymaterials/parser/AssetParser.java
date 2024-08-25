package nl.jixxed.eliteodysseymaterials.parser;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.parser.mapping.MaterialMapping;
import nl.jixxed.eliteodysseymaterials.service.ReportService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.List;

@Slf4j
public class AssetParser implements Parser<MaterialMapping> {
    @Override
    public void parse(final List<MaterialMapping> components, final StoragePool storagePool) {
        components.forEach(component ->
        {
            final String name = component.getName();
            final Asset asset = Asset.forName(name);
            final int amount = component.getCount().intValue();
            if (asset.isUnknown()) {
                log.warn("Unknown Asset detected: " + component);
                ReportService.reportMaterial(component);
            } else {
                StorageService.addMaterial(asset, storagePool, amount);
            }
        });
    }
}
