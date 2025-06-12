package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.service.event.ColonisationConstructionDepotEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ColonisationService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    public static void init() {
        EVENT_LISTENERS.add(EventService.addStaticListener(0, ColonisationConstructionDepotEvent.class, event -> {
            ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
                final ColonisationItems colonisationItems = getColonisationItems(commander);
                colonisationItems.getAllColonisationItems().stream()
                        .filter(colonisationItem -> Objects.equals(event.getMarketID().toString(), colonisationItem.getMarketID()))
                        .findFirst()
                        .ifPresent(colonisationItem -> {
                            colonisationItem.setConstructionRequirements(event.getResourcesRequired());
                            saveColonisationItems(commander, colonisationItems);
                        });
                final Location location = LocationService.getCurrentLocation();
                ColonisationItem.setCurrent(new ColonisationItem("1", "Current", event.getMarketID().toString(), location.getStarSystem().getName(), location.getBody(), location.getStarSystem().getX(), location.getStarSystem().getY(), location.getStarSystem().getZ(), false, null, null, event.getResourcesRequired()));
            });
        }));
    }

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
