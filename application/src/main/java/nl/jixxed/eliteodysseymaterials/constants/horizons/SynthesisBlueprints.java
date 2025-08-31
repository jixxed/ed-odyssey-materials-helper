package nl.jixxed.eliteodysseymaterials.constants.horizons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.UTF8Constants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsNumberModifierValue;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsSynthesisBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("java:S1192")
public class SynthesisBlueprints {
    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> GUARDIAN_PLASMA_CHARGER_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.GUARDIAN_PLASMA_CHARGER_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.CHROMIUM, 3,
                            Manufactured.HEATDISPERSIONPLATE, 2,
                            Manufactured.GUARDIAN_POWERCONDUIT, 3,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 4),
                    Map.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.GUARDIAN_PLASMA_CHARGER_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.CHROMIUM, 4,
                            Manufactured.HEATEXCHANGERS, 2,
                            Manufactured.PHASEALLOYS, 2,
                            Manufactured.GUARDIAN_POWERCELL, 2,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 2),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15)))),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.GUARDIAN_PLASMA_CHARGER_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.CHROMIUM, 6,
                            Raw.ZIRCONIUM, 2,
                            Manufactured.HEATEXCHANGERS, 4,
                            Manufactured.PHASEALLOYS, 6,
                            Manufactured.GUARDIAN_POWERCELL, 4,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 3),
                    Map.of(
                            Raw.CHROMIUM, 3,
                            Raw.ZIRCONIUM, 1,
                            Manufactured.HEATEXCHANGERS, 2,
                            Manufactured.PHASEALLOYS, 3,
                            Manufactured.GUARDIAN_POWERCELL, 2,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 2),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.30))),
                    SquadronPerk.PREMIUM_AMMO_SYNTHESIS
            ));
    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> GUARDIAN_NANITE_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.GUARDIAN_NANITE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Manufactured.GUARDIAN_POWERCELL, 2,
                            Manufactured.TG_ABRASION01, 5,
                            Manufactured.TG_ABRASION02, 5),
                    Map.of(),
                    GameVersion.LIVE));
    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> GUARDIAN_GAUSS_CANNON_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.GUARDIAN_GAUSS_CANNON_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.MANGANESE, 3,
                            Manufactured.FOCUSCRYSTALS, 2,
                            Manufactured.GUARDIAN_POWERCONDUIT, 2,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 4),
                    Map.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.GUARDIAN_GAUSS_CANNON_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.MANGANESE, 5,
                            Manufactured.HEATRESISTANTCERAMICS, 3,
                            Manufactured.FOCUSCRYSTALS, 5,
                            Manufactured.GUARDIAN_POWERCONDUIT, 4,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 3),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15)))),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.GUARDIAN_GAUSS_CANNON_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.MANGANESE, 8,
                            Manufactured.FILAMENTCOMPOSITES, 6,
                            Manufactured.FOCUSCRYSTALS, 10,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 6),
                    Map.of(
                            Raw.MANGANESE, 4,
                            Manufactured.FILAMENTCOMPOSITES, 3,
                            Manufactured.FOCUSCRYSTALS, 5,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 3),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.30))),
                    SquadronPerk.PREMIUM_AMMO_SYNTHESIS
            ));
    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> AX_SMALL_CALIBRE_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.AX_SMALL_CALIBRE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.IRON, 2,
                            Raw.NICKEL, 1,
                            Raw.SULPHUR, 2,
                            Manufactured.TG_WEAPONPARTS, 2),
                    Map.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.AX_SMALL_CALIBRE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.IRON, 2,
                            Raw.PHOSPHORUS, 2,
                            Raw.ZIRCONIUM, 2,
                            Manufactured.UNKNOWNENERGYSOURCE, 3,
                            Manufactured.TG_WEAPONPARTS, 4),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+5%", true, percentagePositive(0.0, 0.05)))),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.AX_SMALL_CALIBRE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.IRON, 3,
                            Raw.PHOSPHORUS, 2,
                            Raw.ZIRCONIUM, 2,
                            Manufactured.UNKNOWNENERGYSOURCE, 4,
                            Manufactured.UNKNOWNCARAPACE, 2,
                            Manufactured.TG_WEAPONPARTS, 6),
                    Map.of(
                            Raw.IRON, 2,
                            Raw.PHOSPHORUS, 1,
                            Raw.ZIRCONIUM, 1,
                            Manufactured.UNKNOWNENERGYSOURCE, 2,
                            Manufactured.UNKNOWNCARAPACE, 1,
                            Manufactured.TG_WEAPONPARTS, 3),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+10%", true, percentagePositive(0.0, 0.10))),
                    SquadronPerk.PREMIUM_AMMO_SYNTHESIS
            ));
    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> AX_REMOTE_FLAK_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.AX_REMOTE_FLAK_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.NICKEL, 4,
                            Raw.CARBON, 3,
                            Raw.SULPHUR, 2),
                    Map.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.AX_REMOTE_FLAK_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.TIN, 2,
                            Raw.ZINC, 3,
                            Raw.ARSENIC, 1,
                            Manufactured.UNKNOWNTECHNOLOGYCOMPONENTS, 3,
                            Manufactured.TG_WRECKAGECOMPONENTS, 2),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+5%", true, percentagePositive(0.0, 0.05)))),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.AX_REMOTE_FLAK_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.ZINC, 8,
                            Raw.TUNGSTEN, 2,
                            Raw.ARSENIC, 1,
                            Manufactured.UNKNOWNENERGYSOURCE, 3,
                            Manufactured.UNKNOWNTECHNOLOGYCOMPONENTS, 4,
                            Manufactured.TG_WEAPONPARTS, 1),
                    Map.of(
                            Raw.ZINC, 4,
                            Raw.TUNGSTEN, 1,
                            Raw.ARSENIC, 1,
                            Manufactured.UNKNOWNENERGYSOURCE, 2,
                            Manufactured.UNKNOWNTECHNOLOGYCOMPONENTS, 2,
                            Manufactured.TG_WEAPONPARTS, 1),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+10%", true, percentagePositive(0.0, 0.10))),
                    SquadronPerk.PREMIUM_AMMO_SYNTHESIS
            ));
    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> ENZYME_MISSILE_LAUNCHER_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.ENZYME_MISSILE_LAUNCHER_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.IRON, 3,
                            Raw.SULPHUR, 3,
                            Manufactured.TG_BIOMECHANICALCONDUITS, 4,
                            Manufactured.TG_PROPULSIONELEMENT, 3,
                            Manufactured.TG_WEAPONPARTS, 3,
                            Raw.LEAD, 2),
                    Map.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.ENZYME_MISSILE_LAUNCHER_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.SULPHUR, 6,
                            Raw.TUNGSTEN, 4,
                            Manufactured.TG_BIOMECHANICALCONDUITS, 5,
                            Manufactured.TG_PROPULSIONELEMENT, 6,
                            Manufactured.TG_WEAPONPARTS, 4,
                            Raw.LEAD, 4),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15)))),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.ENZYME_MISSILE_LAUNCHER_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.PHOSPHORUS, 5,
                            Raw.TUNGSTEN, 4,
                            Manufactured.TG_BIOMECHANICALCONDUITS, 6,
                            Manufactured.TG_PROPULSIONELEMENT, 6,
                            Manufactured.TG_WEAPONPARTS, 5,
                            Raw.LEAD, 6),
                    Map.of(
                            Raw.PHOSPHORUS, 3,
                            Raw.TUNGSTEN, 2,
                            Manufactured.TG_BIOMECHANICALCONDUITS, 3,
                            Manufactured.TG_PROPULSIONELEMENT, 3,
                            Manufactured.TG_WEAPONPARTS, 3,
                            Raw.LEAD, 3),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.30))),
                    SquadronPerk.PREMIUM_AMMO_SYNTHESIS
            ));
    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> FLECHETTE_LAUNCHER_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.FLECHETTE_LAUNCHER_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.TUNGSTEN, 1,
                            Manufactured.ELECTROCHEMICALARRAYS, 3,
                            Manufactured.MECHANICALCOMPONENTS, 2,
                            Raw.BORON, 2),
                    Map.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.FLECHETTE_LAUNCHER_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.TUNGSTEN, 4,
                            Manufactured.ELECTROCHEMICALARRAYS, 6,
                            Manufactured.MECHANICALCOMPONENTS, 4,
                            Raw.BORON, 4),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+5%", true, percentagePositive(0.0, 0.05)))),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.FLECHETTE_LAUNCHER_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.TUNGSTEN, 6,
                            Manufactured.ELECTROCHEMICALARRAYS, 9,
                            Manufactured.MECHANICALCOMPONENTS, 5,
                            Raw.BORON, 6),
                    Map.of(
                            Raw.TUNGSTEN, 3,
                            Manufactured.ELECTROCHEMICALARRAYS, 5,
                            Manufactured.MECHANICALCOMPONENTS, 3,
                            Raw.BORON, 3),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+10%", true, percentagePositive(0.0, 0.10))),
                    SquadronPerk.PREMIUM_AMMO_SYNTHESIS
            ));
    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> GUARDIAN_SHARD_CANNON_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.GUARDIAN_SHARD_CANNON_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.CARBON, 3,
                            Raw.VANADIUM, 2,
                            Manufactured.CRYSTALSHARDS, 3,
                            Manufactured.GUARDIAN_POWERCELL, 3,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 5),
                    Map.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.GUARDIAN_SHARD_CANNON_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Manufactured.CRYSTALSHARDS, 4,
                            Manufactured.GUARDIAN_POWERCELL, 2,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 2),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15)))),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.GUARDIAN_SHARD_CANNON_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.CARBON, 8,
                            Raw.VANADIUM, 4,
                            Manufactured.CRYSTALSHARDS, 8,
                            Manufactured.GUARDIAN_POWERCELL, 6),
                    Map.of(
                            Raw.CARBON, 4,
                            Raw.VANADIUM, 2,
                            Manufactured.CRYSTALSHARDS, 4,
                            Manufactured.GUARDIAN_POWERCELL, 3),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.30))),
                    SquadronPerk.PREMIUM_AMMO_SYNTHESIS
            ));
    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> AX_EXPLOSIVE_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.AX_EXPLOSIVE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.CARBON, 4,
                            Raw.IRON, 3,
                            Raw.NICKEL, 3,
                            Manufactured.TG_PROPULSIONELEMENT, 3),
                    Map.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.AX_EXPLOSIVE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.SULPHUR, 6,
                            Raw.PHOSPHORUS, 6,
                            Raw.MERCURY, 2,
                            Manufactured.TG_PROPULSIONELEMENT, 4,
                            Manufactured.UNKNOWNORGANICCIRCUITRY, 4),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+5%", true, percentagePositive(0.0, 0.05)))),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.AX_EXPLOSIVE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.TUNGSTEN, 5,
                            Raw.MERCURY, 4,
                            Raw.POLONIUM, 2,
                            Manufactured.TG_BIOMECHANICALCONDUITS, 5,
                            Manufactured.TG_PROPULSIONELEMENT, 5,
                            Encoded.TG_SHIPFLIGHTDATA, 6),
                    Map.of(
                            Raw.TUNGSTEN, 3,
                            Raw.MERCURY, 2,
                            Raw.POLONIUM, 1,
                            Manufactured.TG_BIOMECHANICALCONDUITS, 3,
                            Manufactured.TG_PROPULSIONELEMENT, 3,
                            Encoded.TG_SHIPFLIGHTDATA, 3),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+10%", true, percentagePositive(0.0, 0.10))),
                    SquadronPerk.PREMIUM_AMMO_SYNTHESIS
            ));
    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> LIFE_SUPPORT_SYNTHESIS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.LIFE_SUPPORT_SYNTHESIS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.IRON, 2,
                            Raw.NICKEL, 1),
                    Map.of(
                            HorizonsModifier.REFILL, new HorizonsNumberModifierValue("100%", true, percentagePositive(0.0, 1.0)))));
    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> LIMPETS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.LIMPETS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.IRON, 10,
                            Raw.NICKEL, 10),
                    Map.of(
                            Raw.IRON, 5,
                            Raw.NICKEL, 5),
                    Map.of(
                            HorizonsModifier.LIMPETS, new HorizonsNumberModifierValue("+4", true, percentagePositive(0.0, 0.04))),
                    SquadronPerk.LIMPET_SYNTHESIS
            ));
    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> CHAFF = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.CHAFF, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Manufactured.COMPACTCOMPOSITES, 1,
                            Manufactured.FILAMENTCOMPOSITES, 1),
                    Map.of(
                            HorizonsModifier.REFILL, new HorizonsNumberModifierValue("50%", true, percentagePositive(0.0, 0.50)))),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.CHAFF, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Manufactured.COMPACTCOMPOSITES, 1,
                            Manufactured.FILAMENTCOMPOSITES, 2,
                            Manufactured.THERMICALLOYS, 1),
                    Map.of(
                            HorizonsModifier.REFILL, new HorizonsNumberModifierValue("100%", true, percentagePositive(0.0, 1.0)))),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.CHAFF, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Manufactured.COMPACTCOMPOSITES, 1,
                            Manufactured.FILAMENTCOMPOSITES, 2,
                            Manufactured.THERMICALLOYS, 1,
                            Manufactured.PROTORADIOLICALLOYS, 1),
                    Map.of(
                            HorizonsModifier.REFILL, new HorizonsNumberModifierValue("100%", true, percentagePositive(0.0, 1.0)),
                            HorizonsModifier.JAM_DURATION, new HorizonsNumberModifierValue("+2s", true, plus(2.0)))));
    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> HEATSINKS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.HEATSINKS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Manufactured.BASICCONDUCTORS, 2,
                            Manufactured.HEATCONDUCTIONWIRING, 2),
                    Map.of(
                            HorizonsModifier.REFILL, new HorizonsNumberModifierValue("100%", true, percentagePositive(0.0, 1.0)))),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.HEATSINKS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Manufactured.BASICCONDUCTORS, 2,
                            Manufactured.HEATCONDUCTIONWIRING, 2,
                            Manufactured.HEATEXCHANGERS, 2),
                    Map.of(
                            HorizonsModifier.REFILL, new HorizonsNumberModifierValue("100%", true, percentagePositive(0.0, 1.0)),
                            HorizonsModifier.THERMAL_DRAIN, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15)))),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.HEATSINKS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Manufactured.BASICCONDUCTORS, 2,
                            Manufactured.HEATCONDUCTIONWIRING, 2,
                            Manufactured.HEATEXCHANGERS, 2,
                            Manufactured.PROTOHEATRADIATORS, 1),
                    Map.of(
                            HorizonsModifier.REFILL, new HorizonsNumberModifierValue("100%", true, percentagePositive(0.0, 1.0)),
                            HorizonsModifier.THERMAL_DRAIN, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.3)))));
    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> FSD_INJECTION = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.FSD_INJECTION, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.CARBON, 1,
                            Raw.VANADIUM, 1,
                            Raw.GERMANIUM, 1),
                    Map.of(
                            Raw.CARBON, 1,
                            Raw.VANADIUM, 1),
                    Map.of(
                            HorizonsModifier.JUMP_RANGE, new HorizonsNumberModifierValue("+25%", true, percentagePositive(0.0, 0.25))),
                    SquadronPerk.FSD_BOOST_SYNTHESIS
            ),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.FSD_INJECTION, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.CARBON, 1,
                            Raw.VANADIUM, 1,
                            Raw.GERMANIUM, 1,
                            Raw.CADMIUM, 1,
                            Raw.NIOBIUM, 1),
                    Map.of(
                            Raw.VANADIUM, 1,
                            Raw.GERMANIUM, 1,
                            Raw.CADMIUM, 1),
                    Map.of(
                            HorizonsModifier.JUMP_RANGE, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.0, 0.50))),
                    SquadronPerk.FSD_BOOST_SYNTHESIS
            ),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.FSD_INJECTION, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.CARBON, 1,
                            Raw.GERMANIUM, 1,
                            Raw.ARSENIC, 1,
                            Raw.NIOBIUM, 1,
                            Raw.YTTRIUM, 1,
                            Raw.POLONIUM, 1),
                    Map.of(
                            Raw.GERMANIUM, 1,
                            Raw.ARSENIC, 1,
                            Raw.NIOBIUM, 1),
                    Map.of(
                            HorizonsModifier.JUMP_RANGE, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0))),
                    SquadronPerk.FSD_BOOST_SYNTHESIS
            )
    );
    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> PLASMA_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.PLASMA_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.SULPHUR, 3,
                            Raw.PHOSPHORUS, 4,
                            Raw.MANGANESE, 1),
                    Map.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.PLASMA_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.PHOSPHORUS, 5,
                            Raw.MANGANESE, 3,
                            Raw.SELENIUM, 1,
                            Raw.MOLYBDENUM, 4),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15)))),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.PLASMA_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.SELENIUM, 5,
                            Raw.CADMIUM, 4,
                            Raw.MOLYBDENUM, 4,
                            Raw.TECHNETIUM, 2),
                    Map.of(
                            Raw.SELENIUM, 3,
                            Raw.CADMIUM, 2,
                            Raw.MOLYBDENUM, 2,
                            Raw.TECHNETIUM, 1),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.3))),
                    SquadronPerk.PREMIUM_AMMO_SYNTHESIS
            ));
    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> EXPLOSIVE_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.EXPLOSIVE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.IRON, 3,
                            Raw.NICKEL, 3,
                            Raw.CARBON, 4,
                            Raw.SULPHUR, 4),
                    Map.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.EXPLOSIVE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.SULPHUR, 6,
                            Raw.PHOSPHORUS, 6,
                            Raw.ARSENIC, 4,
                            Raw.MERCURY, 2),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15)))),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.EXPLOSIVE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.PHOSPHORUS, 5,
                            Raw.ARSENIC, 4,
                            Raw.NIOBIUM, 5,
                            Raw.MERCURY, 5,
                            Raw.POLONIUM, 5),
                    Map.of(
                            Raw.PHOSPHORUS, 3,
                            Raw.ARSENIC, 2,
                            Raw.NIOBIUM, 3,
                            Raw.MERCURY, 3,
                            Raw.POLONIUM, 3),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.3))),
                    SquadronPerk.PREMIUM_AMMO_SYNTHESIS
            ));
    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> SMALL_CALIBRE_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.SMALL_CALIBRE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.IRON, 2,
                            Raw.NICKEL, 1,
                            Raw.SULPHUR, 2),
                    Map.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.SMALL_CALIBRE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.IRON, 2,
                            Raw.ZINC, 2,
                            Raw.PHOSPHORUS, 2,
                            Raw.SELENIUM, 2,
                            Raw.ZIRCONIUM, 2),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15)))),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.SMALL_CALIBRE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.SULPHUR, 2,
                            Raw.PHOSPHORUS, 2,
                            Raw.TUNGSTEN, 2,
                            Raw.ZIRCONIUM, 2,
                            Raw.MERCURY, 2,
                            Raw.ANTIMONY, 1),
                    Map.of(
                            Raw.SULPHUR, 1,
                            Raw.PHOSPHORUS, 1,
                            Raw.TUNGSTEN, 1,
                            Raw.ZIRCONIUM, 1,
                            Raw.MERCURY, 1,
                            Raw.ANTIMONY, 1),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.3))),
                    SquadronPerk.PREMIUM_AMMO_SYNTHESIS
            ));
    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> HIGH_VELOCITY_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.HIGH_VELOCITY_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.IRON, 2,
                            Raw.VANADIUM, 1),
                    Map.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.HIGH_VELOCITY_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.IRON, 4,
                            Raw.VANADIUM, 3,
                            Raw.TUNGSTEN, 2,
                            Raw.ZIRCONIUM, 2),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15)))),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.HIGH_VELOCITY_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.VANADIUM, 4,
                            Raw.TUNGSTEN, 4,
                            Raw.ZIRCONIUM, 2,
                            Raw.YTTRIUM, 2),
                    Map.of(
                            Raw.VANADIUM, 2,
                            Raw.TUNGSTEN, 2,
                            Raw.ZIRCONIUM, 1,
                            Raw.YTTRIUM, 1),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.3))),
                    SquadronPerk.PREMIUM_AMMO_SYNTHESIS
            ));
    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> LARGE_CALIBRE_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.LARGE_CALIBRE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.NICKEL, 4,
                            Raw.CARBON, 3,
                            Raw.SULPHUR, 2),
                    Map.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.LARGE_CALIBRE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.TIN, 2,
                            Raw.PHOSPHORUS, 3,
                            Raw.ZINC, 3,
                            Raw.ARSENIC, 1,
                            Raw.ZIRCONIUM, 2),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15)))),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.LARGE_CALIBRE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.ZINC, 8,
                            Raw.TUNGSTEN, 2,
                            Raw.ARSENIC, 1,
                            Raw.MERCURY, 1,
                            Raw.ANTIMONY, 2),
                    Map.of(
                            Raw.ZINC, 4,
                            Raw.TUNGSTEN, 1,
                            Raw.ARSENIC, 1,
                            Raw.MERCURY, 1,
                            Raw.ANTIMONY, 1),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.3))),
                    SquadronPerk.PREMIUM_AMMO_SYNTHESIS
            ));
    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> AFM_REFILL = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.AFM_REFILL, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.NICKEL, 2,
                            Raw.ZINC, 2,
                            Raw.CHROMIUM, 2,
                            Raw.VANADIUM, 3),
                    Map.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.AFM_REFILL, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.TIN, 1,
                            Raw.MANGANESE, 2,
                            Raw.VANADIUM, 6,
                            Raw.MOLYBDENUM, 1,
                            Raw.ZIRCONIUM, 1),
                    Map.of(
                            HorizonsModifier.CONSUMPTION, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.0, 0.5)))),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.AFM_REFILL, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.ZINC, 2,
                            Raw.CHROMIUM, 4,
                            Raw.VANADIUM, 6,
                            Raw.ZIRCONIUM, 2,
                            Raw.TELLURIUM, 1,
                            Raw.RUTHENIUM, 1),
                    Map.of(
                            HorizonsModifier.CONSUMPTION, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0)))));
    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> SRV_AMMO_RESTOCK = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.SRV_AMMO_RESTOCK, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.SULPHUR, 2,
                            Raw.PHOSPHORUS, 1),
                    Map.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.SRV_AMMO_RESTOCK, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.PHOSPHORUS, 1,
                            Raw.MANGANESE, 1,
                            Raw.SELENIUM, 1,
                            Raw.MOLYBDENUM, 1),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15)))),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.SRV_AMMO_RESTOCK, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.PHOSPHORUS, 2,
                            Raw.SELENIUM, 2,
                            Raw.MOLYBDENUM, 1,
                            Raw.TECHNETIUM, 1),
                    Map.of(
                            Raw.PHOSPHORUS, 1,
                            Raw.SELENIUM, 1,
                            Raw.MOLYBDENUM, 1,
                            Raw.TECHNETIUM, 1),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.3))),
                    SquadronPerk.PREMIUM_AMMO_SYNTHESIS
            ));
    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> SRV_REPAIR = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.SRV_REPAIR, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.IRON, 2,
                            Raw.NICKEL, 1),
                    Map.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.SRV_REPAIR, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.NICKEL, 3,
                            Raw.MANGANESE, 1,
                            Raw.VANADIUM, 2,
                            Raw.MOLYBDENUM, 1),
                    Map.of(
                            HorizonsModifier.HULL_STRENGTH, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.0, 0.5)))),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.SRV_REPAIR, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.ZINC, 1,
                            Raw.CHROMIUM, 2,
                            Raw.VANADIUM, 2,
                            Raw.TUNGSTEN, 1,
                            Raw.TELLURIUM, 1),
                    Map.of(
                            HorizonsModifier.HULL_STRENGTH, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0)))));
    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> SRV_REFUEL = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.SRV_REFUEL, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.SULPHUR, 1,
                            Raw.PHOSPHORUS, 1),
                    Map.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.SRV_REFUEL, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.SULPHUR, 1,
                            Raw.PHOSPHORUS, 1,
                            Raw.ARSENIC, 1,
                            Raw.MERCURY, 1),
                    Map.of(
                            HorizonsModifier.FUEL_EFFICIENCY, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0)))),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.SRV_REFUEL, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Raw.SULPHUR, 1,
                            Raw.ARSENIC, 1,
                            Raw.MERCURY, 1,
                            Raw.TECHNETIUM, 1),
                    Map.of(
                            HorizonsModifier.FUEL_EFFICIENCY, new HorizonsNumberModifierValue("+200%", true, percentagePositive(0.0, 2.0)))));


    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> CONFIGURABLE_EXPLOSIVE_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.CONFIGURABLE_EXPLOSIVE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.IRON, 3,
                            Raw.NICKEL, 3,
                            Raw.CARBON, 4,
                            Raw.SULPHUR, 4),
                    Map.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.CONFIGURABLE_EXPLOSIVE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.PHOSPHORUS, 6,
                            Raw.ARSENIC, 4,
                            Raw.MERCURY, 2,
                            Manufactured.GUARDIAN_POWERCELL, 1,
                            Manufactured.GUARDIAN_POWERCONDUIT, 1,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 1),
                    Map.of(
                            HorizonsModifier.THERMAL_DAMAGE_RATIO, new HorizonsNumberModifierValue("-100%", false, percentageNegative(0.0, 1.0)),
                            HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, new HorizonsNumberModifierValue("-100%", false, percentageNegative(0.0, 1.0)),
                            HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, new HorizonsNumberModifierValue("+100%", true, plus(1.0)),
                            HorizonsModifier.ANTI_XENO_DAMAGE, new HorizonsNumberModifierValue(UTF8Constants.CHECK_TRUE, true, bool(true))
                    )));

    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> CONFIGURABLE_SMALL_CALIBRE_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.CONFIGURABLE_SMALL_CALIBRE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.IRON, 2,
                            Raw.NICKEL, 1,
                            Raw.SULPHUR, 2),
                    Map.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.CONFIGURABLE_SMALL_CALIBRE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Raw.TIN, 2,
                            Raw.ZINC, 3,
                            Raw.PHOSPHORUS, 3,
                            Manufactured.GUARDIAN_POWERCELL, 1,
                            Manufactured.GUARDIAN_POWERCONDUIT, 1,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 1),
                    Map.of(
                            HorizonsModifier.THERMAL_DAMAGE_RATIO, new HorizonsNumberModifierValue("-100%", false, percentageNegative(0.0, 1.0)),
                            HorizonsModifier.KINETIC_DAMAGE_RATIO, new HorizonsNumberModifierValue("-100%", false, percentageNegative(0.0, 1.0)),
                            HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, new HorizonsNumberModifierValue("+100%", true, plus(1.0)),
                            HorizonsModifier.ANTI_XENO_DAMAGE, new HorizonsNumberModifierValue(UTF8Constants.CHECK_TRUE, true, bool(true))
                    )));

    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> SEISMIC_CHARGE_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.SEISMIC_CHARGE_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.IRON, 2,
                            Raw.NICKEL, 2,
                            Raw.SULPHUR, 2,
                            Raw.PHOSPHORUS, 3,
                            Raw.MERCURY, 1),
                    Map.of()));

    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> SHOCK_CANNON_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.SHOCK_CANNON_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Manufactured.GRIDRESISTORS, 3,
                            Manufactured.HEATDISPERSIONPLATE, 2,
                            Manufactured.FOCUSCRYSTALS, 2,
                            Manufactured.PHASEALLOYS, 2,
                            Raw.LEAD, 2
                    ),
                    Map.of()),
            HorizonsBlueprintGrade.GRADE_2, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.SHOCK_CANNON_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_2,
                    Map.of(
                            Manufactured.GRIDRESISTORS, 5,
                            Manufactured.HEATDISPERSIONPLATE, 3,
                            Manufactured.FOCUSCRYSTALS, 4,
                            Manufactured.PHASEALLOYS, 5,
                            Raw.LEAD, 3
                    ),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+5%", true, percentagePositive(0.0, 0.05)))),
            HorizonsBlueprintGrade.GRADE_3, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.SHOCK_CANNON_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_3,
                    Map.of(
                            Manufactured.GRIDRESISTORS, 7,
                            Manufactured.HEATDISPERSIONPLATE, 4,
                            Manufactured.FOCUSCRYSTALS, 6,
                            Manufactured.PHASEALLOYS, 8,
                            Raw.LEAD, 5
                    ),
                    Map.of(
                            Manufactured.GRIDRESISTORS, 4,
                            Manufactured.HEATDISPERSIONPLATE, 2,
                            Manufactured.FOCUSCRYSTALS, 3,
                            Manufactured.PHASEALLOYS, 4,
                            Raw.LEAD, 3
                    ),
                    Map.of(
                            HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+10%", true, percentagePositive(0.0, 0.1))),
                    SquadronPerk.PREMIUM_AMMO_SYNTHESIS
            ));

    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> SUB_SURFACE_DISPLACEMENT_MUNITIONS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.SUB_SURFACE_DISPLACEMENT_MUNITIONS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Raw.NICKEL, 3,
                            Raw.CARBON, 3,
                            Raw.SULPHUR, 3,
                            Raw.TUNGSTEN, 2),
                    Map.of()));


    public static final Map<HorizonsBlueprintGrade, HorizonsSynthesisBlueprint> CAUSTIC_SINKS = Map.of(
            HorizonsBlueprintGrade.GRADE_1, new HorizonsSynthesisBlueprint(HorizonsBlueprintName.CAUSTIC_SINKS, HorizonsBlueprintType.SYNTHESIS, HorizonsBlueprintGrade.GRADE_1,
                    Map.of(
                            Manufactured.CHEMICALSTORAGEUNITS, 1,
                            Manufactured.GALVANISINGALLOYS, 1,
                            Manufactured.TG_CAUSTICSHARD, 4,
                            Manufactured.TG_CAUSTICGENERATORPARTS, 2),
                    Map.of(),
                    GameVersion.LIVE));

}
