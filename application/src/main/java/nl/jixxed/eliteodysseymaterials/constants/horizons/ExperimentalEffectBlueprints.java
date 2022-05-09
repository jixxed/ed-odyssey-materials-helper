package nl.jixxed.eliteodysseymaterials.constants.horizons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.UTF8Constants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsExperimentalEffectBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("java:S1192")
public class ExperimentalEffectBlueprints {

    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> BEAM_LASER = Map.of(
            HorizonsBlueprintType.CONCORDANT_SEQUENCE, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.CONCORDANT_SEQUENCE,
                    Map.of(
                            Manufactured.FOCUSCRYSTALS, 5,
                            Encoded.EMBEDDEDFIRMWARE, 3,
                            Raw.ZIRCONIUM, 1),
                    Map.of(
                            HorizonsModifier.WING_SHIELD_REGENERATION_INCREASED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                            HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+50%", false)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.REGENERATION_SEQUENCE, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.REGENERATION_SEQUENCE,
                    Map.of(
                            Manufactured.REFINEDFOCUSCRYSTALS, 3,
                            Manufactured.SHIELDINGSENSORS, 4,
                            Encoded.SHIELDFREQUENCYDATA, 1),
                    Map.of(
                            HorizonsModifier.TARGET_WING_SHIELDS_REGENERATED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                            HorizonsModifier.DAMAGE, new HorizonsModifierValue("-10%", false)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.THERMAL_CONDUIT, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.THERMAL_CONDUIT,
                    Map.of(
                            Manufactured.HEATDISPERSIONPLATE, 5,
                            Raw.SULPHUR, 5,
                            Manufactured.TEMPEREDALLOYS, 5),
                    Map.of(
                            HorizonsModifier.DAMAGE_INCREASES_WITH_HEAT_LEVEL, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.THERMAL_SHOCK, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.THERMAL_SHOCK,
                    Map.of(
                            Manufactured.UNCUTFOCUSCRYSTALS, 5,
                            Manufactured.HEATRESISTANTCERAMICS, 3,
                            Manufactured.CONDUCTIVECOMPONENTS, 3,
                            Raw.TUNGSTEN, 3),
                    Map.of(
                            HorizonsModifier.TARGET_HEAT_INCREASED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                            HorizonsModifier.DAMAGE, new HorizonsModifierValue("-10%", false)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.THERMAL_VENT, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.THERMAL_VENT,
                    Map.of(
                            Manufactured.UNCUTFOCUSCRYSTALS, 5,
                            Manufactured.CONDUCTIVEPOLYMERS, 3,
                            Manufactured.PRECIPITATEDALLOYS, 3),
                    Map.of(
                            HorizonsModifier.HEAT_REDUCED_WHEN_STRIKING_A_TARGET, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.OVERSIZED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.OVERSIZED,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.MECHANICALCOMPONENTS, 3,
                            Raw.RUTHENIUM, 1),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+5%", false),
                            HorizonsModifier.DAMAGE, new HorizonsModifierValue("+3%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.STRIPPED_DOWN, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.STRIPPED_DOWN,
                    Map.of(
                            Manufactured.SALVAGEDALLOYS, 5,
                            Raw.CARBON, 5,
                            Raw.TIN, 1),
                    Map.of(
                            HorizonsModifier.MASS, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.DOUBLE_BRACED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.DOUBLE_BRACED,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.COMPACTCOMPOSITES, 5,
                            Raw.VANADIUM, 3),
                    Map.of(
                            HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+15%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.FLOW_CONTROL, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.FLOW_CONTROL,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.HYBRIDCAPACITORS, 3,
                            Encoded.EMBEDDEDFIRMWARE, 1),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> BURST_LASER = Map.of(
            HorizonsBlueprintType.CONCORDANT_SEQUENCE, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.BURST_LASER, HorizonsBlueprintType.CONCORDANT_SEQUENCE,
                    Map.of(
                            Manufactured.FOCUSCRYSTALS, 5,
                            Encoded.EMBEDDEDFIRMWARE, 3,
                            Raw.ZIRCONIUM, 1),
                    Map.of(
                            HorizonsModifier.WING_SHIELD_REGENERATION_INCREASED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                            HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+50%", false)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.INERTIAL_IMPACT, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.BURST_LASER, HorizonsBlueprintType.INERTIAL_IMPACT,
                    Map.of(
                            Manufactured.UNCUTFOCUSCRYSTALS, 5,
                            Encoded.SHIELDCYCLERECORDINGS, 5,
                            Encoded.DISRUPTEDWAKEECHOES, 5),
                    Map.of(
                            HorizonsModifier.DAMAGE_PARTIALLY_KINETIC, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                            HorizonsModifier.DAMAGE, new HorizonsModifierValue("+50%", true),
                            HorizonsModifier.JITTER, new HorizonsModifierValue("3", false)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.PHASING_SEQUENCE, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.BURST_LASER, HorizonsBlueprintType.PHASING_SEQUENCE,
                    Map.of(
                            Manufactured.FOCUSCRYSTALS, 5,
                            Encoded.SHIELDPATTERNANALYSIS, 3,
                            Raw.NIOBIUM, 3,
                            Manufactured.CONFIGURABLECOMPONENTS, 3),
                    Map.of(
                            HorizonsModifier.PART_OF_DAMAGE_THROUGH_SHIELDS, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                            HorizonsModifier.DAMAGE, new HorizonsModifierValue("-10%", false)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.SCRAMBLE_SPECTRUM, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.BURST_LASER, HorizonsBlueprintType.SCRAMBLE_SPECTRUM,
                    Map.of(
                            Manufactured.CRYSTALSHARDS, 5,
                            Encoded.SHIELDDENSITYREPORTS, 3,
                            Encoded.SCRAMBLEDEMISSIONDATA, 5),
                    Map.of(
                            HorizonsModifier.TARGET_MODULES_MALFUNCTIONS, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                            HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("-10%", false)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.THERMAL_SHOCK, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.BURST_LASER, HorizonsBlueprintType.THERMAL_SHOCK,
                    Map.of(
                            Manufactured.UNCUTFOCUSCRYSTALS, 5,
                            Manufactured.HEATRESISTANTCERAMICS, 3,
                            Manufactured.CONDUCTIVECOMPONENTS, 3,
                            Raw.TUNGSTEN, 3),
                    Map.of(
                            HorizonsModifier.TARGET_HEAT_INCREASED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                            HorizonsModifier.DAMAGE, new HorizonsModifierValue("-10%", false)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.OVERSIZED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.BURST_LASER, HorizonsBlueprintType.OVERSIZED,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.MECHANICALCOMPONENTS, 3,
                            Raw.RUTHENIUM, 1),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+5%", false),
                            HorizonsModifier.DAMAGE, new HorizonsModifierValue("+3%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.STRIPPED_DOWN, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.BURST_LASER, HorizonsBlueprintType.STRIPPED_DOWN,
                    Map.of(
                            Manufactured.SALVAGEDALLOYS, 5,
                            Raw.CARBON, 5,
                            Raw.TIN, 1),
                    Map.of(
                            HorizonsModifier.MASS, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.DOUBLE_BRACED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.BURST_LASER, HorizonsBlueprintType.DOUBLE_BRACED,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.COMPACTCOMPOSITES, 5,
                            Raw.VANADIUM, 3),
                    Map.of(
                            HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+15%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.FLOW_CONTROL, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.BURST_LASER, HorizonsBlueprintType.FLOW_CONTROL,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.HYBRIDCAPACITORS, 3,
                            Encoded.EMBEDDEDFIRMWARE, 1),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.MULTI_SERVOS, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.BURST_LASER, HorizonsBlueprintType.MULTI_SERVOS,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.FOCUSCRYSTALS, 4,
                            Manufactured.CONDUCTIVEPOLYMERS, 2,
                            Manufactured.CONFIGURABLECOMPONENTS, 2),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+5%", false),
                            HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("+3%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> PULSE_LASER = Map.of(
            HorizonsBlueprintType.CONCORDANT_SEQUENCE, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.CONCORDANT_SEQUENCE,
                    Map.of(
                            Manufactured.FOCUSCRYSTALS, 5,
                            Encoded.EMBEDDEDFIRMWARE, 3,
                            Raw.ZIRCONIUM, 1),
                    Map.of(
                            HorizonsModifier.WING_SHIELD_REGENERATION_INCREASED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                            HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+50%", false)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.EMISSIVE_MUNITIONS, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.EMISSIVE_MUNITIONS,
                    Map.of(
                            Manufactured.MECHANICALEQUIPMENT, 4,
                            Encoded.EMISSIONDATA, 3,
                            Manufactured.HEATEXCHANGERS, 3,
                            Raw.MANGANESE, 3),
                    Map.of(
                            HorizonsModifier.TARGET_SIGNATURE_INCREASED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                            HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+100%", false)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.PHASING_SEQUENCE, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.PHASING_SEQUENCE,
                    Map.of(
                            Manufactured.FOCUSCRYSTALS, 5,
                            Encoded.SHIELDPATTERNANALYSIS, 3,
                            Raw.NIOBIUM, 3,
                            Manufactured.CONFIGURABLECOMPONENTS, 3),
                    Map.of(
                            HorizonsModifier.PART_OF_DAMAGE_THROUGH_SHIELDS, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                            HorizonsModifier.DAMAGE, new HorizonsModifierValue("-10%", false)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.SCRAMBLE_SPECTRUM, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.SCRAMBLE_SPECTRUM,
                    Map.of(
                            Manufactured.CRYSTALSHARDS, 5,
                            Encoded.SHIELDDENSITYREPORTS, 3,
                            Encoded.SCRAMBLEDEMISSIONDATA, 5),
                    Map.of(
                            HorizonsModifier.TARGET_MODULES_MALFUNCTIONS, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                            HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("-10%", false)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.THERMAL_SHOCK, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.THERMAL_SHOCK,
                    Map.of(
                            Manufactured.UNCUTFOCUSCRYSTALS, 5,
                            Manufactured.HEATRESISTANTCERAMICS, 3,
                            Manufactured.CONDUCTIVECOMPONENTS, 3,
                            Raw.TUNGSTEN, 3),
                    Map.of(
                            HorizonsModifier.TARGET_HEAT_INCREASED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                            HorizonsModifier.DAMAGE, new HorizonsModifierValue("-10%", false)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.OVERSIZED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.OVERSIZED,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.MECHANICALCOMPONENTS, 3,
                            Raw.RUTHENIUM, 1),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+5%", false),
                            HorizonsModifier.DAMAGE, new HorizonsModifierValue("+3%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.STRIPPED_DOWN, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.STRIPPED_DOWN,
                    Map.of(
                            Manufactured.SALVAGEDALLOYS, 5,
                            Raw.CARBON, 5,
                            Raw.TIN, 1),
                    Map.of(
                            HorizonsModifier.MASS, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.FLOW_CONTROL, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.FLOW_CONTROL,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.HYBRIDCAPACITORS, 3,
                            Encoded.EMBEDDEDFIRMWARE, 1),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.DOUBLE_BRACED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.DOUBLE_BRACED,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.COMPACTCOMPOSITES, 5,
                            Raw.VANADIUM, 3),
                    Map.of(
                            HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+15%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)),
            HorizonsBlueprintType.MULTI_SERVOS, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.MULTI_SERVOS,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.FOCUSCRYSTALS, 4,
                            Manufactured.CONDUCTIVEPOLYMERS, 2,
                            Manufactured.CONFIGURABLECOMPONENTS, 2),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+5%", false),
                            HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("+3%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> MULTI_CANNON = Map.ofEntries(
            Map.entry(
                    HorizonsBlueprintType.AUTO_LOADER, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MULTI_CANNON, HorizonsBlueprintType.AUTO_LOADER,
                            Map.of(
                                    Manufactured.MECHANICALEQUIPMENT, 4,
                                    Manufactured.MECHANICALCOMPONENTS, 3,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 3),
                            Map.of(
                                    HorizonsModifier.AUTO_RELOAD_WHILE_FIRING, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.ZACARIAH_NEMO))),
            Map.entry(
                    HorizonsBlueprintType.CORROSIVE_SHELL, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MULTI_CANNON, HorizonsBlueprintType.CORROSIVE_SHELL,
                            Map.of(
                                    Manufactured.CHEMICALSTORAGEUNITS, 5,
                                    Manufactured.PRECIPITATEDALLOYS, 4,
                                    Raw.ARSENIC, 3),
                            Map.of(
                                    HorizonsModifier.TARGET_ARMOR_HARDNESS_REDUCED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                                    HorizonsModifier.AMMO_CAPACITY, new HorizonsModifierValue("-20%", false)),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.ZACARIAH_NEMO))),
            Map.entry(
                    HorizonsBlueprintType.EMISSIVE_MUNITIONS, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MULTI_CANNON, HorizonsBlueprintType.EMISSIVE_MUNITIONS,
                            Map.of(
                                    Manufactured.MECHANICALEQUIPMENT, 4,
                                    Encoded.EMISSIONDATA, 3,
                                    Manufactured.HEATEXCHANGERS, 3,
                                    Raw.MANGANESE, 3),
                            Map.of(
                                    HorizonsModifier.TARGET_SIGNATURE_INCREASED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+100%", false)),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.ZACARIAH_NEMO))),
            Map.entry(
                    HorizonsBlueprintType.THERMAL_SHOCK, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MULTI_CANNON, HorizonsBlueprintType.THERMAL_SHOCK,
                            Map.of(
                                    Manufactured.UNCUTFOCUSCRYSTALS, 5,
                                    Manufactured.HEATRESISTANTCERAMICS, 3,
                                    Manufactured.CONDUCTIVECOMPONENTS, 3,
                                    Raw.TUNGSTEN, 3),
                            Map.of(
                                    HorizonsModifier.TARGET_HEAT_INCREASED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                                    HorizonsModifier.DAMAGE, new HorizonsModifierValue("-10%", false)),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.ZACARIAH_NEMO))),
            Map.entry(
                    HorizonsBlueprintType.INCENDIARY_ROUNDS, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MULTI_CANNON, HorizonsBlueprintType.INCENDIARY_ROUNDS,
                            Map.of(
                                    Manufactured.HEATCONDUCTIONWIRING, 5,
                                    Raw.PHOSPHORUS, 5,
                                    Raw.SULPHUR, 5,
                                    Manufactured.PHASEALLOYS, 3),
                            Map.of(
                                    HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("-5%", false),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+200%", false)),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.ZACARIAH_NEMO))),
            Map.entry(
                    HorizonsBlueprintType.SMART_ROUNDS, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MULTI_CANNON, HorizonsBlueprintType.SMART_ROUNDS,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 5,
                                    Encoded.SECURITYFIRMWARE, 3,
                                    Encoded.DECODEDEMISSIONDATA, 3,
                                    Encoded.SCANDATABANKS, 3),
                            Map.of(
                                    HorizonsModifier.NO_DAMAGE_TO_UNTARGETED_SHIPS, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.ZACARIAH_NEMO))),
            Map.entry(
                    HorizonsBlueprintType.FLOW_CONTROL, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MULTI_CANNON, HorizonsBlueprintType.FLOW_CONTROL,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 5,
                                    Manufactured.HYBRIDCAPACITORS, 3,
                                    Encoded.EMBEDDEDFIRMWARE, 1),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-10%", true)),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.ZACARIAH_NEMO))),
            Map.entry(
                    HorizonsBlueprintType.DOUBLE_BRACED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MULTI_CANNON, HorizonsBlueprintType.DOUBLE_BRACED,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 5,
                                    Manufactured.COMPACTCOMPOSITES, 5,
                                    Raw.VANADIUM, 3),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+15%", true)),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.ZACARIAH_NEMO))),
            Map.entry(
                    HorizonsBlueprintType.STRIPPED_DOWN, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MULTI_CANNON, HorizonsBlueprintType.STRIPPED_DOWN,
                            Map.of(
                                    Manufactured.SALVAGEDALLOYS, 5,
                                    Raw.CARBON, 5,
                                    Raw.TIN, 1),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-10%", true)),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.ZACARIAH_NEMO))),
            Map.entry(
                    HorizonsBlueprintType.OVERSIZED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MULTI_CANNON, HorizonsBlueprintType.OVERSIZED,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 5,
                                    Manufactured.MECHANICALCOMPONENTS, 3,
                                    Raw.RUTHENIUM, 1),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+5%", false),
                                    HorizonsModifier.DAMAGE, new HorizonsModifierValue("+3%", true)),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.ZACARIAH_NEMO))),
            Map.entry(
                    HorizonsBlueprintType.MULTI_SERVOS, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MULTI_CANNON, HorizonsBlueprintType.MULTI_SERVOS,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 5,
                                    Manufactured.FOCUSCRYSTALS, 4,
                                    Manufactured.CONDUCTIVEPOLYMERS, 2,
                                    Manufactured.CONFIGURABLECOMPONENTS, 2),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+5%", false),
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("+3%", true)),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.ZACARIAH_NEMO))));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> CANNON = Map.ofEntries(
            Map.entry(
                    HorizonsBlueprintType.AUTO_LOADER, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.AUTO_LOADER,
                            Map.of(
                                    Manufactured.MECHANICALEQUIPMENT, 4,
                                    Manufactured.MECHANICALCOMPONENTS, 3,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 3),
                            Map.of(
                                    HorizonsModifier.AUTO_RELOAD_WHILE_FIRING, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE))),
            Map.entry(
                    HorizonsBlueprintType.DISPERSAL_FIELD, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.DISPERSAL_FIELD,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 5,
                                    Manufactured.HYBRIDCAPACITORS, 5,
                                    Encoded.ARCHIVEDEMISSIONDATA, 5,
                                    Manufactured.WORNSHIELDEMITTERS, 5),
                            Map.of(
                                    HorizonsModifier.TARGET_GIMBAL_TURRET_TRACKING_REDUCED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE))),
            Map.entry(
                    HorizonsBlueprintType.FORCE_SHELL, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.FORCE_SHELL,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 5,
                                    Raw.ZINC, 5,
                                    Manufactured.PHASEALLOYS, 3,
                                    Manufactured.HEATCONDUCTIONWIRING, 3),
                            Map.of(
                                    HorizonsModifier.TARGET_PUSHED_OFF_COURSE, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                                    HorizonsModifier.SHOT_SPEED, new HorizonsModifierValue("-17%", false)),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE))),
            Map.entry(
                    HorizonsBlueprintType.HIGH_YIELD_SHELL, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.HIGH_YIELD_SHELL,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 5,
                                    Manufactured.PROTOLIGHTALLOYS, 3,
                                    Manufactured.CHEMICALMANIPULATORS, 3,
                                    Raw.NICKEL, 5),
                            Map.of(
                                    HorizonsModifier.DAMAGE_PARTIALLY_EXPLOSIVE, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("-10%", false)),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE))),
            Map.entry(
                    HorizonsBlueprintType.SMART_ROUNDS, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.SMART_ROUNDS,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 5,
                                    Encoded.SECURITYFIRMWARE, 3,
                                    Encoded.DECODEDEMISSIONDATA, 3,
                                    Encoded.SCANDATABANKS, 3),
                            Map.of(
                                    HorizonsModifier.NO_DAMAGE_TO_UNTARGETED_SHIPS, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE))),
            Map.entry(
                    HorizonsBlueprintType.THERMAL_CASCADE, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.THERMAL_CASCADE,
                            Map.of(
                                    Manufactured.HEATCONDUCTIONWIRING, 5,
                                    Manufactured.HYBRIDCAPACITORS, 4,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 3,
                                    Raw.PHOSPHORUS, 5),
                            Map.of(
                                    HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE))),
            Map.entry(
                    HorizonsBlueprintType.OVERSIZED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.OVERSIZED,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 5,
                                    Manufactured.MECHANICALCOMPONENTS, 3,
                                    Raw.RUTHENIUM, 1),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+5%", false),
                                    HorizonsModifier.DAMAGE, new HorizonsModifierValue("+3%", true)),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE))),
            Map.entry(
                    HorizonsBlueprintType.STRIPPED_DOWN, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.STRIPPED_DOWN,
                            Map.of(
                                    Manufactured.SALVAGEDALLOYS, 5,
                                    Raw.CARBON, 5,
                                    Raw.TIN, 1),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-10%", true)),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE))),
            Map.entry(
                    HorizonsBlueprintType.FLOW_CONTROL, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.FLOW_CONTROL,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 5,
                                    Manufactured.HYBRIDCAPACITORS, 3,
                                    Encoded.EMBEDDEDFIRMWARE, 1),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-10%", true)),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE))),
            Map.entry(
                    HorizonsBlueprintType.DOUBLE_BRACED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.DOUBLE_BRACED,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 5,
                                    Manufactured.COMPACTCOMPOSITES, 5,
                                    Raw.VANADIUM, 3),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+15%", true)),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE))),
            Map.entry(
                    HorizonsBlueprintType.MULTI_SERVOS, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.MULTI_SERVOS,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 5,
                                    Manufactured.FOCUSCRYSTALS, 4,
                                    Manufactured.CONDUCTIVEPOLYMERS, 2,
                                    Manufactured.CONFIGURABLECOMPONENTS, 2),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+5%", false),
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("+3%", true)),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE))));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> FRAGMENT_CANNON = Map.of(
            HorizonsBlueprintType.CORROSIVE_SHELL, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.CORROSIVE_SHELL,
                    Map.of(
                            Manufactured.CHEMICALSTORAGEUNITS, 5,
                            Manufactured.PRECIPITATEDALLOYS, 4,
                            Raw.ARSENIC, 3),
                    Map.of(
                            HorizonsModifier.TARGET_ARMOR_HARDNESS_REDUCED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                            HorizonsModifier.AMMO_CAPACITY, new HorizonsModifierValue("-20%", false)),
                    List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.ZACARIAH_NEMO)),
            HorizonsBlueprintType.DAZZLE_SHELL, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.DAZZLE_SHELL,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Raw.MANGANESE, 4,
                            Manufactured.HYBRIDCAPACITORS, 5),
                    Map.of(
                            HorizonsModifier.TARGET_SENSOR_ACUITY_REDUCED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                    List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.ZACARIAH_NEMO)),
            HorizonsBlueprintType.DRAG_MUNITION, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.DRAG_MUNITION,
                    Map.of(
                            Raw.CARBON, 5,
                            Manufactured.GRIDRESISTORS, 5,
                            Raw.MOLYBDENUM, 2),
                    Map.of(
                            HorizonsModifier.TARGET_SPEED_REDUCED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                    List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.ZACARIAH_NEMO)),
            HorizonsBlueprintType.INCENDIARY_ROUNDS, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.INCENDIARY_ROUNDS,
                    Map.of(
                            Manufactured.HEATCONDUCTIONWIRING, 5,
                            Raw.PHOSPHORUS, 5,
                            Raw.SULPHUR, 5,
                            Manufactured.PHASEALLOYS, 3),
                    Map.of(
                            HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                            HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("-5%", false),
                            HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+200%", false)),
                    List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.ZACARIAH_NEMO)),
            HorizonsBlueprintType.SCREENING_SHELL, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.SCREENING_SHELL,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Encoded.SHIELDCYCLERECORDINGS, 5,
                            Encoded.CONSUMERFIRMWARE, 5,
                            Raw.NIOBIUM, 3),
                    Map.of(
                            HorizonsModifier.EFFECTIVENESS_INCREASE_AGAINST_MUNITIONS, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                            HorizonsModifier.RELOAD_TIME, new HorizonsModifierValue("-50%", true)),
                    List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.ZACARIAH_NEMO)),
            HorizonsBlueprintType.OVERSIZED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.OVERSIZED,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.MECHANICALCOMPONENTS, 3,
                            Raw.RUTHENIUM, 1),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+5%", false),
                            HorizonsModifier.DAMAGE, new HorizonsModifierValue("+3%", true)),
                    List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.ZACARIAH_NEMO)),
            HorizonsBlueprintType.STRIPPED_DOWN, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.STRIPPED_DOWN,
                    Map.of(
                            Manufactured.SALVAGEDALLOYS, 5,
                            Raw.CARBON, 5,
                            Raw.TIN, 1),
                    Map.of(
                            HorizonsModifier.MASS, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.ZACARIAH_NEMO)),
            HorizonsBlueprintType.DOUBLE_BRACED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.DOUBLE_BRACED,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.COMPACTCOMPOSITES, 5,
                            Raw.VANADIUM, 3),
                    Map.of(
                            HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+15%", true)),
                    List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.ZACARIAH_NEMO)),
            HorizonsBlueprintType.FLOW_CONTROL, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.FLOW_CONTROL,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.HYBRIDCAPACITORS, 3,
                            Encoded.EMBEDDEDFIRMWARE, 1),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.ZACARIAH_NEMO)),
            HorizonsBlueprintType.MULTI_SERVOS, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.MULTI_SERVOS,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.FOCUSCRYSTALS, 4,
                            Manufactured.CONDUCTIVEPOLYMERS, 2,
                            Manufactured.CONFIGURABLECOMPONENTS, 2),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+5%", false),
                            HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("+3%", true)),
                    List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.ZACARIAH_NEMO)));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> MISSILE_RACK = Map.ofEntries(
            Map.entry(
                    HorizonsBlueprintType.DRAG_MUNITION/*(SEEKER_ONLY)*/, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MISSILE_RACK, HorizonsBlueprintType.DRAG_MUNITION,
                            Map.of(
                                    Raw.CARBON, 5,
                                    Manufactured.GRIDRESISTORS, 5,
                                    Raw.MOLYBDENUM, 2),
                            Map.of(
                                    HorizonsModifier.TARGET_SPEED_REDUCED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK))),
            Map.entry(
                    HorizonsBlueprintType.EMISSIVE_MUNITIONS, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MISSILE_RACK, HorizonsBlueprintType.EMISSIVE_MUNITIONS,
                            Map.of(
                                    Manufactured.MECHANICALEQUIPMENT, 4,
                                    Encoded.EMISSIONDATA, 3,
                                    Manufactured.HEATEXCHANGERS, 3,
                                    Raw.MANGANESE, 3),
                            Map.of(
                                    HorizonsModifier.TARGET_SIGNATURE_INCREASED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+100%", false)),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK))),
            Map.entry(
                    HorizonsBlueprintType.OVERLOAD_MUNITIONS, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MISSILE_RACK, HorizonsBlueprintType.OVERLOAD_MUNITIONS,
                            Map.of(
                                    Manufactured.FILAMENTCOMPOSITES, 5,
                                    Encoded.ENCRYPTIONCODES, 4,
                                    Encoded.SHIELDPATTERNANALYSIS, 2,
                                    Raw.GERMANIUM, 3),
                            Map.of(
                                    HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK))),
            Map.entry(
                    HorizonsBlueprintType.THERMAL_CASCADE, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MISSILE_RACK, HorizonsBlueprintType.THERMAL_CASCADE,
                            Map.of(
                                    Manufactured.HEATCONDUCTIONWIRING, 5,
                                    Manufactured.HYBRIDCAPACITORS, 4,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 3,
                                    Raw.PHOSPHORUS, 5),
                            Map.of(
                                    HorizonsModifier.SHIELDED_TARGET_HEAT_INCREASED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK))),
            Map.entry(
                    HorizonsBlueprintType.PENETRATOR_MUNITIONS/*(DUMBFIRE_ONLY)*/, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MISSILE_RACK, HorizonsBlueprintType.PENETRATOR_MUNITIONS,
                            Map.of(
                                    Manufactured.GALVANISINGALLOYS, 5,
                                    Manufactured.ELECTROCHEMICALARRAYS, 3,
                                    Raw.ZIRCONIUM, 3),
                            Map.of(
                                    HorizonsModifier.TARGET_MODULE_DAMAGE, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK))),
            Map.entry(
                    HorizonsBlueprintType.FSD_INTERRUPT/*(DUMBFIRE_ONLY)*/, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MISSILE_RACK, HorizonsBlueprintType.FSD_INTERRUPT,
                            Map.of(
                                    Encoded.WAKESOLUTIONS, 3,
                                    Encoded.FSDTELEMETRY, 5,
                                    Manufactured.MECHANICALEQUIPMENT, 5,
                                    Manufactured.CONFIGURABLECOMPONENTS, 3),
                            Map.of(
                                    HorizonsModifier.TARGET_FSD_REBOOTS, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                                    HorizonsModifier.DAMAGE, new HorizonsModifierValue("-30%", false),
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("-50%", false)),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK))),
            Map.entry(
                    HorizonsBlueprintType.OVERSIZED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MISSILE_RACK, HorizonsBlueprintType.OVERSIZED,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 5,
                                    Manufactured.MECHANICALCOMPONENTS, 3,
                                    Raw.RUTHENIUM, 1),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+5%", false),
                                    HorizonsModifier.DAMAGE, new HorizonsModifierValue("+3%", true)),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK))),
            Map.entry(
                    HorizonsBlueprintType.STRIPPED_DOWN, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MISSILE_RACK, HorizonsBlueprintType.STRIPPED_DOWN,
                            Map.of(
                                    Manufactured.SALVAGEDALLOYS, 5,
                                    Raw.CARBON, 5,
                                    Raw.TIN, 1),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-10%", true)),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK))),
            Map.entry(
                    HorizonsBlueprintType.FLOW_CONTROL, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MISSILE_RACK, HorizonsBlueprintType.FLOW_CONTROL,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 5,
                                    Manufactured.HYBRIDCAPACITORS, 3,
                                    Encoded.EMBEDDEDFIRMWARE, 1),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-10%", true)),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK))),
            Map.entry(
                    HorizonsBlueprintType.DOUBLE_BRACED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MISSILE_RACK, HorizonsBlueprintType.DOUBLE_BRACED,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 5,
                                    Manufactured.COMPACTCOMPOSITES, 5,
                                    Raw.VANADIUM, 3),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+15%", true)),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK))),
            Map.entry(
                    HorizonsBlueprintType.MULTI_SERVOS, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MISSILE_RACK, HorizonsBlueprintType.MULTI_SERVOS,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 5,
                                    Manufactured.FOCUSCRYSTALS, 4,
                                    Manufactured.CONDUCTIVEPOLYMERS, 2,
                                    Manufactured.CONFIGURABLECOMPONENTS, 2),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+5%", false),
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("+3%", true)),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK))));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> TORPEDO_PYLON = Map.of(
            HorizonsBlueprintType.MASS_LOCK_MUNITION, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.MASS_LOCK_MUNITION,
                    Map.of(
                            Manufactured.MECHANICALEQUIPMENT, 5,
                            Manufactured.HIGHDENSITYCOMPOSITES, 3,
                            Encoded.SHIELDPATTERNANALYSIS, 3),
                    Map.of(
                            HorizonsModifier.TARGET_FSD_INHIBITED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                    List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
            HorizonsBlueprintType.PENETRATOR_PAYLOAD, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.PENETRATOR_PAYLOAD,
                    Map.of(
                            Manufactured.MECHANICALCOMPONENTS, 3,
                            Raw.TUNGSTEN, 3,
                            Encoded.BULKSCANDATA, 5,
                            Raw.SELENIUM, 3),
                    Map.of(
                            HorizonsModifier.TARGET_MODULE_DAMAGE, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                    List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
            HorizonsBlueprintType.REVERBERATING_CASCADE, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.REVERBERATING_CASCADE,
                    Map.of(
                            Manufactured.CONFIGURABLECOMPONENTS, 2,
                            Encoded.SCANDATABANKS, 3,
                            Manufactured.FILAMENTCOMPOSITES, 4,
                            Raw.CHROMIUM, 4),
                    Map.of(
                            HorizonsModifier.TARGET_SHIELD_GENERATOR_DAMAGED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                    List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
            HorizonsBlueprintType.FLOW_CONTROL, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.FLOW_CONTROL,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.HYBRIDCAPACITORS, 3,
                            Encoded.EMBEDDEDFIRMWARE, 1),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
            HorizonsBlueprintType.OVERSIZED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.OVERSIZED,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.MECHANICALCOMPONENTS, 3,
                            Raw.RUTHENIUM, 1),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+5%", false),
                            HorizonsModifier.DAMAGE, new HorizonsModifierValue("+3%", true)),
                    List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
            HorizonsBlueprintType.STRIPPED_DOWN, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.STRIPPED_DOWN,
                    Map.of(
                            Manufactured.SALVAGEDALLOYS, 5,
                            Raw.CARBON, 5,
                            Raw.TIN, 1),
                    Map.of(
                            HorizonsModifier.MASS, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
            HorizonsBlueprintType.DOUBLE_BRACED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.DOUBLE_BRACED,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.COMPACTCOMPOSITES, 5,
                            Raw.VANADIUM, 3),
                    Map.of(
                            HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+15%", true)),
                    List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> MINE_LAUNCHER = Map.of(
            HorizonsBlueprintType.ION_DISRUPTOR, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.ION_DISRUPTOR,
                    Map.of(
                            Raw.SULPHUR, 5,
                            Raw.PHOSPHORUS, 5,
                            Manufactured.CHEMICALDISTILLERY, 3,
                            Manufactured.ELECTROCHEMICALARRAYS, 3),
                    Map.of(
                            HorizonsModifier.TARGET_THRUSTERS_REBOOT, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                    List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
            HorizonsBlueprintType.OVERLOAD_MUNITIONS, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.OVERLOAD_MUNITIONS,
                    Map.of(
                            Manufactured.FILAMENTCOMPOSITES, 5,
                            Encoded.ENCRYPTIONCODES, 4,
                            Encoded.SHIELDPATTERNANALYSIS, 2,
                            Raw.GERMANIUM, 3),
                    Map.of(
                            HorizonsModifier.DAMAGE_PARTIALLY_THERMAL, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                    List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
            HorizonsBlueprintType.RADIANT_CANISTER, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.RADIANT_CANISTER,
                    Map.of(
                            Raw.POLONIUM, 1,
                            Manufactured.PHASEALLOYS, 3,
                            Manufactured.HEATDISPERSIONPLATE, 4),
                    Map.of(
                            HorizonsModifier.AREA_HEAT_INCREASED_SENSORS_DISRUPTED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                    List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
            HorizonsBlueprintType.REVERBERATING_CASCADE, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.REVERBERATING_CASCADE,
                    Map.of(
                            Manufactured.CONFIGURABLECOMPONENTS, 2,
                            Encoded.SCANDATABANKS, 3,
                            Manufactured.FILAMENTCOMPOSITES, 4,
                            Raw.CHROMIUM, 4),
                    Map.of(
                            HorizonsModifier.TARGET_SHIELD_GENERATOR_DAMAGED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                    List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
            HorizonsBlueprintType.SHIFT_LOCK_CANISTER, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.SHIFT_LOCK_CANISTER,
                    Map.of(
                            Manufactured.TEMPEREDALLOYS, 5,
                            Encoded.WAKESOLUTIONS, 3,
                            Manufactured.SALVAGEDALLOYS, 5),
                    Map.of(
                            HorizonsModifier.AREA_FSDS_REBOOT, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                    List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
            HorizonsBlueprintType.EMISSIVE_MUNITIONS, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.EMISSIVE_MUNITIONS,
                    Map.of(
                            Manufactured.MECHANICALEQUIPMENT, 4,
                            Encoded.EMISSIONDATA, 3,
                            Manufactured.HEATEXCHANGERS, 3,
                            Raw.MANGANESE, 3),
                    Map.of(
                            HorizonsModifier.TARGET_SIGNATURE_INCREASED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                            HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+100%", false)),
                    List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
            HorizonsBlueprintType.FLOW_CONTROL, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.FLOW_CONTROL,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.HYBRIDCAPACITORS, 3,
                            Encoded.EMBEDDEDFIRMWARE, 1),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
            HorizonsBlueprintType.OVERSIZED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.OVERSIZED,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.MECHANICALCOMPONENTS, 3,
                            Raw.RUTHENIUM, 1),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+5%", false),
                            HorizonsModifier.DAMAGE, new HorizonsModifierValue("+3%", true)),
                    List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
            HorizonsBlueprintType.STRIPPED_DOWN, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.STRIPPED_DOWN,
                    Map.of(
                            Manufactured.SALVAGEDALLOYS, 5,
                            Raw.CARBON, 5,
                            Raw.TIN, 1),
                    Map.of(
                            HorizonsModifier.MASS, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
            HorizonsBlueprintType.DOUBLE_BRACED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.MINE_LAUNCHER, HorizonsBlueprintType.DOUBLE_BRACED,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.COMPACTCOMPOSITES, 5,
                            Raw.VANADIUM, 3),
                    Map.of(
                            HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+15%", true)),
                    List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> PLASMA_ACCELERATOR = Map.ofEntries(
            Map.entry(
                    HorizonsBlueprintType.DAZZLE_SHELL, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.PLASMA_ACCELERATOR, HorizonsBlueprintType.DAZZLE_SHELL,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 5,
                                    Raw.MANGANESE, 4,
                                    Manufactured.HYBRIDCAPACITORS, 5,
                                    Manufactured.MECHANICALCOMPONENTS, 5),
                            Map.of(
                                    HorizonsModifier.TARGET_SENSOR_ACUITY_REDUCED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                            List.of(Engineer.ETIENNE_DORN, Engineer.ZACARIAH_NEMO, Engineer.BILL_TURNER))),
            Map.entry(
                    HorizonsBlueprintType.DISPERSAL_FIELD, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.PLASMA_ACCELERATOR, HorizonsBlueprintType.DISPERSAL_FIELD,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 5,
                                    Manufactured.HYBRIDCAPACITORS, 5,
                                    Encoded.ARCHIVEDEMISSIONDATA, 5,
                                    Manufactured.WORNSHIELDEMITTERS, 5),
                            Map.of(
                                    HorizonsModifier.TARGET_GIMBAL_TURRET_TRACKING_REDUCED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                            List.of(Engineer.ETIENNE_DORN, Engineer.ZACARIAH_NEMO, Engineer.BILL_TURNER))),
            Map.entry(
                    HorizonsBlueprintType.TARGET_LOCK_BREAKER, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.PLASMA_ACCELERATOR, HorizonsBlueprintType.TARGET_LOCK_BREAKER,
                            Map.of(
                                    Raw.SELENIUM, 5,
                                    Encoded.SECURITYFIRMWARE, 3,
                                    Encoded.ADAPTIVEENCRYPTORS, 1),
                            Map.of(
                                    HorizonsModifier.TARGET_LOSES_TARGET_LOCK, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                            List.of(Engineer.ETIENNE_DORN, Engineer.ZACARIAH_NEMO, Engineer.BILL_TURNER))),
            Map.entry(
                    HorizonsBlueprintType.THERMAL_CONDUIT, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.PLASMA_ACCELERATOR, HorizonsBlueprintType.THERMAL_CONDUIT,
                            Map.of(
                                    Manufactured.HEATDISPERSIONPLATE, 5,
                                    Raw.SULPHUR, 5,
                                    Manufactured.TEMPEREDALLOYS, 5),
                            Map.of(
                                    HorizonsModifier.DAMAGE_INCREASES_WITH_HEAT_LEVEL, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true)),
                            List.of(Engineer.ETIENNE_DORN, Engineer.ZACARIAH_NEMO, Engineer.BILL_TURNER))),
            Map.entry(
                    HorizonsBlueprintType.PHASING_SEQUENCE, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.PLASMA_ACCELERATOR, HorizonsBlueprintType.PHASING_SEQUENCE,
                            Map.of(
                                    Manufactured.FOCUSCRYSTALS, 5,
                                    Encoded.SHIELDPATTERNANALYSIS, 3,
                                    Raw.NIOBIUM, 3,
                                    Manufactured.CONFIGURABLECOMPONENTS, 3),
                            Map.of(
                                    HorizonsModifier.PART_OF_DAMAGE_THROUGH_SHIELDS, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                                    HorizonsModifier.DAMAGE, new HorizonsModifierValue("-10%", false)),
                            List.of(Engineer.ETIENNE_DORN, Engineer.ZACARIAH_NEMO, Engineer.BILL_TURNER))),
            Map.entry(
                    HorizonsBlueprintType.PLASMA_SLUG, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.PLASMA_ACCELERATOR, HorizonsBlueprintType.PLASMA_SLUG,
                            Map.of(
                                    Manufactured.HEATEXCHANGERS, 3,
                                    Encoded.EMBEDDEDFIRMWARE, 2,
                                    Manufactured.REFINEDFOCUSCRYSTALS, 2,
                                    Raw.MERCURY, 4),
                            Map.of(
                                    HorizonsModifier.RELOAD_FROM_SHIP_FUEL, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                                    HorizonsModifier.DAMAGE, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.AMMO_CAPACITY, new HorizonsModifierValue("-100%", false)),
                            List.of(Engineer.ETIENNE_DORN, Engineer.ZACARIAH_NEMO, Engineer.BILL_TURNER))),
            Map.entry(
                    HorizonsBlueprintType.OVERSIZED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.PLASMA_ACCELERATOR, HorizonsBlueprintType.OVERSIZED,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 5,
                                    Manufactured.MECHANICALCOMPONENTS, 3,
                                    Raw.RUTHENIUM, 1),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+5%", false),
                                    HorizonsModifier.DAMAGE, new HorizonsModifierValue("+3%", true)),
                            List.of(Engineer.ETIENNE_DORN, Engineer.ZACARIAH_NEMO, Engineer.BILL_TURNER))),
            Map.entry(
                    HorizonsBlueprintType.FLOW_CONTROL, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.PLASMA_ACCELERATOR, HorizonsBlueprintType.FLOW_CONTROL,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 5,
                                    Manufactured.HYBRIDCAPACITORS, 3,
                                    Encoded.EMBEDDEDFIRMWARE, 1),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-10%", true)),
                            List.of(Engineer.ETIENNE_DORN, Engineer.ZACARIAH_NEMO, Engineer.BILL_TURNER))),
            Map.entry(
                    HorizonsBlueprintType.STRIPPED_DOWN, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.PLASMA_ACCELERATOR, HorizonsBlueprintType.STRIPPED_DOWN,
                            Map.of(
                                    Manufactured.SALVAGEDALLOYS, 5,
                                    Raw.CARBON, 5,
                                    Raw.TIN, 1),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-10%", true)),
                            List.of(Engineer.ETIENNE_DORN, Engineer.ZACARIAH_NEMO, Engineer.BILL_TURNER))),
            Map.entry(
                    HorizonsBlueprintType.DOUBLE_BRACED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.PLASMA_ACCELERATOR, HorizonsBlueprintType.DOUBLE_BRACED,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 5,
                                    Manufactured.COMPACTCOMPOSITES, 5,
                                    Raw.VANADIUM, 3),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+15%", true)),
                            List.of(Engineer.ETIENNE_DORN, Engineer.ZACARIAH_NEMO, Engineer.BILL_TURNER))),
            Map.entry(
                    HorizonsBlueprintType.MULTI_SERVOS, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.PLASMA_ACCELERATOR, HorizonsBlueprintType.MULTI_SERVOS,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 5,
                                    Manufactured.FOCUSCRYSTALS, 4,
                                    Manufactured.CONDUCTIVEPOLYMERS, 2,
                                    Manufactured.CONFIGURABLECOMPONENTS, 2),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+5%", false),
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("+3%", true)),
                            List.of(Engineer.ETIENNE_DORN, Engineer.ZACARIAH_NEMO, Engineer.BILL_TURNER))));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> RAIL_GUN = Map.of(
            HorizonsBlueprintType.FEEDBACK_CASCADE, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.RAIL_GUN, HorizonsBlueprintType.FEEDBACK_CASCADE,
                    Map.of(
                            Encoded.SYMMETRICKEYS, 5,
                            Manufactured.SHIELDEMITTERS, 5,
                            Manufactured.FILAMENTCOMPOSITES, 5),
                    Map.of(
                            HorizonsModifier.TARGET_SHIELD_CELL_DISRUPTED, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                            HorizonsModifier.DAMAGE, new HorizonsModifierValue("-20%", false),
                            HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-40%", true)),
                    List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE)),
            HorizonsBlueprintType.PLASMA_SLUG, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.RAIL_GUN, HorizonsBlueprintType.PLASMA_SLUG,
                    Map.of(
                            Manufactured.HEATEXCHANGERS, 3,
                            Encoded.EMBEDDEDFIRMWARE, 2,
                            Manufactured.REFINEDFOCUSCRYSTALS, 2,
                            Raw.MERCURY, 4),
                    Map.of(
                            HorizonsModifier.RELOAD_FROM_SHIP_FUEL, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                            HorizonsModifier.DAMAGE, new HorizonsModifierValue("-10%", false),
                            HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-40%", true),
                            HorizonsModifier.AMMO_CAPACITY, new HorizonsModifierValue("-100%", false)),
                    List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE)),
            HorizonsBlueprintType.SUPER_PENETRATOR, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.RAIL_GUN, HorizonsBlueprintType.SUPER_PENETRATOR,
                    Map.of(
                            Manufactured.PROTOLIGHTALLOYS, 3,
                            Manufactured.REFINEDFOCUSCRYSTALS, 3,
                            Raw.ZIRCONIUM, 3,
                            Encoded.SHIELDDENSITYREPORTS, 5),
                    Map.of(
                            HorizonsModifier.TARGET_MODULE_DAMAGE, new HorizonsModifierValue(UTF8Constants.CHECK_TRUE, true),
                            HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-40%", true),
                            HorizonsModifier.RELOAD_TIME, new HorizonsModifierValue("+50%", false)),
                    List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE)),
            HorizonsBlueprintType.OVERSIZED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.RAIL_GUN, HorizonsBlueprintType.OVERSIZED,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.MECHANICALCOMPONENTS, 3,
                            Raw.RUTHENIUM, 1),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+5%", false),
                            HorizonsModifier.DAMAGE, new HorizonsModifierValue("+3%", true)),
                    List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE)),
            HorizonsBlueprintType.FLOW_CONTROL, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.RAIL_GUN, HorizonsBlueprintType.FLOW_CONTROL,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.HYBRIDCAPACITORS, 3,
                            Encoded.EMBEDDEDFIRMWARE, 1),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE)),
            HorizonsBlueprintType.STRIPPED_DOWN, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.RAIL_GUN, HorizonsBlueprintType.STRIPPED_DOWN,
                    Map.of(
                            Manufactured.SALVAGEDALLOYS, 5,
                            Raw.CARBON, 5,
                            Raw.TIN, 1),
                    Map.of(
                            HorizonsModifier.MASS, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE)),
            HorizonsBlueprintType.DOUBLE_BRACED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.RAIL_GUN, HorizonsBlueprintType.DOUBLE_BRACED,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.COMPACTCOMPOSITES, 5,
                            Raw.VANADIUM, 3),
                    Map.of(
                            HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+15%", true)),
                    List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE)),
            HorizonsBlueprintType.MULTI_SERVOS, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.RAIL_GUN, HorizonsBlueprintType.MULTI_SERVOS,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 5,
                            Manufactured.FOCUSCRYSTALS, 4,
                            Manufactured.CONDUCTIVEPOLYMERS, 2,
                            Manufactured.CONFIGURABLECOMPONENTS, 2),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+5%", false),
                            HorizonsModifier.RATE_OF_FIRE, new HorizonsModifierValue("+3%", true)),
                    List.of(Engineer.ETIENNE_DORN, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.THE_SARGE)));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> POWER_PLANT = Map.of(
            HorizonsBlueprintType.THERMAL_SPREAD, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.THERMAL_SPREAD,
                    Map.of(
                            Manufactured.GRIDRESISTORS, 5,
                            Raw.VANADIUM, 3,
                            Manufactured.HEATVANES, 1),
                    Map.of(
                            HorizonsModifier.HEAT_EFFICIENCY, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.ETIENNE_DORN, Engineer.FELICITY_FARSEER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
            HorizonsBlueprintType.STRIPPED_DOWN, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.STRIPPED_DOWN,
                    Map.of(
                            Manufactured.GRIDRESISTORS, 5,
                            Raw.VANADIUM, 3,
                            Manufactured.PROTOLIGHTALLOYS, 1),
                    Map.of(
                            HorizonsModifier.MASS, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.ETIENNE_DORN, Engineer.FELICITY_FARSEER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
            HorizonsBlueprintType.MONSTERED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.MONSTERED,
                    Map.of(
                            Manufactured.GRIDRESISTORS, 5,
                            Raw.VANADIUM, 3,
                            Manufactured.POLYMERCAPACITORS, 1),
                    Map.of(
                            HorizonsModifier.MASS, new HorizonsModifierValue("+10%", false),
                            HorizonsModifier.POWER_GENERATION, new HorizonsModifierValue("+5%", true)),
                    List.of(Engineer.ETIENNE_DORN, Engineer.FELICITY_FARSEER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
            HorizonsBlueprintType.DOUBLE_BRACED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.DOUBLE_BRACED,
                    Map.of(
                            Manufactured.GRIDRESISTORS, 5,
                            Raw.VANADIUM, 3,
                            Manufactured.FEDPROPRIETARYCOMPOSITES, 1),
                    Map.of(
                            HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+15%", true)),
                    List.of(Engineer.ETIENNE_DORN, Engineer.FELICITY_FARSEER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> ARMOUR = Map.of(
            HorizonsBlueprintType.ANGLED_PLATING, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.ANGLED_PLATING,
                    Map.of(
                            Manufactured.COMPACTCOMPOSITES, 5,
                            Manufactured.HIGHDENSITYCOMPOSITES, 3,
                            Raw.ZIRCONIUM, 3),
                    Map.of(
                            HorizonsModifier.HULL_BOOST, new HorizonsModifierValue("-3%", false),
                            HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+8%", true)),
                    List.of(Engineer.LIZ_RYDER, Engineer.SELENE_JEAN, Engineer.PETRA_OLMANOVA)),
            HorizonsBlueprintType.LAYERED_PLATING, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.LAYERED_PLATING,
                    Map.of(
                            Manufactured.HEATCONDUCTIONWIRING, 5,
                            Manufactured.HIGHDENSITYCOMPOSITES, 3,
                            Raw.NIOBIUM, 1),
                    Map.of(
                            HorizonsModifier.HULL_BOOST, new HorizonsModifierValue("-3%", false),
                            HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("+8%", true)),
                    List.of(Engineer.LIZ_RYDER, Engineer.SELENE_JEAN, Engineer.PETRA_OLMANOVA)),
            HorizonsBlueprintType.REFLECTIVE_PLATING, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.REFLECTIVE_PLATING,
                    Map.of(
                            Manufactured.COMPACTCOMPOSITES, 5,
                            Manufactured.HEATDISPERSIONPLATE, 3,
                            Manufactured.THERMICALLOYS, 2),
                    Map.of(
                            HorizonsModifier.HULL_BOOST, new HorizonsModifierValue("-3%", false),
                            HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+8%", true)),
                    List.of(Engineer.LIZ_RYDER, Engineer.SELENE_JEAN, Engineer.PETRA_OLMANOVA)),
            HorizonsBlueprintType.DEEP_PLATING, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.DEEP_PLATING,
                    Map.of(
                            Manufactured.COMPACTCOMPOSITES, 5,
                            Manufactured.MECHANICALEQUIPMENT, 3,
                            Raw.MOLYBDENUM, 2),
                    Map.of(
                            HorizonsModifier.HULL_BOOST, new HorizonsModifierValue("+8%", true),
                            HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-3%", false),
                            HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-3%", false),
                            HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-3%", false)),
                    List.of(Engineer.LIZ_RYDER, Engineer.SELENE_JEAN, Engineer.PETRA_OLMANOVA)));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> HULL_REINFORCEMENT_PACKAGE = Map.of(
            HorizonsBlueprintType.ANGLED_PLATING, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.ANGLED_PLATING,
                    Map.of(
                            Manufactured.TEMPEREDALLOYS, 5,
                            Raw.ZIRCONIUM, 3,
                            Raw.CARBON, 5,
                            Manufactured.HIGHDENSITYCOMPOSITES, 3),
                    Map.of(
                            HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("-5%", false),
                            HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+2%", true)),
                    List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
            HorizonsBlueprintType.LAYERED_PLATING, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.LAYERED_PLATING,
                    Map.of(
                            Manufactured.HEATCONDUCTIONWIRING, 5,
                            Manufactured.SHIELDINGSENSORS, 3,
                            Raw.TUNGSTEN, 3),
                    Map.of(
                            HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("-5%", false),
                            HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("+2%", true)),
                    List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
            HorizonsBlueprintType.REFLECTIVE_PLATING, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.REFLECTIVE_PLATING,
                    Map.of(
                            Manufactured.HEATCONDUCTIONWIRING, 5,
                            Manufactured.HEATDISPERSIONPLATE, 3,
                            Manufactured.PROTOLIGHTALLOYS, 1,
                            Raw.ZINC, 4),
                    Map.of(
                            HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("-5%", false),
                            HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+2%", true)),
                    List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
            HorizonsBlueprintType.DEEP_PLATING, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.DEEP_PLATING,
                    Map.of(
                            Manufactured.COMPACTCOMPOSITES, 5,
                            Raw.MOLYBDENUM, 3,
                            Raw.RUTHENIUM, 2),
                    Map.of(
                            HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("+10%", true),
                            HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-2%", false),
                            HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-2%", false),
                            HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-2%", false)),
                    List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> SHIELD_CELL_BANK = Map.of(
            HorizonsBlueprintType.DOUBLE_BRACED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.SHIELD_CELL_BANK, HorizonsBlueprintType.DOUBLE_BRACED,
                    Map.of(
                            Manufactured.CHEMICALSTORAGEUNITS, 5,
                            Raw.CHROMIUM, 3,
                            Raw.YTTRIUM, 1),
                    Map.of(
                            HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+15%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LORI_JAMESON)),
            HorizonsBlueprintType.FLOW_CONTROL, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.SHIELD_CELL_BANK, HorizonsBlueprintType.FLOW_CONTROL,
                    Map.of(
                            Manufactured.CHEMICALSTORAGEUNITS, 5,
                            Raw.CHROMIUM, 3,
                            Manufactured.CONDUCTIVEPOLYMERS, 1),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LORI_JAMESON)),
            HorizonsBlueprintType.STRIPPED_DOWN, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.SHIELD_CELL_BANK, HorizonsBlueprintType.STRIPPED_DOWN,
                    Map.of(
                            Manufactured.CHEMICALSTORAGEUNITS, 5,
                            Raw.CHROMIUM, 3,
                            Manufactured.PROTOLIGHTALLOYS, 1),
                    Map.of(
                            HorizonsModifier.MASS, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LORI_JAMESON)),
            HorizonsBlueprintType.BOSS_CELLS, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.SHIELD_CELL_BANK, HorizonsBlueprintType.BOSS_CELLS,
                    Map.of(
                            Manufactured.CHEMICALSTORAGEUNITS, 5,
                            Raw.CHROMIUM, 3,
                            Manufactured.POLYMERCAPACITORS, 1),
                    Map.of(
                            HorizonsModifier.SPIN_UP_TIME, new HorizonsModifierValue("+20%", false),
                            HorizonsModifier.SHIELD_REINFORCEMENT, new HorizonsModifierValue("+5%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LORI_JAMESON)),
            HorizonsBlueprintType.RECYCLING_CELLS, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.SHIELD_CELL_BANK, HorizonsBlueprintType.RECYCLING_CELLS,
                    Map.of(
                            Manufactured.CHEMICALSTORAGEUNITS, 5,
                            Raw.CHROMIUM, 3,
                            Manufactured.CONFIGURABLECOMPONENTS, 1),
                    Map.of(
                            HorizonsModifier.DURATION, new HorizonsModifierValue("+10%", true),
                            HorizonsModifier.SHIELD_REINFORCEMENT, new HorizonsModifierValue("-5%", false)),
                    List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LORI_JAMESON)));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> SHIELD_BOOSTER = Map.of(
            HorizonsBlueprintType.FORCE_BLOCK, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.FORCE_BLOCK,
                    Map.of(
                            Encoded.SCANARCHIVES, 5,
                            Manufactured.SHIELDINGSENSORS, 3,
                            Encoded.SHIELDPATTERNANALYSIS, 2),
                    Map.of(
                            HorizonsModifier.OPTIMAL_STRENGTH, new HorizonsModifierValue("-1%", false),
                            HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+2%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
            HorizonsBlueprintType.THERMO_BLOCK, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.THERMO_BLOCK,
                    Map.of(
                            Encoded.BULKSCANDATA, 5,
                            Manufactured.CONDUCTIVECERAMICS, 3,
                            Manufactured.HEATVANES, 3),
                    Map.of(
                            HorizonsModifier.OPTIMAL_STRENGTH, new HorizonsModifierValue("-1%", false),
                            HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+2%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
            HorizonsBlueprintType.BLAST_BLOCK, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.BLAST_BLOCK,
                    Map.of(
                            Encoded.SHIELDSOAKANALYSIS, 5,
                            Manufactured.HEATRESISTANTCERAMICS, 3,
                            Manufactured.HEATDISPERSIONPLATE, 3,
                            Raw.SELENIUM, 2),
                    Map.of(
                            HorizonsModifier.OPTIMAL_STRENGTH, new HorizonsModifierValue("-1%", false),
                            HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("+2%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
            HorizonsBlueprintType.FLOW_CONTROL, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.FLOW_CONTROL,
                    Map.of(
                            Encoded.SHIELDSOAKANALYSIS, 5,
                            Encoded.SECURITYFIRMWARE, 3,
                            Manufactured.FOCUSCRYSTALS, 3,
                            Raw.NIOBIUM, 3),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
            HorizonsBlueprintType.DOUBLE_BRACED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.DOUBLE_BRACED,
                    Map.of(
                            Encoded.SHIELDCYCLERECORDINGS, 5,
                            Manufactured.GALVANISINGALLOYS, 3,
                            Manufactured.SHIELDEMITTERS, 3),
                    Map.of(
                            HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+15%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
            HorizonsBlueprintType.SUPER_CAPACITOR, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.SUPER_CAPACITOR,
                    Map.of(
                            Encoded.SHIELDDENSITYREPORTS, 3,
                            Manufactured.COMPACTCOMPOSITES, 5,
                            Raw.CADMIUM, 2),
                    Map.of(
                            HorizonsModifier.OPTIMAL_STRENGTH, new HorizonsModifierValue("+5%", true),
                            HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-2%", false),
                            HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-2%", false),
                            HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-2%", false)),
                    List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> SHIELD_GENERATOR = Map.of(
            HorizonsBlueprintType.HI_CAP, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.HI_CAP,
                    Map.of(
                            Manufactured.WORNSHIELDEMITTERS, 5,
                            Manufactured.UNCUTFOCUSCRYSTALS, 3,
                            Manufactured.CONDUCTIVEPOLYMERS, 1),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+10%", false),
                            HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsModifierValue("+6%", true),
                            HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsModifierValue("+25%", false)),
                    List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
            HorizonsBlueprintType.MULTI_WEAVE, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.MULTI_WEAVE,
                    Map.of(
                            Manufactured.WORNSHIELDEMITTERS, 5,
                            Manufactured.UNCUTFOCUSCRYSTALS, 3,
                            Encoded.SHIELDPATTERNANALYSIS, 1),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+10%", false),
                            HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsModifierValue("+25%", false),
                            HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+3%", true),
                            HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+3%", true),
                            HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("+3%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
            HorizonsBlueprintType.THERMO_BLOCK, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.THERMO_BLOCK,
                    Map.of(
                            Manufactured.WORNSHIELDEMITTERS, 5,
                            Manufactured.UNCUTFOCUSCRYSTALS, 3,
                            Manufactured.HEATVANES, 1),
                    Map.of(
                            HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsModifierValue("-3%", false),
                            HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+8%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
            HorizonsBlueprintType.FAST_CHARGE, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.FAST_CHARGE,
                    Map.of(
                            Manufactured.WORNSHIELDEMITTERS, 5,
                            Manufactured.UNCUTFOCUSCRYSTALS, 3,
                            Manufactured.COMPOUNDSHIELDING, 1),
                    Map.of(
                            HorizonsModifier.REGEN_RATE, new HorizonsModifierValue("+15%", true),
                            HorizonsModifier.BROKEN_REGEN_RATE, new HorizonsModifierValue("+15%", true),
                            HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-1%", false),
                            HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-1%", false),
                            HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-1%", false)),
                    List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
            HorizonsBlueprintType.DOUBLE_BRACED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.DOUBLE_BRACED,
                    Map.of(
                            Manufactured.WORNSHIELDEMITTERS, 5,
                            Manufactured.UNCUTFOCUSCRYSTALS, 3,
                            Manufactured.CONFIGURABLECOMPONENTS, 1),
                    Map.of(
                            HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+15%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
            HorizonsBlueprintType.STRIPPED_DOWN, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.STRIPPED_DOWN,
                    Map.of(
                            Manufactured.WORNSHIELDEMITTERS, 5,
                            Manufactured.UNCUTFOCUSCRYSTALS, 3,
                            Manufactured.PROTOLIGHTALLOYS, 1),
                    Map.of(
                            HorizonsModifier.MASS, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
            HorizonsBlueprintType.FORCE_BLOCK, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.FORCE_BLOCK,
                    Map.of(
                            Manufactured.WORNSHIELDEMITTERS, 5,
                            Manufactured.UNCUTFOCUSCRYSTALS, 3,
                            Encoded.DECODEDEMISSIONDATA, 1),
                    Map.of(
                            HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsModifierValue("-3%", false),
                            HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+8%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
            HorizonsBlueprintType.LO_DRAW, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.LO_DRAW,
                    Map.of(
                            Manufactured.WORNSHIELDEMITTERS, 5,
                            Manufactured.UNCUTFOCUSCRYSTALS, 3,
                            Manufactured.CONDUCTIVEPOLYMERS, 1),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-20%", true),
                            HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsModifierValue("-2%", false),
                            HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsModifierValue("-20%", true),
                            HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-1%", false),
                            HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-1%", false),
                            HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-1%", false)),
                    List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> FRAME_SHIFT_DRIVE = Map.of(
            HorizonsBlueprintType.DEEP_CHARGE, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE, HorizonsBlueprintType.DEEP_CHARGE,
                    Map.of(
                            Encoded.DISRUPTEDWAKEECHOES, 5,
                            Manufactured.GALVANISINGALLOYS, 3,
                            Encoded.HYPERSPACETRAJECTORIES, 1),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+5%", false),
                            HorizonsModifier.MAXIMUM_FUEL_PER_JUMP, new HorizonsModifierValue("+10%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.CHLOE_SEDESI, Engineer.PROFESSOR_PALIN, Engineer.COLONEL_BRIS_DEKKER)),
            HorizonsBlueprintType.DOUBLE_BRACED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE, HorizonsBlueprintType.DOUBLE_BRACED,
                    Map.of(
                            Encoded.DISRUPTEDWAKEECHOES, 5,
                            Manufactured.GALVANISINGALLOYS, 3,
                            Manufactured.CONFIGURABLECOMPONENTS, 1),
                    Map.of(
                            HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+25%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.CHLOE_SEDESI, Engineer.PROFESSOR_PALIN, Engineer.COLONEL_BRIS_DEKKER)),
            HorizonsBlueprintType.STRIPPED_DOWN, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE, HorizonsBlueprintType.STRIPPED_DOWN,
                    Map.of(
                            Encoded.DISRUPTEDWAKEECHOES, 5,
                            Manufactured.GALVANISINGALLOYS, 3,
                            Manufactured.PROTOLIGHTALLOYS, 1),
                    Map.of(
                            HorizonsModifier.MASS, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.CHLOE_SEDESI, Engineer.PROFESSOR_PALIN, Engineer.COLONEL_BRIS_DEKKER)),
            HorizonsBlueprintType.MASS_MANAGER, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE, HorizonsBlueprintType.MASS_MANAGER,
                    Map.of(
                            Encoded.DISRUPTEDWAKEECHOES, 5,
                            Manufactured.GALVANISINGALLOYS, 3,
                            Encoded.HYPERSPACETRAJECTORIES, 1),
                    Map.of(
                            HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-8%", false),
                            HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("+4%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.CHLOE_SEDESI, Engineer.PROFESSOR_PALIN, Engineer.COLONEL_BRIS_DEKKER)),
            HorizonsBlueprintType.THERMAL_SPREAD, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE, HorizonsBlueprintType.THERMAL_SPREAD,
                    Map.of(
                            Encoded.DISRUPTEDWAKEECHOES, 5,
                            Manufactured.GALVANISINGALLOYS, 3,
                            Manufactured.HEATVANES, 1,
                            Manufactured.GRIDRESISTORS, 3),
                    Map.of(
                            HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.CHLOE_SEDESI, Engineer.PROFESSOR_PALIN, Engineer.COLONEL_BRIS_DEKKER)));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> THRUSTERS = Map.of(
            HorizonsBlueprintType.STRIPPED_DOWN, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.THRUSTERS, HorizonsBlueprintType.STRIPPED_DOWN,
                    Map.of(
                            Raw.IRON, 5,
                            Manufactured.HYBRIDCAPACITORS, 3,
                            Manufactured.PROTOLIGHTALLOYS, 1),
                    Map.of(
                            HorizonsModifier.MASS, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),
            HorizonsBlueprintType.DOUBLE_BRACED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.THRUSTERS, HorizonsBlueprintType.DOUBLE_BRACED,
                    Map.of(
                            Raw.IRON, 5,
                            Manufactured.HYBRIDCAPACITORS, 3,
                            Manufactured.FEDPROPRIETARYCOMPOSITES, 1),
                    Map.of(
                            HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+15%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),
            HorizonsBlueprintType.DRIVE_DISTRIBUTORS, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.THRUSTERS, HorizonsBlueprintType.DRIVE_DISTRIBUTORS,
                    Map.of(
                            Raw.IRON, 5,
                            Manufactured.HYBRIDCAPACITORS, 3,
                            Encoded.SECURITYFIRMWARE, 1),
                    Map.of(
                            HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("+10%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),
            HorizonsBlueprintType.DRAG_DRIVES, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.THRUSTERS, HorizonsBlueprintType.DRAG_DRIVES,
                    Map.of(
                            Raw.IRON, 5,
                            Manufactured.HYBRIDCAPACITORS, 3,
                            Encoded.SECURITYFIRMWARE, 1),
                    Map.of(
                            HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsModifierValue("+4%", true),
                            HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+10%", false)),
                    List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),
            HorizonsBlueprintType.THERMAL_SPREAD, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.THRUSTERS, HorizonsBlueprintType.THERMAL_SPREAD,
                    Map.of(
                            Raw.IRON, 5,
                            Manufactured.HYBRIDCAPACITORS, 3,
                            Manufactured.HEATVANES, 1),
                    Map.of(
                            HorizonsModifier.MASS, new HorizonsModifierValue("+5%", false),
                            HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> POWER_DISTRIBUTOR = Map.of(
            HorizonsBlueprintType.FLOW_CONTROL, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.FLOW_CONTROL,
                    Map.of(
                            Raw.PHOSPHORUS, 5,
                            Manufactured.HEATRESISTANTCERAMICS, 3,
                            Manufactured.CONDUCTIVEPOLYMERS, 1),
                    Map.of(
                            HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
            HorizonsBlueprintType.SUPER_CONDUITS, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.SUPER_CONDUITS,
                    Map.of(
                            Raw.PHOSPHORUS, 5,
                            Manufactured.HEATRESISTANTCERAMICS, 3,
                            Encoded.SECURITYFIRMWARE, 1),
                    Map.of(
                            HorizonsModifier.WEAPONS_CAPACITY, new HorizonsModifierValue("-4%", false),
                            HorizonsModifier.WEAPONS_RECHARGE, new HorizonsModifierValue("+4%", true),
                            HorizonsModifier.ENGINES_CAPACITY, new HorizonsModifierValue("-4%", false),
                            HorizonsModifier.ENGINES_RECHARGE, new HorizonsModifierValue("+4%", true),
                            HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsModifierValue("-4%", false),
                            HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsModifierValue("+4%", true)),
                    List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
            HorizonsBlueprintType.CLUSTER_CAPACITOR, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.CLUSTER_CAPACITOR,
                    Map.of(
                            Raw.PHOSPHORUS, 5,
                            Manufactured.HEATRESISTANTCERAMICS, 3,
                            Raw.CADMIUM, 1),
                    Map.of(
                            HorizonsModifier.WEAPONS_CAPACITY, new HorizonsModifierValue("+8%", true),
                            HorizonsModifier.WEAPONS_RECHARGE, new HorizonsModifierValue("-2%", false),
                            HorizonsModifier.ENGINES_CAPACITY, new HorizonsModifierValue("+8%", true),
                            HorizonsModifier.ENGINES_RECHARGE, new HorizonsModifierValue("-2%", false),
                            HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsModifierValue("+8%", true),
                            HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsModifierValue("-2%", false)),
                    List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
            HorizonsBlueprintType.STRIPPED_DOWN, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.STRIPPED_DOWN,
                    Map.of(
                            Raw.PHOSPHORUS, 5,
                            Manufactured.HEATRESISTANTCERAMICS, 3,
                            Manufactured.PROTOLIGHTALLOYS, 1),
                    Map.of(
                            HorizonsModifier.MASS, new HorizonsModifierValue("-10%", true)),
                    List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
            HorizonsBlueprintType.DOUBLE_BRACED, new HorizonsExperimentalEffectBlueprint(HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.DOUBLE_BRACED,
                    Map.of(
                            Raw.PHOSPHORUS, 5,
                            Manufactured.HEATRESISTANTCERAMICS, 3,
                            Manufactured.FEDPROPRIETARYCOMPOSITES, 1),
                    Map.of(
                            HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+15%", true)),
                    List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)));

}
