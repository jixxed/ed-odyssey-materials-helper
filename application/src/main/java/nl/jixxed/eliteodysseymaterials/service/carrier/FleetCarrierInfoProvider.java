package nl.jixxed.eliteodysseymaterials.service.carrier;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.CarrierDockingAccess;
import nl.jixxed.eliteodysseymaterials.enums.CarrierState;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.OrderService;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;

import java.math.BigInteger;

@Setter
@Getter
public class FleetCarrierInfoProvider implements CarrierInfoProvider {
    @Getter(AccessLevel.PRIVATE)
    private final BooleanProperty fleetCarrier = new SimpleBooleanProperty(false);
    private String carrierName;
    private CarrierState carrierState;
    private CarrierDockingAccess carrierDockingAccess;
    private Boolean carrierNotoriousAccess;
    private BigInteger carrierBalance;
    private BigInteger carrierBankBalance;
    private Integer carrierFuel;
    private String carrierCallSign;
    private Integer shipPacks;
    private Integer modulePacks;
    private Integer cargo;
    private Integer cargoSpaceReserved;
    private Integer crew;
    private Integer freeSpace;
    private Integer microresourceCapacityTotal;
    private Integer microresourceCapacityFree;
    private Integer microresourceCapacityUsed;
    private Integer microresourceCapacityReserved;
    private Integer squadronBankTotal;

    @Override
    public String getCarrierTitle() {
        return "statusbar.fleetcarrier";
    }

    @Override
    public EdAwesomeIcon getCarrierIcon() {
        return EdAwesomeIcon.OTHER_CARRIER_SIMPLE;
    }

    @Override
    public Integer getCarrierOrders() {
        return OrderService.getBuyOrderOutstandingCount(StoragePool.FLEETCARRIER);
    }

    @Override
    public BooleanProperty carrierExistsProperty() {
        return fleetCarrier;
    }

    @Override
    public Integer getUsedSpace() {
        if (freeSpace == null) {
            return null;
        }
        return 25000 - freeSpace;
    }
}
