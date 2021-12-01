package nl.jixxed.eliteodysseymaterials.trade.message.inbound;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.trade.message.common.XMessage;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class XMessageMessage extends InboundMessage {
    private XMessage message;
}
