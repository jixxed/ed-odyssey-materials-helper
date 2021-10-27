package nl.jixxed.eliteodysseymaterials.trade.message.outbound.payload;

import lombok.Builder;
import lombok.Data;
import nl.jixxed.eliteodysseymaterials.trade.message.common.XMessage;

@Data
@Builder
public class MessagePayload implements Payload {
    private final String token;
    private final XMessage message;
}
