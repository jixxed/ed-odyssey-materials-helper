/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service.eddn;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.approachsettlement.StationEconomy;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.approachsettlement.SystemFaction;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.docked.LandingPads;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Docked.Docked;
import nl.jixxed.eliteodysseymaterials.service.LocationService;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EDDNDockedMapper extends EDDNMapper {

    @SuppressWarnings("unchecked")
    public static nl.jixxed.eliteodysseymaterials.schemas.eddn.docked.Message mapToEDDN(final Docked docked, final Expansion expansion) {
        return new nl.jixxed.eliteodysseymaterials.schemas.eddn.docked.Message.MessageBuilder()
                .withTimestamp(docked.getTimestamp())
                .withEvent(docked.getEvent())
                .withStarPos(docked.getSystemAddress().map(LocationService::getCurrentStarPos)
                        .map(coordinates -> coordinates.stream()
                                .map(BigDecimal::new)
                                .collect(Collectors.toList()))
                        .orElse(null))
                .withStarSystem(docked.getStarSystem().orElse(null))
                .withSystemAddress(docked.getSystemAddress().orElse(null))
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withDistFromStarLS(docked.getDistFromStarLS())
                .withLandingPads(docked.getLandingPads().map(landingPads -> new LandingPads.LandingPadsBuilder()
                        .withSmall(landingPads.getSmall())
                        .withMedium(landingPads.getMedium())
                        .withLarge(landingPads.getLarge())
                        .build()).orElse(null))
                .withMarketID(docked.getMarketID().orElse(null))
                .withStationAllegiance(docked.getStationAllegiance().orElse(null))
                .withStationEconomies(mapToOptionalEmptyIfEmptyList(docked.getStationEconomies()).map(stationEconomies -> stationEconomies.stream()
                                .map(stationEconomy -> new StationEconomy.StationEconomyBuilder()
                                        .withName(stationEconomy.getName())
                                        .withProportion(stationEconomy.getProportion())
                                        .build()).toList())
                        .orElse(null))
                .withStationEconomy(docked.getStationEconomy().orElse(null))
                .withStationFaction(docked.getStationFaction()
                        .map(stationFaction -> new SystemFaction.SystemFactionBuilder()
                                .withName(stationFaction.getName())
                                .withFactionState(stationFaction.getFactionState().orElse(null))
                                .build()).orElse(null))
                .withStationGovernment(docked.getStationGovernment().orElse(null))
                .withStationName(docked.getStationName())
                .withStationServices(docked.getStationServices().orElse(null))
                .withStationState(docked.getStationState().orElse(null))
                .withStationType(docked.getStationType())
                .build();
    }
}
