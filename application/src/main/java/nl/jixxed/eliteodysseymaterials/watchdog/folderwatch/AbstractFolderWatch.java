package nl.jixxed.eliteodysseymaterials.watchdog.folderwatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractFolderWatch implements Runnable, FolderWatch{
    protected final Thread thread;
    public AbstractFolderWatch(final String threadName) {
        this.thread = new Thread(this);
        this.thread.setName(threadName);
        this.thread.setDaemon(true);
    }

    @Override
    public void terminate() {
        log.info("Terminating " + this.thread.getName());
        this.thread.interrupt();
    }
}
