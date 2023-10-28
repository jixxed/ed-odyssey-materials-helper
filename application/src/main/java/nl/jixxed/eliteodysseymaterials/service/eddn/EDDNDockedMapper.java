package nl.jixxed.eliteodysseymaterials.service.eddn;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.approachsettlement.StationEconomy;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.approachsettlement.SystemFaction;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.docked.LandingPads;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Docked.Docked;
import nl.jixxed.eliteodysseymaterials.service.LocationService;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EDDNDockedMapper extends EDDNMapper {

    public static nl.jixxed.eliteodysseymaterials.schemas.eddn.docked.Message mapToEDDN(final Docked docked, final Expansion expansion) {
        return new nl.jixxed.eliteodysseymaterials.schemas.eddn.docked.Message.MessageBuilder()
                .withTimestamp(docked.getTimestamp())
                .withEvent(docked.getEvent())
                .withStarPos(LocationService.getCurrentStarPos(docked.getSystemAddress()))
                .withStarSystem(docked.getStarSystem())
                .withSystemAddress(docked.getSystemAddress())
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withDistFromStarLS(docked.getDistFromStarLS())
                .withLandingPads(docked.getLandingPads().map(landingPads -> new LandingPads.LandingPadsBuilder()
                        .withSmall(landingPads.getSmall())
                        .withMedium(landingPads.getMedium())
                        .withLarge(landingPads.getLarge())
                        .build()).orElse(null))
                .withMarketID(docked.getMarketID())
                .withStationAllegiance(docked.getStationAllegiance().orElse(null))
                .withStationEconomies(mapToNullIfEmptyList(docked.getStationEconomies()).map(stationEconomies -> stationEconomies.stream()
                        .map(stationEconomy -> new StationEconomy.StationEconomyBuilder()
                                .withName(stationEconomy.getName())
                                .withProportion(stationEconomy.getProportion())
                                .build()).toList())
                        .orElse(null))
                .withStationEconomy(docked.getStationEconomy().orElse(null))
                .withStationFaction(new SystemFaction.SystemFactionBuilder()
                        .withName(docked.getStationFaction().getName())
                        .withFactionState(docked.getStationFaction().getFactionState().orElse(null))
                        .build())
                .withStationGovernment(docked.getStationGovernment())
                .withStationName(docked.getStationName())
                .withStationServices(docked.getStationServices())
                .withStationState(docked.getStationState().orElse(null))
                .withStationType(docked.getStationType())
                .build();
    }
}
