package nl.jixxed.eliteodysseymaterials.trade.message.common;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class Item {
    @Builder.Default
    private Integer tradeid = 0;
    private String sid;
    @Builder.Default
    private Integer sstock = 0;
    private Integer supply;
    @Builder.Default
    private String sname = "";
    private String did;
    @Builder.Default
    private String dname = "";
    @Builder.Default
    private Integer dstock = 0;
    private Integer demand;
}
