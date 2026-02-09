package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.CarrierType;
import nl.jixxed.eliteodysseymaterials.schemas.journal.CarrierLocation.CarrierLocation;
import nl.jixxed.eliteodysseymaterials.service.CarrierService;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.UserPreferencesService;

public class CarrierLocationMessageProcessor implements MessageProcessor<CarrierLocation> {
    @Override
    public void process(final CarrierLocation event) {
        if (event.getCarrierType().map("FleetCarrier"::equals).orElse(false)) {
            UserPreferencesService.setPreference(PreferenceConstants.FLEET_CARRIER_ID, event.getCarrierID().toString());
            LocationService.setFleetCarrierLocationByAddress(event.getSystemAddress());
            CarrierService.carrierExistsProperty(CarrierType.FLEETCARRIER).set(true);
        } else if (event.getCarrierType().map("SquadronCarrier"::equals).orElse(false)) {
            UserPreferencesService.setPreference(PreferenceConstants.SQUADRON_CARRIER_ID, event.getCarrierID().toString());
            LocationService.setSquadronCarrierLocationByAddress(event.getSystemAddress());
            CarrierService.carrierExistsProperty(CarrierType.SQUADRONCARRIER).set(true);
        }
    }

    @Override
    public Class<CarrierLocation> getMessageClass() {
        return CarrierLocation.class;
    }
}
