package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.enums.GameMode;

@Getter
@AllArgsConstructor
public class LoadGameEvent implements Event {
    private final GameMode gameMode;
    private final Expansion expansion;
}
