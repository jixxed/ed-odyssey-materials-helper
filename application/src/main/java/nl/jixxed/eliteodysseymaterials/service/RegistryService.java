package nl.jixxed.eliteodysseymaterials.service;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.helper.OsCheck;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class RegistryService {
    private static final String binDir = Paths.get(ProcessHandle.current().info().command().orElseThrow(IllegalArgumentException::new)).getParent().toString();
    private static final boolean isJava = ProcessHandle.current().info().command().map(s -> s.endsWith("java.exe") || s.endsWith("java")).orElse(false);
    private static final String currentDirSingleSlashed = (binDir.trim().replace("\"", "") + (OsCheck.isWindows() ? "\\" : "/"));
    private static final String currentDirDoubleSlashed = currentDirSingleSlashed.replace("\\", "\\\\");
    private static final String regKey = """
            Windows Registry Editor Version 5.00
                        
            [HKEY_CLASSES_ROOT\\edomh]
            "URL Protocol"=""
                        
            [HKEY_CLASSES_ROOT\\edomh\\shell]
                        
            [HKEY_CLASSES_ROOT\\edomh\\shell\\open]
                        
            [HKEY_CLASSES_ROOT\\edomh\\shell\\open\\command]
            @=\"\\\"""" + currentDirDoubleSlashed + "Elite Dangerous Odyssey Materials Helper.exe\\\" \\\"%1\\\"\"";
    private static final String desktopfile = """
            [Desktop Entry]
            Name=Elite Dangerous Odyssey Materials Helper
            Exec=""" + currentDirSingleSlashed + """
            Elite\\ Dangerous\\ Odyssey\\ Materials\\ Helper %u
            Type=Application
            NoDisplay=true
            Terminal=false
            MimeType=x-scheme-handler/edomh;
            """;

    public static void registerApplication() {
        if (!isJava && OsCheck.isWindows()) {
            try {
                final File file = Files.createTempFile("edomh", ".reg").toFile();
                try (final OutputStream output = new FileOutputStream(file)) {
                    output.write(regKey.getBytes(StandardCharsets.UTF_8));
                } catch (final IOException e) {
                    log.error("Error creating reg file", e);
                }

                Runtime.getRuntime().exec("powershell Start-Process \"reg\" -ArgumentList @('import', '" + file.getAbsolutePath() + "') -Verb RunAs").waitFor();//

            } catch (final IOException | InterruptedException e) {
                log.error("Error creating registry entry", e);
            }
        } else if (!isJava && OsCheck.isLinux()) {
            try {
                final File file = Files.createFile(Path.of(System.getProperty("user.home") + "/.local/share/applications/edomh.desktop")).toFile();
                try (final OutputStream output = new FileOutputStream(file)) {
                    output.write(desktopfile.getBytes(StandardCharsets.UTF_8));
                }
                Runtime.getRuntime().exec("xdg-mime default " + System.getProperty("user.home") + "/.local/share/applications/edomh.desktop x-scheme-handler/edomh").waitFor();//
                Runtime.getRuntime().exec("update-desktop-database /.local/share/applications/").waitFor();//

            } catch (final IOException | InterruptedException e) {
                log.error("Error creating desktop file", e);
            }
        }
    }

    public static void unregisterApplication() {
        try {
            if (!isJava && OsCheck.isWindows()) {
                    Runtime.getRuntime().exec("powershell Start-Process \"reg\" -ArgumentList @('delete', 'HKEY_CLASSES_ROOT\\edomh', '/f') -Verb RunAs").waitFor();//
            } else if (!isJava && OsCheck.isLinux()) {
                final File file = new File(System.getProperty("user.home") + "/.local/share/applications/edomh.desktop");
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
                Runtime.getRuntime().exec("update-desktop-database /.local/share/applications/").waitFor();//
            }
        } catch (final IOException | InterruptedException e) {
            log.error("Error deleting registry entry", e);
        }
    }

    public static boolean isRegistered() {
        if (!isJava && OsCheck.isWindows()) {
            return getValue().equals("\"" + currentDirSingleSlashed + "Elite Dangerous Odyssey Materials Helper.exe\" \"%1\"");
        } else if (!isJava && OsCheck.isLinux()) {
            final File file = new File(System.getProperty("user.home") + "/.local/share/applications/edomh.desktop");
            return file.exists() && file.isFile();
        }
        return false;
    }

    private static String getValue() {
        return getValue("HKEY_CLASSES_ROOT\\edomh\\shell\\open\\command", "");
    }

    private static String getValue(final String keyPath, final String keyName) {

        final Process keyReader;
        try {
            keyReader = Runtime.getRuntime().exec("reg query \"" + keyPath + "\" /v \"" + keyName + "\"");
            final BufferedReader outputReader;
            String readLine;
            final StringBuilder value = new StringBuilder();

            outputReader = new BufferedReader(new InputStreamReader(
                    keyReader.getInputStream()));

            while ((readLine = outputReader.readLine()) != null) {
                value.append(readLine);
            }

            final String[] outputComponents = value.toString().split("\s{4}");

            keyReader.waitFor();

            return outputComponents[outputComponents.length - 1];
        } catch (final IOException | InterruptedException e) {
            log.error("failed to read registry", e);
        }
        return "";
    }
}