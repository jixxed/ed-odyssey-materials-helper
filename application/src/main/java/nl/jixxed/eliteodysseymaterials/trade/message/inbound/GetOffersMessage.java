package nl.jixxed.eliteodysseymaterials.trade.message.inbound;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.trade.message.common.Offer;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetOffersMessage extends InboundMessage {

    private List<Offer> offers;
    private Integer page;
    private Integer ofpages;
}
