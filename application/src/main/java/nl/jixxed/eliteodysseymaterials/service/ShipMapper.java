package nl.jixxed.eliteodysseymaterials.service;

import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfigurationExperimentalEffect;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfigurationModification;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfigurationSlot;
import nl.jixxed.eliteodysseymaterials.domain.ships.Modification;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;

import java.util.Optional;

public class ShipMapper {
    public static ShipConfiguration toShipConfiguration(Ship ship,ShipConfiguration shipConfiguration, String name) {
        shipConfiguration.setShipType(ship.getShipType());
        shipConfiguration.setName(name);
        shipConfiguration.getCoreSlots().clear();
        shipConfiguration.getOptionalSlots().clear();
        shipConfiguration.getHardpointSlots().clear();
        shipConfiguration.getUtilitySlots().clear();
        ship.getCoreSlots().forEach(slot -> {
            shipConfiguration.getCoreSlots().add(new ShipConfigurationSlot());
            final ShipConfigurationSlot shipConfigurationSlot = shipConfiguration.getCoreSlots().get(slot.getIndex());
            mapShipConfigurationSlot(slot,shipConfigurationSlot);
        });
        ship.getOptionalSlots().forEach(slot -> {
            shipConfiguration.getOptionalSlots().add(new ShipConfigurationSlot());
            final ShipConfigurationSlot shipConfigurationSlot = shipConfiguration.getOptionalSlots().get(slot.getIndex());
            mapShipConfigurationSlot(slot,shipConfigurationSlot);
        });
        ship.getHardpointSlots().forEach(slot -> {
            shipConfiguration.getHardpointSlots().add(new ShipConfigurationSlot());
            final ShipConfigurationSlot shipConfigurationSlot = shipConfiguration.getHardpointSlots().get(slot.getIndex());
            mapShipConfigurationSlot(slot,shipConfigurationSlot);
        });
        ship.getUtilitySlots().forEach(slot -> {
            shipConfiguration.getUtilitySlots().add(new ShipConfigurationSlot());
            final ShipConfigurationSlot shipConfigurationSlot = shipConfiguration.getUtilitySlots().get(slot.getIndex());
            mapShipConfigurationSlot(slot,shipConfigurationSlot);
        });
        return shipConfiguration;
    }

    public static Ship toShip(ShipConfiguration shipConfiguration) {
        if(shipConfiguration.getShipType() == null){
            return null;
        }
        Ship ship = ShipService.createBlankShip(shipConfiguration.getShipType());
        shipConfiguration.getCoreSlots().forEach(shipConfigurationSlot -> {
            final Slot slot = ship.getCoreSlots().get(shipConfigurationSlot.getIndex());
            mapSlot(shipConfigurationSlot, slot);
        });
        shipConfiguration.getOptionalSlots().forEach(shipConfigurationSlot -> {
            final Slot slot = ship.getOptionalSlots().get(shipConfigurationSlot.getIndex());
            mapSlot(shipConfigurationSlot, slot);
        });
        shipConfiguration.getHardpointSlots().forEach(shipConfigurationSlot -> {
            final Slot slot = ship.getHardpointSlots().get(shipConfigurationSlot.getIndex());
            mapSlot(shipConfigurationSlot, slot);
        });
        shipConfiguration.getUtilitySlots().forEach(shipConfigurationSlot -> {
            final Slot slot = ship.getUtilitySlots().get(shipConfigurationSlot.getIndex());
            mapSlot(shipConfigurationSlot, slot);
        });
        return ship;
    }

    private static void mapSlot(ShipConfigurationSlot shipConfigurationSlot, Slot slot) {
        final Optional<ShipModule> shipModule1 = ShipModule.getModules(slot.getSlotType()).stream().filter(shipModule -> shipModule.getInternalName().equals(shipConfigurationSlot.getInternalName())).findFirst();
        shipModule1.ifPresent(shipModule2 -> {
            ShipModule shipModule = shipModule2.Clone();
            shipConfigurationSlot.getModification().forEach(shipConfigurationModification -> {
                shipModule.getModifications().add(new Modification(shipConfigurationModification.getType(), shipConfigurationModification.getPercentComplete(), shipConfigurationModification.getGrade()));
            });
            shipConfigurationSlot.getExperimentalEffect().forEach(shipConfigurationExperimentalEffect -> {
                shipModule.getExperimentalEffects().add(shipConfigurationExperimentalEffect.getType());
            });
            slot.setShipModule(shipModule);
        });
    }
    private static void mapShipConfigurationSlot(Slot slot, ShipConfigurationSlot shipConfigurationSlot) {
        final Optional<ShipModule> shipModule1 = Optional.ofNullable(slot.getShipModule());
        shipConfigurationSlot.setIndex(slot.getIndex());
        shipModule1.ifPresent(shipModule2 -> {
            shipConfigurationSlot.setInternalName(shipModule2.getInternalName());
            shipConfigurationSlot.setModification(shipModule2.getModifications().stream().map(modification -> new ShipConfigurationModification(modification.getModification(),modification.getGrade(),modification.getModificationCompleteness())).toList());
            shipConfigurationSlot.setExperimentalEffect(shipModule2.getExperimentalEffects().stream().map(ShipConfigurationExperimentalEffect::new).toList());
        });
    }
}
