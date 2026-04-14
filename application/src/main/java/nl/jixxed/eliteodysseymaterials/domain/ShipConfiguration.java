/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties(ignoreUnknown = true)
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
