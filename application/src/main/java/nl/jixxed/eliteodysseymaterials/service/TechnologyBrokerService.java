package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBrokerType;
import nl.jixxed.eliteodysseymaterials.enums.TechnologyBroker;
import nl.jixxed.eliteodysseymaterials.enums.TechnologyBrokerJson;

import java.io.*;
import java.util.*;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TechnologyBrokerService {
    private static final List<TechnologyBroker> TECHNOLOGY_BROKERS = new ArrayList<>();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String BROKERS_FILE_PATH = OsConstants.getConfigDirectory() + OsConstants.getOsSlash() + "brokers.jsonl";

    static {
        File brokersFile = new File(BROKERS_FILE_PATH);
        if (brokersFile.exists()) {
            update(BROKERS_FILE_PATH);
        } else {
            final InputStream inputStream = TechnologyBrokerService.class.getResourceAsStream("/technologybroker/brokers.jsonl");
            update(inputStream);
        }
    }

    private static void update(InputStream inputStream) {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            TECHNOLOGY_BROKERS.clear();
            while (reader.ready()) {
                final String line = reader.readLine();
                final TechnologyBrokerJson jsonBroker = OBJECT_MAPPER.readValue(line, TechnologyBrokerJson.class);
                TECHNOLOGY_BROKERS.add(new TechnologyBroker(new StarSystem(jsonBroker.getName(), jsonBroker.getCoords().getX(), jsonBroker.getCoords().getY(), jsonBroker.getCoords().getZ()), jsonBroker.getStation().getName(), jsonBroker.getStation().getDistanceToArrival(), jsonBroker.getStation().getVarianceToArrival(), HorizonsBrokerType.forDisplayName(jsonBroker.getStation().getType())));
            }
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    public static TechnologyBroker findClosest(final StarSystem currentLocation, final List<HorizonsBrokerType> horizonsBrokerTypes) {
        final Integer range = PreferencesService.getPreference(PreferenceConstants.HORIZONS_TECHNOLOGY_BROKER_MAX_RANGE, 5000);
        return TECHNOLOGY_BROKERS.stream()
                .filter(technologyBroker -> horizonsBrokerTypes.contains(technologyBroker.getType()) && technologyBroker.getDistanceFromStar() <= range)
                .filter(technologyBroker -> {
                    Optional<Engineer> engineerAtLocation = Arrays.stream(Engineer.values()).filter(engineer -> engineer.getStarSystem().equals(technologyBroker.getStarSystem()) && engineer.getSettlement().getSettlementName().equals(technologyBroker.getName())).findFirst();
                    return engineerAtLocation.map(engineer -> (engineer.isOdyssey()) ? ApplicationState.getInstance().isEngineerUnlocked(engineer) : ApplicationState.getInstance().isEngineerUnlockedExact(engineer))//only allow unlocked engineer stations
                            .orElse(true);// trader allowed if there is no engineer
                })
                .min(Comparator.comparing(technologyBroker -> getDistance(technologyBroker.getStarSystem(), currentLocation)))
                .orElseThrow(IllegalArgumentException::new);
    }

    public static Double getDistance(final StarSystem location1, final StarSystem location2) {
        return Math.sqrt(Math.pow(location1.getX() - location2.getX(), 2) + Math.pow(location1.getY() - location2.getY(), 2) + Math.pow(location1.getZ() - location2.getZ(), 2));
    }

    public static void update(String file) {
        try (var stream = new FileInputStream(file)) {
            update(stream);
        } catch (final Exception ex) {
            log.error("Failed to update technology brokers.", ex);
        }
    }
}
