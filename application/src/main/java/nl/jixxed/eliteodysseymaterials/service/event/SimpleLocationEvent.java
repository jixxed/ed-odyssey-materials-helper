package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.JournalEventType;

import java.util.Optional;

@AllArgsConstructor
@Getter
public class SimpleLocationEvent implements Event {
    private final Optional<String> starSystem;
    private final Optional<String> body;
    private final Optional<String> station;
    private final JournalEventType locationType;
}
