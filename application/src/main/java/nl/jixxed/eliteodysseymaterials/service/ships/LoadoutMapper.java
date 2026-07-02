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

import com.fasterxml.jackson.core.JsonProcessingException;
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
import nl.jixxed.eliteodysseymaterials.schemas.journal.Loadout.Module;
import nl.jixxed.eliteodysseymaterials.service.ReportService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class LoadoutMapper {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
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

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.registerModule(new Jdk8Module().configureAbsentsAsNulls(true));
    }

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
                            if (isPreEngineered(potentialShipModules, engineering, module)) {
                                return potentialShipModules.stream().filter(ShipModule::isPreEngineered).filter(shipModule -> matchingEngineering(shipModule, engineering, module)).findFirst().orElseThrow(IllegalArgumentException::new);
                            }if (isMerc(potentialShipModules, engineering, module)) {
                                return potentialShipModules.stream().filter(ShipModule::isMerc).filter(shipModule -> matchingMercEngineering(shipModule, engineering, module)).findFirst().orElseThrow(IllegalArgumentException::new);
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
                        HorizonsBlueprintType blueprint = determineBlueprint(engineering, module);
                        HorizonsBlueprintGrade grade = determineGrade(engineering);
                        BigDecimal progression = determineGradeProgress(engineering);
                        if (isLegacy) {
                            shipModule.setLegacy(true);
                        }
                        HorizonsBlueprintType experimentalEffect = determineExperimentalEffect(engineering, module);
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

    private static boolean isMerc(List<ShipModule> potentialShipModules, Engineering engineering, Module module) {
        HorizonsBlueprintType blueprint = determineBlueprint(engineering, module);
        return blueprint.isMerc();
    }

    public static boolean matchingEngineering(ShipModule shipModule, Engineering engineering, Module module) {
        if (shipModule.getPreEngineeredMatchType().equals(MatchType.BLUEPRINT)) {
            return shipModule.getModifications().stream().anyMatch(modification -> modification.getModification().equals(HorizonsBlueprintType.forInternalName(engineering.getBlueprintName())))
                    && engineering.getExperimentalEffect().map(experimentalEffect -> shipModule.getExperimentalEffects().stream().anyMatch(effect -> effect.equals(HorizonsBlueprintType.forInternalName(experimentalEffect)))).orElse(true);
        }
        return isPreEngineered(List.of(shipModule), engineering, module);
    }
    public static boolean matchingMercEngineering(ShipModule shipModule, Engineering engineering, Module module) {
        return shipModule.getModifications().stream().anyMatch(modification -> modification.getModification().equals(HorizonsBlueprintType.forInternalName(engineering.getBlueprintName())));
    }

    private static HorizonsBlueprintType determineExperimentalEffect(Engineering engineering, Module module) {
        try {
            return engineering.getExperimentalEffect().map(HorizonsBlueprintType::forInternalName).orElse(null);
        } catch (IllegalArgumentException ex) {
            try {
                ReportService.reportJournal("module", OBJECT_MAPPER.writeValueAsString(module), "Failed to map experimental effect: " + engineering.getExperimentalEffect().orElse(""));
            } catch (JsonProcessingException e) {
               //ignore
            }
            throw ex;
        }
    }

    private static HorizonsBlueprintType determineBlueprint(Engineering engineering, Module module) {
        try {
            return HorizonsBlueprintType.forInternalName(engineering.getBlueprintName());

        } catch (IllegalArgumentException ex) {
            try {
                ReportService.reportJournal("module", OBJECT_MAPPER.writeValueAsString(module), "Failed to map blueprint: " + engineering.getBlueprintName());
            } catch (JsonProcessingException e) {
                //ignore
            }
            throw ex;
        }
    }

    private static BigDecimal determineGradeProgress(Engineering engineering) {
        return engineering.getQuality();
    }

    private static HorizonsBlueprintGrade determineGrade(Engineering engineering) {
        return HorizonsBlueprintGrade.forDigit(engineering.getLevel().intValue());
    }

    static boolean isPreEngineered(List<? extends ShipModule> potentialShipModules, Engineering engineering, Module module) {
        HorizonsBlueprintType blueprint = determineBlueprint(engineering, module);
        HorizonsBlueprintGrade grade = determineGrade(engineering);
        BigDecimal progression = determineGradeProgress(engineering);
        if (progression == null) {
            return false;
        }
        HorizonsBlueprintType experimentalEffect = determineExperimentalEffect(engineering, module);
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
        return !shipModule.isMerc() &&!shipModule.isPreEngineered() && (engineering.getQuality() == null || (engineering.getQuality().doubleValue() == 0.0 && !Origin.GUARDIAN.equals(shipModule.getOrigin())));
    }

    static List<ShipModule> getPotentialShipModules(String module, SlotType slotType) {
        if (slotType.equals(SlotType.CARGO_HATCH)) {
            return new ArrayList<>(ShipModule.getModules(SlotType.CARGO_HATCH));
        }
        return ShipModule.getModules(slotType).stream().filter(shipModule -> shipModule.getInternalName().equalsIgnoreCase(module)).toList();
    }


    public static Slot getShipSlot(Ship ship, String slotName) {
        try {
            if ("CargoHatch".equals(slotName)) {
                return ship.getCargoHatch();
            }
            if (HARDPOINT_SLOT_NAMES.stream().anyMatch(slotName::contains) || MINING_HARDPOINT_SLOT_NAMES.stream().anyMatch(slotName::contains)) {
                return getSlot(ship.getHardpointSlots(), slotName);
            }
            if (UTILITY_SLOT_NAMES.stream().anyMatch(slotName::contains)) {
                return getSlot(ship.getUtilitySlots(), slotName);
            }
            if (CORE_SLOT_NAMES.stream().anyMatch(slotName::contains)) {
                return getSlot(ship.getCoreSlots(), slotName);
            }
            if (OPTIONAL_SLOT_NAMES.stream().anyMatch(slotName::contains)
                    || MILITARY_SLOT_NAMES.stream().anyMatch(slotName::contains)
                    || CARGO_SLOT_NAMES.stream().anyMatch(slotName::startsWith)
                    || PASSENGER_SLOT_NAMES.stream().anyMatch(slotName::startsWith)
                    || SLF_SLOT_NAMES.stream().anyMatch(slotName::startsWith)
                    || LIMPET_SLOT_NAMES.stream().anyMatch(slotName::startsWith)) {//Limpet
                return getSlot(ship.getOptionalSlots(), slotName);
            }
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        }
        log.warn(String.format("Could not determine slot for ship: %s and slotname: %s", ship.getShipType(), slotName));
        return null;
    }

    private static Slot getSlot(List<? extends Slot> slots, String slotName) {
        return slots.stream()
                .filter(slot -> slot.matches(slotName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
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

        final List<Module> modules = new ArrayList<>();

        // Iterate through blank ship slots (same order as toShip uses)
        for (int i = 0; i < blankShip.getCoreSlots().size(); i++) {
            final Slot blankSlot = blankShip.getCoreSlots().get(i);
            final ShipConfigurationSlot configSlot = coreByIndex.get(i);
            if (configSlot != null && configSlot.getId() != null) {
                final ShipModule module = ShipModule.getModule(configSlot.getId()).Clone();
                if(!configSlot.getModification().isEmpty()) {
                    module.applyModification(configSlot.getModification().getFirst().getType(), configSlot.getModification().getFirst().getGrade(), configSlot.getModification().getFirst().getPercentComplete());
                }
                if(configSlot.getFirstExperimentalEffect() != null) {
                    module.applyExperimentalEffect(configSlot.getFirstExperimentalEffect().getType());
                }
                modules.add(buildJournalModule(configSlot, buildSlotNameFromBlank(blankShip.getCoreSlots(), blankSlot, module, Map.of()), module, blankSlot.getSlotType()));
            }
        }

        // Track sequential position per hardpoint size for correct journal slot names
        final Map<Integer, Integer> hardpointSizePosition = new HashMap<>();
        for (int i = 0; i < blankShip.getHardpointSlots().size(); i++) {
            final Slot blankSlot = blankShip.getHardpointSlots().get(i);
            final ShipConfigurationSlot configSlot = hardpointByIndex.get(i);
            if (configSlot != null && configSlot.getId() != null) {
                final ShipModule module = ShipModule.getModule(configSlot.getId()).Clone();
                if(!configSlot.getModification().isEmpty()) {
                    module.applyModification(configSlot.getModification().getFirst().getType(), configSlot.getModification().getFirst().getGrade(), configSlot.getModification().getFirst().getPercentComplete());
                }
                if(configSlot.getFirstExperimentalEffect() != null) {
                    module.applyExperimentalEffect(configSlot.getFirstExperimentalEffect().getType());
                }
                modules.add(buildJournalModule(configSlot, buildSlotNameFromBlank(blankShip.getHardpointSlots(), blankSlot, module, hardpointSizePosition), module, blankSlot.getSlotType()));
            }
        }

        for (int i = 0; i < blankShip.getUtilitySlots().size(); i++) {
            final Slot blankSlot = blankShip.getUtilitySlots().get(i);
            final ShipConfigurationSlot configSlot = utilityByIndex.get(i);
            if (configSlot != null && configSlot.getId() != null) {
                final ShipModule module = ShipModule.getModule(configSlot.getId()).Clone();
                if(!configSlot.getModification().isEmpty()) {
                    module.applyModification(configSlot.getModification().getFirst().getType(), configSlot.getModification().getFirst().getGrade(), configSlot.getModification().getFirst().getPercentComplete());
                }
                if(configSlot.getFirstExperimentalEffect() != null) {
                    module.applyExperimentalEffect(configSlot.getFirstExperimentalEffect().getType());
                }
                modules.add(buildJournalModule(configSlot, buildSlotNameFromBlank(blankShip.getUtilitySlots(), blankSlot, module, Map.of()), module, blankSlot.getSlotType()));
            }
        }

        for (int i = 0; i < blankShip.getOptionalSlots().size(); i++) {
            final Slot blankSlot = blankShip.getOptionalSlots().get(i);
            final ShipConfigurationSlot configSlot = optionalByIndex.get(i);
            if (configSlot != null && configSlot.getId() != null) {
                final ShipModule module = ShipModule.getModule(configSlot.getId()).Clone();
                if(!configSlot.getModification().isEmpty()) {
                    module.applyModification(configSlot.getModification().getFirst().getType(), configSlot.getModification().getFirst().getGrade(), configSlot.getModification().getFirst().getPercentComplete());
                }
                if(configSlot.getFirstExperimentalEffect() != null) {
                    module.applyExperimentalEffect(configSlot.getFirstExperimentalEffect().getType());
                }
                modules.add(buildJournalModule(configSlot, buildSlotNameFromBlank(blankShip.getOptionalSlots(), blankSlot, module, Map.of()), module, blankSlot.getSlotType()));
            }
        }

        final Loadout loadout = new Loadout();
        loadout.setShip(shipConfiguration.getShipType().getInternalName());
        loadout.setShipName(shipConfiguration.getName());
        loadout.setHullHealth(BigDecimal.ONE);
        loadout.setUnladenMass(shipConfiguration.getMass() != null ? BigDecimal.valueOf(shipConfiguration.getMass() - shipConfiguration.getCurrentFuel() - shipConfiguration.getCurrentFuelReserve()) : BigDecimal.ZERO);
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

    private static Module buildJournalModule(ShipConfigurationSlot slot, String slotName, ShipModule module, SlotType slotType) {
        final Module journalModule = new Module();
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
        return blankSlot.getFdevName();
    }

    private static Engineering buildEngineering(ShipConfigurationSlot slot, ShipModule module, SlotType slotType) {
        if (slot.getModification().isEmpty() && slot.getExperimentalEffect().isEmpty() && slot.getModifiers().isEmpty()) {
            return null;
        }

        final Engineering engineering = new Engineering();
        engineering.setModifiers(buildJournalModifiers(slot ,module));

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
        } else if (module.isPreEngineered()) {
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

    private static List<Modifier> buildJournalModifiers(ShipConfigurationSlot slot, ShipModule shipModule) {

        Set<HorizonsModifier> moduleModifiers =  shipModule.getAttibutes().stream().filter(attribute -> !attribute.getJournalName().equals("")).collect(Collectors.toSet());
        final List<Modifier> modifiers = new ArrayList<>();
        for (final HorizonsModifier modifier : moduleModifiers) {
            final Object value = shipModule.getAttributeValue(modifier, false);
            final Modifier journalModifier = new Modifier();
            journalModifier.setLabel(modifier.getJournalName());
            if (value instanceof Double doubleValue) {
                BigDecimal originalValue = BigDecimal.valueOf((Double) shipModule.getOriginalAttributeValue(modifier));
                BigDecimal modifiedValue = BigDecimal.valueOf(doubleValue / modifier.scale(1.0));
                journalModifier.setOriginalValue(originalValue);
                journalModifier.setValue(modifiedValue);
                if(!originalValue.equals(modifiedValue)) {
                    modifiers.add(journalModifier);
                }
            }
        }
        return modifiers;
    }
}
