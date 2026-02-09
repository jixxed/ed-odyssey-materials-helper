package nl.jixxed.eliteodysseymaterials.service.carrier;

import javafx.beans.property.BooleanProperty;
import nl.jixxed.eliteodysseymaterials.enums.CarrierDockingAccess;
import nl.jixxed.eliteodysseymaterials.enums.CarrierState;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;

import java.math.BigInteger;

public interface CarrierInfoProvider {
    String getCarrierTitle();

    EdAwesomeIcon getCarrierIcon();

    Integer getCarrierOrders();

    BooleanProperty carrierExistsProperty();

    String getCarrierName();

    void setCarrierName(String carrierName);

    CarrierState getCarrierState();

    void setCarrierState(CarrierState carrierState);

    CarrierDockingAccess getCarrierDockingAccess();

    void setCarrierDockingAccess(CarrierDockingAccess carrierDockingAccess);

    Boolean getCarrierNotoriousAccess();

    void setCarrierNotoriousAccess(Boolean carrierNotoriousAccess);

    BigInteger getCarrierBalance();

    void setCarrierBalance(BigInteger carrierBalance);
    BigInteger getCarrierBankBalance();

    void setCarrierBankBalance(BigInteger carrierBankBalance);

    Integer getCarrierFuel();

    void setCarrierFuel(Integer carrierFuel);

    String getCarrierCallSign();

    void setCarrierCallSign(String carrierCallSign);

    Integer getShipPacks();

    void setShipPacks(Integer shipPacks);

    Integer getModulePacks();

    void setModulePacks(Integer modulePacks);

    Integer getCargoSpaceReserved();

    void setCargoSpaceReserved(Integer cargoSpaceReserved);

    Integer getCrew();

    void setCrew(Integer crew);

    Integer getFreeSpace();

    void setFreeSpace(Integer freeSpace);

    Integer getMicroresourceCapacityTotal();

    void setMicroresourceCapacityTotal(Integer microresourceCapacityTotal);

    Integer getMicroresourceCapacityFree();

    void setMicroresourceCapacityFree(Integer microresourceCapacityFree);

    Integer getMicroresourceCapacityUsed();

    void setMicroresourceCapacityUsed(Integer microresourceCapacityUsed);

    Integer getMicroresourceCapacityReserved();

    void setMicroresourceCapacityReserved(Integer microresourceCapacityReserved);

    Integer getSquadronBankTotal();

    void setSquadronBankTotal(Integer squadronBankTotal);

    void setCargo(Integer cargo);

    Integer getCargo();

    Integer getUsedSpace();

//                      "capacity": {
//                        "shipPacks": 4950,
//                        "modulePacks": 105,
//                        "cargoForSale": 5128,
//                        "cargoNotForSale": 1825,
//                        "cargoSpaceReserved": 0,
//                        "crew": 6620,
//                        "freeSpace": 6372,
//                        "microresourceCapacityTotal": 1000,
//                        "microresourceCapacityFree": 700,
//                        "microresourceCapacityUsed": 300,
//                        "microresourceCapacityReserved": 0,
//                        "squadronBankTotal": 0
//            },
}
