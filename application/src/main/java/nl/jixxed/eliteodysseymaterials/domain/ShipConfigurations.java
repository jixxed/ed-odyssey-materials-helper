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
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor
@Data
public class ShipConfigurations {
    @SuppressWarnings("java:S1700")
    private Set<ShipConfiguration> shipConfigurations = new HashSet<>();
    private String selectedShipConfigurationUUID;
    @JsonIgnore
    public Set<ShipConfiguration> getAllShipConfigurations() {
        final Set<ShipConfiguration> shipConfigurations1 =  new HashSet<>(this.shipConfigurations);
        shipConfigurations1.add(ShipConfiguration.CURRENT);
        return Collections.unmodifiableSet(shipConfigurations1);
    }

    @JsonIgnore
    public Optional<ShipConfiguration> getSelectedShipConfiguration() {
        if (this.selectedShipConfigurationUUID == null || this.selectedShipConfigurationUUID.isEmpty()) {
            return selectFirstShipConfiguration();
        } else {
            return this.getAllShipConfigurations().stream().filter(shipConfiguration -> shipConfiguration.getUuid().equals(this.selectedShipConfigurationUUID)).findFirst().or(this::selectFirstShipConfiguration);
        }
    }

    @JsonIgnore
    private Optional<ShipConfiguration> selectFirstShipConfiguration() {
        final Optional<ShipConfiguration> shipConfiguration = this.getAllShipConfigurations().stream().findFirst();
        shipConfiguration.ifPresent(configuration -> this.selectedShipConfigurationUUID = configuration.getUuid());
        return shipConfiguration;
    }

    @JsonIgnore
    public ShipConfiguration getShipConfiguration(final String uuid) {
        return getAllShipConfigurations().stream().filter(shipConfiguration -> shipConfiguration.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    @JsonIgnore
    public void addShipConfiguration(final ShipConfiguration shipConfigurationToAdd) {
        //reset UUID if already exists
        while (getAllShipConfigurations().stream().anyMatch(shipConfiguration -> shipConfiguration.getUuid().equals(shipConfigurationToAdd.getUuid()))) {
            shipConfigurationToAdd.setUuid(UUID.randomUUID().toString());
        }
        this.shipConfigurations.add(shipConfigurationToAdd);
    }

    @JsonIgnore
    public void delete(final String ShipConfigurationUUID) {
        this.shipConfigurations.removeIf(shipConfiguration -> shipConfiguration.getUuid().equals(ShipConfigurationUUID));
    }

    @JsonIgnore
    public void renameShipConfiguration(final String uuid, final String name) {
        if (name != null && !name.isEmpty()) {
            final ShipConfiguration shipConfiguration = getShipConfiguration(uuid);
            shipConfiguration.setName((name.length() > 50) ? name.substring(0, 50) : name);
        }
    }

    public ShipConfiguration createShipConfiguration(final String name) {
        final ShipConfiguration shipConfiguration = new ShipConfiguration();
        if (name != null && !name.isEmpty()) {
            shipConfiguration.setName(name);
        } else {
            shipConfiguration.setName("Unnamed ship");
        }
        this.addShipConfiguration(shipConfiguration);
        this.selectedShipConfigurationUUID = shipConfiguration.getUuid();
        return shipConfiguration;
    }

    public void resetShipConfiguration(String shipUUID) {
        final ShipConfiguration shipConfiguration = getShipConfiguration(shipUUID);
        shipConfiguration.setShipType(null);
        shipConfiguration.getHardpointSlots().clear();
        shipConfiguration.getCoreSlots().clear();
        shipConfiguration.getUtilitySlots().clear();
        shipConfiguration.getOptionalSlots().clear();
    }
}
