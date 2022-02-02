package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettlementStatistic {
    private String amount;
    private String settlement;
    private String body;
    private String system;
    private Double x;
    private Double y;
    private Double z;
    private String rank;
}
