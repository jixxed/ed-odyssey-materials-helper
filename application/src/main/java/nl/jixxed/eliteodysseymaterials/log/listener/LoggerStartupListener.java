package nl.jixxed.eliteodysseymaterials.log.listener;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;
import nl.jixxed.eliteodysseymaterials.helper.OsCheck;

import java.nio.file.Paths;

public class LoggerStartupListener extends ContextAwareBase implements LoggerContextListener, LifeCycle {

    private boolean started = false;

    @Override
    public void start() {
        if (started) return;

        Context context = getContext();
        String dir;
        if (OsCheck.isLinux()) {
            String userHome = System.getProperty("user.home");
            dir = userHome + "/.ed-odyssey-materials-helper/";
        } else if (OsCheck.isWindows()) {
            final String binDir = Paths.get(ProcessHandle.current().info().command().orElseThrow(IllegalArgumentException::new)).getParent().toString();
            dir = binDir.trim().replace("\"", "") + "\\";
        } else {
            dir = "";
        }
        System.setProperty("logging.config", dir + "log");
        context.putProperty("logging.config", dir + "log");
        started = true;
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public boolean isResetResistant() {
        return true;
    }

    @Override
    public void onStart(LoggerContext context) {
    }

    @Override
    public void onReset(LoggerContext context) {
    }

    @Override
    public void onStop(LoggerContext context) {
    }

    @Override
    public void onLevelChange(Logger logger, Level level) {

    }
}