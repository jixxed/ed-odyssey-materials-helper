package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItems;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Slf4j
public class ColonisationService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addKeyDeserializer(Commodity.class, new CommodityKeyDeserializer());
        OBJECT_MAPPER.registerModule(simpleModule);
    }

    public static void saveColonisationItems(final Commander commander, final ColonisationItems colonisationItems) {
        try {
            final String colonisationItemsJson = OBJECT_MAPPER.writeValueAsString(colonisationItems);
            final String pathname = commander.getCommanderFolder();
            final File commanderFolder = new File(pathname);
            commanderFolder.mkdirs();
            final File wishlistsFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.HORIZONS_COLONISATION_FILE);
            try (final FileOutputStream fileOutputStream = new FileOutputStream(wishlistsFile)) {
                fileOutputStream.write(colonisationItemsJson.getBytes(StandardCharsets.UTF_8));
            }
        } catch (final JsonProcessingException e) {
            log.error("Failed to save horizons colonisations", e);
        } catch (final IOException e) {
            log.error("Failed to save horizons colonisations to colonisations file", e);
        }
    }

    public static ColonisationItems getColonisationItems(final Commander commander) {
        try {
            final String pathname = commander.getCommanderFolder();
            final File commanderFolder = new File(pathname);
            commanderFolder.mkdirs();
            final File colonisationItemsFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.HORIZONS_COLONISATION_FILE);
            String colonisationItemsFileContents;
            if (colonisationItemsFile.exists()) {//load from file if exists
                colonisationItemsFileContents = Files.readString(colonisationItemsFile.toPath());
                if (colonisationItemsFileContents.isEmpty()) {//create default if empty
                    createColonisationItems(commander);
                }
            } else {//save to file from preferences
                createColonisationItems(commander);
            }
            colonisationItemsFileContents = Files.readString(colonisationItemsFile.toPath());
            try {
                return OBJECT_MAPPER.readValue(colonisationItemsFileContents, ColonisationItems.class);
            } catch (final IOException e) {
                log.warn("Unable to load horizons colonisations from configuration. Try to create new one.", e);
                createColonisationItems(commander);
                colonisationItemsFileContents = Files.readString(colonisationItemsFile.toPath());
                return OBJECT_MAPPER.readValue(colonisationItemsFileContents, ColonisationItems.class);
            }
        } catch (final IOException e) {
            log.error("Unable to load horizons colonisations from configuration.", e);
            throw new RuntimeException(e);
        }
    }

    public static void deleteColonisationItem(final String colonisationItemUUID, final Commander commander) {
        final ColonisationItems colonisationItems = getColonisationItems(commander);
        colonisationItems.delete(colonisationItemUUID);
        saveColonisationItems(commander, colonisationItems);
    }

    private static void createColonisationItems(final Commander commander) {
        final ColonisationItems colonisationItems = new ColonisationItems();
        saveColonisationItems(commander, colonisationItems);
    }

}
