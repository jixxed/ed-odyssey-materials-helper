package nl.jixxed.eliteodysseymaterials.trade.message.inbound;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class Trace {
    private String token;
    private String connectionId;
    private List<String> offers;
}
