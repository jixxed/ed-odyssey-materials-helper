package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.schemas.capi.fleetcarrier.Purchase;
import nl.jixxed.eliteodysseymaterials.schemas.capi.fleetcarrier.Purchase__1;
import nl.jixxed.eliteodysseymaterials.service.OrderService;
import nl.jixxed.eliteodysseymaterials.service.ReportService;

import java.util.List;

@Slf4j
public class CarrierBuyOrderParser {

    public void parseOdyssey(List<Purchase__1> purchases, StoragePool storagePool) {
        if(purchases == null) {
            return;
        }
        purchases.forEach(purchase -> {
            try {
                OrderService.addBuyOrder(OdysseyMaterial.subtypeForName(purchase.getName()), purchase.getPrice().intValue(), purchase.getTotal().intValue(), purchase.getOutstanding().intValue(), storagePool);
            } catch (final IllegalArgumentException ex) {
                log.error(ex.getMessage());
                try {
                    ReportService.reportMaterial(new ObjectMapper().writeValueAsString(purchase));
                } catch (JsonProcessingException e) {
                    log.error(e.getMessage());
                }
            }
        });

    }

    public void parse(List<Purchase> purchases, StoragePool storagePool) {
        if(purchases == null) {
            return;
        }
        purchases.forEach(purchase -> {
            try {
                OrderService.addBuyOrder(HorizonsMaterial.subtypeForName(purchase.getName()), purchase.getPrice().intValue(), purchase.getTotal().intValue(), purchase.getOutstanding().intValue(), storagePool);
            } catch (final IllegalArgumentException ex) {
                log.error(ex.getMessage());
                try {
                    ReportService.reportMaterial(new ObjectMapper().writeValueAsString(purchase));
                } catch (JsonProcessingException e) {
                    log.error(e.getMessage());
                }
            }
        });
    }
}
