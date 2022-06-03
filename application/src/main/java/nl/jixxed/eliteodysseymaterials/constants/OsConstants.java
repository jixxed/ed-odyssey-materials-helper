package nl.jixxed.eliteodysseymaterials.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.helper.OsCheck;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings({"java:S1104", "java:S1444", "java:S3008"})
public class OsConstants {
    private static final String LOCALAPPDATA = "LOCALAPPDATA";
    private static final String PROGRAMDATA = "PROGRAMDATA";
    private static String USER_HOME;
    public static String CONFIG_DIRECTORY;
    public static String DEFAULT_WATCHED_FOLDER;
    public static String PREFERENCES;
    public static String DEEPLINK;
    public static String LOCK;
    public static String DEEPLINK_FOLDER;
    public static String STATISTICS;
    public static String CUSTOM_CSS;
    public static String TESS4J;
    public static String OLD_PREFERENCES;
    public static String OLD_CUSTOM_CSS;
    public static String OS_SLASH;

    static {
        switch (OsCheck.getOperatingSystemType()) {
            case LINUX -> setLinux();
            case WINDOWS -> setWindows();
            default -> throw new IllegalArgumentException("OS not supported.");
        }
    }

    private static void setWindows() {
        USER_HOME = System.getenv("USERPROFILE");
        DEFAULT_WATCHED_FOLDER = USER_HOME + "\\Saved Games\\Frontier Developments\\Elite Dangerous";
        CONFIG_DIRECTORY = System.getenv(LOCALAPPDATA) + "\\odyssey-materials-helper";
        PREFERENCES = CONFIG_DIRECTORY + "\\pref.properties";
        TESS4J = CONFIG_DIRECTORY + "\\tesseract";
        DEEPLINK_FOLDER = CONFIG_DIRECTORY;
        DEEPLINK = CONFIG_DIRECTORY + "\\deeplink";
        LOCK = CONFIG_DIRECTORY + "\\lock";
        STATISTICS = CONFIG_DIRECTORY + "\\material-report.json";
        CUSTOM_CSS = CONFIG_DIRECTORY + "\\style.css";
        OLD_PREFERENCES = System.getenv(PROGRAMDATA) + "\\odyssey-materials-helper\\pref.properties";
        OLD_CUSTOM_CSS = System.getenv(PROGRAMDATA) + "\\odyssey-materials-helper\\style.css";
        OS_SLASH = "\\";
    }

    private static void setLinux() {
        USER_HOME = System.getProperty("user.home");
        CONFIG_DIRECTORY = USER_HOME + "/.config/odyssey-materials-helper";
        DEFAULT_WATCHED_FOLDER = USER_HOME + "/.steam/steam/steamapps/compatdata/359320/pfx/drive_c/users/steamuser/Saved Games/Frontier Developments/Elite Dangerous";
        PREFERENCES = CONFIG_DIRECTORY + "/pref.properties";
        TESS4J = CONFIG_DIRECTORY + "/tesseract";
        DEEPLINK_FOLDER = CONFIG_DIRECTORY;
        DEEPLINK = CONFIG_DIRECTORY + "/deeplink";
        LOCK = CONFIG_DIRECTORY + "/lock";
        STATISTICS = CONFIG_DIRECTORY + "/material-report.json";
        CUSTOM_CSS = CONFIG_DIRECTORY + "/style.css";
        OS_SLASH = "/";
    }
}
