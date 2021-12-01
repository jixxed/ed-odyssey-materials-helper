package nl.jixxed.eliteodysseymaterials.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.helper.OsCheck;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OsConstants {
    private static String USER_HOME;
    public static String DEFAULT_WATCHED_FOLDER;
    public static String PREFERENCES;
    public static String CUSTOM_CSS;

    static {
        switch (OsCheck.getOperatingSystemType()) {
            case MACOS, OTHER -> throw new IllegalArgumentException("OS not supported.");
            case LINUX -> setLinux();
            case WINDOWS -> setWindows();
        }
    }

    private static void setWindows() {
        USER_HOME = System.getenv("USERPROFILE");
        DEFAULT_WATCHED_FOLDER = USER_HOME + "\\Saved Games\\Frontier Developments\\Elite Dangerous";
        PREFERENCES = System.getenv("PROGRAMDATA") + "\\odyssey-materials-helper\\pref.properties";
        CUSTOM_CSS = System.getenv("PROGRAMDATA") + "\\odyssey-materials-helper\\style.css";
    }

    private static void setLinux() {
        USER_HOME = System.getProperty("user.home");
        DEFAULT_WATCHED_FOLDER = USER_HOME + "/.steam/steam/steamapps/compatdata/359320/pfx/drive_c/users/steamuser/Saved Games/Frontier Developments/Elite Dangerous";
        PREFERENCES = USER_HOME + "/.config/odyssey-materials-helper/pref.properties";
        CUSTOM_CSS = USER_HOME + "/.config/odyssey-materials-helper/style.css";
    }
}
