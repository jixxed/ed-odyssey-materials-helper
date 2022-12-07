package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.journalevents.EngineerCraft.EngineerCraft;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

@Slf4j
public class EngineerCraftMessageProcessor implements MessageProcessor<EngineerCraft> {
    @Override
    public void process(final EngineerCraft event) {
        event.getIngredients().forEach(ingredient -> {
            try {
                final HorizonsMaterial horizonsMaterial = HorizonsMaterial.subtypeForName(ingredient.getName());
                if (horizonsMaterial instanceof Commodity commodity && !horizonsMaterial.isUnknown()) {
                    StorageService.removeCommodity(commodity, StoragePool.SHIP, ingredient.getCount().intValue());
                }
                if (!horizonsMaterial.isUnknown()) {
                    StorageService.removeMaterial(horizonsMaterial, ingredient.getCount().intValue());
                }
            } catch (final IllegalArgumentException e) {
                log.error(e.getMessage());
            }
        });
        EventService.publish(new StorageEvent(StoragePool.SHIP));
    }

    @Override
    public Class<EngineerCraft> getMessageClass() {
        return EngineerCraft.class;
    }
}