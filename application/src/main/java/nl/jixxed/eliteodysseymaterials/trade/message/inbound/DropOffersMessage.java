package nl.jixxed.eliteodysseymaterials.trade.message.inbound;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DropOffersMessage extends InboundMessage {
    private List<String> offerIds;
}
