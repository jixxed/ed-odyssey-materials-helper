package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.TabType;

@RequiredArgsConstructor
@Getter
public class CloneSelectedItemEvent implements Event {
    private final TabType selectedTab;
    private final TabType selectedChildTab;

}
