package nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints.preengineered;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsNumberModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.List;
import java.util.Map;

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AXMissileRackPreEngineeredBlueprints {
    //https://forums.frontier.co.uk/threads/support-the-sirius-alliance-defence-pact-trade.604651/page-4
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> PRE_ENGINEERED_BLUEPRINTS = Map.of(
            HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE_RAPID_FIRE_MODIFICATION,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.AX_MISSILE_RACK, HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE_RAPID_FIRE_MODIFICATION, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+70%", false, percentagePositive(0.0, 0.7)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.0, 0.2)),
                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("+38.6%", true, percentagePositive(0.0, 0.386)),
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.0, 0.03)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.0, 0.2)),
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsNumberModifierValue("+42.9%", true, percentagePositive(0.0, 0.429)),
                                    HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0)),
                                    HorizonsModifier.AMMO_MAXIMUM, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0)),
                                    HorizonsModifier.RELOAD_TIME, new HorizonsNumberModifierValue("-45%", true, percentageNegative(0.0, 0.45)),
                                    HorizonsModifier.JITTER, new HorizonsNumberModifierValue("+0.5°", false, plus(0.5))
                            ),
                            List.of(
                            ),
                            true
                    )
            )
    );
}
//    var BUILTIN_STORED_MODULES = {
//        872200 : { name:"2B Enzyme Missile, HC+Caustic",         modulehash:"FLIqG02G0050ypD4sPc8y00C_00Gu00",                 available:0 }, // CG reward // TODO: get sample to test import
//        862500 : { name:"2E/FD AX Missile, HC+RF",               modulehash:"HL3gG-3G_W90zcQ4sPcAhhXEsPcIupDL000P000UxCpWy00", available:1 }, // Sirius tech broker
//        863300 : { name:"3C/FD AX Missile, HC+RF",               modulehash:"HL4wG-3G_W90zcQ4sPcAhhXEsPcIupDL000P000UxCpWy00", available:1 }, // Sirius tech broker // TODO: get sample to test import
//        881400 : { name:"1D/F Grd Gauss, RF+HC",                 modulehash:"HLXCG-2G0092_166_00A_00Ew7ZHD00L800P600T800YsPc", available:1 }, // Salvation tech broker
//        882200 : { name:"2B/F Grd Gauss, RF+HC",                 modulehash:"HLYSG-2G0092_166_00A_00Ew7ZHD00L800P600T800YsPc", available:1 }, // Salvation tech broker
//        881430 : { name:"1D/F Grd Plasma, OC+Foc",               modulehash:"HLXFG-2Gxq60vBh4zHx8y00Cw00H800KvLL",             available:1 }, // Salvation tech broker
//        882230 : { name:"2B/F Grd Plasma, OC+Foc",               modulehash:"HLYVG-2Gxq60vBh4zHx8y00Cw00H800KvLL",             available:1 }, // Salvation tech broker // TODO: get sample to test import
//        881460 : { name:"1D/F Grd Shard, LR+Foc, Pen",           modulehash:"HLXIG-2G0090y0051Cp98HkDFm0H058K_7YP4JAV700YpXv", available:1 }, // Salvation tech broker
//        882160 : { name:"2A/F Grd Shard, LR+Foc, Pen",           modulehash:"HLYOG-2G0090y0051Cp98HkDFm0H058K_7YP4JAV700YpXv", available:1 }, // Salvation tech broker // TODO: get sample to test import
//        882161 : { name:"2A/F Grd Shard, LR5",                   modulehash:"GLYOG02G0080y0051Cp993DDFm0H058L800Op7uV700",     available:0 }, // TODO: CG reward? // TODO: get sample to test import
//        811410 : { name:"1D/F Abrasion Blaster, LR",             modulehash:"FJprG02G0062y006y00Ey00Iy00L800P800",             available:0 }, // CG reward // TODO: get sample to test import
//        811400 : { name:"1D/F Mining Laser, LR, Incen",          modulehash:"HJpqmF5j3H0072y006y00AkPcEy00I_ezL800PBLL",       available:1 }, // Torval Mining Ltd tech broker
//        822230 : { name:"2B Seeker \"V1\", HC+LW, ThermCas",     modulehash:"HK4lG-3Q_W42-Cp6ypDAsPcIwPc",                     available:1 }, // human tech broker
//        822231 : { name:"2B Seeker, HC+RF, Drag",                modulehash:"HK4lG-2R00612008u00GwghUsPcX400b400",             available:0 }, // CG reward // TODO: get sample to test import
//        722500 : { name:"2E/F Multi-cannon, RF+HC, Phasing",     modulehash:"HHewm1WaCS007Uy00Yuaab600f466n600soPcv400",       available:0 }, // CG reward
//        842200 : { name:"2B/F Rail, LR+HC, FeedCas",             modulehash:"FKZyG03I0080-Cp8zCpT000Yyv4b000f000iu00r900",     available:0 }, // CG reward // TODO: get sample to test import
//
//        510600 : { name:"0F ECM, LW+Shd",                        modulehash:"FCTqG03G0032_pD50009000",                         available:0 }, // CG reward // TODO: get sample to test import
//        520900 : { name:"0I Heat Sink \"Sirius\", ACx2",         modulehash:"HCjwG-2G002P000S_00",                             available:1 }, // Sirus tech broker
//        570100 : { name:"0A KWS, FS+LR",                         modulehash:"FDwoG03G0056y008y00GzCpMupDQ_Pc",                 available:0 }, // CG reward // TODO: get sample to test import
//        530900 : { name:"0I/T Point Defence, Foc+LW",            modulehash:"FCzYG05G0042_pD6y00GkPcL000",                     available:0 }, // CG reward // TODO: get sample to test import
//
//        413100 : { name:"3A Power Plant, AR+OC",                 modulehash:"FA5UG03G0040sPc4-cQ8yAFCqAF",                     available:0 }, // CG reward // TODO: get sample to test import
//        413101 : { name:"3A Power Plant, OC",                    modulehash:"HA5UG-7G0036upD8ypDCvcQ",                         available:0 }, // CG reward // TODO: get sample to test import
//        414101 : { name:"4A Power Plant, OC",                    modulehash:"HA72G-7G0036upD8ypDCvcQ",                         available:0 }, // CG reward // TODO: get sample to test import
//        415101 : { name:"5A Power Plant, OC",                    modulehash:"HA8cG-7G0036upD8ypDCvcQ",                         available:0 }, // CG reward // TODO: get sample to test import
//        433100 : { name:"3A FSD, IR+FB",                         modulehash:"HAakG-5G_W60upD6upD8qpDE_PcGzcQKsPc",             available:0 }, // CG reward // TODO: get sample to test import
//        434100 : { name:"4A FSD, IR+FB",                         modulehash:"HAcIG-5G_W60upD6upD8qpDE_PcGzcQKsPc",             available:0 }, // CG reward // TODO: get sample to test import
//        435100 : { name:"5A FSD \"V1\", IR+FB",                  modulehash:"HAdsG-5G_W60upD6upD8qpDE_PcGzcQKsPc",             available:1 }, // human tech broker
//        436100 : { name:"6A FSD, IR+FB",                         modulehash:"HAfQG-5G_W60upD6upD8qpDE_PcGzcQKsPc",             available:0 }, // CG reward // TODO: get sample to test import
//
//        5510 : { name:"5E Anti-Corrosion Cargo (32T)",         modulehash:"H08d00",                                          available:0 }, // CG reward
//        6510 : { name:"6E Anti-Corrosion Cargo (64T)",         modulehash:"H0AB00",                                          available:0 }, // CG reward
//
//        303100 : { name:"3A Shield Gen, KR+TR",                  modulehash:"F7PcG05G0044sPc8wPccupDgvcQ",                     available:0 }, // CG reward // TODO: get sample to test import
//        111300 : { name:"1I DSS \"V1\", ERx2",                   modulehash:"H2jwG-9G_W1P000",                                 available:1 }, // human tech broker
//        };


//Focused/Lightweight Point Defense Turret (2 available, 20 months ago)
//
//        Armoured/Overcharged 3A Power Plant (18 months ago)
//
//        Kinetic/Thermal Resist 3A Shield Generator (18 months ago)
//
//        Long Range/High Capacity 2B Rail Gun with Feedback Cascade (2 available, 18 months ago - winning CG)
//
//        High Capacity/Increased Caustic Damage 2B Enzyme Missile Rack (18 months ago - losing CG)
//
//        Fast Scan/Long Range Kill Warrant Scanner (17 months ago)
//
//        Lightweight/Shielded Electronic Countermeasure (17 months ago)
//
//        Long Range 1D Abrasion Blaster (13 months ago - versus CG which lost. Notably the winning CG's module is available now)
//
//        Short Range 2E Multi-cannon with Phasing Sequence (12 months ago - losing versus CG)
//
//        High Capacity/Rapid Fire 2B Seeker Missile Rack with Drag Munitions (12 months ago - winning versus CG)
//
//        Increased Range/Faster Boot 3A/4A/6A Frame Shift Drive (available twice, 10 months and 8 months ago)
//
//        Double High Capacity Heat Sink Launcher (6 months ago)