package nl.jixxed.eliteodysseymaterials.trade.message.common;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class XBid {
    private String token;
    private String tokenhash;
    private Long timestamp;
    private Boolean accepted;
}
