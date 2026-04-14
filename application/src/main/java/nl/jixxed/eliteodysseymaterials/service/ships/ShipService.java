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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfigurations;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipType;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.FrameShiftDrive;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ShipService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();
    private static ShipConfigurations cache;
    private static String cacheFID;

    public static ShipConfigurations getShipConfigurations(@NonNull final Commander commander) {
        //reset cache if commander changed
        if (commander.getFid() == null || !commander.getFid().equals(cacheFID)) {
            cache = null;
        }
        //load from cache if exists
        if (cache != null) {
            return cache;
        }
        try {
            final String pathname = commander.getCommanderFolder();
            final File commanderFolder = new File(pathname);
            commanderFolder.mkdirs();
            final File shipConfigurationFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.SHIP_CONFIGURATION_FILE);
            String shipConfigurationFileContents;
            if (shipConfigurationFile.exists()) {//load from file if exists
                shipConfigurationFileContents = Files.readString(shipConfigurationFile.toPath());
                if (shipConfigurationFileContents.isEmpty()) {
                    createShipConfigurations(commander);
                    shipConfigurationFileContents = Files.readString(shipConfigurationFile.toPath());
                }
            } else {
                createShipConfigurations(commander);
                shipConfigurationFileContents = Files.readString(shipConfigurationFile.toPath());
            }
            try {
                cache = OBJECT_MAPPER.readValue(shipConfigurationFileContents, ShipConfigurations.class);
                cacheFID = commander.getFid();
                return cache;
            } catch (final IOException e) {
                log.warn("Unable to load ships from configuration. Try to create new one.", e);
                createShipConfigurations(commander);
                shipConfigurationFileContents = Files.readString(shipConfigurationFile.toPath());
                cache = OBJECT_MAPPER.readValue(shipConfigurationFileContents, ShipConfigurations.class);
                cacheFID = commander.getFid();
                return cache;
            }
        } catch (final IOException e) {
            log.error("Unable to load ships from configuration.", e);
            throw new RuntimeException(e);
        }
    }

    public static void saveShipConfigurations(final Commander commander, final ShipConfigurations shipConfigurations) {
        try {
            //update cache on save
            cache = shipConfigurations;
            cacheFID = commander.getFid();
            final String shipConfigurationsJson = OBJECT_MAPPER.writeValueAsString(shipConfigurations);
            final String pathname = commander.getCommanderFolder();
            final File commanderFolder = new File(pathname);
            commanderFolder.mkdirs();
            final File shipConfigurationsFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.SHIP_CONFIGURATION_FILE);
            try (final FileOutputStream fileOutputStream = new FileOutputStream(shipConfigurationsFile)) {
                fileOutputStream.write(shipConfigurationsJson.getBytes(StandardCharsets.UTF_8));
            }
        } catch (final JsonProcessingException e) {
            log.error("Failed to save ships configuration", e);
        } catch (final IOException e) {
            log.error("Failed to save ships to ships configuration file", e);
        }
    }


    private static void createShipConfigurations(final Commander commander) {

        final ShipConfigurations shipConfigurations = new ShipConfigurations();
        saveShipConfigurations(commander, shipConfigurations);
    }

    public static void selectShipConfiguration(final String shipConfigurationUUID, final Commander commander) {
        final ShipConfigurations shipConfigurations = getShipConfigurations(commander);
        shipConfigurations.setSelectedShipConfigurationUUID(shipConfigurationUUID);
        saveShipConfigurations(commander, shipConfigurations);
    }

    public static void deleteShipConfiguration(final String shipConfigurationUUID, final Commander commander) {
        final ShipConfigurations shipConfigurations = getShipConfigurations(commander);
        shipConfigurations.delete(shipConfigurationUUID);
        saveShipConfigurations(commander, shipConfigurations);
    }

    public static Ship createShip(ShipType shipType) {
        return new Ship(Ship.ALL.stream().filter(ship -> ship.getShipType().equals(shipType)).findFirst().orElseThrow(IllegalArgumentException::new));
    }

    public static Ship createBlankShip(ShipType shipType) {
        final Ship blankShip = createShip(shipType);
        blankShip.getUtilitySlots().forEach(imageSlot -> imageSlot.setShipModule(null));
        blankShip.getHardpointSlots().forEach(imageSlot -> imageSlot.setShipModule(null));
        blankShip.getOptionalSlots().forEach(slot -> slot.setShipModule(null));
        //core modules must be present at all times
        blankShip.getCoreSlots().forEach(slot -> slot.setOldShipModule(slot.getShipModule()));
        return blankShip;
    }

    public static Optional<ShipModule> findShipModule(SlotType slotType, String internalName, List<HorizonsBlueprintType> modifications, List<HorizonsBlueprintType> experimentalEffects) {
        final List<ShipModule> list = ShipModule.getModules(slotType).stream().filter(shipModule -> shipModule.getInternalName().equals(internalName)).toList();
        if (list.size() > 1) {
            list.forEach(shipModule -> {
                if (shipModule instanceof FrameShiftDrive fsd) {
                    if (fsd.getModifications().stream().allMatch(modification -> modifications.contains(modification.getModification()))) {

                    }
                }

            });
        }
        return list.stream().findFirst();
    }
}
