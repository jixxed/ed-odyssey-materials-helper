package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.CarrierDockingAccess;
import nl.jixxed.eliteodysseymaterials.enums.CarrierType;
import nl.jixxed.eliteodysseymaterials.schemas.journal.CarrierStats.CarrierStats;
import nl.jixxed.eliteodysseymaterials.service.CarrierService;
import nl.jixxed.eliteodysseymaterials.service.UserPreferencesService;

public class CarrierStatsMessageProcessor implements MessageProcessor<CarrierStats> {
    @Override
    public void process(final CarrierStats event) {
        if(event.getCarrierID().toString().equals(UserPreferencesService.getPreference(PreferenceConstants.FLEET_CARRIER_ID, "none"))) {
            updateCarrierInfo(CarrierType.FLEETCARRIER, event);

        } else if(event.getCarrierID().toString().equals(UserPreferencesService.getPreference(PreferenceConstants.SQUADRON_CARRIER_ID, "none"))) {
            updateCarrierInfo(CarrierType.SQUADRONCARRIER, event);
        }
    }

    private static void updateCarrierInfo(CarrierType carrierType, CarrierStats event) {
        CarrierService.carrierExistsProperty(carrierType).set(true);
        CarrierService.setCarrierCallSign(carrierType, event.getCallsign());
        CarrierService.setCarrierName(carrierType, event.getName());
        CarrierService.setCarrierBalance(carrierType, event.getFinance().getCarrierBalance());
        CarrierService.setSpaceUsage(carrierType, event.getSpaceUsage());
        CarrierService.setCarrierNotoriousAccess(carrierType, event.getAllowNotorious());
        CarrierService.setCarrierDockingAccess(carrierType, CarrierDockingAccess.forKey(event.getDockingAccess()));
        CarrierService.setCarrierFuel(carrierType, event.getFuelLevel().intValue());
    }

    @Override
    public Class<CarrierStats> getMessageClass() {
        return CarrierStats.class;
    }

}
