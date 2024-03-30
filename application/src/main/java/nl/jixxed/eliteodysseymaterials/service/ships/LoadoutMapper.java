package nl.jixxed.eliteodysseymaterials.service.ships;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Loadout.Engineering;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Loadout.Loadout;
import nl.jixxed.eliteodysseymaterials.service.ReportService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
public class LoadoutMapper {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.registerModule(new Jdk8Module().configureAbsentsAsNulls(true));
    }
    private static final Set<String> HARDPOINT_SLOT_NAMES = Set.of("SmallHardpoint", "MediumHardpoint", "LargeHardpoint", "HugeHardpoint");
    private static final Set<String> UTILITY_SLOT_NAMES = Set.of("TinyHardpoint");
    private static final Set<String> CORE_SLOT_NAMES = Set.of("Armour", "PowerPlant", "MainEngines", "FrameShiftDrive", "LifeSupport", "PowerDistributor", "Radar", "FuelTank");
    private static final Set<String> MILITARY_SLOT_NAMES = Set.of("Military");
    private static final Set<String> OPTIONAL_SLOT_NAMES = Set.of("Slot");
    private final static double EPSILON = 0.00001;

    public static Ship toShip(Loadout loadout) {
        final ShipType shipType = ShipType.forInternalName(loadout.getShip()); // TODO - unknown ship
        final Ship ship = ShipService.createBlankShip(shipType);
        loadout.getModules().forEach(module -> {
            try{
                final String slotName = module.getSlot();
                Slot slot = getShipSlot(ship, slotName);
                if (slot != null) {
                    final List<ShipModule> potentialShipModules = getPotentialShipModules(module.getItem(), slot.getSlotType());
                    final ShipModule matchingModule = module.getEngineering().map(engineering -> {
                        if (!potentialShipModules.isEmpty()) {
                            if (isPreEngineered(potentialShipModules, engineering)) {
                                return potentialShipModules.stream().filter(ShipModule::isPreEngineered).findFirst().orElseThrow(IllegalArgumentException::new);
                            } else {
                                return potentialShipModules.stream().filter(shipModule1 -> !shipModule1.isPreEngineered()).findFirst().orElseThrow(IllegalArgumentException::new);
                            }
                        }
//                    log.debug("Slot: " + module.getSlot());
//                    log.debug("Item: " + module.getItem());
//                    log.debug("Potential modules:" + potentialShipModules.stream().map(shipModule -> shipModule.getName().toString()).collect(Collectors.joining(",")));
                        throw new IllegalArgumentException("No potential modules found");
                    }).orElseGet(() -> {
                        try {
                            return potentialShipModules.stream().filter(shipModule1 -> !shipModule1.isPreEngineered()).findFirst().orElseThrow(() -> new IllegalArgumentException(slotName + "|" + module));
                        } catch (IllegalArgumentException ex) {
                            try {
                                ReportService.reportJournal(OBJECT_MAPPER.writeValueAsString(module), "Can't map module: " + module.getItem());
                            } catch (Exception e) {
                                log.error("failed to send journal error", e);
                            }
                            return null;
                        }
                    });
                    if (matchingModule == null) {
                        return;
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
                    module.getValue().ifPresent(value -> shipModule.setBuyPrice(value.longValue()));
                    shipModule.setPowerGroup(module.getPriority().intValue() + 1);
                    if (Boolean.FALSE.equals(module.getOn())) {
                        shipModule.togglePower();
                    }
                    slot.setShipModule(shipModule);
                    slot.setOldShipModule(shipModule.Clone());

                }
            }catch (IllegalArgumentException ex){
                log.error("Failed to map module: " + module.getItem(), ex);
            }
        });
        return ship;
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
        if(progression == null){
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
                        final Object attributeValue = shipModule.getAttributeValue(moduleAttribute);

                        if (attributeValue instanceof Double value2) {
//                            log.debug(moduleAttribute.name() + ": " + value.setScale(2, RoundingMode.HALF_EVEN) + " =? " + BigDecimal.valueOf(value2).multiply(moduleAttribute.getMultiplier()).setScale(2, RoundingMode.HALF_EVEN));
                            return value.setScale(2, RoundingMode.HALF_EVEN).equals(BigDecimal.valueOf(value2).multiply(moduleAttribute.getMultiplier()).setScale(2, RoundingMode.HALF_EVEN));
                        }
                        if (attributeValue instanceof Boolean) {
//                            log.debug("bool true");
                            return true;
                        }
//                        log.debug("false");
                        return false;
                    }).orElse(true));
        }).findFirst().map(ShipModule::isPreEngineered).orElse(false);
    }

    private static boolean isLegacy(ShipModule shipModule, Engineering engineering) {
        return !shipModule.isPreEngineered() && (engineering.getQuality() == null || engineering.getQuality().doubleValue() == 0.0);
    }

    static List<ShipModule> getPotentialShipModules(String module, SlotType slotType) {
        if (slotType.equals(SlotType.CARGO_HATCH)) {
            return new ArrayList<>(ShipModule.getModules(SlotType.CARGO_HATCH));
        }
        return ShipModule.getModules(slotType).stream().filter(shipModule -> shipModule.getInternalName().equalsIgnoreCase(module)).toList();
    }


    static Slot getShipSlot(Ship ship, String slotName) {
        if (HARDPOINT_SLOT_NAMES.stream().anyMatch(slotName::contains)) {
            return getHardpointSlot(ship.getHardpointSlots(), slotName);
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
            return getMilitarySlot(militarySlots, slotName);
        }
        if ("CargoHatch".equals(slotName)) {
            return ship.getCargoHatch();
        }
        log.warn(String.format("Could not determine slot for ship: %s and slotname: %s", ship.getShipType(), slotName));
        return null;
    }

    private static Slot getHardpointSlot(List<ImageSlot> hardpointSlots, String slotName) {
        final int slotNumber = Integer.parseInt(slotName.substring(slotName.length() - 1));
        return getHardpointSlots(hardpointSlots, slotName).get(slotNumber - 1);
    }

    private static List<ImageSlot> getHardpointSlots(List<ImageSlot> hardpointSlots, String slotName) {
        return switch (slotName.substring(0, slotName.length() - 1)) {
            case "SmallHardpoint" -> hardpointSlots.stream()
                    .filter(slot -> slot.getSlotSize() == 1)
                    .toList();
            case "MediumHardpoint" -> hardpointSlots.stream()
                    .filter(slot -> slot.getSlotSize() == 2)
                    .toList();
            case "LargeHardpoint" -> hardpointSlots.stream()
                    .filter(slot -> slot.getSlotSize() == 3)
                    .toList();
            case "HugeHardpoint" -> hardpointSlots.stream()
                    .filter(slot -> slot.getSlotSize() == 4)
                    .toList();
            default -> throw new IllegalStateException("Unexpected value: " + slotName);
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
//            log.debug(slotName);
//            log.debug(slotNumber + "not in size:" + optionalSlots.size());
            throw ex;
        }
    }

    private static Slot getMilitarySlot(List<Slot> militarySlots, String slotName) {
        final int slotNumber = Integer.parseInt(slotName.substring(slotName.length() - 2));
        return militarySlots.get(slotNumber - 1);
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
            default -> throw new IllegalStateException("Unexpected value: " + slotName);
        };
    }
}
