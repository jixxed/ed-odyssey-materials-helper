package nl.jixxed.eliteodysseymaterials.service.ships;

import com.google.common.primitives.Ints;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.Modification;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShipMapper {
    public static ShipConfiguration toShipConfiguration(Ship ship, ShipConfiguration shipConfiguration, String name) {
        shipConfiguration.setShipType(ship.getShipType());
        shipConfiguration.setName(name);
        shipConfiguration.setCurrentFuelReserve(ship.getCurrentFuelReserve());
        shipConfiguration.setCurrentCargo(ship.getCurrentCargo());
        shipConfiguration.setCurrentFuel(ship.getCurrentFuel());
        shipConfiguration.getCoreSlots().clear();
        shipConfiguration.getOptionalSlots().clear();
        shipConfiguration.getHardpointSlots().clear();
        shipConfiguration.getUtilitySlots().clear();
        ship.getCoreSlots().forEach(slot -> {
            if (slot.isOccupied()) {
                final ShipConfigurationSlot shipConfigurationSlot = new ShipConfigurationSlot();
                shipConfiguration.getCoreSlots().add(shipConfigurationSlot);
                mapShipConfigurationSlot(slot, shipConfigurationSlot);
            }
        });
        ship.getOptionalSlots().forEach(slot -> {
            if (slot.isOccupied()) {
                final ShipConfigurationSlot shipConfigurationSlot = new ShipConfigurationSlot();
                shipConfiguration.getOptionalSlots().add(shipConfigurationSlot);
                mapShipConfigurationSlot(slot, shipConfigurationSlot);
            }
        });
        ship.getHardpointSlots().forEach(slot -> {
            if (slot.isOccupied()) {
                final ShipConfigurationSlot shipConfigurationSlot = new ShipConfigurationSlot();
                shipConfiguration.getHardpointSlots().add(shipConfigurationSlot);
                mapShipConfigurationSlot(slot, shipConfigurationSlot);
            }
        });
        ship.getUtilitySlots().forEach(slot -> {
            if (slot.isOccupied()) {
                final ShipConfigurationSlot shipConfigurationSlot = new ShipConfigurationSlot();
                shipConfiguration.getUtilitySlots().add(shipConfigurationSlot);
                mapShipConfigurationSlot(slot, shipConfigurationSlot);
            }
        });
        final ShipConfigurationSlot shipConfigurationSlot = new ShipConfigurationSlot();
        mapShipConfigurationSlot(ship.getCargoHatch(), shipConfigurationSlot);
        shipConfiguration.setCargoHatch(shipConfigurationSlot);
        return shipConfiguration;
    }

    public static Ship toShip(ShipConfiguration shipConfiguration) {
        if (shipConfiguration.getShipType() == null) {
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
        mapSlot(shipConfiguration.getCargoHatch(), ship.getCargoHatch());
        ship.setCurrentFuelReserve(shipConfiguration.getCurrentFuelReserve());
        ship.setCurrentCargo(shipConfiguration.getCurrentCargo());
        ship.setCurrentFuel(shipConfiguration.getCurrentFuel());
        return ship;
    }

    private static void mapSlot(ShipConfigurationSlot shipConfigurationSlot, Slot slot) {
        final Optional<ShipModule> shipModule1 = ShipModule.getModules(slot.getSlotType()).stream().filter(shipModule -> shipModule.getId().equals(shipConfigurationSlot.getId())).findFirst();
        final Optional<ShipModule> oldShipModule = Optional.ofNullable(shipConfigurationSlot.getOldModule()).map(mod->ShipModule.getModules(slot.getSlotType()).stream().filter(shipModule -> shipModule.getId().equals(mod.getId())).findFirst()).orElse(Optional.empty());
        shipModule1.ifPresent(shipModule2 -> {
            ShipModule shipModule = shipModule2.Clone();
            shipConfigurationSlot.getModification().forEach(shipConfigurationModification -> {
                if (!shipConfigurationModification.getType().isPreEngineered()) {// no need to double apply pre-engineering
                    shipModule.getModifications().add(new Modification(shipConfigurationModification.getType(), shipConfigurationModification.getPercentComplete(), shipConfigurationModification.getGrade()));
                }
            });
            shipConfigurationSlot.getExperimentalEffect().forEach(shipConfigurationExperimentalEffect -> {
                shipModule.getExperimentalEffects().add(shipConfigurationExperimentalEffect.getType());
            });
            if (!shipConfigurationSlot.getModifiers().isEmpty()) {
                shipModule.getModifiers().putAll(shipConfigurationSlot.getModifiers());
            }
            final Long buyPrice = shipConfigurationSlot.getBuyPrice();
            if (buyPrice != null) {
                shipModule.setBuyPrice(buyPrice);
            }
            if (shipConfigurationSlot.isLegacy()) {
                shipModule.setLegacy(true);
            }
            if (shipConfigurationSlot.getPowered() != null && Boolean.FALSE.equals(shipConfigurationSlot.getPowered())) {
                shipModule.togglePower();
            }
            if (shipConfigurationSlot.getPowerGroup() != null) {
                shipModule.setPowerGroup(Ints.constrainToRange(shipConfigurationSlot.getPowerGroup(), 1, 5));

            }
            slot.setShipModule(shipModule);
        });
        oldShipModule.ifPresentOrElse(shipModule2 -> {
            ShipModule shipModule = shipModule2.Clone();
            shipConfigurationSlot.getOldModule().getModification().forEach(shipConfigurationModification -> {
                if (!shipConfigurationModification.getType().isPreEngineered()) {// no need to double apply pre-engineering
                    shipModule.getModifications().add(new Modification(shipConfigurationModification.getType(), shipConfigurationModification.getPercentComplete(), shipConfigurationModification.getGrade()));
                }
            });
            shipConfigurationSlot.getOldModule().getExperimentalEffect().forEach(shipConfigurationExperimentalEffect -> {
                shipModule.getExperimentalEffects().add(shipConfigurationExperimentalEffect.getType());
            });
            shipConfigurationSlot.getOldModule().getModifiers().forEach((modifier, value) -> {
                shipModule.getModifiers().put(modifier,value);
            });
            shipModule.setLegacy(Boolean.TRUE.equals(shipConfigurationSlot.getOldModule().getLegacy()));
            final Long buyPrice = shipConfigurationSlot.getOldModule().getBuyPrice();
            if (buyPrice != null) {
                shipModule.setBuyPrice(buyPrice);
            }
            final Integer powerGroup = shipConfigurationSlot.getOldModule().getPowerGroup();
            if (powerGroup != null) {
                shipModule.setPowerGroup(powerGroup);
            }
            if (Boolean.FALSE.equals(shipConfigurationSlot.getOldModule().getPowered())) {
                shipModule.togglePower();
            }
            slot.setOldShipModule(shipModule);
        }, () -> slot.setOldShipModule(null));
    }

    private static void mapShipConfigurationSlot(Slot slot, ShipConfigurationSlot shipConfigurationSlot) {
        shipConfigurationSlot.setIndex(slot.getIndex());
        Optional.ofNullable(slot.getShipModule()).ifPresent(shipModule -> {
            shipConfigurationSlot.setId(shipModule.getId());
            shipConfigurationSlot.setModification(shipModule.getModifications().stream().map(modification -> new ShipConfigurationModification(modification.getModification(), modification.getGrade(), modification.getModificationCompleteness().orElse(BigDecimal.ZERO))).toList());
            shipConfigurationSlot.setExperimentalEffect(shipModule.getExperimentalEffects().stream().map(ShipConfigurationExperimentalEffect::new).toList());
            shipConfigurationSlot.setBuyPrice(shipModule.getBuyPrice());
            if (shipModule.isLegacy()) {
                shipConfigurationSlot.setLegacy(true);
            }
            if (!shipModule.isPowered()) {
                shipConfigurationSlot.setPowered(false);
            }
            if (shipModule.getPowerGroup() > 1) {
                shipConfigurationSlot.setPowerGroup(shipModule.getPowerGroup());
            }
            shipConfigurationSlot.getModifiers().putAll(shipModule.getModifiers());
        });
        Optional.ofNullable(slot.getOldShipModule()).ifPresentOrElse(shipModule -> {
            var id = shipModule.getId();
            var modifications = shipModule.getModifications().stream().map(modification -> new ShipConfigurationModification(modification.getModification(), modification.getGrade(), modification.getModificationCompleteness().orElse(BigDecimal.ZERO))).toList();
            var effects = shipModule.getExperimentalEffects().stream().map(ShipConfigurationExperimentalEffect::new).toList();
            var legacy = shipModule.isLegacy();
            var powered = shipModule.isPowered();
            var powerGroup = shipModule.getPowerGroup();
            var buyPrice = shipModule.getBuyPrice();
            var modifiers = shipModule.getModifiers().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            shipConfigurationSlot.setOldModule(new ShipConfigurationOldModule(id, legacy,powered,powerGroup,buyPrice, modifiers, modifications, effects));
        }, () -> shipConfigurationSlot.setOldModule(null));
    }
}
