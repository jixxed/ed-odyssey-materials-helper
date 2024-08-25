package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.parser.AssetParser;
import nl.jixxed.eliteodysseymaterials.parser.DataParser;
import nl.jixxed.eliteodysseymaterials.parser.GoodParser;
import nl.jixxed.eliteodysseymaterials.parser.mapping.MaterialMapping;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Backpack.Backpack;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.BackpackEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class BackpackMessageProcessor implements MessageProcessor<Backpack> {
    private static final AssetParser ASSET_PARSER = new AssetParser();
    private static final DataParser DATA_PARSER = new DataParser();
    private static final GoodParser GOOD_PARSER = new GoodParser();

    @Override
    public void process(final Backpack event) {

        if (event.getComponents().isEmpty() || event.getData().isEmpty() || event.getItems().isEmpty()) {
            final String timestamp = event.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
            EventService.publish(new BackpackEvent(timestamp));
            return;
        }
        StorageService.resetBackPackCounts();

        ASSET_PARSER.parse(event.getComponents().get().stream().map(MaterialMapping::map).collect(Collectors.toList()), StoragePool.BACKPACK);
        GOOD_PARSER.parse(event.getItems().get().stream().map(MaterialMapping::map).collect(Collectors.toList()), StoragePool.BACKPACK);
        DATA_PARSER.parse(event.getData().get().stream().map(MaterialMapping::map).collect(Collectors.toList()), StoragePool.BACKPACK);
        EventService.publish(new StorageEvent(StoragePool.BACKPACK));
    }

    @Override
    public Class<Backpack> getMessageClass() {
        return Backpack.class;
    }
}
