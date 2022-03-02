package nl.jixxed.eliteodysseymaterials;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Slf4j
public class Main {

    private static FileLock fileLock;

    public static void main(final String[] args) {
        //handle deeplinks
        if (args.length > 0 && args[0].startsWith("edomh://")) {
            handleDeeplink(args[0]);
        }

        //exit if app is already running
        if (isLocked()) {
            System.exit(0);
        }

        FXApplication.launchFx(args);
    }

    private static void handleDeeplink(final String arg) {
        try (final OutputStream output = new FileOutputStream(OsConstants.DEEPLINK + ".tmp")) {
            output.write(arg.getBytes(StandardCharsets.UTF_8));
        } catch (final IOException e) {
            log.error("Error creating create deeplink tmp file", e);
        }
        try {
            Files.move(Path.of(OsConstants.DEEPLINK + ".tmp"), Path.of(OsConstants.DEEPLINK), StandardCopyOption.REPLACE_EXISTING);
        } catch (final IOException e) {
            log.error("Error moving deeplink tmp file", e);
        }
    }


    private static boolean isLocked() {
        try {
            fileLock = new FileOutputStream(OsConstants.LOCK).getChannel().tryLock();
        } catch (final IOException exception) {
            log.error("error acquiring lock", exception);
            return true;
        }
        // null if the lock could not be acquired because another program holds an overlapping lock
        return (fileLock == null);
    }
}
