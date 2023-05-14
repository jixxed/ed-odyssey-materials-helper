package nl.jixxed.eliteodysseymaterials.watchdog;

import lombok.Getter;

import java.io.File;
import java.nio.file.WatchEvent;


public class FileEvent {
    @Getter
    private WatchEvent.Kind<?> kind;

    @Getter
    private File file;


    public FileEvent(final File file, final WatchEvent.Kind<?> kind) {
        this.kind = kind;
        this.file = file;
    }
}