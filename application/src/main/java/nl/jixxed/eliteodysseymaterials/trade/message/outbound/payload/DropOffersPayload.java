package nl.jixxed.eliteodysseymaterials.trade.message.outbound.payload;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DropOffersPayload implements Payload {
    private final List<String> offerIds;
}
