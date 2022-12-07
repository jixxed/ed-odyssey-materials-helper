package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.journalevents.MissionCompleted.MissionCompleted;
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
                    if (horizonsMaterial instanceof Commodity commodity && !horizonsMaterial.isUnknown()) {
                        StorageService.addCommodity(commodity, StoragePool.SHIP, materialsReward.getCount().intValue());
                    }
                    if (!horizonsMaterial.isUnknown()) {
                        StorageService.addMaterial(horizonsMaterial, materialsReward.getCount().intValue());
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