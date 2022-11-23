package nl.jixxed.eliteodysseymaterials.watchdog;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.PollingFileModeEvent;
import nl.jixxed.eliteodysseymaterials.service.event.TerminateApplicationEvent;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

@Slf4j
public class FileWatcher implements Runnable {

    private static final int INTERVAL = 2000;
    private final FileAlterationListenerAdaptor pollingListener;
    private FileListener listener;
    private final Thread thread;
    private File folder;
    private FileAlterationObserver observer;
    private FileAlterationMonitor monitor;
    private final boolean allowPolling;

    FileWatcher(final String threadName) {
        this(threadName, false);
    }

    FileWatcher(final String threadName, final boolean allowPolling) {
        this.allowPolling = allowPolling;
        this.thread = new Thread(this);
        this.thread.setName(threadName);
        this.thread.setDaemon(true);
        EventService.addStaticListener(TerminateApplicationEvent.class, event -> {
            stop();
        });
        this.pollingListener = new FileAlterationListenerAdaptor() {
            @Override
            public void onFileChange(final File file) {
                final FileEvent event = new FileEvent(file);
                FileWatcher.this.listener.onModified(event);
            }

            @Override
            public void onFileCreate(final File file) {
                final FileEvent event = new FileEvent(file);
                FileWatcher.this.listener.onCreated(event);
            }

            @Override
            public void onFileDelete(final File file) {
                final FileEvent event = new FileEvent(file);
                FileWatcher.this.listener.onDeleted(event);
            }
        };
    }

    FileWatcher watch(final File folder) {
        if (!this.thread.isAlive()) {
            this.folder = folder;
            if (this.folder.exists()) {
                setupObserver();
                this.thread.start();
                log.info("Thread started: " + this.thread.getName());
            }
        } else {
            log.error("Blocked starting already running thread: " + this.thread.getName());
        }
        return this;
    }

    private void setupObserver() {
            this.observer = new FileAlterationObserver(this.folder);
            EventService.addStaticListener(PollingFileModeEvent.class, event -> {
                if (event.isPollingEnabled()) {
                    if (!Iterables.contains(this.observer.getListeners(), this.pollingListener)) {
                        this.observer.addListener(this.pollingListener);
                        log.info("Polling added: " + this.thread.getName());
                    }
                    startPollingMode();
                } else {
                    try {
                        if(this.monitor!=null) {
                            this.monitor.stop();
                        }
                    } catch (final Exception e) {
                        throw new RuntimeException(e);
                    }
                    this.observer.removeListener(this.pollingListener);
                    this.monitor = null;
                    log.info("Polling removed: " + this.thread.getName());
                }
            });
            if (PreferencesService.getPreference(PreferenceConstants.POLLING_FILE_MODE, false)) {
                this.observer.addListener(this.pollingListener);
                startPollingMode();
                log.info("Polling added: " + this.thread.getName());
            } else {
                log.info("Polling not added: " + this.thread.getName());
            }
    }

    private void startPollingMode() {
        if (this.allowPolling && PreferencesService.getPreference(PreferenceConstants.POLLING_FILE_MODE, false)) {
            try {
                if (this.monitor == null) {
                    this.monitor = new FileAlterationMonitor(INTERVAL, this.observer);
                    log.info("FileAlterationMonitor added: " + this.thread.getName());
                }
                this.monitor.start();
            }catch (final IllegalStateException ex){//already running
                log.warn("monitor already running");
            }catch (final Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void run() {
        try (final WatchService watchService = FileSystems.getDefault().newWatchService()) {
            final Path path = Paths.get(this.folder.getAbsolutePath());
            path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
            boolean poll = true;
            if (this.allowPolling && PreferencesService.getPreference(PreferenceConstants.POLLING_FILE_MODE, false)) {
                startPollingMode();
            }
            while (!this.thread.isInterrupted() && poll) {
                poll = pollEvents(watchService);
            }
        } catch (final Exception e) {
            Thread.currentThread().interrupt();
        } finally {
            try {
                if (this.allowPolling && this.monitor != null) {
                    this.monitor.stop();
                    log.info("terminated FileAlterationMonitor: " + this.thread.getName());
                }
            } catch (final Exception e) {
                log.error("failed to terminate FileAlterationMonitor: " + this.thread.getName(), e);
            }

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