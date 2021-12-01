package nl.jixxed.eliteodysseymaterials.trade.message.inbound;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.trade.message.common.Offer;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PublishOfferMessage extends InboundMessage {
    private Offer offer;
}
