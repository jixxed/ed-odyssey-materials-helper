package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.Permit;
import nl.jixxed.eliteodysseymaterials.service.event.CommanderAllListedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.CommanderSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class PermitService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final List<Permit> PERMITS = new ArrayList<>();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    static {
        EVENT_LISTENERS.add(EventService.addStaticListener(0, CommanderSelectedEvent.class, commanderSelectedEvent ->
                load(commanderSelectedEvent.getCommander())));
        EVENT_LISTENERS.add(EventService.addStaticListener(0, CommanderAllListedEvent.class, _ ->
                ApplicationState.getInstance().getPreferredCommander().ifPresent(PermitService::load)));
    }

    public static boolean isPermitSystem(final StarSystem starSystem) {
        return Arrays.stream(Permit.values()).anyMatch(permit -> permit.getSystem().equals(starSystem));
    }

    public static boolean havePermit(final Permit permit) {
        return PERMITS.contains(permit);
    }
    public static boolean havePermit(StarSystem starSystem) {
        return PERMITS.stream().anyMatch(permit -> permit.getSystem().equals(starSystem));
    }

    public static boolean togglePermit(final Permit permit) {
        final boolean newState;
        if (PERMITS.contains(permit)) {
            PERMITS.remove(permit);
            newState = false;
        } else {
            PERMITS.add(permit);
            newState = true;
        }
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            save(commander, PERMITS);
        });
        return newState;
    }

    private static void load(final Commander commander) {
        final TypeReference<List<String>> typeRef = new TypeReference<List<String>>() {
        };
        PERMITS.clear();
        List<String> permits = Collections.emptyList();
        try {
            final String pathname = commander.getCommanderFolder();
            final File commanderFolder = new File(pathname);
            commanderFolder.mkdirs();
            final File permitsFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.PERMITS_FILE);
            String permitsFileContents;
            if (permitsFile.exists()) {//load from file if exists
                permitsFileContents = Files.readString(permitsFile.toPath());
                if (permitsFileContents.isEmpty()) {//create default if empty
                    save(commander, new ArrayList<>());
                }
            } else {//save to file from preferences
                save(commander, new ArrayList<>());
            }
            permitsFileContents = Files.readString(permitsFile.toPath());
            permits = OBJECT_MAPPER.readValue(permitsFileContents, typeRef);

        } catch (final IOException e) {
            log.warn("Unable to load permits from configuration. WIll initialize empty", e);
        }
        PERMITS.addAll(permits.stream().map(Permit::forName).toList());
    }

    private static void save(final Commander commander, final List<Permit> favourites) {
        try {
            final String permitsJson = OBJECT_MAPPER.writeValueAsString(favourites.stream().map(Permit::name).toList());
            final String pathname = commander.getCommanderFolder();
            final File commanderFolder = new File(pathname);
            commanderFolder.mkdirs();
            final File permitsFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.PERMITS_FILE);
            try (final FileOutputStream fileOutputStream = new FileOutputStream(permitsFile)) {
                fileOutputStream.write(permitsJson.getBytes(StandardCharsets.UTF_8));
            }
        } catch (final JsonProcessingException e) {
            log.error("Failed to save permits", e);
        } catch (final IOException e) {
            log.error("Failed to save permits to permits file", e);
        }
    }

}
