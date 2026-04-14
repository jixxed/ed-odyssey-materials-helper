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
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.schemas.journal.MissionCompleted.MissionCompleted;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

@Slf4j
public class MissionCompletedMessageProcessor implements MessageProcessor<MissionCompleted> {
    @Override
    public void process(final MissionCompleted event) {
        event.getMaterialsReward().ifPresent(materialsRewards -> {
            materialsRewards.forEach(materialsReward -> {
                final String name = materialsReward.getName();
                try {
                    final HorizonsMaterial horizonsMaterial = HorizonsMaterial.subtypeForName(name);
                    if(!horizonsMaterial.isUnknown()) {
                        if (horizonsMaterial instanceof Commodity commodity) {
                            StorageService.addCommodity(commodity, StoragePool.SHIP, materialsReward.getCount().intValue());
                        } else {
                            StorageService.addMaterial(horizonsMaterial, materialsReward.getCount().intValue());
                        }
                    }
                    EventService.publish(new StorageEvent(StoragePool.SHIP));
                } catch (final IllegalArgumentException ex) {
                    //not a horizons material reward
                    log.warn("Material was not a Horizons material: " + name);
                }
                //don't process odyssey materials because shiplocker event precedes this event
            });
        });
    }
    @Override
    public Class<MissionCompleted> getMessageClass() {
        return MissionCompleted.class;
    }

}