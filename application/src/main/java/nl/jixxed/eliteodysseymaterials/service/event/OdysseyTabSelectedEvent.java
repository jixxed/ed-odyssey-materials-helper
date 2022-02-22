package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs;

@AllArgsConstructor
@Getter
public class OdysseyTabSelectedEvent implements Event {
    private final OdysseyTabs selectedTab;
}
