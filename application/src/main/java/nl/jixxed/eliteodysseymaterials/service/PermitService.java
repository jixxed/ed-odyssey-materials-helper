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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.Permit;
import nl.jixxed.eliteodysseymaterials.enums.PermitType;
import nl.jixxed.eliteodysseymaterials.service.event.CommanderAllListedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.CommanderSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Predicate;

@Slf4j
public class PermitService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Set<Permit> PERMITS = new HashSet<>();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    public static void init() {
        EVENT_LISTENERS.add(EventService.addStaticListener(0, CommanderSelectedEvent.class, commanderSelectedEvent ->
                load(commanderSelectedEvent.getCommander())));
        EVENT_LISTENERS.add(EventService.addStaticListener(0, CommanderAllListedEvent.class, _ ->
                ApplicationState.getInstance().getPreferredCommander().ifPresent(PermitService::load)));
    }

    public static boolean isPermitSystem(final StarSystem starSystem) {
        return Arrays.stream(Permit.values()).anyMatch(permit -> Arrays.asList(permit.getSystems()).contains(starSystem));
    }

    public static boolean havePermit(final Permit permit) {
        return PERMITS.contains(permit);
    }

    public static boolean havePermit(StarSystem starSystem) {
        return PERMITS.stream().anyMatch(permit -> Arrays.asList(permit.getSystems()).contains(starSystem));
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
                    save(commander, new HashSet<>());
                }
            } else {//save to file from preferences
                save(commander, new HashSet<>());
            }
            permitsFileContents = Files.readString(permitsFile.toPath());
            permits = OBJECT_MAPPER.readValue(permitsFileContents, typeRef);

        } catch (final IOException e) {
            log.warn("Unable to load permits from configuration. WIll initialize empty", e);
        }
        PERMITS.addAll(permits.stream().map(Permit::forName).filter(Predicate.not(Permit::isUnknown)).toList());
        PERMITS.addAll(Arrays.stream(Permit.values()).filter(permit -> PermitType.FREE.equals(permit.getType())).toList());
    }

    private static void save(final Commander commander, final Set<Permit> favourites) {
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
