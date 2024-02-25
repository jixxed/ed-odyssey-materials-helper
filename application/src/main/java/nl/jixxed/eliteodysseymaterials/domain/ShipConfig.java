package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class ShipConfig {
    private final BigDecimal fuelReserve;
    private final BigDecimal fuelCapacity;
    private final BigDecimal cargoCapacity;
    private final Integer systemPips;
    private final Integer enginePips;
    private final Integer weaponPips;

}
