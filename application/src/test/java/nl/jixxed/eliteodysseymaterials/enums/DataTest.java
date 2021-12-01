package nl.jixxed.eliteodysseymaterials.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DataTest {
    @Test
    void testForName_Unknown() {
        final Data unknown = Data.forName("f493ft30cmg075hmx87h");
        Assertions.assertEquals(Data.UNKNOWN, unknown);
    }

    @ParameterizedTest
    @ValueSource(strings = {"weaponinventory", "WeApoNInvenTory", "WEAPONINVENTORY"})
    void testForName_CaseInsensitive(final String input) {
        final Data weaponInventory = Data.forName(input);
        Assertions.assertEquals(Data.WEAPONINVENTORY, weaponInventory);
    }

}
