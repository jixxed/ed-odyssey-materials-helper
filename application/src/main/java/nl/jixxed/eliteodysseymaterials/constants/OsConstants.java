package nl.jixxed.eliteodysseymaterials.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.helper.OsCheck;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings({"java:S1104", "java:S1444", "java:S3008"})
public class OsConstants {
    private static PathConfiguration pathConfig;
//    private static final String LOCALAPPDATA = "LOCALAPPDATA";
//    private static final String PROGRAMDATA = "PROGRAMDATA";
//    private static String USER_HOME;
//    private static String CONFIG_DIRECTORY;
//    private static String DEFAULT_WATCHED_FOLDER;
//    private static String PREFERENCES;
//    private static String PREFERENCES_TEMP;
//
//    private static String DEEPLINK;
//    private static String LOCK;
//    private static String DEEPLINK_FOLDER;
//    private static String STATISTICS;
//    private static String CUSTOM_CSS;
//    private static String TESS4J;
//    private static String OS_SLASH;

    static {
        switch (OsCheck.getOperatingSystemType()) {
            case LINUX -> pathConfig = new LinuxPathConfiguration();
            case WINDOWS -> pathConfig = new WindowsPathConfiguration();
            case MACOS -> pathConfig = new MacOSPathConfiguration();
            default -> throw new IllegalArgumentException("OS not supported.");
        }
    }

    // For testing purposes only
    public static void setPathConfiguration(PathConfiguration configuration) {
        pathConfig = configuration;
    }
//    private static void setWindows() {
//        USER_HOME = System.getenv("USERPROFILE");
//        DEFAULT_WATCHED_FOLDER = USER_HOME + "\\Saved Games\\Frontier Developments\\Elite Dangerous";
//        CONFIG_DIRECTORY = System.getenv(LOCALAPPDATA) + "\\odyssey-materials-helper";
//        PREFERENCES = CONFIG_DIRECTORY + "\\pref.properties";
//        PREFERENCES_TEMP = CONFIG_DIRECTORY + "\\pref.tmp";
//        TESS4J = CONFIG_DIRECTORY + "\\tesseract";
//        DEEPLINK_FOLDER = CONFIG_DIRECTORY;
//        DEEPLINK = CONFIG_DIRECTORY + "\\deeplink";
//        LOCK = CONFIG_DIRECTORY + "\\lock";
//        STATISTICS = CONFIG_DIRECTORY + "\\material-report.json";
//        CUSTOM_CSS = CONFIG_DIRECTORY + "\\style.css";
//        OS_SLASH = "\\";
//    }
//
//    private static void setLinux() {
//        USER_HOME = System.getProperty("user.home");
//        CONFIG_DIRECTORY = USER_HOME + "/.config/odyssey-materials-helper";
//        DEFAULT_WATCHED_FOLDER = USER_HOME + "/.steam/steam/steamapps/compatdata/359320/pfx/drive_c/users/steamuser/Saved Games/Frontier Developments/Elite Dangerous";
//        PREFERENCES = CONFIG_DIRECTORY + "/pref.properties";
//        PREFERENCES_TEMP = CONFIG_DIRECTORY + "/pref.tmp";
//        TESS4J = CONFIG_DIRECTORY + "/tesseract";
//        DEEPLINK_FOLDER = CONFIG_DIRECTORY;
//        DEEPLINK = CONFIG_DIRECTORY + "/deeplink";
//        LOCK = CONFIG_DIRECTORY + "/lock";
//        STATISTICS = CONFIG_DIRECTORY + "/material-report.json";
//        CUSTOM_CSS = CONFIG_DIRECTORY + "/style.css";
//        OS_SLASH = "/";
//    }
//
//    private static void setMacOS() {
//        USER_HOME = System.getProperty("user.home");
//        CONFIG_DIRECTORY = USER_HOME + "/.config/odyssey-materials-helper";
//        DEFAULT_WATCHED_FOLDER = USER_HOME + "/configure/your/journal/folder";
//        PREFERENCES = CONFIG_DIRECTORY + "/pref.properties";
//        PREFERENCES_TEMP = CONFIG_DIRECTORY + "/pref.tmp";
//        TESS4J = CONFIG_DIRECTORY + "/tesseract";
//        DEEPLINK_FOLDER = CONFIG_DIRECTORY;
//        DEEPLINK = CONFIG_DIRECTORY + "/deeplink";
//        LOCK = CONFIG_DIRECTORY + "/lock";
//        STATISTICS = CONFIG_DIRECTORY + "/material-report.json";
//        CUSTOM_CSS = CONFIG_DIRECTORY + "/style.css";
//        OS_SLASH = "/";
//    }
//    private static final String LOCALAPPDATA = "LOCALAPPDATA";
//    private static final String PROGRAMDATA = "PROGRAMDATA";
//    private static String USER_HOME;
//    public static String CONFIG_DIRECTORY;
//    public static String DEFAULT_WATCHED_FOLDER;
//    public static String PREFERENCES;
//    public static String PREFERENCES_TEMP;
//
//    public static String DEEPLINK;
//    public static String LOCK;
//    public static String DEEPLINK_FOLDER;
//    public static String STATISTICS;
//    public static String CUSTOM_CSS;
//    public static String TESS4J;
//    public static String OS_SLASH;

    public static String getConfigDirectory() {
        return pathConfig.getConfigDirectory();
    }

    public static String getDefaultWatchedFolder() {
        return pathConfig.getDefaultWatchedFolder();
    }

    public static String getPreferences() {
        return pathConfig.getConfigDirectory() + pathConfig.getOsSlash() + "pref.properties";
    }

    public static String getPreferencesTemp() {
        return pathConfig.getConfigDirectory() + pathConfig.getOsSlash() + "pref.tmp";
    }

    public static String getDeeplink() {
        return pathConfig.getConfigDirectory() + pathConfig.getOsSlash() + "deeplink";
    }

    public static String getLock() {
        return pathConfig.getConfigDirectory() + pathConfig.getOsSlash() + "lock";
    }

    public static String getDeeplinkFolder() {
        return pathConfig.getConfigDirectory();
    }

    public static String getStatistics() {
        return pathConfig.getConfigDirectory() + pathConfig.getOsSlash() + "material-report.json";
    }

    public static String getCustomCss() {
        return pathConfig.getConfigDirectory() + pathConfig.getOsSlash() + "style.css";
    }

    public static String getTess4j() {
        return pathConfig.getConfigDirectory() + pathConfig.getOsSlash() + "tesseract";
    }

    public static String getOsSlash() {
        return pathConfig.getOsSlash();
    }

}
