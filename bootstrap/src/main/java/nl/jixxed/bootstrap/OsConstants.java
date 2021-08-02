package nl.jixxed.bootstrap;


class OsConstants {
    static String FILE_EXTENSION;
    static String KILL_COMMAND;
    static String START_COMMAND;
    static String UPDATE_FILE;
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
        FILE_EXTENSION = "zip";
        UPDATE_FILE = "portable.zip";
        KILL_COMMAND = "taskkill /F /IM \"Elite Dangerous Odyssey Materials Helper.exe\"";
        START_COMMAND = "cmd /c \"$0\\Elite Dangerous Odyssey Materials Helper.exe\"";
        VERSION_FILE = "$0\\app\\Elite Dangerous Odyssey Materials Helper.cfg";
    }

    private static void setLinux() {
        FILE_EXTENSION = "tar.gz";
        UPDATE_FILE = "portable.tar.gz";
        KILL_COMMAND = "";
        START_COMMAND = "";
        VERSION_FILE = "";
    }
}
