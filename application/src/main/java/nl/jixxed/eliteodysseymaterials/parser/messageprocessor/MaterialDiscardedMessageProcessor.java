package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.schemas.journal.MaterialDiscarded.MaterialDiscarded;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;
@Slf4j
public class MaterialDiscardedMessageProcessor implements MessageProcessor<MaterialDiscarded> {
    @Override
    public void process(final MaterialDiscarded event) {
        try {
            final HorizonsMaterial horizonsMaterial = HorizonsMaterial.subtypeForName(event.getName());
            if (!horizonsMaterial.isUnknown()) {
                if (horizonsMaterial instanceof Commodity commodity && !horizonsMaterial.isUnknown()) {
                    StorageService.removeCommodity(commodity, StoragePool.SHIP, event.getCount().intValue());
                }
                if (!horizonsMaterial.isUnknown()) {
                    StorageService.removeMaterial(horizonsMaterial, event.getCount().intValue());
                }
                EventService.publish(new StorageEvent(StoragePool.SHIP));
            }
        } catch (final IllegalArgumentException e) {
            log.error(e.getMessage());
        }
    }
    @Override
    public Class<MaterialDiscarded> getMessageClass() {
        return MaterialDiscarded.class;
    }
}