package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GuardianStructureService {
    private static final List<GuardianStructure> GUARDIAN_STRUCTURES = new ArrayList<>();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        final InputStream inputStream = GuardianStructureService.class.getResourceAsStream("/poi/guardian.jsonl");
        update(inputStream);
    }

    private static void update(InputStream inputStream) {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            GUARDIAN_STRUCTURES.clear();
            while (reader.ready()) {
                final String line = reader.readLine();
                final GuardianStructureJson jsonBroker = OBJECT_MAPPER.readValue(line, GuardianStructureJson.class);
                GUARDIAN_STRUCTURES.add(new GuardianStructure(new StarSystem(jsonBroker.getName(), jsonBroker.getCoords().getX(), jsonBroker.getCoords().getY(), jsonBroker.getCoords().getZ()), jsonBroker.getBody(), GuardianStructureLayout.forName(jsonBroker.getLayout())));
            }
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static GuardianStructure findClosest(final StarSystem currentLocation, final List<GuardianStructureLayout> guardianStructureLayouts) {
        return GUARDIAN_STRUCTURES.stream()
                .filter(guardianStructure -> guardianStructureLayouts.contains(guardianStructure.getLayout()))
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
            log.error("Failed to update guardian structures.", ex);
        }
    }
}
