package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TooltipBlueprintsExpandEvent implements Event{
    private final boolean expanded;
}
