/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
