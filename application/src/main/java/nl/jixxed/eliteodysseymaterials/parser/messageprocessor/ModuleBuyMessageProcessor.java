/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
                .filter(shipModule -> shipModule.getInternalName().equalsIgnoreCase(cleanName(event.getBuyItem())))
                .filter(Predicate.not(ShipModule::isPreEngineered))
                .findFirst()
                .map(ShipModule::Clone)
                .orElse(null);
        shipSlot.setShipModule(module);
        shipSlot.setOldShipModule(module);

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
    public Class<ModuleBuy> getMessageClass() {
        return ModuleBuy.class;
    }
}
