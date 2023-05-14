package nl.jixxed.eliteodysseymaterials.watchdog.folderwatch;

import java.nio.file.attribute.FileTime;

public record FilenameWithLastModifiedDate(String fileName, FileTime fileTime) {
}
