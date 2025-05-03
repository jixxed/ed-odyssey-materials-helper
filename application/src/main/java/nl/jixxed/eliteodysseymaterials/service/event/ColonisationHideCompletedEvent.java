package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ColonisationHideCompletedEvent implements Event {
    private final Boolean hideCompleted;
}
