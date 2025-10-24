package nl.jixxed.eliteodysseymaterials.parser;

import nl.jixxed.eliteodysseymaterials.schemas.journal.Event;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class JournalEventTypesTest {
    @Test
    void testJournalEventTypes() {
        Map<String, Class<? extends Event>> eventTypes = JournalEventTypes.EVENT_TYPES;
        Assertions.assertThat(eventTypes).isNotEmpty();
    }
}