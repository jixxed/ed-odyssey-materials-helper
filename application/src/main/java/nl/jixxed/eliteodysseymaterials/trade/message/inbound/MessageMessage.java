package nl.jixxed.eliteodysseymaterials.trade.message.inbound;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.trade.message.common.Message;

@Data
@NoArgsConstructor
public class MessageMessage extends InboundMessage {
    private Message message;
}
