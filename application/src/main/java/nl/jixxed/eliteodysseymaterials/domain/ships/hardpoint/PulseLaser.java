package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PulseLaser extends HardpointModule {
//            62160 : { mtype:'hpl', cost:    2200, name:'Pulse Laser',                 mount:'F',              class:1, rating:'F', mass: 2.00, integ:40, pwrdraw:0.39, boottime:0, dps: 7.885, damage: 2.050, distdraw:0.300, thmload:0.33, pierce: 20, maximumrng:3000, Rate of Fire:3.846, Burst Interval:0.260, Breach Damage: 1.7, Min Breach Chance:40, Max Breach Chance:80, Thermal Damage:100, Damage Falloff Start:500, fdid:128049381, fdname:'Hpt_PulseLaser_Fixed_Small', eddbid:823 },
//            62171 : { mtype:'hpl', cost:    6600, name:'Pulse Laser',                 mount:'G',              class:1, rating:'G', mass: 2.00, integ:40, pwrdraw:0.39, boottime:0, dps: 6.240, damage: 1.560, distdraw:0.310, thmload:0.31, pierce: 20, maximumrng:3000, Rate of Fire:4.000, Burst Interval:0.250, Breach Damage: 1.3, Min Breach Chance:40, Max Breach Chance:80, Thermal Damage:100, Damage Falloff Start:500, fdid:128049385, fdname:'Hpt_PulseLaser_Gimbal_Small', eddbid:826 },
//            62172 : { mtype:'hpl', cost:   26000, name:'Pulse Laser',                 mount:'T',              class:1, rating:'G', mass: 2.00, integ:40, pwrdraw:0.38, boottime:0, dps: 3.967, damage: 1.190, distdraw:0.190, thmload:0.19, pierce: 20, maximumrng:3000, Rate of Fire:3.333, Burst Interval:0.300, Breach Damage: 1.0, Min Breach Chance:40, Max Breach Chance:80, Thermal Damage:100, Damage Falloff Start:500, fdid:128049388, fdname:'Hpt_PulseLaser_Turret_Small', eddbid:829 },
//            62250 : { mtype:'hpl', cost:   17600, name:'Pulse Laser',                 mount:'F',              class:2, rating:'E', mass: 4.00, integ:51, pwrdraw:0.60, boottime:0, dps:12.069, damage: 3.500, distdraw:0.500, thmload:0.56, pierce: 35, maximumrng:3000, Rate of Fire:3.448, Burst Interval:0.290, Breach Damage: 3.0, Min Breach Chance:40, Max Breach Chance:80, Thermal Damage:100, Damage Falloff Start:500, fdid:128049382, fdname:'Hpt_PulseLaser_Fixed_Medium', eddbid:824 },
//            62261 : { mtype:'hpl', cost:   35400, name:'Pulse Laser',                 mount:'G',              class:2, rating:'F', mass: 4.00, integ:51, pwrdraw:0.60, boottime:0, dps: 9.571, damage: 2.680, distdraw:0.540, thmload:0.54, pierce: 35, maximumrng:3000, Rate of Fire:3.571, Burst Interval:0.280, Breach Damage: 2.3, Min Breach Chance:40, Max Breach Chance:80, Thermal Damage:100, Damage Falloff Start:500, fdid:128049386, fdname:'Hpt_PulseLaser_Gimbal_Medium', eddbid:827 },
//            62262 : { mtype:'hpl', cost:  132800, name:'Pulse Laser',                 mount:'T',              class:2, rating:'F', mass: 4.00, integ:51, pwrdraw:0.58, boottime:0, dps: 6.212, damage: 2.050, distdraw:0.330, thmload:0.33, pierce: 35, maximumrng:3000, Rate of Fire:3.030, Burst Interval:0.330, Breach Damage: 1.7, Min Breach Chance:40, Max Breach Chance:80, Thermal Damage:100, Damage Falloff Start:500, fdid:128049389, fdname:'Hpt_PulseLaser_Turret_Medium', eddbid:830 },
//            62350 : { mtype:'hpl', cost:   70400, name:'Pulse Laser',                 mount:'F',              class:3, rating:'D', mass: 8.00, integ:64, pwrdraw:0.90, boottime:0, dps:18.121, damage: 5.980, distdraw:0.860, thmload:0.96, pierce: 52, maximumrng:3000, Rate of Fire:3.030, Burst Interval:0.330, Breach Damage: 5.1, Min Breach Chance:40, Max Breach Chance:80, Thermal Damage:100, Damage Falloff Start:500, fdid:128049383, fdname:'Hpt_PulseLaser_Fixed_Large', eddbid:825 },
//            62361 : { mtype:'hpl', cost:  140600, name:'Pulse Laser',                 mount:'G',              class:3, rating:'E', mass: 8.00, integ:64, pwrdraw:0.92, boottime:0, dps:14.774, damage: 4.580, distdraw:0.920, thmload:0.92, pierce: 52, maximumrng:3000, Rate of Fire:3.226, Burst Interval:0.310, Breach Damage: 3.9, Min Breach Chance:40, Max Breach Chance:80, Thermal Damage:100, Damage Falloff Start:500, fdid:128049387, fdname:'Hpt_PulseLaser_Gimbal_Large', eddbid:828 },
//            62362 : { mtype:'hpl', cost:  400400, name:'Pulse Laser',                 mount:'T',              class:3, rating:'F', mass: 8.00, integ:64, pwrdraw:0.89, boottime:0, dps: 9.459, damage: 3.500, distdraw:0.560, thmload:0.56, pierce: 52, maximumrng:3000, Rate of Fire:2.703, Burst Interval:0.370, Breach Damage: 3.0, Min Breach Chance:40, Max Breach Chance:80, Thermal Damage:100, Damage Falloff Start:500, fdid:128049390, fdname:'Hpt_PulseLaser_Turret_Large', eddbid:831 },
//            62410 : { mtype:'hpl', cost:  177600, name:'Pulse Laser',                 mount:'F',              class:4, rating:'A', mass:16.00, integ:80, pwrdraw:1.33, boottime:0, dps:26.947, damage:10.240, distdraw:1.480, thmload:1.64, pierce: 65, maximumrng:3000, Rate of Fire:2.632, Burst Interval:0.380, Breach Damage: 8.7, Min Breach Chance:40, Max Breach Chance:80, Thermal Damage:100, Damage Falloff Start:500, fdid:128049384, fdname:'Hpt_PulseLaser_Fixed_Huge', eddbid:1539 },
//            62411 : { mtype:'hpl', cost:  877600, name:'Pulse Laser',                 mount:'G',              class:4, rating:'A', mass:16.00, integ:80, pwrdraw:1.37, boottime:0, dps:21.722, damage: 7.820, distdraw:1.560, thmload:1.56, pierce: 65, maximumrng:3000, Rate of Fire:2.778, Burst Interval:0.360, Breach Damage: 6.6, Min Breach Chance:40, Max Breach Chance:80, Thermal Damage:100, Damage Falloff Start:500, fdid:128681995, fdname:'Hpt_PulseLaser_Gimbal_Huge', eddbid:1545 },

//            62254 : { mtype:'hpl', cost:   26400, name:'Pulse Disruptor Laser',       mount:'F', tag:'P',     class:2, rating:'E', mass: 4.00, integ:51, pwrdraw:0.70, boottime:0, dps: 4.667, damage: 2.800, distdraw:0.900, thmload:1.00, pierce: 35, maximumrng:3000, rof:1.667, bstint:0.600, brcdmg: 2.4, minbrc:40, maxbrc:80, thmwgt:100, dmgfall:500, fdid:128671342, fdname:'Hpt_PulseLaser_Fixed_Medium_Disruptor', eddbid:1808 }, // powerplay // verify

    public static final PulseLaser PULSE_LASER_1_F_F = new PulseLaser(HorizonsBlueprintName.PULSE_LASER, ModuleSize.SIZE_1, ModuleClass.F, false, Mounting.FIXED, 2200, "Hpt_PulseLaser_Fixed_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.00)));
    public static final PulseLaser PULSE_LASER_1_G_G = new PulseLaser(HorizonsBlueprintName.PULSE_LASER, ModuleSize.SIZE_1, ModuleClass.G, false, Mounting.GIMBALLED, 6600, "Hpt_PulseLaser_Gimbal_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.00)));
    public static final PulseLaser PULSE_LASER_1_G_T = new PulseLaser(HorizonsBlueprintName.PULSE_LASER, ModuleSize.SIZE_1, ModuleClass.G, false, Mounting.TURRETED, 26000, "Hpt_PulseLaser_Turret_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.00)));
    public static final PulseLaser PULSE_LASER_2_E_F = new PulseLaser(HorizonsBlueprintName.PULSE_LASER, ModuleSize.SIZE_2, ModuleClass.E, false, Mounting.FIXED, 17600, "Hpt_PulseLaser_Fixed_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00)));
    public static final PulseLaser PULSE_LASER_2_F_G = new PulseLaser(HorizonsBlueprintName.PULSE_LASER, ModuleSize.SIZE_2, ModuleClass.F, false, Mounting.GIMBALLED, 35400, "Hpt_PulseLaser_Gimbal_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00)));
    public static final PulseLaser PULSE_LASER_2_F_T = new PulseLaser(HorizonsBlueprintName.PULSE_LASER, ModuleSize.SIZE_2, ModuleClass.F, false, Mounting.TURRETED, 132800, "Hpt_PulseLaser_Turret_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00)));
    public static final PulseLaser PULSE_LASER_3_D_F = new PulseLaser(HorizonsBlueprintName.PULSE_LASER, ModuleSize.SIZE_3, ModuleClass.D, false, Mounting.FIXED, 70400, "Hpt_PulseLaser_Fixed_Large", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00)));
    public static final PulseLaser PULSE_LASER_3_E_G = new PulseLaser(HorizonsBlueprintName.PULSE_LASER, ModuleSize.SIZE_3, ModuleClass.E, false, Mounting.GIMBALLED, 140600, "Hpt_PulseLaser_Gimbal_Large", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00)));
    public static final PulseLaser PULSE_LASER_3_F_T = new PulseLaser(HorizonsBlueprintName.PULSE_LASER, ModuleSize.SIZE_3, ModuleClass.F, false, Mounting.TURRETED, 400400, "Hpt_PulseLaser_Turret_Large", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00)));
    public static final PulseLaser PULSE_LASER_4_A_F = new PulseLaser(HorizonsBlueprintName.PULSE_LASER, ModuleSize.SIZE_4, ModuleClass.A, false, Mounting.FIXED, 177600, "Hpt_PulseLaser_Fixed_Huge", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 16.00)));
    public static final PulseLaser PULSE_LASER_4_A_G = new PulseLaser(HorizonsBlueprintName.PULSE_LASER, ModuleSize.SIZE_4, ModuleClass.A, false, Mounting.GIMBALLED, 877600, "Hpt_PulseLaser_Gimbal_Huge", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 16.00)));
    public static final PulseLaser PULSE_DISRUPTOR_LASER_2_E = new PulseLaser(HorizonsBlueprintName.PULSE_DISRUPTOR_LASER, ModuleSize.SIZE_2, ModuleClass.E, Origin.POWERPLAY, false, Mounting.FIXED, 877600, "Hpt_PulseLaser_Fixed_Medium_Disruptor", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00)));

    public static final List<PulseLaser> PULSE_LASERS = List.of(
            PULSE_LASER_1_F_F,
            PULSE_LASER_1_G_G,
            PULSE_LASER_1_G_T,
            PULSE_LASER_2_E_F,
            PULSE_LASER_2_F_G,
            PULSE_LASER_2_F_T,
            PULSE_LASER_3_D_F,
            PULSE_LASER_3_E_G,
            PULSE_LASER_3_F_T,
            PULSE_LASER_4_A_F,
            PULSE_LASER_4_A_G,
            PULSE_DISRUPTOR_LASER_2_E
    );

    private PulseLaser(final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final boolean multiCrew, final Mounting mounting, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    private PulseLaser(final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final Origin origin, final boolean multiCrew, final Mounting mounting, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    private PulseLaser(final PulseLaser pulseLaser) {
        super(pulseLaser);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return Collections.emptyList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public PulseLaser Clone() {
        return new PulseLaser(this);
    }

    @Override
    public String getClarifier() {
        final String clarifier = (this == PULSE_DISRUPTOR_LASER_2_E ? " " + LocaleService.getLocalizedStringForCurrentLocale("blueprint.horizons.name.pulse_disruptor_laser") : "");
        return clarifier;
    }
}
