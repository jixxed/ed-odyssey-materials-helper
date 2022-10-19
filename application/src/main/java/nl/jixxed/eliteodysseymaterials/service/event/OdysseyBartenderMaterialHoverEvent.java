package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.Asset;

@RequiredArgsConstructor
@Getter
public class OdysseyBartenderMaterialHoverEvent implements Event {
    final Asset asset;
}
