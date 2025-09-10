package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.schemas.capi.fleetcarrier.Sale;
import nl.jixxed.eliteodysseymaterials.schemas.capi.fleetcarrier.SaleItem;
import nl.jixxed.eliteodysseymaterials.service.OrderService;
import nl.jixxed.eliteodysseymaterials.service.ReportService;

import java.util.List;
import java.util.Map;

@Slf4j
public class CarrierSellOrderParser {

    public void parseOdyssey(Map<String, SaleItem> sales, StoragePool storagePool) {
        if(sales == null) {
            return;
        }
        sales.forEach((s, saleItem) -> {
            try {
                OrderService.addSellOrder(OdysseyMaterial.subtypeForName(saleItem.getName()), saleItem.getPrice().intValue(), saleItem.getStock().intValue(), storagePool);
            } catch (final IllegalArgumentException ex) {
                log.error(ex.getMessage());
                try {
                    ReportService.reportMaterial(new ObjectMapper().writeValueAsString(saleItem));
                } catch (JsonProcessingException e) {
                    log.error(e.getMessage());
                }
            }
        });
    }

    public void parse(List<Sale> sales, StoragePool storagePool) {
        if(sales == null) {
            return;
        }
        sales.forEach(sale -> {
            try {
                OrderService.addSellOrder(HorizonsMaterial.subtypeForName(sale.getName()), Integer.parseInt(sale.getPrice()), Integer.parseInt(sale.getStock()), storagePool);
            } catch (NumberFormatException ex){
                log.error(ex.getMessage());

            } catch (final IllegalArgumentException ex) {
                log.error(ex.getMessage());
                try {
                    ReportService.reportMaterial(new ObjectMapper().writeValueAsString(sale));
                } catch (JsonProcessingException e) {
                    log.error(e.getMessage());
                }
            }
        });
    }
}
