/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service.registry;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.service.VersionService;

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
    private static final String CURRENT_DIR_SINGLE_SLASHED = (BIN_DIR.trim().replace("\"", "") + "/");
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
            if (!VersionService.isDev()) {
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
            if (!VersionService.isDev()) {
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
        if (!VersionService.isDev()) {
            final File file = new File(System.getProperty(USER_HOME) + DESKTOP_FILE_PATH);
            return file.exists() && file.isFile();
        }
        return false;
    }

    @Override
    public boolean hasMediaServices() {
        return true;
    }

    @Override
    public boolean isUACEnabled() {
        return true;
    }

    private static void writeDesktopFile(final File file) throws IOException {
        try (final OutputStream output = new FileOutputStream(file)) {
            output.write(DESKTOPFILE.getBytes(StandardCharsets.UTF_8));
        }
    }
}
