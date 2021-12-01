package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.TradeShow;
import nl.jixxed.eliteodysseymaterials.enums.TradeSort;

@AllArgsConstructor
@Getter
@Setter
public class TradeSearch {
    private String query;
    private TradeSort tradeSort;
    private TradeShow tradeShow;

}
