/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service.ships;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfigurationSlot;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.enums.MatchType;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Loadout.*;
import nl.jixxed.eliteodysseymaterials.service.ReportService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class LoadoutMapper {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.registerModule(new Jdk8Module().configureAbsentsAsNulls(true));
    }

    private static final Set<String> HARDPOINT_SLOT_NAMES = Set.of("SmallHardpoint", "MediumHardpoint", "LargeHardpoint", "HugeHardpoint");
    private static final Set<String> MINING_HARDPOINT_SLOT_NAMES = Set.of("SmallMiningHardpoint", "MediumMiningHardpoint", "LargeMiningHardpoint");
    private static final Set<String> UTILITY_SLOT_NAMES = Set.of("TinyHardpoint");
    private static final Set<String> CORE_SLOT_NAMES = Set.of("Armour", "PowerPlant", "MainEngines", "FrameShiftDrive", "LifeSupport", "PowerDistributor", "Radar", "FuelTank");
    private static final Set<String> MILITARY_SLOT_NAMES = Set.of("Military");
    private static final Set<String> CARGO_SLOT_NAMES = Set.of("Cargo");
    private static final Set<String> PASSENGER_SLOT_NAMES = Set.of("Passenger");
    private static final Set<String> SLF_SLOT_NAMES = Set.of("FighterBay");
    private static final Set<String> LIMPET_SLOT_NAMES = Set.of("LimpetController");
    private static final Set<String> OPTIONAL_SLOT_NAMES = Set.of("Slot");

    public static Ship toShip(Loadout loadout) {
        final ShipType shipType;
        try {
            shipType = ShipType.forInternalName(loadout.getShip());
        } catch (IllegalArgumentException e) {
            try {
                ReportService.reportJournal("module", OBJECT_MAPPER.writeValueAsString(loadout), "Can't map ship: " + loadout.getShip());
            } catch (Exception ex) {
                log.error("failed to send ship error", ex);
            }
            throw e;
        }
        final Ship ship = ShipService.createBlankShip(shipType);
        loadout.getModules().forEach(module -> {
            try {
                final String slotName = module.getSlot();
                Slot slot = getShipSlot(ship, slotName);
                if (slot != null) {
                    final List<ShipModule> potentialShipModules = getPotentialShipModules(module.getItem(), slot.getSlotType());
                    final ShipModule matchingModule = module.getEngineering().map(engineering -> {
                        if (!potentialShipModules.isEmpty()) {
                            if (isPreEngineered(potentialShipModules, engineering)) {
                                return potentialShipModules.stream().filter(ShipModule::isPreEngineered).filter(shipModule -> matchingEngineering(shipModule, engineering)).findFirst().orElseThrow(IllegalArgumentException::new);
                            } else {
                                return potentialShipModules.stream().filter(shipModule1 -> !shipModule1.isPreEngineered()).findFirst().orElseThrow(IllegalArgumentException::new);
                            }
                        }
                        try {
                            ReportService.reportJournal("module", OBJECT_MAPPER.writeValueAsString(module), "No potential modules found: " + module.getItem());
                        } catch (Exception e) {
                            log.error("failed to send module error", e);
                        }
                        throw new IllegalArgumentException("No potential modules found");
                    }).orElseGet(() -> {
                        try {
                            return potentialShipModules.stream().filter(shipModule1 -> !shipModule1.isPreEngineered()).findFirst().orElseThrow(() -> new IllegalArgumentException(slotName + "|" + module));
                        } catch (IllegalArgumentException ex) {
                            try {
                                ReportService.reportJournal("module", OBJECT_MAPPER.writeValueAsString(module), "Can't map regular module: " + module.getItem());
                            } catch (Exception e) {
                                log.error("failed to send module error", e);
                            }
                            return null;
                        }
                    });
                    if (matchingModule == null) {
                        return;
                    }
                    // Debug: log matching module for FSD
                    if (matchingModule.getClass().getSimpleName().equals("FrameShiftDrive") && module.getSlot().equals("FrameShiftDrive")) {
                        log.info("DEBUG toShip FSD: matchingModule.id={} preEng={} modsBeforeApply={}", matchingModule.getId(), matchingModule.isPreEngineered(), matchingModule.getModifications().stream().map(m -> m.getModification().name()).toList());
                    }
                    final ShipModule shipModule = matchingModule.Clone();
                    module.getEngineering().ifPresent(engineering -> {
                        boolean isLegacy = isLegacy(shipModule, engineering);
                        HorizonsBlueprintType blueprint = determineBlueprint(engineering);
                        HorizonsBlueprintGrade grade = determineGrade(engineering);
                        BigDecimal progression = determineGradeProgress(engineering);
                        if (isLegacy) {
                            shipModule.setLegacy(true);
                        }
                        HorizonsBlueprintType experimentalEffect = determineExperimentalEffect(engineering);
                        shipModule.applyModification(blueprint, grade, progression);
                        shipModule.applyExperimentalEffect(experimentalEffect);
                        engineering.getModifiers().forEach(modifier ->
                                modifier.getValue().ifPresent(value ->
                                        {
                                            final HorizonsModifier horizonsModifier = HorizonsModifier.forInternalName(modifier.getLabel());
                                            shipModule.addModifier(horizonsModifier, horizonsModifier.scale(value.doubleValue()));
                                        }
                                ));
                    });
                    // Debug: log modifications after apply for FSD
                    if (matchingModule.getClass().getSimpleName().equals("FrameShiftDrive") && module.getSlot().equals("FrameShiftDrive")) {
                        log.info("DEBUG toShip FSD after: mods={} slot={}", shipModule.getModifications().stream().map(m -> m.getModification().name()).toList(), module.getSlot());
                    }

                    module.getValue().ifPresent(value -> shipModule.setBuyPrice(value.longValue()));
                    shipModule.setPowerGroup(module.getPriority().intValue() + 1);
                    if (Boolean.FALSE.equals(module.getOn())) {
                        shipModule.togglePower();
                    }
                    slot.setShipModule(shipModule);
                    slot.setOldShipModule(shipModule.Clone());

                }
            } catch (IllegalArgumentException ex) {
                log.error("Failed to map module: " + module.getItem(), ex);
            }
        });
        return ship;
    }

    public static boolean matchingEngineering(ShipModule shipModule, Engineering engineering) {
        if (shipModule.getPreEngineeredMatchType().equals(MatchType.BLUEPRINT)) {
            return shipModule.getModifications().stream().anyMatch(modification -> modification.getModification().equals(HorizonsBlueprintType.forInternalName(engineering.getBlueprintName())))
                    && engineering.getExperimentalEffect().map(experimentalEffect -> shipModule.getExperimentalEffects().stream().anyMatch(effect -> effect.equals(HorizonsBlueprintType.forInternalName(experimentalEffect)))).orElse(true);
        }
        return isPreEngineered(List.of(shipModule), engineering);
    }

    private static HorizonsBlueprintType determineExperimentalEffect(Engineering engineering) {
        return engineering.getExperimentalEffect().map(HorizonsBlueprintType::forInternalName).orElse(null);
    }

    private static HorizonsBlueprintType determineBlueprint(Engineering engineering) {
        return HorizonsBlueprintType.forInternalName(engineering.getBlueprintName());
    }

    private static BigDecimal determineGradeProgress(Engineering engineering) {
        return engineering.getQuality();
    }

    private static HorizonsBlueprintGrade determineGrade(Engineering engineering) {
        return HorizonsBlueprintGrade.forDigit(engineering.getLevel().intValue());
    }

    static boolean isPreEngineered(List<? extends ShipModule> potentialShipModules, Engineering engineering) {
        HorizonsBlueprintType blueprint = determineBlueprint(engineering);
        HorizonsBlueprintGrade grade = determineGrade(engineering);
        BigDecimal progression = determineGradeProgress(engineering);
        if (progression == null) {
            return false;
        }
        HorizonsBlueprintType experimentalEffect = determineExperimentalEffect(engineering);
        return potentialShipModules.stream().map(ShipModule::Clone).filter(shipModule -> {
            if (shipModule.getModifications().isEmpty()) {
                shipModule.applyModification(blueprint, grade, progression);
            }
            if (shipModule.getExperimentalEffects().isEmpty()) {
                shipModule.applyExperimentalEffect(experimentalEffect);
            }
            return engineering.getModifiers().stream().allMatch(modification ->
                    modification.getValue().map(value -> {
                        final HorizonsModifier moduleAttribute = HorizonsModifier.forInternalName(modification.getLabel());
                        final Object attributeValue = shipModule.getAttributeValue(moduleAttribute, false);

                        if (attributeValue instanceof Double value2) {
                            return value.setScale(2, RoundingMode.HALF_EVEN).equals(BigDecimal.valueOf(value2).multiply(moduleAttribute.getMultiplier()).setScale(2, RoundingMode.HALF_EVEN));
                        }
                        if (attributeValue instanceof Boolean) {
                            return true;
                        }
                        return false;
                    }).orElse(true));
        }).findFirst().map(ShipModule::isPreEngineered).orElse(false);
    }

    private static boolean isLegacy(ShipModule shipModule, Engineering engineering) {
        return !shipModule.isPreEngineered() && (engineering.getQuality() == null || (engineering.getQuality().doubleValue() == 0.0 && !Origin.GUARDIAN.equals(shipModule.getOrigin())));
    }

    static List<ShipModule> getPotentialShipModules(String module, SlotType slotType) {
        if (slotType.equals(SlotType.CARGO_HATCH)) {
            return new ArrayList<>(ShipModule.getModules(SlotType.CARGO_HATCH));
        }
        return ShipModule.getModules(slotType).stream().filter(shipModule -> shipModule.getInternalName().equalsIgnoreCase(module)).toList();
    }


    public static Slot getShipSlot(Ship ship, String slotName) {
        try {
            if (HARDPOINT_SLOT_NAMES.stream().anyMatch(slotName::contains)) {
                List<ImageSlot> hardpointSlots = ship.getHardpointSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.HARDPOINT)).toList();
                return getHardpointSlot(hardpointSlots, slotName);
            }
            if (MINING_HARDPOINT_SLOT_NAMES.stream().anyMatch(slotName::contains)) {
                List<ImageSlot> hardpointSlots = ship.getHardpointSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.MINING_HARDPOINT)).toList();
                return getHardpointSlot(hardpointSlots, slotName);
            }
            if (UTILITY_SLOT_NAMES.stream().anyMatch(slotName::contains)) {
                return getUtilitySlot(ship.getUtilitySlots(), slotName);
            }
            if (CORE_SLOT_NAMES.stream().anyMatch(slotName::contains)) {
                return getCoreSlot(ship.getCoreSlots(), slotName);
            }
            if (OPTIONAL_SLOT_NAMES.stream().anyMatch(slotName::contains)) {
                final List<Slot> optionalSlots = ship.getOptionalSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.OPTIONAL)).toList();
                return getOptionalSlot(optionalSlots, slotName);
            }
            if (MILITARY_SLOT_NAMES.stream().anyMatch(slotName::contains)) {
                final List<Slot> militarySlots = ship.getOptionalSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.MILITARY)).toList();
                return getRestrictedOptionalSlot(militarySlots, slotName);
            }
            if ("CargoHatch".equals(slotName)) {
                return ship.getCargoHatch();
            }
            if (CARGO_SLOT_NAMES.stream().anyMatch(slotName::startsWith)) {//Cargo
                final List<Slot> cargoSlots = ship.getOptionalSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CARGO)).toList();
                return getRestrictedOptionalSlot(cargoSlots, slotName);
            }
            if (PASSENGER_SLOT_NAMES.stream().anyMatch(slotName::startsWith)) {//Passenger
                final List<Slot> passengerSlots = ship.getOptionalSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.PASSENGER)).toList();
                return getRestrictedOptionalSlot(passengerSlots, slotName);
            }
            if (SLF_SLOT_NAMES.stream().anyMatch(slotName::startsWith)) {//FighterBay
                final List<Slot> slfSlots = ship.getOptionalSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.SLF)).toList();
                return getRestrictedOptionalSlot(slfSlots, slotName);
            }
            if (LIMPET_SLOT_NAMES.stream().anyMatch(slotName::startsWith)) {//Limpet
                final List<Slot> limpetSlots = ship.getOptionalSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.LIMPET)).toList();
                return getRestrictedOptionalSlot(limpetSlots, slotName);
            }
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        }
        log.warn(String.format("Could not determine slot for ship: %s and slotname: %s", ship.getShipType(), slotName));
        return null;
    }

    private static Slot getHardpointSlot(List<ImageSlot> hardpointSlots, String slotName) {
        final int slotNumber = Integer.parseInt(slotName.substring(slotName.length() - 1));
        try {
            return getHardpointSlots(hardpointSlots, slotName).stream()
                    .filter(Slot::hasNamedIndex)
                    .filter(slot -> slot.getNamedIndex() == slotNumber)
                    .findFirst()
                    .orElseGet(() -> getHardpointSlots(hardpointSlots, slotName).get(slotNumber - 1));
        } catch (IllegalArgumentException ex) {
            hardpointSlots.stream().map(Slot::toString).forEach(log::debug);
            throw ex;
        }
    }

    private static List<ImageSlot> getHardpointSlots(List<ImageSlot> hardpointSlots, String slotName) {
        return switch (slotName.substring(0, slotName.length() - 1)) {
            case "SmallHardpoint" -> hardpointSlots.stream()
                    .filter(slot -> slot.getSlotSize() == 1)
                    .filter(slot -> SlotType.HARDPOINT.equals(slot.getSlotType()))
                    .toList();
            case "MediumHardpoint" -> hardpointSlots.stream()
                    .filter(slot -> slot.getSlotSize() == 2)
                    .filter(slot -> SlotType.HARDPOINT.equals(slot.getSlotType()))
                    .toList();
            case "LargeHardpoint" -> hardpointSlots.stream()
                    .filter(slot -> slot.getSlotSize() == 3)
                    .filter(slot -> SlotType.HARDPOINT.equals(slot.getSlotType()))
                    .toList();
            case "HugeHardpoint" -> hardpointSlots.stream()
                    .filter(slot -> slot.getSlotSize() == 4)
                    .filter(slot -> SlotType.HARDPOINT.equals(slot.getSlotType()))
                    .toList();
            case "SmallMiningHardpoint" -> hardpointSlots.stream()
                    .filter(slot -> slot.getSlotSize() == 1)
                    .filter(slot -> SlotType.MINING_HARDPOINT.equals(slot.getSlotType()))
                    .toList();
            case "MediumMiningHardpoint" -> hardpointSlots.stream()
                    .filter(slot -> slot.getSlotSize() == 2)
                    .filter(slot -> SlotType.MINING_HARDPOINT.equals(slot.getSlotType()))
                    .toList();
            case "LargeMiningHardpoint" -> hardpointSlots.stream()
                    .filter(slot -> slot.getSlotSize() == 3)
                    .filter(slot -> SlotType.MINING_HARDPOINT.equals(slot.getSlotType()))
                    .toList();
            default -> throw new IllegalArgumentException("Unexpected value: " + slotName);
        };
    }

    private static Slot getUtilitySlot(List<ImageSlot> utilitySlots, String slotName) {
        final int slotNumber = Integer.parseInt(slotName.substring(slotName.length() - 1));
        return utilitySlots.get(slotNumber - 1);
    }

    private static Slot getOptionalSlot(List<Slot> optionalSlots, String slotName) {
        final int slotNumber = Integer.parseInt(slotName.substring(4, 6));
        try {
            return optionalSlots.stream()
                    .filter(slot -> slot.getNamedIndex() == slotNumber)
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        } catch (IllegalArgumentException ex) {
            optionalSlots.stream().map(slot -> slot.toString()).forEach(log::debug);
            throw ex;
        }
    }

    private static Slot getRestrictedOptionalSlot(List<Slot> restrictedOptionalSlots, String slotName) {
        final int slotNumber = Integer.parseInt(slotName.substring(slotName.length() - 2));
        return restrictedOptionalSlots.get(slotNumber - 1);
    }

    private static Slot getCoreSlot(List<Slot> coreSlots, String slotName) {
        return switch (slotName) {
            case "Armour" -> coreSlots.stream()
                    .filter(slot -> slot.getSlotType().equals(SlotType.CORE_ARMOUR))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
            case "PowerPlant" -> coreSlots.stream()
                    .filter(slot -> slot.getSlotType().equals(SlotType.CORE_POWER_PLANT))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
            case "MainEngines" -> coreSlots.stream()
                    .filter(slot -> slot.getSlotType().equals(SlotType.CORE_THRUSTERS))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
            case "FrameShiftDrive" -> coreSlots.stream()
                    .filter(slot -> slot.getSlotType().equals(SlotType.CORE_FRAME_SHIFT_DRIVE))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
            case "LifeSupport" -> coreSlots.stream()
                    .filter(slot -> slot.getSlotType().equals(SlotType.CORE_LIFE_SUPPORT))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
            case "PowerDistributor" -> coreSlots.stream()
                    .filter(slot -> slot.getSlotType().equals(SlotType.CORE_POWER_DISTRIBUTION))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
            case "Radar" -> coreSlots.stream()
                    .filter(slot -> slot.getSlotType().equals(SlotType.CORE_SENSORS))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
            case "FuelTank" -> coreSlots.stream()
                    .filter(slot -> slot.getSlotType().equals(SlotType.CORE_FUEL_TANK))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
            default -> throw new IllegalArgumentException("Unexpected value: " + slotName);
        };
    }

    public static Loadout toLoadout(ShipConfiguration shipConfiguration) {
        if (shipConfiguration.getShipType() == null) {
            return null;
        }
        final Ship blankShip = ShipService.createBlankShip(shipConfiguration.getShipType());

        // Build config slot lookup by index for each category
        final Map<Integer, ShipConfigurationSlot> coreByIndex = new HashMap<>();
        shipConfiguration.getCoreSlots().forEach(s -> coreByIndex.put(s.getIndex(), s));
        final Map<Integer, ShipConfigurationSlot> hardpointByIndex = new HashMap<>();
        shipConfiguration.getHardpointSlots().forEach(s -> hardpointByIndex.put(s.getIndex(), s));
        final Map<Integer, ShipConfigurationSlot> utilityByIndex = new HashMap<>();
        shipConfiguration.getUtilitySlots().forEach(s -> utilityByIndex.put(s.getIndex(), s));
        final Map<Integer, ShipConfigurationSlot> optionalByIndex = new HashMap<>();
        shipConfiguration.getOptionalSlots().forEach(s -> optionalByIndex.put(s.getIndex(), s));

        final List<nl.jixxed.eliteodysseymaterials.schemas.journal.Loadout.Module> modules = new ArrayList<>();

        // Iterate through blank ship slots (same order as toShip uses)
        for (int i = 0; i < blankShip.getCoreSlots().size(); i++) {
            final Slot blankSlot = blankShip.getCoreSlots().get(i);
            final ShipConfigurationSlot configSlot = coreByIndex.get(i);
            if (configSlot != null && configSlot.getId() != null) {
                final ShipModule module = ShipModule.getModule(configSlot.getId());
                modules.add(buildJournalModule(configSlot, buildSlotNameFromBlank(blankShip.getCoreSlots(), blankSlot, module, Map.of()), module, blankSlot.getSlotType()));
            }
        }

        // Track sequential position per hardpoint size for correct journal slot names
        final Map<Integer, Integer> hardpointSizePosition = new HashMap<>();
        for (int i = 0; i < blankShip.getHardpointSlots().size(); i++) {
            final Slot blankSlot = blankShip.getHardpointSlots().get(i);
            final ShipConfigurationSlot configSlot = hardpointByIndex.get(i);
            if (configSlot != null && configSlot.getId() != null) {
                final ShipModule module = ShipModule.getModule(configSlot.getId());
                modules.add(buildJournalModule(configSlot, buildSlotNameFromBlank(blankShip.getHardpointSlots(), blankSlot, module, hardpointSizePosition), module, blankSlot.getSlotType()));
            }
        }

        for (int i = 0; i < blankShip.getUtilitySlots().size(); i++) {
            final Slot blankSlot = blankShip.getUtilitySlots().get(i);
            final ShipConfigurationSlot configSlot = utilityByIndex.get(i);
            if (configSlot != null && configSlot.getId() != null) {
                final ShipModule module = ShipModule.getModule(configSlot.getId());
                modules.add(buildJournalModule(configSlot, buildSlotNameFromBlank(blankShip.getUtilitySlots(), blankSlot, module, Map.of()), module, blankSlot.getSlotType()));
            }
        }

        for (int i = 0; i < blankShip.getOptionalSlots().size(); i++) {
            final Slot blankSlot = blankShip.getOptionalSlots().get(i);
            final ShipConfigurationSlot configSlot = optionalByIndex.get(i);
            if (configSlot != null && configSlot.getId() != null) {
                final ShipModule module = ShipModule.getModule(configSlot.getId());
                modules.add(buildJournalModule(configSlot, buildSlotNameFromBlank(blankShip.getOptionalSlots(), blankSlot, module, Map.of()), module, blankSlot.getSlotType()));
            }
        }

        final Loadout loadout = new Loadout();
        loadout.setShip(shipConfiguration.getShipType().getInternalName());
        loadout.setShipName(shipConfiguration.getName());
        loadout.setHullHealth(BigDecimal.ONE);
        loadout.setUnladenMass(shipConfiguration.getMass() != null ? BigDecimal.valueOf(shipConfiguration.getMass()) : BigDecimal.ZERO);
        loadout.setMaxJumpRange(shipConfiguration.getJumpRange() != null ? BigDecimal.valueOf(shipConfiguration.getJumpRange()) : BigDecimal.ZERO);

        final FuelCapacity fuelCapacity = new FuelCapacity();
        fuelCapacity.setMain(toBigDecimal(shipConfiguration.getCurrentFuel()));
        fuelCapacity.setReserve(toBigDecimal(shipConfiguration.getCurrentFuelReserve()));
        loadout.setFuelCapacity(fuelCapacity);

        loadout.setRebuy(shipConfiguration.getPrice() != null ? BigInteger.valueOf(shipConfiguration.getPrice()) : BigInteger.ZERO);
        loadout.setModules(modules);
        loadout.setEvent("Loadout");
        loadout.setTimestamp(LocalDateTime.now());

        return loadout;
    }

   private static BigDecimal toBigDecimal(Double value) {
        return value != null ? BigDecimal.valueOf(value) : BigDecimal.ZERO;
    }

   private static nl.jixxed.eliteodysseymaterials.schemas.journal.Loadout.Module buildJournalModule(ShipConfigurationSlot slot, String slotName, ShipModule module, SlotType slotType) {
        final nl.jixxed.eliteodysseymaterials.schemas.journal.Loadout.Module journalModule = new nl.jixxed.eliteodysseymaterials.schemas.journal.Loadout.Module();
        journalModule.setSlot(slotName);
        journalModule.setItem(module != null && module.getInternalName() != null ? module.getInternalName().toLowerCase() : slot.getId());
        journalModule.setOn(slot.getPowered());
        journalModule.setPriority(slot.getPowerGroup() != null ? BigInteger.valueOf(slot.getPowerGroup()) : BigInteger.ONE);
        journalModule.setHealth(BigDecimal.ONE);
        if (slot.getBuyPrice() != null) {
            journalModule.setValue(BigInteger.valueOf(slot.getBuyPrice()));
        }
        final Engineering engineering = buildEngineering(slot, module, slotType);
        if (engineering != null) {
            journalModule.setEngineering(engineering);
        }
        return journalModule;
    }

    private static String buildSlotNameFromBlank(List<? extends Slot> slots, Slot blankSlot, ShipModule module, Map<Integer, Integer> hardpointSizePosition) {
        return switch (blankSlot.getSlotType()) {
            case CORE_ARMOUR -> "Armour";
            case CORE_POWER_PLANT -> "PowerPlant";
            case CORE_THRUSTERS -> "MainEngines";
            case CORE_FRAME_SHIFT_DRIVE -> "FrameShiftDrive";
            case CORE_LIFE_SUPPORT -> "LifeSupport";
            case CORE_POWER_DISTRIBUTION -> "PowerDistributor";
            case CORE_SENSORS -> "Radar";
            case CORE_FUEL_TANK -> "FuelTank";
            case HARDPOINT, MINING_HARDPOINT -> {
                final String size;
                switch (blankSlot.getSlotSize()) {
                    case 1 -> size = "Small";
                    case 2 -> size = "Medium";
                    case 3 -> size = "Large";
                    case 4 -> size = "Huge";
                    default -> throw new IllegalArgumentException("Unexpected slot size: " + blankSlot.getSlotSize());
                }
                final String type = blankSlot.getSlotType() == SlotType.MINING_HARDPOINT ? "Mining" : "";
                final int pos = slots.stream().filter(slot -> slot.getSlotSize() == blankSlot.getSlotSize()).toList().indexOf(blankSlot) + 1;
                yield size + type + "Hardpoint" + pos;
            }
            case UTILITY -> "TinyHardpoint" + blankSlot.getNamedIndex();
            case CARGO -> "Cargo" + String.format("%02d", slots.stream().filter(slot -> slot.getSlotType() == blankSlot.getSlotType()).toList().indexOf(blankSlot) + 1);
            case PASSENGER -> "Passenger" + String.format("%02d", slots.stream().filter(slot -> slot.getSlotType() == blankSlot.getSlotType()).toList().indexOf(blankSlot) + 1);
            case SLF -> "FighterBay" + String.format("%02d", slots.stream().filter(slot -> slot.getSlotType() == blankSlot.getSlotType()).toList().indexOf(blankSlot) + 1);
            case LIMPET -> "LimpetController" + String.format("%02d", slots.stream().filter(slot -> slot.getSlotType() == blankSlot.getSlotType()).toList().indexOf(blankSlot) + 1);
            case MILITARY -> "Military" + String.format("%02d", slots.stream().filter(slot -> slot.getSlotType() == blankSlot.getSlotType()).toList().indexOf(blankSlot) + 1);
            case OPTIONAL -> {
                final String base = "Slot" + String.format("%02d", blankSlot.getNamedIndex());
                final int slotSize = blankSlot.getSlotSize();
                yield base + "_Size" + slotSize;
            }
            default -> throw new IllegalArgumentException("Unexpected slot type: " + blankSlot.getSlotType());
        };
    }

    private static Engineering buildEngineering(ShipConfigurationSlot slot, ShipModule module, SlotType slotType) {
        if (slot.getModification().isEmpty() && slot.getExperimentalEffect().isEmpty() && slot.getModifiers().isEmpty()) {
            return null;
        }

        final Engineering engineering = new Engineering();
        engineering.setModifiers(buildJournalModifiers(slot));

        if (!slot.getModification().isEmpty()) {
            final nl.jixxed.eliteodysseymaterials.domain.ShipConfigurationModification firstMod = slot.getModification().getFirst();
            engineering.setBlueprintName(buildBlueprintName(firstMod.getType(), module));
            engineering.setLevel(BigInteger.valueOf(firstMod.getGrade().getGrade()));
            engineering.setQuality(firstMod.getPercentComplete() != null ? BigDecimal.valueOf(firstMod.getPercentComplete().doubleValue()) : BigDecimal.ZERO);
        } else {
            engineering.setLevel(BigInteger.ONE);
            engineering.setQuality(BigDecimal.ZERO);
        }

        if (!slot.getExperimentalEffect().isEmpty()) {
            HorizonsBlueprintType effect = slot.getExperimentalEffect().getFirst().getType();

            if (effect != null) {
                engineering.setExperimentalEffect(effect.getExperimentalEffectJournalName(module));
            }
        }else if(module.isPreEngineered()) {
            HorizonsBlueprintType effect = module.getPreEngineeredExperimentalEffect();

            if (effect != null) {
                engineering.setExperimentalEffect(effect.getExperimentalEffectJournalName(module));
            }
        }

        return engineering;
    }

  private static String buildBlueprintName(HorizonsBlueprintType type, ShipModule module) {
        return type.getJournalName(module);
    }

    private static List<Modifier> buildJournalModifiers(ShipConfigurationSlot slot) {
        final List<Modifier> modifiers = new ArrayList<>();
        for (final Map.Entry<HorizonsModifier, Object> entry : slot.getModifiers().entrySet()) {
            final HorizonsModifier modifier = entry.getKey();
            final Object value = entry.getValue();
            final Modifier journalModifier = new Modifier();
            journalModifier.setLabel(modifier.getJournalName());
            if (value instanceof Double doubleValue) {
                journalModifier.setValue(BigDecimal.valueOf(doubleValue / modifier.scale(1.0)));
            }
            modifiers.add(journalModifier);
        }
        return modifiers;
    }
}
