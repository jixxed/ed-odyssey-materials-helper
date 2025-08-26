package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.SquadronPerk;
@RequiredArgsConstructor
@Getter
public class PerkChangedEvent implements Event {
    private final SquadronPerk oldPerk;
    private final SquadronPerk newPerk;
}
