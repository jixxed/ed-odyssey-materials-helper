package nl.jixxed.eliteodysseymaterials.trade.message.outbound.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class XBidPushPayload implements Payload {
    private final String offerId;
}
