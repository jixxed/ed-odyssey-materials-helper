package nl.jixxed.bootstrap;


class OsConstants {
    static String KILL_COMMAND;
    static String START_COMMAND;
    static String UPDATE_FILE_SUFFIX;
    static String VERSION_FILE;

    static {
        switch (OsCheck.getOperatingSystemType()) {
            case MacOS, Other -> throw new IllegalArgumentException("OS not supported.");
            case Linux -> setLinux();
            case Windows -> setWindows();
        }
    }

    private OsConstants() {
    }

    private static void setWindows() {
        UPDATE_FILE_SUFFIX = "portable.zip";
        KILL_COMMAND = "taskkill /F /IM \"Elite Dangerous Odyssey Materials Helper.exe\"";
        START_COMMAND = "cmd /c \"%s\\Elite Dangerous Odyssey Materials Helper.exe\"";
        VERSION_FILE = "%s\\app\\Elite Dangerous Odyssey Materials Helper.cfg";
    }

    private static void setLinux() {
        UPDATE_FILE_SUFFIX = ".portable.linux.zip";
        KILL_COMMAND = "ps aux | grep 'Elite Dangerous Odyssey Materials Helper' | grep -v 'grep' | awk '{ print $2 }' | xargs kill -SIGTERM";
        START_COMMAND = "%s/bin/Elite Dangerous Odyssey Materials Helper";
        VERSION_FILE = "%s/lib/app/Elite Dangerous Odyssey Materials Helper.cfg";
    }
}
