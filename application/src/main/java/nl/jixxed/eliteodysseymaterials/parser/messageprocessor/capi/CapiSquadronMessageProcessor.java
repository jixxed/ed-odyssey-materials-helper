package nl.jixxed.eliteodysseymaterials.parser.messageprocessor.capi;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.SquadronPerk;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.parser.*;
import nl.jixxed.eliteodysseymaterials.schemas.capi.squadron.CapiSquadron;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.UserPreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

import java.math.BigInteger;

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
//        OrderService.clearOrders();
        data.getSquadronCarrier().ifPresent(squadronCarrier -> {
            SELL_ORDER_PARSER.parseOdyssey(squadronCarrier.getOrders().getOnfootmicroresources().getSales(), StoragePool.SQUADRONCARRIER);
            BUY_ORDER_PARSER.parseOdyssey(squadronCarrier.getOrders().getOnfootmicroresources().getPurchases(), StoragePool.SQUADRONCARRIER);
//        SELL_ORDER_PARSER.parse(data.getSquadronCarrier().getOrders().getCommodities().getSales());
//        BUY_ORDER_PARSER.parse(data.getSquadronCarrier().getOrders().getCommodities().getPurchases());

            ASSET_PARSER.parse(squadronCarrier.getCarrierLocker().getAssets(), StoragePool.SQUADRONCARRIER);
            GOOD_PARSER.parse(squadronCarrier.getCarrierLocker().getGoods(), StoragePool.SQUADRONCARRIER);
            DATA_PARSER.parse(squadronCarrier.getCarrierLocker().getData(), StoragePool.SQUADRONCARRIER);

            COMMODITY_PARSER.parse(squadronCarrier.getCargo(), StoragePool.SQUADRONCARRIER);
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
        EventService.publish(new StorageEvent(StoragePool.SQUADRONCARRIER));
    }
}
