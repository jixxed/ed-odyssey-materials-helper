package nl.jixxed.eliteodysseymaterials.parser;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.schemas.capi.fleetcarrier.Cargo;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.List;

@Slf4j
public class FleetCarrierCommodityParser {
    public void parse(List<Cargo> cargos) {
        if(cargos == null) {
            return;
        }
        cargos.forEach(cargo ->
        {
            final String name = cargo.getCommodity();
            final Commodity commodity = Commodity.forName(name);
            final int amount = cargo.getQty().intValue();
            if (commodity.isUnknown()) {
                log.warn("Unknown Commodity detected: " + cargo);
            } else {
                StorageService.addCommodity(commodity, StoragePool.FLEETCARRIER, amount);
            }
        });
    }
}
