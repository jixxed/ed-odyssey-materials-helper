package nl.jixxed.eliteodysseymaterials.service.registry;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class WindowsRegistrationHandler implements RegistrationHandler {
    private static final String BIN_DIR = Paths.get(ProcessHandle.current().info().command().orElseThrow(IllegalArgumentException::new)).getParent().toString();
    private static final boolean IS_JAVA = ProcessHandle.current().info().command().map(s -> s.endsWith("java.exe")).orElse(false);
    public static final String CURRENT_DIR_SINGLE_SLASHED = (BIN_DIR.trim().replace("\"", "") + "\\");
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
        if (!IS_JAVA) {
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
            if (!IS_JAVA) {
                Runtime.getRuntime().exec("powershell Start-Process \"reg\" -ArgumentList @('delete', 'HKEY_CLASSES_ROOT\\edomh', '/f') -Verb RunAs").waitFor();//
            }
        } catch (final IOException | InterruptedException e) {
            log.error("Error deleting registry entry", e);
        }
    }

    @Override
    public boolean isRegistered() {
        if (!IS_JAVA) {
            final String registryValue = getRegistryValue();
            final String expectedValue = "\"" + CURRENT_DIR_SINGLE_SLASHED + "Elite Dangerous Odyssey Materials Helper.exe\" \"%1\"";
            log.info("Registry value: {}", registryValue);
            log.info("Expected value: {}", expectedValue);
            final boolean equals = registryValue.equals(expectedValue);
            log.info("Registry equals expected: {}", equals);
            return equals;
        }
        return false;
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
