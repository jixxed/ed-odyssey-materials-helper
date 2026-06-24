/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.domain.ships;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

class ShipFdevNameTest {

    private static final Path RESOURCES_DIR = Path.of("src/test/resources/ships/full");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // Special slots that appear in JSON but aren't defined as slots in the Ship class
    private static final Set<String> IGNORED_SLOTS = Set.of(
            "CargoHatch", "ShipCockpit", "VesselVoice", "PlanetaryApproachSuite"
    );

    private static final List<Arguments> TEST_CASES = loadTestCases();

    private static List<Arguments> loadTestCases() {
        try {
            return Files.list(RESOURCES_DIR)
                    .filter(p -> p.toString().endsWith(".json"))
                    .map(path -> {
                        Ship ship = loadShip(path);
                        return Arguments.of(path.getFileName().toString(), ship);
                    })
                    .filter(arg -> arg.get()[1] != null)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to list ship test resources", e);
        }
    }

    private static Stream<Arguments> testcases() {
        return TEST_CASES.stream();
    }

    private static Ship loadShip(Path path) {
        String filename = path.getFileName().toString();
        try (InputStream is = Files.newInputStream(path)) {
            JsonNode root = OBJECT_MAPPER.readTree(is);
            String shipInternalName = root.path("Ship").asText();
            ShipType shipType = ShipType.forInternalName(shipInternalName);
            return (Ship) Ship.class.getField(shipType.name()).get(null);
        } catch (Exception e) {
            System.err.println("WARNING: Failed to load " + filename + ": " + e.getMessage());
            return null;
        }
    }

    @ParameterizedTest
    @MethodSource("testcases")
    void allJsonSlotNamesMatchShipFdevNames(String filename, Ship ship) {
        Assertions.assertNotNull(ship, "Ship must not be null for " + filename);

        try (InputStream is = Files.newInputStream(RESOURCES_DIR.resolve(filename))) {
            JsonNode root = OBJECT_MAPPER.readTree(is);
            JsonNode modules = root.path("Modules");

            Set<String> jsonSlotNames = collectSlotNames(modules);
            Map<String, SlotType> shipFdevNames = collectAllSlotFdevNames(ship);

            // Every JSON slot name (excluding ignored special slots) must exist in the Ship definition
            for (String jsonSlotName : jsonSlotNames) {
                if (IGNORED_SLOTS.contains(jsonSlotName)) {
                    continue;
                }
                Assertions.assertTrue(shipFdevNames.containsKey(jsonSlotName),
                        "JSON module slot '" + jsonSlotName + "' in " + filename + " has no matching slot in ship " + ship.getShipType().name());
            }

            // Every non-core Ship slot must have a corresponding JSON module
            for (Map.Entry<String, SlotType> entry : shipFdevNames.entrySet()) {
                if (entry.getValue().name().startsWith("CORE_")) {
                    continue;
                }
                Assertions.assertTrue(jsonSlotNames.contains(entry.getKey()),
                        "Ship slot '" + entry.getKey() + "' (" + entry.getValue() + ") in " + ship.getShipType().name()
                                + " is not present in JSON " + filename);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read " + filename, e);
        }
    }

    @ParameterizedTest
    @MethodSource("testcases")
    void shipInternalNameMatchesJson(String filename, Ship ship) {
        Assertions.assertNotNull(ship, "Ship must not be null for " + filename);

        try (InputStream is = Files.newInputStream(RESOURCES_DIR.resolve(filename))) {
            JsonNode root = OBJECT_MAPPER.readTree(is);
            String jsonShipName = root.path("Ship").asText();
            String expectedInternalName = ship.getShipType().getInternalName();

            Assertions.assertEquals(expectedInternalName.toLowerCase(), jsonShipName.toLowerCase(),
                    "Ship field in JSON does not match ShipType internalName for " + filename);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read " + filename, e);
        }
    }

    private Set<String> collectSlotNames(JsonNode modules) {
        var set = new java.util.HashSet<String>();
        for (JsonNode module : modules) {
            set.add(module.path("Slot").asText());
        }
        return Set.copyOf(set);
    }

    private Map<String, SlotType> collectAllSlotFdevNames(Ship ship) {
        Map<String, SlotType> result = new LinkedHashMap<>();
        addSlots(result, ship.getHardpointSlots());
        addSlots(result, ship.getUtilitySlots());
        addSlots(result, ship.getCoreSlots());
        addSlots(result, ship.getOptionalSlots());
        return result;
    }

    private void addSlots(Map<String, SlotType> result, List<? extends Slot> slots) {
        if (slots == null) return;
        for (Slot slot : slots) {
            result.put(slot.getFdevName(), slot.getSlotType());
        }
    }
}
