package nl.jixxed.eliteodysseymaterials.service.registry;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class UbuntuRegistrationHandler implements RegistrationHandler {
    private static final String BIN_DIR = Paths.get(ProcessHandle.current().info().command().orElseThrow(IllegalArgumentException::new)).getParent().toString();
    private static final boolean IS_JAVA = ProcessHandle.current().info().command().map(s -> s.endsWith("java")).orElse(false);
    public static final String CURRENT_DIR_SINGLE_SLASHED = (BIN_DIR.trim().replace("\"", "") + "/");
    private static final String USER_HOME = "user.home";
    private static final String DESKTOP_FILE_PATH = "/.local/share/applications/edomh.desktop";

    private static final String DESKTOPFILE = """
            [Desktop Entry]
            Name=Elite Dangerous Odyssey Materials Helper
            Exec=\"""" + CURRENT_DIR_SINGLE_SLASHED + """
            Elite Dangerous Odyssey Materials Helper" %u
            Type=Application
            NoDisplay=true
            Terminal=false
            MimeType=x-scheme-handler/edomh;
            """;

    @Override
    public void register() {
        try {
            if (!IS_JAVA) {
                final File file = Files.createFile(Path.of(System.getProperty(USER_HOME) + DESKTOP_FILE_PATH)).toFile();
                writeDesktopFile(file);
                Runtime.getRuntime().exec("xdg-mime default edomh.desktop x-scheme-handler/edomh").waitFor();
                Runtime.getRuntime().exec("update-desktop-database " + System.getProperty(USER_HOME) + "/.local/share/applications/").waitFor();//
            }
        } catch (final IOException | InterruptedException e) {
            log.error("Error creating desktop file", e);
        }
    }

    @Override
    public void unregister() {
        try {
            if (!IS_JAVA) {
                final File file = new File(System.getProperty(USER_HOME) + DESKTOP_FILE_PATH);
                if (file.exists() && file.isFile()) {
                    Files.delete(file.getAbsoluteFile().toPath());
                }
                Runtime.getRuntime().exec("update-desktop-database " + System.getProperty(USER_HOME) + "/.local/share/applications/").waitFor();//
            }

        } catch (final IOException | InterruptedException e) {
            log.error("Error deleting registry entry", e);
        }
    }

    @Override
    public boolean isRegistered() {
        if (!IS_JAVA) {
            final File file = new File(System.getProperty(USER_HOME) + DESKTOP_FILE_PATH);
            return file.exists() && file.isFile();
        }
        return false;
    }


    private static void writeDesktopFile(final File file) throws IOException {
        try (final OutputStream output = new FileOutputStream(file)) {
            output.write(DESKTOPFILE.getBytes(StandardCharsets.UTF_8));
        }
    }
}
