package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
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
            final File loadoutsFile = new File(pathname + OsConstants.OS_SLASH + AppConstants.ODYSSEY_LOADOUTS_FILE);
            String loadoutFileContents;
            if (loadoutsFile.exists()) {//load from file if exists
                loadoutFileContents = Files.readString(loadoutsFile.toPath());
                if (loadoutFileContents.isEmpty()) {//create default if empty
                    createLoadoutSetList(commander);
                }
            } else {//save to file from preferences
                final String loadoutSetPreference = PreferencesService.getPreference(PreferenceConstants.LOADOUTS_PREFIX + getFID(commander), "");
                if (loadoutSetPreference.isBlank()) {
                    createLoadoutSetList(commander);
                } else {
                    final LoadoutSetList loadoutSetList = OBJECT_MAPPER.readValue(loadoutSetPreference, LoadoutSetList.class);
                    saveLoadoutSetList(commander, loadoutSetList);
                }
                PreferencesService.removePreference(PreferenceConstants.LOADOUTS_PREFIX + getFID(commander));
            }
            loadoutFileContents = Files.readString(loadoutsFile.toPath());
            return OBJECT_MAPPER.readValue(loadoutFileContents, LoadoutSetList.class);
        } catch (final IOException e) {
            throw new IllegalStateException("Unable to load loadouts from configuration.", e);
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
            final File loadoutsFile = new File(pathname + OsConstants.OS_SLASH + AppConstants.ODYSSEY_LOADOUTS_FILE);
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
