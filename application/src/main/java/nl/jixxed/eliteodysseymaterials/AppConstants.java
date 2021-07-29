package nl.jixxed.eliteodysseymaterials;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConstants {

    static final String APP_TITLE = "ED Odyssey Materials Helper";
    static final String APP_ICON_PATH = "/images/application/rocket.png";
    private static final String FOLDER_USER_PROFILE = System.getenv("USERPROFILE");
    public static final String WATCHED_FOLDER = FOLDER_USER_PROFILE + "\\Saved Games\\Frontier Developments\\Elite Dangerous";
    static final String SHIPLOCKER_FILE = "ShipLocker.json";
    static final String BACKPACK_FILE = "Backpack.json";
    public static final String JOURNAL_FILE_PREFIX = "Journal.";
}
