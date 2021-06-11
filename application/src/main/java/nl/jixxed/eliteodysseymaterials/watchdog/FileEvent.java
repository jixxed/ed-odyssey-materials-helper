package nl.jixxed.eliteodysseymaterials.watchdog;

import java.io.File;
import java.util.EventObject;


public class FileEvent extends EventObject {

    public FileEvent(final File file) {
        super(file);
    }

    public File getFile() {
        return (File) getSource();
    }
}