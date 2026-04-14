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

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.function.Consumer;

@Getter
@Slf4j
class NonStaticEventListener<T extends Event> extends EventListener<T> {
    private final WeakReference<Object> ownerRef;

    NonStaticEventListener(final boolean fxThread, final Object owner, final Integer priority, final Class<T> eventClass, final Consumer<T> consumer) {
        super(fxThread, priority, eventClass, consumer);
        this.ownerRef = new WeakReference<>(owner);
    }

    @Override
    void handleEvent(final T event) {
        if (this.ownerRef == null || this.ownerRef.get() == null) {
            dispose();
        } else {
            super.handleEvent(event);
        }
    }

    boolean hasOwner(final Object owner) {
        return Objects.equals(this.ownerRef.get(), owner);
    }

    private void dispose() {
        EventService.removeListener(this);
    }
}
