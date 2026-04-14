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
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
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
import java.util.Collections;
import java.util.List;

@Slf4j
public class FavouriteService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final List<OdysseyMaterial> FAVOURITES = new ArrayList<>();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    static {
        EVENT_LISTENERS.add(EventService.addStaticListener(0, CommanderSelectedEvent.class, commanderSelectedEvent -> {
            load(commanderSelectedEvent.getCommander());
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(0, CommanderAllListedEvent.class, commanderAllListedEvent -> {
            ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> load(commander)
            );
        }));
    }

    public static boolean isFavourite(final OdysseyMaterial odysseyMaterial) {
        return FAVOURITES.contains(odysseyMaterial);
    }

    public static <T extends OdysseyMaterial> boolean toggleFavourite(final T material) {
        final boolean newState;
        if (FAVOURITES.contains(material)) {
            FAVOURITES.remove(material);
            newState = false;
        } else {
            FAVOURITES.add(material);
            newState = true;
        }
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            save(commander, FAVOURITES);
        });
        return newState;
    }

    private static void load(final Commander commander) {
        final TypeReference<List<String>> typeRef = new TypeReference<List<String>>() {
        };
        FAVOURITES.clear();
        List<String> favourites = Collections.emptyList();
        try {
            final String pathname = commander.getCommanderFolder();
            final File commanderFolder = new File(pathname);
            commanderFolder.mkdirs();
            final File favouritesFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.ODYSSEY_FAVOURITES_FILE);
            String favouritesFileContents;
            if (favouritesFile.exists()) {//load from file if exists
                favouritesFileContents = Files.readString(favouritesFile.toPath());
                if (favouritesFileContents.isEmpty()) {//create default if empty
                    save(commander, new ArrayList<>());
                }
            } else {//save to file from preferences
                save(commander, new ArrayList<>());
            }
            favouritesFileContents = Files.readString(favouritesFile.toPath());
            favourites = OBJECT_MAPPER.readValue(favouritesFileContents, typeRef);

        } catch (final IOException e) {
            log.warn("Unable to load odyssey favourites from configuration. WIll initialize empty", e);
        }
        FAVOURITES.addAll(favourites.stream().map(OdysseyMaterial::subtypeForName).toList());
    }

    private static void save(final Commander commander, final List<OdysseyMaterial> favourites) {
        try {
            final String wishlistsJson = OBJECT_MAPPER.writeValueAsString(favourites.stream().map(OdysseyMaterial::name).toList());
            final String pathname = commander.getCommanderFolder();
            final File commanderFolder = new File(pathname);
            commanderFolder.mkdirs();
            final File favouritesFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.ODYSSEY_FAVOURITES_FILE);
            try (final FileOutputStream fileOutputStream = new FileOutputStream(favouritesFile)) {
                fileOutputStream.write(wishlistsJson.getBytes(StandardCharsets.UTF_8));
            }
        } catch (final JsonProcessingException e) {
            log.error("Failed to save odyssey favourites", e);
        } catch (final IOException e) {
            log.error("Failed to save odyssey favourites to favourites file", e);
        }
    }
}
