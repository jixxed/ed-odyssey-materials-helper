package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.parser.AssetParser;
import nl.jixxed.eliteodysseymaterials.parser.DataParser;
import nl.jixxed.eliteodysseymaterials.parser.GoodParser;
import nl.jixxed.eliteodysseymaterials.parser.mapping.MaterialMapping;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ShipLocker.ShipLocker;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipLockerEvent;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class ShipLockerMessageProcessor implements MessageProcessor<ShipLocker> {
    private static final AssetParser ASSET_PARSER = new AssetParser();
    private static final DataParser DATA_PARSER = new DataParser();
    private static final GoodParser GOOD_PARSER = new GoodParser();
    @Override
    public void process(final ShipLocker event) {

        if (event.getComponents().isEmpty() || event.getData().isEmpty() || event.getItems().isEmpty()) {
            EventService.publish(new ShipLockerEvent(event.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))));
            return;
        }
        StorageService.resetShipLockerCounts();

        ASSET_PARSER.parse(event.getComponents().get().stream().map(MaterialMapping::map).collect(Collectors.toList()), StoragePool.SHIPLOCKER);
        GOOD_PARSER.parse(event.getItems().get().stream().map(MaterialMapping::map).collect(Collectors.toList()), StoragePool.SHIPLOCKER);
        DATA_PARSER.parse(event.getData().get().stream().map(MaterialMapping::map).collect(Collectors.toList()), StoragePool.SHIPLOCKER);
        EventService.publish(new StorageEvent(StoragePool.SHIPLOCKER));
    }

    @Override
    public Class<ShipLocker> getMessageClass() {
        return ShipLocker.class;
    }
}
