package nl.jixxed.eliteodysseymaterials.watchdog.folderwatch;

public interface FolderWatch {
    void terminate();

    public String getFolder();

    boolean isTerminated();
}
