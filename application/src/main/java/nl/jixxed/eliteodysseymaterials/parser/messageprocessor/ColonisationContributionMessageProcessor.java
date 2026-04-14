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

import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ColonisationContribution.ColonisationContribution;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

public class ColonisationContributionMessageProcessor implements MessageProcessor<ColonisationContribution> {
    //    { "timestamp":"2025-06-06T13:33:51Z", "event":"ColonisationContribution", "MarketID":3955004418, "Contributions":[ { "Name":"$Titanium_name;", "Name_Localised":"Titanium", "Amount":784 } ] }
    @Override
    public void process(ColonisationContribution event) {
        event.getContributions().forEach(contribution -> {
            final Commodity commodity = Commodity.forName(cleanName(contribution.getName()));
            final int amount = contribution.getAmount().intValue();
            StorageService.removeCommodity(commodity, StoragePool.SHIP, amount);
        });
    }

    static String cleanName(final String name) {
        if (name.startsWith("$") && name.endsWith("_name;")) {
            return name.substring(1, name.length() - 6);
        }
        return name;
    }

    @Override
    public Class<ColonisationContribution> getMessageClass() {
        return ColonisationContribution.class;
    }
}
