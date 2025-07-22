package nl.jixxed.eliteodysseymaterials.service.registry;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.service.VersionService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@Slf4j
public class WindowsRegistrationHandler implements RegistrationHandler {
    private static final String BIN_DIR = Paths.get(ProcessHandle.current().info().command().orElseThrow(IllegalArgumentException::new)).getParent().toString();
    private static final String CURRENT_DIR_SINGLE_SLASHED = (BIN_DIR.trim().replace("\"", "") + "\\");
    private static final String CURRENT_DIR_DOUBLE_SLASHED = CURRENT_DIR_SINGLE_SLASHED.replace("\\", "\\\\");
    private static final String REG_KEY = """
            Windows Registry Editor Version 5.00
            
            [HKEY_CLASSES_ROOT\\edomh]
            "URL Protocol"=""
            
            [HKEY_CLASSES_ROOT\\edomh\\shell]
            
            [HKEY_CLASSES_ROOT\\edomh\\shell\\open]
            
            [HKEY_CLASSES_ROOT\\edomh\\shell\\open\\command]
            @=\"\\\"""" + CURRENT_DIR_DOUBLE_SLASHED + "Elite Dangerous Odyssey Materials Helper.exe\\\" \\\"%1\\\"\"\n\n";

    @Override
    public void register() {
        if (!VersionService.isDev()) {
            try {
                final File file = Files.createTempFile("edomh", ".reg").toFile();
                writeRegFile(file);

                Runtime.getRuntime().exec("powershell Start-Process \"reg\" -ArgumentList @('import', '" + file.getAbsolutePath() + "') -Verb RunAs").waitFor();//

            } catch (final IOException | InterruptedException e) {
                log.error("Error creating registry entry", e);
            }
        }
    }

    @Override
    public void unregister() {
        try {
            if (!VersionService.isDev()) {
                Runtime.getRuntime().exec("powershell Start-Process \"reg\" -ArgumentList @('delete', 'HKEY_CLASSES_ROOT\\edomh', '/f') -Verb RunAs").waitFor();//
            }
        } catch (final IOException | InterruptedException e) {
            log.error("Error deleting registry entry", e);
        }
    }

    @Override
    public boolean isRegistered() {
        if (!VersionService.isDev()) {
            final String registryValue = getRegistryValue();
            final String expectedValue = "\"" + CURRENT_DIR_SINGLE_SLASHED + "Elite Dangerous Odyssey Materials Helper.exe\" \"%1\"";
            final String expectedValueAlt = "\"%USERPROFILE%\\AppData\\Local\\Elite Dangerous Odyssey Materials Helper Launcher\\program\\Elite Dangerous Odyssey Materials Helper.exe\" \"%1\"";
            log.info("Registry value: {}", registryValue);
            log.info("Expected value: {}", expectedValue);
            final boolean equals = registryValue.equals(expectedValue);
            log.info("Expected alt value: {}", expectedValueAlt);
            final boolean altEquals = registryValue.equals(expectedValueAlt);
            log.info("Registry equals expected: {}", equals || altEquals);
            return equals || altEquals;
        }
        return true;
    }

    @Override
    public boolean hasMediaServices() {
        return !Objects.equals(getRegistryValue("HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Setup\\WindowsFeatures\\WindowsMediaVersion", "Version"), "");
    }

    @Override
    public boolean isUACEnabled() {
        return Objects.equals(getRegistryValue("HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Policies\\System", "EnableLUA"), "0x1");
    }

    private static void writeRegFile(final File file) {
        try (final OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_16LE)) {
            output.write('\ufeff');
            output.write(REG_KEY.replaceAll("\r\n", "\n").replaceAll("\n", "\r\n"));
        } catch (final IOException e) {
            log.error("Error creating reg file", e);
        }
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
