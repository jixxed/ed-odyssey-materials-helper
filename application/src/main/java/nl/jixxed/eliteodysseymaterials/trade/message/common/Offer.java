package nl.jixxed.eliteodysseymaterials.trade.message.common;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Offer {
    @EqualsAndHashCode.Include
    @Builder.Default
    private String offerId = "";
    private Info info;
    @Builder.Default
    private Long created = 0L;
    @Builder.Default
    private Long expired = 0L;
    @Builder.Default
    private String token = "";
    @Builder.Default
    private String connectionId = "";
    @Builder.Default
    private List<String> bids = new ArrayList<>();
    @Builder.Default
    private List<Item> items = new ArrayList<>();

}
