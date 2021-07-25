package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.Tabs;

@AllArgsConstructor
@Getter
public class TabSelecetedEvent extends Event {
    private final Tabs selectedTab;
}
