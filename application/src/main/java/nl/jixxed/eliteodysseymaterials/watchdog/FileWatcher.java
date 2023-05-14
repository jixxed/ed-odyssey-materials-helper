package nl.jixxed.eliteodysseymaterials.watchdog;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.TerminateApplicationEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileWatcher {

    private FileListener listener;
    private File folder;
    private final boolean allowPolling;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    FileWatcher() {
        this(false);
    }

    FileWatcher(final boolean allowPolling) {
        this.allowPolling = allowPolling;
        this.eventListeners.add(EventService.addStaticListener(TerminateApplicationEvent.class, event -> {
            stop();
        }));
    }

    FileWatcher watch(final File folder) {
            this.folder = folder;
            if (this.folder.exists()) {
                FileService.subscribe(this.folder.getAbsolutePath(), this.allowPolling, this.listener);
            }
        return this;
    }

    FileWatcher withListener(final FileListener listener) {
        this.listener = listener;
        return this;
    }

    void stop() {
        FileService.unsubscribe(this.listener);
    }
}