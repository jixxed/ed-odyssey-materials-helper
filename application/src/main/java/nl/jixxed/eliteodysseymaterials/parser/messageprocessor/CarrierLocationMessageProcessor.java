package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.CarrierLocation.CarrierLocation;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.UserPreferencesService;

public class CarrierLocationMessageProcessor implements MessageProcessor<CarrierLocation> {
    @Override
    public void process(final CarrierLocation event) {
        if(event.getCarrierType().map("FleetCarrier"::equals).orElse(false)) {
            UserPreferencesService.setPreference("fleetcarrier.carrier.id", event.getCarrierID().toString());
            LocationService.setCarrierLocation(event.getStarSystem());
        }
    }

    @Override
    public Class<CarrierLocation> getMessageClass() {
        return CarrierLocation.class;
    }
}
