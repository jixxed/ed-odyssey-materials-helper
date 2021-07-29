package nl.jixxed.eliteodysseymaterials.watchdog;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

@Slf4j
public class FileWatcher implements Runnable {

    private FileListener listener;
    private final Thread thread;
    private File folder;

    FileWatcher(final String threadName) {
        this.thread = new Thread(this);
        this.thread.setName(threadName);
        this.thread.setDaemon(true);
    }

    FileWatcher watch(final File folder) {
        if (!this.thread.isAlive()) {
            this.folder = folder;
            if (this.folder.exists()) {
                this.thread.start();
                log.info("Thread started: " + this.thread.getName());
            }
        } else {
            log.error("Blocked starting already running thread: " + this.thread.getName());
        }
        return this;
    }

    @Override
    public void run() {
        try (final WatchService watchService = FileSystems.getDefault().newWatchService()) {
            final Path path = Paths.get(this.folder.getAbsolutePath());
            path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
            boolean poll = true;
            while (!this.thread.isInterrupted() && poll) {
                poll = pollEvents(watchService);
            }
        } catch (final IOException | InterruptedException | ClosedWatchServiceException e) {
            Thread.currentThread().interrupt();
        }
        log.info("Thread finished: " + Thread.currentThread().getName());
    }


    private boolean pollEvents(final WatchService watchService) throws InterruptedException {
        final WatchKey key = watchService.take();
        final Path path = (Path) key.watchable();
        for (final WatchEvent<?> event : key.pollEvents()) {
            notifyListeners(event.kind(), path.resolve((Path) event.context()).toFile());
        }
        return key.reset();
    }

    private void notifyListeners(final WatchEvent.Kind<?> kind, final File file) {
        final FileEvent event = new FileEvent(file);
        if (kind == ENTRY_CREATE) {
            this.listener.onCreated(event);

        } else if (kind == ENTRY_MODIFY) {
            this.listener.onModified(event);

        } else if (kind == ENTRY_DELETE) {
            this.listener.onDeleted(event);
        }
    }

    FileWatcher withListener(final FileListener listener) {
        this.listener = listener;
        return this;
    }

    void stop() {
        this.thread.interrupt();
    }
}