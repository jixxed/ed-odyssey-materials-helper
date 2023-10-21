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

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EDDNApproachSettlementMapper extends EDDNMapper {

    public static Message mapToEDDN(final ApproachSettlement approachSettlement,final Expansion expansion) {
        return new Message.MessageBuilder()
                .withTimestamp(approachSettlement.getTimestamp())
                .withEvent(approachSettlement.getEvent())
                .withBodyID(approachSettlement.getBodyID())
                .withBodyName(approachSettlement.getBodyName())
                .withStarPos(LocationService.getCurrentStarPos(approachSettlement.getSystemAddress()))
                .withStarSystem(LocationService.getCurrentStarSystemName(approachSettlement.getSystemAddress()))
                .withSystemAddress(approachSettlement.getSystemAddress())
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withLatitude(approachSettlement.getLatitude().orElse(null))
                .withLongitude(approachSettlement.getLongitude().orElse(null))
                .withMarketID(approachSettlement.getMarketID().orElse(null))
                .withName(approachSettlement.getName())
                .withStationEconomies(mapToNullIfEmptyList(approachSettlement.getStationEconomies())
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
                .withStationServices(mapToNullIfEmptyList(approachSettlement.getStationServices()).orElse(null))
                .withStationAllegiance(approachSettlement.getStationAllegiance().orElse(null))
                .build();
    }

}
