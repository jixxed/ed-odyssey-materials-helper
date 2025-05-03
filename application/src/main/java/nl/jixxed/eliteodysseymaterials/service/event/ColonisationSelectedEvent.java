package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItem;

@AllArgsConstructor
@Getter
public class ColonisationSelectedEvent implements Event {
    private final ColonisationItem colonisationItem;
}
