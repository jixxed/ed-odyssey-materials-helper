package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.journalevents.Materials.Materials;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

public class MaterialsMessageProcessor implements MessageProcessor<Materials> {
    @Override
    public void process(final Materials event) {
        StorageService.resetHorizonsMaterialCounts();
        event.getRaw().forEach(raw -> {
            final HorizonsMaterial horizonsMaterial = Raw.forName(raw.getName());
            StorageService.addMaterial(horizonsMaterial, raw.getCount().intValue());
        });
        event.getManufactured().forEach(manufactured -> {
            final HorizonsMaterial horizonsMaterial = Manufactured.forName(manufactured.getName());
            StorageService.addMaterial(horizonsMaterial, manufactured.getCount().intValue());
        });
        event.getEncoded().forEach(encoded -> {
            final HorizonsMaterial horizonsMaterial = Encoded.forName(encoded.getName());
            StorageService.addMaterial(horizonsMaterial, encoded.getCount().intValue());
        });
        EventService.publish(new StorageEvent(StoragePool.SHIP));

    }
    @Override
    public Class<Materials> getMessageClass() {
        return Materials.class;
    }
}