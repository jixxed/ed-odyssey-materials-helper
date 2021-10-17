package nl.jixxed.eliteodysseymaterials.trade.message.common;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class Message {
    private String offerId;
    private String myOfferId;
    private String text;
    private Boolean inbound;
    private Long date;
}
