package nl.jixxed.eliteodysseymaterials.constants;

public class WindowsPathConfiguration implements PathConfiguration {
    private static final String LOCALAPPDATA = "LOCALAPPDATA";
    private static final String CONFIG_DIRECTORY;
    private static final String DEFAULT_WATCHED_FOLDER;
    private static final String OS_SLASH;

    static {
        String userHome = System.getenv("USERPROFILE");
        DEFAULT_WATCHED_FOLDER = userHome + "\\Saved Games\\Frontier Developments\\Elite Dangerous";
        CONFIG_DIRECTORY = System.getenv(LOCALAPPDATA) + "\\odyssey-materials-helper";
        OS_SLASH = "\\";
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
