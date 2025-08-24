package nl.jixxed.eliteodysseymaterials.service.capi;

import java.io.File;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public interface EndpointHandler {
    void requestData();

    default boolean isOutdated(final File file) {
        if (!file.exists()) {
            return true;
        }
        final ZonedDateTime now = ZonedDateTime.now();
        final ZonedDateTime fileModified = ZonedDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), ZoneId.systemDefault());
        final long delay = /*300L*/ 300L * 1000L;// 5 minutes
        final long delayed = (now.toEpochSecond() - fileModified.toEpochSecond()) * 1000;
        return delayed > delay;
    }

    void cleanup();

    void enable();

    void disable();
}
