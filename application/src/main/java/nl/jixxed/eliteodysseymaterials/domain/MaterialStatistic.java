/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
    private List<SettlementStatistic> bestavg = new ArrayList<>();
    private List<SettlementStatistic> mostcollected = new ArrayList<>();
    private List<SettlementStatistic> coloniabestrun = new ArrayList<>();
    private List<SettlementStatistic> coloniabestavg = new ArrayList<>();
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

    public List<SettlementStatistic> getBestavg() {
        final Double distanceToSol = LocationService.calculateDistance(LocationService.getCurrentStarSystem(), StarSystem.SOL);
        final Double distanceToColonia = LocationService.calculateDistance(LocationService.getCurrentStarSystem(), StarSystem.COLONIA);
        if (distanceToSol < distanceToColonia) {
            return this.bestavg;
        } else {
            return this.coloniabestavg;
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
