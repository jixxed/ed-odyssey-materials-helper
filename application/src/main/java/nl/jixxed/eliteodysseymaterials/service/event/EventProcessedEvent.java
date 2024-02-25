package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EventProcessedEvent implements Event {
    private final Integer current;
    private final Integer total;
}