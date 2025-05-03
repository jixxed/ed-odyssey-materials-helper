package nl.jixxed.eliteodysseymaterials.constants.horizons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.UTF8Constants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsNumberModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("java:S1192")
public class SynthesisBlueprints {
    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> GUARDIAN_PLASMA_CHARGER_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_PLASMA_CHARGER_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.CHROMIUM, 3,
                            Manufactured.HEATDISPERSIONPLATE, 2,
                            Manufactured.GUARDIAN_POWERCONDUIT, 3,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 4),
                    Map.of(),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_PLASMA_CHARGER_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.CHROMIUM, 4,
                            Manufactured.HEATEXCHANGERS, 2,
                            Manufactured.PHASEALLOYS, 2,
                            Manufactured.GUARDIAN_POWERCELL, 2,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 2),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_PLASMA_CHARGER_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.CHROMIUM, 6,
                            Raw.ZIRCONIUM, 2,
                            Manufactured.HEATEXCHANGERS, 4,
                            Manufactured.PHASEALLOYS, 2,
                            Manufactured.GUARDIAN_POWERCELL, 2,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 3),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.30))),
                    List.of()));
    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> GUARDIAN_NANITE_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_NANITE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Manufactured.GUARDIAN_POWERCELL, 2,
                            Manufactured.TG_ABRASION01, 5,
                            Manufactured.TG_ABRASION02, 5),
                    Map.of(),
                    List.of(),
                    GameVersion.LIVE));
    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> GUARDIAN_GAUSS_CANNON_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_GAUSS_CANNON_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.MANGANESE, 3,
                            Manufactured.FOCUSCRYSTALS, 2,
                            Manufactured.GUARDIAN_POWERCONDUIT, 3,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 4),
                    Map.of(),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_GAUSS_CANNON_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.MANGANESE, 5,
                            Manufactured.HEATRESISTANTCERAMICS, 3,
                            Manufactured.FOCUSCRYSTALS, 5,
                            Manufactured.GUARDIAN_POWERCONDUIT, 4,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 3),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_GAUSS_CANNON_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.MANGANESE, 8,
                            Manufactured.FILAMENTCOMPOSITES, 6,
                            Manufactured.FOCUSCRYSTALS, 10,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 6),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.30))),
                    List.of()));
    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> AX_SMALL_CALIBRE_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.AX_SMALL_CALIBRE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.IRON, 2,
                            Raw.NICKEL, 1,
                            Raw.SULPHUR, 2,
                            Manufactured.TG_WEAPONPARTS, 2),
                    Map.of(),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.AX_SMALL_CALIBRE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.IRON, 2,
                            Raw.PHOSPHORUS, 2,
                            Raw.ZIRCONIUM, 2,
                            Manufactured.UNKNOWNENERGYSOURCE, 3,
                            Manufactured.TG_WEAPONPARTS, 4),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+5%", true, percentagePositive(0.0, 0.05))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.AX_SMALL_CALIBRE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.IRON, 3,
                            Raw.PHOSPHORUS, 2,
                            Raw.ZIRCONIUM, 2,
                            Manufactured.UNKNOWNENERGYSOURCE, 4,
                            Manufactured.UNKNOWNCARAPACE, 2,
                            Manufactured.TG_WEAPONPARTS, 6),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+10%", true, percentagePositive(0.0, 0.10))),
                    List.of()));
    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> AX_REMOTE_FLAK_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.AX_REMOTE_FLAK_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.NICKEL, 4,
                            Raw.CARBON, 3,
                            Raw.SULPHUR, 2),
                    Map.of(),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.AX_REMOTE_FLAK_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.TIN, 2,
                            Raw.ZINC, 3,
                            Raw.ARSENIC, 1,
                            Manufactured.UNKNOWNTECHNOLOGYCOMPONENTS, 3,
                            Manufactured.TG_WRECKAGECOMPONENTS, 2),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+5%", true, percentagePositive(0.0, 0.05))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.AX_REMOTE_FLAK_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.ZINC, 8,
                            Raw.TUNGSTEN, 2,
                            Raw.ARSENIC, 1,
                            Manufactured.UNKNOWNENERGYSOURCE, 3,
                            Manufactured.UNKNOWNTECHNOLOGYCOMPONENTS, 4,
                            Manufactured.TG_WEAPONPARTS, 1),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+10%", true, percentagePositive(0.0, 0.10))),
                    List.of()));
    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> ENZYME_MISSILE_LAUNCHER_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.ENZYME_MISSILE_LAUNCHER_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.IRON, 3,
                            Raw.SULPHUR, 3,
                            Manufactured.TG_BIOMECHANICALCONDUITS, 4,
                            Manufactured.TG_PROPULSIONELEMENT, 3,
                            Manufactured.TG_WEAPONPARTS, 3,
                            Raw.LEAD, 2),
                    Map.of(),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.ENZYME_MISSILE_LAUNCHER_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.SULPHUR, 6,
                            Raw.TUNGSTEN, 4,
                            Manufactured.TG_BIOMECHANICALCONDUITS, 5,
                            Manufactured.TG_PROPULSIONELEMENT, 6,
                            Manufactured.TG_WEAPONPARTS, 4,
                            Raw.LEAD, 4),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.ENZYME_MISSILE_LAUNCHER_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.PHOSPHORUS, 5,
                            Raw.TUNGSTEN, 4,
                            Manufactured.TG_BIOMECHANICALCONDUITS, 6,
                            Manufactured.TG_PROPULSIONELEMENT, 6,
                            Manufactured.TG_WEAPONPARTS, 5,
                            Raw.LEAD, 6),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.30))),
                    List.of()));
    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> FLECHETTE_LAUNCHER_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.FLECHETTE_LAUNCHER_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.TUNGSTEN, 1,
                            Manufactured.ELECTROCHEMICALARRAYS, 3,
                            Manufactured.MECHANICALCOMPONENTS, 2,
                            Raw.BORON, 2),
                    Map.of(),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.FLECHETTE_LAUNCHER_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.TUNGSTEN, 4,
                            Manufactured.ELECTROCHEMICALARRAYS, 6,
                            Manufactured.MECHANICALCOMPONENTS, 4,
                            Raw.BORON, 4),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+5%", true, percentagePositive(0.0, 0.05))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.FLECHETTE_LAUNCHER_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.TUNGSTEN, 6,
                            Manufactured.ELECTROCHEMICALARRAYS, 9,
                            Manufactured.MECHANICALCOMPONENTS, 5,
                            Raw.BORON, 6),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+10%", true, percentagePositive(0.0, 0.10))),
                    List.of()));
    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> GUARDIAN_SHARD_CANNON_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_SHARD_CANNON_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.CARBON, 3,
                            Raw.VANADIUM, 2,
                            Manufactured.CRYSTALSHARDS, 3,
                            Manufactured.GUARDIAN_POWERCELL, 3,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 5),
                    Map.of(),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_SHARD_CANNON_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Manufactured.CRYSTALSHARDS, 4,
                            Manufactured.GUARDIAN_POWERCELL, 2,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 2),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_SHARD_CANNON_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.CARBON, 8,
                            Manufactured.GUARDIAN_POWERCELL, 6,
                            Raw.VANADIUM, 4,
                            Manufactured.CRYSTALSHARDS, 8),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.30))),
                    List.of()));
    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> AX_EXPLOSIVE_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.AX_EXPLOSIVE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.CARBON, 4,
                            Raw.IRON, 3,
                            Raw.NICKEL, 3,
                            Manufactured.TG_PROPULSIONELEMENT, 3),
                    Map.of(),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.AX_EXPLOSIVE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.SULPHUR, 6,
                            Raw.PHOSPHORUS, 6,
                            Raw.MERCURY, 2,
                            Manufactured.TG_PROPULSIONELEMENT, 4,
                            Manufactured.UNKNOWNORGANICCIRCUITRY, 4),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+5%", true, percentagePositive(0.0, 0.05))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.AX_EXPLOSIVE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.TUNGSTEN, 5,
                            Raw.MERCURY, 4,
                            Manufactured.TG_BIOMECHANICALCONDUITS, 5,
                            Raw.POLONIUM, 2,
                            Manufactured.TG_PROPULSIONELEMENT, 5,
                            Encoded.TG_SHIPFLIGHTDATA, 6),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+10%", true, percentagePositive(0.0, 0.10))),
                    List.of()));
    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> LIFE_SUPPORT_SYNTHESIS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.LIFE_SUPPORT_SYNTHESIS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.IRON, 2,
                            Raw.NICKEL, 1),
                    Map.of(
                            HorizonsModifier.REFILL, new HorizonsNumberModifierValue("100%", true, percentagePositive(0.0, 1.0))),
                    List.of()));
    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> LIMPETS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.LIMPETS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.IRON, 10,
                            Raw.NICKEL, 10),
                    Map.of(
                            HorizonsModifier.LIMPETS, new HorizonsNumberModifierValue("+4", true, percentagePositive(0.0, 0.04))),
                    List.of()));
    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> CHAFF = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.CHAFF, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Manufactured.COMPACTCOMPOSITES, 1,
                            Manufactured.FILAMENTCOMPOSITES, 1),
                    Map.of(
                            HorizonsModifier.REFILL, new HorizonsNumberModifierValue("50%", true, percentagePositive(0.0, 0.50))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.CHAFF, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Manufactured.COMPACTCOMPOSITES, 1,
                            Manufactured.FILAMENTCOMPOSITES, 2,
                            Manufactured.THERMICALLOYS, 1),
                    Map.of(
                            HorizonsModifier.REFILL, new HorizonsNumberModifierValue("100%", true, percentagePositive(0.0, 1.0))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.CHAFF, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Manufactured.COMPACTCOMPOSITES, 1,
                            Manufactured.FILAMENTCOMPOSITES, 2,
                            Manufactured.THERMICALLOYS, 2,
                            Manufactured.PROTORADIOLICALLOYS, 1),
                    Map.of(
                            HorizonsModifier.REFILL, new HorizonsNumberModifierValue("100%", true, percentagePositive(0.0, 1.0)),
                            HorizonsModifier.ECM_TIME_TO_CHARGE, new HorizonsNumberModifierValue("+2s", true, plus(2.0))),
                    List.of()));
    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> HEATSINKS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.HEATSINKS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Manufactured.BASICCONDUCTORS, 2,
                            Manufactured.HEATCONDUCTIONWIRING, 2),
                    Map.of(
                            HorizonsModifier.REFILL, new HorizonsNumberModifierValue("100%", true, percentagePositive(0.0, 1.0))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.HEATSINKS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Manufactured.BASICCONDUCTORS, 2,
                            Manufactured.HEATCONDUCTIONWIRING, 2,
                            Manufactured.HEATEXCHANGERS, 2),
                    Map.of(
                            HorizonsModifier.REFILL, new HorizonsNumberModifierValue("100%", true, percentagePositive(0.0, 1.0)),
                            HorizonsModifier.HEAT_DISSIPATION, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.HEATSINKS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Manufactured.BASICCONDUCTORS, 2,
                            Manufactured.HEATCONDUCTIONWIRING, 2,
                            Manufactured.HEATEXCHANGERS, 2,
                            Manufactured.PROTOHEATRADIATORS, 1),
                    Map.of(
                            HorizonsModifier.REFILL, new HorizonsNumberModifierValue("100%", true, percentagePositive(0.0, 1.0)),
                            HorizonsModifier.HEAT_DISSIPATION, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.3))),
                    List.of()));
    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> FSD_INJECTION = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.FSD_INJECTION, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.CARBON, 1,
                            Raw.VANADIUM, 1,
                            Raw.GERMANIUM, 1),
                    Map.of(
                            HorizonsModifier.JUMP_RANGE, new HorizonsNumberModifierValue("+25%", true, percentagePositive(0.0, 0.25))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.FSD_INJECTION, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.CARBON, 1,
                            Raw.VANADIUM, 1,
                            Raw.GERMANIUM, 1,
                            Raw.CADMIUM, 1,
                            Raw.NIOBIUM, 1),
                    Map.of(
                            HorizonsModifier.JUMP_RANGE, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.0, 0.50))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.FSD_INJECTION, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.CARBON, 1,
                            Raw.GERMANIUM, 1,
                            Raw.ARSENIC, 1,
                            Raw.NIOBIUM, 1,
                            Raw.YTTRIUM, 1,
                            Raw.POLONIUM, 1),
                    Map.of(
                            HorizonsModifier.JUMP_RANGE, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0))),
                    List.of()));
    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> PLASMA_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.PLASMA_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.SULPHUR, 3,
                            Raw.PHOSPHORUS, 4,
                            Raw.MANGANESE, 1),
                    Map.of(),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.PLASMA_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.PHOSPHORUS, 5,
                            Raw.MANGANESE, 3,
                            Raw.SELENIUM, 1,
                            Raw.MOLYBDENUM, 4),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.PLASMA_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.SELENIUM, 5,
                            Raw.CADMIUM, 4,
                            Raw.MOLYBDENUM, 4,
                            Raw.TECHNETIUM, 2),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.3))),
                    List.of()));
    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> EXPLOSIVE_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.EXPLOSIVE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.IRON, 3,
                            Raw.NICKEL, 3,
                            Raw.CARBON, 4,
                            Raw.SULPHUR, 4),
                    Map.of(),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.EXPLOSIVE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.SULPHUR, 6,
                            Raw.PHOSPHORUS, 6,
                            Raw.ARSENIC, 4,
                            Raw.MERCURY, 2),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.EXPLOSIVE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.PHOSPHORUS, 5,
                            Raw.ARSENIC, 4,
                            Raw.NIOBIUM, 5,
                            Raw.MERCURY, 5,
                            Raw.POLONIUM, 5),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.3))),
                    List.of()));
    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> SMALL_CALIBRE_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.SMALL_CALIBRE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.IRON, 2,
                            Raw.NICKEL, 1,
                            Raw.SULPHUR, 2),
                    Map.of(),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.SMALL_CALIBRE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.IRON, 2,
                            Raw.ZINC, 2,
                            Raw.PHOSPHORUS, 2,
                            Raw.SELENIUM, 2,
                            Raw.ZIRCONIUM, 2),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.SMALL_CALIBRE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.SULPHUR, 2,
                            Raw.PHOSPHORUS, 2,
                            Raw.TUNGSTEN, 2,
                            Raw.ZIRCONIUM, 2,
                            Raw.MERCURY, 2,
                            Raw.ANTIMONY, 1),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.3))),
                    List.of()));
    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> HIGH_VELOCITY_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.HIGH_VELOCITY_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.IRON, 2,
                            Raw.VANADIUM, 1),
                    Map.of(),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.HIGH_VELOCITY_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.IRON, 4,
                            Raw.VANADIUM, 3,
                            Raw.TUNGSTEN, 2,
                            Raw.ZIRCONIUM, 2),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.HIGH_VELOCITY_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.VANADIUM, 4,
                            Raw.TUNGSTEN, 4,
                            Raw.ZIRCONIUM, 2,
                            Raw.YTTRIUM, 2),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.3))),
                    List.of()));
    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> LARGE_CALIBRE_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.LARGE_CALIBRE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.NICKEL, 4,
                            Raw.CARBON, 3,
                            Raw.SULPHUR, 2),
                    Map.of(),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.LARGE_CALIBRE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.TIN, 2,
                            Raw.PHOSPHORUS, 3,
                            Raw.ZINC, 3,
                            Raw.ARSENIC, 1,
                            Raw.ZIRCONIUM, 2),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.LARGE_CALIBRE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.ZINC, 8,
                            Raw.TUNGSTEN, 2,
                            Raw.ARSENIC, 1,
                            Raw.MERCURY, 1,
                            Raw.ANTIMONY, 2),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.3))),
                    List.of()));
    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> AFM_REFILL = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.AFM_REFILL, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.NICKEL, 2,
                            Raw.ZINC, 2,
                            Raw.CHROMIUM, 2,
                            Raw.VANADIUM, 3),
                    Map.of(),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.AFM_REFILL, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.TIN, 1,
                            Raw.MANGANESE, 2,
                            Raw.VANADIUM, 6,
                            Raw.MOLYBDENUM, 1,
                            Raw.ZIRCONIUM, 1),
                    Map.of(
                            HorizonsModifier.CONSUMPTION, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.0, 0.5))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.AFM_REFILL, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.ZINC, 2,
                            Raw.CHROMIUM, 4,
                            Raw.VANADIUM, 6,
                            Raw.ZIRCONIUM, 2,
                            Raw.TELLURIUM, 1,
                            Raw.RUTHENIUM, 1),
                    Map.of(
                            HorizonsModifier.CONSUMPTION, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0))),
                    List.of()));
    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> SRV_AMMO_RESTOCK = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.SRV_AMMO_RESTOCK, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.SULPHUR, 2,
                            Raw.PHOSPHORUS, 1),
                    Map.of(),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.SRV_AMMO_RESTOCK, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.PHOSPHORUS, 1,
                            Raw.MANGANESE, 1,
                            Raw.SELENIUM, 1,
                            Raw.MOLYBDENUM, 1),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.SRV_AMMO_RESTOCK, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.PHOSPHORUS, 2,
                            Raw.SELENIUM, 2,
                            Raw.MOLYBDENUM, 1,
                            Raw.TECHNETIUM, 1),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.3))),
                    List.of()));
    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> SRV_REPAIR = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.SRV_REPAIR, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.IRON, 2,
                            Raw.NICKEL, 1),
                    Map.of(),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.SRV_REPAIR, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.NICKEL, 3,
                            Raw.MANGANESE, 1,
                            Raw.VANADIUM, 2,
                            Raw.MOLYBDENUM, 1),
                    Map.of(
                            HorizonsModifier.HULL_STRENGTH, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.0, 0.5))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.SRV_REPAIR, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.ZINC, 1,
                            Raw.CHROMIUM, 2,
                            Raw.VANADIUM, 2,
                            Raw.TUNGSTEN, 1,
                            Raw.TELLURIUM, 1),
                    Map.of(
                            HorizonsModifier.HULL_STRENGTH, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0))),
                    List.of()));
    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> SRV_REFUEL = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.SRV_REFUEL, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.SULPHUR, 1,
                            Raw.PHOSPHORUS, 1),
                    Map.of(),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.SRV_REFUEL, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.SULPHUR, 1,
                            Raw.PHOSPHORUS, 1,
                            Raw.ARSENIC, 1,
                            Raw.MERCURY, 1),
                    Map.of(
                            HorizonsModifier.FUEL_EFFICIENCY, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.SRV_REFUEL, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.SULPHUR, 1,
                            Raw.ARSENIC, 1,
                            Raw.MERCURY, 1,
                            Raw.TECHNETIUM, 1),
                    Map.of(
                            HorizonsModifier.FUEL_EFFICIENCY, new HorizonsNumberModifierValue("+200%", true, percentagePositive(0.0, 2.0))),
                    List.of()));


    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> CONFIGURABLE_EXPLOSIVE_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.CONFIGURABLE_EXPLOSIVE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.IRON, 3,
                            Raw.NICKEL, 3,
                            Raw.CARBON, 4,
                            Raw.SULPHUR, 4),
                    Map.of(),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.CONFIGURABLE_EXPLOSIVE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.PHOSPHORUS, 6,
                            Raw.ARSENIC, 4,
                            Raw.MERCURY, 2,
                            Manufactured.GUARDIAN_POWERCELL, 1,
                            Manufactured.GUARDIAN_POWERCONDUIT, 1,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 1),
                    Map.of(
                            HorizonsModifier.ANTI_XENO_DAMAGE, new HorizonsNumberModifierValue(UTF8Constants.CHECK_TRUE, true, bool(true))),
                    List.of()));

    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> CONFIGURABLE_SMALL_CALIBRE_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.CONFIGURABLE_SMALL_CALIBRE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.IRON, 2,
                            Raw.NICKEL, 1,
                            Raw.SULPHUR, 2),
                    Map.of(),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.CONFIGURABLE_SMALL_CALIBRE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.TIN, 2,
                            Raw.ZINC, 3,
                            Raw.PHOSPHORUS, 3,
                            Manufactured.GUARDIAN_POWERCELL, 1,
                            Manufactured.GUARDIAN_POWERCONDUIT, 1,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 1),
                    Map.of(
                            HorizonsModifier.ANTI_XENO_DAMAGE, new HorizonsNumberModifierValue(UTF8Constants.CHECK_TRUE, true, bool(true))),
                    List.of()));

    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> SEISMIC_CHARGE_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.SEISMIC_CHARGE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.IRON, 2,
                            Raw.NICKEL, 2,
                            Raw.SULPHUR, 2,
                            Raw.PHOSPHORUS, 3,
                            Raw.MERCURY, 1),
                    Map.of(),
                    List.of()));

    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> SHOCK_CANNON_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.SHOCK_CANNON_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Manufactured.GRIDRESISTORS, 3,
                            Manufactured.HEATDISPERSIONPLATE, 2,
                            Manufactured.FOCUSCRYSTALS, 2,
                            Manufactured.PHASEALLOYS, 2,
                            Raw.LEAD, 2
                    ),
                    Map.of(),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.SHOCK_CANNON_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Manufactured.GRIDRESISTORS, 5,
                            Manufactured.HEATDISPERSIONPLATE, 3,
                            Manufactured.FOCUSCRYSTALS, 4,
                            Manufactured.PHASEALLOYS, 5,
                            Raw.LEAD, 3
                    ),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+5%", true, percentagePositive(0.0, 0.05))),
                    List.of()),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.SHOCK_CANNON_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Manufactured.GRIDRESISTORS, 7,
                            Manufactured.HEATDISPERSIONPLATE, 4,
                            Manufactured.FOCUSCRYSTALS, 6,
                            Manufactured.PHASEALLOYS, 8,
                            Raw.LEAD, 5
                    ),
                    Map.of(
                            HorizonsModifier.DAMAGE_BOOST, new HorizonsNumberModifierValue("+10%", true, percentagePositive(0.0, 0.1))),
                    List.of()));

    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> SUB_SURFACE_DISPLACEMENT_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.SUB_SURFACE_DISPLACEMENT_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.NICKEL, 3,
                            Raw.CARBON, 3,
                            Raw.SULPHUR, 3,
                            Raw.TUNGSTEN, 2),
                    Map.of(),
                    List.of()));


    public static final Map<HorizonsBlueprintGrade, HorizonsBlueprint> CAUSTIC_SINKS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.CAUSTIC_SINKS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Manufactured.CHEMICALSTORAGEUNITS, 1,
                            Manufactured.GALVANISINGALLOYS, 1,
                            Manufactured.TG_CAUSTICSHARD, 4,
                            Manufactured.TG_CAUSTICGENERATORPARTS, 2),
                    Map.of(),
                    List.of(),
                    GameVersion.LIVE));

}
