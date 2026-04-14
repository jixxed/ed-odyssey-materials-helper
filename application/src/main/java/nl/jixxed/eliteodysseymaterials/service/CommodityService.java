/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.schemas.commodity.CommodityStat;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class CommodityService {

    private static final Map<Commodity, CommodityStat> COMMODITIES = new HashMap<>();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String COMMODITIES_FILE_PATH = OsConstants.getConfigDirectory() + OsConstants.getOsSlash() + "commodities.json";

    static {
        File statsFile = new File(COMMODITIES_FILE_PATH);
        if (statsFile.exists()) {
            update(COMMODITIES_FILE_PATH);
        }
    }

    private static void update(InputStream inputStream) {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            COMMODITIES.clear();
            while (reader.ready()) {
                final String line = reader.readLine();
                final List<CommodityStat> commodityStats = OBJECT_MAPPER.readValue(line, new TypeReference<List<CommodityStat>>(){});
                commodityStats.forEach(commodityStat -> {
                    COMMODITIES.put(Commodity.forName(commodityStat.getCommodityName()), commodityStat);
                });
            }
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void update(String file) {
        try (var stream = new FileInputStream(file)) {
            update(stream);
        } catch (final Exception ex) {
            log.error("Failed to update commodity stats.", ex);
        }
    }

    public static Optional<CommodityStat> getCommodityStat(final Commodity commodity) {
        return Optional.ofNullable(COMMODITIES.get(commodity));
    }
}
