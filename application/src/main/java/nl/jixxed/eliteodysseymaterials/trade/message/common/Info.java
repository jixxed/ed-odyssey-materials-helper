package nl.jixxed.eliteodysseymaterials.trade.message.common;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class Info {
    private String nickname;
    @Builder.Default
    private String location = "Sol";
    @Builder.Default
    private Double x = 0.0;
    @Builder.Default
    private Double y = 0.0;
    @Builder.Default
    private Double z = 0.0;
}
