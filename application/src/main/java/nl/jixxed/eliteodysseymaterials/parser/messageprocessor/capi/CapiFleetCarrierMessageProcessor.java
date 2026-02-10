package nl.jixxed.eliteodysseymaterials.parser.messageprocessor.capi;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.CarrierDockingAccess;
import nl.jixxed.eliteodysseymaterials.enums.CarrierState;
import nl.jixxed.eliteodysseymaterials.enums.CarrierType;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.parser.*;
import nl.jixxed.eliteodysseymaterials.schemas.capi.fleetcarrier.CapiFleetcarrier;
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

import java.math.BigInteger;

@Slf4j
public class CapiFleetCarrierMessageProcessor implements CapiMessageProcessor<CapiFleetcarrier> {
    private static final CarrierAssetParser ASSET_PARSER = new CarrierAssetParser();
    private static final CarrierDataParser DATA_PARSER = new CarrierDataParser();
    private static final CarrierGoodParser GOOD_PARSER = new CarrierGoodParser();
    private static final CarrierCommodityParser COMMODITY_PARSER = new CarrierCommodityParser();
    private static final CarrierBuyOrderParser BUY_ORDER_PARSER = new CarrierBuyOrderParser();
    private static final CarrierSellOrderParser SELL_ORDER_PARSER = new CarrierSellOrderParser();

    @Override
    public void process(final CapiFleetcarrier capiFleetcarrier) {

        StorageService.resetFleetCarrierCounts();
        OrderService.clearOrders(StoragePool.FLEETCARRIER);

        capiFleetcarrier.getOrders().getOnfootmicroresources().getSales().ifPresent(sales -> SELL_ORDER_PARSER.parseOdyssey(sales, StoragePool.FLEETCARRIER));
        capiFleetcarrier.getOrders().getOnfootmicroresources().getPurchases().ifPresent(sales -> BUY_ORDER_PARSER.parseOdyssey(sales, StoragePool.FLEETCARRIER));
        capiFleetcarrier.getOrders().getCommodities().getSales().ifPresent(sales -> SELL_ORDER_PARSER.parse(sales, StoragePool.FLEETCARRIER));
        capiFleetcarrier.getOrders().getCommodities().getPurchases().ifPresent(purchases -> BUY_ORDER_PARSER.parse(purchases, StoragePool.FLEETCARRIER));

        ASSET_PARSER.parse(capiFleetcarrier.getCarrierLocker().getAssets(), StoragePool.FLEETCARRIER);
        GOOD_PARSER.parse(capiFleetcarrier.getCarrierLocker().getGoods(), StoragePool.FLEETCARRIER);
        DATA_PARSER.parse(capiFleetcarrier.getCarrierLocker().getData(), StoragePool.FLEETCARRIER);
        capiFleetcarrier.getCargo().ifPresent(cargos -> COMMODITY_PARSER.parse(cargos, StoragePool.FLEETCARRIER));

            capiFleetcarrier.getMarket().ifPresent(market -> {
                try {final BigInteger marketId = market.getId();
                    UserPreferencesService.setPreference(PreferenceConstants.FLEET_CARRIER_ID, marketId.toString());
                } catch (NullPointerException e) {
                    log.error("Fleet carrier market id not found in CAPI message", e);
                }
            });

        EventService.publish(new StorageEvent(StoragePool.FLEETCARRIER));
        CarrierService.carrierExistsProperty(CarrierType.FLEETCARRIER).set(true);
        String encodedName = capiFleetcarrier.getName().getFilteredVanityName();
        String decodedName = new String(new java.math.BigInteger(encodedName, 16).toByteArray());
        CarrierService.setCarrierName(CarrierType.FLEETCARRIER, decodedName);
        CarrierService.setCarrierCallSign(CarrierType.FLEETCARRIER, capiFleetcarrier.getName().getCallsign());
        CarrierService.setCarrierState(CarrierType.FLEETCARRIER, CarrierState.forKey(capiFleetcarrier.getState()));
        CarrierService.setCarrierDockingAccess(CarrierType.FLEETCARRIER, CarrierDockingAccess.forKey(capiFleetcarrier.getDockingAccess()));
        CarrierService.setCarrierNotoriousAccess(CarrierType.FLEETCARRIER, capiFleetcarrier.getNotoriousAccess());
        CarrierService.setCarrierBalance(CarrierType.FLEETCARRIER, new BigInteger(capiFleetcarrier.getBalance()));
        CarrierService.setCarrierFuel(CarrierType.FLEETCARRIER, Integer.valueOf(capiFleetcarrier.getFuel()));
        CarrierService.setCarrierCapacity(CarrierType.FLEETCARRIER, capiFleetcarrier.getCapacity());
        if(CarrierState.forKey(capiFleetcarrier.getState()) == CarrierState.UNKNOWN){
            ReportService.reportJournal("module", capiFleetcarrier.getState(), "Unknown carrier state");
        }
    }

    @Override
    public Class<CapiFleetcarrier> getMessageClass() {
        return CapiFleetcarrier.class;
    }

    @Override
    public void clear() {
        StorageService.resetFleetCarrierCounts();
        OrderService.clearOrders(StoragePool.FLEETCARRIER);
        EventService.publish(new StorageEvent(StoragePool.FLEETCARRIER));
    }
}
