package nl.jixxed.eliteodysseymaterials.constants;

public class LinuxPathConfiguration implements PathConfiguration {
    private static final String CONFIG_DIRECTORY;
    private static final String DEFAULT_WATCHED_FOLDER;
    private static final String OS_SLASH;

    static {
        String userHome = System.getProperty("user.home");
        CONFIG_DIRECTORY = userHome + "/.config/odyssey-materials-helper";
        DEFAULT_WATCHED_FOLDER = userHome + "/.steam/steam/steamapps/compatdata/359320/pfx/drive_c/users/steamuser/Saved Games/Frontier Developments/Elite Dangerous";
        OS_SLASH = "/";
    }


    public String getConfigDirectory() {
        return CONFIG_DIRECTORY;
    }

    public String getDefaultWatchedFolder() {
        return DEFAULT_WATCHED_FOLDER;
    }

    public String getOsSlash() {
        return OS_SLASH;
    }
}
