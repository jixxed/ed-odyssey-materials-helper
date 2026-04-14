/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
//                log.debug("Adding price for {} with value {}", key, price);
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
