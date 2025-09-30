package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ModuleBuy.ModuleBuy;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ModuleSwap.ModuleSwap;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipLoadoutEvent;
import nl.jixxed.eliteodysseymaterials.service.ships.LoadoutMapper;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipMapper;

import java.util.function.Predicate;

@Slf4j
public class ModuleSwapMessageProcessor implements MessageProcessor<ModuleSwap> {
    @Override
    public void process(final ModuleSwap event) {
        final Ship ship = ShipMapper.toShip(ShipConfiguration.CURRENT);
        if (ship == null) {
            log.error("Ship is null, cannot process ModuleSwap event: " + event);
            return;
        }
        final Slot shipFromSlot = LoadoutMapper.getShipSlot(ship, event.getFromSlot());
        if (shipFromSlot == null) {
            log.error("Could not find ship slot for " + event.getFromSlot());
            return;
        }
        final Slot shipToSlot = LoadoutMapper.getShipSlot(ship, event.getToSlot());
        if (shipToSlot == null) {
            log.error("Could not find ship slot for " + event.getToSlot());
            return;
        }

        ShipModule shipModuleFrom = shipFromSlot.getShipModule();
        ShipModule shipModuleTo = shipToSlot.getShipModule();
        shipToSlot.setShipModule(shipModuleFrom);
        shipToSlot.setOldShipModule(shipModuleFrom);
        shipFromSlot.setShipModule(shipModuleTo);
        shipFromSlot.setOldShipModule(shipModuleTo);
        ShipMapper.toShipConfiguration(ship, ShipConfiguration.CURRENT, ShipConfiguration.CURRENT.getName());
        EventService.publish(new ShipLoadoutEvent());
    }

    static String cleanName(final String name) {
        if (name.startsWith("$") && name.endsWith("_name;")) {
            return name.substring(1, name.length() - 6);
        }
        return name;
    }

    @Override
    public Class<ModuleSwap> getMessageClass() {
        return ModuleSwap.class;
    }
}
