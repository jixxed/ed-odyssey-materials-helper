package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ModuleBuy.ModuleBuy;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipLoadoutEvent;
import nl.jixxed.eliteodysseymaterials.service.ships.LoadoutMapper;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipMapper;

import java.util.function.Predicate;

@Slf4j
public class ModuleBuyMessageProcessor implements MessageProcessor<ModuleBuy> {
    @Override
    public void process(final ModuleBuy event) {
        final Ship ship = ShipMapper.toShip(ShipConfiguration.CURRENT);
        if (ship == null) {
            log.error("Ship is null, cannot process ModuleBuy event: " + event);
            return;
        }
        final Slot shipSlot = LoadoutMapper.getShipSlot(ship, event.getSlot());
        if (shipSlot == null) {
            log.error("Could not find ship slot for " + event.getSlot());
            return;
        }
        final ShipModule module = ShipModule.getModules(shipSlot.getSlotType()).stream()
                .filter(shipModule -> shipModule.getInternalName().equalsIgnoreCase(event.getBuyItem()))
                .filter(Predicate.not(ShipModule::isPreEngineered))
                .findFirst()
                .orElse(null);
        shipSlot.setShipModule(module);
        shipSlot.setOldShipModule(module);

        ShipMapper.toShipConfiguration(ship, ShipConfiguration.CURRENT, ShipConfiguration.CURRENT.getName());
        EventService.publish(new ShipLoadoutEvent());
    }

    @Override
    public Class<ModuleBuy> getMessageClass() {
        return ModuleBuy.class;
    }
}
