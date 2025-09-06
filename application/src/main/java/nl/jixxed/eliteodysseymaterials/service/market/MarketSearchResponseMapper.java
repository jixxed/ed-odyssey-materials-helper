package nl.jixxed.eliteodysseymaterials.service.market;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.schemas.market.search.Market;
import nl.jixxed.eliteodysseymaterials.schemas.market.search.MarketSearchResponse;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class MarketSearchResponseMapper {

    public static List<MarketStation> mapToStations(final MarketSearchResponse MarketSearchResponse) {
        return MarketSearchResponse.getResults().stream()
                .map(result -> {
                    try{
                        StarSystem starSystem = new StarSystem(result.getSystem_name(), result.getSystem_x().doubleValue(), result.getSystem_y().doubleValue(), result.getSystem_z().doubleValue());
                        Optional<Market> marketData = result.getMarket().stream().filter(market -> market.getCommodity().equals(MarketSearchResponse.getSearch().getFilters().getMarket().getFirst().getName())).findFirst();
                        return new MarketStation(
                                starSystem,
                                result.getName(),
                                result.getDistance_to_arrival().doubleValue(),
                                marketData.map(market -> market.getBuy_price().longValue()).orElse(0L),
                                marketData.map(market -> market.getSupply().longValue()).orElse(0L),
                                getLastMarketUpdate(result.getMarket_updated_at())
                        );

                    }catch (final Exception e) {
                        //ignore
                        log.error("No market data for station {} in system {}", result.getName(), result.getSystem_name(), e);
                        return null;
                    }
                    })
                .filter(Objects::nonNull)
                .toList();
    }

    protected static ZonedDateTime getLastMarketUpdate(String update) {
        if (update.endsWith("+00")) {
            update = update + ":00";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX");
        return ZonedDateTime.parse(update, formatter);
    }
}
