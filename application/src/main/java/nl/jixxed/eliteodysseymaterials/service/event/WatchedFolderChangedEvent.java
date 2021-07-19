package nl.jixxed.eliteodysseymaterials.service.event;

public class WatchedFolderChangedEvent extends Event {
    private final String path;

    public WatchedFolderChangedEvent(final String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
