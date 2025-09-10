package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.schemas.journal.CarrierTradeOrder.CarrierTradeOrder;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.OrderService;
import nl.jixxed.eliteodysseymaterials.service.UserPreferencesService;

import java.math.BigInteger;

@Slf4j
public class CarrierTradeOrderMessageProcessor implements MessageProcessor<CarrierTradeOrder> {

    //{ "timestamp":"2025-06-21T09:53:20Z", "event":"CarrierTradeOrder", "CarrierID":3705689344, "BlackMarket":false, "Commodity":"emergencypowercells", "Commodity_Localised":"Emergency Power Cells", "PurchaseOrder":227, "Price":2308 }
    //{ "timestamp":"2025-06-21T10:30:25Z", "event":"CarrierTradeOrder", "CarrierID":3705689344, "BlackMarket":false, "Commodity":"tritium", "SaleOrder":965, "Price":51294 }
    //{ "timestamp":"2025-06-21T10:30:30Z", "event":"CarrierTradeOrder", "CarrierID":3705689344, "BlackMarket":false, "Commodity":"tritium", "CancelTrade":true }
    @Override
    public void process(final CarrierTradeOrder event) {
        String fleetCarrierID = UserPreferencesService.getPreference(PreferenceConstants.FLEET_CARRIER_ID, "-1");
        String squadronCarrierID = UserPreferencesService.getPreference(PreferenceConstants.SQUADRON_CARRIER_ID, "-1");
        StoragePool storagePool = fleetCarrierID.equals(event.getCarrierID().toString()) ? StoragePool.FLEETCARRIER : squadronCarrierID.equals(event.getCarrierID().toString()) ? StoragePool.SQUADRONCARRIER : null;
        if (storagePool != null) {
            try {
                if (event.getCancelTrade().isPresent()) {
                    OrderService.removeSellOrder(HorizonsMaterial.subtypeForName(event.getCommodity()), storagePool);
                    OrderService.removeBuyOrder(HorizonsMaterial.subtypeForName(event.getCommodity()), storagePool);
                } else if (event.getSaleOrder().isPresent()) {
                    OrderService.addSellOrder(HorizonsMaterial.subtypeForName(event.getCommodity()), event.getPrice().orElse(BigInteger.ZERO).intValue(), event.getSaleOrder().orElse(BigInteger.ZERO).intValue(), storagePool);
                } else if (event.getPurchaseOrder().isPresent()) {
                    OrderService.addBuyOrder(HorizonsMaterial.subtypeForName(event.getCommodity()), event.getPrice().orElse(BigInteger.ZERO).intValue(), event.getPurchaseOrder().orElse(BigInteger.ZERO).intValue(), event.getPurchaseOrder().orElse(BigInteger.ZERO).intValue(), storagePool);
                }
            } catch (final IllegalArgumentException e) {
                log.error(e.getMessage());
            }
        }
    }

    @Override
    public Class<CarrierTradeOrder> getMessageClass() {
        return CarrierTradeOrder.class;
    }
}