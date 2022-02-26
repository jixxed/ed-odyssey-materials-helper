package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class BlueprintClickEvent implements Event {
    private final BlueprintName blueprintName;
    private boolean experimental;
}
