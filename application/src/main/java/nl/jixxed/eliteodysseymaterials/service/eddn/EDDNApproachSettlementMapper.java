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
import nl.jixxed.eliteodysseymaterials.schemas.eddn.approachsettlement.Message;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.approachsettlement.StationEconomy;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.approachsettlement.SystemFaction;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ApproachSettlement.ApproachSettlement;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ApproachSettlement.StationFaction;
import nl.jixxed.eliteodysseymaterials.service.LocationService;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EDDNApproachSettlementMapper extends EDDNMapper {

    @SuppressWarnings("unchecked")
    public static Message mapToEDDN(final ApproachSettlement approachSettlement, final Expansion expansion) {
        return new Message.MessageBuilder()
                .withTimestamp(approachSettlement.getTimestamp())
                .withEvent(approachSettlement.getEvent())
                .withBodyID(approachSettlement.getBodyID())
                .withBodyName(approachSettlement.getBodyName())
                .withStarPos(Optional.ofNullable(LocationService.getCurrentStarPos(approachSettlement.getSystemAddress()))
                        .map(coordinates -> coordinates.stream()
                                .map(BigDecimal::new)
                                .collect(Collectors.toList()))
                        .orElse(null))
                .withStarSystem(LocationService.getCurrentStarSystemName(approachSettlement.getSystemAddress()))
                .withSystemAddress(approachSettlement.getSystemAddress())
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withLatitude(approachSettlement.getLatitude().orElse(null))
                .withLongitude(approachSettlement.getLongitude().orElse(null))
                .withMarketID(approachSettlement.getMarketID().orElse(null))
                .withName(approachSettlement.getName())
                .withStationEconomies(mapToOptionalEmptyIfEmptyList(approachSettlement.getStationEconomies())
                        .map(stationEconomies -> stationEconomies.stream()
                                .map(stationEconomy -> new StationEconomy.StationEconomyBuilder()
                                        .withName(stationEconomy.getName())
                                        .withProportion(stationEconomy.getProportion())
                                        .build())
                                .toList())
                        .orElse(null))
                .withStationEconomy(approachSettlement.getStationEconomy().orElse(null))
                .withStationFaction(new SystemFaction.SystemFactionBuilder()
                        .withName(approachSettlement.getStationFaction().map(StationFaction::getName).orElse(null))
                        .withFactionState(approachSettlement.getStationFaction().map(faction -> faction.getFactionState().orElse(null)).orElse(null))
                        .build())
                .withStationGovernment(approachSettlement.getStationGovernment().orElse(null))
                .withStationServices(mapToOptionalEmptyIfEmptyList(approachSettlement.getStationServices()).orElse(null))
                .withStationAllegiance(approachSettlement.getStationAllegiance().orElse(null))
                .build();
    }

}
