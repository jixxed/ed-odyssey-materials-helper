package nl.jixxed.eliteodysseymaterials.service.eddn;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class EDDNCommodityMapperTest {
    @Test
    void testCleanName() {
        final String test = EDDNCommodityMapper.cleanName("$test_name;");
        Assertions.assertThat(test).isEqualTo("test" );
        final String test2 = EDDNCommodityMapper.cleanName("$test_name");
        Assertions.assertThat(test2).isEqualTo("$test_name" );
        final String test3 = EDDNCommodityMapper.cleanName("test_name;");
        Assertions.assertThat(test3).isEqualTo("test_name;" );
    }
}