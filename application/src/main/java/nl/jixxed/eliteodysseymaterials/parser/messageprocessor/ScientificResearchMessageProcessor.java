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
import nl.jixxed.eliteodysseymaterials.parser.EncodedScientificResearchParser;
import nl.jixxed.eliteodysseymaterials.parser.ManufacturedScientificResearchParser;
import nl.jixxed.eliteodysseymaterials.parser.RawScientificResearchParser;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ScientificResearch.ScientificResearch;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

public class ScientificResearchMessageProcessor implements MessageProcessor<ScientificResearch> {
    private static final RawScientificResearchParser RAW_PARSER = new RawScientificResearchParser();
    private static final EncodedScientificResearchParser ENCODED_PARSER = new EncodedScientificResearchParser();
    private static final ManufacturedScientificResearchParser MANUFACTURED_PARSER = new ManufacturedScientificResearchParser();

    @Override
    public void process(final ScientificResearch event) {
        final String category = event.getCategory();
        switch (category.toLowerCase()) {
            case "raw" ->
                    RAW_PARSER.parse(event);
            case "encoded" ->
                    ENCODED_PARSER.parse(event);
            case "manufactured" ->
                    MANUFACTURED_PARSER.parse(event);
        }
        EventService.publish(new StorageEvent(StoragePool.SHIP));
    }

    @Override
    public Class<ScientificResearch> getMessageClass() {
        return ScientificResearch.class;
    }
}