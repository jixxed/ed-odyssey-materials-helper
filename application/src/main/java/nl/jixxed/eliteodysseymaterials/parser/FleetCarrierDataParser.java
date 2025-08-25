package nl.jixxed.eliteodysseymaterials.parser;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.schemas.capi.fleetcarrier.Datum;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.List;

@Slf4j
public class FleetCarrierDataParser {
    public void parse(List<Datum> datas, StoragePool storagePool) {
        if(datas == null) {
            return;
        }
        datas.forEach(capiData ->
        {
            final String name = capiData.getName();
            final Data data = Data.forName(name);
            final int amount = capiData.getQuantity().intValue();
            if (data.isUnknown()) {
                log.warn("Unknown Data detected: " + capiData);
            } else {
                StorageService.addMaterial(data, storagePool, amount);
            }
        });
    }
}
