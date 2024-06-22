package nl.jixxed.eliteodysseymaterials.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.helper.OsCheck;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings({"java:S1075", "java:S2142", "java:S5665"})
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegistryService {
    private static final String BIN_DIR = Paths.get(ProcessHandle.current().info().command().orElseThrow(IllegalArgumentException::new)).getParent().toString();
    private static final boolean IS_JAVA = ProcessHandle.current().info().command().map(s -> s.endsWith("java.exe") || s.endsWith("java")).orElse(false);
    public static final String CURRENT_DIR_SINGLE_SLASHED = (BIN_DIR.trim().replace("\"", "") + (OsCheck.isWindows() ? "\\" : "/"));
    private static final String CURRENT_DIR_DOUBLE_SLASHED = CURRENT_DIR_SINGLE_SLASHED.replace("\\", "\\\\");
    private static final String REG_KEY = """
            Windows Registry Editor Version 5.00
                        
            [HKEY_CLASSES_ROOT\\edomh]
            "URL Protocol"=""
                        
            [HKEY_CLASSES_ROOT\\edomh\\shell]
                        
            [HKEY_CLASSES_ROOT\\edomh\\shell\\open]
                        
            [HKEY_CLASSES_ROOT\\edomh\\shell\\open\\command]
            @=\"\\\"""" + CURRENT_DIR_DOUBLE_SLASHED + "Elite Dangerous Odyssey Materials Helper.exe\\\" \\\"%1\\\"\"\n\n";
    private static final String DESKTOPFILE = """
            [Desktop Entry]
            Name=Elite Dangerous Odyssey Materials Helper
            Exec=""" + CURRENT_DIR_SINGLE_SLASHED + """
            Elite\\ Dangerous\\ Odyssey\\ Materials\\ Helper %u
            Type=Application
            NoDisplay=true
            Terminal=false
            MimeType=x-scheme-handler/edomh;
            """;
    private static final String USER_HOME = "user.home";
    private static final String DESKTOP_FILE_PATH = "/.local/share/applications/edomh.desktop";

    public static void registerApplication() {
        if (!IS_JAVA && OsCheck.isWindows()) {
            try {
                final File file = Files.createTempFile("edomh", ".reg").toFile();
                writeRegFile(file);

                Runtime.getRuntime().exec("powershell Start-Process \"reg\" -ArgumentList @('import', '" + file.getAbsolutePath() + "') -Verb RunAs").waitFor();//

            } catch (final IOException | InterruptedException e) {
                log.error("Error creating registry entry", e);
            }
        } else if (!IS_JAVA && OsCheck.isLinux()) {
            try {
                final File file = Files.createFile(Path.of(System.getProperty(USER_HOME) + DESKTOP_FILE_PATH)).toFile();
                writeDesktopFile(file);
                Runtime.getRuntime().exec("xdg-mime default " + System.getProperty(USER_HOME) + "/.local/share/applications/edomh.desktop x-scheme-handler/edomh").waitFor();//
                Runtime.getRuntime().exec("update-desktop-database /.local/share/applications/").waitFor();//

            } catch (final IOException | InterruptedException e) {
                log.error("Error creating desktop file", e);
            }
        }
    }

    private static void writeDesktopFile(final File file) throws IOException {
        try (final OutputStream output = new FileOutputStream(file)) {
            output.write(DESKTOPFILE.getBytes(StandardCharsets.UTF_8));
        }
    }

    private static void writeRegFile(final File file) {
        try (final OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_16LE)) {
            output.write('\ufeff');
            output.write(REG_KEY.replaceAll("\r\n", "\n").replaceAll("\n", "\r\n"));
        } catch (final IOException e) {
            log.error("Error creating reg file", e);
        }
    }

    public static void unregisterApplication() {
        try {
            if (!IS_JAVA && OsCheck.isWindows()) {
                Runtime.getRuntime().exec("powershell Start-Process \"reg\" -ArgumentList @('delete', 'HKEY_CLASSES_ROOT\\edomh', '/f') -Verb RunAs").waitFor();//
            } else if (!IS_JAVA && OsCheck.isLinux()) {
                final File file = new File(System.getProperty(USER_HOME) + DESKTOP_FILE_PATH);
                if (file.exists() && file.isFile()) {
                    Files.delete(file.getAbsoluteFile().toPath());
                }
                Runtime.getRuntime().exec("update-desktop-database /.local/share/applications/").waitFor();//
            }
        } catch (final IOException | InterruptedException e) {
            log.error("Error deleting registry entry", e);
        }
    }

    public static boolean isRegistered() {
        if (!IS_JAVA && OsCheck.isWindows()) {
            final String registryValue = getRegistryValue();
            final String expectedValue = "\"" + CURRENT_DIR_SINGLE_SLASHED + "Elite Dangerous Odyssey Materials Helper.exe\" \"%1\"";
            log.info("Registry value: {}", registryValue);
            log.info("Expected value: {}", expectedValue);
            final boolean equals = registryValue.equals(expectedValue);
            log.info("Registry equals expected: {}", equals);
            return equals;
        } else if (!IS_JAVA && OsCheck.isLinux()) {
            final File file = new File(System.getProperty(USER_HOME) + DESKTOP_FILE_PATH);
            return file.exists() && file.isFile();
        }
        return false;
    }

    private static String getRegistryValue() {
        return getRegistryValue("HKEY_CLASSES_ROOT\\edomh\\shell\\open\\command", "");
    }

    private static String getRegistryValue(final String keyPath, final String keyName) {

        final Process keyReader;
        try {
            keyReader = Runtime.getRuntime().exec("reg query \"" + keyPath + "\" /v \"" + keyName + "\"");
            final BufferedReader outputReader;
            String readLine;
            final StringBuilder value = new StringBuilder();

            outputReader = new BufferedReader(new InputStreamReader(
                    keyReader.getInputStream()/*, StandardCharsets.UTF_16LE*/));//this might be a fix

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