package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MaterialStatistic {
    private List<EconomyStatistic> economies = new ArrayList<>();
    private List<SettlementStatistic> bestrun = new ArrayList<>();
    private List<SettlementStatistic> mostcollected = new ArrayList<>();
}
