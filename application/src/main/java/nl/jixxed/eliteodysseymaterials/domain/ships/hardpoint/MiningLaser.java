package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.constants.horizons.ExperimentalEffectBlueprints;
import nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints.MiningLaserBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MiningLaser extends HardpointModule {
    //    		  81140 : { mtype:'hmtl',cost:    6800, name:'Mining Laser',                mount:'F',              class:1, rating:'D', mass: 2.00, integ:40, pwrdraw:0.50, boottime:0, dps: 2.000, damage: 2.000, distdraw:1.500, thmload:2.00, pierce: 18, maximumrng: 500,               rof:  1/0, bstint:0    ,                                                                         brcdmg: 0.3, minbrc:10, maxbrc:20,             thmwgt:100, dmgfall: 300,                                             fdid:128049525, fdname:'Hpt_MiningLaser_Fixed_Small', eddbid:888 },
    //            81142 : { mtype:'hmtl',cost:    9400, name:'Mining Laser',                mount:'T',              class:1, rating:'D', mass: 2.00, integ:40, pwrdraw:0.50, boottime:0, dps: 2.000, damage: 2.000, distdraw:1.500, thmload:2.00, pierce: 18, maximumrng: 500,               rof:  1/0, bstint:0    ,                                                                         brcdmg: 0.3, minbrc:10, maxbrc:20,             thmwgt:100, dmgfall: 300, noblueprints:{'*':1}, noexpeffects:{'*':1}, fdid:128740819, fdname:'Hpt_MiningLaser_Turret_Small', eddbid:1587 },
    //            81240 : { mtype:'hmtl',cost:   22580, name:'Mining Laser',                mount:'F',              class:2, rating:'D', mass: 2.00, integ:51, pwrdraw:0.75, boottime:0, dps: 4.000, damage: 4.000, distdraw:3.000, thmload:4.00, pierce: 18, maximumrng: 500,               rof:  1/0, bstint:0    ,                                                                         brcdmg: 0.6, minbrc:10, maxbrc:20,             thmwgt:100, dmgfall: 300, noblueprints:{'*':1}, noexpeffects:{'*':1}, fdid:128049526, fdname:'Hpt_MiningLaser_Fixed_Medium', eddbid:889 },
    //            81242 : { mtype:'hmtl',cost:   32580, name:'Mining Laser',                mount:'T',              class:2, rating:'D', mass: 2.00, integ:51, pwrdraw:0.75, boottime:0, dps: 4.000, damage: 4.000, distdraw:3.000, thmload:4.00, pierce: 18, maximumrng: 500,               rof:  1/0, bstint:0    ,                                                                         brcdmg: 0.6, minbrc:10, maxbrc:20,             thmwgt:100, dmgfall: 300, noblueprints:{'*':1}, noexpeffects:{'*':1}, fdid:128740820, fdname:'Hpt_MiningLaser_Turret_Medium', eddbid:1588 },
    //            81144 : { mtype:'hmtl',cost:   33860, name:'Mining Lance Beam Laser',     mount:'F', tag:'P',     class:1, rating:'D', mass: 2.00, integ:40, pwrdraw:0.70, boottime:0, dps: 8.000, damage: 8.000, distdraw:1.750, thmload:6.00, pierce: 18, maximumrng:2000,               rof:  1/0, bstint:0    ,                                                                         brcdmg: 1.2, minbrc:10, maxbrc:20,             thmwgt:100, dmgfall: 500, noblueprints:{'*':1}, noexpeffects:{'*':1}, fdid:128671340, fdname:'Hpt_MiningLaser_Fixed_Small_Advanced', eddbid:1479 }, // powerplay
    public static final MiningLaser MINING_LASER_1_D_F = new MiningLaser("MINING_LASER_1_D_F", HorizonsBlueprintName.MINING_LASER, ModuleSize.SIZE_1, ModuleClass.D, false, Mounting.FIXED, 6800, "Hpt_MiningLaser_Fixed_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.5), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  2.0), Map.entry(HorizonsModifier.DAMAGE,  2.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  1.5), Map.entry(HorizonsModifier.THERMAL_LOAD,  2.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  18.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  500.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.3), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  10.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  20.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO,  100.0), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  300.0)));
    public static final MiningLaser MINING_LASER_1_D_T = new MiningLaser("MINING_LASER_1_D_T", HorizonsBlueprintName.MINING_LASER, ModuleSize.SIZE_1, ModuleClass.D, true, Mounting.TURRETED, 9400, "Hpt_MiningLaser_Turret_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.5), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  2.0), Map.entry(HorizonsModifier.DAMAGE,  2.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  1.5), Map.entry(HorizonsModifier.THERMAL_LOAD,  2.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  18.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  500.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.3), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  10.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  20.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO,  100.0), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  300.0)));
    public static final MiningLaser MINING_LASER_2_D_F = new MiningLaser("MINING_LASER_2_D_F", HorizonsBlueprintName.MINING_LASER, ModuleSize.SIZE_2, ModuleClass.D, false, Mounting.FIXED, 22580, "Hpt_MiningLaser_Fixed_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.75), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  4.0), Map.entry(HorizonsModifier.DAMAGE,  4.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  3.0), Map.entry(HorizonsModifier.THERMAL_LOAD,  4.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  18.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  500.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.6), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  10.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  20.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO,  100.0), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  300.0)));
    public static final MiningLaser MINING_LASER_2_D_T = new MiningLaser("MINING_LASER_2_D_T", HorizonsBlueprintName.MINING_LASER, ModuleSize.SIZE_2, ModuleClass.D, true, Mounting.TURRETED, 32580, "Hpt_MiningLaser_Turret_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.75), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  4.0), Map.entry(HorizonsModifier.DAMAGE,  4.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  3.0), Map.entry(HorizonsModifier.THERMAL_LOAD,  4.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  18.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  500.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.6), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  10.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  20.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO,  100.0), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  300.0)));
    public static final MiningLaser MINING_LANCE_BEAM_LASER_1_D_F = new MiningLaser("MINING_LANCE_BEAM_LASER_1_D_F", HorizonsBlueprintName.LANCE_BEAM_MINING_LASER, ModuleSize.SIZE_1, ModuleClass.D, Origin.POWERPLAY, false, Mounting.FIXED, 33860, "Hpt_MiningLaser_Fixed_Small_Advanced", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.7), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  8.0), Map.entry(HorizonsModifier.DAMAGE,  8.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  1.75), Map.entry(HorizonsModifier.THERMAL_LOAD,  6.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  18.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  2000.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  1.2), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  10.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  20.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO,  100.0), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  500.0)));
    public static final MiningLaser MINING_LASER_1_D_F_PRE = new MiningLaser("MINING_LASER_1_D_F_PRE", HorizonsBlueprintName.MINING_LASER, ModuleSize.SIZE_1, ModuleClass.D, false, Mounting.FIXED, 6800, "Hpt_MiningLaser_Fixed_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.5), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  2.0), Map.entry(HorizonsModifier.DAMAGE,  2.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  1.5), Map.entry(HorizonsModifier.THERMAL_LOAD,  2.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  18.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  500.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.3), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  10.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  20.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO,  100.0), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  300.0)));
    static {
        MINING_LASER_1_D_F_PRE.getModifications().add(
            new Modification(HorizonsBlueprintType.LONG_RANGE_WEAPON, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
        MINING_LASER_1_D_F_PRE.getExperimentalEffects().add(
            HorizonsBlueprintType.INCENDIARY_ROUNDS
        );
    }
    public static final List<MiningLaser> MINING_LASERS = List.of(
            MINING_LASER_1_D_F,
            MINING_LASER_1_D_T,
            MINING_LASER_2_D_F,
            MINING_LASER_2_D_T,
            MINING_LANCE_BEAM_LASER_1_D_F,
            MINING_LASER_1_D_F_PRE
    );
    public MiningLaser(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public MiningLaser(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public MiningLaser(MiningLaser miningLaser) {
        super(miningLaser);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        if(isPreEngineered()){
            return MiningLaserBlueprints.BLUEPRINTS.keySet().stream().toList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        if(isPreEngineered()){
            return ExperimentalEffectBlueprints.MINING_LASER.keySet().stream().toList();
        }
        return Collections.emptyList();
    }

    @Override
    public MiningLaser Clone() {
        return new MiningLaser(this);
    }

    @Override
    public boolean isPreEngineered() {
        return MINING_LASER_1_D_F_PRE.equals(this);
    }

    @Override
    public String getClarifier() {
        if(MINING_LANCE_BEAM_LASER_1_D_F.equals(this)){
            return " " + LocaleService.getLocalizedStringForCurrentLocale(getName().getLocalizationKey()) ;
        }
        return super.getClarifier();
    }
}
