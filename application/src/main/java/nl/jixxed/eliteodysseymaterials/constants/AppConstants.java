package nl.jixxed.eliteodysseymaterials.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConstants {

    public static final String APP_TITLE = "ED Odyssey Materials Helper";
    public static final String APP_ICON_PATH = "/images/application/rocket.png";
    private static final String FOLDER_USER_PROFILE = System.getenv("USERPROFILE");
    public static final String WATCHED_FOLDER = FOLDER_USER_PROFILE + "\\Saved Games\\Frontier Developments\\Elite Dangerous";
    public static final String SHIPLOCKER_FILE = "ShipLocker.json";
    public static final String BACKPACK_FILE = "Backpack.json";
    public static final String JOURNAL_FILE_PREFIX = "Journal.";
}
