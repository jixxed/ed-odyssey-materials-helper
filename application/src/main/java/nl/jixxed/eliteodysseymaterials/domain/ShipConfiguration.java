package nl.jixxed.eliteodysseymaterials.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private ShipConfigurationSlot cargoHatch;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ShipConfigurationSlot> hardpointSlots = new CopyOnWriteArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ShipConfigurationSlot> utilitySlots = new CopyOnWriteArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ShipConfigurationSlot> coreSlots = new CopyOnWriteArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ShipConfigurationSlot> optionalSlots = new CopyOnWriteArrayList<>();
    private double currentFuelReserve;
    private double currentFuel;
    private double currentCargo;

    @JsonIgnore
    public static final ShipConfiguration CURRENT = new ShipConfiguration("0", "Current Ship (read only)");

    public ShipConfiguration(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    @Override
    public String toString() {
        return getName() + ((shipType != null) ? " - " + LocaleService.getLocalizedStringForCurrentLocale(shipType.getLocalizationKey()):"");
    }

    public ShipConfiguration cloneShipConfiguration() {
        final ShipConfiguration newShipConfiguration = new ShipConfiguration();
        String newName = this.name;
        if (this == CURRENT) {
            newName = newName.replace(" (read only)", "");
        }
        newShipConfiguration.setName(newName + " (cloned)");
        newShipConfiguration.setShipType(this.shipType);
        newShipConfiguration.getCoreSlots().addAll(this.getCoreSlots().stream().map(ShipConfigurationSlot::cloneShipConfigurationSlot).toList());
        newShipConfiguration.getHardpointSlots().addAll(this.getHardpointSlots().stream().map(ShipConfigurationSlot::cloneShipConfigurationSlot).toList());
        newShipConfiguration.getOptionalSlots().addAll(this.getOptionalSlots().stream().map(ShipConfigurationSlot::cloneShipConfigurationSlot).toList());
        newShipConfiguration.getUtilitySlots().addAll(this.getUtilitySlots().stream().map(ShipConfigurationSlot::cloneShipConfigurationSlot).toList());
        newShipConfiguration.setCurrentFuel(this.getCurrentFuel());
        newShipConfiguration.setCurrentCargo(this.getCurrentCargo());
        newShipConfiguration.setCurrentFuelReserve(this.getCurrentFuelReserve());
        newShipConfiguration.setCargoHatch(this.getCargoHatch().cloneShipConfigurationSlot());
        return newShipConfiguration;
    }
    public static synchronized void resetCurrent(){
        CURRENT.name = "Current Ship (read only)";
        CURRENT.shipType = null;
        CURRENT.coreSlots.clear();
        CURRENT.hardpointSlots.clear();
        CURRENT.optionalSlots.clear();
        CURRENT.utilitySlots.clear();
        CURRENT.currentFuel = 0;
        CURRENT.currentCargo = 0;
        CURRENT.currentFuelReserve = 0;
        CURRENT.cargoHatch = null;
    }
}
