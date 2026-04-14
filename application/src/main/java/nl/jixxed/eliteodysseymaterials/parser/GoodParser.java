/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.parser;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Good;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.parser.mapping.MaterialMapping;
import nl.jixxed.eliteodysseymaterials.service.ReportService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.List;

@Slf4j
public class GoodParser implements Parser<MaterialMapping> {
    @Override
    public void parse(final List<MaterialMapping> items, final StoragePool storagePool) {
        items.forEach(item ->
        {
            final String name = item.getName();
            final Good good = Good.forName(name);
            final int amount = item.getCount().intValue();
            if (good.isUnknown()) {
                log.warn("Unknown Good detected: " + item);
                ReportService.reportMaterial(item);
            } else {
                StorageService.addMaterial(good, storagePool, amount);
            }
        });
    }
}
