package nl.jixxed.eliteodysseymaterials.trade.message.outbound;

import lombok.Builder;

@lombok.Data
@Builder
public class OutboundMessage {
    private final String action;
    private final Data data;
}
