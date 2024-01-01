package nl.jixxed.eliteodysseymaterials.service.ships;

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
    public static ShipConfiguration toShipConfiguration(Ship ship, ShipConfiguration shipConfiguration, String name) {
        shipConfiguration.setShipType(ship.getShipType());
        shipConfiguration.setName(name);
        shipConfiguration.getCoreSlots().clear();
        shipConfiguration.getOptionalSlots().clear();
        shipConfiguration.getHardpointSlots().clear();
        shipConfiguration.getUtilitySlots().clear();
        ship.getCoreSlots().forEach(slot -> {
            if(slot.isOccupied()) {
                final ShipConfigurationSlot shipConfigurationSlot = new ShipConfigurationSlot();
                shipConfiguration.getCoreSlots().add(shipConfigurationSlot);
                mapShipConfigurationSlot(slot, shipConfigurationSlot);
            }
        });
        ship.getOptionalSlots().forEach(slot -> {
            if(slot.isOccupied()) {
                final ShipConfigurationSlot shipConfigurationSlot = new ShipConfigurationSlot();
                shipConfiguration.getOptionalSlots().add(shipConfigurationSlot);
                mapShipConfigurationSlot(slot,shipConfigurationSlot);
            }
        });
        ship.getHardpointSlots().forEach(slot -> {
            if(slot.isOccupied()) {
                final ShipConfigurationSlot shipConfigurationSlot = new ShipConfigurationSlot();
            shipConfiguration.getHardpointSlots().add(shipConfigurationSlot);
            mapShipConfigurationSlot(slot,shipConfigurationSlot);
            }
        });
        ship.getUtilitySlots().forEach(slot -> {
            if(slot.isOccupied()) {
                final ShipConfigurationSlot shipConfigurationSlot = new ShipConfigurationSlot();
            shipConfiguration.getUtilitySlots().add(shipConfigurationSlot);
            mapShipConfigurationSlot(slot,shipConfigurationSlot);
            }
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
        final Optional<ShipModule> shipModule1 = ShipModule.getModules(slot.getSlotType()).stream().filter(shipModule -> shipModule.getId().equals(shipConfigurationSlot.getId())).findFirst();
        shipModule1.ifPresent(shipModule2 -> {
            ShipModule shipModule = shipModule2.Clone();
            shipConfigurationSlot.getModification().forEach(shipConfigurationModification -> {
                if(!shipConfigurationModification.getType().isPreEngineered()) {// no need to double apply pre-engineering
                    shipModule.getModifications().add(new Modification(shipConfigurationModification.getType(), shipConfigurationModification.getPercentComplete(), shipConfigurationModification.getGrade()));
                }
            });
            shipConfigurationSlot.getExperimentalEffect().forEach(shipConfigurationExperimentalEffect -> {
                shipModule.getExperimentalEffects().add(shipConfigurationExperimentalEffect.getType());
            });
            if(!shipConfigurationSlot.getModifiers().isEmpty()) {
                shipModule.getModifiers().putAll(shipConfigurationSlot.getModifiers());
            }
            if(shipConfigurationSlot.isLegacy()){
                shipModule.setLegacy(true);
            }
            slot.setShipModule(shipModule);
        });
    }
    private static void mapShipConfigurationSlot(Slot slot, ShipConfigurationSlot shipConfigurationSlot) {
        shipConfigurationSlot.setIndex(slot.getIndex());
        Optional.ofNullable(slot.getShipModule()).ifPresent(shipModule -> {
            shipConfigurationSlot.setId(shipModule.getId());
            shipConfigurationSlot.setModification(shipModule.getModifications().stream().map(modification -> new ShipConfigurationModification(modification.getModification(),modification.getGrade(),modification.getModificationCompleteness())).toList());
            shipConfigurationSlot.setExperimentalEffect(shipModule.getExperimentalEffects().stream().map(ShipConfigurationExperimentalEffect::new).toList());
            if(shipModule.isLegacy()) {
                shipConfigurationSlot.setLegacy(true);
            }
            shipConfigurationSlot.getModifiers().putAll(shipModule.getModifiers());
        });
    }
}
