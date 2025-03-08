package nl.jixxed.eliteodysseymaterials.watchdog.folderwatch;

import com.sun.jna.Platform;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinNT;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.watchdog.FileEvent;

import java.io.File;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static java.nio.file.StandardWatchEventKinds.*;

@Slf4j
public class JNAWindowsPollingFolderWatch implements Runnable, FolderWatch {
    @Getter
    private final String folder;
    private final Consumer<FileEvent> changeConsumer;
    private final ScheduledExecutorService executor;
    private final Map<String, Long> previousFilesModifiedDates = new HashMap<>();
    private boolean initialized = false;

    public JNAWindowsPollingFolderWatch(final String folder, final Consumer<FileEvent> changeConsumer) {
        this.folder = folder;
        this.changeConsumer = changeConsumer;
        this.executor = Executors.newScheduledThreadPool(1, r -> new Thread(r,"JNAWindowsPollingFolderWatch(" + folder + ")"));
        this.executor.scheduleAtFixedRate(this, 0, 2, TimeUnit.SECONDS);
        log.info("Started JNAWindowsPollingFolderWatch(" + folder + ")");
    }

    @Override
    public void terminate() {
        log.info("Terminating JNAWindowsPollingFolderWatch(" + this.folder + ")");
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
                    final boolean modified = date == null || date < path.fileTime().toMillis();
                    if(modified){
                        log.info("File modification detected: " + path.fileName());
                    }
                    return modified;
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
//                    .filter(path -> !path.isDirectory())
                .forEach(path -> {
                    this.changeConsumer.accept(new FileEvent(new File(path.fileName()), ENTRY_CREATE));
                    try {
                        this.previousFilesModifiedDates.put(path.fileName(), path.fileTime().toMillis());
                    } catch (final Exception e) {
                        log.error("Failed to read last modified date", e);
                    }
                });
    }

    private void initialize(final List<FilenameWithLastModifiedDate> listPaths) {
        listPaths.forEach(path -> {
            try {
                this.previousFilesModifiedDates.put(path.fileName(), path.fileTime().toMillis());
            } catch (final Exception e) {
                log.error("Failed to read last modified date", e);
            }
        });
    }

    public static List<FilenameWithLastModifiedDate> getFilesInDirectory(final String directoryPath) {
        final List<FilenameWithLastModifiedDate> files = new ArrayList<>();
        if (Platform.isWindows()) {
            final WinBase.WIN32_FIND_DATA findData = new WinBase.WIN32_FIND_DATA.ByReference();
            final WinNT.HANDLE handle = Kernel32.INSTANCE.FindFirstFile(
                    directoryPath + "\\*",
                    findData.getPointer());
            if (handle != WinBase.INVALID_HANDLE_VALUE) {
                try {
                    do {
                        final WinBase.WIN32_FIND_DATA.ByReference byReference = new WinBase.WIN32_FIND_DATA.ByReference(findData.getPointer());
                        final String fileName = byReference.getFileName();
                        if (!fileName.equals(".") && !fileName.equals("..")) {
                            final FilenameWithLastModifiedDate file = new FilenameWithLastModifiedDate(directoryPath + "\\" + fileName, getFileLastModifiedTime(byReference));
                            files.add(file);
                        }
                    } while (Kernel32.INSTANCE.FindNextFile(handle, findData.getPointer()));
                } finally {
                    Kernel32.INSTANCE.FindClose(handle);
                }
            }
        }
        return files;
    }

    private static FileTime getFileLastModifiedTime(final WinBase.WIN32_FIND_DATA.ByReference file) {
        final WinBase.FILETIME.ByReference fileTime = new WinBase.FILETIME.ByReference(file.ftLastWriteTime.getPointer());
        return FileTime.from(WinBase.FILETIME.filetimeToDate(fileTime.dwHighDateTime, fileTime.dwLowDateTime).toInstant());
    }

}
