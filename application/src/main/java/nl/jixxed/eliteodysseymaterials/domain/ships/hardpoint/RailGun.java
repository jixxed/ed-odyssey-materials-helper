package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.constants.horizons.ExperimentalEffectBlueprints;
import nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints.RailGunBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.List;
import java.util.Map;

public class RailGun extends HardpointModule {
    //    	      84140 : { mtype:'hrg', cost:   51600, name:'Rail Gun',                    mount:'F',              class:1, rating:'D', mass: 2.00, integ:40, pwrdraw:1.15, boottime:0, dps:37.048, damage:23.340, distdraw:2.690,thmload:12.00, pierce:100, maximumrng:3000, duration:1.0, rof:1.587, bstint:0.630,                      ammoclip: 1, ammomax:  80,            rldtime:1.0, brcdmg:22.2, minbrc:40, maxbrc:80,             thmwgt:15.56/.2334, kinwgt: 7.78/.2334, dmgfall:1000, ammocost:75, fdid:128049488, fdname:'Hpt_Railgun_Fixed_Small', eddbid:876 },
    //            84220 : { mtype:'hrg', cost:  412800, name:'Rail Gun',                    mount:'F',              class:2, rating:'B', mass: 4.00, integ:51, pwrdraw:1.63, boottime:0, dps:50.036, damage:41.530, distdraw:5.110,thmload:20.00, pierce:100, maximumrng:3000, duration:1.2, rof:1.205, bstint:0.830,                      ammoclip: 1, ammomax:  80,            rldtime:1.0, brcdmg:39.5, minbrc:40, maxbrc:80,             thmwgt:27.69/.4153, kinwgt:13.84/.4153, dmgfall:1000, ammocost:75, fdid:128049489, fdname:'Hpt_Railgun_Fixed_Medium', eddbid:877 },
    //            84224 : { mtype:'hrg', cost:  619200, name:'Imperial Hammer Rail Gun',    mount:'F', tag:'P',     class:2, rating:'B', mass: 4.00, integ:51, pwrdraw:1.63, boottime:0, dps:61.364, damage:15.000, distdraw:2.000,thmload:11.00, pierce:100, maximumrng:3000, duration:1.2, rof:4.091, bstint:0.400, bstrof:6, bstsize:3, ammoclip: 3, ammomax: 240,            rldtime:1.2, brcdmg:14.3, minbrc:40, maxbrc:80,             thmwgt:10   /.15  , kinwgt: 5   /.15  , dmgfall:1000, ammocost:75, fdid:128671341, fdname:'Hpt_Railgun_Fixed_Medium_Burst', eddbid:1484 }, // powerplay // verify
    public static final RailGun RAIL_GUN_1_D_F = new RailGun("RAIL_GUN_1_D_F", HorizonsBlueprintName.RAIL_GUN, ModuleSize.SIZE_1, ModuleClass.D, false, Mounting.FIXED, 51600, "Hpt_Railgun_Fixed_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.15), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  37.048), Map.entry(HorizonsModifier.DAMAGE,  23.34), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  2.69), Map.entry(HorizonsModifier.THERMAL_LOAD,  12.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  100.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.DURATION,  1.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.587), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.63), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  80.0), Map.entry(HorizonsModifier.RELOAD_TIME,  1.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  22.2), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  40.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  80.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.667), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 0.333), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1000.0)));
    public static final RailGun RAIL_GUN_2_B_F = new RailGun("RAIL_GUN_2_B_F", HorizonsBlueprintName.RAIL_GUN, ModuleSize.SIZE_2, ModuleClass.B, false, Mounting.FIXED, 412800, "Hpt_Railgun_Fixed_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.63), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  50.036), Map.entry(HorizonsModifier.DAMAGE,  41.53), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  5.11), Map.entry(HorizonsModifier.THERMAL_LOAD,  20.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  100.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.DURATION,  1.2), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.205), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.83), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  80.0), Map.entry(HorizonsModifier.RELOAD_TIME,  1.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  39.5), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  40.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  80.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.667), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 0.333), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1000.0)));
    public static final RailGun IMPERIAL_HAMMER_RAIL_GUN_2_B_F = new RailGun("IMPERIAL_HAMMER_RAIL_GUN_2_B_F", HorizonsBlueprintName.IMPERIAL_HAMMER_RAIL_GUN, ModuleSize.SIZE_2, ModuleClass.B, Origin.POWERPLAY, false, Mounting.FIXED, 619200, "Hpt_Railgun_Fixed_Medium_Burst", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.63), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  61.364), Map.entry(HorizonsModifier.DAMAGE,  15.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  2.0), Map.entry(HorizonsModifier.THERMAL_LOAD,  11.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  100.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.DURATION,  1.2), Map.entry(HorizonsModifier.RATE_OF_FIRE,  4.091), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.4), Map.entry(HorizonsModifier.BURST_RATE_OF_FIRE,  6.0), Map.entry(HorizonsModifier.BURST_SIZE,  3.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  3.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  240.0), Map.entry(HorizonsModifier.RELOAD_TIME,  1.2), Map.entry(HorizonsModifier.BREACH_DAMAGE,  14.3), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  40.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  80.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.667), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 0.333), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1000.0)));
    public static final List<RailGun> RAIL_GUNS = List.of(
            RAIL_GUN_1_D_F,
            RAIL_GUN_2_B_F,
            IMPERIAL_HAMMER_RAIL_GUN_2_B_F
    );
    public RailGun(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public RailGun(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public RailGun(RailGun railGun) {
        super(railGun);
    }


    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return RailGunBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return ExperimentalEffectBlueprints.RAIL_GUN.keySet().stream().toList();
    }

    @Override
    public RailGun Clone() {
        return new RailGun(this);
    }
    @Override
    public String getClarifier() {
        if(IMPERIAL_HAMMER_RAIL_GUN_2_B_F.equals(this)){
            return " " + LocaleService.getLocalizedStringForCurrentLocale(getName().getLocalizationKey());
        }
        return super.getClarifier();
    }
}
