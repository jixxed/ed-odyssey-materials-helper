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
