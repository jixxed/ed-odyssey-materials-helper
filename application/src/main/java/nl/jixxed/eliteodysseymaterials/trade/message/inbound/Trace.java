package nl.jixxed.eliteodysseymaterials.trade.message.inbound;

import lombok.Data;

@Data
public class Trace {
    private String token;
    private String connectionId;
}
