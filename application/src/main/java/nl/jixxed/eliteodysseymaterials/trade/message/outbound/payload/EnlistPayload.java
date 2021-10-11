package nl.jixxed.eliteodysseymaterials.trade.message.outbound.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnlistPayload implements Payload {
    private final String token;
}
