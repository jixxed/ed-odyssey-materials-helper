package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ModuleRetrieve.ModuleRetrieve;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipLoadoutEvent;
import nl.jixxed.eliteodysseymaterials.service.ships.LoadoutMapper;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipMapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Predicate;

@Slf4j
public class ModuleRetrieveMessageProcessor implements MessageProcessor<ModuleRetrieve> {
    @Override
    public void process(final ModuleRetrieve event) {

        final Ship ship = ShipMapper.toShip(ShipConfiguration.CURRENT);
        if (ship == null) {
            log.error("Ship is null, cannot process ModuleRetrieve event: " + event);
            return;
        }
        final Slot shipSlot = LoadoutMapper.getShipSlot(ship, event.getSlot());
        if (shipSlot == null) {
            log.error("Could not find ship slot for " + event.getSlot());
            return;
        }
        final ShipModule module = ShipModule.getModules(shipSlot.getSlotType()).stream()
                .filter(shipModule -> shipModule.getInternalName().equalsIgnoreCase(event.getRetrievedItem()))
                .filter(Predicate.not(ShipModule::isPreEngineered))
                .findFirst()
                .orElse(null);
        if (module == null) {
            log.error("Could not find module for " + event.getRetrievedItem() + " in slot " + event.getSlot());
            return;
        }
        //this module is technically not engineered fully because information is missing, but will be updated once the outfitting screen is exited
        event.getEngineerModifications().ifPresent(modifications -> module.applyModification(HorizonsBlueprintType.forInternalName(modifications), HorizonsBlueprintGrade.forDigit(event.getLevel().orElse(BigInteger.ZERO)), event.getQuality().orElse(BigDecimal.ZERO)));
        shipSlot.setShipModule(module);
        shipSlot.setOldShipModule(module);

        ShipMapper.toShipConfiguration(ship, ShipConfiguration.CURRENT, ShipConfiguration.CURRENT.getName());
        EventService.publish(new ShipLoadoutEvent());

    }

    @Override
    public Class<ModuleRetrieve> getMessageClass() {
        return ModuleRetrieve.class;
    }
}
