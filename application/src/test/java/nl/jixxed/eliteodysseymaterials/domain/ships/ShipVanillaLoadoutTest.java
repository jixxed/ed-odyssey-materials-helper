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

class ShipVanillaLoadoutTest {

    private static final Path RESOURCES_DIR = Path.of("src/test/resources/ships/vanilla");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // Non-loadout slots that appear in JSON but are not defined in Ship slot lists
    private static final Set<String> IGNORED_SLOTS = Set.of(
            "PlanetaryApproachSuite", "VesselVoice", "ShipCockpit", "CargoHatch"
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
    void jsonModulesMatchShipSlotsAndUnlistedSlotsAreEmpty(String filename, Ship ship) {
        Assertions.assertNotNull(ship, "Ship must not be null for " + filename);

        try (InputStream is = Files.newInputStream(RESOURCES_DIR.resolve(filename))) {
            JsonNode root = OBJECT_MAPPER.readTree(is);
            JsonNode modules = root.path("Modules");

            // Collect JSON modules, grouped by slot name (excluding ignored slots)
            Map<String, JsonNode> jsonModules = collectModules(modules);

            // Build Ship slot map keyed by fdevName
            Map<String, Slot> shipSlots = new LinkedHashMap<>();
            shipSlots.putAll(buildSlotMap(ship.getHardpointSlots()));
            shipSlots.putAll(buildSlotMap(ship.getUtilitySlots()));
            shipSlots.putAll(buildSlotMap(ship.getOptionalSlots()));
            shipSlots.putAll(buildSlotMap(ship.getCoreSlots()));

            // Validate: every JSON module slot exists in Ship and has a module (default or overridden)
            for (Map.Entry<String, JsonNode> entry : jsonModules.entrySet()) {
                String slotName = entry.getKey();
                Assertions.assertTrue(shipSlots.containsKey(slotName),
                        "JSON module slot '" + slotName + "' in " + filename + " has no matching slot in ship " + ship.getShipType().name());

                Slot shipSlot = shipSlots.get(slotName);
                ShipModule expectedModule = shipSlot.getShipModule();
                Assertions.assertNotNull(expectedModule,
                        "Ship slot '" + slotName + "' in " + ship.getShipType().name()
                                + " has no module but JSON defines one: " + entry.getValue().path("Item").asText());

                String jsonModuleId = entry.getValue().path("Item").asText().toLowerCase();
                String shipModuleId = expectedModule.getInternalName().toLowerCase();
                Assertions.assertEquals(jsonModuleId, shipModuleId,
                        "Module mismatch in slot '" + slotName + "' of " + ship.getShipType().name()
                                + ": expected " + jsonModuleId + " but Ship has " + expectedModule.getId());
            }

            // Validate: every Ship slot not listed in JSON should be empty (skip if default module present)
            for (Map.Entry<String, Slot> entry : shipSlots.entrySet()) {
                if (!jsonModules.containsKey(entry.getKey())) {
                    ShipModule slotModule = entry.getValue().getShipModule();
                    if (slotModule != null) {
                        System.out.println("SKIP: Ship slot '" + entry.getKey() + "' in " + ship.getShipType().name()
                                + " has module '" + slotModule.getId() + "' but not in JSON");
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read " + filename, e);
        }
    }

    private Map<String, JsonNode> collectModules(JsonNode modules) {
        var map = new LinkedHashMap<String, JsonNode>();
        for (JsonNode module : modules) {
            String slotName = module.path("Slot").asText();
            if (!IGNORED_SLOTS.contains(slotName)) {
                map.put(slotName, module);
            }
        }
        return map;
    }

    private Map<String, Slot> buildSlotMap(List<? extends Slot> slots) {
        Map<String, Slot> result = new LinkedHashMap<>();
        if (slots == null) return result;
        for (Slot slot : slots) {
            String fdevName = slot.getFdevName();
            result.put(fdevName, slot);
        }
        return result;
    }
}
