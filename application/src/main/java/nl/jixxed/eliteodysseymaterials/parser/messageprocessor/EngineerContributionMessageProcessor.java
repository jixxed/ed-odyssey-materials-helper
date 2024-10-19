package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.schemas.journal.EngineerContribution.EngineerContribution;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

@Slf4j
public class EngineerContributionMessageProcessor implements MessageProcessor<EngineerContribution> {

    @Override
    public void process(final EngineerContribution event) {
        event.getMaterial().ifPresent(material -> {
            try {
                final HorizonsMaterial horizonsMaterial = HorizonsMaterial.subtypeForName(material);
                if (!horizonsMaterial.isUnknown()) {
                    if (horizonsMaterial instanceof Commodity commodity) {
                        StorageService.removeCommodity(commodity, StoragePool.SHIP, event.getQuantity().intValue());
                    } else {
                        StorageService.removeMaterial(horizonsMaterial, event.getQuantity().intValue());
                    }
                }
                EventService.publish(new StorageEvent(StoragePool.SHIP));
            } catch (final IllegalArgumentException e) {
                log.error(e.getMessage());
                //NotificationService.showWarning(NotificationType.ERROR, "Unknown Material Detected", material + "\nPlease report!");
            }
        });
    }

    @Override
    public Class<EngineerContribution> getMessageClass() {
        return EngineerContribution.class;
    }
}