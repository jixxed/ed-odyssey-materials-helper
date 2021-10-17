package nl.jixxed.eliteodysseymaterials.trade.message.inbound;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OnlineOffersMessage extends InboundMessage {
    private List<String> offerIds;
    private String connectionId;
}
