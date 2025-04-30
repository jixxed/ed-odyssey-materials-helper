package nl.jixxed.eliteodysseymaterials.watchdog;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.helper.OsCheck;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.PollingFileModeEvent;
import nl.jixxed.eliteodysseymaterials.service.event.TerminateApplicationEvent;
import nl.jixxed.eliteodysseymaterials.watchdog.folderwatch.FolderWatch;
import nl.jixxed.eliteodysseymaterials.watchdog.folderwatch.JNAWindowsPollingFolderWatch;
import nl.jixxed.eliteodysseymaterials.watchdog.folderwatch.PollingFolderWatch;
import nl.jixxed.eliteodysseymaterials.watchdog.folderwatch.WatchServiceFolderWatch;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.nio.file.StandardWatchEventKinds.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class FileService {

    private static final Map<WatchPath, List<FileListener>> WATCHPATH_LISTENERS = new ConcurrentHashMap<>();
    private static final Map<WatchPath, FolderWatch> WATCHPATH_FOLDERWATCHERS = new ConcurrentHashMap<>();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    static {
        EVENT_LISTENERS.add(EventService.addStaticListener(TerminateApplicationEvent.class, event -> {
            stopWatchers();
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(PollingFileModeEvent.class, event -> {
            stopWatchers();
            startWatchers();
        }));
    }

    public static void subscribe(final String path, final boolean allowPolling, final FileListener fileListener) {
        final WatchPath watchPath = WatchPath.builder()
                .path(Paths.get(path).normalize().toString())
                .allowPolling(allowPolling)
                .build();
        final List<FileListener> listeners = WATCHPATH_LISTENERS.getOrDefault(watchPath, new ArrayList<>());
        listeners.add(fileListener);
        WATCHPATH_LISTENERS.put(watchPath, listeners);
        startWatchers();
    }

    public static void unsubscribe(final FileListener fileListener) {
        WATCHPATH_LISTENERS.values().forEach(fileListeners -> fileListeners.remove(fileListener));
        stopEmptyWatchers();
        WATCHPATH_LISTENERS.entrySet().removeIf(entry -> entry.getValue().isEmpty());
//        startWatchers();
    }

    private static void stopEmptyWatchers() {
        WATCHPATH_LISTENERS.entrySet().stream().filter(entry -> entry.getValue().isEmpty()).forEach(entry -> {
            final FolderWatch folderWatch = WATCHPATH_FOLDERWATCHERS.get(entry.getKey());
            if (folderWatch == null) {
                return;
            }
            log.info("Unregistered folder watch for " + folderWatch.getFolder());
            folderWatch.terminate();
        });
        WATCHPATH_FOLDERWATCHERS.entrySet().removeIf(entry -> entry.getValue().isTerminated());
    }

    private static void stopWatchers() {
        WATCHPATH_FOLDERWATCHERS.forEach((watchPath, folderWatch) -> {
            log.info("Unregistered folder watch for " + folderWatch.getFolder());
            folderWatch.terminate();
        });
        WATCHPATH_FOLDERWATCHERS.clear();
    }

    private static void startWatchers() {
        final Boolean polling = PreferencesService.getPreference(PreferenceConstants.POLLING_FILE_MODE, false);
        WATCHPATH_LISTENERS.entrySet().stream()
                .filter(entry -> entry.getValue() != null && !entry.getValue().isEmpty()).map(Map.Entry::getKey)
                .forEach(watchPath -> WATCHPATH_FOLDERWATCHERS.computeIfAbsent(watchPath, key -> {
                    log.info("Registered folder watch for " + key.getPath());
                    if (polling && key.getAllowPolling()) {
                        if (OsCheck.isWindows()) {
                            //optimized poller for windows using JNA
                            return new JNAWindowsPollingFolderWatch(watchPath.path, fileEvent -> notifyListeners(watchPath, fileEvent));
                        } else {
                            return new PollingFolderWatch(watchPath.path, fileEvent -> notifyListeners(watchPath, fileEvent));
                        }
                    } else {
                        return new WatchServiceFolderWatch(watchPath.path, fileEvent -> notifyListeners(watchPath, fileEvent));
                    }
                }));
    }

    private static void notifyListeners(final WatchPath watchPath, final FileEvent fileEvent) {
        try {
            if (fileEvent.getKind() == ENTRY_CREATE) {
                WATCHPATH_LISTENERS.get(watchPath).forEach(fileListener -> fileListener.onCreated(fileEvent));
            } else if (fileEvent.getKind() == ENTRY_MODIFY) {
                WATCHPATH_LISTENERS.get(watchPath).forEach(fileListener -> fileListener.onModified(fileEvent));
            } else if (fileEvent.getKind() == ENTRY_DELETE) {
                WATCHPATH_LISTENERS.get(watchPath).forEach(fileListener -> fileListener.onDeleted(fileEvent));
            }

        } catch (RuntimeException e) {
            log.error("failed to notify listeners for file event: {}, {}", fileEvent, e.getMessage());
        }
    }

    @Data
    @Builder
    private static class WatchPath {
        private String path;
        private Boolean allowPolling;
    }

    static Map<String, List<File>> caches = new HashMap<>();
    static Map<String, LocalDateTime> lastRequests = new HashMap<>();

    public static List<File> listFiles(final File folder, final boolean allowPolling) {
        if (caches.get(folder.getAbsolutePath()) == null || lastRequests.get(folder.getAbsolutePath()).isBefore(LocalDateTime.now().minus(5, ChronoUnit.SECONDS))) {
            final boolean polling = PreferencesService.getPreference(PreferenceConstants.POLLING_FILE_MODE, false);
            if (polling && allowPolling) {
                if (OsCheck.isWindows()) {
                    caches.put(folder.getAbsolutePath(), JNAWindowsPollingFolderWatch.getFilesInDirectory(folder.getAbsolutePath()).stream().map(f -> new File(f.fileName())).toList());
                } else {
                    caches.put(folder.getAbsolutePath(), Arrays.asList(Objects.requireNonNull(folder.listFiles())));
                }
            } else {
                caches.put(folder.getAbsolutePath(), Arrays.asList(Objects.requireNonNull(folder.listFiles())));
            }
            lastRequests.put(folder.getAbsolutePath(), LocalDateTime.now());
        }
        return caches.get(folder.getAbsolutePath());
    }
}
