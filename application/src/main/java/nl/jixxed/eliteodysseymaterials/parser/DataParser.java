package nl.jixxed.eliteodysseymaterials.parser;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.parser.mapping.MaterialMapping;
import nl.jixxed.eliteodysseymaterials.service.ReportService;

import java.util.List;
import java.util.Map;

@Slf4j
public class DataParser implements Parser<MaterialMapping> {
    @Override
    public void parse(final List<MaterialMapping> datas, final StoragePool storagePool, final Map<? extends OdysseyMaterial, Storage> knownMap) {
        datas.forEach(dataVal ->
        {
            final String name = dataVal.getName();
            final Data data = Data.forName(name);
            final int amount = dataVal.getCount().intValue();
            if (data.isUnknown()) {
                log.warn("Unknown Data detected: " + dataVal);
                ReportService.reportMaterial(dataVal);
            } else {
                final Storage storage = knownMap.get(data);
                //stack values as items occur multiple times in the json
                storage.setValue(storage.getValue(storagePool) + amount, storagePool);
            }
        });
    }
}
