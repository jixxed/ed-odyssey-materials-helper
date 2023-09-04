package nl.jixxed.eliteodysseymaterials.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ShipConfiguration {

    @JsonIgnore
    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    @EqualsAndHashCode.Include
    private String uuid = UUID.randomUUID().toString();
    private String name;
    private ShipType shipType;
    private final List<ShipConfigurationSlot> hardpointSlots = new ArrayList<>();
    private final List<ShipConfigurationSlot> utilitySlots = new ArrayList<>();
    private final List<ShipConfigurationSlot> coreSlots = new ArrayList<>();
    private final List<ShipConfigurationSlot> optionalSlots = new ArrayList<>();


    @Override
    public String toString() {
        return getName();
    }

}
