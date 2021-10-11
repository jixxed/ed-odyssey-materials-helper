package nl.jixxed.eliteodysseymaterials.trade.message.outbound;

import lombok.Builder;
import nl.jixxed.eliteodysseymaterials.trade.message.outbound.payload.Payload;

@lombok.Data
@Builder
public class Data {
    private final String method;
    private final Payload payload;
}
