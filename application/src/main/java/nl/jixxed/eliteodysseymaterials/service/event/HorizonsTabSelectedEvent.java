package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabs;

@AllArgsConstructor
@Getter
public class HorizonsTabSelectedEvent implements Event {
    private final HorizonsTabs selectedTab;
}
