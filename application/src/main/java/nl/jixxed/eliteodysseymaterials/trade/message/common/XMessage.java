package nl.jixxed.eliteodysseymaterials.trade.message.common;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class XMessage {
    private String messageRef;
    private String offerId;
    private String tokenhash;
    private Info info;
    private String text;
    private Long date;
}