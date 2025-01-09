package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.domain.BrokerTraderJournalEvent;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.parser.EncodedTradeParser;
import nl.jixxed.eliteodysseymaterials.parser.ManufacturedTradeParser;
import nl.jixxed.eliteodysseymaterials.parser.RawTradeParser;
import nl.jixxed.eliteodysseymaterials.schemas.journal.MaterialTrade.MaterialTrade;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.TraderBrokerService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

import java.math.BigInteger;

public class MaterialTradeMessageProcessor implements MessageProcessor<MaterialTrade> {
    private static final RawTradeParser RAW_TRADE_PARSER = new RawTradeParser();
    private static final EncodedTradeParser ENCODED_TRADE_PARSER = new EncodedTradeParser();
    private static final ManufacturedTradeParser MANUFACTURED_TRADE_PARSER = new ManufacturedTradeParser();

    private static BigInteger marketId = BigInteger.ZERO;

    @Override
    public void process(final MaterialTrade event) {
        //we only send events once per station visit
        if(!marketId.equals(LocationService.getMarketID())) {
            marketId = LocationService.getMarketID();
            TraderBrokerService.sendBrokerTraderEvent(new BrokerTraderJournalEvent(
                    event.getTimestamp(),
                    event.getEvent(),
                    event.getMarketID(),
                    event.getTraderType()
            ));
        }
        final String category = event.getTraderType();
        switch (category) {
            case "raw" ->
                    RAW_TRADE_PARSER.parse(event);
            case "encoded" ->
                    ENCODED_TRADE_PARSER.parse(event);
            case "manufactured" ->
                    MANUFACTURED_TRADE_PARSER.parse(event);
        }
        EventService.publish(new StorageEvent(StoragePool.SHIP));

    }

    @Override
    public Class<MaterialTrade> getMessageClass() {
        return MaterialTrade.class;
    }
}