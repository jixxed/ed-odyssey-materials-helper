package nl.jixxed.eliteodysseymaterials.watchdog;

public abstract class FileAdapter implements FileListener {

    @Override
    public void onCreated(final FileEvent event) {
        // no implementation provided
    }

    @Override
    public void onModified(final FileEvent event) {
        // no implementation provided
    }

    @Override
    public void onDeleted(final FileEvent event) {
        // no implementation provided
    }
}