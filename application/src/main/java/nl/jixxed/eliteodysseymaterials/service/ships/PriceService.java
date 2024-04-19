package nl.jixxed.eliteodysseymaterials.service.ships;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.StreamSupport;
@Slf4j
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PriceService {
    private static final Map<String, Long> PRICES = HashMap.newHashMap(2000);

    static {
        try (final CSVParser csvParser = CSVFormat.DEFAULT.parse(new InputStreamReader(Objects.requireNonNull(PriceService.class.getResourceAsStream("/ships/prices.csv")), StandardCharsets.UTF_8))) {

            StreamSupport.stream(csvParser.spliterator(), false).forEach(csvRecord -> {
                final String key = csvRecord.get(0).trim().toLowerCase();
                final Long price = Long.parseLong(csvRecord.get(1).trim());
                log.debug("Adding price for {} with value {}", key, price);
                PRICES.put(key, price);
            });
        } catch (final IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static long getModulePriceOrDefault(final String internalName, final long basePrice) {
        return PRICES.getOrDefault(internalName.toLowerCase(), basePrice);
    }
}
