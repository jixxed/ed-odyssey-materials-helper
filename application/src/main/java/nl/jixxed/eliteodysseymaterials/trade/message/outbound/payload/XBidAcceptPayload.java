package nl.jixxed.eliteodysseymaterials.trade.message.outbound.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class XBidAcceptPayload implements Payload {
    private final String offerId;
    private String tokenhash;
    private Boolean accept;
}
