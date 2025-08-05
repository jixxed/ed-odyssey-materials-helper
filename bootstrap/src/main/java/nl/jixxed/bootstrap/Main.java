package nl.jixxed.bootstrap;

import com.sun.jna.platform.win32.Advapi32;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

@Slf4j
public class Main {

    public static void main(final String[] args) {
        log.info("Launching launcher with java version: " + getVersion());
        if (OsCheck.isWindows()) {
            boolean runningAsAdmin = isRunningAsAdmin();
            boolean uacEnabled = isUACEnabled();
            log.info("runningAsAdmin: {}, uacEnabled: {}", runningAsAdmin, uacEnabled);
            if (runningAsAdmin && uacEnabled) {
                log.error("This application should not be started with elevated privileges. Please restart the app without elevated privileges.");
                AdminError.launchFx(args);
            } else {
                Launcher.launchFx(args);
            }
        } else {
            Launcher.launchFx(args);
        }
    }

    public static boolean isRunningAsAdmin() {
        WinNT.HANDLEByReference hToken = new WinNT.HANDLEByReference();
        boolean isAdmin = false;

        // Open the access token of the current process
        if (Advapi32.INSTANCE.OpenProcessToken(Kernel32.INSTANCE.GetCurrentProcess(),
                WinNT.TOKEN_QUERY, hToken)) {

            // Query the token elevation
            WinNT.TOKEN_ELEVATION elevation = new WinNT.TOKEN_ELEVATION();
            IntByReference returnLength = new IntByReference();

            boolean result = Advapi32.INSTANCE.GetTokenInformation(hToken.getValue(),
                    WinNT.TOKEN_INFORMATION_CLASS.TokenElevation, elevation,
                    elevation.size(), returnLength);

            if (result) {
                elevation.read();
                isAdmin = elevation.TokenIsElevated != 0;
            }

            Kernel32.INSTANCE.CloseHandle(hToken.getValue());
        }

        return isAdmin;
    }

    public static boolean isUACEnabled() {
        return Objects.equals(getRegistryValue("HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Policies\\System", "EnableLUA"), "0x1");
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


    private static int getVersion() {
        String version = System.getProperty("java.version");
        if (version.startsWith("1.")) {
            version = version.substring(2, 3);
        } else {
            final int dot = version.indexOf(".");
            if (dot != -1) {
                version = version.substring(0, dot);
            }
        }
        return Integer.parseInt(version);
    }
}
