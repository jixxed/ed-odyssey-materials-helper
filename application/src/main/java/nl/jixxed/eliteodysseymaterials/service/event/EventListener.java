/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service.event;

import javafx.application.Platform;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Getter
@Slf4j
public class EventListener<T extends Event> {
    protected final boolean fxThread;
    protected final Integer priority;
    protected final Class<T> eventClass;
    protected Consumer<T> consumer;
    @Setter
    protected boolean disabled = false;

    EventListener(final boolean fxThread, final Integer priority, final Class<T> eventClass, final Consumer<T> consumer) {
        this.fxThread = fxThread;
        this.priority = priority;
        this.eventClass = eventClass;
        this.consumer = consumer;
    }

    void handleEvent(final T event) {
        if (fxThread) {
            Platform.runLater(() -> {
                if (!this.disabled) {
                    this.consumer.accept(event);
                }
            });
        } else {
            this.consumer.accept(event);
        }

    }

}
