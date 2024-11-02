package nl.jixxed.eliteodysseymaterials.parser;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.parser.mapping.MaterialMapping;
import nl.jixxed.eliteodysseymaterials.service.ReportService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.List;

@Slf4j
public class DataParser implements Parser<MaterialMapping> {
    @Override
    public void parse(final List<MaterialMapping> datas, final StoragePool storagePool) {
        datas.forEach(dataVal ->
        {
            final String name = dataVal.getName();
            final Data data = Data.forName(name);
            final int amount = dataVal.getCount().intValue();
            if (data.isUnknown() || Data.POWERMEGASHIPDATA.equals(data)) {
                log.warn("Unknown Data detected: " + dataVal);
                ReportService.reportMaterial(dataVal);
            } else {
                StorageService.addMaterial(data, storagePool, amount);
            }
        });
    }
}
