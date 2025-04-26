package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.LoadingStage;

@Getter
@AllArgsConstructor
public class LoadingEvent implements Event {
    private final LoadingStage loadingStage;
}
