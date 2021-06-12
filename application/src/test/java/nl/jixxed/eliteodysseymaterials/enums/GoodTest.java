package nl.jixxed.eliteodysseymaterials.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class GoodTest {
    @Test
    void testForName_Unknown() {
        final Good unknown = Good.forName("f493ft30cmg075hmx87h");
        Assertions.assertEquals(Good.UNKNOWN, unknown);
    }

    @ParameterizedTest
    @ValueSource(strings = {"push", "Push", "PUSH"})
    void testForName_CaseInsensitive(final String input) {
        final Good push = Good.forName(input);
        Assertions.assertEquals(Good.PUSH, push);
    }
}
