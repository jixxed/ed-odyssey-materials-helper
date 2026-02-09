package nl.jixxed.eliteodysseymaterials.service;

import javafx.beans.property.BooleanProperty;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.CarrierDockingAccess;
import nl.jixxed.eliteodysseymaterials.enums.CarrierState;
import nl.jixxed.eliteodysseymaterials.enums.CarrierType;
import nl.jixxed.eliteodysseymaterials.enums.SquadronPerk;
import nl.jixxed.eliteodysseymaterials.schemas.capi.fleetcarrier.Capacity;
import nl.jixxed.eliteodysseymaterials.schemas.journal.CarrierStats.SpaceUsage;
import nl.jixxed.eliteodysseymaterials.service.carrier.CarrierInfoProvider;
import nl.jixxed.eliteodysseymaterials.service.carrier.FleetCarrierInfoProvider;
import nl.jixxed.eliteodysseymaterials.service.carrier.SquadronCarrierInfoProvider;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalInitEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CarrierService {

    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    static {
        EVENT_LISTENERS.add(EventService.addStaticListener(JournalInitEvent.class, journalInitEvent -> {
            if (!journalInitEvent.isInitialised()) {
                reset();
            }
        }));
    }

    public static void reset() {
        providers.forEach((carrierType, carrierInfoProvider) -> {
            carrierInfoProvider.carrierExistsProperty().set(false);
            carrierInfoProvider.setCarrierState(null);
            carrierInfoProvider.setCarrierName("");
            carrierInfoProvider.setCarrierCallSign("");
            carrierInfoProvider.setCarrierNotoriousAccess(null);
            carrierInfoProvider.setCarrierDockingAccess(null);
            carrierInfoProvider.setCarrierBalance(null);
            carrierInfoProvider.setCarrierBankBalance(null);

            carrierInfoProvider.setShipPacks(null);
            carrierInfoProvider.setModulePacks(null);
            carrierInfoProvider.setCargo(null);
            carrierInfoProvider.setCargoSpaceReserved(null);
            carrierInfoProvider.setCrew(null);
            carrierInfoProvider.setFreeSpace(null);
            carrierInfoProvider.setMicroresourceCapacityTotal(null);
            carrierInfoProvider.setMicroresourceCapacityFree(null);
            carrierInfoProvider.setMicroresourceCapacityUsed(null);
            carrierInfoProvider.setMicroresourceCapacityReserved(null);
            carrierInfoProvider.setSquadronBankTotal(null);
        });
    }

    private static final Map<CarrierType, CarrierInfoProvider> providers = Map.of(
            CarrierType.FLEETCARRIER, new FleetCarrierInfoProvider(),
            CarrierType.SQUADRONCARRIER, new SquadronCarrierInfoProvider()
    );

    public static String getCarrierTitle(CarrierType carrierType) {
        return providers.get(carrierType).getCarrierTitle();
    }

    public static EdAwesomeIcon getCarrierIcon(CarrierType carrierType) {
        return providers.get(carrierType).getCarrierIcon();
    }

    public static SquadronPerk getPrimaryPerk() {
        return ApplicationState.getInstance().getPrimaryPerk();
    }

    public static SquadronPerk getFactionPerk() {
        return ApplicationState.getInstance().getFactionPerk();
    }

    public static Optional<Integer> getCarrierOrders(CarrierType carrierType) {
        return Optional.ofNullable(providers.get(carrierType).getCarrierOrders());
    }

    public static BooleanProperty carrierExistsProperty(CarrierType carrierType) {
        return providers.get(carrierType).carrierExistsProperty();
    }

    public static Optional<CarrierState> getCarrierState(CarrierType carrierType) {
        return Optional.ofNullable(providers.get(carrierType).getCarrierState());
    }

    public static void setCarrierState(CarrierType carrierType, CarrierState carrierState) {
        providers.get(carrierType).setCarrierState(carrierState);
    }

    public static String getCarrierName(CarrierType carrierType) {
        return providers.get(carrierType).getCarrierName();
    }

    public static void setCarrierName(CarrierType carrierType, String carrierName) {
        providers.get(carrierType).setCarrierName(carrierName);
    }

    public static Optional<Boolean> getCarrierNotoriousAccess(CarrierType carrierType) {
        return Optional.ofNullable(providers.get(carrierType).getCarrierNotoriousAccess());
    }

    public static void setCarrierNotoriousAccess(CarrierType carrierType, Boolean carrierNotoriousAccess) {
        providers.get(carrierType).setCarrierNotoriousAccess(carrierNotoriousAccess);
    }

    public static Optional<CarrierDockingAccess> getCarrierDockingAccess(CarrierType carrierType) {
        return Optional.ofNullable(providers.get(carrierType).getCarrierDockingAccess());
    }

    public static void setCarrierDockingAccess(CarrierType carrierType, CarrierDockingAccess carrierDockingAccess) {
        providers.get(carrierType).setCarrierDockingAccess(carrierDockingAccess);
    }

    public static Optional<BigInteger> getCarrierBalance(CarrierType carrierType) {
        return Optional.ofNullable(providers.get(carrierType).getCarrierBalance());
    }

    public static void setCarrierBalance(CarrierType carrierType, BigInteger carrierBalance) {
        providers.get(carrierType).setCarrierBalance(carrierBalance);
    }

    public static Optional<BigInteger> getCarrierBankBalance(CarrierType carrierType) {
        return Optional.ofNullable(providers.get(carrierType).getCarrierBankBalance());
    }

    public static void setCarrierBankBalance(CarrierType carrierType, BigInteger carrierBankBalance) {
        providers.get(carrierType).setCarrierBankBalance(carrierBankBalance);
    }

    public static Optional<Integer> getCarrierFuel(CarrierType carrierType) {
        return Optional.ofNullable(providers.get(carrierType).getCarrierFuel());
    }

    public static void setCarrierFuel(CarrierType carrierType, Integer carrierFuel) {
        providers.get(carrierType).setCarrierFuel(carrierFuel);
    }

    public static String getCarrierCallSign(CarrierType carrierType) {
        return providers.get(carrierType).getCarrierCallSign();
    }

    public static void setCarrierCallSign(CarrierType carrierType, String carrierCallSign) {
        providers.get(carrierType).setCarrierCallSign(carrierCallSign);
    }

    public static void setCarrierCapacity(CarrierType carrierType, Capacity capacity) {
        providers.get(carrierType).setShipPacks(capacity.getShipPacks().intValue());
        providers.get(carrierType).setModulePacks(capacity.getModulePacks().intValue());
        providers.get(carrierType).setCargo(capacity.getCargoForSale().intValue() + capacity.getCargoNotForSale().intValue());
        providers.get(carrierType).setCargoSpaceReserved(capacity.getCargoSpaceReserved().intValue());
        providers.get(carrierType).setCrew(capacity.getCrew().intValue());
        providers.get(carrierType).setFreeSpace(capacity.getFreeSpace().intValue());
        providers.get(carrierType).setMicroresourceCapacityTotal(capacity.getMicroresourceCapacityTotal().intValue());
        providers.get(carrierType).setMicroresourceCapacityFree(capacity.getMicroresourceCapacityFree().intValue());
        providers.get(carrierType).setMicroresourceCapacityUsed(capacity.getMicroresourceCapacityUsed().intValue());
        providers.get(carrierType).setMicroresourceCapacityReserved(capacity.getMicroresourceCapacityReserved().intValue());
        providers.get(carrierType).setSquadronBankTotal(capacity.getSquadronBankTotal().map(BigInteger::intValue).orElse(0));
    }

    public static void setSpaceUsage(CarrierType carrierType, SpaceUsage spaceUsage) {
        providers.get(carrierType).setCargo(spaceUsage.getCargo().intValue());
        providers.get(carrierType).setShipPacks(spaceUsage.getShipPacks().intValue());
        providers.get(carrierType).setModulePacks(spaceUsage.getModulePacks().intValue());
        providers.get(carrierType).setCargoSpaceReserved(spaceUsage.getCargoSpaceReserved().intValue());
        providers.get(carrierType).setCrew(spaceUsage.getCrew().intValue());
        providers.get(carrierType).setFreeSpace(spaceUsage.getFreeSpace().intValue());
    }

    public static Optional<Integer> getShipPacks(CarrierType carrierType) {
        return Optional.ofNullable(providers.get(carrierType).getShipPacks());
    }

    public static Optional<Integer> getModulePacks(CarrierType carrierType) {
        return Optional.ofNullable(providers.get(carrierType).getModulePacks());
    }

    public static Optional<Integer> getCargo(CarrierType carrierType) {
        return Optional.ofNullable(providers.get(carrierType).getCargo());
    }

    public static Optional<Integer> getCargoSpaceReserved(CarrierType carrierType) {
        return Optional.ofNullable(providers.get(carrierType).getCargoSpaceReserved());
    }

    public static Optional<Integer> getCrew(CarrierType carrierType) {
        return Optional.ofNullable(providers.get(carrierType).getCrew());
    }

    public static Optional<Integer> getFreeSpace(CarrierType carrierType) {
        return Optional.ofNullable(providers.get(carrierType).getFreeSpace());
    }

    public static Optional<Integer> getUsedSpace(CarrierType carrierType) {
        return Optional.ofNullable(providers.get(carrierType).getUsedSpace());
    }

    public static Optional<Integer> getMicroresourceCapacityTotal(CarrierType carrierType) {
        return Optional.ofNullable(providers.get(carrierType).getMicroresourceCapacityTotal());
    }

    public static Optional<Integer> getMicroresourceCapacityFree(CarrierType carrierType) {
        return Optional.ofNullable(providers.get(carrierType).getMicroresourceCapacityFree());
    }

    public static Optional<Integer> getMicroresourceCapacityUsed(CarrierType carrierType) {
        return Optional.ofNullable(providers.get(carrierType).getMicroresourceCapacityUsed());
    }

    public static Optional<Integer> getMicroresourceCapacityReserved(CarrierType carrierType) {
        return Optional.ofNullable(providers.get(carrierType).getMicroresourceCapacityReserved());
    }

    public static Optional<Integer> getSquadronBankTotal(CarrierType carrierType) {
        return Optional.ofNullable(providers.get(carrierType).getSquadronBankTotal());
    }
}
