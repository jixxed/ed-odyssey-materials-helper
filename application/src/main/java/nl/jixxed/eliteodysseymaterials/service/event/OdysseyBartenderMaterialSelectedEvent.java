package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.bartender.OdysseyBartenderMaterial;

@Getter
@AllArgsConstructor
public class OdysseyBartenderMaterialSelectedEvent implements Event {
    private final OdysseyBartenderMaterial odysseyBartenderMaterial;
}
