package nl.jixxed.eliteodysseymaterials.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AssetTest {
    @Test
    void testForName_Unknown() {
        final Asset unknown = Asset.forName("f493ft30cmg075hmx87h");
        Assertions.assertEquals(Asset.UNKNOWN, unknown);
    }

    @ParameterizedTest
    @ValueSource(strings = {"transmitter", "TrAnSmiTter", "TRANSMITTER"})
    void testForName_CaseInsensitive(final String input) {
        final Asset transmitter = Asset.forName(input);
        Assertions.assertEquals(Asset.TRANSMITTER, transmitter);
    }
}
