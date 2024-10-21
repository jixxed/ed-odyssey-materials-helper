package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.schemas.journal.DeliverPowerMicroResources.DeliverPowerMicroResources;

@Slf4j
public class DeliverPowerMicroResourcesMessageProcessor implements MessageProcessor<DeliverPowerMicroResources> {

    @Override
    public void process(final DeliverPowerMicroResources event) {
        event.getMicroResources().forEach(microResource -> {
            final String name = microResource.getName();
            final String category = microResource.getCategory();
            final int count = microResource.getCount().intValue();
            log.info("Delivered {} {} ({})", count, name, category);
        });
    }

    @Override
    public Class<DeliverPowerMicroResources> getMessageClass() {
        return DeliverPowerMicroResources.class;
    }
}
