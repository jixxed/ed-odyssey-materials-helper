package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.hge.MessageV2;

@RequiredArgsConstructor
@Getter
public class HighGradeEmissionReceivedEvent implements Event {
    private final MessageV2 message;
}
