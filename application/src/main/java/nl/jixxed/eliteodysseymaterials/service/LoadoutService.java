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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.domain.LoadoutSet;
import nl.jixxed.eliteodysseymaterials.domain.LoadoutSetList;
import nl.jixxed.eliteodysseymaterials.enums.GameVersion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

@Slf4j
public class LoadoutService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static LoadoutSetList getLoadoutSetList(final Commander commander) {
        try {
            final String pathname = commander.getCommanderFolder();
            final File commanderFolder = new File(pathname);
            commanderFolder.mkdirs();
            final File loadoutsFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.ODYSSEY_LOADOUTS_FILE);
            String loadoutFileContents;
            if (loadoutsFile.exists()) {//load from file if exists
                loadoutFileContents = Files.readString(loadoutsFile.toPath());
                if (loadoutFileContents.isEmpty()) {//create default if empty
                    createLoadoutSetList(commander);
                }
            } else {//save to file from preferences
                createLoadoutSetList(commander);
            }
            loadoutFileContents = Files.readString(loadoutsFile.toPath());
            try {
                return OBJECT_MAPPER.readValue(loadoutFileContents, LoadoutSetList.class);
            } catch (final IOException e) {
                log.warn("Unable to load loadouts from configuration. Try to create new one.", e);
                createLoadoutSetList(commander);
                loadoutFileContents = Files.readString(loadoutsFile.toPath());
                return OBJECT_MAPPER.readValue(loadoutFileContents, LoadoutSetList.class);
            }
        } catch (final IOException e) {
            log.error("Unable to load loadouts from configuration.", e);
            throw new RuntimeException(e);
        }
    }

    private static void createLoadoutSetList(final Commander commander) {
        final LoadoutSetList loadoutSetList = new LoadoutSetList();
        final LoadoutSet defaultLoadoutSet = new LoadoutSet();
        defaultLoadoutSet.setName("Default Loadout");
        defaultLoadoutSet.setLoadouts(List.of());
        loadoutSetList.addLoadoutSet(defaultLoadoutSet);
        saveLoadoutSetList(commander, loadoutSetList);
    }

    public static void selectLoadoutSet(final String activeLoadoutSetUUID, final Commander commander) {
        final LoadoutSetList loadoutSetList = getLoadoutSetList(commander);
        loadoutSetList.setSelectedLoadoutSetUUID(activeLoadoutSetUUID);
        saveLoadoutSetList(commander, loadoutSetList);
    }

    public static void deleteLoadoutSet(final String activeLoadoutSetUUID, final Commander commander) {
        final LoadoutSetList loadoutSetList = getLoadoutSetList(commander);
        loadoutSetList.delete(activeLoadoutSetUUID);
        saveLoadoutSetList(commander, loadoutSetList);
    }

    public static void saveLoadoutSetList(final Commander commander, final LoadoutSetList loadoutSetList) {
        try {
            final String loadoutJson = OBJECT_MAPPER.writeValueAsString(loadoutSetList);
            final String pathname = commander.getCommanderFolder();
            final File commanderFolder = new File(pathname);
            commanderFolder.mkdirs();
            final File loadoutsFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.ODYSSEY_LOADOUTS_FILE);
            try (final FileOutputStream fileOutputStream = new FileOutputStream(loadoutsFile)) {
                fileOutputStream.write(loadoutJson.getBytes(StandardCharsets.UTF_8));
            }
        } catch (final JsonProcessingException e) {
            log.error("Failed to save loadouts", e);
        } catch (final IOException e) {
            log.error("Failed to save loadouts to loadouts file", e);
        }
    }

    public static void saveLoadoutSet(final Commander commander, final LoadoutSet loadoutSet) {
        if (!loadoutSet.equals(LoadoutSet.CURRENT)) {
            final LoadoutSetList loadoutSetList = getLoadoutSetList(commander);
            loadoutSetList.updateLoadoutSet(loadoutSet);
            saveLoadoutSetList(commander, loadoutSetList);
        }
    }

    private static String getFID(final Commander commander) {
        return (commander.getGameVersion().equals(GameVersion.LEGACY)) ? commander.getFid() + ".legacy" : commander.getFid();
    }
}
