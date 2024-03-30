package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Loadout.Loadout;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipLoadoutEvent;
import nl.jixxed.eliteodysseymaterials.service.ships.LoadoutMapper;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipMapper;

public class LoadoutMessageProcessor implements MessageProcessor<Loadout> {
    @Override
    public void process(Loadout event) {
        try {
            final Ship ship = LoadoutMapper.toShip(event);
            ShipMapper.toShipConfiguration(ship, ShipConfiguration.CURRENT, ShipConfiguration.CURRENT.getName());
            EventService.publish(new ShipLoadoutEvent());
        } catch (IllegalArgumentException e) {
            ShipConfiguration.resetCurrent();
        }
    }

    @Override
    public Class<Loadout> getMessageClass() {
        return Loadout.class;
    }
}
