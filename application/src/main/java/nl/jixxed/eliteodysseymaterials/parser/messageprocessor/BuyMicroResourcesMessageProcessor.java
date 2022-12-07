package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.journalevents.BuyMicroResources.BuyMicroResources;

public class BuyMicroResourcesMessageProcessor implements MessageProcessor<BuyMicroResources> {
    @Override
    public void process(final BuyMicroResources event) {

    }

    @Override
    public Class<BuyMicroResources> getMessageClass() {
        return null;
    }
}
