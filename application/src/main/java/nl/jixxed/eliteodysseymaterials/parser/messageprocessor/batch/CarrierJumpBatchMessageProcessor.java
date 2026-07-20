/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.parser.messageprocessor.batch;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.persistence.common.model.StarSystemModel;
import nl.jixxed.eliteodysseymaterials.schemas.journal.CarrierJump.CarrierJump;
import nl.jixxed.eliteodysseymaterials.service.DatabaseService;

import java.util.List;

@Slf4j
public class CarrierJumpBatchMessageProcessor implements BatchMessageProcessor<CarrierJump> {

    @Override
    public void process(final List<CarrierJump> events) {
        events.forEach(event -> {
            if (!event.getStarSystem().isBlank()) {
                StarSystemModel starSystemModel = new StarSystemModel(
                        event.getSystemAddress(),
                        event.getStarSystem(),
                        event.getStarPos().get(0).doubleValue(),
                        event.getStarPos().get(1).doubleValue(),
                        event.getStarPos().get(2).doubleValue()
                );
                DatabaseService.getCommonDatabase().insert(starSystemModel, DatabaseService.onConflictUpdate("address"));
            }
        });
    }

    @Override
    public Class<CarrierJump> getMessageClass() {
        return CarrierJump.class;
    }
}
