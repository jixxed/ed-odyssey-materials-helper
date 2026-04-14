/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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