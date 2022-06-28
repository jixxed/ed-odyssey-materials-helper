package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.service.LocationService;

import java.util.ArrayList;
import java.util.List;

@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialStatistic {
    @Getter
    private List<EconomyStatistic> economies = new ArrayList<>();
    private List<SettlementStatistic> bestrun = new ArrayList<>();
    private List<SettlementStatistic> mostcollected = new ArrayList<>();
    private List<SettlementStatistic> coloniabestrun = new ArrayList<>();
    private List<SettlementStatistic> coloniamostcollected = new ArrayList<>();

    public List<SettlementStatistic> getBestrun() {
        final Double distanceToSol = LocationService.calculateDistance(LocationService.getCurrentStarSystem(), StarSystem.SOL);
        final Double distanceToColonia = LocationService.calculateDistance(LocationService.getCurrentStarSystem(), StarSystem.COLONIA);
        if (distanceToSol < distanceToColonia) {
            return this.bestrun;
        } else {
            return this.coloniabestrun;
        }
    }

    public List<SettlementStatistic> getMostcollected() {
        final Double distanceToSol = LocationService.calculateDistance(LocationService.getCurrentStarSystem(), StarSystem.SOL);
        final Double distanceToColonia = LocationService.calculateDistance(LocationService.getCurrentStarSystem(), StarSystem.COLONIA);
        if (distanceToSol < distanceToColonia) {
            return this.mostcollected;
        } else {
            return this.coloniamostcollected;
        }
    }
}
