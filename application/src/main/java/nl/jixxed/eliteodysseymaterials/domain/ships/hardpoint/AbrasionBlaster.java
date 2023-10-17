package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AbrasionBlaster extends HardpointModule {
    //              81141 : { mtype:'hmtl',cost:    9700, name:'Abrasion Blaster',            mount:'F',              class:1, rating:'D', mass: 2.00, integ:40, pwrdraw:0.34, boottime:0, dps:20.000, damage: 4.000, distdraw:2.000, thmload:2.00, pierce: 18, maximumrng:1000, shotspd: 667, rof:5.000, bstint:0.200,  brcdmg: 0.6, minbrc:10, maxbrc:20,  thmwgt:100, dmgfall:1000, noblueprints:{'*':1}, noexpeffects:{'*':1}, fdid:128915458, fdname:'Hpt_Mining_AbrBlstr_Fixed_Small', eddbid:1789 },
    //              81143 : { mtype:'hmtl',cost:   27480, name:'Abrasion Blaster',            mount:'T',              class:1, rating:'D', mass: 2.00, integ:40, pwrdraw:0.47, boottime:0, dps:20.000, damage: 4.000, distdraw:2.000, thmload:1.80, pierce: 18, maximumrng:1000, shotspd: 667, rof:5.000, bstint:0.200,  brcdmg: 0.6, minbrc:10, maxbrc:20,  thmwgt:100, dmgfall:1000, noblueprints:{'*':1}, noexpeffects:{'*':1}, fdid:128915459, fdname:'Hpt_Mining_AbrBlstr_Turret_Small', eddbid:1790 },
    public static final AbrasionBlaster ABRASION_BLASTER_1_D_F = new AbrasionBlaster("ABRASION_BLASTER_1_D_F", HorizonsBlueprintName.ABRASION_BLASTER, ModuleSize.SIZE_1, ModuleClass.D, false, Mounting.FIXED   ,  9700, "Hpt_Mining_AbrBlstr_Fixed_Small" , Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.00), Map.entry(HorizonsModifier.INTEGRITY, 40.00), Map.entry(HorizonsModifier.POWER_DRAW, 0.34), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 20.00), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 2.00), Map.entry(HorizonsModifier.THERMAL_LOAD, 2.00), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 18.00), Map.entry(HorizonsModifier.MAXIMUM_RANGE, 1000.00), Map.entry(HorizonsModifier.SHOT_SPEED, 667.00), Map.entry(HorizonsModifier.RATE_OF_FIRE, 5.00), Map.entry(HorizonsModifier.BURST_INTERVAL,0.200), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.6), Map.entry(HorizonsModifier.BREACH_CHANCE_MIN,10), Map.entry(HorizonsModifier.BREACH_CHANCE_MAX,20), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 1.00), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, 1000.00)));
    public static final AbrasionBlaster ABRASION_BLASTER_1_D_T = new AbrasionBlaster("ABRASION_BLASTER_1_D_T", HorizonsBlueprintName.ABRASION_BLASTER, ModuleSize.SIZE_1, ModuleClass.D, false, Mounting.TURRETED, 27480, "Hpt_Mining_AbrBlstr_Turret_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.00), Map.entry(HorizonsModifier.INTEGRITY, 40.00), Map.entry(HorizonsModifier.POWER_DRAW, 0.47), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 20.00), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 2.00), Map.entry(HorizonsModifier.THERMAL_LOAD, 1.80), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 18.00), Map.entry(HorizonsModifier.MAXIMUM_RANGE, 1000.00), Map.entry(HorizonsModifier.SHOT_SPEED, 667.00), Map.entry(HorizonsModifier.RATE_OF_FIRE, 5.00), Map.entry(HorizonsModifier.BURST_INTERVAL,0.200), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.6), Map.entry(HorizonsModifier.BREACH_CHANCE_MIN,10), Map.entry(HorizonsModifier.BREACH_CHANCE_MAX,20), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 1.00), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, 1000.00)));
    public static final AbrasionBlaster ABRASION_BLASTER_1_D_F_PRE = new AbrasionBlaster("ABRASION_BLASTER_1_D_F_PRE", HorizonsBlueprintName.ABRASION_BLASTER_PRE, ModuleSize.SIZE_1, ModuleClass.D, false, Mounting.FIXED   ,  9700, "Hpt_Mining_AbrBlstr_Fixed_Small" , Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.00), Map.entry(HorizonsModifier.INTEGRITY, 40.00), Map.entry(HorizonsModifier.POWER_DRAW, 0.34), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 20.00), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 2.00), Map.entry(HorizonsModifier.THERMAL_LOAD, 2.00), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 18.00), Map.entry(HorizonsModifier.MAXIMUM_RANGE, 1000.00), Map.entry(HorizonsModifier.SHOT_SPEED, 667.00), Map.entry(HorizonsModifier.RATE_OF_FIRE, 5.00), Map.entry(HorizonsModifier.BURST_INTERVAL,0.200), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.6), Map.entry(HorizonsModifier.BREACH_CHANCE_MIN,10), Map.entry(HorizonsModifier.BREACH_CHANCE_MAX,20), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 1.00), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, 1000.00)));
    static {
        ABRASION_BLASTER_1_D_F_PRE.getModifications().add(
                new Modification(HorizonsBlueprintType.LONG_RANGE_WEAPON_INCENDIARY_ROUNDS, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
    }
    public static final List<AbrasionBlaster> ABRASION_BLASTERS = List.of(
            ABRASION_BLASTER_1_D_F,
            ABRASION_BLASTER_1_D_T,
            ABRASION_BLASTER_1_D_F_PRE
    );

    public AbrasionBlaster(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public AbrasionBlaster(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public AbrasionBlaster(AbrasionBlaster abrasionBlaster) {
        super(abrasionBlaster);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints()  {
        return Collections.emptyList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects()  {
        return Collections.emptyList();
    }

    @Override
    public AbrasionBlaster Clone() {
        return new AbrasionBlaster(this);
    }

    @Override
    public boolean isPreEngineered() {
        return HorizonsBlueprintName.ABRASION_BLASTER_PRE.equals(this.getName());
    }

    @Override
    public String getClarifier() {
        return isPreEngineered() ? " " + LocaleService.getLocalizedStringForCurrentLocale(getName().getLocalizationKey()) : "";
    }

    @Override
    public boolean isCGExclusive() {
        return isPreEngineered();
    }
}
