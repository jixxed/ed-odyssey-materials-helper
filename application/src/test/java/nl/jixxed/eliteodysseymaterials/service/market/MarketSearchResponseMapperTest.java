package nl.jixxed.eliteodysseymaterials.service.market;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MarketSearchResponseMapperTest {

    @Test
    void getLastMarketUpdate() {
        String update = "2025-06-23 19:26:32+00";
        ZonedDateTime lastMarketUpdate = MarketSearchResponseMapper.getLastMarketUpdate(update);
        assertEquals(2025, lastMarketUpdate.getYear());
        assertEquals(6, lastMarketUpdate.getMonthValue());
        assertEquals(23, lastMarketUpdate.getDayOfMonth());
        assertEquals(19, lastMarketUpdate.getHour());
        assertEquals(26, lastMarketUpdate.getMinute());
        assertEquals(32, lastMarketUpdate.getSecond());

    }
}