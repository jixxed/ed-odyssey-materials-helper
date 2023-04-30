package nl.jixxed.eliteodysseymaterials.watchdog.folderwatch;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.watchdog.FileEvent;

import java.io.File;
import java.nio.file.attribute.FileTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static java.nio.file.StandardWatchEventKinds.*;
@Slf4j
public class PollingFolderWatch implements Runnable, FolderWatch {
    private final String folder;
    private final Consumer<FileEvent> changeConsumer;
    private final ScheduledExecutorService executor;
    private final Map<String, Long> previousFilesModifiedDates = new HashMap<>();
    private boolean initialized = false;

    public PollingFolderWatch(final String folder, final Consumer<FileEvent> changeConsumer) {
        this.folder = folder;
        this.changeConsumer = changeConsumer;
        this.executor = Executors.newScheduledThreadPool(1, r -> new Thread(r,"PollingFolderWatch(" + folder + ")"));
        this.executor.scheduleAtFixedRate(this, 0, 2, TimeUnit.SECONDS);
        log.info("Started PollingFolderWatch(" + folder + ")");
    }

    @Override
    public void terminate() {
        log.info("Terminating PollingFolderWatch " + this.folder);
        this.executor.shutdownNow();
    }

    @Override
    public void run() {
        final List<FilenameWithLastModifiedDate> listPaths = getFilesInDirectory(this.folder);
        if (!this.initialized) {
            initialize(listPaths);
            this.initialized = true;
        }
        // events for files that were not previously detected
        detectNewFiles(listPaths);
        // events for previous files that can no longer be found
        detectDeletedFiles(listPaths);
        // events for modified files
        detectModifiedFiles(listPaths);

    }

    private void detectModifiedFiles(final List<FilenameWithLastModifiedDate> listPaths) {
        listPaths.stream()
                .filter(path -> {
                    final Long date = this.previousFilesModifiedDates.get(path.fileName());
                    return date == null || date < path.fileTime().toMillis();
                })
                .forEach(path -> {
                    this.changeConsumer.accept(new FileEvent(new File(path.fileName()), ENTRY_MODIFY));
                    try {
                        this.previousFilesModifiedDates.put(path.fileName(), path.fileTime().toMillis());
                    } catch (final Exception e) {
                        log.error("Failed to read last modified date", e);
                    }
                });
    }

    private void detectDeletedFiles(final List<FilenameWithLastModifiedDate> listPaths) {
        this.previousFilesModifiedDates.keySet().stream()
                .filter(filename -> listPaths.stream()
                        .noneMatch(path -> path.fileName().equals(filename)))
                .forEach(filename -> this.changeConsumer.accept(new FileEvent(new File(filename), ENTRY_DELETE)));
    }

    private void detectNewFiles(final List<FilenameWithLastModifiedDate> listPaths) {
        listPaths.stream()
                .filter(path -> !this.previousFilesModifiedDates.containsKey(path.fileName()))
                .forEach(path -> {
                    this.changeConsumer.accept(new FileEvent(new File(path.fileName()), ENTRY_CREATE));
                    try {
                        this.previousFilesModifiedDates.put(path.fileName(), path.fileTime().toMillis());
                    } catch (final Exception e) {
                        log.error("Failed to precess new file", e);
                    }
                });
    }

    private void initialize(final List<FilenameWithLastModifiedDate> listPaths) {
        listPaths.forEach(path -> {
            try {
                this.previousFilesModifiedDates.put(path.fileName(), path.fileTime().toMillis());
            } catch (final Exception e) {
                log.error("Failed to initialize file list", e);
            }
        });
    }

    public static List<FilenameWithLastModifiedDate> getFilesInDirectory(final String directoryPath) {
       return Arrays.stream(Objects.requireNonNull(new File(directoryPath).listFiles())).map(file -> new FilenameWithLastModifiedDate(file.getAbsolutePath(),FileTime.fromMillis(file.lastModified()))).toList();
    }
}
