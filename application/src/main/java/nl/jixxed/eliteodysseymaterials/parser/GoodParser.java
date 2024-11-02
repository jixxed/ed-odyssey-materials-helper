package nl.jixxed.eliteodysseymaterials.parser;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Good;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.parser.mapping.MaterialMapping;
import nl.jixxed.eliteodysseymaterials.service.ReportService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.List;

@Slf4j
public class GoodParser implements Parser<MaterialMapping> {
    @Override
    public void parse(final List<MaterialMapping> items, final StoragePool storagePool) {
        items.forEach(item ->
        {
            final String name = item.getName();
            final Good good = Good.forName(name);
            final int amount = item.getCount().intValue();
            if (good.isUnknown() || Good.POWERRESEARCH.equals(good)) {
                log.warn("Unknown Good detected: " + item);
                ReportService.reportMaterial(item);
            } else {
                StorageService.addMaterial(good, storagePool, amount);
            }
        });
    }
}
