package nl.jixxed.eliteodysseymaterials.watchdog;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.*;


public class FileWatcher implements Runnable {

    protected List<FileListener> listeners = new ArrayList<>();
    protected final File folder;
    protected static final List<WatchService> watchServices = new ArrayList<>();

    public FileWatcher(final File folder) {
        this.folder = folder;
    }

    public void watch() {
        if (this.folder.exists()) {
            final Thread thread = new Thread(this);
            thread.setDaemon(true);
            thread.start();
        }
    }

    @Override
    public void run() {
        try (final WatchService watchService = FileSystems.getDefault().newWatchService()) {
            final Path path = Paths.get(this.folder.getAbsolutePath());
            path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
            watchServices.add(watchService);
            boolean poll = true;
            while (poll) {
                poll = pollEvents(watchService);
            }
        } catch (final IOException | InterruptedException | ClosedWatchServiceException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected boolean pollEvents(final WatchService watchService) throws InterruptedException {
        final WatchKey key = watchService.take();
        final Path path = (Path) key.watchable();
        for (final WatchEvent<?> event : key.pollEvents()) {
            notifyListeners(event.kind(), path.resolve((Path) event.context()).toFile());
        }
        return key.reset();
    }

    protected void notifyListeners(final WatchEvent.Kind<?> kind, final File file) {
        final FileEvent event = new FileEvent(file);
        if (kind == ENTRY_CREATE) {
            for (final FileListener listener : this.listeners) {
                listener.onCreated(event);
            }
            if (file.isDirectory()) {
                new FileWatcher(file).setListeners(this.listeners).watch();
            }
        } else if (kind == ENTRY_MODIFY) {
            for (final FileListener listener : this.listeners) {
                listener.onModified(event);
            }
        } else if (kind == ENTRY_DELETE) {
            for (final FileListener listener : this.listeners) {
                listener.onDeleted(event);
            }
        }
    }

    public FileWatcher addListener(final FileListener listener) {
        this.listeners.add(listener);
        return this;
    }

    public FileWatcher removeListener(final FileListener listener) {
        this.listeners.remove(listener);
        return this;
    }

    public List<FileListener> getListeners() {
        return this.listeners;
    }

    public FileWatcher setListeners(final List<FileListener> listeners) {
        this.listeners = listeners;
        return this;
    }

    public static List<WatchService> getWatchServices() {
        return Collections.unmodifiableList(watchServices);
    }
}