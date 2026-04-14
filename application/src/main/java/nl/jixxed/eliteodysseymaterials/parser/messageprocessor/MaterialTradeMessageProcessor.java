/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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