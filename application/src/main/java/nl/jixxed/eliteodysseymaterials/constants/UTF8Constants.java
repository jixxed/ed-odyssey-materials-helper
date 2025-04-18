package nl.jixxed.eliteodysseymaterials.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UTF8Constants {
    public static final String CHECK_TRUE = "\u2714";
    public static final String CHECK_FALSE = "\u2718";
    public static final String BULLET = "\u2022";

    public static String forBool(boolean value) {
        if (value) {
            return CHECK_TRUE;
        }
        return CHECK_FALSE;
    }
}
