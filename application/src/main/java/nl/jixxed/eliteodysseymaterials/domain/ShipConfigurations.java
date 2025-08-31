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
