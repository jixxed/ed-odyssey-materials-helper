package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class StatusEvent implements Event {
    private final BigDecimal fuelReserve;
    private final BigDecimal fuelCapacity;
    private final BigDecimal cargoCapacity;
}
