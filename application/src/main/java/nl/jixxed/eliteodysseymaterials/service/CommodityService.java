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
