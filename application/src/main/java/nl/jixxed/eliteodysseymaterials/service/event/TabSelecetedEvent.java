package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.Tabs;

@AllArgsConstructor
@Getter
public class TabSelecetedEvent implements Event {
    private final Tabs selectedTab;
}
