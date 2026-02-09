package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.schemas.journal.CarrierJump.CarrierJump;
import nl.jixxed.eliteodysseymaterials.service.CarrierService;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.UserPreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.CarrierJumpJournalEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

import java.util.Optional;

public class CarrierJumpMessageProcessor implements MessageProcessor<CarrierJump> {
    @Override
    public void process(final CarrierJump event) {
        final String body = event.getBody();
        final String starSystem = event.getStarSystem();
        final Economy economy = Economy.forKey(event.getSystemEconomy());
        final Economy secondEconomy = Economy.forKey(event.getSystemSecondEconomy());
        final Government government = Government.forId(event.getSystemGovernment());
        final Security security = Security.forId(event.getSystemSecurity());
        final Allegiance allegiance = Allegiance.forKey(event.getSystemAllegiance());
        final State factionState = event.getSystemFaction().map(systemFaction -> State.forName(systemFaction.getFactionState().orElse(""))).orElse(State.NONE);

        if (!starSystem.isBlank()) {
            final double x = event.getStarPos().get(0).doubleValue();
            final double y = event.getStarPos().get(1).doubleValue();
            final double z = event.getStarPos().get(2).doubleValue();
            EventService.publish(new CarrierJumpJournalEvent(event, new StarSystem(starSystem, economy, secondEconomy, government, security, allegiance, event.getPopulation(), factionState, x, y, z), body));
        }
        EDDNService.carrierjump(event);
        if(event.getMarketID().toString().equals(UserPreferencesService.getPreference(PreferenceConstants.FLEET_CARRIER_ID, "none"))) {
            LocationService.setFleetCarrierLocation(Optional.of(new StarSystem(event.getStarSystem(), event.getStarPos().get(0).doubleValue(), event.getStarPos().get(1).doubleValue(), event.getStarPos().get(2).doubleValue())));
            CarrierService.carrierExistsProperty(CarrierType.FLEETCARRIER).set(true);
            CarrierService.setCarrierCallSign(CarrierType.FLEETCARRIER, event.getStationName());

        } else if(event.getMarketID().toString().equals(UserPreferencesService.getPreference(PreferenceConstants.SQUADRON_CARRIER_ID, "none"))) {
            LocationService.setSquadronCarrierLocation(Optional.of(new StarSystem(event.getStarSystem(), event.getStarPos().get(0).doubleValue(), event.getStarPos().get(1).doubleValue(), event.getStarPos().get(2).doubleValue())));
            CarrierService.carrierExistsProperty(CarrierType.SQUADRONCARRIER).set(false);
            CarrierService.setCarrierCallSign(CarrierType.SQUADRONCARRIER, event.getStationName());
        }
    }
    @Override
    public Class<CarrierJump> getMessageClass() {
        return CarrierJump.class;
    }

}
