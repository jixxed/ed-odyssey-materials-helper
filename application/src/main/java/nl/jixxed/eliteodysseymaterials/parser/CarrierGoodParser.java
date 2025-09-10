package nl.jixxed.eliteodysseymaterials.parser;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Good;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.List;

@Slf4j
public class CarrierGoodParser {
    public void parse(List<nl.jixxed.eliteodysseymaterials.schemas.capi.fleetcarrier.Good> goods, StoragePool storagePool) {
        if(goods == null) {
            return;
        }
        goods.forEach(capiGood ->
        {
            final String name = capiGood.getName();
            final Good good = Good.forName(name);
            final int amount = capiGood.getQuantity().intValue();
            if (good.isUnknown()) {
                log.warn("Unknown Good detected: " + capiGood);
            } else {
                StorageService.addMaterial(good, storagePool, amount);
            }
        });
    }
}
