package nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints;

import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

public class RailGunBlueprints {
    public static final Map<HorizonsBlueprintModificationType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintModificationType.HIGH_CAPACITY_MAGAZINE,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.HIGH_CAPACITY_MAGAZINE, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1
                            ),
                            Map.of(
                                    HorizonsModifier.CLIP_SIZE, new HorizonsModifierValue("+36%", true),
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+4%", false),
                                    HorizonsModifier.AMMO_CAPACITY, new HorizonsModifierValue("+36%", true),
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("+2%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+20%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.HIGH_CAPACITY_MAGAZINE, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.CLIP_SIZE, new HorizonsModifierValue("+52%", true),
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+8%", false),
                                    HorizonsModifier.AMMO_CAPACITY, new HorizonsModifierValue("+52%", true),
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("+4%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+30%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.HIGH_CAPACITY_MAGAZINE, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1,
                                    Raw.NIOBIUM, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.CLIP_SIZE, new HorizonsModifierValue("+68%", true),
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+12%", false),
                                    HorizonsModifier.AMMO_CAPACITY, new HorizonsModifierValue("+68%", true),
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("+6%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+40%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.HIGH_CAPACITY_MAGAZINE, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Manufactured.MECHANICALEQUIPMENT, 1,
                                    Raw.TIN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.CLIP_SIZE, new HorizonsModifierValue("+84%", true),
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+16%", false),
                                    HorizonsModifier.AMMO_CAPACITY, new HorizonsModifierValue("+84%", true),
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("+8%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+50%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.HIGH_CAPACITY_MAGAZINE, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.MECHANICALCOMPONENTS, 1,
                                    Manufactured.MILITARYSUPERCAPACITORS, 1,
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.CLIP_SIZE, new HorizonsModifierValue("+100%", true),
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+20%", false),
                                    HorizonsModifier.AMMO_CAPACITY, new HorizonsModifierValue("+100%", true),
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("+10%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+60%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN))),
            HorizonsBlueprintModificationType.LIGHTWEIGHT_MOUNT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-20%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-30%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-10%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-30%", false),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsModifierValue("-20%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-45%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-20%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-40%", false),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsModifierValue("-25%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-60%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.PHASEALLOYS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-30%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-50%", false),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsModifierValue("-30%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-75%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1,
                                    Manufactured.PROTORADIOLICALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-40%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-60%", false),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsModifierValue("-35%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-90%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN))),
            HorizonsBlueprintModificationType.LONG_RANGE_WEAPON,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.LONG_RANGE_WEAPON, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+3%", false),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsModifierValue("+20%", true),
                                    HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsModifierValue("+20%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+10%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.LONG_RANGE_WEAPON, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Encoded.CONSUMERFIRMWARE, 1,
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+6%", false),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsModifierValue("+40%", true),
                                    HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsModifierValue("+40%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+15%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.LONG_RANGE_WEAPON, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.FOCUSCRYSTALS, 1,
                                    Encoded.CONSUMERFIRMWARE, 1,
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+9%", false),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsModifierValue("+60%", true),
                                    HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsModifierValue("+60%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+20%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.LONG_RANGE_WEAPON, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CONDUCTIVEPOLYMERS, 1,
                                    Manufactured.FOCUSCRYSTALS, 1,
                                    Encoded.CONSUMERFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+12%", false),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsModifierValue("+80%", true),
                                    HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsModifierValue("+80%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+25%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.LONG_RANGE_WEAPON, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.BIOTECHCONDUCTORS, 1,
                                    Manufactured.THERMICALLOYS, 1,
                                    Encoded.INDUSTRIALFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+15%", false),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsModifierValue("+100%", true),
                                    HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsModifierValue("+100%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+30%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN))),
            HorizonsBlueprintModificationType.SHORT_RANGE_BLASTER,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.SHORT_RANGE_BLASTER, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsModifierValue("+27%", true),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsModifierValue("-10%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.SHORT_RANGE_BLASTER, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Encoded.CONSUMERFIRMWARE, 1,
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsModifierValue("+39%", true),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsModifierValue("-20%", false),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+10%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.SHORT_RANGE_BLASTER, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.ELECTROCHEMICALARRAYS, 1,
                                    Encoded.CONSUMERFIRMWARE, 1,
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsModifierValue("+51%", true),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsModifierValue("-30%", false),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+20%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.SHORT_RANGE_BLASTER, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CONDUCTIVEPOLYMERS, 1,
                                    Manufactured.ELECTROCHEMICALARRAYS, 1,
                                    Encoded.CONSUMERFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsModifierValue("+63%", true),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsModifierValue("-40%", false),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+30%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.SHORT_RANGE_BLASTER, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.BIOTECHCONDUCTORS, 1,
                                    Manufactured.CONFIGURABLECOMPONENTS, 1,
                                    Encoded.INDUSTRIALFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsModifierValue("+75%", true),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsModifierValue("-50%", false),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+40%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN))),
            HorizonsBlueprintModificationType.STURDY_MOUNT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+100%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-10%", true),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsModifierValue("+20%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+20%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+150%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-15%", true),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsModifierValue("+30%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+40%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.SHIELDEMITTERS, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+200%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-20%", true),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsModifierValue("+40%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+60%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.MOLYBDENUM, 1,
                                    Raw.TUNGSTEN, 1,
                                    Raw.ZINC, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+250%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-25%", true),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsModifierValue("+50%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+80%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintObjectName.RAIL_GUN, HorizonsBlueprintModificationType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Raw.MOLYBDENUM, 1,
                                    Raw.TECHNETIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+300%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-30%", true),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsModifierValue("+60%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+100%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN))));
}
