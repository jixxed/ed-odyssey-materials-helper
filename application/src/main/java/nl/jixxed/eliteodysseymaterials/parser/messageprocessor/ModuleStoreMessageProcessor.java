package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ModuleStore.ModuleStore;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipLoadoutEvent;
import nl.jixxed.eliteodysseymaterials.service.ships.LoadoutMapper;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipMapper;

@Slf4j
public class ModuleStoreMessageProcessor implements MessageProcessor<ModuleStore> {
    @Override
    public void process(final ModuleStore event) {

        final Ship ship = ShipMapper.toShip(ShipConfiguration.CURRENT);
        if (ship == null) {
            log.error("Ship is null, cannot process ModuleStore event: " + event);
            return;
        }
        final Slot shipSlot = LoadoutMapper.getShipSlot(ship, event.getSlot());
        if (shipSlot == null) {
            log.error("Could not find ship slot for " + event.getSlot());
            return;
        }
        shipSlot.setShipModule(null);
        shipSlot.setOldShipModule(null);

        ShipMapper.toShipConfiguration(ship, ShipConfiguration.CURRENT, ShipConfiguration.CURRENT.getName());
        EventService.publish(new ShipLoadoutEvent());
    }

    @Override
    public Class<ModuleStore> getMessageClass() {
        return ModuleStore.class;
    }
}
