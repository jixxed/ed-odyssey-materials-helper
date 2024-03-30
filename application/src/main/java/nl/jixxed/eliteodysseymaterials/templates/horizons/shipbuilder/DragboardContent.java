package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;

import java.io.Serializable;

public record DragboardContent(ShipModule shipModule, ShipModule oldShipModule)  implements Serializable {

}
