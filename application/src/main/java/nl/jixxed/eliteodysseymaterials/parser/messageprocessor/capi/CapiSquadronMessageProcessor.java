package nl.jixxed.eliteodysseymaterials.parser.messageprocessor.capi;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.parser.*;
import nl.jixxed.eliteodysseymaterials.schemas.capi.squadron.CapiSquadron;
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

import java.math.BigInteger;
import java.util.Arrays;

@Slf4j
public class CapiSquadronMessageProcessor implements CapiMessageProcessor<CapiSquadron> {
    private static final CarrierAssetParser ASSET_PARSER = new CarrierAssetParser();
    private static final CarrierDataParser DATA_PARSER = new CarrierDataParser();
    private static final CarrierGoodParser GOOD_PARSER = new CarrierGoodParser();
    private static final CarrierCommodityParser COMMODITY_PARSER = new CarrierCommodityParser();
    private static final CarrierBankParser BANK_PARSER = new CarrierBankParser();
    private static final CarrierBuyOrderParser BUY_ORDER_PARSER = new CarrierBuyOrderParser();
    private static final CarrierSellOrderParser SELL_ORDER_PARSER = new CarrierSellOrderParser();

    @Override
    public void process(CapiSquadron data) {
        SquadronPerk primaryPerk = SquadronPerk.forId(data.getPerks().getPrimary());
        SquadronPerk factionPerk = SquadronPerk.forId(data.getPerks().getSecondary());
        ApplicationState.getInstance().setPrimaryPerk(primaryPerk);
        ApplicationState.getInstance().setFactionPerk(factionPerk);

        StorageService.resetSquadronCarrierCounts();
        OrderService.clearOrders(StoragePool.SQUADRONCARRIER);
        data.getSquadronCarrier().ifPresent(squadronCarrier -> {
            SELL_ORDER_PARSER.parseOdyssey(squadronCarrier.getOrders().getOnfootmicroresources().getSales(), StoragePool.SQUADRONCARRIER);
            BUY_ORDER_PARSER.parseOdyssey(squadronCarrier.getOrders().getOnfootmicroresources().getPurchases(), StoragePool.SQUADRONCARRIER);
            SELL_ORDER_PARSER.parse(squadronCarrier.getOrders().getCommodities().getSales(), StoragePool.SQUADRONCARRIER);
            BUY_ORDER_PARSER.parse(squadronCarrier.getOrders().getCommodities().getPurchases(), StoragePool.SQUADRONCARRIER);

            if (UserPreferencesService.getPreference(PreferenceConstants.CAPI_ENABLE_ODYSSEY_MATERIALS, true)) {
                ASSET_PARSER.parse(squadronCarrier.getCarrierLocker().getAssets(), StoragePool.SQUADRONCARRIER);
                GOOD_PARSER.parse(squadronCarrier.getCarrierLocker().getGoods(), StoragePool.SQUADRONCARRIER);
                DATA_PARSER.parse(squadronCarrier.getCarrierLocker().getData(), StoragePool.SQUADRONCARRIER);
            }

            if (UserPreferencesService.getPreference(PreferenceConstants.CAPI_ENABLE_HORIZONS_MATERIALS, true)) {
                COMMODITY_PARSER.parse(squadronCarrier.getCargo(), StoragePool.SQUADRONCARRIER);
            }

            CarrierService.carrierExistsProperty(CarrierType.SQUADRONCARRIER).set(true);
            String encodedName = squadronCarrier.getName().getFilteredVanityName();
            String decodedName = new String(new java.math.BigInteger(encodedName, 16).toByteArray());
            CarrierService.setCarrierName(CarrierType.SQUADRONCARRIER, decodedName);
            CarrierService.setCarrierCallSign(CarrierType.SQUADRONCARRIER, squadronCarrier.getName().getCallsign());
            CarrierService.setCarrierState(CarrierType.SQUADRONCARRIER, CarrierState.forKey(squadronCarrier.getState()));
            CarrierService.setCarrierDockingAccess(CarrierType.SQUADRONCARRIER, CarrierDockingAccess.forKey(squadronCarrier.getDockingAccess()));
            CarrierService.setCarrierNotoriousAccess(CarrierType.SQUADRONCARRIER, squadronCarrier.getNotoriousAccess());
            CarrierService.setCarrierBalance(CarrierType.SQUADRONCARRIER, new BigInteger(squadronCarrier.getBalance()));
            BigInteger carrierBankBalance = data.getBank().getCredits().entrySet().stream()
                    .filter(entry -> !"Carrier Balance".equals(entry.getKey()))
                    .flatMap(entry -> Arrays.stream(entry.getValue()))
                    .map(bankItem -> new BigInteger(bankItem.getQty()))
                    .reduce(BigInteger.ZERO, BigInteger::add);
            CarrierService.setCarrierBankBalance(CarrierType.SQUADRONCARRIER, carrierBankBalance);
            CarrierService.setCarrierFuel(CarrierType.SQUADRONCARRIER, Integer.valueOf(squadronCarrier.getFuel()));
            CarrierService.setCarrierCapacity(CarrierType.SQUADRONCARRIER, squadronCarrier.getCapacity());
//            "capacity": {
//                "shipPacks": 4950,
//                        "modulePacks": 105,
//                        "cargoForSale": 5128,
//                        "cargoNotForSale": 1825,
//                        "cargoSpaceReserved": 0,
//                        "crew": 6620,
//                        "freeSpace": 6372,
//                        "microresourceCapacityTotal": 1000,
//                        "microresourceCapacityFree": 700,
//                        "microresourceCapacityUsed": 300,
//                        "microresourceCapacityReserved": 0,
//                        "squadronBankTotal": 0
//            },
//            "capacity": {
//                "shipPacks": 0,
//                        "modulePacks": 0,
//                        "cargoForSale": 2,
//                        "cargoNotForSale": 891,
//                        "cargoSpaceReserved": 6,
//                        "crew": 5800,
//                        "freeSpace": 41719,
//                        "microresourceCapacityTotal": 1000,
//                        "microresourceCapacityFree": 1000,
//                        "microresourceCapacityUsed": 0,
//                        "microresourceCapacityReserved": 0,
//                        "squadronBankTotal": 11582
//            },
            if (CarrierState.forKey(squadronCarrier.getState()) == CarrierState.UNKNOWN) {
                ReportService.reportJournal("capi", squadronCarrier.getState(), "Unknown carrier state");
            }
        });
        //this probably still requires a carrier?
        BANK_PARSER.parse(data.getBank(), StoragePool.SQUADRONCARRIER);
        //currently there is no market on the carrier
//        try {
//            final BigInteger marketId = data.getSquadronCarrier().getMarket().getId();
//            UserPreferencesService.setPreference(PreferenceConstants.SQUADRON_CARRIER_ID, marketId.toString());
//        } catch (NullPointerException e) {
//            log.error("Squadron carrier market id not found in CAPI message", e);
//        }
        EventService.publish(new StorageEvent(StoragePool.SQUADRONCARRIER));
    }

    @Override
    public Class<CapiSquadron> getMessageClass() {
        return CapiSquadron.class;
    }

    @Override
    public void clear() {
        ApplicationState.getInstance().setPrimaryPerk(SquadronPerk.UNKNOWN);
        ApplicationState.getInstance().setFactionPerk(SquadronPerk.UNKNOWN);

        StorageService.resetSquadronCarrierCounts();
        OrderService.clearOrders(StoragePool.SQUADRONCARRIER);
        EventService.publish(new StorageEvent(StoragePool.SQUADRONCARRIER));
    }
}
