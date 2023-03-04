package nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("java:S1192")
public class MineLauncherBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE, HorizonsBlueprintGrade.GRADE_1,
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
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE, HorizonsBlueprintGrade.GRADE_2,
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
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE, HorizonsBlueprintGrade.GRADE_3,
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
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE, HorizonsBlueprintGrade.GRADE_4,
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
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE, HorizonsBlueprintGrade.GRADE_5,
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
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK))),
            HorizonsBlueprintType.LIGHTWEIGHT_MOUNT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-20%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-30%", true)
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_2,
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
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.LIZ_RYDER, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_3,
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
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.LIZ_RYDER, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_4,
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
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.PROTORADIOLICALLOYS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-40%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-60%", false),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsModifierValue("-35%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-90%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK))),
            HorizonsBlueprintType.RAPID_FIRE_MODIFICATION,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.RAPID_FIRE_MODIFICATION, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsModifierValue("-1%", false),
                                    HorizonsModifier.RELOAD_TIME, new HorizonsModifierValue("-25%", true),
                                    HorizonsModifier.JITTER, new HorizonsModifierValue("0,5", false),
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("+8%", true)
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.RAPID_FIRE_MODIFICATION, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.HEATDISPERSIONPLATE, 1,
                                    Manufactured.MECHANICALSCRAP, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsModifierValue("-2%", false),
                                    HorizonsModifier.RELOAD_TIME, new HorizonsModifierValue("-35%", true),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsModifierValue("-5%", true),
                                    HorizonsModifier.JITTER, new HorizonsModifierValue("0,5", false),
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("+17%", true)
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.RAPID_FIRE_MODIFICATION, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.MECHANICALEQUIPMENT, 1,
                                    Manufactured.PRECIPITATEDALLOYS, 1,
                                    Encoded.LEGACYFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsModifierValue("-3%", false),
                                    HorizonsModifier.RELOAD_TIME, new HorizonsModifierValue("-45%", true),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsModifierValue("-15%", true),
                                    HorizonsModifier.JITTER, new HorizonsModifierValue("0,5", false),
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("+26%", true)
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.RAPID_FIRE_MODIFICATION, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.MECHANICALCOMPONENTS, 1,
                                    Encoded.CONSUMERFIRMWARE, 1,
                                    Manufactured.THERMICALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.RELOAD_TIME, new HorizonsModifierValue("-55%", true),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsModifierValue("-25%", true),
                                    HorizonsModifier.JITTER, new HorizonsModifierValue("0,5", false),
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("+35%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.RAPID_FIRE_MODIFICATION, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CONFIGURABLECOMPONENTS, 1,
                                    Raw.TECHNETIUM, 1,
                                    Manufactured.PRECIPITATEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsModifierValue("-5%", false),
                                    HorizonsModifier.RELOAD_TIME, new HorizonsModifierValue("-65%", true),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsModifierValue("-35%", true),
                                    HorizonsModifier.JITTER, new HorizonsModifierValue("0,5", false),
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("+44%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK))),
            HorizonsBlueprintType.STURDY_MOUNT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+100%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-10%", true),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsModifierValue("+20%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+20%", false)
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_2,
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
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_3,
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
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_4,
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
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_5,
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
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK))));
}
