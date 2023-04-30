package nl.jixxed.eliteodysseymaterials.watchdog.folderwatch;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.watchdog.FileEvent;

import java.nio.file.*;
import java.util.function.Consumer;

import static java.nio.file.StandardWatchEventKinds.*;

@Slf4j
public class WatchServiceFolderWatch extends AbstractFolderWatch implements Runnable {

    private final String folder;
    private final Consumer<FileEvent> changeConsumer;

    public WatchServiceFolderWatch(final String folder, final Consumer<FileEvent> changeConsumer) {
        super("WatchServiceFolderWatch(" + folder + ")");
        this.folder = folder;
        this.changeConsumer = changeConsumer;
        this.thread.start();
        log.info("Started " + this.thread.getName());
    }

    @Override
    public void run() {
        try (final WatchService watchService = FileSystems.getDefault().newWatchService()) {
            final Path path = Paths.get(this.folder);
            path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
            boolean poll = true;
            while (!this.thread.isInterrupted() && poll) {
                poll = pollEvents(watchService);
            }
        } catch (final InterruptedException e) {
//            log.warn("watchService interrupted: " + this.thread.getName());
        } catch (final ClosedWatchServiceException e) {
            log.warn("watchService closed: " + this.thread.getName());
        } catch (final Exception e) {
            log.error("killing thread " + this.thread.getName(), e);
            Thread.currentThread().interrupt();
        }
    }

    private boolean pollEvents(final WatchService watchService) throws InterruptedException {
        final WatchKey key = watchService.take();
        final Path path = (Path) key.watchable();
        for (final WatchEvent<?> event : key.pollEvents()) {
            this.changeConsumer.accept(new FileEvent(path.resolve((Path) event.context()).toFile(), event.kind()));
        }
        return key.reset();
    }
}
