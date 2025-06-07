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
