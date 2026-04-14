/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.watchdog.folderwatch;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.watchdog.FileEvent;

import java.nio.file.*;
import java.util.function.Consumer;

import static java.nio.file.StandardWatchEventKinds.*;

@Slf4j
public class WatchServiceFolderWatch extends AbstractFolderWatch implements Runnable {
    @Getter
    private final String folder;
    private final Consumer<FileEvent> changeConsumer;
//    private final PublishSubject<FileEvent> modifyEventSubject = PublishSubject.create();

    public WatchServiceFolderWatch(final String folder, final Consumer<FileEvent> changeConsumer) {
        super("WatchServiceFolderWatch(" + folder + ")");
        this.folder = folder;
        this.changeConsumer = changeConsumer;
//        Observable<FileEvent> debouncedModifyEvents = modifyEventSubject
//                .debounce(100, TimeUnit.MILLISECONDS)
//                .distinctUntilChanged();
//
//        debouncedModifyEvents.subscribe(changeConsumer::accept, throwable ->
//                log.error("Error processing file events", throwable));

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
//            this.changeConsumer.accept(new FileEvent(path.resolve((Path) event.context()).toFile(), event.kind()));
            Path filePath = path.resolve((Path) event.context());
            FileEvent fileEvent = new FileEvent(filePath.toFile(), event.kind());
//            if (shouldDebounce(event, fileEvent)) {
//                // Debounce ENTRY_MODIFY events
//                modifyEventSubject.onNext(fileEvent);
//            } else {
                // Immediately process ENTRY_CREATE & ENTRY_DELETE
                changeConsumer.accept(fileEvent);
//            }
        }
        return key.reset();
    }

//    private static boolean shouldDebounce(WatchEvent<?> event, FileEvent fileEvent) {
//        return event.kind() == ENTRY_MODIFY && (fileEvent.getFile().getName().contains("Journal."));
//    }
}
