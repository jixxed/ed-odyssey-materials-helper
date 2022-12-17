package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsStorageType;
import nl.jixxed.eliteodysseymaterials.enums.MaterialTrader;
import nl.jixxed.eliteodysseymaterials.enums.MaterialTraderJson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialTraderService {
    private static final List<MaterialTrader> MATERIAL_TRADERS = new ArrayList<>();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {

        final InputStream inputStream = MaterialTraderService.class.getResourceAsStream("/materialtrader/traders.json");

        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            while (reader.ready()) {
                final String line = reader.readLine();
                final MaterialTraderJson jsonTrader = OBJECT_MAPPER.readValue(line, MaterialTraderJson.class);
                MATERIAL_TRADERS.add(new MaterialTrader(new StarSystem(jsonTrader.getName(), jsonTrader.getCoords().getX(), jsonTrader.getCoords().getY(), jsonTrader.getCoords().getZ()), jsonTrader.getStation().getName(), jsonTrader.getStation().getDistanceToArrival(), HorizonsStorageType.forName(jsonTrader.getStation().getType())));
            }
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    public static MaterialTrader findClosest(final StarSystem currentLocation, final HorizonsStorageType type) {
        final Integer range = PreferencesService.getPreference(PreferenceConstants.HORIZONS_MATERIAL_TRADER_MAX_RANGE, 5000);
        return MATERIAL_TRADERS.stream()
                .filter(materialTrader -> materialTrader.getType().equals(type) && materialTrader.getDistanceFromStar() <= range)
                .min(Comparator.comparing(materialTrader -> getDistance(materialTrader.getStarSystem(), currentLocation)))
                .orElseThrow(IllegalArgumentException::new);
    }

    public static Double getDistance(final StarSystem location1, final StarSystem location2) {
        return Math.sqrt(Math.pow(location1.getX() - location2.getX(), 2) + Math.pow(location1.getY() - location2.getY(), 2) + Math.pow(location1.getZ() - location2.getZ(), 2));
    }
}
