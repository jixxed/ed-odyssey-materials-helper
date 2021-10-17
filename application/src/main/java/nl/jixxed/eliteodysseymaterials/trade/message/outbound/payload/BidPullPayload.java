package nl.jixxed.eliteodysseymaterials.trade.message.outbound.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BidPullPayload implements Payload {
    private final String token;
    private final String offerId;
    private final String myOfferId;
}
